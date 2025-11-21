package com.zainab.journalApp.service;

import com.zainab.journalApp.api.response.QuoteResponse;
import com.zainab.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class QuoteService {
    @Value("${quotes.api.key}")
    private  String apiKey;
   // private static final String API="https://api.api-ninjas.com/v1/quotes";

    @Autowired
    private AppCache appCache;
    @Autowired
    private RedisService redisService;

    public String getQuote() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Use exchange() with ParameterizedTypeReference to handle List response
        QuoteResponse quoteResponse = redisService.get("Today", QuoteResponse.class);
        if(quoteResponse != null) {
            return quoteResponse.getQuote();
        }
        else{
            ResponseEntity<List<QuoteResponse>> response = restTemplate.exchange(
                    appCache.App_Cache.get(AppCache.keys.quotes_api.toString()),
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<QuoteResponse>>() {}
            );
            List<QuoteResponse> quotes = response.getBody();
            if(quotes != null) {
                redisService.set("Today",quotes.get(0),300l);
            }
            return (quotes != null && !quotes.isEmpty()) ? quotes.get(0).getQuote() : "Quote not available.";

        }



    }
}

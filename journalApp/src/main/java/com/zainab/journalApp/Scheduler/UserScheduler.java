package com.zainab.journalApp.Scheduler;


import com.zainab.journalApp.Enum.Sentiment;
import com.zainab.journalApp.cache.AppCache;
import com.zainab.journalApp.entity.JournalEntry;
import com.zainab.journalApp.entity.User;
import com.zainab.journalApp.model.SentimentData;
import com.zainab.journalApp.repository.UserRepositoryImpl;
import com.zainab.journalApp.service.EmailService;
import com.zainab.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepositoryImpl userRepository;
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;
    @Autowired
    private AppCache appCache;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;


    @Scheduled(cron="0 0 9 * * Mon")
    public void fetchUsersAndSendSaMail(){
        List<User> users = userRepository.getUserForSA();
        for(User user : users){
            List<JournalEntry>journalEntries=user.getJournal_entries();
            List<Sentiment> sentiments=journalEntries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment,Integer> sentimentCounts= new HashMap<>();
            for(Sentiment sentiment: sentiments){
                if(sentiment !=null)
                    sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment,0)+1);

            }
            Sentiment mostFrequentSentiment=null;
            int maxCount=0;
            for (Map.Entry<Sentiment,Integer> entry : sentimentCounts.entrySet()){
                if(entry.getValue()>maxCount){
                    maxCount=entry.getValue();
                    mostFrequentSentiment=entry.getKey();
                }
            }
            if(mostFrequentSentiment!=null){
                SentimentData sentimentData= SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days "+mostFrequentSentiment).build();
                try {
                    kafkaTemplate.send("weekly_sentiments", sentimentData.getEmail(),sentimentData);
                } catch (Exception e) {
                    emailService.sendEmail(user.getEmail(),"Sentiment for last seven days",mostFrequentSentiment.toString());

                }
//                emailService.sendEmail(user.getEmail(),"Sentiment for last seven days",mostFrequentSentiment.toString());
//                the service required  semd mail in the configuration panel

            }
        }

    }

    @Scheduled(cron="0 */10 * * * *")
    public void ClearAppCache(){
        appCache.init();
    }
}

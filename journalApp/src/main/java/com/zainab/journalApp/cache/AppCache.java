package com.zainab.journalApp.cache;


import com.zainab.journalApp.entity.ConfigJournalAppEntry;
import com.zainab.journalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {


    public enum keys{
        quotes_api;
    }

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;


    public Map<String,String> App_Cache;

    @PostConstruct
     public void init() {
         App_Cache=new HashMap<>();
        List<ConfigJournalAppEntry> all=configJournalAppRepository.findAll();
        for(ConfigJournalAppEntry ConfigJournalAppEntry:all){
            App_Cache.put(ConfigJournalAppEntry.getKey(),ConfigJournalAppEntry.getValue());
        }
    }
}

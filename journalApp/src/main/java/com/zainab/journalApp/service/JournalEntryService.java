package com.zainab.journalApp.service;

import com.zainab.journalApp.entity.JournalEntry;
import com.zainab.journalApp.entity.User;
import com.zainab.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    private  JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;


    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
       try{ User user=userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved=journalEntryRepository.save(journalEntry);
        user.getJournal_entries().add(saved);
        userService.saveEntry(user);}
       catch(Exception e){

           throw new RuntimeException("An error occurred while saving the entry",e);
       }
    }
    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }
    public  List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> FindById(ObjectId Id) {
        return journalEntryRepository.findById(Id);
    }
    @Transactional
    public boolean deleteById(ObjectId Id, String userName) {
        boolean removed=false;
        try {
            User user = userService.findByUserName(userName);
            removed = user.getJournal_entries().removeIf(journalEntry -> journalEntry.getId().equals(Id));
            if (removed) {
                userService.saveEntry(user);
                journalEntryRepository.deleteById(Id);
            }

        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occurred while saving the entry",e );
        }return removed;
    }

//    public List<JournalEntry> findByUserName(String userName) {}
}

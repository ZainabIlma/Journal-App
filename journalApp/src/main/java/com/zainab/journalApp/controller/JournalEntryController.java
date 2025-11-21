//package com.zainab.journalApp.controller;
//
//
//import com.zainab.journalApp.entity.JournalEntry;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/_journal")
//public class JournalEntryController {
//
//    private Map<String, JournalEntry> journalEntries= new HashMap<>();
//
//
//     @GetMapping
//    public List<JournalEntry> getAll() {
//        // Return all journal entries as a list
//        return new ArrayList<>(journalEntries.values());
//    }
//    @PostMapping
//    public boolean createEntry(@RequestBody JournalEntry myentry ) {
//        myentry.setDate(LocalDateTime.now());
//         journalEntries.put(myentry.getId(), myentry);
//        return true;
//    }
//    @GetMapping("id/{myId}")
//    public JournalEntry getJournalEntryById(@PathVariable Long myId){
//        return journalEntries.get(myId);
//    }
//    @DeleteMapping ("id/{myId}")
//    public JournalEntry DeleteJournalEntryById(@PathVariable Long myId){
//        return journalEntries.remove(myId);
//    }
//    @PutMapping
//    public boolean updateEntry(@RequestBody JournalEntry myentry) {
//        journalEntries.put(myentry.getId(), myentry);
//        return true;
//    }
//
//}

package com.zainab.journalApp.repository;

import com.zainab.journalApp.entity.JournalEntry;
import com.zainab.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String username);
    void deleteByUserName(String username);
}

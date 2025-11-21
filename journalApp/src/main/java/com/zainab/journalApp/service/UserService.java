package com.zainab.journalApp.service;

import com.zainab.journalApp.entity.User;
import com.zainab.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(User user) {

         userRepository.save(user);
    }
    //newCode
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public boolean saveNewUser(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            List<User> users = getAllUsers();
            log.info("Current users in DB: {}", users);

            log.info("User saved successfully");
            return true;
        }
        catch(Exception e){

            log.error("Error occurred while saving user: {}", user.getUserName(), e);

            return false;
        }

    }
    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("ADMIN","USER"));
        userRepository.save(user);
    }
    public  List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> FindById(ObjectId Id) {
        return userRepository.findById(Id);
    }
    public void deleteById(ObjectId Id) {
        userRepository.deleteById(Id);
    }
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

}

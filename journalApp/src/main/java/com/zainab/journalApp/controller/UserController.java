package com.zainab.journalApp.controller;


import com.zainab.journalApp.entity.User;
import com.zainab.journalApp.repository.UserRepository;
import com.zainab.journalApp.service.QuoteService;
import com.zainab.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository  userRepository;
    @Autowired
    private QuoteService quoteService;

    @PutMapping
    public ResponseEntity updateUser( @RequestBody User user) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User userInDB =userService.findByUserName(userName);
        userInDB.setUserName(user.getUserName());
        userInDB.setPassword(user.getPassword());
        userService.saveNewUser(userInDB);

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }
    @DeleteMapping
    public ResponseEntity<?> deleteUserById( ) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    public ResponseEntity<?> greeting( ) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String quote=quoteService.getQuote();
        String greeting="";
        if(quote!=null){
            greeting=quote;
        }

        return new ResponseEntity<>("Hi "+authentication.getName()+", Quote of the day is: "+greeting, HttpStatus.OK);
    }


}

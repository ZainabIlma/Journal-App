package com.zainab.journalApp.service;

import com.zainab.journalApp.entity.User;
import com.zainab.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class  UserServiceTests {
    @Autowired
    private UserRepository userRepository;
    @Test
    @Disabled
    public void testAddUser() {

//        assertEquals(4,5-2);
        User user=userRepository.findByUserName("Ram");
        assertTrue(!user.getJournal_entries().isEmpty());
    }
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,2,4",
            "3,3,9"
    })
    @Disabled
    public void test(int a,int b, int expected){
        assertEquals(expected,a+b);
    }
}

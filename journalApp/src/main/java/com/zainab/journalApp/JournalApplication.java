package com.zainab.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;




@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class JournalApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(JournalApplication.class, args);
	}
	@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}
	//new code
	@Component
	public class DatabaseCheck {

		@Autowired
		private MongoTemplate mongoTemplate;

		@EventListener(ApplicationReadyEvent.class)
		public void checkActiveDatabase() {
			System.out.println("Active Database: " + mongoTemplate.getDb().getName());
		}
	}


}

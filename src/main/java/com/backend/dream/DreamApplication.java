package com.backend.dream;

import com.backend.dream.entity.Account;
import com.backend.dream.service.imp.AccountServiceImp;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(DreamApplication.class, args);
	}


//	@Bean
//	CommandLineRunner run(AccountServiceImp accountServiceImp){
//		return args -> {
//			accountServiceImp.create(new Account("thanh","123"));
//			accountServiceImp.create(new Account("hung","123"));
//		};
//	};

}

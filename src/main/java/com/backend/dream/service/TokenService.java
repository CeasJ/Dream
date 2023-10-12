package com.backend.dream.service;


import com.backend.dream.entity.Account;

public interface TokenService {
	public String createVerificationTokenForUser(Account account) ;
	
	public com.backend.dream.entity.Token findByToken(String token);
	
	
}

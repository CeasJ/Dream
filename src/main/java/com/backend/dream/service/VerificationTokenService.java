package com.backend.dream.service;


import com.backend.dream.entity.Account;
import com.backend.dream.entity.Token;

public interface VerificationTokenService {
	public String createVerificationTokenForUser(Account account);
	
	public Token findByToken(String token);
	
	
}

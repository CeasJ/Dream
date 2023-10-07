package com.backend.dream.service.imp;

import com.backend.dream.entity.Account;
import com.backend.dream.entity.Token;
import com.backend.dream.repository.TokenRepository;
import com.backend.dream.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;


@Service
@EnableScheduling
public class VerificationTokenServiceImpl implements VerificationTokenService {

	@Autowired
	TokenRepository tokenRepository;

	public String createVerificationTokenForUser(Account account) {
//		Random random = new Random();
//		int tokenInt = random.nextInt(1000000);
//		String token = String.format("%06d", tokenInt);
//
//		VerificationToken verificationToken = new VerificationToken();
//		verificationToken.setToken(token);
//		verificationToken.setAccount(account);
//
//		LocalDateTime expiry_date = LocalDateTime.now().plusSeconds(60);
//		verificationToken.setExpiryDate(expiry_date);
//
//		verificationTokenDAO.save(verificationToken);
		return null;
	}

	@Override
	public Token findByToken(String token) {
		return tokenRepository.findByToken(token);
	}

}

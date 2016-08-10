package com.is.mpg.security;

import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.is.mpg.commons.Utility;

/**
 * UserDetailServiceImpl 에서 비밀번호 확인 할때 matches()를 호출해 비교한다.
 * 
 * @author iskwon
 */
public class PasswordEncoderImpl implements PasswordEncoder {

	private static final Logger logger = LoggerFactory.getLogger(PasswordEncoderImpl.class);
	
	@Override
	public String encode(CharSequence rawPassword) {
		return null;
	}

	@Override
	public boolean matches(CharSequence inputPassword, String memberPassword) {
		try {
			if (inputPassword != null) {
				String encoded = Utility.getEncryptedPassword(inputPassword.toString());
				return encoded.equals(memberPassword);
			}
		} catch (NoSuchAlgorithmException e) {
			logger.error("~~ [An error occurred!]", e);
		}
		return false;
	}

}

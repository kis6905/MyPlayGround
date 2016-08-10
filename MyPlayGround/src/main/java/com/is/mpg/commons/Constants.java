package com.is.mpg.commons;

/**
 * 상수는 최대한 겹치지 않도록 한다.
 * 관련 상수는 100의 자리를 맞춘다. (ex- 101 ~ 199, 201 ~ 299, 301 ~ 399...) 
 * 
 * @author iskwon
 */
public class Constants {
	
	public static final int COMMON_SERVER_ERROR	= 9999; // 서버 에러
	
	/**
	 * 공통으로 사용하는 Result Code
	 */
	public static final int OK		= 0;
	public static final int NOT_OK	= 1;
	
	/**
	 * 로그인 관련
	 */
	public static final int LOGIN_SUCCESS					= 101; // 성공
	public static final int LOGIN_FAIL_MISMATCH				= 102; // 계정 or 비밀번호 불일치
	public static final int LOGIN_FAIL_DISABLED				= 103; // 계정 비활성화
	public static final int LOGIN_FAIL_ACCOUNT_EXPIRED		= 104; // 계정 만료
	public static final int LOGIN_FAIL_CREDENTIALS_EXPIRED	= 105; // 계정 권한 만료
	public static final int LOGIN_FAIL_LOCKED				= 106; // 계정 잠김
	
}

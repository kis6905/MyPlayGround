package com.is.mpg.commons;

/**
 * com_code 테이블에서 사용 되는 코드.
 * DB에 저장된 코드 값과 동일해야한다.
 * 
 * @author iskwon
 */
public class Codes {
	
	/**
	 * 사용자 등급 코드
	 */
	public static final int GRADE_CODE_GROUP = 0;
		public static final int ADMIN_CODE	= 1001;
		public static final int USER_CODE	= 1002;
	
	/**
	 * 각종 정보 코드
	 */
	public static final int INFO_CODE_GROUP = 1;
		public static final int VALID_PASSWORD_CNT_CODE = 1101;
	
}

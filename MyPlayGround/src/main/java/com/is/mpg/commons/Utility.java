package com.is.mpg.commons;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utility {

	private static final Logger logger = LoggerFactory.getLogger(Utility.class);
	
	private static String swearwordRegex = "^*(씨발)|(씨 발)|(씨이발)|(시발)|(시 발)|"
										   + "(병신)|(새끼)|(좆)|(애미)|(에미)|(니앰)|(니엠)|"
										   + "(개새)|(개새끼)|(개 새끼)|(개 새 끼)|(개새 끼)+";
	
	private static String encryptSha256(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes());

		byte byteData[] = md.digest();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		 
		 return sb.toString();
	 }

	 /**
	  * password 암호화( SHA256(SHA256(password)) )
	  * 
      * @throws NoSuchAlgorithmException 
	  */
	 public static String getEncryptedPassword(String input) throws NoSuchAlgorithmException {
		String password = "";
		
		password = encryptSha256(input);
		password = encryptSha256(password);
		
		return password;
	 }
	 
	 /**
	  * Date 형식의 문자열 변환
	  * 
	  * @param inDate
	  * @param inFormat
	  * @param outFormat
	  */
	 public static String convertDateFormat(String inDate, String inFormat, String outFormat) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(inFormat);
			Date date = sdf.parse(inDate);
			sdf = new SimpleDateFormat(outFormat);
			
			return sdf.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	 }
	 
	 /**
	  * 입력받은 날짜와 현재 날짜의 년-월-일 비교
	  * 
	  * @param inDate
	  * @param inFormat
	  * @return 입력받은 날짜 = 현재 날짜 -> 0,
	  * 		입력받은 날짜 < 현재 날짜 -> 음수,
	  * 		입력받은 날짜 > 현재 날짜 -> 양수
	  */
	public static int compareSysdate(String inDate, String inFormat) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(inFormat);
			Date date = sdf.parse(inDate);
			
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			inDate = sdf.format(date);
			String sysDate = sdf.format(new Date());
			
			return inDate.compareTo(sysDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 특수 문자를 엔티티 코드로 변환
	 * 
	 * @param str
	 * @return
	 */
	public static String convertSpecialCharToEntity(String str) {
		if (str == null || str.isEmpty())
			return null;
		
		StringBuilder sb = new StringBuilder();
		char[] charArr = str.toCharArray();
		for (char c : charArr) {
			switch (c) {
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '(':
				sb.append("&#40;");
				break;
			case ')':
				sb.append("&#41;");
				break;
			case '#':
				sb.append("&#35;");
				break;
			case '&':
				sb.append("&#38;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}
	
	/**
	 * 엔터 key 코드인 13을 html <br/> 태그로 변경
	 * 
	 * @param str
	 * @return
	 */
	public static String convertEnterCodeToHtml(String str) {
		StringBuilder sb = new StringBuilder();
		char[] charArr = str.toCharArray();
		for (char c : charArr) {
			switch (c) {
			case 13:
				sb.append("<br/>");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}
	
	/**
	 * 비속어 필터링
	 * 
	 * @param content
	 * @return
	 */
	public static String swearwordFilter(String content) {
		return content.replaceAll(swearwordRegex, "**");
	}
	
	/**
	 * 현재 시간 문자열로 리턴
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentDateToString(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	
	/**
	 * Command 실행
	 * 
	 * @param Command
	 * @return Command 실행 결과
	 */
	public static String runCommand(String command) {
		logger.info("~~ [Shell command = {}]", command);
		String result = null;
		try {
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader br = new BufferedReader(new InputStreamReader(new SequenceInputStream(process.getInputStream(), process.getErrorStream())));
			String line = null;
			result = "";
			while ((line = br.readLine()) != null)
				result += line;
			logger.info("~~ [Command result = {}]", result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (result == null || result.isEmpty()) ? null : result;
	}
	
	public static String runCommand(String[] commands) {
		// 로그에 찍기 위해 하나의 문자열로 합침
		String command = "";
		for (String s : commands)
			command += s;
		
		logger.info("~~ [Shell commands = {}]", command);
		String result = null;
		try {
			Process process = Runtime.getRuntime().exec(commands);
			BufferedReader br = new BufferedReader(new InputStreamReader(new SequenceInputStream(process.getInputStream(), process.getErrorStream())));
			String line = null;
			result = "";
			while ((line = br.readLine()) != null)
				result += line;
			logger.info("~~ [Command result = {}]", result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (result == null || result.isEmpty()) ? null : result;
	}
	
}

package com.is.mpg.dao;

import com.is.mpg.vo.PersistentLogin;

/**
 * @author iskwon
 */
public interface PersistentLoginMapper {
	
	PersistentLogin getTokenForSeries(String series);
	int insertToken(PersistentLogin token);
	int updateToken(PersistentLogin token);
	int deleteToken(String memberId);
	
}

package com.is.mpg.vo;

import java.util.Date;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

public class PersistentLogin {
	
	private String memberId;
	private String series;
	private String tokenValue;
	private Date lastUsed;
	
	public PersistentLogin() {
	}
	
	public PersistentLogin(String memberId, String series, String tokenValue, Date lastUsed) {
		this.memberId = memberId;
		this.series = series;
		this.tokenValue = tokenValue;
		this.lastUsed = lastUsed;
	}

	public PersistentLogin(PersistentRememberMeToken persistentRememberMeToken) {
		this.memberId = persistentRememberMeToken.getUsername();
		this.series = persistentRememberMeToken.getSeries();
		this.tokenValue = persistentRememberMeToken.getTokenValue();
		this.lastUsed = persistentRememberMeToken.getDate();
	}

	public String getMemberId() {
		return memberId;
	}
	
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	public String getSeries() {
		return series;
	}
	
	public void setSeries(String series) {
		this.series = series;
	}
	
	public String getTokenValue() {
		return tokenValue;
	}
	
	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}
	
	public Date getLastUsed() {
		return lastUsed;
	}
	
	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}
	
}

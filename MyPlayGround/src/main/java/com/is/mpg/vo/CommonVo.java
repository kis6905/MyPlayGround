package com.is.mpg.vo;

import org.json.simple.JSONObject;


/**
 * VO에서 공통으로 쓰이는 항목의 VO
 * 
 * @author iskwon
 */
public class CommonVo {
	
	private String registeredDate = null;
	private String modifiedDate = null;
	
	public CommonVo() {
	}
	
	public String getRegisteredDate() {
		return registeredDate;
	}
	
	public void setRegisteredDate(String registeredDate) {
		this.registeredDate = registeredDate;
	}
	
	public String getModifiedDate() {
		return modifiedDate;
	}
	
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public JSONObject toJSONObject() {
		return null;
	}

	@Override
	public String toString() {
		return "CommonVo [registeredDate=" + registeredDate
				+ ", modifiedDate=" + modifiedDate
				+ "]";
	}
	
}

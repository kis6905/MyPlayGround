package com.is.mpg.vo;

public class ComCode {
	
	private Integer codeGroup = null;
	private Integer code = null;
	private String codeTitle = null;
	private String codeValue = null;
	
	public ComCode() {
	}
	
	public ComCode(int codeGroup, int code) {
		this.codeGroup = codeGroup;
		this.code = code;
	}

	public Integer getCodeGroup() {
		return codeGroup;
	}
	
	public void setCodeGroup(Integer codeGroup) {
		this.codeGroup = codeGroup;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public String getCodeTitle() {
		return codeTitle;
	}
	
	public void setCodeTitle(String codeTitle) {
		this.codeTitle = codeTitle;
	}
	
	public String getCodeValue() {
		return codeValue;
	}
	
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	@Override
	public String toString() {
		return "ComCode [codeGroup=" + codeGroup
				+ ", code=" + code
				+ ", codeTitle=" + codeTitle
				+ ", codeValue=" + codeValue
				+ "]";
	}
	
	
}

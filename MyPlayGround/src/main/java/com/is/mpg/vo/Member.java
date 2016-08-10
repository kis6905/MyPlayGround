package com.is.mpg.vo;

import org.json.simple.JSONObject;

public class Member extends CommonVo {
	
	private String memberId			= null;
	private String password			= null;
	private Integer passwordFailCnt	= null;
	private String nickname			= null;
	private Integer gradeCodeGroup	= null;
	private Integer gradeCode		= null;
	private String lastLoginDate	= null;
	
	public Member() {
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getPasswordFailCnt() {
		return passwordFailCnt;
	}

	public void setPasswordFailCnt(Integer passwordFailCnt) {
		this.passwordFailCnt = passwordFailCnt;
	}
	
	public Integer getGradeCodeGroup() {
		return gradeCodeGroup;
	}

	public void setGradeCodeGroup(Integer gradeCodeGroup) {
		this.gradeCodeGroup = gradeCodeGroup;
	}

	public Integer getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(Integer gradeCode) {
		this.gradeCode = gradeCode;
	}
	
	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("memberId", memberId);
		jsonObject.put("memberName", nickname);
		jsonObject.put("gradeCodeGroup", gradeCodeGroup);
		jsonObject.put("gradeCode", gradeCode);
		jsonObject.put("registeredDate", getRegisteredDate());
		jsonObject.put("modifiedDate", getModifiedDate());
		jsonObject.put("lastLoginDate", lastLoginDate);
		return jsonObject;
	}
	
	@Override
	public String toString() {
		return "Member [memberId=" + memberId
//				+ ", password=" + password // 보안상 출력 안함
				+ ", nickname=" + nickname
				+ ", passwordFailCnt=" + passwordFailCnt
				+ ", gradeCodeGroup=" + gradeCodeGroup
				+ ", gradeCode=" + gradeCode
				+ ", registeredDate=" + getRegisteredDate()
				+ ", modifiedDate=" + getModifiedDate()
				+ ", lastLoginDate=" + lastLoginDate
				+ "]";
	}
}

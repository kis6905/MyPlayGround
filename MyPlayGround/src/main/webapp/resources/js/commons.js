/**
 * Commons JS.
 * 
 * @author iskwon
 */

/*
 * 공통 상수
 */
var OK		= 0;
var NOT_OK	= 0;

/*
 * 로그인 관련 상수
 */
var LOGIN_SUCCESS					= 101; // 성공
var LOGIN_FAIL_MISMATCH				= 102; // 계정 or 비밀번호 불일치
var LOGIN_FAIL_DISABLED				= 103; // 계정 비활성화
var LOGIN_FAIL_ACCOUNT_EXPIRED		= 104; // 계정 만료
var LOGIN_FAIL_CREDENTIALS_EXPIRED	= 105; // 계정 권한 만료
var LOGIN_FAIL_LOCKED				= 106; // 계정 잠김

/*
 * 공통으로 사용되는 상수
 */
var COMMON_SERVER_ERROR	= 9999; // 서버 에러


/*
 * 정규 표현식
 */
var emailRegex = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
function validateEmail(email) {
    return emailRegex.test(email);
}


/* 
 * 현재 날짜에서 입력받은 날짜를 빼서 나온 일 수를 리턴한다.
 */
function diffFromSysdate(inputDate) {
	var sysdate = new Date();
	return Math.floor((sysdate.getTime() - inputDate.getTime()) / 1000 / 60 / 60 / 24);
}


/*
 * bootstrap-table에서 사용될 각종 포매터 정의
 */
// 닉네임 포매터
var nicknameFormatter = function(value, row, index) {
	if (row.nickname.length > 3)
		return row.nickname.substring(0, 3) + '..';
	else
		return row.nickname;
}

// 제목 포매터
var boardTitleFormatter = function(value, row, index) {
	var result;
	if (row.title.length > 15)
		result = row.title.substring(0, 16) + '...';
	else 
		result = row.title;
	
	result += '&nbsp; <span class="comment">[' + row.commentCount + ']</span>';
	
	var arr = row.registeredDate.substring(0, 10).split("-");
	var date = new Date(arr[0], arr[1] - 1, arr[2]); // 년, 월, 일
	if (diffFromSysdate(date) == 0)
		result += '&nbsp;&nbsp;<span class=\"label label-danger\">N</span>';
	
	return result;
}

// 날짜 포매터
var registeredDateFormatter = function(value, row, index) {
	var arr = row.registeredDate.substring(0, 10).split("-");
	var date = new Date(arr[0], arr[1] - 1, arr[2]); // 년, 월, 일
	
	if (diffFromSysdate(date) > 0)
		return row.registeredDate.substring(5, 10);
	else
		return row.registeredDate.substring(11, 16);
}


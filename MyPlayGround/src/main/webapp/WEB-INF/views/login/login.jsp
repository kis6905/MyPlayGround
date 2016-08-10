<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>

<%@ include file="../include/include.jsp" %>

<link href="/resources/css/bootstrap/login.css" rel="stylesheet">

<script type="text/javascript">

$(document).ready(function() {
	var rememberMe = $.cookie('autoLogin');
	
	if (typeof rememberMe != 'undefined' && rememberMe.length > 0) {
		location.href = '/main';
	}
});

function onClickLoginBtn() {
	var memberId = $('#memberId').val();
	var password = $('#password').val();
	
	if (memberId == null || memberId.length == 0) {
		alert('ID를 입력하세요.');
		return false;
	}
	
	if (password == null || password.length == 0) {
		alert('비밀번호를 입력하세요.');
		return false;
	}
	
	$('#loginForm').submit();
	
// 	if (validateEmail(memberId)) {
// 		$('#loginForm').submit();
// 	}
// 	else {
// 		alert('Email 형식에 맞지 않습니다.');
// 		$('#memberId').focus();
// 	}
}

/**
 * 엔터 처리
 */
function checkEnterKey(event) {
    var keyupEvent = event || window.event;

    if (keyupEvent.keyCode == 13) {
    	onClickLoginBtn();
    }
}

</script>

</head>

<body>
	<div class="container">
		<img id="loginTitle" align="middle" src="/resources/images/logo.png">
		<form class="form-signin" id="loginForm" action="/authentication" method="post">
			<label for="inputEmail" class="sr-only">ID</label>
			<input type="text" id="memberId" name="memberId" class="form-control" placeholder="ID" required autofocus>
			<label for="inputPassword" class="sr-only">Password</label>
			<input type="password" id="password" name="password" class="form-control" placeholder="Password" required onkeypress="checkEnterKey();">
			<div class="checkbox">
				<label>
					<input type="checkbox" name="remember-me-parameter"> 1주일 동안 자동 로그인
				</label>
			</div>
			<button class="btn btn-lg btn-primary btn-block" type="button" onclick="onClickLoginBtn()">Login</button>
			<button class="btn btn-lg btn-primary btn-block" type="button" onclick="location.href='/join';">회원가입</button>
		</form>
	</div>
	<!-- /container -->
</body>
</html>

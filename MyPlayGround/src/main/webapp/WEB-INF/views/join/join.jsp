<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>

<%@ include file="../include/include.jsp" %>

<script type="text/javascript">

var validId = false, validNickname = false;
var checkedId, checkNickname;

/**
 * 회원가입
 */
function onClickJoinBtn() {
	var id = $('#id').val();
	var password = $('#password').val();
	var nickname = $('#nickname').val();
	
	var checkId = false;
	var checkPassword = false;
	var checkNickname = false;
	
	if (id != null && id.length > 0) {
		if (validId) {
			if (id == checkedId) {
				checkId = true;
			}
			else {
				alert('ID 체크를 해주세요.');
				$('#id').focus();
				return false;
			}
		}
		else {
			alert('ID 체크를 해주세요.');
			$('#id').focus();
			return false;
		}
	}
	else {
		alert('ID를 입력하세요.');
		$('#id').focus();
		return false;
	}
	
	if (password != null && password.length >= 6) {
		checkPassword = true;
	}
	else {
		alert('비밀번호는 6자리 이상이여야 합니다.');
		$('#password').focus();
		return false;
	}
	
	if (nickname != null && nickname.length > 0) {
		if (validNickname) {
			if (nickname == checkedNickname) {
				checkNickname = true;
			}
			else {
				alert('닉네임 체크를 해주세요.');
				$('#nickname').focus();
				return false;
			}
		}
		else {
			alert('닉네임 체크를 해주세요.');
			$('#nickname').focus();
			return false;
		}
	}
	else {
		alert('닉네임을 입력하세요.');
		$('#nickname').focus();
		return false;
	}
	
	if (checkId && checkPassword && checkNickname)
		$('#joinForm').submit();
}

/**
 * ID 체크
 */
function onClickCheckId() {
	var id = $('#id').val();
	
	if (id != null && id.length > 0) {
		$.ajax({
			url: '/join/check/id',
			method: 'POST',
			dataType: 'json',
			data: {
				id: id
			},
			success: function(data, textStatus, jqXHR) {
				validId = data.result;
				if (data.result) {
					checkedId = id;
					alert('사용 가능한 ID 입니다.');
				}
				else {
					$('#id').val('');
					$('#id').focus();
					alert('이미 사용중인 ID 입니다.');
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert('오류가 발생했습니다. 다시 시도해주세요. \n이 문제가 계속될 경우 관리자에게 문의하세요.');
			}
		});
	}
	else {
		alert('ID를 입력하세요.');
		$('#id').focus();
	}
}

/**
 * 닉네임 체크
 */
function onClickCheckNickname() {
	var nickname = $('#nickname').val();
	
	if (nickname != null && nickname.length > 0) {
		$.ajax({
			url: '/join/check/nickname',
			method: 'POST',
			dataType: 'json',
			data: {
				nickname: nickname
			},
			success: function(data, textStatus, jqXHR) {
				validNickname = data.result;
				if (data.result) {
					checkedNickname = nickname;
					alert('사용 가능한 닉네임입니다.');
				}
				else {
					$('#nickname').val('');
					$('#nickname').focus();
					alert('이미 사용중인 닉네임입니다.');
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert('오류가 발생했습니다. 다시 시도해주세요. \n이 문제가 계속될 경우 관리자에게 문의하세요.');
			}
		});
	}
	else {
		alert('닉네임을 입력하세요.');
		$('#nickname').focus();
	}
}

function onChangeId() {
	validId = false;
}

function onChangeNickname() {
	validNickname = false;
}

</script>

</head>

<body>
	<div class="container">
		<div class="page-header">
			<h3>회원가입</h3>
		</div>
		
		<form class="form-horizontal" id="joinForm" action="/join" method="post">
			<!-- ID -->
			<div class="form-group">
				<label for="id" class="col-sm-2 control-label">ID</label>
				<div class="col-sm-10">
					<div class="input-group">
						<input type="text" id="id" name="id" class="form-control" placeholder="ID를 입력하세요" onkeydown="onChangeId();">
						<span class="input-group-btn">
							<button class="btn btn-default" type="button" onclick="onClickCheckId();">Check</button>
						</span>
					</div>
				</div>
			</div>
			
			<!-- Password -->
			<div class="form-group">
				<label for="password" class="col-sm-2 control-label">비밀번호</label>
				<div class="col-sm-10">
					<input type="password" id="password" name="password" class="form-control" placeholder="비밀번호를 입력하세요">
				</div>
			</div>
			
			<!-- Nickname -->
			<div class="form-group">
				<label for="nickname" class="col-sm-2 control-label">닉네임</label>
				<div class="col-sm-10">
					<div class="input-group">
						<input type="text" id="nickname" name="nickname" class="form-control" placeholder="닉네임을 입력하세요" onkeydown="onChangeNickname();">
						<span class="input-group-btn">
							<button class="btn btn-default" type="button" onclick="onClickCheckNickname();">Check</button>
						</span>
					</div>
				</div>
			</div>
			
			<!-- Button -->
			<div class="form-group" align="center">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="button" class="btn btn-default" onclick="onClickJoinBtn();">회원가입</button>
					<button type="button" class="btn btn-default" onclick="history.back();">뒤로가기</button>
				</div>
			</div>
		</form>
	</div>
	<!-- /container -->
</body>
</html>

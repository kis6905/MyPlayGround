<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>

<%@ include file="../include/include.jsp" %>
	
<script type="text/javascript">

$(document).ready(function() {
	var cause = '${cause}' * 1;
	var validPasswordFailCnt = '${validPasswordFailCnt}' * 1;
	
	if (cause == LOGIN_FAIL_MISMATCH) {
		alert('계정 정보가 불일치 합니다. ' + validPasswordFailCnt + '회 틀릴경우 해당 계정은 잠김 처리됩니다.');
	}
	else if (cause == COMMON_SERVER_ERROR) {
		alert('서버에러 입니다. 다시 시도해주세요. \n이 문제가 계속 발생할 경우 관리자에게 문의하시기 바랍니다.');
	}
	else {
		alert('계정이 잠겨있습니다. 관리자에게 문의하시기 바랍니다.');
	}
	
	history.back();
});

</script>
</head>
<body>
</body>
</html>
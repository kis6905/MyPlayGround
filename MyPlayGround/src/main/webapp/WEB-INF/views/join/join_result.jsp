<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>

<%@ include file="../include/include.jsp" %>
	
<script type="text/javascript">

$(document).ready(function() {
	var result = '${result}'; // response에 값을 넣어보낼 경우 boolean Type은 String으로 변한되어 내려온다.
	if (result == 'true') {
		alert('회원가입이 되었습니다. \n로그인 후 이용하시기 바랍니다.');
		location.href = '/login';
	}
	else {
		alert('회원가입에 실패했습니다. \n이 문제가 계속 발생할 경우 관리자에게 문의하시기 바랍니다.');
		history.back();
	}
});

</script>
</head>
<body>
</body>
</html>
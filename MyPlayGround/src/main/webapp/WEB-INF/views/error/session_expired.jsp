<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>

<%@ include file="../include/include.jsp" %>

<script type="text/javascript">

$(document).ready(function() {
	alert('세션이 만료되었거나 다른 사용자가 같은 계정으로 로그인했습니다.');
	location.href = '/logout';
});

</script>
</head>
<body>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>

<%@ include file="../include/include.jsp" %>

<script type="text/javascript">

$(document).ready(function() {
	$('#tabviewTcp').hide();
});

/**
 * 탭 버튼 클릭 처리
 */
function onClickTabBtn(tabKind) {
	if (tabKind == 'http') {
		$('#tabHttp').removeClass('active').addClass('active');
		$('#tabTcp').removeClass('active');
		$('#tabviewHttp').show();
		$('#tabviewTcp').hide();
	}
	else if (tabKind == 'tcp') {
		$('#tabHttp').removeClass('active');
		$('#tabTcp').removeClass('active').addClass('active');
		$('#tabviewHttp').hide();
		$('#tabviewTcp').show();
	}
}

/**
 * 전송 버튼 클릭 처리
 */
function onClickBtn() {
	$.ajax({
		url: '/service/network/request',
		method: 'post',
		dataType: 'json',
		data: {
			text: $('#text').val()
		},
		success: function(data, textStatus, jqXHR) {
			console.log(data.value);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert('에러가 발생했습니다. 관리자에게 문의해주세요.');
		}
	});
}

</script>

</head>

<body>
	<%@ include file="../include/header.jsp" %>

	<!-- Begin page content -->
	<div class="container">
		<div class="page-header">
			<h3>${myService.title}</h3>
		</div>
		
		<div id="contents">
			<ul class="nav nav-tabs">
				<li role="presentation" class="active" id="tabHttp"><a href="javascript: onClickTabBtn('http');">HTTP</a></li>
				<li role="presentation" id="tabTcp"><a href="javascript: onClickTabBtn('tcp');">TCP/IP</a></li>
			</ul>
			
			<div id= "tabviewHttp">
				http 탭
				<input type="text" id="text" />
				<input type="button" value="button" onclick="onClickBtn()" />
			</div>
			
			<div id= "tabviewTcp">
				tcp 탭
			</div>
		</div>
	</div>


	<%@ include file="../include/footer.jsp" %>
</body>
</html>

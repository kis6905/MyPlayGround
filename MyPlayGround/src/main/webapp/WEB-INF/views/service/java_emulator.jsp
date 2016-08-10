<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>

<%@ include file="../include/include.jsp" %>

<script type="text/javascript">

$(document).ready(function() {
	
	// textarea에서 tab 키 눌렀을 때 tab 적용
	$("#javaCodeTextarea").keydown(function(event) {
		// tab key was pressed
	    if(event.keyCode === 9) {
			// get caret position/selection
			var start = this.selectionStart;
			var end = this.selectionEnd;

			var $this = $(this);
			var value = $this.val();

			// set textarea value to: text before caret + tab + text after caret
	        $this.val(value.substring(0, start) + "\t" + value.substring(end));

			// put caret at right position again (add one for the tab)
			this.selectionStart = this.selectionEnd = start + 1;

			// prevent the focus lose
			event.preventDefault();
		}
	});
	
});

/**
 * 실행 버튼 클릭 처리
 */
function onClickRunBtn() {
	var javaCode = $('#javaCodeTextarea').val();
	if (typeof javaCode == 'undefined' || javaCode == '') {
		alert('Code를 작성해주세요.');
		$('#javaCodeTextarea').focus();
		return false;
	}
	
	var consoleHtml = '<img src="/resources/images/icon_loading.gif" width="18px" height="18px" />';
	$('#console').html(consoleHtml);
	
	$.ajax({
		url: '/service/runjava',
		method: 'post',
		dataType: 'json',
		data: {
			javaCode: javaCode
		},
		success: function(data, textStatus, jqXHR) {
			$('#console').html(data.result);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			$('#console').html('Error!!');
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
			<div class="alert alert-info" role="alert">
				<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
				&nbsp;프로세스가 3초 동안 종료되지 않으면 강제 종료 됩니다.<br/>
				<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>
				&nbsp;java.lang, java.util 패키지만 사용 가능합니다.
			</div>
			
			<textarea id="javaCodeTextarea" class="form-control" rows="5" placeholder="Code..."></textarea>
			
			<div class="alert alert-success" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<strong>Tip!</strong> 메인 메소드 안에 들어갈 내용을 입력하시면 됩니다.<br/>
				예)	<code>System.out.print("hi");</code> <span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span> <code>hi</code>
			</div>
			
			<button id="runBtn" type="button" class="btn btn-default" onclick="onClickRunBtn();">
				<span class="glyphicon glyphicon-play" id="runIcon" aria-hidden="true"></span>
				Run
			</button>
			
			<div class="panel panel-default">
				<div class="panel-body" id="console"><span id="consolePlaceholder">Console...</span></div>
			</div>

		</div>
	</div>

	<%@ include file="../include/footer.jsp" %>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>

<%@ include file="../include/include.jsp" %>

<script type="text/javascript" src="/resources/js/sockjs-1.0.3.js"></script>

<script type="text/javascript">

$(document).ready(function() {
	getChattingRoomList();
});



/* 채팅 리스트
-----------------------------------------------------------*/

/**
 * 채팅 방 목록을 가져와 뿌린다.
 */
function getChattingRoomList() {
	viewChattingRoomList();
	
	$.ajax({
		url: '/service/chatting/list',
		method: 'post',
		dataType: 'json',
		success: function(data, textStatus, jqXHR) {
			if (data.result == OK) {
				var chattingRoomListLength = data.chattingRoomList.length;
				var html = '';
				
				if (chattingRoomListLength > 0) {
					html += '<ul class="list-group">';
					
					var chattingRoomList = data.chattingRoomList;
					var chattingRoom;
					for (var inx = 0; inx < chattingRoomList.length; inx++) {
						chattingRoom = chattingRoomList[inx];
						html += '<li class="list-group-item">';
						html += '<button class="btn btn-default" type="button" onclick="join(\'' + chattingRoom.roomId + '\');">';
						html += '<span class="glyphicon glyphicon-comment" aria-hidden="true"></span>';
						html += '</button>';
						html += '&nbsp;&nbsp;&nbsp;' + chattingRoom.roomName;
						html += '</li>';
					}
					
					html += '</ul>';
					$('#listBody').html(html);
				}
				else {
					html += '<ul class="list-group">';
					html += '<li class="list-group-item">';
					html += '채팅 방이 없습니다.';
					html += '</li>';
					$('#listBody').html(html);
				}
			}
			else if (data.result == NOT_OK) {
				alert('에러가 발생했습니다. 관리자에게 문의해주세요.');
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			$('#listArea').html('Error!!');
			alert('에러가 발생했습니다. 관리자에게 문의해주세요.');
		}
	});
}

/**
 * 채팅 방 만들기
 */
function createRoomAndJoin() {
	var roomName = $('#roomName').val();
	if (roomName == null || roomName == '') {
		alert('채팅 방 이름을 입력하세요.');
		return false;
	}
	
	$.ajax({
		url: '/service/chatting/create',
		method: 'post',
		dataType: 'json',
		data: {
			roomName: roomName
		},
		success: function(data, textStatus, jqXHR) {
			if (data.result == OK)
				join(data.chattingRoom.roomId);
			else if (data.result == NOT_OK)
				alert('에러가 발생했습니다. 관리자에게 문의해주세요.');
		},
		error: function(jqXHR, textStatus, errorThrown) {
			$('#listArea').html('Error!!');
			alert('에러가 발생했습니다. 관리자에게 문의해주세요.');
		}
	});
}




/* 채팅 방
-----------------------------------------------------------*/

/**
 * 채팅 참여
 */
var socket = null;
var mRoomId = null;
function join(roomId) {
	
// 	var url = 'ws://10.1.2.193:8080/chatting'; // websocket
	var url = 'http://192.168.123.146:8080/chatting'; // sockjs
// 	var url = 'http://52.79.169.39:7070/chatting'; // AWS
	
// 	if ('WebSocket' in window) {
// 		socket = new WebSocket(url);
		socket = new SockJS(url);
		
		socket.onopen = function() {
			viewChattingRoom();
			mRoomId = roomId;
			socket.send(JSON.stringify({ 'kind': 'join', 'roomId': roomId }));
		};

		socket.onmessage = function(msg) {
			onmessageHendle($.parseJSON(msg.data));
		};
// 	}
// 	else {
// 		alert('지원하지 않는 브라우저입니다. IE 10 이상이나 크롬을 이용하세요.');
// 	}
}

function out() {
	if (socket != null) {
		
		socket.onclose = function(evt) {
			getChattingRoomList();
		}
		
		socket.close();
		socket = null;
	}
	else {
		getChattingRoomList();
	}
}

/**
 * 서버로부터 메시지 받았을 때 처리
 */
function onmessageHendle(data) {
	var kind = data.kind;
	switch (kind) {
	case 'empty':
		alert('채팅 방이 없습니다.');
		out();
		break;
	default:
		addMessage(data);
		break;
	}
}

function getWebSocketSessionId() {
	return socket._transport.url.split('/')[5];
}

/**
 * 채팅창에 내용 추가
 */
function addMessage(data) {
	var kind = data.kind;
	var html = '';
	switch (kind) {
	case 'noti':
		html += '<div class="panel panel-default" id="notiArea">';
		html += '<div class="panel-body" id="notiBody">';
		html += data.message;
		html += '</div>';
		html += '</div>';
		
		$('#roomNameArea').html(data.roomName);
		break;
	case 'chatting':
		// 받은, 보낸 메시지 구분
		if (data.sessionId == getWebSocketSessionId()) {
			html += '<div class="chatting-body-right">';
		}
		else {
			html += '<div class="chatting-body-left">';
			html += '<div class="chatting-profile-area">';
			html += '<img src="/resources/images/image-profile-default.png" width="40px" height="40px">';
			html += '<span class="nickname">' + data.nickname + '</span>';
			html += '</div>';
		}
		
		html += '<div class="chatting-text-area">';
		html += '<div class="chatting-text-body">';
		
		// 같은 숫자나 알파벳을 길게 입력하면 화면 밖으로 나가버림... Bootstrap 버그인듯.
		// 때문에 글자 길이를 보고 엔터 처리 해준다.
// 		var message = '';
// 		var lineCount = data.message.length / 14;
// 		for (var inx = 0; inx < lineCount; inx++) {
// 			message += data.message.substring((inx * 12), ((inx + 1) * 12));
// 			message += '<br/>';
// 		}
// 		html += message;
		
		html += data.message;
		html += '&nbsp;&nbsp;<span class="chatting-time">' + data.time + '</span>';
		html += '</div>';
		html += '</div>';
		html += '</div>';
		
		break;
	default:
		break;
	}
	$('#roomBodyArea').append(html);
	$('#roomBodyArea').scrollTop($('#roomBodyArea')[0].scrollHeight);
}

/**
 * 메시지 전송
 */
function sendMessage() {
	if (socket != null) {
		var message = $('#message').val();
		
		if (message != null && message.length > 0)
			socket.send(JSON.stringify({ 'kind': 'chatting', 'roomId': mRoomId, 'message': message }));
		
		$('#message').val(''); // 메시지 전송후 입력창 비움
		$('#message').focus(); // 모바일에서 키보드를 그대로 유지하기 위해 포커스 줌
	}
	else {
		console.log("Disconnected!");
	}
}

/**
 * 채팅방 참여 시 화면 처리
 */
function viewChattingRoom() {
	$('#listArea').hide();
	$('#roomArea').show();
	
	$('#message').val('');
	$('#roomName').val('');
}

/**
 * 채팅방 나갈 시 화면 처리
 */
function viewChattingRoomList() {
	$('#listArea').show();
	$('#roomArea').hide();
	
	$('#message').val('');
	$('#roomName').val('');
	$('#roomBodyArea').html('');
}

/**
 * 엔터 처리
 */
function checkEnterKey(event) {
    var keyupEvent = event || window.event;

    if (keyupEvent.keyCode == 13) {
    	sendMessage();
    }
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
			<div id="listArea">
				<div class="row roomname-input-row">
					<div class="input-group">
						<input type="text" class="form-control" id="roomName" placeholder="채팅 방 이름...">
						<span class="input-group-btn">
							<button class="btn btn-default" type="button" onclick="createRoomAndJoin();">만들기</button>
						</span>
					</div>
				</div>

				<div id="listBody">
				</div>
			</div><!-- /#listArea -->
			
			<div id="roomArea">
				<div class="panel panel-success">
					<div class="panel-heading">
						<span class="glyphicon glyphicon-comment" id="runIcon" aria-hidden="true"></span>
						&nbsp;<span id="roomNameArea">채팅 방 제목</span>
						<button type="button" class="close" data-dismiss="alert" aria-label="Close" onclick="out();"><span aria-hidden="true">&times;</span></button>
					</div>
					
					<div class="panel-body chatting-body" id="roomBodyArea">
					</div><!-- /#roomBodyArea -->
					
					<div class="panel-footer chatting-footer">
						<div class="row chatting-input-row">
							<div class="input-group">
								<input type="text" class="form-control" id="message" onkeypress="checkEnterKey();" placeholder="입력하세요.">
								<span class="input-group-btn">
									<button class="btn btn-default" type="button" onclick="sendMessage();">전송</button>
								</span>
							</div><!-- /.input-group -->
						</div><!-- /.row -->
					</div><!-- /.panel-footer -->
				</div><!-- /.panel -->
			</div><!-- /#roomArea -->
		</div><!-- /#contents -->
	</div>

	<%@ include file="../include/footer.jsp" %>
</body>
</html>

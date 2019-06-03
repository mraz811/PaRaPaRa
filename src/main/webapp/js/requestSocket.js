var allContent = "";
var fd = new FormData();

function sendMsg(message) { //주문 완료 버튼 누르면 작동되는 이벤트
	
	var nick = $('#nick').val();
	var reciver = $('#targetId').val();
	
	var msg = {
		type : "message",
		text : message,
		id : nick,
		to : reciver,
	};
	ws.send(JSON.stringify(msg));
	
}

function changeViewCustom(mySessionId,targetId) {
	
	document.getElementById("request").style.display = "none";
	document.getElementById("custom").style.display = "block";
	document.getElementById("container").style.display = "none";
	document.getElementById("choiceView").style.display = "none";
			
//			alert("업데이트를 위한 고유값 : " + chatTitle);
//			alert(mySessionId);
//			$("#nickName").focus();
//			if ($("#nickName").val() == '') {
//				alert("닉네임을 입력하세요!!");
//				$("#nickName").focus();
//				return;
//			}
//			$("#chat").focus();
			// 웹소켓을 연결한다. 소켓이 생성되면 그 정보를 서버쪽에서 처리를 하게 위해 전송한다.
			// 실제 웹소켓이 열리는 것은 javascript 쪽이다. 서버는 사용자가 입력한 값을 전송해주기 위한
			// 매개체뿐이다.

			// 웹소켓 객체를 만들기 위해 매개변수로 url을 넣어 접속할 서버를 지정해준다.
			// 파라미터로 내 아이디를 보내준다.
			ws = new WebSocket(
					"ws://192.168.11.38:8091/PaRaPaRa/wsRequest.do?id="
					+ mySessionId + "&target=" + targetId);
			ws.binaryType = "arraybuffer";
			
//			ws = new WebSocket("ws://192.168.4.19:8091/BaBo/wsChat.do");
			// 연결을 맺는것은 비동기 작업이고 실패하기 쉬운 작업이기 때문에, WebSocket 오브젝트를
			// 생성하자마자
			// send() 로 데이터 전송을 시도하는것은 성공하지 않을 가능성이 있습니다. 우리는 연결이 수립된
			// 이후에만
			// 데이터를 전송하도록 하기 위해 onopen 핸들러를 정의하고, 이 위에서 작업합니다.
			ws.onopen = function() {
				alert("고객 주문 세션 열림");
			};
			// 화면쪽으로 전송받은 데이터가 있으면 받은 데이터로 처리해준다.
			ws.onmessage = function(event) {
//				console.log("결과 확인 스크롤 "
//						+ $(".chattingDiv").hasScrollBar());
//				alert(event.data);
				
				
				var message = JSON.parse(event.data); //event.data.request_seq
				
				var requestHTML = "";
				
				var newTr = document.createElement("tr");
				var waitBody = document.getElementById("waitBody");
				requestHTML = "<td style=\"width: 60px; height: 28px\">"+message.rnum+"</td>"
							+"<td id=\"waitMenu\" style=\"width: 220px; height: 28px\" onclick=\"waitMenuDetail("+message.request_seq+","+message.rnum+")\">"+message.request_menu+"</td>"
							+"<td style=\"width: 100px; height: 28px\" >"+message.request_time+"</td>"
							+"<td style=\"width: 40px; height: 28px\"><input type=\"button\" value=\"제조\" onclick=\"changeStatusCode2(this,'"+message.request_seq+","+message.rnum+","+message.request_menu+","+message.request_time+"')\" /></td>"
							+"<td style=\"width: 40px; height: 28px\"><input type=\"button\" value=\"환불\" onclick=\"changeStatusCode0(this,"+message.request_seq+")\"/></td>";
				waitBody.appendChild(newTr).innerHTML = requestHTML;
				
				
				
			}
			// 예외가 발생했을 때 수행된다.
			ws.onclose = function(event) {
				alert("서버와의 연결이 종료되었습니다.");
			}
			// 내용을 입력하고 버튼을 눌렀을 때 수행된다.
			$("#chat_btn").bind("click", function() {
				// 값이 아무것도 없으면 내용을 전송하지 않고 입력하라는 메시지가 나온다.
				if ($("#chat").val() == '') {
					alert("내용을 입력하세요");
					return;
					// 값이 제대로 있으면 websocket 을 전송하고 채팅창을 초기화 해준다.
				} else {
//					alert("하희");
					sendText();
				}
			});
			$(window).bind('beforeunload', beforeUnloadHandler);
			function beforeUnloadHandler() { //화면이동할때 접속끊는거
				return 'message';
			}
		}
function choiceViewStatus(mySessionId,targetId){
	document.getElementById("request").style.display = "block";
	document.getElementById("custom").style.display = "none";
	document.getElementById("choiceView").style.display = "none";
//	alert("업데이트를 위한 고유값 : " + chatTitle);
//	alert(mySessionId);
//	$("#nickName").focus();
//	if ($("#nickName").val() == '') {
//		alert("닉네임을 입력하세요!!");
//		$("#nickName").focus();
//		return;
//	}
//	$("#chat").focus();
	// 웹소켓을 연결한다. 소켓이 생성되면 그 정보를 서버쪽에서 처리를 하게 위해 전송한다.
	// 실제 웹소켓이 열리는 것은 javascript 쪽이다. 서버는 사용자가 입력한 값을 전송해주기 위한
	// 매개체뿐이다.

	// 웹소켓 객체를 만들기 위해 매개변수로 url을 넣어 접속할 서버를 지정해준다.
	// 파라미터로 내 아이디를 보내준다.
	ws = new WebSocket(
			"ws://192.168.11.38:8091/PaRaPaRa/wsRequest.do?id="
					+ mySessionId + "&target=" + targetId);
	ws.binaryType = "arraybuffer";
	
//	ws = new WebSocket("ws://192.168.4.19:8091/BaBo/wsChat.do");
	// 연결을 맺는것은 비동기 작업이고 실패하기 쉬운 작업이기 때문에, WebSocket 오브젝트를
	// 생성하자마자
	// send() 로 데이터 전송을 시도하는것은 성공하지 않을 가능성이 있습니다. 우리는 연결이 수립된
	// 이후에만
	// 데이터를 전송하도록 하기 위해 onopen 핸들러를 정의하고, 이 위에서 작업합니다.
	ws.onopen = function() {
		alert("주문 현황 세션 열림");
	};
	// 화면쪽으로 전송받은 데이터가 있으면 받은 데이터로 처리해준다.
	ws.onmessage = function(event) {
//		console.log("결과 확인 스크롤 "
//				+ $(".chattingDiv").hasScrollBar());
//		alert(event.data);
		
		
		var message = JSON.parse(event.data); //event.data.request_seq
		
		var requestHTML = "";
		
		var newTr = document.createElement("tr");
		var waitBody = document.getElementById("waitBody");
		requestHTML = "<td style=\"width: 60px; height: 28px\">"+message.rnum+"</td>"
					+"<td id=\"waitMenu\" style=\"width: 220px; height: 28px\" onclick=\"waitMenuDetail("+message.request_seq+","+message.rnum+")\">"+message.request_menu+"</td>"
					+"<td style=\"width: 100px; height: 28px\" >"+message.request_time+"</td>"
					+"<td style=\"width: 40px; height: 28px\"><input type=\"button\" value=\"제조\" onclick=\"changeStatusCode2(this,'"+message.request_seq+","+message.rnum+","+message.request_menu+","+message.request_time+"')\" /></td>"
					+"<td style=\"width: 40px; height: 28px\"><input type=\"button\" value=\"환불\" onclick=\"changeStatusCode0(this,"+message.request_seq+")\"/></td>";
		waitBody.appendChild(newTr).innerHTML = requestHTML;
		
		
		
	}
	// 예외가 발생했을 때 수행된다.
	ws.onclose = function(event) {
		alert("서버와의 연결이 종료되었습니다.");
	}
	// 내용을 입력하고 버튼을 눌렀을 때 수행된다.
	$("#chat_btn").bind("click", function() {
		// 값이 아무것도 없으면 내용을 전송하지 않고 입력하라는 메시지가 나온다.
		if ($("#chat").val() == '') {
			alert("내용을 입력하세요");
			return;
			// 값이 제대로 있으면 websocket 을 전송하고 채팅창을 초기화 해준다.
		} else {
//			alert("하희");
			sendText();
		}
	});
	$(window).bind('beforeunload', beforeUnloadHandler);
	function beforeUnloadHandler() { //화면이동할때 접속끊는거
		return 'message';
	}
}
function disconnect() {
	alert("접속 종료 디스커넥트");
	ws.close();
	ws = null;
}

window.onbeforeunload = function() {
	ws.close();
	ws = null ;
}
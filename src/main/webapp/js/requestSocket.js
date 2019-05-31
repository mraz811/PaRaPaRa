var allContent = "";
var fd = new FormData();

function addMenu11(menu){ //주문할 메뉴에 추가하는 이벤트 (화면의 "추가" 버튼)
	
	var menuInfo = menu.split(",");
	var newTr_id = menuInfo[0];
	var menu_seq = menuInfo[1]; //주문 메뉴 번호
	var menu_name = menuInfo[2]; //주문 메뉴 이름
	var menu_qty = 1; //처음 주문 메뉴 수량
	var menu_price = menuInfo[3]; //주문 메뉴 가격
	var sum_price = menuInfo[3]; //주문 메뉴 가격 합계
	var file_rurl = document.getElementById("m"+newTr_id).value;//이미지 파일 링크
	
	var isc = document.getElementsByName("menu_seq"); //추가 버튼 계속 안눌리게 함
	for (var i = 0; i < isc.length; i++) {
		if(menu_seq == isc[i].value){
			alert("이미 선택된 메뉴입니다.");
			return false;
		}
	}
//	document.getElementById(menu_seq).style.display = "none"; //버튼 아예 없애버리긔
	
	
	var newTr = document.createElement("tr"); //새로운 div 생성
    newTr.setAttribute("id", newTr_id);
	
	mBody.appendChild(newTr).innerHTML = "<td>"
											+"<img class=\"menuImg\" src=\""+file_rurl+"\" alt=\"\"/>"
										+"</td>"
										+"<td>"
											+"<input type=\"hidden\" name=\"newTr_id\" value=\""+newTr_id+"\"/>"
											+"<input type=\"hidden\" name=\"menu_seq\" value=\""+menu_seq+"\"/>"
											+"<input type=\"text\" name=\"menu_name\" value=\""+menu_name+"\"/>"
										+"</td>"
										+"<td>"
											+"<input type=\"button\" class=\"downBtn\" value=\"-\" onclick=\"minus(this)\">"
											+"<input type=\"text\" name=\"menu_cnt\" value=\""+menu_qty+"\"/>"
											+"<input type=\"button\" class=\"upBtn\" value=\"+\" onclick=\"plus(this)\">"
										+"</td>"
										+"<td>"
											+"<input type=\"text\" name=\"menu_price\" value=\""+menu_price+"\"/>"
										+"</td>";
}

function customRequest111() { //주문 완료 버튼 누르면 작동되는 이벤트
	// Construct a msg object containing the data the server needs to process
	// the message from the chat client.
//	alert("sendText() 실행");
	var reciver = $('#targetId').val();
	var curAuth = $('#auth').val();
//	alert(reciver);
//	alert(curAuth);
	
	var xhttp = new XMLHttpRequest();
	//메뉴 번호, 수량, 가격  msg 로 보냄
	var seq = document.getElementsByName("menu_seq");
	var cnt = document.getElementsByName("menu_cnt");
	var price = document.getElementsByName("menu_price");
	var menu_seq = new Array();
	var menu_cnt = new Array();
	var menu_price = new Array();
	var message = "";
	for (var i = 0; i < seq.length; i++) { // ,은 seq,cnt,price 배열값들 구분  :은 메뉴 구분
		menu_seq[i] = seq[i].value;        // ex) 1 , 3 , 18000 : 2 , 1 , 19000:
		menu_cnt[i] = cnt[i].value;
		menu_price[i] = price[i].value;
		message += menu_seq[i]+","+menu_cnt[i]+","+menu_price[i]+":";
	}
	alert("메뉴 번호, 수량, 가격 컨캣한거 : "+message); 
	
	
	//주문 메뉴 DB에 저장하는 ajax
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var response = JSON.parse(this.responseText);
			var newTr_id = document.getElementsByName("newTr_id");
			for (var i = 0; i < newTr_id.length; i++) {
				var temp = newTr_id[i].value;
				document.getElementById(temp).style.display = "block";
			}
			alert(response.success);
			document.getElementById("mBody").innerHTML = "";
			
		}
	};
	xhttp.open("POST", "http://localhost:8091/PaRaPaRa/regiCustomOrder.do", true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send("menu_seq="+menu_seq+"&menu_cnt="+menu_cnt+"&menu_price="+menu_price);
	
	
	
	var msg = {
		type : "message",
		text : message,
		id : nick,
		to : reciver,
		auth : curAuth
	};
//	alert(msg.text);
	ws.send(JSON.stringify(msg));
	
}

$(document)
.ready(
		function() {
			
			var curAuth = $('#auth').val();
			
			var chatTitle = $('#storeCode').val();
//			alert("업데이트를 위한 고유값 : " + chatTitle);
			var mySessionId = $('#sessionId').val();
//			alert(mySessionId);
			var targetId = $('#targetId').val();
//			$("#nickName").focus();
			var chatMsgBox = document.getElementById("chatMsgBox");
			chatMsgBox.scrollTop = chatMsgBox.scrollHeight;
//			if ($("#nickName").val() == '') {
//				alert("닉네임을 입력하세요!!");
//				$("#nickName").focus();
//				return;
//			}
			nick = $("#nickName").val();
//			$("#chat").focus();
			// 웹소켓을 연결한다. 소켓이 생성되면 그 정보를 서버쪽에서 처리를 하게 위해 전송한다.
			// 실제 웹소켓이 열리는 것은 javascript 쪽이다. 서버는 사용자가 입력한 값을 전송해주기 위한
			// 매개체뿐이다.

			// 웹소켓 객체를 만들기 위해 매개변수로 url을 넣어 접속할 서버를 지정해준다.
			// 파라미터로 내 아이디를 보내준다.
			ws = new WebSocket(
					"ws://192.168.4.19:8091/PaRaPaRa/wsChat.do?id="
							+ mySessionId + "&target=" + targetId);
			ws.binaryType = "arraybuffer";
			
//			ws = new WebSocket("ws://192.168.4.19:8091/BaBo/wsChat.do");
			// 연결을 맺는것은 비동기 작업이고 실패하기 쉬운 작업이기 때문에, WebSocket 오브젝트를
			// 생성하자마자
			// send() 로 데이터 전송을 시도하는것은 성공하지 않을 가능성이 있습니다. 우리는 연결이 수립된
			// 이후에만
			// 데이터를 전송하도록 하기 위해 onopen 핸들러를 정의하고, 이 위에서 작업합니다.
			ws.onopen = function() {
				ws.send("#$nick_" + nick);
//				$("#receive_msg").append("채팅방에 접속하였습니다.<br/>");
			};
			// 화면쪽으로 전송받은 데이터가 있으면 받은 데이터로 처리해준다.
			ws.onmessage = function(event) {
//				console.log("결과 확인 스크롤 "
//						+ $(".chattingDiv").hasScrollBar());
//				alert(event.data);
				if (event.data.indexOf("<") != 0) {
					var msg = JSON.parse(event.data);
//					alert(msg.view);
					var viewMsg = '';
					console.log("메시지 입력 입장");
					console.log(msg);
					console.log("append가 수행된다.");
					viewMsg = "<div class='clear'></div>";
					if (msg.auth == curAuth) {
						viewMsg += "<div class='form-me'><p>" + msg.view + "</p></div>";
					}else if(msg.auth == "enterChat"){
						viewMsg += "<div class='enter'><p>" + msg.view + "</p></div>";
					}else{
						viewMsg += "<div class='form-other'><p>" + msg.view + "</p></div>";
					}
//					alert(viewMsg);
					
//					console.log(viewMsg);
					$("#chatMsgBox").append(viewMsg);
					chatMsgBox.scrollTop = chatMsgBox.scrollHeight;
					allContent = $("#chatMsgBox").html();
//					alert(allContent);
				}else{
					$("#receive_msg").append(event.data + "<br/>");
				}
				alert("if문 다음에 : " + allContent);
				$.ajax({
					url : "./chatContentUpdate.do",
					type : "post",	
					//업데이트를 위해 db의 chatmember, content을 보냄
					data : "chatTitle="+chatTitle+"&content="+allContent,
					success : function(msg) {
						var isc = msg;
						if(isc=="성공"){
//							alert("gpgp");
						}else{
							//location.href="./error.do";
						}
					}
				});
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
		});

function disconnect() {
alert("접속 종료 디스커넥트");
ws.close();
ws = null;
}

window.onbeforeunload = function() {
	ws.close();
	ws = null ;
}
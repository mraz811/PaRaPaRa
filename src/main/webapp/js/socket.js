var allContent = "";
var fd = new FormData();
function handleFileUpload(files) {
	//파일의 길이만큼 반복하며 formData에 셋팅해준다.
	alert(files.length);
	var megaByte = 1024*1024;
	for (var i = 0; i < files.length; i++) {
		alert("what??");
		alert(files[i].name);
		if((files[i].size/megaByte) <= 10 ){
//		if(map.containsValue(files[i].name) == false && (files[i].size/megaByte) <= 10 ){
			alert("for문실행");
			fd.append(files[i].name, files[i]);
			//파일 중복 업로드를 방지하기 위한 설정
//			map.put(files[i].name, files[i].name);
			// 파일 이름과 정보를 추가해준다.
//			var tag = createFile(files[i].name, files[i].size);
//			$('#fileTable').append(tag);
		}else{
			alert("뭐지");
			//중복되는 정보 확인 위해 콘솔에 찍음
			if((files[i].size/megaByte) > 10){
				alert(files[i].name+"은(는) \n 10메가 보다 커서 업로드가 할 수 없습니다.");
			}else{
				alert('파일 중복 : ' + files[i].name);
			}
		}
	}
	

	
}



function sendText() {
	// Construct a msg object containing the data the server needs to process
	// the message from the chat client.
//	alert("sendText() 실행");
	var reciver = $('#targetId').val();
	var curAuth = $('#auth').val();
//	alert(reciver);
//	alert(curAuth);
	var msg = {
		type : "message",
		text : $("#chat").val(),
		id : nick,
		to : reciver,
//		cnt : key,
		auth : curAuth
//		date : currentDate()
	};
//	alert(msg.text);
	$("#chat").val('');
	$("#chat").focus();
	ws.send(JSON.stringify(msg));
	// Blank the text input element, ready to receive the next line of text from
	// the user.
	
//	key++;
}

$(document)
.ready(
		function() {
			
			var curAuth = $('#auth').val();
			
			var chatTitle = $('#storeCode').val();
//			alert("업데이트를 위한 고유값 : " + chatTitle);
			var mySessionId = $('#sessionId').val();
			alert(mySessionId);
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
					"ws://192.168.11.38:8091/PaRaPaRa/wsChat.do?id="
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
//				alert("if문 다음에 : " + allContent);
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
				
				
//				if ($(".chattingDiv").hasScrollBar()) {
//					var objDiv = $(".chattingDiv");
//					console.log("담긴 객체 정보 = " + objDiv);
//					console.log(objDiv);
//					$(".chattingDiv").scrollTop(
//							$('.chattingDiv')[0].scrollHeight + 20);
//				}
				// console.log(msg.view + " / " + msg.session);
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
			function beforeUnloadHandler() {
				return 'message';
			}
			var objDragDrop = $(".dragDropDiv");
			// div 영역으로 드래그 이벤트호출
			$(".dragDropDiv").on("dragover",
					function(e) {
				e.stopPropagation();
				e.preventDefault();
				$(this).css('border', '2px solid red');
			});
			// 해당 파일 드랍시 호출 이벤트
			$(".dragDropDiv").on("drop", function(e) {
				$(this).css('border', '2px solid green');
				//브라우저로 이동되는 이벤트를 방지하고 드랍 이벤트를 우선시 한다.
				e.preventDefault();
				//DROP 되는 위치에 들어온 파일 정보를 배열 형태로 받아온다.
				var files = e.originalEvent.dataTransfer.files;
				alert(files);
				//DIV에 DROP 이벤트가 발생 했을 때 다음을 호출한다.
				handleFileUpload(files);
//				alert("ajax실행");
				$.ajax({
					type : "POST",
					url : "./regiFile.do", //Upload URL
					data : fd,
					contentType : false,
					processData : false,
					cache : false,
					success : function(data) {
						if(data) {
//							alert("성공이여");
//							alert(data);
							filename = data;
							alert(filename);
							ws.send("%^filename_"+filename);
//							return filename;
//							location.href='./boardList.do';
						}else {
							alert('실패');
						}
					}
				});
				
				
			});
			// div 영역빼고 나머지 영역에 드래그 원래색변경
			$(document).on('dragover', function(e) {
				e.stopPropagation();
				e.preventDefault();
				objDragDrop.css('border', '2px solid green');
				sendFile();
				
			});

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
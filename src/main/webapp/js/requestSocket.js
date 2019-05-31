var allContent = "";
var fd = new FormData();

function addMenu(menu){ // requestSocket.js에서 실행시킬꺼임
	
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
    		alert("이미 선택된 메뉴 입니다.");
    		return false;
    	}
    }
	
//	document.getElementById(menu_seq).style.display = "none"; //버튼 아예 없애버리긔
	var newTr = document.createElement("tr"); //새로운 tr 생성
    newTr.setAttribute("id", newTr_id);
    
    var mBody = document.getElementById("mBody");
    
	mBody.appendChild(newTr).innerHTML = "<td>"
											+"<img class=\"menuImg\" src=\""+file_rurl+"\" alt=\"\"/>"
										+"</td>"
										+"<td>"
											+"<input type=\"hidden\" name=\"newTr_id\" value=\""+newTr_id+"\"/>"
											+"<input type=\"hidden\" name=\"menu_seq\" value=\""+menu_seq+"\"/>"
											+"<input type=\"text\" name=\"menu_name\" value=\""+menu_name+"\"/>"
										+"</td>"
										+"<td>"
											+"<input type=\"button\" class=\"upBtn\" value=\"추가\" onclick=\"plus(this)\">"
											+"<input type=\"text\" class=\"menu_cnt\" name=\"menu_cnt\" value=\""+menu_qty+"\"/>"
											+"<input type=\"button\" class=\"downBtn\" value=\"빼기\" onclick=\"minus(this)\">"
										+"</td>"
										+"<td>"
											+"<input type=\"text\" name=\"menu_price\" value=\""+menu_price+"\"/>"
											+"<input type='hidden'  name='sumMenu_price' value='"+sum_price+"' readonly='readonly'>" 
											+"<input type='hidden'  name='oneMenu_price' value='"+menu_price+"' readonly='readonly'>" 
										+"</td>"
										+"<td>" 
									  		+"<input type='button' class='delBtn' value='삭제'  onclick='delChoice(this, \""+newTr_id+"\")'>" 
									  	+"</td>";
	
	
	// 메뉴 목록에서 추가 버튼을 누를 때 총 금액 값 변경
	var totalMenuPrice = Number($('input[name=totalMenuPrice]').val());
	totalMenuPrice += Number(menu_price);
	$('input[name=totalMenuPrice]').val(totalMenuPrice);
	
	
}

function customRequest() { //주문 완료 버튼 누르면 작동되는 이벤트
	var xhttp = new XMLHttpRequest();
	//메뉴 번호, 수량, 가격  msg 로 보냄
	var seq = document.getElementsByName("menu_seq");
	var name = document.getElementsByName("menu_name");
	var cnt = document.getElementsByName("menu_cnt");
	var price = document.getElementsByName("menu_price");
	var menu_seq = new Array();
	var menu_name = new Array();
	var menu_cnt = new Array();
	var menu_price = new Array();
	
	var date = new Date(); 
	var sub = date.toString();
	var time = sub.substring(16, 24);
	
	var message = "";
	for (var i = 0; i < seq.length; i++) { // ,은 seq,cnt,price 배열값들 구분  :은 메뉴 구분
		menu_seq[i] = seq[i].value;        // ex) 1 , 3 , 18000 : 2 , 1 , 19000:
		menu_name[i] = name[i].value;
		menu_cnt[i] = cnt[i].value;
		menu_price[i] = price[i].value;
		message += menu_seq[i]+","+menu_name[i]+","+menu_cnt[i]+","+menu_price[i]+","+time+":"; //kdjfkdjlfdlkj
	}
	alert("메뉴 번호, 이름, 수량, 가격 컨캣한거 : "+message); 
	
	
	//주문 메뉴 DB에 저장하는 ajax
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var response = JSON.parse(this.responseText);
			var newTr_id = document.getElementsByName("newTr_id");
			for (var i = 0; i < newTr_id.length; i++) {
				var temp = newTr_id[i].value;
				document.getElementById(temp).style.display = "block";
			}
			swal(response.success);
			document.getElementById("mBody").innerHTML = "";
			document.getElementById("rBody").innerHTML = "";
			
		}
	};
	xhttp.open("POST", "http://localhost:8091/PaRaPaRa/regiCustomOrder.do", true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send("menu_seq="+menu_seq+"&menu_cnt="+menu_cnt+"&menu_price="+menu_price);
	
	
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
				
				requestHTML = "";
				
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
	alert(mySessionId);
	alert(targetId);
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
		
		requestHTML = "";
		
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
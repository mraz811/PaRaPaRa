	function makeMenuDetail(request_seq,rnum){
		$.ajax({
			url : "./selMakeReqDetail.do",
			type : "post",
			async : true,
			data : {"request_seq":request_seq},
			dataType : "json",
			success : function(obj){
				var makingDetail = document.getElementById("makingDetail");
				makingDetail.innerHTML = "<div>"+rnum+"</div>"
										  +"<div>"+obj.makeMenu.request_time+"</div>"
										  +"<div>"+obj.makeMenu.menu_name+"</div>";
			},error : function(obj){
				alert("관리자에게 문의해주세요"); 
			}
		})
	}
	function waitMenuDetail(request_seq,rnum){
		$.ajax({
			url : "./selWaitReqDetail.do",
			type : "post",
			async : true,
			data : {"request_seq":request_seq},
			dataType : "json",
			success : function(obj){
				var waitingDetail = document.getElementById("waitingDetail");
				waitingDetail.innerHTML = "<div>"+rnum+"</div>"
										  +"<div>"+obj.makeMenu.request_time+"</div>"
										  +"<div>"+obj.makeMenu.menu_name+"</div>";
			},error : function(obj){
				alert("관리자에게 문의해주세요"); 
			}
		})
	}
	function changeStatusCode3(line,request_seq){
		var os_code = "3";
		$.ajax({
			url : "./updateOrderState.do",
			type : "post",
			async : true,
			data : {"request_seq":request_seq,"os_code":os_code},
			success : function(obj){
				var makeBody = document.getElementById("makeBody");
				var selectTr = line.parentNode.parentNode;
				makeBody.removeChild(selectTr);
			},error : function(obj){
				alert("관리자에게 문의해주세요"); 
			}
		})
	}
	function changeStatusCode2(line,request){
		var requestInfo = request.split(",");
		var request_seq = requestInfo[0]; //주문 seq
		var rnum = requestInfo[1]; //주문 번호
		var menu_name = requestInfo[2]; //메뉴명
		var request_time = requestInfo[3]; //주문 시간
		
		var os_code = "2";
		$.ajax({
			url : "./updateOrderState.do",
			type : "post",
			async : true,
			data : {"request_seq":request_seq,"os_code":os_code},
			success : function(obj){
				var waitBody = document.getElementById("waitBody");
				var selectTr = line.parentNode.parentNode;
				waitBody.removeChild(selectTr);
				
				var newTr = document.createElement("tr");
				var makeBody = document.getElementById("makeBody");
				var html ="";
				html +=  "<td style=\"width: 60px; height: 28px\" >"+rnum+"</td>"
						+"<td id=\"makeMenu\" style=\"width: 270px; height: 28px\" onclick=\"makeMenuDetail("+request_seq+","+rnum+")\">"+menu_name+"</td>"
						+"<td style=\"width: 100px; height: 28px\" >"+request_time+"</td>"
						+"<td style=\"width: 55px; height: 28px\"><input type=\"button\" value=\"완료\" onclick=\"changeStatusCode3(this,"+request_seq+")\" /></td>";
				//alert(html);
				makeBody.appendChild(newTr).innerHTML = html;
			},error : function(obj){
				alert("관리자에게 문의해주세요"); 
			}
		})
	}
	function changeStatusCode0(line,request_seq){
		var os_code = "0";
		$.ajax({
			url : "./updateOrderState.do",
			type : "post",
			async : true,
			data : {"request_seq":request_seq,"os_code":os_code},
			success : function(obj){
				var waitBody = document.getElementById("waitBody");
				var selectTr = line.parentNode.parentNode;
				waitBody.removeChild(selectTr);
			},error : function(obj){
				alert("관리자에게 문의해주세요"); 
			}
		})
	}
	function selRequestList(){
		location.href="./selRequestList.do";
	}
function loadDoc() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var response = JSON.parse(this.responseText);
			$.each(response,function(key,value){
				if(key == "wait"){
					$.each(value,function(key,menu){
			  			$("#print").append();
					});
				}
			});
		}
	};
	xhttp.open("GET", "http://localhost:8091/PaRaPaRa/selRequestStatusRest.do", true);
	xhttp.setRequestHeader("waitList", "makeLists");
	xhttp.send();
}
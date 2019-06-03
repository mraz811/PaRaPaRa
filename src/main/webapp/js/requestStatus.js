	function makeMenuDetail(request_seq,rnum){
		$.ajax({
			url : "./selMakeReqDetail.do",
			type : "post",
			async : true,
			data : {"request_seq":request_seq},
			dataType : "json",
			success : function(obj){
				var makingDetail = document.getElementById("makingDetail");
				makingDetail.innerHTML = "<table class=\"table\">"
											+"<thead>"
											+"<tr>"
											+"<th style=\"width: 80px; height: 35px;\">번호</th>"
											+"<td style=\"width: 80px; height: 35px;\">"+rnum+"</td>"
											+"<th style=\"width: 150px; height: 35px;\">주문 시간</th>"
											+"<td style=\"width: 200px; height: 35px;\">"+obj.makeMenu.request_time+"</td>"
											+"</tr>"
											+"</thead>"
											+"<tbody id=\"mdBody\">"
											+"<tr>"
											+"<th style=\"width: 80px; height: 35px;\">메뉴</th>"
											+"<td colspan=\"3\" style=\"width: 430px; height: 35px;\">"+obj.makeMenu.menu_name+"</td>"
											+"</tr>"
											+"</tbody>"
											+"</table>";
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
				waitingDetail.innerHTML = "<table class=\"table\">"
											+"<thead>"
											+"<tr>"
											+"<th style=\"width: 80px; height: 35px;\">번호</th>"
											+"<td style=\"width: 80px; height: 35px;\">"+rnum+"</td>"
											+"<th style=\"width: 150px; height: 35px;\">주문 시간</th>"
											+"<td style=\"width: 200px; height: 35px;\">"+obj.makeMenu.request_time+"</td>"
											+"</tr>"
											+"</thead>"
											+"<tbody id=\"mdBody\">"
											+"<tr>"
											+"<th style=\"width: 80px; height: 35px;\">메뉴</th>"
											+"<td colspan=\"3\" style=\"width: 430px; height: 35px;\">"+obj.makeMenu.menu_name+"</td>"
											+"</tr>"
											+"</tbody>"
											+"</table>";
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
				document.getElementById("makingDetail").innerHTML = "";
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
				html +=  "<td style=\"width: 60px;\" >"+rnum+"</td>"
						+"<td id=\"makeMenu\" style=\"width: 270px; \" onclick=\"makeMenuDetail("+request_seq+","+rnum+")\">"+menu_name+"</td>"
						+"<td style=\"width: 100px; \" >"+request_time+"</td>"
						+"<td style=\"width: 55px; padding: 8px 0px;\"><input style=\"width: 40px; height: 28px; padding: 2px 2px;\" class=\"btn btn-outline-success\" type=\"button\" value=\"완료\" onclick=\"changeStatusCode3(this,"+request_seq+")\" /></td>";
				//alert(html);
				makeBody.appendChild(newTr).innerHTML = html;
				document.getElementById("waitingDetail").innerHTML = "";
				
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
				document.getElementById("waitingDetail").innerHTML = "";
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
// 제조 중인 메뉴 상세 조회
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
// 대기 중인 메뉴 상세 조회
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
	//주문 완료로 주문 상태 변경
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
	//주문 제조 중으로 주문 상태 변경
	function changeStatusCode2(line,request){
		var requestInfo = request.split("!");
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
				var subMenu_name = menu_name.substring(0, 20);
				if(menu_name.length > 20){
					html =  "<td style=\"width: 60px;\" >"+rnum+"</td>"
					+"<td id=\"makeMenu\" style=\"width: 270px; \" onclick=\"makeMenuDetail("+request_seq+","+rnum+")\">"+subMenu_name+"..."+"</td>"
					+"<td style=\"width: 100px; \" >"+request_time+"</td>"
					+"<td style=\"width: 55px; padding: 8px 0px;\"><input style=\"width: 40px; height: 28px; padding: 2px 2px;\" class=\"btn btn-outline-success\" type=\"button\" value=\"완료\" onclick=\"changeStatusCode3(this,"+request_seq+")\" /></td>";
				}else{
					html =  "<td style=\"width: 60px;\" >"+rnum+"</td>"
					+"<td id=\"makeMenu\" style=\"width: 270px; \" onclick=\"makeMenuDetail("+request_seq+","+rnum+")\">"+menu_name+"</td>"
					+"<td style=\"width: 100px; \" >"+request_time+"</td>"
					+"<td style=\"width: 55px; padding: 8px 0px;\"><input style=\"width: 40px; height: 28px; padding: 2px 2px;\" class=\"btn btn-outline-success\" type=\"button\" value=\"완료\" onclick=\"changeStatusCode3(this,"+request_seq+")\" /></td>";
					
				}
				makeBody.appendChild(newTr).innerHTML = html;
				document.getElementById("waitingDetail").innerHTML = "";
				
			},error : function(obj){
				alert("관리자에게 문의해주세요"); 
			}
		})
	}
	//환불로 주문 상태 변경
	function changeStatusCode0(line,request_seq){
		var os_code = "0";
		swal({
			  title: "주문을 환불하시겠습니까?",
			  type: "warning",
			  showCancelButton: true,
			  confirmButtonClass: "btn-danger",
			  cancelButtonClass: "btn-warning",
			  confirmButtonText: "취소",
			  cancelButtonText: "환불",
			  closeOnConfirm: true,
			  closeOnCancel: true
			},
			function(isConfirm) {
			  if (!isConfirm) {
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
			});
		
	}
	function selRequestList(){
		location.href="./selRequestList.do";
	}

//페이지 리스트 고정 사용 시 불필요 (담당자/업주 조회는 10으로 고정 되어있음)
function pageList(){
	var index = document.getElementById("index");
	var pageNum = document.getElementById("pageNum");
	var listNum = document.getElementById("listNum");
	
	index.value = 0;
	pageNum.value = 1;
	listNum.value = document.getElementById("list").value;
	
	pageAjax();
}


// 페이지 숫자 눌렀을 때
function pageIndex(pageNum){
//	alert(pageNum);
	var index = document.getElementById("index");
	index.value = pageNum-1;
	
	pageAjax();
}

// pageFirst(${row.pageList},${row.pageList})
function pageFirst(num, pageList){
//	var index = 0;
//	var pageNum = 1; // 첫 번째 페이지 
	
	var pageNum = document.getElementById("pageNum");
	var index = document.getElementById("index");
	
	pageNum.value = 1;
	index.value = 0;
//	alert(pageNum.value);
//	alert(index.value);
	// 자바스크립트에서는 같은 변수를 선언해도 다른 애로 인식함.... ㅎㅎ
	pageAjax();
}

// pagePre(${row.pageNum},${row.pageList})
function pagePre(num, pageList){
	if(num-pageList > 0){
		// page가 6이면 (5개씩 떨어지는 페이지 뒤에 또 있다는 말이니까)
		num -= pageList;
		var pageNum = document.getElementById("pageNum");
		var index = document.getElementById("index");
		
		pageNum.value = num;
		index.value = num-1;
		
	}
	pageAjax();
}

// pageNext(${row.pageNum},${row.total},${row.listNum},${row.pageList})
function pageNext(num, total, listNum, pageList){
	var index = Math.ceil(total/listNum); //  묶음 40/5 => 8
 	var max = Math.ceil(index/pageList); // 글의 개수 8/5 => 2
 	
 	if(max*pageList > num+pageList){
 		num+= pageList; 
 		var pageNum = document.getElementById("pageNum");
		var index = document.getElementById("index");
		
		pageNum.value = num;
		index.value = num-1;  
 	}
 	
 	pageAjax();
	
}

// pageLast(${row.pageNum},${row.total},${row.listNum},${row.pageList})
function pageLast(num, total, listNum, pageList){
//	var i = document.getElementById("index").value;
//	var max = Math.ceil(total/pageList);
//	var idx = Math.ceil(max/listNum);
	
	var idx = Math.ceil(total/listNum);
	var max = Math.ceil(idx/pageList);
	
	while(max*pageList > num+pageList){
		num += pageList;
	}
	
	var pageNum = document.getElementById("pageNum");
	var index = document.getElementById("index");

	pageNum.value = num;
//	index.value = num-1;   // 마지막 페이지묶음의 첫페이지 
	index.value = idx-1;   // 마지막 페이지 묶음의 맨 마지막페이지 
	
//	pageNum.value = max - idx +1;
//	index.value = max-1;
	
	pageAjax();
	
}
var pageAjax = function(){
//	alert("아작스 작동 예정");
//	var obj = docuement.getElementById("index").value;
	$.ajax({
		url : "./selRequestListPaging.do",
		type : "post",
		async : true,
		data :  $("#frm").serialize(),    //"index="+ obj  // JSON.stringify
		dataType : "json",
		success: function(msg){
//			alert(msg.lists[0].seq);
//			alert(msg.row.total);
			$.each(msg,function(key,value){
				var htmlTable = "";
				var n = $(".table tr:eq(0) th").length;
//				alert(n);
				
				if(key=="requestList"){ // table을 만들어 줌
					
		
					// 내용을 출력해 준다(lists:[{key,value},{},{}])
					$.each(value,function(key,fri){
						
		//				var regdate = fri.regdate.substring(0,fri.regdate.indexOf(" "));
						var time = fri.request_time;
						var requestTime = time.substring(0, 10);
						var menu = fri.menu_name;
						var menuLen = menu.length;
						var subMenu = menu.substring(0, 27);
						var osCode = fri.os_code;
						var os_code = "";
						if(osCode=="3"){
							os_code = "완료";
						}else if(osCode=="0"){
							os_code = "환불";
						}
						if(menuLen > 27){
						htmlTable +="<tr>" +
								"<td style=\"width: 50px;\">"+fri.rnum+"</td>" +
								"<td id=\"detailMenu\" style=\"width: 300px;\" onclick=\"menuDetail("+fri.request_seq+","+fri.os_code+")\">"+subMenu+"..."+"</td>" +
								"<td style=\"width: 100px;\">"+fri.request_price+"원"+"</a></td>" +
								"<td style=\"width: 100px;\">"+requestTime+"</td>+" +
								"<td style=\"width: 100px;\">"+fri.request_bank+"</td>+" +
								"<td style=\"width: 180px;\">"+fri.request_account+"</td>+" +
								"<td style=\"width: 50px;\">"+os_code+"</td>+" +
								"<td style=\"width: 45px;\"><input type=\"button\" value=\"환불\"/></td></tr>";
						}else{
						htmlTable +="<tr>" +
								"<td style=\"width: 50px;\">"+fri.rnum+"</td>" +
								"<td id=\"detailMenu\" style=\"width: 300px;\" onclick=\"menuDetail("+fri.request_seq+","+fri.os_code+")\">"+fri.menu_name+"</td>" +
								"<td style=\"width: 100px;\">"+fri.request_price+"원"+"</a></td>" +
								"<td style=\"width: 100px;\">"+requestTime+"</td>+" +
								"<td style=\"width: 100px;\">"+fri.request_bank+"</td>+" +
								"<td style=\"width: 180px;\">"+fri.request_account+"</td>+" +
								"<td style=\"width: 50px;\">"+os_code+"</td>+" +
								"<td style=\"width: 45px;\"><input type=\"button\" value=\"환불\"/></td></tr>";
						}
					});

				}else{ // key=row는 paging를 만들어 줌

					htmlTable +="<li><a href='#' onclick='pageFirst("+value.pageList+","+value.pageList+")'>&laquo;</a></li>";
					htmlTable +="<li><a href='#' onclick='pagePre("+value.pageNum+","+value.pageList+")'>&lsaquo;</a></li>";
					
					for (var i =value.pageNum ; i <= value.count; i++) {
						htmlTable +="<li><a href='#' onclick='pageIndex("+i+")'>"+i+"</a></li>";
					}
									
					htmlTable +="<li><a href='#' onclick='pageNext("+value.pageNum+","+value.total+","+value.listNum+","+value.pageList+")'>&rsaquo;</a></li>";
					htmlTable +="<li><a href='#' onclick='pageLast("+value.pageNum+","+value.total+","+value.listNum+","+value.pageList+")'>&raquo;</a></li>";
				}

//				alert(htmlTable);
				
				if(key=="requestList"){
					$(".table > tbody").html(htmlTable);
				}else{
					$(".pagination").html(htmlTable);
				}
			});
		}
	});
	
}
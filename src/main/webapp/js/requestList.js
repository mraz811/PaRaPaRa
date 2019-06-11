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
	var index = document.getElementById("index");
	index.value = pageNum-1;
	
	pageAjax();
}

function pageFirst(num, pageList){
	
	var pageNum = document.getElementById("pageNum");
	var index = document.getElementById("index");
	
	pageNum.value = 1;
	index.value = 0;
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

function pageLast(num, total, listNum, pageList){
	
	var idx = Math.ceil(total/listNum);
	var max = Math.ceil(idx/pageList);
	
	while(max*pageList > num+pageList){
		num += pageList;
	}
	
	var pageNum = document.getElementById("pageNum");
	var index = document.getElementById("index");

	pageNum.value = num;
	index.value = idx-1;   // 마지막 페이지 묶음의 맨 마지막페이지 
	
	
	pageAjax();
	
}
var pageAjax = function(){
	$.ajax({
		url : "./selRequestListPaging.do",
		type : "post",
		async : true,
		data :  $("#frm").serialize(),    //"index="+ obj  // JSON.stringify
		dataType : "json",
		success: function(msg){
			$.each(msg,function(key,value){
				var htmlTable = "";
				var n = $(".table tr:eq(0) th").length;
				
				if(key=="requestList"){ // table을 만들어 줌
					
		
					// 내용을 출력해 준다(lists:[{key,value},{},{}])
					$.each(value,function(key,fri){
						
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
							if(osCode == "3"){
								htmlTable +="<tr>" +
											"<td style=\"width: 50px;\">"+fri.rnum+"</td>" +
											"<td id=\"detailMenu\" style=\"width: 300px;\" onclick=\"menuDetail("+fri.request_seq+","+fri.os_code+")\">"+subMenu+"..."+"</td>" +
											"<td style=\"width: 100px;\">"+fri.request_price+"원"+"</a></td>" +
											"<td style=\"width: 150px;\">"+fri.request_time+"</td>+" +
											"<td style=\"width: 100px;\">"+fri.request_bank+"</td>+" +
											"<td style=\"width: 130px;\">"+fri.request_account+"</td>+" +
											"<td style=\"width: 50px;\">"+os_code+"</td>+" +
											"<td style=\"width: 45px; padding: 5px 0px;\"><input style=\" width:45px; height: 30px;\" class=\"btn btn-outline-warning\" type=\"button\" value=\"환불\" onclick=\"changeStatusCode0("+fri.request_seq+")\"/></td></tr>";
							}else if(osCode == "0"){
								htmlTable +="<tr>" +
											"<td style=\"width: 50px;\">"+fri.rnum+"</td>" +
											"<td id=\"detailMenu\" style=\"width: 300px;\" onclick=\"menuDetail("+fri.request_seq+","+fri.os_code+")\">"+subMenu+"..."+"</td>" +
											"<td style=\"width: 100px;\">"+fri.request_price+"원"+"</a></td>" +
											"<td style=\"width: 150px;\">"+fri.request_time+"</td>+" +
											"<td style=\"width: 100px;\">"+fri.request_bank+"</td>+" +
											"<td style=\"width: 130px;\">"+fri.request_account+"</td>+" +
											"<td style=\"width: 50px;\">"+os_code+"</td>+" +
											"<td style=\"width: 45px;\"></td></tr>";
								
							}
							
						}else{
							if(osCode == "3"){
								htmlTable +="<tr>" +
								"<td style=\"width: 50px;\">"+fri.rnum+"</td>" +
								"<td id=\"detailMenu\" style=\"width: 300px;\" onclick=\"menuDetail("+fri.request_seq+","+fri.os_code+")\">"+fri.menu_name+"</td>" +
								"<td style=\"width: 100px;\">"+fri.request_price+"원"+"</a></td>" +
								"<td style=\"width: 150px;\">"+fri.request_time+"</td>+" +
								"<td style=\"width: 100px;\">"+fri.request_bank+"</td>+" +
								"<td style=\"width: 130px;\">"+fri.request_account+"</td>+" +
								"<td style=\"width: 50px;\">"+os_code+"</td>+" +
								"<td style=\"width: 45px; padding: 5px 0px;\"><input style=\" width:45px; height: 30px;\" class=\"btn btn-outline-warning\" type=\"button\" value=\"환불\" onclick=\"changeStatusCode0("+fri.request_seq+")\"/></td></tr>";
							}else if(osCode == "0"){
								htmlTable +="<tr>" +
								"<td style=\"width: 50px;\">"+fri.rnum+"</td>" +
								"<td id=\"detailMenu\" style=\"width: 300px;\" onclick=\"menuDetail("+fri.request_seq+","+fri.os_code+")\">"+fri.menu_name+"</td>" +
								"<td style=\"width: 100px;\">"+fri.request_price+"원"+"</a></td>" +
								"<td style=\"width: 150px;\">"+fri.request_time+"</td>+" +
								"<td style=\"width: 100px;\">"+fri.request_bank+"</td>+" +
								"<td style=\"width: 130px;\">"+fri.request_account+"</td>+" +
								"<td style=\"width: 50px;\">"+os_code+"</td>+" +
								"<td style=\"width: 45px;\"></td></tr>";
								
							}
						}
					});

				}else{ // key=row는 paging를 만들어 줌

					htmlTable +="<li class=\"page-item\"><a class=\"page-link\" href='#' onclick='pageFirst("+value.pageList+","+value.pageList+")'>&laquo;</a></li>";
					htmlTable +="<li class=\"page-item\"><a class=\"page-link\" href='#' onclick='pagePre("+value.pageNum+","+value.pageList+")'>&lsaquo;</a></li>";
					
					for (var i =value.pageNum ; i <= value.count; i++) {
						htmlTable +="<li class=\"page-item\"><a class=\"page-link\" href='#' onclick='pageIndex("+i+")'>"+i+"</a></li>";
					}
									
					htmlTable +="<li class=\"page-item\"><a class=\"page-link\" href='#' onclick='pageNext("+value.pageNum+","+value.total+","+value.listNum+","+value.pageList+")'>&rsaquo;</a></li>";
					htmlTable +="<li class=\"page-item\"><a class=\"page-link\" href='#' onclick='pageLast("+value.pageNum+","+value.total+","+value.listNum+","+value.pageList+")'>&raquo;</a></li>";
				}

				
				if(key=="requestList"){
					$(".table > tbody").html(htmlTable);
				}else{
					$(".pagination").html(htmlTable);
				}
			});
		}
	});
	
}
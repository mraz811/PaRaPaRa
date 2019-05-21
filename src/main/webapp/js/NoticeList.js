

function pageList(){
	var index = document.getElementById("index");
	var pageNum = document.getElementById("pageNum");
	var listNum = document.getElementById("listNum");
	
	index.value = 0;
	pageNum.value = 1;
	listNum.value = document.getElementById("list").value;
	
	pageAjax();

}

// 페이지 숫자 눌렀을때
function pageIndex(pageNum){
//	alert(pageNum);
	var index = document.getElementById("index");
	index.value = pageNum-1;
//	alert(index.value);
	
//	$("#index").val(pageNum-1);
	pageAjax();
}

//pageFrist(${row.pageList},${row.pageList})
function pageFirst(num, pageList){
//		var index = 0;
//		var pageNum = 1;
		
		var pageNum = document.getElementById("pageNum");
		var	index = document.getElementById("index");
		
		pageNum.value = 1;
		index.value = 0;
		
//		alert(pageNum.value);
//		alert(index.value);
		
		pageAjax();
		
}


//pagePre(${row.pageNum},${row.pageList})
function pagePre(num, pageList){
	if(0<num-pageList){
		num -= pageList;
		var pageNum = document.getElementById("pageNum");
		var index = document.getElementById("index");
		
		pageNum.value = num;
		index.value = num-1;
	}
	
	pageAjax();
}


//pageNext(${row.pageNum},${row.total},${row.listNum},${row.pageList})
function pageNext(num, total, listNum, pageList){
	var index = Math.ceil(total/listNum); //묶음 40/5 => 8
	var max = Math.ceil(index/pageList); // 글의 갯수 8/5 => 2
	
	if(max*pageList > num+pageList){
		num += pageList;
		
		var pageNum = document.getElementById("pageNum");
		var index = document.getElementById("index");
		
		pageNum.value = num;
		index.value = num-1;
	}
	pageAjax();
} 

//pageLast(${row.pageNum},${row.total},${row.listNum},${row.pageList})
function pageLast(num, total, listNum, pageList){
//	var max = Math.ceil(total/pageList); // 21/ 4
//	var idx = Math.ceil(max/listNum); // 4/5 
	
	var idx = Math.ceil(total/listNum);
	var max = Math.ceil(idx/pageList);
	
	while(max*pageList > num+pageList){
		num += pageList
	}
	
	var pageNum = document.getElementById("pageNum");
	var index = document.getElementById("index");
	
	pageNum.value= num;
	index.value = idx-1;
	
//	pageNum.value = max - idx+1;
//	index.value = max-1;

	pageAjax();

}


  ///////////////////////////
  // 페이지 처리를 위한 공통 Ajax //
  //////////////////////////

var pageAjax = function(){
//	alert("아작스 작동 예정");
//	var obj = docuement.getElementById("index").value;
	$.ajax({
		url : "./paging.do",
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
				
				if(key=="lists"){ // table을 만들어 줌
					
					htmlTable += "<tr>"+
					"<th>NO.</th>"+
					"<th>제목</th>"+
					"<th>작성자</th>"+
					"<th>등록일</th></tr>";
		
			// 내용을 출력해 준다(lists:[{key,value},{},{}])
			$.each(value,function(key,fri){
				
				var regdate = fri.regdate.substring(0,fri.regdate.indexOf(" "));
				
				htmlTable +="<tr>" +
						"<td>"+fri.seq+"</td>" +
						"<td><a href='./noticeDetail.do?notice_seq="+fri.seq+"'>"+fri.title+"</a></td>" +
						"<td>"+fri.id+"</td>+" +
						"<td>"+regdate+"</td></tr>";
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
				
				if(key=="lists"){
					$(".table > tbody").html(htmlTable);
				}else{
					$(".pagination").html(htmlTable);
				}
			});
		}
	});
	
}
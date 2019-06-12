/**
 *  업주 리스트 페이징 처리를 위한 javascript 파일
 */

function pageAjax(){
	var pagingFrm = $("#pagingForm").serialize();
	
	$.ajax({
		url: "./ownerPaging.do",
		type: "post",
		async: true,
		data: pagingFrm,
		dataType: "json",
		success: function(msg){
//			alert(msg.listSize);
//			alert(msg.owLists.listSize);
				
			$.each(msg, function(key,value){
				var htmlTable = "";
				
				if(key=="owLists"){
					htmlTable += "<table id='ownerList' class='table table-hover' style='margin-bottom: 0px;'>"+
					"<tr class='table-primary'>"+
						"<th width='115px'>사업자번호</th>"+
						"<th width='90px'>업주명</th>"+
						"<th width='120px'>전화번호</th>"+
						"<th width='195px'>이메일</th>"+
						"<th width='115px'>매장명</th>"+
						"<th width='110px'>계약시작</th>"+
						"<th width='165px'>계약종료</th>"+
						"<th width='100px'></th>"+
					"</tr>";
					if(msg.listSize==0){
						htmlTable+="<tr><td colspan='8' style='text-align: center; color: red; font-weight: bold;'>등록된 업주가 없습니다.</td></tr>";
						htmlTable += "</table>";
					}else{
						htmlTable += "</table>";
					
						$.each(value, function(seckey, val) {
							htmlTable += "<form action='#' method='post' id='form"+val.owner_seq+"' >"+
					"<input type='hidden' name='owner_seq' value="+val.owner_seq+">"+
					"<table class='table table-hover' style='margin-bottom: 0px; height: 59px;'>"+
						"<tr>"+
							"<td width='115px'>"+val.owner_id+"</td>"+		
							"<td width='90px'>"+val.owner_name+"</td>"+	
							"<td width='120px'>"+val.owner_phone+"</td>"+		
							"<td width='195px'>"+val.owner_email+"</td>"+
							"<td width='115px'>"+val.store_name+"</td>"+
							"<td width='110px'>"+(val.owner_start).substr(0, 10)+"</td>";
							
							if(val.owner_end == null){
								htmlTable +=
								"<td width='165px'><input id='owner_end"+val.owner_seq+"' name='owner_end' type='date'></td>"+
								"<td width='100px'><input class='btn btn-secondary' type='button' value='계약종료' onclick='finContract(\""+val.owner_seq+"\",\""+val.owner_start+"\")'></td>";
							}
							if(val.owner_end != null){
								htmlTable += "<td width='165px'>"+(val.owner_end).substr(0, 10)+"</td>"+
								"<td width='100px'><input class='btn btn-outline-primary' type='button' value='계약연장' onclick='reCon(\""+val.owner_seq+"\")'></td>";
							}
						
						htmlTable += "</tr></table></form>";
						
						});
					
					}
				} else if(key=="owPaging") { // key==owPaging
					htmlTable +="<li class='page-item'><a class='page-link' href='#' onclick='pageFirst("+value.pageList+","+value.pageList+")'>&laquo;</a></li>";
					htmlTable +="<li class='page-item'><a class='page-link' href='#' onclick='pagePre("+value.pageNum+","+value.pageList+")'>&lsaquo;</a></li>";
					
					for (var i =value.pageNum ; i <= value.count; i++) {
						htmlTable +="<li class='page-item'><a class='page-link' href='#' onclick='pageIndex("+i+")'>"+i+"</a></li>";
					}
					
					htmlTable +="<li class='page-item'><a class='page-link' href='#' onclick='pageNext("+value.pageNum+","+value.total+","+value.listNum+","+value.pageList+")'>&rsaquo;</a></li>";
					htmlTable +="<li class='page-item'><a class='page-link' href='#' onclick='pageLast("+value.pageNum+","+value.total+","+value.listNum+","+value.pageList+")'>&raquo;</a></li>";
				}
				
				if(key=="owLists"){
//					alert(htmlTable);
					$("#owner_table").html(htmlTable);
				} else if(key=="owPaging"){
					$(".pagination").html(htmlTable);
				}
				
				
			}); // 첫 each문
		} // success
		
	});
}

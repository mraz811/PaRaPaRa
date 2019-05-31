/**
 * 	관리자 관련 javascript
 *  
 */


function pageAjax(){
	var chkCons = $("#chkCons").val();
	var pagingFrm = $("#pagingForm").serialize();
	
	$.ajax({
		url : "./adminPaging.do",
		type: "post",
		async: true,
		data : pagingFrm, 
		dataType : "json",	
		success: function(msg){
			// each문 내에서 key : adLists, adPaging

			//			alert(chkCons);
			
			$.each(msg, function(key,value){
				
				var htmlTable = "";
				if(key=="adLists"){
					if(chkCons == "Y"){
						htmlTable += "<tr class='table-primary'>"+
							"<th width='80px;'></th>"+
							"<th width='120px;'>사번</th>"+
							"<th width='130px;'>담당자명</th>"+
							"<th width='150px;'>전화번호</th>"+
							"<th width='300px;'>이메일</th>"+
							"<th width='130px;'>지역명</th>"+
							"<th width='120px;'>지역코드</th>"+
						"</tr>";
						
						$.each(value, function(seckey, val){

							htmlTable += "<tr><td></td>"+
								"<td>"+val.admin_id+"</td>"+
								"<td>"+val.admin_name+"</td>"+
								"<td>"+val.admin_phone+"</td>"+
								"<td>"+val.admin_email+"</td>"+
								"<td>"+val.loc_name+"</td>"+
								"<td>"+val.loc_code+"</td>"+
							"</tr>";
						});
						
					}else{ //chkCons가 N이거나 시/도 일 때
						htmlTable += "<tr class='table-primary'>"+
								"<th width='80px;'></th>"+
								"<th width='120px;'>사번</th>"+
								"<th width='130px;'>담당자명</th>"+
								"<th width='150px;'>전화번호</th>"+
								"<th width='300px;'>이메일</th>"+
								"<th width='130px;'>지역명</th>"+
								"<th width='120px;'>지역코드</th>"+
							"</tr>";
						
						$.each(value, function(seckey, val){
							
							htmlTable += "<tr>"+
									"<td align='center'><input type='radio' name='admin_id' value='"+val.admin_id+"'></td>"+
									"<td>"+val.admin_id+"</td>"+
									"<td>"+val.admin_name+"</td>"+
									"<td>"+val.admin_phone+"</td>"+
									"<td>"+val.admin_email+"</td>"+
									"<td>"+val.loc_name+"</td>"+
									"<td>"+val.loc_code+"</td>"+
								"</tr>";
						});
						
					}
					
				} else{ // key == adPaging 일 때
					htmlTable +="<li class='page-item'><a class='page-link' href='#' onclick='pageFirst("+value.pageList+","+value.pageList+")'>&laquo;</a></li>";
					htmlTable +="<li class='page-item'><a class='page-link' href='#' onclick='pagePre("+value.pageNum+","+value.pageList+")'>&lsaquo;</a></li>";
					
					for (var i =value.pageNum ; i <= value.count; i++) {
						htmlTable +="<li class='page-item'><a class='page-link' href='#' onclick='pageIndex("+i+")'>"+i+"</a></li>";
					}
									
					htmlTable +="<li class='page-item'><a class='page-link' href='#' onclick='pageNext("+value.pageNum+","+value.total+","+value.listNum+","+value.pageList+")'>&rsaquo;</a></li>";
					htmlTable +="<li class='page-item'><a class='page-link' href='#' onclick='pageLast("+value.pageNum+","+value.total+","+value.listNum+","+value.pageList+")'>&raquo;</a></li>";
				}
				
				// 붙여주기
				if(key=="adLists"){
					$(".table").html(htmlTable);
				} else{
					$(".pagination").html(htmlTable);
				}
				
			}); // 첫번째 each
			
		}
		
	});
	
	
}
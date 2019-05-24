/**
 * 	관리자 관련 javascript
 *  
 */


function pageAjax(){
	
	var pagingFrm = $("#pagingForm").serialize();
	
	$.ajax({
		url : "./adminPaging.do",
		type: "post",
		async: true,
		data : pagingFrm, 
		dataType : "json",	
		success: function(msg){
			// each문 내에서 key : adLists, adPaging
		},
		error: function(msg){
			
		}
		
	});
	
	
}
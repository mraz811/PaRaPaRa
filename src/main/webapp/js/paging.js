/**
 * Paging 처리를 위한 공통 javascript 파일. pageAjax() 부분은 각 페이지에 맞게 작성하기
 */

///////////////////////////////////////----------page처리----------///////////

// 페이지 리스트 고정 사용 시 불필요 (담당자/업주 조회는 10으로 고정 되어있음)
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
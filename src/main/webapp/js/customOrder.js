//고객 주문
function mainMenu(){
	var menu_category = "주메뉴";
	var menuList = document.getElementById("menuList");
	$.ajax({
		url : "./CselAllMenuList.do",
		type : "post",
		async : true,
		data : {"menu_category":menu_category},
		dataType : "json",
		success : function(obj){
			var htmlText = "";
			var cnt = 1;
			$.each(obj,function(key,value){
				if(key == "choiceMenu"){
					$.each(value,function(key,menu){
						htmlText += "<div id=\"menu"+cnt+"\" class=\"menu\"\"><img class=\"menuImg\" src=\""+menu.file_rurl+"\" alt=\"\"/><input id=\"mmenu"+cnt+"\" type=\"hidden\" value=\""+menu.file_rurl+"\" /><input id=\""+menu.menu_seq+"\" type=\"button\" name=\"addButton\" value=\"추가\" onclick=\"addMenu('menu"+cnt+++","+menu.menu_seq+","+menu.menu_name+","+menu.menu_price+"')\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
					});
				}
			});
			menuList.innerHTML = htmlText;
		},error : function(obj){
			alert(obj); 
		}
	})
}
function sideMenu(){
	var menu_category = "사이드메뉴";
	$.ajax({
		url : "./CselAllMenuList.do",
		type : "post",
		async : true,
		data : {"menu_category":menu_category},
		dataType : "json",
		success : function(obj){
			var htmlText = "";
			var cnt = 1;
			$.each(obj,function(key,value){
				if(key == "choiceMenu"){
					$.each(value,function(key,menu){
						htmlText += "<div id=\"menu"+cnt+"\" class=\"menu\"\"><img class=\"menuImg\" src=\""+menu.file_rurl+"\" alt=\"\"/><input id=\"mmenu"+cnt+"\" type=\"hidden\" value=\""+menu.file_rurl+"\" /><input id=\""+menu.menu_seq+"\" type=\"button\" name=\"addButton\" value=\"추가\" onclick=\"addMenu('menu"+cnt+++","+menu.menu_seq+","+menu.menu_name+","+menu.menu_price+"')\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
					});
				}
			});
			menuList.innerHTML = htmlText;
		},error : function(obj){
			alert(obj); 
		}
	})
}
function drinkMenu(){
	var menu_category = "음료";
	$.ajax({
		url : "./CselAllMenuList.do",
		type : "post",
		async : true,
		data : {"menu_category":menu_category},
		dataType : "json",
		success : function(obj){
			var htmlText = "";
			var cnt = 1;
			$.each(obj,function(key,value){
				if(key == "choiceMenu"){ 
					$.each(value,function(key,menu){
						htmlText += "<div id=\"menu"+cnt+"\" class=\"menu\"\"><img class=\"menuImg\" src=\""+menu.file_rurl+"\" alt=\"\"/><input id=\"mmenu"+cnt+"\" type=\"hidden\" value=\""+menu.file_rurl+"\" /><input  id=\""+menu.menu_seq+"\" type=\"button\" name=\"addButton\" value=\"추가\" onclick=\"addMenu('menu"+cnt+++","+menu.menu_seq+","+menu.menu_name+","+menu.menu_price+"')\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
					});
				}
			});
			menuList.innerHTML = htmlText;
		},error : function(obj){
			alert(obj); 
		}
	})
}
//선택 메뉴 수량빼줌
function minus(el) {
	//var su = document.getElementsByName("pi_qty")[0].value;
	//document.getElementsByName("pi_qty")[0].value = new Number(su) - 1;
	//alert($('.downBtn').index(el));
	var idx = $('.downBtn').index(el);
	if($(".menu_cnt:eq("+idx+")").val() < 1){
		return false;
	}
	var su = $(".menu_cnt:eq("+idx+")").val();
	su = $(".menu_cnt:eq("+idx+")").val(su*1-1); 
	// 합계금액을 변경
	var price = Number(document.getElementsByName("oneMenu_price")[idx].value);
	var sumPrice = Number(document.getElementsByName("sumMenu_price")[idx].value);
	
	sumPrice -= price;
	
	document.getElementsByName("sumMenu_price")[idx].value = sumPrice;
	document.getElementsByName("menu_price")[idx].value = sumPrice;
	
	// 발주 품목에서 - 버튼을 누를 때 총 금액 값 변경
	var totalMenuPrice = Number($('input[name=totalMenuPrice]').val());
	totalMenuPrice -= Number(price);
	$('input[name=totalMenuPrice]').val(totalMenuPrice);
	
}
//선택 메뉴 수량 추가해줌
function plus(el) {
	//var su = document.getElementsByName("pi_qty")[0].value;
	//document.getElementsByName("pi_qty")[0].value = new Number(su) + 1;
	//alert($('.upBtn').index(el));
	var idx = $('.upBtn').index(el);
	var su = $(".menu_cnt:eq("+idx+")").val();
	su = $(".menu_cnt:eq("+idx+")").val(su*1+1); 
	// 합계금액을 변경
	var price = Number(document.getElementsByName("oneMenu_price")[idx].value);
	var sumPrice = Number(document.getElementsByName("sumMenu_price")[idx].value);
	
	sumPrice += price;
	
	document.getElementsByName("sumMenu_price")[idx].value = sumPrice;
	document.getElementsByName("menu_price")[idx].value = sumPrice;
	
	// 메뉴 목록에서 추가 버튼을 누를 때 총 금액 값 변경
	var totalMenuPrice = Number($('input[name=totalMenuPrice]').val());
	totalMenuPrice += Number(price);
	$('input[name=totalMenuPrice]').val(totalMenuPrice);
}
function addMenu(menu){ // requestSocket.js에서 실행시킬꺼임
	
	var menuInfo = menu.split(",");
	var newTr_id = menuInfo[0];
	var menu_seq = menuInfo[1]; //주문 메뉴 번호
	var menu_name = menuInfo[2]; //주문 메뉴 이름
	var menu_qty = 1; //처음 주문 메뉴 수량
	var menu_price = menuInfo[3]; //주문 메뉴 가격
	var sum_price = menuInfo[3]; //주문 메뉴 가격 합계
	var file_rurl = document.getElementById("m"+newTr_id).value;//이미지 파일 링크
	

    
    var isc = document.getElementsByName("menu_seq"); //추가 버튼 계속 안눌리게 함
    for (var i = 0; i < isc.length; i++) {
    	if(menu_seq == isc[i].value){
    		alert("이미 선택된 메뉴 입니다.");
    		return false;
    	}
    }
	
//	document.getElementById(menu_seq).style.display = "none"; //버튼 아예 없애버리긔
	var newTr = document.createElement("tr"); //새로운 tr 생성
    newTr.setAttribute("id", newTr_id);
    
    var mBody = document.getElementById("mBody");
    
	mBody.appendChild(newTr).innerHTML = "<td>"
											+"<img class=\"menuImg\" src=\""+file_rurl+"\" alt=\"\"/>"
										+"</td>"
										+"<td>"
											+"<input type=\"hidden\" name=\"newTr_id\" value=\""+newTr_id+"\"/>"
											+"<input type=\"hidden\" name=\"menu_seq\" value=\""+menu_seq+"\"/>"
											+"<input type=\"text\" name=\"menu_name\" value=\""+menu_name+"\"/>"
										+"</td>"
										+"<td>"
											+"<input type=\"button\" class=\"upBtn\" value=\"추가\" onclick=\"plus(this)\">"
											+"<input type=\"text\" class=\"menu_cnt\" name=\"menu_cnt\" value=\""+menu_qty+"\"/>"
											+"<input type=\"button\" class=\"downBtn\" value=\"빼기\" onclick=\"minus(this)\">"
										+"</td>"
										+"<td>"
											+"<input type=\"text\" name=\"menu_price\" value=\""+menu_price+"\"/>"
											+"<input type='hidden'  name='sumMenu_price' value='"+sum_price+"' readonly='readonly'>" 
											+"<input type='hidden'  name='oneMenu_price' value='"+menu_price+"' readonly='readonly'>" 
										+"</td>"
										+"<td>" 
									  		+"<input type='button' class='delBtn' value='삭제'  onclick='delChoice(this, \""+newTr_id+"\")'>" 
									  	+"</td>";
	
	
	// 메뉴 목록에서 추가 버튼을 누를 때 총 금액 값 변경
	var totalMenuPrice = Number($('input[name=totalMenuPrice]').val());
	totalMenuPrice += Number(menu_price);
	$('input[name=totalMenuPrice]').val(totalMenuPrice);
	
	
}
//선택 메뉴에서 해당 메뉴 삭제를 했을 때 빼줌
function delChoice(line, lineId) {

	// 현재 줄의 인덱스 번호
	var idx = $('.delBtn').index(line);
	//alert(idx);
	var menu_cnt = document.getElementsByName("menu_cnt")[idx].value;
	var menuPrice = document.getElementsByName("menu_price")[idx].value;
	
	
	// 선택 메뉴 에서 삭제 버튼을 누를 때 총 금액 값 변경
	var totalMenuPrice = Number($('input[name=totalMenuPrice]').val());
	totalMenuPrice -= Number(menuPrice);
	$('input[name=totalMenuPrice]').val(totalMenuPrice);
	
	
	// 선택 메뉴 에서 해당 메뉴를 삭제
	var mBody = document.getElementById("mBody");
	var selectTr = line.parentNode.parentNode;
	mBody.removeChild(selectTr);
	
}
function customRequest() { //requestSocket.js 에서 실행 시킬꺼임
	var xhttp = new XMLHttpRequest();
	
	var seq = document.getElementsByName("menu_seq");
	var cnt = document.getElementsByName("menu_cnt");
	var price = document.getElementsByName("menu_price");
	
	var menu_seq = new Array();
	var menu_cnt = new Array();
	var menu_price = new Array();
	for (var i = 0; i < seq.length; i++) {
		menu_seq[i] = seq[i].value;
		menu_cnt[i] = cnt[i].value;
		menu_price[i] = price[i].value;
	}
	
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var response = JSON.parse(this.responseText);
			var newTr_id = document.getElementsByName("newTr_id");
			for (var i = 0; i < newTr_id.length; i++) {
				var temp = newTr_id[i].value;
				document.getElementById(temp).style.display = "block";
			}
			swal(response.success);
			document.getElementById("mBody").innerHTML = "";
			document.getElementById("rBody").innerHTML = "";
			
		}
	};
	xhttp.open("POST", "http://localhost:8091/PaRaPaRa/regiCustomOrder.do", true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send("menu_seq="+menu_seq+"&menu_cnt="+menu_cnt+"&menu_price="+menu_price);
}

function cancelRequest(){
	var newTr_id = document.getElementsByName("newTr_id");
	for (var i = 0; i < newTr_id.length; i++) {
		var temp = newTr_id[i].value;
		document.getElementById(temp).style.display = "block";
	}
	document.getElementById("mBody").innerHTML = "";
	document.getElementById("rBody").innerHTML = "";
	$('input[name=totalMewnuQty]').val("0");
	$('input[name=totalMenuPrice]').val("0");
}
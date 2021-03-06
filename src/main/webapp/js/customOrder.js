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
						htmlText += "<div id=\"menu"+cnt+"\" class=\"menu\"\"><img class=\"menuImg\" src=\""+menu.file_rurl+"\" alt=\"\"/><input id=\"mmenu"+cnt+"\" type=\"hidden\" value=\""+menu.file_rurl+"\" /><input class=\"btn btn-outline-primary\"  id=\""+menu.menu_seq+"\" type=\"button\" name=\"addButton\" value=\"추가\" onclick=\"addMenu('menu"+cnt+++","+menu.menu_seq+","+menu.menu_name+","+menu.menu_price+"')\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
					});
				}
			});
			menuList.innerHTML = htmlText;
		},error : function(obj){
			alert("관리자에게 문의해주세요."); 
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
						htmlText += "<div id=\"menu"+cnt+"\" class=\"menu\"\"><img class=\"menuImg\" src=\""+menu.file_rurl+"\" alt=\"\"/><input id=\"mmenu"+cnt+"\" type=\"hidden\" value=\""+menu.file_rurl+"\" /><input class=\"btn btn-outline-primary\"  id=\""+menu.menu_seq+"\" type=\"button\" name=\"addButton\" value=\"추가\" onclick=\"addMenu('menu"+cnt+++","+menu.menu_seq+","+menu.menu_name+","+menu.menu_price+"')\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
					});
				}
			});
			menuList.innerHTML = htmlText;
		},error : function(obj){
			alert("관리자에게 문의해주세요."); 
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
						htmlText += "<div id=\"menu"+cnt+"\" class=\"menu\"\"><img class=\"menuImg\" src=\""+menu.file_rurl+"\" alt=\"\"/><input id=\"mmenu"+cnt+"\" type=\"hidden\" value=\""+menu.file_rurl+"\" /><input class=\"btn btn-outline-primary\"  id=\""+menu.menu_seq+"\" type=\"button\" name=\"addButton\" value=\"추가\" onclick=\"addMenu('menu"+cnt+++","+menu.menu_seq+","+menu.menu_name+","+menu.menu_price+"')\"/><br>"+menu.menu_name+"&nbsp;&nbsp;"+menu.menu_price+"</div>";
					});
				}
			});
			menuList.innerHTML = htmlText;
		},error : function(obj){
			alert("관리자에게 문의해주세요."); 
		}
	})
}
//선택 메뉴 수량빼줌
function minus(el) {
	// 수량 변경
	var idx = $('.downBtn').index(el);
	if($(".menu_cnt:eq("+idx+")").val() < 2){
		return false;
	}
	var su = $(".menu_cnt:eq("+idx+")").val();
	su = $(".menu_cnt:eq("+idx+")").val(su*1-1); 
	
	$(".htmlCnt:eq("+idx+")").html($(".menu_cnt:eq("+idx+")").val()); 
	if($(".htmlCnt:eq("+idx+")").html()<1){
		return false;
	}
	// 합계금액을 변경
	var price = Number(document.getElementsByName("oneMenu_price")[idx].value);
	var sumPrice = Number(document.getElementsByName("sumMenu_price")[idx].value);
	
	sumPrice -= price;
	
	document.getElementsByName("sumMenu_price")[idx].value = sumPrice;
	document.getElementsByName("menu_price")[idx].value = sumPrice;
	
	document.getElementsByClassName("htmlPrice")[idx].innerHTML = sumPrice+"원";
	
	// 고객 주문에서 - 버튼을 누를 때 총 금액 값 변경
	var totalMenuPrice = Number($('input[name=totalMenuPrice]').val());
	totalMenuPrice -= Number(price);
	$('input[name=totalMenuPrice]').val(totalMenuPrice);
	
	$("#totalResultPrice").html(totalMenuPrice+"원");
	
}
//선택 메뉴 수량 추가해줌
function plus(el) {
	// 수량 변경
	var idx = $('.upBtn').index(el);
	var su = $(".menu_cnt:eq("+idx+")").val();
	su = $(".menu_cnt:eq("+idx+")").val(su*1+1); 
	
	
	$(".htmlCnt:eq("+idx+")").html($(".menu_cnt:eq("+idx+")").val()); 
	
	// 합계금액을 변경
	var price = Number(document.getElementsByName("oneMenu_price")[idx].value);
	var sumPrice = Number(document.getElementsByName("sumMenu_price")[idx].value);
	
	sumPrice += price;
	
	document.getElementsByName("sumMenu_price")[idx].value = sumPrice;
	document.getElementsByName("menu_price")[idx].value = sumPrice;
	
	document.getElementsByClassName("htmlPrice")[idx].innerHTML = sumPrice+"원";
	
	
	
	// 메뉴 목록에서 추가 버튼을 누를 때 총 금액 값 변경
	var totalMenuPrice = Number($('input[name=totalMenuPrice]').val());
	totalMenuPrice += Number(price);
	$('input[name=totalMenuPrice]').val(totalMenuPrice);
	
	$("#totalResultPrice").html(totalMenuPrice+"원");
}
function addMenu(menu){ 
	
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
	
	var newTr = document.createElement("tr"); //새로운 tr 생성
    newTr.setAttribute("id", newTr_id);
    
    var mBody = document.getElementById("mBody");
    
	mBody.appendChild(newTr).innerHTML = "<th>"
											+"<img style=\"width:80px;heigth:80px;\" src=\""+file_rurl+"\" alt=\"\"/>"
										+"</th>"
										+"<th style=\"text-align: center;width: 140px;\">"
											+"<input type=\"hidden\" name=\"newTr_id\" value=\""+newTr_id+"\"/>"
											+"<input type=\"hidden\" name=\"menu_seq\" value=\""+menu_seq+"\"/>"
											+"<input style=\"text-align: center;\" type=\"hidden\" name=\"menu_name\" value=\""+menu_name+"\"/>"
											+""+menu_name+""
										+"</th>"
										+"<th>"
										+"<input type=\"button\" class=\"downBtn\" value=\"-\" onclick=\"minus(this)\">"
											+"<input style=\"width:40px;text-align: right;\" type=\"hidden\" class=\"menu_cnt\" name=\"menu_cnt\" value=\""+menu_qty+"\"/>"
											+"&nbsp;&nbsp;&nbsp;<span class=\"htmlCnt\">"+menu_qty+"</span>&nbsp;&nbsp;&nbsp;"
											+"<input type=\"button\" class=\"upBtn\" value=\"+\" onclick=\"plus(this)\">"
										+"</th>"
										+"<th style=\"text-align: center;width: 100px;\">"
										+"<span class=\"htmlPrice\">"+menu_price+"원"+"</span>"
											+"<input style=\"width:60px;text-align: right;\" type=\"hidden\" name=\"menu_price\" value=\""+menu_price+"\"/>"
											+"<input type='hidden'  name='sumMenu_price' value='"+sum_price+"' readonly='readonly'>" 
											+"<input type='hidden'  name='oneMenu_price' value='"+menu_price+"' readonly='readonly'>" 
										+"</th>"
										+"<th>" 
									  		+"<input class=\"btn btn-outline-warning\" type='button' name='delBtn' value='삭제'  onclick='delChoice(this, \""+newTr_id+"\")'>" 
									  	+"</th>";
	
	
	// 메뉴 목록에서 추가 버튼을 누를 때 총 금액 값 변경
	var totalMenuPrice = Number($('input[name=totalMenuPrice]').val());
	totalMenuPrice += Number(menu_price);
	$('input[name=totalMenuPrice]').val(totalMenuPrice);
	
	$("#totalResultPrice").html(totalMenuPrice+"원");
	
}
//선택 메뉴에서 해당 메뉴 삭제를 했을 때 빼줌
function delChoice(line, lineId) {
	// 현재 줄의 인덱스 번호
	var idx = $("input[name=delBtn]").index(line);
	var menu_cnt = document.getElementsByName("menu_cnt")[idx].value;
	var menuPrice = document.getElementsByName("menu_price")[idx].value;
	
	
	// 선택 메뉴 에서 삭제 버튼을 누를 때 총 금액 값 변경
	var totalMenuPrice = Number($('input[name=totalMenuPrice]').val());
	totalMenuPrice -= Number(menuPrice);
	$('input[name=totalMenuPrice]').val(totalMenuPrice);
	
	$("#totalResultPrice").html(totalMenuPrice+"원");
	
	
	// 선택 메뉴 에서 해당 메뉴를 삭제
	var mBody = document.getElementById("mBody");
	var selectTr = line.parentNode.parentNode;
	mBody.removeChild(selectTr);
	
}
function customRequest() { 
	var xhttp = new XMLHttpRequest();
	
	//메뉴 번호, 수량, 가격  msg 로 보냄
	var seq = document.getElementsByName("menu_seq");
	var name = document.getElementsByName("menu_name");
	var cnt = document.getElementsByName("menu_cnt");
	var price = document.getElementsByName("menu_price");
	var menu_seq = new Array();
	var menu_name = new Array();
	var menu_cnt = new Array();
	var menu_price = new Array();
	
	var date = new Date(); 
	var sub = date.toString();
	var time = sub.substring(16, 24);
	
	
	for (var i = 0; i < seq.length; i++) { // ,은 seq,cnt,price 배열값들 구분  :은 메뉴 구분
		menu_seq[i] = seq[i].value;        // ex) 1 , 3 , 18000 : 2 , 1 , 19000:
		menu_name[i] = name[i].value;
		menu_cnt[i] = cnt[i].value;
		menu_price[i] = price[i].value;
	}
	
	if(menu_seq[0] == null){
		swal("주문 메뉴를 선택해주세요.");
		return false;
	}

	var nick = $('#nick').val();
	//주문 메뉴 DB에 저장하는 ajax
	
	xhttp.open("POST", "http://192.168.4.3:8099/PaRaPaRa/regiCustomOrder.do", true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send("menu_seq="+menu_seq+"&menu_cnt="+menu_cnt+"&menu_price="+menu_price+"&nick="+nick);
	
	
	var request_seq = "";
	var rnum = "";
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
			document.getElementsByName("totalMenuPrice")[0].value = "0";
			$("#totalResultPrice").html("");
			request_seq = response.rDto.request_seq;
			rnum = response.rDto.rnum;
			var message = "";
			for (var i = 0; i < menu_seq.length; i++) {
				message += rnum+","+request_seq+","+menu_seq[i]+","+menu_name[i]+","+menu_cnt[i]+","+menu_price[i]+","+time+"!"; 
			}
			
			sendMsg(message);
		}
	};

}
// 주문 취소 버튼 눌렀을 때 작동
function cancelRequest(){
	var newTr_id = document.getElementsByName("newTr_id");
	for (var i = 0; i < newTr_id.length; i++) {
		var temp = newTr_id[i].value;
		document.getElementById(temp).style.display = "block";
	}
	document.getElementById("mBody").innerHTML = "";
	$('input[name=totalMewnuQty]').val("0");
	$('input[name=totalMenuPrice]').val("0");
	
	$("#totalResultPrice").html("0원");
}
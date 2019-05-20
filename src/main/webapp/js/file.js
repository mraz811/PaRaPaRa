// 폼에 데이터를 추가 (파일을 전송하기 전 정보 유지)
var fd = new FormData();
// 파일 중복 업로드 방지용 맵을 선언
var map = new Map();

// 파일 전송 함수
var sendFileToServer = function() {
	alert("sendFileToServer");
	$.ajax({
		type : "POST",
		url : "./regiFile.do", //Upload URL
		data : fd,
		contentType : false,
		processData : false,
		cache : false,
		success : function(data) {
			if(data) {
				alert(data);
//				location.href='./boardList.do';
			}else {
				alert('실패');
			}
		}
	});
}

function handleFileUpload(files) {
	//파일의 길이만큼 반복하며 formData에 셋팅해준다.
	alert(files.length);
	var megaByte = 1024*1024;
	for (var i = 0; i < files.length; i++) {
		alert("what??");
		alert(files[i].name);
		if(map.containsValue(files[i].name) == false && (files[i].size/megaByte) <= 10 ){
			alert("for문실행");
			fd.append(files[i].name, files[i]);
			//파일 중복 업로드를 방지하기 위한 설정
			map.put(files[i].name, files[i].name);
			// 파일 이름과 정보를 추가해준다.
//			var tag = createFile(files[i].name, files[i].size);
//			$('#fileTable').append(tag);
		}else{
			alert("뭐지");
			//중복되는 정보 확인 위해 콘솔에 찍음
			if((files[i].size/megaByte) > 10){
				alert(files[i].name+"은(는) \n 10메가 보다 커서 업로드가 할 수 없습니다.");
			}else{
				alert('파일 중복 : ' + files[i].name);
			}
		}
	}
}



//$(document).ready(function() {
//
//});
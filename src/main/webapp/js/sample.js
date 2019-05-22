"use strict";

//var shiftObj = "";
//var obj = "";

// {index : { name : { color : time, ...}}}



//let shiftObj = {"999":{"이슬":          [ {"1" : "03:30-07:30","2":"01:30-02:30"}]}}


let shiftObj = {
    "0" : {
        "Mrs. Tomato": [
            {"1" : "09:00-12:00"},
            {"2" : "13:00-14:00"},
            {"9" : "17:00-20:00"},
        ]
    },
    
//  "1" : { "Mrs. Tomato": [ {"1" : "09:00-12:00"},{"2" : "13:00-14:00"},] },
//  키 :   {   키                : [ { 키 : 벨류 } , { 키 : 벨류 } ] },
// {   "999":{"이슬":[{"1":"05:30-08:30","2":"04:00-07:30"}]}  ,         ,             }

    "2":{"이슈리":[{"3":"11:00-12:30","5" : "14:00-19:30"}]},
    "3":{"이슈리2":[{"3":"11:00-12:30","5" : "14:00-19:30"}]},
    
};


let obj = {
    // Beginning Time
    startTime: "01:00",
    // Ending Time
    endTime: "25:00",
    // Time to divide(minute)
    divTime: "30",
    // Time Table
    shift: shiftObj,
    // Other options
    option: {
        // workTime include time not displaying
        workTime: true,
        bgcolor: ["#00FFFF"],
        // {index :  name, : index: name,,..}
        // selectBox index and shift index should be same
        // Give randome if shift index was not in selectBox index
        selectBox: {
            "35" : "Jason Paige 01051798468",
            "18" : "Mr.Jason"
        },
        // Set true when using TimeTable inside of BootStrap class row
        useBootstrap: false,
    }
};



// Call Time Table
var instance = new TimeTable(obj);
console.time("time"); // eslint-disable-line
instance.init("#test");
console.timeEnd("time");// eslint-disable-line

// Only works if selectBox option exist
$(document).on("click", "#addRow",()=>{instance.addRow();});

// Change theme color sample
$(document).on("click","#colorChange", ()=>{
    let color = `${getColor()},${getColor()},${getColor()}`;
    document.documentElement.style.setProperty("--rgbTheme", color);
});
function getColor(){
    return Math.floor(Math.random() * Math.floor(255));
}
// Getting Data Sample
$(document).on("click","#getData", ()=>{
    let data = instance.data();
    console.log(data);
});




function intutDB() {

	var alba_seq = "55"; // alba_seq 들어온 매장의 알바들 조회해서 담음
	var ts_date = "2019-05-22"; // 화면의 데이트 긁어오기
	
//	alert(alba_seq);
	
	$.ajax({
		url: "./selTimeSheet.do", //요청 url
		type: "post", // 전송 처리방식
		asyn: false, // true 비동기 false 동기
		data: { 'alba_seq' : alba_seq ,'ts_date': ts_date }, // 서버 전송 파라메터 
//		dataType: "json",
		success: function(objoo){
			alert("성공");
			alert(objoo);
			
			// "1" : { "Mrs. Tomato": [ {"1" : "09:00-12:00"},{"2" : "13:00-14:00"},] }, 
			//{"999":{"이슬":          [ {"1" : "03:30-07:30","2":"01:30-02:30"}]}}
			
//			shiftObj = obj;
			
			alert(shiftObj);
			
			
			
		}, error : function() {
			alert("실패!!");
			alert(obj);
		}
	});
	
}



"use strict";

// {index : { name : { color : time, ...}}}

window.onload = function() {
	
	/*


	var alba_seq = ''; // alba_seq 들어온 매장의 알바들 조회해서 담음
	var ts_date = ''; // 화면의 데이트 긁어오기
	
	$.ajax({
		url: "selTimeSheet.do", //요청 url
		type: "post", // 전송 처리방식
		asyn: false, // true 비동기 false 동기
		data: { 'alba_seq' : alba_seq ,'ts_date': ts_date }, // 서버 전송 파라메터 
		dataType: "json",
		success: function(msg){
			alert("성공");
			alert(msg.alba_seq);
			
//			let shiftObj = 
			
		}, error : function() {
			alert("실패");
		}
	});


	*/
	
}

let shiftObj = {
    "1" : {
        "Mrs. Tomato": [
            {"1" : "10:00-12:00"},
            {"2" : "13:00-14:00"},
            {"9" : "17:00-20:00"},
        ]
    },

    
    
    
    "2" : {
        "Jason Paige": [
            {"3" : "11:00-12:30"},
            {"5" : "14:00-19:30"},
        ]
    },
    "500" : {
        "Jedi": [
            {"8" : "13:00-19:00"}
        ]
    },
    "3" : {
        "Skywalker": [
            {"1" : "10:00-12:00"},
            {"2" : "13:00-14:00"},
            {"9" : "17:00-20:00"},
        ]
    },
    "4" : {
        "Mrs.Smith": [
            {"8" : "10:00-13:30"},
            {"7" : "14:00-17:30"},
        ]
    },
    "5" : {
        "Mario": [
            {"1" : "12:00-15:30"}
        ]
    },
    "6" : {
        "Tom": [
            {"0" : "15:00-22:30"}
        ]
    },
    "7" : {
        "Michael": [
            {"9" : "15:00-18:30"}
        ]
    },
    "8" : {
        "Pikachu": [
            {"1" : "10:00-12:00"},
            {"2" : "13:00-14:00"},
            {"3" : "17:00-20:30"},
        ]
    },
    "9" : {
        "MR.JSON": [
            {"2" : "09:00-12:59"},
            {"4" : "15:00-15:20"},
            {"7" : "17:00-17:30"},
        ]
    },
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
            "18" : "Mr.Jason",
            "25" : "Mrs.Jason",
            "38" : "A",
            "39" : "B",
            "40" : "C"
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


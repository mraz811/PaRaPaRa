
<%@page import="com.happy.para.dto.CalDto"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<% response.setContentType("text/html; charset=UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>calendar</title>
<style type="text/css">

#calAll{
	position: relative;
}

#cal{
	position: absolute;
	left: 60px;
	top: 25px;
}

#menu{
	position: absolute;
	width: 1020px;
	text-align: center;
}

</style>
</head>

 <%
 	List<CalDto> lists = (List<CalDto>) request.getAttribute("lists"); 
 %>

<body>

	<div id="container">
		<%@include file="../header.jsp"%>
		<div class="bodyFrame">
			<div class="bodyfixed">
				<div class="oneDepth">
					<!-- oneDepth에 적힐 내용이 들어감 ex)매장관리 -->
					<p>매장관리</p>
				</div>
				<!-- div class=oneDepth -->
				<div class="twoDepth">
					<!-- onDepth 안에 있는 twoDepth가 들어감 ex)1depth가 매장관리일 경우 a 태그에 적힐 내용은 일정관리, 재고, 발주 등  -->
					<ul class="nav nav-tabs">
						<li class="nav-item"><a class="nav-link" data-toggle="tab">CALENDAR</a></li>
						<li class="nav-item"><a class="nav-link" data-toggle="tab"
							id="pao">발주</a></li>
						<li class="nav-item"><a class="nav-link" data-toggle="tab"
							id="stockList">재고</a></li>
					</ul>
					<div class="tab-content">

					<script type="text/javascript">
						$(function(){
							$("#stockList").click(function(){
								location.href="./selStock.do";
							});
							$("#pao").click(function(){
								location.href="./selPaoList.do";
							});
						});
					</script>

						<div id="calAll">
							<div id="menu">
								<button id="prev">◀</button>
								<a id="now"></a>
								<button id="next">▶</button>
							</div>
								
							<div id="cal">
								<input name="title" type="hidden" />
								<input name="store_code" type="hidden" value="${store_code}" />
								<div id="calendar" style="width: 900px; height: 410px;"></div>
							</div>
						</div>

					</div><!-- div class=tab-content -->
				</div><!-- div class twoDepth -->
			</div><!-- div class=bodyfixed -->
		</div><!-- div class=bodyFrame -->
		<%@include file="../footer.jsp"%>
	</div>
</body>

<link rel="stylesheet" type="text/css" href="https://uicdn.toast.com/tui-calendar/latest/tui-calendar.css" />
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"> -->
<link rel="stylesheet" type="text/css" href="https://uicdn.toast.com/tui.time-picker/latest/tui-time-picker.css">
<link rel="stylesheet" type="text/css" href="https://uicdn.toast.com/tui.date-picker/latest/tui-date-picker.css">

<!-- <script type="text/javascript" src="http://code.jquery.com/jquery-3.3.1.min.js"></script> -->
<!-- <script type="text/javascript" src="./js/jquery-3.3.1.js"></script> -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://uicdn.toast.com/tui.code-snippet/latest/tui-code-snippet.min.js"></script>
<script type="text/javascript" src="https://uicdn.toast.com/tui.time-picker/latest/tui-time-picker.min.js"></script>
<script type="text/javascript" src="https://uicdn.toast.com/tui.date-picker/latest/tui-date-picker.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.20.1/moment.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/chance/1.0.13/chance.min.js"></script>
<script src="https://uicdn.toast.com/tui-calendar/latest/tui-calendar.js"></script>

<script type="text/javascript">
	var Calendar = tui.Calendar;	
	// 달력 양식 지정
	const templates = {						
		    titlePlaceholder: function() {
		      return '제목';
		    },
		    locationPlaceholder: function() {
		      return '내용';
		    },
		    startDatePlaceholder: function() {
		      return '시작일';
		    },
		    endDatePlaceholder: function() {
		      return '종료일';
		    },		
		    // 입력 팝업 관련
		    popupSave: function() {			    	
		      return '저장';
		    },
		    popupUpdate: function() {
		      return '수정';
		    },
		    popupEdit: function() {
			      return 'Edit';
			},
			popupDelete: function() {
			      return '삭제';
			},
			// 상세정보 팝업 관련
		    popupDetailDate: function(start, end) {
		      var isSameDate = moment(start).isSame(end);
		      var endFormat = (isSameDate ? '' : 'YYYY.MM.DD ') + 'hh:mm a';

		      return (moment(start).format('YYYY.MM.DD hh:mm a') + ' - ' + moment(end).format(endFormat));
		    },		
		    popupDetailUser: function(schedule) {
		           return 'User : ' + (schedule.attendees || []).join(', ');
		     },		  
		    popupDetailLocation : function(schedule) {
				return '내용 :' + schedule.location;
			},
		  };

  $(document).ready(function (){	
	  
// 	  var store_code = document.getElementById("store_code").value;
// 	  alert(store_code);
	  
	  // 테마 설정
	  var COMMON_CUSTOM_THEME = {
			    'common.border': '1px solid #EAEBEC',
			    'common.backgroundColor': '#F1F6FA',
			    'common.holiday.color': '#f54f3d',
			    'common.saturday.color': '#3162ea',
			    'common.dayname.color': '#333'
			  };

	  // 캘린더 설정
	  // template : 달력 양식 지정
	  // calendars : 일정 양식 지정
	  var calendar = new Calendar('#calendar', {
		  defaultView: 'month',			  
		  template: templates,
		  useCreationPopup: true,
		  useDetailPopup: true	,
		  theme : COMMON_CUSTOM_THEME,
		  calendars: [
			    {
			      id: '1',
			      name: '가족',
			      color: '#ffffff',
			      bgColor: '#9e5fff',
			      dragBgColor: '#9e5fff',
			      borderColor: '#9e5fff'
			    },
			    {
			      id: '2',
			      name: '학원',
			      color: '#ffffff',
			      bgColor: '#00a9ff',
			      dragBgColor: '#00a9ff',
			      borderColor: '#00a9ff'
			    },
			    {
			    	id: '3',
				    name: '회사',
				    color: '#ffffff',
				    bgColor: '#ffa9ff',
				    dragBgColor: '#ffa9ff',
				    borderColor: '#ffa9ff'
				 },
				 {
				    id: '4',
				    name: 'etc',
				    color: '#ffffff',
				    bgColor: '#A5A5A5',
				    dragBgColor: '#A5A5A5',
				    borderColor: '#A5A5A5'
				},
			  ]	  
		});		  

	  
	  <%for (int i = 0; i < lists.size(); i++) {%>
	  
	  var result  =  {
				
				calendarId: '<%=lists.get(i).getCal_id()%>',
			    id: '<%=lists.get(i).getCal_seq()%>',
			    title: '<%=lists.get(i).getCal_title()%>',
			    location : '<%=lists.get(i).getCal_content()%>',
// 			    isAllDay: true,				   
			    start: '<%=lists.get(i).getCal_start()%>',
			    end: '<%=lists.get(i).getCal_end()%>',				    
			    category: 'time',
// 			    color : '#000000',					   
			    state : 'Free',
			    store_code : '${store_code}',
			    
				};		 
		 
	  calendar.createSchedules([result]);
	  
	  <%}%>	  
	  
	  
	  // 달력 시작일 설정
	  //calendar.setDate(new Date('2019-05-01 10:00'));
	  $("#now").html("<b>"+(calendar.getDate().getFullYear())+"년 "+(calendar.getDate().getMonth()+1)+"월</b>");
	  
	  
	// 스케쥴 생성
		calendar.on('beforeCreateSchedule',function(schedule){		
				
			 var startday = new Date(schedule.start);
			 var endday = new Date(schedule.end);
			 
			 $.ajax({
				url: "regiCal.do", //요청 url
				type: "post", // 전송 처리방식
				asyn: false, // true 비동기 false 동기
// 				data: { 'calendarId' : schedule.calendarId , 'id' : String(Math.random()*100000000000000000),
				data: {
						'calendarId' : schedule.calendarId ,
						'title' : schedule.title ,
						'content' : schedule.location ,
// 						'isAllDay' : schedule.isAllDay ,
						'startyear': startday.getFullYear(), 'startmonth' : startday.getMonth()+1,
						'startday' : startday.getDate() , 'starthours' : startday.getHours(),
						'startminutes' : startday.getMinutes(),
						'endyear': endday.getFullYear(), 'endmonth' : endday.getMonth()+1,
						'endday' : endday.getDate() , 'endhours' : endday.getHours(),
						'endminutes' : endday.getMinutes(),
<%-- 						'store_code' : '<%=ldto.getStore_code()%>', --%>
						'store_code' : '${store_code}',
// 						'store_code' : 'Store_test1',
			
						'state' : schedule.state
				}, // 서버 전송 파라메터
				dataType: "json", // 서버에서 받는 데이터 타입
				success: function(msg){
					alert("성공");
					alert(msg.id);
					var createcal = {						
						    calendarId: schedule.calendarId,
						    id: msg.id,
						    title: schedule.title,
						    location : schedule.location,
// 						    isAllDay: schedule.isAllDay,					   
						    start: schedule.start,
						    end: schedule.end,
						    category: schedule.isAllDay? 'allday' : 'time',
						    color : "#FFFFFF",					  
						    state : schedule.state,						  
				 	};
					 calendar.createSchedules([createcal]);	
				}, error : function() {
					alert("실패");
				}
			});
		});

		// 일정 변경 
	 	calendar.on('beforeUpdateSchedule', function(event) {
	 	         var schedule = event.schedule;
	 	         var startTime = event.start;
	 	         var endTime = event.end;	
	 	         
	 	         if(confirm("일정을 변경하시겠습니까?")){
	 	        	 
	 	        	 $.ajax({
	 					url: "calModi.do", //요청 url
	 					type: "post", // 전송 처리방식
	 					asyn: false, // true 비동기 false 동기
	 					data: { 'id' : schedule.id,
	 							'title' : schedule.title ,
	 							'content' : schedule.location ,  'isAllDay' : schedule.isAllDay ,
	 							'startyear': startTime.getFullYear(), 'startmonth' : startTime.getMonth()+1,
	 							'startday' : startTime.getDate() , 'starthours' : startTime.getHours(),
	 							'startminutes' : startTime.getMinutes(),
	 							'endyear': endTime.getFullYear(), 'endmonth' : endTime.getMonth()+1,
	 							'endday' : endTime.getDate() , 'endhours' : endTime.getHours(),
	 							'endminutes' : endTime.getMinutes(),
	 							'store_code' : '${store_code}',
	 							'state' : schedule.state
	 					}, // 서버 전송 파라메터
	 					dataType: "json", // 서버에서 받는 데이터 타입
	 					success: function(msg){
	 						alert("성공");
	 						alert(msg.id);		
	 						// 일정 수정
	 						 calendar.updateSchedule(schedule.id, schedule.calendarId, {
	 			 	             start: startTime,
	 			 	             end: endTime,
	 			 	             location : schedule.location,
	 			 	             title : schedule.title	,
// 	 			 	             isAllDay: schedule.isAllDay,			 	             
	 			 	             state : schedule.state
	 			 	         });	 						 
	 					}, error : function() {
	 						alert("실패");
	 					}
	 				});	
	 			}
	 	     });
		
		// 스케쥴 클릭시 이벤트
	 	calendar.on('clickSchedule', function(event) {
		       var schedule = event.schedule;		 		       
		       
		          if (lastClickSchedule) {
		              calendar.updateSchedule(lastClickSchedule.id, lastClickSchedule.calendarId, {
		                  isFocused: false
		              });
		          }
		          calendar.updateSchedule(schedule.id, schedule.calendarId, {
		             isFocused: true
		          });
		          
		         lastClickSchedule = schedule;		       
		     });	 	
	 	
	 	// 삭제시
	 	calendar.on('beforeDeleteSchedule', function(event) {
	 	         var schedule = event.schedule;	 	
	 	         alert(schedule.id);
	 	         if(confirm("정말 삭제하시겠습니까?")){	 	        	 
	 	        	 $.ajax({
		 					url: "delCal.do", //요청 url
		 					type: "post", // 전송 처리방식
		 					asyn: false, // true 비동기 false 동기
		 					data: { 'cal_seq' : schedule.id,
		 					}, // 서버 전송 파라메터
		 					dataType: "json", // 서버에서 받는 데이터 타입
		 					success: function(msg){		 						
		 						if(msg.isc >= 1){					
		 							alert("성공");
		 							calendar.deleteSchedule (schedule.id, schedule.calendarId,false); 
		 						}
		 					}, error : function() {
		 						alert("실패");
		 						
		 					}
		 				});		 	         	
	 	         }
	 	});

	 	// 달 이동
	 	$("#prev").click(function() {
	 		calendar.move(-1);
	 		calendar.render();
	 		$("#now").html("<b>"+(calendar.getDate().getFullYear())+"년 "+(calendar.getDate().getMonth()+1)+"월</b>");
		});	 	
	 	
	 	$("#next").click(function() {
	 		calendar.move(1);
	 		calendar.render();
	 		$("#now").html("<b>"+(calendar.getDate().getFullYear())+"년 "+(calendar.getDate().getMonth()+1)+"월</b>");
		});	 	
	 	
	 	$("#calType").change(function() {			
			calendar.changeView($("#calType option:selected").val(),true);
		});
	});
</script>
</html>
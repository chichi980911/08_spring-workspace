<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>실시간 대기오염 정보</h2>
	
	지역 : 
	<select id="location">
		<option>서울</option>
		<option>부산</option>
		<option>대전</option>
	</select>
	<button id="btn1">해당 지역 대기오염정보</button>
	<br><br>
	<table id="result1" border="1">
		<thead>
			<tr> 
				<th>측정소명</th>
				<th>측정일시</th>
				<th>통합대기환경수치</th>
				<th>미세먼지농도</th>
				<th>아황산가스농도</th>
				<th>일산화탄소농도</th>
				<th>이산화질소농도</th>
				<th>오존농도</th>
			</tr>
		</thead>
		<tbody>
		
		</tbody>
	</table>
	
	<script>
		$(function(){
			$("#btn1").click(function(){
				
				//JSON 형식으로 응답 받았을 때
				/* 
				$.ajax({
					url:"air.do",
					data:{location:$("#location").val()},
					success:function(data){
						const arr = data.response.body.items;
						
						let value="";
						for(let i in arr){
							//console.log(arr[i])
							let item = arr[i];
							
							value += "<tr>"
								   + "<td>" +item.stationName+ "</td>"
								   + "<td>" +item.dataTime+ "</td>"
								   + "<td>" +item.khaiValue+ "</td>"
								   + "<td>" +item.pm10Value+ "</td>"
								   + "<td>" +item.so2Value+ "</td>"
								   + "<td>" +item.no2Value+ "</td>"
								   + "<td>" +item.coValue+ "</td>"
								   + "<td>" +item.o3Value+ "</td>"
								   + "</tr>";
						}
							$("#result1 tbody").html(value);	   
					},
					error:function(){
						console.log("ajax통신실패")
					}
				})
				*/
				
				//XML형식으로 응답데이터를 받을 떄 
				$.ajax({
					url:"air.do",
					data:{location:$("#location").val()},
					success:function(data){
						console.log(data)
						//jquery에서의 find 메서드 : 기준이 되는 요소의 하위 요소들 중 특정 요소를 찾을 떄 사용(html,xml)
						//find 메서드는 제이쿼리 메서드
						//console.log($(data).find("item"))
						
						//xml형식의 응답데이터를 받았을 때
						//1. 응답데이터 안에 실제 데이터가 담겨있는 요소 선택
						let itemArr = $(data).find("item");
						
						//2.반복문을 통해 실제 데이터가 담긴 요소들에 접근해서 동적으로 요소 만들기
						let value = "";
						itemArr.each(function(i,item){
						//	console.log(item);
						//console.log($(item).find("").text()); //<stationName>강남구</stationName>
							value += "<tr>"
								   + "<td>" + $(item).find("stationName").text() + "</td>"
								   + "<td>" + $(item).find("dataTime").text() + "</td>"
								   + "<td>" + $(item).find("khaiValue").text() + "</td>"
								   + "<td>" + $(item).find("pm10Value").text() + "</td>"
								   + "<td>" + $(item).find("so2Value").text() + "</td>"
								   + "<td>" + $(item).find("no2Value").text() + "</td>"
								   + "<td>" + $(item).find("coValue").text() + "</td>"
								   + "<td>" + $(item).find("o3Value").text() + "</td>"
								   + "</tr>";
						})
							$("#result1 tbody").html(value);
						
					},
					error:function(){
						console.log("ajax통신 실패")
					}
				})
			})
		})
	</script>
	
	<br>
	
	<h2>지진해일대피소 정보</h2>
	<input type="button" id="btn2" value="실행">
	<div id="result2"></div>
	
	<script>
	$("#btn2").click(()=>{
		$.ajax({
			url:"disaster.do",
			success:data=>{
				//console.log($(data).find("row"));
				
				let $table = $("<table border='1'></table>")
				let $thead = $("<thead></thead>");
				let headTr = "<tr>"
							+"<th>일련번호</th>"
							+"<th>시도명</th>"
							+"<th>시군구명</th>"
							+"<th>대피장소명</th>"
							+"<th>주소</th>"
							+"<th>수용가능인원</th>"
							+"<th>해변으로부터 거리</th>"
							+"<th>대피소분류명</th>"
							+"</tr>";
				$thead.html(headTr);
				
				let $tbody = $("<tbody></tbody>");
				let bodyTr = "";
					$(data).find("row").each((i,row)=>{
					//console.log($(row).find("shel_nm").text())
					
					bodyTr += "<tr>"
							+ "<td>" + $(row).find("id").text() + "</td>"
							+ "<td>" + $(row).find("sido_name").text() + "</td>"
							+ "<td>" + $(row).find("sigungu_name").text() + "</td>"
							+ "<td>" + $(row).find("shel_nm").text() + "</td>"
							+ "<td>" + $(row).find("address").text() + "</td>"
							+ "<td>" + $(row).find("shel_av").text() + "</td>"
							+ "<td>" + $(row).find("lenth").text() + "</td>"
							+ "<td>" + $(row).find("shel_div_type").text() + "</td>"
							+ "</tr>";
				})
					$tbody.html(bodyTr);
					
					//$table.append($thead,$tbody);
					//$table.appendTo("#result2");
					
					$table.append($thead,$tbody).appendTo("#result2");
					
			},
			error:()=>{
				console.log("ajax통신 실패");
			}
		})
	})
			
			/*
			$("#btn2").click(function(){
				$.ajax({
					url:"disaster.do",
					success:(data)=>{
						//console.log($(data).find("row"));
						
						let $table = $("<table border='1'></table>")
						let $thead = $("<thead></thead>");
						let headTr = "<tr>"
									+"<th>일련번호</th>"
									+"<th>시도명</th>"
									+"<th>시군구명</th>"
									+"<th>대피장소명</th>"
									+"<th>주소</th>"
									+"<th>수용가능인원</th>"
									+"<th>해변으로부터 거리</th>"
									+"<th>대피소분류명</th>"
									+"</tr>";
						$thead.html(headTr);
						
						let $tbody = $("<tbody></tbody>");
						let bodyTr = "";
							$(data).find("row").each(function(i,row){
							//console.log($(row).find("shel_nm").text())
							
							bodyTr += "<tr>"
									+ "<td>" + $(row).find("id").text() + "</td>"
									+ "<td>" + $(row).find("sido_name").text() + "</td>"
									+ "<td>" + $(row).find("sigungu_name").text() + "</td>"
									+ "<td>" + $(row).find("shel_nm").text() + "</td>"
									+ "<td>" + $(row).find("address").text() + "</td>"
									+ "<td>" + $(row).find("shel_av").text() + "</td>"
									+ "<td>" + $(row).find("lenth").text() + "</td>"
									+ "<td>" + $(row).find("shel_div_type").text() + "</td>"
									+ "</tr>";
						})
							$tbody.html(bodyTr);
							
							//$table.append($thead,$tbody);
							//$table.appendTo("#result2");
							
							$table.append($thead,$tbody).appendTo("#result2");
							
					},
					error:function(){
						console.log("ajax통신 실패");
					}
				})
			})
			*/
			
			/*
				**화살표 함수**
				익명함수들을 화살표 함수로 작성할 수 있다.
				function(){}를  			"() => {}" 형식으로 작성가능
				
				function(data){}를 		"(data) => {}"로 작성가능
				
				function(a,b){}를 		"(a,b) => {}"로 작성가능
				
				function(){return 10;}을 "()=>{10}"로 작성가능 
			
			*/
		//})
	</script>
	<br><br>
	
	
	<h2>경상남도 범죄소탕 대작전 검거율 (table xml)</h2>
	<input type="button" id="btn3" value="검거율">
	<br><br>
	<table id="result3" border="1">
		<thead>
			<tr> 
				<th>경찰서 명</th>
				<th>경찰 인원</th>
				<th>주요 범죄 발생 카운트</th>
				<th>주요 범죄 검거 카운트</th>
				<th>살인 발생 카운트</th>
				<th>살인 검거 카운트</th>
				<th>폭력 발생 카운트</th>
				<th>폭력 검거 카운트</th>
			</tr>
		</thead>
		<tbody>
		
		</tbody>
	</table>
	
	<script>
		$("#btn3").click(function(){
			$.ajax({
				url:"smoke.do",
				success:function(data){
					let itemArr = $(data).find("item");
					
					let value = "";
					itemArr.each(function(i,item){
						value += "<tr>"
							   + "<td>" + $(item).find("polcsttn_nm").text() + "</td>"
							   + "<td>" + $(item).find("polc_nmpr").text() + "</td>"
							   + "<td>" + $(item).find("five_main_crmnl_occrrnc_cnt").text() + "</td>"
							   + "<td>" + $(item).find("five_main_crmnl_arrest_cnt").text() + "</td>"
							   + "<td>" + $(item).find("murder_occrrnc_cnt").text() + "</td>"
							   + "<td>" + $(item).find("murder_arrest_cnt").text() + "</td>"
							   + "<td>" + $(item).find("violnc_occrrnc_cnt").text() + "</td>"
							   + "<td>" + $(item).find("violnc_arrest_cnt").text() + "</td>"
							   + "</tr>";
					})
						$("#result3 tbody").html(value);
						},
				error:function(){
					console.log("ajax통신실패")
				}
			})
		})
	</script>
	<br>
	
	
	<h2>경상남도 범죄소탕 대작전 검거율 (div json 화살표활용)</h2>
	<input type="button" id="btn4" value="검거">
	<div id="result4"></div>
	
	
	
	<script>
	
	/*
	$.ajax({
               url:"turtle.do",
               success:data=>{
                   console.log(data);
                   
                  const itemArr = data.response.body.items.item;
                  
                  let value = "";
                  
                  for(let i in itemArr){
                     
                     let item = itemArr[i];
                     
                     value += "<tr>"
                          + "<td>" + item.pttId + "</td>"
                          + "<td>" + item.spcKrNm + "</td>"
                          + "<td>" + item.nttyWght + "</td>"
                          + "<td>" + item.nttyLt + "</td>"
                          + "<td>" + item.nttyAcqstDe + "</td>"
                          + "<td>" + item.nttyAcqstLcNm + "</td>"
                          + "<td>" + item.dschrgDe + "</td>"
                          + "<td>" + item.dschrAcqstLcNm + "</td>"
                          + "<td>" + item.mngPznInstNm + "</td>"
                          + "<td>" + item.mngInstNm + "</td>"
                          + "</tr>";
                  }
                  
                  $("#result5 tbody").html(value);
	
	
	*/
	
		$("#btn4").click(function(){
			$.ajax({
				url:"terr.do",
				success:function(data){
					console.log(data)
					const arr = data.getgnPolcCntVsCrmnlOccrrncRtNdArrstRtData.body.items.item;
					
					let $table = $("<table border='1'></table>");
					
					let $thead = $("<thead></thead>");
					let headTr = "<tr>"
								+"<th>경찰서명</th>"
								+"<th>경찰인원</th>"
								+"<th>강도 발생 카운트</th>"
								+"<th>강도 검거 카운트</th>"
								+"<th>강간 발생 카운트</th>"
								+"<th>강간 검거 카운트</th>"
								+"</tr>";
					let $thead1 = $thead.html(headTr);
						
					let $tbody = $("<tbody></tbody>");
					
					
					let bodyTr="";
					
					for(let i in arr){
						let arr1 = arr[i];
						
						 bodyTr += "<tr>"
							+"<td>"+arr1.polcsttn_nm+"</td>"
							+"<td>"+arr1.polc_nmpr+"</td>"
							+"<td>"+arr1.murder_occrrnc_cnt+"</td>"
							+"<td>"+arr1.murder_arrest_cnt+"</td>"
							+"<td>"+arr1.rape_occrrnc_cnr+"</td>"
							+"<td>"+arr1.rape_arrest_cnt+"</td>"
							+"</tr>";
					}
					
						let $tbody1 = $tbody.html(bodyTr);
						$table.append($thead1,$tbody1).appendTo("#result4");
				},
				error:function(){
					console.log("ajax통신실패");
				}
			})
		})
	</script>
	
</body>
</html>
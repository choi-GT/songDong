<%@ page language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html log="ko">
<head>
<meta charset="UTF-8">
<!-- ModelAndView에서 저장한 데이터 호출 -->
<title>${title}</title>
<style>
	* {
	    margin: 0;
	    padding: 0;
	    box-sizing: border-box;
	  }
	  #container {
         background-color: #8ee9bb;
         background-size: cover;
         position: absolute;
         top: 0;
         left: 0;
         right: 0;
         bottom: 0;
       }
   form {
		width: 350px;
		border-radius: 10px;
        background: rgba(0, 0, 0, 0.7);
        margin-left: auto;
        margin-right: auto;
        margin-top: 50px;
        padding: 30px;
    }
	form h2 {
	    color: #fff;
    	text-align: center;
	}
	form input {
		display: black;
		width: 100%;
	}
	form label{
		color: #fff;
	}
	form input {
	    background-color: transparent;
	    border: 0;
	    border-bottom: 2px solid #fff;
	    padding: 10px 5px;
	    margin-top: 10px;
	    margin-bottom: 10px;
  	}
	form button {
	    display: block;
	    width: 100%;
	    background-color: blueviolet;
	    color: #fff;
	    border-radius: 20px;
	    padding: 10px;
	    border: 0;
	    margin-bottom: 10px;
  	}	
	#userID
	, #password
	, #password2 {
		color: #fff;
	}
		  
			   	  
</style>
</head>
<body>
	<div id="container">
	<form id="memberDropForm" method="post" action="/member/memberDropProc">
		<h2 class="">회원 탈퇴</h2>
		<div>
			<label for="userID">아이디</label>
			<input type="text" id="userID" name="userID" placeholder="아이디를 입력하세요." value="" />
		</div>
		<div>
			<label for="password">비밀번호</label>
			<input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요." value="" />
		</div>
		<div>
			<label for="password2">비밀번호 확인</label>
			<input type="password" id="password2" name="password2" placeholder="비밀번호를 입력하세요." value="" />
		</div>
		<button type="button" id="btnDrop">탈퇴</button>
	</form>
	</div>
	<script>
		
		document.querySelector('#btnDrop').addEventListener('click', function() {
			// 비밀번호 확인하기
			const userID = document.querySelector('#userID').value?.trim();
			const password = document.querySelector('#password').value?.trim();
			const password2 = document.querySelector('#password2').value?.trim();
			if (password !== '' && password2 !== '' && password !== password2) {
			alert('비밀번호와 비밀번호 확인 란이 다릅니다.');
			return false;
			}
			document.querySelector('#memberDropForm').submit();
		});
		const message = "<c:out value='${message}' />";
		if (message){
			alert(message);
		}
	</script>
</body>
</html>
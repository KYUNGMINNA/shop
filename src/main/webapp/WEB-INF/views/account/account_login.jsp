<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<%@ include file="/WEB-INF/views/include/header.jsp"%>
<section>
	<form action="<c:url value='/account/login ' />" method="post">
		<div>
			<div class="id">
				<input type="text" name="accountId" placeholder="아이디">
			</div>
			<div class="pw">
				<input type="password" name="accountPw" placeholder="비밀번호" autocomplete="new-password">
			</div>


			<button type="submit">로그인</button>
			<button type="button" id="signIn">회원가입</button>
		</div>

	</form>
</section>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<script>
$(function(){ // Jquery 시작
	
	$('#signIn').click(function(){
		location.href="<c:url value='/account/signIn' /> "
		
	});

}); //Jquery 종료 
	
	
</script>
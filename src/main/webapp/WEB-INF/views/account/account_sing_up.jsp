<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



 <%@ include file="/WEB-INF/views/include/header.jsp" %>
<section>
	<form action="<c:url value='/account/login'/>" method="post" id="loginform">
		<div>
		<input type="text" name="account_id" id="account_id" class="account_id">
		<button type="button"  id="account_id_check_btn" class="account_id_check_btn">아이디 중복 체크</button>
		</div>
		
		<div>
		<input type="password" name="account_pw" id="account_pw" class="account_pw">
		<input type="password"  id="account_pw_check" class="account_pw_check">
		</div>		
		
		<!-- <div>
		<input type="text" name="account_name" id="account_name" class="account_name">
		<input type="text" name="account_birth_year" id="account_birth_year" class="account_birth_year" maxlength="4">
		<select  class="account_birth_month" id="account_birth_month" class="account_birth_month">
			<option value="">월</option>
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
			<option value="6">6</option>
			<option value="7">7</option>
			<option value="8">8</option>
			<option value="9">9</option>
			<option value="10">10</option>
			<option value="11">11</option>
			<option value="12">12</option>										  	 
		</select>
		<input type="text" class="account_brith_day" id="account_brith_day" class="account_brith_day" maxlength="2">
		</div>		

		
		
		
		
		
		
		<div>
		<input type="text" name="account_email_id" id="account_email_id" class="account_email_id" >
		<select class="account_email_address" id="account_email_address" name="account_email_address">
             <option>@naver.com</option>
             <option>@daum.net</option>
             <option>@gmail.com</option>
             <option>@hanmail.com</option>
             <option>@yahoo.co.kr</option>
           </select>
		<button type="button" name="account_email_auth_number_btn" id="account_email_auth_number_btn" class="account_email_auth_number_btn">인증 번호 전송</button>
		<input type="text" name="account_email_auth_number" id="account_email_auth_number" class="account_email_auth_number" placeholder="인증번호 8자리를 입력하세요"  maxlength="8" disabled="disabled">

		</div>		 -->
		
		<button type="button"  id="account_sign_up_btn">회원가입</button>
	</form>
</section>




 <%@ include file="/WEB-INF/views/include/footer.jsp" %>

<script>
//아이디 중복 확인용 
var duplication_id=false;
var reg_exp_id=RegExp(/^[a-z]+[a-z0-9]{4,19}$/g); //영어로 시작되며 영어 +숫자 총합 5글자~20글자 사이의 아이디만 허용(특수문자 불가)

//비밀번호 비밀번호 확인 일치용 
var same_pw=false;
var reg_exp_pw=RegExp(/^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/); //영어 , 특수문자,숫자 포함 



	$(function(){ //Jquery 시작 
		
		let auth_code='';
		let now=new Date().getFullYear();
	
		
		//아이디 중복 체크 이벤트 시작
		$('#account_id_check_btn').click(function(){
			
			//아이디 값 
			const user_id=$('#account_id').val();
			
			//아이디 공백 방지
			if(user_id ===''){
				alert('아이디를 입력해 주세요');
				return;
			}
			
			
			
			//아이디 중복 체크를 위한 비동기 통신 시작
			$.ajax({
				type:'post',
				url:'<c:url value="/account/duplication"/>',
				data:user_id,
				contentType:'application/json',
				success:function(data){
					if(data ==='not_duplication'){
						$('#account_id').attr('readonly','true');
						alert('사용 가능한 아이디 입니다.');
						duplication_id=true;
					}else{
						alert('중복된 아이디 입니다 아아디를 다시 입력해주세요');
						duplication_id=false;
					}
				},
				error:function(){
					alert('서버 문제로 아이디 중복 체크에 실패하였습니다. 나중에 다시 시도해 주세요');
					
				}
			});//아이디 중복 체크 비동기 통신 종료
			
		});//아이디 중복 체크 이벤트 종료 
		
		//비밀번호 비밀번호 확인란 일치 여부 
		$('#account_pw_check').keyup(function(){
			
			if($('#account_pw').val() === $('#account_pw_check').val()){
				same_pw=true;
				
			}else{
				$('#account_pw_check').focus();
			}
			
		});//비밀번호 비밀번호 확인란 일치 여부
		
		
		
		
		
		
		
		//이메일로 인증 번호 전송 이벤트 
		$('#account_email_auth_number_btn').click(function(){
			
			const email=$('#account_email_id').val()+$('#account_email_address').val();
			
			if(email ===''){
				alert('이메일을 다시 입력해 주세요');
				return
			}
			
			$.ajax({
				type:'get',
				url:'<c:url value="/account/emailAuthNumber"/>'+email,
				success:function(message){
					$('#account_email_auth_number').attr('disabled',false);
					alert('인증 번호가 전송되었습니다. 확인 이후 입력란에 입력해 주세요');
					auth_code=message;
				},
				error:function(){
					alert('서버 문제로 메일 전송에 실패하였습니다. 다시 버튼을 눌러주세요');
				}
			});
			
		});//이메일로 인증 번호 전송 이벤트 종료 
		
		
		
		
		//생년 월 일 중 일 날짜 윤달  및  일  올바는 값 입력 체크  이벤트 
		$('#account_birth_day').keyup(function() {
				if($('#account_birth_year').val()>1900 || $('#account_birth_year').val() >now){
					return false;
				}
		
		});
		
		
		
		
		
		//회원가입 버튼 클릭 이벤트
		$('#account_sign_up_btn').click(function() {
				$('#loginform').submit();

			if(auth_code ===$('#account_email_auth_number')){
				
			}

		});//회원 가입 버튼 클릭 이벤트 종료 
		
		
	
	
	
	
	});//JQuery 끝
</script>
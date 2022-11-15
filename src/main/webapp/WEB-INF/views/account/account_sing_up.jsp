<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


 <%@ include file="/WEB-INF/views/include/header.jsp" %> 

<section>
	<form action="<c:url value='/account/signIn'/>" method="post" id="loginform">
		<div>
		<input type="text" name="accountId" id="account_id" class="account_id">
		<button type="button"  id="account_id_check_btn" class="account_id_check_btn">아이디 중복 체크</button>
		</div>
		
		<div>
		<input type="password" name="accountPw" id="accountPw" class="account_pw">
		<span  id="pass"></span>
		<input type="password"  id="account_pw_check" class="account_pw_check">
		<span  id="pass_same_check"></span>
		</div>		
	
		<div>
		<input type="text" name="email" id="account_email_id" class="account_email_id" >
		<span>@</span>
		<select class="account_email_address" id="account_email_address" name="account_email_address">
             <option>naver.com</option>
             <option>daum.net</option>
             <option>gmail.com</option>
             <option>hanmail.com</option>
             <option>yahoo.co.kr</option>
           </select>
		<button type="button" name="account_email_auth_number_btn" id="account_email_auth_number_btn" class="account_email_auth_number_btn">인증 번호 전송</button>
		
		<br>
		<input type="text" name="account_email_auth_number" id="account_email_auth_number" class="account_email_auth_number" placeholder="인증번호 8자리를 입력하세요"  maxlength="8" disabled="disabled">
		
		<button type="button" id="auto_nuum_collect">인증번호 확인</button>
		<span id="auth_same"></span>
		
		</div>	
		<input type="hidden" name="accountEmail" id="accountEmail">
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

//인증번호 확인
var auth_number=false;


	$(function(){ //Jquery 시작 
		
		let auth_code='';
		let now=new Date().getFullYear();
	
		let email='';
		
		//아이디 중복 체크 이벤트 시작
		$('#account_id_check_btn').click(function(){
			//아이디 값 
			const user_id=$('#account_id').val();
			
			
			/* if(!reg_exp_id.test(user_id)){
				alert('아이디는 영어 + 숫자 조합으로 최소 5글자 이상 입력해 주세요');
				return;
			} */
			
			
			//아이디 중복 체크를 위한 비동기 통신 시작
			$.ajax({
				type:'post',
				url:'<c:url value="/account/duplication"/>',
				data:user_id,
				contentType:'application/json',
				success:function(data){
					if(data ==='not_duplication'){
						//$('#account_id').attr('readonly','true');
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
		
		
		
		//비밀번호 작성시 정규표현확인 위함 --blur : 포커스를 잃었을 때 이벤트를 처리하는 이벤트 핸들러
		$('#accountPw').focusout(function(){
			
			if(!reg_exp_pw.test($('#accountPw').val())){
				$('#pass').text('비밀번호는 알파벳 + 특수문자 +숫자를 포함하여 최소 8자리 이상 입력해주세요.');
				return;
			}
		});
		
		//비밀번호 비밀번호 확인란 일치 여부 
		$('#account_pw_check').focusout(function(){
			if($('#accountPw').val() === $('#account_pw_check').val()){
				same_pw=true;
				
				$('#pass_same_check').text('비밀번호가 일치합니다.');		
			}else{
				$('#account_pw_check').focus();
				$('#pass_same_check').text('비밀번호가 일치하지않습니다');
			}
		});//비밀번호 비밀번호 확인란 일치 여부
		
		
		
		
		
		
		//이메일로 인증 번호 전송 이벤트 
		$('#account_email_auth_number_btn').click(function(){
			
			email=$('#account_email_id').val()+ "@" +$('#account_email_address').val();
		
			///이메일이 공백
			if($('#account_email_id').val() ===''){
				alert('이메일을 다시 입력해 주세요');
				return
			}
			console.log(email.length);
			$.ajax({
				type:'get',
				url:'<c:url value="/account/mailCheck?email=" />'+email,
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
		auth_same
		//인증번호 일치 여부 
		$('#auto_num_collect').click(function(){
			
			if($('#account_email_auth_number').val().length===0){
				alert('인증번호를 입력해 주세요');
			}
			
			if($('#account_email_auth_number').val()===auth_code){
				
				// style속성에 값을 줘서 안나오게 
				//$('#pass_same').attr('style','display:none');
				
				$('#auto_same').text('인증번호 확인이 완료되었습니다.');
			}else{
				
				$('#auto_same').text('인증번호가 일치하지 않습니다 다시 확인해 주세요.');
			}
		});
		
		

		
		
		//회원가입 버튼 클릭 이벤트
		$('#account_sign_up_btn').click(function() {
				console.log(email);
				$('#accountEmail').val(email);
				//$('#loginform').submit();

		

		});//회원 가입 버튼 클릭 이벤트 종료 
		
		
	
	
	
	
	});//JQuery 끝
</script>
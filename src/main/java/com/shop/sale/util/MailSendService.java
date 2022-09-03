package com.shop.sale.util;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSendService {

	@Autowired
	private JavaMailSender mailSender;
	private int authNum;
	
	//난수 발생 (여러분들 맘대로 설정하세요)
	public void makeRandomNumber() {
		//난수의 범위 : 111111~999999
		Random r=new Random();
		int checkNum=r.nextInt(888888)+111111;  //111111~999999
		System.out.println("인증 번호 : "+checkNum);
		authNum=checkNum;
	}
	
	//회원 가입시 사용할 이메일 양식 
	public String joinEmail(String email) {
		makeRandomNumber();
		String setFrom="nkm6095@gmail.com"; //email-config에 설정한 자신의 이메일 주소를 입력
		String toMail=email; //수신 받을 이메일 
		String title="[회원 가입 인증 번호]회원 가입 인증 번호"; //이메일 제목
		String content="홈페이지를 방문해 주셔서 감사합니다 .  "+
						"<br><br>"+
						"인증번호는 " + authNum + "입니다"+
						"<br>"+
						"해당 인증 번호를 인증번호 확인란에 가입하여 주세요."; //이메일 내용 삽입
		mailSend(setFrom, toMail, title, content);
		
		return Integer.toString(authNum);
	}
	
	
	//이메일 전송 메서드 
	public void mailSend(String setFrom,String toMail,String title,String content) {
		
		try {
			MimeMessage message=mailSender.createMimeMessage(); // HTML 사용이 가능 하다  -주로 많이 사용 

			//기사 설정들을 담당할 MimeMessageHelper를 생성 .
			//생성자의 매개값으로 (MimeMessage객체 , boole타입 논리값 , 문자인코딩설정 ) 전달
			//true매개값을 전달하면 MultiPart(html ,이미지,오디오 등 대용량 파일 전송가능한것) 형식의 메시지 전달이 가능 .
			//값을 전달하지 않으면 단순 텍스트만사용(즉, 두번째 매개값을 비워두면 단순히 텍스트만 전달할 수 있다)
			MimeMessageHelper helper=new MimeMessageHelper(message,true,"utf-8");
			
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			//true를 전달해야 HTML 형식으로 전송하겠다 / 아무것도 작성하지 않으면 단순 텍스트형으로 전달
			helper.setText(content,true); 
			
			mailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
	
}

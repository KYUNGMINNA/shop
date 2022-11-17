package com.shop.sale.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.sale.account.service.IAccountService;
import com.shop.sale.command.AccountVO;
import com.shop.sale.util.MailSendService;



@Controller
@RequestMapping("/account")
public class AccountController {
 
	private static final Logger logger=LoggerFactory.getLogger(AccountController.class); 
	
	//@Autowired
	//private IAccountService accountService;
	
	
	private final IAccountService accountService;

	//@Autowired
	//private MailSendService mailService;
	
	private final MailSendService mailService;
	

	@Autowired //단일 생성자 autowired 생략 가능 
	public AccountController(IAccountService accountService, MailSendService mailService) {
		super();
		this.accountService = accountService;
		this.mailService = mailService;
	}

	@GetMapping("/login")
	public String getLogin() {
		logger.info("로그인 페이지 접속");
		return "account/account_login";
	}

	@PostMapping("/login")
	public String getLogin(Model model,String accountId,String accountPw,HttpSession session) {


		AccountVO dbInfo=accountService.login(accountId);
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();

		if(dbInfo !=null) {
			if(encoder.matches(accountPw, dbInfo.getAccountPw())) {

				session.setAttribute("loginSession",accountService.login(accountId));
				model.addAttribute("loginInfo", accountService.login(accountId));
				return "/home";

			}else {
				return "redirect:/account/login";
			}

		}else {
			logger.info("info  null 아님");
			return "redirect:/account/signIn";
		}
	}




	//회원가입
	@GetMapping("/signIn")
	public String signUp() {

		return "account/account_sing_up";

	}


	//아이디 중복 체크 비동기 통신
	@PostMapping("/duplication")
	@ResponseBody
	public String idDuplication(@RequestBody String user_id) {
		logger.info("/account/duplication  postMapping responsebody");

		logger.info(Integer.toString(accountService.accountDuplicationId(user_id)));
		
		if(accountService.accountDuplicationId(user_id)==1) {
			System.out.println("중복된 아이디가 존재 함 ");
			return "duplication";
		}

		return "not_duplication";


	}


	//이메일 인증 
	@GetMapping("/mailCheck")
	@ResponseBody			//파라미터 name명과  ,매개변수명이 똑같으면 @requestParam 작성안해도 된다.
	public String mailCheck(String email) {
		System.out.println("이메일 인증 요청 들어옴!");
		System.out.println("인증 이메일 :"+email);
		return mailService.joinEmail(email);

	}




	//회원 가입  결과 
	@PostMapping("/signIn")
	public String signUp(HttpSession session,AccountVO accountVO) {
		accountVO.setAccountType("개인");

		if(accountService.accountInsert(accountVO)==1) {
			session.setAttribute("login_session",accountVO);
			System.out.println("세션에 저장 되었습니다.");
			System.out.println("방금 들어간 데이터의 기본키 값은 : "+accountVO.getAccount());
		}
		return "redirect:/";
	}


}

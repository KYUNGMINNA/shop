package com.shop.sale.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.sale.account.service.AccountService;
import com.shop.sale.account.service.IAccountService;
import com.shop.sale.command.AccountVO;


@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private IAccountService accountService;


	
	//회원가입
	@GetMapping("/signIn")
	public String signUp() {

		return "account/account_sing_up";
	}
	
	
	//아이디 중복 체크 비동기 통신
	@PostMapping("/duplication")
	@ResponseBody
	public String idDuplication(@RequestBody String user_id) {
		System.out.println("컨트롤러 호출됨");
		
		System.out.println(accountService.accountDuplicationId(user_id));
		if(accountService.accountDuplicationId(user_id)==1) {
			System.out.println("중복된 아이디가 존재 함 ");
			return "duplication";
		}
	
		return "not_duplication";
		
		
	}
	
	
	
	

	//회원 가입  결과 
	@PostMapping("/signIn")
	public String signUp(HttpSession session,AccountVO accountVO) {
		accountVO.setAccount_type("개인");

		if(accountService.accountInsert(accountVO)==1) {
			session.setAttribute("login_session",accountVO);
			System.out.println("세션에 저장 되었습니다.");
			System.out.println("방금 들어간 데이터의 기본키 값은 : "+accountVO.getAccount_pk());
		}
		return "redirect:/";
	}


}

package com.shop.sale.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.sale.account.service.AccountService;
import com.shop.sale.account.service.IAccountService;
import com.shop.sale.command.AccountVO;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private IAccountService accountService;


	//회원가입
	@GetMapping("/login")
	public String signUp() {

		return "account/account_sing_up";
	}

	//회원 가입  결과 
	@PostMapping("/login")
	public String signUp(HttpSession session,AccountVO accountVO) {
		accountVO.setAccount_type("개인");

		if(accountService.accountInsert(accountVO)==1) {
			session.setAttribute("login_session",accountVO);
			System.out.println("세션에 저장 되었습니다.");
			System.out.println("방금 들어간 데이터의 기본키 값은 : "+accountVO.getAccount_pk());
		}
		
		
		

		return "forward:/board";
	}


}

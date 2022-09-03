package com.shop.sale.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.shop.sale.command.AccountVO;

@Controller
public class BoardController {

	
	@PostMapping("/board")
	public String board_page(HttpSession session) {
		if(session.getAttribute("login_session")!=null){
			System.out.println("현재 로그인 되어 있는 세션의 정보 "+session.getAttribute("login_session"));						
		}
		return "board/main";
	}
}

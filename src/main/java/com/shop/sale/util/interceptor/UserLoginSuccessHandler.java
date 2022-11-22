package com.shop.sale.util.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.shop.sale.command.AccountVO;



public class UserLoginSuccessHandler implements HandlerInterceptor{

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		ModelMap mv=modelAndView.getModelMap();
		AccountVO accountVO=(AccountVO)mv.get("loginInfo");
		
		if(accountVO ==null) {
			modelAndView.addObject("msg", "loginFail");
			modelAndView.setViewName("/account/login");
		}else {
			HttpSession session=request.getSession();
			session.setAttribute("loginSession", accountVO);			
			response.sendRedirect(request.getContextPath()); 

		}
		
	}
}

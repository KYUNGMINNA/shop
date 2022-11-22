package com.shop.sale.util.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class UserAuthHandler implements HandlerInterceptor {


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session=request.getSession();
		if(session.getAttribute("loginSession") ==null) {
			response.sendRedirect(request.getContextPath()+"/account/login");
			return false;
		}

		return true; 

	}
}

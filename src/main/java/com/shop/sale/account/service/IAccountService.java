package com.shop.sale.account.service;

import org.apache.ibatis.annotations.Param;

import com.shop.sale.command.AccountVO;

public interface IAccountService {

	//로그인
	AccountVO login(String accountId);
		
	
	int accountInsert(AccountVO accountVO);
	
	//아아디 중복체크
	int accountDuplicationId(String account_id);
}

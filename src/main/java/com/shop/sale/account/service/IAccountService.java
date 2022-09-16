package com.shop.sale.account.service;

import com.shop.sale.command.AccountVO;

public interface IAccountService {

	int accountInsert(AccountVO accountVO);
	
	//아아디 중복체크
	int accountDuplicationId(String account_id);
}

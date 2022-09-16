package com.shop.sale.account.mapper;

import com.shop.sale.command.AccountVO;

public interface IAccountMapper {

	//회원 가입
	int accountInsert(AccountVO accountVO);
	
	//아아디 중복체크
	int accountDuplicationId(String account_id);
}

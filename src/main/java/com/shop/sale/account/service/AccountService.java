package com.shop.sale.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.sale.account.mapper.IAccountMapper;
import com.shop.sale.command.AccountVO;

@Service
public class AccountService  implements IAccountService{

	
	@Autowired
	private IAccountMapper mapper;

	@Override
	public int accountInsert(AccountVO accountVO) {
	 return mapper.accountInsert(accountVO);
		
	}

	@Override
	public int accountDuplicationId(String account_id) {
		return mapper.accountDuplicationId(account_id);
	}
	
	
	

}

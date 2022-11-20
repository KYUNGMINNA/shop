package com.shop.sale.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.shop.sale.account.mapper.IAccountMapper;
import com.shop.sale.command.AccountVO;

@Service
public class AccountService implements IAccountService {

	@Autowired
	private IAccountMapper accountMapper;

	@Override
	public AccountVO login(String accountId) {
		return accountMapper.login(accountId);
	}

	@Override
	public int accountInsert(AccountVO accountVO) {
		// 회원의 비밀번호를 암호화 인코딩
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println("암호화 하기 전 비밀번호 : " + accountVO.getAccountPw());

		// 비밀번호를 암호화 해서 user객체에 다시 저장하기
		String securePw = encoder.encode(accountVO.getAccountPw());
		System.out.println("암호화 후 비밀번호 : " + securePw);
		accountVO.setAccountPw(securePw);

		//accountVO.setAccountPw(encoder.encode(accountVO.getAccountPw()); --한줄 요약
		return accountMapper.accountInsert(accountVO);

	}

	@Override
	public int accountDuplicationId(String account_id) {
		return accountMapper.accountDuplicationId(account_id);
	}

}

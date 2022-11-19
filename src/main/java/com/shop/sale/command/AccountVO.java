package com.shop.sale.command;

import java.sql.Time;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/*
 CREATE TABLE accounts(
	account int(10)  primary key auto_increment,
    accountId varchar(100) not null,
    accountPw varchar(100) not null,
    accountEmail varchar(100) not null,
    accountType int(1) default 0,
    accountRegistDate datetime default current_timestamp,
    accountLastLoginDate datetime default current_timestamp
 );
 */



@Getter
@Setter
@ToString
public class AccountVO {

	
	private int account;
	private String accountId;
	private String accountPw;
	private String accountEmail;
	private String accountType;
	
	//mysql  --date :YYYY-MM-DD 1000-01-01~ 9999-12-31
	//dateTime -- YYYY-MM-DD HH:MM:SS   -- 문자형, 8byte  : 직접 값 입력해 줘야 들어감 
	//Time -- -838:59:59~838:59:59 
	//Timestamp -- 1970-01-01 00:00:01~ 2038-01-19 03:14:07  --숫자형 ,4byte 
	
	
	//java.util.Date 클래스로는 오라클Date 형식과 연동할 수 없고  --> java.sql.Date = 년 , 월 ,일를 사용해야 한다.
	//Timestamp -- 년,월,일,시,분,초
	
	//MySQL java.sql.TimeStamp 
	private Timestamp accountRegistDate;
	private Time accountLastLoginDate;
	
	
}

package com.shop.sale.command;

 class test {

	static int sum=0;
	int data=0;
	public static void main(String[] args) {
		test t =new test();
		
		int data=0;
		int sum=0;
		
		while(data<=10) {
			sum+=data;
			data++;
		}
		System.out.println(data+" :"+t.sum);
		
			
		
	}

}

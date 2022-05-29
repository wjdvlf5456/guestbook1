package com.javaex.dao;

import com.javaex.vo.GuestBookVo;

public class TestApp {
	
	public static void main(String[] args) {
		
		GuestBookDao guestbookDao = new GuestBookDao();
		
		GuestBookVo guestBookVo = new GuestBookVo("유재석","###","잘가","2000-12-12");
		
		guestbookDao.guestAdd(guestBookVo);
		
		
		
		
	}

}

package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.GuestBookVo;

public class GuestBookDao {

	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@webdb_high?TNS_ADMIN=/Users/choijungphil/jungphil/Wallet_webdb";
	private String id = "admin";
	private String pw = "^^65Rhcemdtla";

	// DB 구축 메소드
	public void getConnection() {
		try {
			// 오라클 드라이버 로딩
			Class.forName(driver);

			// 데이터베이스 연결
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

	// 자원정리 메소드
	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

	public int guestbookInsert(GuestBookVo guestBookVo) {
		int count = 0;

		getConnection();

		try {
			// SQL문 준비
			String query = "";
			query += " insert into guestbook ";
			query += " values (seq_guestbook_no.nextval, ?, ?, ? ,?)";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, guestBookVo.getName());
			pstmt.setString(2, guestBookVo.getPassword());
			pstmt.setString(3, guestBookVo.getContent());
			pstmt.setString(4, guestBookVo.getRegDate());

			// 실행
			// 성공회수
			count = pstmt.executeUpdate();

			// 결과처리
			System.out.println(count + "건이 등록되었습니다.");

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		close();

		return count;

	}
	
	
	
	
	
	public GuestBookVo getId(int no) {
	      GuestBookVo guestBookVo = null;
	      
	      this.getConnection();
	      
	      try {

	         // 3. SQL문 준비 / 바인딩 / 실행
	         //SQL문 준비 
	         String query = "";
	         query += " select  no, ";
	         query += "         name, ";
	         query += "         password, ";
	         query += "         content ";
	         query += " 		reg_date ";
	         query += " from guestbook ";
	         query += " where no = ? ";
	      
	         //바인딩 
	         pstmt = conn.prepareStatement(query);
	         pstmt.setInt(1, no);
	         
	         //실행
	         rs = pstmt.executeQuery();
	         
	         // 4.결과처리
	         while(rs.next()) {
	            
	            int nod = rs.getInt("no");
	            String name = rs.getString("name");
	            String password = rs.getString("password");
	            String content = rs.getString("content");
	            String regDate = rs.getString("reg_date");
	            
	            guestBookVo = new GuestBookVo(nod,name,password,content,regDate);
	         }
	         
	      } catch (SQLException e) {
	         System.out.println("error:" + e);
	      } 
	      
	      this.close();
	      
	      return guestBookVo;
	   }

	
}

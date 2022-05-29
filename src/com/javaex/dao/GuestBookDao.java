package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	public int guestAdd(GuestBookVo guestBookVo) {
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

	public int guestDelete(int no) {
		int count = 1;

		getConnection();

		try {
			// SQL문 준비
			String query = "";
			query += " delete from guestbook ";
			query += " where no = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			// 실행
			count = pstmt.executeUpdate();

			// 출력
			System.out.println(count + "건이 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		close();

		return count;
	}
	
	public List<GuestBookVo> guestSelect() {

		// 리스트로 만들기
		List<GuestBookVo> guestList = new ArrayList<GuestBookVo>();
		getConnection();

		try {
			// SQL문 준비
			String query = "";
			query += " select no,";
			query += " 		  name,";
			query += " 		  password,";
			query += " 		  content,";
			query += " 		  reg_date";
			query += " from guestbook ";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			// 실행
			// resultSet 가져오기
			rs = pstmt.executeQuery();

			// 결과처리
			// 반복문으로 Vo만들어 List에 추가하기
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");

				GuestBookVo guestBookVo = new GuestBookVo(no, name, password, content, regDate);

				guestList.add(guestBookVo);

			}

			for (int i = 0; i < guestList.size(); i++) {
				guestList.get(i).toString();
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		close();

		return guestList;

	}

}

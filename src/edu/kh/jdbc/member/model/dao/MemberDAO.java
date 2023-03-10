package edu.kh.jdbc.member.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.member.vo.Member;

public class MemberDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private Properties prop = null;
	
	private List<Member> memList = new ArrayList<>();
	
	
	public MemberDAO() {
		try {
			
			prop = new Properties();
			
			prop.loadFromXML(new FileInputStream("member-query.xml"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	public Member selectMyinfo(Connection conn, Member loginMember) throws Exception{
		
		Member selMember = null;
		
		try {
			
			String sql = prop.getProperty("selectMyinfo");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, loginMember.getMemberNo());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				selMember = new Member(rs.getInt("MEMBER_NO"), 
						rs.getString("MEMBER_ID"), 
						rs.getString("MEMBER_NM"), 
						rs.getString("MEMBER_GENDER"), 
						rs.getString("ENROLL_DATE"));
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return selMember;
	}


	
	public List<Member> selectAll(Connection conn) throws Exception{
		
		try {
			
			String sql = prop.getProperty("selectAll");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				memList.add(new Member(rs.getString("MEMBER_ID"), rs.getString("MEMBER_NM"), rs.getString("MEMBER_GENDER")));
			}
			
			
		} finally {
			close(rs);
			close(stmt);
		}
		
		return memList;
	}


	public int updateMember(Connection conn, String memberName, String memberGender, int memberNo) throws Exception{

		int result = 0;
		
		try {
			
			String sql = prop.getProperty("update");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberName);
			pstmt.setString(2, memberGender);
			pstmt.setInt(3, memberNo);
			
			result = pstmt.executeUpdate();
			
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	public int memDupPw(Connection conn, String password) throws Exception {

		int result = 0;
		
		try {
			
			String sql = prop.getProperty("memDupPw");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, password);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	public int updatePw(Connection conn, String pw1, String pw, int memberNo) throws Exception {

		int result = 0;
		try {
			
			String sql = prop.getProperty("updatePw");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pw1);
			pstmt.setString(2, pw);
			pstmt.setInt(3, memberNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	public int secession(Connection conn, Member loginMember, String pw) throws Exception{

		int result = 0;
		
		try {
			
			String sql = prop.getProperty("delete");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, loginMember.getMemberNo());
			pstmt.setString(2, pw);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

}

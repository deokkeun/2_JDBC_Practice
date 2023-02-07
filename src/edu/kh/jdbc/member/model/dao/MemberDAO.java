package edu.kh.jdbc.member.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import static edu.kh.jdbc.common.JDBCTemplate.*;
import edu.kh.jdbc.member.vo.Member;

public class MemberDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	Properties prop = null;
	
	public MemberDAO() {
		try {
			
			prop = new Properties();
			
			prop.loadFromXML(new FileInputStream("member-query"));
			
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

}

package edu.kh.jdbc.member.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.close;
import static edu.kh.jdbc.common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.member.model.dao.MemberDAO;
import edu.kh.jdbc.member.vo.Member;

public class MemberService {
	
	
	private MemberDAO dao = new MemberDAO();
	
	
	public Member selectMyinfo(Member loginMember) throws Exception{

		Connection conn = getConnection();
		
		Member member = dao.selectMyinfo(conn, loginMember);
		
		close(conn);
		
		return member;
		
	}


	public List<Member> selectAll() throws Exception{
		
		Connection conn = getConnection();
		
		List<Member> memList = dao.selectAll(conn);
		
		close(conn);
		
		return memList;
	}


	
	
	public int updateMember(String memberName, String memberGender, int memberNo) throws Exception{

		Connection conn = getConnection();
		
		int result = dao.updateMember(conn, memberName, memberGender, memberNo);
		
		close(conn);
		
		return result;
	}


	public int memDupPw(String password) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.memDupPw(conn, password);
		
		close(conn);
		
		return result;
	}


	public int updatePw(String pw1, String pw, int memberNo) throws Exception{

		Connection conn = getConnection();
		
		int result = dao.updatePw(conn, pw1, pw, memberNo);
		
		
		
		close(conn);
		
		return result;
	}

}

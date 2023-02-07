package edu.kh.jdbc.member.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.close;
import static edu.kh.jdbc.common.JDBCTemplate.getConnection;

import java.sql.Connection;

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

}

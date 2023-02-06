package edu.kh.jdbc.board.view;

import edu.kh.jdbc.member.vo.Member;

public class BoardView {

	private Member loginMember = null;
	
	public void boardMenu(Member LoginMember) {
		
		
		
		selectAllBoard();
		selectBoard();
		
		
		
		
	}
	
	

}

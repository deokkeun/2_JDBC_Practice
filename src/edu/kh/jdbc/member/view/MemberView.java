package edu.kh.jdbc.member.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.member.model.service.MemberService;
import edu.kh.jdbc.member.vo.Member;

public class MemberView {
	
	// 로그인 회원 정보 저장용 변수
	private Member loginMember = null;
	private MemberService memberService = new MemberService();
	private List<Member> memList = new ArrayList<>();
	
	Scanner sc = new Scanner(System.in);

	
	
	
	public void memberMenu(Member LoginMember) {
		

		// 전달 받은 로그인 회원 정보를 필드에 저장
		this.loginMember = LoginMember;
		
		/* 회원기능 (Member View, Service, DAO, member-query.xml)
		 *                                                                                                                                                                                                                 
		 * 1. 내 정보 조회
		 * 2. 회원 목록 조회(아이디, 이름, 성별)
		 * 3. 내 정보 수정(이름, 성별)
		 * 4. 비밀번호 변경(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인)
		 * 5. 회원 탈퇴
		 */
		
		int input = 0;
		
		do {
			System.out.println("[회원 기능]");
			System.out.println("1. 내 정보 조회");
			System.out.println("2. 회원 목록 조회(아이디, 이름, 성별)");
			System.out.println("3. 내 정보 수정(이름, 성별)");
			System.out.println("4. 비밀번호 변경(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인) ");
			System.out.println("5. 회원 탈퇴");
			System.out.print("메뉴 선택 : ");
			input = sc.nextInt();
			sc.nextLine();
			
			switch(input) {
			case 1: selectMyinfo(loginMember); break;
			case 2: selectAll(); break;
			case 3: updateMember(loginMember); break;
			case 4: updatePw(loginMember); break;
			case 5: secession(loginMember); break;
			default : System.out.println("메뉴에 작성된 번호만 입력해주세요");

			}
			
		} while(input != 0);
		
		
	}

	
	
	
	private void secession(Member loginMember) {
		
		try {
			
			System.out.println("<회원탈퇴>");
			System.out.println("정말 회원 탈퇴 하시겠습니까? (Y/N)");
			String input = sc.next().toUpperCase();
			
			if(input.equals("Y")) {
				
				System.out.println("탈퇴 하시려면 비밀번호를 입력해주세요");
				String pw = sc.next();
				int dup = memDupPw(pw);
				
				if(dup == 1) {
					int result = memberService.secession(loginMember, pw);
					System.out.println("회원 탈퇴 완료");
				} else {
					System.out.println("회원 탈퇴중 오류가 발생했습니다.");
				}
				
				
			} else if(input.equals("N")) {
				System.out.println("회원 탈퇴를 하지 않고 이전의 화면으로 돌아갑니다.");
			} else {
				System.out.println("(Y / N) 둘중에 하나만 입력해주세요");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
	
	
	private void updatePw(Member loginMember) {
		this.loginMember = loginMember;
		
		try {
			System.out.println("비밀번호 변경(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인)");
			
			System.out.print("현재 비밀번호 입력 : ");
			String pw = sc.next();
			
			int result = memDupPw(pw);
			
			if(result == 1) {
				System.out.println("변경할 비밀번호를 입력해주세요");
				
				System.out.print("새 비밀번호 : ");
				String pw1 = sc.next();
				
				System.out.print("새 비밀번호 확인 : ");
				String pw2 = sc.next();
				
				if(pw1.equals(pw2)) {
					
					int pass = memberService.updatePw(pw1, pw, loginMember.getMemberNo());
					
					if(pass > 0) {
						System.out.println("비밀번호 변경이 완료되었습니다");
					} else {
						System.out.println("비밀번호 변경중 오류가 발생하였습니다.");
					}
					
				} else {
					System.out.println("새 비밀번호가 일치하지 않습니다.");
				}
				
			} else {
				System.out.println("현재 비밀번호가 일치하지 않습니다.");
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	
	
	private void updateMember(Member loginMember) {
		this.loginMember = loginMember;
		int result = 0;
		System.out.println("<내 정보 수정(이름, 성별)>");
		
		try {
			
			
				System.out.println("회원번호를 입력해주세요");
				System.out.print("회원번호 : ");
				int memberNo = sc.nextInt();
				sc.nextLine();
				
				System.out.println("수정할 이름을 입력해주세요");
				System.out.print("이름 : ");
				String memberName = sc.next();
				
				System.out.println("수정할 성별을 입력해주세요");
				System.out.print("성별 : ");
				String memberGender = sc.next().toUpperCase();
				
				result = memberService.updateMember(memberName, memberGender, memberNo);
				
				if(result > 0) {
					System.out.println("회원 정보 수정이 완료되었습니다");
				} else {
					System.out.println("회원 정보 수정에 실패했습니다.");
				}
				
			}catch (Exception e) {

				e.printStackTrace();
			}
			
		} 
		
		

	private void selectAll() {
		System.out.println("<회원 목록 조회(아이디, 이름, 성별>");
		
		try {
			
			memList = memberService.selectAll();
			
			printAll1(memList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	public void selectMyinfo(Member loginMember) {
		
		System.out.println("<회원 정보 조회>");
		
			try {
				
					System.out.print("비밀번호 입력 : ");
					String memberPw = sc.next();

					System.out.println("실행");
				
					Member mem = memberService.selectMyinfo(loginMember);
					
					printOne(mem);
					
			} catch(Exception e) {
				e.printStackTrace();
			}
	}
		
	
	
	
	
		
	public void printOne(Member mem) {

		System.out.println("멤버 번호 |   아이디  | 이름|        성별       |   입사일" );
		System.out.println("------------------------------------------------------------------------------------------------");
			System.out.printf(" %6d  | %5s | %3s | %3s | %s \n",
					mem.getMemberNo(), mem.getMemberId(), mem.getMemberName(), mem.getMemberGender(), mem.getEnrolldate());
			System.out.println();
	}
	
	
	public void printAll(List<Member> memList) {
		System.out.println("멤버 번호 |   아이디  |    이름    |  성별  |   입사일" );
		System.out.println("------------------------------------------------------------------------------------------------");
				for(Member mem : memList) {
					System.out.printf(" %6d  | %5s | %3s | %3s | %s \n",
							mem.getMemberNo(), mem.getMemberId(), mem.getMemberName(), mem.getMemberGender(), mem.getEnrolldate());
				}
	}
	
	public void printAll1(List<Member> memList) {
		System.out.println("아이디  |    이름    |  성별" );
		System.out.println("------------------------------------------------------------------------------------------------");
				for(Member mem : memList) {
					System.out.printf("%7s | %8s | %3s  \n",
							mem.getMemberId(), mem.getMemberName(), mem.getMemberGender());
				}
	}
	
	
	public int memDupPw(String password) {
		int result = 0;
		try {
			
			result = memberService.memDupPw(password);
			
			if(result > 0) {
				System.out.println("비밀번호가 일치합니다");
			} else {
				System.out.println("비밀번호가 일치하지 않습니다.");
			}
		} catch (Exception e) {
			System.out.println("비밀번호 확인중 오류 발생");
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
		
	
}


	
	
	
	



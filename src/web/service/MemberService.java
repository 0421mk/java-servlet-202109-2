package web.service;

import java.sql.Connection;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.dao.MemberDao;
import web.dto.Member;

public class MemberService {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection con;
	private MemberDao memberDao;
	
	public MemberService(HttpServletRequest request, HttpServletResponse response, Connection con) {
		this.request = request;
		this.response = response;
		this.con = con;
		
		memberDao = new MemberDao(request, response, con);
	}

	public int getMemberCntByLoginId(String loginId) {
		
		return memberDao.getMemberCntByLoginId(loginId);
		
	}

	public void insertMember(String loginId, String loginPw, String userName) {
		
		memberDao.insertMember(loginId, loginPw, userName);
		
	}

	public Member getMemberByLoginId(String loginId) {
		
		return memberDao.getMemberByLoginId(loginId);
	
	}

}

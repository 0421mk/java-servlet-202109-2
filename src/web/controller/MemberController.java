package web.controller;

import java.io.IOException;
import java.sql.Connection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import web.dto.Member;
import web.service.MemberService;

public class MemberController {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection con;
	private MemberService memberService;

	public MemberController(HttpServletRequest request, HttpServletResponse response, Connection con) {
		this.request = request;
		this.response = response;
		this.con = con;

		this.memberService = new MemberService(request, response, con);
	}

	public void pageJoin(boolean isLogined) throws ServletException, IOException {
		
		if (isLogined) {
			response.getWriter().append("<script>alert('로그아웃 후 이용해주세요.'); location.replace('../article/list');</script>");
			return;
		}
		
		request.getRequestDispatcher("/jsp/member/join.jsp").forward(request, response);
	}

	public void doJoin(boolean isLogined) throws IOException {

		if (isLogined) {
			response.getWriter().append("<script>alert('로그아웃 후 이용해주세요.'); location.replace('../article/list');</script>");
			return;
		}
		
		String loginId = request.getParameter("loginId");
		String loginPw = request.getParameter("loginPw");
		String loginPwConfirm = request.getParameter("loginPwConfirm");
		String userName = request.getParameter("userName");

		int selectCnt = memberService.getMemberCntByLoginId(loginId);

		if (selectCnt == 1) {
			response.getWriter().append(String.format("<script>alert('이미 존재하는 아이디입니다.'); history.back();</script>"));

			return;
		}

		if (loginPw.equals(loginPwConfirm) == false) {
			response.getWriter().append(String.format("<script>alert('비밀번호를 확인해주세요.'); history.back();</script>"));

			return;
		}

		memberService.insertMember(loginId, loginPw, userName);

		response.getWriter()
				.append(String.format(
						"<script>alert('회원가입에 성공하였습니다. 환영합니다! %s님!'); location.replace('../article/list');</script>",
						userName));

	}

	public void pageLogin(boolean isLogined) throws ServletException, IOException {
		
		if (isLogined) {
			response.getWriter().append("<script>alert('로그아웃 후 이용해주세요.'); location.replace('../article/list');</script>");
			return;
		}

		request.getRequestDispatcher("/jsp/member/login.jsp").forward(request, response);

	}

	public void doLogin(boolean isLogined) throws IOException {
		
		if (isLogined) {
			response.getWriter().append("<script>alert('로그아웃 후 이용해주세요.'); location.replace('../article/list');</script>");
			return;
		}

		String loginId = request.getParameter("loginId");
		String loginPw = request.getParameter("loginPw");

		Member member = memberService.getMemberByLoginId(loginId);
		
		System.out.println(member);

		if (member == null) {
			response.getWriter().append(String.format("<script>alert('존재하지 않는 아이디입니다.'); history.back();</script>"));

			return;
		}

		if (member.loginPw.equals(loginPw) == false) {
			response.getWriter().append(String.format("<script>alert('비밀번호를 확인해주세요.'); history.back();</script>"));

			return;
		}

		HttpSession session = request.getSession();
		session.setAttribute("loginedMemberId", member.id);

		response.getWriter().append("<script>alert('로그인 성공'); location.replace('../article/list'); </script>");

	}

	public void logout(boolean isLogined) throws IOException {
		
		if (isLogined == false) {
			response.getWriter().append("<script>alert('로그인 후 이용해주세요.'); location.replace('../article/list');</script>");
			return;
		}

		HttpSession session = request.getSession();
		session.removeAttribute("loginedMemberId");

		response.getWriter()
				.append(String.format("<script>alert('로그아웃되었습니다.'); location.replace('../article/list');</script>"));

	}

}
package web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.util.DBUtil;
import web.util.SecSql;

/**
 * Servlet implementation class ArticleListServlet
 */
@WebServlet("/member/doJoin")
public class MemberDoJoinServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		// TODO Auto-generated method stub

		String url = "jdbc:mysql://localhost:3306/java_servlet?serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeBehavior=convertToNull";
		String user = "root";
		String password = "";
		// 커넥터 드라이버 활성화

		String driverName = "com.mysql.jdbc.Driver";
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			System.err.printf("[ClassNotFoundException 예외, %s]\n", e.getMessage());
			response.getWriter().append("DB 드라이버 클래스 로딩 실패");
			return;
		}
		// DB 연결
		Connection con = null;

		try {
			con = DriverManager.getConnection(url, user, password);

			String loginId = request.getParameter("loginId");
			String loginPw = request.getParameter("loginPw");
			String loginPwConfirm = request.getParameter("loginPwConfirm");
			String userName = request.getParameter("userName");

			SecSql confirmSql = new SecSql();

			confirmSql.append("SELECT COUNT(*) FROM member");
			confirmSql.append("WHERE loginId = ?", loginId);

			int selectCnt = DBUtil.selectRowIntValue(con, confirmSql);

			if (selectCnt == 1) {
				response.getWriter().append(String.format("<script>alert('이미 존재하는 아이디입니다.'); history.back();</script>"));

				return;
			}
			
			if(loginPw.equals(loginPwConfirm) == false) {
				response.getWriter().append(String.format("<script>alert('비밀번호를 확인해주세요.'); history.back();</script>"));

				return;
			}

			SecSql sql = new SecSql();

			sql.append("INSERT INTO member");
			sql.append("SET regDate = NOW()");
			sql.append(", loginId = ?", loginId);
			sql.append(", loginPw = ?", loginPw);
			sql.append(", userName = ?", userName);

			DBUtil.insert(con, sql);

			response.getWriter().append(String.format(
					"<script>alert('회원가입에 성공하였습니다. 환영합니다! %s님!'); location.replace('../article/list');</script>",
					userName));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

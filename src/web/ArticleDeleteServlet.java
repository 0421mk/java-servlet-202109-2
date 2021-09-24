package web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import web.util.DBUtil;
import web.util.SecSql;

/**
 * Servlet implementation class ArticleListServlet
 */
@WebServlet("/article/delete")
public class ArticleDeleteServlet extends HttpServlet {

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

			HttpSession session = request.getSession();

			int loginedMemberId = -1;

			if (session.getAttribute("loginedMemberId") == null) {
				response.getWriter().append(String.format("<script>alert('권한이 없습니다.'); history.back('');</script>"));

				return;
			} else {
				loginedMemberId = (int) session.getAttribute("loginedMemberId");
			}

			int id = Integer.parseInt(request.getParameter("id"));
			
			SecSql memberSql = new SecSql();
			
			memberSql.append("SELECT COUNT(*) FROM article WHERE memberId = ?", loginedMemberId);
			int memberCnt = DBUtil.selectRowIntValue(con, memberSql);
			
			if ( memberCnt == 0 ) {
				response.getWriter().append(String.format("<script>alert('권한이 없습니다.'); history.back('');</script>"));

				return;
			}
			
			SecSql sql = new SecSql();

			sql.append("DELETE FROM article");
			sql.append("WHERE id = ?", id);

			DBUtil.delete(con, sql);

			response.getWriter()
					.append(String.format("<script>alert('%d번글을 삭제하였습니다.'); location.replace('list');</script>", id));

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

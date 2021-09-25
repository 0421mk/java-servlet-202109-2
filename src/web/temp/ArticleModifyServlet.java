package web.temp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
@WebServlet("/article/temp/modify")
public class ArticleModifyServlet extends HttpServlet {

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
				response.getWriter().append(String.format(
						"<script>alert('권한이 없습니다.'); history.back('');</script>"));
				
				return;
			} else {
				loginedMemberId = (int) session.getAttribute("loginedMemberId");
			}

			SecSql sql = new SecSql();

			int id = Integer.parseInt(request.getParameter("id"));

			sql.append("SELECT * FROM article WHERE id = ?", id);
			Map<String, Object> articleRow = DBUtil.selectRow(con, sql);
			
			if ((int)articleRow.get("memberId") != loginedMemberId) {
				response.getWriter().append(String.format(
						"<script>alert('권한이 없습니다.'); history.back('');</script>"));
				
				return;
			}

			request.setAttribute("articleRow", articleRow);
			request.getRequestDispatcher("/jsp/article/modify.jsp").forward(request, response);
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

}

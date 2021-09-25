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
@WebServlet("/article/temp/list")
public class ArticleListServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		// TODO Auto-generated method stub

		String url = "jdbc:mysql://localhost:3306/java_servlet?serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeBehavior=convertToNull";
		String user = "root";
		String password = "";
			
		String driverName = "com.mysql.jdbc.Driver";
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			System.err.printf("[ClassNotFoundException 예외, %s]\n", e.getMessage());
			response.getWriter().append("DB 드라이버 클래스 로딩 실패");
			return;
		}

		Connection con = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			
			HttpSession session = request.getSession();
			
			int loginedMemberId = -1;

			if (session.getAttribute("loginedMemberId") != null) {
				loginedMemberId = (int) session.getAttribute("loginedMemberId");
			}
			
			SecSql memberSql = new SecSql();
			
			memberSql.append("SELECT * FROM member WHERE id = ?", loginedMemberId);
			Map<String, Object> memberRow = DBUtil.selectRow(con, memberSql);
			
			request.setAttribute("memberRow", memberRow);

			SecSql sql = new SecSql();

			sql.append("SELECT COUNT(*) FROM article");
			int totalPageCnt = (int) DBUtil.selectRowIntValue(con, sql);
			
			int page = 1;
			int countInPage = 10;
			
			totalPageCnt = (int) Math.ceil((double)totalPageCnt / countInPage);
			
			if (request.getParameter("page") != null) {
				page = Integer.parseInt(request.getParameter("page"));
			}
			
			int startPage = (page - 1) * countInPage;
			
			SecSql articleSql = new SecSql();
			
			articleSql.append("SELECT * FROM article LIMIT ?, ?", startPage, countInPage);
			List<Map<String, Object>> articleRows = DBUtil.selectRows(con, articleSql);
			
			request.setAttribute("totalPageCnt", totalPageCnt);
			request.setAttribute("page", page);
			request.setAttribute("articleRows", articleRows);
			
			request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);
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

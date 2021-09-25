package web.temp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
@WebServlet("/article/temp/doWrite")
public class ArticleDoWriteServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		// TODO Auto-generated method stub

		String url = "jdbc:mysql://localhost:3306/java_servlet?serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeBehavior=convertToNull";
		String user = "root";
		String password = "";
		// 커넥터 드라이버 활성화

		String driverName = "com.mysql.cj.jdbc.Driver";
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

			String title = request.getParameter("title");
			String body = request.getParameter("body");
			int memberId = 1;
			String memberName = "admin";

			SecSql sql = new SecSql();

			sql.append("INSERT INTO article");
			sql.append("SET regDate = NOW()");
			sql.append(", title = ?", title);
			sql.append(", body = ?", body);
			sql.append(", memberId = ?", memberId);
			sql.append(", memberName = ?", memberName);
			
			int id = DBUtil.insert(con, sql);

			response.getWriter()
					.append(String.format("<script>alert('%d번글을 작성하였습니다.'); location.replace('list');</script>", id));

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

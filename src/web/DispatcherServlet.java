package web;

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
import web.controller.ArticleController;
import web.util.DBUtil;
import web.util.SecSql;

@WebServlet(urlPatterns = { "/article/*", "/member/*" })
public class DispatcherServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		// TODO Auto-generated method stub

		String requestUri = request.getRequestURI();
		String[] requestUriBits = requestUri.split("/");

		String controllerName = requestUriBits[2];
		String actionMethod = requestUriBits[3];

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

			boolean isLogined = false;
			int loginedMemberId = -1;
			Map<String, Object> memberRow = null;

			// 로그인 되어있다면
			if (session.getAttribute("loginedMemberId") != null) {
				loginedMemberId = (int) session.getAttribute("loginedMemberId");
				isLogined = true;

				SecSql memberSql = new SecSql();

				memberSql.append("SELECT * FROM member WHERE id = ?", loginedMemberId);
				memberRow = DBUtil.selectRow(con, memberSql);
			}

			request.setAttribute("isLogined", isLogined);
			request.setAttribute("loginedMemberId", loginedMemberId);
			request.setAttribute("memberRow", memberRow);

			if (controllerName.equals("article")) {
				ArticleController controller = new ArticleController(request, response, con);

				if (actionMethod.equals("list")) {
					controller.actionList();
				}
				
				if (actionMethod.equals("detail")) {
					controller.pageDetail();
				}

				if (isLogined == false) {
					response.getWriter()
					.append("<script>alert('권한이 없습니다.'); location.replace('list');</script>");
					return;
					
				} else {
					if (actionMethod.equals("write")) {
						controller.pageWrite();
					}

					if (actionMethod.equals("doWrite")) {
						controller.doWrite(memberRow);
					}
					
					if (actionMethod.equals("modify")) {
						controller.pageModify(loginedMemberId);
					}
					
					if (actionMethod.equals("doModify")) {
						controller.doModify(memberRow);
					}
					
					if (actionMethod.equals("delete")) {
						controller.delete(loginedMemberId);
					}
					
				}
			}

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

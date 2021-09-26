package web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.dto.Article;
import web.service.ArticleService;
import web.util.DBUtil;
import web.util.SecSql;

public class ArticleController {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection con;
	private ArticleService articleService;

	public ArticleController(HttpServletRequest request, HttpServletResponse response, Connection con) {
		this.request = request;
		this.response = response;
		this.con = con;

		this.articleService = new ArticleService(request, response, con);
	}

	public void actionList() throws ServletException, IOException {

		int totalPageCnt = articleService.getArticleTotalCnt();
		int page = 1;
		int countInPage = 10;

		totalPageCnt = (int) Math.ceil((double) totalPageCnt / countInPage);

		List<Article> articles = articleService.getArticlesForPrint(page, countInPage);

		request.setAttribute("totalPageCnt", totalPageCnt);
		request.setAttribute("page", page);
		request.setAttribute("articles", articles);

		request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);

	}

	public void pageWrite(boolean isLogined) throws ServletException, IOException {
		
		if (isLogined == false) {
			response.getWriter()
			.append("<script>alert('권한이 없습니다.'); location.replace('list');</script>");
			return;
		}

		request.getRequestDispatcher("/jsp/article/write.jsp").forward(request, response);

	}

	public void doWrite(Map<String, Object> memberRow, boolean isLogined) throws IOException {
		
		if (isLogined == false) {
			response.getWriter()
			.append("<script>alert('권한이 없습니다.'); location.replace('list');</script>");
			return;
		}

		String title = request.getParameter("title");
		String body = request.getParameter("body");
		int memberId = (int) memberRow.get("id");
		String memberName = (String) memberRow.get("userName");

		int id = articleService.insertIntoArticle(title, body, memberId, memberName);

		response.getWriter()
				.append(String.format("<script>alert('%d번글을 작성하였습니다.'); location.replace('list');</script>", id));

	}

	public void pageModify(int loginedMemberId, boolean isLogined) throws ServletException, IOException {
		
		if (isLogined == false) {
			response.getWriter()
			.append("<script>alert('권한이 없습니다.'); location.replace('list');</script>");
			return;
		}

		int id = Integer.parseInt(request.getParameter("id"));

		Article article = articleService.getArticleById(id);

		if ((int) article.memberId != loginedMemberId) {
			response.getWriter().append(String.format("<script>alert('작성자가 아닙니다.'); history.back('');</script>"));

			return;
		}

		request.setAttribute("article", article);
		request.getRequestDispatcher("/jsp/article/modify.jsp").forward(request, response);

	}

	public void doModify(Map<String, Object> memberRow, boolean isLogined) throws IOException {
		
		if (isLogined == false) {
			response.getWriter()
			.append("<script>alert('권한이 없습니다.'); location.replace('list');</script>");
			return;
		}

		String title = request.getParameter("title");
		String body = request.getParameter("body");
		int id = Integer.parseInt(request.getParameter("id"));
		String memberName = (String) memberRow.get("userName");
		int memberId = (int) memberRow.get("id");

		articleService.updateArticle(title, body, id, memberName, memberId);

		response.getWriter()
				.append(String.format("<script>alert('%d번글을 수정하였습니다.'); location.replace('list');</script>", id));

	}

	public void delete(int loginedMemberId, boolean isLogined) throws IOException {
		
		if (isLogined == false) {
			response.getWriter()
			.append("<script>alert('권한이 없습니다.'); location.replace('list');</script>");
			return;
		}

		int id = Integer.parseInt(request.getParameter("id"));

		Article article = articleService.getArticleById(id);

		if ((int) article.memberId != loginedMemberId) {
			response.getWriter().append(String.format("<script>alert('작성자가 아닙니다.'); history.back('');</script>"));

			return;
		}

		articleService.articleDeleteById(id);

		response.getWriter()
				.append(String.format("<script>alert('%d번글을 삭제하였습니다.'); location.replace('list');</script>", id));

	}

	public void pageDetail() throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));

		Article article = articleService.getArticleById(id);

		request.setAttribute("article", article);
		request.getRequestDispatcher("/jsp/article/detail.jsp").forward(request, response);
		
	}

}
package web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.dto.Article;
import web.service.ArticleService;

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
		
		totalPageCnt = (int) Math.ceil((double)totalPageCnt / countInPage);

		List<Article> articles = articleService.getArticlesForPrint(page, countInPage);
		
		request.setAttribute("totalPageCnt", totalPageCnt);
		request.setAttribute("page", page);
		request.setAttribute("articles", articles);
		
		request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);

	}

}

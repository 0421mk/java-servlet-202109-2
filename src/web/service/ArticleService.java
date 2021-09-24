package web.service;

import java.sql.Connection;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.dao.ArticleDao;
import web.dto.Article;

public class ArticleService {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection con;
	private ArticleDao articleDao;
	
	public ArticleService(HttpServletRequest request, HttpServletResponse response, Connection con) {
		this.request = request;
		this.response = response;
		this.con = con;
		
		articleDao = new ArticleDao(request, response, con);
	}

	public int getArticleTotalCnt() {

		return articleDao.getArticleTotalCnt();

	}

	public List<Article> getArticlesForPrint(int page, int countInPage) {

		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		int startPage = (page - 1) * countInPage;
		
		List<Article> articles = articleDao.getArticlesForPrint(startPage, countInPage);

		return articles;
		
	}

}

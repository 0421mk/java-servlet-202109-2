package web.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.dao.ArticleDao;
import web.dto.Article;
import web.util.DBUtil;
import web.util.SecSql;

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

	public int insertIntoArticle(String title, String body, int memberId, String memberName) {
		
		return articleDao.insertIntoArticle(title, body, memberId, memberName);
		
	}

	public void updateArticle(String title, String body, int id, String memberName, int memberId) {
		
		articleDao.updateArticle(title, body, id, memberName, memberId);
		
	}

	public Article getArticleById(int id) {
		
		return articleDao.getArticleById(id);
		
	}

	public void articleDeleteById(int id) {
		
		articleDao.articleDeleteById(id);
		
	}

}

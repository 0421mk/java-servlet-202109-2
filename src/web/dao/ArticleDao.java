package web.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.dto.Article;
import web.util.DBUtil;
import web.util.SecSql;

public class ArticleDao {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection con;

	public ArticleDao(HttpServletRequest request, HttpServletResponse response, Connection con) {
		
		this.request = request;
		this.response = response;
		this.con = con;
		
	}

	public int getArticleTotalCnt() {
		
		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*) FROM article");

		return DBUtil.selectRowIntValue(con, sql);
		
	}

	public List<Article> getArticlesForPrint(int startPage, int countInPage) {
		
		SecSql articleSql = new SecSql();

		articleSql.append("SELECT * FROM article LIMIT ?, ?", startPage, countInPage);
		List<Map<String, Object>> articleRows = DBUtil.selectRows(con, articleSql);
		
		List<Article> articles = new ArrayList<>();
		
		for (Map<String, Object> articleRow : articleRows) {
			articles.add(new Article(articleRow));
		}
		
		return articles;
	}
	
}

package web.dao;

import java.sql.Connection;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.dto.Member;
import web.util.DBUtil;
import web.util.SecSql;

public class MemberDao {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection con;

	public MemberDao(HttpServletRequest request, HttpServletResponse response, Connection con) {
		
		this.request = request;
		this.response = response;
		this.con = con;
		
	}

	public int getMemberCntByLoginId(String loginId) {
		
		SecSql confirmSql = new SecSql();

		confirmSql.append("SELECT COUNT(*) FROM member");
		confirmSql.append("WHERE loginId = ?", loginId);

		int selectCnt = DBUtil.selectRowIntValue(con, confirmSql);
		
		return selectCnt;
		
	}

	public void insertMember(String loginId, String loginPw, String userName) {
		
		SecSql sql = new SecSql();

		sql.append("INSERT INTO member");
		sql.append("SET regDate = NOW()");
		sql.append(", loginId = ?", loginId);
		sql.append(", loginPw = ?", loginPw);
		sql.append(", userName = ?", userName);

		DBUtil.insert(con, sql);
		
	}

	public Member getMemberByLoginId(String loginId) {
		
		SecSql confirmSql = new SecSql();
		
		confirmSql.append("SELECT * FROM member");
		confirmSql.append("WHERE loginId = ?", loginId);

		Map<String, Object> memberRow = DBUtil.selectRow(con, confirmSql);
		
		//처음 null로 선언
		Member member = null;
		
		// null pointer exception 방지
		if(memberRow != null) {
			member = new Member(memberRow);
		}
		
		return member;
	}

}

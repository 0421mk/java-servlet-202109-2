package web.dto;

import java.util.Map;

public class Member {
	public int id;
	public String regDate;
	public String loginId;
	public String loginPw;
	public String userName;
	
	public Member(Map<String, Object> row) {	
		this.id = (int)row.get("id");
		this.regDate = (String)row.get("regDate");
		this.loginId = (String)row.get("loginId");
		this.loginPw = (String)row.get("loginPw");
		this.userName = (String)row.get("userName");
	}
}

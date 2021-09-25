<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="web.dto.Article"%>
<%
Article article = (Article) request.getAttribute("article");
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 상세페이지</title>
</head>
<body>
	<h1>게시물 상세페이지</h1>
	<div>
		<ul>
			<li>글 번호 : <%=(int) article.id%></li>
			<li>날짜 : <%=(String) article.regDate%></li>
			<li>작성자 : <%=(String) article.memberName%></li>
			<li>제목 : <%=(String) article.title%></li>
			<li>내용 : <%=(String) article.body%></li>
			<li><a href="modify?id=<%=(int) article.id%>">수정</a> <a
				href="delete?id=<%=(int) article.id%>">삭제</a></li>
		</ul>
		<div>
			<a href="list">리스트로 돌아가기</a>
		</div>
	</div>

</body>
</html>
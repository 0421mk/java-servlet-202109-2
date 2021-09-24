<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%
Map<String, Object> articleRow = (Map<String, Object>) request.getAttribute("articleRow");
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
			<li>글 번호 : <%=(int) articleRow.get("id")%></li>
			<li>날짜 : <%=(String) articleRow.get("regDate")%></li>
			<li>작성자 : <%=(String) articleRow.get("memberName")%></li>
			<li>제목 : <%=(String) articleRow.get("title")%></li>
			<li>내용 : <%=(String) articleRow.get("body")%></li>
			<li><a href="modify?id=<%=(int) articleRow.get("id")%>">수정</a> <a
				href="delete?id=<%=(int) articleRow.get("id")%>">삭제</a></li>
		</ul>
		<div>
			<a href="list">리스트로 돌아가기</a>
		</div>
	</div>

</body>
</html>
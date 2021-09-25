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
<title>게시물 수정</title>
</head>
<body>
	<h1>게시물 수정</h1>
	<div>
		<div>
			<form action="doModify" method="POST">
				<input type="hidden" value="<%=(int) article.id%>"
					name="id" />
				<div>
					<input type="text" name="title"
						value="<%=(String) article.title%>" />
				</div>
				<div>
					<textarea name="body" cols="30" rows="10"><%=(String) article.body%></textarea>
				</div>
				<input type="submit" value="수정하기" />
			</form>
		</div>
		<br />
		<div>
			<a href="list">리스트로 돌아가기</a>
		</div>
	</div>

</body>
</html>
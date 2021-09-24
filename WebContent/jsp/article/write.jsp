<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 작성</title>
</head>
<body>
	<h1>게시물 작성</h1>
	<div>
		<div>
			<form action="doWrite" method="POST">
				<div>
					<input type="text" name="title" placeholder="제목을 입력해주세요." />
				</div>
				<div>
					<textarea name="body" cols="30" rows="10" placeholder="내용을 입력해주세요."></textarea>
				</div>
				<input type="submit" value="작성하기" />
			</form>
		</div>
		<br />
		<div>
			<a href="list">리스트로 돌아가기</a>
		</div>
	</div>

</body>
</html>
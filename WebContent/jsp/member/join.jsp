<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script>
		var submitDone = false;
		
		function submitCheck(form) {
			if(submitDone) {
				alert('처리중 입니다.');
				return;
			}
			
			if(form.loginId.value == "") {
				alert("아이디를 입력해주세요.");
				form.loginId.focus();
				
				return;
			}
			
			if(form.loginPw.value == "") {
				alert("비밀번호를 입력해주세요.");
				form.loginPw.focus();
				
				return;
			}
			
			if(form.loginPwConfirm.value == "") {
				alert("비밀번호 확인을 입력해주세요.");
				form.loginPwConfirm.focus();
				
				return;
			}
			
			if(form.userName.value == "") {
				alert("이름을 입력해주세요.");
				form.userName.focus();
				
				return;
			}
			
			submitDone = true;
			form.submit();
		}	
	</script>
	<h1>회원 가입</h1>
	<div>
		<div>
			<form action="doJoin" method="POST"
				onsubmit="submitCheck(this); return false;">
				<div>
					<input type="text" name="loginId" placeholder="아이디를 입력해주세요." />
				</div>
				<div>
					<input type="password" name="loginPw" placeholder="비밀번호를 입력해주세요." />
				</div>
				<div>
					<input type="password" name="loginPwConfirm" placeholder="비밀번호 확인을 입력해주세요." />
				</div>
				<div>
					<input type="text" name="userName" placeholder="이름을 입력해주세요." />
				</div>
				<input type="submit" value="회원가입" />
			</form>
		</div>
		<br />
		<div>
			<a href="../article/list">리스트로 돌아가기</a>
		</div>
	</div>
</body>
</html>
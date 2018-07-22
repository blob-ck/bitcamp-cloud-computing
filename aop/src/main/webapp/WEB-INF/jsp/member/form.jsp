<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>멤버 등록</title>
</head>
<body>

<h1>멤버 등록</h1>
<!--
form.jsp가 존재하는 현재 폴더 = app/member/form.jsp
같은 폴더에 존재하므로 경로 복잡하게 할 필요 없음
 -->
<form action="add" method="post">
<table border='1'>
<tr>
    <th>아이디</th><td><input type="text" name="id"></td>
</tr>
<tr>
    <th>이메일</th><td><input type="email" name="email"></td>
</tr>
<tr>
    <th>암호</th><td><input type="password" name="password"></td>
</tr>
</table>
<button>등록</button>
</form>
<a href='list'>목록</a>
</body>
</html>
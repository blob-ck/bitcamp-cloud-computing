<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>멤버 목록</title>
</head>
<body>
<h1>멤버 목록</h1>
<p><a href='form.html'>새회원</a></p>
<c:choose>
	<c:when test="${list == null}">
	   <p>등록된 회원이 없습니다.</p>
	</c:when>
	<c:otherwise>
		<table border='1'>
		<tr>
		    <th>아이디</th><th>이메일</th>
		</tr>
		<c:forEach items='${list}' var="member">
			<tr>
			    <td><a href='view?id=${member.id}'>${member.id}</a></td><td>${member.email}</td>
			</tr>
		</c:forEach>
		</table>
	</c:otherwise>
</c:choose>
</body>
</html>

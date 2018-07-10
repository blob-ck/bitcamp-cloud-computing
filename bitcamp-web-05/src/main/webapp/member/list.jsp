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
<h1>멤버 목록 in jsp + jstl + EL</h1>
<p><a href='form.html'>새회원</a></p>
<table border='1'>
<tr>
    <th>아이디</th><th>이메일</th>
</tr>

<%-- <jsp:useBean id="list" 
type="java.util.List<bitcamp.pms.domain.Member>"
scope="request"></jsp:useBean> --%>

<%-- <%
//List<Member> list = (List<Member>)request.getAttribute("list");
for(Member member : list){
%>
<tr>
    <td><a href='view?id=<%=member.getId() %>'><%=member.getId() %></a></td>
    <td><%=member.getEmail() %></td>
</tr>
<%} %> --%>

<c:forEach items="${list}" var="member">
<tr>
<%-- ${member.getId() EL의 간편 기능을 사용하면 이렇게도 된다 ${member.id} --%>
    <td><a href='view?id=${member.getId()}'>${member.id}</a></td>
    <td>${member.email}</td>
</tr>
</c:forEach>


</table>
</body>
</html>

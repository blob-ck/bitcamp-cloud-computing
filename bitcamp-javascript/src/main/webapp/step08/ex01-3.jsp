<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
 %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

${param.a} ${param.op} ${param.b} = 
<c:choose>
    <c:when test="${param.op == '+'}">${param.a + param.b}</c:when>
    <c:when test="${param.op == '-'}">${param.a - param.b}</c:when>
    <c:when test="${param.op == '*'}">${param.a * param.b}</c:when>
    <c:when test="${param.op == '/'}">${param.a / param.b}</c:when>
    <c:otherwise>해당 연산을 지원하지 않습니다.</c:otherwise>
</c:choose>
<%
//아직 버퍼에 저장만 된 상태이므로(flush 아직 안됨) 쓰레드 10초 후에 응답하게 된다
Thread.currentThread().sleep(3000); //10초 후에 실행됨
%>
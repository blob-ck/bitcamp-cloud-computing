<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="my-css.jsp"%>
<meta charset='UTF-8'>
<title>실행 오류!</title>
</head>
<body>
<h1>오류 발생!</h1>
<pre>
<%
Throwable error = (Throwable)request.getAttribute("error");

//out은 jsp의 out객체이므로, 한번 변환해서 넘겨준다 - jsp out -> servlet out (printwriter)
error.printStackTrace(new PrintWriter(out));
%>
</pre>
</body>
</html>

<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>오류 발생!</title>
</head>
<body>
<h1>오류 내용</h1>
<%
Throwable error = (Throwable)request.getAttribute("error");
error.printStackTrace(new PrintWriter(out));
%>

</body>
</html>

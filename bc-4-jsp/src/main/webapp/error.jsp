<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ERROR 발생!</title>
</head>
<h1>Error 발생~!</h1>
<body>
<%
Throwable error = (Throwable)request.getAttribute("error");
error.printStackTrace(new PrintWriter(out));
%>
</body>
</html>
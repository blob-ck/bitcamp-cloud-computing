<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- <meta http-equiv='Refresh' content='10;url=list'> -->
<title>오류 발생!</title>
</head>
<body>
<p>EL 오류메시지 출력</p>
${error}
<p>==============</p>
<p>Throw 오류메시지 출력</p>
<% 
Throwable error = (Throwable)request.getAttribute("error"); 
error.printStackTrace(new PrintWriter(out));
%>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 업로드3 - Spring</title>
</head>
<h1>파일 업로드3 - Spring WebMVC File Upload</h1>
<body>
이름 : ${name} <br>
나이 : ${age} <br>
사진 : ${newfilename} <br>
<img src="../files/${newfilename}">
<img id="img1">
<script>
'use strict'
setTimeout(() => {
    document.getElementById('img1').src = '../files/${newfilename}';
}, 5000);
</script>
</body>
</html>
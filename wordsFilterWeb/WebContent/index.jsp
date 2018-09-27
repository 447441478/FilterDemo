<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		<h1>一些文章...</h1>
		<h1>一些文章...</h1>
		<hr>
		<form action="<c:url value='/NoteServlet'/>" method="post">
			用户名：<input type="text" name="name"/><br/>
			评论：<textarea name="note" rows="10" cols="20"></textarea>
			<input type="submit" value="发表" />
		</form> 
	</body>
</html>
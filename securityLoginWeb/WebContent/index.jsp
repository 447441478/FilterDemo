<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>FilterWeb</title>
	</head>
	<body>
		<form action="<c:url value='/login'/>" method="post">
			name:<input type="text" name="name" /><br />
			<input type="submit" value='登录' />
		</form>
		<br/>
		<a href="<c:url value='/jsps/show.jsp'/>">走你</a> <br/>
	</body>
</html>
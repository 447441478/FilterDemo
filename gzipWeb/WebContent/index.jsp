<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		<a href="<c:url value='/OneServlet'/>">压缩字节数据</a><br/>
		<a href="<c:url value='/TwoServlet'/>">压缩字符数据</a><br/>
		<a href="<c:url value='/jsps/show.jsp'/>">既有字符又有字节的jsp</a><br/>
	</body>
</html>
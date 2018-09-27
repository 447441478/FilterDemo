<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>自动登录</title>
		<style type="text/css">
			label:hover , input[type=radio]:hover {
				cursor: pointer;
			}
		</style>
	</head>
	<body>
		<!-- 当用户没登录就显示表单进行登录 -->
		<c:if test="${empty sessionScope.user}" var="boo">
			<h1>用户登录</h1>
			<font color="red">${sessionScope.error}</font>
			<form action="<c:url value='/LoginServlet'/>" method="post">
				用户名：<input name="name"/><br/>
				密&emsp;码：<input type="password" name="pwd"/><br/>
				<label><input type="radio" name="time" value='0' checked="checked" />不自动登录</label>	&emsp;		
				<label><input type="radio" name="time" value='1' />1天</label>&emsp;				
				<label><input type="radio" name="time" value='7' />1周</label>&emsp;	<br/>
				<input type="submit" value="登录"/>
			</form>
		</c:if>
		<!-- 当用户登录就显示用户相关信息 -->
		<c:if test="${!boo}">
			欢迎您，${sessionScope.user}<br/>
			<a href="<c:url value='/jsps/show.jsp'/>">查看信息</a><br/>
			<a href="<c:url value='/CancelAutoLoginServlet'/>">退出自动登录</a>
		</c:if>
	</body>
</html>
<%@page import="eduportal.model.*"%>
<%@page import="eduportal.dao.entity.*"%>
<%@page import="eduportal.dao.*"%>
<%@page import="eduportal.api.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UserEditor</title>
</head>
<body>
	<%
		UserEntity admin = null;
		for (Cookie c : request.getCookies()) {
			if (c.getName().equals("sesToken")) {
				admin = AuthContainer.getUser(c.getValue());
			}
		}
		
	%>

</body>
</html>
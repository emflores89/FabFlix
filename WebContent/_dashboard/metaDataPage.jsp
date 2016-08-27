<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>MetaData</title>
		<link href="${pageContext.request.contextPath}/css/dashboardStyle.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/css/metaStyle.css" rel="stylesheet" type="text/css" />
	</head>
	
	<div class = "title">
        <h1>FabFlix Management</h1>
    </div>
	
	<nav>
        <a class="active" href="${pageContext.request.contextPath}/_dashboard/main.jsp">Home</a> |
        <a href="${pageContext.request.contextPath}/_dashboard/insertStar.jsp">Add Star</a> |
        <a href="${pageContext.request.contextPath}/_dashboard/addMovie.jsp">Add Movie</a> |
        <a href="${pageContext.request.contextPath}/MetaDataServlet">Metadata</a> |
        <a href="${pageContext.request.contextPath}/EmployeeLogoutServlet">Logout</a>
    </nav>
	
	<body>
		<c:forEach items="${results}" var="current">
			<h2>Table: ${current.key}</h2>		
			<table>
				<c:forEach items="${current.value}" var="item">
				<tr>
					<td>${item.var1}</td>
					<td>${item.var2}</td>
					<td>${item.var3}</td>
					<c:if test="${not empty item.var4}">
						<td>${item.var4}</td>
					</c:if>
					<c:if test="${not empty item.var5}">
						<td>${item.var5}</td>
					</c:if>
				</tr>
				</c:forEach>
			</table>
		</c:forEach>

	</body>
</html>
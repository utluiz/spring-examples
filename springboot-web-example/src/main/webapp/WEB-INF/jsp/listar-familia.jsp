<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Dino Family JSP</title>
</head>
<body>

<h1>Dino Family</h1>

<ul>
	<c:forEach items="${family}" var="nome">
		<li>${nome}</li>
	</c:forEach>
</ul>

</body>
</html>
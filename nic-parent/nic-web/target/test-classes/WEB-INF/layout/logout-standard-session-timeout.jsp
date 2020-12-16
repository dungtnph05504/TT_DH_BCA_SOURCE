<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/login_style.css"/>"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/common.css"/>"></link>
	<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/jquery.contextMenu.css"/>"></link>
	<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-1.9.1.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-ui.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/tools.js"/>"></script>
	<script type="text/javascript"
	src="<c:url value="/resources/js/common.js"/>"></script>
</head>
<body>
<div id="main">
<div id="content_main">
<tiles:insertTemplate template="/WEB-INF/layout/header.jsp" />
<tiles:insertTemplate template="/WEB-INF/views/logout_session_timeout.jsp" />
</div>
</div>
</body>
</html>
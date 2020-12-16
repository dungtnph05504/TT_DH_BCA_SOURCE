<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>MNID System</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/common.css"/>"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/flexigrid.css"/>"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/style.css"/>"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/jquery-ui.css"/>"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/menuStyleIE7.css"/>"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/jquery-ui-timepicker-addon.css" />"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/jquery.contextMenu.css"/>"></link>
<link href="<c:url value="/resources/css/SpryTabbedPanels.css"/>" rel="stylesheet" type="text/css" /> <!-- Tabs css -->
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-1.9.1.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-ui.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/tools.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/flexigrid.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/menu.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/common.js"/>"></script>
<!--  <script type=text/javascript src="<c:url value="/resources/js/nricApplet.js"/>"></script>-->
<script type="text/javascript"
	src="<c:url value="/resources/js/ajaxFormValidator.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/errorBinder.js" />"></script>
<!-- <script src="<c:url value="/resources/js/mouseover_image.js"/>" type="text/javascript"></script> -->
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-ui-timepicker-addon.js" />"></script> 
<script type=text/javascript
	src="<c:url value="/resources/js/jquery.contextMenu.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/SpryTabbedPanels.js"/>"></script> 
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name=GENERATOR content="MSHTML 8.00.7600.16535" />
<!--[if gte IE 9]>
  			<style type="text/css">
    			.gradient {
       			filter: none;
    			}
  			</style>
		<![endif]-->
</head>
<body>
	<!--   <div id=shadow-container> -->
	<div class=shadow1>
		<div class=shadow2>
			<div class=shadow3>
				<div class=container style="background-color: #DDDDDD;">
					<tiles:insertTemplate template="/WEB-INF/layout/header.jsp" /> 
					<!-- Dynamic body, should be set for UI pages -->
					<div style="height: 850px; background-color: #DDDDDD;"> 
						<tiles:insertAttribute name="transaction" />
						<tiles:insertAttribute name="body" />
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--  </div> -->
	<tiles:insertTemplate template="/WEB-INF/layout/footer.jsp" />
</body>
</html>
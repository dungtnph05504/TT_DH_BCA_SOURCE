<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
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
<div class=header>
	<table style="border: none; width: 100%">
		<tbody>
			<tr>
				<td rowSpan=2 width="0%">
					<!--IMG src="img/department_logo.gif" alt="DEPARTMENT LOGO"-->
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tbody>
							<tr>
								<td rowSpan="2" width="25%" style="padding: 6px"><img
									src="<c:url value="/resources/img/logo.jpg"/>"
									alt="SYSTEM LOGO" height="120px"></td>
								<!-- height="60px" width="210px" -->
								<!-- <td height="20px"></td>
         						<td width="120px"></td> -->
								<td width="50%">
									<div
										style="font-family: Verdana, Geneva, sans-serif; font-size: 20px; font-weight: bold; text-align: center; color: #191970;"
										id="page_heading">ERROR PAGE</div>
								</td>
								<td width="25%" align="right" class="displayDate"
									style="padding-right: 10px"><c:if
										test="${not empty sessionScope.userSession}">
		  								Good Day ${sessionScope.userSession.firstName} 
		  							</c:if> <br /> <c:if test="${not empty sessionScope.userSession}">
		  							Logged In As : ${sessionScope.userSession.userName}
		  							</c:if> <br /> <br /> <script>
												var myVar = setInterval(
														function() {
															myTimer()
														}, 1000);
												function myTimer() {
													var today = getCurrentDisplayDate();
													document
															.getElementById("timeDisplay").innerHTML = today;
												}
											</script>
									<div id="timeDisplay" style="font-size: 14px;">&nbsp;</div> <br />
									<c:if test="${not empty sessionScope.userSession}">
										<span id="workStationDisplay" style="font-size: 12px;">Workstation
											Id: ${sessionScope.userSession.workstationId}</span>
									</c:if></td>
								<td width="1px">&nbsp;</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<div id="dividerRed" class="displayOn">&nbsp;</div>

<br />

<tiles:insertTemplate template="/WEB-INF/views/pageNotFound.jsp" />
</div>
</div>
</body>
</html>
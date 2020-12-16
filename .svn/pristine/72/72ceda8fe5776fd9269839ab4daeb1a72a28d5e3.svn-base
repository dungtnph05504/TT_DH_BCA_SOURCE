<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="-1" />
<title>EPP System</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/flexigrid.css"/>"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/style-nic.css"/>"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/jquery-ui.css"/>"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/menuStyleIE7.css"/>"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/jquery-ui-timepicker-addon.css" />"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/jquery.contextMenu.css"/>"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/SpryTabbedPanels.css"/>"></link>
 <link rel="stylesheet" type="text/css" 
 href="<c:url value="/resources/css/tutorsty.css"/>"></link> <!-- jQuery scrollbar -->
 <link rel="stylesheet" type="text/css" 
   href="<c:url value="/resources/css/flexcrollstyles.css"/>"></link> <!-- jQuery scrollbar -->
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/display-style.css"/>"></link>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/jquery-confirm.css/>"></link>		
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mouseover_image.css"/>"></link>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-1.9.1.js"/>"></script>
<script type="text/javascript"
src="<c:url value="/resources/js/jquery-confirm.js"/>"></script>	
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-ui.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/tools.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/flexigrid.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.number.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/menu.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/common.js"/>"></script>
<!--  <script type=text/javascript src="<c:url value="/resources/js/nricApplet.js"/>"></script>-->
<script type="text/javascript"
	src="<c:url value="/resources/js/ajaxFormValidator.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/errorBinder.js" />"></script>
 <script src="<c:url value="/resources/js/mouseover_image.js"/>" type="text/javascript"></script> 
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-ui-timepicker-addon.js" />"></script>
<script type='text/javascript' src="<c:url value="/resources/js/flexcroll.js" />"></script> <!-- jQuery scrollbar -->	
<script type=text/javascript src="<c:url value="/resources/js/jquery.contextMenu.js" />"></script>
<script type=text/javascript src="<c:url value="/resources/js/SpryTabbedPanels.js" />"></script>
<script type=text/javascript src="<c:url value="/resources/js/frameBreaker.js" />"></script>
<script src="<c:url value="/resources/js/jQueryRotate.js"/>" type="text/javascript"></script>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<c:url var="logoutWithSessionTimeoutUrl" value="/servlet/user/logoutWithSessionTimeout" />
<c:url var="logoutUrl" value="/servlet/user/logout" />
<c:url var="pingServerUrl" value="/servlet/user/pingServer" />

<meta name=GENERATOR content="MSHTML 8.00.7600.16535" />
<!--[if gte IE 9]>
  			<style type="text/css">
    			.gradient {
       			filter: none;
    			}
  			</style>
		<![endif]-->
		

<script>
var user = '${sessionScope.userSession.userId}';
var sessionTimeout = 5;	
var needStop = false;

$(function() {
	
	var x;
	setInterval(function() {
	if(x == 0) {
	$('.blinking').removeAttr('style');
	 x = 1;
	} else  {
	if(x = 1) {
	$('.blinking').css('color', 'red');
	 x = 0;
	 }
	}
	}, 500);
	$("#dialog-continueSession").dialog({
		modal : true,
		autoOpen : false,
		width : 300,
		height : 200,
		title : 'Session Timeout Alert',
		closeOnEscape : false,
		open : function() {
			sessionTimeout=2;
			needStop = false;
			DisplaySessionTimeout();
		},
		show : {
			effect : "blind",
			duration : 1000
		},
		hide : {
			duration : 1000
		},
		buttons : {
			'Ok' : function() {
				sessPingServer();
				needStop = true;
				$(this).dialog('close');
			},
			'Logout' : function() {
				redirect();
				$(this).dialog('close');

			}
		}
	});

	if (user != null && user != '') {
		setInterval(function() {
			$("#dialog-continueSession").dialog('open');
		},30000000);
	}
});

function DisplaySessionTimeout() {
	sessionTimeout = sessionTimeout - 1;
	if (!needStop) {
		if (sessionTimeout >= 0) {
			window.setTimeout("DisplaySessionTimeout()", 60000);
		} else {
			redirectForSessionTimeout();
		}
	}
}

function redirectForSessionTimeout() {
	needStop = true;
	var url = '${logoutWithSessionTimeoutUrl}';
	window.location.href = url;
}

function redirect() {
	needStop = true;
	var url = '${logoutUrl}';
	window.location.href = url;
}


function sessPingServer(form) {
	var ajaxUrl = '${pingServerUrl}';
	$.ajax({
		url : ajaxUrl,
		cache : false,
		success : function(data) {
			//TODO put any logic required here
		},
		error : function(e) {
			redirectForSessionTimeout();
		}
	});
}

</script>

</head>

<body>
	<!--   <div id=shadow-container> -->
	<div class=shadow1>
		<div class=shadow2>
			<div class=shadow3>
				<!--<div class=container style="background-color: #DDDDDD;">
					-->   
					<div style="height: auto;">
						<tiles:insertAttribute name="body" />
					</div>
				</div>
			</div>
		</div>
	
	<div style="height: auto;"> 
	</div>
	
	
	
	<div id="dialog-continueSession" style="display: none;">
		<table>
			<tr>
				<td><img src="<c:url value="/resources/images/question.jpg"/>" height="40px" width="40px"></td>
				<td width="10px">&nbsp;</td>
				<td><span class="blinking"><b>Do You want to continue the session?</b></span></td>
			</tr>
		</table>
	</div>
	
	
	<tiles:insertTemplate template="/WEB-INF/layout/header-empty.jsp" />
</body>
</html>
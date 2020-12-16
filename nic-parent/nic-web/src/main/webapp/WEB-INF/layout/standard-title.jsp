<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="vn" lang="vn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />

 <title><tiles:insertAttribute name="title" /></title>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"></link> 
<script type="text/javascript" src="<c:url value="/resources/js/jquery.min.js"/>"></script>
<script src="http://code.highcharts.com/modules/exporting.js"></script>
<%-- <script type="text/javascript" src="<c:url value="/resources/js/exporting.js"/>"></script>	
<script type="text/javascript" src="<c:url value="/resources/js/offline-exporting.js"/>"></script>  --%>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/flexigrid.css"/>"></link>
<script type="text/javascript" src="<c:url value="/resources/js/flexigrid.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jquery-confirm.css"/>"></link>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-confirm.js"/>"></script>				
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/stylesheet.css"/>"></link>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style-nic.css"/>"></link>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jquery-ui.css"/>"></link>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/menuStyleIE7.css"/>"></link>
<%-- <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jq href="<c:url value="/resources/css/jquery.contextMenu.css"/>"></link> --%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/SpryTabbedPanels.css"/>"></link>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tutorsty.css"/>"></link> <!-- jQuery scrollbar -->
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/flexcrollstyles.css"/>"></link> <!-- jQuery scrollbar -->
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/display-style.css"/>"></link>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/highcharts.css"/>"></link>
<script type="text/javascript" src="<c:url value="/resources/js/highcharts.js"/>"></script>	
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mouseover_image.css"/>"></link>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/sumoselect.css"/>"></link>	
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/sumoselect.min.css"/>"></link>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap-datetimepicker.min.css"/>"></link>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap-toggle.min.css"/>"></link>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"></link>
<%-- <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.9.1.js"/>"></script> --%>
<script type="text/javascript" src="<c:url value="/resources/js/tools.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.number.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/menu.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/common.js"/>"></script>
<!--  <script type=text/javascript src="<c:url value="/resources/js/nricApplet.js"/>"></script>-->
<script type="text/javascript" src="<c:url value="/resources/js/ajaxFormValidator.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/errorBinder.js" />"></script>
<script src="<c:url value="/resources/js/mouseover_image.js"/>" type="text/javascript"></script> 
<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui-timepicker-addon.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/flexcroll.js" />"></script> <!-- jQuery scrollbar -->	
<script type="text/javascript" src="<c:url value="/resources/js/jquery.contextMenu.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/SpryTabbedPanels.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/frameBreaker.js" />"></script>
<script src="<c:url value="/resources/js/jQueryRotate.js"/>" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.mask.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/charset.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.sumoselect.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.sumoselect.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap-datetimepicker.min.js" />"></script> 
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap-toggle.min.js" />"></script> 

 <script src="/resources/style1/js/moment.min.js"></script>
 <link href="<c:url value="/resources/style1/css/jquery-ui.css" />" rel="stylesheet"> </link>
 <link href="<c:url value="/resources/style1/plugins/bootstrap-4.0.0/css/bootstrap.css" />" rel="stylesheet"> </link>
 <link href="<c:url value="/resources/style1/css/font-awesome.min.css" />" rel="stylesheet"> </link>
 <link href="<c:url value="/resources/style1/css/bootstrap-modal.css" />" rel="stylesheet"> </link>
 <link href="<c:url value="/resources/style1/css/bootstrap-table.css" />" rel="stylesheet"> </link>
 <link href="<c:url value="/resources/style1/css/general.css" />" rel="stylesheet"> </link>
 <link href="<c:url value="/resources/style1/css/style-table.css" />" rel="stylesheet"> </link>
 <link href="<c:url value="/resources/style1/css/bootstrap-select.css" />" rel="stylesheet"> </link>
 <link href="<c:url value="/resources/style1/css/toastr.css" />" rel="stylesheet"> </link>
 <link href="<c:url value="/resources/style1/css/Layout.css" />" rel="stylesheet"> </link>
 <link href="<c:url value="/resources/style1/css/simple-sidebar.css" />" rel="stylesheet"> </link>
 <link href="<c:url value="/resources/style1/css/style.css" />" rel="stylesheet"> </link>
 <link href="<c:url value="/resources/style1/css/zTreeStyle.css" />" rel="stylesheet"> </link>
<script src="<c:url value="/resources/style1/js/tableHeadFixer.js"/>"></script>
<script src="<c:url value="/resources/style1/js/dataTables.bootstrap.min.js" />"></script>


<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/datatables.min.css"/>"></link>
<script type="text/javascript" src="<c:url value="/resources/js/datatables.min.js" />"></script>   
<script type="text/javascript" src="<c:url value="/resources/js/jquery.twbsPagination.js" />"></script> 
<script type="text/javascript" src="<c:url value="/resources/js/notify.js"/>"></script>
<script src="<c:url value="/resources/style1/js/moment-with-locales.js" />"></script>



<link href="/resources/css/treeview/tree.css" rel="stylesheet">
<script src="/resources/js/treeview/tree.js" type="text/javascript"></script>
<script src="/resources/js/treeview/treeitem.js" type="text/javascript"></script>
<script src="/resources/js/treeview/treeitemClick.js" type="text/javascript"></script>
<%-- <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/datatables.min.css"/>"></link>
<script type=text/javascript src="<c:url value="/resources/js/datatables.min.js" />"></script>   
<script type=text/javascript src="<c:url value="/resources/js/jquery.twbsPagination.js" />"></script> 
<script type="text/javascript" src="<c:url value="/resources/js/notify.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/notify.min.js"/>"></script> --%>
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
<style>
.dataTables_wrapper {
	padding: 10px !important;
} 


.dataTables_filter {
	display: none !important;
}
.new-heading-2 {
	font-family: arial, helvetica, sans-serif;
    font-weight: bold;
    overflow: hidden;
    text-align: center;
    margin-bottom: 10px;
    border-bottom: 1px solid #d6d6d6;
    padding: 10px 20px 5px;
    font-size: 20px;
    color: #4A6277;
    position: relative;
} 
.new-heading-3 {
	padding-top: 10px;
    color: #000;
    font-family: arial, helvetica, sans-serif;
    font-size: 16px;
    font-weight: bold;
    overflow: hidden;
    text-align: center;
    margin-bottom: 10px;
    border-bottom: 1px solid #d6d6d6;
}
.form-desing {
	padding-left: 20px;padding-right: 20px;background-color: #fff;margin-bottom: 60px;
}
.fix-paging {
	height: 17px;
    padding-top: 1px;
}
fieldset {
    padding: .35em .625em .75em;
    margin: 0 2px;
    border: 1px solid #c0c0c0;
}
legend {
	display: block;
    width: auto;
    padding-left: 10px;
    padding-right: 10px;
    margin-bottom: 0px;
    font-size: 16px;
    line-height: inherit;
    color: #333;
    border: 0;
    border-bottom: 0px solid #e5e5e5;
    font-weight: 600;
}

.style-no-record {
	text-align: center;
	color: rgb(144, 148, 156);
	font-size: 16px;
	font-weight: bold;
}

.back-gr td {
	background-color: #7fae46;
	color: #fff;
}
div.SumoSelect {
	width: 100%;
}
</style>		

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
		/* setInterval(function() {
			$("#dialog-continueSession").dialog('open');
		},1800000); */
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
	<tiles:insertTemplate template="/WEB-INF/layout/menu.jsp" />
	<!-- <div id=shadow-container> -->
	<div id="flexbox">
						<div id="wrapper" class="main-content">
							<div class="content-person-infomation">
							<div class="content-person-item container-fluid">
			
				<!-- Dynamic body, should be set for UI pages -->
				<div style="height: auto;background-color: #fff;">
					<tiles:insertAttribute name="queue" />
					<tiles:insertAttribute name="transaction" />
					<tiles:insertAttribute name="body" />
			
				</div>

					<!--<div id="dialog-continueSession" style="display: none;">
						<table>
							<tr>
								<td><img src="<c:url value="/resources/images/question.jpg"/>" height="40px" width="40px"></td>
								<td width="10px">&nbsp;</td>
								<td><span class="blinking"><b>Bạn có muốn tiếp tục phiên làm việc?</b></span></td>
							</tr>
						</table>
					</div>-->
				</div>
				
				<div class="col-sm-12 main-footer">
    <div class="form-group">
        <div class="col-sm-5 text-left none-padding mar-top-15">
            Bản quyền © 2017 Cục Quản lý xuất nhập cảnh - Bộ Công an
        </div>
        <div class="col-sm-2">

        </div>
        <div class="col-sm-5 text-center  none-padding mar-top-5">
            
        </div>
    </div>
</div>
			</div>
		</div>
		</div>
</body>

</html>
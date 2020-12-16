<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<div id="main">
	<DIV class=content>
		<A id=top href="http://203.127.251.87:9082/nric/servlet/"></A>
		<DIV id=message>
			<!-- Model.errorList -->
		</DIV>
	</DIV>
	<BR>

	<div class="content_main">
		<!--<form id="registrationModel" class="inline" action="/nric/servlet/registration" method="post">
-->
		<div id="global_icon_div"></div>
		<div id="global_error_div"></div>
		<br />

		<div class="border_success">
			<!--<div class="success"> -->
			<div class="success_left">
				<img align='left'
					src="<c:url value="/resources/images/success.jpg" />" width="30"
					height="30" />

			</div>
			<div class="success">
				<table width="800" height="62" border="0" cellpadding="5">
					<tr>
						<span style="font-size: 20px; font-weight: bold; color: #000;">&nbsp;Investigation
							Status</span>
						<td width="587" height="28"
							style="font-size: 18px; font-weight: bold; color: #000;">
							<p class="text_pad">
								&nbsp;Successfully Processed Investigation Job with Job Id:
								<c:out value="${approveJobId}" />
							</p>
							<p class="text_pad"
								style="font-size: 12px; font-weight: normal; color: #000;">&nbsp;Transaction
								approved for Card Production.</p>
						</td>
					</tr>
				</table>
			</div>

		</div>



<br />
<br />
<br />
<br />
<br />
<br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
	</div>
</div>
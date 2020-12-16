<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jquery-ui.css"/>"></link>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mouseover_image.css"/>"></link>
<c:url var="actionURL" value="/servlet/user/createUserDetails"/>
<c:set var="appletURL" value="/epid-admin/applets/"/>
<c:url var="urlGetBranch" value="/servlet/user/ajax/getBranch"/>
<c:url var="userJobListUrl" value="/servlet/adminConsole/userManagement" />

<script type="text/javascript">

</script>

<!-- jquery dialog box script for save button -->
<script> 
  function submitFpEnroll(){		
		try {
			var userIdVal=document.getElementById('businessId.field').value;
			var userNameVal=document.getElementById('firstName').value;
			var emailVal=document.getElementById('email.field').value;						
			var EppApplet=document.EppApplet;			
			EppApplet.setDebug(true);
			var result = EppApplet.fingerprintEnrollment(userIdVal, userNameVal, emailVal);						
			if(result===true){				
				document.forms["CreateUserDetailsForm"].action = '${userJobListUrl}';
				document.forms["CreateUserDetailsForm"].submit();
			}else if(result=='true'){				
				document.forms["CreateUserDetailsForm"].action = '${userJobListUrl}';
				document.forms["CreateUserDetailsForm"].submit();
			}else{
				alert("Fingerprint Enrollment Failed.");
			}
		} catch(e) {
			alert("Error"+e);
		}
	
}
  </script>
<div id="content_main">
<form:form id="CreateUserDetailsForm" name="CreateUserDetailsForm" modelAttribute="addEditUserModel" action="${actionURL}" method="POST" cssClass="inline">
		<div class="container">
                        <div class="row">
                            <div class="roundedBorder ov_hidden">
		<table class="heading_report" style="width: 40%;height: 35px;">			<tr>
					<b><td class='subHeading' colspan='4' style="padding: 5px">Finger Print Enrolment</td></b>
			</tr>
		</table>
				
					<table style="width:100%;background-color:#E3F1FE" cellspacing="0"class="data_table" cellpadding="0">
						<thead>
							<tr>
						<tbody>
						
							<tr>
								<td style="border: none; font-weight: bold">User ID<span style="COLOR: #ff0000;">*</span></td>
								<td style="border: none;padding-right: 35%;">
									<input id="uid" name="uid" value ="${userEdit.userName}" autocomplete = "off" class="defaultText" type="text"   maxlength="100" disabled />
								</td>
							</tr>
							<tr>
								<td style="border: none; font-weight: bold">First Name<span style="COLOR: #ff0000;">*</span></td>
								<td style="border: none;">
									<input id="personName.field" name="personName" class="defaultText"  value="${userEdit.firstName}" type="text"   maxlength="100" disabled />
								</td>
							</tr>						
					</table>		
					<table style="text-align: right;" class="displayTag">
						<tr>
							<td align="right" style="padding: 10px;text-align: right;"><input type="button" id="save_btn" class="button_grey" value="Enroll FingerPrint" onClick="submitFpEnroll();"/>
								&nbsp; 								
							</tr>
					</table>
		<input type="hidden" id="loginId.field" value="ADMINISTRATOR" />
		<input type="hidden" id="firstName" value="${userEdit.firstName}" />		
		<form:hidden id="businessId.field" path="businessId" />		
		<input type="hidden" id="entryPoint" name="entryPoint" value="ADD" />
		<input type="hidden" id="mobile" name="mobile" value="" />
		<input type="hidden" id="stateCode" name="stateCode" value="" />
		<input type="hidden" id="did" name="did" value="" />
		<input type="hidden" id="_s_token" name="_s_token" value="" />
		<input type="hidden" id="locId" name="locId"
			value="L20141010235959_0001" />
		<form:hidden cssClass="defaultText" id="email.field" path="email"
			maxlength="200" escapeXml="true" />
		</div>
		</div>
		</div>
	</form:form>
	
</div>
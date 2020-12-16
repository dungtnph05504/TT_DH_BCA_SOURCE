<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<c:url var="prodUrl" value="/servlet/investigation/startinvestigation" />
<c:url var="searchUrl" value="/servlet/investigation/search" />
<c:url var="pendingRejectedJobUrl" value="/servlet/investigation/pendingRejectedJobs" />
<c:url var="newRejectedJobUrl" value="/servlet/investigation/newRejectedJob"/>
<form:form commandName="rejectedJobList" id="form" name="form">

			<c:if test="${not empty requestScope.Errors}">
				<div style="color:Red;background-color:White;border-style:solid;border-color:Red;border-width:thin;">
					<c:forEach items="${requestScope.Errors}" var="errorMessage">
						<li>
							${errorMessage}
						</li>
					</c:forEach>
					
				</div>
			</c:if>
			
			<c:if test="${not empty requestScope.messages}">
				<div class="border_success">
				<div class="success_left">
				<img align='left'
					src="<c:url value="/resources/images/success.jpg" />" width="30"
					height="30" />
                </div>
			
				
					 <div class="success">
						<table width="600" height="40" border="0" cellpadding="5">
							<tr> 
								<td style="padding-left: 5px;font-weight: bold; vertical-align: top;">
					    			<c:forEach items="${requestScope.messages}" var="infoMessage">
											${infoMessage}
									</c:forEach>
								</td>
							  </tr>
						</table>
					</div>
				</div>
				<br />
		</c:if>

		<!--Content start-->
		<div id="content_main">
   <div id="content_inner"> 
     <div id="heading">
     
        <div id="heading_left" style="font-weight: bold;" align="justify">
       Investigation Rejected Job List</div>
       
       <div id="heading_right">
       <table width="100%" border="0" class="table_cont">
  <tr>
    <td width="53%" height="35" align="right">Application Ref No</td>
    <td width="32%" align="right"><label>
      <input type="text" name="search_data" id="search_data" class="fld_txt" value="${txnId}"/>
    </label></td>
    <td width="15%" align="right"><label>
      <input type="button" class="button_grey"  value="Search" style="width: 60px;" onclick="doSubmitSave(this.form);"  />
      	<input type="hidden" id="userId" name="userId" value="${sessionScope.userSession.userName}" />
    </label></td>
  </tr>
</table>

       </div>
     </div>
      
	  <br />
			
			<display:table cellspacing="1" cellpadding="0" id="row"
				export="false" class="displayTag" name="rejectedJobList" defaultsort="1" sort="external"  partialList="true" size="${totalRecords}"
				defaultorder="ascending" pagesize="${pageSize}"
				requestURI="/servlet/supervisorController/investigationRejectedList">
				<display:column title="Job ID" sortable="true" sortProperty="uploadJobId">
				<!-- {prasad}changes for the  proxy redirect to login page error [start] -->
				<c:url var="jobUrl" value="/servlet/investigation/startInvestigationCompare/${row.uploadJobId}" />
				<a href="${jobUrl}"> <c:out value="${row.uploadJobId}" /></a>
					 
					<!--<a href="${prodUrl}/${row.uploadJobId}"> <c:out
							value="${row.uploadJobId}" />
					</a> 
				--><!-- {prasad}changes for the  proxy redirect to login page error [end] -->
				</display:column>
				<display:column property="transactionId" sortable="true"
					title="Application Ref ID" maxLength="30" />
				<display:column property="jobType" sortable="true"
					title="Transaction Type" maxLength="30" />
				<display:column property="investigationType" sortable="true"
					title="Job Type" maxLength="30" >
			</display:column>
				<display:column property="dateOfApplication" title="Date of Application" sortable="true" format="{0,date,dd-MMM-yyyy}" >
				</display:column>
			</display:table>

			<!--<br />
			<div id="after_table">
				<table class="innerTable">
					<tr>
						<td style='padding-right: 8px' align="right">
						<c:if test="${empty rejectedJobList}">
							<input type="button"
							onclick="doSubmitNew(this.form);" class="button_grey"
							 value="New Rejected Case"
							 style="width: 120px;" /> 
						</c:if>
						<c:if test="${not empty rejectedJobList}">
							<input type="button"  onclick="doSubmitNew1(this.form);"
							class="button_grey"
							 value="New Rejected Case"    style="width: 120px;" /> 
						</c:if>
							&nbsp;</td>
					</tr>
				</table>
			</div>
		--></div>
	</div>
<div id="dialog-confirm"></div>
	<script type="text/javascript">
		function doSubmitSave(form) {
			form.action = '<c:url value="/servlet/investigation/search" />';
			form.submit();
		}
	</script>
	<script type="text/javascript">
	
		function doSubmitNew(form) {
			document.forms["form"].action = "${newRejectedJobUrl}";
			document.forms["form"].submit();
		}
		$( "#dialog-confirm" ).dialog({
			modal: true,
		      autoOpen: false,
			  width : 500,
			  resizable: true,
		      show: {
		        effect: "fade",
		        duration: 200
		      },
		      hide: {
		        //effect: "explode",
		        //duration: 1000
		      },
			   buttons:{
		    Ok: function() {
		    	document.forms["form"].action = "${pendingRejectedJobUrl}";
				document.forms["form"].submit();
		    },
			Cancel: function() {
				$(this).dialog("close");
		    }
		   }
		});
		
		function doSubmitNew1(form) {
			 $("#dialog-confirm").dialog('option', 'title', 'Pending Rejected Jobs Status');
			 $("#dialog-confirm").html("There are Pending Rejected Jobs for Investigation? Do you want to continue?");
			 $("#dialog-confirm").dialog( 'open' );				
		}
	</script>
</form:form>



<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<c:url var="prodUrl" value="/servlet/investigation/startinvestigation" />
<c:url var="searchUrl" value="/servlet/investigation/search" />
<c:url var="newUrl" value="/servlet/fraudCaseManagement/newInvestigation" />
<c:url var="pendingJobUrl" value="/servlet/investigation/pendingJobList" />
<form:form commandName="jobList" id="form" name="form" >

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
         <div class="container">
   <div class="row">
   <div class="roundedBorder ov_hidden">
     <div id="heading">
     
        <div id="heading_left" style="font-weight: bold;" align="justify">
      Danh sách điều tra bị tạm ngừng</div>
       
       <div id="heading_right">
       <table width="100%" border="0" class="table_cont">
  <tr>
    <td width="53%" height="35" align="right">Tên người dùng</td>
    <td width="32%" align="right"><label>
      <input type="text" name="search_data" id="search_data" class="fld_txt" value="${userIdInput}"/>
    </label></td>
    <td width="15%" align="right"><label>
      <input type="button" class="btn_small btn-primary"  value="Tìm kiếm" onclick="doSubmitSave(this.form);"  /> 
    </label></td>
  </tr>
</table>

       </div>
     </div>
      
	  <br />
			<%
				int pageSize = 20;
			%> 
  
		<c:if test="${empty jobList}">
			Không tìm thấy điều tra nào được giao.
			<br />
		</c:if>
		<c:if test="${not empty jobList}">
  
			<display:table cellspacing="1" cellpadding="0" id="row" sort="external"  partialList="true" size="${totalRecords}"
				export="false" class="displayTag" name="jobList" defaultsort="1"
				defaultorder="ascending" pagesize="${pageSize}"
				requestURI="/servlet/fraudCaseManagementAssigned/fraudCaseManagementAssignedList">
				<display:column title="ID công việc" sortable="false" sortProperty="uploadJobId"> 
				  <c:out value="${row.uploadJobId}" />  
				</display:column>
				<display:column property="transactionId" sortable="false"
					title="ID ứng dụng" maxLength="30" />
				<display:column property="jobType" sortable="false"
					title="Loại giao dịch" maxLength="30" />
				<display:column property="investigationType" sortable="false"
					title="Loại điều tra" maxLength="30" >
			    </display:column>
				<display:column property="dateOfApplication" title="Ngày nộp đơn" sortable="false" format="{0,date,dd-MMM-yyyy}" >
				</display:column>
				<display:column property="investigationOfficerId" sortable="false"
					title="Được giao cho Người dùng" maxLength="64" >
			    </display:column>
				<display:column title="" sortable="false" > 
					<a href="#" onclick="unassignJob('${row.uploadJobId}')">Bỏ chỉ định công việc</a> 
				</display:column>
				<display:column title="" sortable="false" > 
					<a href="#" onclick="unassignAllJobsForUser('${row.investigationOfficerId}')">Gỡ bỏ tất cả công việc cho người dùng này</a> 
				</display:column>
			</display:table>
		</c:if> 
		</div>
		</div>
		</div>
		</div>
	</div>
	<script type="text/javascript">
		function doSubmitSave(form) {
			var aUserId = trim($('#search_data').val());
			var urlSuffix;
			if(aUserId.length > 0){
				urlSuffix = '/' + aUserId;	
			}else{
				urlSuffix = '';
			}
			form.action = '<c:url value="/servlet/fraudCaseManagementAssigned/search" />'+urlSuffix;
			form.submit();
		}

		function trim(str) {
		    return str.replace(/^\s+|\s+$/g,"");
		}	
	</script>
	
	
    <!-- ========================================================== DIALOG BOX - START ================================================================ -->
		<div id="dialog-confirm-unassignJob"></div>	
		<script type="text/javascript">
		
		    var jobIdToUnassign_current;
		
			function unassignJob(jobIdToUnassign) { 
			     jobIdToUnassign_current = jobIdToUnassign;
				 $("#dialog-confirm-unassignJob").dialog('option', 'title', 'Unassign Job');
				 $("#dialog-confirm-unassignJob").html("Bạn có muốn bỏ gán công việc này không  (Mã công việc "+jobIdToUnassign+")?");
				$( "#dialog-confirm-unassignJob").dialog("open");
			}
		
			$( "#dialog-confirm-unassignJob" ).dialog({
				modal: true,
			      autoOpen: false,
				  width : 500,
				  resizable: true,
			      show: {
			        effect: "fade",
			        duration: 200
			      },
			      hide: { 
			      },
				   buttons:{
			    Ok: function() {
			    	unassignJob_do();
			    },
				Cancel: function() {
					$(this).dialog("close");
			    }
			   }
			});
			
			function unassignJob_do() {
				form.action = '<c:url value="/servlet/fraudCaseManagementAssigned/unassignJob/" />'+jobIdToUnassign_current;
				form.submit();
			} 
		</script>
    <!-- ========================================================== DIALOG BOX - END ================================================================ -->
    
    
	
	
    <!-- ========================================================== DIALOG BOX - START ================================================================ -->
		<div id="dialog-confirm-unassignAllJobsForUser"></div>	
		<script type="text/javascript">
		
		    var userIdToUnassign_current;
		
			function unassignAllJobsForUser(userIdToUnassign) { 
			     userIdToUnassign_current = userIdToUnassign;
				 $("#dialog-confirm-unassignAllJobsForUser").dialog('option', 'title', 'Unassign All Jobs For User');
				 $("#dialog-confirm-unassignAllJobsForUser").html("Bạn có muốn bỏ gán tất cả các công việc cho người dùng này  (Mã người dùng "+userIdToUnassign+")?");
				$( "#dialog-confirm-unassignAllJobsForUser").dialog("open");
			}
		
			$( "#dialog-confirm-unassignAllJobsForUser" ).dialog({
				modal: true,
			      autoOpen: false,
				  width : 500,
				  resizable: true,
			      show: {
			        effect: "fade",
			        duration: 200
			      },
			      hide: { 
			      },
				   buttons:{
			    Ok: function() {
			    	unassignAllJobsForUser_do();
			    },
				Cancel: function() {
					$(this).dialog("close");
			    }
			   }
			});
			
			function unassignAllJobsForUser_do() {
				form.action = '<c:url value="/servlet/fraudCaseManagementAssigned/unassignAllJobsForUser/" />'+userIdToUnassign_current;
				form.submit();
			} 
		</script>
    <!-- ========================================================== DIALOG BOX - END ================================================================ -->
    
</form:form>



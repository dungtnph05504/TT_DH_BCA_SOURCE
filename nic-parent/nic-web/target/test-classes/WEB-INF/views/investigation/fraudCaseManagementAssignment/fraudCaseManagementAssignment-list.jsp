<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>  
	
	<c:if test="${not empty requestScope.Errors}">
		<div class="border_error">
		<div class="error_left">
		<img align='left'
			src="<c:url value="/resources/images/error_new.jpg" />" width="30"
			height="30" />
              </div>
	
		
			 <div class="errors">
				<table width="600" height="40" border="0" cellpadding="5">
					<tr> 
						<td style="padding-left: 5px;font-weight: bold; vertical-align: top;">
			    			<c:forEach items="${requestScope.Errors}" var="errorMessage">
									${errorMessage}
							</c:forEach>
						</td>
					  </tr>
				</table>
			</div>
		</div>
		<br />
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
	<form:form  modelAttribute="formData" name="formData" >
		<div id="content_main">
		   <div id="content_inner"> 
		         <div class="container">
   <div class="row">
   <div class="roundedBorder ov_hidden">
			     <div id="heading"> 
			        <div id="heading_left" style="font-weight: bold;" align="justify">
			              Danh sách điều tra được giao bị tạm ngưng
			        </div> 
			     </div>
			     
			     <div style="clear:both"></div>
			   
			     <div style="min-height: 10px"></div>
			   
			     <div style="border:  solid 1px gray; border-radius:4px; margin: 2px">
			     	<div style="margin: 2px">
			        
				       <div style="">
					       <div style="vertical-align: text-top; display: inline-block; width: 20%; margin-left: 20%; float: left"> 
								<div style="border:  solid 1px gray; margin: 2px; border-radius:4px; min-height:92px;">
									<div style="margin: 2px">
										<div style="text-align:  center;">Trạng thái giao
										</div>
										<div style="margin-left: 30px">
											<div style="text-align:  left;">
												<form:radiobutton path="assignmentStatus" value="ALL" />&nbsp;&nbsp;Tất cả 
											</div>
											<div style="text-align:  left;"> 
												<form:radiobutton path="assignmentStatus" value="ASSIGNED" />&nbsp;&nbsp;Chỉ được Chỉ định
											</div>
											<div style="text-align:  left;">
												<form:radiobutton path="assignmentStatus" value="UNASSIGNED" />&nbsp;&nbsp;Không được chỉ định 
											</div>  
										</div>  
									</div>
								</div>
					       </div>
					       
					       <div style="vertical-align: text-top; display: inline-block; width: 20%; float: left">
								<div style="border:  solid 1px gray; margin: 2px; border-radius:4px;min-height:92px;">
									<div style="margin: 2px"> 
										<div style="text-align:  center;">ID ứng dụng
										</div>
										<div style="text-align:  center;">
											<form:input path="searchTransactionId" type="text" />
										</div>  
									</div>
								</div>
					       </div>
					       
					       <div style="vertical-align: text-top; display: inline-block; width: 20%; margin-right: 19%; float: left">
								<div style="border:  solid 1px gray; margin: 2px; border-radius:4px;min-height:92px;">
									<div style="margin: 2px"> 
										<div style="text-align:  center;">Hiện đang được giao
										</div>
										<div style="text-align:  center;">
											<form:input path="currentlyAssignedToUserId" type="text" />
										</div>  
									</div>
								</div>
					       </div>  
				       </div>
			     
			     	   <div style="clear:both"></div>
					       
				       <div style="margin: 2px">
							<div style="text-align:  center;">
								 <input type="button" class="btn_small btn-primary"  value="Áp dụng bộ lọc" style="" onclick="doApplyFilter();"  />
							</div>   
				       </div>
			       
			     	</div>
			     </div>
			     
			     <div style="clear:both"></div>
			      
				 <br />
		      
				<%
					int pageSize = 20;
				%>   
			
				<c:if test="${empty jobList}">
					No Investigation Found.
					<br />
				</c:if>
				<c:if test="${not empty jobList}">
					<display:table cellspacing="1" cellpadding="0" id="row" sort="external"  partialList="true" size="${totalRecords}"
						export="false" class="displayTag" name="jobList" defaultsort="1"
						defaultorder="ascending" pagesize="${pageSize}"
						requestURI="/servlet/fraudCaseManagementAssignment/fraudCaseManagementAssignmentList">
						<display:column title="ID công việc" property="uploadJobId" sortable="false" >
						</display:column>
						<display:column property="transactionId" sortable="false"
							title="ID ứng dụng" maxLength="30" />
						<display:column property="dateOfApplication" title="Ngày nộp đơn" sortable="false" format="{0,date,dd-MMM-yyyy}" >
						</display:column>
						<display:column property="estDateOfCollectionString" sortable="false"
							title="Ngày phát hành" maxLength="30" />
						<display:column property="priorityString" sortable="false"
							title="Ưu tiên" maxLength="30" />
						<display:column property="investigationOfficerId" sortable="false"
							title="Đã giao cho" maxLength="64" />
						<display:column property="jobType" sortable="false"
							title="Loại giao dịch" maxLength="30" />
						<display:column property="investigationType" sortable="false"
							title="Loại điều tra" maxLength="30" >
						</display:column>
						<display:column title="Chọn" sortable="false" > 
							<form:checkbox path="selectedJobIds" value="${row.uploadJobId}" cssClass="${row.currentlyAssignedToAnInvestigationOfficer}"  
							/> 
						</display:column>
					</display:table>
				</c:if>
	  
			
		 
			<%  /* ******************************************************************************************************************** */ %>	
			<%  /* ************************************* actions for selected jobs - start ******************************************** */ %>	
			<%  /* ******************************************************************************************************************** */ %>	
		
			<c:if test="${totalRecords > 0}"> 
				<div style="clear:both"></div>
			   
				<div style="min-height: 5px"></div>
			   
			    <div style="border:  solid 1px gray; border-radius:4px; margin: 2px">
			     	<div style="margin: 2px">
						<div style="text-align:  center; font-weight:  bold; margin-top: 2px; margin-bottom: 2px"> Hành động đối với những việc đã chọn ở trên
						</div> 
						<div> 
							<div style="vertical-align: text-top; display: inline-block; width:  20%; margin-left:29%; float: left"> 
								<div style="border:  solid 1px gray; border-radius:4px; margin: 2px">  
									<div style="margin:2px">
										<div style="text-align:  center">
											<input type="button" class="btn_small btn-primary"  value="Chỉ định đã chọn" style="" onclick="doAssign();"  />
										</div>
										<div style="text-align:  center">
											Tên người dùng
										</div>  
										<div style="text-align:  center">
											<form:input path="assignToId" class="assignToIdStyle" type="text" />
										</div>    
									</div>
								</div>
							</div>
							<div style="vertical-align: text-top; display: inline-block; width:  20%; margin-right:29%; float: left"> 
								<div style="border:  solid 1px gray; border-radius:4px; margin: 2px;min-height:74px;"> 
									<div style="margin:2px">  
										<div style="text-align:  center">
											<input type="button" class="btn_small btn-primary"  value="Bỏ giao đã chọn" style="" onclick="doUnassign();"  />
										</div>     
									</div>
								</div>
							</div> 
						</div>
						<div style="clear:both"></div>
					</div>
				</div>
					
				<div style="clear:both"></div>
			</c:if>
		
			<%  /* ******************************************************************************************************************** */ %>	
			<%  /* ************************************** actions for selected jobs - end ********************************************* */ %>	
			<%  /* ******************************************************************************************************************** */ %>	
		  
			<%  /* ******************************************************************************************************************** */ %>	
			<%  /* ********************************* Actions For All Jobs In The System - start *************************************** */ %>	
			<%  /* ******************************************************************************************************************** */ %>	
		  
				<div style="clear:both"></div>
			   
				<div style="min-height: 5px"></div>
			   
			    <div style="border:  solid 1px gray; border-radius:4px; margin: 2px">
			     	<div style="margin: 2px">
						<div style="text-align:  center; font-weight:  bold; margin-top: 2px; margin-bottom: 2px"> Hành động cho tất cả các việc trong hệ thống
						</div>
						<div> 
							<div style="vertical-align: text-top; display: inline-block; width:  30%; margin-left:35%; margin-right:34%; float: left"> 
								<div style="border:  solid 1px gray; border-radius:4px; margin: 2px">  
									<div style="margin:2px">
										<div style="text-align:  center">
											<input type="button" class="btn_small btn-primary"  value="Bỏ giao Tất cả Việc làm" style="" onclick="doUnassignAllForUser();"  />
										</div>
										<div style="text-align:  center">
											Hiện tại giao cho người dùng
										</div>  
										<div style="text-align:  center">
											<form:input path="unassignAllForUserUserId" class="unassignAllForUserUserIdStyle" type="text" />
										</div>    
									</div>
								</div>
							</div> 
						</div>
						<div style="clear:both"></div>
					</div>
				</div>
					
				<div style="clear:both"></div> 
			 
			<%  /* ******************************************************************************************************************** */ %>	
			<%  /* ********************************** Actions For All Jobs In The System - end **************************************** */ %>	
			<%  /* ******************************************************************************************************************** */ %>	
		   </div>
		</div>
		</div>
		</div>
		</div>
	</form:form>
		
		
		
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ************************************************ apply filter - start ********************************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
		
		<script type="text/javascript">
			function doApplyFilter() {
				document.forms["formData"].action = '<c:url value="/servlet/fraudCaseManagementAssignment/applyFilter" />';
				document.forms["formData"].submit();
			}
		</script>
	
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ************************************************** apply filter - end ********************************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
		  
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* *************************************************** assign - start ************************************************* */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
		 
		<c:url var="urlAssign" value="/servlet/fraudCaseManagementAssignment/assign" />  
		
		<div id="dialog-confirm-assign"></div>
		
		<script type="text/javascript"> 
			$( "#dialog-confirm-assign" ).dialog({
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
			    	document.forms["formData"].action = "${urlAssign}";
					document.forms["formData"].submit();
			    },
				Cancel: function() {
					$(this).dialog("close");
			    }
			   }
			});
		</script> 
			
		<script type="text/javascript"> 
			function doAssign() {
				
				 var messages = validateForAssignment();
				 if (messages != ''){
			        alert  (messages);
			        return;
				 }
				
				 $("#dialog-confirm-assign").dialog('option', 'title', 'Assignment Confirmation');
				 $("#dialog-confirm-assign").html("Proceed to assign/reassign the jobs you selected?");
				 $("#dialog-confirm-assign").dialog( 'open' );			
			}
			
			function validateForAssignment() {
				
				var numberOfJobsInPage = ${totalRecords}; 
				
				if (numberOfJobsInPage == 0){
					return 'There are no jobs available for assignment.'; 
				}

				var inp = $(".assignToIdStyle").val();
				if ($.trim(inp).length == 0){
					return 'Please input the User Id to assign to.';
				} 
				
				if (     ( $(".currentlyNotAssignedToAnInvestigationOfficer:checked").length 
					   +   $(".currentlyAssignedToAnInvestigationOfficer:checked").length 
					     )
						  == 0){
					return 'Please select the jobs that you want to assign.';
				}  
				
				return ''; 		
			}
		</script>
	
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* **************************************************** assign - end ************************************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
		  
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ************************************************* unassign - start ************************************************* */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
		 
		<c:url var="urlUnassign" value="/servlet/fraudCaseManagementAssignment/unassign" />  
		
		<div id="dialog-confirm-unassign"></div>
		
		<script type="text/javascript"> 
			$( "#dialog-confirm-unassign" ).dialog({
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
			    	$('.currentlyNotAssignedToAnInvestigationOfficer').each(function () { 
			    		  $(this).prop('checked', false);
			    	});
			    	document.forms["formData"].action = "${urlUnassign}";
					document.forms["formData"].submit();
			    },
				Cancel: function() {
					$(this).dialog("close");
			    }
			   }
			});
		</script> 
			
		<script type="text/javascript"> 
			function doUnassign() {
				
				 var messages = validateForUnassignment();
				 if (messages != ''){
			        alert  (messages);
			        return;
				 }
				
				 $("#dialog-confirm-unassign").dialog('option', 'title', 'Unassignment Confirmation');
				 $("#dialog-confirm-unassign").html("Proceed to unassign the jobs you selected?");
				 $("#dialog-confirm-unassign").dialog( 'open' );			
			}
			
			function validateForUnassignment() {
				
				var numberOfJobsInPage = ${totalRecords}; 
				
				if (numberOfJobsInPage == 0){
					return 'There are no jobs available for unassignment.';
				}
				
				if ($(".currentlyAssignedToAnInvestigationOfficer:checked").length == 0){
					return 'Please select the jobs (that are currently assigned) that you want to unassign.';
				}  
				
				return ''; 		
			}
		</script>
	
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ************************************************** unassign - end ************************************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>
  
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ******************************************** unassignAllForUser - start ******************************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
		 
		<c:url var="urlUnassignAllForUser" value="/servlet/fraudCaseManagementAssignment/unassignAllForUser" />  
		
		<div id="dialog-confirm-unassignAllForUser"></div>
		
		<script type="text/javascript"> 
			$( "#dialog-confirm-unassignAllForUser" ).dialog({
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
			    	document.forms["formData"].action = "${urlUnassignAllForUser}";
					document.forms["formData"].submit();
			    },
				Cancel: function() {
					$(this).dialog("close");
			    }
			   }
			});
		</script> 
			
		<script type="text/javascript"> 
			function doUnassignAllForUser() {
				
				 var messages = validateForUnassignAllForUser();
				 if (messages != ''){
			        alert  (messages);
			        return;
				 }
				
				 $("#dialog-confirm-unassignAllForUser").dialog('option', 'title', 'Unassignment Confirmation');
				 $("#dialog-confirm-unassignAllForUser").html("Proceed to unassign all jobs assigned to the User Id you inputted?");
				 $("#dialog-confirm-unassignAllForUser").dialog( 'open' );			
			}
			
			function validateForUnassignAllForUser() { 

				var inp = $(".unassignAllForUserUserIdStyle").val();
				if ($.trim(inp).length == 0){
					return 'Please fill in the Currently Assigned To User Id.';
				}  
				
				return ''; 		
			}
		</script>
	
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ********************************************* unassignAllForUser - end ********************************************* */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
	
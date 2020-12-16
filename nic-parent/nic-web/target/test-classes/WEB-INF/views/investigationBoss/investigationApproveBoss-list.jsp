<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<c:url var="JobInvestigationPendingBossUrl" value="/servlet/investigationBoss/investigationPendingBossList" />
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
					<td
						style="padding-left: 5px; font-weight: bold; vertical-align: top;">
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
					<td
						style="padding-left: 5px; font-weight: bold; vertical-align: top;">
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
<form:form modelAttribute="formData" name="formData">
	<div id="content_main">
		<div id="content_inner">
			<div class="container">
				<div class="row">
					<div class="roundedBorder ov_hidden">
						<div id="heading">
							<div id="heading_left" style="font-weight: bold; background:gray; cursor:pointer;margin-right:5px" align="justify">
								<a style="color:#fff;text-decoration:none" onclick=jobInvestigationPendingBossFunction();>Danh sách Chờ xét duyệt </a>
							</div>
							<div id="heading_left" style="font-weight: bold;" align="justify">
								Danh sách Đã xét duyệt 
							</div>
						</div>

						<div style="clear: both"></div>

						<div style="min-height: 10px"></div>

						<div style="margin: 2px">
							<div
								style="vertical-align: text-top; display: inline-block; width: 20%; float: left">
								<div
									style="border: solid 1px gray; margin: 2px; border-radius: 4px; min-height: 92px;">
									<div style="margin: 2px">
										<div style="text-align: center;">Mã giao dịch</div>
										<div style="text-align: center;">
											<form:input path="searchTransactionId" type="text" />
										</div>
									</div>
									<div style="clear: both"></div>
									<div style="margin: 2px">
										<div style="text-align: center;">
											<input type="button" class="btn_small btn-primary"
												value="Áp dụng bộ lọc" style="" onclick="doApplyFilter();" />
										</div>
									</div>
								</div>
							</div>
						</div>

						<div style="clear: both"></div>

						<br />

						<%
							int pageSize = 20;
						%>

						<c:if test="${empty jobList}">
					Không có bản ghi nào được tìm thấy.
					<br />
						</c:if>
						<c:if test="${not empty jobList}">
							<display:table cellspacing="1" cellpadding="0" id="row"
								sort="external" partialList="true" size="${totalRecords}"
								export="false" class="displayTag" name="jobList" defaultsort="1"
								defaultorder="ascending" pagesize="${pageSize}"
								requestURI="/servlet/investigationBoss/investigationApproveBossList">
								<display:column title="ID" property="uploadJobId"
									sortable="false">
								</display:column>
								<display:column property="transactionId" sortable="false"
									title="Mã giao dịch" maxLength="30" />
								<display:column property="fullName" sortable="false"
									title="Họ tên đầy đủ" maxLength="50" />
								<display:column property="dateOfApplication"
									title="Ngày nộp đơn" sortable="false"
									format="{0,date,dd-MMM-yyyy}">
								</display:column>
								<display:column property="estDateOfCollectionString"
									sortable="false" title="Ngày trả kết quả" maxLength="30" />
								<display:column property="priorityString" sortable="false"
									title="Ưu tiên" maxLength="30" />
								<display:column property="regSiteCode"
									sortable="false" title="Nơi đăng ký hồ sơ" maxLength="64" />
									<display:column property="passportType"
									sortable="false" title="Loại hộ chiếu" maxLength="64" />
								<display:column property="jobType" sortable="false"
									title="Loại giao dịch" maxLength="30" />
								<display:column title="Chọn" sortable="false">
									<form:checkbox path="selectedJobIds" value="${row.transactionId}"
										cssClass="${row.currentlyAssignedToAnInvestigationOfficer}" />
								</display:column>
							</display:table>
						</c:if>



						<%
							/* ******************************************************************************************************************** */
						%>
						<%
							/* ************************************* actions for selected jobs - start ******************************************** */
						%>
						<%
							/* ******************************************************************************************************************** */
						%>

					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
			<div style="margin: 10px"> 
				<input type="button" class="btn btn-primary"              onclick="openBoxReject();" name="approve"  value="Tạo danh sách" /> 
				&nbsp; 
			</div>
	</div>

	<div id="dialog-reject-getRemarks" title="Tạo danh sách bàn giao sang BCA" style="display: none;"> 
			<div class="form-group">		
				<label for="codeHandover">Mã danh sách bàn giao</label>
				<input class="form-control"  id="codeHandover" name="codeHandover" />	
				<br />
				<label for="nameHandover">Tên danh sách bàn giao</label>
				<input class="form-control" id="nameHandover" name="nameHandover" />
				
			</div> 
	</div> 

	<c:url var="createHandover" value="/servlet/investigationBoss/createHandover" />
	
	<script type="text/javascript">
		function doApplyFilter() {
			document.forms["formData"].action = '<c:url value="/servlet/investigationBoss/applyFilterApprove" />';
			document.forms["formData"].submit();
		}
		
		function openBoxReject(){
			  $( "#dialog-reject-getRemarks" ).dialog( "open" ); 
		}
		
			$(function() {
			    $( "#dialog-reject-getRemarks" ).dialog({
					  modal: true,
				      autoOpen: false,
					  width : 600,
					  height: 300,
					  resizable: false,
				      show: {
				        effect: "fade",
				        duration: 1000
				      },
				      hide: { },
				      buttons: {
				         "Continue": function() {
							var inp = $("#codeHandover").val();
							var inp1 = $("#nameHandover").val();
							
							if ($.trim(inp).length == 0){
								alert ('Chưa nhập mã danh sách!');
								return;
							}   
							if ($.trim(inp1).length == 0){
								alert ('Chưa nhập tên danh sách!');
								return;
							}    
				            $( this ).dialog( "close" );
				            requestApprove(inp, inp1);
				         },
				         "Back": function() {
				            $( this ).dialog( "close" );
				         }
				      } 
			    });
		  }); 
	
		
		function requestApprove(code, name) { 
			if(confirm("Có chăc chắn muốn thực hiện hành động này?"))
			   {
					document.forms["formData"].currentlyAssignedToUserId.value =  code;
			      	document.forms["formData"].unassignAllForUserUserId.value  =  name;
			 	  	document.forms["formData"].action = '${createHandover}';  
				   	document.forms["formData"].submit();  
	
			   }
			  else
			    return false;
		}
		
		function jobInvestigationPendingBossFunction() {
			document.forms["investigationMenuForm"].action= '${JobInvestigationPendingBossUrl}';
			document.forms["investigationMenuForm"].submit();	
		}
	
	</script>

	<form:input    path="currentlyAssignedToUserId"                       type="hidden" name="currentlyAssignedToUserId"                       value="" />
	<form:input    path="unassignAllForUserUserId"                        type="hidden" name="unassignAllForUserUserId"                        value="" />
</form:form>



<%
	/* ******************************************************************************************************************** */
%>
<%
	/* ************************************************ apply filter - start ********************************************** */
%>
<%
	/* ******************************************************************************************************************** */
%>



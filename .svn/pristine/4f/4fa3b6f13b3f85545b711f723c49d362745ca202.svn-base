<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<c:url var="JobSuccessPersoUrl" value="/servlet/investigationConfirm/successPersoList" />
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
							<div id="heading_left" style="font-weight: bold;margin-right:5px" align="justify">
								Danh sách in lỗi
							</div>
							<div id="heading_left" style="font-weight: bold; background:gray; cursor:pointer;text-decoration:none;color:#fff" align="justify">
								<a style="color:#fff;text-decoration:none" onclick=jobSuccessPersoUrl();>Danh sách In thành công </a>
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
										<div style="text-align: center;">ID ứng dụng</div>
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
								requestURI="/servlet/investigationConfirm/errorPersoList">
								<display:column title="ID công việc" property="uploadJobId" sortProperty="uploadJobId"
									sortable="false">
									<c:url var="jobUrl" value="/servlet/investigationBca/startDetailInvestigationP/${row.uploadJobId}" />
									<%-- <a href="${jobUrl}"> <c:out value="${row.uploadJobId}" /></a> --%>
								</display:column>
								<display:column property="transactionId" sortable="false"
									title="ID ứng dụng" maxLength="30" />
								<display:column property="fullName" sortable="false"
									title="Họ tên đầy đủ" maxLength="50" />
								<display:column property="dateOfApplication"
									title="Ngày nộp đơn" sortable="false"
									format="{0,date,dd-MMM-yyyy}">
								</display:column>
								<display:column property="estDateOfCollectionString"
									sortable="false" title="Ngày phát hành" maxLength="30" />
								<display:column property="priorityString" sortable="false"
									title="Ưu tiên" maxLength="30" />
								<display:column property="regSiteCode"
									sortable="false" title="Nơi đăng ký hồ sơ" maxLength="64" />
									<display:column property="passportType"
									sortable="false" title="Loại hộ chiếu" maxLength="64" />
								<display:column property="jobType" sortable="false"
									title="Loại giao dịch" maxLength="30" />
								<display:column property="countError" sortable="false"
									title="Số lần in hỏng" maxLength="30" />
								<%-- <display:column title="Chọn" sortable="false">
									<form:checkbox path="selectedJobIds" value="${row.uploadJobId}"
										cssClass="${row.currentlyAssignedToAnInvestigationOfficer}" />
								</display:column> --%>
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

<script type="text/javascript">
	function doApplyFilter() {
		document.forms["formData"].action = '<c:url value="/servlet/investigationConfirm/applyFilterError" />';
		document.forms["formData"].submit();
	}
	
	function jobSuccessPersoUrl() {
		document.forms["investigationMenuForm"].action= '${JobSuccessPersoUrl}';
		document.forms["investigationMenuForm"].submit();	
	}
</script>

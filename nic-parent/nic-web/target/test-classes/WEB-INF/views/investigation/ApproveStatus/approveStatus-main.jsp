<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<c:url var="prodUrl" value="/servlet/investigation/startApproveStatus" />
<c:url var="newUrl" value="/servlet/investigation/newApproveStatus" />
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
		 <div class="container">
   <div class="row">
   <div class="roundedBorder ov_hidden">
   <div id="content_inner"> 
     <div id="heading">
     
        <div id="heading_left" style="font-weight: bold;" align="justify">
       Danh sách chờ phê duyệt</div>
       
       <div id="heading_right">
       <table width="100%" border="0" class="table_cont">
  <tr>
    <td width="53%" height="35" align="right">Số đơn đăng ký</td>
    <td width="32%" align="right"><label>
      <input type="text" name="search_data" id="search_data" class="fld_txt" value="${txnId}"/>
    </label></td>
    <td width="15%" align="right"><label>
      <input type="button" class="btn_small btn-primary"  value="Tìm kiếm" onclick="doSubmitSave(this.form);"  />
      	<input type="hidden" id="userId" name="userId" value="${sessionScope.userSession.userName}" />
    </label></td>
  </tr>
</table>

       </div>
     </div>
      
	  <br />
			<%
				int pageSize = 20;
			%>
<!-- 			<div style="float: right; padding-right: 120px; font-weight: bold;"> -->
<!--   Đang chờ điều tra : -->
<%--    <c:out value="${pendingCount}"/> --%>
<!--   </div> -->
		
		
		<c:if test="${empty jobList}">
			Không bản ghi trong danh sách bàn giao
			<br />
		</c:if>
		<c:if test="${not empty jobList}">
			<display:table cellspacing="1" cellpadding="0" id="row" sort="external"  partialList="true" size="${totalRecords}"
				export="false" class="displayTag table" name="jobList" defaultsort="1"
				defaultorder="ascending" pagesize="${pageSize}"
				requestURI="/servlet/investigation/getApproveJob"><!-- TRUNG SỬA PHÂN TRANG -->
				<display:column title="Mã công việc" sortable="false" sortProperty="uploadJobId">
				<!-- {prasad}changes for the  proxy redirect to login page error [start] -->
				<c:url var="jobUrl" value="/servlet/investigation/startApproveStatus/${row.uploadJobId}" />
				<a href="${jobUrl}"> <c:out value="${row.uploadJobId}" /></a>
					 
					<!--<a href="${prodUrl}/${row.uploadJobId}"> <c:out
							value="${row.uploadJobId}" />
					</a> 
				--><!-- {prasad}changes for the  proxy redirect to login page error [end] -->
				</display:column>
				<display:column property="transactionId" sortable="false"
					title="Mã ứng dụng" maxLength="30" />
				<display:column property="jobType" sortable="false"
					title="Loại giao dịch" maxLength="30" />
				<display:column property="investigationType" sortable="false"
					title="Loại điều tra" maxLength="30" >
			</display:column>
				<display:column property="dateOfApplication" title="Ngày nộp đơn" sortable="false" format="{0,date,dd-MMM-yyyy}" >
				</display:column>
				<display:column property="estDateOfCollectionString" sortable="false"
					title="Ngày phát hành" maxLength="30" />
				<display:column property="priorityString" sortable="false"
					title="Ưu tiên" maxLength="30" />
			</display:table>
		</c:if>

<!-- 			<br /> -->
<!-- 			<div id="after_table"> -->
<!-- 				<table class="innerTable"> -->
<!-- 					<tr> -->
<!-- 						<td style='padding-right: 8px' align="right"> -->
<%-- 						<c:if test="${empty jobList}"> --%>
<!-- 							<input type="button" -->
<!-- 							onclick="doSubmitNew(this.form);" class="button_grey" -->
<!-- 							 value="Điều tra mới" -->
<!-- 							 style="width: 120px;" />  -->
<%-- 						</c:if> --%>
<%-- 						<c:if test="${not empty jobList}"> --%>
<!-- 							<input type="button"  onclick="doSubmitNew1(this.form);" -->
<!-- 							class="btn_small btn-primary" -->
<!-- 							 value="Điều tra mới"  style="width: 120px;" />  -->
<%-- 						</c:if> --%>
<!-- 							&nbsp;</td> -->
<!-- 					</tr> -->
<!-- 				</table> -->
<!-- 			</div> -->
		</div>
	</div>
	</div>
	</div>
	</div>
<div id="dialog-confirm"></div>
	<script type="text/javascript">
		function doSubmitSave(form) {
			form.action = '<c:url value="/servlet/investigation/searchApprove" />';//TRUNG SỬA GỌI HÀM BỘ SEARCH
			form.submit();
		}
	</script>
	<script type="text/javascript">
	
		function doSubmitNew(form) {
			document.forms["form"].action = "${newUrl}";
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
		    	document.forms["form"].action = "${newUrl}";
				document.forms["form"].submit();
		    },
			Cancel: function() {
				$(this).dialog("close");
		    }
		   }
		});
		
		function doSubmitNew1(form) {
			 $("#dialog-confirm").dialog('option', 'title', 'Pending Jobs Status');
			 $("#dialog-confirm").html("There are Pending Jobs for Investigation? Do you want to continue?");
			 $("#dialog-confirm").dialog( 'open' );			
		}
	</script>
</form:form>



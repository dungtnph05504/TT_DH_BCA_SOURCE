<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<style>
<!--

-->
table#row > tr {
	height: 35px;
}
.displayTag tr.odd {
	height: 35px;
}
.displayTag tr.even {
	height: 35px;
}

</style>

<form:form modelAttribute="formData" name="formData">

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
       Danh sách chờ duyệt in</div>
       
       <div id="heading_right">
       <table width="100%" border="0" class="table_cont">
  <tr>
    <td width="53%" height="35" align="right">Mã giao dịch</td>
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
			Không bản ghi trong danh sách chờ duyệt in
			<br />
		</c:if>
		<c:if test="${not empty jobList}">
			<display:table cellspacing="1" cellpadding="0" id="row" sort="external"  partialList="true" size="${totalRecords}"
				export="false" class="displayTag table" name="jobList" defaultsort="1"
				defaultorder="ascending" pagesize="${pageSize}"
				requestURI="/servlet/investigation/getPersoJob"><!-- TRUNG SỬA PHÂN TRANG -->
				<display:column title="ID" sortable="false" sortProperty="uploadJobId">
				<!-- {prasad}changes for the  proxy redirect to login page error [start] -->
				<c:url var="jobUrl" value="/servlet/investigation/persoDetailId/${row.uploadJobId}" />
				<a href="${jobUrl}"> <c:out value="${row.uploadJobId}" /></a>
				</display:column>
				<display:column property="transactionId" sortable="false"
					title="Mã giao dịch" maxLength="30" />
				<display:column property="jobType" sortable="false"
					title="Loại giao dịch" maxLength="30" />
				<display:column property="investigationType" sortable="false"
					title="Loại điều tra" maxLength="30" >
			</display:column>
				<display:column property="dateOfApplication" title="Ngày nộp đơn" sortable="false" format="{0,date,dd-MMM-yyyy}" >
				</display:column>
				<display:column property="estDateOfCollectionString" sortable="false"
					title="Ngày trả kết quả" maxLength="30" />
				<display:column property="priorityString" sortable="false"
					title="Ưu tiên" maxLength="30" />
					<display:column title="Chọn" sortable="false">
									<form:checkbox path="selectedJobIds" value="${row.transactionId}"
										cssClass="${row.currentlyAssignedToAnInvestigationOfficer}" />
								</display:column>
		<%-- 		<display:column title="Chọn" sortable="false">
									<form:checkbox path="selectedJobIds" value="${row.uploadJobId}"
										cssClass="${row.currentlyAssignedToAnInvestigationOfficer}" />
				</display:column> --%>
			</display:table>
		</c:if>

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

	<div id="dialog-reject-getRemarks" title="Tạo danh sách bàn giao" style="display: none;"> 
			<div class="form-group">		
				<label for="codeHandover">Mã danh sách bàn giao</label>
				<input class="form-control"  id="codeHandover" name="codeHandover" />	
				<br />
				<label for="nameHandover">Tên danh sách bàn giao</label>
				<input class="form-control" id="nameHandover" name="nameHandover" />
				
			</div> 
	</div> 

	<c:url var="createHandover" value="/servlet/investigation/createHandover" />
	
<div id="dialog-confirm"></div>
	<script type="text/javascript">
		function doSubmitSave(form) {
			form.action = '<c:url value="/servlet/investigation/searchPersoTransactionId" />';//TRUNG SỬA GỌI HÀM BỘ SEARCH
			form.submit();
		}
		
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
		</script>
		
		<form:input    path="currentlyAssignedToUserId"                       type="hidden" name="currentlyAssignedToUserId"                       value="" />
	<form:input    path="unassignAllForUserUserId"                        type="hidden" name="unassignAllForUserUserId"                        value="" />
	<form:input    path="assignToId"                        type="hidden" name="assignToId"                        value="" />
</form:form>



<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url var="viewUrl" value="/servlet/nicMainEnquiry/viewDetails" />
<c:url var="url" value="/servlet/nicMainEnquiry/viewHistory" />
<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.9.1.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui.js"/>"></script>
<form:form id="nicHistoryForm" action="${viewUrl}" method="post" modelAttribute="nicHistory">
	<table>
		<tr>
			<td class="subjectsmall" width="200px">Mã giao dịch</td>
			<td class="subjectsmall" width="150px">CMND/CCCD</td>
			<td class="subjectsmall" width="50px">Tạo số</td>
			<td class="subjectsmall" width="50px">Cập nhật số</td>
			<td class="subjectsmall" width="150px">CCN</td>
			<td class="subjectsmall" width="200px">Ngày ứng dụng</td>
		</tr>
		<c:forEach var="history" items="${nicHistory}">
			<tr>
				<td width="200px" class='description'><a href="#"
					onclick="viewDtls('${history.id.transactionId}')"> <c:out
							value="${history.id.transactionId}" /></a></td>
				<td width="150px" class='description'>${history.nin}</td>
				<td width="80px" class='description'>${history.genNo}</td>
				<td width="80px" class='description'>${history.updateNo}</td>
				<td width="150px" class='description'>${history.ccn}</td>
				<td width="200px" class='description'>${history.dateOfApplication}</td>
			</tr>
		</c:forEach>
	</table>
	 <table style="width:100%;">
    	<tr>
        	<td align="center" style="padding: 20px; text-align: right;" >
		      <input type="button" class="button_grey" id="close_btn"  value="Close"/>
	        </td>
	    </tr>
      </table>
	
	<input type="hidden" id="currentURLView" name="currentURLView"
		value="${viewUrl}" />
</form:form>
<script type="text/javascript">
</script>
<script type="text/javascript">
	function viewDtls(txnId) {
		var url = $("#currentURLView").val() + "/" + txnId;
		//alert(url);
		document.forms["nicHistoryForm"].action = url;
		document.forms["nicHistoryForm"].method = "GET";
		document.forms["nicHistoryForm"].onSubmit = window
				.showModalDialog(url, null,
						"dialogWidth:1280px;dialogHeight:800px;resizable:yes;addressbar:no;");
	}

	$(function(){
		$("#close_btn").click(function(){
			 $("#dialog-approve").dialog('close');	
		});
		
	});
	
</script>
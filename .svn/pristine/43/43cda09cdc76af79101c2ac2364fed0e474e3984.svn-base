<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

<c:url var="requestPrint" value="/servlet/listHandover/showPdfResult" />

<style>
#dialog-continueSession{
	display: none !important;
}
</style>

<%
	try {
%>

<script type="text/javascript">
$(function() {
	$("#printCollectionSlipId").click(function(){ 
			var orig = $("#packID").val();
			//var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
				   var test = '${requestPrint}' + "/" + orig;  
				   window.open(test, "popup", "location=no,resizable=yes,left=0,height=800px,width=1280px");
	});
}); 

</script>
<div class="container">
	<div class="row">
		<div class="ov_hidden" style="text-align: center;">
			<img src="<c:url value="/resources/images/check-mark.png" />"
				style="margin-top: 30px;" alt="Alternate Text" />
			<h2 style="font-variant: unset;">Tạo danh sách bàn giao thành công</h2>


			<p class="text_pad" style="font-size: 15px;">
				<br>Đã tạo danh sách bàn giao đồng bộ Hộ chiếu sang Bộ công an thành công. 
				<br>Mã bàn giao: <span style="color: blue;font-size: 0.9em;font-weight: 700;">${code}</span>
			</p>

			<button type="button" class="btn btn-primary hidden-print"
				id="printCollectionSlipId" style="width: 160px;">
				<span class="glyphicon glyphicon-print"></span> In phiếu bàn giao
			</button>
			<input hidden="hidden" value="${code}" name="packID" id="packID" />
		</div>
	</div>
</div>

<%
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
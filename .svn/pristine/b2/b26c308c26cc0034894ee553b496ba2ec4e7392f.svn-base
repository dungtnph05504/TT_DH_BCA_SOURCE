<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:url var="paymentMatrixUrl" value="/servlet/paymentMatrixController/paymentMatrix" />
<c:url var="paymentDeleteUrl" value="/servlet/paymentMatrixController/paymentMatrixDelete" />
<style>
.fix-bottom-1 {
	margin-top: 15px;
}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NIC Payment Matrix</title>
</head>
<body>
	<div class="content_main">
		<div class="container">
        	<div class="row">
        		<div class="roundedBorder ov_hidden">
			<br />
				<div class="new-heading-2">CHỈNH SỬA THANH TOÁN</div>
				<div id="update_msg" style="display: none;">
					<img src="images/info1.jpg" width="30" height="30">&nbsp;<span
						style="font-size: 14px; color: #0000FF">Cập nhật thành công
				</div>
				<br />
				<form:form  modelAttribute="paymentMatrixDefForm"  commandName="paymentMatrixDefForm" id="paymentMatrixDefForm"  name="paymentMatrixDefForm" class="inline">
				<div class="col-md-12">
				<div class="col-md-6">
					<div class="col-md-4 fix-bottom-1">
						
						<label for="countryCode">Loại giao dịch:<i style="color: red">(*)</i></label>
					</div>
					<div class="col-md-8 fix-bottom-1">
						<form:input type="text" size="30" path="transactionType" id="transactionType" disabled="true" class="form-control"/>
					</div>
					<div class="col-md-4 fix-bottom-1">
						<label for="countryCode">Kiểu giao dịch:<i style="color: red">(*)</i></label>
					</div>
					<div class="col-md-8 fix-bottom-1">
						<form:input type="text" size="30" path="transactionSubtype" id="transactionSubtype" disabled="true" class="form-control"/>	
					</div>
					<div class="col-md-4 fix-bottom-1">
						<label for="countryCode">Số tiền phí giao dịch:<i style="color: red">(*)</i></label>
					</div>
					<div class="col-md-8 fix-bottom-1">
						<form:input type="text" size="10" path="feeAmount" id="feeAmount" class="form-control"/>
					</div>				
				 	 <div class="col-md-4 fix-bottom-1">
						<label for="countryCode">Giảm giá:</label>
					</div>
					<div class="col-md-8 fix-bottom-1" style="height: 30px;">
						<form:checkbox size="10" path="reduceRateFlag" id="reduceRateFlag" class="form-control"/>
					</div>					
					<div class="col-md-4 fix-bottom-1">
						<label for="countryCode">Xóa:</label>
					</div>
					<div class="col-md-8 fix-bottom-1" style="height: 30px;">
						<form:checkbox size="10" path="deleteFlag" id="deleteFlag" class="form-control"/>
					</div>			
			 </div>
          </div>
          <div class="col-md-12" style="text-align: right;margin-top: 30px;">
          		<button type="button" class="btn btn-danger" id="cancelBtn">
					<span class="glyphicon glyphicon-remove"></span> Hủy
				</button>
				<button type="button" class="btn btn-warning" id="deleteBtn">
					<span class="glyphicon glyphicon-trash"></span> Xóa
				</button>
				<button type="button" class="btn btn-primary" onclick="window.location.href='${requestScope['javax.servlet.forward.request_uri']}'" id="resetBtn">
					<span class="glyphicon glyphicon-refresh"></span> Làm mới
				</button>
				<button type="button" class="btn btn-success" id="update_btn">
					<span class="glyphicon glyphicon-ok"></span> Cập nhật
				</button>
				<!--<input type="button" id="cancelBtn" class="btn btn-danger" value="Hủy" />
				<input type="button" id="deleteBtn" class="btn btn-warning" value="Xóa" />
				<input type="button" id="resetBtn" class="btn btn-primary" onclick="window.location.href='${requestScope['javax.servlet.forward.request_uri']}'" value="làm mới" />
         		<input type="button" id="update_btn" class="btn btn-success" value="Cập nhật"/>-->
          </div>
					<!--<table style="width:100%;background-color:#E3F1FE" cellspacing="0"class="data_table_noborder" cellpadding="0">
						<tr>
							<td>Loại giao dịch:</td>
							<td> <form:input type="text" size="30" path="transactionType" id="transactionType" disabled="true"/>	
							</td>
						</tr>
						<tr>
							<td>Kiểu giao dịch:</td>
							<td> <form:input type="text" size="30" path="transactionSubtype" id="transactionSubtype" disabled="true"/>	
							</td>
						</tr>
						<tr id="tb_row">
							<td>Số tiền phí giao dịch:</td>
							<td><form:input type="text" size="10" path="feeAmount" id="feeAmount" /></td>
						</tr>
						<tr>
							<td>Giảm giá:</td>
							<td><form:checkbox size="10" path="reduceRateFlag" id="reduceRateFlag" /></td>
						</tr>
						<tr style="display: none">
							<td>Lá cờ dễ tha:</td>
							<td><form:checkbox size="10" path="waivableFlag" id="waivableFlag" /></td>
						</tr>
						<tr>
							<td>Xóa:</td>
							<td><form:checkbox size="10" path="deleteFlag" id="deleteFlag" /></td>
						</tr>
					<tr style="display: none">
							<td>Số lần bị mất:</td>
							<td><form:input type="text" size="30" path="noOfTimeLost" id="noOfTimeLost" disabled="true"/>
							</td>
						</tr>
						<!--<tr><td>System Id:</td><td><input type="text" size="30" value="RIC"/></td></tr>
					</table>-->
					<form:input type="hidden" id="updateBy" path="updateBy" value="${sessionScope.userSession.userName}" />
				</form:form>
					<!--<table style="text-align: right;" class="displayTag">
						<tr>
							<td align="right" style="padding: 10px;text-align: right;"><input
								type="button" id="update_btn" class="button_grey" value="Cập nhật"> &nbsp;
								 <input onclick="window.location.href='${requestScope['javax.servlet.forward.request_uri']}'" type="button" class="button_grey" id="resetBtn" value="Làm lại" />
								&nbsp;
							<input type="button"  class="button_grey" id="deleteBtn" value="Xóa" />
	 &nbsp; <input type="button"  class="button_grey" 	id="cancelBtn" value="Hủy" /></td>
						</tr>
					</table>-->

	</div>
</div>
</div>
</div>
<div id="savedialog-confirm"></div>
<div id="faildialog-confirm"></div>
<div id="dialog-confirm"></div>
	<script>
		
		$(function() {
			  $("#update_btn").click(function(){
				  
				  if(!IsNumeric($("#feeAmount").val())){
						//alert("Số tiền phí không hợp lệ");
						 $.alert({
							title: 'Thông báo',
							content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Số tiền phí không hợp lệ!",
						});
						 return false;
					}
				  
				     document.getElementById('noOfTimeLost').disabled=false;
				     document.getElementById('transactionType').disabled=false;
				     document.getElementById('transactionSubtype').disabled=false;
					 
					 $.post('<c:url value="/servlet/paymentMatrixController/paymentMatrixUpdate" />',$('#paymentMatrixDefForm').serialize(),
				             function(data){
				   if(data=='success'){
				    	$("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#savedialog-confirm").html('Đã cập nhật thành công ma trận thanh toán :  '+$("#transactionType").val());
				       $("#savedialog-confirm").dialog( 'open' );
				       
				    }else if(data=='fail'){
				     $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
				     $("#faildialog-confirm").html('Không thể tạo Ma trận thanh toán :  '+$("#transactionType").val());
				     $("#faildialog-confirm").dialog( 'open' );
				    }
					/*  document.getElementById('documentId').disabled=true;
					 document.getElementById('transactionType').disabled=true;
				     document.getElementById('transactionSubtype').disabled=true; */
					 }); 
				});
			  
			$("#createDate").datepicker({
				showOn : "button",
				buttonImage : "images/calendar.gif",
				buttonImageOnly : true,
				dateFormat : 'dd/mm/yy'
			});
			$("#updateDate").datepicker({
				showOn : "button",
				buttonImage : "images/calendar.gif",
				buttonImageOnly : true,
				dateFormat : 'dd/mm/yy'
			});
			
			$("#cancelBtn").click(function(){	
				 document.forms["paymentMatrixDefForm"].action = '${paymentMatrixUrl}';
				 document.forms["paymentMatrixDefForm"].submit();	

			});
			
			$("#deleteBtn").click(function(){	
			  	var transactionType = $('#transactionType').val();
				
			  $("#dialog-confirm").dialog('option', 'title', 'Xóa thanh toán');
			    $("#dialog-confirm").html("Bạn có chắc muốn xóa ma trận thanh toán với : "+ transactionType +"?");
			    $("#dialog-confirm").dialog( 'open' );
			    
				 /* document.getElementById('documentId').disabled=true;
				 document.getElementById('transactionType').disabled=true;
			     document.getElementById('transactionSubtype').disabled=true; */

			});
		});
		
		 function IsNumeric(sText) {
				var ValidChars = "0123456789.";
				var IsNumber=true;
				var Char;
				
				if(sText !="" && sText !=null){
				for (i = 0; i < sText.length && IsNumber == true; i++) 
				{ 
					Char = sText.charAt(i); 
					if (ValidChars.indexOf(Char) == -1) 
					{
					IsNumber = false;
					}
				}
			   }else{
				IsNumber = false;
			   }
				
			    return IsNumber;
			}	
	</script>

	<!-- script for the delete for payment parameter-->
	<script>
		$(function() {
			
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
				    	$(this).dialog("close");
				    	document.getElementById('noOfTimeLost').disabled=false;
					     document.getElementById('transactionType').disabled=false;
					     document.getElementById('transactionSubtype').disabled=false;
						document.forms["paymentMatrixDefForm"].action = '${paymentDeleteUrl}';
						//document.forms["paymentMatrixDefForm"].action = '${proofDocMatrixMain}';
						
						 document.forms["paymentMatrixDefForm"].submit();
				    },
					Cancel: function() {
						$(this).dialog("close");
				    }
				   }
				    });
			 
			 
			  
			  $("#savedialog-confirm" ).dialog({
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
				    	$(this).dialog("close");
				    	  document.forms["paymentMatrixDefForm"].action = '${paymentMatrixUrl}';
				    	  document.forms["paymentMatrixDefForm"].submit();
				    }
				   }
			});
			 
			 $( "#faildialog-confirm" ).dialog({
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
				    	$(this).dialog("close");
				    }
				   }
				    });
		});
	</script>

</body>

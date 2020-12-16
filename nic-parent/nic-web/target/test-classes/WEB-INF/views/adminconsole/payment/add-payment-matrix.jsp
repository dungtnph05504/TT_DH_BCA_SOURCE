<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="resetPage" value="/servlet/paymentMatrixController/addPaymentMatrix" />
<c:url var="paymentMatrixUrl" value="/servlet/paymentMatrixController/paymentMatrix" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NIC Payment Matrix</title>
</head>
<style>
tr {
	height: 40px;
}

.fix-td-1 {
	width: 25%;
    padding-left: 10px;
}
tr {
	height: 35px;
}
.displayTag tr.odd {
	height: 35px;
}
.displayTag tr.even {
	height: 35px;
}
.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
    white-space: pre-wrap;
}
.fix-bottom-1 {
	margin-top: 15px;
}
</style>
<script type="text/javascript">
      $(function() {
    	  
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
		 
  $('#add_btn').click(function() {
		  var noOfTimeLost = $("#noOfTimeLost").val();
		  if(noOfTimeLost == ''){
			  $.alert({
					title: 'Thông báo',
					content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Vui lòng nhập giá trị cho số lần bị mất",
				});
			  //alert('Please enter a value for No Of Time Lost');
			  return false;
		  }
		  
		  if(!IsNumber($("#noOfTimeLost").val())){
			  $.alert({
					title: 'Thông báo',
					content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Số lần bị mất không hợp lệ",
				});
				//alert("Invalid No. Of Time Lost value");
				 return false;
			}
		  
		  if(!IsNumeric($("#feeAmount").val())){
			  $.alert({
					title: 'Thông báo',
					content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Số tiền phí không hợp lệ",
				});
				//alert("Số tiền phí không hợp lệ");
				 return false;
			}
		  
		  
		  var transactionType = $("#transactionType").val()
		  if(transactionType == ''){
			 // alert('Vui lòng chọn loại giao dịch');
			 $.alert({
					title: 'Thông báo',
					content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Vui lòng chọn loại giao dịch",
				});
			  return false;
		  }
		  
		  var transactionSubtype = $("#transactionSubtype").val()
		  if(transactionSubtype == ''){
			  //alert('Vui lòng chọn giao dịch phụ');
			   $.alert({
					title: 'Thông báo',
					content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Vui lòng chọn giao dịch phụ",
				});
			  return false;
		  }
		  
		  
		  $.post('<c:url value="/servlet/paymentMatrixController/paymentMatrixCreate" />',$('#paymentMatrixDefForm').serialize(),
		             function(data){
		   if(data=='success'){
		   	   $("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
		       $("#savedialog-confirm").html('Đã tạo thành công Ma trận thanh toán :  '+$("#transactionType").val());
		       $("#savedialog-confirm").dialog( 'open' );
		    }else if(data=='fail'){
		       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
		       $("#faildialog-confirm").html('Không thể tạo Ma trận thanh toán:  '+$("#transactionType").val());
		       $("#faildialog-confirm").dialog( 'open' );
		    }else if(data=='alreadyExists'){
			   $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
			   $("#faildialog-confirm").html('Ma trận thanh toán đã tồn tại cho Số lần bị mất:'+$("#noOfTimeLost").val()+", Loại giao dịch:"+$("#transactionType").val()+", Loại giao dịch phụ:"+$("#transactionSubtype").val());
			   $("#faildialog-confirm").dialog( 'open' );
			}
		  
	  });
  });
  
  $("#cancelBtn").click(function(){	
		 document.forms["paymentMatrixDefForm"].action = '${paymentMatrixUrl}';
		 document.forms["paymentMatrixDefForm"].submit();	

	});
  
  $("#resetBtn").click(function(){
		
		 document.forms["paymentMatrixDefForm"].action = '${resetPage}';
		 document.forms["paymentMatrixDefForm"].submit();
		
	});
  
  });
  $(function() {
	$( "#createDate" ).datepicker({
	showOn: "button",
	buttonImage: "images/calendar.gif",
	buttonImageOnly: true,
	dateFormat: 'dd/mm/yy'
	});
	$( "#updateDate" ).datepicker({
	showOn: "button",
	buttonImage: "images/calendar.gif",
	buttonImageOnly: true,
	dateFormat: 'dd/mm/yy'
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
  
  function IsNumber(sText) {
		var ValidChars = "0123456789";
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
<body>

<div class="content_main">
<div class="container">
          <div class="row">
                            <div class="roundedBorder ov_hidden">
<div class="new-heading-2">THÊM PHÍ</div>
		&nbsp;
		<div id="update_msg" style="display: none;"><img src="images/info1.jpg" width="30" height="30">&nbsp;<span style="font-size: 14px; color: #0000FF">Ma trận Thanh toán đã được lưu thành công</div>
				
     <!--********************customized code for now***************************************--> 
   <div id="admin_console_info_div" style="display:table-cell;vertical-align:top;" ></div><br/>
<form:form  modelAttribute="paymentMatrixDefForm"  commandName="paymentMatrixDefForm" id="paymentMatrixDefForm"  name="paymentMatrixDefForm" class="inline">
			<div class="col-md-12">
			<div class="col-md-6">
				<div class="col-md-4 fix-bottom-1">
					
					<label for="countryCode">Loại giao dịch:<i style="color: red">(*)</i></label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<form:select path="transactionType" id="transactionType" class="form-control">
						<form:option value="">-- SELECT -- </form:option>
						<form:options items="${transactionTypes}" itemLabel="codeValueDesc" itemValue="id.codeValue"></form:options>
					</form:select>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Kiểu giao dịch:<i style="color: red">(*)</i></label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<form:select path="transactionSubtype" id="transactionSubtype" class="form-control">
						<form:option value="">-- SELECT -- </form:option>
						<form:options items="${transactionSubTypes}" itemLabel="codeValueDesc" itemValue="id.codeValue"></form:options>
					</form:select>
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
					<label for="countryCode">Lá cờ dễ tha:</label>
				</div>
				<div class="col-md-8 fix-bottom-1" style="height: 30px;">
					<form:checkbox size="10" path="waivableFlag" id="waivableFlag" class="form-control"/>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Xóa:</label>
				</div>
				<div class="col-md-8 fix-bottom-1" style="height: 30px;">
					<form:checkbox size="10" path="deleteFlag" id="deleteFlag" class="form-control"/>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Số lần bị mất:</label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<form:input type="text" size="30" path="noOfTimeLost" id="noOfTimeLost"  class="form-control"/>
				</div>
			 </div>
          </div>
          <div class="col-md-12" style="text-align: right;margin-top: 30px;">
				<button type="button" class="btn btn-danger"   id="cancelBtn">
					<span class="glyphicon glyphicon-remove"></span> Hủy
				</button>
				<button type="button" class="btn btn-primary" id="resetBtn">
					<span class="glyphicon glyphicon-refresh"></span> Làm mới
				</button>
				<button type="button" class="btn btn-success" id="add_btn">
					<span class="glyphicon glyphicon-ok"></span> Lưu
				</button>
				<!--<input type="button" id="cancelBtn" class="btn btn-danger" value="Hủy" />
				<input type="button" id="resetBtn" class="btn btn-primary" value="làm mới" />
         		<input type="button" id="add_btn" class="btn btn-success" value="Lưu"/>-->
          </div>
					<!--<table style="width:100%;background-color:#E3F1FE" cellspacing="0"class="data_table_noborder tht-table" cellpadding="0">
						<tr>
							<td class="fix-td-1">Loại giao dịch:</td>
							<td> 
								<form:select style="width: 220px;" path="transactionType" id="transactionType">
									<form:option value="">-- SELECT -- </form:option>
									<form:options items="${transactionTypes}" itemLabel="codeValueDesc" itemValue="id.codeValue"></form:options>
								</form:select>
							</td>
						</tr>
						<tr>
							<td class="fix-td-1">Kiểu giao dịch:</td>
							<td> 
								<form:select style="width: 220px;" path="transactionSubtype" id="transactionSubtype">
									<form:option value="">-- SELECT -- </form:option>
									<form:options items="${transactionSubTypes}" itemLabel="codeValueDesc" itemValue="id.codeValue"></form:options>
								</form:select>
							</td>
						</tr>
						<tr id="tb_row">
							<td class="fix-td-1">Số tiền Phí Giao dịch:</td>
							<td><form:input type="text" size="10" path="feeAmount" id="feeAmount" style="width: 220px;"/></td>
						</tr>
						<tr>
							<td class="fix-td-1">Giảm giá:</td>
							<td><form:checkbox size="10" path="reduceRateFlag" id="reduceRateFlag" /></td>
						</tr>
						<tr style="display: false">
							<td class="fix-td-1">Lá cờ dễ tha:</td>
							<td><form:checkbox size="10" path="waivableFlag" id="waivableFlag" /></td>
						</tr>
						<tr>
							<td class="fix-td-1">Xóa:</td>
							<td><form:checkbox size="10" path="deleteFlag" id="deleteFlag" /></td>
						</tr>
						<tr style="display: false">
							<td class="fix-td-1">Số lần bị mất:</td>
							<td><form:input type="text" size="30" path="noOfTimeLost" id="noOfTimeLost" style="width: 220px;"/>
							</td>
						</tr>
						<!--<tr><td>System Id:</td><td><input type="text" size="30" value="RIC"/></td></tr>
					</table>-->
					<form:input type="hidden" id="createBy" path="createBy" value="${sessionScope.userSession.userName}" />
				</form:form>
	<!--<table style="text-align: right;" >
	<tr><td colspan="2" align="right" style="padding: 10px;text-align: center;">
	<input id="add_btn" type="button" class="button_grey" value="Lưu" style="width: 60px;"/> &nbsp;
	 <input type="button" class="button_grey" id="resetBtn" value="Làm lại" style="width: 60px;"/> &nbsp;
	<input type="button"  class="button_grey" 	id="cancelBtn" value="Hủy" style="width: 60px;"/>
	
	</td></tr>
	</table>-->
	</div>
	</div>
	</div>
</div>	
<div id="savedialog-confirm" style="display: none;"></div>
<div id="faildialog-confirm" style="display: none;"></div>
</body>
</html>

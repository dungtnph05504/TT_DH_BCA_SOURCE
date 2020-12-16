 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="resetPage" value="/servlet/officeNationController/addOfficalNation" />
<c:url var="officalNationUrl" value="/servlet/officeNationController/officalNationList" />
<c:url var="psUrl" value="/servlet/officeNationController/personList" />
<c:url var="checkVisaUrl" value="/servlet/officeNationController/checkNationVisa" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script> -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NIC Công hàm</title>
</head>

<script type="text/javascript">
var imgBase = "";
var stringUrl = "";

	function readURL(input) {
		/* if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function (e) {
				imgBase = e.target.result;
			};
			reader.readAsDataURL(input.files[0]);
		} */
		
		/* var reader = new FileReader();
		var fileByteArray = [];
		reader.readAsArrayBuffer(input.files[0]);
		reader.onloadend = function (evt) {
		    if (evt.target.readyState == FileReader.DONE) {
		       var arrayBuffer = evt.target.result,
		           array = new Uint8Array(arrayBuffer);
		       for (var i = 0; i < array.length; i++) {
		           fileByteArray.push(array[i]);
		        }
		    }
		} */
		var input = document.getElementById("dataFile");
		var files = input.files;
	    // Pass the file to the blob, not the input[0].
	    fileData = new Blob([files[0]]);
	    // Pass getBuffer to promise.
	    var promise = new Promise(getBuffer);
	    // Wait for promise to be resolved, or log error.
	    promise.then(function(data) {
	      // Here you can pass the bytes to another function.
	      output.innerHTML = data.toString();
	      //console.log(data);
	      
	      $("#dataF").val(data);
	    })
	    
	    
		
	}
	
	function ClearDDL(){
		$("#businessID").empty();
		$('#businessID')
        .append($("<option></option>")
                   .attr("value","")
                   .text("--SELECT--")); 
	}
	
    $(function() {
    	$("#nationCode").change(function(){
    		var id = $("#nationCode").val();
    		var code = $("#passportType").val();
    		 $("#noteVisa").css("display","none");
   		  $("#validFrom").html("");
   		  $("#validTo").html("");
   		  $("#stayday").html("");
   		  $("#passportExpiredday").html("");
   		  $("#freeday").html("");
   		  $("#maxLastemmiday").html("");
    		if(id != "" && code != ""){
	    	  	$.ajax({
	    		      type: "GET",
	    		      url: "${checkVisaUrl}", 
	    		      data: {id: id, code: code},
	    		      dataType: "json",
	    		      contentType: "application/json; charset=utf-8",
	    		      success: function(response) {
	    		    	  if(response != null){
	    		    		  $("#noteVisa").css("display","block");
	    		    		  if(response.validFrom != ""){
	    		    			  var vF = new Date(response.validFrom).toLocaleDateString("en-US");
	    		    		  	$("#validFrom").html(vF);
	    		    		  }
	    		    		  if(response.validTo != ""){
	    		    			  var vT = new Date(response.validTo).toLocaleDateString("en-US");
	    		    		  	$("#validTo").html(response.validTo);
	    		    		  }
	    		    		  $("#stayday").html(response.stayday);
	    		    		  $("#passportExpiredday").html(response.passportExpiredday);
	    		    		  $("#freeday").html(response.freeday);
	    		    		  $("#maxLastemmiday").html(response.maxLastemmiday);
	    		    	  }
	    	          },
	    	          error: function(e) {
	    	              //alert('Error: ' + e);
	    	          } 
	    		});
    		}
      	});
    	
    	$("#passportType").change(function(){
    		var id = $("#nationCode").val();
    		var code = $("#passportType").val();
    		$("#noteVisa").css("display","none");
  		  $("#validFrom").html("");
  		  $("#validTo").html("");
  		  $("#stayday").html("");
  		  $("#passportExpiredday").html("");
  		  $("#freeday").html("");
  		  $("#maxLastemmiday").html("");
    		if(id != "" && code != ""){
	    	  	$.ajax({
	    		      type: "GET",
	    		      url: "${checkVisaUrl}", 
	    		      data: {id: id, code: code},
	    		      dataType: "json",
	    		      contentType: "application/json; charset=utf-8",
	    		      success: function(response) {
	    		    	  if(response != null){
	    		    		  $("#noteVisa").css("display","block");
	    		    		  if(response.validFrom != ""){
	    		    			  var vF = new Date(response.validFrom).toLocaleDateString("en-US");
	      		    		  	$("#validFrom").html(vF);
	      		    		  }
	      		    		  if(response.validTo != ""){
	      		    			 var vT = new Date(response.validTo).toLocaleDateString("en-US");
	     		    		  	$("#validTo").html(response.validTo);
	      		    		  }
	      		    		  $("#stayday").html(response.stayday);
	      		    		  $("#passportExpiredday").html(response.passportExpiredday);
	      		    		  $("#freeday").html(response.freeday);
	      		    		  $("#maxLastemmiday").html(response.maxLastemmiday);
	    		    	  }
	    	          },
	    	          error: function(e) {
	    	              //alert('Error: ' + e);
	    	          } 
	    		});
    		}
      	});
    	
    	$("#decisionNumber").change(function(){
    		ClearDDL();
    		var id = $("#decisionNumber").val();
    	  	$.ajax({
    		      type: "GET",
    		      url: "${psUrl}", 
    		      data: {id: id},
    		      dataType: "json",
    		      contentType: "application/json; charset=utf-8",
    		      success: function(response) {
    		    	  if(response != null){
    		    		  
	    		    	  for (var i = 0; i < response.length; i++) {
	    		    	  $('#businessID')
	    		          .append($("<option></option>")
	    		                     .attr("value",response[i].id)
	    		                     .text(response[i].fullname)); 
	    		    	  }
    		    	  }
    	          },
    	          error: function(e) {
    	              //alert('Error: ' + e);
    	        	  $.alert({
  						title: 'Thông báo',
  						content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/error_1a.png\">" + " Đã có lỗi xảy ra: " + e,
  					});
    	          } 
    		});
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
			    	  document.forms["officalNationForm"].action = '${officalNationUrl}';
			    	  document.forms["officalNationForm"].submit();
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
	  
	  
	  if(checkValid()){
		  ///var base64ImageContent = imgBase.replace(/^data:image\/(png|jpg|jpeg);base64,/, "");

	  	  stringUrl = $('#officalNationForm').serialize(); /* + "&docString="+ encodeURIComponent(base64ImageContent) */;
	
		  $.post('<c:url value="/servlet/paymentMatrixController/createOfficalNation" />',stringUrl,
		             function(data){
		   if(data=='success'){
		   	   $("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
		       $("#savedialog-confirm").html('Đã tạo thành công Tờ khai Công hàm :  '+$("#officalNationNo").val());
		       $("#savedialog-confirm").dialog( 'open' );
		    }else if(data=='duplicate'){
		       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
		       $("#faildialog-confirm").html('Số công hàm:  '+$("#officalNationNo").val() + ' đã tồn tại.');
		       $("#faildialog-confirm").dialog( 'open' );
		    }
		    else {
			       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
			       $("#faildialog-confirm").html('Không thể tạo Tờ khai Công hàm:  '+$("#officalNationNo").val());
			       $("#faildialog-confirm").dialog( 'open' );
			}
	  	});
	  }
	  else {
		   $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
	       $("#faildialog-confirm").html('Nhập đầy đủ thông tin có dấu (*)');
	       $("#faildialog-confirm").dialog( 'open' );
	  }
  });
  
  $("#cancelBtn").click(function(){	
		 document.forms["officalNationForm"].action = '${officalNationUrl}';
		 document.forms["officalNationForm"].submit();	

	});
  
  $("#resetBtn").click(function(){
		 document.forms["officalNationForm"].action = '${resetPage}';
		 document.forms["officalNationForm"].submit();
	});
  });
  $(function() {
	  
	  $("#signDate").datepicker({         
		  autoclose: true,         
		  todayHighlight: true,
		 dateFormat: 'dd/mm/yy'
	});
  
/* 	$( "#signDate" ).datepicker({
	showOn: "button",
	buttonImage: "/eppcentral/resources/images/icon_welcome/calendar.gif",
	buttonImageOnly: true
	}); */
	$( "#updateDate" ).datepicker({
		showOn: "button",
		buttonImage: "/eppcentral/resources/images/icon_welcome/calendar.gif",
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

  	$(document).ready(function() {
	    $('.js-example-basic-multiple').select2();
	    $('.js-example-basic-single').select2();
	});
  	
  	function checkValid(){
  		var d1 =  $('#officalNationNo').val();
  		var d2 =  $('#nationCode').val();
  		var d3 =  $('#decisionNumber').val();
  		var d4 =  $('#businessID').val();
  		var d5 =  $('#passportType').val();
  		/* var xp = document.getElementById('nationCode');
  		var codeP = "";
  		for (var k = 0; k < xp.options.length; k++ )
  		{
			  if(xp.options[k].selected){
				 d4 = codeP + xp.options[k].value + ',';
			  }
		} 
  		
  		var xp1 = document.getElementById('decisionNumber');
  		var codeP1 = "";
  		for (var k1 = 0; k1 < xp1.options.length; k1++ )
  		{
			  if(xp1.options[k1].selected){
				 d2 = codeP1 + xp1.options[k1].value + ',';
			  }
		} 
  		
  		var xp2 = document.getElementById('businessID');
  		var codeP2 = "";
  		for (var k2 = 0; k2 < xp2.options.length; k2++ )
  		{
			  if(xp2.options[k2].selected){
				 d3 = codeP2 + xp2.options[k2].value + ',';
			  }
		}  */
  		
  		if(d1 != "" && d1 != null && d2 != "" && d2 != null && d3 != "" && d3 != null
  				&& d4 != "" && d4 != null && d5 != "" && d5 != null) 
  		{
  			return true;
  		}
  		return false;
  	}
  	
  	var isShift = false;
  	var seperator = "/";
  	

  	function IsNumeric(input, keyCode) {
  	    if (keyCode == 16) {
  	        isShift = true;
  	    }
  	    //Allow only Numeric Keys.
  	    if (((keyCode >= 48 && keyCode <= 57) || keyCode == 8 || keyCode <= 37 || (keyCode <= 39) || (keyCode >= 96 && keyCode <= 105)) && isShift == false) {
  	        if ((input.value.length == 2 || input.value.length == 5) && keyCode != 8) {
  	            input.value += seperator;
  	        }

  	        return true;
  	    }
  	    else {
  	        return false;
  	    }
  	};
  	
  	function ValidateDateFormat(input, keyCode) {
  	    var dateString = input.value;
  	    if (keyCode == 16) {
  	        isShift = false;
  	    }
  	    var regex = /(((0|1)[0-9]|2[0-9]|3[0-1])\/(0[1-9]|1[0-2])\/((19|20)\d\d))$/;

  	    //Check whether valid dd/MM/yyyy Date Format.
  	    if (regex.test(dateString) || dateString.length == 0) {
  	        //ShowHideError(input, "none");
  	    } else {
  	       // demo.showNotification('bottom', 'right', 2, "Định dạng nhập dữ liệu ngày tháng chưa chính xác (DD/MM/YYYY)");
  	    }
  	};
  	
	</script>
<body>

	<div class="content_main">
		<div class="container">
			<div class="row">
				<div class="roundedBorder ov_hidden">
					<div class="new-heading-2">THÊM TỜ KHAI CÔNG HÀM MỚI</div>
					&nbsp;
					<div id="update_msg" style="display: none;">
						<!-- <img src="images/info1.jpg" width="30" height="30"> -->&nbsp;
						<span style="font-size: 14px; color: #0000FF">Công hàm mới đã được lưu thành công
					</div>

					<!--********************customized code for now***************************************-->
					<div id="admin_console_info_div"
						style="display: table-cell; vertical-align: top;"></div>
					<br />
					<form:form modelAttribute="officalNationForm"
						commandName="officalNationForm" id="officalNationForm"
						name="officalNationForm" class="inline" enctype="multipart/form-data">
						<div class="col-sm-6">
							<div class="form-group">
								<label for="officalNationNo">Số tờ khai<i style="color: red">(*)</i>: </label>
								<form:input class="form-control" type="text"
									path="officalNationNo" id="officalNationNo" />
							</div>
							<%-- <div class="form-group">
								<label for="decisionNumber">Số quyết định<i style="color: red">(*)</i>:
								</label>
								<form:input class="form-control" type="text"
									path="decisionNumber" id="decisionNumber" />
							</div> --%>
							<div class="form-group">
								<label for="decisionNumber">Số quyết định<i style="color: red">(*)</i>:</label>
								<form:select style="width: 100%;" path="decisionNumber" id="decisionNumber"
									class="js-example-basic-single">
									<form:option value="">-- SELECT -- </form:option>
									<form:options items="${decisionList}" itemLabel="decisionNumber"
										itemValue="decisionNumber"></form:options>
								</form:select>
							</div>
							<div class="form-group">
								<label for="businessID">Chọn người cử đi công tác<i style="color: red">(*)</i>:</label>
								<form:select style="width: 100%;" path="businessID" id="businessID"
									class="js-example-basic-single">
									<form:option value="">-- SELECT -- </form:option>
									<%-- <form:options items="${businessList}" itemLabel="fullname"
										itemValue="id"></form:options> --%>
								</form:select>
							</div>
							<div class="form-group">
								<label for="nationCode">Chọn quốc gia<i style="color: red">(*)</i>:</label>
								<form:select style="width: 100%;" path="nationCode" id="nationCode"
									class="js-example-basic-single">
									<form:option value="">-- SELECT -- </form:option>
									<form:options items="${codeNational}" itemLabel="codeValueDesc"
										itemValue="id.codeValue"></form:options>
								</form:select>
								<div id="noteVisa" style="display:none;margin-top: 10px;">
									<i style="color:green;margin: 5px;" >(*) Có dữ liệu Quốc gia miễn thị thực</i>
									<p style="margin: 5px;">- Thời hạn từ <i id="validFrom"></i> đến <i id="validTo"></i></p>
									<p style="margin: 5px;">- Số ngày tối đa: <i id="stayday"></i> ngày</p>
									<p style="margin: 5px;">- Ngày nhập cảnh tối thiểu: <i id="passportExpiredday"></i> ngày</p>
									<p style="margin: 5px;">- Số ngày miễn thị thực: <i id="freeday"></i> ngày</p>
									<p style="margin: 5px;">- Ngày nhập cảnh gần nhất tối thiểu: <i id="maxLastemmiday"></i> ngày</p>
								</div>
							</div>
							<div class="form-group">
								<label for="passportType">Loại hộ chiếu<i style="color: red">(*)</i>: </label>
								<form:select style="width: 100%;" path="passportType" id="passportType"
									class="js-example-basic-single">
									<form:option value="">-- SELECT -- </form:option>
									<form:option value="P">Phổ thông</form:option>
									<form:option value="PD">Ngoại giao</form:option>
									<form:option value="PO">Công vụ</form:option>
								</form:select>
							</div>
							
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label for="passportNo">Số hộ chiếu:</label>
								<form:input class="form-control" type="text"
									path="passportNo" id="passportNo" />
							</div>
							<div class="form-group">
								<label for="passportNo">Ngày cấp:</label>
								<form:input class="form-control" type="text"
									path="passportIss" id="passportIss" />
							</div>
							<div class="form-group">
								<label for="passportNo">Ngày hết hạn:</label>
								<form:input class="form-control" type="text"
									path="passportExp" id="passportExp" />
							</div>
							<div class="form-group">
								<label for="passportNo">Cơ quan cấp:</label>
								<form:select style="width: 100%;" path="passportIssuePlace" id="passportIssuePlace"
									class="js-example-basic-single">
									<form:option value="">-- SELECT -- </form:option>
									<form:options items="${issPlddl}" itemLabel="codeValueDesc"
										itemValue="id.codeValue"></form:options>
								</form:select>
								<%-- <form:input class="form-control" type="text"
									path="passportIssuePlace" id="passportIssuePlace" /> --%>
							</div>
							
							<%-- <div class="form-group">
								<label for="visaNo">Số Visa: </label>
								<form:input class="form-control" type="text" path="visaNo" id="visaNo" />
							</div> --%>
							<!-- <div class="form-group">
								<label for="dataFile">Tải tài liệu công văn:</label> <input
									class="custom-file-input" type="file" name="dataFile"
									id="dataFile"onchange="readURL(this);"/>
							</div> -->
							<div class="form-group">
								<label for="description">Mô tả thêm:</label>
								<form:textarea class="form-control" type="text"
									path="description" id="description" rows="7"></form:textarea>
							</div>
						</div>
						<form:input type="hidden" id="createBy" path="createBy"
							value="${sessionScope.userSession.userName}" />
					</form:form>
					<table style="text-align: right;">
						<tr>
							<td colspan="2" align="right" style="padding: 10px; text-align: right;">
								<button type="button" class="btn btn-danger" id="cancelBtn">
									<span class="glyphicon glyphicon-remove"></span> Hủy
								</button>
								<button type="button" class="btn btn-primary" id="resetBtn">
									<span class="glyphicon glyphicon-refresh"></span> Làm mới
								</button>
								<button type="button" class="btn btn-success" id="add_btn">
									<span class="glyphicon glyphicon-ok"></span> Lưu
								</button>
								<!--<input type="button" class="btn btn-danger" id="cancelBtn" value="Hủy"/>
								<input type="button" class="btn btn-primary" id="resetBtn" value="Làm mới"  /> 
								<input id="add_btn" type="button" class="btn btn-success" value="Lưu" />-->
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="savedialog-confirm" style="display: none;"></div>
	<div id="faildialog-confirm" style="display: none;"></div>
</body>
</html>

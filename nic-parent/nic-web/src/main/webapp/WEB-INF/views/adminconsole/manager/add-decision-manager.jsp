 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="resetPage" value="/servlet/decisionController/addDecisionManager" />
<c:url var="decisionManagerUrl" value="/servlet/decisionController/decisionManagerList" />
<c:url var="psUrl" value="/servlet/decisionController/signerList" />
<c:url var="wpEUrl" value="/servlet/decisionController/writePositionEng" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script> -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NIC Quyết định người cử đi công tác</title>
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
		$("#signer").empty();
		$('#signer')
        .append($("<option></option>")
                   .attr("value","")
                   .text("--SELECT--")); 
	}
	
    $(function() {
	    	$("#competentAuthorities").change(function(){
	    		ClearDDL();
	    		var id = $("#competentAuthorities").val();
	    	  	$.ajax({
	    		      type: "GET",
	    		      url: "${psUrl}", 
	    		      data: {id : id},//JSON.stringify(id),
	    		      dataType: "json",
	    		      contentType: "application/json; charset=utf-8",
	    		      success: function(response) {
	    		    	  if(response != null && response.length > 0){
		    		    	  for (var i = 0; i < response.length; i++) {
			    		    	  $('#signer')
			    		          .append($("<option></option>")
			    		                     .attr("value",response[i].codeSigner)
			    		                     .text(response[i].nameSigner)); 
		    		    	  }
	    		    	  }
	    	          },
	    	          error: function(e) {
	    	             //alert('Error: ' + e);
	    	          } 
	    		});
	    		
	    		$.ajax({
	    		      type: "GET",
	    		      url: "${wpEUrl}", 
	    		      data: {id : id},//JSON.stringify(id),
	    		      dataType: "text",
	    		      contentType: "application/json; charset=utf-8",
	    		      success: function(response) {
	    		    	  if(response != null && response != ""){
	    		    		  $("#competentAuthoritiesEng").val(response);
	    		    	  }
	    	          },
	    	          error: function(e) {
	    	             //alert('Error: ' + e);
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
			    	  document.forms["decisionManagerForm"].action = '${decisionManagerUrl}';
			    	  document.forms["decisionManagerForm"].submit();
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
	  
	  var x = document.getElementById('transitNationCode');
	  var codeT = "";
	  for ( var i = 0; i < x.options.length; i++ )
	  {
		  if(x.options[i].selected){
		  codeT = codeT + x.options[i].value + ',';
		  }
	  }
	  $('#transitNation').val(codeT);
	  
	  var xp = document.getElementById('countryPlanCode');
	  var codeP = "";
	  for ( var k = 0; k < xp.options.length; k++ )
	  {
		  if(xp.options[k].selected){
		  codeP = codeP + xp.options[k].value + ',';
		  }
	  }
	  $('#countryPlan').val(codeP);
	  
	  if(checkValid()){
		  ///var base64ImageContent = imgBase.replace(/^data:image\/(png|jpg|jpeg);base64,/, "");
		
	  	  stringUrl = $('#decisionManagerForm').serialize();
	
		  $.post('<c:url value="/servlet/decisionController/createDecisionManager" />',stringUrl,
		             function(data){
		   if(data=='success'){
		   	   $("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
		       $("#savedialog-confirm").html('Đã tạo thành công Quyết định :  '+$("#decisionNumber").val());
		       $("#savedialog-confirm").dialog( 'open' );
		    }else if(data=='duplicate'){
		       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
		       $("#faildialog-confirm").html('Số quyết định:  '+$("#decisionNumber").val() + ' đã tồn tại.');
		       $("#faildialog-confirm").dialog( 'open' );
		    }
		    else {
			       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
			       $("#faildialog-confirm").html('Không thể tạo Quyết định:  '+$("#decisionNumber").val());
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
		 document.forms["decisionManagerForm"].action = '${decisionManagerUrl}';
		 document.forms["decisionManagerForm"].submit();	

	});
  
  $("#resetBtn").click(function(){
		 document.forms["decisionManagerForm"].action = '${resetPage}';
		 document.forms["decisionManagerForm"].submit();
	});
  });
  $(function() {
	  
	  $("#signDate").datepicker({         
		  autoclose: true,         
		  todayHighlight: true,
		  maxDate: 0,
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
  		var d1 =  $('#decisionNumber').val();
  		var d2 =  $('#signDate').val();
  		var d3 =  $('#purpose').val();
  		var d4 =  $('#timeplan').val();
  		var d5 =  $('#tripCost').val();
  		var d6 = '';
  		var xp = document.getElementById('countryPlanCode');
  		var codeP = "";
  		for (var k = 0; k < xp.options.length; k++ )
  		{
			  if(xp.options[k].selected){
				 d6 = codeP + xp.options[k].value + ',';
			  }
		} 
  		
  		if(d1 != "" && d1 != null && d2 != "" && d2 != null && d3 != "" && d3 != null
  				&&d4 != "" && d5 != null && d6 != "" && d6 != null && d5 != "" && d5 != null) 
  		{
  			return true;
  		}
  		return false;
  	}
  	
  	var isShift = false;
  	var seperator = "/";
  	
  	$(document).ready(function () {
  	    //Reference the Table.
  	    var tblForm = document.getElementById("signDate");

  	                //Set Max Length.
  	                tblForm.setAttribute("maxlength", 10);

  	                //Only allow Numeric Keys.
  	                tblForm.onkeydown = function (e) {
  	                    return IsNumeric(this, e.keyCode);
  	                };

  	                //Validate Date as User types.
  	                tblForm.onfocusout = function (e) {
  	                    ValidateDateFormat(this, e.keyCode);
  	                };
  	            
  	});
  	
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
  	
  	function convertToBase64() {
        //Read File
        var selectedFile = document.getElementById("fileUploadPath").files;
        //Check File is not Empty
        if (selectedFile.length > 0) {
            // Select the very first file from list
            var fileToLoad = selectedFile[0];
            // FileReader function for read the file.
            var fileReader = new FileReader();
            var base64;
            // Onload of file read the file content
            fileReader.onload = function(fileLoadedEvent) {
                base64 = fileLoadedEvent.target.result;
                // Print data in console
                console.log(base64);
                imgBase = base64;
                var base64ImageContent = imgBase.replace(/^data:application\/(pdf);base64,/, "");
            	$("#fileUpload").val(base64ImageContent);
            };
            // Convert data to base64
            fileReader.readAsDataURL(fileToLoad);
        }
    }
  	
  	
	</script>
<body>

	<div class="content_main">
		<div class="container">
			<div class="row">
				<div class="roundedBorder ov_hidden">
					<div class="new-heading-2">THÊM QUYẾT ĐỊNH MỚI</div>
					&nbsp;
					<div id="update_msg" style="display: none;">
						<!-- <img src="images/info1.jpg" width="30" height="30"> -->&nbsp;
						<span style="font-size: 14px; color: #0000FF">Quyết định mới đã được lưu thành công
					</div>

					<!--********************customized code for now***************************************-->
					<div id="admin_console_info_div"
						style="display: table-cell; vertical-align: top;"></div>
					<br />
					<form:form modelAttribute="decisionManagerForm"
						commandName="decisionManagerForm" id="decisionManagerForm"
						name="decisionManagerForm" class="inline" method="post" enctype="multipart/form-data">
						<div class="col-sm-6">
							<div class="form-group">
								<label for="decisionNumber">Số quyết định<i
									style="color: red">(*)</i>:
								</label>
								<form:input class="form-control" type="text"
									path="decisionNumber" id="decisionNumber" />
								<!--  <input type="text" class="form-control" id="usr">-->
							</div>
							<div class="form-group">
								<label for="competentAuthorities">Chọn cơ quan có thẩm quyến<i style="color: red">(*)</i>:</label>
								<form:select style="width: 100%;" path="competentAuthorities"
									id="competentAuthorities" class="js-example-basic-single">
									<form:option value="">-- SELECT -- </form:option>
									<form:options items="${codeGov}" itemLabel="codeValueDesc"
										itemValue="id.codeValue"></form:options>
								</form:select>
							</div>
							<div class="form-group">
								<label for="signer">Chọn người ký:</label>
								<%-- <form:select style="width: 100%;" path="signer" id="signer"
									class="js-example-basic-single">
									<form:option value="">-- SELECT -- </form:option>
									<form:options items="${codeSigner}" itemLabel="codeValueDesc"
										itemValue="id.codeValue"></form:options>
								</form:select> --%>
								<form:select style="width: 100%;" path="signer" id="signer"
									class="js-example-basic-single">
									<form:option value="">-- SELECT -- </form:option>
									<%-- <form:options items="${businessList}" itemLabel="fullname"
										itemValue="id"></form:options> --%>
								</form:select>
							</div>
							<div class="form-group">
								<label for="competentAuthoritiesEng">Cơ quan có thẩm quyến (bằng Tiếng anh):</label>
								<form:input class="form-control" type="text"
									path="competentAuthoritiesEng" id="competentAuthoritiesEng" />
							</div>
							<div class="form-group">
								<label for="signDate">Ngày ký quyết định<i style="color: red">(*)</i>: </label>
								<%-- <form:input class="form-control" type="text" path="signDate" id="signDate" /> --%>
								<div id="datepicker" class="input-group date" data-date-format="dd-mm-yyyy"> 
									<form:input class="form-control" type="text" path="signDate" id="signDate" autocomplete="off" />
									<span class="input-group-addon">
										<!-- <img class="ui-datepicker-trigger" src="/eppcentral/resources/images/icon_welcome/calendar.gif" alt="..." title="..."> -->
										<i class="glyphicon glyphicon-calendar"></i>
									</span> 
								</div>
							</div>

							<div class="form-group">
								<label for="purpose">Mục đích chuyến đi<i
									style="color: red">(*)</i>:
								</label>
								<form:select style="width: 100%;" path="purpose" id="purpose"
									class="js-example-basic-single">
									<form:option value="">-- SELECT -- </form:option>
									<form:options items="${purposeddl}" itemLabel="codeValueDesc"
										itemValue="id.codeValue"></form:options>
								</form:select>
								<%-- <form:input class="form-control" type="text" path="purpose"
									id="purpose" /> --%>
							</div>
							<div class="form-group">
								<label for="timeplan">Thời gian dự tính xuất cảnh<i
									style="color: red">(*)</i>:
								</label>
								<form:input class="form-control" type="text" path="timeplan"
									id="timeplan" />
							</div>
							<div class="form-group">
								<label for="tripCost">Chi phí chuyến đi<i
									style="color: red">(*)</i>:
								</label>
								<form:select style="width: 100%;" path="tripCost" id="tripCost"
									class="js-example-basic-single">
									<form:option value="">-- SELECT -- </form:option>
									<form:options items="${tripcostddl}" itemLabel="codeValueDesc"
										itemValue="id.codeValue"></form:options>
								</form:select>
								<%-- <form:input class="form-control" type="text" path="tripCost"
									id="tripCost" /> --%>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label for="invitingAgency">Cơ quan mời (hoặc thu xếp chuyến đi):</label>
								<form:input class="form-control" type="text"
									path="invitingAgency" id="invitingAgency" />
							</div>
							<div class="form-group">
								<label for="transitNationCode">Quốc gia quá cảnh:</label> <select
									class="js-example-basic-multiple" multiple="multiple"
									style="width: 100%;" id="transitNationCode">
									<c:if test="${not empty codeNational}">
										<c:forEach var="entry" items="${codeNational}">
											<option value="${entry.id.codeValue}">${entry.codeValueDesc}</option>
										</c:forEach>
									</c:if>
								</select>
							</div>
							<div class="form-group">
								<label for="timeLasts">Thời gian kéo dài:</label>
								<form:input class="form-control" type="text" path="timeLasts"
									id="timeLasts" />
								<!--  <input type="text" class="form-control" id="usr">-->
							</div>
							<div class="form-group">
								<label for="countryPlanCode">Quốc gia đến<i
									style="color: red">(*)</i>:
								</label>
								<select
									class="js-example-basic-multiple" multiple="multiple"
									style="width: 100%;" id="countryPlanCode">
									<c:if test="${not empty codeNational}">
										<c:forEach var="entry" items="${codeNational}">
											<option value="${entry.id.codeValue}">${entry.codeValueDesc}</option>
										</c:forEach>
									</c:if>
								</select>
							</div>
							<div class="form-group">
								<label for="data">Tải tài liệu công văn:</label> <input
									class="custom-file-input" type="file" name="fileUploadPath" id="fileUploadPath" onchange="convertToBase64();"/>
							</div>
							<div class="form-group">
								<label for="description">Mô tả thêm:</label>
								<form:textarea class="form-control" type="text"
									path="description" id="description" rows="7"></form:textarea>
							</div>
						</div>
						<form:input type="hidden" id="createBy" path="createBy"
							value="${sessionScope.userSession.userName}" />
						<form:input type="hidden" id="transitNation" path="transitNation" />
						<form:input type="hidden" id="countryPlan" path="countryPlan" />
						<form:input type="hidden" id="dataF" path="dataF" />
						<form:input type="hidden" id="fileUpload" path="fileUpload" />
					</form:form>
					<table style="text-align: right;">
						<tr>
							<td colspan="2" align="right" style="padding: 10px; text-align: right;">
								<button type="button" class="btn btn-danger"   id="cancelBtn">
									<span class="glyphicon glyphicon-remove"></span> Hủy
								</button>
								<button type="button" class="btn btn-primary" id="resetBtn">
									<span class="glyphicon glyphicon-refresh"></span> Làm mới
								</button>
								<button type="button" class="btn btn-success" id="add_btn">
									<span class="glyphicon glyphicon-ok"></span> Lưu
								</button>
								<!--<input type="button" class="btn btn-danger" id="cancelBtn" value="Hủy" />
								<input type="button" class="btn btn-primary" id="resetBtn" value="Làm mới" /> 
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

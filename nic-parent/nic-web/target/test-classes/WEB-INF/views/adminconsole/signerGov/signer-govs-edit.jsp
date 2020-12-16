<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true" %>

<c:url var="paymentMatrixUrl" value="/servlet/signerController/signerGovs" />
<c:url var="paymentDeleteUrl" value="/servlet/signerController/signerGovsDelete" />

<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NIC Chữ ký công văn</title>
</head>
<body>
	<div class="content_main">
		<div class="container">
        	<div class="row">
        		<div class="roundedBorder ov_hidden">
		
						<div class="new-heading-2">CHỈNH SỬA CHỮ KÝ CÔNG VĂN</div> 
						<!--<input type="button" class="btn-sm btn-danger" id="cancelBtn" value="Hủy" style="float: right;"/>-->
				
				&nbsp;
				<div id="update_msg" style="display: none;">
					<img src="images/info1.jpg" width="30" height="30"/>&nbsp;<span
						style="font-size: 14px; color: #0000FF">Cập nhật thành công dữ liệu chữ ký công văn</span>
				</div>
				<br />
				<form:form  modelAttribute="paymentMatrixDefForm"  commandName="paymentMatrixDefForm" id="paymentMatrixDefForm"  name="paymentMatrixDefForm" class="inline"
				enctype="multipart/form-data" method="POST">
					<%-- <table style="width:100%;background-color:#E3F1FE" cellspacing="0"class="data_table_noborder" cellpadding="0">
						<form:input type="text" size="30" path="id" id="id" style="display:none"/>
						<tr>
							<td>Tên người ký:</td>
							<td> <form:input type="text" size="200" path="nameSigner" id="nameSigner"/>	
							</td>
						</tr>
						<tr>
							<td>Tên cơ quan:</td>
							<td> <form:input type="text" size="200" path="nameGov" id="nameGov" disabled="true"/>	
							</td>
						</tr>
						<tr>
							<td>Ảnh chữ ký:</td>
								<td>  
								<div style="text-align:center">
						                <!-- photo dimension: 480 (width) x 640 (height) -->
						                <c:choose>
						                    <c:when test="${not empty photoStr}">
						                        <div>
						                            <img style="display:block" src="data:image/jpg;base64,${photoStr}"
						                                 class="img-border doGetAJpgSafeVersion" height="320" width="440" />
						                        </div>
						                    </c:when>
						                    <c:otherwise>
						                        <div>
						                            <img style="display:block" src="<c:url value='/resources/images/No_Image.jpg' />" class="img-border" height="320" width="240" />
						                            <div style="font-weight: bold; color: #000;text-align:center;">No Image</div>
						                        </div>
						                    </c:otherwise>
						                </c:choose>
						            </div>
					            </td>
					            
						</tr>
						<tr>
							<td>Thay ảnh khác: </td>
							<td><input type="file" name="fileImage" id="fileImage" accept="image/gif, image/jpeg, image/png" onchange="convertToBase64();"/><br></td>
						</tr>
						<tr>
							<td>Trạng thái:</td>
							<td><form:checkbox size="10" path="activeT" id="activeT"/></td>
						</tr>
						<tr>
							<td>Mô tả thêm:</td>
							<td><form:input type="text" path="description" id="description" style="height: 50px; width: 247px;" /></td>
						</tr>
						
						<!--<tr><td>System Id:</td><td><input type="text" size="30" value="RIC"/></td></tr>-->
					</table> --%>
					
						
						<div class="form-group">
								<label for="purpose">Mã người ký<i style="color: red">(*)</i>: </label>
								<form:input class="form-control" type="text" path="codeSigner" id="codeSigner" readonly="true" />
						</div>
						<div class="form-group">
								<label for="purpose">Tên người ký<i style="color: red">(*)</i>: </label>
								<form:input class="form-control" type="text" path="nameSigner" id="nameSigner" />
						</div>
						<div class="form-group">
								<label for="codeGovernment">Chọn cơ quan người ký<i style="color: red">(*)</i>:</label>
								<form:select style="width: 100%;" path="codeGovernment"
									id="codeGovernment" class="js-example-basic-single">
									<form:option value="">-- SELECT -- </form:option>
									<form:options items="${codeGov}" itemLabel="codeValueDesc"
										itemValue="id.codeValue"></form:options>
								</form:select>
						</div>
						<div class="form-group">
								<label for="active">Trạng thái: </label>
								<form:radiobutton path="active" id="active" value="Y" style="color:green"/>Hoạt động 
								<form:radiobutton path="active" id="active" value="N" style="color:red"/>Tạm đóng
								<%-- <form:checkbox size="10" path="activeT" id="activeT"/> --%>
						</div>
						<div class="form-group">
								<label for="purpose">Ảnh chữ ký trước đó đã lưu: </label>
								<div style="text-align:center">
						                <!-- photo dimension: 480 (width) x 640 (height) -->
						                <c:choose>
						                    <c:when test="${not empty photoStr}">
						                        <div>
						                            <img style="display:block" src="data:image/jpg;base64,${photoStr}" id="picView"
						                                 class="img-border doGetAJpgSafeVersion" height="320" width="440" />
						                        </div>
						                    </c:when>
						                    <c:otherwise>
						                        <div>
						                            <img style="display:block" src="<c:url value='/resources/images/No_Image.jpg' />" class="img-border" height="320" width="240" />
						                            <div style="font-weight: bold; color: #000;text-align:center;">No Image</div>
						                        </div>
						                    </c:otherwise>
						                </c:choose>
						       </div>
						</div>
						<div class="form-group">
								<label for="data">Thêm ảnh mới:</label> 
								<input accept="image/gif, image/jpeg, image/png" type="file" name="fileImage" id="fileImage" onchange="convertToBase64();"/>
						</div>
						<div class="form-group">
								<label for="description">Mô tả thêm:</label>
								<form:textarea class="form-control" type="text"
									path="description" id="description" rows="7"></form:textarea>
						</div>
						<form:input type="hidden" id="id" path="id" />
					<form:input type="hidden" id="updateBy" path="updateBy" value="${sessionScope.userSession.userName}" />
					<form:input type="hidden" id="docDataF" path="docDataF" />
				</form:form>
					<table style="text-align: right;">
						<tr>
							<td align="right" style="padding: 10px;text-align: right;">
								<button type="button" class="btn btn-danger" id="cancelBtn">
									<span class="glyphicon glyphicon-remove"></span> Hủy
								</button>
								<button type="button" class="btn btn-primary" id="resetBtn" onclick="window.location.href='${requestScope['javax.servlet.forward.request_uri']}'">
									<span class="glyphicon glyphicon-refresh"></span> Làm mới
								</button>
								<button type="button" class="btn btn-success" id="update_btn">
									<span class="glyphicon glyphicon-ok"></span> Cập nhật
								</button>
							<!--<input type="button" class="btn btn-danger" id="cancelBtn" value="Hủy" />
							<input onclick="window.location.href='${requestScope['javax.servlet.forward.request_uri']}'" type="button" class="btn btn-primary" id="resetBtn" value="Làm mới" />
								&nbsp;
							<input type="button" id="update_btn" class="btn btn-success" value="Cập nhật"> 
							&nbsp;-->
<!-- 							<input type="button"  class="button_grey" id="deleteBtn" value="Xóa" /> -->
						</tr>
					</table>

	</div>
</div>
</div>
</div>
<div id="savedialog-confirm"></div>
<div id="faildialog-confirm"></div>
<div id="dialog-confirm"></div>
	<script>
	function convertToBase64() {
	      //Read File
	      var selectedFile = document.getElementById("fileImage").files;
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
	              var base64ImageContent = imgBase.replace(/^data:image\/(png|jpg|jpeg);base64,/, "");
	          	$("#docDataF").val(base64ImageContent);
	          };
	          // Convert data to base64
	          fileReader.readAsDataURL(fileToLoad);
	      }
	  }
	
		$(document).ready(function() {
		    $('.js-example-basic-multiple').select2();
		    $('.js-example-basic-single').select2();
		    /* 
		    $('#picView').attr("src", encodeURI('${photoStr}')); */
		});
		var imgBase = "";
		var stringUrl = "";
		function readURL(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();
				reader.onload = function (e) {
					imgBase = e.target.result;
				};
				reader.readAsDataURL(input.files[0]);
			}
		}
		
		
		$(function() {
			  $("#update_btn").click(function(){
				  var base64ImageContent = imgBase.replace(/^data:image\/(png|jpg|jpeg);base64,/, "");

				  stringUrl = $('#paymentMatrixDefForm').serialize() + "&docString="+ encodeURIComponent(base64ImageContent);

				  if($("#nameGov").val() == ""){
						//alert("Thiếu tên cơ quan/ trụ sở làm việc");
						$.alert({
							title: 'Thông báo',
							content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Thiếu tên cơ quan/ trụ sở làm việc",
						});
						 return false;
					}
				  if($("#nameSigner").val() == ""){
						//alert("Thiếu tên người ký");
						$.alert({
							title: 'Thông báo',
							content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Thiếu tên người ký",
						});
						 return false;
					}
					 $.post('<c:url value="/servlet/signerController/signerGovsUpdate" />',stringUrl,
				             function(data){
				   if(data=='success'){
				    	$("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#savedialog-confirm").html('Đã cập nhật thành công dữ liệu');
				       $("#savedialog-confirm").dialog( 'open' );
				       
				    }else if(data=='fail'){
				     $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
				     $("#faildialog-confirm").html('Cập nhật dữ liệu lỗi. Vui lòng thử lại.');
				     $("#faildialog-confirm").dialog( 'open' );
				    }
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
			  	var codeGov = $('#codeGov').val();
				
			  $("#dialog-confirm").dialog('option', 'title', 'Xóa bản ghi chữ ký');
			    $("#dialog-confirm").html("Bạn có chắc muốn bản ghi mã: "+ codeGov +"?");
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

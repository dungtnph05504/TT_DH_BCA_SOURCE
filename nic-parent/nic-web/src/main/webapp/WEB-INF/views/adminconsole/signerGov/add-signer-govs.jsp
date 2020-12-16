 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:url var="resetPage" value="/servlet/signerController/addSignerGovs" />
<c:url var="paymentMatrixUrl" value="/servlet/signerController/signerGovs" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NIC Chữ ký công văn</title>
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
      
      imgBase = data;
    })
}
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
			    "Đóng": function() {    	
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
			    "Đóng": function() {    	
			    	$(this).dialog("close");
			    }
			   }
			    });
		 
  $('#add_btn').click(function() {
	  //var base64ImageContent = imgBase.replace(/^data:image\/(png|jpg|jpeg);base64,/, "");

	  stringUrl = $('#paymentMatrixDefForm').serialize() + "&docString="+ imgBase;//encodeURIComponent(base64ImageContent);
	   if($("#codeSigner").val() == ""){
			//alert("Thiếu mã người ký");
			$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Thiếu mã người ký",
			});
			 return false;
		}
 	  /*if($("#codeGov").val() == ""){
			alert("Thiếu tên cơ quan/ trụ sở làm việc");
			 return false;
		}*/
	  if($("#nameSigner").val() == ""){
			//alert("Thiếu tên người ký");
			$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Thiếu tên người ký",
			});
			 return false;
		} 
		  
		  $.post('<c:url value="/servlet/signerController/signerGovsCreate" />',stringUrl,
		             function(data){
		   if(data=='success'){
		   	   $("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
		       $("#savedialog-confirm").html('Đã tạo thành công Chữ ký công văn :  '+$("#codeSigner").val());
		       $("#savedialog-confirm").dialog( 'open' );
		    }else if(data=='duplicate'){
		       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
		       $("#faildialog-confirm").html('Mã:  '+$("#codeSigner").val() + ' đã tồn tại. Vui lòng đổi Mã ký tự khác.');
		       $("#faildialog-confirm").dialog( 'open' );
		    }
		    else {
			       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
			       $("#faildialog-confirm").html('Không thể tạo Chữ ký công văn:  '+$("#codeSigner").val());
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
	});
	</script>
<body>

	<div class="content_main">
		<div class="container">
			<div class="row">
				<div class="roundedBorder ov_hidden">
					<div class="new-heading-2">THÊM CHỮ KÝ CÔNG VĂN</div> 
					&nbsp; 
					<div id="update_msg" style="display: none;">
						<img src="images/info1.jpg" width="30" height="30">&nbsp;<span
							style="font-size: 14px; color: #0000FF">Chữ ký công văn đã
							được lưu thành công
					</div>

					<!--********************customized code for now***************************************-->
					<div id="admin_console_info_div"
						style="display: table-cell; vertical-align: top;"></div>
					<br />
					<form:form modelAttribute="paymentMatrixDefForm"
						commandName="paymentMatrixDefForm" id="paymentMatrixDefForm"
						name="paymentMatrixDefForm" class="inline">
						<%-- <table style="width: 100%; background-color: #E3F1FE"
							cellspacing="0" class="data_table_noborder" cellpadding="0">
							<tr>
								<td>Chọn cơ quan người ký:</td>
								<td><form:select style="width: 220px;"
										path="codeGovernment" id="codeGovernment">
										<form:option value="">-- SELECT -- </form:option>
										<form:options items="${codeGov}" itemLabel="codeValueDesc"
											itemValue="id.codeValue"></form:options>
									</form:select></td>
							</tr>
							
										<tr>
							<td>Chọn cơ quan:</td>
							<td> 
								<form:select style="width: 220px;" path="nameGov" id="nameGov">
									<form:option value="">-- SELECT -- </form:option>
									<form:options items="${codeGov}" itemLabel="codeValueDesc" itemValue="id.codeValue"></form:options>
								</form:select>
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
								<td>Thêm ảnh mới:</td>
								<td><input type="file" name="fileImage" id="fileImage"
									accept="image/gif, image/jpeg, image/png"
									onchange="readURL(this);" /><br></td>
							</tr>
							<tr>
							<td>Trạng thái:</td>
							<td><form:checkbox size="10" path="active" id="active" /></td>
						</tr>
							<tr>
								<td>Mô tả thêm:</td>
								<td><form:input type="text" path="description"
										id="description" style="height: 50px;width: 247px;" /></td>
							</tr>
						</table> --%>
						
						<div class="form-group">
								<label for="purpose">Mã người ký<i style="color: red">(*)</i>: </label>
								<form:input class="form-control" type="text" path="codeSigner" id="codeSigner" />
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
								<label for="data">Thêm ảnh mới:</label> 
								<input accept="image/gif, image/jpeg, image/png" type="file" name="fileImage" id="fileImage" onchange="convertToBase64();"/>
						</div>
						<div class="form-group">
								<label for="description">Mô tả thêm:</label>
								<form:textarea class="form-control" type="text"
									path="description" id="description" rows="7"></form:textarea>
						</div>
						<form:input type="hidden" id="createBy" path="createBy"
							value="${sessionScope.userSession.userName}" />
						<form:input type="hidden" id="docDataF" path="docDataF" />
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
								<!--<input type="button" class="btn btn-danger" id="cancelBtn" value="Hủy" />
								<input type="button" class="btn btn-primary" id="resetBtn" value="Làm lại" />
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

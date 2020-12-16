<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url var="submitUrl" value="/servlet/report/save"/>
<c:url var="hiddenInptUrl" value="/servlet/report/query_ajax"/>
 <c:url var="reqFrdURL" value="/servlet/report/forwardReq"/>
  <c:url var="queryUpdateUrl" value="/servlet/report/queryUpdate"/>
 <c:url var="url" value="/servlet/report"/>
 <c:url var="frdCnclRqUrl" value="/servlet/report/cancelFrdToReqPage"/>
 <c:url var="deleteRptURL" value="/servlet/report/deleteRpt"/>
  <c:url var="frdDefUrl" value="/servlet/report/view"/>
 <c:url var="frdEditPgUrl" value="/servlet/report/edit"/>
 <c:url var="repCatUrl" value="/servlet/report/viewByCategory"/>
 
 <style>
 .modal {
	display: none;
	position: fixed;
	z-index: 1000;
	top: 0;
	left: 0;
	height: 100%;
	width: 100%;
	background: rgba(255, 255, 255, .8)
		url('<c:url value="/resources/images/e921f-ajax-loader.gif" />') 50%
		50% no-repeat;
}
.fix-bottom {
	margin-top: 10px;
}
/* When the body has the loading class, we turn
   the scrollbar off with overflow:hidden */
body.loading {
	overflow: hidden;
}

/* Anytime the body has the loading class, our
   modal element will be visible */
body.loading .modal {
	display: block;
}
 </style>

<script>

$( document ).ready(function() {
	
	
	var reportId=document.forms["nicReportForm"]["reportId"].value;
	if (reportId !==null)
	  {
		$("#parameterTable").show();
		$("#parameterTableDiv").show();
	  }
    
});
		$(function() {
			$("#msgDialog").dialog({
				width : 400,
				height : 150,
				resizable : false,
				modal : true,
				autoOpen : false,
				 open : function() {
					    $('.ui-dialog-buttonpane span').addClass('btn_blue gradient'); 
					   
				  },
				show : {
					effect : "blind",
					duration : 1000
				},
				hide : {
					//effect : "explode",
					duration : 100
				},
				close : function() {
					$(this).dialog("close");
				},
				buttons : {
					Ok : function() {
						$(this).dialog("close");
					}
				}
			});	
		
			
			$("#paramDialog").dialog( {
				autoOpen : false,
				width : 550,
				height : 400,
				modal : true,
				bgiframe : true,
				closeOnEscape: false,
				open : function() {
					resetParam();
				},
				buttons : {
					
					"Thêm mới" : function() {
						var arr = window.parent.document.getElementsByName("paramNameCollection");
						var paramNameArr = new  Array();
						$.each(arr,function(index,val){
							paramNameArr.push(val.value);
						});
						if($.inArray(trim(document.getElementById('paramName').value),paramNameArr) > -1){
							alert("Tên tham số đã tồn tại");
							return false;
						}
						if(trim(document.getElementById('paramName').value)=="" || trim(document.getElementById('paramName').value)== null){
							alert("Bạn chưa nhập tên tham số");
							return false;
						} else if(trim(document.getElementById('paramDes').value)=="" || trim(document.getElementById('paramDes').value)== null){
							alert("Bạn chưa nhập mô tả tham số");
							return false;
						} else if(trim(document.getElementById('paramType').value)=="" || trim(document.getElementById('paramType').value)== null){
							alert("Kiểu dữ liệu là bắt buộc");
							return false;
						} else if(trim(document.getElementById('priority').value)=="" || trim(document.getElementById('priority').value)== null){
							alert("Bạn chưa nhập ưu tiên");
							return false;
						} else if(!IsNumeric(trim(document.getElementById('priority').value))){
							alert("Ưu tiên không hợp lệ");
			       			return false;
			      		}
						var selectMan = '';
						if (document.getElementById('mandatory').value =="Y") {
							selectMan = '<option value="Y" SELECTED>Y</option>'+
							'<option value="N">N</option>';
						} else { 
							selectMan = '<option value="N" SELECTED>N</option>'+
							'<option value="Y">Y</option>';
						}
						var paraDes = '<tr align="center"><td class="chartTableeven">'+
						'<input id="paramNameCollection" name="paramNameCollection" value="'+document.getElementById('paramName').value+
						'"></td><td class="chartTableeven"><input id="paramDesCollection" size="50" name="paramDesCollection" value="'+document.getElementById('paramDes').value+
						'"></td><td class="chartTableeven"><input id="paramTypeCollection" name="paramTypeCollection" value="'+document.getElementById('paramType').value+
						'"></td><td class="chartTableeven"><input id="priorityCollection" name="priorityCollection" value="'+document.getElementById('priority').value+
						'" size="5" ></td><td class="chartTableeven"><select id="mandatoryCollection" name="mandatoryCollection">' +selectMan+
						'</select>' +
						'</td><td class="chartTableeven"><input id="paramAliasCollection" name="paramAliasCollection" value="'+document.getElementById('paramAlias').value+
						'"></td></tr>';
						
						
						 $('#parameterTable').append(paraDes);
						//$('#parameterTable').html(value);
				    	//	document.getElementById('paramDetails').style.display="inline";
					   //	document.getElementById('noParamDetails').style.display = 'none'; 
					    $("#noParamDetails").hide();
					    $("#parameterTableDiv").show();
					    $("#parameterTable").show();
						$(this).dialog("close");
					},
					"Làm lại" : function() {
						resetParam();
					},
					"Hủy" : function() {
						$(this).dialog("close");
					}
				}
			
			});
			
			$('#AddParam').click(function() {
				var titleName="Thêm tham số";
		        $("#paramDialog").dialog('option', 'title', titleName);
		        $("#paramDialog").dialog( "option", "width", 700 );
				$("#paramDialog").dialog( "option", "height", 400 );
				$("#paramDialog").dialog('open');
			});
			
			$('#paramDesOk').click(function() {
				if(trim(document.getElementById('paramName').value)=="" || trim(document.getElementById('paramName').value)== null){
					alert("Bạn chưa nhập tên tham số");
					return false;
				} else if(trim(document.getElementById('paramDes').value)=="" || trim(document.getElementById('paramDes').value)== null){
					alert("Bạn chưa nhập mô tả tham số");
					return false;
				} else if(trim(document.getElementById('paramType').value)=="" || trim(document.getElementById('paramType').value)== null){
					alert("Bạn chưa nhập kiểu dữ liệu");
					return false;
				} else if(trim(document.getElementById('priority').value)=="" || trim(document.getElementById('priority').value)== null){
					alert("Bạn chưa nhập ưu tiên");
					return false;
				} else if(!IsNumeric(trim(document.getElementById('priority').value))){
					alert("Trường ưu tiên không hợp lệ");
	       			return false;
	      		}
				var selectMan = '';
				if (document.getElementById('mandatory').value =="Y") {
					selectMan = '<option value="Y" SELECTED>Y</option>'+
					'<option value="N">N</option>';
				} else { 
					selectMan = '<option value="N" SELECTED>N</option>'+
					'<option value="Y">Y</option>';
				}
				var paraDes = '<tr align="center"><td class="chartTableeven">'+
				'<input id="paramNameCollection" name="paramNameCollection" value="'+document.getElementById('paramName').value+
				'"></td><td class="chartTableeven"><input id="paramDesCollection" size="50" name="paramDesCollection" value="'+document.getElementById('paramDes').value+
				'"></td><td class="chartTableeven"><input id="paramTypeCollection" name="paramTypeCollection" value="'+document.getElementById('paramType').value+
				'"></td><td class="chartTableeven"><input id="priorityCollection" name="priorityCollection" value="'+document.getElementById('priority').value+
				'" size="5" ></td><td class="chartTableeven"><select id="mandatoryCollection" name="mandatoryCollection">' +selectMan+
				'</select>' +
				'</td><td class="chartTableeven"><input id="paramAliasCollection" name="paramAliasCollection" value="'+document.getElementById('paramAlias').value+
				'"></td></tr>';
				
				
				 $('#parameterTable').append(paraDes);
				//$('#parameterTable').html(value);
		    	//	document.getElementById('paramDetails').style.display="inline";
			   //	document.getElementById('noParamDetails').style.display = 'none'; 
			    $("#noParamDetails").hide();
			    $("#parameterTableDiv").show();
			    $("#parameterTable").show();
				$('#paramDialog').dialog('close');
			});
			
		});
		
function trim(str) {
		       return str.replace(/^\s+|\s+$/g,"");
}
function IsNumeric(sText) {
	var ValidChars = "0123456789.";
	var IsNumber=true;
	var Char;
	for (i = 0; i < sText.length && IsNumber == true; i++) 
	{ 
		Char = sText.charAt(i); 
		if (ValidChars.indexOf(Char) == -1) 
		{
		IsNumber = false;
		}
	}
    return IsNumber;
}
function confirmDelete(name) {
    var agree=confirm("Bạn có chắc muốn xoá: "+name+"?");
    if (agree) {
      return true ;
    } else {
      return false ;
    }
  }

function deleteId(id) {
	//	alert(id);
		$.post('<c:url value="/servlet/report/delete"/>', {
			id : id
		}, function(data) {
		});

		//$("#dropdown3").show();
		//$("#fortable").show();
		//$("#defitable").hide();
		//$("#defitable2").hide();

		/* $("#statResult")
				.flexOptions(
						{
							url : "${url}/query_ajax?${requestScope['javax.servlet.forward.query_string']}"

						}).flexReload(); */

}

	
</script>

   <div id="msgDialog" title="Thông báo">
	<div class="isa_info" align="center">
		<span style="font-size: 25"></span>
	</div>
	<div class="error_msg"></div>
</div> 


<form:form  modelAttribute="nicReportForm"  action="${submitUrl}" id="nicReportForm" name="nicReportForm"  method="GET">
<!-- <div id="main"> -->
<div id="content_main">
	<div class="container">
	        	<div class="row" style="margin-bottom: 80px;">
	        		<div class="roundedBorder ov_hidden">
  <div class="new-heading-2">QUẢN LÝ BÁO CÁO</div>
  <br>
<div class="border_success" style="display: none">
<!--<div class="success"> -->
<div class="success_left">
 <img align='left' src="<c:url value="/resources/images/success.jpg" />" width="30" height="30" style="padding:5px 0 0 15px"/>
 
 </div>
<div class="success">
 <table width="800" height="30" border="0" cellpadding="5">
<tr> 
    <td  id="saves" width="587" height="28" style="font-size: 18px; font-weight:bold; color: #000;">Tạo báo cáo thành công<p class="text_pad">&nbsp;<c:out value="${nicReportForm.reportId}" /></p>
    <td id="updates" width="587" height="28" style="font-size: 18px; font-weight:bold; color: #000;">Cập nhật báo cáo thành công<p class="text_pad">&nbsp;<c:out value="${nicReportForm.reportId}" /></p>
  
  </tr>
 </table>
</div>
</div>
<div class="border_error" style="display: none">
<!--<div class="errors"> -->
<div class="success_left">
 <img align='left' src="<c:url value="/resources/images/alert.png" />"  width="30" height="30" style="padding:5px 0 0 15px"/>
 </div>
<div class="errors">
<table width="800" height="30" border="0" cellpadding="5">
  <tr>
    <td  id="fails" width="587" height="28" style="font-size: 18px; font-weight:bold; color: #000;">Lỗi trong khi tạo báo cáo.<p class="text_pad">&nbsp; <c:out value="${nicReportForm.reportId}" /></p>
     <td id="failu" width="587" height="28" style="font-size: 18px; font-weight:bold; color: #000;">Lỗi trong khi cập nhật báo cáo.<p class="text_pad">&nbsp; <c:out value="${nicReportForm.reportId}" /></p>
  </tr> 
</table>
</div>
</div>
  <div id="reportDef">     
  	<div class="col-md-12">
			<div class="col-md-6">
				<div class="col-md-4 fix-bottom">
					<label for="countryCode">Mã báo cáo:</label>
				</div>
				<div class="col-md-8 fix-bottom">
					<form:input id="reportId" path="reportId" type="text" size='50' class="form-control"/>
				</div>
				<div class="col-md-4 fix-bottom">
					<label for="countryCode">Tên báo cáo<i style="color: red">(*)</i>:</label>
				</div>
				<div class="col-md-8 fix-bottom">
					<form:input  id="reportName" path="reportName" type="text" size='50' class="form-control"/>				
				</div>
				<div class="col-md-4 fix-bottom">
					<label for="countryCode">Mô tả<i style="color: red">(*)</i>:</label>
				</div>
				<div class="col-md-8 fix-bottom">
					<form:textarea id="description" path="description"  rows="4" class="form-control"></form:textarea>
				</div>
				<div class="col-md-4 fix-bottom">
					<label for="countryCode">Ưu tiên<i style="color: red">(*)</i>:</label>
				</div>
				<div class="col-md-8 fix-bottom">
					<form:input  id = "reportPriority" path="reportPriority" type="text" width='5%' class="form-control"/>
				</div>
				<div class="col-md-4 fix-bottom">
					<label for="countryCode">Danh mục<i style="color: red">(*)</i>:</label>
				</div>
				<div class="col-md-8 fix-bottom">
					<c:if test='${empty nicReportForm.reportId}'>
		    		  	 <form:select id="reportCategory" path="reportCategory" class="form-control">
								<form:option value="NONE" label="--- Chọn ---"    />
								<form:options items="${reportCategoryList}" itemLabel="codeValueDesc"  itemValue="id.codeValue"/>
				   		 </form:select>
		    		 </c:if>
		    		 <c:if test='${not empty nicReportForm.reportId}'>	
			   		     <form:select id="reportCategory" path="reportCategory" class="form-control">
								<form:option value="NONE" label="--- Chọn ---"    />
								<form:options items="${reportCategoryList}" itemLabel="codeValueDesc"  itemValue="id.codeValue"/>
					   	 </form:select>
			   		</c:if> 
				</div>
			 </div>
         </div>	               
   	<!--<table style="width:100%;background-color:#E3F1FE" class="data_table">
  			<tr><td style="padding:15px" colspan="4">
		<table style="width:100%" cellspacing="0" class="" id="row" cellpadding="0">
		   <c:if test='${not empty nicReportForm.reportId}'>
   			 <tr><td width="20%">Mã báo cáo: </td>
   			 <td>
   			  <c:out value="${nicReportForm.reportId}" />
			  <form:hidden id="reportId" path="reportId" />
   			 </td>
   			 </tr>
   			</c:if>
   			 <c:if test='${empty nicReportForm.reportId}'>
   			 <tr><td width="20%">Mã báo cáo<span style="COLOR: #ff0000;">*</span>: </td><td><form:input id="reportId" path="reportId" type="text" size='50' /></td></tr>
   			</c:if>	
			<tr><td width="20%">Tên báo cáo<span style="COLOR: #ff0000;">*</span>: </td><td><form:input  id="reportName" path="reportName" type="text" size='50' /></td></tr>    
   			<tr><td>Mô tả<span style="COLOR: #ff0000;">*</span>: </td><td><form:textarea id="description" path="description"  rows="4" style="width:98%"></form:textarea></td></tr>
    		<tr><td>Ưu tiên<span style="COLOR: #ff0000;">*</span>: </td><td><form:input  id = "reportPriority" path="reportPriority" type="text" width='5%'/></td></tr>
    		 <c:if test='${empty nicReportForm.reportId}'>
    		  <tr><td width="20%">Danh mục<span style="COLOR: #ff0000;">*</span>: </td><td>
    		  <form:select id="reportCategory" path="reportCategory" >
						<form:option value="NONE" label="--- Chọn ---"    />
						<form:options items="${reportCategoryList}" itemLabel="codeValueDesc"  itemValue="id.codeValue"/>
		    </form:select></td></tr>
    		 </c:if>
    		 <c:if test='${not empty nicReportForm.reportId}'>	
   			<tr><td>Danh mục<span style="COLOR: #ff0000;">*</span>: </td><td>
   		    <form:select id="reportCategory" path="reportCategory" >
						<form:option value="NONE" label="--- Chọn ---"    />
						<form:options items="${reportCategoryList}" itemLabel="codeValueDesc"  itemValue="id.codeValue"/>
		    </form:select>
	   		 </td></tr>
	   		 </c:if> 
	   	   
	  		
		   </table>
	  
	  </table> -->                  
   </div>
   <c:if test="${nicReportForm.reportCategory ne 'CR'}"> 
   		<div class="col-md-12 fix-bottom" style="background-color: #f3f1f1;">
			<div class="col-md-5" style="padding-top: 5px;">
				<div class="col-md-4">
					<label for="countryCode">Tên thông số</label>
				</div>
				<div class="col-md-8">
					<label for="countryCode">Mô tả</label>
				</div>				
			 </div>
			 <div class="col-md-7" style="padding-top: 5px;">
				<div class="col-md-3">
					<label for="countryCode">Kiểu dữ liệu</label>
				</div>
				<div class="col-md-2">
					<label for="countryCode">Ưu tiên</label>
				</div>
				<div class="col-md-2">
					<label for="countryCode">Bắt buộc?</label>
				</div>
				<div class="col-md-4">
					<label for="countryCode">Hiển thị</label>
				</div>
				<div class="col-md-1">
					<label for="countryCode">Xóa</label>
				</div>
			 </div>
         </div>	
         <div class="col-md-12">
	         <c:forEach var="reportParameter" items="${nicReportForm.parameterList}" >
	         	  <c:if test='${reportParameter.deleteFlag =="N"}'>
	         	  		<div class="col-md-5 fix-bottom">
							<div class="col-md-4">
								<label for="countryCode">${reportParameter.id.paraName}</label>
								<input type="hidden" id="paramNameCollection" name="paramNameCollection" value='${reportParameter.id.paraName}' />
							</div>
							<div class="col-md-8">
								<input id="paramDesCollection" name="paramDesCollection" value='${reportParameter.description}'  size="50" class="form-control" />
							</div>				
						 </div>
						 <div class="col-md-7 fix-bottom">
							<div class="col-md-3">
								<input id="paramTypeCollection" name="paramTypeCollection" value='${reportParameter.paramType}' class="form-control" />
							</div>
							<div class="col-md-2">
								<input id="priorityCollection" name="priorityCollection" value='${reportParameter.priority}' size="5" class="form-control" />
							</div>
							<div class="col-md-2">
									<c:if test='${reportParameter.isMandatory =="Y"}'>
									 	<select id="mandatoryCollection" name="mandatoryCollection" class="form-control">
											<option value="Y">Có</option>
											<option value="N">Không</option>
										</select>	
										</c:if>
										<c:if test='${reportParameter.isMandatory == "N"}'>
									 	<select id="mandatoryCollection" name="mandatoryCollection" class="form-control">
									 		<option value="N">Không</option>
											<option value="Y">Có</option>
										</select>	
									</c:if>
							</div>
							<div class="col-md-4">
								<input id="paramAliasCollection" name="paramAliasCollection" value='${reportParameter.parameterAlias}' class="form-control" />
							</div>
							<div class="col-md-1">
								<input type="checkbox" id="paramDeleteCollection"  name="paramDeleteCollection" class="form-control" />
							</div>
						 </div>
	         	  </c:if>	
	         </c:forEach>
         </div>
    <!--<div id="parameterTableDiv" style="display: none" >  
               
   	<table id="parameterTable"  style="display: none" width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#333333" class="displayTag" style="background-image:url('<%=request.getContextPath() %>/resources/images/head.png');background-repeat: repeat-x;" >
   	<tr>
						<th style="text-align: left">Tên thông số </th>
						<th style="text-align: left">Mô tả</th>
						<th style="text-align: left">Kiểu dữ liệu </th>
						<th style="text-align: left">Ưu tiên</th>
						<th style="text-align: left">Bắt buộc?</th>
						<th style="text-align: left">Hiển thị</th>
						<c:if test='${not empty nicReportForm.reportId}'>
						<th style="text-align: left">Xóa</th>
						</c:if>
	 </tr>
	<c:forEach var="reportParameter" items="${nicReportForm.parameterList}" >
	                       <c:if test='${reportParameter.deleteFlag =="N"}'>
								<tr align="center">
								
									 <td class="chartTableeven">
									  <c:out value='${reportParameter.id.paraName}' />
										<input type="hidden" id="paramNameCollection" name="paramNameCollection" value='${reportParameter.id.paraName}' />									 	
									 </td>
									 <td class="chartTableeven">
									 	<input id="paramDesCollection" name="paramDesCollection" value='${reportParameter.description}'  size="50">
									 </td>
									 <td class="chartTableeven">
									 	<input id="paramTypeCollection" name="paramTypeCollection" value='${reportParameter.paramType}'></td>
									 <td class="chartTableeven">
									 	<input id="priorityCollection" name="priorityCollection" value='${reportParameter.priority}' size="5" ></td>
									 <td class="chartTableeven"> 
									 	<c:if test='${reportParameter.isMandatory =="Y"}'>
									 	<select id="mandatoryCollection" name="mandatoryCollection" >
											<option value="Y">Có</option>
											<option value="N">Không</option>
										</select>	
										</c:if>
										<c:if test='${reportParameter.isMandatory == "N"}'>
									 	<select id="mandatoryCollection" name="mandatoryCollection" >
									 		<option value="N">Không</option>
											<option value="Y">Có</option>
										</select>	
										</c:if>
									 </td>
									 <td class="chartTableeven">
									 	<input id="paramAliasCollection" name="paramAliasCollection" value='${reportParameter.parameterAlias}'>
									 </td>
									  <td class="chartTableeven"> 
										<input type="checkbox" id="paramDeleteCollection"  name="paramDeleteCollection" />
									 </td>
								</tr>	
								</c:if>	
	</c:forEach>
   	</table>
   	</div>-->
   	
</c:if>

 </div>
 
<%--  </c:if> --%>
 <c:if test="${empty nicReportForm.reportId and nicReportForm.reportCategory ne 'CR'}">
 <!--<div id="noParamDetails">
  	<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#333333"  class="displayTag" style="background-image:url('<%=request.getContextPath() %>/resources/images/head.png');background-repeat: repeat-x;">
  		<tr>
  			<td>Không tìm thấy dữ liệu</td>
  		</tr>
  	</table>
 </div>-->
 </c:if>
 <div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
	<div style="margin: 10px"> 
		<c:if test='${empty nicReportForm.reportId}'>			 
			<c:if test="${nicReportForm.reportCategory ne 'CR'}">
				<button type="button" class="btn btn-primary" id="AddParam">
					 <span class="glyphicon glyphicon-plus-sign"></span> Thêm tham số
				</button>
			</c:if>
			<button type="button" class="btn btn-success" id="save_btn" onclick="saveForm('save')">
				<span class="glyphicon glyphicon-save"></span> Cập nhật
			</button>
			<button type="button" class="btn btn-primary" id="reset_btn_one">
				<span class="glyphicon glyphicon-refresh"></span> Làm lại
			</button>
			<button type="button" class="btn btn-danger" id="cancel_btn_one">
				<span class="glyphicon glyphicon-remove"></span> Hủy
			</button>
			<!--<input type="button" onclick="saveForm('save')" class="button_grey" id="save_btn" value="Cập nhật"/>
			<input type="button" class="button_grey" id="reset_btn_one"  value="Làm lại"/>
			<input type="button" class="button_grey" id="cancel_btn_one"  value="Hủy"/>-->
		</c:if>
		<c:if test='${not empty nicReportForm.reportId}'>		 
			   	 <c:if test="${nicReportForm.reportCategory ne 'CR'}">
			    	<button type="button" class="btn btn-primary" id="AddParam">
						 <span class="glyphicon glyphicon-plus-sign"></span> Thêm tham số
					</button>
			   	 </c:if>
			   	 	<button type="button" class="btn btn-success" id="update_btn" onclick="saveForm('update')">
						<span class="glyphicon glyphicon-save"></span> Cập nhật
					</button>
					<button type="button" class="btn btn-warning" id="delete_btn">
						<span class="glyphicon glyphicon-trash"></span> Xóa báo cáo
					</button>
					<button type="button" class="btn btn-danger" id="cancel_btn">
						<span class="glyphicon glyphicon-remove"></span> Hủy
					</button>
					<!--<input type="button" onclick="saveForm('update')" class="button_grey" id="update_btn" value="Cập nhật"/>		
					<input type="button" class="button_grey" id="cancel_btn"  value="Hủy"/>
					<input type="button" class="button_grey"  id="delete_btn"  value="Xóa báo cáo"/>-->
					<c:choose>
						<c:when test="${nicReportForm.reportCategory ne 'CR'}">
							<button type="button" class="btn btn-primary" id="uploadjrxml" onclick="doFormFrdSubmit(this.form);">
								 <span class="glyphicon glyphicon-download-alt"></span> Tải mẫu báo cáo
							</button>
						</c:when>
						<c:otherwise>
							<button type="button" class="btn btn-primary" id="reportQueryStr" onclick="doFormQuerySubmit(this.form);">
								 <span class="glyphicon glyphicon-floppy-open"></span> Truy vấn
							</button>
						</c:otherwise>
					</c:choose>
					<button type="button" class="btn btn-primary" id="reset_btn_two">
						 <span class="glyphicon glyphicon-refresh"></span> Làm lại
					</button>

	  	</c:if>
	</div>
</div>
 <!--<c:if test='${empty nicReportForm.reportId}'>
  <div id="buttonsTable">                    
   	<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#333333" class="data_table" >
	   	<tr>
		   	  <td colspan="5" align="center" style="padding: 20px;">
		   	  <c:if test="${nicReportForm.reportCategory ne 'CR'}">
		     	<input type="button"  class="button_grey" id="AddParam" value="Thêm tham số"/>
		     	  </c:if>
				<input type="button" onclick="saveForm('save')" class="button_grey" id="save_btn" value="Cập nhật"/>
				<input type="button" class="button_grey" id="reset_btn_one"  value="Làm lại"/>
				<input type="button" class="button_grey" id="cancel_btn_one"  value="Hủy"/>
			 </td>
		</tr>
   	</table>
 </div>
 </c:if>
   <c:if test='${not empty nicReportForm.reportId}'>
  <div id="buttonsEditTable">                    
   	<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#333333" class="data_table" >
   <tr>
   	<td colspan="5" align="center" style="padding: 20px;">
   	 <c:if test="${nicReportForm.reportCategory ne 'CR'}">
    	<input type="button"  class="button_grey" id="AddParam" value="Thêm tham số"/>
    </c:if>
		<input type="button" onclick="saveForm('update')" class="button_grey" id="update_btn" value="Cập nhật"/>		
		<input type="button" class="button_grey" id="cancel_btn"  value="Hủy"/>
		<input type="button" class="button_grey"  id="delete_btn"  value="Xóa báo cáo"/>
		<c:choose>
			<c:when test="${nicReportForm.reportCategory ne 'CR'}">
				<input type="button" class="button_grey" id="uploadjrxml"  onclick="doFormFrdSubmit(this.form);" value="Tải mẫu báo cáo" />
			</c:when>
			<c:otherwise>
				<input type="button" class="button_grey" id="reportQueryStr"  onclick="doFormQuerySubmit(this.form);" value="Truy vấn" />
			</c:otherwise>
		</c:choose>
		
		<input type="button" class="button_grey" id="reset_btn_two"  value="Làm lại"/>
	</td>
	</tr>
   	</table>
 </div>
  </c:if>-->
 <form:hidden  id="reportParameterName" path="reportParameterName" />
 <form:hidden  id="reportParameterDesc"  path="reportParameterDesc"/>
 <form:hidden  id="reportParameterType"  path="reportParameterType" />
 <form:hidden  id="reportParameterPriority" path="reportParameterPriority" />
 <form:hidden  id="reportParameterMandatory"  path="reportParameterMandatory"/>
 <form:hidden  id="reportParameterAlias"  path="reportParameterAlias"/>
 <form:hidden  id="categoryLoad" path="categoryLoad" name="categoryLoad"/>
 <form:hidden  id="deleteCollection" path="deleteCollection" name="deleteCollection"/>	


<script type="text/javascript">

   function saveForm(task){
	   
	   
	   //alert(task);
	   
	   
	   var arr = new Array();
	    arr = window.parent.document.getElementsByName("paramNameCollection");
	    var reportParamNameStr = '';
	    var reportParamDesStr = '';
	    var reportParamTypeStr = '';
	    var reportParamPrtysStr = '0';
	    var reportParamMandaStr = '';
	    var reportParameAliasStr = '';
	    var reportParamDeleteStr = '';
	    for(var i = 0; i < arr.length; i++)
	    {
	       var obj7 = window.parent.document.getElementsByName("paramDeleteCollection").item(i);
 
	       if (obj7 == null) {
		       var obj1 = window.parent.document.getElementsByName("paramNameCollection").item(i);
		       var obj2 = window.parent.document.getElementsByName("paramDesCollection").item(i);
		       var obj3 = window.parent.document.getElementsByName("paramTypeCollection").item(i);
		       var obj4 = window.parent.document.getElementsByName("priorityCollection").item(i);
		       var obj5 = window.parent.document.getElementsByName("mandatoryCollection").item(i);
		       var obj6 = window.parent.document.getElementsByName("paramAliasCollection").item(i);
		        reportParamNameStr = reportParamNameStr + obj1.value + ' &&& ';
		  		reportParamDesStr = reportParamDesStr + trim(obj2.value) + ' &&& ';
		  		reportParamTypeStr = reportParamTypeStr + obj3.value + ' &&& ';
		  		reportParamPrtysStr = reportParamPrtysStr + obj4.value + ' &&& ';
		  		reportParamMandaStr = reportParamMandaStr + obj5.value + ' &&& ';
		  		reportParameAliasStr = reportParameAliasStr + obj6.value + ' &&& ';
		  		reportParamDeleteStr = reportParamDeleteStr + ' New&&&';
	       } else if (obj7 != null && obj7.checked == false) {
		       var obj1 = window.parent.document.getElementsByName("paramNameCollection").item(i);
		       var obj2 = window.parent.document.getElementsByName("paramDesCollection").item(i);
		       var obj3 = window.parent.document.getElementsByName("paramTypeCollection").item(i);
		       var obj4 = window.parent.document.getElementsByName("priorityCollection").item(i);
		       var obj5 = window.parent.document.getElementsByName("mandatoryCollection").item(i);
		       var obj6 = window.parent.document.getElementsByName("paramAliasCollection").item(i);
		        reportParamNameStr = reportParamNameStr + obj1.value + ' &&& ';
		  		reportParamDesStr = reportParamDesStr + trim(obj2.value) + ' &&& ';
		  		reportParamTypeStr = reportParamTypeStr + obj3.value + ' &&& ';
		  		reportParamPrtysStr = reportParamPrtysStr + obj4.value + ' &&& ';
		  		reportParamMandaStr = reportParamMandaStr + obj5.value + ' &&& ';
		  		reportParameAliasStr = reportParameAliasStr + obj6.value + ' &&& ';
		  		reportParamDeleteStr = reportParamDeleteStr + ' N&&&';
		  }  else if (obj7 != null && obj7.checked == true) {
		       // var obj1 = window.parent.document.getElementsByName("paramNameCollection").item(i);
		   	
		   	   var obj1 = window.parent.document.getElementsByName("paramNameCollection").item(i);
		       var obj2 = window.parent.document.getElementsByName("paramDesCollection").item(i);
		       var obj3 = window.parent.document.getElementsByName("paramTypeCollection").item(i);
		       var obj4 = window.parent.document.getElementsByName("priorityCollection").item(i);
		       var obj5 = window.parent.document.getElementsByName("mandatoryCollection").item(i);
		       var obj6 = window.parent.document.getElementsByName("paramAliasCollection").item(i);
		        reportParamNameStr = reportParamNameStr + obj1.value + ' &&& ';
		  		reportParamDesStr = reportParamDesStr + trim(obj2.value) + ' &&& ';
		  		reportParamTypeStr = reportParamTypeStr + obj3.value + ' &&& ';
		  		reportParamPrtysStr = reportParamPrtysStr + obj4.value + ' &&& ';
		  		reportParamMandaStr = reportParamMandaStr + obj5.value + ' &&& ';
		  		reportParameAliasStr = reportParameAliasStr + obj6.value + ' &&& ';
		  		reportParamDeleteStr = reportParamDeleteStr + ' Y&&&';
		   	 
		  }
	    }


	 //   alert("reportParameterName: " + reportParamNameStr);
	 //  alert("reportParameterDesc: " + reportParamDesStr);
	//   alert("reportParameterType: " + reportParamTypeStr);
	//   alert("reportParameterPriority: " + reportParamPrtysStr);
	 //  alert("reportParameterMandatory: " + reportParamMandaStr);
	//   alert("reportParameterName: " + reportParameAliasStr);
	//   alert("reportParameterAlias: " + reportParamDeleteStr);
	  
    document.getElementById('reportParameterName').value = reportParamNameStr;
   	document.getElementById('reportParameterDesc').value = reportParamDesStr;
   	document.getElementById('reportParameterType').value = reportParamTypeStr;
   	document.getElementById('reportParameterPriority').value = reportParamPrtysStr;
   	document.getElementById('reportParameterMandatory').value = reportParamMandaStr;
   	document.getElementById('reportParameterAlias').value = reportParameAliasStr;	   
   	document.getElementById('deleteCollection').value = reportParamDeleteStr;
 // // alert("Start the process");
	   var flag = 0;	  
	   
		if ($("#reportId").val() == "") {
			flag = flag + 1;			
			$(".error_msg").html("Vui lòng nhập mẫu báo cáo");
		}else if($("#reportName").val() == ""){
			flag = flag + 1;			
			$(".error_msg").html("Vui lòng nhập tên báo cáo");			
		}else if($("#description").val() == ""){
			flag = flag + 1;			
			$(".error_msg").html("Vui lòng nhập mô tả");			
		}else if($("#reportPriority").val() == ""){
			flag = flag + 1;		
			$(".error_msg").html("Vui lòng nhập thứ tự ưu tiên báo cáo");			
		}else if($("#reportCategory").val() == "" ||  $("#reportCategory").val() == "NONE"){			
			flag = flag + 1;			
			$(".error_msg").html("Vui lòng chọn danh mục");			
		}
		
		if (flag > 0) {			
			$("#msgDialog").dialog("open");
			
		}else{			
			 $('.modal').show();
			 
			if(task == 'save'){
		  		 $.post('<c:url value="/servlet/report/save" />',$('#nicReportForm').serialize(),
	         		   function(data){
			 			 // alert(data);
			 			 if(data =='success'){
			 				/* $('.border_success').show();
			 				$('#updates').hide();
			 				$('.border_error').hide(); */
			 				$("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
			 			    $("#savedialog-confirm").html('Đã lưu thành công báo cáo: '+$("#reportId").val());
			 			    $("#savedialog-confirm").dialog( 'open' );
			 			 }else if(data =='fail'){
			 				/* $('.border_success').hide();
			 				$('#failu').hide();
			 				$('.border_error').show();  */
			 				$("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
			 				$("#faildialog-confirm").html('Không lưu được báo cáo: '+$("#reportId").val());
			 				$("#faildialog-confirm").dialog( 'open' );
			 			 }else if(data =='exist'){
				 				/* $('.border_success').hide();
				 				$('#failu').hide();
				 				$('.border_error').show();  */
				 				$("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
				 				$("#savedialog-confirm").html('Mã báo cáo đã tồn tại trong hệ thống:  '+$("#reportId").val());
				 				$("#savedialog-confirm").dialog( 'open' );
				 		 }
	         		   //	$("#global_icon_div").html(data); 
			 			$('.modal').hide();
				 });    
		 
	     		  return false; 
			}else {				
				 $.post('<c:url value="/servlet/report/update" />',$('#nicReportForm').serialize(),
		         		   function(data){
				 			
					       if(data =='success'){
					    	    $("#updatedialog-confirm").dialog('option', 'title', 'Thông báo');
				 			    $("#updatedialog-confirm").html('Cập nhật báo cáo thành công: '+$("#reportId").val());
				 			    $("#updatedialog-confirm").dialog( 'open' );			 				
			 			   }else if(data =='fail'){
			 				  $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
				 			  $("#faildialog-confirm").html('Không cập nhật được báo cáo: '+$("#reportId").val());
				 			  $("#faildialog-confirm").dialog( 'open' );			 				
			 			   }
		         		   	//$("#global_icon_div").html(data); 
					       $('.modal').hide();
				 });    
			     
		     	 return false; 			
			 }
	   
		}
   }
   
	$("#cancel_btn").click(function(){			
		    var reportId =$("#reportId").val();		      
		    var reportCategory=$("#reportCategory").val();//$("#reportCategory  option").filter(":selected").text();		    
		    document.forms["nicReportForm"].reportId= reportId;		
		    document.forms["nicReportForm"].reportCategory=reportCategory;	
		    document.forms["nicReportForm"].categoryLoad.value='Y';		    
			document.forms["nicReportForm"].action = '${frdCnclRqUrl}';
		    document.forms["nicReportForm"].submit();		
		
	});
	$("#cancel_btn_one").click(function(){			
	    var reportId =$("#reportId").val();		      
	    var reportCategory=$("#reportCategory").val();
	    
	    document.forms["nicReportForm"].reportId= reportId;	   
	    document.forms["nicReportForm"].reportCategory=reportCategory;	
	    document.forms["nicReportForm"].categoryLoad.value='Y';	    
		document.forms["nicReportForm"].action = '${frdCnclRqUrl}';
	    document.forms["nicReportForm"].submit();		
	
    });
	
	function updateForm(){
		
	     document.getElementById('reportId').disabled=false;
		   $.post('<c:url value="/servlet/report/update" />',$('#nicReportForm').serialize(),
		            function(data){
		               // alert(data);            	
		            	$("#global_icon_div").html(data);                
		   }); 
		   document.getElementById('reportId').disabled=true; 
			
		   return false;
 }  
  
   $("#delete_btn").click(function(){ 
	    
	    $("#deletedialog-confirm").dialog('option', 'title', 'Delete Report');
	    $("#deletedialog-confirm").html('Bạn có chắc chắn muốn xóa Báo cáo: '+$("#reportId").val());
	    $("#deletedialog-confirm").dialog( 'open' );               

	});

	function doFormFrdSubmit(form) {
		
	
	var reportId = $("#reportId").val();
	var reportName = $("#reportName").val();
	var reportCategory = $("#reportCategory").val();
		//$("#reportCategory  option").filter(":selected")	.text();
		document.forms["nicReportForm"].reportId.value = reportId;
		document.forms["nicReportForm"].reportName.value = reportName;
		document.forms["nicReportForm"].reportCategory.value = reportCategory;
		document.forms["nicReportForm"].action = '${reqFrdURL}';
		document.forms["nicReportForm"].submit();

	}
	
	function doFormQuerySubmit(form) {
		
		
		var reportId = $("#reportId").val();
		var reportName = $("#reportName").val();
		var reportCategory = $("#reportCategory").val();
		//$("#reportCategory  option").filter(":selected").text();
			document.forms["nicReportForm"].reportId.value = reportId;
			document.forms["nicReportForm"].reportName.value = reportName;
			document.forms["nicReportForm"].reportCategory.value = reportCategory;
			document.forms["nicReportForm"].action = '${queryUpdateUrl}';
			document.forms["nicReportForm"].submit();

		}
	
	
	 $("#reset_btn_one").click(function(){	
			
		   document.forms["nicReportForm"].action = '${frdDefUrl}';
		   document.forms["nicReportForm"].submit();			
	});

	$("#reset_btn_two").click(function(){
		document.getElementById('reportId').disabled=false;
		var repId=$("#reportId").val();
		document.forms["nicReportForm"].reportId.value = repId;	
		document.forms["nicReportForm"].action = '${frdEditPgUrl}';
		document.forms["nicReportForm"].submit();
		
	});
	$(function() {
	    $( "#deletedialog-confirm" ).dialog({
		modal: true,
	      autoOpen: false,
		  width : 500,
		  resizable: true,
		  open : function() {
			    $('.ui-dialog-buttonpane span').addClass('btn_blue gradient'); 
			   
		  },
	      show: {
	        effect: "fade",
	        duration: 200
	      },
	      hide: {
	        //effect: "explode",
	        //duration: 1000
	      },
		   buttons:{
	    "Đồng ý": function() {
	    	$(this).dialog("close");
	    	var reportId = $("#reportId").val();
			var reportCategory =$("#reportCategory").val();// $("#reportCategory  option").filter(":selected").text();
			document.forms["nicReportForm"].reportId = reportId;
			document.forms["nicReportForm"].reportCategory = reportCategory;
			document.forms["nicReportForm"].categoryLoad.value = "Y";
			document.forms["nicReportForm"].action = '${deleteRptURL}';
			document.forms["nicReportForm"].submit();
	    },
		"Hủy": function() {
			$(this).dialog("close");
	    }
	   }
	    });
	  });
	$(function() {
	    $("#savedialog-confirm" ).dialog({
		modal: true,
	      autoOpen: false,
		  width : 500,
		  resizable: true,
		  open : function() {
			    $('.ui-dialog-buttonpane span').addClass('btn_blue gradient'); 
			   
		  },
	      show: {
	        effect: "fade",
	        duration: 200
	      },
	      hide: {
	        //effect: "explode",
	        //duration: 1000
	      },
		   buttons:{
	    "Đồng ý": function() {    	
	    	  $(this).dialog("close");
	    	 // var category = $("#reportCategory").val();
	    	 // document.forms["nicReportForm"].action = '${repCatUrl}/'+category;
	    	//  document.forms["nicReportForm"].submit();
	    	var reportId =$("#reportId").val();		      
	  	    var reportCategory=$("#reportCategory").val();	  	    
	  	    document.forms["nicReportForm"].reportId= reportId;	   
	  	    document.forms["nicReportForm"].reportCategory=reportCategory;	
	  	    document.forms["nicReportForm"].categoryLoad.value='Y';	    
	  		document.forms["nicReportForm"].action = '${frdCnclRqUrl}';
	  	    document.forms["nicReportForm"].submit();	
	    	 
	    }	
	   }
	    });
	  });
	$(function() {
	    $("#updatedialog-confirm" ).dialog({
		modal: true,
	      autoOpen: false,
		  width : 500,
		  resizable: true,
		  open : function() {
			    $('.ui-dialog-buttonpane span').addClass('btn_blue gradient'); 
			   
		  },
	      show: {
	        effect: "fade",
	        duration: 200
	      },
	      hide: {
	        //effect: "explode",
	        //duration: 1000
	      },
		   buttons:{
	    "Đồng ý": function() {    	
	    	    $(this).dialog("close");	    	  
	    	    var reportId =$("#reportId").val();		      
			    var reportCategory=$("#reportCategory").val();//$("#reportCategory  option").filter(":selected").text();		    
			    document.forms["nicReportForm"].reportId= reportId;		
			    document.forms["nicReportForm"].reportCategory=reportCategory;	
			    document.forms["nicReportForm"].categoryLoad.value='Y';		    
				document.forms["nicReportForm"].action = '${frdCnclRqUrl}';
			    document.forms["nicReportForm"].submit();
	    	 // var category = $("#reportCategory").val();
	    	 // document.forms["nicReportForm"].action = '${repCatUrl}/'+category;
	    	 // document.forms["nicReportForm"].submit();
	    	 
	    }	
	   }
	    });
	  });
	  
	$(function() {
	    $("#faildialog-confirm" ).dialog({
		modal: true,
	      autoOpen: false,
		  width : 500,
		  resizable: true,
		  open : function() {
			    $('.ui-dialog-buttonpane span').addClass('btn_blue gradient'); 
			   
		  },
	      show: {
	        effect: "fade",
	        duration: 200
	      },
	      hide: {
	        //effect: "explode",
	        //duration: 1000
	      },
		   buttons:{
	    "Đồng ý": function() {    	 
	    	$(this).dialog("close"); 
	    }	
	   }
	    });
	  });

function resetParam(){
	
	$("#paramName").val("");
	$("#paramDes").val("");
	$("#paramType").val("");
	$("#priority").val("");
	$("#paramAlias").val("");
}

$("#row tr").each(function() {
    var deletedFlag = $(this).find("td:nth(3)").text();
    if (deletedFlag == "true") {
      $(this).attr("class","darkred");   
    }
 });
 
</script>
<!-- </div> -->
<div id="savedialog-confirm"></div>
<div id="faildialog-confirm"></div>
<div id="updatedialog-confirm"></div>
</div>
</div>
</div>
 </form:form>
 
<div class="modal">
		<!-- Place at bottom of page -->
</div>
 
 <div id="paramDialog">	
	  	<div class="col-md-12">
				<div class="col-md-3 fix-bottom">
					<label for="countryCode">Tên tham số<i style="color: red">(*)</i>:</label>
				</div>
				<div class="col-md-9 fix-bottom">
					<input id="paramName"  type="text" size='50' class="form-control"/>
				</div>
				<div class="col-md-3 fix-bottom">
					<label for="countryCode">Mô tả<i style="color: red">(*)</i>:</label>
				</div>
				<div class="col-md-9 fix-bottom">
					<input id="paramDes"  type="text" size='50' class="form-control"/>
				</div>
				<div class="col-md-3 fix-bottom">
					<label for="countryCode">Kiểu dữ liệu<i style="color: red">(*)</i>:</label>
				</div>
				<div class="col-md-9 fix-bottom">
					<input id="paramType"  type="text" size='50' class="form-control"/>
				</div>
				<div class="col-md-3 fix-bottom">
					<label for="countryCode">Ưu tiên (1-9)<i style="color: red">(*)</i>:</label>
				</div>
				<div class="col-md-9 fix-bottom">
					<input id="priority"  type="text" size='50' class="form-control"/>
				</div>
				<div class="col-md-3 fix-bottom">
					<label for="countryCode">Bắt buộc</label>
				</div>
				<div class="col-md-9 fix-bottom">
					<select id="mandatory"  class="form-control">						
						<option value="N" label="Không" />
						<option value="Y" label="Có" />
					</select>
				</div>
				<div class="col-md-3 fix-bottom">
					<label for="countryCode">Hiển thị<i style="color: red">(*)</i>:</label>
				</div>
				<div class="col-md-9 fix-bottom">
					<input id="paramAlias"  type="text" size='50' class="form-control"/>
				</div>
         </div>	 
	<!--<table id="paramDialogTable1"  width="100%" height="100%" align="center" class="displayTag" style="background-image:url('<%=request.getContextPath() %>/resources/images/head.png');background-repeat: repeat-x;">
			<tr>
				<td width="20%">Tên tham số<span style="COLOR: #ff0000;">*</span>:</td>
				<td><input id="paramName"  type="text" size='50' /></td>
			</tr>
			<tr>
				<td>Mô tả<span style="COLOR: #ff0000;">*</span>:</td>
				<td><input id="paramDes"  type="text" size='50' /></td>
				
			</tr>
			 <tr>
				<td width="20%">Kiểu dữ liệu<span style="COLOR: #ff0000;">*</span>:</td>
				<td><input id="paramType"  type="text" size='50' /></td>
			</tr>
			<tr>
				<td width="20%">Ưu tiên (1-9)<span style="COLOR: #ff0000;">*</span>:</td>
				<td><input id="priority"  type="text" size='50' /></td>
			</tr>

			<tr>
				<td>Bắt buộc:</td>
				<td><select id="mandatory"  >						
						<option value="N" label="Không" />
						<option value="Y" label="Có" />
					</select></td>
			</tr>
			<tr>
				<td width="20%">Hiển thị<span style="COLOR: #ff0000;">*</span>:</td>
				<td><input id="paramAlias"  type="text" size='50' /></td>
			</tr>
	</table>-->
</div>
<div id="deletedialog-confirm"></div>


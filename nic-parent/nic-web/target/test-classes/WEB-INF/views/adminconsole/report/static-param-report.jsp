<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url var="submitUrl" value="/servlet/reportmgmt/reportGeneration" />
<c:url var="cancelUrl" value="/servlet/reportmgmt/generation" />
<c:url var="url" value="/servlet/reportmgmt" />
<c:url var="downloadRpt" value="/servlet/reportmgmt/staticParamRep" />
<c:url var="staticpdf" value="/servlet/reportmgmt/staticpdf" />
<c:url var="getCustomTableData" value="/servlet/reportmgmt/getCustomTableData" />

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
#inner_content {
	border: thin;
	float: left;
	font-family: Verdana, Arial, Helvetica, sans-serif;
	background-color: #F0F0F0;
	/* width: 1250px; */
}

#inner_main_left {
	float: left;
	width: 950px;
	height: 320px;
	overflow: hidden;
}

/*  #showHtmlRpt {
	float: left;
	width: 1200px; 
	/* height: 200px; */
}
*
/
.fix-bottom {
	margin-top: 15px;
}
.width-common {
	width: 70%;
}
img.ui-datepicker-trigger {
	float: right;
    margin-top: -22px;
    margin-right: 125px;
}
</style>
<script>

		$(document).ready(function() {
							$("#showHtmlRpt").hide();
							$(".dateFieldClass")
									.datepicker(
											{
												showOn : "button",
												buttonImage : "<c:url value="/resources/images/calendar.gif" />",
												buttonImageOnly : true,
												changeMonth : true,
												changeYear : true,
												 showSecond : true,
												controlType : 'select',
												/* timeFormat : 'hh:mm:ss', */ 
												dateFormat : 'mm/dd/yy',
												yearRange:"-100:+10"
											});
							
							$(".numberFieldClass").number(true);
							});
		
		$.fn.serializeObject = function()
		{
		    var o = {};
		    var a = this.serializeArray();
		    $.each(a, function() {
		        if (o[this.name] !== undefined) {
		            if (!o[this.name].push) {
		                o[this.name] = [o[this.name]];
		            }
		            o[this.name].push(this.value || '');
		        } else {
		            o[this.name] = this.value || '';
		        }
		    });
		    return o;
		};
		$(function() {
			$("#msgDialog").dialog({
				width : 400,
				height : 150,
				resizable : false,
				modal : true,
				autoOpen : false,
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
					"Đóng" : function() {
						$(this).dialog("close");
					}
				}
			});
			
	

	
	


	$("#genBtnId").click(function() {
		 var category= $("#reportCategory").val();
		 var rptFrmt= $("#rptFrmt").val();
			if(category=='CR'){
				if(rptFrmt != 'HTML'){
				 var jsonForm = JSON.stringify($('#reportMenuForm').serializeObject());							 
				 window.location = '${staticpdf}'+'?jsonObj='+jsonForm;
				}else{
					 var jsonForm = JSON.stringify($('#reportMenuForm').serializeObject());		
					var test = '${getCustomTableData}'+'?jsonObj='+jsonForm;
					$.ajax({
				   	    type : "GET",
				   	    url : test,
				   	    success : function(data) {
				   	 	if(data!='-1'){
				    			$('#searchResult').html(data);
				    	}else{
				    		$('#searchResult').html('Xảy ra lỗi trong khi nhận kết quả tìm kiếm, vui lòng kiểm tra nhật ký để biết thêm chi tiết.');
				    	}
				   	 		   },
				 		error : function(e) {
				 			$('#searchResult').html('Xảy ra lỗi trong khi nhận kết quả tìm kiếm, vui lòng kiểm tra nhật ký để biết thêm chi tiết.');
						}
					});
					
				}
			}else{
		
						var mandateFieldArr = new Array();
						$.each($("input[data-mandatory='Y']"),function(index,val){
							if(val.value == ''){
								mandateFieldArr.push(val.id+" là bắt buộc");
							}
						});
						$.each($("select[data-mandatory='Y']"),function(index,val){
							if(val.value == 'All'){
								mandateFieldArr.push(val.id+" là bắt buộc");
							}
						});
						if(mandateFieldArr.length > 0){
							//alert(mandateFieldArr.join("\n"));
							$.alert({
								title: 'Thông báo',
								content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + mandateFieldArr.join("\n"),
							});
							return false;
						}      
									
							
							 var jsonForm = JSON.stringify($('#reportMenuForm').serializeObject());							 
							 window.location = '${downloadRpt}'+'?jsonObj='+jsonForm;	
	}
						
	});

	$("#resetBtn").click(
			function() {
				var id =$("#reportId").val()+","+$("#reportFileName").val()+","+$("#reportCategory").val();
				window.location.href="${url}/reportGeneration/" + id;					 
	});		
	
	$("#cancelBtn").click(function(){	
		 document.forms["reportMenuForm"].action = '${cancelUrl}/'  + $("#reportCategory").val();
		 document.forms["reportMenuForm"].submit();	

	});
});

</script>
<form:form method="GET" modelAttribute="reportMenuForm" id="reportMenuForm"	 enctype="multipart/form-data" cssClass="inline" >
	<div class="">		
		<div>
	        	<div class="row">
	        		<div class="ov_hidden">
		<!-- Content Start -->
		<!-- <div id="content"> -->
			<!--<span id="heading_report" style="width: 250px">Tạo báo cáo</span><br />
			<br />-->
			<div class="new-heading-2">TẠO BÁO CÁO</div>
	<c:if test="${not empty requestScope.Errors}">
		<div style="color:Red;background-color:White;border-style:solid;border-color:Red;border-width:thin;">
			<c:forEach items="${requestScope.Errors}" var="takla">
				<li style="height: 10px;padding-top: 20px;font-size: 16px;">
					${takla}
				</li>
			</c:forEach>
			
		</div>
	</c:if>
			<div>
				<div class="col-md-12">
					<div class="col-md-6">
						<c:forEach items="${reportMenuForm.nicReportMenuList}" var="codevalueItem" varStatus="status">
							<div class="col-md-4 fix-bottom" style="margin-top: 15px;">
								<label for="countryCode">${codevalueItem.propId}
									<c:if test="${codevalueItem.mandatory == 'Y'}">
										<i style="color: red">(*)</i>
									</c:if>
								</label>
							</div>
							<div class="col-md-8 fix-bottom" style="margin-top: 15px;">
								<c:choose>
									<c:when test="${codevalueItem.propertyType == 'date'}">
										 <input type="text" data-mandatory="${codevalueItem.mandatory}" class="dateFieldClass form-control width-common" readonly="readonly" id="${codevalueItem.propId}" name="${codevalueItem.propertyName}" />
									</c:when>
									<c:when test="${codevalueItem.propertyType == 'number'}">							
										 <input type="text" data-mandatory="${codevalueItem.mandatory}" class="numberFieldClass form-control width-common"  value="" width="15" id="${codevalueItem.propId}" name="${codevalueItem.propertyName}" />
									</c:when>
									<c:when test="${codevalueItem.propertyType == 'multipleValue'}">
											<select class="form-control width-common" data-mandatory="${codevalueItem.mandatory}"	id="${codevalueItem.propId}" name="${codevalueItem.propertyName}">
											<option value="" label="---Chọn---" />	
												<c:forEach var="maplist" items="${codevalueItem.multiMenu}" varStatus="status1">												
													<option value="${maplist.key}" label="${maplist.value}" />
												</c:forEach>
											</select>					
									</c:when>
									<c:when test="${codevalueItem.propertyType == 'number_range'}">									
											<select class="form-control width-common" data-mandatory="${codevalueItem.mandatory}"	id="${codevalueItem.propId}" name="${codevalueItem.propertyName}">
											<option value="0" label="---TODAY---" />	
												<c:forEach var="maplist" items="${codevalueItem.multiMenu}" varStatus="status1">												
																<option value="${maplist.key}" label="${maplist.value}" />
												</c:forEach>
											</select>
									</c:when>
									<c:otherwise>
										<c:choose>
										  <c:when test='${codevalueItem.propertyName == "gender"}'>										 
												<select data-mandatory="${codevalueItem.mandatory}" class="form-control width-common" id="${codevalueItem.propId}" name="${codevalueItem.propertyName}">
													<option value="" label="---Chọn---" />
													<option value="M" label="Nam" />	
													<option value="F" label="Nữ" />	
													<option value="U" label="Khác" />	
												</select>
										  </c:when>
										  <c:otherwise>
										    	<input type="text" class="form-control width-common" data-mandatory="${codevalueItem.mandatory}" value="" width="15" id="${codevalueItem.propId}" name="${codevalueItem.propertyName}" />
										  </c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</div>
						</c:forEach>
						<div class="col-md-4" style="margin-top: 15px;">
							<label for="countryCode">Định dạng báo cáo</label>
						</div>
						<div class="col-md-8" style="margin-top: 15px;">
							<form:select id="rptFrmt" class="form-control width-common" path="rptFrmt">						
								<form:option value="pdf">PDF</form:option>
								<form:option value="xls">Excel</form:option>
								<form:option value="csv">CSV</form:option>
								<c:if test="${reportMenuForm.reportCategory eq 'CR'}">
									<form:option value="HTML">Tabular</form:option>
								</c:if>						
							</form:select>
						</div>
					</div>
				</div>
				<!--<table  id="tablepaging"  style="width:100%;background-color:#E3F1FE" class="data_table" >
					 <tr>
					 	<td valign="top">
					 		<table id="intputMenu">
					 			<tr>
                            <td colspan=4>&nbsp;</td>
                        </tr>
					<c:forEach var="codevalueItem" items="${reportMenuForm.nicReportMenuList}" varStatus="status">
						
						<tr >
						<td class="subjectsmall" id="sdate" align="left" width="200" height="25">${codevalueItem.propId}
							<c:if test='${codevalueItem.mandatory =="Y"}'>
								<span style="COLOR: #ff0000;">*</span>
							</c:if>
						</td>						
						<td align="left" width="2" height="25">:</td>
						
						<c:choose>
							<c:when test="${codevalueItem.propertyType == 'date'}">
								<td class='description_slimplain' align="left" style="width: 200px;" height="25">
									<input type="text" data-mandatory="${codevalueItem.mandatory}" class="dateFieldClass" readonly="readonly" id="${codevalueItem.propId}" name="${codevalueItem.propertyName}" />
																	
								</td>
							</c:when>
							<c:when test="${codevalueItem.propertyType == 'number'}">
								<td class='description_slimplain' align="left" style="width: 200px;" height="25">
									<input type="text" data-mandatory="${codevalueItem.mandatory}" class="numberFieldClass"  value="" width="15" id="${codevalueItem.propId}" name="${codevalueItem.propertyName}" />
								</td>
							</c:when>
							<c:when test="${codevalueItem.propertyType == 'multipleValue'}">
								<td class='description_slimplain' align="left" width="100" height="25">
									<select style="width: 30;" data-mandatory="${codevalueItem.mandatory}"	id="${codevalueItem.propId}" name="${codevalueItem.propertyName}">
									<option value="" label="---Chọn---" />	
										<c:forEach var="maplist" 
														items="${codevalueItem.multiMenu}" varStatus="status1">												
														<option value="${maplist.key}" label="${maplist.value}" />
										</c:forEach>
									</select>
								</td>
							</c:when>
							<c:when test="${codevalueItem.propertyType == 'number_range'}">
								<td class='description_slimplain' align="left" width="100" height="25">
									<select style="width: 30;" data-mandatory="${codevalueItem.mandatory}"	id="${codevalueItem.propId}" name="${codevalueItem.propertyName}">
									<option value="0" label="---TODAY---" />	
										<c:forEach var="maplist" 
														items="${codevalueItem.multiMenu}" varStatus="status1">												
														<option value="${maplist.key}" label="${maplist.value}" />
										</c:forEach>
									</select>
								</td>
							</c:when>
							<c:otherwise>
								<c:choose>
								  <c:when test='${codevalueItem.propertyName == "gender"}'>
								    <td class='description_slimplain' align="left" width="100" height="25">
										<select data-mandatory="${codevalueItem.mandatory}"	id="${codevalueItem.propId}" name="${codevalueItem.propertyName}">
											<option value="" label="---Chọn---" />
											<option value="M" label="Male" />	
											<option value="F" label="Female" />	
											<option value="U" label="Unknown" />	
										</select>
									</td>
								  </c:when>
								  <c:otherwise>
								    <td class='description_slimplain' align="left" width="100" height="25">
								    	<input type="text" data-mandatory="${codevalueItem.mandatory}" value="" width="15" id="${codevalueItem.propId}" name="${codevalueItem.propertyName}" />
								    </td>
								  </c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
						<td>							
						</td>
						</tr>
						
					</c:forEach>
					
					<tr>
						<td class="subjectsmall" height="50" width="150">Định dạng báo cáo</td>
						<td align="left" width="2" height="25">:</td>
						<td class='description_slimplain' align="left" width="100"
							height="25">							
						<form:select id="rptFrmt"  path="rptFrmt">						
								<form:option value="pdf">PDF</form:option>
								<form:option value="xls">Excel</form:option>
								<form:option value="csv">CSV</form:option>
								<c:if test="${reportMenuForm.reportCategory eq 'CR'}">
									<form:option value="HTML">Tabular</form:option>
								</c:if>						
						</form:select></td>
						<td></td>
					</tr>

					

					<tr>
						<td colspan="3">&nbsp;</td>
					</tr>
					 		</table>
					 	</td>
					 	
					 	
					 </tr>
					  <tr>
						<!-- <td colspan="5" align="left" style="padding: 40px;">
						<input 	type="button"  class="button_grey" 	id="genBtnId" value="Generate" />
						<input id="resetBtn"  type="button" class="button_grey" value="Reset" align="center" /> -->
						<form:input type="hidden" id="reportId" path="reportId" />						
						<form:input type="hidden" id="reportFileName" path="reportFileName" />
						 <form:input type="hidden" id="reportCategory" path="reportCategory" />	
						
					<!--</tr>


				</table>-->
				</div>
				<div class="col-md-12" style="text-align: right;margin-top: 30px;">
					<button type="button" class="btn btn-danger"  id="cancelBtn">
						<span class="glyphicon glyphicon-remove"></span> Hủy
					</button>
					<button type="button" class="btn btn-primary" id="resetBtn">
						<span class="glyphicon glyphicon-refresh"></span> Làm mới
					</button>
					<button type="button" class="btn btn-success" id="genBtnId">
						<span class="glyphicon glyphicon-ok"></span> Tạo báo cáo
					</button>
					<!--<input type="button" id="cancelBtn" class="btn btn-danger"  value="Hủy" />
					<input type="button" id="resetBtn" class="btn btn-primary" value="Làm lại" />
	         		<input type="button" class="btn btn-success" id="genBtnId" value="Tạo báo cáo" />-->
	            </div>
				<!--<div align="right" class="displayTag" style="background-image:url('<%=request.getContextPath() %>/resources/images/head.png');background-repeat: repeat-x;">
				<table>
				 <tr>
						 <td colspan="5" align="center" style="padding: 20px;">
						<input 	type="button"  class="button_grey" 	id="genBtnId" value="Tạo báo cáo" />
						<input id="resetBtn"  type="button" class="button_grey" value="Làm lại" align="center" />
						<input id="cancelBtn"  type="button" class="button_grey" value="Hủy" align="center" />
						
						
						
				</tr>
				</table>
				</div>-->	
				<div id="searchResult">
				</div>
</div>
</div>
		</div>

	<!-- </div> -->
<div class="modal">
		<!-- Place at bottom of page -->
</div>
	<script>
		
		
	</script>




</form:form>

<div id="msgDialog" title="Thông báo">
	<div class="isa_info" align="center">
		<span style="font-size: 40"></span>
	</div>
	<div class="error_msg"></div>
</div>
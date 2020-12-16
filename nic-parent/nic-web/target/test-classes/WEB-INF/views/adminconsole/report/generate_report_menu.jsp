<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url var="submitUrl" value="/servlet/reportmgmt/reportGeneration" />
<c:url var="cancelUrl" value="/servlet/reportmgmt/generation" />
<c:url var="url" value="/servlet/reportmgmt" />
<c:url var="pdfDownload" value="/servlet/reportmgmt/pdf" />
<c:url var="xlsDownload" value="/servlet/reportmgmt/xls" />
<c:url var="csvDownload" value="/servlet/reportmgmt/csv" />
<c:url var="staticpdf" value="/servlet/reportmgmt/staticpdf" />

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
												dateFormat : 'dd/mm/yy',
												yearRange:"-100:+10"
											});
							
							
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
					Ok : function() {
						$(this).dialog("close");
					}
				}
			});
			$('#removBtnId').click(	function() {
				$('#selRptcols :selected').each(
						function() {
							$('#rptcols').append(
									"<option value='" + $(this).val()
											+ "'>" + $(this).text()
											+ "</option>");
							$(this).remove();
						});
			});
	$('#addBtnId').click(
			function() {
				$('#rptcols option:selected').each(
						function() {
							$('#selRptcols').append(
									"<option value='" + $(this).val()
											+ "'>" + $(this).text()
											+ "</option>");
							$(this).remove();
						});
			});

	$('#selectAllCol1BtnId').click(function() {
		$('#rptcols option').prop('selected', 'selected');
		
	});

	$('#selectAllCol2BtnId').click(function() {
		$('#selRptcols option').prop('selected', 'selected');
		
	});
	


	$("#genBtnId").click(function() {
		 var category= $("#reportCategory").val();
		if(category=='CR'){
			 var jsonForm = JSON.stringify($('#reportMenuForm').serializeObject());							 
			 window.location = '${staticpdf}'+'?jsonObj='+jsonForm;
		}else{
						var mandateFieldArr = new Array();
						$.each($("input[data-mandatory='Y']"),function(index,val){
							if(val.value == ''){
								mandateFieldArr.push(val.id+" is Mandatory");
							}
						});
						$.each($("select[data-mandatory='Y']"),function(index,val){
							if(val.value == 'All'){
								mandateFieldArr.push(val.id+" is Mandatory");
							}
						});
						if(mandateFieldArr.length > 0){
							alert(mandateFieldArr.join("\n"));
							return false;
						}
						
		                var flag = 0;
						var id = $("#reportId").val();
						var tabName = $("#tableName").val();
						var objName = $("#objName").val();
						
						$('#selRptcols option').prop('selected', 'selected');							
						var columnNames = [];
						var i = 0;
						$("#selRptcols option").each(function(key, value) {
							columnNames[key] = value;
						});
						
						if ($("#rptFrmt").val() == 'pdf') {
							
							$("#showHtmlRpt").hide();
							 var jsonForm = JSON.stringify($('#reportMenuForm').serializeObject());							 
							 window.location = '${pdfDownload}'+'?jsonObj='+jsonForm;									
						}else if ($("#rptFrmt").val() == 'xls') {
							
							$("#showHtmlRpt").hide();
							 var jsonForm = JSON.stringify($('#reportMenuForm').serializeObject());							 
							 window.location = '${xlsDownload}'+'?jsonObj='+jsonForm;									
						}else if ($("#rptFrmt").val() == 'csv') {
							
							$("#showHtmlRpt").hide();
							 var jsonForm = JSON.stringify($('#reportMenuForm').serializeObject());							 
							 window.location = '${csvDownload}'+'?jsonObj='+jsonForm;									
						}else{
							$("#showHtmlRpt").empty();
							$("#showHtmlRpt").append($("<table>").attr("id", "tranRptflexme"));
							$("#showHtmlRpt").show();
							var columns = [];
							var tName = "Table Name";
							$("#selRptcols option").each(function() {
								var jsonObj = {};
								jsonObj['display'] = $(this).text();
								jsonObj['name'] = $(this).val();
								jsonObj['width'] = '200';
								jsonObj['height'] = '25';
								jsonObj['align'] = 'center';
								jsonObj['sortable'] = 'false';
								columns.push(jsonObj);											

							});	
							var jsonForm = JSON.stringify($('#reportMenuForm').serializeObject());
							$("#tranRptflexme").flexigrid({
								params : [ {
										name : "jsonForm",
										value : jsonForm
								} ],													
								url : "${url}/report_query",                                                    
								dataType : 'json',
								colModel : columns,
								sortname : "name",
								sortorder : "asc",
								title : tName,
								usepager : true,
								useRp : true,
								rp : 10,
								showTableToggleBtn : true,
								width : 1300,
								height : 200,
								singleSelect : true,
								nowrap : false

							});
						}
		}
	});

	$("#resetBtn").click(
			function() {
				var id =$("#reportId").val()+","+$("#reportFileName").val()+","+$("#reportCategory").val();
				window.location.href="${url}/reportGeneration/" + id;					 
	});		
	
	$("#cancelBtn").click(function(){	
		 document.forms["reportMenuForm"].action = '${cancelUrl}/' +$("#reportCategory").val();
		 document.forms["reportMenuForm"].submit();	

	});
});

</script>
<form:form method="GET" modelAttribute="reportMenuForm" id="reportMenuForm"	 enctype="multipart/form-data" cssClass="inline" >
	<div id="main">		
	<div class="container">
        	<div class="row">
        		<div class="roundedBorder ov_hidden">
		<!-- Content Start -->
		<!-- <div id="content"> -->
			<span id="heading_report" style="width: 250px">Tìm kiếm báo cáo</span><br />
			<br />
	<c:if test="${not empty requestScope.Errors}">
		<div style="color:Red;background-color:White;border-style:solid;border-color:Red;border-width:thin;">
			<c:forEach items="${requestScope.Errors}" var="takla">
				<li style="height: 40px;padding-top: 20px;font-size: 16px;">
					${takla}
				</li>
			</c:forEach>
			
		</div>
	</c:if>
			<div>
				<table  id="tablepaging"  style="width:100%;background-color:#E3F1FE" class="data_table" >
					 <tr>
					 	<td valign="top">
					 		<table id="intputMenu">
					 			<tr>
                            <td colspan=4>&nbsp;</td>
                        </tr>
					<c:forEach var="codevalueItem" items="${reportMenuForm.nicReportMenuList}" varStatus="status">
						
						<tr >
						<td class="subjectsmall" id="sdate" align="left" width="200" height="25">${codevalueItem.propertyName}
							<c:if test='${codevalueItem.mandatory =="Y"}'>
								<span style="COLOR: #ff0000;">*</span>
							</c:if>
						</td>						
						<td align="left" width="2" height="25">:</td>
						
						<c:choose>
							<c:when test="${codevalueItem.propertyType == 'date'}">
								<td class='description_slimplain' align="left" style="width: 200px;" height="25">
									<input type="text" data-mandatory="${codevalueItem.mandatory}" class="dateFieldClass" readonly="readonly" name="${codevalueItem.propId}" id="${codevalueItem.propertyName}" />
																	
								</td>
							</c:when>
							<c:when test="${codevalueItem.propertyType == 'multipleValue'}">
								<td class='description_slimplain' align="left" width="100"
							height="25"><select data-mandatory="${codevalueItem.mandatory}"
								name="${codevalueItem.propId}" id="${codevalueItem.propertyName}">
								<option value="All" label="---SELECT---" />
								<c:forEach var="maplist" 
												items="${codevalueItem.multiMenu}" varStatus="status1">												
												<option value="${maplist.value }" label="${maplist.value }" />
								</c:forEach>
							</select>
						</td>
							</c:when>
							<c:otherwise>
								<td class='description_slimplain' align="left" width="100"
							height="25"><input type="text" data-mandatory="${codevalueItem.mandatory}" value="" width="15" name="${codevalueItem.propId}"
							id="${codevalueItem.propertyName}" /></td>
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
								<form:select id="rptFrmt" 
											path="rptFrmt" name="rptFrmt">
												<form:option value="html" label="Tabular" />
												<form:option value="pdf" label="pdf" />
												<form:option value="xls" label="Excel" />
												<form:option value="csv" label="CSV" />
										</form:select>
						</td>
						<td></td>
					</tr>
					<tr>
						<td colspan="3">&nbsp;</td>
					</tr>
					 		</table>
					 	</td>
					 	
					 	<td>
					 		<table>
								<tr>
									<td class="subjectsmall">Loại báo cáo&nbsp;&nbsp;<input id="selectAllCol1BtnId"
										type="button" class="button_grey" value="Select All"
										align="right" />
									</td>
									<td></td>
									<td class="subjectsmall">Chọn&nbsp;&nbsp;<input
										id="selectAllCol2BtnId" type="button" class="button_grey"
										value="Select All" align="center" />
									</td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td><form:select id="rptcols" multiple="multiple"
											path="unSelectedCols" name="rptCols[]"
											style="width:200px;;height:240px">
											<c:forEach var="columns"
												items="${reportMenuForm.columnParamList}"
												varStatus="status">
												<form:option value="${columns.key}"
													label="${columns.value }" />
											</c:forEach>
										</form:select></td>
									<td>
										<table>
											<tr>
												<td><input id="addBtnId" type="button" class="button_grey"
													value=">>" align="center" /></td>
											</tr>
											<tr>
												<td><input id="removBtnId" type="button"
													class="button_grey" value="<<" align=" center" /></td>
											</tr>
										</table>
									</td>
									<td><form:select id="selRptcols" multiple="multiple"
											path="selectedCols" name="rptCols[]"
											style="width:200px;;height:240px">
											<c:forEach var="item" 
											          items="${reportMenuForm.queryColumnList}"
											          varStatus="status2">
												<form:option value="${item.key}" label="${item.value }" data-isdate=""/>
												
											</c:forEach>
											<%-- <form:options items="${reportMenuForm.columnValues}" /> --%>
										</form:select> <!-- <select id="selRptcols" style="width:200px;height:200px"   multiple="multiple" name="countriesAdded[]"> 
										</select> --></td>
								</tr>
							</table>
					 	</td>
					 </tr>
				</table>
				<form:input type="hidden" id="reportId"       path="reportId" />
				<form:input type="hidden" id="tableName"      path="tableName" />
				<form:input type="hidden" id="objName"        path="objName" />
				<form:input type="hidden" id="reportCategory" path="reportCategory" />	
				<form:input type="hidden" id="reportFileName" path="reportFileName" />
				<form:input type="hidden" id="loginUser"      path="loginUser" value="${sessionScope.userSession.userName}" />
				
				</div>
				<div align="right" class="displayTag" style="background-image:url('<%=request.getContextPath() %>/resources/images/head.png');background-repeat: repeat-x;">
				<table>
				 <tr>
						 <td colspan="5" align="center" style="padding: 20px;">
						<input 	type="button"  class="button_grey" 	id="genBtnId" value="Tạo báo cáo" />
						<input id="resetBtn"  type="button" class="button_grey" value="Làm lại" align="center" />
						<input id="cancelBtn"  type="button" class="button_grey" value="Hủy" align="center" />
						</td>
				</tr>
				</table>
				</div>
				
			

			<div id="showHtmlRpt">

				<table id="tranRptflexme">
					<tr>
						<td colspan="3">&nbsp;</td>
					</tr>
				</table>
			</div>
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

<div id="msgDialog" title="Alert">
	<div class="isa_info" align="center">
		<span style="font-size: 40"></span>
	</div>
	<div class="error_msg"></div>
</div>
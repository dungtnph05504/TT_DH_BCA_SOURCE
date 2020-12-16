
<%
	try {
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url var="exceptionHandlingResultUrl"
	value="/servlet/txnReport/showExceptionHandlngRptTabluarResult" />
<c:url var="exceptionHandlingPdfResultUrl"
	value="/servlet/txnReport/showExceptionHandlngRptPdfResult" />
<c:url var="exceptionHandlingExcelResultUrl"
	value="/servlet/txnReport/showExcelResult" />
<style>
#inner_content {
	border: thin;
	float: left;
	font-family: Verdana, Arial, Helvetica, sans-serif;
	background-color: #F0F0F0;
	width: 1280px;
}

#inner_main_left {
	float: left;
	width: 950px;
	height: 315px;
	overflow: hidden;
}

#showHtmlRpt {
	float: left;
	width: 1280px;
	height: 215px;
}

#heading_left {
	text-align: center;
	float: left;
	height: 45px;
	line-height: 45px;
	width: 300px;
	font-weight: bold;
	color: #FFF;
	background: #c5deea; /* Old browsers */
	background: -moz-linear-gradient(top, #c5deea 0%, #8abbd7 8%, #066dab 100%);
	/* FF3.6+ */
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #c5deea),
		color-stop(8%, #8abbd7), color-stop(100%, #066dab));
	/* Chrome,Safari4+ */
	background: -webkit-linear-gradient(top, #c5deea 0%, #8abbd7 8%, #066dab 100%);
	/* Chrome10+,Safari5.1+ */
	background: -o-linear-gradient(top, #c5deea 0%, #8abbd7 8%, #066dab 100%);
	/* Opera 11.10+ */
	background: -ms-linear-gradient(top, #c5deea 0%, #8abbd7 8%, #066dab 100%);
	/* IE10+ */
	background: linear-gradient(to bottom, #c5deea 0%, #8abbd7 8%, #066dab 100%);
	/* W3C */
	filter: progid:DXImageTransform.Microsoft.gradient(    startColorstr='#c5deea',
		endColorstr='#066dab', GradientType=0); /* IE6-9 */
}

#heading {
	height: 25px;
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #333333;
}
</style>
<form:form id="exceptionHandlRptForm"
	modelAttribute="excptnHandlngRptDto" method="POST"
	enctype="multipart/form-data"
	style="width: 100%; background-color:#FFFFC6;">
	<!-- menu End-->
	<div id="dividerRed" class="displayOn">&nbsp;</div>
	<!-- Content Start -->
	<div id="inner_content">
		<table>
			<tr>
				<td id="heading_left" style="width: 215px; height: 40px;"><span
					class="heading" style="width: 215px">Search Critera</span></td>
			</tr>
			<tr>
				<td class="subjectsmall" align="left" width="100" height="25">Forward
					Start DateTime</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="forwardStartDt" id="startDt" /></td>
				<td rowspan="9">
					<table width="50%">
						<tr>
							<td class="subjectsmall">Report Columns&nbsp;&nbsp;<input
								id="selectAllCol1BtnId" type="button" class="button_grey"
								value="Select All" align="right" /></td>
							</td>
							<td></td>
							<td class="subjectsmall">Selected columns&nbsp;&nbsp;<input
								id="selectAllCol2BtnId" type="button" class="button_grey"
								value="Select All" align="center" /></td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td><select id="rptcols" multiple="multiple"
								name="rptColumns[]" style="width: 200px;; height: 200px">
							</select></td>
							<td>
								<table>
									<tr>
										<td><input id="addBtnId" type="button" class="button_grey"
											value=">>" align="center" /></td>
									</tr>
									<tr>
										<td><input id="removBtnId" type="button" class="button_grey"
											value="<<" align=" center" /></td>
									</tr>
								</table>
							</td>
							<td><form:select id="selRptcols"
									style="width: 200px; height: 200px" multiple="multiple"
									name="selectedColumns" path="selColumns">
									<option value="transactionNo">Transaction No</option>
									<option value="nin">NIN</option>
									<option value="reason">Exception Reason</option>
									<option value="remarks">Remarks</option>
									<option value="transferDate">Forward Date Time</option>
									<option value="resolutionDate">Resolution Date Time</option> 
								</form:select></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="subjectsmall" align="left" width="100" height="25">Forward
					End DateTime</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="forwardEndDt" id="endDt" /></td>
			</tr>
			<tr>
				<td class="subjectsmall" align="left" width="100" height="25">Resolution
					Start DateTime</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="resolutionStartDt" id="resStDt" /></td>
			</tr>
			<tr>
				<td class="subjectsmall" align="left" width="100" height="25">Resolution
					End DateTime</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="resolutionEndDt" id="resEndDt" /></td>
			</tr>
			<tr>
				<td class="subjectsmall" height="50" width="300">Exception
					Reason</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="100"
					height="25">
					<!-- <form:select id="rejResn" path="reason"><option value="0">All</option>
						<option value="RS 1">Reject Due to Wrong Printed Name</option>
						<option value="other">Others</option></form:select> --> <form:select
						id="rejResn" path="reason">
						<form:option value="" label="ALL" />
						<form:options items="${exceptionReasonList}" id="reasonExptnLst" />
					</form:select>
				</td>
				<td></td>
			</tr>
			<tr>
				<td class="subjectsmall" height="50" width="300">RIC Office</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="100"
					height="25">
					<%--  <label class="subjectsmall" height="50" for="ricOfficId">${siteCodes}</label>
					<form:hidden  id="ricOfficId" path="ricOffice" readonly="readonly"  
						value="${siteCodes}" /> --%>
					<form:select id="ricOfficId" path="ricOffice"
						width="200" style="width: 200px">
						<form:option value="ALL" label="ALL" />
						<form:options items="${siteCodes}" id="ricOfficIdLst" />
					</form:select>
				</td>
				<td></td>
			</tr>
			<tr>
				<td class="subjectsmall" height="50" width="300">Report Format
				</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="100"
					height="25"><form:select path="rptFormat" id="rptFrmt">
						<option value="html">Tabular</option>
						<option value="pdf">PDF</option>
						<!-- <option value="excel">Excel</option> -->
					</form:select></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td><input id="genBtnId" type="button" class="button_grey"
					value="Generate" align="center" />&nbsp;&nbsp;&nbsp;<input
					id="resetBtn" type="button" class="button_grey" value="Reset"
					align="center" /></td>
				<td>
				<td></td>
				</td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</table>
		<div id="showHtmlRpt">
			<table id="tranRptflexme">
			</table>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#showHtmlRpt").hide();
			$("#startDt")
					.datepicker(
							{
								showOn : "button",
								buttonImage : "<c:url value="/resources/images/calendar.gif" />",
								buttonImageOnly : true,
								changeMonth : true,
								changeYear : true,
								controlType : 'select',
								dateFormat : 'dd-M-yy'
							});
			$("#endDt")
					.datepicker(
							{
								showOn : "button",
								buttonImage : "<c:url value="/resources/images/calendar.gif" />",
								buttonImageOnly : true,
								changeMonth : true,
								changeYear : true,
								controlType : 'select',
								dateFormat : 'dd-M-yy'
							});
			$("#resStDt")
					.datepicker(
							{
								showOn : "button",
								buttonImage : "<c:url value="/resources/images/calendar.gif" />",
								buttonImageOnly : true,
								changeMonth : true,
								changeYear : true,
								controlType : 'select',
								dateFormat : 'dd-M-yy'
							});
			$("#resEndDt")
					.datepicker(
							{
								showOn : "button",
								buttonImage : "<c:url value="/resources/images/calendar.gif" />",
								buttonImageOnly : true,
								changeMonth : true,
								changeYear : true,
								controlType : 'select',
								dateFormat : 'dd-M-yy'
							});
			$("#genBtnId")
					.click(
							function() {
								if ($("#rptFrmt").val() == 'pdf') {
									$("#showHtmlRpt").hide();
									var selRptcols = document
											.getElementById("selRptcols");
									if (selRptcols != null) {
										for ( var x = 0; x < selRptcols.options.length; x++) {
											selRptcols.options[x].selected = true;
										}
									}
									var orig = $("#exceptionHandlRptForm")
											.serialize();
									var withoutEmpties = orig.replace(
											/[^&]+=\.?(?:&|$)/g, '');
									var test = "${exceptionHandlingPdfResultUrl}"
											+ '?' + withoutEmpties; 
									document.forms["exceptionHandlRptForm"].action = test;
									document.forms["exceptionHandlRptForm"].method = "POST";
									document.forms["exceptionHandlRptForm"].onSubmit = window
											.open(test);
									//document.forms["exceptionHandlRptForm"].submit();
									//return true;
									//document.forms["exceptionHandlRptForm"].onSubmit=window.showModalDialog(test,null, "dialogWidth:1280px;dialogHeight:800px;resizable:yes;addressbar:no;"); 

								} else if ($("#rptFrmt").val() == 'html') { 
									doSubmit(this.form);
								} else if ($("#rptFrmt").val() == 'excel') {
									$("#showHtmlRpt").hide();
									var selRptcols = document
											.getElementById("selRptcols");
									if (selRptcols != null) {
										for ( var x = 0; x < selRptcols.options.length; x++) {
											selRptcols.options[x].selected = true;
										}
									}
									var orig = $("#exceptionHandlRptForm")
											.serialize();
									var withoutEmpties = orig.replace(
											/[^&]+=\.?(?:&|$)/g, '');
									var test = "${exceptionHandlingExcelResultUrl}"
											+ '?' + withoutEmpties; 
									document.forms["exceptionHandlRptForm"].action = test;
									document.forms["exceptionHandlRptForm"].method = "POST";
									window.open(test, "popup", "location=no,resizable=yes,left=0,height=800px,width=1280px");
								}
							});

			$('#removBtnId').click(
					function() {
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

			$("#resetBtn").click(
					function() {
						$("#startDt").val("");
						$("#endDt").val("");
						$("#resStDt").val("");
						$("#resEndDt").val("");
						$("#transTypId").val("");
						$("#transSubTypId").val("");
						$("#transStatusId").val("");
						$("#rptFrmt").val("");
						$("#ricOfficId").val("");
						$("#rejResn").val("");
						var dataArr = [ {
							'value' : 'transactionNo',
							'text' : 'Transaction No'
						}, {
							'value' : 'nin',
							'text' : 'NIN'
						}, {
							'value' : 'reason',
							'text' : 'Exception Reason'
						}, {
							'value' : 'remarks',
							'text' : 'Remarks'
						}, {
							'value' : 'transferDate',
							'text' : 'Forward Date Time'
						}, {
							'value' : 'resolutionDate',
							'text' : 'Resolution Date Time'
						} ];
						$('#selRptcols option').remove();
						$.each(dataArr, function(i) {
							$('#selRptcols').append(
									$("<option></option>").attr("value",
											dataArr[i]['value']).text(
											dataArr[i]['text']));
						});
						$('#tranRptflexme').empty();
						$("#showHtmlRpt").hide();
						$('#rptcols').find('option').remove().end();

					});

			$("#rptcols").dblclick(function() {
				$('#rptcols option').prop('selected', 'selected');
			});
			$("#selRptcols").dblclick(function() {
				$('#selRptcols option').prop('selected', 'selected');
			});
		});

		function doSubmit(form) {
			if ($("#startDt").val() == null || $("#startDt").val().trim() ==""){
				alert("Start Date is mandatory");
				document.getElementById('startDt').focus();
				return;
			}
			
			if ($("#endDt").val() == null || $("#endDt").val().trim() ==""){
				alert("End Date is mandatory");
				document.getElementById('endDt').focus()
				return;
			}		
			var selRptcols = document.getElementById("selRptcols");
			if (selRptcols != null) {
				for ( var x = 0; x < selRptcols.options.length; x++) {
					selRptcols.options[x].selected = true;
				}
			}
			var orig = $(form).serialize();
			var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
			var test = $("#currentURL").val() + '?' + withoutEmpties; 
			var columns = [];
			var jsonObj1 = {};
			jsonObj1['display'] = 'S.No';
			jsonObj1['name'] = 'serialNo';
			jsonObj1['width'] = '50';
			jsonObj1['height'] = '20';
			jsonObj1['align'] = 'center';
			jsonObj1['sortable'] = 'false';
			columns.push(jsonObj1);
			$("#selRptcols option").each(function() {
				var jsonObj = {};
				jsonObj['display'] = $(this).text();
				jsonObj['name'] = $(this).val();
				jsonObj['width'] = '150';
				jsonObj['height'] = '20';
				jsonObj['align'] = 'center';
				jsonObj['sortable'] = 'false';
				columns.push(jsonObj);
			});
			/* $.ajax({
				type : 'POST',
				url : test,
				dataType : 'json',
				success : function(data) {
					var columns = [];
					var jsonObj1 = {};
					jsonObj1['display'] = 'S.No';
					jsonObj1['name'] = 'serialNo';
					jsonObj1['width'] = '50';
					jsonObj1['height'] = '20';
					jsonObj1['align'] = 'center';
					jsonObj1['sortable'] = 'false';
					columns.push(jsonObj1);
					$("#selRptcols option").each(function() {
						var jsonObj = {};
						jsonObj['display'] = $(this).text();
						jsonObj['name'] = $(this).val();
						jsonObj['width'] = '150';
						jsonObj['height'] = '20';
						jsonObj['align'] = 'center';
						jsonObj['sortable'] = 'false';
						columns.push(jsonObj);
					}); */
					$("#showHtmlRpt").empty();
					$("#showHtmlRpt").append(
							$("<table>").attr("id", "tranRptflexme"));
					$("#showHtmlRpt").show();
					$("#tranRptflexme").flexigrid({  
						url : test,
						dataType : 'json',
						colModel : columns, 
						sortname : "transactionId",
						sortorder : "asc",
						title : 'EXCEPTION HANDLING REPORT',
						usepager : true,
						useRp : true,
						rp : 15,  
						showTableToggleBtn : true,	 
						singleSelect : true,
						nowrap : false,
						height : 320, 
						width:1260
					});
					//$('#tranRptflexme').flexAddData(data);
				//}
			//});
		}

	 
	</script>
</form:form>
<input type="hidden" id="currentURL" name="currentURL"
	value="${exceptionHandlingResultUrl}" />
<%
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
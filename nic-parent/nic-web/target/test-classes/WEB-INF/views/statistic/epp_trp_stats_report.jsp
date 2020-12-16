<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<c:url var="url" value="/servlet/trpStatistic/view"/>
<c:url var="search" value="/servlet/trpStatistic/search"/>
<c:url var="detailUrl" value="/servlet/trpStatistic/transDetail"/>
<c:url var="exportPdfUrl" value="/servlet/trpStatistic/exportPdf"/>
<c:url var="exportCsvUrl" value="/servlet/trpStatistic/exportCsv"/>
<c:url var="exportExcelUrl" value="/servlet/trpStatistic/exportExcel"/>
<style>
	#transList a hover { color: #0e4954; text-decoration: underline; font-weight: bold; }
	#transList a:focus { color: #ff9124; font-weight: bold; }
</style>
<script type="text/javascript">
var reload = "0";
var today = new Date();
 $(function() {
	 $.datepicker.setDefaults({
		duration : '',
		showTime : false,
		constrainInput : true,
		stepMinutes : 1,
		stepHours : 1,
		altTimeField : '',
		time24h : true,
		changeMonth : true,
		changeYear : true,
		dateFormat : 'mm/dd/yy',
		minDate : today,
		maxDate: '+10y',
		inline : true
	});

	 if(isEmpty($("#fromDate").val())) {
		$("#fromDate").val(formatToDay());
	}
	
	$('#fromDate').datepicker({
			onSelect : function(selectedDate) {
				$('#fromDate').datepicker('option', 'setDate', selectedDate);
			}
		});
		
	$("#search_btn").click(function() {
		if(validateForm()) {
			document.forms["statRptForm"].action = '${search}';
			document.forms["statRptForm"].submit();
		}
		
	});

	$("#reset_btn").click(function() {
		//$("#statRptForm")[0].reset();
		$("#fromDate").val(formatToDay());
		$("#noOfDays").val("0");
		$("#collectedType").attr('checked',false);
		$("#pendingType").attr('checked',false);
	});

 });

	function GetTransDetail(params) {
		var orig = $("#statRptForm").serialize();
		var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
		var actionUrl = '${detailUrl}' + '/' + params.replace(/[/]/g,'-');
		$('#exportParam').val(params);
		$('#statDetailResult').empty();
		$("#forDetailTable").show();
		$('#statDetailResult').show();
		if (reload == "0") {
			reload = "1";
			$("#statDetailResult").flexigrid({
				url : actionUrl,
				dataType : 'json',
				colModel : [ {
					display : 'Mã giao dịch',
					name : 'transactionId',
					width : 150,
					sortable : false,
					align : 'left'
				},{
					display : 'Số hồ sơ',
					name : 'applnRefNo',
					width : 80,
					sortable : false,
					align : 'left'
				},
				{
					display : 'Ngày tiếp nhận',
					name : 'datTimeOfApplication',
					width : 140,
					sortable : false,
					align : 'left'
				},
				{
					display : 'Tên',
					name : 'firstName',
					width : 125,
					sortable : false,
					align : 'left'
				},{
					display : 'Tên đệm',
					name : 'middleName',
					width : 125,
					sortable : false,
					align : 'left'
				},{
					display : 'Họ',
					name : 'lastName',
					width : 125,
					sortable : false,
					align : 'left'
				},{
					display : 'Loại giao dịch',
					name : 'transactionType',
					width : 210,
					sortable : false,
					align : 'left'
				},{
					display : 'Trạng thái giao dịch',
					name : 'transactionStatus',
					width : 140,
					sortable : false,
					align : 'left'
				},{
					display : 'Nơi phát hành',
					name : 'issSiteCode',
					width : 165,
					sortable : false,
					align : 'left'
				}],
				sortname : 'transactionId',
				sortorder : 'desc',
				title : 'Transaction List',
				usepager : true,
				useRp : true,
				rp : 25,
				rpOptions : [ 10, 25, 30, 50, 75, 100, 200 ], //allowed per-page values
				procmsg : 'Processing, Please wait ...',
				pagestat : 'Displaying {from} to {to} of {total} records',
				showToggleBtn : false,
				showTableToggleBtn : true,
				singleSelect : true,
				nowrap : false,
				width : 'auto',
				height : 395
			});
		} else {
			$("#statDetailResult").flexOptions({
				url : actionUrl,
				newp : 1
			}).flexReload();
		}
	} 

	function formatToDay() {
		var today = new Date();
	    var dd = today.getDate();
	    var mm = today.getMonth()+1; //January is 0!
	    var yyyy = today.getFullYear();
	    if(dd<10){
	        dd='0'+dd
	    } 
	    if(mm<10){
	        mm='0'+mm
	    } 
	    return mm+'/'+dd+'/'+yyyy;
	}

	function validateForm() {
		// Check requirement data
		var result = true;
		var errMsg = "The following error(s) has been encountered";
		if($("#noOfDays").val() == '0') {
			errMsg += "<li>Phải chọn số ngày</li>";
			result = false;
		}
		
		if(!$("#collectedType").is(':checked') && !$("#pendingType").is(':checked')) {
			errMsg += "<li>Mã giao dịch hoặc số hộ chiếu phải được nhập</li>";
			result = false;
		}

		if(!result) {
			$("#msgDialogSpan").html(errMsg);
			$("#msgDialog").dialog("open");
		}

		return result;
	}
	
	function isEmpty(str) {
		if(str == null || str == 'undefined' || str.trim().length <= 0) {
			return true;
		}
		return false;
	}

	function exportPdf() {
		var expParam = $('#exportParam').val();
		var actionUrl = '${exportPdfUrl}' + '/' + expParam.replace(/[/]/g,'-');
		document.forms["statRptForm"].action = actionUrl;
		document.forms["statRptForm"].submit();
	}

	function exportCsv() {
		var expParam = $('#exportParam').val();
		var actionUrl = '${exportCsvUrl}' + '/' + expParam.replace(/[/]/g,'-');
		document.forms["statRptForm"].action = actionUrl;
		document.forms["statRptForm"].submit();
	}

	function exportExcel() {
		var expParam = $('#exportParam').val();
		var actionUrl = '${exportExcelUrl}' + '/' + expParam.replace(/[/]/g,'-');
		document.forms["statRptForm"].action = actionUrl;
		document.forms["statRptForm"].submit();
	}

	$(function() {
		$("#msgDialog").dialog({
			width : 400,
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
	});
</script>
<form:form modelAttribute="eppStatRptForm" id="statRptForm"  name="statRptForm" cssClass="inline">
 <div class="main_content">
 <div id="heading_report" align="justify" style='padding:2px'>Báo cáo và thống kê</div>
  <br /><br /><br />
               <div id="dropdown3">
                    <table id="tablepaging" class="displayTag" style="background-color: #E3F1FE; ">
                        <tr >
							<td width='10%' style="border: none; font-weight: bold; padding: 20px 0 0 20px;">Từ ngày</td>
							<td  width='35%' style="border: none; padding-top: 20px;">
							<form:input type="text" class="pickDateTimeForLocale" style="cursor: pointer;" path="fromDate" id="fromDate" value="${eppStatRptForm.fromDate}" readonly="true" />
							</td>
                            <td style="border: none; padding-top: 20px;"><form:checkbox id="collectedType" path="collectedType"  style="cursor: pointer;"/>&nbsp;&nbsp;<span  style="font-weight: bold;">Số hộ chiếu được thu/cấp</span></td>
                        </tr>
                        <tr>
	                        <td width='10%' style="border: none; font-weight: bold; padding: 10px 0 20px 20px;">No Of Days </td>
							<td class="description" width='35%'>
								<form:select id="noOfDays" path="noOfDays" style="cursor: pointer;">
									<form:option value="0" label="--- Select ---" />
									<form:option value="1" label="1" />
									<form:option value="2" label="2" />
									<form:option value="3" label="3" />
									<form:option value="4" label="4" />
									<form:option value="5" label="5" />
									<form:option value="6" label="6" />
								</form:select>
								</td>Số giao dịch</span></td>
	                        </tr>
                    </table>
                    <table style="width: 100%; text-align: right;">
				<tr>
					<td colspan="2" align="right" style="padding: 10px; text-align: right;">
						<input type="button" id="search_btn" class="button_grey" value="Tìm kiếm"/>&nbsp; 
						<input type="button" id="reset_btn" class="button_grey" value="Làm lại" />
					</td>
				</tr>
			</table>
 		</div>
 		<div id="transList" class="displayTag"
				style="background-image:url('<%=request.getContextPath()%>/images/head.png');background-repeat: repeat-x;">
				<display:table cellspacing="1" cellpadding="0" id="row"
					export="fasle" class="displayTag" name="transList"
					partialList="false" size="${totalRecords}" 
					defaultorder="ascending" pagesize="${pageSize}"
					requestURI="/servlet/trpStatistic/search">
					<display:setProperty name="export.pdf" value="true" />
					<display:setProperty name="export.excel" value="true" />
					<display:setProperty name="export.csv" value="false" />
					<display:setProperty name="export.xml" value="false" />
					<display:setProperty name="export.pdf.filename" value="Transaction_Summary.pdf"/>
					<display:setProperty name="export.excel.filename" value="Transaction_Summary.xls"/>
					<display:setProperty name="paging.banner.onepage" value="" />
					<display:setProperty name="paging.banner.one_item_found" value="" />
					<display:setProperty name="paging.banner.all_items_found" value="" />
					<display:setProperty name="paging.banner.full" value="" />
					
					<display:column title="Collected Date" sortable="true" >
						<c:out value="${row.statDate}" />
					</display:column>
					<c:if test="${eppStatRptForm.pendingType == true}">
						<display:column title="Pending AFIS" sortable="true" >
						<c:if test='${row.pendingAfis == 0 }'>
							<c:out value="${row.pendingAfis}" />
						</c:if>
						<c:if test='${row.pendingAfis != 0 }'>
							<a id="selectClass" href="javascript:GetTransDetail('PEN_${row.statDate}_WA');" >
							<c:out value="${row.pendingAfis}" /></a>
						</c:if>
						</display:column>
						<display:column title="Pending Investigation" sortable="true">
							<c:if test='${row.pendingInvestigation == 0 }'>
								<c:out value="${row.pendingInvestigation}" />
							</c:if>
							<c:if test='${row.pendingInvestigation != 0 }'>
								<a id="selectClass" href="javascript:GetTransDetail('PEN_${row.statDate}_FG');" >
								<c:out value="${row.pendingInvestigation}" /></a>
							</c:if>
						</display:column>
						<display:column title="Pending Verified" sortable="true">
							<c:if test='${row.pendingVerified == 0 }'>
								<c:out value="${row.pendingVerified}" />
							</c:if>
							<c:if test='${row.pendingVerified != 0 }'>							
								<a id="selectClass" href="javascript:GetTransDetail('PEN_${row.statDate}_VE');" >
								<c:out value="${row.pendingVerified}" /></a>
							</c:if>
						</display:column>
						<display:column title="Pending Perso" sortable="true">
							<c:if test='${row.pendingPerso == 0 }'>
								<c:out value="${row.pendingPerso}" />
							</c:if>
							<c:if test='${row.pendingPerso != 0 }'>
								<a id="selectClass" href="javascript:GetTransDetail('PEN_${row.statDate}_RP:WC');" >
								<c:out value="${row.pendingPerso}" /></a>
							</c:if>
						</display:column>
						<display:column title="Pending QC" sortable="true" >
							<c:if test='${row.pendingQc == 0 }'>
								<c:out value="${row.pendingQc}" />
							</c:if>
							<c:if test='${row.pendingQc != 0 }'>
								<a id="selectClass" href="javascript:GetTransDetail('PEN_${row.statDate}_QC');" >
								<c:out value="${row.pendingQc}" /></a>
							</c:if>
						</display:column>
						<display:column title="Pending Dispatch" sortable="true">
							<c:if test='${row.pendingDispatch == 0 }'>
								<c:out value="${row.pendingDispatch}" />
							</c:if>
							<c:if test='${row.pendingDispatch != 0 }'>
								<a id="selectClass" href="javascript:GetTransDetail('PEN_${row.statDate}_ST');" >
								<c:out value="${row.pendingDispatch}" /></a>
							</c:if>
						</display:column>
						<display:column title="Pending Receive" sortable="true">
							<c:if test='${row.pendingReceive == 0 }'>
								<c:out value="${row.pendingReceive}" />
							</c:if>
							<c:if test='${row.pendingReceive != 0 }'>
								<a id="selectClass" href="javascript:GetTransDetail('PEN_${row.statDate}_RD');" >
								<c:out value="${row.pendingReceive}" /></a>
							</c:if>
						</display:column>
						<display:column title="Pending Issue" sortable="true">
							<c:if test='${row.pendingIssue == 0 }'>
								<c:out value="${row.pendingIssue}" />
							</c:if>
							<c:if test='${row.pendingIssue != 0 }'>
								<a id="selectClass" href="javascript:GetTransDetail('PEN_${row.statDate}_OK');" >
								<c:out value="${row.pendingIssue}" /></a>
							</c:if>
						</display:column>
					</c:if>
					<c:if test="${eppStatRptForm.collectedType == true}">
						<display:column title="Passport Collected/Issued" sortable="true">
							<c:if test='${row.numPassportCollected == 0 }'>
								<c:out value="${row.numPassportCollected}" />
							</c:if>
							<c:if test='${row.numPassportCollected != 0 }'>
								<a id="selectClass" href="javascript:GetTransDetail('TOT_${row.statDate}_FA:KO:RD');" >
								<c:out value="${row.numPassportCollected}" /></a>
							</c:if>
						</display:column>
					</c:if>
					
				</display:table>
				<br />
			</div>
                <p>&nbsp;</p>
                <!--newly added-->
				<div id="forDetailTable" style="display: none">
				<input type="hidden" id="exportParam" name="exportParam" value="">
				<p style="font-weight: bold;">Định dạng: <a href="javascript:exportPdf();">PDF</a> | <a
					href="javascript:exportCsv();">CSV</a> | <a
					href="javascript:exportExcel();">EXCEL</a></p>
			<table id="statDetailResult">
						<tr>
							<td colspan=4>&nbsp;</td>
						</tr>
					</table>
				</div>
 </div>
  <div id="msgDialog" title="Alert" />
   <div align="left">
 <ol id="msgDialogSpan" >
 </ol>
</div>
 </form:form>
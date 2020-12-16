<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:url var="showExcelResult" value="/servlet/dataSyncMonitorController/generatePDF" />
<c:url var="dataSyncDetailsUrl" value="/servlet/dataSyncMonitorController/getDataSyncDetails" />
<c:url var="dataSyncDetailsForMonUrl1" value="/servlet/dataSyncMonitorController/dataSyncDetailsForMon" />
<c:url var="dataSyncDetailsForMonUrl" value="/servlet/dataSyncMonitorController/getDataSyncDetailsByMonth" />

<script type="text/javascript">

function openDetails(date){
	$("#selectedDate").val( date);
	$("#selectedMonth").val( selectedDate);
	var url = '${dataSyncDetailsUrl}';
	
	document.forms["dataSyncMonitoringForm"].action = url;  
	//document.forms["dataSyncMonitoringForm"].method = "POST";  
	document.forms["dataSyncMonitoringForm"].submit();
	
}

function viewAllDetailsByMonly(date){
	$("#selectedDate").val( date);
	$("#selectedMonth").val( selectedDate);
	var url = '${dataSyncDetailsForMonUrl}';
	
	document.forms["dataSyncMonitoringForm"].action = url;  
	//document.forms["dataSyncMonitoringForm"].method = "POST";  
	document.forms["dataSyncMonitoringForm"].submit();
	
}

function generatePDF(){
	$("#selectedMonth").val( selectedDate);
	 
	var url = '${showExcelResult}';
	
	//document.forms["dataSyncMonitoringForm"].action = url;  
	//document.forms["dataSyncMonitoringForm"].method = "POST";  
	//document.forms["dataSyncMonitoringForm"].submit();
	
	
	var orig = $("#dataSyncMonitoringForm").serialize();
	var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
	var test = url + '?' + withoutEmpties;  
	
	oIFrm = document.getElementById('reportIFrame'); 
    oIFrm.src = test;
    
}

$(function(){
});

function load(){
	var oIFrm = document.getElementById('reportIFrame'); 
		if($('#reportIFrame').contents().find('body').html() == '-1'){		
			alert('Error occurred while generating the report, please check the log for more details.');
		}else if($('#reportIFrame').contents().find('body').html() == '0'){
			alert('Error occurred while generating the report, please check the log for more details.');
		}
}
</script>


	<table style="width: 100%;">
	<tr style="text-align: right;">
		<td>
			&nbsp;
		</td>
	</tr>
	<tr>
		<td>
			<display:table cellspacing="1" cellpadding="0" id="row"
				export="false" class="displayTag" name="list">
				<display:column title="Application Date" property="applicateDate" style="width:15%;"/>
				<display:column title="Total" property="totTrans" style="width:15%;" />
				<display:column title="Synchronized" property="syncronizedTrans" style="width:15%;"/>
				<display:column title="Not Required" property="notRequired" style="width:15%;" />
				<display:column title="To be Synchronized" property="toBeSyncronized" style="width:15%;"/>
				<display:column title="Status" style="width:10%;">
					<c:choose>
						<c:when test="${row.status eq 'Y'}">
							<img src=<c:url value='/resources/images/tick.gif'/> height="16"
								width="16" />
						</c:when>
						<c:otherwise>
							<img src=<c:url value='/resources/images/cross.png'/> height="16"
								width="16" />
						</c:otherwise>
					</c:choose>
				</display:column>
				<display:column title="Actions" style="width:15%;">
					<c:choose>
						<c:when test="${row.status eq 'Y'}">
					&nbsp;
				</c:when>
						<c:otherwise>
							<input type="button"
								onclick="javaScript:openDetails('${row.applicateDate}');"
								class="button_grey" value="View Details" />
						</c:otherwise>
					</c:choose>
				</display:column>
			</display:table>
		</td>
	</tr>
	<tr>
		<td>
			&nbsp;
		</td>
	</tr>
	<tr>
		<td style="text-align: right;">
						<input type="button" id="view_detail_mon_btn" class="button_grey" value="View All Details" onclick="javaScript:viewAllDetailsByMonly();"/>&nbsp;
			<input style="text-align: right;" type="button" id="refresh" onclick="javaScript:refreshDetails();" class="button_grey" value="Refresh" />
			<c:if test="${not empty list }">
				<input style="text-align: right;" type="button" id="generatePDF" onclick="javaScript:generatePDF();" class="button_grey" value="Generate PDF" />
			</c:if>
		</td>
	</tr>
</table>


<iframe id="reportIFrame" src="" onload="load()" style="display:none;"> 
</iframe>
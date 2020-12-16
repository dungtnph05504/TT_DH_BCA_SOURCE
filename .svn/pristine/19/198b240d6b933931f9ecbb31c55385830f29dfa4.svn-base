<%
	try {
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url var="ricWaiverResultUrl"
	value="/servlet/pymtReport/showWavrTabluarResult" />
<c:url var="ricWaiverPdfResultUrl"
value="/servlet/pymtReport/showWavrPdfResult" />
<c:url var="ricWaiverXlsResultUrl"
	value="/servlet/pymtReport/showWaverExcelResult" />
<c:url var="ricTxnSubTypeUrl"
	value="/servlet/pymtReport/txnsubtype" />

<style>
#inner_content {
	border: thin;
	float: left;
	font-family: Verdana, Arial, Helvetica, sans-serif;
	background-color: #F0F0F0;
	width: 1500px;
}

#inner_main_left {
	float: left;
	width: 950px;
	height: 315px;
	overflow: hidden;
}

#showHtmlRpt {
	float: left;
	width: 1500px;
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
	filter: progid:DXImageTransform.Microsoft.gradient(   startColorstr='#c5deea',
		endColorstr='#066dab', GradientType=0); /* IE6-9 */
}

#heading {
	height: 25px;
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #333333;
}
</style> 
<form:form id="ricWaiverRptForm"
	modelAttribute="ricWaiverRptDto" method="POST" enctype="multipart/form-data"
	style="width: 100%; background-color:#FFFFC6;">
	<!-- menu End--> 
	<!-- Content Start -->
	<div id="inner_content">
		<table>
			<tr>
				<td id="heading_left" style="width: 215px; height: 40px;"><span
					class="heading" style="width: 215px">Search Critera</span></td>
			</tr>
			<tr>
				<td class="subjectsmall" align="left" width="100" height="25">Waiver
					Start Date</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="startDate" id="crdRejStDt" value="${startDate}"/></td>
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
<!-- 								<option value="waiverNo">Waiver No</option>
								<option value="txnNo">Transaction No</option>
								<option value="nin">NIN</option>
								<option value="firstName">First Name</option>
								<option value="surName">SurName</option>
								<option value="surNameBirth">Surname Birth</option>
								<option value="txnType">Transaction Type</option>
								<option value="txnSubType">Transaction Subtype</option>
								<option value="waiverAmt">Waiver Amount</option>
								<option value="waiverDate">Waiver Date</option>
								<option value="waiverReason">Waiver Reason</option>
								<option value="waiverOfficer">Waiver Officer</option>	
								<option value="servedBy">Served By</option> -->
								</select></td>
							<td>
								<table>
									<tr>
										<td><input id="addBtnId" type="button" class="button_grey"
											value=">>" align="center" /></td>
									</tr>
									<tr>
										<td><input id="removBtnId" type="button" class="button_grey"
											value="<<" align="center" /></td>
									</tr>
								</table>
							</td>
							<td><form:select id="selRptcols"
								style="width: 200px; height: 200px" multiple="multiple"
								name="selectedColumns" path="selColumns">
								<option value="waiverNo">Waiver No</option>
								<option value="txnNo">Transaction No</option>
								<option value="nin">NIN</option>
								<option value="firstName">First Name</option>
								<option value="surName">SurName</option>
								<option value="surNameBirth">Surname Birth</option>
								<option value="txnType">Transaction Type</option>
								<option value="txnSubType">Transaction Subtype</option>
								<option value="citizenType">Senior Citizen</option>	
								<option value="waiverAmt">Waiver Amount</option>
								<option value="waiverDate">Waiver Date</option>
								<option value="waiverReason">Waiver Reason</option>
								<option value="waiverOfficer">Waiver Officer</option>	
								<option value="servedBy">Served By</option>								
							</form:select></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="subjectsmall" align="left" width="100" height="25">Waiver
					End Date</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="endDate" id="crdRejEndDt" value="${endDate}"/></td>
			</tr>


			<tr>
				<td class="subjectsmall" height="50" width="300">Transaction Type</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="100"
					height="25">
				         <form:select
							id="txnType" path="txnType">
							<form:option value="" label="ALL" />
							<form:options items="${txnTypeList}"
								id="txnTypeLst" />
						</form:select></td>
 				<td></td>
 			</tr>
 			
 			
			<tr>
				<td class="subjectsmall" height="50" width="300">Transaction Sub Type</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="100"
					height="25">
								
				         <form:select
							id="txnSubType" path="txnSubType">
							<form:option value="" label="ALL" />
							<form:options items="${txnSubTypeList}"
								id="txnSubTypeLst" />
						</form:select></td>
 				<td></td>
 			</tr> 
 			
 			<tr>
				<td class="subjectsmall" height="50" width="300">Senior Citizen</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="100"
					height="25"><form:select id="citizenType" path="citizenType">
						<form:option value="" label="ALL" />
						<form:option value="Y" label="Yes" />
						<form:option value="N" label="No" />
					</form:select></td>
				<td></td>
			</tr>
 			
 			
			<tr>
				<td class="subjectsmall" height="50" width="300">Waived By</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="100"
					height="25">
						<form:input type="text" name="servedBy" size="30" path="servedBy" id="servdby"/>
 				<td></td>
 			</tr>  		 			

			


 			<tr> 
				<td class="subjectsmall" height="50" width="300">RIC Office</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="100"
					height="25">
					<form:select id="ricOfficId" path="ricOffice"
						width="200" style="width: 200px">
						<form:option value="ALL" label="ALL" />
						<form:options items="${siteCodes}" id="ricOfficIdLst" />
					</form:select>
					<%-- <label class="subjectsmall" height="50"
					for="ricOfficId">${siteCodes}</label> <form:hidden id="ricOfficId"
						path="ricOffice" readonly="readonly" value="${siteCodes}" /> --%></td>
 				<td></td>
 			</tr>





			<tr>
				<td class="subjectsmall" height="50" width="300">Report Format
				</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="100"
					height="25"><form:select path="rptFormat" id="rptFrmt"><option value="html">Tabular</option>
						<option value="pdf">PDF</option>
						<option value="xls">Excel</option>
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
	$( "#crdRejStDt" ).datepicker({
	showOn: "button",
	buttonImage : "<c:url value="/resources/images/calendar.gif" />",
	buttonImageOnly: true,
	changeMonth: true,
	changeYear: true, 
	controlType: 'select',
	dateFormat : 'dd-M-yy'
	});
	$( "#crdRejEndDt" ).datepicker({
	showOn: "button",
	buttonImage : "<c:url value="/resources/images/calendar.gif" />",
	buttonImageOnly: true,
	changeMonth: true,
	changeYear: true, 
	controlType: 'select',
	dateFormat : 'dd-M-yy'
	});   
	$("#genBtnId").click(function(){ 
		 if($("#rptFrmt").val() == 'pdf'){
			$("#showHtmlRpt").hide(); 
			var selRptcols =document.getElementById("selRptcols");        
            if(selRptcols  !=null){      
               for(var x=0;x<selRptcols.options.length;x++){        
            	   selRptcols.options[x].selected=true;
               }
            }
			var orig = $("#ricWaiverRptForm").serialize();
			var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
			var test = "${ricWaiverPdfResultUrl}" + '?' + withoutEmpties;
			document.forms["ricWaiverRptForm"].action = test;  
			document.forms["ricWaiverRptForm"].method = "POST";   
			//document.forms["ricWaiverRptForm"].onSubmit=window.showModalDialog(test,null, "dialogWidth:1280px;dialogHeight:800px;resizable:yes;addressbar:no;");
			
			/* document.forms["ricWaiverRptForm"].onSubmit = window.open(test, "popup", "location=no"); */
			window.open(test, "popup", "location=no,resizable=yes,left=0,top=0,height=700px,width=1280px");
			 
		 }else if($("#rptFrmt").val() == 'html'){
			 doSubmit(this.form); 
		 }else if($("#rptFrmt").val() == 'xls'){
			 $("#showHtmlRpt").hide(); 
				var selRptcols =document.getElementById("selRptcols");        
	            if(selRptcols  !=null){      
	               for(var x=0;x<selRptcols.options.length;x++){        
	            	   selRptcols.options[x].selected=true;
	               }
	            }
				var orig = $("#ricWaiverRptForm").serialize();
				var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
				var test = "${ricWaiverXlsResultUrl}" + '?' + withoutEmpties; 
				document.forms["ricWaiverRptForm"].action = test;  
				document.forms["ricWaiverRptForm"].method = "POST";   
				window.open(test, "popup", "location=no,resizable=yes,left=0,top=0,height=800px,width=1280px");
		} 	 
	});
	
	$('#removBtnId').click(function(){
			$('#selRptcols :selected').each( function() {
				 $('#rptcols').append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>");
				$(this).remove(); 
			});
	 });
	 $('#addBtnId').click(function(){
        $('#rptcols option:selected').each( function() {
                $('#selRptcols').append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>");
            $(this).remove();
        }); 
    });
	
	$('#selectAllCol1BtnId').click(function(){
        $('#rptcols option').prop('selected', 'selected'); 
    });
	
	$('#selectAllCol2BtnId').click(function(){
        $('#selRptcols option').prop('selected', 'selected'); 
    });
	
	 $("#resetBtn").click(function(){ 
		 	$("#crdRejStDt").val(""); 
				$("#crdRejEndDt").val(""); 
				$("#resStDt").val("");  
				$("#rptFrmt").val("");  
  				$("#servdby").val("");  
				 var dataArr = [{'value':'waiverNo','text':'Waiver No'},
            {'value':'txnNo','text':'Transaction No'},  
			   {'value':'nin','text':'NIN'},
			   {'value':'firstName','text':'First Name'},
			   {'value':'surName','text':'SurName'},
			   {'value':'surNameBirth','text':'Surname Birth'},
			   {'value':'txnType','text':'Transaction Type'},
			   {'value':'txnSubType','text':'Transaction Subtype'},
			   {'value':'citizenType','text':'Senior Citizen'}, 
			   {'value':'waiverReason','text':'Waiver Reason'},  
			   {'value':'waiverAmt','text':'Waiver Amount'},
			   {'value':'waiverDate','text':'Waiver Date'},
			   {'value':'waiverOfficer','text':'Waiver Officer'},
			   {'value':'servedBy','text':'Served By'}
			   ];  
				 $('#selRptcols option').remove(); 
					$.each(dataArr, function(i){
						$('#selRptcols').append($("<option></option>")
										.attr("value",dataArr[i]['value'])
										.text(dataArr[i]['text']));
					}); 
					$('#tranRptflexme').empty(); 
					$("#showHtmlRpt").hide();
					$('#rptcols').find('option').remove().end();  
				 
		});
	 
	$("#rptcols").dblclick(function(){
			$('#rptcols option').prop('selected', 'selected'); 
		});
		$("#selRptcols").dblclick(function(){
			$('#selRptcols option').prop('selected', 'selected'); 
		});
});

function doSubmit(form) {
	var selRptcols = document.getElementById("selRptcols");
	if (selRptcols != null && selRptcols.options.length != 0) {
		for ( var x = 0; x < selRptcols.options.length; x++) {
			selRptcols.options[x].selected = true;
		}
		var orig = $(form).serialize();
		var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
		var test = $("#currentURL").val() + '?' + withoutEmpties;
		$
				.ajax({
					type : 'POST',
					url : test,
					dataType : 'json',
					success : function(data) {
						$("#showHtmlRpt").empty();
						$("#showHtmlRpt").append(
								$("<table>").attr("id", "tranRptflexme"));
						$("#showHtmlRpt").show();
						if (data.total < 1) {
							$("#tranRptflexme")
									.html(
											"<div style='align:center;color:blue;font-weight:bold; font-size: 15pt;width: 1260px; display:block;'>No Records Found</div>");
						} else {
							var columns = [];
							var jsonObj1 = {};
							jsonObj1['display'] = 'S.No';
							jsonObj1['name'] = 'serialNo';
							jsonObj1['height'] = '20';
							jsonObj1['width'] = '50';
							jsonObj1['align'] = 'center';
							jsonObj1['sortable'] = 'false';
							columns.push(jsonObj1);
							$("#selRptcols option").each(function() {
								var jsonObj = {};
								jsonObj['display'] = $(this).text();
								jsonObj['name'] = $(this).val();
								jsonObj['height'] = '20';
								jsonObj['width'] = '150';
								jsonObj['align'] = 'center';
								jsonObj['sortable'] = 'false';
								columns.push(jsonObj);
							});
							$("#tranRptflexme").flexigrid({
								url : test,
								dataType : 'json',
								colModel : columns,
								sortname : "firstName",
								sortorder : "asc",
								title : 'WAVIER REPORT',
								usepager : true,
								useRp : true,
								rp : 10,
								showTableToggleBtn : false,
								height : 320,
								width : 1260,
								singleSelect : true,
								nowrap : false
							});
						}
					}
				});
	}else {
		$("#showHtmlRpt").hide();
		alert("Please Select the Columns to generate Report");
	}
}
$("#txnType").change(function(){			 
	 var subtype= $("#txnType").val();
	    
       $.ajax({		        	
      	 url: "${ricTxnSubTypeUrl}",
      	 data: ({subtype : subtype}),
      	 contentType: "application/json; charset=utf-8",
           dataType: "json",
      	 success: function(result){
      		  $('#txnSubType').empty();
      		  $('#txnSubType').append($("<option></option>").attr("value", '').text('ALL'));    		 	 	
      	 	 
      	 	$.each(result, function(key, value){
      	 		
      	 		$('#txnSubType').append($("<option></option>").attr("value", key).text(value));	
      	 		
      	 	});
      	 }
      });
       
    

});
		 
</script>
</form:form> 
<input type="hidden" id="currentURL" name="currentURL"
	value="${ricWaiverResultUrl}" />
<%
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
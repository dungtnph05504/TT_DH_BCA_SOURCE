
<%
	try {
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url var="ricBatchCardInfoResultUrl"
	value="/servlet/txnReport/showBatchCardInfoTabluarResult" />
<c:url var="ricBatchCardInfoPdfResultUrl"
value="/servlet/txnReport/showBatchCardInfoPdfResult" />
<c:url var="ricBatchCardInfoXlsResultUrl"
value="/servlet/txnReport/showBatchCardInfoXlsfResult" />
<c:url var="ricTxnSubTypeUrl"
	value="/servlet/txnReport/txnsubtype" />
<style>
#inner_content {
	border: thin;
	float: left;
	font-family: Verdana, Arial, Helvetica, sans-serif;
	background-color: #EEE;
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
	width: 1260px; 
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
.flexigrid div.nBtn {
	height: 28px;
	width: 20px;
}
</style>  
<form:form id="ricBatchCardInfoRptForm"
	modelAttribute="ricBatchCardInfoRptDto" method="POST" enctype="multipart/form-data"
	style="width: 1280px; background-color:#EEE"> 
	<div id="inner_content">
		<table>
			<tr>
				<td id="heading_left" style="width: 215px; height: 40px;"><span
					class="heading" style="width: 215px">Search Critera</span></td>
			</tr>
			<tr>
				<td class="subjectsmall" align="left" width="100" height="25">Transaction Start Date</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="txnStartDate" id="txnStartDate" value="${startDate}" /></td>
						
				<td class="subjectsmall" align="left" width="100" height="25">Transaction End Date</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="txnEndDate" id="txnEndDate" value="${endDate}" /></td> 
				<!-- 
				<td class="subjectsmall" align="left" width="100" height="25">Package
					Start Date</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="cardPkgStartDate" id="dispStDt" /></td>
				
		
				<td class="subjectsmall" align="left" width="100" height="25">Package End Date</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="cardPkgEndDate" id="crdDispatchEndDt" /></td> 
				 -->
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
											value="<<" align="center" /></td>
									</tr>
								</table>
							</td>
							<td><form:select id="selRptcols"
								style="width: 200px; height: 200px" multiple="multiple"
								name="selectedColumns" path="selColumns">
								<option value="pkgSeqNo">Package Sequence No</option>
								<option value="batchId">Package Id</option>
								<option value="nin">NIN</option>
								<option value="txnNo">Transaction No</option>
								<option value="firstName">First Name</option>
								<option value="surName">SurName</option>
								<option value="gender">Gender</option>
								<option value="ccNo">CCN</option>
								<option value="cardIssueDate">Card Issue Date</option>
								<option value="cardStatus">Card Status</option>
								<option value="cardExpiryDate">Card Expiry Date</option>
								<option value="applicationDate">Application Date</option>
								<option value="packageDate">Package Date</option>								
							</form:select></td>
						</tr>
					</table>
				</td>
			</tr>
			<!--  
			<tr>
				<td class="subjectsmall" align="left" width="100" height="25">Batch Start Date</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="startDate" id="batchStDt" /></td>
						
				<td class="subjectsmall" align="left" width="100" height="25">Batch End Date</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="cardBatchEndDate" id="crdBatchEndDt" /></td>
						
						
			</tr>
			
-->
			<tr>
				<td class="subjectsmall" height="50" width="300">Transaction Type</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="100"
					height="25">
				         <form:select
							id="txnType" path="txnType" width="175" style="width: 175px">
							<form:option value="" label="ALL" />
							<form:options items="${txnTypeList}"
								id="txnTypeLst" />
						</form:select></td>
 				
				<td class="subjectsmall" height="50" width="300">Transaction Sub Type</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="100"
					height="25">
								
				         <form:select
							id="txnSubType" path="txnSubType" width="175" style="width: 175px">
							<form:option value="" label="ALL" />
							<form:options items="${txnSubTypeList}"
								id="txnSubTypeLst" />
						</form:select></td>
 				
 				
 				
 			</tr>
 			<tr>
 				<td class="subjectsmall" align="left" width="100" height="25">Package
					Start Date</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="cardPkgStartDate" id="dispStDt" /></td> 
				<td class="subjectsmall" align="left" width="100" height="25">Package End Date</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="cardPkgEndDate" id="crdDispatchEndDt" /></td>
 				<!-- 
				<td class="subjectsmall" align="left" width="100" height="25">Transaction Start Date</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="txnStartDate" id="txnStartDate" value="${startDate}" /></td>
						
				<td class="subjectsmall" align="left" width="100" height="25">Transaction End Date</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="txnEndDate" id="txnEndDate" value="${endDate}" /></td> -->
			</tr> 
			<tr>
				<td class="subjectsmall" align="left" width="100" height="25">Card Issue Start Date</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="cardIssueDate" id="crdIssueStartDt" /></td>
						
				<td class="subjectsmall" align="left" width="100" height="25">Card Issue End Date</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="cardIssueEndDate" id="crdIssueEndDt" /></td> 
			</tr> 
			
			<tr>
				<td class="subjectsmall" align="left" width="100" height="25">Card Expiry Start Date</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="cardExpiryDate" id="crdExpiryStartDt" /></td>
						
						
				<td class="subjectsmall" align="left" width="100" height="25">Card Expiry End Date</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="cardExpiryEndDate" id="crdExpiryEndDt" /></td>
						
			</tr> 						
 			
 			
			<tr>
				<td class="subjectsmall" height="50" width="300">Card Status</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="100"
					height="25">
								
				         <form:select
							id="cardStatus" path="cardStatus">
							<form:option value="" label="ALL" /> 
						<form:options items="${cardStatusVal}" /></form:select>
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
					height="25"><form:select path="rptFormat" id="rptFrmt"><option value="html">Tabular</option>
						<option value="pdf">PDF</option>
						<option value="xls">Excel</option>
						</form:select></td>
				<td></td>
			</tr>
			
			<tr>						 
			 
			 

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
	$( "#dispStDt" ).datepicker({
	showOn: "button",
	buttonImage : "<c:url value="/resources/images/calendar.gif" />",
	buttonImageOnly: true,
	changeMonth: true,
	changeYear: true, 
	controlType: 'select',
	dateFormat : 'dd-M-yy'
	});
	$( "#batchStDt" ).datepicker({
	showOn: "button",
	buttonImage : "<c:url value="/resources/images/calendar.gif" />",
	buttonImageOnly: true,
	changeMonth: true,
	changeYear: true, 
	controlType: 'select',
	dateFormat : 'dd-M-yy'
	});
	$( "#crdIssueStartDt" ).datepicker({
		showOn: "button",
		buttonImage : "<c:url value="/resources/images/calendar.gif" />",
		buttonImageOnly: true,
		changeMonth: true,
		changeYear: true, 
		controlType: 'select',
		dateFormat : 'dd-M-yy'
		});
	$( "#crdExpiryStartDt" ).datepicker({
		showOn: "button",
		buttonImage : "<c:url value="/resources/images/calendar.gif" />",
		buttonImageOnly: true,
		changeMonth: true,
		changeYear: true, 
		controlType: 'select',
		dateFormat : 'dd-M-yy'
		});
	$( "#crdDispatchEndDt" ).datepicker({
		showOn: "button",
		buttonImage : "<c:url value="/resources/images/calendar.gif" />",
		buttonImageOnly: true,
		changeMonth: true,
		changeYear: true, 
		controlType: 'select',
		dateFormat : 'dd-M-yy'
		});
	$( "#crdBatchEndDt" ).datepicker({
		showOn: "button",
		buttonImage : "<c:url value="/resources/images/calendar.gif" />",
		buttonImageOnly: true,
		changeMonth: true,
		changeYear: true, 
		controlType: 'select',
		dateFormat : 'dd-M-yy'
		});
	$( "#crdIssueEndDt" ).datepicker({
		showOn: "button",
		buttonImage : "<c:url value="/resources/images/calendar.gif" />",
		buttonImageOnly: true,
		changeMonth: true,
		changeYear: true, 
		controlType: 'select',
		dateFormat : 'dd-M-yy'
		});
	$( "#crdExpiryEndDt" ).datepicker({
		showOn: "button",
		buttonImage : "<c:url value="/resources/images/calendar.gif" />",
		buttonImageOnly: true,
		changeMonth: true,
		changeYear: true, 
		controlType: 'select',
		dateFormat : 'dd-M-yy'
		});	
	
	$( "#txnStartDate" ).datepicker({
		showOn: "button",
		buttonImage : "<c:url value="/resources/images/calendar.gif" />",
		buttonImageOnly: true,
		changeMonth: true,
		changeYear: true, 
		controlType: 'select',
		dateFormat : 'dd-M-yy'
		});
	$( "#txnEndDate" ).datepicker({
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
			var orig = $("#ricBatchCardInfoRptForm").serialize();
			var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
			var test = "${ricBatchCardInfoPdfResultUrl}" + '?' + withoutEmpties;
			document.forms["ricBatchCardInfoRptForm"].action = test;  
			document.forms["ricBatchCardInfoRptForm"].method = "POST";   
			//document.forms["ricBatchCardInfoRptForm"].onSubmit=window.showModalDialog(test,null, "dialogWidth:1280px;dialogHeight:800px;resizable:yes;addressbar:no;");
			
			/* document.forms["ricBatchCardInfoRptForm"].onSubmit = window.open(test, "popup", "location=no"); */
			window.open(test, "popup", "location=no,resizable=yes,left=0,height=800px,width=1280px");
			 
		 }else if($("#rptFrmt").val() == 'xls'){
				$("#showHtmlRpt").hide(); 
				var selRptcols =document.getElementById("selRptcols");        
	            if(selRptcols  !=null){      
	               for(var x=0;x<selRptcols.options.length;x++){        
	            	   selRptcols.options[x].selected=true;
	               }
	            }
				var orig = $("#ricBatchCardInfoRptForm").serialize();
				var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
				var test = "${ricBatchCardInfoXlsResultUrl}" + '?' + withoutEmpties;
				document.forms["ricBatchCardInfoRptForm"].action = test;  
				document.forms["ricBatchCardInfoRptForm"].method = "POST";   
				//document.forms["ricBatchCardInfoRptForm"].onSubmit=window.showModalDialog(test,null, "dialogWidth:1280px;dialogHeight:800px;resizable:yes;addressbar:no;");
				
				/* document.forms["ricBatchCardInfoRptForm"].onSubmit = window.open(test, "popup", "location=no"); */
				window.open(test, "popup", "location=no,resizable=yes,left=0,height=800px,width=1280px");
				 
			 }else if($("#rptFrmt").val() == 'html'){
			 doSubmit(this.form); 
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
		 	$("#dispStDt").val(""); 
				$("#batchStDt").val(""); 
				$("#resStDt").val(""); 
/* 				$("#resEndDt").val("");
				$("#transTypId").val("");
				$("#transSubTypId").val("");
				$("#transStatusId").val(""); */ 
				$("#rptFrmt").val("");  
/* 				$("#ricOfficId").val(""); 
				$("#rejResn").val("");  */
				/*  var dataArr = [{'value':'transactionNo','text':'Transaction No'},
               {'value':'nin','text':'NIN'},  
			   {'value':'reason','text':'Exception Reason'},
			   {'value':'remarks','text':'Remarks'},
			   {'value':'transferDate','text':'Forward Date Time'},
			   {'value':'resolutionDate','text':'Resolution Date Time'}];   */
			   var dataArr = [{'value':'pkgSeqNo','text':'Package Sequence No'},
			   			      {'value':'batchId','text':'Package Id'},
			                  {'value':'nin','text':'NIN'},  
			   			   {'value':'txnNo','text':'Transaction No'},
			   			   {'value':'firstName','text':'First Name'},
			   			   {'value':'surName','text':'SurName'},
			   			   {'value':'gender','text':'Gender'},
			   			{'value':'ccNo','text':'CCN'},
			   			{'value':'cardIssueDate','text':'Card Issue Date'},
			   			{'value':'cardStatus','text':'Card Status'},
			   			{'value':'cardExpiryDate','text':'Card Expiry Date'},
			   			{'value':'applicationDate','text':'Application Date'},
			   			{'value':'packageDate','text':'Package Date'}
			   			   ];	 
				$('#rptcols option').remove(); 
				$.each(dataArr, function(i){
					$('#rptcols').append($("<option></option>")
									.attr("value",dataArr[i]['value'])
									.text(dataArr[i]['text']));
				}); 
				$('#tranRptflexme').empty(); 
				$("#showHtmlRpt").hide();
				$('#selRptcols').find('option').remove().end(); 
				 
		});
	 
	$("#rptcols").dblclick(function(){
			$('#rptcols option').prop('selected', 'selected'); 
		});
		$("#selRptcols").dblclick(function(){
			$('#selRptcols option').prop('selected', 'selected'); 
		});
});

function doSubmit(form) { 
	//alert($("#endDate").val());
	
	var selRptcols =document.getElementById("selRptcols");        
    if(selRptcols  !=null){      
       for(var x=0;x<selRptcols.options.length;x++){        
    	   selRptcols.options[x].selected=true;
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
			jsonObj['width'] = '200';
			jsonObj['height'] = '20';
			jsonObj['align'] = 'center';
			jsonObj['sortable'] = 'false'; 
			columns.push(jsonObj); 
	});	
			
			 $("#showHtmlRpt").empty();
				$("#showHtmlRpt").append(
						$("<table>")
								.attr("id", "tranRptflexme"));
				$("#showHtmlRpt").show(); 
				$("#tranRptflexme").flexigrid({ 
					url : test,
					dataType : 'json',
					colModel : columns, 
					sortname : "batchId",
					sortorder : "asc",
					title : 'BATCH CARD INFO DOWNLOAD REPORT',
					usepager : true,
					useRp : true,
					rp : 10,
					showTableToggleBtn : false,			
					height : 320, 
					width:1260,
					singleSelect : true,
					nowrap : false  
				});	 
			   // $('#tranRptflexme').flexAddData(data); 
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
	value="${ricBatchCardInfoResultUrl}" />
<%
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
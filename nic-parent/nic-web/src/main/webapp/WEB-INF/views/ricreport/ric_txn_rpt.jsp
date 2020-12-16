<%
	try {
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url var="ricTxnResultUrl"
	value="/servlet/txnReport/showTabluarResult" />
<c:url var="ricTxnPdfResultUrl"
	value="/servlet/txnReport/showPdfResult" />
<c:url var="ricTxnExcelResultUrl"
	value="/servlet/txnReport/showExcelResult" />
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
	filter: progid:DXImageTransform.Microsoft.gradient(    startColorstr='#c5deea',
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
<form:form id="ricTxnRptForm" modelAttribute="ricTxnRptDto"
	method="POST" enctype="multipart/form-data"
	style="width: 1280px; background-color:#EEE">
	<!-- Content Start -->
	<div id="inner_content">
		<table>
			<tr>
				<td id="heading_left" style="width: 175px; height: 40px;"><span
					class="heading" style="width: 215px">Search Critera</span></td>
			</tr>
			<tr>
				<td class="subjectsmall" align="left" width="100" height="25">Transaction
					Start Date</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="startDate" id="startDt" value="${startDate}" /></td>
				<td rowspan="9">
					<table width="50%">
						<tr>
							<td class="subjectsmall">Report Columns&nbsp;&nbsp;<input
								id="selectAllCol1BtnId" type="button" class="button_grey"
								value="Select All" align="right" /></td>
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
									<option value="nin">NIN</option>
									<option value="transactionNo">Transaction No</option>
									<option value="firstName">First Name</option>
									<option value="surName">SurName</option>
									<option value="surNameBirth">Surname Birth</option>
									<option value="gender">Gender</option>
									<option value="txnType">Transaction Type</option>
									<option value="txnSubType">Transaction Subtype</option>
									<option value="txnStatus">Transaction Status</option>
									<option value="applicationDate">Transaction Date</option>
									<option value="lastModifiedDate">Last Modified Date</option>
									<option value="estColStartDt">Estimated Collection Date</option>
								</form:select></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="subjectsmall" align="left" width="100" height="25">Transaction
					End Date</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="endDate" id="endDt" value="${endDate}" /></td>
			</tr>


			<tr>
				<td class="subjectsmall" height="50" width="300">Transaction
					Type</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="100"
					height="25"><form:select id="txnType" path="txnType"
						width="200" style="width: 200px">
						<form:option value="" label="ALL" />
						<form:options items="${txnTypeList}" id="txnTypeLst" />
					</form:select></td>
				<td></td>
			</tr>


			<tr>
				<td class="subjectsmall" height="50" width="300">Transaction
					Sub Type</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="100"
					height="25"><form:select id="txnSubType" path="txnSubType"
						width="200" style="width: 200px">
						<form:option value="" label="ALL" />
						<form:options items="${txnSubTypeList}" id="txnSubTypeLst" />
					</form:select></td>
				<td></td>
			</tr>


			<tr>
				<td class="subjectsmall" height="50" width="300">Transaction
					Status</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="100"
					height="25"><form:select id="txnStatus" path="txnStatus"
						width="200" style="width: 200px">
						<form:option value="" label="ALL" />
						<form:options items="${txnStatus}" id="txnStatusLst" />
					</form:select></td>
				<td></td>
			</tr>

			<tr>
				<td class="subjectsmall" height="50" width="300">Gender</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="100"
					height="25"><form:select id="gender" path="gender" width="200"
						style="width: 200px">
						<form:option value="" label="ALL" />
						<form:options items="${gender}" id="genderLst" />
					</form:select></td>
				<td></td>
			</tr>
			<%-- <tr>
				<td class="subjectsmall" align="left" width="100" height="25">Estimated
					Collection Start Date</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="estColStartDt" id="estColStDate"  /></td>
			</tr>
			<tr>

				<td class="subjectsmall" align="left" width="100" height="25">Estimated
					Collection End Date</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="250"
					height="25"><form:input type="text" readonly="readonly"
						path="estColEndDt" id="estColEndDate"
						  /></td>

			</tr>  --%>
			<tr>
				<td class="subjectsmall" height="50" width="300">RIC Office</td>
				<td align="left" width="2" height="25">:</td>
				<td class='description_slimplain' align="left" width="100"
					height="25"><form:select id="ricOfficId" path="ricOffice"
						width="200" style="width: 200px">
						<form:option value="ALL" label="ALL" />
						<form:options items="${siteCodes}" id="ricOfficIdLst" />
					</form:select></td>
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
				<td><input id="genBtnId" type="button"
					class="button_grey" value="Generate" align="center" />&nbsp;&nbsp;&nbsp;<input
					id="resetBtn" type="button" class="button_grey" value="Reset"
					align="center" /></td>
				<td>
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
</form:form>
	<script type="text/javascript"> 
$(function() {  
	$("#showHtmlRpt").hide(); 
	$( "#startDt" ).datepicker({
	showOn: "button",
	buttonImage : "<c:url value="/resources/images/calendar.gif" />",
	buttonImageOnly: true,
	changeMonth: true,
	changeYear: true, 
	controlType: 'select',
	dateFormat : 'dd-M-yy'
	});
	$( "#endDt" ).datepicker({
	showOn: "button",
	buttonImage : "<c:url value="/resources/images/calendar.gif" />",
	buttonImageOnly: true,
	changeMonth: true,
	changeYear: true, 
	controlType: 'select',
	dateFormat : 'dd-M-yy'
	});
	$("#estColStDate" ).datepicker({
		showOn: "button",
		buttonImage : "<c:url value="/resources/images/calendar.gif" />",
		buttonImageOnly: true,
		changeMonth: true,
		changeYear: true, 
		controlType: 'select',
		dateFormat : 'dd-M-yy'
		});
		$( "#estColEndDate" ).datepicker({
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
			var orig = $("#ricTxnRptForm").serialize();
			var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
			var test = "${ricTxnPdfResultUrl}" + '?' + withoutEmpties;
			document.forms["ricTxnRptForm"].action = test;  
			document.forms["ricTxnRptForm"].method = "POST";   
			//document.forms["ricTxnRptForm"].onSubmit=window.showModalDialog(test,null, "dialogWidth:1280px;dialogHeight:800px;resizable:yes;addressbar:no;");
			
			window.open(test, "popup", "location=no,resizable=yes,left=0,height=800px,width=1280px");

			 
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
				var orig = $("#ricTxnRptForm").serialize();
				var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
				var test = "${ricTxnExcelResultUrl}" + '?' + withoutEmpties; 
				document.forms["ricTxnRptForm"].action = test;  
				document.forms["ricTxnRptForm"].method = "POST";   
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
				$("#startDt").datepicker({dateFormat:"dd-M-yy"}).datepicker("setDate",new Date());
				$("#endDt").datepicker({dateFormat:"dd-M-yy"}).datepicker("setDate",new Date());
		 		$("#resStDt").val(""); 
 				$("#txnTypeLst").val("");
				$("#txnSubTypeLst").val("");
				$("#txnStatusLst").val("");
				$("#genderLst").val(""); 
				$("#rptFrmt").val("");  
 				$("#txnServed").val(""); 
				$("#ricOfficIdLst").val("");

				var dataArr = [{'value':'transactionNo','text':'Transaction No'},
               {'value':'nin','text':'NIN'},
               {'value':'firstName','text':'First Name'},
               {'value':'surName','text':'SurName'},
               {'value':'surNameBirth','text':'SurName Birth'},
               {'value':'gender','text':'Gender'},
               {'value':'txnType','text':'Transaction Type'},
               {'value':'txnSubType','text':'Transaction Subtype'},
               {'value':'txnStatus','text':'Transaction Status'},
               {'value':'applicationDate','text':'Transaction Date'},
               {'value':'lastModifiedDate','text':'Last Modified Date'},
               {'value':'estColStartDt','text':'Estimated Collection Date'}
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
	 
	$("#selRptcols").dblclick(function(){
			$('#selRptcols option').prop('selected', 'selected'); 
		});
		$("#rptcols").dblclick(function(){
			$('#rptcols option').prop('selected', 'selected'); 
		});
});

		function doSubmit(form) { 
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
					jsonObj['width'] = '150';
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
						//alert(test);
						$("#tranRptflexme").flexigrid({ 
							url : test,
							dataType : 'json',
							colModel : columns, 
							sortname : "transactionNo",
							sortorder : "asc",
							title : 'TRANSACTION REPORT',
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
			 //var test = $("#ricTxnSubTypeUrl").val();  
			
			 //alert("==>>"+test);
		             
		         $.ajax({		        	
		        	 url: "${ricTxnSubTypeUrl}",
		        	 data: ({subtype : subtype}),
		        	 contentType: "application/json; charset=utf-8",
		             dataType: "json",
		        	 success: function(result){
		        		 $('#txnSubType').empty();
		        		// if(subtype=="ALL"||subtype==""){
		        	 		  $('#txnSubType').append($("<option></option>").attr("value", '').text('ALL'));
		        		 	 // $('#txnSubType').append($("<option></option>").attr("value", key).text(value));	
		        	 	 //}
		        	 	$.each(result, function(key, value){
		        	 		
		        	 		$('#txnSubType').append($("<option></option>").attr("value", key).text(value));	
		        	 		
		        	 	});
		        	 }
		        });
		         
		      

		});
		 
</script> 
<input type="hidden" id="currentURL" name="currentURL"
	value="${ricTxnResultUrl}" />
<%
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
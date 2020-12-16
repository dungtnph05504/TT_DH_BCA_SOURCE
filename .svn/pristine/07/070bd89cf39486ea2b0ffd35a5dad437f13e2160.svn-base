<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url var="searchViewDetailsUrl" value="/servlet/dataSyncMonitorController/searchViewDetailsForMonth" />
<c:url var="showExcelViewDetailResult" value="/servlet/dataSyncMonitorController/generateViewDetailsPDFForMonth" />
<c:url var="prevPageUrl" value="/servlet/dataSyncMonitorController/init" />
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<c:url var="retryDetailedTransUrl" value="/servlet/dataSyncMonitorController/retryDetailedTrans" />
<c:url var="dataSyncDetailsUrl" value="/servlet/dataSyncMonitorController/getDataSyncDetails" />
<c:url var="getViewSyncDetailsUrl" value="/servlet/dataSyncMonitorController/getViewSyncDetails" />
<c:url var="jobEnqJobDetailsUrl" value="/servlet/nicEnquiry/jobEnqJobDetails"/>
<c:url var="jobEnqDetailsInitUrl" value="/servlet/nicEnquiry/jobEnqDetailsInit" />
<c:url var="showPDFProofDoc" value="/servlet/nicEnquiry/showPDFProofDoc" />
<c:url var="fpInfoUrl" value="/servlet/nicEnquiry/fpInfo" />
<c:url var="fullViewPhotoUrl" value="/servlet/nicEnquiry/photosFullView" />
	 <!-- jquery plugin to zoom in and out, rotate the scanned document -->
<script src="<c:url value="/resources/js/jquery.iviewer.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/jquery.mousewheel.min.js"/>"
	type="text/javascript"></script>

 <link rel="stylesheet" type="text/css"
 href="<c:url value="/resources/css/jquery.iviewer.css"/>"></link>
 
<script src="<c:url value="/resources/js/mouseover_investigation.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/jQueryRotate.js"/>"
	type="text/javascript"></script>

<style>
.ui-datepicker-calendar {
    display: none;
    }
    
.modal {
	display: none;
	position: fixed;
	z-index: 9999999;
	top: 0;
	left: 0;
	height: 100%;
	width: 100%;
	background: rgba(255, 255, 255, .6)
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

.viewer
  {
      width: 700px;
      height: 700px;
      border: 1px solid black;
      position: relative;
  }

  .wrapper
  {
      overflow: hidden;
  }
</style>


<script>
var loadImg="0";
var selectedViewDate = '', isTransRetried="N";

$(function() {
	
	$("#dialog-approve").dialog({
		autoOpen : false,
		width : 1100,
		height : 600,
		modal : true,
		bgiframe : true,
		cache : false,
		closeOnEscape : false
	});
	
	
	$("#dialog-photoFullView").dialog( {
		autoOpen : false,
		width : 1100,
		height : 600,
		modal : true,
		bgiframe : true,
		cache :false,
		closeOnEscape: false
	});
	
	 $("#retrydialog-confirm" ).dialog({
			modal: true,
		      autoOpen: false,
			  width : 500,
			  resizable: true,
		      show: {
		        effect: "fade",
		        duration: 200
		      },
			   buttons:{
		    Ok: function() {    	
		    	$(this).dialog("close");
		    		
		    	refreshViewDetails();
		    }
		   }
	});
	 
	 $("#retrydialog-confirm2" ).dialog({
			modal: true,
		      autoOpen: false,
			  width : 500,
			  resizable: true,
		      show: {
		        effect: "fade",
		        duration: 200
		      },
			   buttons:{
		    Ok: function() {    	
		    	$(this).dialog("close");
		    	refreshReqAndStatusDetails($('#transIdVal').text(),$('#ninVal').text());
		    }
		   }
	});
	 
	
	$( "#selectedMonth" ).datepicker({
		 showOn : "button",
		 buttonImage : "<c:url value="/resources/images/calendar.gif" />",
		 buttonImageOnly : true,
		 showButtonPanel: false,
		 changeMonth : true,
		 changeYear : true,
		 maxDate: new Date,
		 minDate: new Date(2013, 8, 1),
		 showSecond : false,
		 controlType : 'select',
		 dateFormat : 'MM-yy',
		 yearRange: "-100:+10",
		 onClose: function(dateText, inst) {
	            var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
	            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();            
	            $(this).datepicker('setDate', new Date(year, month, 1));
	        },
	        beforeShow : function(input, inst) {
               if ((datestr = $(this).val()).length > 0) {
                   year = datestr.substring(datestr.length-4, datestr.length);
                   month = jQuery.inArray(datestr.substring(0, datestr.length-5), $(this).datepicker('option', 'monthNames'));
                   $(this).datepicker('option', 'defaultDate', new Date(year, month, 1));
                   $(this).datepicker('setDate', new Date(year, month, 1));	
				}
           }
		}).keyup(function( e) {
		      if(e.keyCode == 8 || e.keyCode == 46) {
		          $.datepicker._clearDate(this);
		      }
		});
	

	
 $(document).on("click", "#search_view_det_btn",function(){  
		 
	 if($("#selectedMonth").val()=='' || $("#selectedMonth").val()==null){
		 alert('Please select the Date');
		 return false;
	 }
	 
		 getSearchViewResults();
		 
	 });
 
 getSearchViewResults();
 
function getSearchViewResults() { 
	$('.modal').show();
	 var url = '${searchViewDetailsUrl}';
	 selectedViewDate = $("#selectedMonth").val();
		var orig = $("#dataSyncMonitoringForm").serialize();
		var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
		var test = url + '?' + withoutEmpties;  
	
		$.ajax({
	   	    type : "POST",
	   	    url : test,
	   	    success : function(data) {	
	   	    			$('#searchViewDetResult').html(data);
	   	    			$('.modal').hide();
	   	 		   },
	   	 	error : function(e){
		   	 	$('#searchViewDetResult').html('Error occurred while displaying the syncronization details');
	   			$('.modal').hide();
	   	 	}
		});
	}

});


function hideLoadImg(){
	 $('.modal').hide();
}

function generateViewDetailsPDF(){
	$("#selectedMonth").val( selectedViewDate);
	 
	var url = '${showExcelViewDetailResult}';
	
	//document.forms["dataSyncMonitoringForm"].action = url;  
	//document.forms["dataSyncMonitoringForm"].method = "POST";  
	//document.forms["dataSyncMonitoringForm"].submit();
	
	var orig = $("#dataSyncMonitoringForm").serialize();
	var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
	var test = url + '?' + withoutEmpties;  
	
	oIFrm = document.getElementById('reportIFrame'); 
    oIFrm.src = test; 
}


function goPrevPage(){
	var url = '${prevPageUrl}';
	
	document.forms["dataSyncMonitoringForm"].action = url;  
	document.forms["dataSyncMonitoringForm"].method = "POST";  
	document.forms["dataSyncMonitoringForm"].submit();
}


function closeDetDia(){
	$("#dialog-approve").dialog('close');
	
	if(isTransRetried=="Y"){
		refreshViewDetails();
	}
}

function refreshReqAndStatusDetails(transId, nin){
	$('.modal').show();
	 var url = '${getViewSyncDetailsUrl}';
	 $("#transactionId").val(transId)
	  $("#nin").val(nin)
		var orig = $("#dataSyncMonitoringForm").serialize();
		var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
		var test = url + '?' + withoutEmpties;  
		
		$.ajax({
	   	    type : "POST",
	   	    url : test,
	   	    success : function(data) {	
	   	    			$('#DS-Sync-Details').html(data);
	   	    			$('.modal').hide();
	   	 		   },
	   	 	error : function(e){
		   	 	$('#DS-Sync-Details').html('Error occurred while displaying the syncronization details for Transaction Id:'+transId+", NIN:"+nin);
	   			$('.modal').hide();
	   	 	}
		});
}

function openFullViewPhotoDialog(transactionId,nin){
	 $('.modal').show();
	var titleName ='Photos#'+transactionId+" ,"+nin;
	$("#dialog-photoFullView").dialog('option', 'title', titleName);
	$("#dialog-photoFullView").dialog( "option", "width", 1250 );
	$("#dialog-photoFullView").dialog( "option", "height", 850 );
	 $("#dialog-photoFullView").html("Loading .....");
	$("#dialog-photoFullView").dialog('open');
	$.ajax({
  	    type : "GET",
  	 	url: '${fullViewPhotoUrl}',  
  	 	data: {
  	 		transactionId: transactionId,
  	 		nin: nin
  	 	},  
  	    success : function(data) {				
		   	    	 $("#dialog-photoFullView").html(data);
		   	    	 $('.modal').hide();
  	 		      },
 		   	 	error : function(e){
		   	 		$("#dialog-photoFullView").html('Error occurred while displaying the Photos');
	   	    	 	$('.modal').hide();
		   	 	}
	});
}

function openSyncDetails(transId, nin ){
	$('.modal').show();
	isTransRetried="N";
	 var url = '${getViewSyncDetailsUrl}';
	 $("#transactionId").val(transId)
	  $("#nin").val(nin)
		var orig = $("#dataSyncMonitoringForm").serialize();
		var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
		var test = url + '?' + withoutEmpties;  
		
		$("#dialog-approve").dialog('option', 'title', 'Data Synchronization Requests & Status');
		$("#dialog-approve").dialog("option", "width", 1000);
		$("#dialog-approve").dialog("option", "height", 600);
		
		document.getElementById('DS-TransInfo-job').style.display="none";
		document.getElementById('DS-TransInfo').style.display="none";
		document.getElementById('DS-SyncDet').style.display="inline";
		$("#dialog-approve").dialog('open');
	
		$.ajax({
	   	    type : "POST",
	   	    url : test,
	   	    success : function(data) {	
	   	    			$('#DS-Sync-Details').html(data);
	   	    			$('.modal').hide();
	   	 		   },
	   	 	error : function(e){
		   	 	$('#DS-Sync-Details').html('Error occurred while displaying the syncronization details for Transaction Id:'+transId+", NIN:"+nin);
	   			$('.modal').hide();
	   	 	}
		});
}

function getFPinfo(transactionId,nin,fpIndicator,fpQuality,fpEncode, fpVerifyScore){
	  $('.modal').show();
	 var titleName ='Fingerprints #'+transactionId+" ,"+nin;
		$("#dialog-photoFullView").dialog('option', 'title', titleName);
		$("#dialog-photoFullView").dialog( "option", "width", 750 );
		$("#dialog-photoFullView").dialog( "option", "height",520 );
		 \$("#dialog-photoFullView").html("Loading .....");
		$("#dialog-photoFullView").dialog('open');
		$.ajax({
	   	    type : "GET",
	   	 	url: '${fpInfoUrl}',  
	   	 	data: {
	   	 		transactionId: transactionId,
	   	 		nin: nin,
	   	 		fpIndicator:fpIndicator,
	   	 		fpQuality:fpQuality,
	   	 		fpEncode:fpEncode,
	   	 		fpVerifyScore:fpVerifyScore
	   	 	},  
	   	    success : function(data) {				
			   	    	 $("#dialog-photoFullView").html(data);
			   	    	 $('.modal').hide();
	   	 		      },
	   	 	error : function(e){
	   	 		$("#dialog-photoFullView").html('Error occurred while displaying the fingerprint information');
 	    	 	$('.modal').hide();
	   	 	}
		}); 
}

function retryDetailedTrans(dataSyncId, transId){
	$('.modal').show();
	 var url = '${retryDetailedTransUrl}';
	 $("#dataSyncId").val( dataSyncId);
	 $("#transactionId").val( transId);
		var orig = $("#dataSyncMonitoringForm").serialize();
		var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
		var test = url + '?' + withoutEmpties;  
	
		$.ajax({
	   	    type : "POST",
	   	    url : test,
	   	    success : function(data) {	
	   	    			if(data=="Success"){
	   	    				$("#retrydialog-confirm").dialog('option', 'title', 'Status');
	   	    		       $("#retrydialog-confirm").html('Successfully submitted the Transaction: '+transId+' for Retry.');
	   	    		       $("#retrydialog-confirm").dialog( 'open' );
	   	    			}else{
	   	    				$("#retrydialog-confirm").dialog('option', 'title', 'Status');
		   	    		       $("#retrydialog-confirm").html('Error occurred while submitting the Transaction :  '+transId+' for Retry.');
		   	    		       $("#retrydialog-confirm").dialog( 'open' );
	   	    			}
	   	    			$('.modal').hide();
	   	 		   },
	   	 	error : function(e){
	   	 	$("#retrydialog-confirm").dialog('option', 'title', 'Status');
		       $("#retrydialog-confirm").html('Error occurred while submitting the Transaction :  '+transId+' for Retry.');
		       $("#retrydialog-confirm").dialog( 'open' );
	   			$('.modal').hide();
	   	 	}
		});
}

function retryReqAndStatusTrans(dataSyncId,transId){
	isTransRetried = "Y";
	$('.modal').show();
	 var url = '${retryDetailedTransUrl}';
	 $("#dataSyncId").val( dataSyncId);
	 $("#transactionId").val( transId);
		var orig = $("#dataSyncMonitoringForm").serialize();
		var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
		var test = url + '?' + withoutEmpties;  
	
		$.ajax({
	   	    type : "POST",
	   	    url : test,
	   	    success : function(data) {	
	   	    			if(data=="Success"){
	   	    				$("#retrydialog-confirm2").dialog('option', 'title', 'Status');
	   	    		       $("#retrydialog-confirm2").html('Successfully submitted the Transaction: '+transId+' for Retry.');
	   	    		       $("#retrydialog-confirm2").dialog( 'open' );
	   	    			}else{
	   	    				$("#retrydialog-confirm2").dialog('option', 'title', 'Status');
		   	    		       $("#retrydialog-confirm2").html('Error occurred while submitting the Transaction :  '+transId+' for Retry.');
		   	    		       $("#retrydialog-confirm2").dialog( 'open' );
	   	    			}
	   	    			$('.modal').hide();
	   	 		   },
	   	 	error : function(e){
	   	 	$("#retrydialog-confirm2").dialog('option', 'title', 'Status');
		       $("#retrydialog-confirm2").html('Error occurred while submitting the Transaction :  '+transId+' for Retry.');
		       $("#retrydialog-confirm2").dialog( 'open' );
	   			$('.modal').hide();
	   	 	}
		});
		
		
		
}


function refreshViewDetails(){
	$('.modal').show();
	 var url = '${searchViewDetailsUrl}';
	 $("#selectedMonth").val( selectedViewDate);
		var orig = $("#dataSyncMonitoringForm").serialize();
		var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
		var test = url + '?' + withoutEmpties;  
	
		$.ajax({
	   	    type : "POST",
	   	    url : test,
	   	    success : function(data) {	
	   	    			$('#searchViewDetResult').html(data);
	   	    			$('.modal').hide();
	   	 		   },
	   	 	error : function(e){
		   	 	$('#searchViewDetResult').html('Error occurred while displaying the syncronization details');
	   			$('.modal').hide();
	   	 	}
		});
	}
	
	
	function BackToSyncDet(){
		$("#dialog-approve").dialog('option', 'title', 'Data Synchronization Requests & Status');
		document.getElementById('DS-TransInfo-job').style.display="none";
		document.getElementById('DS-TransInfo').style.display="none";
		document.getElementById('DS-SyncDet').style.display="inline";
		
		
	}
	function ViewTransDet() {
		$("#dialog-approve").dialog('option', 'title', 'Transaction Details');
		
		document.getElementById('DS-TransInfo-job').style.display="inline";
		document.getElementById('DS-TransInfo').style.display="inline";
		document.getElementById('DS-SyncDet').style.display="none";
		
		
		$('.modal').show();
		//document.getElementById('jid').value = jobId;
		//document.getElementById('txnId').value = transId;
		$.ajax({
			type : "GET",
			url : "${jobEnqDetailsInitUrl}",
			success: function(data) { 
	 	    	$('#DS-TransInfo').html(data);
	 	    	 $('.modal').hide();
		  } ,
		  error: function(e){
			  $('#DS-TransInfo').html('Error occurred while displaying the tranaction details.');
	 	    	 $('.modal').hide();
		  }
		});
	}

	
function load(){
	var oIFrm = document.getElementById('reportIFrame'); 
		if($('#reportIFrame').contents().find('body').html() == '-1'){		
			alert('Error occurred while generating the report, please check the log for more details.');
		}else if($('#reportIFrame').contents().find('body').html() == '0'){
			alert('Error occurred while generating the report, please check the log for more details.');
		}
}

function RotateScanDoc(){
	var rotatId="#"+rotImagId+"Doc"; 
	var	src_img = $(rotatId).attr('src');
	 var iv1 = $("#viewer").iviewer({
				src: src_img
			}); 
		if(loadImg=="0"){ 
			loadImg="1";  
		}else{  
			iv1.iviewer('loadImage', src_img);
							return false;
		}
	}

function showProofDoc(transId,docView ,docName,dataType, docDesc){
    if ( (dataType == 'JPEG')) {
		$("#dialog-scan-doc").dialog('option', 'title', 'Proof Document :'+docDesc);
   	  $("#dialog-scan-doc").data('docView', docView).data(
  		       'docName', docName).dialog("open");
   }else {
		var url = "${showPDFProofDoc}" + "/" + transId + "/" + docName;
		
		window.open(url);
	} 
}

function closePhotoFullDialog(){
	 $("#dialog-photoFullView").dialog('close');
}
</script>
	<div id="main">
		<div id="content_main">

			<div id="heading_report" align="justify" style='padding: 2px'>Detailed NIC and AFIS Data Synchronization Monitoring</div>
<br /><br />
			<!--********************customised code for now***************************************-->
			<form:form modelAttribute="dataSyncMonitoringForm" name="dataSyncMonitoringForm" action="${searchViewDetailsUrl}" method="GET">
			
			
			<table style="width: 100%; text-align:center; background-color: #E3F1FE" cellspacing="0"
				class="data_table" cellpadding="0">
				<tr>
					<td style="border: none; font-weight: bold">Date &nbsp; &nbsp; &nbsp;&nbsp; &nbsp;
						<form:input type="text" id="selectedMonth" name="selectedMonth" path="selectedMonth" cssClass="defaultText" size="20" maxlength="20" readonly="true" />
						&nbsp; &nbsp; &nbsp;&nbsp; &nbsp;
						
						<input type="button" id="search_view_det_btn" class="button_grey" value="Generate" />
					
					</td>
    			  
				</tr>
			</table>
			<form:hidden path="transactionId" id="transactionId"/>
			<form:hidden path="nin" id="nin"/>
			<form:hidden path="dataSyncId" id="dataSyncId"/>
			<input type="hidden" id="jid" name="jid">
	<input type="hidden" id="txnId" name="txnId">
			</form:form>
		</div>

	</div>
	
	
	<div id="searchViewDetResult" style="font-size: 10px; padding:5px;">
	</div>
			
<div id="dialog-approve" style="display: none;">
	<table style="width: 100%;">
		<tr id="DS-TransInfo-job">
			<td>
				<table style="width: 100%;">
					<tr>
						<td align="right">
			
			<img id="back"  alt="View Data Sync Request and Status Details" src="<c:url value="/resources/images/back_2.png"/>" onclick="javaScript:BackToSyncDet();" style="border: align: right;"
																			align="right" />			
						</td>
					</tr>
					<tr>
						<td>
							<div id="DS-TransInfo">
								
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr id="DS-SyncDet">
			<td>
				<table style="width: 100%;">
					<tr>
						<td align="right">
							<input style="text-align: right;" type="button" id="DS-TransInfo-BackBtn" onclick="javaScript:ViewTransDet();" class="button_grey" value="View Transaction Details" />
						</td>
					</tr>
					<tr>
						<td>
							<div id="DS-Sync-Details">
								
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<div id="retrydialog-confirm" style="display: none;"></div>
<div id="retrydialog-confirm2" style="display: none;"></div>
<div class="modal">
		<!-- Place at bottom of page -->
</div>

	<div id="dialog-photoFullView" style="display: none;"></div>
		<c:if test="${viewFPFalg eq 'Y' }">
			<applet name="investigationApplet" code="com.nec.asia.applet.InvestigationApplet.class" codebase="<%=request.getContextPath()%>/applet" id="investigationApplet" archive="nic-applet.jar,spid6.jar" height="1" width="1" mayscript="mayscript">
		        <param name="verify" value="N">
		    </applet>
	   </c:if>
    


<iframe id="reportIFrame" src="" onload="load()" style="display:none;"> 
</iframe>
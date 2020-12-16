<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="proofDocMatrixUrl" value="/servlet/proofDocMatrixController/proofDocMatrix" />
<c:url var="proofDocUpdateUrl" value="/servlet/proofDocMatrixController/proofDocsUpdate" />
<c:url var="proofDocDeleteUrl" value="/servlet/proofDocMatrixController/proofDocsDelete" />
<c:url var="proofDocMatrixMain" value="/servlet/proofDocMatrixController/proofDocMatrix" />
<div id="main">
<div class="content_main">
	<div class="container">
	        	<div class="row">
	        		<div class="roundedBorder ov_hidden">
<form:form  modelAttribute="proofDocumentDef" id="proofDocumentDef"  name="proofDocumentDef"  class="inline">

<div id="heading_report" align="justify" style='padding:2px'>
        Edit Proof Document</div>   
                &nbsp;   

   <table style="width:100%;background-color:#E3F1FE" cellspacing="0"class="data_table_noborder" cellpadding="0">
    
    <tr><td width="15%">Document Id:</td><td><form:input type="text" size="30" path="documentId" id="documentId"  disabled="true"/></td></tr>
    <tr><td width="15%">Document Name:<span style="COLOR: #ff0000;">*</span></td><td><form:input type="text" size="30" path="documentDesc" id="documentDesc" /></td></tr>
    <tr><td width="15%">Transaction Type:</td>
	<td>
	 <form:input type="text" size="30" path="transactionType" id="transactionType" disabled="true"/>	
	</td>

	</tr>
    <tr><td width="15%">Transaction Subtype:</td>
	<td>
	 <form:input type="text" size="30" path="transactionSubtype" id="transactionSubtype" disabled="true"/>	
	</td></tr>
   <!-- <tr><td>System Id:</td><td><input type="text" size="30" value="RIC"/></td></tr>-->
   <tr><td width="15%">Indicator:</td><td><form:select style="width: 220px;" path="requireIndicator" id="requireIndicator">
		<form:option value="M">
			Mandatory
		</form:option>
		<form:option value="M1">
			Mandatory for citizen who were born after 30th September 1995
		</form:option>
		<form:option value="O">
			Optional
		</form:option>
		<form:option value="O1">
			Optional (Mandatory for all married citizens)
		</form:option>
	</form:select></td></tr>
</table>


	<table  class="displayTag" style="background-image:url('<%=request.getContextPath() %>/resources/images/head.png');background-repeat: repeat-x;height: 40px; text-align:right;">
	 <tr>
	 	<td align="right" style="padding: 10px; text-align: right;">
	 		<input type="button"  class="button_grey" 	id="updateBtn" value="Update" />
			&nbsp; <input onclick="window.location.href='${requestScope['javax.servlet.forward.request_uri']}'" type="button" class="button_grey" id="resetBtn" value="Reset" />
			&nbsp;<input type="button"  class="button_grey" id="deleteBtn" value="Delete" />
	 &nbsp;<input type="button"  class="button_grey" 	id="cancelBtn" value="Cancel" />
	</table>

</form:form>
</div>
</div>
</div>
</div>
</div>
<div id="savedialog-confirm"></div>
<div id="faildialog-confirm"></div>
<div id="dialog-confirm"></div>
<script>

  $(function() {
	  
	  $("#savedialog-confirm" ).dialog({
			modal: true,
		      autoOpen: false,
			  width : 500,
			  resizable: true,
		      show: {
		        effect: "fade",
		        duration: 200
		      },
		      hide: {
		        //effect: "explode",
		        //duration: 1000
		      },
			   buttons:{
		    Ok: function() {    	
		    	$(this).dialog("close");
		    		
		    	  document.forms["proofDocumentDef"].action = '${proofDocMatrixUrl}';
		    	  document.forms["proofDocumentDef"].submit();
		    }
		   }
	});
	 
	 $( "#faildialog-confirm" ).dialog({
			modal: true,
		      autoOpen: false,
			  width : 500,
			  resizable: true,
		      show: {
		        effect: "fade",
		        duration: 200
		      },
		      hide: {
		        //effect: "explode",
		        //duration: 1000
		      },
			   buttons:{
		    Ok: function() {    	
		    	$(this).dialog("close");
		    }
		   }
		    });
	 
	$( "#createDate" ).datepicker({
	showOn: "button",
	buttonImage: "images/calendar.gif",
	buttonImageOnly: true,
	dateFormat: 'dd/mm/yy'
	});
	
	 $( "#dialog-confirm" ).dialog({
			modal: true,
		      autoOpen: false,
			  width : 500,
			  resizable: true,
		      show: {
		        effect: "fade",
		        duration: 200
		      },
		      hide: {
		        //effect: "explode",
		        //duration: 1000
		      },
			   buttons:{
		    Ok: function() {
		    	$(this).dialog("close");
		    	document.getElementById('documentId').disabled=false;
			     document.getElementById('transactionType').disabled=false;
			     document.getElementById('transactionSubtype').disabled=false;
				document.forms["proofDocumentDef"].action = '${proofDocDeleteUrl}';
				//document.forms["proofDocumentDef"].action = '${proofDocMatrixMain}';
				
				 document.forms["proofDocumentDef"].submit();
		    },
			Cancel: function() {
				$(this).dialog("close");
		    }
		   }
		    });
	 
	$( "#updateDate" ).datepicker({
	showOn: "button",
	buttonImage: "images/calendar.gif",
	buttonImageOnly: true,
	dateFormat: 'dd/mm/yy'
	}); 
	
  $("#updateBtn").click(function(){
	     document.getElementById('documentId').disabled=false;
	     document.getElementById('transactionType').disabled=false;
	     document.getElementById('transactionSubtype').disabled=false;
		// document.forms["proofDocumentDef"].action = '${proofDocUpdateUrl}';
		// document.forms["proofDocumentDef"].action = '${proofDocMatrixMain}';
		 //document.forms["proofDocumentDef"].submit();
		 
		 $.post('<c:url value="/servlet/proofDocMatrixController/proofDocsUpdate" />',$('#proofDocumentDef').serialize(),
	             function(data){
	   if(data=='success'){
	    	$("#savedialog-confirm").dialog('option', 'title', 'Status');
	       $("#savedialog-confirm").html('Successfully updated the Proof Document :  '+$("#documentId").val());
	       $("#savedialog-confirm").dialog( 'open' );
	       
	    }else if(data=='fail'){
	     $("#faildialog-confirm").dialog('option', 'title', 'Status');
	     $("#faildialog-confirm").html('Failed to created Proof Document :  '+$("#documentId").val());
	     $("#faildialog-confirm").dialog( 'open' );
	    }
		/*  document.getElementById('documentId').disabled=true;
		 document.getElementById('transactionType').disabled=true;
	     document.getElementById('transactionSubtype').disabled=true; */
		 }); 
	});
  $("#deleteBtn").click(function(){	
	  	var id = $('#documentId').val();
	  $("#dialog-confirm").dialog('option', 'title', 'Delete Proof Document');
	    $("#dialog-confirm").html("Are you sure want to delete Proof Document : "+ id +"?");
	    $("#dialog-confirm").dialog( 'open' );
	    
		 /* document.getElementById('documentId').disabled=true;
		 document.getElementById('transactionType').disabled=true;
	     document.getElementById('transactionSubtype').disabled=true; */

	});
  $("#cancelBtn").click(function(){	
		 document.forms["proofDocumentDef"].action = '${proofDocMatrixUrl}';
		 document.forms["proofDocumentDef"].submit();	

	});
  

  });
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:url var="proofDocCreateUrl" value="/servlet/proofDocMatrixController/proofDocCreate" />
<c:url var="proofDocMatrixUrl" value="/servlet/proofDocMatrixController/proofDocMatrix" />
<div id="main">
<div class="content_main">
	<div class="container">
	        	<div class="row">
	        		<div class="roundedBorder ov_hidden">
<form:form  modelAttribute="proofDocumentDef"  commandName="proofDocumentDef" id="proofDocumentDef"  name="proofDocumentDef" class="inline">

<div id="heading_report" align="justify" style='padding:2px'>
        Add Proof Document</div>
     <!--********************customized code for now***************************************--> 
   <div id="admin_console_info_div" style="display:table-cell;vertical-align:top;" ></div><br/>
   <table style="width:100%;background-color:#E3F1FE" cellspacing="0"class="data_table_noborder" cellpadding="0">
    
    <tr><td style="width:15%">Document ID:<span style="COLOR: #ff0000;">*</span></td><td>
	  <form:input type="text" size="30" path="documentId" id="documentId"/>
	</td></tr>
    <tr><td width="15%">Document Name:<span style="COLOR: #ff0000;">*</span></td><td><form:input type="text" size="30" path="documentDesc" id="documentDesc" /></td></tr>
    <tr><td width="15%">Transaction Type:<span style="COLOR: #ff0000;">*</span></td>
	<td>
	<form:select style="width: 220px;" path="transactionType" id="transactionType" items="${transactionTypes}" itemLabel="codeValueDesc" itemValue="id.codeValue">
	</form:select>
	</td></tr>
    <tr><td width="15%">Transaction Subtype:<span style="COLOR: #ff0000;">*</span></td>
	<td> 
	<form:select style="width: 220px;" path="transactionSubtype" id="transactionSubtype"  items="${transactionSubTypes}" itemLabel="codeValueDesc" itemValue="id.codeValue">
		
	</form:select>
	</td></tr>
    <!--<tr><td>System Id:</td><td><input type="text" size="30"/></td></tr>-->
	<tr><td width="15%">Indicator:<span style="COLOR: #ff0000;">*</span></td><td>
	
	<form:select style="width: 220px;" path="requireIndicator" id="requireIndicator">
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
	</form:select>
	
	</td></tr>
    
</table>

	<table class="displayTag" style="background-image:url('<%=request.getContextPath() %>/resources/images/head.png');background-repeat: repeat-x;height: 40px; text-align:right;">
	<tr><td align="right" style="padding: 10px; text-align: right;"><input id="update_btn" type="button" class="button_grey" value="Save"/> &nbsp;
	<input onclick="window.location.href='${requestScope['javax.servlet.forward.request_uri']}'" 	type="button"  class="button_grey" 	id="resetBtn" value="Reset" />
	&nbsp;<input type="button"  class="button_grey" 	id="cancelBtn" value="Cancel" />
	
	</td></tr>
	</table>
	<input type="hidden" id="createBy" value="${sessionScope.userSession.userName}" />
	</form:form>
</div>
</div>
</div>
</div>
</div>
<div id="savedialog-confirm"></div>
<div id="faildialog-confirm"></div>

<script>
$(function() {
	
	 $("#savedialog-confirm").dialog({
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
	 
	 $("#cancelBtn").click(function(){	
		 document.forms["proofDocumentDef"].action = '${proofDocMatrixUrl}';
		 document.forms["proofDocumentDef"].submit();	

	});
  
	
  $('#update_btn').click(function() {
	  var documentId = $("#documentId").val();
	  if(documentId == ''){
		  alert('Please enter a value for Document Id');
		  return false;
	  }
	  
	  var documentDesc = $("#documentDesc").val()
	  if(documentDesc == ''){
		  alert('Please enter a value for Document Description');
		  return false;
	  }
	  
	  var transactionType = $("#transactionType").val()
	  if(transactionType == ''){
		  alert('Please select Transaction Type');
		  return false;
	  }
	  
	  var transactionSubtype = $("#transactionSubtype").val()
	  if(transactionSubtype == ''){
		  alert('Please select Transaction Subtype');
		  return false;
	  }
	  
	  var requireIndicator = $("#requireIndicator").val()
	  if(requireIndicator == ''){
		  alert('Please select an Indicator');
		  return false;
	  }
	  
	  $.post('<c:url value="/servlet/proofDocMatrixController/proofDocCreate" />',$('#proofDocumentDef').serialize(),
	             function(data){
	   if(data=='success'){
	   	   $("#savedialog-confirm").dialog('option', 'title', 'Status');
	       $("#savedialog-confirm").html('Successfully created the Proof Document :  '+$("#documentId").val());
	       $("#savedialog-confirm").dialog( 'open' );
	    }else if(data=='fail'){
	       $("#faildialog-confirm").dialog('option', 'title', 'Status');
	       $("#faildialog-confirm").html('Failed to created Proof Document :  '+$("#documentId").val());
	       $("#faildialog-confirm").dialog( 'open' );
	    }else if(data=='alreadyExists'){
		   $("#faildialog-confirm").dialog('option', 'title', 'Status');
		   $("#faildialog-confirm").html('Proof Document already exists with Document Id:'+$("#documentId").val()+", Transaction Type:"+$("#transactionType").val()+", Transaction Subtype:"+$("#transactionSubtype").val());
		   $("#faildialog-confirm").dialog( 'open' );
		}
	          
	              
	  });  
	  
	 //document.forms["proofDocumentDef"].action = '${proofDocCreateUrl}';
	 //document.forms["proofDocumentDef"].submit();
  });
  });

</script>

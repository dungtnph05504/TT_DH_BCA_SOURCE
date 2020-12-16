<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url var="submitUrl" value="/servlet/reportmgmt/reportGeneration" />
<c:url var="cancelUrl" value="/servlet/reportmgmt/generation" />
<c:url var="url" value="/servlet/reportmgmt" />
<c:url var="pdfDownload" value="/servlet/reportmgmt/staticpdf" />
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


<form:form method="GET" modelAttribute="reportMenuForm" id="reportMenuForm" name="reportMenuForm" enctype="multipart/form-data" cssClass="inline" >
	<div id="main">	
		<div class="container">
	        	<div class="row">
	        		<div class="roundedBorder ov_hidden">	
		<!-- Content Start -->
		<!-- <div id="content"> -->
			<!-- <span id="heading_report" style="width: 250px">Search Criteria</span><br /> -->
			<br />			
				<div align="right" class="displayTag" style="background-image:url('<%=request.getContextPath() %>/resources/images/head.png');background-repeat: repeat-x;">
				<table>
				 <tr>
				
						<td class="subjectsmall" height="50" width="150">Định dạng báo cáo</td>
						<td align="left" width="2" height="25">:</td>
						<td class='description_slimplain' align="left" width="100" height="25">
						<form:select id="rptFrmt"  path="rptFrmt">						
								<form:option value="pdf">PDF</form:option>
								<form:option value="xls">Excel</form:option>
								<form:option value="csv">CSV</form:option>								
						</form:select>
						<form:input type="hidden" id="reportId" path="reportId" />		
				        <form:input type="hidden" id="reportCategory" path="reportCategory" />						
						<form:input type="hidden" id="reportFileName" path="reportFileName" />
						</td>
						<td>
						</td>
						<td colspan="5" align="center" style="padding: 20px;">
						  <input  type="button" class="button_grey"  id="genBtnId"  value="Tạo báo cáo" />						
						  <input  type="button" class="button_grey"  id="cancelBtn" value="Hủy" />
						</td>
				</tr>
				</table>
				</div>
    </div>
</div>
</div>
</div>
</form:form>

				<div id="queryResult">
				</div>

<script>
$(function() { 
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
	$("#genBtnId").click(function() {	
		 var rptFrmt = $("#rptFrmt").val();
		 var category= $("#reportCategory").val();
		if(category!='CR'){
			 var jsonForm = JSON.stringify($('#reportMenuForm').serializeObject());							 
			 window.location = '${pdfDownload}'+'?jsonObj='+jsonForm;
	}else{
		if(rptFrmt=='HTML'){
		var urlQR = '${pdfDownload}'+'?jsonObj='+jsonForm;
		 $.ajax({
		   	    type : "GET",
		   	    url : urlQR,
		   	    success : function(data) {	
		   	    	 $("#queryResult").html(data);
   	 		      }
			});
	}else{
		 var jsonForm = JSON.stringify($('#reportMenuForm').serializeObject());							 
		 window.location = '${pdfDownload}'+'?jsonObj='+jsonForm;
	}
		
	}
				
     });
	
});


</script>


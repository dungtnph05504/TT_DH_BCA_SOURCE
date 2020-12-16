<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url var="searchUrl" value="/servlet/dataSyncMonitorController/search" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>


<style type="text/css">
 
.ui-datepicker-calendar {
    display: none;
    }
 
</style>

<style>
 .modal {
	display: none;
	position: fixed;
	z-index: 1000;
	top: 0;
	left: 0;
	height: 100%;
	width: 100%;
	background: rgba(255, 255, 255, .6)
		url('<c:url value="/resources/images/e921f-ajax-loader.gif" />') 50%
		50% no-repeat;
}
body.loading {
	overflow: hidden;
}

body.loading .modal {
	display: block;
}
 </style>


<script>
var selectedDate = '';

$(function() {
	
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
	
	 getSearchResult();
	
 $(document).on("click", "#search_btn",function(){  
		 
	 if($("#selectedMonth").val()=='' || $("#selectedMonth").val()==null){
		 alert('Please select From Date');
		 return false;
	 }
	 
		 getSearchResult();
		 
	 });
 
function getSearchResult() { 
	$('.modal').show();
	 var url = '${searchUrl}';
		 selectedDate = $("#selectedMonth").val();
		var orig = $("#dataSyncMonitoringForm").serialize();
		var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
		var test = url + '?' + withoutEmpties;  
	
		$.ajax({
	   	    type : "POST",
	   	    url : test,
	   	    success : function(data) {	
	   	    			$('#searchResult').html(data);
	   	    			$('.modal').hide();
	   	 		   },
	   	 	error : function(e){
		   	 	$('#searchResult').html('Đã xảy ra lỗi');
	   			$('.modal').hide();
	   	 	}
		});
	}

});


function hideLoadImg(){
	 $('.modal').hide();
}


function refreshDetails(){
	$('.modal').show();
	 var url = '${searchUrl}';
	 $("#selectedMonth").val( selectedDate);
		var orig = $("#dataSyncMonitoringForm").serialize();
		var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
		var test = url + '?' + withoutEmpties;  
	
		$.ajax({
	   	    type : "POST",
	   	    url : test,
	   	    success : function(data) {	
	   	    			$('#searchResult').html(data);
	   	    			$('.modal').hide();
	   	 		   },
	   	 	error : function(e){
		   	 	$('#searchResult').html('Đã xảy ra lỗi');
	   			$('.modal').hide();
	   	 	}
		});
	}
</script>
	<div id="main">
		<div id="content_main">

			<div id="heading_report" align="justify" style='padding: 2px'>Data Synchronization Monitoring</div>
<br /><br />
			<!--********************customised code for now***************************************-->
			<form:form modelAttribute="dataSyncMonitoringForm" name="dataSyncMonitoringForm" action="${searchUrl}" method="GET">
			
			
			<table style="width: 100%; text-align:center; background-color: #E3F1FE" cellspacing="0"
				class="data_table" cellpadding="0">
				<tr>
					<td style="border: none; font-weight: bold">Month &nbsp; &nbsp; &nbsp;&nbsp; &nbsp;
						<form:input type="text" id="selectedMonth" name="selectedMonth" path="selectedMonth" cssClass="defaultText" size="20" maxlength="20" readonly="true" />
						&nbsp; &nbsp; &nbsp;&nbsp; &nbsp;
						
						<input type="button" id="search_btn" class="button_grey" value="Generate" /> 
					
					</td>
    			  
				</tr>
			</table>
			<form:hidden path="selectedDate" id="selectedDate"/>
			</form:form>
		</div>

	</div>
	
	
	<div id="searchResult" style="font-size: 10px; padding:5px;">
	</div>
			

<div class="modal">
		<!-- Place at bottom of page -->
</div>
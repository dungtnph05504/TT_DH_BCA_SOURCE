<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url var="viewUrl" value="/servlet/transactionEnquiry/viewDetails" />
<c:url var="searchUrl" value="/servlet/transactionEnquiry/search" />
<c:url var="searchPhotosUrl" value="/servlet/transactionEnquiry/searchPhotos" />

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
</style>
 
<script>
$(function(){
	
	$("#dialog-photos").dialog( {
		autoOpen : false,
		width : 1100,
		height : 600,
		modal : true,
		bgiframe : true,
		cache :false,
		closeOnEscape: false
	});
	
});

</script>

<form:form modelAttribute="nicRegData" id="txnEnqForm" action="${searchUrl}" method="post">
	<div id="main">
		<div id="content_main">

			<div id="heading_report" align="justify" style='padding: 2px'>Re-print</div>

			<!--********************customised code for now***************************************-->
			<table style="width: 100%; background-color: #E3F1FE" cellspacing="0"
				class="data_table" cellpadding="0">
				<tr>
					<td style="border: none; font-weight: bold">NIN</td>
					<td style="border: none;"><form:input id="nin" name="nin" path="nin"   
							cssClass="defaultText" size="30" maxlength="30" /></td>
					<td style="border: none; font-weight: bold">Transaction Id</td>
					<td style="border: none;"><form:input id="transactionId" name="transactionId" path="transactionId"   
							cssClass="defaultText" size="30" maxlength="30" /></td>
				</tr>
				<!--<tr>
					<td style="border: none; font-weight: bold">Surname</td>
					<td style="border: none;"><form:input id="surnameFull" name="surnameFull" path="surnameFull"  
							cssClass="defaultText" size="30" maxlength="30" /></td>
					<td style="border: none; font-weight: bold; width: 15%;">Status</td>
					<td style="border: none;">
				     <select id="nicTransaction.transactionStatus" name="nicTransaction.transactionStatus" path="nicTransaction.transactionStatus">
				      <option value="">-- ALL --</option>    
				      <c:forEach var="opt" items="${transStatusList}">
				       <option title="${opt.codeValueDesc}" value="${opt.id.codeValue}"><c:out value="${opt.codeValueDesc}" /></option>
				      </c:forEach>
				     </select>
					</td>
				</tr>
				<tr>
					<td style="border: none; font-weight: bold">Surname At Birth</td>
					<td style="border: none;"><form:input id="surnameBirthFull" name="surnameBirthFull" path="surnameBirthFull"  
							cssClass="defaultText" size="30" maxlength="30" /></td>
					<td style="border: none; font-weight: bold">Other Name</td>
					<td style="border: none;"><form:input id="optionSurname" name="optionSurname" path="optionSurname"
							cssClass="defaultText" size="30" maxlength="30" /></td>
				</tr>
				<tr>
					<td style="border: none; font-weight: bold; width: 15%;">Gender</td>
					<td style="border: none;">
				     <select id="gender" name="gender" path="gender">
				      <option value="">-- ALL --</option>    
				      <c:forEach var="opt" items="${genderList}">
				       <option title="${opt.codeValueDesc}" value="${opt.id.codeValue}"><c:out value="${opt.codeValueDesc}" /></option>
				      </c:forEach>
				     </select>
					</td>
					<td style="border: none; font-weight: bold">Date of Birth</td>
					<td style="border: none;"><form:input id="dateOfBirthDisp" name="dateOfBirthDisp" path="dateOfBirthDisp"  
							cssClass="defaultText" size="12" maxlength="12" readonly="true" /></td>
				</tr>
				<tr>
					<td style="border: none; font-weight: bold">Application date</td>
					<td style="border: none;">
						from<form:input type="text" id="dateFrom" name="dateFrom" path="dateFrom" cssClass="defaultText" size="12" maxlength="12" readonly="true" />
						to<form:input type="text" id="dateTo" name="dateTo" path="dateTo" cssClass="defaultText" size="12" maxlength="12" readonly="true" />
					</td>
					<td colspan="2" align="right" style="padding: 10px; text-align: right;">
						 input type="button" id="search_btn" class="button_grey" value="Search"/>&nbsp; 
						<input type="button" id="search_pho_btn" class="button_grey" value="Search Photos" />&nbsp; 
						<input type="reset" id="reset_btn" class="button_grey" value="Reset" / 
					</td>
				</tr>

			--></table>

			<table style="width: 100%; text-align: right;">
				<tr>
					<td colspan="2" align="right" style="padding: 10px; text-align: right;">
						<input type="button" id="search_btn" class="button_grey" value="Search"/>&nbsp; 
						<!-- input type="button" id="search_pho_btn" class="button_grey" value="Search Photos" />&nbsp; --> 
						<input type="reset" id="reset_btn" class="button_grey" value="Reset" />
					</td>
				</tr>
			</table>

			<div id="searchResult">
				<table id="searchResultFlexGrid"></table>
			</div>
		</div>
	</div>

	<input type="hidden" id="nin" name="nin" />
	<input type="hidden" id="searchUrl" name="searchUrl" value="${searchUrl}" />
	<input type="hidden" id="searchPhotosUrl" name="searchPhotosUrl" value="${searchPhotosUrl}" />
	<input type="hidden" id="currentURLView" name="currentURLView" value="${viewUrl}" />
</form:form>
<div id="dialog-photos"></div>
<script type="text/javascript">  
var reload="0";
	$(function() { 
		$(document).on("click", "#search_btn", 
		  function(){  
			transQueryList(); 
		});
		
		$(document).on("click", "#search_pho_btn", 
			function(){  
				searchPhotos(); 
		});
				
		 $("#details").dialog({
				modal : true,
				autoOpen : false,
				width : 700,
				closeOnEscape : false,
				show: "slide",
			    showOpt: {direction: 'right'} 
			}); 
		
		  $("#dialog-viewDetails").dialog({
			modal : true,
			autoOpen : false,
			width : 700,
			closeOnEscape : false,
			show : {
				effect : "blind",
				duration : 1000
			},
			hide : {
				//effect : "explode",
				duration : 1000
			},
			open : function() {
				//var ninVal = $(this).data('nin');
			}

		}); 
		$("#msgDialog").dialog({
			width : 1200, 
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
		
		$("#dateOfBirthDisp").datepicker({
			showOn : "button",
			buttonImage : "<c:url value="/resources/images/calendar.gif" />",
			buttonImageOnly : true,
			changeMonth : true,
			changeYear : true,
			showSecond : true,
			controlType : 'select',
			dateFormat : 'dd-M-yy',
			yearRange: "-100:+10"
		}).keyup(function( e) {
		      if(e.keyCode == 8 || e.keyCode == 46) {
		          $.datepicker._clearDate(this);
		      }
		});
		$("#dateFrom").datepicker({
			showOn : "button",
			buttonImage : "<c:url value="/resources/images/calendar.gif" />",
			buttonImageOnly : true,
			changeMonth : true,
			changeYear : true,
			showSecond : true,
			controlType : 'select',
			dateFormat : 'dd-M-yy',
			yearRange: "-100:+10"
		}).keyup(function( e) {
		      if(e.keyCode == 8 || e.keyCode == 46) {
		          $.datepicker._clearDate(this);
		      }
		});

		$("#dateTo").datepicker({
			showOn : "button",
			buttonImage : "<c:url value="/resources/images/calendar.gif" />",
			buttonImageOnly : true,
			changeMonth : true,
			changeYear : true,
			showSecond : true,
			controlType : 'select',
			dateFormat : 'dd-M-yy',
			yearRange: "-100:+10"
		}).keyup(function( e) {
		      if(e.keyCode == 8 || e.keyCode == 46) {
		          $.datepicker._clearDate(this);
		      }
		});
		$('#dateTo').datepicker().datepicker('setDate',new Date());
		$("#reset_btn").click(function() {  
			$("#exptnHandln").hide(); 
			$("#nicTransaction.regSiteCode").val("");
			$("#transactionId").val("");
			$("#surnameFull").val("");
			$("#surnameBirthFull").val(""); 
			$("#optionSurname").val("");
			$("#gender").val(""); 
			$("#dateOfBirth").val("");  
			$("#searchResultFlexGrid").empty();
			$("#searchResultFlexGrid").hide();
		}); 
 
	});

	function validSearch(){
		if($("#nicTransaction.regSiteCode").val() == "" && $("#transactionId").val()  == "" && $("#ccNo").val()==""
			&&$("#surnameFull").val()  == "" && $("#surnameBirthFull").val() == "" && $("#optionSurname").val()==""
			&& $("#gender").val()=="" && $("#dateOfBirth").val()==""){  
			return false
		} 
		return true;
	}

	var jsondata; 
	function transQueryList() { 
		if(validSearch()==false){
			$("#msgDialogSpan").html("Please Key in One or More Search Criteria");
			$("#msgDialog").dialog("open");
			return false;
		} 
		var orig = $("#txnEnqForm").serialize();
		var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
		var test = $("#searchUrl").val() + '?' + withoutEmpties;  
		$('#searchResultFlexGrid').empty();
		$("#exptnHandln").show(); 
		$('#searchResultFlexGrid').show(); 
		if(reload=="0"){
			reload="1";
			$("#searchResultFlexGrid").flexigrid({ 
				url:test,
				dataType : 'json',
				colModel : [
					{display : 'Transaction Id', name : 'transactionId', width : 160, align : 'left', render : renderView },
					{display : 'NIN', name : 'nin', width : 150, sortable : true, align : 'left' }, 
					{display : 'Surname', name : 'surnameFull', width : 180, sortable : true, align : 'left' }, 
					{display : 'Firstname', name : 'firstnameFull', width : 220, sortable : true, align : 'left' }, 
					{display : 'Surname At Birth', name : 'surnameBirthFull', width : 200, sortable : true, align : 'left' },
					{display : 'Gender', name : 'gender', width : 60, sortable : true, align : 'left' }, 
					//{display : 'Date Of Birth', name : 'dateOfBirth', width : 100, sortable : true, align : 'left' }, 
					{display : 'Transaction Status', name : 'nicTransaction.transactionStatus', width :208, sortable : true, align : 'left' }
				], 
				sortname : "nicId",
				sortorder : "desc",
				title : 'NIC Transaction List to Re-print',
				usepager : true,
				useRp : true,
				rp : 20,
				showTableToggleBtn : true,			
				height : 250,
				singleSelect : true,
				nowrap : false, 
				onSuccess: function(){  
					vlidateData();
			    } 
			});
		}else{
			$("#searchResultFlexGrid").flexOptions({ 
				url:test, newp:1 
			}).flexReload()
		}

		function vlidateData(){ 
			var rowlength=document.getElementById("searchResultFlexGrid").rows.length;   
            if(rowlength > 100){
            	$("#msgDialogSpan").html("System will return Max of 100 Records");
    			$("#msgDialog").dialog("open");  
                return false;
            }
		}
		/* var frm = $('#txnEnqForm'); 
		$.ajax({
			type: frm.attr('method'),
            url: frm.attr('action'),
            data: frm.serialize(),
            success: function (data) {
                if(data.rows == null && data.total > 0){
                	$("#msgDialogSpan").html("Search found too many matches, please key in one or more search criteria");
        			$("#msgDialog").dialog("open");
                    return false;
                }
                if(data.total > 100){
                	$("#msgDialogSpan").html("System will return Max of 100 Records");
        			$("#msgDialog").dialog("open");
                    return false;
                }
            	jsondata = data;
            	$('#searchResultFlexGrid').show();
            	$('#searchResultFlexGrid').flexAddData(data);
			}
		}); */ 
	}
 
	function renderView(content){
		 return "<a href=\"#\" onclick=\"viewDtls('" + content + "')\">" + content + "</a>"; 
	} 
 
	function viewDtls(transNo){   
		var url = $("#currentURLView").val()+"/"+transNo;
		document.forms["txnEnqForm"].action = url;  
		document.forms["txnEnqForm"].method = "GET";  
		document.forms["txnEnqForm"].onSubmit=window.showModalDialog(url,null, "dialogWidth:1280px;dialogHeight:800px;resizable:yes;addressbar:no;"); 
	}

	/* function searchPhotos() {
		$('.modal').show();
		var titleName = "Photo Gallery";
		$.ajax({
			type : "POST",
			url : '${searchPhotosUrl}',
		    data: $("#txnEnqForm").serialize(),  
			success : function(data) {
				$("#dialog-photos").html(data);
				$("#dialog-approve").dialog('option', 'title', titleName);
				$("#dialog-approve").dialog('open');
				$('.modal').hide();
			}
		});
	} */
	
	function searchPhotos() {
		$('.modal').show();
		var titleName = "Photo Gallery";
		var orig = $("#txnEnqForm").serialize();
		var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
		var test = $("#searchPhotosUrl").val() + '?' + withoutEmpties;

		$.ajax({
			type : "POST",
			url : test,
			cache : false,
			success : function(data) {
				$("#dialog-photos").html(data);
				$("#dialog-photos").dialog('open');
				$('.modal').hide();
			}
		});
	}

	/* $('.modal').show();
	 $.ajax({
	    type:'POST',
	    url:ajaxUrl,
	    cache : false,
	    data: $("#ricTransactionInfoForm").serialize(),
	    success: function(data) {
	       //alert(data);
	   	 $('#ajaxSuccessMessage').html(data);
	        //$('#info').hide('slow');
	        $('#ajaxSuccessMessage').show('slow');
	        $('.modal').hide('slow');
	    },
	    error: function(e){

	        alert('Error: ' + e);
	        $('.modal').hide('slow');
	    }
	}); */
</script>
<style>
#exptnHandln {
	float: left;
	width: 1260px; 
}</style>

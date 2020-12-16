<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url var="viewUrl" value="/servlet/cardEnquiry/viewHistory" />
<c:url var="url" value="/servlet/cardEnquiry/search" />
<div class="container">
                        <div class="row">
                            <div class="roundedBorder ov_hidden">
<table
	style="border: 1px SOLID; border-radius: 10px; margin-top: 0px; margin-left: 1px; background-color: #FFFFC6; width: 99%">
	<tr>
		<td>
			<div style="height: 15px" id="sub_heading_new">Card Enquiry</div>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td><form:form id="cardEnquiryForm" action="${url}"
				method="post" style="width: 100%; background-color:#FFFFC6;"
				modelAttribute="nicIssuance">
				<table class="innerTable" style="margin-left: 5px;">
					<tr>
						<td class="subject" width="25%">NIN</td>
						<td><form:input path="nin" id="nin" maxlength="14" /></td>
						<td width="10%"></td>
						<td class="subject" width="25%">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td class="subject" width="25%">CCN</td>
						<td><form:input path="ccn" id="ccn" /></td>
						<td width="15%"></td>
						<td class="subject" width="25%">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td colspan="4"><br /> <input id="srchIdBtn" type="button"
							class="btn_blue" value="Search">&nbsp;&nbsp; <input
							id="resetAllBtn" type="button" class="btn_blue" value="Reset"> 
						</td>
					</tr>
				</table>
				<input type="hidden" id="currentURLView" name="currentURLView"
					value="${viewUrl}" />
			</form:form></td>
	</tr>
</table>
<input type="hidden" id="currentURL" name="currentURL" value="${url}" />
<div id="exptnHandln">
  <table id="cardEnquiryFlexGrid"></table>
</div> 
<div id="msgDialog" title="Alert">
	<div class="isa_info" align="center">
		<span style="font-size: 40" id="msgDialogSpan"></span>
	</div>
</div> 
</div>
</div>
</div>
<script type="text/javascript">  
var reload="0";
	$(function() { 
		$(document).on("click", "#srchIdBtn", 
		  function(){  
			transQueryList(); 
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
		
		$("#dateOfBirth").datepicker({
			showOn : "button",
			buttonImage : "<c:url value="/resources/images/calendar.gif" />",
			buttonImageOnly : true,
			changeMonth : true,
			changeYear : true,
			showSecond : true,
			controlType : 'select',
			dateFormat : 'dd-M-yy',
			yearRange: "-100:+10"
		});
		$("#resetAllBtn").click(function() {  
			$("#exptnHandln").hide(); 
			$("#nin").val("");
			$("#ccn").val("");
			$("#cardEnquiryFlexGrid").empty();
			$("#cardEnquiryFlexGrid").hide();
		}); 
 
	});

	function validSearch(){
		if($("#nin").val() == "" && $("#ccn").val()  == ""){  
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
		var orig = $("#cardEnquiryForm").serialize();
		var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
		var test = $("#currentURL").val() + '?' + withoutEmpties;  
		$('#cardEnquiryFlexGrid').empty();
		$("#exptnHandln").show(); 
		$('#cardEnquiryFlexGrid').show(); 
		if(reload=="0"){
			reload="1";
			$("#cardEnquiryFlexGrid").flexigrid({ 
				url:test,
				dataType : 'json',
				colModel : [
					{display : 'NIN', name : 'nin', width : 140, sortable : true, align : 'left', render : renderView}, 
					{display : 'CCN', name : 'ccn', width : 140, sortable : false, align : 'left' }, 
					{display : 'Transaction Id', name : 'nicTransaction.transactionId', width : 150, align : 'left' },
					//{display : 'Registration Site', name : 'nicTransaction.regSiteCode', width : 150, align : 'center' },
					{display : 'Package Id', name : 'packageId', width : 150, sortable : false, align : 'left' },
					{display : 'Sam Key Version', name : 'samKeyVersion', width : 150, sortable : false, align : 'left' },
					{display : 'Card Status', name : 'cardStatus', width : 150, sortable : false, align : 'left' },
					//{display : 'Card Status Change Time', name : 'cardStatusChangeTime', width : 150, sortable : false, align : 'left' },
					//{display : 'Card Status Change Reason', name : 'cardStatusChangeReason', width : 150, sortable : false, align : 'left' },
					{display : 'Date of Issue', name : 'dateOfIssue', width : 150, sortable : false, align : 'left' },
					{display : 'Collection Date', name : 'collectionDate', width : 150, sortable : false, align : 'left' },
					], 
				sortname : "updateDate",
				sortorder : "desc",
				title : 'Card details',
				usepager : true,
				useRp : true,
				rp : 15,
				showTableToggleBtn : true,			
				height : 250,
				singleSelect : true,
				nowrap : false, 
				onSuccess: function(){  
					vlidateData();
			    } 
			});
		}else{
			$("#cardEnquiryFlexGrid").flexOptions({ 
				url:test 
			}).flexReload()
		}

		function vlidateData(){ 
				var rowlength=document.getElementById("cardEnquiryFlexGrid").rows.length;   
                if(rowlength > 100){
                	$("#msgDialogSpan").html("System will return Max of 100 Records");
        			$("#msgDialog").dialog("open");  
                    return false;
                }
			}
		/* var frm = $('#cardEnquiryForm'); 
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
            	$('#cardEnquiryFlexGrid').show();
            	$('#cardEnquiryFlexGrid').flexAddData(data);
			}
		}); */ 
	}
 
	function renderView(content){
		 return "<a href=\"#\" onclick=\"viewDtls('" + content + "')\">" + content + "</a>"; 
	} 
 
	function viewDtls(nin){   
		var url = $("#currentURLView").val()+"/"+nin; 
		document.forms["cardEnquiryForm"].action = url;  
		document.forms["cardEnquiryForm"].method = "GET";  
		// document.forms["cardEnquiryForm"].submit();
		//fix for the AddressBar
		document.forms["cardEnquiryForm"].onSubmit=window.showModalDialog(url,null, "dialogWidth:850px;dialogHeight:300px;resizable:yes;addressbar:no;"); 
	}    
</script>
<style>
#exptnHandln {
	float: left;
	width: 1260px; 
}</style>

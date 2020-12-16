<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	try {
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<table
	style="border: 1px SOLID; border-radius: 10px; margin-top: 0px; margin-left: 1px; background-color: #FFFFC6; width: 99%">
	<tr>
		<td>
			<div style="height: 15px" id="sub_heading_new">Transaction And
				Card Status Search</div>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td><form:form id="transactionOrCardStatusForm" action="${url}"
				method="post" style="width: 100%; background-color:#FFFFC6;"
				modelAttribute="TransactionOrCardStatusDetailsDTO">
				<table class="innerTable" style="margin-left: 5px;">
					<tr>
						<td class="subject" width="25%">NIN</td>
						<td><form:input path="nin" id="nin" /></td>
						<td width="10%"></td>
						<td class="subject" width="25%">Transaction Number</td>
						<td><form:input path="transactionNo" id="transNo" /></td>
					</tr>
					<tr>
						<td class="subject" width="25%">First Name</td>
						<td><form:input path="firstName" id="firstName" /></td>
						<td width="15%"></td>
						<td class="subject" width="25%">Card Control Number</td>
						<td><form:input path="ccNo" id="ccNo" /></td>
					</tr>
					<tr>
						<td class="subject" width="25%">Surname</td>
						<td><form:input path="surname" id="surname" /></td>
						<td width="10%"></td>
						<td class="subject" width="25%">Surname At Birth</td>
						<td><form:input path="surnameBirth" id="surnameBirth" /></td>
					</tr>
					<tr>
						<td class="subject" width="25%">Gender</td>
						<td><form:select id="gender" path="gender">
								<form:option value="NONE" label="--- Select ---" />
								<form:options items="${genderList}" id="genderLst" />
							</form:select></td>
						<td width="10%"></td>
						<td class="subject" width="25%">Date of Birth</td>
						<td><form:input path="dob" id="dob" readonly="true" /></td>
					</tr>
					<tr>
						<td colspan="4"><br /> <input id="srchIdBtn" type="button"
							class="btn_blue" value="Search">&nbsp;&nbsp; <input
							id="resetAllBtn" type="button" class="btn_blue" value="Reset">
						</td>
					</tr>
				</table>
			</form:form></td>
	</tr>
</table>
<table
	style="border: 1px SOLID; border-radius: 10px; margin-top: 2px; margin-left: 1px; background-color: #FFFFC6; width: 99%; display: none"
	id="cpdv2Tbl">
	<tr>
		<td class="">
			<table id="transOrCardStatusFlexGrid"></table>
		</td>
	</tr>
</table>

<script type="text/javascript">

	$("#transOrCardStatusFlexGrid").flexigrid({
		//url : "${url}",
		dataType : 'json',
		colModel : [
			{display : 'Card Control No', name : 'ccNo', width : 135, align : 'center', sortable : true },
			{display : 'Transaction Status', name : 'transactionStatus', width : 95, align : 'center', sortable : true },  
			{display : 'Transaction Id', name : 'transactionNo', width : 100, align : 'center', hide : false },
			{display : 'NIN', name : 'nin', width : 120, sortable : false, align : 'center' }, 
			{display : 'Surname', name : 'surname', width : 88, sortable : false, align : 'center' }, 
			{display : 'Firstname', name : 'firstName', width : 95, sortable : false, align : 'center' }, 
			{display : 'Date Of Birth', name : 'dob', width : 90, align : 'center' }, 
			{display : 'Date Of Issue', name : 'dateOfIssue', width : 100, align : 'center'},   
			{display : '', name : 'nin', width : 50, align : 'center', render : renderView}
			
			], 
		sortname : "cardControlNo",
		sortorder : "asc",
		title : 'Transaction / Card Status Enquiry',
		usepager : false,
		useRp : true,
		rp : 15,
		showTableToggleBtn : true,			
		height : 'auto',
		singleSelect : true,
		nowrap : false
	});

	
	$(document).ready(function() {
		$("#srchIdBtn").click(function(){
			loadCpdv2List(); 
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
			width : 600,
			height : 200,
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
		
		$("#dob").datepicker({
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
			document.getElementById("cpdv2Form").reset();
			$('#cpdv2FlexGrid').empty();
			$("#cpdv2Tbl").hide();
		});
	});

	function validSearch(){
		if($("#nin").val() == "" && $("#surname").val()  == "" &&
		$("#firstname").val()  == "" && $("#surnameatbirth").val() == ""){
			var countSearch = 0;
			if($("#address1").val() != ""){
				countSearch++;
			}
			if($("#address2").val() != ""){
				countSearch++;
			}
			if($("#address3").val() != ""){
				countSearch++;
			}
			if($("#address4").val() != ""){
				countSearch++;
			}
			if($("#dob").val() != ""){
				countSearch++;
			}
			if($("#gender").val() != "NONE"){
				countSearch++;
			}
			if(countSearch<2){
				return false;
			}
		}
		return true;
	}

	var jsondata;
	function loadCpdv2List() {
		if(validSearch()==false){
			$("#msgDialogSpan").html("Please Key in One or More Search Criteria");
			$("#msgDialog").dialog("open");
			return false;
		}
		
		var frm = $('#cpdv2Form');
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
            	jsondata = data;
            	$('#cpdv2FlexGrid').flexAddData(data);
			}
		});
		$("#cpdv2Tbl").show();
	}

	function viewDtls(nin) {
		for(var index = 0 ; index < jsondata.rows.length ; index++){
			if(jsondata.rows[index].nin == nin){
				$('#ninid').html(jsondata.rows[index].nin);
				$('#firstnameid').html(jsondata.rows[index].firstname);
				$('#surnameid').html(jsondata.rows[index].surname);
				$('#dobid').html(jsondata.rows[index].dob);
				$('#genderid').html(jsondata.rows[index].gender);
				$('#maritalstatusid').html(jsondata.rows[index].maritalStatus);
				$('#address1id').html(jsondata.rows[index].address1);
				$('#address2id').html(jsondata.rows[index].address2);
				$('#address3id').html(jsondata.rows[index].address3);
				$('#address4id').html(jsondata.rows[index].address4);
				$('#fathernameid').html(jsondata.rows[index].fatherName);
				$('#fathersurnameid').html(jsondata.rows[index].fatherSurname);
				$('#mothernameid').html(jsondata.rows[index].motherName);
				$('#mothersurnameid').html(jsondata.rows[index].motherSurname);
				
				break;
			}
		}
		$("#dialog-viewDetails").dialog("open");
	}

	function renderView(content){
		return "<a href=\"#\" onclick=\"viewDtls('" + content + "')\">View</a>";
	}

	
</script>
<%
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
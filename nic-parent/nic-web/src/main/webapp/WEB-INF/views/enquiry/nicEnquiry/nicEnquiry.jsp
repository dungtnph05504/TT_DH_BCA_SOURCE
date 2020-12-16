<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url var="viewHistoryUrl" value="/servlet/nicMainEnquiry/viewHistory" />
<c:url var="url" value="/servlet/nicMainEnquiry/search" />

<table
	style="border: 1px SOLID; border-radius: 10px; margin-top: 0px; margin-left: 1px; background-color: #FFFFC6; width: 99%">
	<tr>
		<td>
			<div style="height: 15px" id="sub_heading_new">Tra cứu NIC</div>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td><form:form id="nicEnquiryForm" action="${url}"
				method="post" style="width: 100%; background-color:#FFFFC6;"
				modelAttribute="nicMain">
				<table class="innerTable" style="margin-left: 5px;">
					<tr>
						<td class="subject" width="25%">CMND</td>
						<td><form:input path="nin" id="nin" maxlength="14" /></td>
						<td width="10%"></td>
						<td class="subject" width="25%">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td class="subject" width="25%">Họ</td>
						<td><form:input path="surname" id="surname" /></td>
						<td width="15%"></td>
						<td class="subject" width="25%">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				<!--	<tr>
						<td class="subject" width="25%">Tên khai sinh</td>
						<td><form:input path="surnameBirth" id="surnameBirth" /></td>
						<td width="10%"></td>
						<td class="subject" width="25%">Tên khác</td>
						<td><form:input path="optionSurname" id="optionSurname" /></td>
					</tr>-->
					<tr>
						<td class="subject" width="25%">Giới tính</td>
						<td><form:select id="gender" path="gender">
								<form:option value="" label="--- Select ---" />
								<form:options items="${genderList}" id="genderLst" />
							</form:select></td>
						<td width="10%"></td>
						<td class="subject" width="25%">Ngày sinh</td>
						<td><form:input path="dateOfBirth" id="dateOfBirth" readonly="true" /></td>
					</tr>
					<tr>
						<td colspan="4"><br /> <input id="srchIdBtn" type="button"
							class="btn_small btn-primary" value="Tìm kiếm">&nbsp;&nbsp; <input
							id="resetAllBtn" type="button" class="btn_small btn-primary" value="Làm lại"> 
						</td>
					</tr>
				</table>
				<input type="hidden" id="nin" name="nin" />
			</form:form></td>
	</tr>
</table>
<input type="hidden" id="currentURL" name="currentURL" value="${url}" />
<div id="exptnHandln">
  <table id="nicEnquiryFlexGrid"></table>
</div> 
<div id="msgDialog" title="Alert">
	<div class="isa_info" align="center">
		<span style="font-size: 40" id="msgDialogSpan"></span>
	</div>
</div> 
<div id="dialog-approve">
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

		$("#dialog-approve").dialog( {
			autoOpen : false,
			width : 800,
			height : 150,
			modal : true,
			bgiframe : true,
			cache :false,
			closeOnEscape: true
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
			$("#transNo").val("");
			$("#firstName").val("");
			$("#ccNo").val("");
			$("#surname").val("");
			$("#surnameBirth").val(""); 
			$("#gender").val(""); 
			$("#dateOfBirth").val("");  
			$("#nicEnquiryFlexGrid").empty();
			$("#nicEnquiryFlexGrid").hide();
		}); 
 
	});

	function validSearch(){
		if($("#nin").val() == "" && $("#surname").val()  == "" && $("#ccNo").val()=="" &&
		$("#firstName").val()  == "" && $("#surnameBirth").val() == "" && $("#transNo").val()==""){  
			return false
		} 
		return true;
	}

	var jsondata; 
	function transQueryList() { 
		if(validSearch()==false){
			$("#msgDialogSpan").html("Vui lòng nhập một hoặc nhiều tiêu chí tìm kiếm");
			$("#msgDialog").dialog("open");
			return false;
		} 
		var orig = $("#nicEnquiryForm").serialize();
		var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
		var test = $("#currentURL").val() + '?' + withoutEmpties;  
		$('#nicEnquiryFlexGrid').empty();
		$("#exptnHandln").show(); 
		$('#nicEnquiryFlexGrid').show(); 
		if(reload=="0"){
			reload="1";
			$("#nicEnquiryFlexGrid").flexigrid({ 
				url:test,
				dataType : 'json',
				colModel : [
					{display : 'NIN', name : 'nin', width : 140, sortable : true, align : 'center', render : renderView}, 
					{display : 'Gen no', name : 'genNo', width : 60, sortable : false, align : 'center' }, 
					{display : 'Transaction Id', name : 'transactionId', width : 150, align : 'center' },
					{display : 'Surname', name : 'surname', width : 190, sortable : false, align : 'center' }, 
					{display : 'Firstname', name : 'firstname', width : 190, sortable : false, align : 'center' }, 
					{display : 'Surname at birth', name : 'surnameBirthFull', width : 190, sortable : false, align : 'center' }, 
					{display : 'Gender', name : 'gender', width : 80, sortable : false, align : 'center' }, 
					/*{display : 'Date Of Birth', name : 'dateOfBirth', width : 150, sortable : false, align : 'center' },*/ 
				], 
				sortname : "nicId",
				sortorder : "asc",
				title : 'NIC Danh sách đăng ký',
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
			$("#nicEnquiryFlexGrid").flexOptions({ 
				url:test 
			}).flexReload()
		}

		function vlidateData(){ 
				var rowlength=document.getElementById("nicEnquiryFlexGrid").rows.length;   
                if(rowlength > 100){
                	$("#msgDialogSpan").html("Hệ thống sẽ trả tối đa 100 kết quả");
        			$("#msgDialog").dialog("open");  
                    return false;
                }
			}
		/* var frm = $('#nicEnquiryForm'); 
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
            	$('#nicEnquiryFlexGrid').show();
            	$('#nicEnquiryFlexGrid').flexAddData(data);
			}
		}); */ 
	}
 
	function renderView(content){
		return "<a href=\"#\" onclick=\"viewHistory('" + content + "')\">" + content + "</a>"; 
	} 
 
	function viewHistory(nin) {
		$('.modal').show();
		var titleName = "NIC History";
		$("#dialog-approve").dialog('option', 'title', titleName);
		$("#dialog-approve").dialog('open');
		$.ajax({
			type : "GET",
			url : '${viewHistoryUrl}/'+nin,
		    data: $("#nicEnquiryForm").serializeArray(),  
			success : function(data) {
				$("#dialog-approve").html(data);
				$('.modal').hide();
			}

		});
	}


</script>
<style>
#exptnHandln {
	float: left;
	width: 1260px; 
}</style>

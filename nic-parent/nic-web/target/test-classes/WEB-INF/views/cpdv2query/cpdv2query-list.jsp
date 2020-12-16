<%try{ %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:url var="url" value="/servlet/cpdv2query/cpdv2_ajax" />

	<table style="border:1px SOLID;border-radius: 10px ;margin-top:0px;margin-left:1px;background-color:#FFFFC6;width: 99%">
		<tr>	
			<td>
				<div style="height:15px" id="sub_heading_new">Tiêu chí tìm kiếm</div> 
			</td>
		</tr> 
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td>
			<form:form id="cpdv2Form" action="${url}" method="post" style="width: 100%; background-color:#FFFFC6;" modelAttribute="ninDetailsDto">
				<table class="innerTable" style="margin-left:5px;">
					<tr>
						<td class="subject" width="25%">CMND/CCCD</td>
						<td>
							<form:input path="nin" id="nin"/>
						</td>
						<td class="subject" width="25%">Họ</td>
						<td>
							<form:input path="surname" id="surname" />
						</td>
					</tr>
					
					<tr>
						<td class="subject">Tên</td>
						<td>
							<form:input path="firstname" id="firstname" />
						</td>
						<td class="subject">Tên đệm</td>
						<td>
							<form:input path="surnameBirth" id="surnameatbirth" />
						</td>
					</tr>
					<tr>
						<td class="subject">Address 1 (FlatNo, Building Name)</td>
						<td>
							<form:input path="address1" id="address1" />
						</td>
						<td class="subject">Address 2 (Street No and Name)</td>
						<td>
							<form:input path="address2" id="address2" />
						</td>
					</tr>
					<tr>
						<td class="subject">Address 3 (Locality)</td>
						<td>
							<form:input path="address3" id="address3" />
						</td>
						<td class="subject">Address 4 (Town/District/Village)</td>
						<td>
							<form:input path="address4" id="address4" />
						</td>
					</tr>
					<tr>
						<td class="subject">Giới tính</td>
						<td>
							<form:select id="gender" path="gender">
								<form:option value="NONE" label="--- Select ---" />
								<form:options items="${genderList}" id="genderLst" />
							</form:select>
						</td>
						<td class="subject">Ngày tháng năm sinh</td>
						<td>
							<form:input path="dob" id="dob" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td colspan="4">
							<br/>
							<input id="srchIdBtn" type="button" class="btn_blue" value="Tìm kiếm">
							<input id="resetAllBtn" type="button" class="btn_blue" value="Làm lại">
						</td>
					</tr>
				</table>
				</form:form>
			</td>
		</tr>
	</table>
	<table style="border:1px SOLID;border-radius: 10px ;margin-top:2px; margin-left:1px; background-color:#FFFFC6;width: 99%;display:none" id="cpdv2Tbl">
		<tr>
			<td class="">
				<table id="cpdv2FlexGrid"></table>
			</td>
		</tr>		
	</table>
	
	<div id="dialog-viewDetails" title="View CPD Reference Data">
		<table width="100%">
			<tr>
				<td id="sub_heading_new" colspan='2'>Kết quả kiểm tra CPD </td>
			</tr>
			<tr>
				<td width='30%' class="subjectsmall">CMND/CCCD</td>
				<td class='description' id="ninid"><span></span></td>
			</tr>
			<tr>
				<td class="subjectsmall">Tên</td>
				<td class='description' id="firstnameid"><span></span></td>
			</tr>
			<tr>
				<td class="subjectsmall">Họ</td>
				<td class='description' id="surnameid"><span></span></td>
			</tr>
			<tr>
				<td class="subjectsmall">Tên đệm</td>
				<td class='description' id="surnameatbirthid"><span></span></td>
			</tr>
			<tr>
				<td class="subjectsmall">Ngày sinh</td>
				<td id="dobid" class='description'><span></span></td>
			</tr>
			<tr>
				<td class="subjectsmall">Giới tính</td>
				<td class='description' id="genderid"><span></span></td>
			</tr>
			<tr>
				<td class="subjectsmall">Tình trạng hôn nhân</td>
				<td class='description' id="maritalstatusid"><span></span></td>
			</tr>
			<tr>
				<td width="40%" class="subjectsmall">Địa chỉ</td>
				<td class='description' id="address1id"><span></span></td>
			</tr>
			<tr>
				<td class="subjectsmall">Address 2</td>
				<td class='description' id="address2id"><span></span></td>
			</tr>
			<tr>
				<td class="subjectsmall">Address 3</td>
				<td class='description' id="address3id"><span></span></td>
			</tr>
			<tr>
				<td class="subjectsmall">Address 4</td>
				<td class='description' id="address4id"><span></span></td>
			</tr>
			<tr>
				<td class="subjectsmall">Tên bố</td>
				<td class='description' id="fathernameid"><span></span></td>
			</tr>
			<tr>
				<td class="subjectsmall">Father Surname</td>
				<td class='description' id="fathersurnameid"><span></span></td>
			</tr>
			<tr>
				<td class="subjectsmall">Mother Name</td>
				<td class='description' id="mothernameid"><span></span></td>
			</tr>
			<tr>
				<td class="subjectsmall">Mother Surname</td>
				<td class='description' id="mothersurnameid"><span></span></td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
		</table>
	</div>
	
	<div id="msgDialog" title="Alert">
		<div class="isa_info" align="center">
			<span style="font-size: 40" id="msgDialogSpan"></span>  
		</div>
	</div>
	
	
<script type="text/javascript">

	$("#cpdv2FlexGrid").flexigrid({
		//url : "${url}",
		dataType : 'json',
		colModel : [
			{display : 'NIN', name : 'nin', width : 135, align : 'center', sortable : true },
			{display : 'Name', name : 'firstname', width : 95, align : 'center', sortable : true },  
			{display : 'Surname', name : 'surname', width : 100, align : 'center', hide : false },
			{display : 'Surname at Birth', name : 'surnameBirth', width : 120, sortable : false, align : 'center' }, 
			{display : 'Sex', name : 'gender', width : 88, sortable : false, align : 'center' }, 
			{display : 'Birth Date', name : 'dob', width : 95, sortable : false, align : 'center' },
			{display : 'Address', name : 'address1', width : 125, align : 'center' }, 
			{display : 'Marital Status', name : 'maritalStatus', width : 90, align : 'center' }, 
			{display : 'Father Name', name : 'fatherName', width : 100, align : 'center'},  
			{display : 'Mother Name', name : 'motherName', width : 100, align : 'center'}, 
			{display : '', name : 'nin', width : 50, align : 'center', render : renderView}
			
			],
		//searchitems : [ 
		   //	{display : 'Transaction Id', name : 'transactionId'} ],
		sortname : "cardControlNo",
		sortorder : "asc",
		title : 'CPD Reference Data Search',
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
			$("#msgDialogSpan").html("Vui lòng nhập một hoặc nhiều tiêu chí tìm kiếm");
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
                	$("#msgDialogSpan").html("Tìm kiếm tìm thấy quá nhiều kết quả trùng khớp, vui lòng nhập vào một hoặc nhiều tiêu chí tìm kiếm");
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

<%}catch(Exception e){e.printStackTrace();} %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url var="submitUrl" value="/servlet/reportmgmt/reportGeneration" />
<c:url var="cancelUrl" value="/servlet/reportmgmt/generation" />
<c:url var="url" value="/servlet/reportmgmt" />
<c:url var="downloadRpt" value="/servlet/reportmgmt/staticParamRep" />
<c:url var="staticpdf" value="/servlet/reportmgmt/staticpdf" />
<c:url var="getCustomTableData" value="/servlet/reportmgmt/getCustomTableData" />

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
	height: 200px;
}
*/
</style>
<script>

		$(document).ready(function() {
			$("#showHtmlRpt").hide();
			$(".dateFieldClass").datepicker(
			{
				showOn : "button",
				buttonImage : "<c:url value="/resources/images/calendar.gif" />",
				buttonImageOnly : true,
				changeMonth : true,
				changeYear : true,
				 showSecond : true,
				controlType : 'select',
				/* timeFormat : 'hh:mm:ss', */ 
				dateFormat : 'dd/mm/yy',
				yearRange:"-100:+10"
			});
		});
		
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
		$(function() {
			$("#msgDialog").dialog({
				width : 400,
				height : 150,
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
			

	$("#genBtnId").click(function() {
		 var category= $("#reportCategory").val();
		 var rptFrmt= $("#rptFrmt").val();
			if(category=='CR') {
				if(rptFrmt != 'HTML') {
				 var jsonForm = JSON.stringify($('#reportMenuForm').serializeObject());							 
				 var url = '${staticpdf}'+'?jsonObj='+jsonForm;
				oIFrm = document.getElementById('reportIFrame'); 
		        oIFrm.src = url; 
				} else {
					$('.modal').show();
					var jsonForm = JSON.stringify($('#reportMenuForm').serializeObject());		
					var test = '${getCustomTableData}'+'?jsonObj='+jsonForm;
					$.ajax({
				   	    type : "GET",
				   	    url : test,
				   	    success : function(data) {
					   	 	if(data!='-1'){
				    			$('#searchResult').html(data);
				    			hideLoadImg();
					    	}else{
					    		$('#searchResult').html('Xảy ra lỗi trong khi tạo báo cáo, vui lòng kiểm tra nhật ký để biết thêm chi tiết.');
					    		hideLoadImg();
					    	}
				   	 	},
				 		error : function(e) {
				 			$('#searchResult').html('Xảy ra lỗi trong khi tạo báo cáo, vui lòng kiểm tra nhật ký để biết thêm chi tiết.');
				 			hideLoadImg();
						}
					});
				}
			} else {
				var mandateFieldArr = new Array();
				$.each($("input[data-mandatory='Y']"),function(index,val){
					if(val.value == ''){
						mandateFieldArr.push(val.id+" là bắt buộc");
					}
				});
				$.each($("select[data-mandatory='Y']"),function(index,val){
					if(val.value == 'All'){
						mandateFieldArr.push(val.id+" là bắt buộc");
					}
				});
				if(mandateFieldArr.length > 0){
					alert(mandateFieldArr.join("\n"));
					return false;
				}      
							
				 var jsonForm = JSON.stringify($('#reportMenuForm').serializeObject());							 
				 window.location = '${downloadRpt}'+'?jsonObj='+jsonForm;	
			}
		});
	
		$("#resetBtn").click(function() {
			var id =$("#reportId").val()+","+$("#reportFileName").val()+","+$("#reportCategory").val();
			window.location.href="${url}/reportGeneration/" + id;					 
		});		
		
		$("#cancelBtn").click(function(){	
			 document.forms["reportMenuForm"].action = '${cancelUrl}/' + $("#reportCategory").val();
			 document.forms["reportMenuForm"].submit();	
		});
	});
		
	function hideLoadImg(){
		 $('.modal').hide();
	}
	
	function load() {
		var oIFrm = document.getElementById('reportIFrame'); 
		if($('#reportIFrame').contents().find('body').html() == '-1'){		
			alert('Xảy ra lỗi trong khi tạo báo cáo, vui lòng kiểm tra nhật ký để biết thêm chi tiết.');
		}else if($('#reportIFrame').contents().find('body').html() == '0'){
			alert('Xảy ra lỗi trong khi tạo báo cáo, vui lòng kiểm tra nhật ký để biết thêm chi tiết.');
		}
	}
</script>
<form:form method="GET" modelAttribute="reportMenuForm" id="reportMenuForm" enctype="multipart/form-data" cssClass="inline">
	<div id="content_main">
		<div class="container">
          <div class="row">
               <div class="roundedBorder ov_hidden">
		<!-- Content Start -->
		<!-- <div id="content"> -->
		<div id="heading_report" align="justify" colspan='2' style='padding: 2px'>Tiêu chí tìm kiếm</div>
		<br />
		<br />
		<br />
		<c:if test="${not empty requestScope.Errors}">
			<div
				style="color: Red; background-color: White; border-style: solid; border-color: Red; border-width: thin;">
				<c:forEach items="${requestScope.Errors}" var="takla">
					<li style="height: 40px; padding-top: 20px; font-size: 16px;">
						${takla}</li>
				</c:forEach>

			</div>
		</c:if>
		<div>
			<table id="tablepaging" 
				style="width: 100%; background-color: #E3F1FE" class="data_table">
				<tr>
					<td valign="top">
						<table id="intputMenu" style="width: 100%;">
							<tr>
								<td class="subjectsmall" style="width: 15%;">Mô tả báo cáo</td>
								<td align="left" width="2px">:</td>
								<td class='description_slimplain' align="left"><c:out
										value="${reportMenuForm.reportDesc }"></c:out></td>
							</tr>
							<tr>
								<td class="subjectsmall">Định dạng báo cáo</td>
								<td align="left" width="2">:</td>
								<td class='description_slimplain' align="left"><form:select
										id="rptFrmt" path="rptFrmt">
										<form:option value="pdf">PDF</form:option>
										<c:if test="${reportMenuForm.reportCategory eq 'CR'}">
											<form:option value="HTML">Bảng</form:option>
										</c:if>
										<form:option value="xls">Excel</form:option>
										<form:option value="csv">CSV</form:option>
									</form:select></td>
							</tr>
							<tr>
								<td colspan="3">&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<form:input type="hidden" id="reportId" path="reportId" />
			<form:input type="hidden" id="reportFileName" path="reportFileName" />
			<form:input type="hidden" id="reportCategory" path="reportCategory" />
		</div>
		<div align="right" class="displayTag"
			style="background-image:url('<%=request.getContextPath() %>/resources/images/head.png');background-repeat: repeat-x;">
			<table>
				<tr>
					<td colspan="5" align="center" style="padding: 20px;">
						<input type="button" class="button_grey" id="genBtnId"  value="Generate" />
						<input type="button" class="button_grey" id="resetBtn"  value="Reset" align="center" /> 
						<input type="button" class="button_grey" id="cancelBtn" value="Cancel" align="center" />
					</td>
				</tr>
			</table>
		</div>
		<div id="searchResult"></div>
	</div>
	</div>
	</div>
	</div>
	<!-- </div> -->
	<div class="modal">
		<!-- Place at bottom of page -->
	</div>

</form:form>
<iframe id="reportIFrame" src="" onload="load()" style="display: none;">
</iframe>
<div id="msgDialog" title="Alert">
	<div class="isa_info" align="center">
		<span style="font-size: 40"></span>
	</div>
	<div class="error_msg"></div>
</div>
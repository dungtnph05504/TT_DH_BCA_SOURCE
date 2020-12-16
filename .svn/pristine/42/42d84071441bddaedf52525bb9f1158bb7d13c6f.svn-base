<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<c:url var="auditSearchUrl" value="/servlet/nicAuditEnquiry/auditSearchList" />
<c:url var="auditEnquiryAccessLogUrl"
	value="/servlet/nicAuditEnquiry/auditEnquiryAccessLog" />

<style>
.cls-mg-bot {
	margin-top: 10px;
}
.displayTag tr.odd {
	height: 35px;
}
.displayTag tr.even {
	height: 35px;
}
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
 
<form:form modelAttribute="nicEnqForm" id="searchJobsForm"
	action="" method="post">
	<div class="form-desing">
		<div>
			<div>
				<div class="row">
			<div class="ov_hidden">
			<div class="new-heading-2">NHẬT KÝ HỆ THỐNG</div>			
			<div style="border: solid 1px #cccc; border-radius: 4px; margin: 2px;min-width:100%;float:left">
			<div class="col-md-12 col-sm-12">
				<div class="col-md-4 col-sm-4">
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Tên người dùng:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:input path="userId" id="userId" cssClass="defaultText" size="30" maxlength="30" style="width: 100%;"/>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Máy trạm:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:input path="wkstnId" id="wkstnId" cssClass="defaultText" size="30" maxlength="30" style="width: 100%;"/>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Chức năng:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:input path="functionName" id="functionName" cssClass="defaultText" size="30" maxlength="30" style="width: 100%;"/>
									</div>
								</div>
								<div class="col-md-4 col-sm-4">									
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Kiểm định từ ngày:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot" style="position: relative;">
										<form:input type="text" id="dateFrom" name="dateFrom" path="auditDateFrom" cssClass="defaultText" size="12" maxlength="12" readonly="true" style="width: 100%;margin-right: -20px;"/>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Kiểm định đến ngày:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot" style="position: relative;">
										<form:input type="text" id="dateTo" name="dateTo" path="auditDateTo" cssClass="defaultText" size="12" maxlength="12" readonly="true" style="width: 100%;margin-right: -20px;"/>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Trạng thái:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:select style="width: 100%;" path="errorFlag" id="errorFlag" name="errorFlag" >
									      	<form:option value="">-- Chọn --</form:option>    
									        <form:option value="N">Thành công</form:option> 
									        <form:option value="Y">Lỗi</form:option> 
									     </form:select>
									</div>
								</div>	
								<div class="col-md-4 col-sm-4" style="padding-top: 75px;margin-bottom: 10px;">
									<button type="button" id="search_btn_data" class="btn btn-success">
										<span class="glyphicon glyphicon-search"></span> Tìm kiếm
									</button>
									<button type="button" id="cancel_search_btn_data" onclick="window.location='${auditEnquiryAccessLogUrl}'" class="btn btn-success">
										<span class="glyphicon glyphicon-refresh"></span> Hủy tìm kiếm
									</button>
								</div>		
			</div>
			<!--<div class="col-md-12" style="text-align: center;margin-top: 20px;margin-bottom: 20px;">
				<button type="button" id="search_btn_data" class="btn_small btn-primary btn-search">
					<span class="glyphicon glyphicon-search"></span> Tìm kiếm
				</button>
				<button type="button" id="reset_btn" class="btn_small btn-primary btn-search">
					<span class="glyphicon glyphicon-refresh"></span> Làm mới
				</button>
			</div>-->
			
			</div>
			<div>
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="max-width: 150px;">Ngày yêu cầu
								
								      </th>	
								      <th class="th-sm" style="min-width: 150px;">Chức năng
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Mã máy trạm
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Tên người dùng
								
								      </th>
								      <th class="th-sm" style="min-width: 150px;">Mã máy chủ
								
								      </th>
								      <th class="th-sm" style="min-width: 150px;">Mã phiên
								
								      </th>
								      <th class="th-sm" style="min-width: 100px;">Trạng thái
								
								      </th>
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>							    									      
									      <td>${item.auditDateStr}</td>
									      <td>${item.functionName}</td>
									      <td>${item.wkstnId}</td>	
									      <td>${item.userId}</td>
									      <td>${item.serverId}</td>
									      <td>${item.sessionId}</td>
									      <td>${item.errorFlag}</td>							     
									    </tr>
								    </c:forEach>
								  </tbody>
								</table>
								<div class="col-md-5 col-sm-12" style="padding-left: 0px;">
									<div class="dataTables_info">Hiển thị ${startIndex} đến ${endIndex} của ${totalRecord} kết quả</div>
								</div>
								<div class="col-md-7 col-sm-12" style="padding-right: 0px;">
									<ul style="float: right;" class="pagination" id="pagination"></ul>
								</div>			 
								<input type="hidden" name="pageNo" id="pageNo" />
						      </div>
			<!--<div class="col-md-12">
				<div id="searchResult">
					<table id="searchResultFlexGrid"></table>
				</div>
			</div>-->
		</div>
	</div>
	</div>
	</div>
	</div>
	<input type="hidden" id="jid" name="jid" />
	<input type="hidden" id="txnId" name="txnId" />
	<input type="hidden" id="searchUrl" name="searchUrl"
		value="${auditSearchUrl}" />

</form:form>

<div id="msgDialog" title="Cảnh báo">
	<div class="isa_info" align="center">
		<span style="font-size: 40" id="msgDialogSpan"></span>
</div> 
</div>
<div id="dialog-approve"></div>

<div class="modal">
		<!-- Place at bottom of page -->
</div>

<script type="text/javascript">
var reload="0";
$(function() { 
	//$('#dtBasicExample').DataTable();
	//$('.dataTables_length').addClass('bs-select');
	var totalPages = ${totalPage};
	var currentPage = ${pageNo};
	var pageSize = ${pageSize};
	window.pagObj = $('#pagination').twbsPagination({
			totalPages: totalPages,
			visiblePages: pageSize,
			startPage: currentPage,
			next: '<span class=\"glyphicon glyphicon-forward fix-paging\"></span>',
			prev: '<span class=\"glyphicon glyphicon-backward fix-paging\"></span>',
			last: '<span class=\"glyphicon glyphicon-step-forward fix-paging\"></span>',
			first: '<span class=\"glyphicon glyphicon-step-backward fix-paging\"></span>',
			onPageClick: function (event, page) {
				if (currentPage != page) {
					$('#pageNo').val(page);
					document.forms["searchJobsForm"].action = '${auditSearchUrl}';  
					document.forms["searchJobsForm"].submit();  
				}
			}
		});	
	$(document).on("click", "#search_btn", 
	  function(){  
		//transQueryList(); 
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
			"Đóng" : function() {
					$(this).dialog("close");
				 }
		}
	});

	$("#reset_btn").click(function() {  
		$("#searchResult").hide(); 
		$("#userId").val("");
		$("#wkstnId").val("");
		$("#functionName").val("");
		$("#dateFrom").val("");
		$("#dateTo").val("");
		$("#searchResultFlexGrid").empty();
		$("#searchResultFlexGrid").hide();
	}); 

});

function validSearch(){
	if($("#userId").val() == "" && $("#wkstnId").val()  == "" && $("#functionName").val()=="" 
		&&	$("#startDate").val()  == "" && $("#endDate").val() == ""){  
		return false
	} 
	return true;
}

$('#search_btn_data').click(function(){
	//auditEnquiryList();
	var url = '${auditSearchUrl}';
    //var orig = $("#searchJobsForm").serialize();
    //var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
    //var test = url + '?' + withoutEmpties;  
    document.forms["searchJobsForm"].action = url;  
	document.forms["searchJobsForm"].submit();
	/*$.ajax({
		url : test,
		cache: false,
		type: "POST",
		success : function(data) {
			if(data.length > 0){
				var tb = $('#dtBasicExample').DataTable();	
				tb.clear();
	        	for(var i = 0; data.length; i++){
	        		tb.row.add([
	        		             data[i].auditDateStr,
	        		             data[i].functionName,
	        		             data[i].wkstnId,
	        		             data[i].userId,
	        		             data[i].serverId,
	        		             data[i].sessionId,
	        		             data[i].errorFlag
	        		]).draw(false);
	        	}
				
			}else{				
				$.alert({
					title: 'Thông báo',
					content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + ' Không tìm thấy dữ liệu',
					 buttons: {   
	        		        "Đóng": function () {}
	        		    }
				});
			}
		}
	});*/
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

//$('#dateFrom').datepicker().datepicker('setDate',new Date());
//$('#dateTo').datepicker().datepicker('setDate',new Date());



//Audit Enquiry Flexigrid

 /*function auditEnquiryList() { 
   //$('.modal').show();
   var url = '${auditSearchUrl}';
   var orig = $("#searchJobsForm").serialize();
   var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
   var test = url + '?' + withoutEmpties;  
   if(reload=="0"){
    reload="1";
    $("#searchResultFlexGrid").flexigrid({ 
     url:test,
     dataType : 'json',
     colModel : [
 				{display : 'Ngày yêu cầu', name : 'auditDateStr', width : 120, sortable : true, align : 'left'}, 
 				{display : 'Chức năng', name : 'functionName', width : 140, align : 'left' },
 				{display : 'Mã máy trạm', name : 'wkstnId', width : 160, sortable : true, align : 'left' }, 
 				{display : 'Tên người dùng', name : 'userId', width : 140, sortable : true, align : 'left' }, 
 				{display : 'Mã máy chủ', name : 'serverId', width : 160, sortable : true, align : 'left' },
 				{display : 'Mã phiên', name : 'sessionId', width : 280, sortable : true, align : 'left' },
 				{display : 'Trạng thái', name : 'errorFlag', width : 140, sortable : true, align : 'left', render: renderStatus },
 				{display : '', name : 'fixBootstrap', width : 10, sortable : true, align : 'left'}
 			], 
     title : 'Kiểm tra yêu cầu - Nhật ký truy cập',
     usepager : true,
     sortname : 'auditDate',
     sortorder : 'desc',
     useRp : true,
     showTableToggleBtn : true,   
     height : 280,
     singleSelect : true,
     nowrap : false,
     resizable : false
    });
   }else{
    $("#searchResultFlexGrid").flexOptions({ 
     url:test, newp:1 
    }).flexReload()
   }
   
  }*/

 
  
//});

function renderStatus(content, currentRow){
  if(content=='N'){
   return "<font color='green'><b>Thành công</b></font>";
  }
  else{
   return "<font color='red'><b><b>Lỗi</b></font>";
  }
 }

</script>
	
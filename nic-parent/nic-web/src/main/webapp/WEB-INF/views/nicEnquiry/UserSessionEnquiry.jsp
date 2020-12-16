<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<c:url var="userSessionEnquiryUrl" value="/servlet/userSessionEnquiry/userSessEnquirySearchList" />

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
.cls-mg-bot {
	margin-top: 10px;
}
.displayTag tr.odd {
	height: 35px;
}
.displayTag tr.even {
	height: 35px;
}
 </style>
 


<form:form modelAttribute="userSessionEnquiryForm" id="userSessionEnqForm"
	action="" method="post">
	<div class="form-desing">
	<div>
                        <div class="row">
                            <div class="ov_hidden">
		<div>

			<div class="new-heading-2">NHẬT KÝ HỆ NGƯỜI DÙNG</div>
				<div style="border: solid 1px #cccc; border-radius: 4px; margin: 2px;float:left">
				<div class="col-md-12 col-sm-12" >
				<div class="col-md-4 col-sm-4">
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Tên người dùng:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:input path="userId" id="userId" cssClass="defaultText" size="30" maxlength="30" style="width: 100%;"/>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Đăng nhập từ ngày:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot" style="position: relative;">
										<form:input type="text" id="logindateFrom" name="logindateFrom" path="loginDateFrom" cssClass="defaultText" size="12" maxlength="12" readonly="true" style="width: 100%;margin-right: -20px;"/>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Đăng xuất từ ngày:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot" style="position: relative;">
										<form:input type="text" id="dateFrom" name="dateFrom" path="logoutDateFrom" cssClass="defaultText" size="12" maxlength="12" readonly="true" style="width: 100%;margin-right: -20px;"/>
									</div>
								</div>
								<div class="col-md-4 col-sm-4">									
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Máy trạm:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot" >
										<form:input path="wkstnId" id="wkstnId" cssClass="defaultText" size="30" maxlength="30" style="width: 100%;"/>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Đăng nhập đến ngày:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot" style="position: relative;">
										<form:input type="text" id="logindateTo" name="logindateTo" path="loginDateTo" cssClass="defaultText" size="12" maxlength="12" readonly="true" style="width: 100%;margin-right: -20px;"/>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Đăng xuất đến ngày:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot" style="position: relative;">
										<form:input type="text" id="dateTo" name="dateTo" path="logoutDateTo" cssClass="defaultText" size="12" maxlength="12" readonly="true" style="width: 100%;margin-right: -20px;"/>
									</div>
								</div>	
								<div class="col-md-4 col-sm-4" style="padding-top: 75px;margin-bottom: 10px;">
									<button type="button" id="search_btn_data" class="btn_small btn-primary btn-search">
										<span class="glyphicon glyphicon-search"></span> Tìm kiếm
									</button>												
								</div>		
			</div>
			
			</div>
						<div>
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="max-width: 150px;">Tên người dùng
								
								      </th>	
								      <th class="th-sm" style="min-width: 150px;">Mã máy trạm
								
								      </th>
								      <th class="th-sm" style="min-width: 150px;">Mã phiên
								
								      </th>
								      <th class="th-sm" style="min-width: 150px;">Ngày đăng nhập
								
								      </th>
								      <th class="th-sm" style="min-width: 150px;">Ngày đăng xuất
								
								      </th>								     
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>							    									      
									      <td>${item.userId}</td>
									      <td>${item.wkstnId}</td>
									      <td>${item.sessionId}</td>	
									      <td>${item.loginDateFrom}</td>
									      <td>${item.logoutDateTo}</td>							     
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
	<input type="hidden" id="jid" name="jid">
	<input type="hidden" id="txnId" name="txnId">
	<input type="hidden" id="searchUrl" name="searchUrl"
		value="${userSessionEnquiryUrl}" />

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
					document.forms["userSessionEnqForm"].action = '${userSessionEnquiryUrl}';  
					document.forms["userSessionEnqForm"].submit();  
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
			Ok : function() {
					$(this).dialog("close");
				 }
		}
	});

	$("#resetAllBtn").click(function() {  
		$("#searchResult").hide(); 
		$("#nin").val("");
		$("#transNo").val("");
		$("#firstName").val("");
		$("#ccNo").val("");
		$("#surname").val("");
		$("#surnameBirth").val(""); 
		$("#gender").val(""); 
		$("#dateOfBirth").val("");  
		$("#jobEnquiryFlexGrid").empty();
		$("#jobEnquiryFlexGrid").hide();
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
	//userSessionEnquiryList();
	var url = '${userSessionEnquiryUrl}';
    var orig = $("#userSessionEnqForm").serialize();
    var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
    var test = url + '?' + withoutEmpties;   
    document.forms["userSessionEnqForm"].action = '${userSessionEnquiryUrl}';  
	document.forms["userSessionEnqForm"].submit(); 
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
	        		             data[i].userId,
	        		             data[i].wkstnId,
	        		             data[i].sessionId,
	        		             data[i].loginDateFrom,
	        		             data[i].logoutDateTo
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



$("#logindateFrom").datepicker({
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

$("#logindateTo").datepicker({
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
//$('#logindateTo').datepicker().datepicker('setDate',new Date());

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

//$('#dateTo').datepicker().datepicker('setDate',new Date());

$("#reset_btn").click(function() {  
	$("#exptnHandln").hide(); 
	$("#nicTransaction.regSiteCode").val("");
	$("#officerID").val("");
	$("#dateFrom").val("");
	$("#dateTo").val(""); 
	$("#fpRangeFrom").val("");
	$("#fpRangeTo").val(""); 
	$("#searchData").hide();
}); 


//User Session Enquiry Flexigrid

 /*function userSessionEnquiryList() { 

   var url = '${userSessionEnquiryUrl}';
   var orig = $("#userSessionEnqForm").serialize();
   var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
   var test = url + '?' + withoutEmpties;  
   //alert(test);
   if(reload=="0"){
    reload="1";
    $("#searchResultFlexGrid").flexigrid({ 
     url:test,
     dataType : 'json',
     colModel : [
				{display : 'Tên người dùng', name : 'userId', width : 200, sortable : false, align : 'left' },
				{display : 'Mã máy trạm', name : 'wkstnId', width : 250, sortable : false, align : 'left' },
 				{display : 'Mã phiên', name : 'sessionId', width : 300, sortable : false, align : 'left' }, 
 				{display : 'Ngày đăng nhập', name : 'loginDateFrom', width : 200, sortable : true, align : 'left'}, 
 				{display : 'Ngày đăng xuất', name : 'logoutDateTo', width : 200, align : 'left' },
 				{display : '', name : 'fixBootstrap', width : 10, align : 'left' },
 				{display : '', name : 'fixBootstrap', width : 10, align : 'left' },
 				{display : '', name : 'fixBootstrap', width : 10, align : 'left' }
 			], 
     title : 'Truy vấn người dùng',
     usepager : false,
     useRp : false,
     showTableToggleBtn : true,   
     height : 280,
     singleSelect : true,
     nowrap : false,
     resizable : false,
     sortedElement : 'loginDate'
    });
   }else{
    $("#searchResultFlexGrid").flexOptions({ 
     url:test, newp:1 
    }).flexReload()
   }
   
  }*/
  
//});


</script>
	
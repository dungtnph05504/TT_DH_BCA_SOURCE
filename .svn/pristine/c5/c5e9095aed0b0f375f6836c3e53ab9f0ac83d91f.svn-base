<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 

<c:url var="resetPage" value="/servlet/decisionController/addDecisionManager" />
<c:url var="businessListUrl" value="/servlet/decisionController/addBusinessList" />
<c:url var="updateData" value="/servlet/decisionController/updateBusinessList" />
<c:url var="findData" value="/servlet/decisionController/findBusinessList" />
<c:url var="listDecision" value="/servlet/decisionController/decisionManagerList" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script> -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>NIC Quyết định người cử đi công tác</title>
</head>

<script type="text/javascript">
var imgBase = "";
var stringUrl = "";

function readURL(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.onload = function (e) {
			imgBase = e.target.result;
		};
		reader.readAsDataURL(input.files[0]);
	}
}
      $(function() {
    	  //$('#dtBasicExample').DataTable();
    	 // $('.dataTables_length').addClass('bs-select');
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
					document.forms["form"].action = '${businessListUrl}/${decisionNo}';  
					document.forms["form"].submit();  
				}
			}
		});	
    	  $("#addForm").dialog({
    		     autoOpen: false,
    		     modal: true,
    		     width : 800,
    		     resizable: true
    		});

		  $("#savedialog-confirm" ).dialog({
				modal: true,
			      autoOpen: false,
				  width : 500,
				  resizable: true,
			      show: {
			        effect: "fade",
			        duration: 200
			      },
			      hide: {
			        //effect: "explode",
			        //duration: 1000
			      },
				   buttons:{
			    Ok: function() {    	
			    	$(this).dialog("close");
			    	  /* document.forms["businessListForm"].action = '${businessListUrl}' + '/' + ${decisionNo};
			    	  document.forms["businessListForm"].submit(); */
			    	  window.location.reload();
			    }
			   }
		});
		 
		 $( "#faildialog-confirm" ).dialog({
				modal: true,
			      autoOpen: false,
				  width : 500,
				  resizable: true,
			      show: {
			        effect: "fade",
			        duration: 200
			      },
			      hide: {
			        //effect: "explode",
			        //duration: 1000
			      },
				   buttons:{
			    Ok: function() {    	
			    	$(this).dialog("close");
			    }
			   }
			    });
		 
		$('#add_btn').click(function() {
			 if(checkValid()){
			  stringUrl = $('#businessListForm').serialize(); /* + "&docString="+ encodeURIComponent(base64ImageContent) */;

				  
				  $.post('<c:url value="/servlet/decisionController/createBusinessList" />',stringUrl,
				             function(data){
					  if(data=='success'){
					   	   $("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
					       $("#savedialog-confirm").html('Đã tạo thành công Người cử đi công tác theo số quyết định: '+ $("#decisionNumber").val());
					       $("#savedialog-confirm").dialog( 'open' );
					    }else if(data=='duplicate'){
					       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
					       $("#faildialog-confirm").html('Người cử đi công tác theo số quyết định: '+$("#decisionNumber").val() + ' và thứ tự: ' + $("#serial").val() +' đã tồn tại.');
					       $("#faildialog-confirm").dialog( 'open' );
					    }
					    else {
						   $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
						   $("#faildialog-confirm").html('Không thể tạo Người cử đi công tác: '+$("#decisionNumber").val() + ' và thứ tự: ' + $("#serial").val());
						   $("#faildialog-confirm").dialog( 'open' );
						}
			  		});
				}  
			 	else {
					   $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#faildialog-confirm").html('Nhập đầy đủ thông tin có dấu (*)');
				       $("#faildialog-confirm").dialog( 'open' );
				}
		});	 
		
		$('#update_btn').click(function() {
			  //var base64ImageContent = imgBase.replace(/^data:image\/(png|jpg|jpeg);base64,/, "");
			 if(checkValid()){
			      stringUrl = $('#businessListForm').serialize(); /* + "&docString="+ encodeURIComponent(base64ImageContent) */;

				  $.post('<c:url value="/servlet/decisionController/updateBusinessList" />',stringUrl,
				             function(data){
					  if(data=='success'){
					   	   $("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
					       $("#savedialog-confirm").html('Đã sửa thành công thông tin Người cử đi công tác theo số thứ tự '+ $("#serial").val());
					       $("#savedialog-confirm").dialog( 'open' );
					    }else if(data=='duplicate'){
					       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
					       $("#faildialog-confirm").html('Người cử đi công tác theo số quyết định: '+$("#decisionNumber").val() + ' và thứ tự: ' + $("#serial").val() +' đã tồn tại.');
					       $("#faildialog-confirm").dialog( 'open' );
					    }
					    else {
						   $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
						   $("#faildialog-confirm").html('Không thể sửa thông tin Người cử đi công tác theo số thứ tự: ' + $("#serial").val());
						   $("#faildialog-confirm").dialog( 'open' );
						}
			  		});
			 }
			 else {
				   $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
			       $("#faildialog-confirm").html('Nhập đầy đủ thông tin có dấu (*)');
			       $("#faildialog-confirm").dialog( 'open' );
			  }
		});	 
	 
	  
	  	$("#cancelBtn").click(function(){	
			 /* document.forms["businessListForm"].action = '${businessListUrl}' + '/' + ${decisionNo};
			 document.forms["businessListForm"].submit(); */	
	  		window.location.reload();
		});
	  
	  	$("#resetBtn").click(function(){
			 /* document.forms["businessListForm"].action = '${resetPage}';
			 document.forms["businessListForm"].submit(); */
			 ClearPopup();
		});
	  
	  	$("#addP").click(function(){	
	  		ClearPopup();
			$("#addForm").css("display", "block");
			$("#resetBtn").css("display", "block");
			$("#update_btn").css("display", "none");
	  		$("#add_btn").css("display", "block");
			$("#addForm").dialog("open");
			$("#addForm").attr("title", "Tạo mới người được cử đi công tác");
			/* if($('#addForm').css('display') == 'none')
			{
			
			}
			else
			{
				$("#addForm").css("display", "none");
				$("#addP").val('Thêm người cử đi công tác');
			}
			 */
			//$("#addForm").toggle();
		});
  
	  	
		
		$("#backQD").click(function(){
			 document.forms[0].action = '${listDecision}';
			 document.forms[0].submit();	
	
		});
  });
      
  $(function() {
		/* $( "#dob" ).datepicker({
		showOn: "button",
		buttonImage: "/eppcentral/resources/images/icon_welcome/calendar.gif",
		buttonImageOnly: true,
		dateFormat: 'dd/mm/yy'
		}); */
		
		$("#dob").datepicker({         
			  autoclose: true,         
			  todayHighlight: true ,
			 dateFormat: 'dd/mm/yy'
		});
	});
  
  	function IsNumeric(sText) {
		var ValidChars = "0123456789.";
		var IsNumber=true;
		var Char;
		
		if(sText !="" && sText !=null){
		for (i = 0; i < sText.length && IsNumber == true; i++) 
		{ 
			Char = sText.charAt(i); 
			if (ValidChars.indexOf(Char) == -1) 
			{
			IsNumber = false;
			}
		}
	   }else{
		IsNumber = false;
	   }
		
	    return IsNumber;
	}	
  
  	function deletePerson(id, code){	
  		$.confirm({
		    title: 'Thông báo',
		    content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/tip_1a.png\">" + ' Có chắc muốn xóa bản ghi này?',
		    buttons: {
		        OK: function () {
		        	$.post('<c:url value="/servlet/decisionController/deleteBusinessList" />' + '/' + id + '/' + code ,code,
				             function(data){
				   if(data=='success'){
				   	   $("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#savedialog-confirm").html('Đã xóa thành công Người cử đi công tác theo số thứ tự: '+ id);
				       $("#savedialog-confirm").dialog( 'open' );
				    }else if(data=='duplicate'){
				       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#faildialog-confirm").html('Quyết định đã được phê duyệt/hủy cấp công hàm nên không thực hiện được thao tác này');
				       $("#faildialog-confirm").dialog( 'open' );
				    }
				    else {
					       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
					       $("#faildialog-confirm").html('Không thể xóa Người cử đi công tác theo số thứ tự: '+ id);
					       $("#faildialog-confirm").dialog( 'open' );
					}
		        	});
		        },
		        CANCEL: function () {
		        	//return false;
		        }			       
		    }
		})
  		/*if(!confirm('Có chắc muốn xóa bản ghi này?')){return false;}
		else
			{
				$.post('<c:url value="/servlet/decisionController/deleteBusinessList" />' + '/' + id + '/' + code ,code,
			             function(data){
			   if(data=='success'){
			   	   $("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
			       $("#savedialog-confirm").html('Đã xóa thành công Người cử đi công tác theo số thứ tự: '+ id);
			       $("#savedialog-confirm").dialog( 'open' );
			    }else if(data=='duplicate'){
			       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
			       $("#faildialog-confirm").html('Quyết định đã được phê duyệt/hủy cấp công hàm nên không thực hiện được thao tác này');
			       $("#faildialog-confirm").dialog( 'open' );
			    }
			    else {
				       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#faildialog-confirm").html('Không thể xóa Người cử đi công tác theo số thứ tự: '+ id);
				       $("#faildialog-confirm").dialog( 'open' );
				}
			 });
		}*/
	};
	
  	function ClearPopup(){
  		$("#serial").attr("readonly", false);
  		$('#serial').val('');
  		$('#fullname').val('');
  		$('#dob').val('');
  		$('#pob').val('');
  		$('#address').val('');
  		$('#agency').val('');
  		$('#position').val('');
  		$('#positionEng').val('');
  		$('#addressAgency').val('');
  		$('#phone').val('');
  		$('#type').val('');
  		$('#rank').val('');
  		$('#curb').val('');
  		$('#jaw').val('');
  		$('#description').val('');
  	}
  	
  	function editPerson(id, num){	
  		$("#addForm").attr("title", "Sửa thông tin người cử đi công tác");
  		$("#update_btn").css("display", "block");
  		$("#add_btn").css("display", "none");
  		$("#resetBtn").css("display", "none");
  		$.ajax({                                                                    
  		    type: "POST",
  		    url: "${findData}",
  		    data: {id : id, num : num},
  		    dataType:'json',
  		    success: function(response) {
  		    	if(response != null){
	  		    	$('#serial').val(response.serial);
	  		    	$('#serial').attr('readonly', true);
	  		  		$('#fullname').val(response.fullname);
	  		  		$('#dob').val(response.dateF);
	  		  		$('#pob').val(response.pob);
	  		  		$('#gender').val(response.gender);
	  		  		$('#address').val(response.address);
	  		  		$('#agency').val(response.agency);
	  		  		$('#position').val(response.position);
	  		  		$('#positionEng').val(response.positionEng);
	  		  		$('#addressAgency').val(response.addressAgency);
	  		  		$('#phone').val(response.phone);
	  		  		$('#type').val(response.type);
	  		  		$('#rank').val(response.rank);
	  		  		$('#curb').val(response.curb);
	  		  		$('#jaw').val(response.jaw);
	  		  		$('#description').val(response.description);
	  		  		$("#addForm").dialog("open");
  		    	}
  		    },
  		    error: function(e) {
  		        //alert('Error while saving filters: ' + e.message);
  		    	$.alert({
					title: 'Thông báo',
					content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/error_1a.png\">" + " Đã có lỗi xảy ra: " + e,
				});
  		    }
  		});
	}
  	
  	function ToJavaScriptDate(value) {
        var pattern = /Date\(([^)]+)\)/;
        var results = pattern.exec(value);
        //alert(results);
        $.alert({
			title: 'Thông báo',
			content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + results,
		});
        var dt = new Date(parseFloat(results[1]));
        return dt.getDate() + "/" + (dt.getMonth() + 1) + "/" + dt.getFullYear();
    }
  	
  	$(document).ready(function() {
	    $('.js-example-basic-multiple').select2();
	    $('.js-example-basic-single').select2();
	});
  	
  	function checkValid(){
  		var d1 =  $('#decisionNumber').val();
  		var d2 =  $('#serial').val();
  		var d3 =  $('#fullname').val();
  		var d4 =  $('#dob').val();
  		var d5 =  $('#pob').val();
  		var d6 =  $('#address').val();
  		var d7 =  $('#position').val();
  		
  		if(d1 != "" && d1 != null && d2 != "" && d2 != null && d3 != "" && d3 != null
  				&&d4 != "" && d5 != null && d6 != "" && d6 != null && d5 != "" && d5 != null 
  					&& d7 != "" && d7 != null) 
  		{
  			return true;
  		}
  		return false;
  	}
  	
  	var isShift = false;
  	var seperator = "/";
  	
  	$(document).ready(function () {
  	    //Reference the Table.
  	    var tblForm = document.getElementById("dob");

  	                //Set Max Length.
  	                tblForm.setAttribute("maxlength", 10);

  	                //Only allow Numeric Keys.
  	                tblForm.onkeydown = function (e) {
  	                    return IsNumeric(this, e.keyCode);
  	                };

  	                //Validate Date as User types.
  	                tblForm.onfocusout = function (e) {
  	                    ValidateDateFormat(this, e.keyCode);
  	                };
  	            
  	});
  	
  	function IsNumeric(input, keyCode) {
  	    if (keyCode == 16) {
  	        isShift = true;
  	    }
  	    //Allow only Numeric Keys.
  	    if (((keyCode >= 48 && keyCode <= 57) || keyCode == 8 || (keyCode <= 37) || (keyCode <= 39) || (keyCode >= 96 && keyCode <= 105)) && isShift == false) {
  	        if ((input.value.length == 2 || input.value.length == 5) && keyCode != 8) {
  	            input.value += seperator;
  	        }

  	        return true;
  	    }
  	    else {
  	        return false;
  	    }
  	};
  	
  	function ValidateDateFormat(input, keyCode) {
  	    var dateString = input.value;
  	    if (keyCode == 16) {
  	        isShift = false;
  	    }
  	    var regex = /(((0|1)[0-9]|2[0-9]|3[0-1])\/(0[1-9]|1[0-2])\/((19|20)\d\d))$/;

  	    //Check whether valid dd/MM/yyyy Date Format.
  	    if (regex.test(dateString) || dateString.length == 0) {
  	        //ShowHideError(input, "none");
  	    } else {
  	       // demo.showNotification('bottom', 'right', 2, "Định dạng nhập dữ liệu ngày tháng chưa chính xác (DD/MM/YYYY)");
  	    }
  	};
	</script>
<body>

<div class="form-desing">
		<div>
			<div class="row">
				<div class="ov_hidden">
				
					<div class="new-heading-2">DANH SÁCH NGƯỜI CỬ ĐI CÔNG TÁC</div>
					<div id="update_msg" style="display: none;">
						<!-- <img src="images/info1.jpg" width="30" height="30"> -->
						&nbsp;
						<span style="font-size: 14px; color: #0000FF">Người cử đi công tác đã được lưu thành công </span>
					</div>
					<!--********************customized code for now***************************************-->
					<div id="admin_console_info_div" style="display: table-cell; vertical-align: top;"></div>
					<br />
					<form:form commandName="listBusiness" id="form" name="form">
					<div>
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="max-width: 50px;">STT
								
								      </th>	
								      <th class="th-sm" style="min-width: 140px;">Số quyết định
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Thứ tự đi 
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Tên đầy đủ
								
								      </th>	
								      <th class="th-sm" style="min-width: 120px;">Ngày sinh
								
								      </th>	
								      <th class="th-sm" style="min-width: 140px;">Cơ quan chủ quản
								
								      </th>	
								      <th class="th-sm" style="min-width: 130px;">Chức vụ
								
								      </th>	
								      <th class="th-sm" style="min-width: 120px;">Ngày tạo
								
								      </th>	
								      <th class="th-sm" style="min-width: 60px;">
								
								      </th>							     
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>							    
									      <td><a href="#"  onclick="editPerson('${item.serial}','${item.decisionNumber}')"> <c:out value="${item.id}" /></a></a></td>							      
									      <td>${item.decisionNumber}</td>	
									      <td>${item.serial}</td>	
									      <td>${item.fullname}</td>	
									      <td>${item.dob}</td>	
									      <td>${item.agency}</td>	
									      <td>${item.position}</td>	
									      <td>${item.createDate}</td>	
									      	<td><button type="button" class="btn btn-danger"  onclick="deletePerson('${item.serial}','${item.decisionNumber}')" id="delete${item.id}">
													<span class="glyphicon glyphicon-remove"></span> Xóa
												</button></td>						     
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
				<!--<c:if test="${empty listBusiness}">
					Không bản ghi trong danh sách người được cử đi công tác.
					<br />
				</c:if>
				
				<c:if test="${not empty listBusiness}">
					<display:table cellspacing="1" cellpadding="0" id="row" sort="external"  partialList="true" size="${totalRecords}"
						export="false" class="displayTag table" name="listBusiness" defaultsort="1"
						defaultorder="ascending" pagesize="1000">
						<display:setProperty name="paging.banner.item_name" value='kết quả'></display:setProperty>
						<display:setProperty name="paging.banner.items_name" value='kết quả'></display:setProperty>
						<display:setProperty name="paging.banner.placement" value='bottom'></display:setProperty>
						<display:setProperty name="paging.banner.no_items_found" value='<span class="pagebanner"> Không tìm thấy dữ liệu. </span>'></display:setProperty>
						<display:setProperty name="paging.banner.one_item_found" value='<span class="pagebanner"> Tìm thấy 1 kết quả. </span>'></display:setProperty>
						<display:setProperty name="paging.banner.all_items_found" value='<span class="pagebanner"> {0} {1} tìm thấy, hiển thị tất cả {2}. </span>'></display:setProperty>
						<display:setProperty name="paging.banner.some_items_found" value='<span class="pagebanner"> {0} {1} tìm thấy, hiển thị từ {2} đến {3}. </span>'></display:setProperty>		
						<display:setProperty name="paging.banner.first" value='<span class="pagelinks"> [Đầu tiên/Trước] {0} [ <a href="{3}">Tiếp</a>/ <a href="{4}">Cuối cùng</a>] </span>'></display:setProperty>		
						<display:setProperty name="paging.banner.last" value='<span class="pagelinks">[ <a href="{1}">Đầu tiên</a>/ <a href="{2}">Trước</a>] {0} [Tiếp/Cuối cùng] </span>'></display:setProperty>								
						<display:setProperty name="paging.banner.full" value='	<span class="pagelinks"> [<a href="{1}">Đầu tiên</a>/ <a href="{2}">Trước</a>] {0} [ <a href="{3}">Tiếp</a>/ <a href="{4}">Cuối cùng </a>]</span>'></display:setProperty>												
						<display:column title="STT" sortable="false" sortProperty="id">
						<a href="#"  onclick="editPerson('${row.serial}','${row.decisionNumber}')"> <c:out value="${row.id}" /></a>
						</display:column>
						<display:column property="decisionNumber" sortable="false"
							title="Số quyết định" maxLength="30" >
						</display:column>
						<display:column property="serial" sortable="serial"
							title="Số thứ tự trong quyết định" maxLength="20" >
						</display:column>
						<display:column property="fullname" sortable="fullname"
							title="Tên đầy đủ" maxLength="20" >
						</display:column>
						<display:column property="dob" sortable="false"
							title="Ngày sinh" format="{0,date,dd-MMM-yyyy}">
						</display:column>
						<display:column property="agency" sortable="false"
							title="Tên cơ quan chủ quản" maxLength="30" >
						</display:column>
						<display:column property="position" sortable="false"
							title="Chức vụ" maxLength="100" >
						</display:column>
						<display:column property="createDate" title="Ngày tạo" 
							sortable="false" format="{0,date,dd-MMM-yyyy}" >
						</display:column>
						<display:column>
							<button type="button" class="btn btn-danger"  onclick="deletePerson('${row.serial}','${row.decisionNumber}')" id="delete${row.id}">
								<span class="glyphicon glyphicon-remove"></span> Xóa
							</button>						
						</display:column>
					</display:table>
					</c:if>-->
					</form:form>
					<div id="addForm" style="display:none" title="Tạo mới người được cử đi công tác">
					<form:form modelAttribute="businessListForm" commandName="businessListForm" id="businessListForm" name="businessListForm" class="inline">
						<div class="col-sm-6">
							<div class="form-group">
								<label for="decisionNumber">Số quyết định<i style="color: red">(*)</i>: </label>
								<form:input class="form-control" type="text" readonly="true" path="decisionNumber" id="decisionNumber" value="${decisionNo}" /> 
							</div>
							<div class="form-group">
								<label for="serial">Thứ tự được cử đi công tác<i style="color: red">(*)</i> :</label>
								<form:input class="form-control" type="text" path="serial" id="serial" /> 
							</div>
							<div class="form-group">
								<label for="fullname">Họ tên đầy đủ<i style="color: red">(*)</i>: </label>
								<form:input class="form-control" type="text" path="fullname" id="fullname" /> 
							</div>
							<div class="form-group">
								<label for="decisionNumber">Giới tính: </label>
								<form:radiobutton path="gender" id="gender" value="M" checked="true"/>Nam 
								<form:radiobutton path="gender" id="gender" value="F"/>Nữ 
							</div>
							<div class="form-group">
								<label for="dob">Ngày sinh<i style="color: red">(*)</i>: </label>
								<div id="datepicker" class="input-group date" data-date-format="dd-mm-yyyy"> 
									<form:input class="form-control" type="text" path="dob" id="dob" autocomplete="off" />
									<span class="input-group-addon">
										<!-- <img class="ui-datepicker-trigger" src="/eppcentral/resources/images/icon_welcome/calendar.gif" alt="..." title="..."> -->
										<i class="glyphicon glyphicon-calendar"></i>
									</span> 
								</div>
							</div>
							<div class="form-group">
								<label for="pob">Nơi sinh<i style="color: red">(*)</i>: </label>
								<form:select style="width: 100%;" path="pob" id="pob"
									class="js-example-basic-single">
									<form:option value="">-- SELECT -- </form:option>
									<form:options items="${pobddl}" itemLabel="codeValueDesc"
										itemValue="id.codeValue"></form:options>
								</form:select>
								<%-- <form:input class="form-control" type="text" path="pob" id="pob" />  --%>
							</div>
							<div class="form-group">
								<label for="address">Địa chỉ hiện tại<i style="color: red">(*)</i>: </label>
								<form:input class="form-control" type="text" path="address" id="address" /> 
							</div>
							<div class="form-group">
								<label for="agency">Chọn cơ quan công tác:</label>
								<form:input class="form-control" type="text" path="agency" id="agency" /> 
							</div>
							<div class="form-group">
								<label for="position">Chức vụ<i style="color: red">(*)</i>: </label>
								<form:input class="form-control" type="text" path="position" id="position" /> 
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label for="positionEng">Chức vụ (bằng Tiếng Anh): </label>
								<form:input class="form-control" type="text" path="positionEng" id="positionEng" />
							</div>
							<div class="form-group">
								<label for="addressAgency">Địa chỉ cơ quan: </label>
								<form:input class="form-control" type="text" path="addressAgency" id="addressAgency" />
							</div>
							<div class="form-group">
								<label for="phone">Số điện thoại: </label>
								<form:input class="form-control" type="text" path="phone" id="phone" />
							</div>
							<div class="form-group">
								<label for="type">Loại công chức/ viên chức: </label>
								<form:input class="form-control" type="text" path="type" id="type" />
							</div>
							<div class="form-group">
								<label for="rank">Bậc công chức/ viên chức: </label>
								<form:input class="form-control" type="text" path="rank" id="rank" />
							</div>
							<div class="form-group">
								<label for="curb">Ngạch công chức/ viên chức: </label>
								<form:input class="form-control" type="text" path="curb" id="curb" />
							</div>
							<div class="form-group">
								<label for="jaw">Bậc/ hàm (nếu là lực lượng vũ trang): </label>
								<form:input class="form-control" type="text" path="jaw" id="jaw" />
							</div>
							<div class="form-group">
								<label for="description">Mô tả thêm: </label>
								<form:textarea class="form-control" type="text"
									path="description" id="description" rows="5"></form:textarea>
							</div>
						</div>
						<form:input type="hidden" id="createBy" path="createBy"
							value="${sessionScope.userSession.userName}" />
						<button type="button" class="btn btn-primary" id="resetBtn" style="float: right;">
							<span class="glyphicon glyphicon-refresh"></span> Làm mới
						</button>
						<button type="button" class="btn btn-primary"  id="add_btn" style="float: right;margin-right: 10px;">
							<span class="glyphicon glyphicon glyphicon-save"></span> Lưu
						</button>
						<button type="button" class="btn btn-success" id="update_btn" style="float: right;"> 
							<span class="glyphicon glyphicon-ok"></span> Cập nhật
						</button>
					
					</form:form>
					</div>
				</div>
			</div>
		</div>
		<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
			<div style="margin: 10px"> 			
				 	<button type="button" class="btn btn-danger" id="backQD">
						<span class="glyphicon glyphicon-remove"></span> Quay lại quyết định
					</button>
					<button type="button" class="btn btn-success" id="addP">
						<span class="glyphicon glyphicon-plus-sign"></span> Thêm người cử đi công tác
					</button>
			</div>
		</div>
	</div>
	<div id="savedialog-confirm" style="display: none;"></div>
	<div id="faildialog-confirm" style="display: none;"></div>
</body>
</html>

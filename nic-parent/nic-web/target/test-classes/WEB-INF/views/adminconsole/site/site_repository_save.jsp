<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<c:url var="saveUrl" value="/servlet/siteMgmt/saveSiteRepo"/>
<c:url var="url" value="/servlet/siteMgmt/getSiteRepo"/>


<style>
#ajax-load-qa {
	background: rgba(255, 255, 255, .5)
		url('<c:url value="/resources/images/loading_nin.gif" />') 50%
		50% no-repeat;
	position: fixed;
	z-index: 100;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	display: none;
	text-align: center;
}
/* When the body has the loading class, we turn
   the scrollbar off with overflow:hidden */
body.loading {
	overflow: hidden;
}
.fix-bottom-1 {
	margin-top: 15px;
}
/* Anytime the body has the loading class, our
   modal element will be visible */

</style>
<!-- <div id="main"> -->
<div id="content_main">
<form:form  modelAttribute="siteRepoForm" id="siteRepoForm" name="siteRepoForm" >	
<div class="container">
        	<div class="row">
        		<div class="ov_hidden">
	<div class="new-heading-2">THÊM/CHỈNH SỬA TRUNG TÂM</div> 
	<div id="global_icon_div"></div>
	<fieldset>
		<legend>Thông tin trung tâm</legend>

		<div class="col-md-12">
			<div class="col-md-6">
				<div class="col-md-4 fix-bottom-1">	
					<label for="countryCode">Mã trung tâm<i style="color: red;padding: 5px;">*</i></label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<c:choose>
			   			 <c:when test='${not empty siteRepoForm.siteId}'>			   			 
			   			 	 <form:input  id="siteId" path="siteId" type="text" size='50' maxlength="5" disabled="true" class="form-control"/>
			   			 </c:when>
			   			 <c:otherwise>			   			 	
			   			 	 <form:input  id="siteId" path="siteId" type="text" size='50' maxlength="5" class="form-control"/>
			   			 </c:otherwise>
		   			</c:choose>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Tên trung tâm<i style="color: red;padding: 5px;">*</i></label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<form:input  id="siteName" path="siteName" type="text" size='50' class="form-control"/>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Nhóm trung tâm<i style="color: red;padding: 5px;">*</i></label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<form:select id="groupId" path="groupId" class="form-control">
		   	         	<form:option value=""  label="--- Chọn ---"/>
						<c:forEach var="maplist" items="${siteRepoForm.sysSiteGroupMap}" varStatus="status1">
							<form:option value="${maplist.key}" label="${maplist.value }"/>
						</c:forEach>
		   	        </form:select>
				</div>				
			 	 <div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Loại chức năng<i style="color: red;padding: 5px;">*</i></label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					 <form:select id="siteFunctionType" path="siteFunctionType" class="form-control">
		   	         	<form:option value=""  label="--- Chọn ---"/>
						<c:forEach var="maplist" items="${siteRepoForm.sysSiteFunctionTypeMap}" varStatus="status1">
							<form:option value="${maplist.key}" label="${maplist.value }"/>
						</c:forEach>
		   	        </form:select>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Quốc gia<i style="color: red;padding: 5px;">*</i></label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<form:select id="country" path="country" class="form-control">
		   	         	<form:option value=""  label="--- Chọn ---"/>
						<c:forEach var="maplist" items="${siteRepoForm.sysNationalityMap}" varStatus="status1">
							<form:option value="${maplist.key}" label="${maplist.value }"/>
						</c:forEach>
		   	        </form:select>
				</div>	
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Địa chỉ</label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<form:textarea id="address" path="address" type="text" rows="3" cols="50" class="form-control"/>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Mã bưu điện</label>
				</div>
				<div class="col-md-8 fix-bottom-1" style="height: 30px;">
					<form:input  id="zipCode" path="zipCode" type="text" size='50' class="form-control"/>
				</div>				
			 </div>
			 <div class="col-md-6">
				<div class="col-md-4 fix-bottom-1">
					
					<label for="countryCode">Thành phố</label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<form:input  id="city" path="city" type="text" size='50' class="form-control"/>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Khu vực</label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					 <form:input  id="region" path="region" type="text" size='50' class="form-control"/>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Số trung tâm</label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<form:input  id="siteNumber" path="siteNumber" type="text" size='5'  maxlength='5' class="form-control"/>
				</div>				
			 	 <div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Trạng thái</label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<form:select id="activeIndicator" path="activeIndicator" class="form-control">
						<form:option value="" label="--- Chọn ---"/>
						<form:option value="Y" label="Hoạt động"/>
						<form:option value="N" label="Không hoạt động"/>
	   	        	</form:select>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Thẩm quyền</label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<form:input  id="authority" path="authority" type="text" size='50' class="form-control"/>
				</div>	
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Tạo phiên bản</label>
				</div>
				<div class="col-md-8 fix-bottom-1" style="height: 30px;">
					<form:input  id="createVersion" path="createVersion" type="text" size='50' class="form-control"/>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Bản nâng cấp</label>
				</div>
				<div class="col-md-8 fix-bottom-1" style="height: 30px;">
					<form:input  id="updateVersion" path="updateVersion" type="text" size='50' class="form-control"/>
				</div>				
			 </div>
          </div>
          </fieldset>
        <div style="display: flex;flex: 0 auto;justify-content: center;">
			<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
				<div style="margin: 10px"> 			
					 <button type="button" class="btn btn-success" id="cancel_btn">
					<span class="glyphicon glyphicon-remove"></span> Hủy
					</button>
					<!--<button type="button" class="btn btn-success" id="reset_btn">
						<span class="glyphicon glyphicon-refresh"></span> Làm mới
					</button>-->
		           	<c:if test='${empty siteGroupForm.groupId}'>
		           		<button type="button" class="btn btn-success" id="save_btn">
							<span class="glyphicon glyphicon-ok"></span> Lưu
						</button>
		        	</c:if>
		        	<c:if test='${not empty siteGroupForm.groupId}'>
		        		<button type="button" class="btn btn-success" id="update_btn" onclick="updateForm()">
							<span class="glyphicon glyphicon-ok"></span> Cập nhật
						</button>
				    </c:if>
				</div>
			</div>
		 </div>
		 <div id="ajax-load-qa"></div>
   </div>
   </div>
   </div>
</form:form>

 </div>

<script>
$(document).ready(function () {
  //called when key is pressed in textbox
  /*$("#createVersion").keypress(function (e) {
     
     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
        //display error message
        $("#errmsg1").html("Vui lòng nhập giá trị số nguyên hợp lệ!").show().fadeOut("slow");
           return false;
    }
   });

  $("#updateVersion").keypress(function (e) {
	     //if the letter is not digit then display error and don't type anything
	     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
	        //display error message
	        $("#errmsg2").html("Vui lòng nhập giá trị số nguyên hợp lệ!").show().fadeOut("slow");
	           return false;
	    }
	   });

  $("#siteNumber").keypress(function (e) {
	     //if the letter is not digit then display error and don't type anything
	     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
	        //display error message
	        $("#errmsg3").html("Vui lòng nhập giá trị số nguyên hợp lệ!").show().fadeOut("slow");
	           return false;
	    }
	   });*/
});

$("#save_btn").click(function(){
	var flag = 0;
	var err_msg = '';
	if ($("#siteId").val() == "") {
		flag = flag + 1;			
		err_msg = 'Vui lòng nhập mã trung tâm!';
	}
	if ($("#siteName").val() == "") {
		flag = flag + 1;		
		err_msg = 'Vui lòng nhập tên trung tâm!';
	} 
	if ($("#groupId").val() == "") {
		flag = flag + 1;		
		err_msg = 'Vui lòng chọn mã nhóm trung tâm!';		
	}
	if ($("#siteFunctionType").val() == "") {
		flag = flag + 1;
		err_msg = 'Vui lòng chọn Loại chức năng trung tâm!';		
	}
	if ($("#country").val() == "") {
		flag = flag + 1;
		err_msg = 'Vui lòng chọn quốc gia!';		
	}
	if (flag > 0) {			
		$.notify(err_msg, {
			globalPosition: 'bottom left',
			className: 'warn',
		});
	}else{			
	$('#ajax-load-qa').show();
	$("#siteId").attr('disabled', false);
	$.post('<c:url value="/servlet/siteMgmt/saveSiteRepo" />',$('#siteRepoForm').serialize(),
	    function(data) {
			$('#ajax-load-qa').hide();
			if(data=='success'){
				
				$.notify('Đã lưu thành công: ' + $("#siteId").val(), {
					globalPosition: 'bottom left',
					className: 'success',
				});
			} else if(data=='fail'){
				
				$.notify('Không thể lưu mã trung tâm đã lưu: ' + $("#siteId").val(), {
					globalPosition: 'bottom left',
					className: 'warn',
				});
			} else if(data=='exist'){
				
				$.notify('Mã hoặc tên trung tâm đã tồn tại trong hệ thống: ' + $("#siteId").val(), {
					globalPosition: 'bottom left',
					className: 'warn',
				});
			}
	 });
	
   // $("#editSiteRepositoryTable").show();	
	
} 
});
$("#cancel_btn").click(function(){
	document.forms["siteRepoForm"].action = '${url}/'+$("#groupId").val();
	document.forms["siteRepoForm"].submit();
});
$("#reset_btn").click(function(){
	document.forms["siteRepoForm"].reset();
});
$(function() {
    $( "#savedialog-confirm" ).dialog({
	modal: true,
      autoOpen: false,
	  width : 500,
	  resizable: true,
	  open : function() {
		    $('.ui-dialog-buttonpane span').addClass('btn_blue gradient'); 
		   
	  },
      show: {
        effect: "blue",
        duration: 200
      },
      hide: {
       // effect: "explode",
        duration: 1000
      },
	   buttons:{
    Ok: function() {    	
    	  $(this).dialog("close");
    	  $("#groupId").attr('disabled', false);
    	  document.forms["siteRepoForm"].action = '${url}/'+$("#groupId").val();
    	  document.forms["siteRepoForm"].submit();
    	  $("#groupId").attr('disabled', true);
    }	
   }
    });
  });
  
$(function() {
    $( "#faildialog-confirm" ).dialog({
	modal: true,
      autoOpen: false,
	  width : 500,
	  resizable: true,
	  open : function() {
		    $('.ui-dialog-buttonpane span').addClass('btn_blue gradient'); 
		   
	  },
      show: {
        effect: "fade",
        duration: 200
      },
      hide: {
       // effect: "explode",
        duration: 1000
      },
	   buttons:{
    Ok: function() {    	 
    	$(this).dialog("close"); 
    }	
   }
    });
  });

function updateForm() {
	$('#ajax-load-qa').show();
	//$('.modal').show();	
	//$('#ajax-load-qa').show();
	$("#siteId").attr('disabled', false);
	$.post('<c:url value="/servlet/siteMgmt/saveSiteRepo" />',$('#siteRepoForm').serialize(),
	     function(data){
			$('#ajax-load-qa').hide();
			 if(data=='success'){
					//$("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
				   // $("#savedialog-confirm").html('Đã cập nhật thành công '+$("#siteId").val());
				    
				    //$("#savedialog-confirm").dialog( 'open' );
				    $.notify('Đã cập nhật thành công: ' + $("#siteId").val(), {
						globalPosition: 'bottom left',
						className: 'success',
					});
			  } else if(data=='fail'){
				  //$("# faildialog-confirm").dialog('option', 'title', 'Thông báo');
				 // $("# faildialog-confirm").html('Không thể cập nhật '+$("#siteId").val());
				 // $("# faildialog-confirm").dialog( 'open' );
				  $.notify('Không thể cập nhật: ' + $("#siteId").val(), {
						globalPosition: 'bottom left',
						className: 'success',
					});
			  }
		 }); 	
	
	 $("#siteId").attr('disabled', true);
}

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
			effect : "explode",
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
});

</script>
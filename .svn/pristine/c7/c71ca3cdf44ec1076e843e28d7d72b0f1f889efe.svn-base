<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:url var="saveUrl" value="/servlet/siteMgmt/saveSiteGroup"/>
<c:url var="url" value="/servlet/siteMgmt/view"/>
<c:url var="resetPage" value="/servlet/siteMgmt/editSiteGroup"/>


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

/* Anytime the body has the loading class, our
   modal element will be visible */

.pding-left {
	padding-left: 10px;
}
.fix-bottom-1 {
	margin-top: 15px;
}
 </style>
<!-- <div id="main"> -->
<div id="content_main">
<form:form  modelAttribute="siteGroupForm" id="siteGroupForm" name="siteGroupForm" >	
<div class="container">
        	<div class="row">
        		<div class="ov_hidden">
	<div class="new-heading-2">THÊM/CHỈNH SỬA NHÓM TRUNG TÂM</div>     
	<div id="global_icon_div"></div>
		<fieldset>
			<legend>Thông tin về nhóm trung tâm</legend>
		
		<div class="col-md-12">
			<div class="col-md-6">
				<div class="col-md-4 fix-bottom-1">
					
					<label for="countryCode">Mã nhóm trung tâm<i style="color: red;padding: 5px;">*</i></label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<c:choose>
			   			 <c:when test='${not empty siteGroupForm.groupId}'>
			   			 	<form:input id="groupId" path="groupId" class="form-control" type="text" size='50' maxlength="5" disabled="true"/>
			   			 </c:when>
			   			 <c:otherwise>
			   			 	<form:input id="groupId" path="groupId" class="form-control" type="text" size='50' maxlength="5"/>
			   			 </c:otherwise>
		   			</c:choose>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Tên nhóm trung tâm</label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<form:input  id="groupName" path="groupName" class="form-control" type="text" size='50' />
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Tạo phiên bản</label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<form:input  id="groupCreateVersion" class="form-control" path="groupCreateVersion" type="text" size='50' />
				</div>				
			 			
			 </div>
			 <div class="col-md-6">
			 	<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Bản nâng cấp</label>
				</div>
				<div class="col-md-8 fix-bottom-1" style="height: 30px;">
					<form:input  id="groupUpdateVersion" class="form-control" path="groupUpdateVersion" type="text" size='50' />
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Cấp độ trung tâm</label>
				</div>
				<div class="col-md-8 fix-bottom-1" style="height: 30px;">
					<form:select id="levelNic" path="levelNic" class="form-control">
						<form:option value="1" label="Trung tâm xử lý"/>
						<form:option value="2" label="Trung tâm điều hành"/>
	   	        	</form:select>
				</div>		
				<div class="col-md-4 fix-bottom-1">
					<label for="typeArea">Vùng/miền:</label>
				</div>
				<div class="col-md-8 fix-bottom-1" style="height: 30px;">
					<form:select id="typeArea" path="typeArea" class="form-control">
						<form:option value="" label="-- Chọn --"/>
						<form:option value="MB" label="Bắc"/>
						<form:option value="MT" label="Trung"/>
						<form:option value="MN" label="Nam"/>
	   	        	</form:select>
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
   </div>
   </div>
   </div>
   <div id="ajax-load-qa"></div>
   <form:hidden path="mode" id="mode" />
</form:form>
 </div>

<script>
/*$(document).ready(function () {
  //called when key is pressed in textbox
  $("#groupCreateVersion").keypress(function (e) {
     //if the letter is not digit then display error and don't type anything
     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
        //display error message
        $("#errmsg1").html("Vui lòng nhập giá trị số nguyên hợp lệ!").show().fadeOut("slow");
           return false;
    }
   });

  $("#groupUpdateVersion").keypress(function (e) {
	     //if the letter is not digit then display error and don't type anything
	     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
	        //display error message
	        $("#errmsg2").html("Vui lòng nhập giá trị số nguyên hợp lệ!").show().fadeOut("slow");
	           return false;
	    }
	   });
});*/
function checkDigit(){
	var mes = '';
	if($("#groupCreateVersion").val().trim() != '' && isNaN($("#groupCreateVersion").val())){
		mes = 'Vui lòng nhập giá trị số nguyên.';
	}
	if($("#groupUpdateVersion").val().trim() != '' && isNaN($("#groupUpdateVersion").val())){
		mes = 'Vui lòng nhập giá trị số nguyên.';
	}
	return mes;
}	
$("#save_btn").click(function(){
	var flag = 0;
	var err_msg = '';
	if ($("#groupId").val() == "") {
		flag = flag + 1;	
		err_msg = 'Mã nhóm không được bỏ trống!';

	}
	var mes = checkDigit();
	if(mes != ''){
		flag = flag + 1;	
		err_msg = mes;
	}
	if (flag > 0) {			

		$.notify(err_msg, {
			globalPosition: 'bottom left',
			className: 'warn',
		});
	}else{			
		$('#ajax-load-qa').show();
		$("#groupId").attr('disabled', false);
		$.post('<c:url value="/servlet/siteMgmt/saveSiteGroup" />',$('#siteGroupForm').serialize(),
		    function(data){
			$('#ajax-load-qa').hide();
				if(data=='success'){
					$.notify('Đã lưu thành công: ' + $("#groupId").val(), {
						globalPosition: 'bottom left',
						className: 'success',
					});
				} else if(data=='fail'){
					$.notify('Không thể lưu: ' + $("#groupId").val(), {
						globalPosition: 'bottom left',
						className: 'warn',
					});
				}else if(data=='exist'){
					$.notify('Mã nhóm hoặc tên nhóm đã tồn tại trong hệ thống: ' + $("#groupId").val(), {
						globalPosition: 'bottom left',
						className: 'warn',
					});
				}
		 });
	} 
});
$("#cancel_btn").click(function(){
	document.forms["siteGroupForm"].action = '${url}';
	document.forms["siteGroupForm"].submit();
	
});
$("#reset_btn").click(function() {
	document.forms["siteGroupForm"].reset();
});
/*$(function() {
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
	        //effect: "explode",
	        duration: 1000
	      },
		   buttons:{
	    Ok: function() {    	
	    	  $(this).dialog("close");
	    	  document.forms["siteGroupForm"].action = '${url}';
	    	  document.forms["siteGroupForm"].submit();
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
        //effect: "explode",
        duration: 1000
      },
	   buttons:{
    Ok: function() {
    	$(this).dialog("close"); 
    }	
   }
    });
  });*/

function updateForm() {
	$('#ajax-load-qa').show();
	$("#groupId").attr('disabled', false);
	$.post('<c:url value="/servlet/siteMgmt/saveSiteGroup" />',$('#siteGroupForm').serialize(),
	     function(data){
			 $('#ajax-load-qa').hide();	
			 if(data=='success'){
					
				    $.notify('Nhóm trang được cập nhật thành công: ' + $("#groupId").val(), {
						globalPosition: 'bottom left',
						className: 'success',
					});
			  } else if(data=='fail'){
				  
				  $.notify('Không cập nhật được nhóm trang: ' + $("#groupId").val(), {
						globalPosition: 'bottom left',
						className: 'warn',
				  });
			  }
		 }); 	
	
	 $("#groupId").attr('disabled', true);
}

/*$(function() {
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
});*/

</script>
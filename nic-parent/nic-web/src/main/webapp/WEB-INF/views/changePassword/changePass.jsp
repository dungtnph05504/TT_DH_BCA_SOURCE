<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jquery-ui.css"/>"></link>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mouseover_image.css"/>"></link>
	<c:url var="backToMainUrl" value="/servlet/user/logout" />
	<c:url var="cancelUrl" value="/servlet/user/home" />
<style>
.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
    white-space: pre-wrap;
}	
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
.fix-bottom {
	margin-top: 15px;
}
 </style>
<script type="text/javascript">

</script>

<!-- jquery dialog box script for save button -->
<script>
  $(function() {
    
 
   
    $( "#dialog-confirm" ).dialog({
    	resizable: false,
    	modal: true,
          autoOpen: false,
    	  width :500,
    	  resizable: false,
          show: {
            effect: "fade",
            duration: 1000
             },
          hide: {
            duration: 1000
          },
    	  buttons:{
        "Đồng ý": function() {
        	$(this).dialog('close');
        	$('.modal').show();
        	 document.getElementById('changePasswordId').disabled=false;	
        	 $.post('<c:url value="/servlet/changePasswordController/storePassword" />',$('#form').serialize(),
		             function(data){
        		 document.getElementById('changePasswordId').disabled=true;
				   if(data=='success'){
				   	   $("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#savedialog-confirm").html('Cập nhật thành công mật khẩu mới. Vui lòng đăng nhập bằng mật khẩu mới');
				       $("#savedialog-confirm").dialog( 'open' );
				       $('.modal').hide();
				    }else if(data=='fail'){
				       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#faildialog-confirm").html('Không thể cập nhật mật khẩu cho người dùng :  '+$("#changePasswordId").val());
				       $("#faildialog-confirm").dialog( 'open' );
				       $('.modal').hide();
				    }else if(data == 'incorrectPwd'){
				    	 $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
					       $("#faildialog-confirm").html('Mật khẩu không đúng. Vui lòng liên hệ với quản trị viên');
					       $("#faildialog-confirm").dialog( 'open' );
					       $('.modal').hide();
				    }
				  
			  });
        	 
        	 //document.forms["form"].submit();
        	 
        },
    	"Hủy": function() {
         $(this).dialog("close");
        }
       }
        });
    
  });
  $(function() {
		$("#msgDialog").dialog({
			width : 400,
			height : 175,
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
			    "Đóng": function() {    	
			    	$(this).dialog("close");
			    	  document.forms["form"].action = '${backToMainUrl}';
			    	  document.forms["form"].submit();
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
			    "Đóng": function() {    	
			    	$(this).dialog("close");
			    }
			   }
			    });

		//Tooltip for Password field
			$('#newPwd').on({
				  "click": function() {
				   $(this).tooltip({ 
							    items: "#newPwd", 
							    content: "Mật khẩu phải có ít nhất 8 ký tự bằng chữ, số và phải có ít nhất một chữ hoa. Và không chứa tên tài khoản của người dùng."});
				    $(this).tooltip("enable");
					$(this).tooltip("open");
				  },
				  "mouseout": function() {      
				     $(this).tooltip("disable");   
				  }
				});
	});
  </script>
  
  <!-- Jquery Dialog box div for save -->
<!-- <div id="dialog-save" title="Save Card">
  <p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>Confirm change Password?</p>
</div> -->

<div id="content_main">
<c:url var="url" value="/servlet/adminConsole/userManagement/storePassword"/>
	<c:if test="${not empty requestScope.Errors}">
		<div style="color:Red;background-color:White;border-style:solid;border-color:Red;border-width:thin;">
			<c:forEach items="${requestScope.Errors}" var="takla">
				<li>
					${takla}
				</li>
			</c:forEach>
			
		</div>
	</c:if>
<form:form action="${url}" name="form" id="form" cssClass="inline" cssStyle="">
<div class="form-desing">
          <div class="row">
                            <div class="ov_hidden">
		<div class="new-heading-2">THAY ĐỔI MẬT KHẨU</div>
				<div class="col-md-12">
					<div class="col-md-6">
						<div class="col-md-4 fix-bottom">
							<label for="countryCode">Tên đăng nhập<i style="color: red">(*)</i></label>
						</div>
						<div class="col-md-8 fix-bottom">
							<input id="changePasswordId" name="changePasswordId" value ="${changePasswordId}" class="form-control" type="text" value="" size="30" maxlength="30" readonly="readonly" />
						</div>
						<div class="col-md-4 fix-bottom">
							<label for="countryCode">Mật khẩu hiện tại<i style="color: red">(*)</i></label>
						</div>
						<div class="col-md-8 fix-bottom">
							<input id="currentPwd" name="currentPwd"  class="form-control" type="password" value="" size="30" maxlength="30" />
						</div>
						<div class="col-md-4 fix-bottom">
							<label for="countryCode">Mật khẩu mới<i style="color: red">(*)</i></label>
						</div>
						<div class="col-md-8 fix-bottom">
							<input id="newPwd" name="newPwd" class="form-control" type="password" value="" size="30" maxlength="30" />
						</div>
						<div class="col-md-4 fix-bottom">
							<label for="countryCode">Xác nhận mật khẩu<i style="color: red">(*)</i></label>
						</div>
						<div class="col-md-8 fix-bottom">
							<input id="confirmPwd" name="confirmPwd" class="form-control" type="password" value="" size="30" maxlength="30" />
						</div>
					</div>
				</div>
				<div class="col-md-12" style="text-align: right;margin-top: 30px;">
					<button type="button" class="btn btn-primary"  onclick="doSubmitSave(this.form);" id="save_btn">
						<span class="glyphicon glyphicon-ok"></span> Đồng ý
					</button>
					<button type="button" class="btn btn-danger"  onclick="window.location.href='${cancelUrl}'" id="reset_btn">
						<span class="glyphicon glyphicon-remove"></span> Hủy bỏ
					</button>
					<!--<input type="button" id="reset_btn" class="btn btn-danger"   onclick="window.location.href='${cancelUrl}'"  value="Hủy bỏ" />
	         		<input type="button" class="btn btn-success" id="save_btn" value="Đồng ý" onclick="doSubmitSave(this.form);" />-->
	          </div>
					<!--<table style="width:100%;background-color:#E3F1FE" cellspacing="0"class="data_table" cellpadding="0">
						<thead>
							<tr>
						<tbody>
						<tr>
								<td style="border: none; font-weight: bold">Tên đăng nhập<span style="COLOR: #ff0000;">*</span></td>
								<td style="border: none;padding-right: 35%;">
									<input id="changePasswordId" name="changePasswordId" value ="${changePasswordId}" class="defaultText" type="text" value="" size="30" maxlength="30" disabled />
								</td>
							</tr>
							<tr>
								<td style="border: none; font-weight: bold">Mật khẩu hiện tại<span style="COLOR: #ff0000;">*</span></td>
								<td style="border: none;">
									<input id="currentPwd" name="currentPwd" class="defaultText" type="password" value="" size="30" maxlength="30" />
								</td>
							</tr>
							<tr>
								<td style="border: none; font-weight: bold">Mật khẩu mới<span style="COLOR: #ff0000;">*</span></td>
								<td style="border: none;">
									<input id="newPwd" name="newPwd" class="defaultText" type="password" value="" size="30" maxlength="30" />
								</td>
							</tr>
							<tr>
								<td style="border: none; font-weight: bold">Xác nhận mật khẩu<span style="COLOR: #ff0000;">*</span></td>
								<td style="border: none;">
									<input id="confirmPwd" name="confirmPwd" class="defaultText" type="password" value="" size="30" maxlength="30" />
								</td>
							</tr>
					</table>-->		
					<!--<table style="text-align: right;" class="displayTag">
						<tr>
							<td align="right" style="padding: 10px;text-align: right;"><input type="button" id="save_btn" class="button_grey" value="Đồng ý" onclick="doSubmitSave(this.form);"/>
								&nbsp; 
								<input type="button" id="reset_btn" class="button_grey" onclick="window.location.href='${cancelUrl}'"
									value="Hủy bỏ" />
								</td>
							</tr>
					</table>-->
					</div>
					</div>
					</div>
	</form:form>
</div>
<div id="msgDialog" title="Cảnh báo">
	<div class="isa_info" align="center">
		<span style="font-size: 25"></span>
	</div>
	<div class="error_msg"></div>
</div>
<div id="dialog-confirm"></div>
<div id="savedialog-confirm" style="display: none;"></div>
<div id="faildialog-confirm" style="display: none;"></div>
<script type="text/javascript">
	function doSubmitSave(form) {
		$('#unassignedRoles option').prop('selected', 'selected');
		$('#assignedRoles option').prop('selected', 'selected');
		var flag = 0;	
		 
		if($("#currentPwd").val() == ""){
			flag = flag + 1;			
			//$(".error_msg").html("Mật khẩu hiện tại là bắt buộc. ");		
			$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Mật khẩu hiện tại là bắt buộc.",
			});
		}
		else if($("#newPwd").val() == ""){
			flag = flag + 1;			
			//$(".error_msg").html("Mật khẩu mới là bắt buộc.");	
			$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Mật khẩu mới là bắt buộc.",
			});
		}else if($("#confirmPwd").val() == ""){
			flag = flag + 1;
			//$(".error_msg").html("Xác nhận mật khẩu mới là bắt buộc.");
			$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Xác nhận mật khẩu mới là bắt buộc.",
			});	
		}
		else if(!($("#newPwd").val() == $("#confirmPwd").val())){
			flag = flag + 1;		
			//$(".error_msg").html("Mật khẩu và xác nhận mật khẩu phải giống nhau.");
			$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Mật khẩu và xác nhận mật khẩu phải giống nhau.",
			});	
		}
		else if(!validatePassword()){
			flag = flag + 1;		
			//$(".error_msg").html("Mật khẩu phải có ít nhất 8 ký tự với chữ số và phải có ít nhất một chữ hoa. Và không chứa tên tài khoản của người dùng.");
			$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Mật khẩu phải có ít nhất 8 ký tự có chữ và số và phải có ít nhất một chữ hoa. Không được chứa tên tài khoản của người dùng.",
			});	
		}			
		if (flag > 0) {				
			//$("#msgDialog").dialog("open");			
			
		}else{	
			$.confirm({
			    title: 'Xác nhận',
			    content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/tip_1a.png\">" + ' Bạn có chắc chắn muốn thay đổi mật khẩu?',
			    buttons: {
			        "Đồng ý": function () {
			        	//$(this).dialog('close');
			        	 $('.modal').show();
			        	 document.getElementById('changePasswordId').disabled=false;	
			        	 $.post('<c:url value="/servlet/changePasswordController/storePassword" />',$('#form').serialize(),
					             function(data){
			        		 document.getElementById('changePasswordId').disabled=true;
							   if(data=='success'){
							   	   //$("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
							       //$("#savedialog-confirm").html('Cập nhật thành công mật khẩu mới. Vui lòng đăng nhập bằng mật khẩu mới');
							       //$("#savedialog-confirm").dialog( 'open' );
							       $('.modal').hide();
							       $.alert({
										title: 'Thông báo',
										content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/success_1a.png\">" + " Cập nhật thành công mật khẩu mới.",
									});
							       
							    }else if(data=='fail'){
							       //$("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
							       //$("#faildialog-confirm").html('Không thể cập nhật mật khẩu cho người dùng :  '+$("#changePasswordId").val());
							       //$("#faildialog-confirm").dialog( 'open' );
							       $('.modal').hide();
							       $.alert({
										title: 'Thông báo',
										content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/error_1a.png\">" + " Không thể cập nhật mật khẩu cho người dùng: " + $("#changePasswordId").val(),
									});
							    }else if(data == 'incorrectPwd'){
							    	 //$("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
								      // $("#faildialog-confirm").html('Mật khẩu không đúng. Vui lòng liên hệ với quản trị viên');
								      // $("#faildialog-confirm").dialog( 'open' );
								       $('.modal').hide();
								       $.alert({
											title: 'Thông báo',
											content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/error_1a.png\">" + "Mật khẩu không đúng. Vui lòng liên hệ với quản trị viên",
									   });
							    }
							  
						  }); 
			        },
			        "Hủy": function () {
			        	//return false;
			        }			       
			    }
			})
		 //$("#dialog-confirm").dialog('option', 'title', 'Xác nhận thay đổi mật khẩu');
		// $("#dialog-confirm").html('Bạn có chắc chắn muốn thay đổi Mật khẩu ?');
		 //$("#dialog-confirm").dialog( 'open' );
		
		}
	}
	
function validatePassword(){
		
		var pswd = $('#newPwd').val();
		var uname = $('#changePasswordId').val();
		if ( pswd.length < 8 ) {
			//alert('Password must be at least 8 characters');
			return false;
		} 


		if (!pswd.match(/[A-z]/) ) {
			//alert('Password must be at least one letter');
			return false;
		}

		//validate capital letter
		if (!pswd.match(/[A-Z]/) ) {
			//alert('Password must contain at least one capital letter');
			return false;
		}

		//validate number
		if (!pswd.match(/\d/) ) {
			//alert('Password must contain at least one number');
			return false;
		}

		//validate contains account name
		if (pswd.toUpperCase().indexOf(uname) !== -1 ) {
			//alert('Passwords may not contain the user's samAccountName (Account Name) value or entire displayName (Full Name value). ');
			return false;
		}
		
		return true;
	}
</script>
<div class="modal">
		<!-- Place at bottom of page -->
</div>

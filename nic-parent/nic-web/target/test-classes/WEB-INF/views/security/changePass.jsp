<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jquery-ui.css"/>"></link>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/mouseover_image.css"/>"></link>
	<c:url var="backToMainUrl" value="/servlet/adminConsole/userManagement" />
<style>

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
.button_grey {
 	height: 30px;
}
.displayTag-1 {
	font-family: Arial, Helvetica, sans-serif;
    font-size: 9pt;
    width: 99.8%;
}
.data_table-1 {
    border-radius: 10px;
}
.data_table-1 tbody tr {
	height: 40px;
}
.fix-bottom {
	margin-top: 20px;
}
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
 </style>

<!-- jquery dialog box script for save button -->
<script>
		
  $(function() {  
	//Tooltip for Password field
		/*$('#newPwd').on({
			  "click": function() {
			   $(this).tooltip({ 
						    items: "#newPwd", 
						    content: "Mật khẩu phải có ít nhất 8 ký tự có chữ và số và phải có ít nhất một chữ hoa.\n Và không chứa tên tài khoản của người dùng"});
			    $(this).tooltip("enable");
				$(this).tooltip("open");
			  },
			  "mouseout": function() {      
			     $(this).tooltip("disable");   
			  }
			});*/
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
        	 $.post('<c:url value="/servlet/adminConsole/userManagement/storePassword" />',$('#form').serialize(),
		             function(data){
        		 document.getElementById('changePasswordId').disabled=true;
				   if(data=='success'){
				   	   $("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#savedialog-confirm").html('Đã cập nhật thành công mật khẩu mới cho người dùng:  '+$("#changePasswordId").val());
				       $("#savedialog-confirm").dialog( 'open' );
				       $('.modal').hide();
				    }else if(data=='fail'){
				       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#faildialog-confirm").html('Không thể cập nhật mật khẩu cho người dùng:  '+$("#changePasswordId").val());
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
  
  function changePass(){
	  $('#ajax-load-qa').show();
	  document.getElementById('changePasswordId').disabled=false;	
 	  $.post('<c:url value="/servlet/adminConsole/userManagement/storePassword" />',$('#form').serialize(),
      function(data){
		  document.getElementById('changePasswordId').disabled=true;
		  $('#ajax-load-qa').hide();
		  if(data=='success'){
		  	  //$("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
		      //$("#savedialog-confirm").html('Đã cập nhật thành công mật khẩu mới cho người dùng:  '+$("#changePasswordId").val());
		      //$("#savedialog-confirm").dialog( 'open' );
		      //$('.modal').hide();
		      var mes = 'Đã cập nhật thành công mật khẩu mới cho người dùng: '+$("#changePasswordId").val();
			    $.notify(mes, {
					globalPosition: 'bottom left',
					className: 'success',
				});
		   }else if(data=='fail'){
		      //$("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
		      ////$("#faildialog-confirm").html('Không thể cập nhật mật khẩu cho người dùng:  '+$("#changePasswordId").val());
		     // $("#faildialog-confirm").dialog( 'open' );
		     //$('.modal').hide();
		     mes = 'Không thể cập nhật mật khẩu cho người dùng: '+$("#changePasswordId").val();
		     $.notify(mes, {
					globalPosition: 'bottom left',
					className: 'warn',
				});
		   }else if(data == 'incorrectPwd'){
		   	 //$("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
		       //$("#faildialog-confirm").html('Mật khẩu không đúng. Vui lòng liên hệ với quản trị viên');
		       //$("#faildialog-confirm").dialog( 'open' );
		       //$('.modal').hide();
			   mes = 'Mật khẩu không đúng. Vui lòng liên hệ với quản trị viên.';
		     	$.notify(mes, {
					globalPosition: 'bottom left',
					className: 'warn',
				});
		   }
			  
	  });
  }
  
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
				Ok : function() {
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
			    Ok: function() {    	
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
			    Ok: function() {    	
			    	$(this).dialog("close");
			    }
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
<form:form action="${url}" method="POST" name="form" id="form" cssClass="inline" cssStyle="">
		<%-- <input type="hidden" name="changePasswordId" id="changePasswordId" value="${changePasswordId}"> --%>
		<div class="container">
                <div class="bgr-white ov_hidden">
                        <div class="new-heading-2">THAY ĐỔI MẬT KHẨU</div>
                        <fieldset>
							<legend>Thông tin mật khẩu người dùng</legend>
				
	                        <div class="row">
	                        	<div class="col-md-12">
									<div class="col-md-6">
										<div class="col-md-4 fix-bottom">
											<label for="countryCode">Tên đăng nhập<i style="color: red">(*)</i>:</label>
										</div>
										<div class="col-md-8 fix-bottom">
											<input id="changePasswordId" name="changePasswordId" value ="${changePasswordId}" autocomplete = "off" class="form-control" type="text" value="" size="30" maxlength="30" disabled />
										</div>
										<div class="col-md-4 fix-bottom">
											<label for="countryCode">Mật khẩu mới<i style="color: red">(*)</i>:</label>
										</div>
										<div class="col-md-8 fix-bottom">
											<input id="newPwd" name="newPwd" value ="" autocomplete = "off" class="form-control" type="password" size="30" maxlength="30" />
										</div>
										<div class="col-md-4 fix-bottom">
											<label for="countryCode">Xác nhận mật khẩu<i style="color: red">(*)</i>:</label>
										</div>
										<div class="col-md-8 fix-bottom">
											<input id="confirmPwd" name="confirmPwd" value ="" autocomplete = "off" class="form-control" type="password" size="30" maxlength="30" />
										</div>
									</div>
	                        	</div>
							</div>
						</fieldset>
						<!--<div class="col-md-12" style="text-align: right;">
							<button type="button" class="btn btn-danger"   onclick="window.location.href='${backToMainUrl}'" id="reset_btn">
								<span class="glyphicon glyphicon-remove"></span> Hủy
							</button>
							<button type="button" class="btn btn-success" onclick="doSubmitSave(this.form);" id="save_btn">
								<span class="glyphicon glyphicon-ok"></span> Lưu
							</button>
							
						</div>-->
                 </div>    
             	 <div style="display: flex;flex: 0 auto;justify-content: center;">
					<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
						<div style="margin: 10px"> 	
							<button type="button" class="btn btn-success"   onclick="window.location.href='${backToMainUrl}'" id="reset_btn">
								<span class="glyphicon glyphicon-remove"></span> Hủy
							</button>
							<button type="button" class="btn btn-success" onclick="doSubmitSave(this.form);" id="save_btn">
								<span class="glyphicon glyphicon-ok"></span> Lưu
							</button>
						</div>
					</div>
				</div>
				<!-- Message lưu hồ sơ -->
				<div class="modal fade" id="messageLHS" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
				  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 300px;">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">THÔNG BÁO</h5>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
				        </button>
				      </div>
				      <div class="modal-body" id="idMessage">
				      		<div>
				      			<i class="glyphicon glyphicon-question-sign" style="font-size: 2em;"></i>
				      			<p style="display: inline-block;position: absolute;margin-left: 10px;margin-top: 5px;">Bạn có muốn thay đổi mật khẩu?</p>
				      		</div>
				      </div>							      
				      <div class="modal-footer" style="padding: 5px;">
				       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
				       			<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> KHÔNG</span>
				       		</button>
				       		<button type="button" onclick="changePass();" class="btn btn-success" data-dismiss="modal" aria-label="Close" style="float: right;margin-right: 10px;">
				       			<span class="glyphicon glyphicon-ok btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> CÓ</span>
				       		</button>
				       		
				      </div>
				    </div>
				  </div>
				</div>	
				<!-- ----------------------------------------------------------------------------> 
				<div id="ajax-load-qa"></div>
			</div>
	</form:form>
</div>
<div id="msgDialog" title="Thông báo">
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
		var ror_msg = '';   
		if($("#newPwd").val() == ""){
			flag = flag + 1;			
			//$(".error_msg").html("Mật khẩu mới là bắt buộc ");	
			ror_msg = 'Mật khẩu mới là bắt buộc.';
		}else if($("#confirmPwd").val() == ""){
			flag = flag + 1;
			//$(".error_msg").html("Xác nhận mật khẩu là bắt buộc ");
			ror_msg = 'Xác nhận mật khẩu là bắt buộc.';	
		}
		else if(!($("#newPwd").val() == $("#confirmPwd").val())){
			flag = flag + 1;		
			//$(".error_msg").html("Mật khẩu và xác nhận mật khẩu phải giống nhau.");	
			ror_msg = 'Mật khẩu và xác nhận mật khẩu phải giống nhau.';
		}
		else if(!validatePassword()){
			flag = flag + 1;		
			//$(".error_msg").html("Mật khẩu phải có ít nhất 8 ký tự có chữ và số và phải có ít nhất một chữ hoa.\n Và không chứa tên Tài khoản của người dùng.");		
			ror_msg = 'Mật khẩu phải có ít nhất 8 ký tự có chữ và số và phải có ít nhất một chữ hoa.';
		}			
		if (flag > 0) {				
			//$("#msgDialog").dialog("open");			
			$.notify(ror_msg, {
				globalPosition: 'bottom left',
				className: 'warn',
			});
		}else{	
			$('#messageLHS').modal();
		// $("#dialog-confirm").dialog('option', 'title', 'Thay đổi mật khẩu');
		// $("#dialog-confirm").html('Bạn có chắc chắn muốn thay đổi Mật khẩu?');
		// $("#dialog-confirm").dialog( 'open' );
		
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

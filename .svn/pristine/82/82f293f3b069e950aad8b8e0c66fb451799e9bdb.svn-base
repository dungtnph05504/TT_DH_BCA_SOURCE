<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:url var="userJobListUrl" value="/servlet/adminConsole/userManagement" />
<c:url var="loadDepartmentUrl" value="/servlet/adminConsole/userManagement/loadDepartment" />
<style>

/* When the body has the loading class, we turn
   the scrollbar off with overflow:hidden */
body.loading {
	overflow: hidden;
}
.fix-bottom {
	margin-top: 10px;
}
/* Anytime the body has the loading class, our
   modal element will be visible */
body.loading .modal {
	display: block;
}
.btn-with{
	width: 30px;
}
.bgr-white {
	background-color: #fff;
}
.form-control {
	border: 1px solid #cecece;
}
 </style>
<script type="text/javascript">
	$(document).ready(
			function() {

				$('#addAllBtnId').click(
						function() {
							$('#unassignedRoles option').prop('selected', 'selected');
							$('#unassignedRoles option:selected').each(
									function() {
										$('#assignedRoles').append(
												"<option value='"
														+ $(this).val() + "'>"
														+ $(this).text()
														+ "</option>");
										$(this).remove();
									});
						});

				$('#addBtnId').click(
						function() {
							$('#unassignedRoles option:selected').each(
									function() {
										$('#assignedRoles').append(
												"<option value='"
														+ $(this).val() + "'>"
														+ $(this).text()
														+ "</option>");
										$(this).remove();
									});
						});

				$('#removBtnId').click(
						function() {
							$('#assignedRoles :selected').each(
									function() {
										$('#unassignedRoles').append(
												"<option value='"
														+ $(this).val() + "'>"
														+ $(this).text()
														+ "</option>");
										$(this).remove();
									});
						});

				$('#removAllBtnId').click(
						function() {
							$('#assignedRoles option')
									.prop('selected', 'selected');
							$('#assignedRoles :selected').each(
									function() {
										$('#unassignedRoles').append(
												"<option value='"
														+ $(this).val() + "'>"
														+ $(this).text()
														+ "</option>");
										$(this).remove();
									});
						});
				
				$("#userId").keypress(function (event) {
				    var regex = new RegExp("^[a-zA-Z0-9]+$");
				    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
				    if (!regex.test(key)) {
				       event.preventDefault();
				       return false;
				    }
				  });

				//Tooltip for Password field
				$('#password').on({
					  "click": function() {
					   $(this).tooltip(
							    { 
								    items: "#password", 
								    content: "Mật khẩu phải có ít nhất 8 ký tự với chữ số và phải có ít nhất một chữ hoa. \nVà không chứa tên tài khoản của người dùng. "});
					    $(this).tooltip("enable");
						$(this).tooltip("open");
					  },
					  "mouseout": function() {      
					     $(this).tooltip("disable");   
					  }
					});
			});

</script>

<!-- <div id="main"> -->
	<div id="content_main">
<c:url var="url" value="/servlet/adminConsole/userManagement/saveUser"/>
	<c:if test="${not empty requestScope.Errors}">
		<div style="color:Red;background-color:White;border-style:solid;border-color:Red;border-width:thin;height: 25px;margin-top: 15px;">
			<c:forEach items="${requestScope.Errors}" var="takla">
				<li style="margin-top: 15px;">
					${takla}
				</li>
			</c:forEach>
			
		</div>
	</c:if>
	
<form:form modelAttribute="addEditUserModel" name="form" id="form" action="${url}" method="POST" cssClass="inline" cssStyle="">
<div class="container">
          <div class="row">
               <div class="ov_hidden bgr-white">
		<!--<table class="heading_report" id="heading_left" style="width: 25%;height: 35px;">
			<tr>
				<c:if test="${empty userEdit}">
					<td class='subHeading' id="heading_left" colspan='4' style="padding: 5px">THÊM MỚI NGƯỜI DÙNG</td>
				</c:if>
				<c:if test="${not empty userEdit}">
					<td class='subHeading' id="heading_left" colspan='4' style="padding: 5px">SỬA NGƯỜI DÙNG</td>
				</c:if>
			</tr>
		</table>-->
				<c:if test="${empty userEdit}">
					<div class="new-heading-2">THÊM MỚI NGƯỜI DÙNG</div>
				</c:if>
				<c:if test="${not empty userEdit}">
					<div class="new-heading-2">CẬP NHẬT NGƯỜI DÙNG</div>
				</c:if>
				<fieldset>
					<legend>Thông tin người dùng</legend>
				
					<div class="col-md-12">
						<div class="col-md-6">
							<div class="col-md-4 fix-bottom">
								<label for="countryCode">Họ<i style="color: red">(*)</i></label>
							</div>
							<div class="col-md-8 fix-bottom">
								<input id="firstName" name="firstName" class="form-control" type="text" value="${userEdit.firstName}" size="30" maxlength="30" autocomplete = "off" />
							</div>
							<div class="col-md-4 fix-bottom">
								<label for="countryCode">Tên<i style="color: red">(*)</i></label>
							</div>
							<div class="col-md-8 fix-bottom">
								<input id="lastName" name="lastName" class="form-control" type="text" value="${userEdit.lastName}" size="30" maxlength="30" />
							</div>
							<div class="col-md-4 fix-bottom">
								<label for="countryCode">Mã nhân viên</label>
							</div>
							<div class="col-md-8 fix-bottom">
								<input id="middleInitial" name="middleInitial" class="form-control" type="text" value="${userEdit.middleInitial}" size="30" maxlength="30" />
							</div>
							<div class="col-md-4 fix-bottom">
								<label for="countryCode">Mã trung tâm<i style="color: red">(*)</i></label>
							</div>
							<div class="col-md-8 fix-bottom">
								<select id="siteCode" name="siteCode" class="form-control">										
									<option value=""  label="--- Chọn ---" />
									<c:forEach var="maplist" items="${addEditUserModel.sitCodeMap}" varStatus="status1">												
										<c:choose>
											<c:when test="${maplist.key == userEdit.siteCode}">
												<option value="${maplist.key}" selected="selected" label="${maplist.value }" />
											</c:when>
											<c:otherwise>
												<option value="${maplist.key}" label="${maplist.value }" />
											</c:otherwise>
										</c:choose>
									</c:forEach>										 
								</select>
							</div>
							<div class="col-md-4 fix-bottom">
								<label for="countryCode">Phòng ban</label>
							</div>
							<div class="col-md-8 fix-bottom">
								<select id="department" name="department" class="form-control">										
									<option value=""  label="--- Chọn ---" />
									<c:forEach var="maplist" items="${addEditUserModel.departmentMaps}" varStatus="status1">												
										<c:choose>
											<c:when test="${maplist.key == userEdit.department}">
												<option value="${maplist.key}" selected="selected" label="${maplist.value}" />
											</c:when>
											<c:otherwise>
												<option value="${maplist.key}" label="${maplist.value }" />
											</c:otherwise>
										</c:choose>
									</c:forEach>										 
								</select>
							</div>
							<div class="col-md-4 fix-bottom">
								<label for="countryCode">Chức vụ</label>
							</div>
							<div class="col-md-8 fix-bottom">
								<select id="position" name="position" class="form-control">										
									<option value=""  label="--- Chọn ---" />
									<c:forEach var="maplist" items="${addEditUserModel.positionMaps}" varStatus="status1">												
										<c:choose>
											<c:when test="${maplist.key == userEdit.position}">
												<option value="${maplist.key}" selected="selected" label="${maplist.value}" />
											</c:when>
											<c:otherwise>
												<option value="${maplist.key}" label="${maplist.value}" />
											</c:otherwise>
										</c:choose>
									</c:forEach>										 
								</select>
							</div>
							<div class="col-md-4 fix-bottom">
								<label for="countryCode">Email</label>
							</div>
							<div class="col-md-8 fix-bottom">
								<input id="email" name="email" class="form-control" type="text" value="" size="30" maxlength="30" />
							</div>
							<div class="col-md-4 fix-bottom">
								<label for="countryCode">Tên đăng nhập<i style="color: red">(*)</i></label>
							</div>
							<div class="col-md-8 fix-bottom">
								<c:if test="${empty userEdit}">
									<input id="userId" name="userId" class="form-control" type="text"
										value="" size="30" maxlength="30" alt="Tên đăng nhập (0-9, a-z, A-Z)" pattern="^[a-zA-Z0-9]+$"  />
									<input id="transactionType" type="hidden"  name="mode" value="ADD"/>
									<input type="hidden"  id="userName"  name="userName" value="" />
								</c:if>
								<c:if test="${not empty userEdit}">
									<input id="userId" name="userId" class="form-control" type="text"
										value="${userEdit.userId}" size="30" maxlength="30" disabled />
									<input id="transactionType" type="hidden"  name="mode" value="EDIT"/>
									<input type="hidden"  id="userName"  name="userName" value="${userEdit.userName}" />
								</c:if>
							</div>
							<c:if test="${(empty userEdit) or ((not empty userEdit) and (!isUserExistingInAd))}">
								<div class="col-md-4 fix-bottom">
									<label for="countryCode">Mật khẩu<i style="color: red">(*)</i></label>
								</div>
								<div class="col-md-8 fix-bottom">
									<input id="password" name="password" class="form-control" type="password" value="" size="30" maxlength="30" />
								</div>
								<div class="col-md-4 fix-bottom">
									<label for="countryCode">Xác nhận mật khẩu<i style="color: red">(*)</i></label>
								</div>
								<div class="col-md-8 fix-bottom">
									<input id="confirmPwd" name="confirmPwd" class="form-control" type="password" value="" size="30" maxlength="30" />
								</div>
							</c:if>
						 </div>
						 <div class="col-md-6" style="margin-top: 10px;">
						 	 <div class="col-md-5" style="text-align: center;padding: 0px;">
						 	 	 <!--<input type="button" class="btn btn-primary" value="Quyền không được chỉ định" style="margin-bottom: 10px;"/>-->
						 	 	 <fieldset>
						 	 	 	<legend>Quyền chưa cấp</legend>
							 	 	 <form:select id="unassignedRoles" path="unassignedRoles" cssStyle="width: 200px; height: 200px" multiple="true" >
										<c:forEach var="maplist" items="${addEditUserModel.unassignedRoleMaps}" >
												<form:option value="${maplist.key}" label="${maplist.value }"/>
										</c:forEach>
									 </form:select>
						 	 	 </fieldset>
						 	 </div>
						 	 <div class="col-md-2" style="padding-left:30px;padding-top: 50px;">
						 	 	<table>
									<tr>
										<td style="height: 40px;"><input id="addAllBtnId" type="button"
										class="btn_small btn-success btn-with" value=">>" align="center" /></td>
									</tr>
									<tr>
										<td style="height: 40px;"><input id="addBtnId" type="button"
										class="btn_small btn-success btn-with" value=">" align="center" /></td>
									</tr>
										<tr>
											<td style="height: 40px;"><input id="removBtnId" type="button"
											class="btn_small btn-success btn-with" value="<" align="center" /></td>
										</tr>
										<tr>
											<td style="height: 40px;"><input id="removAllBtnId" type="button"
											class="btn_small btn-success btn-with" value="<<" align=" center" /></td>
										</tr>
								</table>
						 	 </div>
						 	 <div class="col-md-5" style="text-align: center;padding: 0px;">
						 	 <!--<input type="button" class="btn btn-primary" value="Quyền được chỉ định" style="margin-bottom: 10px;"/>-->
						 	 <fieldset>
						 	 	 	<legend>Quyền đã cấp</legend>
								 	 <form:select id="assignedRoles" path="assignedRoles" cssStyle="width: 200px; height: 200px" multiple="true" >
						   	        	<c:forEach var="maplist" items="${addEditUserModel.assignedRoleMaps}" >
											<form:option value="${maplist.key}" label="${maplist.value }"/>
										</c:forEach>
						   	         </form:select>
				   	         </fieldset>
						 	 </div>	
						 </div>
			          </div>
          		</fieldset>
          <!--<div class="col-md-12" style="text-align: right;margin-top: 30px;">
				<button type="button" class="btn btn-danger"   onclick="goBack();" id="back_btn">
					<span class="glyphicon glyphicon-remove"></span> Hủy
				</button>
				<button type="button" class="btn btn-primary" onclick="window.location.href='${requestScope['javax.servlet.forward.request_uri']}'" id="reset_btn">
					<span class="glyphicon glyphicon-refresh"></span> Làm mới
				</button>
				<button type="button" class="btn btn-success" onclick="doSubmitSave(this.form);">
					<span class="glyphicon glyphicon-ok"></span> Lưu
				</button>
				
          </div>--> 
        <div style="display: flex;flex: 0 auto;justify-content: center;">
			<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
				<div style="margin: 10px"> 			
					<button type="button" class="btn btn-success"   onclick="goBack();" id="back_btn">
						<span class="glyphicon glyphicon-remove"></span> Hủy
					</button>
					<button type="button" class="btn btn-success" onclick="window.location.href='${requestScope['javax.servlet.forward.request_uri']}'" id="reset_btn">
						<span class="glyphicon glyphicon-refresh"></span> Làm mới
					</button>
					<button type="button" class="btn btn-success" onclick="doSubmitSave(this.form);">
						<span class="glyphicon glyphicon-ok"></span> Lưu
					</button>
				</div>
			</div>
		</div>
		<c:if test="${empty userEdit}">
			<input type="hidden"
			id="organizationalUnitString" name="organizationalUnitString"
			class="defaultText" type="text"
			value="OU=MNIS" size="30" maxlength="30" />
		</c:if>
		<c:if test="${not empty userEdit}">
			<input type="hidden"
			id="organizationalUnitString" name="organizationalUnitString"
			class="defaultText" type="text"
			value="${userEdit.organizationalUnitString}" size="30" maxlength="30" disabled  />
		</c:if>				
	</div>
	</div>
	</div>
	</form:form>
	</div>
<!-- </div> -->
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
	      			<p style="display: inline-block;position: absolute;margin-left: 10px;margin-top: 5px;">Bạn có muốn lưu người dùng?</p>
	      		</div>
	      </div>							      
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> KHÔNG</span>
	       		</button>
	       		<button type="button" onclick="saveUser();" class="btn btn-success" data-dismiss="modal" aria-label="Close" style="float: right;margin-right: 10px;">
	       			<span class="glyphicon glyphicon-ok btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> CÓ</span>
	       		</button>
	       		
	      </div>
	    </div>
	  </div>
	</div>	
	<!-- ----------------------------------------------------------------------------> 
 <div id="msgDialog" title="Thông báo">
	<div class="isa_info" align="center">
		<span style="font-size: 25"></span>
	</div>
	<div class="error_msg"></div>
</div>
<div id="dialog-confirm"></div>

<script type="text/javascript">
	function doSubmitSave(form) {
		
		//$('.modal').show();
		
		var flag = 0;
		var userid =$("#userId").val();		
		var res = userid.toUpperCase();	
		var ror_msg = '';
		$("#userId").val(res);
		//alert($("#userId"));   
		if ($("#firstName").val() == "") {
			flag = flag + 1;			
			//$(".error_msg").html("Bắt buộc nhập vào tên.");	
			ror_msg = 'Bắt buộc nhập vào tên.';
		}else if($("#lastName").val() == ""){
			flag = flag + 1;			
			//$(".error_msg").html("Bắt buộc nhập vào họ.");	
			ror_msg = 'Bắt buộc nhập vào họ.';
		}else if($("#siteCode").val() == ""){
			flag = flag + 1;			
			//$(".error_msg").html("Bắt buộc nhập vào mã trung tâm.");	
			ror_msg = 'Bắt buộc nhập vào mã trung tâm.';
		}else if($("#userId").val() == ""){
			flag = flag + 1;			
			//$(".error_msg").html("Bắt buộc nhập tên đăng nhập.");	
			ror_msg = 'Bắt buộc nhập tên đăng nhập.';
		}
		  
		if($('#password').length>0){
			
			if ($("#transactionType").val() == 'EDIT' || !userIsSelectedFromAD()) {
			
				if($("#password").val() == ""){
					flag = flag + 1;			
					//$(".error_msg").html("Bắt buộc phải nhập mật khẩu.");	
					ror_msg = 'Bắt buộc nhập tên đăng nhập.';
				} else if(!validatePassword()){
					flag = flag + 1;		
					//$(".error_msg").html("Mật khẩu phải có ít nhất 8 ký gồm chữ và số và phải có ít nhất một chữ hoa. \nVà không chứa tên Tài khoản của người dùng.");		
					ror_msg = 'Mật khẩu phải có ít nhất 8 ký tự gồm chữ và số và phải có ít nhất một chữ hoa.';
				} else if($("#confirmPwd").val() == ""){
					flag = flag + 1;
					//$(".error_msg").html("Xác nhận mật khẩu là bắt buộc.");
					ror_msg = 'Xác nhận mật khẩu là bắt buộc.';	
				} else if(!($("#password").val() == $("#confirmPwd").val())){
					flag = flag + 1;		
					ror_msg = 'Mật khẩu và xác nhận mật khẩu phải giống nhau.';	
					//$(".error_msg").html("Mật khẩu và xác nhận mật khẩu phải giống nhau.");	
				}
			
			}
			
		} 
		
		<c:if test="${empty userEdit}">
			if($.trim($("#userId").val()).length > 0
					&& existsInAd($("#userId").val()) && !userIsSelectedFromAD()){
				flag = flag + 1;		
				//$(".error_msg").html("Tên người dùng đã tồn tại.");	
				ror_msg = 'Tên người dùng đã tồn tại.';	
			}		
			
			if($.trim($("#userId").val()).length > 0
					&& existsInDb($("#userId").val())){
				flag = flag + 1;		
				//$(".error_msg").html("Tên này đã được dùng.");	
				ror_msg = 'Tên này đã được dùng.';	
			}	
		</c:if>
		
		//$('.modal').hide();
		
		if (flag > 0) {			
			//$("#msgDialog").dialog("open");
			$.notify(ror_msg, {
				globalPosition: 'bottom left',
				className: 'warn',
			});
		}else{			
			$('#unassignedRoles option').prop('selected', 'selected');
		    $('#assignedRoles option').prop('selected', 'selected');
		    $('#messageLHS').modal();
		    //$('#messageLHS').appendTo("body") 
		    //$("#dialog-confirm").dialog('option', 'title', 'Tạo người dùng mới');
		    //$("#dialog-confirm").html('Bạn có chắc chắn muốn lưu người dùng :   '+$("#userId").val());
		    //$("#dialog-confirm").dialog( 'open' );
		    
		}
	}
	$(function() {
	    $( "#dialog-confirm" ).dialog({
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
	    	// if(validatePassword()){
		    	 $('.modal').show();
		    	 saveUser();
	    	// }
	    	
	    },
		Cancel: function() {
			$(this).dialog("close");
	    }
	   }
	    });
	  });
	
function validatePassword(){
		
		var pswd = $('#password').val();
		var uname = $('#userId').val();
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
	
	function validPassword(){
		
		var pswd = $('#password').val();

		if ( pswd.length < 8 ) {
			$('#length').removeClass('valid').addClass('invalid');
		} else {
			$('#length').removeClass('invalid').addClass('valid');
		}


		if ( pswd.match(/[A-z]/) ) {
			$('#letter').removeClass('invalid').addClass('valid');
		} else {
			$('#letter').removeClass('valid').addClass('invalid');
		}

		//validate capital letter
		if ( pswd.match(/[A-Z]/) ) {
			$('#capital').removeClass('invalid').addClass('valid');
		} else {
			$('#capital').removeClass('valid').addClass('invalid');
		}

		//validate number
		if ( pswd.match(/\d/) ) {
			$('#number').removeClass('invalid').addClass('valid');
		} else {
			$('#number').removeClass('valid').addClass('invalid');
		}
		
		
	}
	function saveUser(){
		
		 document.getElementById('userId').disabled=false;
    	 document.forms["form"].siteCode.value=$("#siteCode").val();
    	 document.forms["form"].submit();
    	 document.getElementById('userId').disabled=true;
    	 
	}
	function goBack(){
		document.forms["form"].action = '${userJobListUrl}';
		 form.submit();
	}
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
function IsEmail(email) {
		  var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		  return regex.test(email);
}
$(function() {
	
	$('#userId').keyup(function(){
	    this.value = this.value.toUpperCase();
	});
	
	/* $('input[type=password]').keyup(function() {
		validPassword();
	}).focus(function() {
		$('#pswd_info').show();
	}).blur(function() {
		$('#pswd_info').hide();
	});
	 */
	 
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
});

</script>





<c:if test="${empty userEdit}">	

	
	<input id="userSelectedFromAD" type="hidden"/>
	
	<div style="display: none" id="selectFromAD" title="Select From Active Directory" >
	  
	    <div> 
	    	<div style="margin-left:  13%; width:  82%; clear:both; margin-top:  5px;">  
	    		<div style="display:  inline-block; float:  left; width:  38%">
			    	<div style="display:  inline-block; float:  left;" > 
						First Name 
					</div>  
			    	<div style="display:  inline-block; float:  right;"> 
						<input style="display:  inline-block; float:  left;"  id="searchADFirstName"  class="defaultText" type="text" size="30" maxlength="30" />   
					</div> 
				</div> 
	    		<div style="display:  inline-block; float:  left; width:  38%; margin-left:  3%">
			    	<div style="display:  inline-block; float:  left;" > 
						User Name
					</div>  
			    	<div style="display:  inline-block; float:  right;"> 
						<input style="display:  inline-block; float:  left;"  id="searchADUserName"   class="defaultText" type="text" size="30" maxlength="30" />   
					</div> 
				</div>
			</div>
	    
	    	<div style="width:  100%; clear:both; min-height: 3px;"></div>
	    	<div style="margin-left:  13%; width:  82%; clear:both; margin-top: 5px;">  
	    		<div style="display:  inline-block; float:  left; width:  38%">
			    	<div style="display:  inline-block; float:  left;" > 
						Middle Name 
					</div>  
			    	<div style="display:  inline-block; float:  right;"> 
						<input style="display:  inline-block; float:  left;"  id="searchADMiddleName" class="defaultText" type="text" size="30" maxlength="30" />
					</div> 
				</div>  
			</div>
	    
	    	<div style="width:  100%; clear:both; min-height: 3px;"></div>
	    	<div style="margin-left:  13%; width:  82%; clear:both; margin-top: 5px;">  
	    		<div style="display:  inline-block; float:  left; width:  38%">
			    	<div style="display:  inline-block; float:  left;" > 
						Surname
					</div>  
			    	<div style="display:  inline-block; float:  right;"> 
						<input style="display:  inline-block; float:  left;"  id="searchADSurname"    class="defaultText" type="text" size="30" maxlength="30" />   
					</div> 
				</div>  
			</div> 
			
	    	<div style="clear:both; min-height: 5px;"> </div> 
			<div style="width:  100%">  
				<div   style="             display:  inline-block; margin-left:  37%;"                        >&nbsp;</div>
				<input style="width:   7%; display:  inline-block;                                       "    class="button_grey"     type="button" value="Search" id="selectFromAD_search_button" onclick="selectFromAD_search();" /> 
				<input style="width:   7%; display:  inline-block; margin-left:   1%; margin-right:   1%;"    class="button_grey"     type="button" value="Reset"  id="selectFromAD_reset_button"  onclick="selectFromAD_reset();"  />  
				<input style="width:   7%; display:  inline-block;                                       "    class="button_grey"     type="button" value="Close"  id="selectFromAD_close_button"  onclick="selectFromAD_close();"  />
				<div   style="             display:  inline-block;                    margin-right:    37%;"  >&nbsp;</div> 
			</div>				
		</div>
	
	    <div style="clear:both; min-height: 5px;"> </div>
	    
		<div id="fortable" style="display: none">
			<table id="adList">
			   <tr>
					<td colspan=4>&nbsp;</td>
				</tr>
			</table>  
	                               
		</div>
	</div>	
	
	<c:url var="storeADSearchKeyToSessionUrl" value="/servlet/adminConsole/userManagement/storeADSearchKeyToSession" />
	
	<script type="text/javascript"> 
	
		$(function() {
			    $( "#selectFromAD" ).dialog({
					  modal: true,
				      autoOpen: false,
					  width : 1200,
					  height: 590,
					  resizable: false,
				      show: {
				        effect: "fade",
				        duration: 1000
				      },
				      hide: { }
			    });
		}); 
			
		function selectFromAD_search(){
			
		 	$("#selectFromAD_search_button").attr("disabled", true);
		 	$("#selectFromAD_close_button" ).attr("disabled", true);
		 	$("#selectFromAD_reset_button" ).attr("disabled", true);
		 	
		 	if (
		 			$.trim($('#searchADFirstName').val()).length  == 0   		
		 	     && $.trim($('#searchADMiddleName').val()).length == 0  		
		 	     && $.trim($('#searchADSurname').val()).length    == 0  		
		 	     && $.trim($('#searchADUserName').val()).length   == 0
		 	){
		 		//alert ('Please input at least 1 search key.'); 
				$(".error_msg").html("Vui lòng nhập từ khóa tìm kiếm.");	
				$("#msgDialog").dialog("open");

				$("#selectFromAD_search_button").removeAttr("disabled");
				$("#selectFromAD_close_button" ).removeAttr("disabled");
				$("#selectFromAD_reset_button" ).removeAttr("disabled");
				
				return;
		 	}
		 	
			$.ajax({
				type : "GET",
				url : '${storeADSearchKeyToSessionUrl}' + '/' + getKeyData($('#searchADFirstName').val()) + '/' + getKeyData($('#searchADMiddleName').val()) + '/' + getKeyData($('#searchADSurname').val()) + '/' + getKeyData($('#searchADUserName').val()) 
					+ '/' + getAUrlUniquifier()
					,
				success : function(data) {
					
					if (data == '<%=com.nec.asia.nic.security.usermanagement.UserManagementController.SUCCESS%>'){ 

						
						//$('#adList').empty();
						//$("#fortable").show();  
						//$( ".sDiv" ).show();
						//$('#adList').show();
						//$("#adList").flexOptions({
						//	url: "${adListUrl}",
						//	newp : 1
						//}).flexReload(); 
						
						$("#adList").flexReload(); 
					

						$("#selectFromAD_search_button").removeAttr("disabled");
						$("#selectFromAD_close_button" ).removeAttr("disabled");
						$("#selectFromAD_reset_button" ).removeAttr("disabled");
					}else{
						//alert("Failed when connecting to the server  (to store search keys).  You will be redirected to the main User Management Page after you close this message.");
						$.alert({
								title: 'Thông báo',
								content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Không thể kết nối với máy chủ!",
							});
						goBack();
					}
				},
				error : function(e) {
					//alert("Failed when connecting to the server  (to store search keys).  You will be redirected to the main User Management page after you close this message.");
					$.alert({
								title: 'Thông báo',
								content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Không thể kết nối với máy chủ!",
							});
					goBack();
				}
			}); 
		}
			
		function getAUrlUniquifier(){
			return (new Date().getTime()).toString();
		}
			
		function getKeyData(aData){ 
			if ($.trim(aData).length > 0){ 
				return $.trim(aData);
			}else{ 
				return '<%=com.nec.asia.nic.security.usermanagement.UserManagementController.EMPTY_INPUT%>';
			}
		}
			
		function selectFromAD_close(){
			$("#selectFromAD").dialog( "close" );
		}
			
		function selectFromAD_reset(){  
			
			$('#searchADFirstName').val('');
			$('#searchADMiddleName').val('');
			$('#searchADSurname').val('');
			$('#searchADUserName').val(''); 
			
		}
	</script>
	
	<c:url var="existsInAdUrl" value="/servlet/adminConsole/userManagement/existsInAd" />
	
	<script type="text/javascript">
		
		function existsInAd(aUserId){
			
			var existsInAd_saveResult = '';
		
			$.ajax({
				type : "GET",
				url : '${existsInAdUrl}/' + aUserId + '/' + getAUrlUniquifier(),
				success : function(data) {

					if (data == '<%=com.nec.asia.nic.security.usermanagement.UserManagementController.ERROR%>'){
						//alert("Failed when connecting to the server  (to check if User Name exists in Active Directory).  You will be redirected to the main User Management Page after you close this message.");
						$.alert({
								title: 'Thông báo',
								content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Không thể kết nối với máy chủ!",
							});
						goBack();
					}
					
					existsInAd_saveResult = data;
				},
				error : function(e) {
					//alert("Failed when connecting to the server  (to check if User Name exists in Active Directory).  You will be redirected to the main User Management page after you close this message.");
					$.alert({
								title: 'Thông báo',
								content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Không thể kết nối với máy chủ!",
							});
					goBack();
				},
		        async: false
			}); 
			
			return (existsInAd_saveResult == '<%=com.nec.asia.nic.security.usermanagement.UserManagementController.BOOLEAN_STRING_TRUE%>');
		}
		
		function userIsSelectedFromAD(){
		
		   return ($("#userSelectedFromAD").val() == 'true');
		}
		
		function doSelectFromAD(){
			
			   $("#selectFromAD").dialog( "open" );
		}
	</script>
	
	<c:url var="existsInDbUrl" value="/servlet/adminConsole/userManagement/existsInDb" />
	
	<script type="text/javascript">
		
		function existsInDb(aUserId){
			
			var existsInDb_saveResult = '';
		
			$.ajax({
				type : "GET",
				url : '${existsInDbUrl}/' + aUserId + '/' + getAUrlUniquifier(),
				success : function(data) {

					if (data == '<%=com.nec.asia.nic.security.usermanagement.UserManagementController.ERROR%>'){
						//alert("Failed when connecting to the server  (to check if User Name exists in DB).  You will be redirected to the main User Management Page after you close this message.");
						$.alert({
								title: 'Thông báo',
								content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Không thể kết nối với máy chủ!\n(Để kiểm tra tên người dùng có tồn tại tròn db không)",
							});
						goBack();
					}
					
					existsInDb_saveResult = data;
				},
				error : function(e) {
					//alert("Failed when connecting to the server  (to check if User Name exists in DB).  You will be redirected to the main User Management page after you close this message.");
					$.alert({
								title: 'Thông báo',
								content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Không thể kết nối với máy chủ!\n(Để kiểm tra tên người dùng có tồn tại tròn db không)",
							});
					goBack();
				},
		        async: false
			}); 
			
			return (existsInDb_saveResult == '<%=com.nec.asia.nic.security.usermanagement.UserManagementController.BOOLEAN_STRING_TRUE%>');
		} 
	</script>
		

		
	<c:url var="adListUrl" value="/servlet/adminConsole/userManagement/adList" />

	<script type="text/javascript">
	
		$("#adList").flexigrid({			
			url: "${adListUrl}",
			dataType : 'json',
			colModel : [	
						{display: 'User Name'  , name  : 'userId'    , width : 230, sortable : false, align: 'left'},			
						{display: 'First Name' , name  : 'givenName' , width : 230, sortable : false, align: 'left'},
						{display: 'Middle Name', name  : 'middleName', width : 230, sortable : false, align: 'left'},
						{display: 'SurName'    , name  : 'surname'   , width : 230, sortable : false, align: 'left'},
						{display: 'Select'                           , width : 180, sortable : false, align: 'center',render: selectButton}						
			], 
			sortname: "userId",
			sortorder: "asc",
			title : 'Select From Active Directory',
			usepager : true,
			useRp : true,
			rp : 10,
			showTableToggleBtn : true,   
			height: 315,
			singleSelect : true,
			nowrap : false		
		}); 	
		 
		function isAnAcceptableUserIdForDb(key){
		    var regex = new RegExp("^[a-zA-Z0-9]+$");
		    return regex.test(key); 
		}	 
		 
		function selectButton(content, currentRow){	 
			
			if (!(isAnAcceptableUserIdForDb($.trim(currentRow.userId)))){ 
				return '<input class="button_grey_disabled" type="button"  value="Select" style="font-size: .7em" '  
				        + ' disabled '    
				        + ' title="Invalid User Name.  Valid characters in a User Name are:  a to z, A to Z, and 0 to 9." '  
						+ '/>';
			}
			
			return '<input class="button_grey" type="button" style="font-size: .7em" value="Select" onclick="selectThisADuser(' 
					      + "'" + currentRow.givenName   + "'"
					+ ',' + "'" + currentRow.surname     + "'"
					+ ',' + "'" + currentRow.userId      + "'"  
					+ ',' + "'" + currentRow.employeeId  + "'"   
					+ ',' + "'" + currentRow.email       + "'"  
					+ ',' + "'" + currentRow.username    + "'"  					
					+ ')"'
					+ '/>';
		} 	
		 
		function getACleanVersionOfString(data){
			if (data == null || data == 'null') {
			     return ''; 
			} else {
			     return data; 
			}
		}	 
		
		function selectThisADuser(firstName, surName, userId, employeeId, email, userName){
			
			$("#userSelectedFromAD").val('true');

			$("#selectFromAD").dialog("close");
			
			$('.modal').show();

			$("#password").val('');  
			$("#confirmPwd").val('');  

			$("#passwordRow").hide(); 
			$("#confirmPasswordRow").hide();

			$("#userId").val($.trim(userId.toUpperCase())); 
			
			$("#userId").attr("disabled", true);
			
			$("#firstName").val(getACleanVersionOfString(firstName)); 
			
			$("#lastName").val(getACleanVersionOfString(surName));    
			
			$("#siteCode").val(""); 		

			$("#middleInitial").val(getACleanVersionOfString(employeeId));  		

			$("#email").val(getACleanVersionOfString(email));  
			
			$("#userName").val(userName);   
			 
			$('.modal').hide();
			 
		} 
	</script>
	
	<script type="text/javascript">
		$(document).ready(
			function() {$("#selectFromADButton").removeAttr("disabled");
		});
		
		$(document).ready(function() {
			$('#siteCode').change(function(){
				 var siteId = $('#siteCode').val();
				 var url = '${loadDepartmentUrl}/' + siteId;
			     $.ajax({
						type : "GET",
						url : url,					
						success: function(data) {
							$("#department option").remove();
							$("#department").append(new Option("--- Chọn ---", ""));	
							for(var i = 0; i < data.length; i++){
								$("#department").append(new Option(data[i].value, data[i].key));								
							}
						
				        },
				        error: function(e){}
				 });
			}); 
		});
		
	
	</script>

</c:if>	
	
	<div class="modal">
			<!-- Place at bottom of page -->
	</div>
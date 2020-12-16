<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
<style>
#ajax-load-qa {
	background: rgba(255, 255, 255, .7)
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
.btn-default {
	width: 60px;
}
.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
    white-space: pre-wrap;
}
.img-logo img{
	width:335px;
	position: relative;
	height: 335px;
}
.none-padding{
	padding-left:0;
	padding-right:0
}
.input-group{
	margin-top:15px;
	padding:3px 0px;
	background: rgba(255,255,255,0.6);
	border-radius: 6px;
	width: 350px;
	margin-bottom: 15px;
}

.input-group .form-control,.input-group-addon{
	border:0;
	box-shadow: none;
	background: none;
	color: #0c3774;
}
.input-group-addon{
	padding: 3px 10px;
	background: none;
	border-right: 2px solid #cfb97d!important;
}
.logo_login {
	width: 350px;
}
.width-17{
	width:17px
}
.dangnhap button{
	background:#e50102;
	border:1px solid #e50102;
	padding: 7px 20px;
	color: #fff;
	border-radius:5px;
	/*font-family: 'Arial_bold';*/
	font-size: 16px;
	/* margin-top:25px */
	position:relative;
	left:0px;
	top:0px;
	-webkit-transition: all 0.3s;
	-moz-transition: all 0.3s;
	-o-transition: all 0.3s;
	transition: all 0.3s;
    font-weight:bold;
}
.dangnhap button:hover{
	
	left:5px;
	top:-5px;
}
.input-group.none button{
	border-left:2px solid #cfb97d;
	height: 36px;
}
.input-group.none button:hover{
	background:#fff
}
body, form {
	background: url('<c:url value="/resources/images/bg_body.png" />') no-repeat !important;
	background-size: 100% !important;
	
}
</style>
<script type="text/javascript">	
$(document).ready(function() {
	/* $( "#loginPwdBtn" ).hide(); */
	$('#account').focus();
	
	$( "#resetBtn" ).click(function() {
		$( "#loginForm")[0].reset();
		if($("#pwdDiv").is(':visible')) {
			$( "#loginPwdBtn" ).hide();
			$( "#userDiv" ).addClass( "nobottomborder" );
			$( "#pwdDiv" ).hide();
			$( "#loginBtn" ).show();
		} 
	});
	
	$('#account').keyup(function(){
	    this.value = this.value.toUpperCase();
	});

	$('#account').keydown(function(event){ 
	    var keyCode = (event.keyCode ? event.keyCode : event.which);
	    if (keyCode == 13) {
		    if($( "#loginPwdBtn" ).is(":visible")) {
		    	$("#password").focus();
			} else {
				doSubmit(this.form);
			}
	    }
	});

	$('#password').keydown(function(event){ 
	    var keyCode = (event.keyCode ? event.keyCode : event.which);
	    if (keyCode == 13) {
	    	doSubmitLogin(this.form);
	    }
	});
});

function doSubmit(form) {
	var userId = $("#account").val();
	$.ajax({
		cache: false,
        type: "POST",
        url: '${pageContext.request.contextPath}/servlet/user/loginChk',
        data: "userId=" +userId ,
        success: function(response) {
        if(response=="Y"){
       		$( "#pwdDiv" ).show();
   			$( "#loginPwdBtn" ).show();
   			$( "#loginBtn" ).hide();
   			$( "#userDiv" ).removeClass( "nobottomborder" );
   			$('#password').focus();
       	} else {
       		if(document.EppApplet) {
       			var EppApplet=document.EppApplet;
       			EppApplet.setDebug(true);
       			var result = EppApplet.fingerprintLogin(userId);
   				if(result===true || result=='true') {
   					//$('#workstationId').val(document.EppApplet.getWorkStationId());
   					$('#workstationId').val("DESKTOP-L1PAIR3");
   					form.submit();
   				}else{
   					//alert("Login Failed !!!.Please try again");
   					$.alert({
   					    title: 'Thông báo',
   					    content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/secure.png\">" + " Đăng nhập không thành công! \n Xin hãy thử lại",
   					});
   				}
   			} else {
   				$.alert({
				    title: 'Thông báo',
					content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/secure.png\">" + " Không tải được Applet",
				});
   			}
       	 }
       },
        error: function(e){
        //alert('Error: ' + e);
        	$.alert({
			    title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/secure.png\">" + " Đã có lỗi xảy ra: " + e,
			});
        }
     });
}
function doSubmitLogin(form){
	$('#ajax-load-qa').css('display', 'block');
	var userId = $("#account").val();
	$.ajax({
		cache: false,
        type: "POST",
        url: '${pageContext.request.contextPath}/servlet/user/loginChk',
        data: "userId=" +userId ,
        success: function(response) {
        if(response=="Y"){
        	//$('#workstationId').val(document.EppApplet.getWorkStationId());
        	$('#workstationId').val("DESKTOP-L1PAIR3");
        	form.submit();
       	} else {
       		$('#ajax-load-qa').css('display', 'none');
       		if($("#pwdDiv").is(':visible')) {
    			$( "#loginPwdBtn" ).hide();
    			$( "#userDiv" ).addClass( "nobottomborder" );
    			$( "#pwdDiv" ).hide();
    			$( "#loginBtn" ).show();
    		}
       		if(document.EppApplet) {
	   			var EppApplet=document.EppApplet;
	   			EppApplet.setDebug(true);
	   			var result = EppApplet.fingerprintLogin(userId);
				if(result===true || result=='true') {
					$('#workstationId').val(document.EppApplet.getWorkStationId());
					form.submit();
				} else {
					//alert("Đăng nhập không thành công");
					$.alert({
   					    title: 'Thông báo',
   					    content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/secure.png\">" + " Đăng nhập không thành công! \n Xin hãy thử lại",
   					});
				}
			} else {
				$.alert({
				    title: 'Thông báo',
					content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/secure.png\">" + " Không tải được Applet",
				});
			}
       	 }
       },
        error: function(e){
        	$('#ajax-load-qa').css('display', 'none');
        	$.alert({
				title: 'Thông báo',
			    content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/secure.png\">" + " Error: " + e,
			});
        }
     }); 
	
	return;
}
</script>

<div id="dividerRed" class="displayOn">&nbsp;</div>
<c:url var="url" value="/servlet/user/login"/>
<c:url var="loginCheckUrl" value="/servlet/user/loginChk"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"></link>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/login_style.css"/>"></link>	


<div>
	<div>
		<form:form name="loginForm" modelAttribute="user" action="${url}" method="POST"  cssClass="inline" cssStyle="" id="loginForm">			
			<input cssClass="defaultText" value="" id="tokenId.field" name="tokenId" maxlength="20" style="display: none;" />
			<input cssClass="defaultText" value="" id="tokenCounter.field" name="tokenCounter" maxlength="20" style="display: none;" />
			<input cssClass="defaultText" value="" id="workstationHostname.field" name="workstationHostname" maxlength="20" style="display: none;" />
			<c:if test="${not empty requestScope.Errors}">
				<div style="color:Red;border-style:solid;border-color:Red;border-width:thin;">
					<c:forEach items="${requestScope.Errors}" var="takla">
						<li>
							${takla}
						</li>
					</c:forEach>
					
				</div>
			</c:if>
			<div class="row">
			<div class="col-md-2"></div>
				<div class="col-md-6" style="margin-top: 10%;margin-left: 60px;">
					<div class="col-md-5">
						<div class="none-padding img-logo">
			                <img class="logo" src="<c:url value="/resources/images/logo_xnc.png" />">
			            </div>
					</div>
					<div class="col-md-7 dangnhap" style="padding-left: 100px;padding-top: 50px;">
						<img class="logo_login" src="<c:url value="/resources/images/tieude.png" />">
								<div class="input-group">
			                        <span class="input-group-addon"><img class="width-17" src="<c:url value="/resources/images/user.png" />"></span>
			                        <input type="text" name="account" id="account" placeholder="Nhập tên đăng nhập" class="form-control" />                   
			                    </div>
			                    <div class="input-group">
			                        <span class="input-group-addon"><img class="width-17" src="<c:url value="/resources/images/pass.png" />"></span>
			                        <input type="password" name="password" id="password" autocomplete = "off" placeholder="Nhập mật khẩu" class="form-control" />              
			                    </div>
			                   <button type="button" id="loginPwdBtn"  onclick="return doSubmitLogin(this.form);">ĐĂNG NHẬP</button>
					</div>
				</div>
			<div class="col-md-3"></div>
	</div>
			<!--<div><h1><spring:message code="label.login.title.nic" text="Đăng nhập" /></h1></div>
					<div>
						<label class="inputtext"><spring:message code="label.user.id" text="Tên đăng nhập" />: </label>
							<input type="text" name="account" id="account" />
					</div>
					<div>
						<label class="inputtext"><spring:message code="label.user.password" text="Mật khẩu" />: </label>
						<input type="password" name="password" id="password" autocomplete = "off"/>
					</div>
					<div class="button">
						<div>
							
							 <input class="btn-primary" type="button" value="Làm mới" id="resetBtn" /> 
							 <input class="btn-primary" type="button" value="Đăng nhập" id="loginPwdBtn"  onclick="return doSubmitLogin(this.form);"/>
							 
						</div>
						<a href="#">NAB team</a>
					</div>-->
			<div id="ajax-load-qa"></div>		
			<input type="hidden" name="workstationId" id="workstationId" />
			<input id="redirect" name="redirect" type="hidden" value="${redirect}" />
		</form:form>

	</div>
</div>

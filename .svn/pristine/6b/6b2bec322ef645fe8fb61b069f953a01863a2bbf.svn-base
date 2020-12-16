<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>



<c:url var="saveParamUrl" value="/servlet/parameterController/save"/>
<c:url var="cancel" value="/servlet/parameterController/getParamList"/>

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
.fix-bottom {
	margin-top: 15px;
}
/* Anytime the body has the loading class, our
   modal element will be visible */
body.loading .modal {
	display: block;
}
 </style>
 <!-- <div id="main"> -->
 <div id="content_main">
<form:form  modelAttribute="parameterForm" id="paramForm" name="paramForm" method="GET">
<div class="container">
                        <div class="row">
                            <div class="ov_hidden">	
	<div class="new-heading-2">THÊM THÔNG SỐ HỆ THỐNG</div>
	<div id="global_icon_div"></div>

<!--<div class="border_success" style="display: none">

<div class="success_left">
 <img align='left' src="<c:url value="/resources/images/success.jpg" />" width="30" height="30" style="padding:5px 0 0 15px"/>
 
 </div>
<div class="success">
 <table width="800" height="30" border="0" cellpadding="5">
<tr> 
    <td width="587" height="28" style="font-size: 18px; font-weight:bold; color: #000;">Đã tạo thành công chi tiết thông số <p class="text_pad">&nbsp;<c:out value="" /></p>
    
  </tr>
 </table>
</div>
</div>


<div class="border_error" style="display: none">

<div class="success_left">
 <img align='left' src="<c:url value="/resources/images/alert.png" />"  width="30" height="30" style="padding:5px 0 0 15px"/>
 </div>
<div class="errors">

<table width="800" height="30" border="0" cellpadding="5">

  <tr>
    <td width="587" height="28" style="font-size: 18px; font-weight:bold; color: #000;">Không thể tạo chi tiết thông số<p class="text_pad">&nbsp; <c:out value="" /></p>
  </tr>
 
</table>

</div>
</div>-->
	<fieldset>
		<legend>Thông tin tham số hệ thống</legend>
	
		<div class="col-md-12">
			<div class="col-md-6">
				<div class="col-md-4 fix-bottom">
					<label for="countryCode">Tên thông số<i style="color: red">(*)</i></label>
				</div>
				<div class="col-md-8 fix-bottom">
					<form:input  id="paraName" path="paraName" class="form-control" type="text" size='50'/>
				</div>
				<div class="col-md-4 fix-bottom">
					<label for="countryCode">Mô tả về thông số<i style="color: red">(*)</i></label>
				</div>
				<div class="col-md-8 fix-bottom">
					<form:input id="paraDesc" path="paraDesc" class="form-control" type="text" size='50'></form:input>
				</div>
				<div class="col-md-4 fix-bottom">
					<label for="countryCode">Thông số giá trị ngắn<i style="color: red">(*)</i></label>
				</div>
				<div class="col-md-8 fix-bottom">
					<form:input id="paraValue" path="paraValue" type="text" class="form-control" size='50'></form:input>
				</div>
				<div class="col-md-4 fix-bottom">
					<label for="countryCode">Phạm vi thông số<i style="color: red">(*)</i></label>
				</div>
				<div class="col-md-8 fix-bottom">
					<form:select id="paraScope" path="paraScope" class="form-control">   	     
		   	         	<form:option value="AFIS" label="AFIS"/>
						<form:option value="API" label="API"/>
						<form:option value="APPLICATION" label="APPLICATION"/>
						<form:option value="CMND" label="CMND"/>
						<form:option value="DATAPACK" label="DATAPACK"/>
						<form:option value="DEMO" label="DEMO"/>
						<form:option value="PERSO" label="PERSO"/>
						<form:option value="SYSTEM" label="SYSTEM"/>
						<form:option value="TRANSMISSION" label="TRANSMISSION"/>
						<form:option value="VALIDITY_PERIOD" label="VALIDITY_PERIOD"/>
						<form:option value="WORKFLOW" label="WORKFLOW"/>    
		   	        </form:select>
				</div>
				<div class="col-md-4 fix-bottom">
					<label for="countryCode">Loại thông số<i style="color: red">(*)</i></label>
				</div>
				<div class="col-md-8 fix-bottom">
					<form:select path="paraType" id="paraType" class="form-control" items="${paraTypeList}" itemValue="key" itemLabel="key" />
				</div>
			 </div>
			 <div class="col-md-6" style="margin-top: 10px;">
			 	 <div class="col-md-4 fix-bottom">
					<label for="countryCode">Mã hệ thống<i style="color: red">(*)</i></label>
				</div>
				<div class="col-md-8 fix-bottom">
				 	<form:select id="systemId" path="systemId" class="form-control">    	     
			   	         <form:option value=""  label="--- Select ---"/>
							 <c:forEach var="maplist" items="${parameterForm.sysCodeMap}" varStatus="status1">												
								 <form:option value="${maplist.key}" label="${maplist.value}"/>
							 </c:forEach> 	      
			   	   </form:select>
				</div>
				<div class="col-md-4 fix-bottom">
					<label for="countryCode">Gắn cờ người dùng có thể truy cập</label>
				</div>
				<div class="col-md-8 fix-bottom" style="height: 40px;">
					<form:checkbox id="userAccessibleFlag" path="userAccessibleFlag" class="form-control"/>
				</div>
				<div class="col-md-4 fix-bottom">
					<label for="countryCode">Gắn cờ quản trị viên có thể truy cập</label>
				</div>
				<div class="col-md-8 fix-bottom" style="height: 40px;">
					 <form:checkbox id="adminAccessibleFlag" path="adminAccessibleFlag" class="form-control" /> 
				</div>
				<div class="col-md-4 fix-bottom">
					<label for="countryCode">Giá trị Lob thông số</label>
				</div>
				<div class="col-md-8 fix-bottom">
					<form:textarea id="paraLobValue" path="paraLobValue" cols='50' rows="5" class="form-control"/>
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
					<button type="button" class="btn btn-success" onclick="window.location.href='${requestScope['javax.servlet.forward.request_uri']}'" id="reset_btn">
						<span class="glyphicon glyphicon-refresh"></span> Làm mới
					</button>
					<button type="button" class="btn btn-success" id="save_btn">
						<span class="glyphicon glyphicon-ok"></span> Lưu
					</button>
				</div>
			</div>
		  </div>
		  <div id="ajax-load-qa"></div>	
	<!--<div id="paramTable">                 
   	<table style="width:100%;background-color:#E3F1FE" cellspacing="0"class="data_table" cellpadding="0">   			 
   			<tr><td width="20%">Tên thông số<span style="COLOR: #ff0000;">*</span>: </td><td><form:input  id="paraName" path="paraName" type="text" size='50'/></td></tr>   			 			   
   			<tr><td>Mô tả về Thông Số<span style="COLOR: #ff0000;">*</span>: </td><td><form:input id="paraDesc" path="paraDesc" type="text" size='50'></form:input></td></tr>   			
    		<tr><td>Thông số giá trị ngắn<span style="COLOR: #ff0000;">*</span>: </td><td><form:input id="paraValue" path="paraValue" type="text" size='50'></form:input>
    		<tr><td>Giá trị Lob thông số<span style="COLOR: #ff0000;"></span>: </td><td><form:textarea id="paraLobValue" path="paraLobValue" cols='50' rows="10" /></td></tr>
    		<tr><td>Phạm vi thông số<span style="COLOR: #ff0000;">*</span>: </td>
    		<td>
   	        <form:select id="paraScope" path="paraScope">   	     
   	         <form:option value="Hệ thống" label="System"/>
   	          <form:option value="Ứng dụng" label="Application"/>   	      
   	        </form:select>
   	        </td>   		   
   		 </tr> 
   		 <tr>
    		<td>Loại thông số<span style="COLOR: #ff0000;">*</span>: </td>
    		<td>
   	         <form:select path="paraType" id="paraType" items="${paraTypeList}" itemValue="key" itemLabel="key" />
   	        </td>
   	     </tr> 
   	     <tr>
   	       	<td>Mã hệ thống<span style="COLOR: #ff0000;">*</span>:</td>
   	       	<td>
   	        <form:select id="systemId" path="systemId">   	     
   	         <form:option value=""  label="--- Select ---"/>
							<c:forEach var="maplist" 
									items="${parameterForm.sysCodeMap}" varStatus="status1">												
									<form:option value="${maplist.key}" label="${maplist.value }"/>
							</c:forEach> 	      
   	        </form:select>
   	        </td>
   	       </tr>
    		<tr>
    		<td>Gắn cờ người dùng có thể truy cập:</td>
    		<td>
   	        <form:checkbox id="userAccessibleFlag" path="userAccessibleFlag"/>   	         
   	        </td>
   	        </tr>
   	         <tr>
   	         <td>Gắn cờ quản trị viên có thể truy cập:</td>
   	         <td>
   	        <form:checkbox id="adminAccessibleFlag" path="adminAccessibleFlag"/>   	         
   	        </td>
   	        </tr>		 			
	   </table>
  

  </div> -->
   <!--<div id="upddatebuttonsTable" align="right" style="background-image:url('<%--=request.getContextPath() --%>/resources/images/head.png');background-repeat: repeat-x;">                    
      <table >
    	<tr>
        	<td colspan="5" align="center" style="padding: 20px;">
        	 
        	  <input type="button"  class="btn_small btn-primary" id="save_btn" value="Lưu" style="width: 60px;"/> 
        	  <input type="button" id="reset_btn" class="btn_small btn-primary" onclick="window.location.href='${requestScope['javax.servlet.forward.request_uri']}'"
					style="width: 60px;"				value="Làm lại" />       	  
		      <input type="button" class="btn_small btn-primary" id="cancel_btn"  value="Hủy" style="width: 60px;"/>
		     <%--  <form:hidden  id="codeName"  path="codeName"/> --%>
	        </td>
	    </tr>
      </table>
   </div>-->
  </div>
  </div>
  </div>
</form:form>
</div>
<!-- </div> -->
 <div id="msgDialog" title="Thông báo">
	<div class="isa_info" align="center">
		<span style="font-size: 25"></span>
	</div>
	<div class="error_msg"></div>
</div>
<div id="savedialog-confirm"></div>
<div id="faildialog-confirm"></div>
<script>

$("#save_btn").click(function(){
	var flag = 0;
	var err_msg = '';
	if($("#paraName").val() == ""){
		flag = flag + 1;			
		//$(".error_msg").html("Vui lòng nhập tên thông số.");	
		err_msg = 'Vui lòng nhập tên thông số.';
	}else if($("#paraDesc").val() == ""){
		flag = flag + 1;			
		//$(".error_msg").html("Vui lòng nhập mô tả thông số.");	
		err_msg = 'Vui lòng nhập mô tả thông số.';
	}else if($("#paraValue").val() == ""){
		flag = flag + 1;
		//$(".error_msg").html("Vui lòng nhập giá trị thông số ngắn.");
		err_msg = 'Vui lòng nhập giá trị thông số ngắn.';	
	}else if ($("#paraScope").val() == "") {
			flag = flag + 1;			
			//$(".error_msg").html("Vui lòng chọn phạm vi thông số.");
			err_msg = 'Vui lòng chọn phạm vi thông số.';
	}else if ($("#systemId").val() == "") {
		flag = flag + 1;			
		//$(".error_msg").html("Vui lòng chọn mã hệ thống.");
		err_msg = 'Vui lòng chọn mã hệ thống.';
    }	
	
	if($("#paraType").val() == "INTEGER"){
		if(!IsNumeric(trim($("#paraValue").val()))){
			flag = flag + 1;
			//$(".error_msg").html("Giá trị ngắn của thông số không hợp lệ, hãy nhập giá trị số nguyên hợp lệ.");
			err_msg = 'Giá trị ngắn của thông số không hợp lệ. Hãy nhập giá trị số nguyên hợp lệ.';
  		}
	}
	
	if($("#paraType").val() == "BOOLEAN"){
		if($("#paraValue").val()!='true' && $("#paraValue").val()!='false'){
			flag = flag + 1;
			//$(".error_msg").html("Giá trị ngắn của thông số không hợp lệ, vui lòng nhập giá trị Boolean hợp lệ.");
			err_msg = 'Giá trị ngắn của thông số không hợp lệ. Vui lòng nhập giá trị Boolean hợp lệ.';
		}
	}
	
	if (flag > 0) {			
		
		//$("#msgDialog").dialog("open");
		$.notify(err_msg, {
			globalPosition: 'bottom left',
			className: 'warn',
		});
		
	}else{			
		 //$('.modal').show();	
		 //alert($('#paramForm').serialize());
	 $('#ajax-load-qa').show();
	 $.post('<c:url value="/servlet/parameterController/save" />',$('#paramForm').serialize(),
	            function(data){
		 $('#ajax-load-qa').hide();
		if(data=='success'){

			//$("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
		    //$("#savedialog-confirm").html('Chi tiết thông số đã lưu thành công :  '+$("#paraName").val());
		    //$("#savedialog-confirm").dialog( 'open' );
			$.notify('Thông số đã được lưu thành công.', {
				globalPosition: 'bottom left',
				className: 'success',
			});
		    
		  }else if(data=='fail'){
			  //$("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
			  //$("#faildialog-confirm").html('Không lưu được chi tiết thông số :  '+$("#paraName").val());
			  //$("#faildialog-confirm").dialog( 'open' );	
			  $.notify('Thông số không được lưu thành công.', {
					globalPosition: 'bottom left',
					className: 'warn',
				});
		  }else if(data=='exist'){
			  //$("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
			 // $("#savedialog-confirm").html('Thông số được lưu trữ trong Hệ thống. Xin vui lòng liên hệ với DB.');
			  //$("#savedialog-confirm").dialog( 'open' );	
			  $.notify('Thông số đã tồn tại trong hệ thống. Vui lòng kiểm tra lại.', {
					globalPosition: 'bottom left',
					className: 'warn',
				});
		  }
	         
	            	
	  }); 		 
	} 
});
$("#cancel_btn").click(function(){
	
	 document.forms["paramForm"].action = '${cancel}';
	 document.forms["paramForm"].submit();
	
});


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
});

$(function() {
    $("#savedialog-confirm" ).dialog({
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
        //duration: 1000
      },
	   buttons:{
    Ok: function() {    	
    	  $(this).dialog("close");  
    	  document.forms["paramForm"].action = '${cancel}';
    	  document.forms["paramForm"].submit();
    	 
    }	
   }
    });
  });
  
$(function() {
    $("#faildialog-confirm" ).dialog({
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
        //duration: 1000
      },
	   buttons:{
    Ok: function() {    	 
    	$(this).dialog("close"); 
    }	
   }
    });
  });
function IsNumeric(sText) {
	var ValidChars = "0123456789.";
	var IsNumber=true;
	var Char;
	for (i = 0; i < sText.length && IsNumber == true; i++) 
	{ 
		Char = sText.charAt(i); 
		if (ValidChars.indexOf(Char) == -1) 
		{
		IsNumber = false;
		}
	}
    return IsNumber;
}

function trim(str) {
    return str.replace(/^\s+|\s+$/g,"");
}	

</script>
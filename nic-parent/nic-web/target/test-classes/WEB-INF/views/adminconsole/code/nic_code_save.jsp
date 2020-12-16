<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>


<c:url var="codeValUrl" value="/servlet/codeMgmt/getcodevalues"/>
<c:url var="codeEditUrl" value="/servlet/codeMgmt/editCode"/>
<c:url var="codeDeleteUrl" value="/servlet/codeMgmt/deleteCode"/>
<c:url var="updateUrl" value="/servlet/codeMgmt/updateCodeValue"/>
<c:url var="url" value="/servlet/codeMgmt/view"/>
<c:url var="cancel" value="/servlet/codeMgmt/getcodevalues"/>
<c:url var="resetPage" value="/servlet/codeMgmt/requestToSaveUpdate"/>


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
body.loading .modal {
	display: block;
}
.fix-bottom {
	margin-top: 20px;
}
 </style>
<!-- <div id="main"> -->
<div id="content_main">
<form:form  modelAttribute="codeValueForm" id="codeValueEdit" name="codeValueEdit" >	
<div class="container">
                        <div class="row">
                            <div class="ov_hidden">
	<div class="new-heading-2">QUẢN LÝ MÃ DANH MỤC</div>
	<div id="global_icon_div"></div>
	<fieldset>
		<legend>Thông tin mã danh mục</legend>
	
		<div class="col-md-12">
			<div class="col-md-6">
				<div class="col-md-4 fix-bottom">
					<label for="countryCode">Tên mã<i style="color: red">(*)</i>:</label>
				</div>
				<div class="col-md-8 fix-bottom">
					 <c:choose>
			   			 <c:when test='${not empty codeValueForm.codeValue and not empty codeValueForm.codeId}'>
			   			 	<form:input id="codeId" path="codeId" class="form-control"  type="text" size='50' disabled="true"/>
			   			 </c:when>
			   			 <c:otherwise>
			   			 	<c:if test='${empty codeValueForm.codeValue and not empty codeValueForm.codeId}'>
				   			    <form:input id="codeId" path="codeId" class="form-control"  type="text" size='50' disabled="true"/>			   			
				   			</c:if>
				   			<c:if test='${empty codeValueForm.codeValue and empty codeValueForm.codeId}'>
				   			 	<form:input id="codeId" path="codeId" class="form-control"  type="text" size='50'/>
				   			</c:if>
			   			 </c:otherwise>
   			 		</c:choose>
				</div>
				<div class="col-md-4 fix-bottom">
					<label for="countryCode">Giá trị mã<i style="color: red">(*)</i>:</label>
				</div>
				<div class="col-md-8 fix-bottom">
					<c:choose>
			   			 <c:when test='${not empty codeValueForm.codeValue and not empty codeValueForm.codeId}'>			   			
			   			 	<form:input  id="codeValue" path="codeValue" class="form-control" type="text" size='50' disabled="true" />
			   			 </c:when>
			   			 <c:otherwise>
			   			 	<c:if test='${empty codeValueForm.codeValue and not empty codeValueForm.codeId}'>			   		
				   			 <form:input  id="codeValue" path="codeValue" class="form-control" type="text" size='50' />
				   			 </c:if>
				   			  <c:if test='${empty codeValueForm.codeValue and empty codeValueForm.codeId}'>				   			 
				   			 <form:input  id="codeValue" path="codeValue" class="form-control" type="text" size='50'/>
				   			 </c:if>
			   			 </c:otherwise>
		   			 </c:choose>					
				</div>
				<div class="col-md-4 fix-bottom">
					<label for="countryCode">Mã mô tả<i style="color: red">(*)</i>:</label>
				</div>
				<div class="col-md-8 fix-bottom">
					<form:input id="codeValueDesc" path="codeValueDesc" class="form-control" type="text" size='50'></form:input>
				</div>
				<div class="col-md-4 fix-bottom">
					<label for="countryCode">Mã cha<i style="color: red">(*)</i>:</label>
				</div>
				<div class="col-md-8 fix-bottom">
					<form:input id="parentCodeValue" class="form-control" path="parentCodeValue" type="text" size='50'></form:input>
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
					
		         	<c:if test='${ empty codeValueForm.displayTable}'>
		        	   <button type="button" class="btn btn-success" id="sreset_btn">
							<span class="glyphicon glyphicon-refresh"></span> Làm mới
						</button>
						<button type="button" class="btn btn-success" id="save_btn">
							<span class="glyphicon glyphicon-ok"></span> Lưu
						</button>
		        	  
		        	  </c:if>
		        	   <c:if test='${not empty codeValueForm.displayTable}'>	   
					      <button type="button" class="btn btn-success"  onclick="deleteCode()"  id="delete_btn">
								<span class="glyphicon glyphicon-trash"></span> Xóa
							</button>
							<button type="button" class="btn btn-success" id="ureset_btn">
								<span class="glyphicon glyphicon-refresh"></span> Làm mới
							</button>
							<button type="button" class="btn btn-success" onclick="updateForm()" id="update_btn">
								<span class="glyphicon glyphicon-ok"></span> Cập nhật
							</button>
				      
				      </c:if>
				</div>
		 	</div>
		</div>
		<div id="ajax-load-qa"></div>
	<!--<div id="editcodeTable">                 
    	<table style="width:100%;background-color:#E3F1FE;margin-top:20px;" class="data_table">
   	           <tr>  			
	    	   				
			 <%-- <c:if test='${empty codeValueForm.codeValue}'>
   			 <tr><td width="20%">Code Name<span style="COLOR: #ff0000;">*</span>: </td><td><form:input id="codeId" path="codeId" type="text" size='50' /></td><td></td></tr>
   			 <tr><td width="20%">Code Value<span style="COLOR: #ff0000;">*</span>: </td><td><form:input  id="codeValue" path="codeValue" type="text" size='50' /></td><td></td></tr>
   			 </c:if>
   			  <c:if test='${not empty codeValueForm.codeValue}'>
   			 <tr><td width="20%">Code Name<span style="COLOR: #ff0000;">*</span>: </td><td><form:input id="codeId" path="codeId" type="text" size='50'   disabled="true"/></td><td></td></tr>
   			 <tr><td width="20%">Code Value<span style="COLOR: #ff0000;">*</span>: </td><td><form:input  id="codeValue" path="codeValue" type="text" size='50' disabled="true" /></td><td></td></tr>
   			 </c:if> --%>
   			 
   			 <c:choose>
	   			 <c:when test='${not empty codeValueForm.codeValue and not empty codeValueForm.codeId}'>
	   			 	<tr><td width="20%">Tên mã<span style="COLOR: #ff0000;">*</span>: </td><td><form:input id="codeId" path="codeId" type="text" size='50'   disabled="true"/></td><td></td></tr>
	   			 	<tr><td width="20%">Giá trị mã<span style="COLOR: #ff0000;">*</span>: </td><td><form:input  id="codeValue" path="codeValue" type="text" size='50' disabled="true" /></td><td></td></tr>
	   			 </c:when>
	   			 <c:otherwise>
	   			 	<c:if test='${empty codeValueForm.codeValue and not empty codeValueForm.codeId}'>
		   			 <tr><td width="20%">Tên mã<span style="COLOR: #ff0000;">*</span>: </td><td><form:input id="codeId" path="codeId" type="text" size='50' disabled="true"/></td><td></td></tr>
		   			 <tr><td width="20%">Giá trị mã<span style="COLOR: #ff0000;">*</span>: </td><td><form:input  id="codeValue" path="codeValue" type="text" size='50' /></td><td></td></tr>
		   			 </c:if>
		   			  <c:if test='${empty codeValueForm.codeValue and empty codeValueForm.codeId}'>
		   			 <tr><td width="20%">Tên mã<span style="COLOR: #ff0000;">*</span>: </td><td><form:input id="codeId" path="codeId" type="text" size='50'/></td><td></td></tr>
		   			 <tr><td width="20%">Giá trị mã<span style="COLOR: #ff0000;">*</span>: </td><td><form:input  id="codeValue" path="codeValue" type="text" size='50'/></td><td></td></tr>
		   			 </c:if>
	   			 </c:otherwise>
   			 </c:choose>
   			 
   			<tr><td>Mã mô tả<span style="COLOR: #ff0000;">*</span>: </td><td><form:input id="codeValueDesc" path="codeValueDesc" type="text" size='50'></form:input></td><td></td></tr>
   			
   		     <tr><td>Mã cha<span style="COLOR: #ff0000;"></span>: </td><td>
   		     <form:input id="parentCodeValue" path="parentCodeValue" type="text" size='50'></form:input></td><td></td></tr>	  

  </table> 
  <!--</div> -->
   <!--<div id="upddatebuttonsTable"  align="right" class="displayTag" style="background-image:url('<%=request.getContextPath() %>/resources/images/head.png');background-repeat: repeat-x;">                    
      <table>
    	<tr>
        	<td colspan="5" align="center" style="padding: 20px;">
        	  <c:if test='${ empty codeValueForm.displayTable}'>
        	  <input type="button"  class="button_grey" id="save_btn" value="Lưu"/>
        	   <input type="button" class="button_grey" id="sreset_btn"  value="làm lại"/>
        	  </c:if>
        	   <c:if test='${not empty codeValueForm.displayTable}'>	   
		      <input type="button"  class="btn_small btn-primary" onclick="updateForm()" id="update_btn"  value="Cập nhật"/>
		       <input type="button" class="btn_small btn-primary" id="ureset_btn"  value="Reset"/>
		      <input type="button" class="btn_small btn-primary" id="delete_btn"  onclick="deleteCode()"  value="Xóa"/>
		      </c:if>		      
		      <input type="button" class="btn_small btn-primary" id="cancel_btn"  value="Hủy"/>
	        </td>
	    </tr>
      </table>
   </div>-->
  <form:hidden  id="codeName"  path="codeName"/>
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
	      			<p style="display: inline-block;position: absolute;margin-left: 10px;margin-top: 5px;">Bạn có muốn xóa mã này?</p>
	      		</div>
	      </div>							      
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> KHÔNG</span>
	       		</button>
	       		<button type="button" onclick="delMaDM();" class="btn btn-success" data-dismiss="modal" aria-label="Close" style="float: right;margin-right: 10px;">
	       			<span class="glyphicon glyphicon-ok btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> CÓ</span>
	       		</button>
	       		
	      </div>
	    </div>
	  </div>
	</div>	
	<!-- ---------------------------------------------------------------------------->
  </div>
  </div>
  </div>
</form:form>
 </div>
<!--  </div> -->
 <div id="msgDialog" title="Thông báo">
	<div class="isa_info" align="center">
		<span style="font-size: 25"></span>
	</div>
	<div class="error_msg"></div>
</div>
<div id="dialog-confirm"></div>
<div id="savedialog-confirm"></div>
<div id="faildialog-confirm"></div>
<script>

function delMaDM(){
	document.getElementById('codeId').disabled=false;
	document.getElementById('codeValue').disabled=false;	    
	document.forms["codeValueEdit"].action = '${codeDeleteUrl}';
	document.forms["codeValueEdit"].submit();
	document.getElementById('codeId').disabled=true;
	document.getElementById('codeValue').disabled=true;
}

$("#save_btn").click(function(){
	var flag = 0;
	var err_msg = '';   
	if ($("#codeId").val() == "") {
		flag = flag + 1;			
		//$(".error_msg").html("Vui lòng nhập tên mã");
		err_msg = 'Vui lòng nhập tên mã.';
		
	}else if($("#codeValue").val() == ""){
		flag = flag + 1;			
		//$(".error_msg").html("Vui lòng nhập giá trị mã");	
		err_msg = 'Vui lòng nhập giá trị mã.';
	}else if($("#codeValueDesc").val() == ""){
		flag = flag + 1;			
		//$(".error_msg").html("Vui lòng nhập mô tả mã");	
		err_msg = 'Vui lòng nhập mô tả mã.';
	}		
	if (flag > 0) {			
		
		//$("#msgDialog").dialog("open");
		$.notify(err_msg, {
			globalPosition: 'bottom left',
			className: 'warn',
		});
		
	}else{			
		 //$('.modal').show();
	$('#ajax-load-qa').show();
	//alert($('#editcodeTable').serialize());
	$("#codeId").attr('disabled', false);
	 $.post('<c:url value="/servlet/codeMgmt/save" />',$('#codeValueEdit').serialize(),
	            function(data){
		 $('#ajax-load-qa').hide();
		 if(data=='success'){
			
				//$("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
			    //$("#savedialog-confirm").html('Tên mã đã lưu thành công '+$("#codeId").val());
			    //$("#savedialog-confirm").dialog( 'open' );
			    $.notify('Thêm mã thành công.', {
					globalPosition: 'bottom left',
					className: 'success',
				});
			 //$(".border_error").hide();
			// $(".border_success").show();
		  }else if(data=='fail'){
			  //$("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
			  //$("#faildialog-confirm").html('Không thể lưu tên mã '+$("#codeId").val());
			  //$("#faildialog-confirm").dialog( 'open' );
			 $.notify('Không thể thêm mã.', {
				globalPosition: 'bottom left',
				className: 'warn',
			});
			 // $(".border_success").hide();
			 // $(".border_error").show();  
		  }else if(data=='exist'){
			  //$("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
			 // $("#faildialog-confirm").html('Tên mã hoặc giá trị mã được lưu trữ trong hệ thống. Xin vui lòng liên hệ với DB '+$("#codeId").val());
			  //$("#faildialog-confirm").dialog( 'open' );
				 $.notify('Mã đã tồn tại trong hệ thống.', {
					globalPosition: 'bottom left',
					className: 'warn',
				});
			 // $(".border_success").hide();
			 // $(".border_error").show();  
		  }
	         
	            	
	 }); 		 
	
    	$("#editcodeTable").show();	
	    $("#codevalueTable").hide();	
	
} 
});
$("#cancel_btn").click(function(){
	//[tuenv][13 Jan 2016] - add
	if ($("#codeName").val() == "") {
		document.forms["codeValueEdit"].action = '${url}';
	} else {
		document.forms["codeValueEdit"].action = '${cancel}/'+$('#codeName').val();
	}
	//document.forms["codeValueEdit"].action = '${cancel}/'+$('#codeName').val();
	 document.forms["codeValueEdit"].submit();
	
});
$("#sreset_btn").click(function(){
	//[tuenv][13 Jan 2016] - add
	//document.forms["codeValueEdit"].action = '${resetPage}';
	if ($("#codeName").val() == "") {
		document.forms["codeValueEdit"].reset();
	} else {
		document.forms["codeValueEdit"].action = '${resetPage}?codeId='+$("#codeId").val();
		document.forms["codeValueEdit"].submit();
	}	 
	
});
$("#ureset_btn").click(function(){
	
	var codeParames=$("#codeId").val()+"&&"+$("#codeValue").val();
	 document.forms["codeValueEdit"].action = '${codeEditUrl}/'+codeParames;
	 document.forms["codeValueEdit"].submit();
	
});
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
    	  document.getElementById('codeId').disabled=false;
    	  document.getElementById('codeValue').disabled=false;	    
    	  document.forms["codeValueEdit"].action = '${codeDeleteUrl}';
    	  document.forms["codeValueEdit"].submit();
    	  document.getElementById('codeId').disabled=true;
    	  document.getElementById('codeValue').disabled=true;
    },
	Cancel: function() {
		$(this).dialog("close");
    }
   }
    });
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
        //effect: "explode",
        //duration: 1000
      },
	   buttons:{
    Ok: function() {    	
    	  $(this).dialog("close");
    	  document.forms["codeValueEdit"].action = '${codeValUrl}/'+$("#codeId").val();
    	  document.forms["codeValueEdit"].submit();
    	 
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
        //duration: 1000
      },
	   buttons:{
    Ok: function() {    	 
    	$(this).dialog("close"); 
    }	
   }
    });
  });

function updateForm(){
	var flag = 0;	
	var err_msg = '';   
	if($("#codeValueDesc").val() == ""){
		flag = flag + 1;			
		//$(".error_msg").html("Vui lòng nhập mô tả mã");		
		err_msg = 'Vui lòng nhập mô tả mã.';
	}		
	if (flag > 0) {			
		
		//$("#msgDialog").dialog("open");
		$.notify(err_msg, {
			globalPosition: 'bottom left',
			className: 'warn',
		});
		
	}else{			
		 //$('.modal').show();
	$('#ajax-load-qa').show();
	$("#codeId").attr('disabled', false);
	$("#codeValue").attr('disabled', false);	
   //  document.forms["codeValueEdit"].action = '${updateUrl}';
	// document.forms["codeValueEdit"].submit(); 
	 
	 $.post('<c:url value="/servlet/codeMgmt/updateCodeValue" />',$('#codeValueEdit').serialize(),
	            function(data){
		 $('#ajax-load-qa').hide();
		 if(data=='success'){
			 $.notify('Mã đã được cập nhật thành công.', {
				globalPosition: 'bottom left',
				className: 'success',
			 });
				//$("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
			    //$("#savedialog-confirm").html('Tên mã cập nhật thành công '+$("#codeId").val());
			    
			   // $("#savedialog-confirm").dialog( 'open' );
			    
			 //$(".border_error").hide();
			// $(".border_success").show();
		  }else if(data=='fail'){
			  $.notify('Không thể cập nhật tên mã.', {
					globalPosition: 'bottom left',
					className: 'warn',
				});
			  //$("# faildialog-confirm").dialog('option', 'title', 'Thông báo');
			 // $("# faildialog-confirm").html('Không thể cập nhật tên mã '+$("#codeId").val());
			  //$("# faildialog-confirm").dialog( 'open' );
			 
			 // $(".border_success").hide();
			 // $(".border_error").show();  
		  }
	         
	            	
	 }); 	
	
	 $("#codeId").attr('disabled', true);
	$("#codeValue").attr('disabled', true);
	}
}
function  deleteCode(){
	
	document.getElementById('codeId').disabled=false;
	//$("#dialog-confirm").dialog('option', 'title', 'Xóa dữ liệu');
   // $("#dialog-confirm").html('Bạn có chắc muốn xóa giá trị mã '+$("#codeId").val());
    //$("#dialog-confirm").dialog( 'open' );
   	$('#messageLHS').modal();
    document.getElementById('codeId').disabled=true;
	
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

</script>
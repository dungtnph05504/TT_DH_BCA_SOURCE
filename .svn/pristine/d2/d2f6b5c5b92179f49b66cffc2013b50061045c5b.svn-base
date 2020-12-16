<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url var="submitUrl" value="/servlet/report/uploadFile"/>
 <c:url var="backToList" value="/servlet/report/backToList"/>
<c:url var="url" value="/servlet/report"/>
<c:url var="frdCnclRqUrl" value="/servlet/report/cancelToReportLoad"/>
<c:url var="reqFrdURL" value="/servlet/report/forwardReq"/>
<c:url var="frdBackPgUrl" value="/servlet/report/cancelBack"/>

<c:url var="deleteTemplateUrl" value="/servlet/report/deleteTemplate"/>

<div id="main">
	<div class="container">
	        	<div class="row">
	        		<div class="roundedBorder ov_hidden">
<!-- <div id="content_main"> -->
<form:form modelAttribute="nicReportForm"  id="nicReportForms"  name="nicReportForms"  action="${submitUrl}"  method="POST"  enctype="multipart/form-data" >
<script>
		$(function() {//
			$("#msgDialog").dialog({
				width : 600,
				height : 200,
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
<div id="heading_report" align="justify" colspan='2' style='padding:2px'>Quản lý báo cáo</div>&nbsp;
<br>
<br>
<%-- <c:if test ="${nicReportForm.status == 'success'}">
<div class="border_success">
<!--<div class="success"> -->
<div class="success_left">
 <img align='left' src="<c:url value="/resources/images/success.jpg" />" width="30" height="30"/>
 
 </div>
<div class="success">
 <table width="800" height="62" border="0" cellpadding="5">
<tr> 
    <td  id="saves" width="587" height="28" style="font-size: 18px; font-weight:bold; color: #000;">Successfully uploaded  the report template.<p class="text_pad">&nbsp;<c:out value="${nicReportForm.reportId}" /></p>
   </tr>
 </table>
</div>
</div>
</c:if>
<c:if test ="${nicReportForm.status == 'fail'}">
<div class="border_error">
<!--<div class="errors"> -->
<div class="success_left">
 <img align='left' src="<c:url value="/resources/images/alert.png" />"  width="30" height="30"/>
 </div>
<div class="errors">
<table width="800" height="62" border="0" cellpadding="5">
  <tr>
    <td  id="fails" width="587" height="28" style="font-size: 18px; font-weight:bold; color: #000;">Không thể tải lên mẫu báo cáo.<p class="text_pad">&nbsp; <c:out value="${nicReportForm.reportId}" /></p>
    </tr> 
</table>
</div>
</div>
</c:if> --%>
        <div id="categoryList">
                    <table style="width:100%;background-color:#E3F1FE" class="data_table">
                        
                            
                        <tr>
                            <td width="5%"></td>
                            <td width='10%'>Danh mục<span style="COLOR: #ff0000;">*</span>: </td>
                            <td class="description" width='50%'>
                             <form:input type="text" id="reportCategoryDesc" path="reportCategoryDesc" size='50' readonly="true"/>
                             
                              <form:hidden id="reportCategory" path="reportCategory" />
                            </td>		
                         </tr> 						                       
  						<tr id="reportnameList">
                            <td width="5%"></td>
                            <td width='10%'>Tên mẫu báo cáo<span style="COLOR: #ff0000;">*</span>: </td>
                            <td width='50%'>
                             <form:input type="text" id="reportName" path="reportName" size='50' />
                            </td>		
                            
                        </tr>                         
                        <tr id="brouseOpt">                            
                            <td width="5%" ></td>
                            <td width='10%'>Mẫu báo cáo<span style="COLOR: #ff0000;">*</span>: </td>
                         	<td width="50%">
                         		<form:input style="width:400px;"  id="fileData" path="fileData" type="file" />
                         	</td>
                          
      					</tr>                       
                        <tr id="textBox">
  	                    	  <td width="5%" ></td>
 	                   		  <td width='10%'>Truy vấn báo cáo<span style="COLOR: #ff0000;"></span>: </td>
 	                     	  <td width="50%">
   	                    		<form:textarea id="description" path="description"  rows="7" cols="70"></form:textarea>
   	                    		 <form:input type="hidden" id="reportId" path="reportId" />
   	                    		 <form:input type="hidden" id="message" path="status" />
   	                    		 <%--  <form:input type="hidden" id="reportName" path="reportName" /> --%>
    	                 	  </td>
                         </tr>
      						 <%-- <tr>
						        <td colspan="3"><form:errors path="*" element="div"
 						        class="errors" /></td>
  						    </tr>  --%>
                    </table>
                    
                    
                  </div>     
               
                <div id="upload_btn"  >
                <table width="50%" id="tablepaging" class="displayTag"   class="roundedBorder" style="background-image:url('<%=request.getContextPath() %>/resources/images/head.png');background-repeat: repeat-x;">
               	<tr >      			
      			<td  width="80%"></td>
                   <td   align="right" >                
                   <input type="button" class="button_grey" onclick="formValidate(this.form)" id="up_btn"  value="Tải lên"/>
                   <input type="button" class="button_grey"  id="cancl_btn"  value="Hủy"/>
                   <input type="button" class="button_grey"  id="reset_btn"  value="Làm lại"/>
                 </td> 
               </tr>
              
               </table>
              </div>
            
 			<div id="tab"> 	                      
 			<table width="100%" id="table2"      style="background-image:url('<%=request.getContextPath() %>/resources/images/head.png');background-repeat: repeat-x;">
 				 	
                         <tr>
                            <td colspan="3">&nbsp;</td>
                        </tr>	 
 			</table>
     	  </div>



 </form:form>
 </div>
 </div>
 </div>
 </div>
 <!-- </div> -->
   <div id="msgDialog" title="Alert">
	<div class="isa_info" align="center">
		<span style="font-size: 40"></span>
	</div>
	<div class="error_msg"></div>
</div> 
<div id="savedialog-confirm"></div>
<div id="faildialog-confirm"></div>        
    
 <script type="text/javascript">
 
 
 function formValidate(form){
	  var id =$("#reportName").val(); // option").filter(":selected").text();
	//alert($("#reportnameList option").filter(":selected").text());
	 var flag = 0;	   
		if ($("#fileData").val() == "" || $("#fileData").val() == " ") {
			
			flag = flag + 1;			
			$(".error_msg").html("Vui lòng chọn đường dẫn");
		}
		
		if ($("#id").val() == "" || $("#id").val() == " ") {
			
			flag = flag + 1;			
			$(".error_msg").html("Vui lòng chọn tên mẫu báo cáo hợp lệ");
		}
		
		 $("#table2 tr").each(function() {
		       var tempname = $(this).find("td:nth(1)").text();
		        if (tempname == id) {
		        	flag = flag + 1;	
		        	$(".error_msg").html("Mẫu báo cáo đã tồn tại với tên: "+id);
		        }
		    });
		 
		
		if (flag > 0) {			
			$("#msgDialog").dialog("open");
			
		} else{		
			 
			 form.action="${url}/uploadFile/"+id;
		 	 form.submit();
			
		}
	 
 }

	 var reportName=$("#reportName").val();// option").filter(":selected").text();	
	 var reportId=($('#reportId').val());
	
	
	$("#table2").flexigrid({	
		
		 dataType : 'json',
		colModel : [
					{display: 'Mã mẫu báo cáo', name : 'reportId', width : 150, sortable : true, align: 'center'},
					{display: 'Tên mẫu báo cáo', name : 'templateFileName', width : 500, sortable : true, align: 'center'},					
					{display: 'Mẫu Báo cáo chính', name : 'mainReportFlg', width : 210, align: 'center'},
					{display: 'Xóa mẫu báo cáo', name : 'reportId', width : 363, align: 'center',render: renderDeleteIt},
					
				],
		 sortname: "reportId",
		 sortorder: "asc",
		 title : 'Mẫu báo cáo',
		 usepager : false,
		 useRp : false,
		 showTableToggleBtn : true,   
		 height : '130',
		 singleSelect : true,
		 nowrap : false
	});
	$("#table2").show();	
	$(document).ready(function() {
		 var reportName=$("#reportName").val(); // option").filter(":selected").text();	
		 var reportId=$('#reportId').val();
		 var reportParam=reportId+","+reportName;
		 //alert(reportParam);
		
		 $('#table2').flexOptions({
			
			 url: "${url}/ajax_query/"+reportParam
					 
		 
		     }).flexReload();  
		});
	$("#table2").show();
	
	if($("#message").val()=="success"){		
		//document.forms["nicReportForms"].categoryLoad.value='Y';
		document.forms["nicReportForms"].action = '${backToList}';		
	    document.forms["nicReportForms"].submit();		
		//$("#savedialog-confirm").dialog('option', 'title', 'Status');
		//$("#savedialog-confirm").html('Successfully Uploaded The Report Template for  :  '+$("#reportName").val());
		//$("#savedialog-confirm").dialog( 'open' );		
	}if($("#message").val()=="fail"){
		$("#faildialog-confirm").dialog('option', 'title', 'Status');
		$("#faildialog-confirm").html('Không thể tải lên mẫu báo cáo cho :  '+$("#reportName").val());
		$("#faildialog-confirm").dialog( 'open' );	
	}	
	
function renderDeleteIt(content, currentRow){
	return "<button type=\"button\" onclick=\"deleteId('" + currentRow.reportId +","+currentRow.templateFileName+"')\"><img onclick=\"deleteId('" + currentRow.reportId +","+currentRow.templateFileName+"')\" src=<c:url value='/resources/images/delete_icon.jpg'/> width='18' height='18'/></button>";
}
	
function deleteId(id) {
		var url = '${deleteTemplateUrl}';
		$.get(url, {
			id : id
		}, function(data) {
			$("#global_icon_div").html(data);
			
			$("#table2")
			.flexOptions(
					{
						url : "${url}/ajax_query/"+id

					}).flexReload();

		});

		$("#dropdown3").show();
		$("#fortable").show();
		$("#defitable").hide();
		$("#defitable2").hide();

		
     }


$("#cancl_btn").click(function(){
	
	document.forms["nicReportForms"].action = '${frdBackPgUrl}';
    document.forms["nicReportForms"].submit();
});
$("#reset_btn").click(function(){

var reportCategory=$("#reportCategory").val();
var repName = $("#reportName").val();
//document.forms["nicReportForms"].reportId.value = reportId;
document.forms["nicReportForms"].reportName.value = repName;
document.forms["nicReportForms"].reportCategory.value = reportCategory;
document.forms["nicReportForms"].action = '${reqFrdURL}';
document.forms["nicReportForms"].submit();
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
    	  document.forms["nicReportForms"].action = '${frdBackPgUrl}';
    	  document.forms["nicReportForms"].submit();
    	 
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
  
  
  function checkForTempName(templateName){
	  $("#table2 tr").each(function() {
	       var tempname = $(this).find("td:nth(1)").text();
	        if (tempname == templateName) {
	          return true;
	        } else{
	        	return false;
	        }
	    });
  }
 </script>




<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url var="submitQueryUrl" value="/servlet/report/submitQuery"/>
 <c:url var="backToList" value="/servlet/report/backToList"/>
<c:url var="url" value="/servlet/report"/>
<c:url var="frdCnclRqUrl" value="/servlet/report/cancelToReportLoad"/>
<c:url var="reqFrdURL" value="/servlet/report/queryUpdate"/>
<c:url var="frdBackPgUrl" value="/servlet/report/cancelBack"/>

<div id="main">
<!-- <div id="content_main"> -->

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
		
		function updateReportQuery(form){
			  var id =$("#reportName").val(); // option").filter(":selected").text();
				 var flag = 0;	   
					if($("#description").val() == ""){
						flag = flag + 1;			
						$(".error_msg").html("Please enter report query");			
					}
					if (flag > 0) {			
						$("#msgDialog").dialog("open");
						
					} else{		
						 
						 form.action="${url}/submitQuery/"+id;
					 	 form.submit();
						
					}
		}
	</script>
	
	<form:form modelAttribute="nicReportForm"  id="nicReportForms"  name="nicReportForms"  action="${submitUrl}"  method="POST"  >
	<div class="container">
        	<div class="row">
        		<div class="roundedBorder ov_hidden">
<div id="heading_report" align="justify" colspan='2' style='padding:2px'>Quản lý báo cáo</div>&nbsp;
<br>
<br>
        <div id="categoryList">
                    <table style="width:100%;background-color:#E3F1FE" class="data_table">
                        
                            
                        <tr>
                            <td width="5%"></td>
                            <td width='10%'>Danh mục<span style="COLOR: #ff0000;">*</span>: </td>
                            <td class="description" width='50%'>
                             <form:input type="text" id="reportCategoryDesc" path="reportCategoryDesc" size='50' readonly="true"/>
                             
                              <form:input type="hidden" id="reportCategory" path="reportCategory" />	
                            </td>		
                         </tr> 						                       
  						<tr id="reportnameList">
                            <td width="5%"></td>
                            <td width='10%'>Tên báo cáo<span style="COLOR: #ff0000;">*</span>: </td>
                            <td width='50%'>
                             <form:input type="text" id="reportName" path="reportName" size='50' readonly="true"/>
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
                   <input type="button" class="button_grey" onclick="updateReportQuery(this.form)" id="up_btn"  value="Cập nhật"/>
                   <input type="button" class="button_grey"  id="cancl_btn"  value="hủy"/>
                   <input type="button" class="button_grey"  id="reset_btn"  value="Làm lại"/>
                 </td> 
               </tr>
              
               </table>
              </div>
            
 			<div id="tab"> 	                      
     	  </div>
</div>
</div>
</div>


 </form:form>
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
			$(".error_msg").html("Vui lòng chọn đường dẫn tệp báo cáo");
		}else if($("#description").val() == ""){
			//flag = flag + 1;			
			//$(".error_msg").html("Vui lòng nhập truy vấn báo cáo");			
		}
		if (flag > 0) {			
			$("#msgDialog").dialog("open");
			
		} else{		
			 
			 form.action="${url}/uploadFile/"+id;
		 	 form.submit();
			
		}
	 
 }

	 var reportName=$("#reportName").val();// option").filter(":selected").text();	
	 var reportId=($('#reportId').val());
	
	
	$(document).ready(function() {
		 var reportName=$("#reportName").val(); // option").filter(":selected").text();	
		 var reportId=$('#reportId').val();
		 var reportParam=reportId+","+reportName;
		 //alert(reportParam);
		
		
		});
	
	
	if($("#message").val()=="success"){		
		document.forms["nicReportForms"].action = '${backToList}';		
	    document.forms["nicReportForms"].submit();		
		//$("#savedialog-confirm").dialog('option', 'title', 'Status');
		//$("#savedialog-confirm").html('Successfully Uploaded The Report Template for  :  '+$("#reportName").val());
		//$("#savedialog-confirm").dialog( 'open' );		
	}if($("#message").val()=="fail"){
		$("#faildialog-confirm").dialog('option', 'title', 'Status');
		$("#faildialog-confirm").html('Không thể tải lên mẫu báo cáo cho:  '+$("#reportName").val());
		$("#faildialog-confirm").dialog( 'open' );	
	}	
	
function renderDeleteIt(content, currentRow){
	return "<button type=\"button\" onclick=\"deleteId('" + currentRow.reportId +","+currentRow.templateFileName+"')\"><img src=<c:url value='/resources/images/delete_icon.jpg'/> width='18' height='18'/></button>";
}
	
function deleteId(id) {
		
		
		$.post('<c:url value="/servlet/report/deleteTemplate"/>', {
			id : id
		}, function(data) {
			$("#global_icon_div").html(data);
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
 </script>




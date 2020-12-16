<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<form id="formsubmit" enctype="multipart/form-data" method="post" action="/eppcentral/servlet/batchJobMgmt/importTransactions" >

<div style="padding-left: 50px;padding-right: 50px;background-color: #fff;">
		<div>
			<div>
				<div class="row">
					<div class="ov_hidden">
			 <div class="new-heading-2">IMPORT GIAO DỊCH</div>     
			<div id="message" style="margin:5px 10px"></div>
			<table style="width: 100%; background-color: #E3F1FE" cellspacing="0" class="data_table" cellpadding="0">
				<tbody><tr>
					<td style="border: none; font-weight: bold">Chọn file XML</td>
					<td style="border: none;"><input type="file" multiple id="fileInput"/></td>
				</tr>
				
        
			</tbody></table>

			<table style="width: 100%; text-align: right;">
				<tbody><tr>
					<td colspan="2" align="right" style="padding: 10px; text-align: right;">
						<input type="button"  class="btn_small btn-primary" value="Import"  onclick="getFile()"> 
					
					</td>
				</tr>
			</tbody></table>

		
		</div>
	</div>
	</div>
	</div>
	</div>

</form>

<script>
var total=0;
function getFile(){
	
	total=0;
      var x = document.getElementById("fileInput");
	    var txt = "";
	    if ('files' in x) {
	        if (x.files.length == 0) {
	            txt = "Bạn phải chọn file xml để import.";
	        } else {

			  for (var i = 0; i < x.files.length; i++) {
			     var file = x.files[i];
              const reader = new FileReader()
	        	reader.onload = function(e) {
            	  postFile(reader.result);
				}
	        	reader.readAsText(file)
		        
           }
			  setTimeout(function(){
				
				  alert("Import giao dịch thành công.");
				  }, 3000);
	          	
	        	
	           
	        }
	    } 
		}



 function postFile(xml) {

	
	     stringUrl = $('#formsubmit').serialize() + "&xml="+ xml;
	
		  
		  $.post('/eppcentral/servlet/batchJobMgmt/importTransactions',stringUrl,
		             function(data){
		   if(data=='1'){
		   	 
		   total++;
		    }
		    else {
			      
			}
	  });
  }
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@page import="org.apache.commons.codec.binary.Base64"%> 
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.nec.asia.nic.investigation.controller.InvestigationHit"%>  
<c:url var="backBtnUrl" value="/servlet/listHandover/listHandover" />
<c:url var="homeUrl" value="/servlet/user/home" />		
<c:url var="requestPrint" value="/servlet/listHandover/showPdfResult" />		
<c:url var="requestPrintSync" value="/servlet/listHandover/showPdfResultSync" />
<!-- TRUNG THÊM THÔNG TIN -->
<c:url var="txnDetailInitUrl" value="/servlet/investigationConfirm/txnDetailTrans" />
<!-- END -->			
<c:set var="zero" scope="page" value="0"/>	
<% int zeru = 0;%> 	
	
<script src="<c:url value="/resources/js/mouseover_image.js"/>"
	type="text/javascript"></script> 

													
<style>
.color-table-0 {
	background-color: #fff;
}
.color-table-1 {
	background-color: #F4F9FE;
}
.cls-mg-bot {
	margin-top: 10px;
}
tr {
	height: 35px;
}
.displayTag tr.odd {
	height: 35px;
}
.displayTag tr.even {
	height: 35px;
}
.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
    white-space: pre-wrap;
}
	#ajax-load-qa {
		background: rgba(255, 255, 255, .8)
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
	.doGetAJpgSafeVersion { 
	     visibility:  hidden;
	} 
	 
	.NotTheSame{
		 color:     #FF0000; 
	}	
													
</style>
													
<style>
	
	.theBlockLeft {
		display: inline-block; 
		width: 43%;
		max-width: 43%;
		margin-right:1%;
		float:left;
	} 
	
	.theBlockRight {
		display: inline-block; 
		width: 43%;
		max-width: 43%;
		margin-right:1%;
		float:left;
	}
	
	.theBlockRightThird {
		display: inline-block; 
		width: 11%;
		max-width: 11%;
		float:right;
	}
	
	.theOneRow {
		margin-top:  5px;
	}

</style>

													
<style>
	
	/*.noHit_theBlock { 
		width: 43%;
		max-width: 43%;
		margin-right:28%;
		margin-left:28%;
	}  */

</style>
	
											
											
											
<style>
     .oneDocArea {
    	 width: 30%;
         margin-right: 2%;
     	 display:  inline-block;
         float:  left;
     }
		
     .theDocArea {
    	 margin-right:4%;
     }

</style>									
										
		
	<body>
		<form:form modelAttribute="investigationHitData" name="investigationHitData"
		id="investigationHitData">
		
		<c:if test="${not empty requestScope.Errors}">
		<div style="color:Red;background-color:White;border-style:solid;border-color:Red;border-width:thin;">
			<c:forEach items="${requestScope.Errors}" var="errorMessage">
				<li>
					${errorMessage}
				</li>
			</c:forEach>
			
		</div>
	</c:if>
	
	<c:if test="${not empty requestScope.messages}">
		<div class="border_success">
		<div class="success_left">
		<img align='left'
			src="<c:url value="/resources/images/success.jpg" />" width="30"
			height="30" />
              </div>
	
		
			 <div class="success">
				<table width="600" height="10" border="0" cellpadding="5">
					<tr> 
						<td style="padding-left: 5px;font-weight: bold; vertical-align: top;">
			    			<c:forEach items="${requestScope.messages}" var="infoMessage">
									${infoMessage}
							</c:forEach>
						</td>
					  </tr>
				</table>
			</div>
		</div>
		<br />
	</c:if>
 
<!--Content start-->
	<div id="dialog-approve"></div>
	<div class="form-desing">
		<div>
			<div>
				<div class="row">
					<div class="ov_hidden">
						<div class="new-heading-2">CHI TIẾT DANH SÁCH ĐÃ BÀN GIAO</div>

						<div style="clear: both"></div>

						<div style="min-height: 10px"></div>



						<br />
						<div id="ajax-load-qa"></div>

						<%--
							int pageSize = 20;
						--%>

						 <div>	
							<table class="table table-bordered table-sm" id="dtBasicExample" cellspacing="0" width="100%">
								<thead>
									<tr>
									<th class="th-sm" style="min-width: 140px;">Mã giao dịch</th>
									<th class="th-sm" style="min-width: 150px;">Mã gói bàn giao</th>
									<th class="th-sm" style="min-width: 150px;">Loại danh sách</th>
									<th class="th-sm" style="min-width: 150px;">Người lập danh sách</th>
									<th class="th-sm" style="min-width: 120px;">Ngày tạo</th></tr>
								</thead>
								<tbody>						
									<c:forEach var="row" items="${jobList}">										
										<tr>
											<td>
												<a style="color: blue;" onclick="callDialog('${row.transactionId}','${row.jobId}')">${row.transactionId}</a>
												<input hidden="hidden" value="${row.packageId}" name="packID" id="packID"/>
											</td>
											<td>${row.packageId}</td>
											<td>${row.typeListName}</td>
											<td>${row.createBy}</td>
											<td>${row.createDate}</td>
										</tr>
										
									</c:forEach> 
								</tbody>
							</table>
						</div>
							
			



						<%
							/* ******************************************************************************************************************** */
						%>
						<%
							/* ************************************* actions for selected jobs - start ******************************************** */
						%>
						<%
							/* ******************************************************************************************************************** */
						%>

					</div>
				</div>
			</div>
		</div>
	</div>

<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
			<div style="margin: 10px"> 
				<button type="button" class="btn btn-primary" id="backBtn" name="back">
					<span class="glyphicon glyphicon-remove"></span> Quay lại
				</button>
				<button type="button" class="btn btn-primary" id="printBtn" onclick="requestPrint(${typeJob});">
					<span class="glyphicon glyphicon-print"></span> In danh sách
				</button>
				<!--<input type="button" class="btn btn-primary" id="printBtn"          onclick="requestPrint(${typeJob});"                                 name="printBtn"     value="In danh sách" />
				&nbsp;
				<input type="button" class="btn btn-primary" id="backBtn"                                           name="back"     value="Quay lại" />-->
			</div>
		</div>
<script>
$('#dtBasicExample').DataTable();
$('.dataTables_length').addClass('bs-select');
		$('#backBtn').click(function(e) { 
		    e.preventDefault();  
		    investigationHitData.action = "${backBtnUrl}";
		    investigationHitData.submit();
		}); 
		  function openBoxReject(){
			  $( "#dialog-reject-getRemarks" ).dialog( "open" ); 
		  }
		  
		  $(function() {
			    $( "#dialog-reject-getRemarks" ).dialog({
					  modal: true,
				      autoOpen: false,
					  width : 600,
					  height: 250,
					  resizable: false,
				      show: {
				        effect: "fade",
				        duration: 1000
				      },
				      hide: { },
				      buttons: {
				         "Continue": function() {
							var inp = $("#jobRejectRemarksHolder").val();
							if ($.trim(inp).length == 0){
								alert ('Nhập nội dung lý do từ chối!');
								return;
							}    
				            $( this ).dialog( "close" );
				            doReject();
				         },
				         "Back": function() {
				            $( this ).dialog( "close" );
				         }
				      } 
			    });
		  }); 
		  
		  function doReject() { 
		       
		 	    $(".ui-dialog-buttonpane button:contains('Yes')").button("disable");
		 	    $(".ui-dialog-buttonpane button:contains('Back')").button("disable");
			 
				doReject_noHit();
			
		   }
		
		   function doReject_noHit() {
				 
				prepareHitInformation_noHit();  
	  
				investigationHitData.action = "${rejectUrl_noHit}"; 
			  	document.forms["investigationHitData"].submit();  
		   }
		   
		   function requestApprove() { 
				if(confirm("Có chăc chắn muốn thực hiện hành động này?"))
				   {
					  prepareHitInformation_noHit();
				 	   document.forms["investigationHitData"].action = '${approveUrl_noHit}';  
					   document.forms["investigationHitData"].submit();  

				   }
				  else
				    return false;
			}
		   
		   function requestToPendingBCA() { 
				if(confirm("Có chắc chắn muốn gửi thông tin sang Bộ công an để xác minh?"))
				   {
					  prepareHitInformation_noHit();
				 	   document.forms["investigationHitData"].action = '${pending_bca}';  
					   document.forms["investigationHitData"].submit();  

				   }
				  else
				    return false;
			}
			
		   function prepareHitInformation_noHit() {
				        
			      document.forms["investigationHitData"].jobApproveRemarks.value =  $('#jobApproveRemarksHolder').val();
			      document.forms["investigationHitData"].jobRejectRemarks.value  =  $('#jobRejectRemarksHolder').val();
			      document.forms["investigationHitData"].jobSuspendRemarks.value =  $('#jobSuspendRemarksHolder').val();  
			        
			} 
			   
			/*  ///2. Sửa năm hộ chiếu
			   function requestPassportYear() { 
				   document.getElementById("PassportYearInsert").style.display = 'block';
				   document.getElementById("passportYearDisplay").style.display = 'none';
				   var contents = document.getElementById('passportYearDisplay').innerText;
				   if(contents != null || contents != "")
					   $("#passportYeartxt").val(contents);
				   document.getElementById("passportYeartxt").style.display = 'block';
				   document.getElementById("ppYearOk").style.display = 'block';
			   }
			   
			   function doPassportYear() {     
				   var transId = document.getElementById('transactionTxt').innerText;
				   //var contents= {"passportNo": "", "passportYear": $("#passportYeartxt").val(), "transactionId": transId};
				   var contents = {
						   passportNo:  "",
						   passportYear: $("#passportYeartxt").val(),
						   transactionId: transId
				   }
				   
				   document.getElementById("passportYearDisplay").style.display = 'block';
				   document.getElementById("passportYeartxt").style.display = 'none';
				   document.getElementById("ppYearOk").style.display = 'none';

				   document.getElementById('passportYearDisplay').innerText = $("#passportYeartxt").val();;
			 	   
				 	  $.ajax({
				 	      type: "POST",
				 	      url: "${passportUrl}", 
				 	      data: contents,
				 	      dataType: "json",
				 	      contentType: "application/json; charset=utf-8",
				 	      success: function(response) {
			                    if(response.result)
			                    	alert('Đã cập nhật thành công');
			                },
			                error: function(e) {
			                    alert('Error: ' + e);
			                } 
				 	  });
			   }
	       //========================== */
	    	   
	    	/// ======= Check chữ ký ====
			   
			   function requestCheckSign() { 
				   var transId = document.getElementById('transactionTxt').innerText;
				   investigationHitData.action = "${checkSignUrl}" + "/" + transId; 
				  document.forms["investigationHitData"].submit();  
			   }
			   
	  		// ========== ENd check chữ ký
	  		
	  		/// ======= Kiểm tra api ====
			   
			   function requestAPI() { 
				   var transId = document.getElementById('transactionTxt').innerText;
				   var contents = {
						   transactionId: transId
				   }
				   
				   $.ajax({
				 	      type: "POST",
				 	      url: "${apiUrl}", 
				 	      data: contents,
				 	      dataType: "json",
				 	      contentType: "application/json; charset=utf-8",
				 	      success: function(response) {
			                    if(response.result)
			                    	alert('Đã đồng bộ thành công');
			                },
			                error: function(e) {
			                    alert('Error: ' + e);
			                } 
				 	  });
			   }
			   
	  		// ========== ENd check chữ ký
	    	
	  		
	  		//TRUNG THÊM GỌI THÔNG TIN CHI TIẾT BẢN GHI
			function callDialog(txnId, jobId_) {
				//var txnId =document.getElementById('transId_').value;
				/* var txnId = '${nicData.transactionId}'; */
				$('#ajax-load-qa').css('display', 'block');
				console.log('Ma giao dich: ' + txnId);
				console.log('JOB ID: ' + jobId_);
				$.ajax({
					type : "GET",
					url : "${txnDetailInitUrl}/" + txnId + "/" + jobId_,
					success : function(data) {
						$('#ajax-load-qa').css('display', 'none');
						$('.modal').show();
						var titleName = "Thông tin chi tiết bản ghi";
						$("#dialog-approve").dialog('option', 'title', titleName);
						$("#dialog-approve").dialog("option", "width", 1200);
						$("#dialog-approve").dialog("option", "height", 600);
						$("#dialog-approve").dialog("option", "maxHeight", 600);
						$("#dialog-approve").dialog("option", "resizable", false);
						$("#dialog-approve").dialog('open');
						$("#dialog-approve").html(data);
						$('.modal').hide();
					},
					error: function(e){
						$('#ajax-load-qa').css('display', 'none');						
						$('.modal').hide();
					}
				});
			}
		
			$("#dialog-approve").dialog({
				autoOpen : false,
				width : 960,
				height : 480,
				modal : true,
				bgiframe : true,
				cache : false,
				closeOnEscape : false
			});
			
			function requestPrint(t) { 
				var packId = $('#packID').val();
						var url = '${requestPrint}' + "/" + packId;
						if(t == 4 || t == 6){
						   url = '${requestPrintSync}' + "/" + orig;  
					   }
				 	  	document.forms["investigationHitData"].action = url;  
					   document.forms["investigationHitData"].method = "POST";  
					  // window.open(url, "popup", "location=no");
					   window.open(url, "popup", "location=no,resizable=yes,left=0,height=800px,width=1280px");

			}

		 </script>

	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ******************************************** reject - get remarks - end ******************************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
	<form:input    path="uploadJobId"                             type="hidden" name="uploadJobId"                             value="${jobsID}" />
	<form:input    path="jobApproveRemarks"                       type="hidden" name="jobApproveRemarks"                       value="" />
	<form:input    path="jobRejectRemarks"                        type="hidden" name="jobRejectRemarks"                        value="" />
	<form:input    path="jobSuspendRemarks"                       type="hidden" name="jobSuspendRemarks"                       value="" /> 
	<form:input    path="transactionIdAndPassportNumbersToCancel" type="hidden" name="transactionIdAndPassportNumbersToCancel" value="" /> 
	</form:form>
</body>

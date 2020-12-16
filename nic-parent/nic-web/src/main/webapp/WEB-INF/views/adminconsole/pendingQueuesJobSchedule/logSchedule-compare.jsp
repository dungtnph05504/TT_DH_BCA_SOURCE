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
<c:url var="backBtnUrl" value="/servlet/queuesJobListController/queuesJobList" />
<c:url var="homeUrl" value="/servlet/user/home" />		
<c:url var="requestPrint" value="/servlet/listHandover/showPdfResult" />
<c:url var="detailQueue" value="/servlet/queuesJobListController/startDetailQueuesJobList" />		

<!-- TRUNG THÊM THÔNG TIN -->
<c:url var="txnDetailInitUrl" value="/servlet/investigationConfirm/txnDetailTrans" />
<!-- END -->			
<c:set var="zero" scope="page" value="0"/>	
<% int zeru = 0;%> 	
	
<script src="<c:url value="/resources/js/mouseover_image.js"/>"
	type="text/javascript"></script> 

													
<style>
	.cls-mg-bot {
	margin-top: 10px;
}
.color-table-0 {
	background-color: #fff;
}
.color-table-1 {
	background-color: #F4F9FE;
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
	.doGetAJpgSafeVersion { 
	     visibility:  hidden;
	} 
	 
	.NotTheSame{
		 color:     #FF0000; 
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
						<div class="new-heading-2">CHI TIẾT HÀNG CHỜ</div>	

						<div style="clear: both"></div>

						<div style="min-height: 10px"></div>

						<div style="clear: both"></div>

						<br />
						<div id="ajax-load-qa"></div>
						<%--
							int pageSize = 20;
						--%>

					
							<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								<thead>
									<tr>
									<th class="th-sm" style="min-width: 150px;">Mã giao dịch</th>
									<th class="th-sm" style="min-width: 140px;">Loại hàng chờ</th>
									<th class="th-sm" style="min-width: 140px;">Loại giao dịch</th>
									<th class="th-sm" style="min-width: 100px;">Trạng thái</th>
									<th class="th-sm" style="min-width: 150px;">Thời gian thực hiện</th>
									<th class="th-sm" style="min-width: 150px;">Mô tả thêm</th>
									</tr>
								</thead>
								<tbody>
								
									<c:forEach var="row" items="${jobList}">
										
										<tr>
											<td>${row.code}</td>
											<td>${row.typeLogJob}</td>
											<td>${row.typeTransaction}</td>
											<td>${row.status}</td>
											<td>${row.createDate}</td>
											<td>${row.description}</td>
										</tr>
									
									</c:forEach> 
								</tbody>
							</table>
							<div class="col-md-5 col-sm-12" style="padding-left: 0px;">
								<div class="dataTables_info">Hiển thị ${startIndex} đến ${endIndex} của ${totalRecord} kết quả</div>
							</div>
							<div class="col-md-7 col-sm-12" style="padding-right: 0px;">
								<ul style="float: right;" class="pagination" id="pagination"></ul>
							</div>			 
							<input type="hidden" name="pageNo" id="pageNo" /> 
							
				



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
				<!-- <input type="button" class="btn btn-primary" id="printBtn"          onclick="requestPrint();"                                 name="printBtn"     value="In danh sách" /> -->
			<!-- 	&nbsp; -->
				<input type="button" class="btn btn-primary" id="backBtn"                                           name="back"     value="Quay lại" />
			</div>
		</div>
<script>
//$('#dtBasicExample').DataTable();
//$('.dataTables_length').addClass('bs-select');
	var totalPages = ${totalPage};
	var currentPage = ${pageNo};
	var pageSize = ${pageSize};
	window.pagObj = $('#pagination').twbsPagination({
			totalPages: totalPages,
			visiblePages: pageSize,
			startPage: currentPage,
			next: '<span class=\"glyphicon glyphicon-forward fix-paging\"></span>',
			prev: '<span class=\"glyphicon glyphicon-backward fix-paging\"></span>',
			last: '<span class=\"glyphicon glyphicon-step-forward fix-paging\"></span>',
			first: '<span class=\"glyphicon glyphicon-step-backward fix-paging\"></span>',
			onPageClick: function (event, page) {
				if (currentPage != page) {
					$('#pageNo').val(page);
					document.forms["investigationHitData"].action = '${detailQueue}/${idPack}';  
					document.forms["investigationHitData"].submit();  
				}
			}
		});
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
			
			function requestPrint() { 
				var orig = $("#packID").val();
				//var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
					   var test = '${requestPrint}' + "/" + orig;  
				 	   document.forms["investigationHitData"].action = test;  
					   document.forms["investigationHitData"].method = "POST"; 
					   window.open(test, "popup", "location=no,resizable=yes,left=0,height=800px,width=1280px");
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

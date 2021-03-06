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
<c:url var="backBtnUrl" value="/servlet/investigationBca/investigationConfirmBcaDetail" />
<c:url var="homeUrl" value="/servlet/user/home" />	
<c:url var="apiUrl" value="/servlet/investigation/apiCheck" />		
<!-- TRUNG THÊM THÔNG TIN -->
<c:url var="txnDetailInitUrl" value="/servlet/investigationBca/txnDetailTrans" />
<!-- END -->
<c:set var="zero" scope="page" value="0"/>	
<% int zeru = 0;%> 	
	
<script src="<c:url value="/resources/js/mouseover_image.js"/>"
	type="text/javascript"></script> 

													
<style>
	
	.doGetAJpgSafeVersion { 
	     visibility:  hidden;
	} 
	 
	.NotTheSame{
		 color:     #FF0000; 
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
	<div class="container">
   <div class="row">
   <div class="roundedBorder ov_hidden">
   <div id="heading">
    <div id="heading_left" style="font-weight: bold;" align="justify">
		Chi tiết hồ sơ xác minh từ Bộ công an
	</div>
</div>
	<form:form modelAttribute="investigationHitData" name="investigationHitData"
		id="investigationHitData" style="margin-top:20px;">	
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
				<table width="600" height="40" border="0" cellpadding="5">
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
		<div class="waitingWhileWaiting" style="display:none"> 
				<div style="font-color:#fff; margin-top:50px; font-size: 1.5em; font-weight: bold; text-align:center" >
					Đang xử lý yêu cầu. Vui lòng đợi..
				</div> 
		</div>
						<!-- Jquery Dialog box div for image popup ( Picture )---->
		<div class="noHit_theBlock onHoverMousePointerThumb"> 
						
    <div id="dialog-image_photoCompare" style="display: block; margin:10px 0px">
		<div class="centerCaption">
            <div style="text-align:center">
                <!-- photo dimension: 480 (width) x 640 (height) -->
                <c:choose>
                    <c:when test="${not empty photoStr}">
                        <div>
                            <img src="data:image/jpg;base64,${photoStr}"
                                 class="img-border" height="320" width="240" />
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div>
                            <img src="<c:url value='/resources/images/No_Image.jpg' />" class="img-border" height="320" width="240" title="Hit Candidate" />
                            <div style="font-weight: bold; color: #000;text-align:center;">Không có ảnh</div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
    	</div>
    </div>
						
    <div class="data_table2" style="margin:30px 0px; color: #337ab7;">
        <div class="form-group text">
            <label class="control-label" style="width: 35%;color: #000;">Mã giao dịch</label>
            <label class="control-label" id="transactionTxt">:</label>
            <c:out value="${nicData.transactionId}" />
        </div>
        <div class="form-group text">
            <label class="control-label" style="width: 35%;color: #000;">CMND/CCCD</label>
            <label class="control-label">:</label>
            <c:out value="${nicData.nin}" />
        </div>
        <div class="form-group text">
            <label class="control-label" style="width: 35%;color: #000;">Họ và tên</label>
            <label class="control-label">:</label>
            <c:out value="${nicData.nicRegistrationData.surnameLine1}" />
        </div>
        <div class="form-group text">
            <label class="control-label" style="width: 35%;color: #000;">Ngày sinh</label>
            <label class="control-label">:</label>
            <fmt:formatDate value="${nicData.nicRegistrationData.dateOfBirth}" pattern="dd/MM/yyyy" var="newdatevar" /><%-- ${nicData.dateOfBirthDesc} --%>
            <c:out value="${newdatevar}" />
        </div>
        <div class="form-group text">
            <label class="control-label" style="width: 35%;color: #000;">Giới tính</label>
            <label class="control-label">:</label>
             <c:choose>
                    <c:when test="${nicData.nicRegistrationData.gender == 'M'}">
                        Nam
                    </c:when>
                    <c:when test="${nicData.nicRegistrationData.gender == 'F'}">
                        Nữ
                    </c:when>
                    <c:otherwise>
                        Khác
                    </c:otherwise>
            </c:choose>
        </div>
        <div class="form-group text">
            <label class="control-label" style="width: 35%;color: #000;">Địa chỉ thường trú</label>
            <label class="control-label">:</label>
            <c:out value="${nicData.nicRegistrationData.addressLine1}" />
        </div>
        <div class="form-group text">
            <label class="control-label" style="width: 35%;color: #000;">Tôn giáo</label>
            <label class="control-label">:</label>
            <c:out value="${nicData.religionDesc}" />
        </div>
        <div class="form-group text">
            <label class="control-label" style="width: 35%;color: #000;">Dân tộc</label>
            <label class="control-label">:</label>
            <c:out value="${nicData.nationDesc}" />
        </div>
    </div>
    
    <div class="data_table2" style="margin:30px 0px; color: #337ab7;">
        <div class="form-group text">
            <label class="control-label" style="width: 35%;color: #000;">Nơi sinh</label>
            <label class="control-label">:</label>
            <c:out value="${nicData.nicRegistrationData.placeOfBirth}" />
        </div>
        <div class="form-group text">
            <label class="control-label" style="width: 35%;color: #000;">Tình trạng hôn nhân</label>
            <label class="control-label">:</label>
            <c:choose>
                    <c:when test="${nicData.nicRegistrationData.maritalStatus == 'M'}">
                        Đã kết hôn
                    </c:when>
                    <c:when test="${nicData.nicRegistrationData.maritalStatus == 'S'}">
                        Độc thân
                    </c:when>
                    <c:otherwise>
                        Khác
                    </c:otherwise>
             </c:choose>
        </div>
        <div class="form-group text">
            <label class="control-label" style="width: 35%;color: #000;">Số hộ chiếu cũ</label>
            <label class="control-label">:</label>
            <c:choose>
                    <c:when test="${nicData.transactionType == 'REG'}">
                        Đăng ký mới
                    </c:when>
                    <c:otherwise>
                        <c:out value="${nicData.prevPassportNo}" />
                    </c:otherwise>
             </c:choose>
        </div>
        <div class="form-group text">
            <label class="control-label" style="width: 35%;color: #000;">Nơi nhận hồ sơ</label>
            <label class="control-label">:</label>
            <c:out value="${nicData.regSiteCode}" />
        </div>
        <div class="form-group text">
            <label class="control-label" style="width: 35%;color: #000;">Kiểu giao dịch</label>
            <label class="control-label">:</label>
            <c:choose>
                    <c:when test="${nicData.transactionType == 'REG'}">
                        Đăng ký mới
                    </c:when>
                    <c:when test="${nicData.transactionType == 'REP'}">
                        Thay thế / Đăng ký lại
                    </c:when>
                    <c:otherwise>
                        Báo mất / hỏng
                    </c:otherwise>
             </c:choose>
        </div>
        <div class="form-group text">
            <label class="control-label" style="width: 35%;color: #000;">Loại hộ chiếu</label>
            <label class="control-label">:</label>
            <c:choose>
                    <c:when test="${nicData.passportType == 'P'}">
                        Hộ chiếu phổ thông
                    </c:when>
                    <c:when test="${nicData.passportType == 'PD'}">
                        Hộ chiếu ngoại giao
                    </c:when>
                    <c:otherwise>
                        Hộ chiếu công vụ
                    </c:otherwise>
             </c:choose>
        </div>
        <div class="form-group text">
            <label class="control-label" style="width: 35%;color: #000;">Ngày tạo</label>
            <label class="control-label">:</label>
             <c:out value="${nicData.createDesc}" />
        </div>
        <div class="form-group text">
            <label class="control-label" style="width: 35%;color: #000;">Ghi chú</label>
            <label class="control-label">:</label>
        </div>
        <div class="form-group text" id="PassportNoInsert" style="display:none">
            <label class="control-label">Số hộ chiếu:</label>
            <label class="control-label" id="passportNoDislay" style="display:none"></label>
            <input type="text" id="passportNotxt" name="passportNotxt" />
            <input type="button" class="btn btn-primary"  onclick="doPassportNo()" name="ppNoOk" id="ppNoOk"  value="Ok" /> 
        </div>
        <div class="form-group text" id="PassportYearInsert" style="display:none">
            <label class="control-label">Năm hộ chiếu:</label>
            <label class="control-label" id="passportYearDisplay" style="display:none"></label>
            <input type="text" id="passportYeartxt" name="passportYeartxt" />
            <input type="button" class="btn btn-primary"  onclick="doPassportYear()" name="ppYearOk" id="ppYearOk"  value="Ok" /> 
        </div>
        <div id="ajax-load-qa"></div>
    </div>
    
    <div id="dialog-approve"></div>
												</div>
		
		<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
			<div style="margin: 10px"> 
				<button type="button" class="btn btn-primary" onclick="requestToConfirmBCA(${id}); return false;" name="approve">
					<span class="glyphicon glyphicon-ok"></span> Duyệt hồ sơ
				</button>
				<button type="button" class="btn btn-danger"   onclick="openBoxReject(); return false;" name="reject">
					<span class="glyphicon glyphicon-remove"></span> Từ chối
				</button>
				<button type="button" class="btn btn-primary" id="backBtn" name="back">
					<span class="glyphicon glyphicon-backward"></span> Quay lại
				</button>
				<button type="button" class="btn btn-primary" onclick="callDialog('${transID}', '${jobsID}');">
					<span class="glyphicon glyphicon-zoom-in"></span> Xem thông tin
				</button>
				<!--<input type="button" class="btn btn-primary"              onclick="requestToConfirmBCA(); return false;" name="approve"  value="Duyệt hồ sơ" /> 
				&nbsp; 
				<input type="button" class="btn btn-danger"              onclick="openBoxReject(); return false;" name="reject"  value="Từ chối" /> 
				&nbsp; 
				<input type="button" class="btn btn-primary" id="backBtn"   name="back"     value="Quay lại" />
				&nbsp; 
				<input type="button" onclick="callDialog('${transID}', '${jobsID}');"     class="btn btn-primary"  value="Xem thông tin" />  -->                              
			</div>
		</div>
		<c:url var="rejectUrl_noHit" value="/servlet/investigationBca/reject_noHit" />
		<c:url var="approveUrl_noHit" value="/servlet/investigationBca/approve_conf_bca" />
		
		<div id="dialog-reject-getRemarks" title="Từ chối - Nhập nhận xét" style="display: none;"> 
			<div style="margin: 2px;">	
				Nhận xét
			</div>
			<div>		
				<textarea style="height: 100px; width: 100%;" id="jobRejectRemarksHolder" name="jobRejectRemarksHolder" ></textarea>		
			</div> 
		</div> 
		   
	<form:input    path="uploadJobId"                             type="hidden" name="uploadJobId"                             value="${jobsID}" />
	<form:input    path="jobApproveRemarks"                       type="hidden" name="jobApproveRemarks"                       value="" />
	<form:input    path="jobRejectRemarks"                        type="hidden" name="jobRejectRemarks"                        value="" />
	<form:input    path="jobSuspendRemarks"                       type="hidden" name="jobSuspendRemarks"                       value="" /> 
	<form:input    path="transactionIdAndPassportNumbersToCancel" type="hidden" name="transactionIdAndPassportNumbersToCancel" value="" /> 
	
		</form:form>
</div>
</div>
</div>
</body>
<script>

$('#backBtn').click(function(e) { 
	var id = '${listCode}';
    e.preventDefault();  
    investigationHitData.action = "${backBtnUrl}/" + id;
    investigationHitData.submit();
}); 
		function requestToConfirmBCA(id) {
			//alert(id);
			$.confirm({
			    title: 'Thông báo',
			    content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/tip_1a.png\">" + ' Chắc chắn duyệt bản ghi này?',
			    buttons: {
			        "Đồng ý": function () {
			        	prepareHitInformation_noHit();
			        	//document.forms["investigationHitData"].currentlyAssignedToUserId.value =  id;
					 	document.forms["investigationHitData"].action = '${approveUrl_noHit}/' + id;  
						document.forms["investigationHitData"].submit(); 
			        },
			        "Hủy": function () {
			        	//return false;
			        }			       
			    }
			})
			/*if(confirm("Chắc chắn duyệt bản ghi này?"))
			   {
				  prepareHitInformation_noHit();
			 	   document.forms["investigationHitData"].action = '${approveUrl_noHit}';  
				   document.forms["investigationHitData"].submit();  
		
			   }
			  else
			    return false;*/
		}
		
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
				         "Tiếp tục": function() {
							var inp = $("#jobRejectRemarksHolder").val();
							if ($.trim(inp).length == 0){
								//alert ('Nhập nội dung lý do từ chối!');
								$.alert({
									title: 'Thông báo',
									content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Nhập nội dung lý do từ chối!",
								});
								return;
							}    
				            $( this ).dialog( "close" );
				            doReject();
				         },
				         "Đóng": function() {
				            $( this ).dialog( "close" );
				         }
				      } 
			    });
		  }); 
		  
		  function doReject() { 
		       
		 	    $(".ui-dialog-buttonpane button:contains('Có')").button("disable");
		 	    $(".ui-dialog-buttonpane button:contains('Không')").button("disable");
			 
				doReject_noHit();
			
		   }
		 
		   function doReject_noHit() {
				 
				prepareHitInformation_noHit();  
	  
				investigationHitData.action = "${rejectUrl_noHit}"; 
			  	document.forms["investigationHitData"].submit();  
		   }
		
		function prepareHitInformation_noHit() {
			        
		      document.forms["investigationHitData"].jobApproveRemarks.value =  $('#jobApproveRemarksHolder').val();
		      document.forms["investigationHitData"].jobRejectRemarks.value  =  $('#jobRejectRemarksHolder').val();
		      document.forms["investigationHitData"].jobSuspendRemarks.value =  $('#jobSuspendRemarksHolder').val();  
		        
		}
		
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
				error: function(e) {
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
		                    	//alert('Đã đồng bộ thành công');
		                    	$.alert({
									title: 'Thông báo',
									content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/success_1a.png\">" + " Đã đồng bộ thành công",
								});
		                },
		                error: function(e) {
		                    //alert('Error: ' + e);
		                	 $.alert({
								 title: 'Thông báo',
								 content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/error_1a.png\">" + " Đã có lỗi xảy ra: " + e,
							 });
		                } 
			 	  });
		   }
  		// ========== ENd check chữ ký
    	 
</script>
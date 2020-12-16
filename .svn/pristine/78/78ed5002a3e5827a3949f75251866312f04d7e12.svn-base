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
<c:url var="backBtnUrl" value="/servlet/syncSinger/getSyncSingerJob" />
<c:url var="approveUrl" value="/servlet/syncSinger/approveStatus" />
<c:url var="homeUrl" value="/servlet/user/home" />
<c:url var="passportUrl" value="/servlet/investigation/passportNoEdit" />		
<c:url var="checkSignUrl" value="/servlet/investigation/checkSignDetail" />			
<c:url var="apiUrl" value="/servlet/syncSinger/apiCheck" />		
			
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
    <div class="new-heading-2">CHI TIẾT HỒ SƠ ĐỒNG BỘ SANG A72</div>     
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
                            <div style="font-weight: bold; color: #000;text-align:center;">No Image</div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
    	</div>
    </div>
						
    <div class="data_table2" style="margin:30px 0px; color: #337ab7;">
        <div class="form-group text">
            <label class="control-label" style="width: 35%;color: #000;">Mã giao dịch</label>
            <label class="control-label">:</label>
            <input hidden="hidden" value="${nicData.transactionId}" id="transactionTxt"/>
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
            <c:out value="${nicData.dateOfBirthDesc}" />
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
            <c:out value="${nicData.nicRegistrationData.religion}" />
        </div>
        <div class="form-group text">
            <label class="control-label" style="width: 35%;color: #000;">Dân tộc</label>
            <label class="control-label">:</label>
            <c:out value="${nicData.nicRegistrationData.nation}" />
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
    </div>
												</div>
		
		<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
			<div style="margin: 10px"> 
				<!-- 
				<input type="button" class="btn btn-primary"              onclick="requestApprove(); return false;" name="approve"  value="Phê duyệt" /> 
				&nbsp; 	
				<input type="button" class="button_grey_2"              onclick="requestPassportNo() ; return false;" name="passportNo"   value="Số hộ chiếu" /> 
				&nbsp;
						 
				<input type="button" class="button_grey_2"              onclick="requestCheckSign() ; return false;" name="checkSign"   value="Kiểm tra chữ ký" /> 
				&nbsp; 
				<input type="button" class="button_grey_2"              onclick="requestPassportYear(); return false;" name="passportYear"  value="Năm hộ chiếu" /> 
				&nbsp; 	
				 -->
				<!-- <input type="button" class="button_grey_2"              onclick="requestAPI(); return false;" name="apiSerivce"  value="Đồng bộ HC lên A72" /> 
				&nbsp; 	 -->	
				<button type="button" class="btn btn-primary"  id="backBtn"  name="back">
					<span class="glyphicon glyphicon-remove"></span> Quay lại
				</button>
				<!--<input type="button" class="btn btn-primary" id="backBtn"                                           name="back"     value="Quay lại" />-->
			</div>
		</div>
		
		<div id="dialog-approve-confirm" title="Approve Application - Confirmation" style="display: none;"> 
				<p>
					<span class="ui-icon ui-icon-alert"
							style="float: left; margin: 0 7px 20px 0;"> </span>  
							Bạn có chắc muốn phê duyệt bản ghi này? 
				</p>    
		</div> 
		
		 	<form:input    path="uploadJobId"                             type="hidden" name="uploadJobId"                             value="${jobDetails.uploadJobId}" />
        	<form:input    path="searchResultId"                          type="hidden" name="searchResultId"                          value="" />
        	<form:input    path="hitTransactionId"                        type="hidden" name="hitTransactionId"                        value="" />
        	<form:input    path="remarks"                                 type="hidden" name="remarks"                                 value="" />
        	<form:input    path="duplicateDecision"                       type="hidden" name="duplicateDecision"                       value="" />
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
		function redirectForProcessingTimeout() { 
			alert("The processing of your request failed.  Please try again.");
			var url = '${homeUrl}';
			window.location.href = url;
		}
		
		  $(function() {
		    $( "#dialog-approve-confirm" ).dialog({
			modal: true,
		      autoOpen: false,
			  width : 600,
			  height: 200,
			  resizable: false,
		      show: {
		        effect: "fade",
		        duration: 1000
		      },
		      hide: { },
		      buttons: {
			     "Yes": function() {
			          doApprove();
			     },
			     "Back": function() {
			          $( this ).dialog( "close" );
			         
			     }
			  } 
		    });
		  });

		   function requestApprove() { 

			   $("#dialog-approve-confirm").dialog( "open" );
		   }
		   
		   function doApprove() { 
		       
		 	    $(".ui-dialog-buttonpane button:contains('Có')").button("disable");
		 	    $(".ui-dialog-buttonpane button:contains('Không')").button("disable");
			       
				
					doApprove_noHit();
				
		   }
		
		   function doApprove_noHit() { 
			  investigationHitData.action = "${approveUrl}"; 
			  document.forms["investigationHitData"].submit();  
		   }
  		
            $('#backBtn').click(function(e) { 
                e.preventDefault();  
                investigationHitData.action = "${backBtnUrl}";
                investigationHitData.submit();
            }); 
            
        	$(document).ready(function() { 
       		 
                var processingTimeout = setTimeout(redirectForProcessingTimeout, 60000);   
        		
        		$(".doGetAJpgSafeVersion").each(function() {
        			var anApplet = document.getElementById('investigationApplet');
        			if (!anApplet) {
        			    $( this ).css( "visibility", "visible");
        				return;
        			}
        			
        			var currentValue=$( this ).attr( "src" );    
        			  
        			if (!(currentValue.substring(0,27)=='data:image/jpg;base64,     ')) {
        			    $( this ).css( "visibility", "visible");
        				return;
        			}      
        			 
        			var newSrc = 'data:image/jpg;base64,' + document.investigationApplet.getAJpgSafeVersion(currentValue.substring(27)); 
        			 
        		    $( this ).attr( "src", newSrc);
        		    $( this ).css( "visibility", "visible");
        		}); 

        	    clearTimeout(processingTimeout);
        		$(".waitingWhileWaiting").css( "display", "none");
        		$(".waitingWhenDoneWaiting").css( "display", "block");
        	});
            
        ///====Thêm số hộ chiếu và năm hộ chiếu===
    	///1. Sửa số hộ chiếu

		   function requestPassportNo() { 
			   document.getElementById("PassportNoInsert").style.display = 'block';
			   document.getElementById("passportNoDislay").style.display = 'none';
			   var contents = document.getElementById('passportNoDislay').innerText;
			   if(contents != null || contents != "")
				   $("#passportNotxt").val(contents);
			   document.getElementById("passportNotxt").style.display = 'block';
			   document.getElementById("ppNoOk").style.display = 'block';
		   }
		   
		   function doPassportNo() {     
			   var transId = document.getElementById('transactionTxt').innerText;
			   var contents = {
					   passportNo:  $("#passportNotxt").val(),
					   passportYear: "",
					   transactionId: transId
			   }
			   document.getElementById("passportNoDislay").style.display = 'block';
			   document.getElementById("passportNotxt").style.display = 'none';
			   document.getElementById("ppNoOk").style.display = 'none';

			   document.getElementById('passportNoDislay').innerText = $("#passportNotxt").val();
		 	   console.log($("#passportNotxt").val());
		 	   
			 	  $.ajax({
			 	      type: "POST",
			 	      url: "${passportUrl}", 
			 	      dataType: "JSON",
			 	      contentType: "application/json; charset=utf-8",
			 	      data: contents,
			 	      success: function(response) {
		                    if(response.result)
		                    	alert('Đã cập nhật thành công');
		                },
		                error: function(e) {
		                    alert('Error: ' + e);
		                } 
			 	  });
		   }

		///2. Sửa năm hộ chiếu
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
       //==========================
    	   
    	/// ======= Check chữ ký ====
		   
		   function requestCheckSign() { 
			   var transId = document.getElementById('transactionTxt').innerText;
			   investigationHitData.action = "${checkSignUrl}" + "/" + transId; 
			  document.forms["investigationHitData"].submit();  
		   }
		   
  		// ========== ENd check chữ ký
  		
  		/// ======= Kiểm tra api ====
		   
		   function requestAPI() { 
			   var transId = document.getElementById('transactionTxt').value;
			   var contents = {
					   transactionId: transId
			   }
			   
			   $.ajax({
			 	      type: "POST",
			 	      url: "${apiUrl}", 
			 	      data: contents,
			 	      dataType: "text",
			 	      contentType: "application/json; charset=utf-8",
			 	      success: function(response) {
			 	    	 	alert(response);
			 	    	 	investigationHitData.action = "${backBtnUrl}";
			 			    investigationHitData.submit();
		                },
		                error: function(e) {
		                    alert('Thành công');
		                } 
			 	  });
		   }
		   
  		// ========== ENd check chữ ký
    	 
</script>
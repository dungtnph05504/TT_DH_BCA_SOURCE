<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="col" uri="colfunction"%>
<%@page import="org.apache.commons.codec.binary.Base64"%> 
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page
	import="com.nec.asia.nic.investigation.controller.InvestigationHit"%>  
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>	
<c:url var="transDocumentsUrl" value="/servlet/transactionEnquiry/getTransDocuments" />
<c:url var="showPDFProofDoc" value="/servlet/transactionEnquiry/showPDFProofDoc" />
<c:url var="fpInfoUrl" value="/servlet/transactionEnquiry/fpInfo" />
<c:url var="convertImgUrl" value="/servlet/investigation/formatWsqToJpg" />

<c:set var="zero" scope="page" value="0"/>	
<% int zeru = 0;%> 	
<script type="text/javascript">
var ppType = '${nicData.passportType}';
//alert('${inv_none}');
setTimeout(function(){
	if(ppType == 'P'){
    	$("#btnInfoCustom").css('display', 'none');
    	$("#btnsignerImage").css('display', 'none');
    }
}, 1);
/*$(document).ready(function() {
    if(ppType == 'P'){
    	$("#btnInfoCustom").css('display', 'none');
    	$("#btnsignerImage").css('display', 'none');
    }
});*/
</script>
<div id="class-tab-s" style="width: 100%;">
	<input type="button" class="btn btn-primary" id="btnInfoPerson"
		onclick="openform('1')" value="Thông tin cá nhân" /> 
		<!--<c:if test="${nicData.passportType != 'P'}">
		</c:if>-->
		<input
			type="button" class="btn btn-primary" id="btnInfoCustom"
			onclick="openform('2')" style="background: gray" value="Thông tin quyết định" /> 
		<input type="button"
		class="btn btn-primary" id="btnInvestigationP" onclick="openform('3')"
		style="background: gray" value="Thông tin nghi vấn - điều tra" />
		<input type="button"
		class="btn btn-primary" id="btnResponseBCA" onclick="openform('7')"
		style="background: gray" value="Thông tin truy vấn từ BCA" />
		<!--<c:if test="${nicData.passportType != 'P'}">
		</c:if>-->
		<input type="button"
			class="btn btn-primary" id="btnsignerImage" onclick="openform('6')"
			style="background: gray" value="So sánh chữ ký quyêt định" />
		<input type="button"
		class="btn btn-primary" id="btnDocumentAttch" onclick="openform('4')"
		style="background: gray" value="Tài liệu đính kèm" />
		<input type="button"
		class="btn btn-primary" id="btnImageFP" onclick="openform('5')"
		style="background: gray" value="Ảnh vân tay" />
	<hr style="margin-top: 5px;">
</div>

<div class="tab-content" id="infoPerson">
	<div class="col-sm-4">
		<div id="dialog-image_photoCompare" style="display: block;">
			<div class="centerCaption">

				<div style="text-align: center">
					<!-- photo dimension: 480 (width) x 640 (height) -->
					<c:choose>
						<c:when test="${not empty photoStr}">
							<div>
								<img src="data:image/jpg;base64,${photoStr}"
									class="img-border " style="width:160px" />
							</div>
						</c:when>
						<c:otherwise>
							<div>
								<img src="<c:url value='/resources/images/No_Image.jpg' />"
									class="img-border" style="width:160px"
									title="Hit Candidate" />
								<div style="font-weight: bold; color: #000; text-align: center;">Không có ảnh</div>
							</div>
						</c:otherwise>
					</c:choose>
				</div>

			</div>
		</div>
		<div class="form-group text" style="padding-top: 230px;">
			<label class="control-label">Mã giao dịch:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.transactionId}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Số hộ chiếu cũ:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.prevPassportNo}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Loại hộ chiếu đăng ký:</label> <label style="color: #337ab7;">
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
			</label>
		</div>
<%-- 		<div class="form-group text">
			<label class="control-label">Họ:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.surnameFull}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Tên:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.firstnameFull}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Tên đệm:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.middlenameFull}" /></label>
		</div> --%>
		<div class="form-group text">
			<label class="control-label">Họ và tên đầy đủ:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.surnameLine1}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Giới tính:</label> <label style="color: #337ab7;"> <c:choose>
					<c:when test="${nicData.nicRegistrationData.gender == 'M'}">
	                        Nam
	                    </c:when>
					<c:otherwise>
	                        Nữ
	                    </c:otherwise>
				</c:choose>
			</label>
		</div>
		<div class="form-group text">
			<label class="control-label">Nơi sinh:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.placeOfBirth}" /></label>
		</div>
	</div>
	<%-- <div class="col-sm-4">
		<div class="form-group text">
			<label class="control-label">Mã giao dịch:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.transactionId}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Số hộ chiếu cũ:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.prevPassportNo}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Loại hộ chiếu đăng ký:</label> <label style="color: #337ab7;">
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
			</label>
		</div>
		<div class="form-group text">
			<label class="control-label">Họ:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.surnameFull}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Tên:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.firstnameFull}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Tên đệm:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.middlenameFull}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Họ và tên đầy đủ:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.surnameLine1}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Giới tính:</label> <label style="color: #337ab7;"> <c:choose>
					<c:when test="${nicData.nicRegistrationData.gender == 'M'}">
	                        Nam
	                    </c:when>
					<c:otherwise>
	                        Nữ
	                    </c:otherwise>
				</c:choose>
			</label>
		</div>
		<div class="form-group text">
			<label class="control-label">Nơi sinh:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.placeOfBirth}" /></label>
		</div>
	</div> --%>
	<div class="col-sm-4">
		<div class="form-group text">
			<label class="control-label">Ngày sinh:</label> <label style="color: #337ab7;">
			<fmt:formatDate value="${nicData.nicRegistrationData.dateOfBirth}" pattern="dd/MM/yyyy" var="newdatevar" />
            <c:out value="${newdatevar}" /></label>
			<!--<c:out value="${nicData.nicRegistrationData.dateOfBirth}" />-->
		</div>
		<div class="form-group text">
			<label class="control-label">CMND/CCCD:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nin}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Ngày cấp CMND/CCCD:</label> <label style="color: #337ab7;">
			<fmt:formatDate value="${nicData.nicRegistrationData.dateNin}" pattern="dd/MM/yyyy" var="newdateNin" />
            <c:out value="${newdateNin}" /></label>
			<!--<c:out value="${nicData.dateNinDesc}" /></label>-->
		</div>
		<div class="form-group text">
			<label class="control-label">Cơ quan cấp phát:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.addressNin}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Dân tộc:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.nation}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Tôn giáo:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.religion}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Tình trạng hôn nhân:</label> <label style="color: #337ab7;">
				<c:out value="${nicData.nicRegistrationData.maritalStatus}" />
			</label>
		</div>
		<div class="form-group text">
			<label class="control-label">Tên địa chỉ cơ quan:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.addressCompany}" /></label>
		</div>
	
		<div class="form-group text">
			<label class="control-label">Nghề nghiệp:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.job}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Email:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.email}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Số điện thoại:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.contactNo}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Mức độ ưu tiên cấp hộ chiếu:</label> <label style="color: #337ab7;">
				<c:if test="${nicData.priority == '1'}">
					Thông thường
				</c:if>
				<c:if test="${nicData.priority == '2'}">
					Nhanh
				</c:if>
				<c:if test="${nicData.priority == '3'}">
					Ngay lập tức
				</c:if>
			</label>
		</div>
	</div>
	<div class="col-sm-4">
		<div class="form-group text">
			<label class="control-label">Nhận trả qua bưu điện:</label> <label style="color: #337ab7;">
				<c:choose>
					<c:when test="${nicData.nicRegistrationData.methodReceive == 1}">
	                        Có
	                    </c:when>
					<c:otherwise>
	                        Không
	                    </c:otherwise>
				</c:choose>
			</label>
		</div>
		<div class="form-group text">
			<label class="control-label">Địa chỉ nơi nhận:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.addressReceive}" /></label>
		</div>
		<!--<div class="form-group text">
			<label class="control-label">Quốc gia:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.nationality}" /></label>
		</div>-->
		<div class="form-group text">
			<label class="control-label">Tỉnh/Thành phố:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.addressLine5}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Quận/Huyện:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.addressLine4}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Địa chỉ thường trú:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.addressLine1}" /></label>
		</div>
		<!--<div class="form-group text">
			<label class="control-label">Địa chỉ tạm trú:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.addressTempStreet}" /></label>
		</div>-->
		<div class="form-group text">
			<label class="control-label">Họ tên bố:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.fatherSurname}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Ngày sinh của bố:</label> <label style="color: #337ab7;">
			<fmt:formatDate value="${nicData.nicRegistrationData.fatherDob}" pattern="dd/MM/yyyy" var="newdatevarf" />
            <c:out value="${newdatevarf}" /></label>
			<!--<c:out value="${nicData.fatherDobDesc}" /></label>-->
		</div>
		<div class="form-group text">
			<label class="control-label">Họ tên mẹ:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.motherSurname}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Ngày sinh của mẹ:</label> <label style="color: #337ab7;">
			<fmt:formatDate value="${nicData.nicRegistrationData.motherDob}" pattern="dd/MM/yyyy" var="newdatevarm" />
            <c:out value="${newdatevarm}" /></label>
			<!--<c:out value="${nicData.motherDobDesc}" /></label>-->
		</div>
		<div class="form-group text">
			<label class="control-label">Họ tên vợ/chồng:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.spouseSurname}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Ngày sinh của vợ/chồng:</label> <label style="color: #337ab7;">
			<fmt:formatDate value="${nicData.nicRegistrationData.spouseDob}" pattern="dd/MM/yyyy" var="newdatevars" />
            <c:out value="${newdatevars}" /></label>
			<!--<c:out value="${nicData.spouseDobDesc}" /></label>-->
		</div>
		<div class="form-group text">
			<label class="control-label">Ngày tạo giao dịch:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.createDesc}" /></label>
		</div>
	</div>
</div>

<div class="tab-content" id="infoCustom" style="display: none">
	<div class="col-sm-4">
		<div class="form-group text">
			<label class="control-label">Thứ tự người đi công tác:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.stManWorking}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Số quyết định:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.numDecision}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Ngày ký quyết định:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.dateDecisionDesc}" /> </label>
		</div>
		<div class="form-group text">
			<label class="control-label">Cơ quan chủ quản:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.govDecision}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Người ký quyết định:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.signerDecision}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Tên cơ quan:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.nameDepartment}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Địa chỉ cơ quan:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.addressDepartment}" /></label>
		</div>
	</div>
	<div class="col-sm-4">
		<div class="form-group text">
			<label class="control-label">Điện thoại cơ quan:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.phoneDepartment}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Chức vụ:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.position}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Bậc hàm:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.rank}" /></label>
		</div>
	
		<div class="form-group text">
			<label class="control-label">Chức vụ tiếng anh:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.postionEng}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Loại:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.civilServants}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Bậc:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.levelRank}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Ngạch:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.quantum}" /></label>
		</div>
	</div>
	<div class="col-sm-4">
		<div class="form-group text">
			<label class="control-label">Mục đích chuyến đi:</label> <label style="color: #337ab7;">
				<c:out value="${nicData.nicRegistrationData.purpose}" />
			</label>
		</div>
		<div class="form-group text">
			<label class="control-label">Kinh phí chuyến đi:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.typeExpense}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Đi đến nước:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.toNation}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Quá cảnh nước:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.transitNation}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Dự định xuất cảnh:</label> 
				<label style="color: #337ab7;"><c:out
						value="${nicData.estimateFromDesc}" /></label>
		</div>
		<div class="form-group text">
			<label class="control-label">Thời gian làm việc nước ngoài:</label> <label style="color: #337ab7;"><c:out
					value="${nicData.nicRegistrationData.estimateTo}" /></label>
		</div>
	</div>
</div>

<div class="tab-content" id="investigationP" style="display: none">
		<!--content start -->
	<c:if test="${inv_none != 'false'}">
		<span style="color: blue; font-size: 12px;">Hồ sơ hợp lệ.</span>
	</c:if>	
	<div id="content_main">
		   <div class="container">
   <div class="row">
   <div class="roundedBorder ov_hidden">
		<div class="c"></div> 
 
		<script>
		   var numberOfHits = <%=((Integer)request.getAttribute("invHitsSize")).intValue()%>;
		   var currentHitOffered = 0;
		    
		   function goToNextHit() {
			  
		      if(numberOfHits==0){
		    	  return;
		      }

		      if(currentHitOffered == (numberOfHits - 1)){
		    	  return;
		      }
		      
		      currentHitOffered++;

		      eval("document.location.href = '#HitNumber_"+currentHitOffered.toString()+"';");
		 	}
		    
		   function goToPreviousHit() {
			   
		      if(numberOfHits==0){
		    	  return;
		      }

		      if(currentHitOffered == 0){
		    	  return;
		      }
		      
		      currentHitOffered--;
			     
		      eval("document.location.href = '#HitNumber_"+currentHitOffered.toString()+"';");
		 	}
		    
		   function goToFirstHit() {
			  
		      if(numberOfHits==0){
		    	  return;
		      }

		      currentHitOffered=0;

		      eval("document.location.href = '#HitNumber_"+currentHitOffered.toString()+"';");
		 	}
		    
		   function goToLastHit() {
			   
		      if(numberOfHits==0){
		    	  return;
		      }

		      currentHitOffered=(numberOfHits - 1);
			     
		      eval("document.location.href = '#HitNumber_"+currentHitOffered.toString()+"';");
		 	}
		    
		   function setAllValue(allValue) { 
			   
		      if(<c:out value="${invHitsSize}" />==0){
		    	  return;
		      }


			  for (i = 0; i < <%=((Integer)request.getAttribute("invHitsSize")).intValue()%>; i++) { 
					eval("$('input:radio[name="+"duplicateDecision"+"_"+i.toString()+"]').filter('[value="+allValue+"]').prop('checked', true);");
			  } 
	    	  
	    	  return false;
		 	} 
		   
		</script> 
				
				
		
		<div class="waitingWhenDoneWaiting" style="display:block;" >							
					<!-- ================================================= all hits - start ================================================= -->
					<!-- ================================================= all hits - start ================================================= -->
					<!-- ================================================= all hits - start ================================================= -->
					<!-- ================================================= all hits - start ================================================= -->
					<!-- ================================================= all hits - start ================================================= -->
					<!-- ================================================= all hits - start ================================================= -->
					<!-- ================================================= all hits - start ================================================= -->
					<!-- ================================================= all hits - start ================================================= -->
					<!-- ================================================= all hits - start ================================================= -->
					<!-- ================================================= all hits - start ================================================= -->
									
					<!-- TRUNG -->
					<c:choose> 
						  <c:when test="${not inv_none}">  
								<input type="hidden" name="TransactionId_"     value="${invHits[rowCtr].hitCandidateListTransId}"  id="TransactionId_"/>
				          </c:when>	
				          <c:otherwise>  
								<input type="hidden" name="TransactionId_"     value="${nicData.transactionId}"  id="TransactionId_"/>
				          </c:otherwise>
				          </c:choose> 
				                 	<!-- END -->
			                <%
			                	if ((Integer)request.getAttribute("invHitsSize") > 0){
			                %>	 
				                <%
				                	for (int rowCounter = 0; rowCounter < (Integer)request.getAttribute("invHitsSize"); rowCounter++){
					                	request.setAttribute("rowCtr",rowCounter);
					                	request.setAttribute("rowCtrString",Integer.toString(rowCounter));
				                %>
				                  
    								<a  name="HitNumber_<c:out value="${rowCtr}" />"> </a>
									<input type="hidden" 	name="searchResultId_<c:out value="${rowCtr}" />"    value="${invHits[rowCtr].searchResultId}" 
															id="searchResultId_<c:out value="${rowCtr}" />" 
											/>
									<input type="hidden" name="hitTransactionId_<c:out value="${rowCtr}" />"     value="${invHits[rowCtr].hitTransactionId}" 
															id="hitTransactionId_<c:out value="${rowCtr}" />"
											/>
				                 
									<!-- ======================================= some main fp stuff - start ===================================================== -->
									<!-- ======================================= some main fp stuff - start ===================================================== -->
									<!-- ======================================= some main fp stuff - start ===================================================== -->
									<!-- ======================================= some main fp stuff - start ===================================================== -->
									<!-- ======================================= some main fp stuff - start ===================================================== -->
				                   
										<script type="text/javascript">
											
											$(document).ready(function(){
												setFP2Main<%=rowCounter%>();
											});
										
										   function setFP2Main<%=rowCounter%>() {
												var applet3 = document.getElementById('investigationApplet');
												if (applet3) {
													try {   
														 
														<%    
															try {
																Map<String, String> mainFpIndicatorMap = (Map<String, String>) ((List<InvestigationHit>) (request
																		.getAttribute("invHits"))).get(zeru).mainFpIndicatorMap;
																
																for (int i=1;i<=10;i++){ 
																	String amainfpIndicator = (String) mainFpIndicatorMap.get(Integer.toString(i));
																	String i2=Integer.toString(i);
																	if (i2.length()<2){
																		i2="0"+i2;
																	}
																	  
																	request.setAttribute("mainfp"+i+"Indicator"+"_"+rowCounter+"_", amainfpIndicator);
																	} 
										
															} catch (Exception e) {
																e.printStackTrace();
															}
														%> 
														
													} catch (e) { 
														var errorDetails = '';
														if(e.number)		errorDetails += 'e.number:'+e.number+'\n';
														if(e.name)			errorDetails += 'e.name:'+e.name+'\n';
														if(e.message)		errorDetails += 'e.message:'+e.message+'\n';
														if(e.description)	errorDetails += 'e.description:'+e.description+'\n'; 
													}
												} 
											} 
										
										</script> 
									<!-- ======================================= some main fp stuff - end ===================================================== -->
									<!-- ======================================= some main fp stuff - end ===================================================== -->
									<!-- ======================================= some main fp stuff - end ===================================================== -->
									<!-- ======================================= some main fp stuff - end ===================================================== -->
									<!-- ======================================= some main fp stuff - end ===================================================== -->
				                 
				                 
									<!-- ======================================= some hit fp stuff - start ===================================================== -->
									<!-- ======================================= some hit fp stuff - start ===================================================== -->
									<!-- ======================================= some hit fp stuff - start ===================================================== -->
									<!-- ======================================= some hit fp stuff - start ===================================================== -->
									<!-- ======================================= some hit fp stuff - start ===================================================== -->
				                     
										<script type="text/javascript">
											
											$(document).ready(function(){
												setFP2Hit<%=rowCounter%>();
											});
										
										   function setFP2Hit<%=rowCounter%>() {
												var applet3 = document.getElementById('investigationApplet');
												if (applet3) {
													try {   
														<%    
															try {
																Map<String, String> fpIndicatorMap = (Map<String, String>) ((List<InvestigationHit>) (request
																		.getAttribute("invHits"))).get(rowCounter).hitFpIndicatorMap;
																
																for (int i=1;i<=10;i++){ 
																	String afpIndicator = (String) fpIndicatorMap.get(Integer.toString(i));
																	String i2=Integer.toString(i);
																	if (i2.length()<2){
																		i2="0"+i2;
																	}
																	  
																	request.setAttribute("hitfp"+i+"Indicator"+"_"+rowCounter+"_", afpIndicator);
																	} 
										
															} catch (Exception e) {
																e.printStackTrace();
															}
														%> 
														
													} catch (e) { 
														var errorDetails = '';
														if(e.number)		errorDetails += 'e.number:'+e.number+'\n';
														if(e.name)			errorDetails += 'e.name:'+e.name+'\n';
														if(e.message)		errorDetails += 'e.message:'+e.message+'\n';
														if(e.description)	errorDetails += 'e.description:'+e.description+'\n'; 
													}
												} 
											} 
										
										</script> 
									<!-- ======================================= some hit fp stuff - end ===================================================== -->
									<!-- ======================================= some hit fp stuff - end ===================================================== -->
									<!-- ======================================= some hit fp stuff - end ===================================================== -->
									<!-- ======================================= some hit fp stuff - end ===================================================== -->
									<!-- ======================================= some hit fp stuff - end ===================================================== -->
				                 
									
									<!--1Hit--> 
									<div
										style="margin: 15px 5px 5px 5px; border: 1px solid #000; border-radius: 5px;">
										<!--1Hit_inner-->
										<div style="margin: 5px"> 
											
											<c:if test="${not inv_noHit}">
												<div
													style="margin: 5px 0px 5px 0px">
													<!--1Hit_inner-->
													<div style="margin: 0px">
														<div
															style="align: center; font-weight: bold; height: 27px"
															class="sno">
															<div class="table_header" style="padding-top: 3px">
																Trùng&nbsp;<c:out value="${rowCtr + 1}" />&nbsp;của&nbsp;
																<c:out value="${invHitsSize}" />
															</div>
														</div>
													</div>
												</div>
											</c:if>
			

													
											<div class="theOneRow">
												<div 
													<c:if test="${not inv_noHit}"> 
														class="theBlockLeft" 
													</c:if>
													<c:if test="${inv_noHit}"> 
														class="noHit_theBlock" 
													</c:if>
												>
													<table align="left" class="round_border" width="100%"
														 height="27"  border="0">
														<tr>
															<td width="50%" height="25" align="center"
																style="font-weight: bold" class="sno"><span
																class="table_header">Người nộp hồ sơ</span></td>
														</tr>
													</table>
												</div> 
												
												<c:if test="${not inv_noHit}"> 
													<!-- <div class="theBlockRightThird">
														<table align="left" class="round_border" width="100%"
															 height="27"  border="0">
															<tr>
																<td height="25" width="50%" align="center"
																	style="font-weight: bold" class="sno"><span
																	class="table_header">Bản sao?</span></td>
															</tr>
														</table>
													</div> -->
													
													<div class="theBlockRight" >
														<table align="left" class="round_border" width="100%"
															 height="27"  border="0">
															<tr>
																<td height="25" width="50%" align="center"
																	style="font-weight: bold" class="sno"><span
																	class="table_header">Đối tượng trùng</span></td>
															</tr>
														</table>
													</div>
												</c:if>
											</div> 			
			
			
											<div class="theOneRow">
												<div 
													<c:if test="${not inv_noHit}"> 
														class="theBlockLeft" 
													</c:if>
													<c:if test="${inv_noHit}"> 
														class="noHit_theBlock" 
													</c:if>
												>
															<table width="100%" height="150" border="0"> 
																<tr>
																	<td  align="left"  style="text-align: left;" style="max-width:45%" >
																		<div   
																			<c:choose>
																				<c:when test="${inv_noHit and not empty invHits[rowCtr].mainCandidatePhoto}">
																										id="image-popup_photoCompare_<c:out value="${rowCtr}" />" 
																										class="thumbnail-item-no-margin onHoverMousePointerThumb" 
											 									</c:when> 
																				<c:otherwise>
																					 					 class="thumbnail-item-no-margin" 	
																				</c:otherwise>
																			</c:choose> 
																		>
																			<c:if test="${invHits[rowCtr].mainCandidatePhoto!=null}">
																				<img
																					src="data:image/jpg;base64,${invHits[rowCtr].mainCandidatePhoto}"
																					width="324" height="405" class="thumbnail" style="max-width:100%" />
																			</c:if>
																			<c:if test="${invHits[rowCtr].mainCandidatePhoto==null}">
																				<img
																					src="<c:url value='/resources/images/No_Image.jpg'/>"
																					width="324" height="405" class="img-border"  style="max-width:100%" /> 
																			</c:if>
																		</div>
																	</td>
																	<td width="50%" valign="top" style="text-align: center;  "  > 
																		<div style="margin-top: 50px; "  
																			<c:choose>
																				<c:when test="${inv_noHit and not empty invHits[rowCtr].mainCandidateSignature}">
																					id="image-popup_signatureCompare_<c:out value="${rowCtr}" />"
																					class="thumbnail-item onHoverMousePointerThumb"
																				</c:when>
																				<c:otherwise>
																					class="thumbnail-item"
																				</c:otherwise>
																			</c:choose> 
																		>
																			<c:if test="${invHits[rowCtr].mainCandidateSignature!=null}">
																				<img id="mainCandSign_Thumb"
																					src="data:image/jpg;base64,${invHits[rowCtr].mainCandidateSignature}"
																					height="60" class="thumbnail "  style="max-width:100%" /> 
																			</c:if>
																			<!-- phúc edit đóng khung ảnh nếu không có
																			<c:if test="${invHits[rowCtr].mainCandidateSignature==null}">
																				<img src="<c:url value='/resources/images/No_Image.jpg'/>"
																					height="60" class="thumbnail"  style="max-width:100%" />
				
																			</c:if>-->
																		</div> 
																		<div  style="margin-top: 60px; "  >
																			<table width="100%" border="0" class="t_header" style="width: 96%; margin-left: 2%;margin-right: 2%"> 
																			  
																				<% for (int aCounter=1; aCounter<=10; aCounter++){
																					request.setAttribute("aCounter", Integer.valueOf(aCounter));
																					
																					String amainfpIndicator = (String)request.getAttribute("mainfp"+aCounter+"Indicator"+"_"+rowCounter+"_"); 
																					if (amainfpIndicator==null 
																							|| !amainfpIndicator.equals("N")
																						){
																						continue;	
																					} 
																				%>
																					<tr>
																						<td style="width: 22%; ">
																							&nbsp; 
																						</td> 
																						<td style="width: 45%; text-align: left; border: none;" class="text">
																							<c:choose>
																								<c:when test="${aCounter eq 1}">Ngón cái bên phải</c:when> 
																								<c:when test="${aCounter eq 2}">Ngón trỏ bên phải</c:when> 
																								<c:when test="${aCounter eq 3}">Ngón giữa bên phải</c:when> 
																								<c:when test="${aCounter eq 4}">Ngón đeo nhẫn bên phải</c:when> 
																								<c:when test="${aCounter eq 5}">Ngón út bên phải</c:when> 
																								<c:when test="${aCounter eq 6}">Ngón cái bên trái</c:when> 
																								<c:when test="${aCounter eq 7}">Ngón trỏ bên trái</c:when> 
																								<c:when test="${aCounter eq 8}">Ngón giữa bên trái</c:when> 
																								<c:when test="${aCounter eq 9}">Ngón đeo nhẫn bên trái</c:when> 
																								<c:when test="${aCounter eq 10}">Ngón út bên trái</c:when> 
																							</c:choose> 
																						</td> 
																				
																						<td
																							style=" width: 10%;text-align: center; border: none; ">
																							<c:forEach var="qualityMap"
																								items="${invHits[rowCtr].mainCandidateFpQuality}">
																								<c:if test="${qualityMap.key eq aCounter}">
																									<c:choose>
																										<c:when
																											test="${qualityMap.key eq aCounter  and  qualityMap.value.fpQuaScr ge qualityMap.value.goodFPQuaScr}">
																											<div id="quality10" title="${qualityMap.value.fpQuaScr}"
																												class=""
																												style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
																												<p class=""
																													style="text-align: center; valign: top; margin: 0; padding: 5px 6px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
																											</div>
																										</c:when>
																										<c:when
																											test="${qualityMap.key eq aCounter  and (qualityMap.value.fpQuaScr ge qualityMap.value.accetableFPQuaScr and qualityMap.value.fpQuaScr lt qualityMap.value.goodFPQuaScr)}">
																											<div id="quality10" title="${qualityMap.value.fpQuaScr}"
																												class=""
																												style="background-image: url('<c:url value="/resources/images/orange_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
																												<p class=""
																													style="text-align: center; valign: top; margin: 0; padding: 5px 6px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
																											</div>
																										</c:when>
																										<c:otherwise>
																											<div id="quality10" title="${qualityMap.value.fpQuaScr}"
																												class=""
																												style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
																												<p class=""
																													style="text-align: center; valign: top; margin: 0; padding: 5px 6px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
																											</div>
																										</c:otherwise>
																									</c:choose>
																								</c:if>
																							</c:forEach>  
																						</td> 
																						<td style="width: 23%; ">
																							&nbsp; 
																						</td> 
																					</tr>   
																				<%}%>
																			</table>
																		</div> 
																	</td>
																</tr>
															</table>
												</div> 
												
												
												
												<c:if test="${not inv_noHit}">
													<%-- <div class="theBlockRightThird">
															<br> 
															<div style="width:50%; margin-left: 25%; margin-right:25%; text-align:left; font-size: 15px; font-weight: bold;">
																<input type="radio" name="duplicateDecision_<c:out value="${rowCtr}" />" class="yes_chk" 
																					value="Y" checked style="width:  20%" /><span
																					style="margin-left:2px; text-align:left; font-size: 15px; font-weight: bold;width:  80%">Có</span>
															</div>	
															<br> 
															<div style="width:50%; margin-left: 25%; margin-right:25%; text-align:left; font-size: 15px; font-weight: bold;">
																<input type="radio" name="duplicateDecision_<c:out value="${rowCtr}" />" class="no_chk" 
																					value="N"  style="width:  20%" /><span
																					style="margin-left:2px; text-align:left; font-size: 15px; font-weight: bold;width:  80%">Không</span>
															</div>					
															<br>
															<br>
															<br>
															<div style="text-align:center; font-size: 15px; font-weight: bold;">Ghi chú</div> 
														<textarea id="remarks_<c:out value="${rowCtr}" />" rows="4" cols="50"  style=" width: 80%; margin-left:  auto; margin-right:  auto; height: 225px;"></textarea>
													</div> --%>
												  
													<div class="theBlockRight">
																<table width="100%" height="150" border="0"> 
																	<tr>
																		<td    align="left"    style=" text-align: left;" style="max-width:45%" >
																			<div
																			 
																				<c:choose>
																					<c:when test="${not empty invHits[rowCtr].hitCandidatePhoto and not empty invHits[rowCtr].mainCandidatePhoto}">
																											id="image-popup_photoCompare_<c:out value="${rowCtr}" />" 
																											class="thumbnail-item-no-margin onHoverMousePointerThumb" 
												 									</c:when> 
																					<c:otherwise>
																						 					 class="thumbnail-item-no-margin" 	
																					</c:otherwise>
																				</c:choose>
																			 
																			>
																				<c:if test="${invHits[rowCtr].hitCandidatePhoto!=null}">
																					<img
																						src="data:image/jpg;base64,${invHits[rowCtr].hitCandidatePhoto}"
																						width="324" height="405" class="thumbnail doGetAJpgSafeVersion"  style="max-width:100%" /> 
																				</c:if>
																				<c:if test="${invHits[rowCtr].hitCandidatePhoto==null}">
																					<img
																						src="<c:url value='/resources/images/No_Image.jpg'/>"
																						width="324" height="405" class="img-border"  style="max-width:100%" />
																				</c:if>
																			</div>
																		</td>
																		<td width="50%"     valign="top"  style="text-align: center; ">
																			<div  style="margin-top: 50px; "  
																			 
																				<c:choose>
																					<c:when test="${not empty invHits[rowCtr].hitCandidateSignature and not empty invHits[rowCtr].mainCandidateSignature}">
																						id="image-popup_signatureCompare_<c:out value="${rowCtr}" />"
																						class="thumbnail-item onHoverMousePointerThumb"
																					</c:when>
																					<c:otherwise>
																						class="thumbnail-item"
																					</c:otherwise>
																				</c:choose>
																			 
																			>
																				<c:if test="${invHits[rowCtr].hitCandidateSignature!=null}">
																					<img
																						src="data:image/jpg;base64,${invHits[rowCtr].hitCandidateSignature}"
																						height="60" class="thumbnail doGetAJpgSafeVersion"  style="max-width:100%"/> 
																				</c:if>
																				<c:if test="${invHits[rowCtr].hitCandidateSignature==null}">
																					<img
																						src=<c:url value='/resources/images/No_Image.jpg'/>
																						height="60" class="thumbnail"  style="max-width:100%"/>
					
																				</c:if>
																			</div> 
																			
																			<div  style="margin-top: 60px; " > 
																				<table width="100%" border="0" class="t_header" style="width: 96%; margin-left: 2%;margin-right: 2%">
																				 
																					<!-- ======================================= fp compare stuff - prepare - start ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - start ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - start ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - start ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - start ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - start ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - start ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - start ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - start ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - start ===================================================== -->
																                 			
												
																						<script type="text/javascript">
																							<%  
																								try {  
																									Map<String, byte[]> mainFpMap = (Map<String, byte[]>) ((List<InvestigationHit>) (request
																											.getAttribute("invHits"))).get(rowCounter).mainFpMap;  
									
																								    for (int i=1;i<=10;i++){
																								    	 String is2= Integer.toString(i) ;
																								    	 is2=((is2.length()==2)?is2:"0"+is2);  
									
																								    	 String aString=Base64.encodeBase64URLSafeString((byte[]) mainFpMap.get(is2));
																										 %>
																											var CompareMainFP<%=is2%>Base64StrSpid<%="_"+rowCounter+"_"%>  = <%=((aString==null)?"null":"'"+aString+"'")%>;
																							    	 	 <%   
																								    }  
																								} catch (Exception e) {
																									e.printStackTrace();
																								}
																							%> 
								
																							<%  
																								try {  
																									Map<String, byte[]> hitFpMap = (Map<String, byte[]>) ((List<InvestigationHit>) (request
																											.getAttribute("invHits"))).get(rowCounter).hitFpMap;
									
																								    for (int i=1;i<=10;i++){
																								    	 String is2= Integer.toString(i) ;
																								    	 is2=((is2.length()==2)?is2:"0"+is2);  
									
																								    	 String aString=Base64.encodeBase64URLSafeString((byte[]) hitFpMap.get(is2));
																										 %>
																											var CompareHitFP<%=is2%>Base64StrSpid<%="_"+rowCounter+"_"%>  = <%=((aString==null)?"null":"'"+aString+"'")%>;
																							    	 	 <%   
																								    }    
																								} catch (Exception e) {
																									e.printStackTrace();
																								}
																							%> 
																							
																							function Compare_<%=rowCounter%>_(index){
																								var mainfp=eval("CompareMainFP"+index+"Base64StrSpid"+"_"+<%=rowCounter%>+"_");
																								var hitfp =eval("CompareHitFP" +index+"Base64StrSpid"+"_"+<%=rowCounter%>+"_");
																								 
																								var compareFingerprints = document.investigationApplet.compareFingerprints(mainfp,hitfp); 
																							}
																						</script>
															  
												
																					<!-- ======================================= fp compare stuff - prepare - end ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - end ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - end ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - end ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - end ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - end ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - end ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - end ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - end ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - end ===================================================== -->
																                 			 
																					<% for (int aCounter=1; aCounter<=10; aCounter++){
																						request.setAttribute("aCounter", Integer.valueOf(aCounter));
																						 
																						String ahitfpIndicator  = (String)request.getAttribute("hitfp" +aCounter+"Indicator"+"_"+rowCounter+"_");
																						if (ahitfpIndicator==null  
																								|| !ahitfpIndicator.equals("N") 
																								){
																							continue;
																						} 
																					%>
																						<tr >
																							<td style="width:45%; text-align: left; border: none;" class="text">
																								<c:choose>
																									<c:when test="${aCounter eq 1}">Ngón cái bên phải</c:when> 
																									<c:when test="${aCounter eq 2}">Ngón trỏ bên phải</c:when> 
																									<c:when test="${aCounter eq 3}">Ngón giữa bên phải</c:when> 
																									<c:when test="${aCounter eq 4}">Ngón đeo nhẫn bên phải</c:when> 
																									<c:when test="${aCounter eq 5}">Ngón út bên phải</c:when> 
																									<c:when test="${aCounter eq 6}">Ngón cái bên trái</c:when> 
																									<c:when test="${aCounter eq 7}">Ngón trỏ bên trái</c:when> 
																									<c:when test="${aCounter eq 8}">Ngón giữa bên trái</c:when> 
																									<c:when test="${aCounter eq 9}">Ngón đeo nhẫn bên trái</c:when> 
																									<c:when test="${aCounter eq 10}">Ngón út bên trái</c:when> 
																								</c:choose> 
																							</td> 
																				
																							<td
																								style="width:10%; text-align: center; border: none;">
																								<c:forEach var="hitQualityMap"
																									items="${invHits[rowCtr].hitCandidateFpQuality}">
																									<c:if test="${hitQualityMap.key eq aCounter}">
																										<c:choose>
																											<c:when
																												test="${hitQualityMap.key eq aCounter   and  hitQualityMap.value.fpQuaScr ge hitQualityMap.value.goodFPQuaScr}">
																												<div id="quality10"
																													title="${hitQualityMap.value.fpQuaScr}" class=""
																													style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
																													<p class=""
																														style="text-align: center; valign: top; margin: 0; padding: 5px 6px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
																												</div>
																											</c:when>
																											<c:when
																												test="${hitQualityMap.key eq aCounter and (hitQualityMap.value.fpQuaScr ge hitQualityMap.value.accetableFPQuaScr and hitQualityMap.value.fpQuaScr lt hitQualityMap.value.goodFPQuaScr)}">
																												<div id="quality10"
																													title="${hitQualityMap.value.fpQuaScr}" class=""
																													style="background-image: url('<c:url value="/resources/images/orange_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
																													<p class=""
																														style="text-align: center; valign: top; margin: 0; padding: 5px 6px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
																												</div>
																											</c:when>
																											<c:otherwise>
																												<div id="quality10"
																													title="${hitQualityMap.value.fpQuaScr}" class=""
																													style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
																													<p class=""
																														style="text-align: center; valign: top; margin: 0; padding: 5px 6px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
																												</div>
																											</c:otherwise>
																										</c:choose>
																									</c:if>
																								</c:forEach>  
																							</td>
																				 
																							<c:if test="${not empty invHits[rowCtr].investigationForm}">  
																								<c:forEach varStatus="matchMapCount"
																									var="matchMap"
																									items="${invHits[rowCtr].investigationForm.matchScore}">
																										<c:if test='${matchMapCount.index + 1 eq aCounter}'>  
																											<td style="width:45%; text-align: right; border: none;"
																											 
											
																												<%		
																												//<!-- ======================================= fp compare stuff - do - start ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - start ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - start ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - start ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - start ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - start ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - start ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - start ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - start ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - start ===================================================== -->%>
																							                 			 
																													<%for (int ctr=1;ctr<=10;ctr++){
									
																															if(ctr!=aCounter){
																																continue;
																															} 
																														
																															String ctr2=Integer.toString(ctr);
																															if(ctr2.length()<2){
																																ctr2="0"+ctr2;
																															}
																															%>  
																																<%{%>
																																	<%
																																		String mainfpIndicator = (String)request.getAttribute("mainfp"+ctr+"Indicator"+"_"+rowCounter+"_");
																																	    String hitfpIndicator  = (String)request.getAttribute("hitfp" +ctr+"Indicator"+"_"+rowCounter+"_");
																																		
																																		if (	(mainfpIndicator==null || !mainfpIndicator.equals("N"))
																																				|| 
																																				(hitfpIndicator==null  || !hitfpIndicator.equals("N"))
																																			){ %> 
																																		<%}else{ %>
																																			onclick="Compare_<%=rowCounter%>_('<%=ctr2%>');"
																																			class="onHoverMousePointerThumb"
																																		<%}%>
																																<%}%> 
																													<%}%> 
																													
																												<%
																												//<!-- ======================================= fp compare stuff - do - end ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - end ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - end ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - end ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - end ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - end ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - end ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - end ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - end ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - end ===================================================== -->%>
																							                 			 
																											
																											>
																												<c:if
																													test='${matchMap.value <60}'>
																														<span style="width: 70%; border: none;" class="text"><c:out
																																value='${matchMap.value}' />%<img
																															src="<%=request.getContextPath()%>/resources/images/Red.jpg"
																															alt="Red icon" title="No Match"  height="12" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>
																												</c:if>
																												<c:if
																													test='${matchMap.value >=60 && matchMap.value <80}'>
																														<span style="width: 70%; border: none;" class="text"><c:out
																																value='${matchMap.value}' />%<img
																															src="<%=request.getContextPath()%>/resources/images/Blue.jpg"
																															alt="Blue icon" title="Possible Match"  height="12" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>  
																												</c:if>
																												<c:if test='${matchMap.value >=80}'>
																														<span style="width: 70%; border: none;" class="text"><c:out
																																value='${matchMap.value}' />%<img
																															src="<%=request.getContextPath()%>/resources/images/Green.jpg"
																															alt="Green icon" title="Highly Match"  height="12" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>
																												</c:if> 
																											</td>
																										</c:if>
																								</c:forEach>
																							</c:if>
																							<c:if test="${empty invHits[rowCtr].investigationForm}"> 
																								<td>
																								</td>	</td>
																							</c:if>
																				
																						</tr>   
																					<%}%>   
											
																				</table>
																			</div> 
																		</td>
																	</tr>
																</table>
													</div>
												</c:if>
											</div>
											
											
											 
			
											
													 
											<div style="clear: both">
											</div>
											<div style="margin-top: 0px; height:1px;max-height:1px"> 
											</div>		 

											 
			
											
													
											<div class="theOneRow" style="margin-top: 5px">
												<div 
													<c:if test="${not inv_noHit}"> 
														class="theBlockLeft" 
													</c:if>
													<c:if test="${inv_noHit}"> 
														class="noHit_theBlock onHoverMousePointerThumb" 
													</c:if>
													 
													<c:if test="${inv_noHit}"> 
														id="details_btn_textualCompare_<c:out value="${rowCtr}" />"
													</c:if>
												> 
															    <table width="100%" height="80" border="0"
																	cellpadding="2" class="data_table2"> 
		
																	<tr>
																		<td valign="top"  width="33%" class="text" >Mã ứng dụng</td>
																		<td valign="top"  width="1%">:</td>
																		<td valign="top"  width="66%" id="transId_"><c:out value="${jobDetails.transactionId}" />
																		</td>
																	</tr>
		
																	<tr>
																		<td valign="top"  class="text">Ngày giao dịch</td>
																		<td valign="top" >:</td>
																		<td valign="top" ><c:out value="${invHits[rowCtr].mainCandidateDateOfApplication}" />
																		</td>
																	</tr>
		
																	<tr>
																		<td valign="top"  class="text">Loại ứng dụng</td>
																		<td valign="top" >:</td>
																		<td valign="top" ><c:out value="${invHits[rowCtr].mainCandidateTransactionType}" />
																		</td>
																	</tr>
		
																	<tr>
																		<td valign="top"  class="text">Trạng thái hồ sơ</td>
																		<td valign="top" >:</td>
																		<td valign="top" ><c:out value="${invHits[rowCtr].mainCandidateApplicationPassportStatus}" />
																		</td>
																	</tr> 
		
																	<tr>
																		<td valign="top"  class="text">Họ tên</td>
																		<td valign="top" >:</td>
																		<td valign="top" >${invHits[rowCtr].mainCandidateFuN}
																		</td>
																	</tr>
		
																	<!--<tr>
																		<td valign="top"  class="text">Họ</td>
																		<td valign="top" >:</td>
																		<td valign="top" >${invHits[rowCtr].mainCandidateFNShort}
																		</td>
																	</tr>
		
																	<tr>
																		<td valign="top"  class="text">Tên đệm</td>
																		<td valign="top" >:</td>
																		<td valign="top" >${invHits[rowCtr].mainCandidateMNShort}
																		</td>
																	</tr>-->
		
																	<tr>
																		<td valign="top"  class="text">Trung tâm phát hành</td>
																		<td valign="top" >:</td>
																		<td valign="top" >${invHits[rowCtr].mainCandidateIssuingAuthority}
																		</td>
																	</tr>
		
																	<tr>
																		<td valign="top"  class="text">Ngày phát hành</td>
																		<td valign="top" >:</td>
																		<td valign="top" >${invHits[rowCtr].mainCandidateReleaseDate}
																		</td>
																	</tr>
		
																	<tr>
																		<td valign="top"  class="text">Ưu tiên</td>
																		<td valign="top" >:</td>
																		<td valign="top" >${invHits[rowCtr].mainCandidatePriority}
																		</td>
																	</tr>
												  					
												  					<tr>
																		<td colspan="3" style="margin-top:4px; margin-bottom:4px;border-top:solid 1px gray;height:1px"></td>
																	</tr> 
												  					
												  					<c:if test="${empty invHits[rowCtr].mainCandidateInvestigationInformation}">
													  					<tr>
																			<td valign="top"  class="text">&nbsp;</td>
																			<td valign="top" >&nbsp;</td>
																			<td valign="top" >&nbsp;
																			</td>
																		</tr> 
												  					</c:if>
												  					<c:if test="${not empty invHits[rowCtr].mainCandidateInvestigationInformation}">
													  					<tr>
																		<td valign="top"  class="text">Thông tin điều tra</td>
																		<td valign="top" >:</td>
																		<td valign="top" > 
																			<table style="margin: -2px 0px 0px -2px;border-collapse: collapse;padding: 0px;"> 
																				<c:forEach var="mainCandidate_InvestigationInformation" varStatus="anotherCounter"
																						items="${invHits[rowCtr].mainCandidateInvestigationInformation}">
																					<tr> 
																						<td valign="top"  >
																							${mainCandidate_InvestigationInformation.item}
																							<c:if test="${not empty mainCandidate_InvestigationInformation.subItems}">
																								<table>  
																									<c:forEach var="mainCandidate_InvestigationInformationSubData" varStatus="alsoACounter"
																											items="${mainCandidate_InvestigationInformation.subItems}">
																										<tr> 
																											<td valign="top"  >
																												&nbsp;&nbsp;	
																											</td> 
																											<td valign="top"  >
																												${alsoACounter.index+1}.		
																											</td> 
																											<td valign="top"  >
																												&nbsp;&nbsp;	
																											</td> 
																											<td valign="top"  >
																												${mainCandidate_InvestigationInformationSubData.item}	
																											</td> 
																										</tr>   		
																									</c:forEach> 
																								</table> 
																							</c:if>  
																						</td> 
																					</tr>   
																				</c:forEach>  
																			</table> 
																		</td>
																		</tr> 
												  					</c:if>
												  					
												  					<c:if test="${not empty invHits[rowCtr].hitCandidateHitInfo}">
													  					<tr>
																			<td valign="top"  class="text">&nbsp;</td>
																			<td valign="top" >&nbsp;</td>
																			<td valign="top" >&nbsp;
																			</td>
																		</tr> 
												  					</c:if>
		
																</table>  
												</div> 
												
												
												<c:if test="${not inv_noHit}">
													<div class="theBlockRightThird">
														&nbsp;
													</div>
													
													<div class="theBlockRight onHoverMousePointerThumb" id="details_btn_textualCompare_<c:out value="${rowCtr}" />" > 
																    <table height="80" width="100%" border="0"
																			cellPadding="2" class="data_table2">  
	
																		<tr>
																			<td valign="top"  width="33%" class="text">Mã ứng dụng</td>
																			<td valign="top"  width="1%">:</td>
																			<td valign="top"  width="66%"><c:out value="${invHits[rowCtr].hitCandidateListTransId}" /> 
																			</td>
																		</tr>
																	
																		<tr>
																			<td valign="top"  class="text">Ngày giao dịch</td>
																			<td valign="top" >:</td>
																			<td valign="top" ><c:out value="${invHits[rowCtr].hitCandidateDateOfApplication}" />
																			</td>
																		</tr>
																	
																		<tr>
																			<td valign="top"  class="text">Loại ứng dụng</td>
																			<td valign="top" >:</td>
																			<td valign="top" ><c:out value="${invHits[rowCtr].hitCandidateTransactionType}" />
																			</td>
																		</tr>
																	
																		<tr>
																			<td valign="top"  class="text">Trạng thái hồ sơ</td>
																			<td valign="top" >:</td>
																			<td valign="top" ><c:out value="${invHits[rowCtr].hitCandidateApplicationPassportStatus}" />
																			</td>
																		</tr>  
																		<tr>
																			<td valign="top"  class="text">Họ tên</td>
																			<td valign="top" >:</td>
																			<td valign="top"  colspan="2">${invHits[rowCtr].hitCandidateFuN}
																			</td>
																		</tr>
			
																		<!--<tr>
																			<td valign="top"  class="text">Họ</td>
																			<td valign="top" >:</td>
																			<td valign="top"  colspan="2">${invHits[rowCtr].hitCandidateFNShort}
																			</td>
																		</tr>
	
																		<tr>
																			<td valign="top"  class="text">Tên đệm</td>
																			<td valign="top" >:</td>
																			<td valign="top" >${invHits[rowCtr].hitCandidateMNShort}
																			</td>
																		</tr>-->
													  					<tr>
																			<td valign="top"  class="text">Trung tâm phát hành</td>
																			<td valign="top" >:</td>
																			<td valign="top" >${invHits[rowCtr].hitCandidateIssuingAuthority}</td>
																		</tr> 
													  					<tr>
																			<td valign="top"  class="text">Ngày phát hành</td>
																			<td valign="top" >:</td>
																			<td valign="top" >${invHits[rowCtr].hitCandidateReleaseDate}</td>
																		</tr> 
													  					<tr>
																			<td valign="top"  class="text">Ưu tiên</td>
																			<td valign="top" >:</td>
																			<td valign="top" >${invHits[rowCtr].hitCandidatePriority}</td>
																		</tr> 
													  					
													  					<tr>
																			<td colspan="3" style="margin-top:4px; margin-bottom:4px;border-top:solid 1px gray;height:1px"></td>
																		</tr> 
													  					
													  					<tr>
																			<td valign="top"  class="text">Danh mục trùng</td>
																			<td valign="top" >:</td>
																			<td valign="top" >${invHits[rowCtr].hitCandidatehitCategories}
																			</td>
																		</tr> 
																		<c:if test="${not empty invHits[rowCtr].hitCandidateHitInfo}">
														  					<tr>
																				<td valign="top"  valign="top" class="text">Thông tin trùng</td>
																				<td valign="top"  valign="top" >:</td>
																				<td valign="top" > 
																					<table style="margin: -2px 0px 0px -2px;border-collapse: collapse;padding: 0px;"> 
																						<c:forEach var="hitCandidateHitInfoData" varStatus="anotherCounter"
																								items="${invHits[rowCtr].hitCandidateHitInfo}">
																							<tr> 
																								<td valign="top"  >
																									${hitCandidateHitInfoData.item}
																									<c:if test="${not empty hitCandidateHitInfoData.subItems}">
																										<br>
																										<table>  
																											<c:forEach var="hitCandidateHitInfoDataSubData" varStatus="alsoACounter"
																													items="${hitCandidateHitInfoData.subItems}">
																												<tr> 
																													<td valign="top"  >
																														${alsoACounter.index+1}.		
																													</td> 
																													<td valign="top"  >
																														${hitCandidateHitInfoDataSubData.item}	
																													</td> 
																												</tr>   		
																											</c:forEach> 
																										</table> 
																									</c:if>  
																								</td> 
																							</tr>   
																						</c:forEach>  
																					</table>
																					
																				</td>
																			</tr>   
																		</c:if> 
																	</table>  
													</div> 
												</c:if> 
											</div> 
													 
											<div style="clear: both">
											</div>
											<div style="margin-top: 0px; height:1px;max-height:1px"> 
											</div>
													
											<div class="theOneRow">
												<div 
													<c:if test="${not inv_noHit}"> 
														class="theBlockLeft" 
													</c:if>
													<c:if test="${inv_noHit}"> 
														class="noHit_theBlock" 
													</c:if>
												> 
												    <table width="100%" height="80" border="0"
														cellpadding="2" class="data_table2">  
														<tr>
															<td>
																<div style="text-align:left; font-size: 15px; font-weight: bold;">Tệp đính kèm  (${invHits[rowCtr].mainCandidateAttachmentSize})</div>
																
																<c:if test="${invHits[rowCtr].mainCandidateAttachmentSize gt 0}"> 
																	<div class="theDocArea">
																		<c:forEach var="attachmentEntry"
																					items="${invHits[rowCtr].mainCandidateAttachments}"> 
																			<div class="oneDocArea">
																			
																				<c:if test="${empty attachmentEntry.link}"> 
																							<img src="<c:url value='/resources/images/document_icon_low_border.jpg'/>"
																								width="18" height="18" class="ximg-border" style="border:0;" /> &nbsp;<c:out value="${attachmentEntry.attachmentTypeDescription}" />
																					  
																				</c:if>   
																			
																				<c:if test="${not empty attachmentEntry.link}">   
																				    <a href="javascript:showImage('<c:out value="${attachmentEntry.link}" />',
																				    
																						<c:if test="${empty attachmentEntry.imageProperties}">
																							0
																						</c:if> 
																						<c:if test="${not empty attachmentEntry.imageProperties}">
																							<c:out value="${attachmentEntry.imageProperties.width}" />
																						</c:if> 
																				    	,
																						<c:if test="${empty attachmentEntry.imageProperties}">
																							0
																						</c:if> 
																						<c:if test="${not empty attachmentEntry.imageProperties}">
																							<c:out value="${attachmentEntry.imageProperties.height}" />
																						</c:if> 
																				    		)" >
																					    <span class="onHoverMousePointerThumb"> 
																							<img src="<c:url value='/resources/images/document_icon_low_border.jpg'/>"
																								width="18" height="18" class="ximg-border"  style="border:0;" /> &nbsp;<c:out value="${attachmentEntry.attachmentTypeDescription}" />
																					    </span>
																					</a> 
																				</c:if>  
																				
																			</div> 
																		</c:forEach>  
																	</div> 
																</c:if>
																
															</td>
														</tr>
													</table>  
												</div> 
												
												
												<c:if test="${not inv_noHit}">
													<div class="theBlockRightThird">
														&nbsp;
													</div>
													
													<div class="theBlockRight" > 
														&nbsp;
													</div>
												</c:if>
											</div> 
											
											
											
											
											
											
											
										</div>
										<div class="c"></div> 
			
									</div>
									
									 
																		
																		
																		
												
									<!-- textual compare -->			
									<div>						
									
											
											
																
											<!-- textual compare - start -->
											<!-- textual compare - start -->
											<!-- textual compare - start -->
											<!-- textual compare - start -->
											<!-- textual compare - start -->
														 
															
															
													               
													<!--- Dialog box on click of Details button -->
													<div id="dialog_textualCompare_<c:out value="${rowCtr}" />"  
														<c:if test="${not inv_noHit}"> 
															title="Chi tiết ứng viên chính và đối tượng trùng"
														</c:if>
														<c:if test="${inv_noHit}"> 
															title="Chi tiết ứng viên chính"
														</c:if> 
														align="center" style="display: none;">
														<div id=" ">
															<div style="border: thin;">
																<table width="100%" height="200" border="0">
																	<tr>
																		<td width="50%"  
																			<c:if test="${inv_noHit}"> 
																				colspan="2"
																			</c:if> 
																			height="35" align="center"
																			style="font-weight: bold" class="sno"><div
																			class="table_header" style="text-align:center;" >Ứng viên chính</div></td> 
																		<c:if test="${not inv_noHit}"> 
																			<td   width="50%"   height="35" align="center"
																				style="font-weight: bold" class="sno"><div
																				class="table_header" style="text-align:center;" >Trùng đối tượng</div></td> 
																		</c:if> 
																	</tr>
																	<tr>
																		<td colspan="2" class="img-border"><table width="100%" border="0"
																				cellpadding="2"> 
																				<tr>
																					<td  class="bold" width="20%" height="25">Mã ứng dụng</td>
																					<td  width="1%" >:</td>
																					<td width="28%" ><c:out value="${jobDetails.transactionId}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td  width="2%" >&nbsp;</td>
																						<td class="bold" width="20%" height="25">Mã ứng dụng</td>
																						<td width="1%" >:</td>
																						<td width="28%" 
																							<c:if test="${jobDetails.transactionId ne invHits[rowCtr].hitCandidateListTransId}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateListTransId}" /> </td>
																				</c:if> 
																				</tr> 
																				<tr>
																					<td class="bold" height="25">Ngày giao dịch</td>
																					<td>:</td>
																					<td><c:out value="${invHits[rowCtr].mainCandidateDateOfApplication}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="25">Ngày giao dịch</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidateDateOfApplication ne invHits[rowCtr].hitCandidateDateOfApplication}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateDateOfApplication}" /></td>
																					</c:if> 
																				</tr>
																				<tr>
																					<td class="bold" height="25">Loại ứng dụng</td>
																					<td>:</td>
																					<td><c:out value="${invHits[rowCtr].mainCandidateTransactionType}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="25">Loại ứng dụng</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidateTransactionType ne invHits[rowCtr].hitCandidateTransactionType}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateTransactionType}" /></td>
																					</c:if> 
																				</tr>
																				<tr>
																					<td class="bold" height="25">Trạng thái hồ sơ</td>
																					<td>:</td>
																					<td><c:out value="${invHits[rowCtr].mainCandidateApplicationPassportStatus}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="25">Trạng thái hồ sơ</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidateApplicationPassportStatus ne invHits[rowCtr].hitCandidateApplicationPassportStatus}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateApplicationPassportStatus}" /></td>
																					</c:if> 
																				</tr>
																				<tr>
																					<td class="bold" height="28">Họ tên</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateFuN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="28">Họ tên</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateFuN ne invHits[rowCtr].hitCandidateFuN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateFuN}" /></td> 
																					</c:if> 
																				</tr>
																				<!--<tr>
																					<td class="bold" height="28">Họ</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateFN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="28">Họ</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateFN ne invHits[rowCtr].hitCandidateFN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateFN}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td class="bold" height="28">Tên đệm</td>
																					<td>:</td>
																					<td><c:out
																							value="${invHits[rowCtr].mainCandidateMN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="28">Tên đệm</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidateMN ne invHits[rowCtr].hitCandidateMN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateMN}" /></td> 
																					</c:if> 
																				</tr>-->
																				<tr>
																					<td class="bold" height="28">Còn được biết là</td>
																					<td>:</td>
																					<td><c:out
																							value="${invHits[rowCtr].mainCandidateAlsoKnownAs}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="28">Còn được biết là</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidateAlsoKnownAs ne invHits[rowCtr].hitCandidateAlsoKnownAs}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateAlsoKnownAs}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td class="bold" height="28">Vị trí</td>
																					<td>:</td>
																					<td><c:out
																							value="${invHits[rowCtr].mainCandidatePosition}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="28">Vị trí</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidatePosition ne invHits[rowCtr].hitCandidatePosition}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidatePosition}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td class="bold" height="28">Giới hạn</td>
																					<td>:</td>
																					<td><c:out
																							value="${invHits[rowCtr].mainCandidateLimitation}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="28">Giới hạn</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidateLimitation ne invHits[rowCtr].hitCandidateLimitation}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateLimitation}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td class="bold" height="25">Quốc tịch</td>
																					<td>:</td>
																					<td><c:out
																							value="${invHits[rowCtr].mainCandidateNationality}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="25">Quốc tịch</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidateNationality ne invHits[rowCtr].hitCandidateNationality}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateNationality}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td class="bold" height="25">Nơi sinh</td>
																					<td>:</td>
																					<td><c:out
																							value="${invHits[rowCtr].mainCandidatePlaceOfBirth}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="25">Nơi sinh</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidatePlaceOfBirth ne invHits[rowCtr].hitCandidatePlaceOfBirth}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidatePlaceOfBirth}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td class="bold" height="25">Trung tâm phát hành</td>
																					<td>:</td>
																					<td>${invHits[rowCtr].mainCandidateIssuingAuthority}</td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="25">Trung tâm phát hành</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidateIssuingAuthority ne invHits[rowCtr].hitCandidateIssuingAuthority}">
																								class="notMatchedStyle" 
																							</c:if>
																							>${invHits[rowCtr].hitCandidateIssuingAuthority}</td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td class="bold" height="25">Giới tính</td>
																					<td>:</td>
																					<td ><c:out value="${invHits[rowCtr].mainCandidateGender}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="25">Giới tính</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateGender ne invHits[rowCtr].hitCandidateGender}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateGender}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td class="bold" height="25">Ngày sinh</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateDOB}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="25">Ngày sinh</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateDOB ne invHits[rowCtr].hitCandidateDOB}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateDOB}" /></td> 
																					</c:if> 
																				</tr>
																				<!-- phúc edit -->
																				<c:if test="${invHits[rowCtr].mainOverseasAddressCountry != ''}">
																					<tr>
																						<td class="bold" height="25">Quốc gia</td>
																						<td>:</td>
																						<td ><c:out
																								value="${invHits[rowCtr].mainOverseasAddressCountry}" /></td>
																						<c:if test="${not inv_noHit}">
																							<td   >&nbsp;</td>
																							<td class="bold" height="25">Quốc gia</td>
																							<td>:</td>
																							<td 
																								<c:if test="${invHits[rowCtr].mainOverseasAddressCountry ne invHits[rowCtr].hitOverseasAddressCountry}">
																									class="notMatchedStyle" 
																								</c:if>
																								><c:out value="${invHits[rowCtr].hitOverseasAddressCountry}" /></td> 
																						</c:if> 
																					</tr> 
																				</c:if>
																				<c:if test="${invHits[rowCtr].mainAddress5 != ''}">
																					<tr>
																						<td class="bold" height="25">Tỉnh/Thành phố</td>
																						<td>:</td>
																						<td ><c:out
																								value="${invHits[rowCtr].mainAddress5}" /></td>
																						<c:if test="${not inv_noHit}">
																							<td   >&nbsp;</td>
																							<td class="bold" height="25">Tỉnh/Thành phố</td>
																							<td>:</td>
																							<td 
																								<c:if test="${invHits[rowCtr].mainAddress5 ne invHits[rowCtr].hitAddress5}">
																									class="notMatchedStyle" 
																								</c:if>
																								><c:out value="${invHits[rowCtr].hitAddress5}" /></td> 
																						</c:if> 
																					</tr> 
																				</c:if>
																				<c:if test="${invHits[rowCtr].mainAddress4 != ''}">
																					<tr>
																						<td class="bold" height="25">Quận/Huyện</td>
																						<td>:</td>
																						<td ><c:out
																								value="${invHits[rowCtr].mainAddress4}" /></td>
																						<c:if test="${not inv_noHit}">
																							<td   >&nbsp;</td>
																							<td class="bold" height="25">Quận/Huyện</td>
																							<td>:</td>
																							<td 
																								<c:if test="${invHits[rowCtr].mainAddress4 ne invHits[rowCtr].hitAddress4}">
																									class="notMatchedStyle" 
																								</c:if>
																								><c:out value="${invHits[rowCtr].hitAddress4}" /></td> 
																						</c:if> 
																					</tr> 
																				</c:if>
																				<tr>
																					<td class="bold" height="25">Địa chỉ</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandiadteStreetName}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="25">Địa chỉ</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandiadteStreetName ne invHits[rowCtr].hitCandidateStreetName}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateStreetName}" /></td> 
																					</c:if> 
																				</tr>
																			<!--	<tr>
																					<td class="bold" height="25">Quốc gia</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateFlatNo}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="25">Quốc gia</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateFlatNo ne invHits[rowCtr].hitCandidateFlatNo}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateFlatNo}" /></td> 
																					</c:if> 
																				</tr> -->
																				<tr>
																					<td class="bold" height="25">Tình trạng hôn nhân</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateMaritalStatus}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="25">Tình trạng hôn nhân</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateMaritalStatus ne invHits[rowCtr].hitCandidateMaritalStatus}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateMaritalStatus}" /></td> 
																					</c:if> 
																				</tr>
																				<!--<tr>
																					<td class="bold" height="25">Tên của bố</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateFathersN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="25">Tên của bố</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateFathersN ne invHits[rowCtr].hitCandidateFathersN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateFathersN}" /></td>
																					</c:if>  
																				</tr>
																				<tr>
																					<td class="bold" height="25">Họ và tên bố</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateFathersSN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="25">Họ và tên bố</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateFathersSN ne invHits[rowCtr].hitCandidateFathersSN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateFathersSN}" /></td>
																					</c:if> 
																				</tr>
																				<tr>
																					<td class="bold" height="25">Tên đệm của bố</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateFathersMN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td> 
																						<td class="bold" height="25">Tên đệm của bố</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateFathersMN ne invHits[rowCtr].hitCandidateFathersMN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateFathersMN}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td class="bold" height="25">Tên của mẹ</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateMothersN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="25">Tên của mẹ</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateMothersN ne invHits[rowCtr].hitCandidateMothersN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateMothersN}" /></td>
																					</c:if> 
																				</tr>
																				<tr>
																					<td class="bold" height="25">Họ và tên mẹ</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateMothersSN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td> 
																						<td class="bold" height="25">Họ và tên mẹ</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateMothersSN ne invHits[rowCtr].hitCandidateMothersSN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateMothersSN}" /></td>
																					</c:if> 
																				</tr>
																				<tr>
																					<td class="bold" height="25">Tên đệm của mẹ</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateMothersMN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td> 
																						<td class="bold" height="25">Tên đệm của mẹ</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateMothersMN ne invHits[rowCtr].hitCandidateMothersMN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateMothersMN}" /></td>
																					</c:if> 
																				</tr>
																				 
																				 
																				<tr>
																					<td class="bold" height="25">Họ của vợ/chồng</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateSpouseFN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td> 
																						<td class="bold" height="25">Họ của vợ/chồng</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidateSpouseFN ne invHits[rowCtr].hitCandidateSpouseFN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateSpouseFN}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td class="bold" height="25">Họ tên của vợ/chồng</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateSpouseSN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="25">Họ tên của vợ/chồng</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidateSpouseSN ne invHits[rowCtr].hitCandidateSpouseSN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateSpouseSN}" /></td> 
																					</c:if> 
																				</tr> 
																				<tr>
																					<td class="bold" height="25">Tên đệm của vợ/chồng</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateSpouseMN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="25">Tên đệm của vợ/chồng</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidateSpouseMN ne invHits[rowCtr].hitCandidateSpouseMN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateSpouseMN}" /></td> 
																					</c:if> 
																				</tr>--> 
																				<tr>
																					<td class="bold" height="25">Số hộ chiếu cũ</td>
																					<td>:</td>
																					<td><c:out value="${invHits[rowCtr].mainCandidatePreviousPassportNumber}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="25">Số hộ chiếu</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidatePreviousPassportNumber ne invHits[rowCtr].hitCandidateDocumentPassportNumber}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateDocumentPassportNumber}" /></td> 
																					</c:if> 
																				</tr>   
																				<tr>
																					<td class="bold" height="25">Ngày phát hành hộ chiếu cũ</td>
																					<td>:</td>
																					<td><c:out value="${invHits[rowCtr].mainCandidatePreviousPassportIssueDate}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td class="bold" height="25">Ngày phát hành</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidatePreviousPassportIssueDate ne invHits[rowCtr].hitCandidateDocumentPassportIssuedDate}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateDocumentPassportIssuedDate}" /></td> 
																					</c:if> 
																				</tr>  
																				<c:if test="${not inv_noHit}"> 
																					<tr>
																						<td class="bold" height="25">&nbsp;</td>
																						<td>&nbsp;</td>
																						<td>&nbsp;</td> 
																						<td   >&nbsp;</td>
																						<td  class="bold" height="25">Ngày hết hạn</td>
																						<td>:</td>
																						<td><c:out value="${invHits[rowCtr].hitCandidateDocumentPassportExpirationDate}" /></td>
																					</tr>   
																					<tr>
																						<td class="bold" height="25">&nbsp;</td>
																						<td>&nbsp;</td>
																						<td>&nbsp;</td>
																						<td   >&nbsp;</td>
																						<td class="bold" height="25">Tình trạng hộ chiếu</td>
																						<td>:</td>
																						<td><c:out value="${invHits[rowCtr].hitCandidateDocumentPassportStatus}" /></td> 
																					</tr>  
																					<tr>
																						<td class="bold" height="25">&nbsp;</td>
																						<td>&nbsp;</td>
																						<td>&nbsp;</td>
																						<td   >&nbsp;</td>
																						<td class="bold" height="25">Loại hộ chiếu</td>
																						<td>:</td>
																						<td><c:out value="${invHits[rowCtr].hitCandidateDocumentPassportType}" /></td> 
																					</tr> 
																				</c:if> 
													
																			</table></td>
																	</tr>
																</table>
															</div> 
															<div class="c"></div>
													
														</div>
													</div>
													
													
													<script>
													  $(function() {
													    $( "#dialog_textualCompare_<c:out value="${rowCtr}" />" ).dialog({
														modal: true,
													      autoOpen: false,
														  width : 900,
														  height : 860,
														 /*  <c:if test="${not inv_noHit}"> height : 860, </c:if>
														  <c:if test="${inv_noHit}">  height : 760,  </c:if>  */
														  resizable: false,
													      show: {
													        effect: "fade",
													        duration: 1000
													      },
													      hide: {
													       // effect: "explode",
													        duration: 1000
													      }
													    });
													 
													    $( "#details_btn_textualCompare_<c:out value="${rowCtr}" />" ).click(function() {
													      $( "#dialog_textualCompare_<c:out value="${rowCtr}" />" ).dialog( "open" );
													    });
													  });
													
													  </script>
													
													 
									
											
											
											<!-- textual compare - end -->
											<!-- textual compare - end -->
											<!-- textual compare - end -->
											<!-- textual compare - end -->
											<!-- textual compare - end -->



									</div>
 
																		
																		
																		
												
									<!-- photo compare -->			
									<div>						
									
											
											
																
											<!-- photo compare - start -->
											<!-- photo compare - start -->
											<!-- photo compare - start -->
											<!-- photo compare - start -->
											<!-- photo compare - start -->
														 
									
									
												<!-- Jquery Dialog box div for image popup ( Picture )---->
												<div id="dialog-image_photoCompare_<c:out value="${rowCtr}" />"  
													<c:if test="${not inv_noHit}"> 
														title="Facial Comparison" 
													</c:if>
													<c:if test="${inv_noHit}"> 
														title="Face" 
													</c:if> 
													style="display: none;">
													<div class="centerCaption">
														<table>
															<tr>
																<!-- photo dimension: 480 (width) x 640 (height) -->
																<c:if test="${invHits[rowCtr].mainCandidatePhoto != null}">
																	<td><img
																		src="data:image/jpg;base64,${invHits[rowCtr].mainCandidatePhoto}"
																		class="img-border " height="640" width="480" title="Main Candidate" />
																		<div style="font-weight: bold; color: #000;text-align:center;">Ứng viên chính</div></td>
																</c:if>
																<c:if test="${invHits[rowCtr].mainCandidatePhoto == null}">
																	<td><img src=<c:url value='/resources/images/No_Image.jpg'/>
																		class="img-border" height="640" width="480" title="Main Candidate" />
																		<div style="font-weight: bold; color: #000;text-align:center;">Không có ảnh</div></td>
																</c:if>
																<c:if test="${not inv_noHit}">
																	<c:if test="${invHits[rowCtr].hitCandidatePhoto != null}">
																		<td><img
																			src="data:image/jpg;base64,${invHits[rowCtr].hitCandidatePhoto}"
																			class="img-border doGetAJpgSafeVersion" height="640" width="480" title="Hit Candidate" />
																			<div style="font-weight: bold; color: #000;text-align:center;">Trùng đối tượng</div>
																		</td>
																	</c:if>
																	<c:if test="${invHits[rowCtr].hitCandidatePhoto == null}">
																		<td><img src=<c:url value='/resources/images/No_Image.jpg'/>
																			class="img-border" height="640" width="480" title="Hit Candidate" />
																			<div style="font-weight: bold; color: #000;text-align:center;">Không có ảnh</div></td>
																	</c:if>
																</c:if>
															</tr>
														</table>
													</div>
												</div>
												
												
												<!--- Dialog box script for the image pop up ( Picture ) --->
												<script>
												  $(function() {
												    $( "#dialog-image_photoCompare_<c:out value="${rowCtr}" />" ).dialog({
													modal: true,
												      autoOpen: false,
													  width : 1040,
													  height : 730,
													  resizable: false,
												      show: {
												        effect: "fade",
												        duration: 1000
												      },
												      hide: {
												        //effect: "explode",
												        //duration: 1000
												      }
												    });
												 
												    $( "#image-popup_photoCompare_<c:out value="${rowCtr}" />" ).click(function(event) {
												      event.preventDefault();
												      $( "#dialog-image_photoCompare_<c:out value="${rowCtr}" />" ).dialog( "open" );
												    });
												  });
												</script>
											
											<!-- photo compare - end -->
											<!-- photo compare - end -->
											<!-- photo compare - end -->
											<!-- photo compare - end -->
											<!-- photo compare - end -->



									</div>
														
																		
												
									<!-- signature compare -->			
									<div>						
									
											
											
																
											<!-- signature compare - start -->
											<!-- signature compare - start -->
											<!-- signature compare - start -->
											<!-- signature compare - start -->
											<!-- signature compare - start -->
														 
									
									

												<!-- Jquery Dialog box div for image popup ( Signature ) ---->
												<div id="dialog-image_signatureCompare_<c:out value="${rowCtr}" />"  
													<c:if test="${not inv_noHit}"> 
														title="Signature Comparison"
													</c:if>
													<c:if test="${inv_noHit}"> 
														title="Signature"
													</c:if> 
													style="display: none;">
													<div class="centerCaption">
														<table>
															<tr>
																<!-- signature dimension: 453 (width) x 118 (height), thumb print dimension: 512 x 512 -->
																<c:if test="${invHits[rowCtr].mainCandidateSignature != null}">
																	<td style="text-align:center; width: 50%" ><img
																		src="data:image/jpg;base64,${invHits[rowCtr].mainCandidateSignature}"
																		class="img-border " height="180" title="Main Candidate" /><br> <span
																		style="font-weight: bold; color: #000;">Ứng viên chính</span></td>
																</c:if>
																<c:if test="${invHits[rowCtr].mainCandidateSignature == null}">
																	<td style="text-align:center; width: 50%" ><img src="<c:url value='/resources/images/No_Image.jpg'/>"
																		class="img-border" height="180" title="Main Candidate" /><br>
																		<span style="font-weight: bold; color: #000;">Không có ảnh</span></td>
																</c:if>
																<c:if test="${not inv_noHit}"> 
																	<c:if test="${invHits[rowCtr].hitCandidateSignature != null}">
																		<td style="text-align:center; width: 50%" ><img
																			src="data:image/jpg;base64,${invHits[rowCtr].hitCandidateSignature}"
																			class="img-border doGetAJpgSafeVersion" height="180" title="Hit Candidate" /><br> <span
																			style="font-weight: bold; color: #000;">Trùng đối tượng</span></td>
																	</c:if>
																	<c:if test="${invHits[rowCtr].hitCandidateSignature == null}">
																		<td style="text-align:center; width: 50%" ><img src="<c:url value='/resources/images/No_Image.jpg'/>"
																			class="img-border" height="180" title="Hit Candidate" /><br>
																			<span style="font-weight: bold; color: #000;">Không có ảnh</span></td>
																	</c:if>
																</c:if>
															</tr>
														</table>
													</div>
												</div>
							
												<!--- Dialog box script for the image pop up ( Signature ) --->
												<script>
												  $(function() {
												    $( "#dialog-image_signatureCompare_<c:out value="${rowCtr}" />" ).dialog({
													modal: true,
												      autoOpen: false,
													  width : 950,
													  height : 280,
													  resizable: false,
												      show: {
												        effect: "fade",
												        duration: 1000
												      },
												      hide: {
												        //effect: "explode",
												        //duration: 1000
												      }
												    });
												 
												    $( "#image-popup_signatureCompare_<c:out value="${rowCtr}" />" ).click(function() {
												      $( "#dialog-image_signatureCompare_<c:out value="${rowCtr}" />" ).dialog( "open" );
												    });
												  });
												</script>
									
											
											<!-- signature compare - end -->
											<!-- signature compare - end -->
											<!-- signature compare - end -->
											<!-- signature compare - end -->
											<!-- signature compare - end -->



									</div>
									
									
									
			
				                <%	
				                	}
				                %>

			                <%	
			                	}
			                %>

					<!-- ================================================= all hits - end ================================================= -->
					<!-- ================================================= all hits - end ================================================= -->
					<!-- ================================================= all hits - end ================================================= -->
					<!-- ================================================= all hits - end ================================================= -->
					<!-- ================================================= all hits - end ================================================= -->
					<!-- ================================================= all hits - end ================================================= -->
					<!-- ================================================= all hits - end ================================================= -->
					<!-- ================================================= all hits - end ================================================= -->
					<!-- ================================================= all hits - end ================================================= -->
					<!-- ================================================= all hits - end ================================================= -->
						
						
						 
			
					<c:if test="${not inv_noHit}"> 
			
						<br />
	
						<table style="width: 100%;">
							<tr>
								<td style="width: 10%; text-align: left; border: none;"></td>
	
	
								<td style="width: 10%; text-align: right; border: none;"><span
									style="width: 50%; border: none;" class="text">&nbsp;Tỉ lệ trùng cao&nbsp;<img
										src="<c:out value="${pageContext.request.contextPath}"/>/resources/images/Green.jpg"
										alt="Green icon" height="12" />
								</span>&nbsp;&nbsp;&nbsp; <span style="width: 50%; border: none;"
									class="text">Không có điểm trùng&nbsp;<img
										src="<c:out value="${pageContext.request.contextPath}"/>/resources/images/Red.jpg"
										alt="Red icon" height="12" />
								</span>&nbsp;&nbsp;&nbsp; <span style="width: 50%; border: none;"
									class="text">Possible Match&nbsp;<img
										src="<c:out value="${pageContext.request.contextPath}"/>/resources/images/Blue.jpg"
										alt="Blue icon" height="12" />&nbsp;
								</span></td>
							</tr>
	
						</table>
						<br /> 
					</c:if>
		</div>
		
		<!-- 05/02/2018: TRUNG THÊM THÔNG TIN CƠ BẢN -->
		<c:if test="${inv_none}"> 
		<div class="noHit_theBlock onHoverMousePointerThumb"> 
						
    	<div class="data_table2">
	        <div class="form-group text">
	            <label class="control-label">Mã giao dịch:</label>
	            <label class="control-label"><c:out value="${nicData.transactionId}" /></label>
	        </div>
	        <div class="form-group text">
	            <label class="control-label">CMND/CCCD:</label>
	            <label class="control-label"><c:out value="${nicData.nin}" /></label>
	        </div>
	        <div class="form-group text">
	            <label class="control-label">Họ:</label>
	            <label class="control-label"><c:out value="${nicData.nicRegistrationData.surnameFull}" /></label>
	        </div>
	        <div class="form-group text">
	            <label class="control-label">Tên:</label>
	            <label class="control-label"><c:out value="${nicData.nicRegistrationData.firstnameFull}" /></label>
	        </div>
	        <div class="form-group text">
	            <label class="control-label">Tên đệm:</label>
	            <label class="control-label"><c:out value="${nicData.nicRegistrationData.middlenameFull}" /></label>
	        </div>
	        <div class="form-group text">
	            <label class="control-label">Cơ quan cấp phát:</label>
	            <label class="control-label"><c:out value="${nicData.issuingAuthority}" /></label>
	        </div>
	        <div class="form-group text">
	            <label class="control-label">Loại hộ chiếu:</label>
	            <label class="control-label">
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
	            </label>
	        </div>
	        <div class="form-group text">
	            <label class="control-label">Ngày tạo:</label>
	            <label class="control-label"><c:out value="${nicData.nicRegistrationData.createDatetime}" /></label>
	        </div>
    </div>
    <div id="dialog-image_photoCompare" style="display: block;">
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
												</div>   
												</c:if>	
												<div id="dialog-approve"></div>

	<div class="modal">
		<!-- Place at bottom of page -->
	</div>
		<!-- END -->
	</div>
	</div>
	</div>
	</div>


<!--content end -->
</div>

<div class="tab-content" id="signerImage" style="display: none">
	<div class="data_table2" style="margin-right:0px; width: 50%">
	    <div class="form-group text">
	     		<h2 style="color:black">QUYẾT ĐỊNH ĐÍNH KÈM</h2>
	    </div>
        <!-- <div class="form-group text">
            <label class="control-label">Mã giao dịch:</label>
            <label class="control-label" id="transactionTxt"><c:out value="${nicData.transactionId}" /></label>
        </div> -->
        <div class="form-group text">
            <label class="control-label">Số quyết định:</label>
            <label class="control-label"><c:out value="${numCompare}" /></label>
        </div>
        <div class="form-group text">
            <label class="control-label">Cơ quan đại điện:</label>
            <label class="control-label"><c:out value="${govCompare}" /></label>
        </div>
        <div class="form-group text">
            <label class="control-label">Người ký quyết định:</label>
            <label class="control-label"><c:out value="${signerCompare}" /></label>
        </div>
        <div id="dialog-image_photoCompare" style="display: block;padding-left: 25%;    width: auto;">
		   <div class="centerCaption">
            <div style="text-align:center">
                <!-- photo dimension: 480 (width) x 640 (height) -->
                <c:choose>
                    <c:when test="${not empty photoStrSigner}">
                        <div>
                            <img src="data:image/jpg;base64,${photoStrSigner}"
                                 class="img-border" height="320" width="440" />
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
    </div>
    					
    <div class="data_table2" style="margin-right:0px; width: 50%">
	    <div class="form-group text">
	     		<h2 style="color:black">TÀI LIỆU SO SÁNH</h2>
	    </div>
        <div class="form-group text">
            <label class="control-label">Cơ quan đại điện:</label>
            <label class="control-label"><c:out value="${govCompareDb}" /></label>
        </div>
        <div class="form-group text">
            <label class="control-label">Người ký quyết định:</label>
            <label class="control-label"><c:out value="${signerCompareDb}" /></label>
        </div>
         <div class="form-group text">
            <label class="control-label">Tình trạng hoạt động:</label>
            <label class="control-label"><c:out value="${statusSign}" /></label>
        </div>
        <div id="dialog-image_photoCompare" style="display: block;padding-left: 25%;    width: auto;">
		   <div class="centerCaption">
            <div style="text-align:center">
                <!--  photo dimension: 480 (width) x 640 (height)-->
                <c:choose>
                    <c:when test="${not empty photoCompare}">
                        <div>
                            <img src="data:image/jpg;base64,${photoCompare}"
                                 class="img-border " height="320" width="440" />
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
    </div>

</div>

<div class="tab-content" id="documentAttch" style="display: none">

	<c:if test="${empty proofDocList}">
		<span style="color: #FF0000; font-size: 12px;">Không có tài liệu.</span>
	</c:if>
	<c:if test="${not empty proofDocList}">
		<%--<div class="displayTag" style="background-image:url('<%=request.getContextPath() %>/resource/images/head.png');background-repeat: repeat-x;">
				<display:table id="proofDocList" export="false" class="displayTag"
					name="${proofDocList.rows}" defaultsort="1" sort='external'
					pagesize="${pageSize}" size="${proofDocList.total}" partialList="true"
					requestURI="/servlet/nicEnquiry/getTransDocuments/${txnId}">
					<display:column property="documentName" sortable="true" title="Document Name"
						sortName="documentName" />
					<display:column property="document" 
						title="Document" />
				</display:table>
			</div> --%>
						<div>
							<table id="box1_table" width="50%" border="0" cellpadding="0"
								cellspacing="0" bordercolor="#333333"
								style="border: 0px; padding: 5px;">
								<tr>
									<td class="sno" style="font-weight: bold;" height="30px"><span
										class="table_header" style="padding-left: 40px;">Document Name</span></td>
									<td class="sno" style="font-weight: bold; padding-left: 15px;"><span
										class="table_header" style="padding-left: 5px;">Document</span>
									</td>
								</tr>
							</table>
						</div>
						<!--  -->
						<div id="mycustomscroll" class='flexcroll'>
							<table  id="box1_table" width="50%" border="0" cellpadding="0"
								cellspacing="0" bordercolor="#333333" class="data_table">
								<c:forEach var="c" items="${proofDocList}" varStatus="status">
																			
								  <c:if test="${empty c.dmsLink}">  
									
									<c:choose>
										<c:when test="${c.type eq 'JPEG'}">
											<tr>
												<td class="nricFormat"><c:out
														value="${c.purpose}" /></td>
												<td class="nricFormat">
													<div class="scanDocViewOnly" data-view="${status.index}"
														data-name="${c.documentName}" data-type="${c.type}" >
														<img
															src=<c:url value='/resources/images/document_icon.jpg'/>
															width="40px" height="40px" title="${c.purpose}" 
															onclick="showProofDoc('${c.transactionNo}', '${status.index}',	'${c.documentName}','${c.type}','${c.purpose}');"/>
													</div>
												</td>
											</tr>
										</c:when>
										<c:otherwise>
											<tr>
												<td class="nricFormat"><c:out value="${c.purpose}" />
												</td>
												<td class="nricFormat">
													<div class="scanDocViewOnly" data-view="${status.index}"
														data-name="${c.documentName}" data-type="${c.type}" >
														<img
															src=<c:url value='/resources/images/pdf_icon.jpg'/>
															width="40px" height="40px" title="${c.purpose}" onclick="showProofDoc('${c.transactionNo}', '${status.index}',	'${c.documentName}','${c.type}','${c.purpose}');"/>
													</div>
												</td>
											</tr>
										</c:otherwise>
									</c:choose>
								  </c:if>  
								 								
								  <c:if test="${not empty c.dmsLink}">  
											<tr>
												<td class="nricFormat"><c:out
														value="${c.purpose}" /></td>
												<td class="nricFormat">
													<div class="scanDocViewOnly" data-view="${status.index}"
														data-name="${c.documentName}" data-type="${c.type}" >
														<img
															src=<c:url value='/resources/images/document_icon.jpg'/>
															width="40px" height="40px" title="${c.purpose}"  
															onclick="showImage('<c:out value="${c.dmsLink}" />')" />
													</div>
												</td>
											</tr>
								  </c:if>  
								  
								</c:forEach>
							</table>
						</div>
					</c:if>
	<br />
	
	  <!-- <table style="width:100%;">
    	<tr>
        	<td align="center" style="padding: 20px; text-align: right;" >
		      <input type="button" class="button_grey" id="docs_cls_btn"  value="Close"/>
	        </td>
	    </tr>
      </table> -->
      
      <div id="dialog-scan-doc" title="Scanned Document"
	style="display: none;">	
	<table>
		<tr>
			<c:forEach var="docItem" items="${proofDocList}" varStatus="status">
				<c:choose>
					<c:when
						test="${docItem.document == null || docItem.document == ''}">
						<div style="display: none;" id="${docItem.documentName}Div">
							<img width="595px" height="842px" style="align: left;"
								src=<c:url value='/resources/images/No_Image.jpg'/>
								id="documentView${status.index}" alt="No Image" />
						</div>
					</c:when>
					<c:otherwise>
						<div style="display: none;" id="${docItem.documentName}Div">
							<c:forEach var="docListItem" items="${docItem.document}"
								varStatus="listItemStatus"> 
								<img style="display: none;" title="${docItem.purpose}"
									src="data:image/jpg;base64,${docListItem}" style="align: center" 
									id="${docItem.documentName}Doc" alt="Proof Document"/>
								
							</c:forEach>
						</div>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</tr>
	</table>
	
	 <!-- JQuery Zoom in and out -->
	 <div class="wrapper">
            <div id="viewer" class="viewer iviewer_cursor"></div>
        </div>
        
</div>
	
</div>

<div class="tab-content" id="imageFP" style="display: none">
	<table style="width: 100%; text-align: center;">
 	<tr>
 		<td>
 			<table width="100%" border="0" class="t_header">
					<tr>
						<td style=" text-align: center; border: none;"
							class="text"><b>Ngón cái phải</b></td>
						<td style= "text-align: center; border: none;"
							class="text"><b>Ngón trỏ phải</b></td>
						<td style=" text-align: center; border: none;"
							class="text"><b>Ngón giữa phải</b></td>
						<td style="text-align: center; border: none;"
							class="text"><b>Ngón áp út phải</b></td>
						<td style="text-align: center; border: none;"
							class="text"><b>Ngón út phải</b></td>
					</tr>
					
					<tr>
							<%int i=1; %>
						<c:forEach var="fpInfo" items="${fpMap}" begin="0" end="4">
							<td align="center">
							<c:set var="fpId">fp<%=i %></c:set>
							<c:set var="fpInd">fpInd<%=i %></c:set>
								<table>
									<tr>	
										<td colspan="3" align="center">
											<c:choose>
												<c:when test="${viewFPFalg eq 'Y' }">
													<c:choose>
														<c:when test="${fpInfo.value.baseImageConvert != 'N'}">
															<img src="data:image/jpg;base64,${fpInfo.value.baseImageConvert}" id="<%=i %>" height="110px"  alt="Fingerprint Image"
															 title="Quality : ${fpInfo.value.fpQuaScr} <%--   Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />
															
															<input type="hidden" id="${fpId}" value="${fpInfo.value.fpImage}" />
															<input type="hidden" id="${fpInd}" value="${fpInfo.value.fpIndicator}" />
														</c:when>
														<c:otherwise>
															<img id="FP<%=i %>" data-index="<%=i %>" src="<c:url value="/resources/images/No_Image_Small.jpg"/>">
														</c:otherwise>
													</c:choose>
									
								
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${not empty fpInfo.value.fpImage}">
															<img id="FP1" data-index="1"
														src="<c:url value="/resources/images/fpTick.jpg"/>"
														width="80px" height="80px" 
														style="border: 1px SOLID; " alt="RIGHT THUMB"
														title="Quality : ${fpInfo.value.fpQuaScr}   <%-- Verify Score : ${fpInfo.value.fpVerifyScr} --%>"
														align="middle" />
														</c:when>
														<c:otherwise>
																<c:choose>
																	<c:when
																		test="${fpInfo.value.fpIndicator == 'A'}">
																		<img id="FP5" data-index="5"
																			src="<c:url value="/resources/images/AS.jpg"/>" width="80px"
																			height="80px" style="border: 1px SOLID;"
																			alt="RIGHT LITTLE" align="middle" title="Quality : ${fpInfo.value.fpQuaScr} <%--   Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />
																	</c:when>
																	<c:when
																		test="${fpInfo.value.fpIndicator == 'U'}">
																		<img id="FP5" data-index="5"
																			src="<c:url value="/resources/images/Unable.jpg"/>" width="80px"
																			height="80px" style="border: 1px SOLID; "
																			alt="RIGHT LITTLE" align="middle" />
																	</c:when>
																	<c:otherwise>
																		<img id="FP5" data-index="5"
																			src="<c:url value="/resources/images/NS.jpg"/>" width="80px"
																			height="80px" style="border: 1px SOLID;"
																			alt="RIGHT LITTLE" align="middle" />
																	</c:otherwise>
																</c:choose>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
											
										</td>
									</tr>
									<tr>	
										<td style="text-align: center;">
										<c:if test="${not empty fpInfo.value.fpImage}">
											<c:choose>
												<c:when test="${fpInfo.value.fpQuaScr ge fpInfo.value.goodFPQuaScr  or fpInfo.value.fpQuaScr eq fpInfo.value.goodFPQuaScr}">
													<div id="quality10" class="" title="${fpInfo.value.fpQuaScr}"
														style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
														<p class=""
															style="text-align: center; valign: top; margin: 0; padding: 5px 6px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
													</div>
												</c:when>
												<c:when
													test="${fpInfo.value.fpQuaScr ge fpInfo.value.accetableFPQuaScr and fpInfo.value.fpQuaScr lt fpInfo.value.goodFPQuaScr}">
													<div id="quality10" class="" title="${fpInfo.value.fpQuaScr}"
														style="background-image: url('<c:url value="/resources/images/orange_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
														<p class=""
															style="text-align: center; valign: top; margin: 0; padding: 5px 6px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
													</div>
												</c:when>
												<c:otherwise>
													<div id="quality10" class="" title="${fpInfo.value.fpQuaScr}"
														style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
														<p class=""
															style="text-align: center; valign: top; margin: 0; padding: 5px 6px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
													</div>
												</c:otherwise>
											</c:choose> 
											<%-- <c:choose>
												<c:when test="${fpInfo.value.fpVerifyScr >= fpVerifyMatchScore}">
													<div id="verify3" class="" title="${fpInfo.value.fpVerifyScr}"
														style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
														<p class=""
															style="text-align: center; valign: top; margin: 0; padding: 5px 6px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
													</div>
												</c:when>
												<c:otherwise>
													<div id="verify3" class="" title="${fpInfo.value.fpVerifyScr}"
														style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
														<p class=""
															style="text-align: center; valign: top; margin: 0; padding: 5px 6px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
													</div>
												</c:otherwise>
											</c:choose> --%>
											<c:choose>
												<c:when test="${fpInfo.value.encodeFlag}">
													<p style="padding: 0px 5px 0px 0px;">
														<img src="<c:url value="/resources/images/contactless_selected.jpg "/>"
															class='radioButtonImage' style='width: 30px; height: 23px' />
													</p>
												</c:when>
												<c:otherwise>
													<p style="padding: 0px 5px 0px 0px;">
														&nbsp;
													</p>
												</c:otherwise>
											</c:choose>
											</c:if>
										</td>
									</tr>
								</table>
								
								
							</td>
							<%i=i+1; %>	
						</c:forEach>
					</tr>
				</table>
 		</td>
 	</tr>
 	<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
						<tr>
						<td>&nbsp;</td>
					</tr>
 	<tr>
 		<td>
 			<table width="100%" border="0" class="t_header">
					<tr>
						<td style="width: 10%; text-align: center; border: none;"
							class="text"><b>Ngón cái trái</b></td>
						<td style="width: 10%; text-align: center; border: none;"
							class="text"><b>Ngón trỏ trái</b></td>
						<td style="width: 10%; text-align: center; border: none;"
							class="text"><b>Ngón giữa trái</b></td>
						<td style="width: 10%; text-align: center; border: none;"
							class="text"><b>Ngón áp út trái</b></td>
						<td style="width: 10%; text-align: center; border: none;"
							class="text"><b>Ngón út trái</b></td>
					</tr>
					<tr >
						<c:forEach var="fpInfo" items="${fpMap}" begin="5" end="9">
							<td align="center">
							<c:set var="fpId">fp<%=i %></c:set>
							<c:set var="fpInd">fpInd<%=i %></c:set>
							<table>
									<tr>	
										<td colspan="3" style="text-align: center;">
											<c:choose>
												<c:when test="${viewFPFalg eq 'Y' }">
													<c:choose>
														<c:when test="${fpInfo.value.baseImageConvert != 'N'}">
															<img src="data:image/jpg;base64,${fpInfo.value.baseImageConvert}" id="<%=i %>" height="110px"  alt="Fingerprint Image" title="Quality : ${fpInfo.value.fpQuaScr}  <%--  Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />
															
															<input type="hidden" id="${fpId}" value="${fpInfo.value.fpImage}" />
															<input type="hidden" id="${fpInd}" value="${fpInfo.value.fpIndicator}" />
														</c:when>
														<c:otherwise>
															<img id="FP<%=i %>" data-index="<%=i %>"  src="<c:url value="/resources/images/No_Image_Small.jpg"/>">
														</c:otherwise>
													</c:choose>
								
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${not empty fpInfo.value.fpImage}">
															<img id="FP1" data-index="1" src="<c:url value="/resources/images/fpTick.jpg"/>"
														width="80px" height="80px" 
														style="border: 1px SOLID;" alt="RIGHT THUMB"
														title="Quality : ${fpInfo.value.fpQuaScr} <%--   Verify Score : ${fpInfo.value.fpVerifyScr} --%>"
														align="middle" />
														</c:when>
														<c:otherwise>
																<c:choose>
																	<c:when
																		test="${fpInfo.value.fpIndicator == 'A'}">
																		<img id="FP5" data-index="5"
																			src="<c:url value="/resources/images/AS.jpg"/>" width="80px"
																			height="80px" style="border: 1px SOLID;"
																			alt="RIGHT LITTLE" align="middle"/>
																	</c:when>
																	<c:when
																		test="${fpInfo.value.fpIndicator == 'U'}">
																		<img id="FP5" data-index="5"
																			src="<c:url value="/resources/images/Unable.jpg"/>" width="80px"
																			height="80px" style="border: 1px SOLID;"
																			alt="RIGHT LITTLE" align="middle"/>
																	</c:when>
																	<c:otherwise>
																		<img id="FP5" data-index="5"
																			src="<c:url value="/resources/images/NS.jpg"/>" width="80px"
																			height="80px" style="border: 1px SOLID;"
																			alt="RIGHT LITTLE" align="middle"/>
																	</c:otherwise>
																</c:choose>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
											
										</td>
									</tr>
									<tr>	
										<td style="text-align:center;">
										<c:if test="${not empty fpInfo.value.fpImage}">
											<c:choose>
												<c:when test="${fpInfo.value.fpQuaScr ge fpInfo.value.goodFPQuaScr  or fpInfo.value.fpQuaScr eq fpInfo.value.goodFPQuaScr}">
													<div id="quality10" class="" title="${fpInfo.value.fpQuaScr}"
														style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
														<p class=""
															style="text-align: center; valign: top; margin: 0; padding: 5px 6px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
													</div>
												</c:when>
												<c:when
													test="${fpInfo.value.fpQuaScr ge fpInfo.value.accetableFPQuaScr and fpInfo.value.fpQuaScr lt fpInfo.value.goodFPQuaScr}">
													<div id="quality10" class="" title="${fpInfo.value.fpQuaScr}"
														style="background-image: url('<c:url value="/resources/images/orange_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
														<p class=""
															style="text-align: center; valign: top; margin: 0; padding: 5px 6px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
													</div>
												</c:when>
												<c:otherwise>
													<div id="quality10" class="" title="${fpInfo.value.fpQuaScr}"
														style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
														<p class=""
															style="text-align: center; valign: top; margin: 0; padding: 5px 6px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
													</div>
												</c:otherwise>
											</c:choose> 
											<%-- <c:choose>
												<c:when test="${fpInfo.value.fpVerifyScr >= fpVerifyMatchScore}">
													<div id="verify3" class="" title="${fpInfo.value.fpVerifyScr}"
														style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
														<p class=""
															style="text-align: center; valign: top; margin: 0; padding: 5px 6px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
													</div>
												</c:when>
												<c:otherwise>
													<div id="verify3" class="" title="${fpInfo.value.fpVerifyScr}"
														style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
														<p class=""
															style="text-align: center; valign: top; margin: 0; padding: 5px 6px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
													</div>
												</c:otherwise>
											</c:choose> --%>
											<c:if test="${fpInfo.value.encodeFlag}"> 
				                               <p style="padding: 0px 5px 0px 0px;">
													<img src="<c:url value="/resources/images/contactless_selected.jpg "/>"
														class='radioButtonImage' style='width: 30px; height: 23px' />
												</p>
											</c:if>
											</c:if>
										</td>
									</tr>
								</table>
								
							</td>
								<input type="hidden" id="${fpId}" value="${fpInfo.value.fpImage}" />
								<input type="hidden" id="${fpInd}" value="${fpInfo.value.fpIndicator}" />
								
							<%i=i+1; %>	
						</c:forEach>
					</tr>
				</table>
 		</td>
 	</tr>
 </table>
</div>

<div class="tab-content" id="responseBCA" style="display: none">
	<c:choose>
		<c:when  test="${not checkBCA_result}">
			<span style="color: #FF0000; font-size: 12px;">Không có thông tin truy vấn từ Bộ Công an.</span>
		</c:when >
		<c:otherwise>
				<table  id="box1_table" border="0" cellpadding="0"
								cellspacing="0" bordercolor="#333333" class="table table-striped table-hover table-bordered">
					<tr style="background-color: #337ab7;color: #fff;text-align: center;">
					  <th colspan="11" style="text-align: center;">THÔNG TIN DANH SÁCH ĐEN</th>
					</tr>
					<tr style="background-color: #337ab7;color: #fff;text-align: center;">
					    <th>Họ tên</th>
					    <th>Tên truy vấn</th>
					    <th>Bí danh</th>
					    <th>Ngày sinh</th>
					    <th>Giới tính</th>
					    <th>Dân tộc</th>
					    <th>Tôn giáo</th>
					    <th>CMND/CCCD</th>
					    <th>Địa chỉ</th>
					    <th>Ghi chú</th>
					    <th>Loại</th>
					</tr>
					<c:if test="${not empty modelBl}">
						<c:forEach var="bl_data" items="${modelBl}"> 
						<tr>
							<td>${bl_data.NAME}</td>
							<td>${bl_data.SEARCH_NAME}</td>
							<td>${bl_data.NICK_NAME}</td>
							<td>${bl_data.DATE_OF_BIRTH}</td>
							<td>${bl_data.GENDER}</td>
							<td>${bl_data.ETHNIC}</td>
							<td>${bl_data.RELIGION}</td>
							<td>${bl_data.ID_NUMBER}</td>
							<td>${bl_data.ADDRESS}</td>
							<td>${bl_data.NOTE}</td>			
							<td>${bl_data.TYPE_}</td>	
						</tr>
						</c:forEach>
					</c:if>
				</table>
				<br />
				<table  id="box1_table" border="0" cellpadding="0"
								cellspacing="0" bordercolor="#333333" class="table table-striped table-hover table-bordered">
					<tr style="background-color: #337ab7;color: #fff;text-align: center;">
					  <th colspan="10" style="text-align: center;">THÔNG TIN DỮ LIỆU CMND / CCCD</th>
					</tr>
					<tr style="background-color: #337ab7;color: #fff;text-align: center;">
					    <th>Họ tên</th>
					    <th>Tên truy vấn</th>
					    <th>Giới tính</th>
					    <th>Ngày sinh</th>
					    <th>Nơi sinh</th>
					    <th>CMND/CCCD</th>
					    <th>Ngày phát hành</th>
					    <th>Họ tên mẹ</th>
					    <th>Họ tên bố</th>
					    <th>Trạng thái</th>
					</tr>
					<c:if test="${not empty modelId}">
						<c:forEach var="id_data" items="${modelId}"> 
						<tr>
							<td>${id_data.NAME}</td>
							<td>${id_data.SEARCH_NAME}</td>
							<td>${id_data.GENDER}</td>
							<td>${id_data.DATE_OF_BIRTH}</td>
							<td>${id_data.PLACE_OF_BIRTH}</td>
							<td>${id_data.ID_NUMBER}</td>
							<td>${id_data.DATE_OF_ISSUE}</td>
							<td>${id_data.FATHER_NAME}</td>			
							<td>${id_data.MOTHER_NAME}</td>				
							<td>${id_data.STATUS}</td>
						</tr>
						</c:forEach>
					</c:if>
				</table>
		</c:otherwise>
	</c:choose>
</div>

<div id="dialog-scan-doc" title="Scanned Document" style="display: none;">	</div>
<script>
//setFP();

$(function(){
	/* $("#docs_cls_btn").click(function(){
		 $("#dialog-approve").dialog('close');	
	});
	 */
	$("#dialog-scan-doc").dialog({
		resizable : false,
		modal : true,
		autoOpen : false,
		width : 800,
		height : 800,
		resizable : false,
		show : {
			effect : "fade",
			duration : 1000
		},
		hide : {
			//effect: "explode",
			duration : 1000
		},
		 open : function() {
		    var data = $('#dialog-scan-doc').data('docView');
		    var data2 = $('#dialog-scan-doc').data('docName');
		    rotImagId = $('#dialog-scan-doc').data('docName'); 
		    //show(data2 + "Div");
		    RotateScanDoc();
		   },
		   close : function() {
		    var data = $('#dialog-scan-doc').data('docView');
		    var data2 = $('#dialog-scan-doc').data('docName');
		    hide(data2 + "Div");
		   }
	});
});	

	function showImage(url) {
		   
			  window.open(url,'_blank');
			  return;  
	}

	function openform(a) {
	/* 	if ((a == '1' && $('#infoPerson').css('display') == 'none')
				|| (a == '2' && $('#infoCustom').css('display') == 'none')
				|| (a == '3' && $('#investigationP').css('display') == 'none')) {
			$("#infoPerson").toggle(500);
			$("#infoCustom").toggle(500);
			$("#investigationP").toggle(500);
		}
 */
		if (a == 1) {
			document.getElementById("btnInfoPerson").style["background"] = "";
			document.getElementById("btnInfoCustom").style["background"] = "gray";
			document.getElementById("btnInvestigationP").style["background"] = "gray";
			document.getElementById("btnDocumentAttch").style["background"] = "gray";
			document.getElementById("btnImageFP").style["background"] = "gray";
			document.getElementById("btnsignerImage").style["background"] = "gray";
			document.getElementById("btnResponseBCA").style["background"] = "gray";
			
			document.getElementById("infoPerson").style["display"] = "block";
			document.getElementById("infoCustom").style["display"] = "none";
			document.getElementById("investigationP").style["display"] = "none";
			document.getElementById("documentAttch").style["display"] = "none";
			document.getElementById("imageFP").style["display"] = "none";
			document.getElementById("signerImage").style["display"] = "none";
			document.getElementById("responseBCA").style["display"] = "none";
		} else if (a == 2) {
			document.getElementById("btnInfoPerson").style["background"] = "gray";
			document.getElementById("btnInfoCustom").style["background"] = "";
			document.getElementById("btnInvestigationP").style["background"] = "gray";
			document.getElementById("btnDocumentAttch").style["background"] = "gray";
			document.getElementById("btnImageFP").style["background"] = "gray";
			document.getElementById("btnsignerImage").style["background"] = "gray";
			document.getElementById("btnResponseBCA").style["background"] = "gray";
			
			document.getElementById("infoPerson").style["display"] = "none";
			document.getElementById("infoCustom").style["display"] = "block";
			document.getElementById("investigationP").style["display"] = "none";
			document.getElementById("documentAttch").style["display"] = "none";
			document.getElementById("imageFP").style["display"] = "none";
			document.getElementById("signerImage").style["display"] = "none";
			document.getElementById("responseBCA").style["display"] = "none";
		} else if(a == 3) {
			document.getElementById("btnInfoPerson").style["background"] = "gray";
			document.getElementById("btnInfoCustom").style["background"] = "gray";
			document.getElementById("btnInvestigationP").style["background"] = "";
			document.getElementById("btnDocumentAttch").style["background"] = "gray";
			document.getElementById("btnImageFP").style["background"] = "gray";
			document.getElementById("btnsignerImage").style["background"] = "gray";
			document.getElementById("btnResponseBCA").style["background"] = "gray";
			
			document.getElementById("infoPerson").style["display"] = "none";
			document.getElementById("infoCustom").style["display"] = "none";
			document.getElementById("investigationP").style["display"] = "block";
			document.getElementById("documentAttch").style["display"] = "none";
			document.getElementById("imageFP").style["display"] = "none";
			document.getElementById("signerImage").style["display"] = "none";
			document.getElementById("responseBCA").style["display"] = "none";
		} else if(a == 4) {
			document.getElementById("btnInfoPerson").style["background"] = "gray";
			document.getElementById("btnInfoCustom").style["background"] = "gray";
			document.getElementById("btnInvestigationP").style["background"] = "gray";
			document.getElementById("btnDocumentAttch").style["background"] = "";
			document.getElementById("btnImageFP").style["background"] = "gray";
			document.getElementById("btnsignerImage").style["background"] = "gray";
			document.getElementById("btnResponseBCA").style["background"] = "gray";
			
			document.getElementById("infoPerson").style["display"] = "none";
			document.getElementById("infoCustom").style["display"] = "none";
			document.getElementById("investigationP").style["display"] = "none";
			document.getElementById("documentAttch").style["display"] = "block";
			document.getElementById("imageFP").style["display"] = "none";
			document.getElementById("signerImage").style["display"] = "none";
			document.getElementById("responseBCA").style["display"] = "none";
		} else if(a == 5) {
			document.getElementById("btnInfoPerson").style["background"] = "gray";
			document.getElementById("btnInfoCustom").style["background"] = "gray";
			document.getElementById("btnInvestigationP").style["background"] = "gray";
			document.getElementById("btnDocumentAttch").style["background"] = "gray";
			document.getElementById("btnImageFP").style["background"] = "";
			document.getElementById("btnsignerImage").style["background"] = "gray";
			document.getElementById("btnResponseBCA").style["background"] = "gray";
			
			document.getElementById("infoPerson").style["display"] = "none";
			document.getElementById("infoCustom").style["display"] = "none";
			document.getElementById("investigationP").style["display"] = "none";
			document.getElementById("documentAttch").style["display"] = "none";
			document.getElementById("imageFP").style["display"] = "block";
			document.getElementById("signerImage").style["display"] = "none";
			document.getElementById("responseBCA").style["display"] = "none";
		} else if(a == 6) {
			document.getElementById("btnInfoPerson").style["background"] = "gray";
			document.getElementById("btnInfoCustom").style["background"] = "gray";
			document.getElementById("btnInvestigationP").style["background"] = "gray";
			document.getElementById("btnDocumentAttch").style["background"] = "gray";
			document.getElementById("btnImageFP").style["background"] = "gray";
			document.getElementById("btnsignerImage").style["background"] = "";
			document.getElementById("btnResponseBCA").style["background"] = "gray";
			
			document.getElementById("infoPerson").style["display"] = "none";
			document.getElementById("infoCustom").style["display"] = "none";
			document.getElementById("investigationP").style["display"] = "none";
			document.getElementById("documentAttch").style["display"] = "none";
			document.getElementById("imageFP").style["display"] = "none";
			document.getElementById("signerImage").style["display"] = "block";
			document.getElementById("responseBCA").style["display"] = "none";
			
		} else if(a == 7) {
			document.getElementById("btnInfoPerson").style["background"] = "gray";
			document.getElementById("btnInfoCustom").style["background"] = "gray";
			document.getElementById("btnInvestigationP").style["background"] = "gray";
			document.getElementById("btnDocumentAttch").style["background"] = "gray";
			document.getElementById("btnImageFP").style["background"] = "gray";
			document.getElementById("btnsignerImage").style["background"] = "gray";
			document.getElementById("btnResponseBCA").style["background"] = "";
			
			document.getElementById("infoPerson").style["display"] = "none";
			document.getElementById("infoCustom").style["display"] = "none";
			document.getElementById("investigationP").style["display"] = "none";
			document.getElementById("documentAttch").style["display"] = "none";
			document.getElementById("imageFP").style["display"] = "none";
			document.getElementById("signerImage").style["display"] = "none";
			document.getElementById("responseBCA").style["display"] = "block";
		}
	}
	
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
	
	function showProofDoc(transId, docView, docName, dataType, docDesc) {
		if ((dataType == 'JPEG')) {
			/* var url = "${showJPEGProofDoc}" + "/" + transId + "/" + docName;
			window.open(url);  */
			window.open('data:image/jpg;base64,' + docView,'Image','width=largeImage.stylewidth,height=largeImage.style.height,resizable=1');
			/* $("#dialog-scan-doc").dialog("option", "width", 730);
			$("#dialog-scan-doc").dialog("option", "height", 790);
			$("#dialog-scan-doc").dialog('option', 'title',
					'Proof Document: ' + docDesc);
			$("#dialog-scan-doc").data('docView', docView).data('docName',
					docName).dialog("open");
			$("#dialog-scan-doc").dialog('option', 'title',
					'Proof Document: ' + docDesc);
			$("#dialog-scan-doc").data('docView', docView).data('docName',
					docName).dialog("open"); */
		} else {
			var url = "${showPDFProofDoc}" + "/" + transId + "/" + docName;
			window.open(url);
		}
	}
	
	
	function setFP() {
		/* var fpApplet = document.getElementById('investigationApplet'); */
		/* var fpApplet = document.getElementById('EppApplet');
		if (fpApplet) { */
			try {
				for(var i=1;i<=10;i++){
					var id = i;
					var fpIdVal ='fp'+id;
					var fpIndVal='fpInd'+id;
					
					var fpStr = document.getElementById(fpIdVal).value;
					var fpInd = document.getElementById(fpIndVal).value;
					
					if(fpStr !== 'undefined' && fpStr !== "" && fpStr!==null && fpStr !== undefined){
						
						//var jpgStr = fpApplet.convertImageFormatWsqToJpg(fpStr);
						var jpgStr = "";
						$.ajax({
				 	      type: "POST",
				 	      url: "${convertImgUrl}", 
				 	      data: fpStr,
				 	      dataType: "json",
				 	      contentType: "application/json; charset=utf-8",
				 	      success: function(response) {
				 	    	  alert(response);
			                    if(response != "")
			                    	jpgStr = response;
			                    console.log("LOG: " + response);
			                }
				 	  	}).done(function(principal){
				 	        alert(principal);
				 	    });
						if(jpgStr !== "" && jpgStr!==null){
							setJpegImages(jpgStr,fpInd, '01', id );
						}else{
							setJpegImages(jpgStr,'I', '01', id );
						}
					}else{
						setJpegImages(null,fpInd, '01', id );
					}
				}
				
			} catch (e) {
				alert('Error occurred while displaying the fingerprints : '+e);
			}
		/* } */
	}

	function setJpegImages(imageVal, status, index, id) {
			if(imageVal != 'undefined' && imageVal != "" && imageVal!=null && imageVal !== undefined){
				document.getElementById(id).src = "data:image/jpg;base64,"
						+ imageVal;
			} else if (status == 'A') {
				$("#" + id)
						.attr("src", '<c:url value="/resources/images/AS.jpg"/>');
			} else if (status == 'U') {
				$("#" + id).attr("src",
						'<c:url value="/resources/images/Unable.jpg"/>');
			} else if (status == 'I') {
				$("#" + id).attr("src",
				'<c:url value="/resources/images/Invalid_FP.jpg"/>');
			}else {
				$("#" + id)
						.attr("src", '<c:url value="/resources/images/NS.jpg"/>');
			}
	}
	
	
</script>


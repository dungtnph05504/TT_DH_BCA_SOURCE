<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="fpInfoUrl" value="/servlet/transactionEnquiry/fpInfo" />
<!-- <script type="text/javascript">
	$(function() {
		$("#personSum_cls_btn").click(function() {
			$("#dialog-approve").dialog('close');
		});
	});
</script> -->
<style>
.doGetAJpgSafeVersion {
	visibility: hidden;
}

.NotTheSame {
	color: #FF0000;
}

</style>
<script> 

	function getFPinfo(transactionId, nin, fpIndicator, fpQuality, fpEncode,fpVerifyScore) 
	{
		//$('.modal').show();
		//var titleName = 'Fingerprints #' + transactionId + " ," + nin;
		//$("#dialog-fpView").dialog('option', 'title', titleName);
		//$("#dialog-fpView").dialog("option", "width", 750);
		//$("#dialog-fpView").dialog("option", "height", 470);//520
		//$("#dialog-fpView").html("Loading .....");
		//$("#dialog-fpView").dialog('open');
		$('#ajax-load-qa').show();
		$.ajax({
					type : "GET",
					url : '${fpInfoUrl}',
					data : {
						transactionId : transactionId,
						passportNo : nin,
						fpIndicator : fpIndicator,
						fpQuality : fpQuality,
						fpEncode : fpEncode,
						fpVerifyScore : fpVerifyScore
					},
					success : function(data) {
						$("#divVanTay").html(data);
						$('#idVanTay').modal();
						$('#ajax-load-qa').hide();
						//$('.modal').hide();
					},
					error : function(e) {
						$("#divVanTay").html('Đã xảy ra lỗi khi hiển thị thông tin về dấu vân tay');
						$('#idVanTay').modal();
						$('#ajax-load-qa').hide();
					}
				});
	}
	    
	$(document).ready(function() { 
		$('#clsThongTin').click(function() {
			$('#idVanTay').modal('toggle');
		});
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
	});
</script>
<form:form modelAttribute="nicEnqForm" id="form1" action="/servlet/nicEnquiry/search" method="post">
	<!-- <div id="inner_main"> -->
		<!-- <div id="inner_main_left" style="width: 100%; float: none;"> -->
			<table width="100%" style="font-size: 12px; padding-left: 10px;">
				<c:choose>
					<c:when test="${not empty nicRegistrationData}">
						<tr>
							<%-- <td width='30%' style="font-weight: bold;padding: 5px;" >NIN</td>
				<td  id="ninid" align="left"> <c:out value="${nicRegistrationData.nin }"/></td> --%>

							<td rowspan="15" valign="top" align="center" style="padding: 5px;">
								<table style="text-align: center;">
									<tr>
										<td style="padding: 5px;" align="center">
										<c:choose>
												<c:when test="${not empty photoStr}">
													<img id="photoid" src="data:image/jpg;base64,${photoStr}" class="thumbnail doGetAJpgSafeVersion"
														style="cursor: pointer;" alt="Photo" align="middle" width="170px" height="270px"/>
													<!--<img id="photoid" src="data:image/jpg;base64,${photoStr}" class="thumbnail doGetAJpgSafeVersion"
														style="cursor: pointer;" alt="Photo" align="middle" width="99px"
														onclick="openFullViewPhotoDialog('${nicRegistrationData.transactionId}','${passportNo}');" />-->
												</c:when>
												<c:otherwise>
													<img id="photoid"
														src="<c:url value="/resources/images/No_Image.jpg"/>" width="99px" height="132px" align="middle" />
												</c:otherwise>
											</c:choose>
											</td>
									</tr>
									<%-- <tr>
										<td style="padding: 5px;" align="center">
											<c:choose>
												<c:when test="${(signatureFlag eq 'S')}">
													<img id="signatureid"
														src="data:image/jpg;base64,${signatureStr}"
														alt="Signature" align="middle" height="35px" />
												</c:when>
												<c:when test="${signatureFlag eq 'T' }">
													<img id="signatureid"
														src="data:image/jpg;base64,${signatureStr}"
														alt="Signature" align="middle" height="100px" />
												</c:when>
												<c:otherwise>
													<b> No Signature</b>
												</c:otherwise>
											</c:choose>
										</td>
									</tr> --%>
									<tr>
										<td style="padding: 5px;" align="center"><c:choose>
												<c:when test="${(empty fullAmpFlag) || (fullAmpFlag eq 'N')}">
													<%-- <input type="button" style="cursor: pointer;" class="button_grey" onclick="getFPinfo('${nicRegistrationData.transactionId}','${nicRegistrationData.nin}', '${nicRegistrationData.fpIndicator}', '${nicRegistrationData.fpQuality}', '${nicRegistrationData.fpEncode}', '${nicRegistrationData.fpVerifyScore}');"   value="View Fingerprint Info"/> --%>
													<input type="button" style="cursor: pointer;" class="btn btn-success"
														onclick="getFPinfo('${nicRegistrationData.transactionId}','${passportNo}', '${nicRegistrationData.fpIndicator}', '${nicRegistrationData.fpQuality}', '${nicRegistrationData.fpEncode}', '');"
														value="Xem thông tin vân tay" />
												</c:when>
												<c:otherwise>
													<b style="color: #FF0000; font-size: 12px;">Không có thông tin vân tay</b>
												</c:otherwise>
											</c:choose></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td style="font-weight: bold; padding: 5px;">Số hộ chiếu</td>
							<td id="firstnameid" style="padding: 5px;"><c:out
									value="${passportNo}" /></td>
						</tr>
						<tr>
							<td style="font-weight: bold; padding: 5px;">Trạng thái hộ chiếu</td>
							<td id="firstnameid" style="padding: 5px;"><c:out
									value="${passportStatus}" /></td>
						</tr>
						<tr>
							<td style="font-weight: bold; padding: 5px;">Họ tên</td>
							<td id="firstnameid" style="padding: 5px;"><c:out
									value="${nicRegistrationData.surnameFull}" /></td>
						</tr>
						<!--<tr>
							<td style="font-weight: bold; padding: 5px;">Họ</td>
							<td id="surnameid" style="padding: 5px;"><c:out
									value="${nicRegistrationData.surnameFull}" /></td>
						</tr>
						<tr>
							<td style="font-weight: bold; padding: 5px;">Tên đệm</td>
							<td id="middlenameid" style="padding: 5px;"><c:out
									value="${nicRegistrationData.middlenameFull}" /></td>
						</tr>-->
						
						<%-- <tr>
				<td style="font-weight: bold;padding: 5px;" >Surname at Birth</td>
				<td  id="surnameatbirthid" style="padding: 5px;"><c:out value="${nicRegistrationData.surnameBirthFull }"/></td>
			</tr> --%>
						<%-- <tr>
				<td style="font-weight: bold;padding: 5px;" >Option Surname</td>
				<td  id="surnameatbirthid" style="padding: 5px;"><c:out value="${nicRegistrationData.optionSurname}"/></td>
			</tr> --%>
						<tr>
							<c:choose>
								<c:when test="${approxDOBFlag eq 'N' }">
									<td style="font-weight: bold; padding: 5px;">Ngày sinh</td>
								</c:when>
								<c:otherwise>
									<td style="font-weight: bold; padding: 5px;">Approx. DOB
									</td>
								</c:otherwise>
							</c:choose>

							<td id="dobid" style="padding: 5px;"><c:out value="${dob}" /></td>
						</tr>
						<tr>
							<td style="font-weight: bold; padding: 5px;">Giới tính</td>
							<td id="genderid" style="padding: 5px;"><c:choose>
									<c:when test="${nicRegistrationData.gender eq 'M'}">
			        					Nam
			        				</c:when>
									<c:when test="${nicRegistrationData.gender eq 'F'}">
			        					Nữ
			        				</c:when>
									<c:when test="${nicRegistrationData.gender eq 'X'}">
			        					Không xác định
			        				</c:when>
									<c:otherwise>
										<c:out value="${nicRegistrationData.gender}" />
									</c:otherwise>
								</c:choose></td>
						</tr>
						<!--<tr>
							<td style="font-weight: bold; padding: 5px;">Tình trạng hôn nhân</td>
							<td id="maritalstatusid" style="padding: 5px;"><c:choose>
									<c:when test="${nicRegistrationData.maritalStatus eq '0'}">
        					Độc thân
        				</c:when>
									<c:when test="${nicRegistrationData.maritalStatus eq '1'}">
        					Kết hôn
        				</c:when>
									<c:when test="${nicRegistrationData.maritalStatus eq '2'}">
        					Khác
        				</c:when>
									<c:otherwise>
										<c:out value="${nicRegistrationData.maritalStatus}" />
									</c:otherwise>
								</c:choose></td>
						</tr>-->
						<tr>
							<td width="20%" style="font-weight: bold; padding: 5px;">Địa chỉ</td>
							<td id="address1id" style="padding: 5px;"><c:out
									value="${nicRegistrationData.addressLine1}" /></td>
						</tr>
						<%-- <tr>
							<td style="font-weight: bold; padding: 5px;">Địa chỉ</td>
							<td id="address2id" style="padding: 5px;"><c:out
									value="${nicRegistrationData.addressLine2 }" /></td>
						</tr>
						
						<tr>
							<td style="font-weight: bold; padding: 5px;">Tên đầy đủ</td>
							<td id="printRemarks2" style="padding: 5px;"><c:out
									value="${nicRegistrationData.printRemarks2}" /></td>
						</tr>
						
						<tr>
							<td style="font-weight: bold; padding: 5px;">AKA</td>
							<td id="aliasName" style="padding: 5px;"><c:out
									value="${nicRegistrationData.aliasName}" /></td>
						</tr>
						
						<tr>
							<td style="font-weight: bold; padding: 5px;">Chức vụ</td>
							<td id="position" style="padding: 5px;"><c:out
									value="${nicRegistrationData.occupationDesc}" /></td>
						</tr>
						
						<tr>
							<td style="font-weight: bold; padding: 5px;">Giới hạn</td>
							<td id="limitation" style="padding: 5px;"><c:out
									value="${nicRegistrationData.printRemarks1}" /></td>
						</tr> --%>
						<%-- <tr>
							<td style="font-weight: bold; padding: 5px;">Locality</td>
							<td id="address3id" style="padding: 5px;"><c:out
									value="${nicRegistrationData.addressLine3 }" /></td>
						</tr>
						<tr>
							<td style="font-weight: bold; padding: 5px;">City/Town/Village</td>
							<td id="address4id" style="padding: 5px;"><c:out
									value="${townVillageDesc}" /></td>
						</tr>
						<tr>
							<td style="font-weight: bold; padding: 5px;">District/Outer
								Island</td>
							<td id="address5id" style="padding: 5px;"><c:out
									value="${districtDesc}" /></td>
						</tr>
						<tr>
				<td style="font-weight: bold;padding: 5px;" >Postal Code</td>
				<td  id="address6id" style="padding: 5px;"><c:out value="${nicRegistrationData.addressLine6 }"/></td>
			</tr>
						<tr>
							<td style="font-weight: bold; padding: 5px;">Overseas
								Address</td>
							<td id="overseasAddid" style="padding: 5px;"><c:out
									value="${nicRegistrationData.overseasAddress }" /></td>
						</tr>
						<tr>
							<td style="font-weight: bold; padding: 5px;">Overseas
								Country</td>
							<td id="overseasCtryid" style="padding: 5px;"><c:out
									value="${nicRegistrationData.overseasCountry }" /></td>
						</tr>
						<tr>
				<td style="font-weight: bold;padding: 5px;" >Preferred Mailing Address</td>
				<td  id="prefMailAddid" style="padding: 5px;"><c:out value="${nicRegistrationData.preferredMailingAddress }"/></td>
			</tr>
						<tr>
				<td style="font-weight: bold;padding: 5px;" >Father Name</td>
				<td  id="fathernameid" style="padding: 5px;"><c:out value="${nicRegistrationData.fatherName }"/></td>
			</tr>
						<tr>
							<td style="font-weight: bold; padding: 5px;">Father Surname</td>
							<td id="fathersurnameid" style="padding: 5px;"><c:out
									value="${nicRegistrationData.fatherSurname }" /></td>
						</tr>
									<tr>
				<td style="font-weight: bold;padding: 5px;" >Father NIN</td>
				<td  id="fatherninid" style="padding: 5px;"><c:out value="${nicRegistrationData.fatherNin }"/></td>
			</tr>
						<tr>
				<td style="font-weight: bold;padding: 5px;" >Mother Name</td>
				<td  id="mothernameid" style="padding: 5px;"><c:out value="${nicRegistrationData.motherName }"/></td>
			</tr>
						<tr>
							<td style="font-weight: bold; padding: 5px;">Mother Surname</td>
							<td id="mothersurnameid" style="padding: 5px;"><c:out
									value="${nicRegistrationData.motherSurname }" /></td>
						</tr>
									<tr>
				<td style="font-weight: bold;padding: 5px;" >Mother NIN</td>
				<td  id="motherninid" style="padding: 5px;"><c:out value="${nicRegistrationData.motherNin }"/></td>
			</tr>
						<tr>
				<td style="font-weight: bold;padding: 5px;" >Spouse Name</td>
				<td  id="spousenameid" style="padding: 5px;"><c:out value="${nicRegistrationData.spouseName }"/></td>
			</tr>
						<tr>
							<td style="font-weight: bold; padding: 5px;">Spouse Surname</td>
							<td id="spousesurnameid" style="padding: 5px;"><c:out
									value="${nicRegistrationData.spouseSurname }" /></td>
						</tr> --%>
						<%-- 			<tr>
				<td style="font-weight: bold;padding: 5px;"  >Spouse NIN</td>
				<td  id="spouseninid" style="padding: 5px;"><c:out value="${nicRegistrationData.spouseNin }"/></td>
			</tr> --%>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="2">Không tìm thấy tóm tắt Người dùng để hiển thị</td>
						</tr>
						<!-- <tr>
							<td colspan="2" height="220px">&nbsp;</td>
						</tr> -->
					</c:otherwise>
				</c:choose>

			</table>
			<!-- <table style="width: 100%;">
				<tr height="152px">
					<td align="center" valign="bottom" style="padding: 20px; text-align: right;"><input
						type="button" class="button_grey" id="personSum_cls_btn"
						value="Close" /></td>
				</tr>
			</table> -->
		<!-- </div> -->
	<!-- </div> -->
	<!-- Message lưu hồ sơ -->
	<div class="modal fade" id="idVanTay" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
	  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 950px;">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">CHI TIẾT VÂN TAY</h5>
	        <button type="button" class="close" id="clsThongTin">
	          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
	        </button>
	      </div>
	      <div class="modal-body" id="divVanTay" style="height: 400px;">
	      		
	      </div>							      
	      <div class="modal-footer" style="padding: 5px;">
	       		       		
	      </div>
	    </div>
	  </div>
	</div>	
	<!-- ---------------------------------------------------------------------------->
	
</form:form>



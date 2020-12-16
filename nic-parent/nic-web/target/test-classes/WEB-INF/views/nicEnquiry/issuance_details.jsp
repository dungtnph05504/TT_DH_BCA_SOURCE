<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <script type="text/javascript">
$(function(){
	$("#issuance_det_cls_btn").click(function(){
		 $("#infoDialog").dialog('close');	
	});
	if('${viewFPFalg}'=='Y'){
		 setFPForIss();
	}
});

function setFPForIss() {
	var fpAppletIss = document.getElementById('investigationApplet');
	if (fpAppletIss) {
		try {
			for(var i=1;i<='${fpSize}';i++){
				var id = i;
				var fpIdVal ='fpIss'+id;
				
				var fpStr = document.getElementById(fpIdVal).value;
				
				if(fpStr !== 'undefined' && fpStr !== "" && fpStr!==null && fpStr !== undefined){
					var jpgStr = fpAppletIss.convertImageFormatWsqToJpg(fpStr);
					if(jpgStr !== "" && jpgStr!==null){
						setJpegImagesForIss(jpgStr,'N', '01', id );
					}else{
						setJpegImagesForIss(jpgStr,'I', '01', id );
					}
				}else{
					setJpegImagesForIss(null,'NS', '01', id );
				}
			}
			
		} catch (e) {
			alert('Đã có lỗi xảy ra khi hiển thị vân tay : '+e);
		}
	}
}

function setJpegImagesForIss(imageVal, status, index, id) {
		if(imageVal != 'undefined' && imageVal != "" && imageVal!=null && imageVal !== undefined){
			id='Iss'+id;
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
<c:choose>
	<c:when test="${not empty convertedIssuanceData }">
		 <table style="width: 100%; text-align: center;">
		 	<tr>
				<td>
					<table style="width: 100%; text-align: center;">
						<tr>
							<td align="left">
								<b>Mã giao dịch:</b>
							</td>
							<td align="left">
								<c:out value="${convertedIssuanceData.issuanceDetails.transactionId}"></c:out>
							</td>
							<td width="25px;" align="left">
							</td>
							<td align="left">
								<b>Người tạo:</b>
							</td >
							<td align="left">
								<c:out value="${convertedIssuanceData.issuanceDetails.issuanceBy}"></c:out>
							</td>
						</tr>
						<tr>
							<td align="left">
								<b>CMND/CCCD:</b>
							</td>
							<td align="left">
								<c:out value="${convertedIssuanceData.issuanceDetails.nin}"></c:out>
							</td>
							<td width="25px;" align="left">
							</td>
							<td align="left">
								<b>Mã máy trạm:</b>
							</td>
							<td align="left">
								<c:out value="${convertedIssuanceData.issuanceDetails.issuanceWkstnId}"></c:out>
							</td>
						</tr>
						<tr>
							<td align="left">
								<b>CCN:</b>
							</td>
							<td align="left">
								<c:out value="${convertedIssuanceData.issuanceDetails.ccn}"></c:out>
							</td>
							<td width="25px;" align="left">
							</td>
							<td align="left">
								<b>Ngày tạo:</b>
							</td>
							<td align="left">
								<c:out value="${convertedIssuanceData.issuanceDetails.issuanceDate}"></c:out>
							</td>
						</tr>
						<tr>
							<td align="left">
								<b>Trạng thái:</b>
							</td>
							<td align="left">
								<c:out value="${convertedIssuanceData.issuanceDetails.cardStatus}"></c:out>
							</td>
							<td width="25px;" align="left">
							</td>
							<td align="left">
								<b>Quyết định ban hành:</b>
							</td>
							<td align="left">
								<c:choose>
									<c:when test="${convertedIssuanceData.issuanceDetails.issuanceDecision eq 'I'}">
										Phát hành
									</c:when>
									<c:when test="${convertedIssuanceData.issuanceDetails.issuanceDecision eq 'R'}">
										Hủy
									</c:when>
									<c:otherwise>
										<c:out value="${convertedIssuanceData.issuanceDetails.issuanceDecision}"></c:out>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					
					</table>
				</td>
		 	</tr>
		 	<tr>
		 		<td valign="top" height="200px;">
		 			<table style="width: 100%;text-align: left;">
		 				<c:if test="${not empty fpEnquiryInfoList}">
		 				<%int i=1; %>
		 				<c:forEach items="${fpEnquiryInfoList}" var="fpInfo">
		 				<c:set var="fpIssId">fpIss<%=i %></c:set>
		 					<tr>
		 						<td valign="top" height="200px;"  width="200px;" style="vertical-align: top; padding: 0px;">
		 							<c:choose>
										<c:when test="${viewFPFalg eq 'Y' }">
										<table style="font-size: 9px;height: 200px; vertical-align: top;" >
		 								<tr>
		 									<td valign="top">
												<img align="top" src="data:image/jpg;base64," id="Iss<%=i %>"  alt="Fingerprint Image" style="padding: 0px; width:200px; height: 200px" title="Quality : ${fpInfo.qualityScore}   Match Score : ${fpInfo.matchScore}" />
												 <input type="hidden" id="${fpIssId}" value="${fpInfo.fpDataStr}" />
												</td>
										</tr>
										</table>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${not empty fpInfo.fpDataStr}">
													<img id="FP1" data-index="1"
													src="<c:url value="/resources/images/fpTick.jpg"/>"
													width="200px" height="200px" 
													style="border: 1px SOLID; align: left" alt="RIGHT THUMB"
													title="Quality : ${fpInfo.qualityScore}   Match Score : ${fpInfo.matchScore}"
													align="top" />
												</c:when>
												<c:otherwise>
													<img  align="top" id="FP5" data-index="5" src="<c:url value="/resources/images/NS.jpg"/>" width="200px" height="200px" style="border: 1px SOLID; align: left" alt="Fingerprint Image" align="middle" />
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
		 						</td>
		 						<td width="10px">
		 							&nbsp;
		 						</td>
		 						<td align="left" valign="middle" style="padding-top: 15px;">
		 							<table style="font-size: 9px;height: 140px;" >
		 								<tr>
		 									<td style="height: 25px;">
		 										Vị trí vân tay:&nbsp; &nbsp; 
		 										<c:choose>
		 											<c:when test="${fpInfo.fpPosition eq '1'}">
		 												Ngón cái phải
		 											</c:when>
		 											<c:when test="${fpInfo.fpPosition eq '2'}">
		 												Ngón trỏ phải
		 											</c:when>
		 											<c:when test="${fpInfo.fpPosition eq '3'}">
		 												Ngón giữa phải
		 											<c:when test="${fpInfo.fpPosition eq '4'}">
		 												Ngón áp út phải
		 											</c:when>
		 											<c:when test="${fpInfo.fpPosition eq '5'}">
		 												Ngón út phải
		 											</c:when>
		 											<c:when test="${fpInfo.fpPosition eq '6'}">
		 												Ngón cái trái
		 											</c:when>
		 											<c:when test="${fpInfo.fpPosition eq '7'}">
		 												Ngón trỏ trái
		 											</c:when>
		 											<c:when test="${fpInfo.fpPosition eq '8'}">
		 												Ngón giữa trái
		 											</c:when>
		 											<c:when test="${fpInfo.fpPosition eq '9'}">
		 												Ngón áp út trái
		 											</c:when>
		 											<c:when test="${fpInfo.fpPosition eq '10'}">
		 												Ngón út trái
		 											</c:when>
		 											<c:otherwise>
		 												Vị trí ngón tay không hợp lệ
		 											</c:otherwise>
		 										</c:choose>
		 									</td>
		 								</tr>
		 								<tr>
		 									<td style="height: 25px;">
		 										Chất lượng: &nbsp; &nbsp; <c:out value="${fpInfo.qualityScore}"></c:out>
		 									</td>
		 								</tr>
		 								<tr>
		 									<td style="height: 25px;">
		 										Nguồn vân tay: &nbsp; &nbsp; <c:out value="${fpInfo.fpSource}"></c:out>
		 									</td>
		 								</tr>
		 								<tr>
		 									<td style="height: 25px;">
		 										Vân tay đã xác minh: &nbsp; &nbsp;
		 										<c:choose>
		 											<c:when test="${fpInfo.fpVerifyFlag eq 'Y'}">
		 												<img id="tick" src="<c:url value="/resources/images/tick.gif"/>" width="16px" height="16px" style="align: left" alt="Verified" title="Verified" />
		 											</c:when>
		 											<c:otherwise>
		 												<img id="cross" src="<c:url value="/resources/images/cross.png"/>" width="16px" height="16px" style="align: left" alt="Verified" title="Not Verified" />
		 											</c:otherwise>
		 										</c:choose>
		 									</td>
		 								</tr>
		 								<tr>
		 									<td style="height: 25px;">
		 										Điểm trùng:&nbsp; &nbsp; <c:out value="${fpInfo.matchScore}"></c:out>
		 									</td>
		 								</tr>
		 							</table>
		 						</td>
		 					</tr>
		 					<%i=i+1; %>	
		 				</c:forEach>
		 				</c:if>
		 				<c:if test="${empty fpEnquiryInfoList }">
		 					Không có vân tay
		 				</c:if>
		 			</table>
		 		</td>
		 	</tr>
		 	<tr>
		 		<td>
		 			&nbsp;
		 		</td>
		 	</tr>
		    	<tr>
		        	<td align="center" style="padding: 20px; text-align: right;" >
				      <input type="button" class="button_grey" id="issuance_det_cls_btn"  value="Close"/>
			        </td>
			    </tr>
		 </table>
	
	</c:when> 
	<c:otherwise>
		 <table style="width: 100%; text-align: center;">
		 		<tr>
		 			<td>
		 				Không tìm thấy thông tin phát hành để hiển thị
		 			</td>
		 		</tr>
		 		<tr>
		        	<td align="center" style="padding: 20px; text-align: right;" >
				      <input type="button" class="button_grey" id="issuance_det_cls_btn"  value="Close"/>
			        </td>
			    </tr>
		 </table>
	</c:otherwise>
</c:choose>

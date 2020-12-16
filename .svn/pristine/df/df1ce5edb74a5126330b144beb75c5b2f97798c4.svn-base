<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <script type="text/javascript">
$(function(){
	$("#fpInfo_cls_btn").click(function(){
		 $("#dialog-fpView").dialog('close');	
	});
	
	if('${viewFPFalg}'=='Y'){
		 setFP();
	}
});

function setFP() {
	/* var fpApplet = document.getElementById('investigationApplet'); */
	var fpApplet = document.getElementById('EppApplet');
	if (fpApplet) {
		try {
			for(var i=1;i<=10;i++){
				var id = i;
				var fpIdVal ='fp'+id;
				var fpIndVal='fpInd'+id;
				
				var fpStr = document.getElementById(fpIdVal).value;
				var fpInd = document.getElementById(fpIndVal).value;
				
				if(fpStr !== 'undefined' && fpStr !== "" && fpStr!==null && fpStr !== undefined){
					var jpgStr = fpApplet.convertImageFormatWsqToJpg(fpStr);
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
			//alert('Đã có lỗi xảy ra trong khi hiển thị dấu vân tay : '+e);
			/*$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/error_1a.png\">" + "Đã có lỗi xảy ra trong khi hiển thị dấu vân tay",
			});*/
		}
	}
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
														<c:when test="${fpInfo.value.fpImage != ''}">
															<img src="data:image/jpg;base64,${fpInfo.value.fpImage}" id="<%=i %>" height="110px"  alt="Fingerprint Image"
															style="padding: 5px; width:80px; height: 80px" title="Quality : ${fpInfo.value.fpQuaScr} <%--   Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />
															
															<input type="hidden" id="${fpId}" value="${fpInfo.value.fpImage}" />
															<input type="hidden" id="${fpInd}" value="${fpInfo.value.fpIndicator}" />
														
														</c:when>
														<c:otherwise>
															<img id="FP<%=i %>" width="80px" height="80px" data-index="<%=i %>" src="<c:url value="/resources/images/noFp.png"/>" />
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
							class="text"><b>Ngón ap út trái</b></td>
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
														<c:when test="${fpInfo.value.fpImage != ''}">
															<img src="data:image/jpg;base64,${fpInfo.value.fpImage}" id="<%=i %>" height="110px"  alt="Fingerprint Image" title="Quality : ${fpInfo.value.fpQuaScr}  <%--  Verify Score : ${fpInfo.value.fpVerifyScr} --%>"
															style="padding: 5px; width:80px; height: 80px" />
															
															<input type="hidden" id="${fpId}" value="${fpInfo.value.fpImage}" />
															<input type="hidden" id="${fpInd}" value="${fpInfo.value.fpIndicator}" />
														</c:when>
														<c:otherwise>
															<img id="FP<%=i %>" width="80px" height="80px" data-index="<%=i %>" src="<c:url value="/resources/images/noFp.png"/>" />
														</c:otherwise>
													</c:choose>
								
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${not empty fpInfo.value.fpImage}">
															<img id="FP1" data-index="1"
														src="<c:url value="/resources/images/fpTick.jpg"/>"
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
 	<tr>
 		<td>
 			&nbsp;
 		</td>
 	</tr>
    	
 </table>
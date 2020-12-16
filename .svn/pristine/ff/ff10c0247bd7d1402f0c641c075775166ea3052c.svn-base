<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url var="userDeclarationPdf" value="/servlet/transactionEnquiry/userDeclarationPdf" />
<c:url var="collSlipPdf" value="/servlet/transactionEnquiry/colSlipPdf" />
<script>
	$(function() {
		$("#ninDtlsDialog").dialog({
			modal : true,
			autoOpen : false,
			width : 500,
			height : 300,
			closeOnEscape : false,
			show : {
				effect : "blind",
				duration : 1000
			},
			hide : {
				//effect : "explode",
				//duration : 1000
			}
		});
		$('#viewMoreApplDetails').click(function() {
			$('#ninDtlsDialog').dialog("open");
		});

		$("#userDeclarationPdf").click(function() {
			var url = "${userDeclarationPdf}" + "/" + $("#trnasIdSpan").text();
			window.open(url).focus();
			//window.showModalDialog(url, null, "dialogWidth:1280px;dialogHeight:800px;resizable:yes;addressbar:no;"); 
		});

		$("#colSlipPdf").click(function() { 
			var url = "${collSlipPdf}" + "/" + $("#trnasIdSpan").text();
			window.open(url).focus();
			//window.showModalDialog(url, null, "dialogWidth:1280px;dialogHeight:800px;resizable:yes;addressbar:no;"); 
		});
	});
</script>
<div class="" style="background-color: #DDDDDD;">
	<form:form id="viewDetailTransForm" modelAttribute="newRegistration"
		style="background-color: #DDDDDD; margin: 0;">
		<div style="width: 1200px">
			<table style="margin-top: 1px">
				<tr>
					<td>
						<div style="height: 445px;">
							<!-- Appl Details   <%@include file="/WEB-INF/views/common/applicant-details.jsp"%> -->
							<table class="roundedBorder"
								style="width: 600px; margin-top: 0px; background-color: #FFFFC6">
								<tr>
									<td colspan='2'>
										<div id="sub_heading_new">APPLICATION DETAILS</div>
									</td>
								</tr>
								<tr class="evenrowcolor">
									<td width="150px" class="description">NIN:</td>
									<td class='subjectsmall'><span id="nin.field.span"
										style="font-size: 12px">${newRegistration.person.nin}</span></td>
								<tr>
								<tr class="oddrowcolor">
									<td width="150px" class="description">Surname:</td>
									<td class='subjectsmall' width='150px'><span
										id="name.field.span" style="font-size: 12px">${newRegistration.person.surname}</span></td>
								</tr>
								<tr class="evenrowcolor">
									<td width="150px" class="description">First Name:</td>
									<td class='subjectsmall' width='80%'><span
										id="name.field.span" style="font-size: 12px">${newRegistration.person.firstname}
									</span></td>
								</tr>
								<tr class="oddrowcolor">
									<td width="150px" class="description">Surname at Birth:</td>
									<td class='subjectsmall'><span id="marriedName.field.span"
										style="font-size: 12px">${newRegistration.person.surnameBirth}</span>

									</td>
								</tr>
								<tr class="evenrowcolor">
									<td class="description">D.O.B:</td>
									<td class='subjectsmall'><span id="DOB.field.span"
										style="font-size: 12px">${newRegistration.person.dob}</span></td>
								</tr>
								<tr class="oddrowcolor">
									<td class="description">Gender:</td>
									<td class='subjectsmall'><span id="sex.field.span"
										style="font-size: 12px">${newRegistration.person.genderDesc}</span></td>
								</tr>
								<tr class="evenrowcolor">
									<td class="description">Marital &nbsp;Status:</td>
									<td class='subjectsmall'><span
										id="maritalStatus.field.span" style="font-size: 12px">${newRegistration.person.maritalStatusDesc}</span>

									</td>
								</tr>
								<tr>
									<td colspan=2 class="description"
										style="font-weight: bold; text-decoration: underline; color: #00008B; height: 20px;">ADDRESS</td>
								</tr>
								<tr class="oddrowcolor">
									<td width="150px" class="description">Flat No,Building
										Name:</td>
									<td id="address1td"><span id="maritalStatus.field.span"
										style="font-size: 12px">${newRegistration.person.address1}</span>

									</td>
								</tr>
								<tr class="evenrowcolor">
									<td width="150px" class="description">Street No. and Name:</td>
									<td id="address2td"><span id="maritalStatus.field.span"
										style="font-size: 12px">${newRegistration.person.address2}</span>

									</td>
								</tr>
								<tr class="oddrowcolor">
									<td width="150px" class="description">Locality:</td>
									<td id="address3td" style="width: 450px"><span
										id="maritalStatus.field.span" style="font-size: 12px">${newRegistration.person.address3}</span>

									</td>
								</tr>
								<tr class="evenrowcolor">
									<td width="150px" class="description">Town/Village:</td>
									<td id="address4td"><span id="maritalStatus.field.span"
										style="font-size: 12px">${newRegistration.townDisp}</span>

									</td>
								</tr>
								<tr class="oddrowcolor">
									<td width="150px" class="description">District:</td>
									<td id="address5td"><span id="maritalStatus.field.span"
										style="font-size: 12px">${newRegistration.districtDisp}</span>

									</td>
								</tr>
								<tr class="oddrowcolor">
									<td width="150px" class="description">Postcode:</td>
									<td id="address6td"><span id="maritalStatus.field.span"
										style="font-size: 12px">${newRegistration.person.address6}</span>

									</td>
								</tr>
								<tr>
									<td style="padding: 5px; width: 180px" align="right"><span
										style="text-align: right; padding: 5px"><input
											type="button" style="width: 150px; white-space: normal;"
											class="btn_blue gradient" id="viewMoreApplDetails"
											value="More Person Details" /></span></td>
								</tr>
							</table>
						</div>
					</td>
					<!--Right Side-->
					<td valign="top">
						<div style="height: 445px;">
							<%@include file="/WEB-INF/views/common/document-registration.jsp"%>
							<!-- Remarks  -->
							<table class="roundedBorder"
								style="width: 650px; margin-top: 1px; height: 75px; background-color: #FFFFC6">
								<tr style="">
									<td>
										<div id="sub_heading_new" style="height: 20px">REMARKS</div>
									</td>
								</tr>
								<tr>
									<td style="padding: 5px"><div
											style="height: 100px; width: 615px; overflow-x: hidden">
											<table style="table-layout: fixed; width: 100%">
												<c:forEach var="log"
													items="${newRegistration.ricTransactionLogList}"
													varStatus="status">
													<tr>
														<td width="200px">${log.userId}</td>
														<td width="400px" style="word-wrap: break-word">${log.remarks}</td>
													</tr>

												</c:forEach>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
			<!--bottom table-->
			<table style="margin-top: 0px">
				<tr>
					<td style="width: 500px">
						<%@include file="/WEB-INF/views/common/fingerprint-registration.jsp"%>
					</td>
					<td valign="top"><table>
							<tr>
								<td valign="top">
									<%@include file="/WEB-INF/views/common/facial-registration.jsp"%></td>
								<td valign="top"><table class="roundedBorder"
										style="margin-left: 5px; margin-top: 0px; margin-left: 0px; width: 395px; height: 236px; background-color: #FFFFC6">
										<tr>
											<td><div id="sub_heading_new">USER DECLARATION</div></td>
										</tr>
										<tr>
											<td>
												<table>
													<tr>
														<td style="padding: 5px; width: 150px"><span
															class="subjectsmall" style="align: right; width: 10px"
															id="required">RIC Collection Office</span></td>
														<td><span class='subjectsmall'
															style="font-size: 12px">${newRegistration.issueSite}</span>
														</td>
													</tr>
													<tr>
														<td style="padding: 5px"><span class="subjectsmall"
															style="align: right" id="">Contact Number</span></td>
														<td><span class='subjectsmall'
															style="font-size: 12px">${newRegistration.phone}</span></td>
													</tr>
													<tr>
														<td style="padding: 5px"><span class="subjectsmall"
															style="align: right" id="">Email
																Address&nbsp;&nbsp;&nbsp;</span></td>
														<td><span class='subjectsmall'
															style="font-size: 12px">${newRegistration.email}</span></td>
													</tr>
													<tr>
														<td colspan="2" style="padding-left: 5px">
															<div id="fpSection" style="display: none;">
																<span style="border: none; text-align: center"
																	class="subjectsmall">&nbsp;&nbsp;Fingerprint</span>
																<c:choose>
																	<c:when test="${not empty newRegistration.signFp}">
																		<img id="fingerImg"
																		src="<c:url value="/images/fpTick.jpg"/>"
																		width="100px" height="120px" alt="Signature"
																		align="middle" />
																	</c:when>
																	<c:otherwise>
																		<img id="fingerImg"
																		src="<c:url value="/images/NS.jpg"/>"
																		width="100px" height="120px" alt="Signature"
																		align="middle" />
																	</c:otherwise>
																</c:choose>
															</div>
															<div id="sigSection" style="display: block">
																<span style="border: none; text-align: center"
																	class="subjectsmall">&nbsp;&nbsp;Signature</span>
																<c:if test="${newRegistration.sign }">
																	<img id="signature"
																		src="data:image/jpg;base64,${newRegistration.sign}"
																		width="300px" height="60px" alt="Signature"
																		align="middle" style="border: 1px SOLID" />
																</c:if>
															</div>
														</td>
													</tr> 
													<tr>
														<td>
															<div id="" style="text-align: center; height: 7px;" ></div>
															<div id="" style="text-align: center;" >
															<c:if test="${newRegistration.transactionType != 'CON'}">
																<input type="button"
																	style="width: 150px; border-radius: 0px;"
																	class="btn_blue gradient" id="userDeclarationPdf"
																	value="View User Declaration" />
															</c:if>
															</div>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2"align="right">
						<table>
							<tr>
								<td colspan="2" valign="top">
									<div align="right">
										<c:if test="${newRegistration.transactionType != 'CON'}">
										<input type="button" style="width: 150px; text-align: center; white-space: normal;"
											class="btn_blue gradient" id="colSlipPdf"
											value="View Collection Slip" />
										</c:if>
									</div>
								</td>
								<td colspan="2" valign="top">
									<div align="right">
										<input type="button" id="close" class="btn_blue gradient"
											style="width: 100px; text-align: center; white-space: normal;"
											onclick="JavaScript: window.close();" value="Close"
											name="closeBtn" />
									</div>
								</td>
							</tr> 
						</table>
					</td>
				</tr>
			</table>
		</div>
		<form:hidden path="sign" name="sign" id="signHidnFeild" />
		<input type="hidden" id="parentURLView" name="parentURLView"
			value="${url}" />
	</form:form>
</div>
<div id="ninDtlsDialog" title="Details" style="width: 500px">
	<table id="ninDtlsTbl" class="displayTable">
		<tr class="evenrowcolor">
			<td class="description" width="200px">Maiden Name:</td>
			<td class='subjectsmall' width="300px"><span
				id="maritalStatus.field.span" style="font-size: 12px">${newRegistration.person.maidenName}</span>
			</td>
		</tr>
		<tr class="oddrowcolor">
			<td class="description" width="200px">Father's Name:</td>
			<td class='subjectsmall' width="300px"><span
				id="maritalStatus.field.span" style="font-size: 12px">${newRegistration.person.fatherName}</span>
			</td>
		</tr>
		<tr class="evenrowcolor">
			<td class="description" width="200px">Father's Surname:</td>
			<td class='subjectsmall'><span id="maritalStatus.field.span"
				style="font-size: 12px">${newRegistration.person.fatherSurname}</span>
			</td>
		</tr>
		<tr class="oddrowcolor">
			<td class="description" width="200px">Mother's Name:</td>
			<td class='subjectsmall' width="300px"><span
				id="maritalStatus.field.span" style="font-size: 12px">${newRegistration.person.motherName}</span>
			</td>
		</tr>
		<tr class="evenrowcolor">
			<td class="description" width="200px">Mother's Surname:</td>
			<td class='subjectsmall' width="300px"><span
				id="maritalStatus.field.span" style="font-size: 12px">${newRegistration.person.motherSurname}</span>
			</td>
		</tr>
	</table>
</div>
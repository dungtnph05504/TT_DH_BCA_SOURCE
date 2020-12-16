<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url var="userDeclarationPdf"
	value="/servlet/transCardStatusQuery/userDeclarationPdf" />
<c:url var="collSlipPdf"
	value="/servlet/transCardStatusQuery/colSlipPdf" />
<script>
	$(function() {
		$("#edtAddressDialog")
		.dialog(
				{
					modal : true,
					autoOpen : false,
					width : 600,
					closeOnEscape : false,
					show : {
						effect : "blind",
						duration : 1000
					},
					hide : {
					//duration : 1000
					},
					buttons : {
						"Ok" : {
						    text: "Ok",								    
						    click: function() {
								$('#address1td span').text(
										$('#address1').val());
								$('#address2td span').text(
										$('#address2').val());
								//EUNIKE START 20130717
								var address3var = $(
										'#address3 option:selected').val();
								if ('NONE' != address3var) {
									$('#address3td span').text(
											$('#address3 option:selected')
													.text());
								} else {
									$('#address3td span').text("");
								}
								var address4var = $(
										'#address4 option:selected').val();
								if ('NONE' != address4var) {
									$('#address4td span').text(
											$('#address4 option:selected')
													.text());
								} else {
									$('#address4td span').text("");
								}
								var address5var = $(
										'#address5 option:selected').val();
								if ('NONE' != address5var) {
									$('#address5td span').text(
											$('#address5 option:selected')
													.text());
								} else {
									$('#address5td span').text("");
								}
								//EUNIKE END
								$('#address6td span').text(
										$('#address6').val());
						
								var address1 = $('#address1').val();
								var address2 = $('#address2').val();
								var address3 = $(
										'#address3 option:selected').text();//$('#address3').val();
								var address4 = $(
										'#address4 option:selected').text();
								var address5 = $(
										'#address5 option:selected').text();
								var address6 = $('#address6').val();
								var district = $('#address5').val();
								var locality = $('#address3').val();
								var town = $('#address4').val();
								//$('#address4').val();
						
								document.getElementById('address1HdnInpt').value = address1;
								document.getElementById('address2HdnInpt').value = address2;
								//EUNIKE START 20130717
								document.getElementById('address3HdnInpt').value = address3;
								document.getElementById('address4HdnInpt').value = address4;
								document.getElementById('address5HdnInpt').value = address5;
								//EUNIKE END
								document.getElementById('address6HdnInpt').value = address6;
								document.getElementById('localityHdnInpt').value = locality;
								document.getElementById('districtHdnInpt').value = district;
								document.getElementById('townHdnInpt').value = town;
								var addressChangeFlag = false;
								var oldAddress3 = $(
										'#oldAddress3HidenField').val();
								if ($.trim(address3) == $.trim(oldAddress3)) {
									$('#address3td').css("color", "black");
								} else {
									$('#address3td')
											.css("color", "#C30943");
									addressChangeFlag = true;
								}
						
								var oldAddress1 = $(
										'#oldAddress1HidenField').val();
								if ($.trim(address1) == $.trim(oldAddress1)) {
									$('#address1td span').css("color",
											"black");
								} else {
									$('#address1td span').css("color",
											"#C30943");
									addressChangeFlag = true;
								}
								var oldAddress2 = $(
										'#oldAddress2HidenField').val();
								if ($.trim(address2) == $.trim(oldAddress2)) {
									$('#address2td span').css("color",
											"black");
								} else {
									$('#address2td span').css("color",
											"#C30943");
									addressChangeFlag = true;
								}
								var oldAddress4 = $(
										'#oldAddress4HidenField').val();
								if ($.trim(address4) == $.trim(oldAddress4)) {
									$('#address4td span').css("color",
											"black");
								} else {
									$('#address4td span').css("color",
											"#C30943");
									addressChangeFlag = true;
								}
								var oldAddress5 = $(
										'#oldAddress5HidenField').val();
								if ($.trim(address5) == $.trim(oldAddress5)) {
									$('#address5td').css("color", "black");
								} else {
									$('#address5td')
											.css("color", "#C30943");
									addressChangeFlag = true;
								}
								var oldAddress6 = $(
										'#oldAddress6HidenField').val();
								if ($.trim(address6) == $.trim(oldAddress6)) {
									$('#address6td').css("color", "black");
								} else {
									$('#address6td')
											.css("color", "#C30943");
									addressChangeFlag = true;
								}
								document
										.getElementById('addressChangeFlagHdnField').value = addressChangeFlag;
						
								$(this).dialog('close');
								callback(addressChangeFlag);
						
							}
						}, "Cancel" : {
						    text: "Cancel",
						    classes:"btn_blue gradient",
						    click: function() {
								$(this).dialog('close');
								callback(false);
							}
						}							
					}
				});
		$("#userDeclarationPdf").click(function() {
			var url = "${userDeclarationPdf}" + "/" + $("#trnasIdSpan").text();
			window.open(url).focus(); 
		});

		$("#colSlipPdf").click(function() { 
			var url = "${collSlipPdf}" + "/" + $("#trnasIdSpan").text();
			window.open(url).focus();
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
										<div id="sub_heading_new">Chi tiết ứng dụng</div>
									</td>
								</tr>
								<tr class="evenrowcolor">
									<td width="150px" class="description">CMND/CCCD:</td>
									<td class='subjectsmall'><span id="nin.field.span"
										style="font-size: 12px">${newRegistration.person.nin}</span></td>
								<tr>
								<tr class="oddrowcolor">
									<td width="150px" class="description">Họ:</td>
									<td class='subjectsmall' width='150px'><span
										id="name.field.span" style="font-size: 12px">${newRegistration.person.surname}</span></td>
								</tr>
								<tr class="evenrowcolor">
									<td width="150px" class="description">Tên:</td>
									<td class='subjectsmall' width='80%'><span
										id="name.field.span" style="font-size: 12px">${newRegistration.person.firstname}
									</span></td>
								</tr>
								<!--<tr class="oddrowcolor">
									<td width="150px" class="description">Surname at Birth:</td>
									<td class='subjectsmall'><span id="marriedName.field.span"
										style="font-size: 12px">${newRegistration.person.surnameBirth}</span>

									</td>
								</tr>-->
								<tr class="evenrowcolor">
									<td class="description">Ngày sinh:</td>
									<td class='subjectsmall'><span id="DOB.field.span"
										style="font-size: 12px">${newRegistration.person.dob}</span></td>
								</tr>
								<tr class="oddrowcolor">
									<td class="description">Giới tính:</td>
									<td class='subjectsmall'><span id="sex.field.span"
										style="font-size: 12px">${newRegistration.person.genderDesc}</span></td>
								</tr>
								<tr class="evenrowcolor">
									<td class="description">Tình trạng hôn nhân:</td>
									<td class='subjectsmall'><span
										id="maritalStatus.field.span" style="font-size: 12px">${newRegistration.person.maritalStatusDesc}</span>

									</td>
								</tr>
							<!--	<tr>
									<td colspan=2 class="description"
										style="font-weight: bold; text-decoration: underline; color: #00008B; height: 20px;">ADDRESS</td>
								</tr>-->
								<tr class="oddrowcolor">
									<td width="150px" class="description">Địa chỉ:</td>
									<td id="address1td"><span id="maritalStatus.field.span"
										style="font-size: 12px">${newRegistration.person.address1}</span>

									</td>
								</tr>
							<!--	<tr class="evenrowcolor">
									<td width="150px" class="description">Street No. and Name:</td>
									<td id="address2td"><span id="maritalStatus.field.span"
										style="font-size: 12px">${newRegistration.person.address2}</span>

									</td>
								</tr>="oddrowcolor">
									<td width="150px" class="description">Locality:</td>
									<td id="address3td" style="width: 450px"><span
										id="maritalStatus.field.span" style="font-size: 12px">${newRegistration.person.address3}</span>

									</td>
								</tr>
								<tr class="evenrowcolor">
									<td width="150px" class="description">Town/Village:</td>
									<td id="address4td"><span id="maritalStatus.field.span"
										style="font-size: 12px">${newRegistration.person.address4}</span>

									</td>
								</tr>
								<tr class="oddrowcolor">
									<td width="150px" class="description">District:</td>
									<td id="address5td"><span id="maritalStatus.field.span"
										style="font-size: 12px">${newRegistration.person.address5}</span>

									</td>
								</tr>
								<tr class="oddrowcolor">
									<td width="150px" class="description">Postcode:</td>
									<td id="address6td"><span id="maritalStatus.field.span"
										style="font-size: 12px">${newRegistration.person.address6}</span>

									</td>
								</tr>-->
								<tr class
								<tr>
									<td style="padding: 5px; width: 180px" align="right"><span
										style="text-align: right; padding: 5px"><input
											type="button" style="width: 150px; white-space: normal;"
											class="btn_blue gradient" id="viewMoreApplDetails"
											value="Xem thêm" /></span></td>
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
										<div id="sub_heading_new" style="height: 20px">Nhận xét</div>
									</td>
								</tr>
								<tr>
									<td style="padding: 5px"><div
											style="height: 60px; width: 615px; overflow-x: hidden">
											<table style="table-layout: fixed; width: 100%">
												<c:forEach var="log"
													items="${newRegistration.transactionLogList}"
													varStatus="status">
													<tr>
														<td width="200px">${log.userId}</td>
														<td width="400px" style="word-wrap: break-word">${log.remarks}</td>
													</tr>

												</c:forEach>
											</table>
										</div></td>
								</tr>
							</table>
						</div>
					</td>
				</tr>

			</table>
			<!--bottom table-->
			<table style="margin-top: 0px">
				<tr>
					<td style="width: 500px"><%@include
							file="/WEB-INF/views/common/fingerprint-registration.jsp"%>
					</td>
					<td valign="top"><table>
							<tr>
								<td valign="top"><%@include
										file="/WEB-INF/views/common/facial-registration.jsp"%></td>
								<td valign="top"><table class="roundedBorder"
										style="margin-left: 5px; margin-top: 0px; margin-left: 0px; width: 395px; height: 236px; background-color: #FFFFC6">
										<tr>
											<td><div id="sub_heading_new">Thông tin tờ khai</div></td>
										</tr>
										<tr>
											<td>
												<table>
													<tr>
														<td style="padding: 5px; width: 150px"><span
															class="subjectsmall" style="align: right; width: 10px"
															id="required">Trung tâm phát hành</span></td>
														<td><span class='subjectsmall'
															style="font-size: 12px">${newRegistration.issueSite}</span>
														</td>
													</tr>
													<tr>
														<td style="padding: 5px"><span class="subjectsmall"
															style="align: right" id="">Số điện thoại</span></td>
														<td><span class='subjectsmall'
															style="font-size: 12px">${newRegistration.phone}</span></td>
													</tr>
													<tr>
														<td style="padding: 5px"><span class="subjectsmall"
															style="align: right" id=""> Địa chỉ Email
																&nbsp;&nbsp;&nbsp;</span></td>
														<td><span class='subjectsmall'
															style="font-size: 12px">${newRegistration.email}</span></td>
													</tr>
													<tr>
														<td colspan="2" style="padding-left: 5px">
															<div id="fpSection" style="display: none;">
																<span style="border: none; text-align: center"
																	class="subjectsmall">&nbsp;&nbsp;Vân tay</span>
																<c:if test="${not empty newRegistration.signFp}">
																	<img id="fingerImg"
																		src="data:image/jpg;base64,${newRegistration.signFp}"
																		width="100px" height="120px" alt="Signature"
																		align="middle" />
																</c:if>
															</div>
															<div id="sigSection" style="display: block">
																<span style="border: none; text-align: center"
																	class="subjectsmall">&nbsp;&nbsp;Chữ ký</span>
																<c:if test="${not empty newRegistration.sign}">
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
																<input type="button"
																	style="width: 150px; border-radius: 0px;"
																	class="btn_blue gradient" id="userDeclarationPdf"
																	value="Xem thông tin tờ khai" />
															</div>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table></td>
							</tr>
						</table></td>
				</tr>
				<tr>
					<td colspan="2"align="right">
						<table>
							<tr>
								<td colspan="2" valign="top">
									<div align="right">
										<input type="button" style="width: 150px; text-align: center; white-space: normal;"
											class="btn_blue gradient" id="colSlipPdf"
											value="Xem biên nhận" />
									</div>
								</td>
								<td colspan="2" valign="top">
									<div align="right">
										<input type="button" id="close" class="btn_blue gradient"
											style="width: 100px; text-align: center; white-space: normal;"
											onclick="JavaScript: window.close();" value="Đóng"
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

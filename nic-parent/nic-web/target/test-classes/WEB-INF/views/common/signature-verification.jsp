<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script>
	var sign;
	var fp;

	$(function() {
		$("#signCapture")
				.click(
						function() {
							$("#confirmSign").attr("disabled", false);
							if ($("#signCapture").val() == "Sign"
									|| $("#signCapture").val() == "Re-Sign") {
								$("#signCapture").val("Re-Sign");
								sign=document.verificationApplet
										.captureSign();								
								document.forms["ricVerificationInfoForm"].signHidnFeild.value = sign;
								document.getElementById("spidSignatureCapture").src = "data:image/jpg;base64,"
										+ sign;
							} else if ($("#signCapture").val() == "Capture Fingerprint"
									|| $("#signCapture").val() == "Re-Capture Fingerprint") {
								$("#signCapture").val("Re-Capture Fingerprint");
								document.verificationApplet
										.captureFingerprint("0");

								document.forms["ricVerificationInfoForm"].signFpHidnFeild.value = document.verificationApplet
										.getCapturedImage();
								fp = document.verificationApplet
										.getCapturedJpgImage();
								document.getElementById("spidSignatureCapture").src = "data:image/jpg;base64,"
										+ fp;
							}
						});
		$("input[name='useFpForSignYN']")
				.change(
						function() {
							if ($('#useFP').is(':checked')) {
								document.getElementById("signCapture").value = "Capture Fingerprint";
								/* document.getElementById("signatureDisplay").src = '<c:url value="/resources/images/No_Image.jpg" />';
								document.getElementById("spidSignatureCapture").src = '<c:url value="/resources/images/No_Image.jpg" />'; */
								document.forms["ricVerificationInfoForm"].useFPHdnField.value = true;
								$("#fpSection").css("display", "block");
								$("#sigSection").css("display", "none");

							} else {
								document.getElementById("signCapture").value = "Sign";
								/* document.getElementById("fingerImg").src = '<c:url value="/resources/images/No_Image.jpg" />';
								document.getElementById("spidSignatureCapture").src = '<c:url value="/resources/images/No_Image.jpg" />'; */
								document.forms["ricVerificationInfoForm"].useFPHdnField.value = false;
								$("#sigSection").css("display", "block");
								$("#fpSection").css("display", "none");
							}
						});
		$('#confirmSign')
				.click(
						function(event) {
							$("#dialog-userDeclaration").dialog("close");
							if ($("#signCapture").val() == "Sign"
									|| $("#signCapture").val() == "Re-Sign") {
								document.getElementById("signatureDisplay").src = "data:image/jpg;base64,"
										+ sign;
							} else if ($("#signCapture").val() == "Capture Fingerprint"
									|| $("#signCapture").val() == "Re-Capture Fingerprint") {
								document.getElementById("fingerImg").src = "data:image/jpg;base64,"
										+ fp;
							}
							userDeclarationFlag = true;

						});
		$('#returnSign').click(function(event) {
			$("#dialog-userDeclaration").dialog("close");

		});
		$("#dialog-userDeclaration")
				.dialog(
						{
							modal : true,
							autoOpen : false,
							width : 800,
							height : 1000,
							closeOnEscape : false,
							show : {
								effect : "blind",
								duration : 1000
							},
							hide : {
								//effect : "explode",
								duration : 1000
							},
							open : function() {
								var address1 = $('#address1td span').text();
								$('#address1UsrDeclrClmn span').text(address1);
								var address2 = $('#address2td span').text();
								$('#address2UsrDeclrClmn span').text(address2);
								var address3 = $('#address3td span').text();
								$('#address3UsrDeclrClmn span').text(address3);
								var address4 = $('#address4td span').text();
								$('#address4UsrDeclrClmn span').text(address4);
								var ricOff = $(this).data('ricOffice');
								var fpEncode = $('#fpEncodeHdnField').val();
								if (fpEncode != '') {
									var n = fpEncode.split(",");
									var finger1 = n[0];
									var fp1 = getFingerImage(finger1);
									document.getElementById("FP1UsrDeclrImg").src = "data:image/jpg;base64,"
											+ fp1;
									var finger2 = n[1];
									var fp2 = getFingerImage(finger2);
									document.getElementById("FP2UsrDeclrImg").src = "data:image/jpg;base64,"
											+ fp2;
									var finger3 = n[2];
									var fp3 = getFingerImage(finger3);
									document.getElementById("FP3UsrDeclrImg").src = "data:image/jpg;base64,"
											+ fp3;
									var finger4 = n[3];
									var fp4 = getFingerImage(finger4);
									document.getElementById("FP4UsrDeclrImg").src = "data:image/jpg;base64,"
											+ fp4;
								}

								$('#ricCollectionSiteValueUsrDeclrClmn span')
										.text(ricOff);
							}

						});
		$("#userDeclaration").click(
				function() {
					var ricOffice = $('#ricOffice option:selected').text();
					$("#dialog-userDeclaration").data('ricOffice', ricOffice)
							.dialog("open");
				});
		function getFingerImage(finger) {
			var fp;
			if (finger == 'FP01') {
				fp = $('#fp1JpgHdn').val();
			} else if (finger == 'FP02') {
				fp = $('#fp2JpgHdn').val();
			} else if (finger == 'FP03') {
				fp = $('#fp3JpgHdn').val();
			} else if (finger == 'FP04') {
				fp = $('#fp4JpgHdn').val();
			} else if (finger == 'FP05') {
				fp = $('#fp5JpgHdn').val();
			} else if (finger == 'FP06') {
				fp = $('#fp6JpgHdn').val();
			} else if (finger == 'FP07') {
				fp = $('#fp7JpgHdn').val();
			} else if (finger == 'FP08') {
				fp = $('#fp8JpgHdn').val();
			} else if (finger == 'FP09') {
				fp = $('#fp9JpgHdn').val();
			} else {
				fp = $('#fp10JpgHdn').val();
			}
			return fp;

		}
	});
</script>

<table class="roundedBorder"
	style="margin-left: 5px; margin-top: 0px; margin-left: 0px; width: 405px; height: 236px; background-color: #FFFFC6">
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
					<td><form:select path="issueSite" id='ricOffice'
							name="issueSite"
							style="font-family: Arial; font-size: 12px; overflow: auto;">
							<form:option value="NONE" label="--- Select ---" />
							<form:options items="${siteCodes}" id="siteCodesOptions" />
						</form:select></td>
				</tr>
				<tr>
					<td style="padding: 5px"><span class="subjectsmall"
						style="align: right" id="">Contact Number</span></td>
					<td><form:input path="phone" name="phone" id="phoneInptTxt" /></td>
				</tr>
				<tr>
					<td style="padding: 5px"><span class="subjectsmall"
						style="align: right" id="">Email Address&nbsp;&nbsp;&nbsp;</span></td>
					<td><form:input path="email" name="email" id="emailInptTxt" /></td>
				</tr>
				<tr>
					<td colspan="2" style="padding-left: 5px">

						<div id="fpSection" style="display: none;">
							<span style="border: none; text-align: center"
								class="subjectsmall">&nbsp;&nbsp;Fingerprint</span> <img
								id="fingerImg"
								src="<c:url value="/resources/images/No_Image.jpg" />"
								width="80px" height="80px"
								style="border: 1px SOLID; align: left" alt="BERT CERT"
								align="middle" />
						</div>


						<div id="sigSection" style="display: block">
							<span style="border: none; text-align: center"
								class="subjectsmall">&nbsp;&nbsp;Signature</span><img
								id="signatureDisplay"
								src="<c:url value="/resources/images/No_Image.jpg" />"
								width="300px" height="60px" align="middle"
								style="border: 1px SOLID" />
						</div>
					</td>
				</tr>
				<tr>
					<td style="padding: 5px"><span id="useFPsection"
						class="subjectsmall"><form:checkbox path="useFpForSignYN"
								name="useFpForSignYN" id="useFP" /> Use Fingerprint </span></td>
					<td style="padding-right: 18px; padding-bottom: 5px"><div
							id="" style="text-align: right">
							<input type="button" style="width: 100px; border-radius: 0px"
								class="btn_blue gradient" id="userDeclaration"
								value="Declaration" />
						</div></td>
				</tr>

			</table>
		</td>
	</tr>
</table>
<div id="dialog-userDeclaration" title="User Declaration">

	<table>
		<tr>

			<td id="sub_heading" style="width: 750px" colspan='5'>Application
				Details</td>
			<td></td>

			<!--<td id="sub_heading" colspan="5">4.Facial </td>-->

		</tr>
		<tr>
			<td colspan='2'>
				<table class="innerTable">
					<tr>
						<td width='25%' class="subjectsmall">NIN</td>
						<td class='description' id="ninUsrDeclrClmn"><span>${newRegistration.person.nin}</span></td>

					</tr>
					<!-- particularsGeneral session -->
					<tr>
						<td width='25%' class="subjectsmall">Surname</td>
						<td class='description' width='150px' id="surnameUsrDeclrClmn"><span>${newRegistration.person.surname}</span>
							<input id="name.field" name="bioData.nameToDisplay"
							class="defaultText" type="text" value="ROSHAN" size="50"
							maxlength="68" style="display: none;" disabled /></td>

					</tr>

					<tr>
						<td width='25%' class="subjectsmall">First Name</td>
						<td class='description' width='450px' id="nameUsrDeclrClmn"><span>${newRegistration.person.firstname}</span>
							<input id="nameOnCard.field" name="bioData.nameOnCard"
							class="defaultText" type="text" value="RAMESSUR" size="50"
							maxlength="68" style="display: none;" disabled /></td>

					</tr>

					<tr>
						<td width='25%' class="subjectsmall">Surname at Birth</td>
						<td class='description' id="surnameBirthUsrDeclrClmn"><span>${newRegistration.person.surnameBirth}</span>
							<input id="marriedName.field" class="defaultText" type="text"
							value="ROSHAN" size="50" maxlength="68" style="display: none;"
							disabled /></td>

					</tr>

					<tr>
						<td class="subjectsmall">D.O.B</td>
						<td class='description' id="dobUsrDeclrClmn"><span>${newRegistration.person.dob}</span></td>

					</tr>

					<tr>
						<td class="subjectsmall">Gender</td>
						<td class='description' width='50px' align="left"
							id="sexUsrDeclrClmn"><span>${newRegistration.person.gender}</span></td>

					</tr>


					<tr>
						<td class="subjectsmall">Marital &nbsp;Status</td>
						<td class='description' width='50px' align="left"
							id="address1UsrDeclrClmn"><span>${newRegistration.person.maritalStatus}</span></td>

					</tr>


					<tr>
						<td width="40%" class="subjectsmall">Address
							1(FlatNo,Building Name)</td>
						<td id="address1UsrDeclrClmn" colspan="3" class="description"
							align="left"><span></span></td>
					</tr>
					<tr>
						<td class="subjectsmall">Address 2(Street No and Name)</td>
						<td id="address2UsrDeclrClmn" colspan="3" class="description"
							align="left"><span></span></td>
					</tr>
					<tr>
						<td class="subjectsmall">Address 3</td>
						<td id="address3UsrDeclrClmn" colspan="3" class="description"
							align="left"><span></span></td>
					</tr>
					<tr>
						<td class="subjectsmall">Address 4(Town/District/Village)</td>
						<td id="address4UsrDeclrClmn" colspan="3" class="description"
							align="left"><span></span></td>
					</tr>

					<tr>
						<td class="subjectsmall" id="AddressEditedLabel"
							style="display: none;">Address Edited?</td>
						<td id="addressEditedFlagUsrDeclrClmn" colspan="3"
							class="description" align="left" style="display: none;"><span></span>
						</td>
					</tr>
					<tr>
						<td class="subjectsmall" id="ricCollectionSite"
							style="display: table-cell;">RIC Collection Office</td>
						<td id="ricCollectionSiteValueUsrDeclrClmn" class=description
							align=left style="display: table-cell;"><span>${newRegistration.issueSite}</span></td>
					</tr>
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
				</table>
			</td>

			<td colspan='2' style="float: left;">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<div id="facial" style="height: 300px;">

					<div id="facialGray">
						<p>
							<img id="imgGrayUsrDeclrImg"
								src="data:image/jpg;base64,${newRegistration.printedFace.face}"
								width="150" height="200" alt="PrintedFace" align="middle" />
						</p>
					</div>
				</div>

			</td>
		</tr>
	</table>
	<table>
		<!-- Fingerprint -->
		<tr>
			<td id="sub_heading" class="fpHeadingClass" style="width: 700px">Fingerprint
				- Encoded in Card</td>
		</tr>
		<tr>
			<td>
				<table class="innerTable" style="border: 0px;">
					<tr>
						<td style="width: 10%; text-align: center; border: none;"><span
							style="font-size: 13px; font-weight: bold;">Fingerprint</span></td>
						<td style="width: 10%; text-align: center; border: none;"><span
							style="width: 50%; border: none;"><img id="FP1UsrDeclrImg"
								src="<c:url value="/resources/images/No_Image.jpg" />"
								width="113px" height="130px" align="middle" /></span></td>
						<td style="width: 10%; text-align: center; border: none;"><span
							style="width: 50%; border: none;"><img id="FP2UsrDeclrImg"
								src="<c:url value="/resources/images/No_Image.jpg" />"
								width="113px" height="130px" align="middle" /></span></td>
						<td style="width: 10%; text-align: center; border: none;"><span
							style="width: 50%; border: none;"><img id="FP3UsrDeclrImg"
								src="<c:url value="/resources/images/No_Image.jpg" />"
								width="113px" height="130px" align="middle" /></span></td>
						<td style="width: 10%; text-align: center; border: none;"><span
							style="width: 50%; border: none;"><img id="FP4UsrDeclrImg"
								src="<c:url value="/resources/images/No_Image.jpg" />"
								width="113px" height="130px" align="middle" /></span></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table>
		<tr>
			<td class=subjectsmall>${usrDeclarationStatement}<br />${usrDeclarationStatement1}
			</td>
		</tr>
		<tr>
			<td>
				<div>
					<p>
						<img src="<c:url value="/resources/images/No_Image.jpg" />"
							id="spidSignatureCapture"
							style="display: block; height: 200px; width: 400px;">

					</p>
				</div>
			</td>
		</tr>
		<tr>
			<td><input class="btn_blue gradient" type="button"
				id="signCapture" value="Sign" style="align: right"> <input
				class="btn_blue gradient" type="button" id="confirmSign"
				value="Confirm" style="align: right" disabled="disabled"> <input
				class="btn_blue gradient" type="button" id="returnSign"
				value="Cancel" style="align: right"></td>
		<tr>
	</table>
	<script>
									var useFpVal = '${newRegistration.useFpForSignYN}';									
									//alert("useFpVal >>" + useFpVal);
									document
									.getElementById("fingerImg").src = '<c:url value="/resources/images/No_Image.jpg" />';
									document
									.getElementById("signatureDisplay").src = '<c:url value="/resources/images/No_Image.jpg" />';
									if (useFpVal == 'true') {
										//var signfpvar =$('#signFpHidnFeild').val();
										var signfpvar = document.forms["ricVerificationInfoForm"].signFpHidnFeild.value;
										if (signfpvar != '') {
											document
													.getElementById("fingerImg").src = "data:image/jpg;base64,"
													+ signfpvar;
											document
													.getElementById("spidSignatureCapture").src = "data:image/jpg;base64,"
													+ signfpvar;
											$("#fpSection").css("display",
													"block");
											$("#sigSection").css("display",
													"none");
										}
									} else {
										//var signvar =$('#signHidnFeild').val();
										//alert(document.forms["ricVerificationInfoForm"].signHidnFeild.value);
										var signvar = document.forms["ricVerificationInfoForm"].signHidnFeild.value;
										if (signvar != null && signvar != '') {
											document
													.getElementById("signatureDisplay").src = "data:image/jpg;base64,"
													+ signvar;
											document
													.getElementById("spidSignatureCapture").src = "data:image/jpg;base64,"
													+ signvar;
											$("#fpSection").css("display",
													"none");
											$("#sigSection").css("display",
													"block");
										}

									}
								</script>
</div>
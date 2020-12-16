<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url var="populateLocality" value="/servlet/registration/formAddress3"></c:url>
<script type="text/javascript">
	$(function() {
		$('#address4').change(function() {
			var town = $("#address4 option:selected").val();
			$.fn.formAddress3Fn(town);

		});
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
		$.fn.formAddress3Fn = function(docname) {
			formAddress3(docname);
		};
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
								'Ok' : function() {
									$('#address1td span').text(
											$('#address1').val());
									$('#address2td span').text(
											$('#address2').val());
									$('#address3td span').text($('#address3 option:selected').text());
									$('#address4td span').text($('#address4 option:selected').text());

									var address1 = $('#address1').val();
									var address2 = $('#address2').val();
									var address3 = $('#address3 option:selected').text();//$('#address3').val();
									var address4 = $('#address4 option:selected').text();
									var district = $('#address4').val();
									var locality = $('#address3').val();
									//$('#address4').val();
									
									document.getElementById('address1HdnInpt').value = address1;
									document.getElementById('address2HdnInpt').value = address2;
									document.getElementById('address3HdnInpt').value = address3;
									document.getElementById('address4HdnInpt').value = address4;
									document.getElementById('localityHdnInpt').value = locality;
									document.getElementById('districtHdnInpt').value = district;
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
									document.getElementById('addressChangeFlagHdnField').value = addressChangeFlag;

									$(this).dialog('close');
									callback(addressChangeFlag);

								},
								'Cancel' : function() {
									$(this).dialog('close');
									callback(false);
								}
							}
						});
		$("#editAddress").click(function() {
			$("#edtAddressDialog").dialog("open");

		});

	});
	function formAddress3(town) {
		var url = "${populateLocality}/" + town;
		$.ajax({
			type : "POST",
			url : url,
			success : function(data) {
				var options = '';
				 $.each(data, function(key, value) {
					 options += '<option value="' + key + '">' +value
						+ '</option>';
		            });
				/* for ( var i = 0; i < data.size; i++) {
					alert(data.length);
					alert(data[i].key+"======="+data[i].value);
					options += '<option value="' + data[i].key + '">' + data[i].value
							+ '</option>';
				} */
				$("select#address3").html(options);
			}
		});
	}
	function callback(value) {
		if (value == true) {
			var d = new Date();
			var today = '';
			var m_names = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun",
					"Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
			var curr_date = d.getDate();
			var curr_month = d.getMonth();
			var curr_year = d.getFullYear();
			var hour = d.getHours().toString();
			var min = d.getMinutes().toString();
			today = curr_date + "-" + m_names[curr_month] + "-" + curr_year
					+ " " + hour + ":" + min;
			var officer = 'Officer A';/*Get logged in user in real time */
			$('#addressEditedFlag span').text('by ' + officer + ' on ' + today);
			document.getElementById("addressEditedFlag").style.display = "table-cell";
			document.getElementById("AddressEditedLabel").style.display = "table-cell";
			document.getElementById("loggedInUsrHdnInpt").value = officer;
			document.getElementById("updtTimeHdnInpt").value = today;			
		} else {
			document.getElementById("addressEditedFlag").style.display = "none";
			document.getElementById("AddressEditedLabel").style.display = "none";
			document.getElementById("loggedInUsrHdnInpt").value = null;
			document.getElementById("updtTimeHdnInpt").value = null;

		}
	}
</script>
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
		<td class='subjectsmall' width='150px'><span id="name.field.span"
			style="font-size: 12px">${newRegistration.person.surname}</span></td>
	</tr>
	<tr class="evenrowcolor">
		<td width="150px" class="description">First Name:</td>
		<td class='subjectsmall' width='80%'><span id="name.field.span"
			style="font-size: 12px">${newRegistration.person.firstname} </span></td>
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
			style="font-size: 12px">${newRegistration.person.gender}</span></td>
	</tr>
	<tr class="evenrowcolor">
		<td class="description">Marital &nbsp;Status:</td>
		<td class='subjectsmall'><span id="maritalStatus.field.span"
			style="font-size: 12px">${newRegistration.person.maritalStatus}</span>

		</td>
	</tr>
	<tr>
		<td colspan=2>
			<table class="">
				<c:choose>
					<c:when test="${isUpdate == 'Y' || isUpdateVerify == 'Y'}">
						<tr>					
							<td class="description" style="font-weight: bold; text-decoration: underline; color: #00008B; height: 20px;">ADDRESS</td>
							<td class="description" style="font-weight: bold; text-decoration: underline; color: #00008B; height: 20px;">
								<input type="button" style="width: 150px; white-space: normal;"	class="btn_blue gradient" id="editAddress"	value="Edit Address" />
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>					
							<td colspan=2 class="description" style="font-weight: bold; text-decoration: underline; color: #00008B; height: 20px;">ADDRESS</td>
						</tr>
					</c:otherwise>	
				</c:choose>	
				
				<tr class="oddrowcolor">
					<td width="150px" class="description">Flat No,Building Name:</td>
					<td id="address1td"><span id="maritalStatus.field.span"
						style="font-size: 12px">${newRegistration.person.address1}</span>

					</td>
				</tr>
				<tr class="oddrowcolor">
					<td width="150px" class="description">Street No and Name:</td>
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
				<tr class="oddrowcolor">
					<td width="150px" class="description">Town/District/Village:</td>
					<td id="address4td"><span id="maritalStatus.field.span"
						style="font-size: 12px">${newRegistration.person.address4}</span>

					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr class="evenrowcolor">
		<c:choose>
			<c:when test="${newRegistration.addressChangeFlag == false}">
				<td class="subjectsmall" id="AddressEditedLabel"
					style="display: none;">Address Edited?</td>
				<td id="addressEditedFlag" colspan="3" class=description align=left
					style="display: none;"><span id="addressEditedText.span"></span>
				</td>
			</c:when>
			<c:otherwise>
				<td class="subjectsmall" id="AddressEditedLabel">Address
					Edited?</td>
				<td id="addressEditedFlag" colspan="3" class=description align=left><span
					id="addressEditedText.span">by ${newRegistration.updatedBy}
						on ${newRegistration.updateTime}</span></td>
			</c:otherwise>
		</c:choose>

	</tr>
	<tr>
		<td colspan=2>
			<table style="width: 600px">
				<tr>
					<c:choose>
						<c:when test="${isUpdate == 'Y'  || isUpdateVerify == 'Y'}">
							<td style="width: 200px;">
								<input type="button" style="width: 100px; white-space: normal;float: left;"	class="btn_blue gradient" id="updateVerifyFp"	value="Verify FP" />
								<p style="padding: 0px 5px 0px 0px;">
									<c:choose>
										<c:when test="${newRegistration.fpVerified==true}">
											<img id="verifyFPImage" src="<c:url value="/images/new_thumbs_up.jpg"/>" class='radioButtonImage' style='width: 30px; height: 23px' />
										</c:when>
										<c:otherwise>
											<img id="verifyFPImage" src="<c:url value="/images/new_thumbs_down.jpg"/>" class='radioButtonImage' style='width: 30px; height: 23px' />
										</c:otherwise>									
									</c:choose>									
								</p>
							</td>
							<td style="width: 200px;">
								<input type="button" style="width: 100px; white-space: normal;float: left;"	class="btn_blue gradient" id="updateVerifyCard"	value="Verify Card" />
								<p style="padding: 0px 5px 0px 0px;">
								<c:choose>
										<c:when test="${newRegistration.cardVerified==true}">
											<img id="verifyCardImage" src="<c:url value="/images/new_thumbs_up.jpg"/>" class='radioButtonImage' style='width: 30px; height: 23px' />
											<script type="text/javascript">
												$("#cardDetails").attr("disabled", false).addClass("btn_blue");
											</script>
										</c:when>
										<c:otherwise>
											<img id="verifyCardImage" src="<c:url value="/images/new_thumbs_down.jpg"/>" class='radioButtonImage' style='width: 30px; height: 23px' />
											<script type="text/javascript">
												$("#cardDetails").attr("disabled", true);
											</script>
										</c:otherwise>									
									</c:choose>	
									
								</p>
							</td>
						</c:when>
						<c:otherwise>
							<td>
								<div></div>
							</td>
							<td>
								<div></div>
							</td>
						</c:otherwise>
					</c:choose>
						
					
					<td style="padding: 5px; width: 150px"><span
						style="text-align: right; padding: 5px"><c:choose>
								<c:when test="${newRegistration.comingFromRegistration==true || isUpdateConfirm == 'Y'}">
									<input type="button"
										style="width: 150px; white-space: normal; display: none;"
										class="btn_blue gradient" id="editAddress"
										value="Edit Address" />
								</c:when>
								<c:when test="${isUpdate == 'Y' || isUpdateVerify == 'Y'}">
									<input type="button"
										style="width: 100px; white-space: normal;"
										class="btn_blue gradient" id="cardDetails" disabled
										value="Card Details" />
								</c:when>
								<c:otherwise>
									<input type="button" style="width: 150px; white-space: normal;"
										class="btn_blue gradient" id="editAddress"
										value="Edit Address" />
								</c:otherwise>
							</c:choose></span>
					</td>

					<td style="padding: 5px; width: 180px"><span
						style="text-align: right; padding: 5px"><input
							type="button" style="width: 150px; white-space: normal;"
							class="btn_blue gradient" id="viewMoreApplDetails"
							value="More Person Details" /></span></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<div id="edtAddressDialog" title="Edit Address" style="width: 600px">
	<table id="edtAddressTbl" class="displayTable">
		<tr class="evenrowcolor">
			<td class="description" width="200px">Address1</td>
			<td class='subjectsmall' width="300px"><form:textarea
					class="addressFields"
					style="font-family: Arial; font-size: 12px; overflow: auto;"
					id="address1" name="addresstextbox1" cols="53" rows="2"
					maxlength='100' path="person.address1"></form:textarea></td>
		</tr>
		<tr class="oddrowcolor">
			<td class="description" width="200px">Address2</td>
			<td class='subjectsmall' width="300px"><form:textarea
					class="addressFields"
					style="font-family: Arial; font-size: 12px; overflow: auto;"
					id="address2" name="addresstextbox2" cols="53" rows="2"
					maxlength='100' path="person.address2"></form:textarea></td>
		</tr>
		<tr class="evenrowcolor">
			<td class="description" width="200px">Address3</td>
			<td class='subjectsmall'><form:select path="locality"
					id="address3" name="locality" class="addressFields">
					<form:option value="NONE" label="--- Select ---" />
					<form:options items="${address3List}" id="address3Options" />
				</form:select></td>
		</tr>
		<tr class="oddrowcolor">
			<td class="description" width="200px">Address4</td>
			<td class='subjectsmall' width="300px"><form:select
					path="district" id="address4" name="district"
					class="addressFields">
					<form:option value="NONE" label="--- Select ---" />
					<form:options items="${address4List}" />
				</form:select></td>
		</tr>
	</table>
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
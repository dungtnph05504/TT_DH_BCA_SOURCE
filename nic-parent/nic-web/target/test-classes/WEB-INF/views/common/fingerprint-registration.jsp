<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script>
	$(function() {

		$("#dialog-FPFullView").dialog({
			modal : true,
			autoOpen : false,
			width : 1200,
			height : 600,
			closeOnEscape : false,
			show : {
				effect : "blind",
				duration : 1000
			},
			hide : {
				//effect : "explode",
				duration : 1000
			}
		});
		$("#viewFPFull").click(function() {
			$("#dialog-FPFullView").dialog("open");
		});
		$('#VerifyFP')
				.click(
						function() {
							if ($("#VerifyFP").val() == "Verify") {
								$("#VerifyFP").val("ReVerify");
							}
							$('.modal').show();
							var resValue = document.registrationApplet
									.captureTenprint();
							if (resValue == 0) {
								var matchScore1 = document.registrationApplet
										.verifyFingerprint("1",
												document.forms["ricTransactionInfoForm"].fp1MnuTemplateHdn.value,
												document.registrationApplet
														.getFinger1MNUTemplate());
								document.forms["ricTransactionInfoForm"].fp1MatchScoreHdn.value = matchScore1;
								setVerifyInfo(matchScore1, 1);
								var matchScore2 = document.registrationApplet
										.verifyFingerprint("2",
												document.forms["ricTransactionInfoForm"].fp2MnuTemplateHdn.value,
												document.registrationApplet
														.getFinger2MNUTemplate());
								document.forms["ricTransactionInfoForm"].fp2MatchScoreHdn.value = matchScore2;
								setVerifyInfo(matchScore2, 2);
								var matchScore3 = document.registrationApplet
										.verifyFingerprint("3",
												document.forms["ricTransactionInfoForm"].fp3MnuTemplateHdn.value,
												document.registrationApplet
														.getFinger3MNUTemplate());
								document.forms["ricTransactionInfoForm"].fp3MatchScoreHdn.value = matchScore3;
								setVerifyInfo(matchScore3, 3);
								var matchScore4 = document.registrationApplet
										.verifyFingerprint("4",
												document.forms["ricTransactionInfoForm"].fp4MnuTemplateHdn.value,
												document.registrationApplet
														.getFinger4MNUTemplate());
								document.forms["ricTransactionInfoForm"].fp4MatchScoreHdn.value = matchScore4;
								setVerifyInfo(matchScore4, 4);
								var matchScore5 = document.registrationApplet
										.verifyFingerprint("5",
												document.forms["ricTransactionInfoForm"].fp5MnuTemplateHdn.value,
												document.registrationApplet
														.getFinger5MNUTemplate());
								document.forms["ricTransactionInfoForm"].fp5MatchScoreHdn.value = matchScore5;
								setVerifyInfo(matchScore5, 5);
								var matchScore6 = document.registrationApplet
										.verifyFingerprint("6",
												document.forms["ricTransactionInfoForm"].fp6MnuTemplateHdn.value,
												document.registrationApplet
														.getFinger6MNUTemplate());
								document.forms["ricTransactionInfoForm"].fp6MatchScoreHdn.value = matchScore6;
								setVerifyInfo(matchScore6, 6);
								var matchScore7 = document.registrationApplet
										.verifyFingerprint("7",
												document.forms["ricTransactionInfoForm"].fp7MnuTemplateHdn.value,
												document.registrationApplet
														.getFinger7MNUTemplate());
								document.forms["ricTransactionInfoForm"].fp7MatchScoreHdn.value = matchScore7;
								setVerifyInfo(matchScore7, 7);
								var matchScore8 = document.registrationApplet
										.verifyFingerprint("8",
												document.forms["ricTransactionInfoForm"].fp8MnuTemplateHdn.value,
												document.registrationApplet
														.getFinger8MNUTemplate());
								document.forms["ricTransactionInfoForm"].fp8MatchScoreHdn.value = matchScore8;
								setVerifyInfo(matchScore8, 8);
								var matchScore9 = document.registrationApplet
										.verifyFingerprint("9",
												document.forms["ricTransactionInfoForm"].fp9MnuTemplateHdn.value,
												document.registrationApplet
														.getFinger9MNUTemplate());
								document.forms["ricTransactionInfoForm"].fp9MatchScoreHdn.value = matchScore9;
								setVerifyInfo(matchScore9, 9);
								var matchScore10 = document.registrationApplet
										.verifyFingerprint("10",
												document.forms["ricTransactionInfoForm"].fp10MnuTemplateHdn.value,
												document.registrationApplet
														.getFinger10MNUTemplate());
								document.forms["ricTransactionInfoForm"].fp10MatchScoreHdn.value = matchScore10;
								setVerifyInfo(matchScore10, 10);
								document.forms["ricTransactionInfoForm"].verifyFinger1WsqHdnInpt.value = document.registrationApplet
										.getFinger1WSQValue();
								document.forms["ricTransactionInfoForm"].verifyFinger2WsqHdnInpt.value = document.registrationApplet
										.getFinger2WSQValue();
								document.forms["ricTransactionInfoForm"].verifyFinger3WsqHdnInpt.value = document.registrationApplet
										.getFinger3WSQValue();
								document.forms["ricTransactionInfoForm"].verifyFinger4WsqHdnInpt.value = document.registrationApplet
										.getFinger4WSQValue();
								document.forms["ricTransactionInfoForm"].verifyFinger5WsqHdnInpt.value = document.registrationApplet
										.getFinger5WSQValue();
								document.forms["ricTransactionInfoForm"].verifyFinger6WsqHdnInpt.value = document.registrationApplet
										.getFinger6WSQValue();
								document.forms["ricTransactionInfoForm"].verifyFinger7WsqHdnInpt.value = document.registrationApplet
										.getFinger7WSQValue();
								document.forms["ricTransactionInfoForm"].verifyFinger8WsqHdnInpt.value = document.registrationApplet
										.getFinger8WSQValue();
								document.forms["ricTransactionInfoForm"].verifyFinger9WsqHdnInpt.value = document.registrationApplet
										.getFinger9WSQValue();
								document.forms["ricTransactionInfoForm"].verifyFinger10WsqHdnInpt.value = document.registrationApplet
										.getFinger10WSQValue();

								document.forms["ricTransactionInfoForm"].verifyFinger1StatusHdnInpt.value = document.registrationApplet
										.getFinger1Quality();
								document.forms["ricTransactionInfoForm"].verifyFinger2StatusHdnInpt.value = document.registrationApplet
										.getFinger2Quality();
								document.forms["ricTransactionInfoForm"].verifyFinger3StatusHdnInpt.value = document.registrationApplet
										.getFinger3Quality();
								document.forms["ricTransactionInfoForm"].verifyFinger4StatusHdnInpt.value = document.registrationApplet
										.getFinger4Quality();
								document.forms["ricTransactionInfoForm"].verifyFinger5StatusHdnInpt.value = document.registrationApplet
										.getFinger5Quality();
								document.forms["ricTransactionInfoForm"].verifyFinger6StatusHdnInpt.value = document.registrationApplet
										.getFinger6Quality();
								document.forms["ricTransactionInfoForm"].verifyFinger7StatusHdnInpt.value = document.registrationApplet
										.getFinger7Quality();
								document.forms["ricTransactionInfoForm"].verifyFinger8StatusHdnInpt.value = document.registrationApplet
										.getFinger8Quality();
								document.forms["ricTransactionInfoForm"].verifyFinger9StatusHdnInpt.value = document.registrationApplet
										.getFinger9Quality();
								document.forms["ricTransactionInfoForm"].verifyFinger10StatusHdnInpt.value = document.registrationApplet
										.getFinger10Quality();
								$('.modal').hide();

							} else {
								alert('Some Problem with capturing fingerprints. Please try again');
								$('.modal').hide();
							}
							return false;
						});

		$('#captureFP')
				.click(
						function() {
							if ($("#captureFP").val() == "Capture") {
								$("#captureFP").val("Recapture");
							}
							$('.modal').show();
							var resValue = document.registrationApplet
									.captureTenprint();
							if (resValue == 0) {
								setDefaultFP();
								document.forms["ricTransactionInfoForm"].fp1MnuTemplateHdn.value = document.registrationApplet
										.getFinger1MNUTemplate();
								document.forms["ricTransactionInfoForm"].fp2MnuTemplateHdn.value = document.registrationApplet
										.getFinger2MNUTemplate();
								document.forms["ricTransactionInfoForm"].fp3MnuTemplateHdn.value = document.registrationApplet
										.getFinger3MNUTemplate();
								document.forms["ricTransactionInfoForm"].fp4MnuTemplateHdn.value = document.registrationApplet
										.getFinger4MNUTemplate();
								document.forms["ricTransactionInfoForm"].fp5MnuTemplateHdn.value = document.registrationApplet
										.getFinger5MNUTemplate();
								document.forms["ricTransactionInfoForm"].fp6MnuTemplateHdn.value = document.registrationApplet
										.getFinger6MNUTemplate();
								document.forms["ricTransactionInfoForm"].fp7MnuTemplateHdn.value = document.registrationApplet
										.getFinger7MNUTemplate();
								document.forms["ricTransactionInfoForm"].fp8MnuTemplateHdn.value = document.registrationApplet
										.getFinger8MNUTemplate();
								document.forms["ricTransactionInfoForm"].fp9MnuTemplateHdn.value = document.registrationApplet
										.getFinger9MNUTemplate();
								document.forms["ricTransactionInfoForm"].fp10MnuTemplateHdn.value = document.registrationApplet
										.getFinger10MNUTemplate();
								document.forms["ricTransactionInfoForm"].finger1WsqHdnInpt.value = document.registrationApplet
										.getFinger1WSQValue();
								document.forms["ricTransactionInfoForm"].finger2WsqHdnInpt.value = document.registrationApplet
										.getFinger2WSQValue();
								document.forms["ricTransactionInfoForm"].finger3WsqHdnInpt.value = document.registrationApplet
										.getFinger3WSQValue();
								document.forms["ricTransactionInfoForm"].finger4WsqHdnInpt.value = document.registrationApplet
										.getFinger4WSQValue();
								document.forms["ricTransactionInfoForm"].finger5WsqHdnInpt.value = document.registrationApplet
										.getFinger5WSQValue();
								document.forms["ricTransactionInfoForm"].finger6WsqHdnInpt.value = document.registrationApplet
										.getFinger6WSQValue();
								document.forms["ricTransactionInfoForm"].finger7WsqHdnInpt.value = document.registrationApplet
										.getFinger7WSQValue();
								document.forms["ricTransactionInfoForm"].finger8WsqHdnInpt.value = document.registrationApplet
										.getFinger8WSQValue();
								document.forms["ricTransactionInfoForm"].finger9WsqHdnInpt.value = document.registrationApplet
										.getFinger9WSQValue();
								document.forms["ricTransactionInfoForm"].finger10WsqHdnInpt.value = document.registrationApplet
										.getFinger10WSQValue();

								document.forms["ricTransactionInfoForm"].finger1StatusHdnInpt.value = document.registrationApplet
										.getFinger1Quality();
								document.forms["ricTransactionInfoForm"].finger2StatusHdnInpt.value = document.registrationApplet
										.getFinger2Quality();
								document.forms["ricTransactionInfoForm"].finger3StatusHdnInpt.value = document.registrationApplet
										.getFinger3Quality();
								document.forms["ricTransactionInfoForm"].finger4StatusHdnInpt.value = document.registrationApplet
										.getFinger4Quality();
								document.forms["ricTransactionInfoForm"].finger5StatusHdnInpt.value = document.registrationApplet
										.getFinger5Quality();
								document.forms["ricTransactionInfoForm"].finger6StatusHdnInpt.value = document.registrationApplet
										.getFinger6Quality();
								document.forms["ricTransactionInfoForm"].finger7StatusHdnInpt.value = document.registrationApplet
										.getFinger7Quality();
								document.forms["ricTransactionInfoForm"].finger8StatusHdnInpt.value = document.registrationApplet
										.getFinger8Quality();
								document.forms["ricTransactionInfoForm"].finger9StatusHdnInpt.value = document.registrationApplet
										.getFinger9Quality();
								document.forms["ricTransactionInfoForm"].finger10StatusHdnInpt.value = document.registrationApplet
										.getFinger10Quality();

								document.forms["ricTransactionInfoForm"].finger1IndicatorHdnInpt.value = document.registrationApplet
										.getFinger1Status();
								document.forms["ricTransactionInfoForm"].finger2IndicatorHdnInpt.value = document.registrationApplet
										.getFinger2Status();
								document.forms["ricTransactionInfoForm"].finger3IndicatorHdnInpt.value = document.registrationApplet
										.getFinger3Status();
								document.forms["ricTransactionInfoForm"].finger4IndicatorHdnInpt.value = document.registrationApplet
										.getFinger4Status();
								document.forms["ricTransactionInfoForm"].finger5IndicatorHdnInpt.value = document.registrationApplet
										.getFinger5Status();
								document.forms["ricTransactionInfoForm"].finger6IndicatorHdnInpt.value = document.registrationApplet
										.getFinger6Status();
								document.forms["ricTransactionInfoForm"].finger7IndicatorHdnInpt.value = document.registrationApplet
										.getFinger7Status();
								document.forms["ricTransactionInfoForm"].finger8IndicatorHdnInpt.value = document.registrationApplet
										.getFinger8Status();
								document.forms["ricTransactionInfoForm"].finger9IndicatorHdnInpt.value = document.registrationApplet
										.getFinger9Status();
								document.forms["ricTransactionInfoForm"].finger10IndicatorHdnInpt.value = document.registrationApplet
										.getFinger10Status();

								document.forms["ricTransactionInfoForm"].fp1JpgHdn.value = document.registrationApplet
										.getFinger1jpgValue();
								document.forms["ricTransactionInfoForm"].fp2JpgHdn.value = document.registrationApplet
										.getFinger2jpgValue();

								document.forms["ricTransactionInfoForm"].fp3JpgHdn.value = document.registrationApplet
										.getFinger3jpgValue();

								document.forms["ricTransactionInfoForm"].fp4JpgHdn.value = document.registrationApplet
										.getFinger4jpgValue();

								document.forms["ricTransactionInfoForm"].fp5JpgHdn.value = document.registrationApplet
										.getFinger5jpgValue();

								document.forms["ricTransactionInfoForm"].fp6JpgHdn.value = document.registrationApplet
										.getFinger6jpgValue();

								document.forms["ricTransactionInfoForm"].fp7JpgHdn.value = document.registrationApplet
										.getFinger7jpgValue();

								document.forms["ricTransactionInfoForm"].fp8JpgHdn.value = document.registrationApplet
										.getFinger8jpgValue();

								document.forms["ricTransactionInfoForm"].fp9JpgHdn.value = document.registrationApplet
										.getFinger9jpgValue();

								document.forms["ricTransactionInfoForm"].fp10JpgHdn.value = document.registrationApplet
										.getFinger10jpgValue();

								setQualityInfo(document.registrationApplet
										.getFinger1Quality(), 1);
								setQualityInfo(document.registrationApplet
										.getFinger2Quality(), 2);
								setQualityInfo(document.registrationApplet
										.getFinger3Quality(), 3);
								setQualityInfo(document.registrationApplet
										.getFinger4Quality(), 4);
								setQualityInfo(document.registrationApplet
										.getFinger5Quality(), 5);
								setQualityInfo(document.registrationApplet
										.getFinger6Quality(), 6);
								setQualityInfo(document.registrationApplet
										.getFinger7Quality(), 7);
								setQualityInfo(document.registrationApplet
										.getFinger8Quality(), 8);
								setQualityInfo(document.registrationApplet
										.getFinger9Quality(), 9);
								setQualityInfo(document.registrationApplet
										.getFinger10Quality(), 10);

								setJpegImages(document.registrationApplet
										.getFinger1jpgValue(),
										document.registrationApplet
												.getFinger1Status(), 1, "FP1");
								setJpegImages(document.registrationApplet
										.getFinger2jpgValue(),
										document.registrationApplet
												.getFinger2Status(), 2, "FP2");
								setJpegImages(document.registrationApplet
										.getFinger3jpgValue(),
										document.registrationApplet
												.getFinger3Status(), 3, "FP3");
								setJpegImages(document.registrationApplet
										.getFinger4jpgValue(),
										document.registrationApplet
												.getFinger4Status(), 4, "FP4");
								setJpegImages(document.registrationApplet
										.getFinger5jpgValue(),
										document.registrationApplet
												.getFinger5Status(), 5, "FP5");
								setJpegImages(document.registrationApplet
										.getFinger6jpgValue(),
										document.registrationApplet
												.getFinger6Status(), 6, "FP6");
								setJpegImages(document.registrationApplet
										.getFinger7jpgValue(),
										document.registrationApplet
												.getFinger7Status(), 7, "FP7");
								setJpegImages(document.registrationApplet
										.getFinger8jpgValue(),
										document.registrationApplet
												.getFinger8Status(), 8, "FP8");
								setJpegImages(document.registrationApplet
										.getFinger9jpgValue(),
										document.registrationApplet
												.getFinger9Status(), 9, "FP9");
								setJpegImages(document.registrationApplet
										.getFinger10jpgValue(),
										document.registrationApplet
												.getFinger10Status(), 10,
										"FP10");

								setJpegImages(document.registrationApplet
										.getFinger1jpgValue(),
										document.registrationApplet
												.getFinger1Status(), 1,
										"FPFULL1");
								setJpegImages(document.registrationApplet
										.getFinger2jpgValue(),
										document.registrationApplet
												.getFinger2Status(), 2,
										"FPFULL2");
								setJpegImages(document.registrationApplet
										.getFinger3jpgValue(),
										document.registrationApplet
												.getFinger3Status(), 3,
										"FPFULL3");
								setJpegImages(document.registrationApplet
										.getFinger4jpgValue(),
										document.registrationApplet
												.getFinger4Status(), 4,
										"FPFULL4");
								setJpegImages(document.registrationApplet
										.getFinger5jpgValue(),
										document.registrationApplet
												.getFinger5Status(), 5,
										"FPFULL5");
								setJpegImages(document.registrationApplet
										.getFinger6jpgValue(),
										document.registrationApplet
												.getFinger6Status(), 6,
										"FPFULL6");
								setJpegImages(document.registrationApplet
										.getFinger7jpgValue(),
										document.registrationApplet
												.getFinger7Status(), 7,
										"FPFULL7");
								setJpegImages(document.registrationApplet
										.getFinger8jpgValue(),
										document.registrationApplet
												.getFinger8Status(), 8,
										"FPFULL8");
								setJpegImages(document.registrationApplet
										.getFinger9jpgValue(),
										document.registrationApplet
												.getFinger9Status(), 9,
										"FPFULL9");
								setJpegImages(document.registrationApplet
										.getFinger10jpgValue(),
										document.registrationApplet
												.getFinger10Status(), 10,
										"FPFULL10");
								$('.modal').hide();
							} else {
								alert('Some Problem with capturing fingerprints. Please try again');
								$('.modal').hide();
							}
							return false;
						});

	});

	function setQualityInfo(data, index) {

		if (data == "G") {
			$("#quality" + index)
					.css(
							{
								'background-image' : 'url(<c:url value="/resources/images/green_circle_new1.jpg" />)'
							});

		} else if (data == "A") {
			$("#quality" + index)
					.css(
							{
								'background-image' : 'url(<c:url value="/resources/images/orange_circle_new1.jpg" />)'
							});
		} else {
			$("#quality" + index)
					.css(
							{
								'background-image' : 'url(<c:url value="/resources/images/red_circle_new1.jpg" />)'
							});
		}
	}
	function setEncodeFP() {
		$.each([ '1', '2', '3', '4', '5', '6', '7', '8', '9', '10' ], function(
				index, value) {
			$("#defaultFPDiv" + value).hide();
		});
		var fpEncode = $('#fpEncodeHdnField').val();

		if (fpEncode != '') {
			var n = fpEncode.split(",");
			for ( var i = 0; i < n.length; i++) {
				var finger = n[i];
				if (finger == 'FP01') {
					$("#defaultFPDiv1").show();
				} else if (finger == 'FP02') {
					$("#defaultFPDiv2").show();
				} else if (finger == 'FP03') {
					$("#defaultFPDiv3").show();
				} else if (finger == 'FP04') {
					$("#defaultFPDiv4").show();
				} else if (finger == 'FP05') {
					$("#defaultFPDiv5").show();
				} else if (finger == 'FP06') {
					$("#defaultFPDiv6").show();
				} else if (finger == 'FP07') {
					$("#defaultFPDiv7").show();
				} else if (finger == 'FP08') {
					$("#defaultFPDiv8").show();
				} else if (finger == 'FP09') {
					$("#defaultFPDiv9").show();
				} else if (finger == 'FP10') {
					$("#defaultFPDiv10").show();
				}
			}
		}
	}
	function setDefaultFP() {
		$.each([ '1', '2', '3', '4', '5', '6', '7', '8', '9', '10' ], function(
				index, value) {
			$("#defaultFPDiv" + value).hide();
		});
		var counter = 0;
		var fpencodeStr = "";
		switch (1) {
		case 1:
			if (($('#finger1StatusHdnInpt').val()) != "B") {
				counter++;
				fpencodeStr += "FP01,";
				$("#defaultFPDiv1").show();
			}
		case 6:
			if (($('#finger6StatusHdnInpt').val()) != "B") {
				counter++;
				fpencodeStr += "FP06,";
				$("#defaultFPDiv6").show();
			}
		case 2:
			if (($('#finger2StatusHdnInpt').val()) != "B") {
				counter++;
				fpencodeStr += "FP02,";
				$("#defaultFPDiv2").show();
			}
		case 7:
			if (($('#finger7StatusHdnInpt').val()) != "B") {
				counter++;
				fpencodeStr += "FP07,";
				$("#defaultFPDiv7").show();
			}
			if (counter == 4) {
				break;
			}
		case 3:
			if (($('#finger3StatusHdnInpt').val()) != "B") {
				counter++;
				fpencodeStr += "FP03,";
				$("#defaultFPDiv3").show();
			}
			if (counter == 4) {
				break;
			}
		case 8:
			if (($('#finger8StatusHdnInpt').val()) != "B") {
				counter++;
				fpencodeStr += "FP08,";
				$("#defaultFPDiv8").show();
			}
			if (counter == 4) {
				break;
			}
		case 4:
			if (($('#finger4StatusHdnInpt').val()) != "B") {
				counter++;
				fpencodeStr += "FP04,";
				$("#defaultFPDiv4").show();
			}
			if (counter == 4) {
				break;
			}
		case 9:
			if (($('#finger9StatusHdnInpt').val()) != "B") {
				counter++;
				fpencodeStr += "FP09,";
				$("#defaultFPDiv9").show();
			}
			if (counter == 4) {
				break;
			}
		case 5:
			if (($('#finger5StatusHdnInpt').val()) != "B") {
				counter++;
				fpencodeStr += "FP05,";
				$("#defaultFPDiv5").show();
			}
			if (counter == 4) {
				break;
			}
		case 10:
			if (($('#finger10StatusHdnInpt').val()) != "B") {
				counter++;
				fpencodeStr += "FP10,";
				$("#defaultFPDiv10").show();
			}
			if (counter == 4) {
				break;
			}
		default:
			alert('default case');
		}
		document.forms["ricTransactionInfoForm"].fpEncodeHdnField.value = fpencodeStr;

	}
	function setJpegImages(imageVal, status, index, id) {
		if (imageVal != null) {
			document.getElementById(id).src = "data:image/jpg;base64,"
					+ imageVal;
		} else if (status == 'A') {
			$("#" + id)
					.attr("src", '<c:url value="/resources/images/AS.jpg"/>');
		} else if (status == 'U') {
			$("#" + id).attr("src",
					'<c:url value="/resources/images/Unable.jpg"/>');
		} else {
			$("#" + id)
					.attr("src", '<c:url value="/resources/images/NS.jpg"/>');
		}
	}

	function setEmptyImages(id) {
		$("#" + id).attr("src", '<c:url value="/resources/images/NS.jpg"/>');
	}
	function setVerifyInfo(matchScore, index) {
		if (Number(matchScore) > 6000) {
			$("#verify" + index)
					.css(
							{
								'background-image' : 'url(<c:url value="/resources/images/green_circle_new1.jpg" />)'
							});

		} else {
			$("#verify" + index)
					.css(
							{
								'background-image' : 'url(<c:url value="/resources/images/red_circle_new1.jpg" />)'
							});
		}

	}
</script>

<table class="roundedBorder"
	style="background-color: #FFFFC6; margin-top: 0px">
	<tr>
		<td>
			<table>
				<tr>
					<td style="width: 200px">
						<div id="sub_heading_new">FINGERPRINT</div>
					</td>
					<td style="padding: 5px"><c:choose>
							<c:when
								test="${newRegistration.comingFromRegistration==true || newRegistration.comingFromVerification==true || isUpdateVerify == 'Y' || isUpdateConfirm == 'Y'}">
								<div style="width:100px;"></div>
							</c:when>
							<c:otherwise>
								<input type="button" style="width: 100px;"
									class="btn_blue gradient" id="captureFP" value="Capture" />
							</c:otherwise>
						</c:choose></td>
					<td style="padding: 5px"><c:choose>
							<c:when
								test="${newRegistration.comingFromRegistration==true || newRegistration.comingFromVerification==true || isUpdateVerify == 'Y' || isUpdateConfirm == 'Y'}">
								<div style="width:100px;"></div>
							</c:when>
							<c:otherwise>
								<input type="button" style="width: 100px"
									class="btn_blue gradient" id="VerifyFP" value="Verify" />
							</c:otherwise>
						</c:choose></td>
					<td style="padding: 5px"><input type="button"
						style="width: 100px" class="btn_blue gradient" id="viewFPFull"
						value="Full View" /></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="4">

			<table>
				<tr>
					<td style="width: 20px;"></td>
					<td style="width: 50px;" class=subjectsmall
						style="text-align: center;  border: none;">Thumb</td>
					<td style="width: 50px;" class=subjectsmall
						style="text-align: center;  border: none;">Index</td>
					<td style="width: 50px;" class=subjectsmall
						style="text-align: center;  border: none;">Middle</td>
					<td style="width: 50px;" class=subjectsmall
						style="text-align: center;  border: none;">Ring</td>
					<td style="width: 50px;" class=subjectsmall
						style="text-align: center;  border: none;">Little</td>
				</tr>
				<tr>
					<td style="width: 20px;" class=subjectsmall
						style="text-align: center;  border: none;">Right</td>
					<td style="width: 80px"><c:choose>
							<c:when
								test="${newRegistration.fingerPrint01.fp == null || newRegistration.fingerPrint01.fp == ''}">
								<img id="FP1" src="<c:url value="/resources/images/No_Image.jpg"/>"
									width="80px" height="80px"
									style="border: 1px SOLID; align: left" alt="RIGHT THUMB"
									align="middle" />
							</c:when>
							<c:otherwise>
								<img id="FP1"
									src="data:image/jpg;base64,${newRegistration.fingerPrint01.fpJpg}"
									width="80px" height="80px"
									style="border: 1px SOLID; align: left" alt="RIGHT THUMB"
									align="middle" />
							</c:otherwise>
						</c:choose></td>
					<td style="width: 80px"><c:choose>
							<c:when
								test="${newRegistration.fingerPrint02.fp == null || newRegistration.fingerPrint02.fp == ''}">
								<img id="FP2" src="<c:url value="/resources/images/No_Image.jpg"/>"
									width="80px" height="80px"
									style="border: 1px SOLID; align: left" alt="RIGHT INDEX"
									align="middle" />
							</c:when>
							<c:otherwise>
								<img id="FP2"
									src="data:image/jpg;base64,${newRegistration.fingerPrint02.fpJpg}"
									width="80px" height="80px"
									style="border: 1px SOLID; align: left" alt="RIGHT INDEX"
									align="middle" />
							</c:otherwise>
						</c:choose></td>
					<td style="width: 80px"><c:choose>
							<c:when
								test="${newRegistration.fingerPrint03.fp == null || newRegistration.fingerPrint03.fp == ''}">
								<img id="FP3" src="<c:url value="/resources/images/No_Image.jpg"/>"
									width="80px" height="80px"
									style="border: 1px SOLID; align: left" alt="RIGHT INDEX"
									align="middle" />
							</c:when>
							<c:otherwise>
								<img id="FP3"
									src="data:image/jpg;base64,${newRegistration.fingerPrint03.fpJpg}"
									width="80px" height="80px"
									style="border: 1px SOLID; align: left" alt="RIGHT INDEX"
									align="middle" />
							</c:otherwise>
						</c:choose></td>
					<td style="width: 80px"><c:choose>
							<c:when
								test="${newRegistration.fingerPrint04.fp == null || newRegistration.fingerPrint04.fp == ''}">
								<img id="FP4" src="<c:url value="/resources/images/No_Image.jpg"/>"
									width="80px" height="80px"
									style="border: 1px SOLID; align: left" alt="RIGHT INDEX"
									align="middle" />
							</c:when>
							<c:otherwise>
								<img id="FP4"
									src="data:image/jpg;base64,${newRegistration.fingerPrint04.fpJpg}"
									width="80px" height="80px"
									style="border: 1px SOLID; align: left" alt="RIGHT INDEX"
									align="middle" />
							</c:otherwise>
						</c:choose></td>
					<td style="width: 80px"><c:choose>
							<c:when
								test="${newRegistration.fingerPrint05.fp == null || newRegistration.fingerPrint05.fp == ''}">
								<img id="FP5" src="<c:url value="/resources/images/No_Image.jpg"/>"
									width="80px" height="80px"
									style="border: 1px SOLID; align: left" alt="RIGHT INDEX"
									align="middle" />
							</c:when>
							<c:otherwise>
								<img id="FP5"
									src="data:image/jpg;base64,${newRegistration.fingerPrint05.fpJpg}"
									width="80px" height="80px"
									style="border: 1px SOLID; align: left" alt="RIGHT INDEX"
									align="middle" />
							</c:otherwise>
						</c:choose></td>
				</tr>
				<tr>
					<td style="width: 10px;" class=subjectsmall
						style="text-align: center;  border: none;"></td>
					<td style="width: 80px">
						<div style="width: 90px; overflow: hidden;">
							<c:choose>
								<c:when test="${newRegistration.fingerPrint01.fpQuality == 'G'}">
									<div id="quality1" class=""
										style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:when>
								<c:when test="${newRegistration.fingerPrint01.fpQuality == 'A'}">
									<div id="quality1" class=""
										style="background-image: url('<c:url value="/resources/images/orange_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:when>
								<c:otherwise>
									<div id="quality1" class=""
										style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${newRegistration.fingerPrint01.verify >= 6000}">
									<div id="verify1" class=""
										style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 4px 4px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
									</div>
								</c:when>
								<c:otherwise>
									<div id="verify1" class=""
										style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 4px 4px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
									</div>
								</c:otherwise>
							</c:choose>


							<div style="display: none" id="defaultFPDiv1">
								<p style="padding: 0px 5px 0px 0px;">
									<img src="<c:url value="/resources/images/contactless_selected.jpg "/>"
										class='radioButtonImage' style='width: 30px; height: 23px' />
								</p>
							</div>


						</div>
					</td>
					<td style="width: 80px">
						<div style="width: 90px; overflow: hidden;">
							<c:choose>
								<c:when test="${newRegistration.fingerPrint02.fpQuality == 'G'}">
									<div id="quality2" class=""
										style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:when>
								<c:when test="${newRegistration.fingerPrint02.fpQuality == 'A'}">
									<div id="quality2" class=""
										style="background-image: url('<c:url value="/resources/images/orange_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:when>
								<c:otherwise>
									<div id="quality2" class=""
										style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${newRegistration.fingerPrint02.verify >= 6000}">
									<div id="verify2" class=""
										style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 4px 4px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
									</div>
								</c:when>
								<c:otherwise>
									<div id="verify2" class=""
										style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 4px 4px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
									</div>
								</c:otherwise>
							</c:choose>


							<div style="display: none" id="defaultFPDiv2">
								<p style="padding: 0px 5px 0px 0px;">
									<img src="<c:url value="/resources/images/contactless_selected.jpg "/>"
										class='radioButtonImage' style='width: 30px; height: 23px' />
								</p>
							</div>

						</div>
					</td>
					<td style="width: 80px">
						<div style="width: 90px; overflow: hidden;">
							<c:choose>
								<c:when test="${newRegistration.fingerPrint03.fpQuality == 'G'}">
									<div id="quality3" class=""
										style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:when>
								<c:when test="${newRegistration.fingerPrint03.fpQuality == 'A'}">
									<div id="quality3" class=""
										style="background-image: url('<c:url value="/resources/images/orange_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:when>
								<c:otherwise>
									<div id="quality3" class=""
										style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${newRegistration.fingerPrint03.verify >= 6000}">
									<div id="verify3" class=""
										style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 4px 4px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
									</div>
								</c:when>
								<c:otherwise>
									<div id="verify3" class=""
										style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 4px 4px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
									</div>
								</c:otherwise>
							</c:choose>

							<div style="display: none" id="defaultFPDiv3">
								<p style="padding: 0px 5px 0px 0px;">
									<img src="<c:url value="/resources/images/contactless_selected.jpg "/>"
										class='radioButtonImage' style='width: 30px; height: 23px' />
								</p>
							</div>

						</div>
					</td>
					<td style="width: 80px">
						<div style="width: 90px; overflow: hidden;">
							<c:choose>
								<c:when test="${newRegistration.fingerPrint04.fpQuality == 'G'}">
									<div id="quality4" class=""
										style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:when>
								<c:when test="${newRegistration.fingerPrint04.fpQuality == 'A'}">
									<div id="quality4" class=""
										style="background-image: url('<c:url value="/resources/images/orange_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:when>
								<c:otherwise>
									<div id="quality4" class=""
										style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${newRegistration.fingerPrint04.verify >= 6000}">
									<div id="verify4" class=""
										style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 4px 4px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
									</div>
								</c:when>
								<c:otherwise>
									<div id="verify4" class=""
										style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 4px 4px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
									</div>
								</c:otherwise>
							</c:choose>


							<div style="display: none" id="defaultFPDiv4">
								<p style="padding: 0px 5px 0px 0px;">
									<img src="<c:url value="/resources/images/contactless_selected.jpg "/>"
										class='radioButtonImage' style='width: 30px; height: 23px' />
								</p>
							</div>

						</div>
					</td>
					<td style="width: 80px">
						<div style="width: 90px; overflow: hidden;">
							<c:choose>
								<c:when test="${newRegistration.fingerPrint05.fpQuality == 'G'}">
									<div id="quality5" class=""
										style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:when>
								<c:when test="${newRegistration.fingerPrint05.fpQuality == 'A'}">
									<div id="quality5" class=""
										style="background-image: url('<c:url value="/resources/images/orange_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:when>
								<c:otherwise>
									<div id="quality5" class=""
										style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${newRegistration.fingerPrint05.verify >= 6000}">
									<div id="verify5" class=""
										style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 4px 4px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
									</div>
								</c:when>
								<c:otherwise>
									<div id="verify5" class=""
										style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 4px 4px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
									</div>
								</c:otherwise>
							</c:choose>


							<div style="display: none" id="defaultFPDiv5">
								<p style="padding: 0px 5px 0px 0px;">
									<img src="<c:url value="/resources/images/contactless_selected.jpg "/>"
										class='radioButtonImage' style='width: 30px; height: 23px' />
								</p>
							</div>

						</div>
					</td>
				</tr>
				<tr>
					<td style="width: 20px;" class=subjectsmall
						style="text-align: center;  border: none;">Left</td>
					<td style="width: 80px"><c:choose>
							<c:when
								test="${newRegistration.fingerPrint06.fp == null || newRegistration.fingerPrint06.fp == ''}">
								<img id="FP6" src="<c:url value="/resources/images/No_Image.jpg"/>"
									width="80px" height="80px"
									style="border: 1px SOLID; align: left" alt="RIGHT INDEX"
									align="middle" />
							</c:when>
							<c:otherwise>
								<img id="FP6"
									src="data:image/jpg;base64,${newRegistration.fingerPrint06.fpJpg}"
									width="80px" height="80px"
									style="border: 1px SOLID; align: left" alt="RIGHT INDEX"
									align="middle" />
							</c:otherwise>
						</c:choose></td>
					<td style="width: 80px"><c:choose>
							<c:when
								test="${newRegistration.fingerPrint07.fp == null || newRegistration.fingerPrint07.fp == ''}">
								<img id="FP7" src="<c:url value="/resources/images/No_Image.jpg"/>"
									width="80px" height="80px"
									style="border: 1px SOLID; align: left" alt="RIGHT INDEX"
									align="middle" />
							</c:when>
							<c:otherwise>
								<img id="FP7"
									src="data:image/jpg;base64,${newRegistration.fingerPrint07.fpJpg}"
									width="80px" height="80px"
									style="border: 1px SOLID; align: left" alt="RIGHT INDEX"
									align="middle" />
							</c:otherwise>
						</c:choose></td>
					<td style="width: 80px"><c:choose>
							<c:when
								test="${newRegistration.fingerPrint08.fp == null || newRegistration.fingerPrint08.fp == ''}">
								<img id="FP8" src="<c:url value="/resources/images/No_Image.jpg"/>"
									width="80px" height="80px"
									style="border: 1px SOLID; align: left" alt="RIGHT INDEX"
									align="middle" />
							</c:when>
							<c:otherwise>
								<img id="FP8"
									src="data:image/jpg;base64,${newRegistration.fingerPrint08.fpJpg}"
									width="80px" height="80px"
									style="border: 1px SOLID; align: left" alt="RIGHT INDEX"
									align="middle" />
							</c:otherwise>
						</c:choose></td>
					<td style="width: 80px"><c:choose>
							<c:when
								test="${newRegistration.fingerPrint09.fp == null || newRegistration.fingerPrint09.fp == ''}">
								<img id="FP9" src="<c:url value="/resources/images/No_Image.jpg"/>"
									width="80px" height="80px"
									style="border: 1px SOLID; align: left" alt="RIGHT INDEX"
									align="middle" />
							</c:when>
							<c:otherwise>
								<img id="FP9"
									src="data:image/jpg;base64,${newRegistration.fingerPrint09.fpJpg}"
									width="80px" height="80px"
									style="border: 1px SOLID; align: left" alt="RIGHT INDEX"
									align="middle" />
							</c:otherwise>
						</c:choose></td>
					<td style="width: 80px"><c:choose>
							<c:when
								test="${newRegistration.fingerPrint10.fp == null || newRegistration.fingerPrint10.fp == ''}">
								<img id="FP10" src="<c:url value="/resources/images/No_Image.jpg"/>"
									width="80px" height="80px"
									style="border: 1px SOLID; align: left" alt="RIGHT INDEX"
									align="middle" />
							</c:when>
							<c:otherwise>
								<img id="FP10"
									src="data:image/jpg;base64,${newRegistration.fingerPrint10.fpJpg}"
									width="80px" height="80px"
									style="border: 1px SOLID; align: left" alt="RIGHT INDEX"
									align="middle" />
							</c:otherwise>
						</c:choose></td>
				</tr>
				<tr>
					<td style="width: 10px;" class=subjectsmall
						style="text-align: center;  border: none;"></td>
					<td style="width: 80px">
						<div style="width: 90px; overflow: hidden;">
							<c:choose>
								<c:when test="${newRegistration.fingerPrint06.fpQuality == 'G'}">
									<div id="quality6" class=""
										style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:when>
								<c:when test="${newRegistration.fingerPrint06.fpQuality == 'A'}">
									<div id="quality6" class=""
										style="background-image: url('<c:url value="/resources/images/orange_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:when>
								<c:otherwise>
									<div id="quality6" class=""
										style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${newRegistration.fingerPrint06.verify >= 6000}">
									<div id="verify6" class=""
										style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 4px 4px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
									</div>
								</c:when>
								<c:otherwise>
									<div id="verify6" class=""
										style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 4px 4px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
									</div>
								</c:otherwise>
							</c:choose>


							<div style="display: none" id="defaultFPDiv6">
								<p style="padding: 0px 5px 0px 0px;">
									<img src="<c:url value="/resources/images/contactless_selected.jpg "/>"
										class='radioButtonImage' style='width: 30px; height: 23px' />
								</p>
							</div>

						</div>
					</td>
					<td style="width: 80px">
						<div style="width: 90px; overflow: hidden;">
							<c:choose>
								<c:when test="${newRegistration.fingerPrint07.fpQuality == 'G'}">
									<div id="quality7" class=""
										style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:when>
								<c:when test="${newRegistration.fingerPrint07.fpQuality == 'A'}">
									<div id="quality7" class=""
										style="background-image: url('<c:url value="/resources/images/orange_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:when>
								<c:otherwise>
									<div id="quality7" class=""
										style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${newRegistration.fingerPrint07.verify >= 6000}">
									<div id="verify7" class=""
										style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 4px 4px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
									</div>
								</c:when>
								<c:otherwise>
									<div id="verify7" class=""
										style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 4px 4px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
									</div>
								</c:otherwise>
							</c:choose>


							<div style="display: none" id="defaultFPDiv7">
								<p style="padding: 0px 5px 0px 0px;">
									<img src="<c:url value="/resources/images/contactless_selected.jpg "/>"
										class='radioButtonImage' style='width: 30px; height: 23px' />
								</p>
							</div>

						</div>
					</td>
					<td style="width: 80px">
						<div style="width: 90px; overflow: hidden;">
							<c:choose>
								<c:when test="${newRegistration.fingerPrint08.fpQuality == 'G'}">
									<div id="quality8" class=""
										style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:when>
								<c:when test="${newRegistration.fingerPrint08.fpQuality == 'A'}">
									<div id="quality8" class=""
										style="background-image: url('<c:url value="/resources/images/orange_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:when>
								<c:otherwise>
									<div id="quality8" class=""
										style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${newRegistration.fingerPrint08.verify >= 6000}">
									<div id="verify8" class=""
										style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 4px 4px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
									</div>
								</c:when>
								<c:otherwise>
									<div id="verify8" class=""
										style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 4px 4px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
									</div>
								</c:otherwise>
							</c:choose>


							<div style="display: none" id="defaultFPDiv8">
								<p style="padding: 0px 5px 0px 0px;">
									<img src="<c:url value="/resources/images/contactless_selected.jpg "/>"
										class='radioButtonImage' style='width: 30px; height: 23px' />
								</p>
							</div>

						</div>
					</td>
					<td style="width: 80px">
						<div style="width: 90px; overflow: hidden;">
							<c:choose>
								<c:when test="${newRegistration.fingerPrint09.fpQuality == 'G'}">
									<div id="quality9" class=""
										style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:when>
								<c:when test="${newRegistration.fingerPrint09.fpQuality == 'A'}">
									<div id="quality9" class=""
										style="background-image: url('<c:url value="/resources/images/orange_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:when>
								<c:otherwise>
									<div id="quality9" class=""
										style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${newRegistration.fingerPrint09.verify >= 6000}">
									<div id="verify9" class=""
										style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 4px 4px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
									</div>
								</c:when>
								<c:otherwise>
									<div id="verify9" class=""
										style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 4px 4px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
									</div>
								</c:otherwise>
							</c:choose>


							<div style="display: none" id="defaultFPDiv9">
								<p style="padding: 0px 5px 0px 0px;">
									<img src="<c:url value="/resources/images/contactless_selected.jpg "/>"
										class='radioButtonImage' style='width: 30px; height: 23px' />
								</p>
							</div>

						</div>
					</td>
					<td style="width: 80px">
						<div style="width: 90px; overflow: hidden;">
							<c:choose>
								<c:when test="${newRegistration.fingerPrint10.fpQuality == 'G'}">
									<div id="quality10" class=""
										style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:when>
								<c:when test="${newRegistration.fingerPrint10.fpQuality == 'A'}">
									<div id="quality10" class=""
										style="background-image: url('<c:url value="/resources/images/orange_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:when>
								<c:otherwise>
									<div id="quality10" class=""
										style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 3px 5px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
									</div>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${newRegistration.fingerPrint10.verify >= 6000}">
									<div id="verify10" class=""
										style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 4px 4px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
									</div>
								</c:when>
								<c:otherwise>
									<div id="verify10" class=""
										style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg" />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
										<p class=""
											style="text-align: center; valign: top; margin: 0; padding: 4px 4px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;V</p>
									</div>
								</c:otherwise>
							</c:choose>


							<div style="display: none" id="defaultFPDiv10">
								<p style="padding: 0px 5px 0px 0px;">
									<img src="<c:url value="/resources/images/contactless_selected.jpg "/>"
										class='radioButtonImage' style='width: 30px; height: 23px' />
								</p>
							</div>

						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>



<div id="dialog-FPFullView" title="Fingerprint" style="width: 1260px">
	<table>
		<tr>
			<td style="width: 20px;"></td>
			<td style="width: 512px;" class=subjectsmall
				style="text-align: center;  border: none;">Right</td>
			<td style="width: 512px;" class=subjectsmall
				style="text-align: center;  border: none;">Left</td>
		</tr>
		<tr>
			<td style="width: 20px;" class=subjectsmall
				style="text-align: center;  border: none;">Thumb</td>
			<td style="width: 512px"><c:choose>
					<c:when
						test="${newRegistration.fingerPrint01.fp == null || newRegistration.fingerPrint01.fp == ''}">
						<img id="FPFULL1" src="<c:url value="/resources/images/NS.jpg " />"
							width="512px" height="512px"
							style="border: 1px SOLID; align: left" alt="BERT CERT"
							align="middle" />
					</c:when>
					<c:otherwise>
						<img id="FPFULL1"
							src="data:image/jpg;base64,${newRegistration.fingerPrint01.fpJpg}"
							width="512px" height="512px"
							style="border: 1px SOLID; align: left" alt="BERT CERT"
							align="middle" />
					</c:otherwise>
				</c:choose></td>
			<td style="width: 512px"><c:choose>
					<c:when
						test="${newRegistration.fingerPrint06.fp == null || newRegistration.fingerPrint06.fp == ''}">
						<img id="FPFULL6" src="<c:url value="/resources/images/NS.jpg " />"
							width="512px" height="512px"
							style="border: 1px SOLID; align: left" alt="BERT CERT"
							align="middle" />
					</c:when>
					<c:otherwise>
						<img id="FPFULL6"
							src="data:image/jpg;base64,${newRegistration.fingerPrint06.fpJpg}"
							width="512px" height="512px"
							style="border: 1px SOLID; align: left" alt="BERT CERT"
							align="middle" />
					</c:otherwise>
				</c:choose></td>
		</tr>
		<tr>
			<td style="width: 20px;" class=subjectsmall
				style="text-align: center;  border: none;">Index</td>
			<td style="width: 512px"><c:choose>
					<c:when
						test="${newRegistration.fingerPrint02.fp == null || newRegistration.fingerPrint02.fp == ''}">
						<img id="FPFULL2" src="<c:url value="/resources/images/NS.jpg " />"
							width="512px" height="512px"
							style="border: 1px SOLID; align: left" alt="BERT CERT"
							align="middle" />
					</c:when>
					<c:otherwise>
						<img id="FPFULL2"
							src="data:image/jpg;base64,${newRegistration.fingerPrint02.fpJpg}"
							width="512px" height="512px"
							style="border: 1px SOLID; align: left" alt="BERT CERT"
							align="middle" />
					</c:otherwise>
				</c:choose></td>
			<td style="width: 512px"><c:choose>
					<c:when
						test="${newRegistration.fingerPrint07.fp == null || newRegistration.fingerPrint07.fp == ''}">
						<img id="FPFULL7" src="<c:url value="/resources/images/NS.jpg " />"
							width="512px" height="512px"
							style="border: 1px SOLID; align: left" alt="BERT CERT"
							align="middle" />
					</c:when>
					<c:otherwise>
						<img id="FPFULL7"
							src="data:image/jpg;base64,${newRegistration.fingerPrint07.fpJpg}"
							width="512px" height="512px"
							style="border: 1px SOLID; align: left" alt="BERT CERT"
							align="middle" />
					</c:otherwise>
				</c:choose></td>
		</tr>
		<tr>
			<td style="width: 20px;" class=subjectsmall
				style="text-align: center;  border: none;">Middle</td>
			<td style="width: 512px"><c:choose>
					<c:when
						test="${newRegistration.fingerPrint03.fp == null || newRegistration.fingerPrint03.fp == ''}">
						<img id="FPFULL3" src="<c:url value="/resources/images/NS.jpg " />"
							width="512px" height="512px"
							style="border: 1px SOLID; align: left" alt="BERT CERT"
							align="middle" />
					</c:when>
					<c:otherwise>
						<img id="FPFULL3"
							src="data:image/jpg;base64,${newRegistration.fingerPrint03.fpJpg}"
							width="512px" height="512px"
							style="border: 1px SOLID; align: left" alt="BERT CERT"
							align="middle" />
					</c:otherwise>
				</c:choose></td>
			<td style="width: 512px"><c:choose>
					<c:when
						test="${newRegistration.fingerPrint08.fp == null  || newRegistration.fingerPrint08.fp == ''}">
						<img id="FPFULL8" src="<c:url value="/resources/images/NS.jpg " />"
							width="512px" height="512px"
							style="border: 1px SOLID; align: left" alt="BERT CERT"
							align="middle" />
					</c:when>
					<c:otherwise>
						<img id="FPFULL8"
							src="data:image/jpg;base64,${newRegistration.fingerPrint08.fpJpg}"
							width="512px" height="512px"
							style="border: 1px SOLID; align: left" alt="BERT CERT"
							align="middle" />
					</c:otherwise>
				</c:choose></td>
		</tr>
		<tr>
			<td style="width: 20px;" class=subjectsmall
				style="text-align: center;  border: none;">Ring</td>
			<td style="width: 512px"><c:choose>
					<c:when
						test="${newRegistration.fingerPrint04.fp == null || newRegistration.fingerPrint04.fp == ''}">
						<img id="FPFULL4" src="<c:url value="/resources/images/NS.jpg " />"
							width="512px" height="512px"
							style="border: 1px SOLID; align: left" alt="BERT CERT"
							align="middle" />
					</c:when>
					<c:otherwise>
						<img id="FPFULL4"
							src="data:image/jpg;base64,${newRegistration.fingerPrint04.fpJpg}"
							width="512px" height="512px"
							style="border: 1px SOLID; align: left" alt="BERT CERT"
							align="middle" />
					</c:otherwise>
				</c:choose></td>
			<td style="width: 512px"><c:choose>
					<c:when
						test="${newRegistration.fingerPrint09.fp == null || newRegistration.fingerPrint09.fp == ''}">
						<img id="FPFULL9" src="<c:url value="/resources/images/NS.jpg " />"
							width="512px" height="512px"
							style="border: 1px SOLID; align: left" alt="BERT CERT"
							align="middle" />
					</c:when>
					<c:otherwise>
						<img id="FPFULL9"
							src="data:image/jpg;base64,${newRegistration.fingerPrint09.fpJpg}"
							width="512px" height="512px"
							style="border: 1px SOLID; align: left" alt="BERT CERT"
							align="middle" />
					</c:otherwise>
				</c:choose></td>
		</tr>
		<tr>
			<td style="width: 20px;" class=subjectsmall
				style="text-align: center;  border: none;">Little</td>
			<td style="width: 512px"><c:choose>
					<c:when
						test="${newRegistration.fingerPrint05.fp == null || newRegistration.fingerPrint05.fp == ''}">
						<img id="FPFULL5" src="<c:url value="/resources/images/NS.jpg " />"
							width="512px" height="512px"
							style="border: 1px SOLID; align: left" alt="BERT CERT"
							align="middle" />
					</c:when>
					<c:otherwise>
						<img id="FPFULL5"
							src="data:image/jpg;base64,${newRegistration.fingerPrint05.fpJpg}"
							width="512px" height="512px"
							style="border: 1px SOLID; align: left" alt="BERT CERT"
							align="middle" />
					</c:otherwise>
				</c:choose></td>
			<td style="width: 512px"><c:choose>
					<c:when
						test="${newRegistration.fingerPrint10.fp == null || newRegistration.fingerPrint10.fp == ''}">
						<img id="FPFULL10" src="<c:url value="/resources/images/NS.jpg " />"
							width="512px" height="512px"
							style="border: 1px SOLID; align: left" alt="BERT CERT"
							align="middle" />
					</c:when>
					<c:otherwise>
						<img id="FPFULL10"
							src="data:image/jpg;base64,${newRegistration.fingerPrint10.fpJpg}"
							width="512px" height="512px"
							style="border: 1px SOLID; align: left" alt="BERT CERT"
							align="middle" />
					</c:otherwise>
				</c:choose></td>
		</tr>
	</table>
	<script type="text/javascript">
		$.each([ '1', '2', '3', '4', '5', '6', '7', '8', '9', '10' ], function(
				index, value) {
			$("#defaultFPDiv" + value).hide();
		});
		var fpEncode = '${newRegistration.fpEncode}';
		if (fpEncode != '') {
			var n = fpEncode.split(",");
			for ( var i = 0; i < n.length; i++) {
				var finger = n[i];
				if (finger == 'FP01') {
					$("#defaultFPDiv1").show();
				} else if (finger == 'FP02') {
					$("#defaultFPDiv2").show();
				} else if (finger == 'FP03') {
					$("#defaultFPDiv3").show();
				} else if (finger == 'FP04') {
					$("#defaultFPDiv4").show();
				} else if (finger == 'FP05') {
					$("#defaultFPDiv5").show();
				} else if (finger == 'FP06') {
					$("#defaultFPDiv6").show();
				} else if (finger == 'FP07') {
					$("#defaultFPDiv7").show();
				} else if (finger == 'FP08') {
					$("#defaultFPDiv8").show();
				} else if (finger == 'FP09') {
					$("#defaultFPDiv9").show();
				} else if (finger == 'FP10') {
					$("#defaultFPDiv10").show();
				}
			}
		}
	</script>
</div>


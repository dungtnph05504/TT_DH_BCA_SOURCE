<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
$(function(){

	$("#dialog-FacialFullView").dialog({
		modal : true,
		autoOpen : false,
		width : 1250,
		height : 800,
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
	$("#viewFullFacial").click(function() {
		$("#dialog-FacialFullView").dialog("open");
	});
	$("#capturefacialBtnId")
			.click(
					function() {

						if ($("#capturefacialBtnId").val() == "Capture") {
							$("#capturefacialBtnId").val("Recapture");
						}
						var res = document.registrationApplet.captureICAO();
						if (res == 0) {
							setTimeout(
									function() {
										document.forms["ricTransactionInfoForm"].enrolledFaceHdnInpt.value = document.registrationApplet
												.captureEnrolledFacial();
										document.forms["ricTransactionInfoForm"].encodedFaceHdnInpt.value = document.registrationApplet
												.captureEncodedFacial();
										document.forms["ricTransactionInfoForm"].printedFaceHdnInpt.value = document.registrationApplet
												.capturePrintedFacial();
										document.forms["ricTransactionInfoForm"].thumbNailFaceHdnInpt.value = document.registrationApplet
												.captureThumbNailFacial();
										document.forms["ricTransactionInfoForm"].thumbNailEncodeFaceHdnInpt.value = document.registrationApplet
												.captureEncodedThumbNailFacial();
										document.forms["ricTransactionInfoForm"].thumbNaiPrintedFaceHdnInpt.value = document.registrationApplet
												.capturePrintedThumbNailFacial();
										document.forms["ricTransactionInfoForm"].facialEnrolledIndicatorHdnInpt.value = document.registrationApplet
												.getFacialQuality();
										document
												.getElementById("facialEnrolled").src = "data:image/jpg;base64,"
												+ document.registrationApplet
														.captureEnrolledFacial();
										document
												.getElementById("facialEncoded").src = "data:image/jpg;base64,"
												+ document.registrationApplet
														.captureEncodedFacial();
										document
												.getElementById("facialPrinted").src = "data:image/jpg;base64,"
												+ document.registrationApplet
														.capturePrintedFacial();
									}, 600);
						}
					});
	$("#viewFullFacial")
			.click(
					function() {
						document.getElementById("facialEnrolledFull").src = "data:image/jpg;base64,"
								+ $("#enrolledFaceHdnInpt").val();
						document.getElementById("facialEncodedFull").src = "data:image/jpg;base64,"
								+ $("#encodedFaceHdnInpt").val();
						document.getElementById("facialPrintedFull").src = "data:image/jpg;base64,"
								+ $("#printedFaceHdnInpt").val();
						$("#dialog-FacialFullView").dialog("open");
					});
});


</script>
<table class="roundedBorder" style="margin-left: 5px; margin-top: 0px; margin-left: 0px; width: 350px; height: 210px; background-color: #FFFFC6">
                <tr>
                    <td style="padding: 5px; width: 100px" colspan=2>
                        <div id="sub_heading_new">FACIAL</div>
                    </td>
                    <td style="padding: 5px">
                        <div id="" style="">
                        <c:choose>
							<c:when
								test="${newRegistration.comingFromRegistration==true || newRegistration.comingFromVerification==true}">
								<input type="button" style="width: 100px;display: none;" class="btn_blue gradient" id="capturefacialBtnId" value="Capture" />
							</c:when>
							<c:otherwise>
								<input type="button" style="width: 100px" class="btn_blue gradient" id="capturefacialBtnId" value="Capture" />
							</c:otherwise>
						</c:choose>
                            
                        </div>
                    </td>
                    <td style="padding: 5px">
                        <input type="button" style="width: 100px" class="btn_blue gradient" id="viewFullFacial" value="Full View" />
                    </td>
                </tr>
                <tr>
                    <td style="width: 10px; padding: 2px"></td>
                    <td style="width: 100px;" class=subjectsmall style="text-align: left;  border: none;">Enrolled</td>
                    <td style="width: 100px;" class=subjectsmall style="text-align: left;  border: none;">Print on Card</td>
                    <td style="width: 100px;" class=subjectsmall style="text-align: left;  border: none;">Encode in Chip</td>
                </tr>
                <tr>
                    <td style="width: 10px"></td>
                    <td style="width: 110px;">
                     	<c:choose>
                     		<c:when test="${newRegistration.enrolledFace.face == null || newRegistration.enrolledFace.face == ''}">
                     			<img id="facialEnrolled" src="<c:url value="/resources/images/No_Image.jpg " />" width="100px" height="120px" alt="EnrolledFace" align="middle" />
                            </c:when>
                            <c:otherwise>
                            	<img id="facialEnrolled" src="data:image/jpg;base64,${newRegistration.enrolledFace.face}" width="100px" height="120px" alt="EnrolledFace" align="middle" />
                            </c:otherwise>
                         </c:choose>
                        
                    </td>
                    <td style="width: 110px;">
                    	<c:choose>
                     		<c:when test="${newRegistration.printedFace.face == null || newRegistration.printedFace.face == ''}">
                     			<img id="facialPrinted" src="<c:url value="/resources/images/No_Image.jpg " />" width="100px" height="120px" alt="PrintedFace" align="middle" />
                            </c:when>
                            <c:otherwise>
                            	<img id="facialPrinted" src="data:image/jpg;base64,${newRegistration.printedFace.face}" width="100px" height="120px" alt="PrintedFace" align="middle" />
                            </c:otherwise>
                         </c:choose>
                    </td>
                    <td style="width: 110px">
                    	<c:choose>
                     		<c:when test="${newRegistration.encodedFace.face == null || newRegistration.encodedFace.face == ''}">
                     			<img id="facialEncoded" src="<c:url value="/resources/images/No_Image.jpg " />" width="100px" height="120px" alt="EncodedFace" align="middle" />
                            </c:when>
                            <c:otherwise>
                            	<img id="facialEncoded" src="data:image/jpg;base64,${newRegistration.encodedFace.face}" width="100px" height="120px" alt="EncodedFace" align="middle" />
                            </c:otherwise>
                         </c:choose>
                    </td>
                </tr>
            </table>
            
     <div id="dialog-FacialFullView" title="Facial Full View" style="width: 1260px">
    <table>
        <tr>
            <td style="width: 480px;" class=subjectsmall style="text-align: center;  border: none;">Enrolled</td>
            <td style="width: 480px;" class=subjectsmall style="text-align: center;  border: none;">Print on Card</td>
            <td style="width: 240px;" class=subjectsmall style="text-align: center;  border: none;">Encode in Chip</td>
        </tr>
        <tr>
            <td style="width: 480px;">
            <c:choose>
                     		<c:when test="${newRegistration.enrolledFace.face == null || newRegistration.enrolledFace.face == ''}">
                     			 <img id="facialEnrolledFull" src="<c:url value="/resources/images/No_Image.jpg " />" style="border: 1px SOLID; align: left" alt="FACIAL CAPTURED" align="middle" />
                            </c:when>
                            <c:otherwise>
                            	 <img id="facialEnrolledFull" src="data:image/jpg;base64,${newRegistration.enrolledFace.face}" style="border: 1px SOLID; align: left" alt="FACIAL CAPTURED" align="middle" />
                            </c:otherwise>
                         </c:choose>
               
            </td>
            <td style="width: 480px;">
            <c:choose>
                     		<c:when test="${newRegistration.printedFace.face == null || newRegistration.printedFace.face == ''}">
                     			 <img id="facialPrintedFull" src="<c:url value="/resources/images/No_Image.jpg " />" style="border: 1px SOLID; align: left" alt="FACIAL PRINT" align="middle" />
                            </c:when>
                            <c:otherwise>
                            	 <img id="facialPrintedFull" src="data:image/jpg;base64,${newRegistration.printedFace.face}" style="border: 1px SOLID; align: left" alt="FACIAL CAPTURED" align="middle" />
                            </c:otherwise>
                         </c:choose>
                
            </td>
            <td valign="top" style="width: 240px;">
            <c:choose>
                     		<c:when test="${newRegistration.encodedFace.face == null || newRegistration.encodedFace.face == ''}">
                     			 <img id="facialEncodedFull" src="<c:url value="/resources/images/No_Image.jpg " />" style="border: 1px SOLID; align: left" alt="FACIAL PRINT" align="middle" />
                            </c:when>
                            <c:otherwise>
                            	 <img id="facialEncodedFull" src="data:image/jpg;base64,${newRegistration.encodedFace.face}" style="border: 1px SOLID; align: left" alt="FACIAL CAPTURED" align="middle" />
                            </c:otherwise>
                         </c:choose>
            </td>
        </tr>
    </table>
</div>
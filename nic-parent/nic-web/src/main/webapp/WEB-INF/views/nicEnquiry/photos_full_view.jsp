<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <table>
        <tr>
            <td colspan="2">
            	<table style="width: 100%;">
            		<tr>
            			<td width="15%"> <span style="font-weight: bold;">Mã giao dịch:</span> </td>
            			<td> <span><c:out value="${photoEnquiryInfo.transactionId }"/></span> </td>
            			
            		</tr>
            		<tr>
            			<td width="15%"> <span style="font-weight: bold;">Số hộ chiếu:</span> </td>
            			<td> <span><c:out value="${passportNo}"/> </span> </td>
            		</tr>
            	</table>
            </td>
        </tr>
         <tr>
            <td colspan="2">
            	&nbsp;
            </td>
        </tr>
        <!--  <tr>
            <td style="width: 480px; border: none;" class="subjectsmall">Original</td>
            <td style="width: 480px; border: none;" class="subjectsmall">Grey</td>
            <td style="width: 240px; border: none;" class="subjectsmall">Encode in Chip</td>
        </tr> -->
        <tr>
            <td style="padding-right: 5px;" ><c:choose>
				<c:when
					test="${photoEnquiryInfo.capturePhotoStr== null || photoEnquiryInfo.capturePhotoStr == ''}">
					<img id="facialEnrolledFull" title="Original Photo" width="auto"
						src="<c:url value="/resources/images/No_Image_480_640.jpg " />"
						style="border: 1px SOLID; align: left" alt="FACIAL CAPTURED"/>
				</c:when>
				<c:otherwise>
					<img id="facialEnrolledFull" title="Original Photo" width="auto"
						src="data:image/jpg;base64,${photoEnquiryInfo.capturePhotoStr}"
						style="border: 1px SOLID; align: left" alt="FACIAL CAPTURED"/>
				</c:otherwise>
			</c:choose></td>
            <td style="padding-right: 5px; " align="center" >
           	 <%-- <c:choose>
				<c:when
					test="${photoEnquiryInfo.greyPhotoStr == null || photoEnquiryInfo.greyPhotoStr == ''}">
					<img id="facialPrintedFull" title="Grey Photo" width="480px"
						src="<c:url value="/resources/images/No_Image_480_640.jpg " />"
						style="border: 1px SOLID; align: left" alt="FACIAL PRINT"/>
				</c:when>
				<c:otherwise>
					<img id="facialPrintedFull" title="Grey Photo" width="480px"
						src="data:image/jpg;base64,${photoEnquiryInfo.greyPhotoStr}"
						style="border: 1px SOLID; align: left" alt="FACIAL CAPTURED"/>
				</c:otherwise>
			</c:choose> --%>
			<%--
			<c:choose>
				<c:when test="${ not empty photoEnquiryInfo.signatureStr}">
					<img id="signatureid" src="data:image/jpg;base64,${photoEnquiryInfo.signatureStr}"
							alt="Signature" align="middle" width="auto" />
				</c:when>
				<c:otherwise>
					<b> Không có chữ ký</b>
				</c:otherwise>
			</c:choose> --%>
		</td>
		<%-- <td>
			<table>
				<tr>
					<td ><c:choose>
							<c:when
								test="${photoEnquiryInfo.chipPhotoStr == null || photoEnquiryInfo.chipPhotoStr == ''}">
								<img id="facialEncodedFull" width="240px" title="Encode in Chip"
									src="<c:url value="/resources/images/No_Image_240_320.jpg " />"
									style="border: 1px SOLID; align: left" alt="FACIAL PRINT" />
							</c:when>
							<c:otherwise>
								<img id="facialEncodedFull" width="240px" title="Encode in Chip"
									src="data:image/jpg;base64,${photoEnquiryInfo.chipPhotoStr}"
									style="border: 1px SOLID; align: left" alt="FACIAL CAPTURED"/>
							</c:otherwise>
						</c:choose></td>
				</tr>
				<tr>
					<td style="padding-top: 5px;"><c:choose>
							<c:when
								test="${photoEnquiryInfo.cliPhotoStr == null || photoEnquiryInfo.cliPhotoStr == ''}">
								<img id="facialEncodedFull" width="240px" title="CLI Photo"
									src="<c:url value="/resources/images/No_Image_96_128.jpg " />"
									style="border: 1px SOLID; align: left" alt="FACIAL PRINT"/>
							</c:when>
							<c:otherwise>
								<img id="facialEncodedFull" width="240px" title="CLI Photo"
									src="data:image/jpg;base64,${photoEnquiryInfo.cliPhotoStr}"
									style="border: 1px SOLID; align: left" alt="FACIAL CAPTURED"/>
							</c:otherwise>
						</c:choose></td>
				</tr>
			</table>
		</td> --%>
	</tr>
	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="2" align="right"><input type="button"
			class="button_grey" id="photo_full_close_btn" value="Close"
			onclick="closePhotoFullDialog();" /></td>
	</tr>
</table>
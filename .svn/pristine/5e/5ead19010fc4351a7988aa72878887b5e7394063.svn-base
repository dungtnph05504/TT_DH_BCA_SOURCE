<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script>
var collectorVerified = false;

$(document).ready(function() {
	
	$("#selFPForVer").change(function() {
		alert(this.value);
		if(this.value === 'NONE'){
			$("#captrFPBtn").attr("disabled", true);
			$("#verFPOpnrBtn").attr("disabled", true);
		}else{
			$("#captrFPBtn").attr("disabled", false).addClass("btn_blue");
		}		
	});

});
	$(function() {
		$("#dialog-verifyFP")
		.dialog(
				{
					modal : true,
					autoOpen : false,
					width : 800,
					height : 350,
					title : 'Verify Fingerprint',
					closeOnEscape : false,
					show : {
						effect : "blind",
						duration : 1000
					},
					hide : {
						//effect : "explode",
						duration : 1000
					},
					buttons : {
						'Success' : function() {
							$("#verifyFPImage").attr("src", '<c:url value="/images/new_thumbs_up.jpg"/>');
							$(this).dialog('close');						
						},
						'Fail' : function() {
							$(this).dialog('close');
						}
					}
				});
		$("#captrFPBtn").click(function() {
			var captureFinger = $("#selFPForVer").val();
			var fptext = $('#fingernameFP'+captureFinger).val();
			alert(fptext);			
			var res = document.registrationApplet.captureFingerprint(captureFinger);			
			if(res == 0){				
				$("#status"+captureFinger).html('<img src="<c:url value="/resources/images/info.gif"/>" width="20px" height="20px" alt=""/> '+fptext+' Fingerprint Captured.');
				
				$("#captrFPBtn").attr("disabled", true);
				$("#verFPOpnrBtn").attr("disabled",false).addClass("btn_blue");
				$("#capturedImage"+captureFinger).val(document.registrationApplet.getCapturedImage());
				$("#minutiaTemplate"+captureFinger).val(document.registrationApplet.getMinutiaTemplate());
			}
		});
		$("#verFPOpnrBtn").click(function() {
			var toVerify = $("#selFPForVer").val();
			var fptext = $('#fingernameFP'+toVerify).val();
			alert(fptext);
			$.post(	'<c:url value="/servlet/cardissue/getFingerprintImage" />',
					{
						finger : toVerify,
						applicationNo :  $("#transactionIdHdnInpt").val()
					}, function(data) {
						//alert(data);
						var matchScore = document.registrationApplet.verifyFingerprint(toVerify,$("#minutiaTemplate"+toVerify).val(),data);
						//alert(matchScore);
						if(matchScore > 6000){
							
							$("#status"+toVerify).html('<img src="<c:url value="/resources/images/tick.gif"/>"   width="20px" height="20px" alt=""/>1:1 '+fptext+' Fingerprint Verified. ');
							
							$("#activCardBtn").attr("disabled", false).addClass("btn_blue");
							$("#verFPOpnrBtn").attr("disabled", true);
						} else{
							$("#captrFPBtn").attr("disabled",false).addClass("btn_blue");
							alert("Fingerprints not matched ");
						}
					});
			
		});
	});
		
</script>

<div id="dialog-verifyFP">
    <table class="innerTable">
        <tr class="oddrowcolor">
            <td class="subjectsmall" align="left" width="280px">Select Fingerprint
            </td>
            <td align="left" width="2px">:</td>
            <td align="left" width="150px">

                <form:select path="defaultFp" id="selFPForVer">
                    <form:option value="NONE" label="--- Select ---" />
                    <c:forEach var="fpListItem" items="${defaultFpList}" varStatus="status">
                        <form:option value="${fpListItem.key}" label="${status.count} - ${fpListItem.value}" />
                    </c:forEach>
                </form:select>

            </td>
            <td align="left">&nbsp;
                <input type="button" class="btn_blue" value="Capture Fingerprint" id="captrFPBtn" disabled="disabled" />
                <input type="button" class="btn_blue" value="Verify Finger Print" id="verFPOpnrBtn" disabled="disabled" />
            </td>
        </tr>
        <tr class="evenrowcolor">
            <td class="subjectsmall" align="left" width="280px">Status</td>
            <td align="left" width="2px">:

            </td>
            <td class="subjectsmall" align="left" colspan="2">               
                <table>
                   
                    <c:forEach var="fpListItem" items="${defaultFpList}" varStatus="status">
                        <tr>
                            <td>
                                <p id="status${fpListItem.key}" align="left"></p>
                            </td>
                        </tr>
                    </c:forEach>

                </table>
            </td>
        </tr>
    </table>
</div>

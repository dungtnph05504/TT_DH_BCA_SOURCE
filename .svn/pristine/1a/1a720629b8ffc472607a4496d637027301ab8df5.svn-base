<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@page import="org.apache.commons.codec.binary.Base64"%> 
<%@page import="com.nec.asia.nic.comp.job.dto.FingerprintDTO"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%> 
<%@page import="com.nec.asia.nic.investigation.controller.FraudCaseManagementData"%>
<c:url var="falseHitUrl" value="/servlet/investigation/falseHit" />
<c:url var="trueHitUrl" value="/servlet/investigation/trueHit" />
<c:url var="submitDecisionUrl" value="/servlet/fraudCaseManagement/submitDecision" /> 
<c:url var="backBtnUrl"
	value="/servlet/fraudCaseManagement/investigationSuspendedList" />
<c:url var="cancelDocumentDataUrl" value="/servlet/fraudCaseManagement/cancelDocumentData" /> 
	
<c:set var="zero" scope="page" value="0"/>	
<% int zeru = 0;%> 	
	
<script src="<c:url value="/resources/js/mouseover_image.js"/>"
	type="text/javascript"></script>

<script>
//dummy
// eclipse-formatted
//UNUSED
/*
 * fullyMainCandAmputated
 hitCandFpQuality
 mainCandidateSign_Thumb
 */
 </script>


													
<style>
	
	.doGetAJpgSafeVersion { 
	     visibility:  hidden;
	} 
													
</style>
															
<style>
	
	.theBlockLeft {
		display: inline-block; 
		width: 14%;
		margin-right:1%;
		float:left;
	} 
	
	.theBlockRight {
		display: inline-block; 
		width: 54%;
		margin-right:1%;
		float:left;
	} 
	
	.theBlockRight3 {
		display: inline-block; 
		width: 30%;
		float:right;
	} 
	
	.theBlockRight2and3 {
		display: inline-block; 
		width: 84%;
		margin-right:1%;
		float:right;
	} 
	
	.theOneRow {
		margin-top:  5px;
		padding-bottom:  15px;
	}

</style>
	

						
<style>
	.hit_document_major_title {
		text-align: left; font-size: 15px; font-weight: bold; 
	}
	
	.hit_document_content1 {
		display:inline-block; width: 23%; margin-left:3%;margin-right:1%;float:left;
	}
	
	.hit_document_content2 {
		display:inline-block; width: 14%; margin-left:1%;margin-right:1%;float:left;
	}
	
	.hit_document_content3 {
		display:inline-block; width: 28%; margin-left:1%;margin-right:1%;float:left;
	}
	
	.hit_document_content4 {
		display:inline-block; width: 10%; margin-left:1%;margin-right:1%;float:left;
	}
	
	.hit_document_content5 {
		display:inline-block; width: 13%; margin-left:1%;margin-right:1%;float:left;
	}
	
	.hit_document_border_at_top {
		border-top:  gray solid 1px;
		margin-top:  2px;
		margin-bottom:  2px;
		min-height:  2px;
	}
	
	.hit_document_header {
		font-size: 13px; font-weight: bold;text-align:left;
	}
	
	.hit_document_detail {
		font-size: 12px; text-align: left; 
		/* 		border-top: solid 1px gray; */
	}
	
	.hit_document_overflow {
		overflow-x:  auto; 
	}

</style>											


	<c:if test="${not empty requestScope.Errors}">
		<div style="color:Red;background-color:White;border-style:solid;border-color:Red;border-width:thin;">
			<c:forEach items="${requestScope.Errors}" var="errorMessage">
				<li>
					${errorMessage}
				</li>
			</c:forEach>
			
		</div>
	</c:if>
	
	<c:if test="${not empty requestScope.messages}">
		<div class="border_success">
		<div class="success_left">
		<img align='left'
			src="<c:url value="/resources/images/success.jpg" />" width="30"
			height="30" />
              </div>
	
		
			 <div class="success">
				<table width="600" height="40" border="0" cellpadding="5">
					<tr> 
						<td style="padding-left: 5px;font-weight: bold; vertical-align: top;">
			    			<c:forEach items="${requestScope.messages}" var="infoMessage">
									${infoMessage}
							</c:forEach>
						</td>
					  </tr>
				</table>
			</div>
		</div>
		<br />
	</c:if>
 
					 



	<!--content start -->
	<div id="Xcontent_main">
		<div id="heading_investigation">
			<div id="heading_report">
				<h1 style="font-weight: bold" align="center">
					<span class="table_header">Investigation - Suspended</span>
				</h1>
			</div>
		</div>
  

		<div  class="waitingWhenDoneWaiting" style="display:none;position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: lightblue; z-index: +1000">
			<div style="margin: 10px">
				<input type="button" class="button_grey_2" id="printBtn" name="print" value="Print" />
				&nbsp; 
				<input type="button" class="button_grey_2" id="submitBtn" name="submit" value="Submit" /> 
				&nbsp; 
				<input type="button" class="button_grey_2" id="backBtn" name="back" value="Back" />
			</div>
		</div>

		<div class="c"></div> 
 
				
				
									
					<!-- ================================================= all hits - start ================================================= -->
					<!-- ================================================= all hits - start ================================================= -->
					<!-- ================================================= all hits - start ================================================= -->
					<!-- ================================================= all hits - start ================================================= -->
					<!-- ================================================= all hits - start ================================================= -->
					<!-- ================================================= all hits - start ================================================= -->
					<!-- ================================================= all hits - start ================================================= -->
					<!-- ================================================= all hits - start ================================================= -->
					<!-- ================================================= all hits - start ================================================= -->
					<!-- ================================================= all hits - start ================================================= -->
									
				
			                <%
			                	if ((Integer)request.getAttribute("mainCandidateDataContainerSize") > 0){
			                %>	 
				                <%
				                	int rowCounter = 0;
				                
				                    {
					                	request.setAttribute("rowCtr",rowCounter);
					                	request.setAttribute("rowCtrString",Integer.toString(rowCounter));
				                %>




	<!--1Hit-->
	<div class="waitingWhenDoneWaiting" style="display:none; width: 100%">
		<div style="margin: 15px 0px 5px 0px; width: 100%">
			<!--1Hit_inner-->
			<div style="margin: 5px">





				<div class="theOneRow">
					<div class="theBlockLeft" style="z-index: -100;">
						<div style="z-index: -100;">
							<table width="100%" height="150" border="0" style="z-index: -100;">
								<tr style="z-index: -100;">
									<td align="left" style="text-align: left;z-index: -100;">
										<div class="thumbnail-item-no-margin" style="z-index: -100; width: 180px; max-width: 180px">
											<c:if test="${mainCandidateDataContainer[rowCtr].mainCandidatePhoto!=null}">
												<img
													src="data:image/jpg;base64,${mainCandidateDataContainer[rowCtr].mainCandidatePhoto}"
													 class="thumbnail doGetAJpgSafeVersion" style="max-width:98%;width:98%;margin-left:1%;margin-right1%;z-index: -100;" />
											</c:if>
											<c:if test="${mainCandidateDataContainer[rowCtr].mainCandidatePhoto==null}">
												<img src="<c:url value='/resources/images/No_Image.jpg'/>"
													 class="thumbnail" style="max-width:98%;width:98%;margin-left:1%;margin-right1%;z-index: -100;" />
											</c:if>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</div>

					<div class="theBlockRight">
						<div>
							<table width="100%" height="80" border="0" cellpadding="2"
								class="data_table2">

								<tr>
									<td width="33%" class="text" >Application ID</td>
									<td width="1%">:</td>
									<td width="66%"><c:out value="${jobDetails.transactionId}" /></td>
								</tr>

								<tr>
									<td class="text" >Date Captured</td>
									<td>:</td>
									<td><c:out value="${mainCandidateDataContainer[rowCtr].mainCandidateDateOfApplication}" /></td>
								</tr>

								<tr>
									<td class="text" >Application Type</td>
									<td>:</td>
									<td><c:out value="${mainCandidateDataContainer[rowCtr].mainCandidateTransactionType}" /></td>
								</tr>

								<tr>
									<td class="text" >Application / Passport Status</td>
									<td>:</td>
									<td><c:out value="${mainCandidateDataContainer[rowCtr].mainCandidateApplicationPassportStatus}" /></td>
								</tr>

								<tr>
									<td class="text" >Surname</td>
									<td>:</td>
									<td>${mainCandidateDataContainer[rowCtr].mainCandidateSNShort}</td>
								</tr>

								<tr>
									<td class="text" >Given Name</td>
									<td>:</td>
									<td>${mainCandidateDataContainer[rowCtr].mainCandidateFNShort}</td>
								</tr>

								<tr>
									<td class="text" >Middle Name</td>
									<td>:</td>
									<td>${mainCandidateDataContainer[rowCtr].mainCandidateMNShort}</td>
								</tr>

								<tr>
									<td class="text" >Issuing Authority</td>
									<td>:</td>
									<td>${mainCandidateDataContainer[rowCtr].mainCandidateIssuingAuthority}</td>
								</tr>

							</table>
						</div>
						
					</div>

					<div class="theBlockRight3">
						<div style="margin-top:5px;">
							<div
								style="width: 30%; margin-left: 2%; margin-right: 68%; text-align: left; font-size: 15px; font-weight: bold;">
								<input type="radio" name="decision" class="yes_chk" value="A"
									checked style="width: 20%" /><span
									style="text-align: left; font-size: 15px; font-weight: bold; width: 80%">&nbsp;Approve</span>
							</div>
							<br>
							<div
								style="width: 30%; margin-left: 2%; margin-right: 68%; text-align: left; font-size: 15px; font-weight: bold;">
								<input type="radio" name="decision" class="no_chk" value="R"
									style="width: 20%" /><span
									style="text-align: left; font-size: 15px; font-weight: bold; width: 80%">&nbsp;Reject</span>
							</div>
						</div>
						<div style="margin-top: 20px">
							<div
								style="text-align: left; font-size: 15px; font-weight: bold;">Remarks</div>
							<textarea id="remarks" rows="4" cols="300"
								style="width: 95%; height: 130px;"></textarea>
						</div>
					</div>
				</div>








				<c:if test="${not empty invHitDocuments}">
				
				<div class="theOneRow">
					<div class="theBlockLeft">&nbsp;
					</div>

					<div class="theBlockRight2and3" >
						    
							<div style="margin-top: 10px">
								<div class="hit_document_major_title">Hit Documents</div>
								<div style="margin:2px; border: solid 1px gray">
									<div style="margin:  2px; clear:  both;">
										<div style="clear:  both;">
											<div class="hit_document_content1 hit_document_header">Application ID</div>
											<div class="hit_document_content2 hit_document_header">Passport Number</div>
											<div class="hit_document_content3 hit_document_header">Passport Status</div>
											<div class="hit_document_content4 hit_document_header">Passport Type</div>
											<div class="hit_document_content5 hit_document_header">Cancel Passport</div>
										</div> 
										<div style="clear:  both; min-height:2px">
										</div> 
										<div style="clear:  both">
							    			<c:forEach items="${invHitDocuments}" var="invHitDocument">
												<div style="clear:  both;" class="hit_document_border_at_top">
													<div class="hit_document_content1 hit_document_detail hit_document_overflow">${invHitDocument.applicationId}</div>
													<div class="hit_document_content2 hit_document_detail hit_document_overflow">${invHitDocument.passportNumber}</div>
													<div class="hit_document_content3 hit_document_detail hit_document_overflow">${invHitDocument.passportStatusDescription}</div>
													<div class="hit_document_content4 hit_document_detail hit_document_overflow">${invHitDocument.passportTypeDescription}</div>
													<div class="hit_document_content5 hit_document_detail">
														<c:if test="${invHitDocument.passportStatus eq 'OK'}">
			      											<input type="button" id="cancelDocumentButton" value="Cancel Passport" 
			      													class="button_grey" 
			      													style="margin-top:  1px; margin-bottom:  1px; margin-right:  30%; padding: 1px 0px 1px 0px;font-size: 11px;width: 70%; float: left"
			      													onclick="doCancelDocument('${invHitDocument.applicationId}','${invHitDocument.passportNumber}');"  
			      											/> 
														</c:if> 
														<c:if test="${invHitDocument.passportStatus ne 'OK'}">
			      											<input type="button" id="cancelDocumentButton2" value="Cancel Passport" 
			      													class="button_grey_disabled" disabled 
			      													style="margin-top:  1px; margin-bottom:  1px; margin-right:  30%; padding: 1px 0px 1px 0px;font-size: 11px;width: 70%; float: left"
			      											/> 
														</c:if> 
													</div>
												</div> 
											</c:forEach>
										</div>
										<div style="clear:  both;"> 
										</div>
									</div>
								</div>
							</div> 
						
					</div> 
				</div> 
				
				</c:if> 



			</div>
		</div>
	</div>



	<%
		}
	%>

			                <%
			                	}
			                %>

					<!-- ================================================= all hits - end ================================================= -->
					<!-- ================================================= all hits - end ================================================= -->
					<!-- ================================================= all hits - end ================================================= -->
					<!-- ================================================= all hits - end ================================================= -->
					<!-- ================================================= all hits - end ================================================= -->
					<!-- ================================================= all hits - end ================================================= -->
					<!-- ================================================= all hits - end ================================================= -->
					<!-- ================================================= all hits - end ================================================= -->
					<!-- ================================================= all hits - end ================================================= -->
					<!-- ================================================= all hits - end ================================================= -->
						
						
						 
			 
			
	</div>
	<div style="clear: both">
	</div>
	<div style="margin-top: 40px; height:2px;max-height:2px"> 
	</div>
	
</body>

<!--content end -->

  








<script type="text/javascript">
            $('#submitBtn').click(function(e) { 
            	e.preventDefault(); 
                $( "#dialog-confirm1" ).dialog( "open" );
            });
            
            $('#backBtn').click(function(e) { 
                e.preventDefault();  
                fraudCaseManagementData.action = "${backBtnUrl}";
                fraudCaseManagementData.submit();
            }); 
            $('#printBtn').click(function(e) { 
            	e.preventDefault();  
            	doPrintJob('${jobDetails.workflowJobId}');
            });
            

            function doPrintJob(workflowJobId) {
            
                  var aPage="${pageContext.request.contextPath}"+"/servlet/fraudCaseManagement/print/"+workflowJobId; 
                  
                  newWin=window.open(aPage,'_blank','resizable=no,toolbar=no,menubar=no,scrollbars=yes,location=no,directories=no,status=yes');
                  //newWin.moveTo(30,30);
                  //newWin.resizeTo(screen.availWidth-60,screen.availHeight-60);
                  newWin.moveTo(315,30);
                  newWin.resizeTo(650,screen.availHeight-60);
            }


            
</script>
 


<!-- Jquery Dialog box div for confirmation for Yes Checked---->
<div id="dialog-confirm1" title="Confirmation - Submit Record"
	style="display: none;">
	<form:form modelAttribute="fraudCaseManagementData" name="fraudCaseManagementData"
		id="fraudCaseManagementData">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"> </span>Do you want to submit?
		</p>  
		<form:input path="uploadJobId"       type="hidden" name="uploadJobId"       value="${jobDetails.uploadJobId}" /> 
		<form:input path="remarks"           type="hidden" name="remarks"           value="" />
		<form:input path="decision"          type="hidden" name="decision"          value="" />
	</form:form>
</div> 
  
<!--- Dialog box script for the confirmation  for Yes Selected--->
<script>
  $(function() {
    $( "#dialog-confirm1" ).dialog({
	modal: true,
      autoOpen: false,
	  width : 600,
	  height: 200,
	  resizable: false,
      show: {
        effect: "fade",
        duration: 1000
      },
      hide: {

      },
	   buttons:{
    Ok: function() {
    	 doSubmitTrueHit();
	
    },
	Cancel: function() {
     $(this).dialog("close");
    }
   }
    });
  });
  
   function doSubmitTrueHit() { 
		       
      document.forms["fraudCaseManagementData"].remarks.value  =  document.getElementById("remarks").value;
      document.forms["fraudCaseManagementData"].decision.value =  $('input:radio[name=decision]:checked').val(); 

      document.forms["fraudCaseManagementData"].action = '${submitDecisionUrl}';
  	  document.forms["fraudCaseManagementData"].submit();  
   }

   function trim(str) {
		return str.replace(/^\s+|\s+$/g, "");
	}
  </script>
  
  
  
  
  
  
  
  
  
 


<!-- Jquery Dialog box div for confirmation for Yes Checked---->
<div id="dialog-confirm-cancel-document" title="Confirmation - Cancel Document"
	style="display: none;">
	<form:form modelAttribute="cancelDocumentData" name="cancelDocumentData"
		id="cancelDocumentData">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"> </span>Do you want to cancel passport <span id="passportNumber_spanId"></span>  (Application ID <span id="applicationID_spanId"></span>)?
		</p>  
		<form:input path="uploadJobId"       type="hidden" name="uploadJobId"       value="${jobDetails.uploadJobId}" /> 
		<form:input path="transactionId"     type="hidden" name="transactionId"     value="" />
		<form:input path="passportNo"        type="hidden" name="passportNo"        value="" />
	</form:form>
</div> 
  
<!--- Dialog box script for the confirmation  for Yes Selected--->
<script>

	function doCancelDocument(applicationId, passportNumber) { 
			       
		   $("#passportNumber_spanId").text(passportNumber);
		   $("#applicationID_spanId").text(applicationId);
	       
		   document.forms["cancelDocumentData"].transactionId.value  =  applicationId;
		   document.forms["cancelDocumentData"].passportNo.value     =  passportNumber; 

		   $("#dialog-confirm-cancel-document" ).dialog("open"); 
	} 

	$(function() {
		  
	    $( "#dialog-confirm-cancel-document" ).dialog({
		modal: true,
	      autoOpen: false,
		  width : 600,
		  height: 200,
		  resizable: false,
	      show: {
	        effect: "fade",
	        duration: 1000
	      },
	      hide: {
	
	      },
		   buttons:{
	    Ok: function() {
	    	proceedCancelDocument();
		
	    },
		Cancel: function() {
	     $(this).dialog("close");
	    }
	   }
	    });
	});
	  
	function proceedCancelDocument() { 
	
	      document.forms["cancelDocumentData"].action = '${cancelDocumentDataUrl}';
	  	  document.forms["cancelDocumentData"].submit();  
	} 
  </script>
  

  
<c:url var="homeUrl" value="/servlet/user/home" />  

<script> 
    //var aValidBase64String = "iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==";
    
	function redirectForProcessingTimeout() { 
		alert("The processing of your request failed.  Please try again.");
		var url = '${homeUrl}';
		window.location.href = url;
	}

	$(document).ready(function() { 
		 
        var processingTimeout = setTimeout(redirectForProcessingTimeout, 60000);
		
		$(".doGetAJpgSafeVersion").each(function() {
			var anApplet = document.getElementById('investigationApplet');
			if (!anApplet) {
			    $( this ).css( "visibility", "visible");
				return;
			}
			
			var currentValue=$( this ).attr( "src" );    
			  
			if (!(currentValue.substring(0,27)=='data:image/jpg;base64,     ')) {
			    $( this ).css( "visibility", "visible");
				return;
			}      
			 
			var newSrc = 'data:image/jpg;base64,' + document.investigationApplet.getAJpgSafeVersion(currentValue.substring(27)); 
			 
		    $( this ).attr( "src", newSrc);
		    $( this ).css( "visibility", "visible");
		}); 

	    clearTimeout(processingTimeout);
		$(".waitingWhileWaiting").css( "display", "none");
		$(".waitingWhenDoneWaiting").css( "display", "block");
	});
</script>
 

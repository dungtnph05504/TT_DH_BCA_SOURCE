<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@page import="org.apache.commons.codec.binary.Base64"%> 
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page
	import="com.nec.asia.nic.investigation.controller.InvestigationHit"%>  
<c:url var="backBtnUrl"
	value="/servlet/investigation/investigation" />
	
<c:set var="zero" scope="page" value="0"/>	
<% int zeru = 0;%> 	
	
<script src="<c:url value="/resources/js/mouseover_image.js"/>"
	type="text/javascript"></script> 

													
<style>
	
	.doGetAJpgSafeVersion { 
	     visibility:  hidden;
	} 
	 
	.NotTheSame{
		 color:     #FF0000; 
	}	
													
</style>
													
<style>
	
	.theBlockLeft {
		display: inline-block; 
		width: 43%;
		max-width: 43%;
		margin-right:1%;
		float:left;
	} 
	
	.theBlockRight {
		display: inline-block; 
		width: 43%;
		max-width: 43%;
		margin-right:1%;
		float:left;
	}
	
	.theBlockRightThird {
		display: inline-block; 
		width: 11%;
		max-width: 11%;
		float:right;
	}
	
	.theOneRow {
		margin-top:  5px;
	}

</style>

													
<style>
	
	.noHit_theBlock { 
		width: 43%;
		max-width: 43%;
		margin-right:28%;
		margin-left:28%;
	}  

</style>
	
											
											
											
<style>
     .oneDocArea {
    	 width: 30%;
         margin-right: 2%;
     	 display:  inline-block;
         float:  left;
     }
		
     .theDocArea {
    	 margin-right:4%;
     }

</style>									
										
<body>
	<form:form modelAttribute="investigationHitData" name="investigationHitData"
		id="investigationHitData">



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
	<div id="content_main">
		<div id="heading_investigation">
			<div id="heading_report">
				<h1 style="font-weight: bold" align="center">
					<span class="table_header">Investigation</span>
				</h1>
			</div>
		</div>

		<div class="waitingWhileWaiting" > 
				<div style="font-color:#fff; margin-top:50px; font-size: 1.5em; font-weight: bold; text-align:center" >
					Processing Your Request.  Please Wait.
				</div> 
		</div>

		
		<div class="waitingWhenDoneWaiting" style="display:none; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: lightblue; z-index: +1000">
			<div style="margin: 10px"> 
				<input type="button" class="button_grey_2"              onclick="requestApprove(); return false;" name="approve"  value="Approve" /> 
				&nbsp; 
				<input type="button" class="button_grey_2"              onclick="requestReject() ; return false;" name="reject"   value="Reject" /> 
				&nbsp; 
				<input type="button" class="button_grey_2"              onclick="requestSuspend(); return false;" name="suspend"  value="Suspend" /> 
				&nbsp; 
				<input type="button" class="button_grey_2" id="backBtn"                                           name="back"     value="Back" />
			</div>
		</div>

		<div class="c"></div> 
 
		<script>
		   var numberOfHits = <%=((Integer)request.getAttribute("invHitsSize")).intValue()%>;
		   var currentHitOffered = 0;
		    
		   function goToNextHit() {
			  
		      if(numberOfHits==0){
		    	  return;
		      }

		      if(currentHitOffered == (numberOfHits - 1)){
		    	  return;
		      }
		      
		      currentHitOffered++;

		      eval("document.location.href = '#HitNumber_"+currentHitOffered.toString()+"';");
		 	}
		    
		   function goToPreviousHit() {
			   
		      if(numberOfHits==0){
		    	  return;
		      }

		      if(currentHitOffered == 0){
		    	  return;
		      }
		      
		      currentHitOffered--;
			     
		      eval("document.location.href = '#HitNumber_"+currentHitOffered.toString()+"';");
		 	}
		    
		   function goToFirstHit() {
			  
		      if(numberOfHits==0){
		    	  return;
		      }

		      currentHitOffered=0;

		      eval("document.location.href = '#HitNumber_"+currentHitOffered.toString()+"';");
		 	}
		    
		   function goToLastHit() {
			   
		      if(numberOfHits==0){
		    	  return;
		      }

		      currentHitOffered=(numberOfHits - 1);
			     
		      eval("document.location.href = '#HitNumber_"+currentHitOffered.toString()+"';");
		 	}
		    
		   function setAllValue(allValue) { 
			   
		      if(<c:out value="${invHitsSize}" />==0){
		    	  return;
		      }


			  for (i = 0; i < <%=((Integer)request.getAttribute("invHitsSize")).intValue()%>; i++) { 
					eval("$('input:radio[name="+"duplicateDecision"+"_"+i.toString()+"]').filter('[value="+allValue+"]').prop('checked', true);");
			  } 
	    	  
	    	  return false;
		 	} 
		   
		</script> 
				
				
		
		<div class="waitingWhenDoneWaiting" style="display:none;" >							
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
			                	if ((Integer)request.getAttribute("invHitsSize") > 0){
			                %>	 
				                <%
				                	for (int rowCounter = 0; rowCounter < (Integer)request.getAttribute("invHitsSize"); rowCounter++){
					                	request.setAttribute("rowCtr",rowCounter);
					                	request.setAttribute("rowCtrString",Integer.toString(rowCounter));
				                %>
				                  
    								<a  name="HitNumber_<c:out value="${rowCtr}" />"> </a>
									<input type="hidden" 	name="searchResultId_<c:out value="${rowCtr}" />"    value="${invHits[rowCtr].searchResultId}" 
															id="searchResultId_<c:out value="${rowCtr}" />" 
											/>
									<input type="hidden" name="hitTransactionId_<c:out value="${rowCtr}" />"     value="${invHits[rowCtr].hitTransactionId}" 
															id="hitTransactionId_<c:out value="${rowCtr}" />"
											/>
				                 
									<!-- ======================================= some main fp stuff - start ===================================================== -->
									<!-- ======================================= some main fp stuff - start ===================================================== -->
									<!-- ======================================= some main fp stuff - start ===================================================== -->
									<!-- ======================================= some main fp stuff - start ===================================================== -->
									<!-- ======================================= some main fp stuff - start ===================================================== -->
				                   
										<script type="text/javascript">
											
											$(document).ready(function(){
												setFP2Main<%=rowCounter%>();
											});
										
										   function setFP2Main<%=rowCounter%>() {
												var applet3 = document.getElementById('investigationApplet');
												if (applet3) {
													try {   
														 
														<%    
															try {
																Map<String, String> mainFpIndicatorMap = (Map<String, String>) ((List<InvestigationHit>) (request
																		.getAttribute("invHits"))).get(zeru).mainFpIndicatorMap;
																
																for (int i=1;i<=10;i++){ 
																	String amainfpIndicator = (String) mainFpIndicatorMap.get(Integer.toString(i));
																	String i2=Integer.toString(i);
																	if (i2.length()<2){
																		i2="0"+i2;
																	}
																	  
																	request.setAttribute("mainfp"+i+"Indicator"+"_"+rowCounter+"_", amainfpIndicator);
																	} 
										
															} catch (Exception e) {
																e.printStackTrace();
															}
														%> 
														
													} catch (e) { 
														var errorDetails = '';
														if(e.number)		errorDetails += 'e.number:'+e.number+'\n';
														if(e.name)			errorDetails += 'e.name:'+e.name+'\n';
														if(e.message)		errorDetails += 'e.message:'+e.message+'\n';
														if(e.description)	errorDetails += 'e.description:'+e.description+'\n'; 
													}
												} 
											} 
										
										</script> 
									<!-- ======================================= some main fp stuff - end ===================================================== -->
									<!-- ======================================= some main fp stuff - end ===================================================== -->
									<!-- ======================================= some main fp stuff - end ===================================================== -->
									<!-- ======================================= some main fp stuff - end ===================================================== -->
									<!-- ======================================= some main fp stuff - end ===================================================== -->
				                 
				                 
									<!-- ======================================= some hit fp stuff - start ===================================================== -->
									<!-- ======================================= some hit fp stuff - start ===================================================== -->
									<!-- ======================================= some hit fp stuff - start ===================================================== -->
									<!-- ======================================= some hit fp stuff - start ===================================================== -->
									<!-- ======================================= some hit fp stuff - start ===================================================== -->
				                     
										<script type="text/javascript">
											
											$(document).ready(function(){
												setFP2Hit<%=rowCounter%>();
											});
										
										   function setFP2Hit<%=rowCounter%>() {
												var applet3 = document.getElementById('investigationApplet');
												if (applet3) {
													try {   
														<%    
															try {
																Map<String, String> fpIndicatorMap = (Map<String, String>) ((List<InvestigationHit>) (request
																		.getAttribute("invHits"))).get(rowCounter).hitFpIndicatorMap;
																
																for (int i=1;i<=10;i++){ 
																	String afpIndicator = (String) fpIndicatorMap.get(Integer.toString(i));
																	String i2=Integer.toString(i);
																	if (i2.length()<2){
																		i2="0"+i2;
																	}
																	  
																	request.setAttribute("hitfp"+i+"Indicator"+"_"+rowCounter+"_", afpIndicator);
																	} 
										
															} catch (Exception e) {
																e.printStackTrace();
															}
														%> 
														
													} catch (e) { 
														var errorDetails = '';
														if(e.number)		errorDetails += 'e.number:'+e.number+'\n';
														if(e.name)			errorDetails += 'e.name:'+e.name+'\n';
														if(e.message)		errorDetails += 'e.message:'+e.message+'\n';
														if(e.description)	errorDetails += 'e.description:'+e.description+'\n'; 
													}
												} 
											} 
										
										</script> 
									<!-- ======================================= some hit fp stuff - end ===================================================== -->
									<!-- ======================================= some hit fp stuff - end ===================================================== -->
									<!-- ======================================= some hit fp stuff - end ===================================================== -->
									<!-- ======================================= some hit fp stuff - end ===================================================== -->
									<!-- ======================================= some hit fp stuff - end ===================================================== -->
				                 
									
									<!--1Hit--> 
									<div
										style="margin: 15px 5px 5px 5px; border: 2px solid blue; border-radius: 5px;">
										<!--1Hit_inner-->
										<div style="margin: 5px"> 
											
											<c:if test="${not inv_noHit}">
												<div
													style="margin: 5px 0px 5px 0px">
													<!--1Hit_inner-->
													<div style="margin: 0px">
														<div
															style="align: center; font-weight: bold; height: 27px"
															class="sno">
															<div class="table_header" style="padding-top: 3px">
																Hit&nbsp;<c:out value="${rowCtr + 1}" />&nbsp;of&nbsp;
																<c:out value="${invHitsSize}" />
															</div>
														</div>
													</div>
												</div>
											</c:if>
			

													
											<div class="theOneRow">
												<div 
													<c:if test="${not inv_noHit}"> 
														class="theBlockLeft" 
													</c:if>
													<c:if test="${inv_noHit}"> 
														class="noHit_theBlock" 
													</c:if>
												>
													<table align="left" class="round_border" width="100%"
														 height="27"  border="0">
														<tr>
															<td width="50%" height="25" align="center"
																style="font-weight: bold" class="sno"><span
																class="table_header">Main Candidate</span></td>
														</tr>
													</table>
												</div> 
												
												<c:if test="${not inv_noHit}"> 
													<div class="theBlockRightThird">
														<table align="left" class="round_border" width="100%"
															 height="27"  border="0">
															<tr>
																<td height="25" width="50%" align="center"
																	style="font-weight: bold" class="sno"><span
																	class="table_header">Duplicate?</span></td>
															</tr>
														</table>
													</div>
													
													<div class="theBlockRight" >
														<table align="left" class="round_border" width="100%"
															 height="27"  border="0">
															<tr>
																<td height="25" width="50%" align="center"
																	style="font-weight: bold" class="sno"><span
																	class="table_header">Hit Candidate</span></td>
															</tr>
														</table>
													</div>
												</c:if>
											</div> 			
			
			
											<div class="theOneRow">
												<div 
													<c:if test="${not inv_noHit}"> 
														class="theBlockLeft" 
													</c:if>
													<c:if test="${inv_noHit}"> 
														class="noHit_theBlock" 
													</c:if>
												>
															<table width="100%" height="150" border="0"> 
																<tr>
																	<td  align="left"  style="text-align: left;" style="max-width:45%" >
																		<div   
																			<c:choose>
																				<c:when test="${inv_noHit and not empty invHits[rowCtr].mainCandidatePhoto}">
																										id="image-popup_photoCompare_<c:out value="${rowCtr}" />" 
																										class="thumbnail-item-no-margin onHoverMousePointerThumb" 
											 									</c:when> 
																				<c:otherwise>
																					 					 class="thumbnail-item-no-margin" 	
																				</c:otherwise>
																			</c:choose> 
																		>
																			<c:if test="${invHits[rowCtr].mainCandidatePhoto!=null}">
																				<img
																					src="data:image/jpg;base64,${invHits[rowCtr].mainCandidatePhoto}"
																					width="324" height="405" class="thumbnail " style="max-width:100%" />
																			</c:if>
																			<c:if test="${invHits[rowCtr].mainCandidatePhoto==null}">
																				<img
																					src="<c:url value='/resources/images/No_Image.jpg'/>"
																					width="324" height="405" class="img-border"  style="max-width:100%" /> 
																			</c:if>
																		</div>
																	</td>
																	<td width="50%" valign="top" style="text-align: center;  "  > 
																		<div style="margin-top: 50px; "  
																			<c:choose>
																				<c:when test="${inv_noHit and not empty invHits[rowCtr].mainCandidateSignature}">
																					id="image-popup_signatureCompare_<c:out value="${rowCtr}" />"
																					class="thumbnail-item onHoverMousePointerThumb"
																				</c:when>
																				<c:otherwise>
																					class="thumbnail-item"
																				</c:otherwise>
																			</c:choose> 
																		>
																			<c:if test="${invHits[rowCtr].mainCandidateSignature!=null}">
																				<img id="mainCandSign_Thumb"
																					src="data:image/jpg;base64,${invHits[rowCtr].mainCandidateSignature}"
																					height="60" class="thumbnail "  style="max-width:100%" /> 
																			</c:if>
																			<c:if test="${invHits[rowCtr].mainCandidateSignature==null}">
																				<img
																					src="<c:url value='/resources/images/No_Image.jpg'/>"
																					height="60" class="thumbnail"  style="max-width:100%" />
				
																			</c:if>
																		</div> 
																		<div  style="margin-top: 60px; "  >
																			<table width="100%" border="0" class="t_header" style="width: 96%; margin-left: 2%;margin-right: 2%"> 
																			  
																				<% for (int aCounter=1; aCounter<=10; aCounter++){
																					request.setAttribute("aCounter", Integer.valueOf(aCounter));
																					
																					String amainfpIndicator = (String)request.getAttribute("mainfp"+aCounter+"Indicator"+"_"+rowCounter+"_"); 
																					if (amainfpIndicator==null 
																							|| !amainfpIndicator.equals("N")
																						){
																						continue;	
																					} 
																				%>
																					<tr>
																						<td style="width: 22%; ">
																							&nbsp; 
																						</td> 
																						<td style="width: 45%; text-align: left; border: none;" class="text">
																							<c:choose>
																								<c:when test="${aCounter eq 1}">Right Thumb</c:when> 
																								<c:when test="${aCounter eq 2}">Right Index</c:when> 
																								<c:when test="${aCounter eq 3}">Right Middle</c:when> 
																								<c:when test="${aCounter eq 4}">Right Ring</c:when> 
																								<c:when test="${aCounter eq 5}">Right Little</c:when> 
																								<c:when test="${aCounter eq 6}">Left Thumb</c:when> 
																								<c:when test="${aCounter eq 7}">Left Index</c:when> 
																								<c:when test="${aCounter eq 8}">Left Middle</c:when> 
																								<c:when test="${aCounter eq 9}">Left Ring</c:when> 
																								<c:when test="${aCounter eq 10}">Left Little</c:when> 
																							</c:choose> 
																						</td> 
																				
																						<td
																							style=" width: 10%;text-align: center; border: none; ">
																							<c:forEach var="qualityMap"
																								items="${invHits[rowCtr].mainCandidateFpQuality}">
																								<c:if test="${qualityMap.key eq aCounter}">
																									<c:choose>
																										<c:when
																											test="${qualityMap.key eq aCounter  and  qualityMap.value.fpQuaScr ge qualityMap.value.goodFPQuaScr}">
																											<div id="quality10" title="${qualityMap.value.fpQuaScr}"
																												class=""
																												style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
																												<p class=""
																													style="text-align: center; valign: top; margin: 0; padding: 5px 6px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
																											</div>
																										</c:when>
																										<c:when
																											test="${qualityMap.key eq aCounter  and (qualityMap.value.fpQuaScr ge qualityMap.value.accetableFPQuaScr and qualityMap.value.fpQuaScr lt qualityMap.value.goodFPQuaScr)}">
																											<div id="quality10" title="${qualityMap.value.fpQuaScr}"
																												class=""
																												style="background-image: url('<c:url value="/resources/images/orange_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
																												<p class=""
																													style="text-align: center; valign: top; margin: 0; padding: 5px 6px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
																											</div>
																										</c:when>
																										<c:otherwise>
																											<div id="quality10" title="${qualityMap.value.fpQuaScr}"
																												class=""
																												style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
																												<p class=""
																													style="text-align: center; valign: top; margin: 0; padding: 5px 6px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
																											</div>
																										</c:otherwise>
																									</c:choose>
																								</c:if>
																							</c:forEach>  
																						</td> 
																						<td style="width: 23%; ">
																							&nbsp; 
																						</td> 
																					</tr>   
																				<%}%>
																			</table>
																		</div> 
																	</td>
																</tr>
															</table>
												</div> 
												
												
												
												<c:if test="${not inv_noHit}">
													<div class="theBlockRightThird">
															<br> 
															<div style="width:50%; margin-left: 25%; margin-right:25%; text-align:left; font-size: 15px; font-weight: bold;">
																<input type="radio" name="duplicateDecision_<c:out value="${rowCtr}" />" class="yes_chk" 
																					value="Y" checked style="width:  20%" /><span
																					style="margin-left:2px; text-align:left; font-size: 15px; font-weight: bold;width:  80%">Yes</span>
															</div>	
															<br> 
															<div style="width:50%; margin-left: 25%; margin-right:25%; text-align:left; font-size: 15px; font-weight: bold;">
																<input type="radio" name="duplicateDecision_<c:out value="${rowCtr}" />" class="no_chk" 
																					value="N"  style="width:  20%" /><span
																					style="margin-left:2px; text-align:left; font-size: 15px; font-weight: bold;width:  80%">No</span>
															</div>					
															<br>
															<br>
															<br>
															<div style="text-align:center; font-size: 15px; font-weight: bold;">Remarks</div> 
														<textarea id="remarks_<c:out value="${rowCtr}" />" rows="4" cols="50"  style=" width: 80%; margin-left:  auto; margin-right:  auto; height: 225px;"></textarea>
													</div>
												  
													<div class="theBlockRight">
																<table width="100%" height="150" border="0"> 
																	<tr>
																		<td    align="left"    style=" text-align: left;" style="max-width:45%" >
																			<div
																			 
																				<c:choose>
																					<c:when test="${not empty invHits[rowCtr].hitCandidatePhoto and not empty invHits[rowCtr].mainCandidatePhoto}">
																											id="image-popup_photoCompare_<c:out value="${rowCtr}" />" 
																											class="thumbnail-item-no-margin onHoverMousePointerThumb" 
												 									</c:when> 
																					<c:otherwise>
																						 					 class="thumbnail-item-no-margin" 	
																					</c:otherwise>
																				</c:choose>
																			 
																			>
																				<c:if test="${invHits[rowCtr].hitCandidatePhoto!=null}">
																					<img
																						src="data:image/jpg;base64,${invHits[rowCtr].hitCandidatePhoto}"
																						width="324" height="405" class="thumbnail doGetAJpgSafeVersion"  style="max-width:100%" /> 
																				</c:if>
																				<c:if test="${invHits[rowCtr].hitCandidatePhoto==null}">
																					<img
																						src="<c:url value='/resources/images/No_Image.jpg'/>"
																						width="324" height="405" class="img-border"  style="max-width:100%" />
																				</c:if>
																			</div>
																		</td>
																		<td width="50%"     valign="top"  style="text-align: center; ">
																			<div  style="margin-top: 50px; "  
																			 
																				<c:choose>
																					<c:when test="${not empty invHits[rowCtr].hitCandidateSignature and not empty invHits[rowCtr].mainCandidateSignature}">
																						id="image-popup_signatureCompare_<c:out value="${rowCtr}" />"
																						class="thumbnail-item onHoverMousePointerThumb"
																					</c:when>
																					<c:otherwise>
																						class="thumbnail-item"
																					</c:otherwise>
																				</c:choose>
																			 
																			>
																				<c:if test="${invHits[rowCtr].hitCandidateSignature!=null}">
																					<img
																						src="data:image/jpg;base64,${invHits[rowCtr].hitCandidateSignature}"
																						height="60" class="thumbnail doGetAJpgSafeVersion"  style="max-width:100%"/> 
																				</c:if>
																				<c:if test="${invHits[rowCtr].hitCandidateSignature==null}">
																					<img
																						src=<c:url value='/resources/images/No_Image.jpg'/>
																						height="60" class="thumbnail"  style="max-width:100%"/>
					
																				</c:if>
																			</div> 
																			
																			<div  style="margin-top: 60px; " > 
																				<table width="100%" border="0" class="t_header" style="width: 96%; margin-left: 2%;margin-right: 2%">
																				 
																					<!-- ======================================= fp compare stuff - prepare - start ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - start ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - start ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - start ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - start ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - start ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - start ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - start ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - start ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - start ===================================================== -->
																                 			
												
																						<script type="text/javascript">
																							<%  
																								try {  
																									Map<String, byte[]> mainFpMap = (Map<String, byte[]>) ((List<InvestigationHit>) (request
																											.getAttribute("invHits"))).get(rowCounter).mainFpMap;  
									
																								    for (int i=1;i<=10;i++){
																								    	 String is2= Integer.toString(i) ;
																								    	 is2=((is2.length()==2)?is2:"0"+is2);  
									
																								    	 String aString=Base64.encodeBase64URLSafeString((byte[]) mainFpMap.get(is2));
																										 %>
																											var CompareMainFP<%=is2%>Base64StrSpid<%="_"+rowCounter+"_"%>  = <%=((aString==null)?"null":"'"+aString+"'")%>;
																							    	 	 <%   
																								    }  
																								} catch (Exception e) {
																									e.printStackTrace();
																								}
																							%> 
								
																							<%  
																								try {  
																									Map<String, byte[]> hitFpMap = (Map<String, byte[]>) ((List<InvestigationHit>) (request
																											.getAttribute("invHits"))).get(rowCounter).hitFpMap;
									
																								    for (int i=1;i<=10;i++){
																								    	 String is2= Integer.toString(i) ;
																								    	 is2=((is2.length()==2)?is2:"0"+is2);  
									
																								    	 String aString=Base64.encodeBase64URLSafeString((byte[]) hitFpMap.get(is2));
																										 %>
																											var CompareHitFP<%=is2%>Base64StrSpid<%="_"+rowCounter+"_"%>  = <%=((aString==null)?"null":"'"+aString+"'")%>;
																							    	 	 <%   
																								    }    
																								} catch (Exception e) {
																									e.printStackTrace();
																								}
																							%> 
																							
																							function Compare_<%=rowCounter%>_(index){
																								var mainfp=eval("CompareMainFP"+index+"Base64StrSpid"+"_"+<%=rowCounter%>+"_");
																								var hitfp =eval("CompareHitFP" +index+"Base64StrSpid"+"_"+<%=rowCounter%>+"_");
																								 
																								var compareFingerprints = document.investigationApplet.compareFingerprints(mainfp,hitfp); 
																							}
																						</script>
															  
												
																					<!-- ======================================= fp compare stuff - prepare - end ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - end ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - end ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - end ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - end ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - end ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - end ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - end ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - end ===================================================== -->
																					<!-- ======================================= fp compare stuff - prepare - end ===================================================== -->
																                 			 
																					<% for (int aCounter=1; aCounter<=10; aCounter++){
																						request.setAttribute("aCounter", Integer.valueOf(aCounter));
																						 
																						String ahitfpIndicator  = (String)request.getAttribute("hitfp" +aCounter+"Indicator"+"_"+rowCounter+"_");
																						if (ahitfpIndicator==null  
																								|| !ahitfpIndicator.equals("N") 
																								){
																							continue;
																						} 
																					%>
																						<tr >
																							<td style="width:45%; text-align: left; border: none;" class="text">
																								<c:choose>
																									<c:when test="${aCounter eq 1}">Right Thumb</c:when> 
																									<c:when test="${aCounter eq 2}">Right Index</c:when> 
																									<c:when test="${aCounter eq 3}">Right Middle</c:when> 
																									<c:when test="${aCounter eq 4}">Right Ring</c:when> 
																									<c:when test="${aCounter eq 5}">Right Little</c:when> 
																									<c:when test="${aCounter eq 6}">Left Thumb</c:when> 
																									<c:when test="${aCounter eq 7}">Left Index</c:when> 
																									<c:when test="${aCounter eq 8}">Left Middle</c:when> 
																									<c:when test="${aCounter eq 9}">Left Ring</c:when> 
																									<c:when test="${aCounter eq 10}">Left Little</c:when> 
																								</c:choose> 
																							</td> 
																				
																							<td
																								style="width:10%; text-align: center; border: none;">
																								<c:forEach var="hitQualityMap"
																									items="${invHits[rowCtr].hitCandidateFpQuality}">
																									<c:if test="${hitQualityMap.key eq aCounter}">
																										<c:choose>
																											<c:when
																												test="${hitQualityMap.key eq aCounter   and  hitQualityMap.value.fpQuaScr ge hitQualityMap.value.goodFPQuaScr}">
																												<div id="quality10"
																													title="${hitQualityMap.value.fpQuaScr}" class=""
																													style="background-image: url('<c:url value="/resources/images/green_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
																													<p class=""
																														style="text-align: center; valign: top; margin: 0; padding: 5px 6px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
																												</div>
																											</c:when>
																											<c:when
																												test="${hitQualityMap.key eq aCounter and (hitQualityMap.value.fpQuaScr ge hitQualityMap.value.accetableFPQuaScr and hitQualityMap.value.fpQuaScr lt hitQualityMap.value.goodFPQuaScr)}">
																												<div id="quality10"
																													title="${hitQualityMap.value.fpQuaScr}" class=""
																													style="background-image: url('<c:url value="/resources/images/orange_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
																													<p class=""
																														style="text-align: center; valign: top; margin: 0; padding: 5px 6px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
																												</div>
																											</c:when>
																											<c:otherwise>
																												<div id="quality10"
																													title="${hitQualityMap.value.fpQuaScr}" class=""
																													style="background-image: url('<c:url value="/resources/images/red_circle_new1.jpg " />'); width: 25px; height: 24px; background-repeat: no-repeat; float: left;">
																													<p class=""
																														style="text-align: center; valign: top; margin: 0; padding: 5px 6px 0px 0px; font-size: 12px; font-weight: bold">&nbsp;Q</p>
																												</div>
																											</c:otherwise>
																										</c:choose>
																									</c:if>
																								</c:forEach>  
																							</td>
																				 
																							<c:if test="${not empty invHits[rowCtr].investigationForm}">  
																								<c:forEach varStatus="matchMapCount"
																									var="matchMap"
																									items="${invHits[rowCtr].investigationForm.matchScore}">
																										<c:if test='${matchMapCount.index + 1 eq aCounter}'>  
																											<td style="width:45%; text-align: right; border: none;"
																											 
											
																												<%		
																												//<!-- ======================================= fp compare stuff - do - start ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - start ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - start ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - start ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - start ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - start ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - start ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - start ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - start ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - start ===================================================== -->%>
																							                 			 
																													<%for (int ctr=1;ctr<=10;ctr++){
									
																															if(ctr!=aCounter){
																																continue;
																															} 
																														
																															String ctr2=Integer.toString(ctr);
																															if(ctr2.length()<2){
																																ctr2="0"+ctr2;
																															}
																															%>  
																																<%{%>
																																	<%
																																		String mainfpIndicator = (String)request.getAttribute("mainfp"+ctr+"Indicator"+"_"+rowCounter+"_");
																																	    String hitfpIndicator  = (String)request.getAttribute("hitfp" +ctr+"Indicator"+"_"+rowCounter+"_");
																																		
																																		if (	(mainfpIndicator==null || !mainfpIndicator.equals("N"))
																																				|| 
																																				(hitfpIndicator==null  || !hitfpIndicator.equals("N"))
																																			){ %> 
																																		<%}else{ %>
																																			onclick="Compare_<%=rowCounter%>_('<%=ctr2%>');"
																																			class="onHoverMousePointerThumb"
																																		<%}%>
																																<%}%> 
																													<%}%> 
																													
																												<%
																												//<!-- ======================================= fp compare stuff - do - end ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - end ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - end ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - end ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - end ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - end ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - end ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - end ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - end ===================================================== -->
																												//<!-- ======================================= fp compare stuff - do - end ===================================================== -->%>
																							                 			 
																											
																											>
																												<c:if
																													test='${matchMap.value <60}'>
																														<span style="width: 70%; border: none;" class="text"><c:out
																																value='${matchMap.value}' />%<img
																															src="<%=request.getContextPath()%>/resources/images/Red.jpg"
																															alt="Red icon" title="No Match"  height="12" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>
																												</c:if>
																												<c:if
																													test='${matchMap.value >=60 && matchMap.value <80}'>
																														<span style="width: 70%; border: none;" class="text"><c:out
																																value='${matchMap.value}' />%<img
																															src="<%=request.getContextPath()%>/resources/images/Blue.jpg"
																															alt="Blue icon" title="Possible Match"  height="12" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>  
																												</c:if>
																												<c:if test='${matchMap.value >=80}'>
																														<span style="width: 70%; border: none;" class="text"><c:out
																																value='${matchMap.value}' />%<img
																															src="<%=request.getContextPath()%>/resources/images/Green.jpg"
																															alt="Green icon" title="Highly Match"  height="12" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>
																												</c:if> 
																											</td>
																										</c:if>
																								</c:forEach>
																							</c:if>
																							<c:if test="${empty invHits[rowCtr].investigationForm}"> 
																								<td>
																								</td>	</td>
																							</c:if>
																				
																						</tr>   
																					<%}%>   
											
																				</table>
																			</div> 
																		</td>
																	</tr>
																</table>
													</div>
												</c:if>
											</div>
											
											
											 
			
											
													 
											<div style="clear: both">
											</div>
											<div style="margin-top: 0px; height:1px;max-height:1px"> 
											</div>		 

											 
			
											
													
											<div class="theOneRow" style="margin-top: 5px">
												<div 
													<c:if test="${not inv_noHit}"> 
														class="theBlockLeft" 
													</c:if>
													<c:if test="${inv_noHit}"> 
														class="noHit_theBlock onHoverMousePointerThumb" 
													</c:if>
													 
													<c:if test="${inv_noHit}"> 
														id="details_btn_textualCompare_<c:out value="${rowCtr}" />"
													</c:if>
												> 
															    <table width="100%" height="80" border="0"
																	cellpadding="2" class="data_table2"> 
		
																	<tr>
																		<td valign="top"  width="33%" class="text" >Application ID</td>
																		<td valign="top"  width="1%">:</td>
																		<td valign="top"  width="66%"><c:out value="${jobDetails.transactionId}" />
																		</td>
																	</tr>
		
																	<tr>
																		<td valign="top"  class="text">Date Captured</td>
																		<td valign="top" >:</td>
																		<td valign="top" ><c:out value="${invHits[rowCtr].mainCandidateDateOfApplication}" />
																		</td>
																	</tr>
		
																	<tr>
																		<td valign="top"  class="text">Application Type</td>
																		<td valign="top" >:</td>
																		<td valign="top" ><c:out value="${invHits[rowCtr].mainCandidateTransactionType}" />
																		</td>
																	</tr>
		
																	<tr>
																		<td valign="top"  class="text">Application / Passport Status</td>
																		<td valign="top" >:</td>
																		<td valign="top" ><c:out value="${invHits[rowCtr].mainCandidateApplicationPassportStatus}" />
																		</td>
																	</tr> 
		
																	<tr>
																		<td valign="top"  class="text">Surname</td>
																		<td valign="top" >:</td>
																		<td valign="top" >${invHits[rowCtr].mainCandidateSNShort}
																		</td>
																	</tr>
		
																	<tr>
																		<td valign="top"  class="text">Given Name</td>
																		<td valign="top" >:</td>
																		<td valign="top" >${invHits[rowCtr].mainCandidateFNShort}
																		</td>
																	</tr>
		
																	<tr>
																		<td valign="top"  class="text">Middle Name</td>
																		<td valign="top" >:</td>
																		<td valign="top" >${invHits[rowCtr].mainCandidateMNShort}
																		</td>
																	</tr>
		
																	<tr>
																		<td valign="top"  class="text">Issuing Authority</td>
																		<td valign="top" >:</td>
																		<td valign="top" >${invHits[rowCtr].mainCandidateIssuingAuthority}
																		</td>
																	</tr>
		
																	<tr>
																		<td valign="top"  class="text">Release Date</td>
																		<td valign="top" >:</td>
																		<td valign="top" >${invHits[rowCtr].mainCandidateReleaseDate}
																		</td>
																	</tr>
		
																	<tr>
																		<td valign="top"  class="text">Priority</td>
																		<td valign="top" >:</td>
																		<td valign="top" >${invHits[rowCtr].mainCandidatePriority}
																		</td>
																	</tr>
												  					
												  					<tr>
																		<td colspan="3" style="margin-top:4px; margin-bottom:4px;border-top:solid 1px gray;height:1px"></td>
																	</tr> 
												  					
												  					<c:if test="${empty invHits[rowCtr].mainCandidateInvestigationInformation}">
													  					<tr>
																			<td valign="top"  class="text">&nbsp;</td>
																			<td valign="top" >&nbsp;</td>
																			<td valign="top" >&nbsp;
																			</td>
																		</tr> 
												  					</c:if>
												  					<c:if test="${not empty invHits[rowCtr].mainCandidateInvestigationInformation}">
													  					<tr>
																		<td valign="top"  class="text">Investigation Information</td>
																		<td valign="top" >:</td>
																		<td valign="top" > 
																			<table style="margin: -2px 0px 0px -2px;border-collapse: collapse;padding: 0px;"> 
																				<c:forEach var="mainCandidate_InvestigationInformation" varStatus="anotherCounter"
																						items="${invHits[rowCtr].mainCandidateInvestigationInformation}">
																					<tr> 
																						<td valign="top"  >
																							${mainCandidate_InvestigationInformation.item}
																							<c:if test="${not empty mainCandidate_InvestigationInformation.subItems}">
																								<table>  
																									<c:forEach var="mainCandidate_InvestigationInformationSubData" varStatus="alsoACounter"
																											items="${mainCandidate_InvestigationInformation.subItems}">
																										<tr> 
																											<td valign="top"  >
																												&nbsp;&nbsp;	
																											</td> 
																											<td valign="top"  >
																												${alsoACounter.index+1}.		
																											</td> 
																											<td valign="top"  >
																												&nbsp;&nbsp;	
																											</td> 
																											<td valign="top"  >
																												${mainCandidate_InvestigationInformationSubData.item}	
																											</td> 
																										</tr>   		
																									</c:forEach> 
																								</table> 
																							</c:if>  
																						</td> 
																					</tr>   
																				</c:forEach>  
																			</table> 
																		</td>
																		</tr> 
												  					</c:if>
												  					
												  					<c:if test="${not empty invHits[rowCtr].hitCandidateHitInfo}">
													  					<tr>
																			<td valign="top"  class="text">&nbsp;</td>
																			<td valign="top" >&nbsp;</td>
																			<td valign="top" >&nbsp;
																			</td>
																		</tr> 
												  					</c:if>
		
																</table>  
												</div> 
												
												
												<c:if test="${not inv_noHit}">
													<div class="theBlockRightThird">
														&nbsp;
													</div>
													
													<div class="theBlockRight onHoverMousePointerThumb" id="details_btn_textualCompare_<c:out value="${rowCtr}" />" > 
																    <table height="80" width="100%" border="0"
																			cellPadding="2" class="data_table2">  
	
																		<tr>
																			<td valign="top"  width="33%" class="text">Application ID</td>
																			<td valign="top"  width="1%">:</td>
																			<td valign="top"  width="66%"><c:out value="${invHits[rowCtr].hitCandidateListTransId}" /> 
																			</td>
																		</tr>
																	
																		<tr>
																			<td valign="top"  class="text">Date Captured</td>
																			<td valign="top" >:</td>
																			<td valign="top" ><c:out value="${invHits[rowCtr].hitCandidateDateOfApplication}" />
																			</td>
																		</tr>
																	
																		<tr>
																			<td valign="top"  class="text">Application Type</td>
																			<td valign="top" >:</td>
																			<td valign="top" ><c:out value="${invHits[rowCtr].hitCandidateTransactionType}" />
																			</td>
																		</tr>
																	
																		<tr>
																			<td valign="top"  class="text">Application / Passport Status</td>
																			<td valign="top" >:</td>
																			<td valign="top" ><c:out value="${invHits[rowCtr].hitCandidateApplicationPassportStatus}" />
																			</td>
																		</tr>  
																		<tr>
																			<td valign="top"  class="text">Surname</td>
																			<td valign="top" >:</td>
																			<td valign="top"  colspan="2">${invHits[rowCtr].hitCandidateSNShort}
																			</td>
																		</tr>
			
																		<tr>
																			<td valign="top"  class="text">Given Name</td>
																			<td valign="top" >:</td>
																			<td valign="top"  colspan="2">${invHits[rowCtr].hitCandidateFNShort}
																			</td>
																		</tr>
	
																		<tr>
																			<td valign="top"  class="text">Middle Name</td>
																			<td valign="top" >:</td>
																			<td valign="top" >${invHits[rowCtr].hitCandidateMNShort}
																			</td>
																		</tr>
													  					<tr>
																			<td valign="top"  class="text">Issuing Authority</td>
																			<td valign="top" >:</td>
																			<td valign="top" >${invHits[rowCtr].hitCandidateIssuingAuthority}</td>
																		</tr> 
													  					<tr>
																			<td valign="top"  class="text">Release Date</td>
																			<td valign="top" >:</td>
																			<td valign="top" >${invHits[rowCtr].hitCandidateReleaseDate}</td>
																		</tr> 
													  					<tr>
																			<td valign="top"  class="text">Priority</td>
																			<td valign="top" >:</td>
																			<td valign="top" >${invHits[rowCtr].hitCandidatePriority}</td>
																		</tr> 
													  					
													  					<tr>
																			<td colspan="3" style="margin-top:4px; margin-bottom:4px;border-top:solid 1px gray;height:1px"></td>
																		</tr> 
													  					
													  					<tr>
																			<td valign="top"  class="text">Hit Categories</td>
																			<td valign="top" >:</td>
																			<td valign="top" >${invHits[rowCtr].hitCandidatehitCategories}
																			</td>
																		</tr> 
																		<c:if test="${not empty invHits[rowCtr].hitCandidateHitInfo}">
														  					<tr>
																				<td valign="top"  valign="top" class="text">Hit Information</td>
																				<td valign="top"  valign="top" >:</td>
																				<td valign="top" > 
																					<table style="margin: -2px 0px 0px -2px;border-collapse: collapse;padding: 0px;"> 
																						<c:forEach var="hitCandidateHitInfoData" varStatus="anotherCounter"
																								items="${invHits[rowCtr].hitCandidateHitInfo}">
																							<tr> 
																								<td valign="top"  >
																									${hitCandidateHitInfoData.item}
																									<c:if test="${not empty hitCandidateHitInfoData.subItems}">
																										<br>
																										<table>  
																											<c:forEach var="hitCandidateHitInfoDataSubData" varStatus="alsoACounter"
																													items="${hitCandidateHitInfoData.subItems}">
																												<tr> 
																													<td valign="top"  >
																														${alsoACounter.index+1}.		
																													</td> 
																													<td valign="top"  >
																														${hitCandidateHitInfoDataSubData.item}	
																													</td> 
																												</tr>   		
																											</c:forEach> 
																										</table> 
																									</c:if>  
																								</td> 
																							</tr>   
																						</c:forEach>  
																					</table>
																					
																				</td>
																			</tr>   
																		</c:if> 
																	</table>  
													</div> 
												</c:if> 
											</div> 
													 
											<div style="clear: both">
											</div>
											<div style="margin-top: 0px; height:1px;max-height:1px"> 
											</div>
													
											<div class="theOneRow">
												<div 
													<c:if test="${not inv_noHit}"> 
														class="theBlockLeft" 
													</c:if>
													<c:if test="${inv_noHit}"> 
														class="noHit_theBlock" 
													</c:if>
												> 
												    <table width="100%" height="80" border="0"
														cellpadding="2" class="data_table2">  
														<tr>
															<td>
																<div style="text-align:left; font-size: 15px; font-weight: bold;">Attachments  (${invHits[rowCtr].mainCandidateAttachmentSize})</div>
																
																<c:if test="${invHits[rowCtr].mainCandidateAttachmentSize gt 0}"> 
																	<div class="theDocArea">
																		<c:forEach var="attachmentEntry"
																					items="${invHits[rowCtr].mainCandidateAttachments}"> 
																			<div class="oneDocArea">
																			
																				<c:if test="${empty attachmentEntry.link}"> 
																							<img src="<c:url value='/resources/images/document_icon_low_border.jpg'/>"
																								width="18" height="18" class="ximg-border" style="border:0;" /> &nbsp;<c:out value="${attachmentEntry.attachmentTypeDescription}" />
																					  
																				</c:if>   
																			
																				<c:if test="${not empty attachmentEntry.link}">   
																				    <a href="javascript:showImage('<c:out value="${attachmentEntry.link}" />',
																				    
																						<c:if test="${empty attachmentEntry.imageProperties}">
																							0
																						</c:if> 
																						<c:if test="${not empty attachmentEntry.imageProperties}">
																							<c:out value="${attachmentEntry.imageProperties.width}" />
																						</c:if> 
																				    	,
																						<c:if test="${empty attachmentEntry.imageProperties}">
																							0
																						</c:if> 
																						<c:if test="${not empty attachmentEntry.imageProperties}">
																							<c:out value="${attachmentEntry.imageProperties.height}" />
																						</c:if> 
																				    		)" >
																					    <span class="onHoverMousePointerThumb"> 
																							<img src="<c:url value='/resources/images/document_icon_low_border.jpg'/>"
																								width="18" height="18" class="ximg-border"  style="border:0;" /> &nbsp;<c:out value="${attachmentEntry.attachmentTypeDescription}" />
																					    </span>
																					</a> 
																				</c:if>  
																				
																			</div> 
																		</c:forEach>  
																	</div> 
																</c:if>
																
															</td>
														</tr>
													</table>  
												</div> 
												
												
												<c:if test="${not inv_noHit}">
													<div class="theBlockRightThird">
														&nbsp;
													</div>
													
													<div class="theBlockRight" > 
														&nbsp;
													</div>
												</c:if>
											</div> 
											
											
											
											
											
											
											
										</div>
										<div class="c"></div> 
			
									</div>
									
									 
																		
																		
																		
												
									<!-- textual compare -->			
									<div>						
									
											
											
																
											<!-- textual compare - start -->
											<!-- textual compare - start -->
											<!-- textual compare - start -->
											<!-- textual compare - start -->
											<!-- textual compare - start -->
														 
															
															
													               
													<!--- Dialog box on click of Details button -->
													<div id="dialog_textualCompare_<c:out value="${rowCtr}" />"  
														<c:if test="${not inv_noHit}"> 
															title="Main Candidate And Hit Candidate Details"
														</c:if>
														<c:if test="${inv_noHit}"> 
															title="Main Candidate Details"
														</c:if> 
														align="center" style="display: none;">
														<div id=" ">
															<div style="border: thin;">
																<table width="100%" height="200" border="0">
																	<tr>
																		<td width="50%"  
																			<c:if test="${inv_noHit}"> 
																				colspan="2"
																			</c:if> 
																			height="35" align="center"
																			style="font-weight: bold" class="sno"><div
																			class="table_header" style="text-align:center;" >Main Candidate</div></td> 
																		<c:if test="${not inv_noHit}"> 
																			<td   width="50%"   height="35" align="center"
																				style="font-weight: bold" class="sno"><div
																				class="table_header" style="text-align:center;" >Hit Candidate</div></td> 
																		</c:if> 
																	</tr>
																	<tr>
																		<td colspan="2" class="img-border"><table width="100%" border="0"
																				cellpadding="2"> 
																				<tr>
																					<td  width="20%" height="25">Application ID</td>
																					<td  width="1%" >:</td>
																					<td width="28%" ><c:out value="${jobDetails.transactionId}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td  width="2%" >&nbsp;</td>
																						<td  width="20%" height="25">Application ID</td>
																						<td width="1%" >:</td>
																						<td width="28%" 
																							<c:if test="${jobDetails.transactionId ne invHits[rowCtr].hitCandidateListTransId}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateListTransId}" /> </td>
																				</c:if> 
																				</tr> 
																				<tr>
																					<td height="25">Date Captured</td>
																					<td>:</td>
																					<td><c:out value="${invHits[rowCtr].mainCandidateDateOfApplication}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="25">Date Captured</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidateDateOfApplication ne invHits[rowCtr].hitCandidateDateOfApplication}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateDateOfApplication}" /></td>
																					</c:if> 
																				</tr>
																				<tr>
																					<td height="25">Application Type</td>
																					<td>:</td>
																					<td><c:out value="${invHits[rowCtr].mainCandidateTransactionType}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="25">Application Type</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidateTransactionType ne invHits[rowCtr].hitCandidateTransactionType}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateTransactionType}" /></td>
																					</c:if> 
																				</tr>
																				<tr>
																					<td height="25">Application / Passport Status</td>
																					<td>:</td>
																					<td><c:out value="${invHits[rowCtr].mainCandidateApplicationPassportStatus}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="25">Application / Passport Status</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidateApplicationPassportStatus ne invHits[rowCtr].hitCandidateApplicationPassportStatus}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateApplicationPassportStatus}" /></td>
																					</c:if> 
																				</tr>
																				<tr>
																					<td height="28">Surname</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateSN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="28">Surname</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateSN ne invHits[rowCtr].hitCandidateSN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateSN}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td height="28">Given Name</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateFN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="28">Given Name</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateFN ne invHits[rowCtr].hitCandidateFN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateFN}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td height="28">Middle Name</td>
																					<td>:</td>
																					<td><c:out
																							value="${invHits[rowCtr].mainCandidateMN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="28">Middle Name</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidateMN ne invHits[rowCtr].hitCandidateMN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateMN}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td height="28">Also Known As</td>
																					<td>:</td>
																					<td><c:out
																							value="${invHits[rowCtr].mainCandidateAlsoKnownAs}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="28">Also Known As</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidateAlsoKnownAs ne invHits[rowCtr].hitCandidateAlsoKnownAs}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateAlsoKnownAs}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td height="28">Position</td>
																					<td>:</td>
																					<td><c:out
																							value="${invHits[rowCtr].mainCandidatePosition}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="28">Position</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidatePosition ne invHits[rowCtr].hitCandidatePosition}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidatePosition}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td height="28">Limitation</td>
																					<td>:</td>
																					<td><c:out
																							value="${invHits[rowCtr].mainCandidateLimitation}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="28">Limitation</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidateLimitation ne invHits[rowCtr].hitCandidateLimitation}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateLimitation}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td height="25">Nationality</td>
																					<td>:</td>
																					<td><c:out
																							value="${invHits[rowCtr].mainCandidateNationality}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="25">Nationality</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidateNationality ne invHits[rowCtr].hitCandidateNationality}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateNationality}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td height="25">Place Of Birth</td>
																					<td>:</td>
																					<td><c:out
																							value="${invHits[rowCtr].mainCandidatePlaceOfBirth}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="25">Place Of Birth</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidatePlaceOfBirth ne invHits[rowCtr].hitCandidatePlaceOfBirth}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidatePlaceOfBirth}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td height="25">Issuing Authority</td>
																					<td>:</td>
																					<td>${invHits[rowCtr].mainCandidateIssuingAuthority}</td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="25">Issuing Authority</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidateIssuingAuthority ne invHits[rowCtr].hitCandidateIssuingAuthority}">
																								class="notMatchedStyle" 
																							</c:if>
																							>${invHits[rowCtr].hitCandidateIssuingAuthority}</td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td height="25">Gender</td>
																					<td>:</td>
																					<td ><c:out value="${invHits[rowCtr].mainCandidateGender}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="25">Gender</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateGender ne invHits[rowCtr].hitCandidateGender}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateGender}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td height="25">Date Of Birth</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateDOB}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="25">Date Of Birth</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateDOB ne invHits[rowCtr].hitCandidateDOB}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateDOB}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td height="25">Address</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandiadteStreetName}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="25">Address</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandiadteStreetName ne invHits[rowCtr].hitCandidateStreetName}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateStreetName}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td height="25">Country</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateFlatNo}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="25">Country</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateFlatNo ne invHits[rowCtr].hitCandidateFlatNo}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateFlatNo}" /></td> 
																					</c:if> 
																				</tr> 
																				<tr>
																					<td height="25">Marital Status</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateMaritalStatus}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="25">Marital Status</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateMaritalStatus ne invHits[rowCtr].hitCandidateMaritalStatus}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateMaritalStatus}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td height="25">Father's Name</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateFathersN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="25">Father's Name</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateFathersN ne invHits[rowCtr].hitCandidateFathersN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateFathersN}" /></td>
																					</c:if>  
																				</tr>
																				<tr>
																					<td height="25">Father's Surname</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateFathersSN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="25">Father's Surname</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateFathersSN ne invHits[rowCtr].hitCandidateFathersSN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateFathersSN}" /></td>
																					</c:if> 
																				</tr>
																				<tr>
																					<td height="25">Father's Middle Name</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateFathersMN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td> 
																						<td height="25">Father's Middle Name</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateFathersMN ne invHits[rowCtr].hitCandidateFathersMN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateFathersMN}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td height="25">Mother's Name</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateMothersN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="25">Mother's Name</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateMothersN ne invHits[rowCtr].hitCandidateMothersN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateMothersN}" /></td>
																					</c:if> 
																				</tr>
																				<tr>
																					<td height="25">Mother's Surname</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateMothersSN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td> 
																						<td height="25">Mother's Surname</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateMothersSN ne invHits[rowCtr].hitCandidateMothersSN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateMothersSN}" /></td>
																					</c:if> 
																				</tr>
																				<tr>
																					<td height="25">Mother's Middle Name</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateMothersMN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td> 
																						<td height="25">Mother's Middle Name</td>
																						<td>:</td>
																						<td 
																							<c:if test="${invHits[rowCtr].mainCandidateMothersMN ne invHits[rowCtr].hitCandidateMothersMN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateMothersMN}" /></td>
																					</c:if> 
																				</tr>
																				 
																				 
																				<tr>
																					<td height="25">Spouse First Name</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateSpouseFN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td> 
																						<td height="25">Spouse First Name</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidateSpouseFN ne invHits[rowCtr].hitCandidateSpouseFN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateSpouseFN}" /></td> 
																					</c:if> 
																				</tr>
																				<tr>
																					<td height="25">Spouse Surname</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateSpouseSN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="25">Spouse Surname</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidateSpouseSN ne invHits[rowCtr].hitCandidateSpouseSN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateSpouseSN}" /></td> 
																					</c:if> 
																				</tr> 
																				<tr>
																					<td height="25">Spouse Middle Name</td>
																					<td>:</td>
																					<td ><c:out
																							value="${invHits[rowCtr].mainCandidateSpouseMN}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="25">Spouse Middle Name</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidateSpouseMN ne invHits[rowCtr].hitCandidateSpouseMN}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateSpouseMN}" /></td> 
																					</c:if> 
																				</tr> 
																				<tr>
																					<td height="25">Previous Passport Number</td>
																					<td>:</td>
																					<td><c:out value="${invHits[rowCtr].mainCandidatePreviousPassportNumber}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="25">Passport Number</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidatePreviousPassportNumber ne invHits[rowCtr].hitCandidateDocumentPassportNumber}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateDocumentPassportNumber}" /></td> 
																					</c:if> 
																				</tr>   
																				<tr>
																					<td height="25">Previous Passport Issue Date</td>
																					<td>:</td>
																					<td><c:out value="${invHits[rowCtr].mainCandidatePreviousPassportIssueDate}" /></td>
																					<c:if test="${not inv_noHit}">
																						<td   >&nbsp;</td>
																						<td height="25">Issue Date</td>
																						<td>:</td>
																						<td
																							<c:if test="${invHits[rowCtr].mainCandidatePreviousPassportIssueDate ne invHits[rowCtr].hitCandidateDocumentPassportIssuedDate}">
																								class="notMatchedStyle" 
																							</c:if>
																							><c:out value="${invHits[rowCtr].hitCandidateDocumentPassportIssuedDate}" /></td> 
																					</c:if> 
																				</tr>  
																				<c:if test="${not inv_noHit}"> 
																					<tr>
																						<td height="25">&nbsp;</td>
																						<td>&nbsp;</td>
																						<td>&nbsp;</td> 
																						<td   >&nbsp;</td>
																						<td height="25">Expiry Date</td>
																						<td>:</td>
																						<td><c:out value="${invHits[rowCtr].hitCandidateDocumentPassportExpirationDate}" /></td>
																					</tr>   
																					<tr>
																						<td height="25">&nbsp;</td>
																						<td>&nbsp;</td>
																						<td>&nbsp;</td>
																						<td   >&nbsp;</td>
																						<td height="25">Passport Status</td>
																						<td>:</td>
																						<td><c:out value="${invHits[rowCtr].hitCandidateDocumentPassportStatus}" /></td> 
																					</tr>  
																					<tr>
																						<td height="25">&nbsp;</td>
																						<td>&nbsp;</td>
																						<td>&nbsp;</td>
																						<td   >&nbsp;</td>
																						<td height="25">Passport Type</td>
																						<td>:</td>
																						<td><c:out value="${invHits[rowCtr].hitCandidateDocumentPassportType}" /></td> 
																					</tr> 
																				</c:if> 
													
																			</table></td>
																	</tr>
																</table>
															</div> 
															<div class="c"></div>
													
														</div>
													</div>
													
													
													<script>
													  $(function() {
													    $( "#dialog_textualCompare_<c:out value="${rowCtr}" />" ).dialog({
														modal: true,
													      autoOpen: false,
														  width : 900,
														  <c:if test="${not inv_noHit}"> 
														  		height : 860,
													      </c:if>
														  <c:if test="${inv_noHit}"> 
													  			height : 760,
														  </c:if> 
														  resizable: false,
													      show: {
													        effect: "fade",
													        duration: 1000
													      },
													      hide: {
													       // effect: "explode",
													        duration: 1000
													      }
													    });
													 
													    $( "#details_btn_textualCompare_<c:out value="${rowCtr}" />" ).click(function() {
													      $( "#dialog_textualCompare_<c:out value="${rowCtr}" />" ).dialog( "open" );
													    });
													  });
													
													  </script>
													
													 
									
											
											
											<!-- textual compare - end -->
											<!-- textual compare - end -->
											<!-- textual compare - end -->
											<!-- textual compare - end -->
											<!-- textual compare - end -->



									</div>
 
																		
																		
																		
												
									<!-- photo compare -->			
									<div>						
									
											
											
																
											<!-- photo compare - start -->
											<!-- photo compare - start -->
											<!-- photo compare - start -->
											<!-- photo compare - start -->
											<!-- photo compare - start -->
														 
									
									
												<!-- Jquery Dialog box div for image popup ( Picture )---->
												<div id="dialog-image_photoCompare_<c:out value="${rowCtr}" />"  
													<c:if test="${not inv_noHit}"> 
														title="Facial Comparison" 
													</c:if>
													<c:if test="${inv_noHit}"> 
														title="Face" 
													</c:if> 
													style="display: none;">
													<div class="centerCaption">
														<table>
															<tr>
																<!-- photo dimension: 480 (width) x 640 (height) -->
																<c:if test="${invHits[rowCtr].mainCandidatePhoto != null}">
																	<td><img
																		src="data:image/jpg;base64,${invHits[rowCtr].mainCandidatePhoto}"
																		class="img-border " height="640" width="480" title="Main Candidate" />
																		<div style="font-weight: bold; color: #000;text-align:center;">Main
																			Candidate</div></td>
																</c:if>
																<c:if test="${invHits[rowCtr].mainCandidatePhoto == null}">
																	<td><img src=<c:url value='/resources/images/No_Image.jpg'/>
																		class="img-border" height="640" width="480" title="Main Candidate" />
																		<div style="font-weight: bold; color: #000;text-align:center;">No Image</div></td>
																</c:if>
																<c:if test="${not inv_noHit}">
																	<c:if test="${invHits[rowCtr].hitCandidatePhoto != null}">
																		<td><img
																			src="data:image/jpg;base64,${invHits[rowCtr].hitCandidatePhoto}"
																			class="img-border doGetAJpgSafeVersion" height="640" width="480" title="Hit Candidate" />
																			<div style="font-weight: bold; color: #000;text-align:center;">Hit Candidate</div>
																		</td>
																	</c:if>
																	<c:if test="${invHits[rowCtr].hitCandidatePhoto == null}">
																		<td><img src=<c:url value='/resources/images/No_Image.jpg'/>
																			class="img-border" height="640" width="480" title="Hit Candidate" />
																			<div style="font-weight: bold; color: #000;text-align:center;">No Image</div></td>
																	</c:if>
																</c:if>
															</tr>
														</table>
													</div>
												</div>
												
												
												<!--- Dialog box script for the image pop up ( Picture ) --->
												<script>
												  $(function() {
												    $( "#dialog-image_photoCompare_<c:out value="${rowCtr}" />" ).dialog({
													modal: true,
												      autoOpen: false,
													  width : 1040,
													  height : 730,
													  resizable: false,
												      show: {
												        effect: "fade",
												        duration: 1000
												      },
												      hide: {
												        //effect: "explode",
												        //duration: 1000
												      }
												    });
												 
												    $( "#image-popup_photoCompare_<c:out value="${rowCtr}" />" ).click(function(event) {
												      event.preventDefault();
												      $( "#dialog-image_photoCompare_<c:out value="${rowCtr}" />" ).dialog( "open" );
												    });
												  });
												</script>
											
											<!-- photo compare - end -->
											<!-- photo compare - end -->
											<!-- photo compare - end -->
											<!-- photo compare - end -->
											<!-- photo compare - end -->



									</div>
														
																		
												
									<!-- signature compare -->			
									<div>						
									
											
											
																
											<!-- signature compare - start -->
											<!-- signature compare - start -->
											<!-- signature compare - start -->
											<!-- signature compare - start -->
											<!-- signature compare - start -->
														 
									
									

												<!-- Jquery Dialog box div for image popup ( Signature ) ---->
												<div id="dialog-image_signatureCompare_<c:out value="${rowCtr}" />"  
													<c:if test="${not inv_noHit}"> 
														title="Signature Comparison"
													</c:if>
													<c:if test="${inv_noHit}"> 
														title="Signature"
													</c:if> 
													style="display: none;">
													<div class="centerCaption">
														<table>
															<tr>
																<!-- signature dimension: 453 (width) x 118 (height), thumb print dimension: 512 x 512 -->
																<c:if test="${invHits[rowCtr].mainCandidateSignature != null}">
																	<td style="text-align:center; width: 50%" ><img
																		src="data:image/jpg;base64,${invHits[rowCtr].mainCandidateSignature}"
																		class="img-border " height="180" title="Main Candidate" /><br> <span
																		style="font-weight: bold; color: #000;">Main Candidate</span></td>
																</c:if>
																<c:if test="${invHits[rowCtr].mainCandidateSignature == null}">
																	<td style="text-align:center; width: 50%" ><img src="<c:url value='/resources/images/No_Image.jpg'/>"
																		class="img-border" height="180" title="Main Candidate" /><br>
																		<span style="font-weight: bold; color: #000;">No Image</span></td>
																</c:if>
																<c:if test="${not inv_noHit}"> 
																	<c:if test="${invHits[rowCtr].hitCandidateSignature != null}">
																		<td style="text-align:center; width: 50%" ><img
																			src="data:image/jpg;base64,${invHits[rowCtr].hitCandidateSignature}"
																			class="img-border doGetAJpgSafeVersion" height="180" title="Hit Candidate" /><br> <span
																			style="font-weight: bold; color: #000;">Hit Candidate</span></td>
																	</c:if>
																	<c:if test="${invHits[rowCtr].hitCandidateSignature == null}">
																		<td style="text-align:center; width: 50%" ><img src="<c:url value='/resources/images/No_Image.jpg'/>"
																			class="img-border" height="180" title="Hit Candidate" /><br>
																			<span style="font-weight: bold; color: #000;">No Image</span></td>
																	</c:if>
																</c:if>
															</tr>
														</table>
													</div>
												</div>
									
									
									


												<!--- Dialog box script for the image pop up ( Signature ) --->
												<script>
												  $(function() {
												    $( "#dialog-image_signatureCompare_<c:out value="${rowCtr}" />" ).dialog({
													modal: true,
												      autoOpen: false,
													  width : 950,
													  height : 280,
													  resizable: false,
												      show: {
												        effect: "fade",
												        duration: 1000
												      },
												      hide: {
												        //effect: "explode",
												        //duration: 1000
												      }
												    });
												 
												    $( "#image-popup_signatureCompare_<c:out value="${rowCtr}" />" ).click(function() {
												      $( "#dialog-image_signatureCompare_<c:out value="${rowCtr}" />" ).dialog( "open" );
												    });
												  });
												</script>
									
											
											<!-- signature compare - end -->
											<!-- signature compare - end -->
											<!-- signature compare - end -->
											<!-- signature compare - end -->
											<!-- signature compare - end -->



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
						
						
						 
			
					<c:if test="${not inv_noHit}"> 
			
						<br />
	
						<table style="width: 100%;">
							<tr>
								<td style="width: 10%; text-align: left; border: none;"></td>
	
	
								<td style="width: 10%; text-align: right; border: none;"><span
									style="width: 50%; border: none;" class="text">&nbsp;Highly
										Match&nbsp;<img
										src="<c:out value="${pageContext.request.contextPath}"/>/resources/images/Green.jpg"
										alt="Green icon" height="12" />
								</span>&nbsp;&nbsp;&nbsp; <span style="width: 50%; border: none;"
									class="text">No Match&nbsp;<img
										src="<c:out value="${pageContext.request.contextPath}"/>/resources/images/Red.jpg"
										alt="Red icon" height="12" />
								</span>&nbsp;&nbsp;&nbsp; <span style="width: 50%; border: none;"
									class="text">Possible Match&nbsp;<img
										src="<c:out value="${pageContext.request.contextPath}"/>/resources/images/Blue.jpg"
										alt="Blue icon" height="12" />&nbsp;
								</span></td>
							</tr>
	
						</table>
						<br /> 
					</c:if>
		</div>
	</div>
	
	
	
	<div style="clear: both">
	</div>
	<div style="margin-top: 40px; height:2px;max-height:2px"> 
	</div>

<!--content end -->





<%-- <div id="appletplace">
	<applet name="investigationApplet"
		code="com.nec.asia.applet.InvestigationApplet.class"
		codebase="<%=request.getContextPath()%>/applet"
		id="investigationApplet"
		archive="nic-applet.jar,spid6.jar" height="1"
		width="1" mayscript="mayscript">
		<param name="verify" value="N">
	</applet>
</div> --%>
 





<script type="text/javascript">  
            $('#backBtn').click(function(e) { 
                e.preventDefault();  
                investigationHitData.action = "${backBtnUrl}";
                investigationHitData.submit();
            }); 
</script>

 
		
<%  /* ******************************************************************************************************************** */ %>	
<%  /* *************************************************** reject - start ************************************************* */ %>	
<%  /* ******************************************************************************************************************** */ %>	
			 
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ******************************************** reject - request - start ********************************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
				 
		<script >
		   function requestReject() { 

			  <c:if test="${not inv_noHit}">  
				  //validation
				     var itemsLackingRemarks = getItemsThatShouldHaveRemarksButDontHave();
				     if (itemsLackingRemarks != "") {
					 		alert('Please Provide Remarks For Those Declared As Duplicate Records  (Item/s:  '+itemsLackingRemarks+')'); 
					 		return false;
					 } 
			  </c:if>

			  // 
                $( "#dialog-reject-getRemarks" ).dialog( "open" ); 
		   }
		</script>
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* *********************************************** reject - request - end ********************************************* */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
				 
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ******************************************* reject - get remarks - start ******************************************* */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
				  
		<div id="dialog-reject-getRemarks" title="Reject Application - Input Remarks" style="display: none;"> 
			<div style="margin: 2px; text-align:  center">	
				Remarks
			</div>
			<div>		
				<textarea style="height: 70px; width: 90%; margin-left:  5% " id="jobRejectRemarksHolder" name="jobRejectRemarksHolder" ></textarea>		
			</div> 
		</div> 
		   
		<script>
		  $(function() {
			    $( "#dialog-reject-getRemarks" ).dialog({
					  modal: true,
				      autoOpen: false,
					  width : 600,
					  height: 250,
					  resizable: false,
				      show: {
				        effect: "fade",
				        duration: 1000
				      },
				      hide: { },
				      buttons: {
				         "Continue": function() {
							var inp = $("#jobRejectRemarksHolder").val();
							if ($.trim(inp).length == 0){
								alert ('Please input your remarks.  It is required when rejecting an application.');
								return;
							}    
				        	 
				            $( this ).dialog( "close" );
				            $("#dialog-reject-confirm").dialog( "open" );
				         },
				         "Back": function() {
				            $( this ).dialog( "close" );
				         }
				      } 
			    });
		  }); 
		 </script>

	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ******************************************** reject - get remarks - end ******************************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>	

	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ********************************************* reject - confirm - start ********************************************* */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
		 
		<div id="dialog-reject-confirm" title="Reject Application - Confirmation" style="display: none;"> 
				<p>
					<span class="ui-icon ui-icon-alert"
							style="float: left; margin: 0 7px 20px 0;"> </span>  
							Are you sure you want to reject the application? 
				</p>    
		</div> 
		   
		<script>
		  $(function() {
		    $( "#dialog-reject-confirm" ).dialog({
			modal: true,
		      autoOpen: false,
			  width : 600,
			  height: 200,
			  resizable: false,
		      show: {
		        effect: "fade",
		        duration: 1000
		      },
		      hide: { },
		      buttons: {
			     "Yes": function() {
			          doReject();
			     },
			     "Back": function() {
			          $( this ).dialog( "close" );
			          $("#dialog-reject-getRemarks").dialog( "open" );
			     }
			  } 
		    });
		  });
		</script>
  
				 			
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ********************************************** reject - confirm - end ********************************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>	

	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ************************************************ reject - do - start *********************************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
		<c:url var="rejectUrl"       value="/servlet/investigation/reject" />
		<c:url var="rejectUrl_noHit" value="/servlet/investigation/reject_noHit" />
				 			 
		<script>
		   function doReject() { 
		       
		 	    $(".ui-dialog-buttonpane button:contains('Yes')").button("disable");
		 	    $(".ui-dialog-buttonpane button:contains('Back')").button("disable");
			       
				<c:if test="${not inv_noHit}"> 
					doReject_HaveHit();
				</c:if>
				<c:if test="${inv_noHit}"> 
					doReject_noHit();
				</c:if> 
		   }
		
		   function doReject_noHit() {
				 
				prepareHitInformation_noHit();  
	  
				investigationHitData.action = "${rejectUrl_noHit}"; 
			  	document.forms["investigationHitData"].submit();  
		   }
		  
		   function doReject_HaveHit() { 
		 
			  prepareHitInformation();  
 
		      document.forms["investigationHitData"].action = '${rejectUrl}';  
		  	  document.forms["investigationHitData"].submit();  
		   } 
		 </script>

	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* *********************************************** reject - do - start ************************************************ */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
				 					   
<%  /* ******************************************************************************************************************** */ %>	
<%  /* **************************************************** reject - end ************************************************** */ %>	
<%  /* ******************************************************************************************************************** */ %>	
 	 					    
 	 					    
 
		
<%  /* ******************************************************************************************************************** */ %>	
<%  /* *************************************************** suspend - start ************************************************* */ %>	
<%  /* ******************************************************************************************************************** */ %>	
			 
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ******************************************** suspend - request - start ********************************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
				 
		<script >
		   function requestSuspend() { 

			  <c:if test="${not inv_noHit}">  
				  //validation 
				     //init
					     var errorMsg = ''; 

				     //true hits have remarks
					     var itemsLackingRemarks = getItemsThatShouldHaveRemarksButDontHave();
					     if (itemsLackingRemarks != "") {
					    	 errorMsg = addToMessage(errorMsg, 'Please Provide Remarks For Those Declared As Duplicate Records  (Item/s:  '+itemsLackingRemarks+').');  
						 }  

				     //process validation result
					     if (errorMsg != "") {
					 		alert(errorMsg); 
					 		return false;
						 } 
			  </c:if>

			  // 
                $( "#dialog-suspend-getRemarks" ).dialog( "open" ); 
		   }
		</script>
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* *********************************************** suspend - request - end ********************************************* */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
				  
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ******************************************* suspend - get remarks - start ******************************************* */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
				  
		<div id="dialog-suspend-getRemarks" title="Suspend Application - Input Remarks" style="display: none;"> 
			<div style="margin: 2px; text-align:  center">	
				Remarks
			</div>
			<div>		
				<textarea style="height: 70px; width: 90%; margin-left:  5% " id="jobSuspendRemarksHolder" name="jobSuspendRemarksHolder"  ></textarea>		
			</div> 
		</div> 
		   
		<script>
		  $(function() {
			    $( "#dialog-suspend-getRemarks" ).dialog({
					  modal: true,
				      autoOpen: false,
					  width : 600,
					  height: 250,
					  resizable: false,
				      show: {
				        effect: "fade",
				        duration: 1000
				      },
				      hide: { },
				      buttons: {
				         "Continue": function() {
			        	    <c:if test="${not inv_noHit}"> 
								var inp = $("#jobSuspendRemarksHolder").val();
								if ($.trim(inp).length == 0 && getNumberOfHitsMarkedTrueByUser() == 0){
									alert ('Please input your remarks.  It is required when you did not select a True Hit, and you want to suspend an application.');
									return;
								}    
					  		</c:if>  
				        	 
				            $( this ).dialog( "close" );
				            $("#dialog-suspend-confirm").dialog( "open" );
				         },
				         "Back": function() {
				            $( this ).dialog( "close" );
				         }
				      } 
			    });
		  }); 
		 </script>

	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ******************************************** suspend - get remarks - end ******************************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
				  
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ********************************************* suspend - confirm - start ********************************************* */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
		 
		<div id="dialog-suspend-confirm" title="Suspend Application - Confirmation" style="display: none;"> 
				<p>
					<span class="ui-icon ui-icon-alert"
							style="float: left; margin: 0 7px 20px 0;"> </span>  
							Are you sure you want to suspend the application? 
				</p>    
		</div> 
		   
		<script>
		  $(function() {
		    $( "#dialog-suspend-confirm" ).dialog({
			modal: true,
		      autoOpen: false,
			  width : 600,
			  height: 200,
			  resizable: false,
		      show: {
		        effect: "fade",
		        duration: 1000
		      },
		      hide: { },
		      buttons: {
			     "Yes": function() {
			          doSuspend();
			     },
			     "Back": function() {
			          $( this ).dialog( "close" );
			          $("#dialog-suspend-getRemarks").dialog( "open" );
			     }
			  } 
		    });
		  });
		</script>
  
				 			
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ********************************************** suspend - confirm - end ********************************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>	

	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ************************************************ suspend - do - start *********************************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
		<c:url var="suspendUrl"       value="/servlet/investigation/suspend" />
		<c:url var="suspendUrl_noHit" value="/servlet/investigation/suspend_noHit" />
				 			 
		<script>
		   function doSuspend() { 
		       
		 	    $(".ui-dialog-buttonpane button:contains('Yes')").button("disable");
		 	    $(".ui-dialog-buttonpane button:contains('Back')").button("disable");
			       
				<c:if test="${not inv_noHit}"> 
					doSuspend_HaveHit();
				</c:if>
				<c:if test="${inv_noHit}"> 
					doSuspend_noHit();
				</c:if> 
		   }
		
		   function doSuspend_noHit() {
				 
				prepareHitInformation_noHit();  
	  
				investigationHitData.action = "${suspendUrl_noHit}"; 
			  	document.forms["investigationHitData"].submit();  
		   }
		  
		   function doSuspend_HaveHit() { 
		 
			  prepareHitInformation();  
 
		      document.forms["investigationHitData"].action = '${suspendUrl}';  
		  	  document.forms["investigationHitData"].submit();  
		   } 
		 </script>

	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* *********************************************** suspend - do - start ************************************************ */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
				 					   
<%  /* ******************************************************************************************************************** */ %>	
<%  /* **************************************************** suspend - end ************************************************** */ %>	
<%  /* ******************************************************************************************************************** */ %>	
 	 					   	 					     
 
		
<%  /* ******************************************************************************************************************** */ %>	
<%  /* *************************************************** approve - start ************************************************* */ %>	
<%  /* ******************************************************************************************************************** */ %>	
			 
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ******************************************** approve - request - start ********************************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
				 
		<script >
		   function requestApprove() { 

			   $("#dialog-approve-confirm").dialog( "open" );
		   }
		</script>
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* *********************************************** approve - request - end ********************************************* */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
				 
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ******************************************* approve - get remarks - start ******************************************* */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
				  
		
		   
		<script>
		 
		  function prepare_dialog_approve_getPassportsToCancel() { 

		      for (i = 0; i < <%=((Integer)request.getAttribute("invHitsSize")).intValue()%>; i++) { 
				  if (getARadioData("duplicateDecision",i)=="Y"){ 
					 $(".aPassportForHit_aBlock"+i.toString()).each(function () {  
						 $(this).css("display", "block"); 
			    	 }); 
				  } else {  
					 $(".aPassportForHit_aBlock"+i.toString()).each(function () {  
						 $(this).css("display", "none");
				     }); 
					 $(".aPassportForHit"+i.toString()).each(function () {  
					 	 $(this).prop('checked', false);
				     }); 
				  }
			  }  
		       
		  }
		  
		 </script>

	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ******************************************** approve - get remarks - end ******************************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>	

	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ********************************************* approve - confirm - start ********************************************* */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
		 
		<div id="dialog-approve-confirm" title="Approve Application - Confirmation" style="display: none;"> 
				<p>
					<span class="ui-icon ui-icon-alert"
							style="float: left; margin: 0 7px 20px 0;"> </span>  
							Are you sure you want to approve the application? 
				</p>    
		</div> 
		   
		<script>
		  $(function() {
		    $( "#dialog-approve-confirm" ).dialog({
			modal: true,
		      autoOpen: false,
			  width : 600,
			  height: 200,
			  resizable: false,
		      show: {
		        effect: "fade",
		        duration: 1000
		      },
		      hide: { },
		      buttons: {
			     "Yes": function() {
			          doApprove();
			     },
			     "Back": function() {
			          $( this ).dialog( "close" );
			         
			     }
			  } 
		    });
		  });
		</script>
  
				 			
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ********************************************** approve - confirm - end ********************************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>	

	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ************************************* approve - get passports to cancel - start ************************************ */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
				  
		<style>
			.aTableMainBlock { 
				margin:2px;
				border:  1px solid gray;
				border-radius:  4px;
			} 
			
			.aTableMainBlockInner { 
				margin:2px;
			} 
			
			.aTableRow { 
			} 
			
			.aTableTitle { 
				font-weight:  bold;
			} 
			
			.aTableEmptyBlock {
				display: inline-block; 
				width: 2%;  
				min-width: 2%;  
				float:left;
			} 
			
			.aTableBlock {
				display: inline-block;  
				float:left;
				text-align: center;  
				margin-left:1%;
				margin-right:1%;  
				margin-top:  5px;
				margin-bottom:  5px;  
				/* background-color: #f1f1f1; */
				/* border-radius:  3px; */
			}  
			
			.aTableBlock_overflow { 
				overflow-x:  auto; 
			} 
			
			.aTableBlock_wide { 
				width: 15%; 
				min-width: 15%; 
				max-width: 15%;  
			}  
			
			.aTableBlock_default { 
				width: 10%; 
				min-width: 10%;  
				max-width: 10%; 
			}  
		
		</style>		  
				  
		<div id="dialog-approve-getPassportsToCancel" title="Approve Application - Get Passports To Cancel" style="display: none;"> 
			<div style="margin: 2px; text-align:  center">	
				Below are documents of the True Hits you identified  (you have ticked Yes).
				<br><br>
				Select the documents you want to cancel, if any.
			</div>	 						
			<c:if test="${not inv_noHit}"> 
				<div class="aTableMainBlock">
					<div class="aTableMainBlockInner">
						<div class="aTableRow aTableTitle">  
							<div class="aTableEmptyBlock">&nbsp;</div> 
							<div class="aTableBlock aTableBlock_default">Cancel Passport</div>
							<div class="aTableBlock aTableBlock_wide    aTableBlock_overflow">Application ID</div>
							<div class="aTableBlock aTableBlock_wide    aTableBlock_overflow">Passport Type</div>
							<div class="aTableBlock aTableBlock_default aTableBlock_overflow">Passport Number</div>
							<div class="aTableBlock aTableBlock_default aTableBlock_overflow">Issue Date</div>
							<div class="aTableBlock aTableBlock_default aTableBlock_overflow">Expiry Date</div>
							<div class="aTableBlock aTableBlock_default aTableBlock_overflow">Status</div>
							<div class="aTableEmptyBlock">&nbsp;</div>	 
						</div> 	 
						<div style="clear:  both"> </div> 	 
						<div>	 	 
							<c:forEach var="aHit" items="${invHits}" varStatus="theVarStatusForEachHit"> 
								<c:if test="${not empty aHit.hitCandidatePassports}"> 
									<c:forEach var="aHitCandidatePassport" items="${aHit.hitCandidatePassports}" > 
										<!-- 										<div style="clear:  both">  -->
										<!-- 										</div> 	 -->
										<div style="clear:  both" class="aTableRow aPassportForHit_aBlock${theVarStatusForEachHit.index}">  
											<div class="aTableEmptyBlock">&nbsp;</div> 
											<div class="aTableBlock aTableBlock_default">
												<input type="checkbox" class="aPassportForHit${theVarStatusForEachHit.index}" value="${aHitCandidatePassport.transactionIdAndPassportNo_concatenated}">
											</div>
											<div class="aTableBlock aTableBlock_wide    aTableBlock_overflow"><c:out value="${aHitCandidatePassport.transactionId}" /></div>
											<div class="aTableBlock aTableBlock_wide    aTableBlock_overflow"><c:out value="${aHitCandidatePassport.passportType_forDisplay}" /></div>
											<div class="aTableBlock aTableBlock_default aTableBlock_overflow"><c:out value="${aHitCandidatePassport.passportNo}" /></div>
											<div class="aTableBlock aTableBlock_default aTableBlock_overflow"><fmt:formatDate pattern="dd-MMM-yyyy" value="${aHitCandidatePassport.dateOfIssue}"/></div> 
											<div class="aTableBlock aTableBlock_default aTableBlock_overflow"><fmt:formatDate pattern="dd-MMM-yyyy" value="${aHitCandidatePassport.dateOfExpiry}"/></div>
											<div class="aTableBlock aTableBlock_default aTableBlock_overflow"><c:out value="${aHitCandidatePassport.status_forDisplay}" /></div>
											<div class="aTableEmptyBlock">&nbsp;</div>	 
										</div>   
									</c:forEach> 
								</c:if>	
							</c:forEach>  
						</div> 
						<div style="clear:  both"> </div>
					</div> 
				</div> 
			</c:if>	
		</div> 
		   
		<script>
		  $(function() {
			    $( "#dialog-approve-getPassportsToCancel" ).dialog({
					  modal: true,
				      autoOpen: false,
					  width : 1100,
					  height: 500,
					  resizable: false,
				      show: {
				        effect: "fade",
				        duration: 1000
				      },
				      hide: { },
				      buttons: {
				         "Continue": function() {
				            $( this ).dialog( "close" );
				            
				            if (numberOfPassportsSelectedByUserToCancel() == 0){
				            	$("#dialog-approve-confirm-noPassportsToCancel").dialog( "open" );
				            }else{
				            	$("#dialog-approve-confirm-havePassportsToCancel").dialog( "open" );
				            } 
				         },
				         "Back": function() {
				            $( this ).dialog( "close" );
					        $("#dialog-approve-getRemarks").dialog( "open" );
				         }
				      } 
			    });
		  }); 
		 </script>

	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ************************************** approve - get passports to cancel - end ************************************* */ %>	
	<%  /* ******************************************************************************************************************** */ %>	

	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ************************** approve - dialog-approve-confirm-noPassportsToCancel - start **************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
		 
		<div id="dialog-approve-confirm-noPassportsToCancel" title="Approve Application - Confirmation" style="display: none;"> 
				<p>
					<span class="ui-icon ui-icon-alert"
							style="float: left; margin: 0 7px 20px 0;"> </span>  
							Are you sure you want to approve the application? 
				</p>    
		</div> 
		   
		<script>
		  $(function() {
		    $( "#dialog-approve-confirm-noPassportsToCancel" ).dialog({
			modal: true,
		      autoOpen: false,
			  width : 600,
			  height: 200,
			  resizable: false,
		      show: {
		        effect: "fade",
		        duration: 1000
		      },
		      hide: { },
		      buttons: {
			     "Yes": function() {
			          doApprove();
			     },
			     "Back": function() {
			          $( this ).dialog( "close" );
			          $("#dialog-approve-getPassportsToCancel").dialog( "open" );
			     }
			  } 
		    });
		  });
		</script>
  
				 			
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ************************** approve - dialog-approve-confirm-noPassportsToCancel - end ****************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>	

	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ************************ approve - dialog-approve-confirm-havePassportsToCancel - start **************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
		 
		<div id="dialog-approve-confirm-havePassportsToCancel" title="Approve Application - Confirmation" style="display: none;"> 
				<p>
					<span class="ui-icon ui-icon-alert"
							style="float: left; margin: 0 7px 20px 0;"> </span>  
							Are you sure you want to approve the application, and cancel the passports you selected? 
				</p>    
		</div> 
		   
		<script>
		  $(function() {
		    $( "#dialog-approve-confirm-havePassportsToCancel" ).dialog({
			  modal: true,
		      autoOpen: false,
			  width : 600,
			  height: 200,
			  resizable: false,
		      show: {
		        effect: "fade",
		        duration: 1000
		      },
		      hide: { },
		      buttons: {
			     "Yes": function() {
			          doApprove();
			     },
			     "Back": function() {
			          $( this ).dialog( "close" );
			          $("#dialog-approve-getPassportsToCancel").dialog( "open" );
			     }
			  } 
		    });
		  });
		</script>
   			
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ************************* approve - dialog-approve-confirm-havePassportsToCancel - end ***************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>	

	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ************************************************ approve - do - start *********************************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
		<c:url var="approveUrl"       value="/servlet/investigation/approveStatus" />
		
				 			 
		<script>
		   function doApprove() { 
		       
		 	    $(".ui-dialog-buttonpane button:contains('Yes')").button("disable");
		 	    $(".ui-dialog-buttonpane button:contains('Back')").button("disable");
			       
				
					doApprove_noHit();
				
		   }
		
		   function doApprove_noHit() {
				 
				//prepareHitInformation_noHit();  
	  
				investigationHitData.action = "${approveUrl}"; 
			  	document.forms["investigationHitData"].submit();  
		   }
		  
		  
		 </script>

	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* *********************************************** approve - do - start ************************************************ */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
				 					   
<%  /* ******************************************************************************************************************** */ %>	
<%  /* **************************************************** approve - end ************************************************** */ %>	
<%  /* ******************************************************************************************************************** */ %>	
 	 					   	 					    
 	 					   
 	 					   
 	 					   
<%  /* ******************************************************************************************************************** */ %>	
<%  /* *************************************************** common - start ************************************************* */ %>	
<%  /* ******************************************************************************************************************** */ %>	
		  	
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ******************************************** related to actions - start ******************************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>
 
		<script> 
		  
		   function prepareHitInformation() {
			      
			  var dataDelimiter = '<%=com.nec.asia.nic.investigation.controller.InvestigationController.DATA_DELIMITER%>';
			  
			  var commonJobUploadId = document.forms["investigationHitData"].uploadJobId.value;
		
		      document.forms["investigationHitData"].uploadJobId.value       =  '';
		      document.forms["investigationHitData"].searchResultId.value    =  '';
		      document.forms["investigationHitData"].hitTransactionId.value  =  '';
		      document.forms["investigationHitData"].remarks.value           =  '';
		      document.forms["investigationHitData"].duplicateDecision.value =  '';
			  
			  for (i = 0; i < <%=((Integer)request.getAttribute("invHitsSize")).intValue()%>; i++) { 
				  if (i!=0){
				      document.forms["investigationHitData"].uploadJobId.value       =  document.forms["investigationHitData"].uploadJobId.value       + dataDelimiter;
				      document.forms["investigationHitData"].searchResultId.value    =  document.forms["investigationHitData"].searchResultId.value    + dataDelimiter;
				      document.forms["investigationHitData"].hitTransactionId.value  =  document.forms["investigationHitData"].hitTransactionId.value  + dataDelimiter;
				      document.forms["investigationHitData"].remarks.value           =  document.forms["investigationHitData"].remarks.value           + dataDelimiter;
				      document.forms["investigationHitData"].duplicateDecision.value =  document.forms["investigationHitData"].duplicateDecision.value + dataDelimiter;
				  } 
		
			      document.forms["investigationHitData"].uploadJobId.value       =  document.forms["investigationHitData"].uploadJobId.value       + commonJobUploadId;
			      document.forms["investigationHitData"].searchResultId.value    =  document.forms["investigationHitData"].searchResultId.value    + getAData("searchResultId",i);
			      document.forms["investigationHitData"].hitTransactionId.value  =  document.forms["investigationHitData"].hitTransactionId.value  + getAData("hitTransactionId",i);
			      document.forms["investigationHitData"].remarks.value           =  document.forms["investigationHitData"].remarks.value           + "[" + getAData("remarks",i) + "]";
			      document.forms["investigationHitData"].duplicateDecision.value =  document.forms["investigationHitData"].duplicateDecision.value + getARadioData("duplicateDecision",i);
			  } 
			     
		      document.forms["investigationHitData"].jobApproveRemarks.value =  $('#jobApproveRemarksHolder').val();
		      document.forms["investigationHitData"].jobRejectRemarks.value  =  $('#jobRejectRemarksHolder').val();
		      document.forms["investigationHitData"].jobSuspendRemarks.value =  $('#jobSuspendRemarksHolder').val(); 
		      
		      var transactionIdAndPassportNumbersToCancel_calculated_initialValue = '';
		      var transactionIdAndPassportNumbersToCancel_calculated = transactionIdAndPassportNumbersToCancel_calculated_initialValue;
		      for (i = 0; i < <%=((Integer)request.getAttribute("invHitsSize")).intValue()%>; i++) { 
				  if (getARadioData("duplicateDecision",i)=="Y"){ 
					 $(".aPassportForHit"+i.toString()+":checked").each(function () { 
						 transactionIdAndPassportNumbersToCancel_calculated =  addToMessage_anotherSimple(transactionIdAndPassportNumbersToCancel_calculated, $(this).val(), transactionIdAndPassportNumbersToCancel_calculated_initialValue, '<%=com.nec.asia.nic.comp.trans.dto.InvestigationHitData.transactionIdAndPassportNosToCancel__MULTIPLE_PASSPORT__DELIMITER%>');
			    	 }); 
				  } 
			  } 
		      document.forms["investigationHitData"].transactionIdAndPassportNumbersToCancel.value =  transactionIdAndPassportNumbersToCancel_calculated; 
		   }
		    
		   function prepareHitInformation_noHit() {
			        
		      document.forms["investigationHitData"].jobApproveRemarks.value =  $('#jobApproveRemarksHolder').val();
		      document.forms["investigationHitData"].jobRejectRemarks.value  =  $('#jobRejectRemarksHolder').val();
		      document.forms["investigationHitData"].jobSuspendRemarks.value =  $('#jobSuspendRemarksHolder').val();  
		        
		   } 
		
		   function getNumberOfHitsMarkedTrueByUser() {
			   var numberOfHitsMarkedTrueByUser = 0;
				  
			   for (i = 0; i < <%=((Integer)request.getAttribute("invHitsSize")).intValue()%>; i++) { 
				  if (getARadioData("duplicateDecision",i)=="Y"){
					  numberOfHitsMarkedTrueByUser++;  
				  } 
			   }
			   
			   return numberOfHitsMarkedTrueByUser;
		   }
		
		   function getItemsThatShouldHaveRemarksButDontHave() {
			   var itemsLackingRemarks = "";
			   var msgDelimiter = ", ";
				  
			   for (i = 0; i < <%=((Integer)request.getAttribute("invHitsSize")).intValue()%>; i++) { 
				  if (getARadioData("duplicateDecision",i)=="Y"){
					  if (trim(getAData("remarks",i))==''){ 
						  if (itemsLackingRemarks != ""){ 
							  itemsLackingRemarks = itemsLackingRemarks + msgDelimiter;
					      } 
						  itemsLackingRemarks = itemsLackingRemarks + (i+1).toString();
					  } 
				  } 
			   }
			   
			   return itemsLackingRemarks;
		   }
		
		   function getAData(elementPrefix, itemNumber) {
				 return document.getElementById(elementPrefix+"_"+itemNumber.toString()).value;
		   }
		
		   function getARadioData(elementPrefix, itemNumber) {
				 return eval("$('input:radio[name="+elementPrefix+"_"+itemNumber.toString()+"]:checked').val()");
		   }
		
		   function trim(str) {
				return str.replace(/^\s+|\s+$/g, "");
		   } 
		
		   function numberOfPassportsToActuallyOfferToCancel() { 
			   
			   var numberOfPassportsToActuallyOfferToCancel_count = 0;
			   for (i = 0; i < <%=((Integer)request.getAttribute("invHitsSize")).intValue()%>; i++) { 
				  if (getARadioData("duplicateDecision",i)=="Y"){
					  numberOfPassportsToActuallyOfferToCancel_count = numberOfPassportsToActuallyOfferToCancel_count + ($(".aPassportForHit"+i.toString()).length); 
				  } 
				} 
			   return numberOfPassportsToActuallyOfferToCancel_count;
		   }
		
		   function numberOfPassportsSelectedByUserToCancel() {
			   
			   var numberOfPassportsSelectedByUserToCancel_count = 0;
			   for (i = 0; i < <%=((Integer)request.getAttribute("invHitsSize")).intValue()%>; i++) { 
				  if (getARadioData("duplicateDecision",i)=="Y"){
					  numberOfPassportsSelectedByUserToCancel_count = numberOfPassportsSelectedByUserToCancel_count + 
					      ($(".aPassportForHit"+i.toString()+":checked").length)    
					  ; 
				  } 
				} 
			   return numberOfPassportsSelectedByUserToCancel_count;
		   }
		 </script>

	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ******************************************* related to actions - start ********************************************* */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
				 					  

	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ****************************************** simple message builder - start ****************************************** */ %>
	<%  /* ******************* init is ''                                                                  ******************** */ %>		
	<%  /* ******************* delimiter is '  '                                                           ******************** */ %>		
	<%  /* ******************************************************************************************************************** */ %>

		<script type="text/javascript">  
			function addToMessage(currentMessage, additionalItem) {
				
				var messageDelimiter = '  ';
				
				if(currentMessage == ''){
					return additionalItem;
				}

				return currentMessage + messageDelimiter + additionalItem;
			}
		</script>
	
	<%  /* ******************************************************************************************************************** */ %> 
	<%  /* ******************************************* simple message builder - end ******************************************* */ %>	
	<%  /* ******************************************************************************************************************** */ %>
				 				 	    
	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ************************************* another simple message builder - start *************************************** */ %> 	 	
	<%  /* ******************************************************************************************************************** */ %>

		<script type="text/javascript">  
			function addToMessage_anotherSimple(currentMessage, additionalItem, initialValue, messageDelimiter) { 
				
				if(currentMessage == initialValue){
					return additionalItem;
				}

				return currentMessage + messageDelimiter + additionalItem;
			}
		</script>
	
	<%  /* ******************************************************************************************************************** */ %> 
	<%  /* ************************************* another simple message builder - end ***************************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>
				 				 	    
<%  /* ******************************************************************************************************************** */ %>	
<%  /* **************************************************** common - end ************************************************** */ %>	
<%  /* ******************************************************************************************************************** */ %>	
		  		  
  
  <script>
     
	  function showImage(url, width, height) {
		  
		  if(width == 0){
			  window.open(url,'_blank');
			  return;
		  }
			 
		  var scrollBarWidth  = 20;
		  var scrollBarHeight = 20;
			 
		  width  = width  + scrollBarWidth;
		  height = height + scrollBarHeight; 
		  
		  var leftPosition; 
		  
		  //width 
		  if (width >= (screen.availWidth-60)){ 
			  width        = screen.availWidth-60;
			  leftPosition = 30; 
		  } 
		  else { 
			  leftPosition = (screen.availWidth - width) / 2; 
		  }


		  var topPosition; 
		  
		  //height 
		  if (height >= (screen.availHeight-60)){ 
			  height      = screen.availHeight-60;
			  topPosition = 30; 
		  } 
		  else { 
			  topPosition = (screen.availHeight - height) / 2; 
		  }   
		   
		  newWin=window.open(url,'_blank','left='+leftPosition+',top='+topPosition+',width='+width+',height='+height+',resizable=yes,toolbar=no,menubar=no,scrollbars=yes,location=no,directories=no,status=no');
          return; 
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
  
	
	<form:input    path="uploadJobId"                             type="hidden" name="uploadJobId"                             value="${jobDetails.uploadJobId}" />
	<form:input    path="searchResultId"                          type="hidden" name="searchResultId"                          value="" />
	<form:input    path="hitTransactionId"                        type="hidden" name="hitTransactionId"                        value="" />
	<form:input    path="remarks"                                 type="hidden" name="remarks"                                 value="" />
	<form:input    path="duplicateDecision"                       type="hidden" name="duplicateDecision"                       value="" />
	<form:input    path="jobApproveRemarks"                       type="hidden" name="jobApproveRemarks"                       value="" />
	<form:input    path="jobRejectRemarks"                        type="hidden" name="jobRejectRemarks"                        value="" />
	<form:input    path="jobSuspendRemarks"                       type="hidden" name="jobSuspendRemarks"                       value="" /> 
	<form:input    path="transactionIdAndPassportNumbersToCancel" type="hidden" name="transactionIdAndPassportNumbersToCancel" value="" /> 
</form:form>
</body>
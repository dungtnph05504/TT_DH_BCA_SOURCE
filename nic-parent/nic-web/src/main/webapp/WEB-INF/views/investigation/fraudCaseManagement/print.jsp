<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="com.nec.asia.nic.comp.job.dto.FingerprintDTO"%>
<%@page import="com.nec.asia.nic.utils.DateUtil"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page
	import="com.nec.asia.nic.investigation.controller.InvestigationHit"%>
<c:url var="falseHitUrl" value="/servlet/investigation/falseHit" />
<c:url var="trueHitUrl" value="/servlet/investigation/trueHit" />
<c:url var="submitHitDecisionUrl" value="/servlet/investigation/submitHitDecision" /> 
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
													
</style>
		
													
<style>
	
	.noHit_theBlock { 
		width: 70%;
		max-width: 70%;
		margin-right:14%;
		margin-left:15%;
	}  

</style> 
													
<style>
	
	.theBlockLeft {
		display: inline-block; 
		width: 49%;
		margin-right:1%;
		float:left;
	} 
	
	.theBlockRight {
		display: inline-block; 
		width: 49%;
		margin-left:1%;
		float:right;
	} 
	
	.theOneRow {
		margin-top:  5px;
	}
	
/* 	body { */
/* 		margin:  10mm 20mm 10mm 20mm; */
/* 	}  */
	
	*{
		font-size: 11px;
	}
	
	.boldText {
		font-weight:  bold;
	}
	
	
	
	/* 
	
	.theSingleLineBlockLeft {
		display: inline-block; 
		width: 49%;
		margin-right:1%;
		float:left;
	} 
	
	.theSingleLineBlockRight {
		display: inline-block; 
		width: 49%;
		margin-left:1%;
		float:right;
	} 
	
	.theSingleLineOneRow {
		margin-top:  5px;
	}
	
	 */
	
	
	
</style>
	
											
											
											
<style>
     .theDocArea {
    	 margin-right:4%;
     }
		
     .oneDocArea {
    	 width: 98%;
         margin-right: 2%;  
         text-align:  left;
     }
		
     .doAPageBreakBeforeMe {
    	 page-break-before: always;
     }
      
</style>									
										 


											 
<style> 
		
     .aTable {
		border:  none; 
		border-collapse: collapse;
     }
		
     .aTableRow {
     }
											
</style>						 


  <div class="waitingWhileWaiting" > 
		<div style="font-color:#fff; margin-top:50px; font-size: 1.5em; font-weight: bold; text-align:center" >
			Processing Your Request.  Please Wait.
		</div> 
  </div>

	<!--content start -->
	<div id="xxcontent_main" class="waitingWhenDoneWaiting" style="display:none;" > 
 
 
				
									
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
													{
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
													{
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
										
										<% if (rowCounter>0){%>   
											<div style="width:100%;height:1px; clear:both;" class="doAPageBreakBeforeMe">
											</div> 		
										<%}%>
										
										<div style="margin: 5px 5px 5px 5px;">
											<div style="width: 100%; text-align: center">OFFICE OF CONSULAR
												AFFAIRS</div>
											<div
												style="width: 100%; text-align: center; font-size: 1.1em; font-weight: bold">ELECTRONIC
												PASSPORT PERSONALIZATION CENTER (EPC BSP)</div>
											<div style="width: 100%; text-align: right; font-weight: bold">
												DATE:&nbsp;&nbsp;<span style="text-decoration: underline;"><%=DateUtil.parseDate(new Date(), DateUtil.FORMAT__MONTH_FULL_TEXT__DAY__YEAR_FULL)%></span>
											</div>
											<div style="width: 100%; text-align: center">
												<span
													style="text-decoration: underline; font-size: 1.3em; font-weight: bold;">AFIS
													RESULTS MANAGEMENT REPORT</span>
											</div>
										</div> 				
										
										<div
											style="margin: 15px 5px 5px 5px; border: 1px solid gray; border-radius: 5px;">
											<!--1Hit_inner-->
											<div style="margin: 5px"> 
											
												<c:if test="${not inv_noHit}">
													<div
														style="margin: 5px 0px 0px 0px">
														<!--1Hit_inner--> 
															<div style="width:100%; text-align: center; font-weight: bold " >
																	Hit&nbsp;<c:out value="${rowCtr + 1}" />&nbsp;of&nbsp;
																	<c:out value="${invHitsSize}" />
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
														<table align="left"  width="100%"
															  border="0">
															<tr>
																<td width="50%"  
																	style="font-weight: bold; text-align:center"  ><span
																	 >Main Candidate</span></td>
															</tr>
														</table>
													</div>  
													
													<c:if test="${not inv_noHit}">
														<div class="theBlockRight" >
															<table align="left"   width="100%"
																  border="0">
																<tr>
																	<td width="50%"  
																		style="font-weight: bold; text-align:center"  ><span
																		 >Hit Candidate</span></td>
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
														
														style="text-align: center;"
													>
														<table width="100%" align="center" border="0"> 
															<tr>
																<td  width="30%"   style="text-align: center; vertical-align: top;">
																	<div class="thumbnail-item-no-margin">
																		<c:if test="${invHits[rowCtr].mainCandidatePhoto!=null}">
																			<img class=""
																				src="data:image/jpg;base64,${invHits[rowCtr].mainCandidatePhoto}"
																				width="81" height="101" style="margin-top: 5px; border: 1px solid gray"  />
																		</c:if>
																		<c:if test="${invHits[rowCtr].mainCandidatePhoto==null}">
																			<img
																				src="<c:url value='/resources/images/No_Image.jpg'/>"
																				width="81" height="101"  style="margin-top: 5px; border: 1px solid gray"   /> 
																		</c:if>
																	</div>
																	<div style="margin-top:10px; text-align: center;"  >
																		<c:if test="${invHits[rowCtr].mainCandidateSignature!=null}">
																			<img id="mainCandSign_Thumb" class=""
																				src="data:image/jpg;base64,${invHits[rowCtr].mainCandidateSignature}"
																				height="30" 	style="border:solid 1px gray; margin: 2px;"  /> 
																		</c:if>
																		<c:if test="${invHits[rowCtr].mainCandidateSignature==null}">
																			<img
																				src="<c:url value='/resources/images/No_Image.jpg'/>"
																				height="30" 
																				style="border:solid 1px gray; margin: 2px;" />
			
																		</c:if>
																	</div>
																</td>
																<td width="70%" align="center" style="text-align: center; vertical-align: top;">
	
																	<table width="100%" border="0" > 
																	  
																		<%
																		   Map<String, byte[]> mainFpMap = (Map<String, byte[]>) ((List<InvestigationHit>) (request.getAttribute("invHits"))).get(zeru).mainFpMap;	
																		   Map<String, String> mainFpBase64JpgMap = (Map<String, String>) ((List<InvestigationHit>) (request.getAttribute("invHits"))).get(zeru).mainFpBase64JpgMap;	
																		
																		   for (int aCounter=1; aCounter<=10; aCounter++){
																			request.setAttribute("aCounter", Integer.valueOf(aCounter));
																			
																			
																			String is= Integer.toString(aCounter) ;
																			is=((is.length()==2)?is:"0"+is);  
																    	 
																			
																			String amainfpIndicator = (String)request.getAttribute("mainfp"+aCounter+"Indicator"+"_"+rowCounter+"_"); 
																			if (amainfpIndicator==null 
																					|| !amainfpIndicator.equals("N")
																				){
																				continue;	
																			} 
																		%>
																			<tr> 
																				<td
																					style=" width: 10%;text-align: center; border: none; "> 
																					&nbsp; 
																				</td> 
																				<td style="width: 20%; text-align: left; ">
																					<img src="data:image/jpg;base64,<%=mainFpBase64JpgMap.get(is)%>"
																						   style="border: 1px gray solid; margin: 2px; width:40px; height:40px"
																						    
																						 />
																				</td> 
																				<td
																					style=" width: 1%;text-align: center; border: none; "> 
																					&nbsp; 
																				</td> 
																				<td style="width: 58%; text-align: left; border: none;"  >
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
																					style=" width: 11%;text-align: center; border: none; "> 
																					&nbsp; 
																				</td>
																		
																			</tr>   
																		<%}%>
																	</table>
																</td>
															</tr> 
														</table>
													</div>   
													
													<c:if test="${not inv_noHit}"> 
														<div class="theBlockRight">
															<table width="100%"  border="0"> 
																<tr>
																	<td  width="30%"  style=" text-align: center; vertical-align: top;">
																		<div 
																		>
																			<c:if test="${invHits[rowCtr].hitCandidatePhoto!=null}">
																				<img class="doGetAJpgSafeVersion"
																					src="data:image/jpg;base64,${invHits[rowCtr].hitCandidatePhoto}"
																					width="81" height="101"  style="margin-top: 5px; border: 1px solid gray"  /> 
																			</c:if>
																			<c:if test="${invHits[rowCtr].hitCandidatePhoto==null}">
																				<img
																					src="<c:url value='/resources/images/No_Image.jpg'/>"
																					width="81" height="101"  style="margin-top: 5px; border: 1px solid gray"  />
																			</c:if>
																		</div>
																		<div style="margin-top:10px; text-align: center;" >
																			<c:if test="${invHits[rowCtr].hitCandidateSignature!=null}">
																				<img class="doGetAJpgSafeVersion"
																					src="data:image/jpg;base64,${invHits[rowCtr].hitCandidateSignature}"
																					height="30" 	style="border:solid 1px gray; margin: 2px;"  /> 
																			</c:if>
																			<c:if test="${invHits[rowCtr].hitCandidateSignature==null}">
																				<img
																					src=<c:url value='/resources/images/No_Image.jpg'/>
																					height="30" 	style="border:solid 1px gray; margin: 2px;"  />
				
																			</c:if>
																		</div>
																	</td>
																	<td width="70%"    style="text-align: center; vertical-align: top;">
																		
																		<table width="100%" border="0"   >
																 	 
																			<%  
																			   Map<String, byte[]> hitFpMap          = (Map<String, byte[]>) ((List<InvestigationHit>) (request.getAttribute("invHits"))).get(rowCounter).hitFpMap;	
																			   Map<String, String> hitFpBase64JpgMap = (Map<String, String>) ((List<InvestigationHit>) (request.getAttribute("invHits"))).get(rowCounter).hitFpBase64JpgMap;	
 
																			   for (int aCounter=1; aCounter<=10; aCounter++){
																				request.setAttribute("aCounter", Integer.valueOf(aCounter));
 
																				String is= Integer.toString(aCounter) ;
																				is=((is.length()==2)?is:"0"+is);  
																				 
																				String ahitfpIndicator  = (String)request.getAttribute("hitfp" +aCounter+"Indicator"+"_"+rowCounter+"_");
																				if (ahitfpIndicator==null  
																						|| !ahitfpIndicator.equals("N") 
																						){
																					continue;
																				} 
																			%>
																				<tr >
																					<td
																						style="width:10%; text-align: center; border: none;"> 
																						&nbsp;
																					</td>
																					<td
																						style="width:20% ">  
																						<img src="data:image/jpg;base64,<%=hitFpBase64JpgMap.get(is)%>"
																							   style="border: 1px gray solid; margin: 2px; width:40px; height:40px" 
																							 />
																					</td>
																					<td
																						style="width:1%; text-align: center; border: none;"> 
																						&nbsp;
																					</td>
																					<td style="width:38%; text-align: left; border: none;"  >
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
																						style="width:1%; text-align: center; border: none;"> 
																						&nbsp;
																					</td>
																		 
																					<c:if test="${not empty invHits[rowCtr].investigationForm}"> 
																						<c:forEach varStatus="matchMapCount"
																							var="matchMap"
																							items="${invHits[rowCtr].investigationForm.matchScore}">
																								<c:if test='${matchMapCount.index + 1 eq aCounter}'>  
																									<td style="width:20%; text-align: left; border: none;"
																									  
																									>
																										<c:if
																											test='${matchMap.value >=1 && matchMap.value <=59.99}'>
																												<span style="width: 70%; border: none;"   ><c:out
																														value='${matchMap.value}' />%</span>
																										</c:if>
																										<c:if
																											test='${matchMap.value >=60 && matchMap.value <=79.99}'>
																												<span style="width: 70%; border: none;"  ><c:out
																														value='${matchMap.value}' />%</span>  
																										</c:if>
																										<c:if test='${matchMap.value >=80 && matchMap.value <=100}'>
																												<span style="width: 70%; border: none;"  ><c:out
																														value='${matchMap.value}' />%</span>
																										</c:if>
																										<c:if test='${matchMap.value<=1}'>
																												<span style="width: 70%; border: none;" ><c:out
																														value=' ' /> </span>
																										</c:if> 
																									</td>
																								</c:if>
																						</c:forEach>
																					</c:if>
																					<c:if test="${empty invHits[rowCtr].investigationForm}"> 
																						<td>&nbsp;
																						</td>
																					</c:if>
																					
																					
																					
																					<td
																						style="width:10%; text-align: center; border: none;"> 
																						&nbsp;
																					</td>
																		
																				</tr>   
																			<%}%>   
									
																		</table>
																	</td>
																</tr> 
															</table>
														</div>
													</c:if> 	
												</div> 
	
														
												<div class=" " style="clear:both; width:100%; margin-top: 15px">		
												</div>  
	
												<div class="theOneRow">		
													<div   
														<c:if test="${inv_noHit}"> 
															class="noHit_theBlock" 
														</c:if> 
													> 
													    <table class="aTable "  width="100%" border="0"
															  > 
	
															<tr>
																<td valign="top"  width="24%" class="boldText" >Application ID</td>
																<td valign="top"  width="1%">:</td>
																<td valign="top"  width="24%"><c:out value="${jobDetails.transactionId}" />
																</td>
																<c:if test="${not inv_noHit}">
																	<td valign="top"  width="2%">&nbsp;</td>
																	<td valign="top"  width="24%"  class="boldText" >Application ID</td>
																	<td valign="top"  width="1%">:</td>
																	<td valign="top"  width="24%">
																					<c:choose>
																						<c:when
																							test="${jobDetails.transactionId eq invHits[rowCtr].hitCandidateListTransId}">
																							<c:out value="${invHits[rowCtr].hitCandidateListTransId}" />
																						</c:when>
																						<c:otherwise>
																								<c:out value="${invHits[rowCtr].hitCandidateListTransId}" />
																						</c:otherwise>
																					</c:choose>
																	</td>
																</c:if> 	
															</tr>
	
															<tr style="height: 1px; max-height: 1px"><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td><c:if test="${not inv_noHit}"><td valign="top"  ></td><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td></c:if></tr>
	
															<tr>
																<td valign="top"   class="boldText"  >Applicant ID</td>
																<td valign="top"  >:</td>
																<td valign="top"  ><c:out value="${invHits[rowCtr].mainCandidateApplicantID}" />
																</td>
																<c:if test="${not inv_noHit}">
																	<td valign="top" >&nbsp;</td>
																	<td valign="top"   class="boldText" >Applicant ID</td>
																	<td valign="top" >:</td>
																	<td valign="top" ><c:out value="${invHits[rowCtr].hitCandidateApplicantID}" />
																	</td>
																</c:if> 
															</tr>
	
															<tr style="height: 1px; max-height: 1px"><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td><c:if test="${not inv_noHit}"><td valign="top"  ></td><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td></c:if></tr>
	
															<tr>
																<td valign="top"   class="boldText" >Date Of Application</td>
																<td valign="top" >:</td>
																<td valign="top" ><c:out value="${invHits[rowCtr].mainCandidateDateOfApplication}" />
																</td>
																<c:if test="${not inv_noHit}">
																	<td valign="top" >&nbsp;</td>
																	<td valign="top"   class="boldText" >Date Of Application</td>
																	<td valign="top" >:</td>
																	<td valign="top" ><c:out value="${invHits[rowCtr].hitCandidateDateOfApplication}" />
																	</td>
																</c:if> 
															</tr>
	
															<tr style="height: 1px; max-height: 1px"><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td><c:if test="${not inv_noHit}"><td valign="top"  ></td><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td></c:if></tr>
	
															<tr>
																<td valign="top"   class="boldText" >Application Type</td>
																<td valign="top" >:</td>
																<td valign="top" ><c:out value="${invHits[rowCtr].mainCandidateTransactionType}" />
																</td>
																<c:if test="${not inv_noHit}">
																	<td valign="top" >&nbsp;</td>
																	<td valign="top"   class="boldText" >Application Type</td>
																	<td valign="top" >:</td>
																	<td valign="top" ><c:out value="${invHits[rowCtr].hitCandidateTransactionType}" />
																	</td>
																</c:if> 
															</tr>
	
															<tr style="height: 1px; max-height: 1px"><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td><c:if test="${not inv_noHit}"><td valign="top"  ></td><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td></c:if></tr>
	
															<tr>
																<td valign="top"   class="boldText" >Application / Passport Status</td>
																<td valign="top" >:</td>
																<td valign="top" ><c:out value="${invHits[rowCtr].mainCandidateApplicationPassportStatus}" />
																</td>
																<c:if test="${not inv_noHit}">
																	<td valign="top" >&nbsp;</td>
																	<td valign="top"   class="boldText" >Application / Passport Status</td>
																	<td valign="top" >:</td>
																	<td valign="top" ><c:out value="${invHits[rowCtr].hitCandidateApplicationPassportStatus}" />
																	</td>
																</c:if> 
															</tr> 
	
															<tr style="height: 1px; max-height: 1px"><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td><c:if test="${not inv_noHit}"><td valign="top"  ></td><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td></c:if></tr>
	
															<tr>
																<td valign="top"   class="boldText" >Passport Type</td>
																<td valign="top" >:</td>
																<td valign="top" ><c:out value="${invHits[rowCtr].mainCandidatePassportType}" />
																</td>
																<c:if test="${not inv_noHit}">
																	<td valign="top" >&nbsp;</td>
																	<td valign="top"   class="boldText" >Passport Type</td>
																	<td valign="top" >:</td>
																	<td valign="top" ><c:out value="${invHits[rowCtr].hitCandidatePassportType}" />
																	</td>
																</c:if> 
															</tr> 
	
															<tr style="height: 1px; max-height: 1px"><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td><c:if test="${not inv_noHit}"><td valign="top"  ></td><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td></c:if></tr>
	
															<tr>
																<td valign="top"   class="boldText" >Priority</td>
																<td valign="top" >:</td>
																<td valign="top" ><c:out value="${invHits[rowCtr].mainCandidatePriority}" />
																</td>
																<c:if test="${not inv_noHit}">
																	<td valign="top" >&nbsp;</td>
																	<td valign="top"   class="boldText" >Priority</td>
																	<td valign="top" >:</td>
																	<td valign="top" ><c:out value="${invHits[rowCtr].hitCandidatePriority}" />
																	</td>
																</c:if> 
															</tr> 
	
															<tr style="height: 1px; max-height: 1px"><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td><c:if test="${not inv_noHit}"><td valign="top"  ></td><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td></c:if></tr>
	
															<tr>
																<td valign="top"   class="boldText" >Surname</td>
																<td valign="top" >:</td>
																<td valign="top" >${invHits[rowCtr].mainCandidateSNShort}
																</td>
																<c:if test="${not inv_noHit}">
																	<td valign="top" >&nbsp;</td>
																	<td valign="top"   class="boldText" >Surname</td>
																	<td valign="top" >:</td>
																	<td valign="top"  colspan="2">${invHits[rowCtr].hitCandidateSNShort}
																	</td>
																</c:if> 
															</tr>
	
															<tr style="height: 1px; max-height: 1px"><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td><c:if test="${not inv_noHit}"><td valign="top"  ></td><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td></c:if></tr>
	
															<tr>
																<td valign="top"   class="boldText" >Given Name</td>
																<td valign="top" >:</td>
																<td valign="top" >${invHits[rowCtr].mainCandidateFNShort}
																</td>
																<c:if test="${not inv_noHit}">
																	<td valign="top" >&nbsp;</td>
																	<td valign="top"   class="boldText" >Given Name</td>
																	<td valign="top" >:</td>
																	<td valign="top"  colspan="2">${invHits[rowCtr].hitCandidateFNShort}
																	</td>
																</c:if> 
															</tr>
	
															<tr style="height: 1px; max-height: 1px"><td valign="top"  colspan="3" style="border-top: solid 1px gray"><c:if test="${not inv_noHit}"></td><td valign="top"  ></td><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td></c:if></tr>
	
															<tr>
																<td valign="top"   class="boldText" >Middle Name</td>
																<td valign="top" >:</td>
																<td valign="top" >${invHits[rowCtr].mainCandidateMNShort}
																</td>
																<c:if test="${not inv_noHit}">
																	<td valign="top" >&nbsp;</td>
																	<td valign="top"   class="boldText" >Middle Name</td>
																	<td valign="top" >:</td>
																	<td valign="top" >${invHits[rowCtr].hitCandidateMNShort}
																	</td>
																</c:if> 
															</tr>
	
															<tr style="height: 1px; max-height: 1px"><td valign="top"  colspan="3" style="border-top: solid 1px gray"><c:if test="${not inv_noHit}"></td><td valign="top"  ></td><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td></c:if></tr>
	
															<tr>
																<td valign="top"   class="boldText" >Also Known As</td>
																<td valign="top" >:</td>
																<td valign="top" >${invHits[rowCtr].mainCandidateAlsoKnownAs}
																</td>
																<c:if test="${not inv_noHit}">
																	<td valign="top" >&nbsp;</td>
																	<td valign="top"   class="boldText" >Also Known As</td>
																	<td valign="top" >:</td>
																	<td valign="top" >${invHits[rowCtr].hitCandidateAlsoKnownAs}
																	</td>
																</c:if> 
															</tr>
	
															<tr style="height: 1px; max-height: 1px"><td valign="top"  colspan="3" style="border-top: solid 1px gray"><c:if test="${not inv_noHit}"></td><td valign="top"  ></td><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td></c:if></tr>
	
															<tr>
																<td valign="top"   class="boldText" >Position</td>
																<td valign="top" >:</td>
																<td valign="top" >${invHits[rowCtr].mainCandidatePosition}
																</td>
																<c:if test="${not inv_noHit}">
																	<td valign="top" >&nbsp;</td>
																	<td valign="top"   class="boldText" >Position</td>
																	<td valign="top" >:</td>
																	<td valign="top" >${invHits[rowCtr].hitCandidatePosition}
																	</td>
																</c:if> 
															</tr>
	
															<tr style="height: 1px; max-height: 1px"><td valign="top"  colspan="3" style="border-top: solid 1px gray"><c:if test="${not inv_noHit}"></td><td valign="top"  ></td><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td></c:if></tr>
	
															<tr>
																<td valign="top"   class="boldText" >Limitation</td>
																<td valign="top" >:</td>
																<td valign="top" >${invHits[rowCtr].mainCandidateLimitation}
																</td>
																<c:if test="${not inv_noHit}">
																	<td valign="top" >&nbsp;</td>
																	<td valign="top"   class="boldText" >Limitation</td>
																	<td valign="top" >:</td>
																	<td valign="top" >${invHits[rowCtr].hitCandidateLimitation}
																	</td>
																</c:if> 
															</tr>
	
															<tr style="height: 1px; max-height: 1px"><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td><c:if test="${not inv_noHit}"><td valign="top"  ></td><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td></c:if></tr>
	
															<tr>
																<td valign="top"   class="boldText" >Date Of Birth</td>
																<td valign="top" >:</td>
																<td valign="top" >${invHits[rowCtr].mainCandidateDOB}
																</td>
																<c:if test="${not inv_noHit}">
																	<td valign="top" >&nbsp;</td>
																	<td valign="top"   class="boldText" >Date Of Birth</td>
																	<td valign="top" >:</td>
																	<td valign="top" >${invHits[rowCtr].hitCandidateDOB}
																	</td>
																</c:if> 
															</tr>
	
															<tr style="height: 1px; max-height: 1px"><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td><c:if test="${not inv_noHit}"><td valign="top"  ></td><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td></c:if></tr>
	
															<tr>
																<td valign="top"   class="boldText" >Issuing Authority</td>
																<td valign="top" >:</td>
																<td valign="top" >${invHits[rowCtr].mainCandidateIssuingAuthority}
																</td>
																<c:if test="${not inv_noHit}">
																	<td valign="top" >&nbsp;</td>
																	<td valign="top"   class="boldText" >Issuing Authority</td>
																	<td valign="top" >:</td>
																	<td valign="top" >${invHits[rowCtr].hitCandidateIssuingAuthority}</td>
																</c:if> 
															</tr> 
	
															<tr style="height: 1px; max-height: 1px"><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td><c:if test="${not inv_noHit}"><td valign="top"  ></td><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td></c:if></tr>
	
															<tr>
																<td valign="top"   class="boldText" >Previous Passport Number</td>
																<td valign="top" >:</td>
																<td valign="top" >${invHits[rowCtr].mainCandidatePreviousPassportNumber}
																</td>
																<c:if test="${not inv_noHit}">
																	<td valign="top" >&nbsp;</td>
																	<td valign="top"   class="boldText" >Hit Categories</td>
																	<td valign="top" >:</td>
																	<td valign="top" >${invHits[rowCtr].hitCandidatehitCategories}
																	</td>
										  						</c:if>
															</tr>
	
															<tr style="height: 1px; max-height: 1px"><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td><c:if test="${not inv_noHit}"><td valign="top"  ></td><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td></c:if></tr>
	
															<tr>
																<td valign="top"   class="boldText" >Previous Passport Issue Date</td>
																<td valign="top" >:</td>
																<td valign="top" >${invHits[rowCtr].mainCandidatePreviousPassportIssueDate}
																</td>
																<c:if test="${not inv_noHit}">
																	<td valign="top" >&nbsp;</td>
																	<td valign="top"   class="boldText" >Hit Information</td>
																	<td valign="top" >:</td>
																	<td valign="top" >
									  									<c:if test="${not empty invHits[rowCtr].hitCandidateHitInfo}">
																			<table style="margin: 0px 0px 0px 0px;border-collapse: collapse;padding: 0px;"> 
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
									  									</c:if> 
									  									<c:if test="${empty invHits[rowCtr].hitCandidateHitInfo}">&nbsp;
									  									</c:if> 
																	</td> 
									  							</c:if>
															</tr> 
	
															<tr style="height: 1px; max-height: 1px"><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td><c:if test="${not inv_noHit}"><td valign="top"  ></td><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td></c:if></tr>
										  					
										  					<tr>  
																<td valign="top"   class="boldText" >Investigation Information</td>
																<td valign="top" >:</td>
																<td valign="top" > 
								  									<c:if test="${empty invHits[rowCtr].mainCandidateInvestigationInformation}">&nbsp;
								  									</c:if> 
								  									<c:if test="${not empty invHits[rowCtr].mainCandidateInvestigationInformation}">
																		<table style="margin: 0px 0px 0px 0px;border-collapse: collapse;padding: 0px;"> 
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
								  									</c:if>   
																</td> 
																<c:if test="${not inv_noHit}">
																	<td valign="top" >&nbsp;</td>
																	<td valign="top"   class="boldText" >Investigation Officer Remarks</td>
																	<td valign="top" >:</td>
																	<td valign="top" >${invHits[rowCtr].hitCandidateIORemarks}</td>
										  						</c:if>  
															</tr>
	
															<tr style="height: 1px; max-height: 1px"><td valign="top"  colspan="3" style="border-top: solid 1px gray"></td><c:if test="${not inv_noHit}"><td valign="top"  ></td><td valign="top"  colspan="3" style=" "></td></c:if></tr>
										  					
										  					<tr>  
																<td valign="top"   class="boldText" >Overall Investigation Officer Remarks</td>
																<td valign="top" >:</td>
																<td valign="top" >${invHits[rowCtr].mainCandidateIORemarks}
																</td>   
																<c:if test="${not inv_noHit}">
																	<td valign="top" >&nbsp;</td>
																	<td valign="top"   class="boldText" >&nbsp;</td>
																	<td valign="top" >&nbsp;</td>
																	<td valign="top" >&nbsp;</td>
										  						</c:if>  
															</tr>   
	
														</table>  
													</div> 
													 
												</div> 
																				 
	
												
												
												
												
											</div>
											<div class="c"></div>
				
				
				
				
										</div>
									  
			
				                <%	
				                	}
				                %>
				                  
								<!-- ========================================== documents - start ======================================== --> 
								<!-- ========================================== documents - start ======================================== --> 
								<!-- ========================================== documents - start ======================================== --> 
								<!-- ========================================== documents - start ======================================== --> 
								<!-- ========================================== documents - start ======================================== --> 
										  
									<c:if test="${invHits[0].mainCandidateAttachmentSize gt 0}">
										<c:forEach var="attachmentEntry" varStatus="alsoAnotherCounter"
													items="${invHits[0].mainCandidateAttachments}"> 
												
											<div style="width:100%;height:1px; clear:both;" class="doAPageBreakBeforeMe">
											</div> 
												
											<div style="text-align: center;border:1px solid gray; font-weight:  bold"><c:out value="${attachmentEntry.attachmentTypeDescription}" /></div>
												
											<div style="margin-top:  5px; ">
												<c:if test="${attachmentEntry.imageString!=null}"> 
													<div style="border:1px solid gray"> 
														<img src="data:image/jpg;base64,${attachmentEntry.imageString}" id="imageID${alsoAnotherCounter.index+1}"
														style="vertical-align:  middle; text-align:  center; margin:  auto; max-width:  100%" class="" 
														/>
													</div>
													<script>
														$(function() {
															//mar 16:jesus:2016:comment below first while doing jp2-handling coding
															//resizeAndCenterImage('imageID<c:out value="${alsoAnotherCounter.index+1}" />',<c:out value="${attachmentEntry.imageProperties.width}" />,<c:out value="${attachmentEntry.imageProperties.height}" />);
														});
													</script>
												</c:if> 
												<c:if test="${attachmentEntry.imageString==null}">
														No Image.
												</c:if>
											</div>
											 
										</c:forEach>  
										
										<script> 
											function resizeAndCenterImage(imageID, width, height) { 
												
												var screen_availWidth = 650;
												var scrollbar_width   = 15;
												
												screen_availWidth = screen_availWidth - scrollbar_width;
												 
												var leftPosition; 
												 
												 //width 
												 if (width >= (screen_availWidth)){ 
													 //width        = screen.availWidth;
													leftPosition = 0; 
												 } else { 
													leftPosition = (screen_availWidth - width) / 2; 
												 } 
												
												 //var topPosition = 25;  
												
												 //$("#"+imageID).width(width);
												 $("#"+imageID).css('margin-left', leftPosition);
												 //$("#"+imageID).css('margin-top' , topPosition);
											}
										</script> 
										
									</c:if> 
				                
								<!-- ========================================== documents - end ======================================== --> 
								<!-- ========================================== documents - end ======================================== --> 
								<!-- ========================================== documents - end ======================================== --> 
								<!-- ========================================== documents - end ======================================== --> 
								<!-- ========================================== documents - end ======================================== --> 
				                 
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

<!--content end -->



 
   


  
  
<!--- Dialog box script for the confirmation  for Yes Selected--->
<script> 

   function getAData(elementPrefix, itemNumber) {
		 return document.getElementById(elementPrefix+"_"+itemNumber.toString()).value;
   }

   function getARadioData(elementPrefix, itemNumber) {
		 return eval("$('input:radio[name="+elementPrefix+"_"+itemNumber.toString()+"]:checked').val()");
   }

   function trim(str) {
		return str.replace(/^\s+|\s+$/g, "");
   }
   
   $(function() {	
   });
  </script>
  
   
  
<script> 
    //var aValidBase64String = "iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==";
    
	function redirectForProcessingTimeout() { 
		alert("The processing of your request failed.  Please try again."); 
		window.close();
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
		  
		window.print(); 
		
	});
</script>
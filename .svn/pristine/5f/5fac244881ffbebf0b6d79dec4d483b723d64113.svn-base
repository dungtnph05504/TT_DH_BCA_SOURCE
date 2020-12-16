<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<c:url var="approveUrl" value="/servlet/investigation/approveJob" />
<c:url var="rejectUrl" value="/servlet/investigation/rejectJob" />
<c:url var="investigationJobUrl" value="/servlet/investigation/investigation" />
<c:url var="startInvestigationUrl" value="/servlet/investigation/startinvestigation" />
<c:url var="startInvestigationCompareUrl" value="/servlet/investigation/startInvestigationCompare" />
<c:url var="viewEHUrl" value="/servlet/investigation/viewEHApprove/${jobDetails.transactionId},${jobDetails.uploadJobId}" />
<c:url var="refreshUrl" value="/servlet/investigation/refreshCPDData/${candidateCPDData.nin},${jobDetails.uploadJobId},${jobDetails.transactionId}" />
<c:url var="cancelTransnUrl" value="/servlet/investigation/cancelTransaction" />
<c:url var="remarksTabUrl" value="/servlet/investigation/remarksTab" />
<c:url var="scannedUserDeclPdfDocs"	value="/servlet/investigation/userDeclarationPdf" />
<c:url var="scannedCollSlipPdfDocs"	value="/servlet/investigation/collectionSlipPdf" />
<script src="<c:url value="/resources/js/mouseover_investigation.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/js/jQueryRotate.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/js/multiSelect/jquery.multiselect.js" />" type="text/javascript"></script>
 <!-- jquery plugin to zoom in and out, rotate the scanned document -->
<script src="<c:url value="/resources/js/jquery.iviewer.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/js/jquery.mousewheel.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/js/multiSelect/jquery.multiselect.filter.js" />" type="text/javascript" ></script>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jquery.iviewer.css"/>"></link>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/multiSelect/jquery.multiselect.css"/>"></link>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/multiSelect/jquery.multiselect.filter.css"/>"></link>
	
<form:form modelAttribute="jobDetails" commandName="jobDetails"
 id="form" name="jobDetailsForm" action="" method="post">

	<!-- content start -->
	<div id="content_main">
	   <div class="container">
   <div class="row">
   <div class="roundedBorder ov_hidden">
		<div id="content_inner">
			<div id="heading_investigation">
				<div id="heading_report" align="justify">Điều tra</div>
			</div>

			<br />
			<div id="inner1_main">
				<div id="inner1_main_left">
					<table width="75%" height="100" name="jobDetails" border="0"
						cellpadding="2">
						<tr>
							<td style="font-weight: bold">ID công việc</td>
							<td>:</td>
							<td><c:out value="${jobDetails.uploadJobId}" /> <input
								type="hidden" name="jobId" id="jobId"
								value="${jobDetails.uploadJobId}" />
						</tr>
						<tr>
							<td style="font-weight: bold">Số đơn đăng ký</td>
							<td>:</td>
							<td name="mainTransactionId" id="mainTransactionId"><c:out
									value="${jobDetails.transactionId}" /></td>

						</tr>
						<tr>
							<td style="font-weight: bold">Loại giao dịch</td>
							<td>:</td>
							<td><c:out value="${jobType}" /></td>
						</tr>
						<tr>
							<td style="font-weight: bold">Loại công việc</td>
							<td>:</td>
							<td id="jobTypeValue"><c:out
									value="${jobDetails.investigationType}" /></td>
						</tr>
						<tr>
							<td style="font-weight: bold">Sở hữu</td>
							<td>:</td>
							<td><c:out value="${jobDetails.investigationOfficerId}" />
							</td>
						</tr>
						<tr>
							<td style="font-weight: bold">Ngày nộp đơn</td>
							<td>:</td>
							<!--<td><c:out value="${jobDetails.jobUploadTime}" /> -->
							<td><c:out value="${jobUploadTime}" /></td>
						</tr>
						<tr>
							<td style="font-weight: bold">Số lần truy cập đúng</td>
							<td>:</td>
							<td><c:out value="${countTrueHit}" />
							</td>
						</tr>
						<tr>
							<td style="font-weight: bold">Số lần truy cập sai</td>
							<td>:</td>
							<td><c:out value="${countFalseHit}" />
							</td>
						</tr>
						<tr>
							<td style="font-weight: bold">Đang chờ xử lý</td>
							<td>:</td>
							<td><c:out value="${countPending}" />
							</td>
						</tr>
                        <tr>
							<td style="font-weight: bold">Trạng thái điều tra</td>
							<td>:</td>
							<td>
							<c:choose>
							<c:when test="${jobDetails.investigationStatus eq '00'}">
							<c:out value="Initial" />
							</c:when>
							<c:when test="${jobDetails.investigationStatus eq '01'}">
							<c:out value="In Progress" />
							</c:when>
							<c:when test="${jobDetails.investigationStatus eq '02'}">
							<c:out value="Approved" />
							</c:when>
							<c:when test="${jobDetails.investigationStatus eq '04'}">
							<c:out value="Rejected" />
							</c:when>
							</c:choose>
							</td>
						</tr>
					</table>
				</div>

				<div id="inner1_main_right">
					<c:if test="${candidatePhoto!=null}">
						<img align='right' src="data:image/jpg;base64,${candidatePhoto}"
							height="180" style="position: relative; left: -200px;" />
					</c:if>
					<c:if test="${candidatePhoto==null}">
						<img align='right'
							src=<c:url value='/resources/images/No_Image.jpg'/> height="180"
							width="150" style="position: relative; left: -200px;"
							class="img-border" />
					</c:if>
				</div>
				<div class="c"></div>
				<br />
			</div>
			<br />
			<div id="inner_div" align="right">
				<!-- [chris][20130815] added CC logic - start -->
				<input type="button" id="view_eh_btn" class="button_grey"
					name="btnViewEH" value="Xem xử lý ngoại lệ"
					<c:if test="${!isCCExceptionHandlingFlag}">style="display:none;"</c:if> />
				<!-- [chris][20130815] added CC logic - end -->
				<c:choose>
					<c:when test="${fullyMainCandAmputated eq false}">
						<c:choose>
							<c:when
								test="${jobDetails.investigationType == 'AFIS' and countPending eq 0 and countTrueHit eq 0}">
								<input type="button" id="approve_btn" class="button_grey"
									name="btnNext" value="Phê duyệt" />
								<input type="button" class="button_grey_disable"
									disabled="disabled" name="btnNext" value="Từ chối" />
							</c:when>
							<c:otherwise>
								<input type="button" class="button_grey_disable"
									disabled="disabled" class="button_grey" name="btnNext"
									value="Phê duyệt" />
								<input type="button" id="reject_btn" class="button_grey"
									name="btnNext" value="Từ chối" />
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<input type="button" id="approve_btn" class="button_grey"
							name="btnNext" value="Phê duyệt" />
						<input type="button" id="reject_btn" class="button_grey"
							name="btnNext" value="Từ chối" />
					</c:otherwise>
				</c:choose>

				<input type="button" id="cancel_btn" class="button_grey"
					name="btnNext" value="H" />
				<input type="button" id="cancel_opn_btn" class="button_grey"
					name="btnNext" value="Cancel Transaction" style="padding-right: 10px;"/>
			</div>
		</div>
		<div id="TabbedPanels1" class="TabbedPanels">
			<ul class="TabbedPanelsTabGroup">
				<c:choose>
					<c:when
						test="${fullyMainCandAmputated eq false or empty fullyMainCandAmputated}">
						<c:if test="${jobDetails.investigationType == 'AFIS' || jobDetails.investigationType == 'PERSO'}">
							<li id="hitRecordsTab" class="TabbedPanelsTab" tabindex="0">Hit	Records</li>
						</c:if>
						<li class="TabbedPanelsTab" tabindex="1">Demographic Data</li>
						<li class="TabbedPanelsTab" tabindex="2">Remarks</li>
						<li class="TabbedPanelsTab" tabindex="3">Reference Document</li>
						<li class="TabbedPanelsTab" tabindex="4">Perso</li>
						<c:if test="${userRole eq 'SUPERVISOR'}">
						<li class="TabbedPanelsTab" tabindex="5">Rejected Info</li>
						</c:if>
					</c:when>
					<c:otherwise>
						<li class="TabbedPanelsTab" tabindex="1">Transaction Info - Fully Amputated Case</li>
						<li class="TabbedPanelsTab" tabindex="2">Remarks</li>
						<li class="TabbedPanelsTab" tabindex="3">Reference Document</li>
						<li class="TabbedPanelsTab" tabindex="4">Perso</li>
					</c:otherwise>
				</c:choose>
			</ul>
			<div class="TabbedPanelsContentGroup">
				<c:if test="${fullyMainCandAmputated eq false or empty fullyMainCandAmputated}">
					<c:if test="${jobDetails.investigationType == 'AFIS' || jobDetails.investigationType == 'PERSO'}">
						<c:if test="${empty hitCandidateList}">
							<span style="font-size: 15px; font-weight: bold;">No Hit Records for this main candidate.</span>
						</c:if>
						<c:if test="${not empty hitCandidateList}">
							<div class="TabbedPanelsContent">
								<br />
								<div>
									<table id="box1_table" width="100%" border="0" cellpadding="0"
										cellspacing="0" bordercolor="#333333"
										style="border: 0px; padding: 5px;">
										<tr>
											<td width="10%" class="sno" style="font-weight: bold;"
												height="30px"><span class="table_header">NIN</span></td>
											<td width="10%" class="sno"
												style="font-weight: bold; padding-left: 45px;"
												height="30px;"><span class="table_header">Application
													Ref No</span></td>
											<td width="10%" class="sno" style="font-weight: bold; padding-left: 35px;"><span
												class="table_header">First Name</span></td>
											<td width="10%" class="sno"
												style="font-weight: bold; padding-left: 20px;"><span
												class="table_header">Surname</span></td>
											<td width="10%" class="sno"
												style="font-weight: bold; padding-left: 15px;"><span
												class="table_header">Decision</span></td>
											<td width="10%" class="sno"
												style="font-weight: bold; padding-left: 5px;"><span
												class="table_header">Hit Fingers</span></td>
											<td width="10%" class="sno"
												style="font-weight: bold; padding-left: 5px;"><span
												class="table_header">Matching Score</span></td>
											<td width="10%" class="sno"
												style="font-weight: bold; padding-right: 10px;"><span
												class="table_header">CCN</span></td>
											<td width="10%" class="sno"
												style="font-weight: bold; padding-left: 25px;"><span
												class="table_header">Card Status</span></td>
											<td width="10%" class="sno"
												style="font-weight: bold; padding-left: 5px;"><span
												class="table_header">Photo</span></td>
											<td width="10%" class="sno" style="font-weight: bold;"><span
												class="table_header" style="padding-right: 20px;">Remarks</span>
											</td>
										</tr>
									</table>
								</div>
								<div id="mycustomscroll" class='flexcroll'>
									<table id="box1_table" width="100%" border="1" cellpadding="0"
										cellspacing="0" bordercolor="#333333" class="data_table">
										<c:if test="${hitCandidateList!=null}">
											<c:forEach var="c" items="${hitCandidateList}">
												<tr>
													<c:if test="${c.nin=='No Data'}">
														<td width="10%" class="title_link"><c:out
																value="${c.nin}" /></td>
													</c:if>
													<c:if test="${c.nin!='No Data'}">
														<td width="10%" class="title_link">
															<!-- {prasad}changes for the  proxy redirect to login page error [start] -->
															<c:url var="startInvestCompareUrl"
																value="/servlet/investigation/startInvestigationCompare/${c.transactionId}&&${jobDetails.transactionId}&&${c.searchResultId}&&${jobDetails.uploadJobId}" />
															<a onclick=Tools.displayWait();
															href="${startInvestCompareUrl}"> <c:out
																	value="${c.nin}" /> </a> <!-- end --> <%-- <a
												onclick=Tools.displayWait();
												href="${startInvestigationCompareUrl}/${c.transactionId}&&${jobDetails.transactionId}&&${c.searchResultId}&&${jobDetails.uploadJobId}"><c:out
														value="${c.nin}" /> 
											</a> --%> <!-- {prasad}changes for the  proxy redirect to login page error [end] -->
														</td>
													</c:if>
													<td width="10%" class="nricFormat"><c:out
															value="${c.transactionId}" /></td>
													<td width="10%" class="nricFormat"><c:out
															value="${c.firstName}" /></td>
													<td width="10%" class="nricFormat"><c:out
															value="${c.surname}" /></td>
													<td width="10%" class="nricFormat"><c:out
															value="${c.verifyDecision}" /></td>
													<td width="10%" class="nricFormat"><c:out
															value="${c.hitFingers}" /></td>
													<td width="10%" class="nricFormat"><c:out
															value="${c.matchingScore}" /></td>
													<!--<td width="10%" class="nricFormat" style="visibility: hidden;">${c.searchResultId}"</td>
										-->
										            <td width="10%" class="nricFormat"><c:out
															value="${c.ccn}" /></td>
													<td width="10%" class="nricFormat"><c:out
															value="${c.cardStatus}" /></td>
													<td width="10%" style="width: 5%; padding: 3px;"
														class="nricFormat">
														<div class="thumbnail-item">
															<c:if test="${c.photo!=null}">
																<a href="#"><img
																	src="data:image/jpg;base64,${c.photo}" 
																	height="100" class="thumbnail" /> </a>
																<div class="tooltip">
																	<img src="data:image/jpg;base64,${c.photo}" 
																		 height="200" alt="" /> <span class="overlay"></span>
																</div>
															</c:if>
															<c:if test="${c.photo==null}">
																<a href="#"><img
																	src=<c:url value='/resources/images/No_Image.jpg'/>
																	height="100" width="75" class="img-border" /> </a>
															</c:if>
														</div>

										</td>
										<td width="10%" class="nricFormat">
											<c:if test="${not empty c.remarksData}">
												<span class="historyEventDataSmark pointer"
													ref="<c:out value="${c.remarksData}" />"> <img
													src="<c:url value="/resources/images/remarks_icon.jpg"/>"
													width="50" height="50" alt="View Remarks" border="0"
													style="padding-left: 3px;" /> </span>
											</c:if></td>
										</tr>
										</c:forEach>
										</c:if>

									</table>
								</div>
							</div>
						</c:if>
					</c:if>
					<!-- Demographic Data Tab -->
					<div class="TabbedPanelsContent">
						<div id="inner_main" style="background-color: #FFFFF;">
							<div id="inner_main_left">
								<table width="100%" height="200" border="0">
									<tr>
										<td width="320" height="35" align="center"
											style="font-weight: bold" class="sno"><span
											class="table_header">Data from RIC</span></td>
									</tr>
									<tr>
										<td class="demographic-tab-border-right">
											<div id="dataFromRic_Space" style="height: 19px"></div>
											<table width="100%" border="0" cellpadding="2"
												class="data_table2">
												
												<tr>
													<td height="20px">NIN</td>
													<td>:</td>
													<td><c:out value="${hitCandidateDTO.nin}" />
													</td>
												</tr>
												<tr>
													<td>Surname</td>
													<td>:</td>
													<td><c:out
															value="${hitCandidateDTO.surname}" /></td>
												</tr>
												<tr>
													<td>First Name</td>
													<td>:</td>
													<td><c:out
															value="${hitCandidateDTO.firstName}" /></td>
												</tr>
												<tr>
													<td>Surname at Birth</td>
													<td>:</td>
													<td><c:out
															value="${hitCandidateDTO.surnameAtBirth}" /></td>
												</tr>
												<tr>
													<td>Gender</td>
													<td>:</td>
													<td><c:choose>
															<c:when test="${hitCandidateDTO.sex eq 'M'}">
																<c:out value="Male"></c:out>
															</c:when>
															<c:when test="${hitCandidateDTO.sex eq 'F'}">
																<c:out value="Female"></c:out>
															</c:when>
															<c:when test="${hitCandidateDTO.sex eq 'X'}">
																<c:out value="Unknown"></c:out>
															</c:when>
															<c:otherwise>
																<c:out value="${hitCandidateDTO.sex}" />
															</c:otherwise>
														</c:choose></td>
												</tr>
												<tr>
													<td>Date Of Birth</td>
													<td>:</td>
													<td><c:out value="${ricDob}" /></td>
												</tr>
												<tr>
													<td>House No/Flat No/Building Name</td>
													<td>:</td>
													<td><c:out
															value="${hitCandidateDTO.flatNoApartmentName}" /></td>
												</tr>
												<tr>
													<td>Street</td>
													<td>:</td>
													<td><c:out
															value="${hitCandidateDTO.streetName}" /></td>
												</tr>
												<tr>
													<td>Locality</td>
													<td>:</td>
													<td><c:out
															value="${hitCandidateDTO.locality}" /></td>
												</tr>
												<tr>
													<td>City/Town/Village</td>
													<td>:</td>
													<td><c:out
															value="${dataFromRicTwnVillageDesc}" /></td>
												</tr>
												<tr>
													<td>District/Outer Island</td>
													<td>:</td>
													<td><c:out
															value="${dataFromRicDistrictDesc}" /></td>
												</tr>
												<tr>
													<td>PostCode</td>
													<td>:</td>
													<td><c:out
															value="${hitCandidateDTO.postalCode}" /></td>
												</tr>
												<tr>
													<td>Marital Status</td>
													<td>:</td>
													<td><c:choose>
															<c:when test="${hitCandidateDTO.maritalStatus eq 'M'}">
																<c:out value="Married" />
															</c:when>
															<c:when test="${hitCandidateDTO.maritalStatus eq 'S'}">
																<c:out value="Single" />
															</c:when>
															<c:when test="${hitCandidateDTO.maritalStatus eq 'O'}">
																<c:out value="Others" />
															</c:when>
															<c:otherwise>
																<c:out value="${hitCandidateDTO.maritalStatus}" />
															</c:otherwise>
														</c:choose>
													</td>
												</tr>
												<tr>
													<td>Father's Name</td>
													<td>:</td>
													<td><c:out
															value="${hitCandidateDTO.fatherFirstName}" /></td>
												</tr>
												<tr>
													<td>Father's Surname</td>
													<td>:</td>
													<td><c:out
															value="${hitCandidateDTO.fatherSurname}" /></td>
												</tr>
												<tr>
													<td>Mother's Name</td>
													<td>:</td>
													<td><c:out
															value="${hitCandidateDTO.motherFirstName}" /></td>
												</tr>
												<tr>
													<td>Mother's Surname</td>
													<td>:</td>
													<td><c:out
															value="${hitCandidateDTO.motherSurname}" /></td>
												</tr>
												<!-- 
 * Modification History:
* 
 * 23 Sep 2013 (sailaja): To display the Spouse Details.
*/ -->

												<tr>
													<td>Spouse Name</td>
													<td>:</td>
													<td><c:out value="${hitCandidateDTO.spouseFirstName}" />
													</td>
												</tr>
												<tr>
													<td>Spouse Surname</td>
													<td>:</td>
													<td><c:out value="${hitCandidateDTO.spouseSurname}" />
													</td>
												</tr>
												<tr>
													<td>Option Surname</td>
													<td>:</td>
													<td><c:choose>
															<c:when test="${hitCandidateDTO.optionSurname eq 'OWN'}">
																<c:out value="Own Surname at Birth" />
															</c:when>
															<c:when
																test="${hitCandidateDTO.optionSurname eq 'SPOUSE'}">
																<c:out value="Spouse Surname at Birth" />
															</c:when>
															<c:when
																test="${hitCandidateDTO.optionSurname eq 'OWN-SPOUSE'}">
																<c:out value="Adjoined (Own-Spouse) Surname at Birth" />
															</c:when>
															<c:when
																test="${hitCandidateDTO.optionSurname eq 'SPOUSE-OWN'}">
																<c:out value="Adjoined (Spouse-Own) Surname at Birth" />
															</c:when>
															<c:when
																test="${hitCandidateDTO.optionSurname eq 'OWN_SPOUSE'}">
																<c:out value="Adjoined (Own Spouse) Surname at Birth" />
															</c:when>
															<c:when
																test="${hitCandidateDTO.optionSurname eq 'SPOUSE_OWN'}">
																<c:out value="Adjoined (Spouse Own) Surname at Birth" />
															</c:when>
															<c:otherwise>
																<c:out value="${hitCandidateDTO.optionSurname}" />
															</c:otherwise>
														</c:choose></td>
												</tr>
												<!-- Modification end -->
											</table>
										</td>
									</tr>
								</table>
							</div>

							<div id="oldCPDData">
                               <div id="inner_main_right">
								<c:if test="${empty candidateCPDData}">
									<span style="color: #FF0000; font-size: 15px;">No CPD
										Data Found.</span>
								</c:if>
								<c:if test="${not empty candidateCPDData}">
									<table width="99%" height="200" border="0">
										<tr>
											<td width="320" height="35" align="center"
												style="font-weight: bold" class="sno"><span
												class="table_header">Data from CPD</span></td>
										</tr>

										<tr>
											<td class="demographic-tab-border-right">

												<table width="100%" border="0" cellpadding="2"
													class="data_table2">
													
													<tr>
														<td>NIN</td>
														<td>:</td>
														<td id="nin_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.nin eq hitCandidateDTO.nin}">
																	<c:out value="${candidateCPDData.nin}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.nin}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
														<td><span class="refreshCPDData"><img
													src="<c:url value="/resources/images/refresh_icon.png"/>"
													width="20" height="20" alt="Refresh CPD Data" border="0"
													style="float: right;" /></span></td>
													</tr>
													<tr>
														<td>Surname</td>
														<td>:</td>
														<td id="sn_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.surname eq hitCandidateDTO.surname}">
																	<c:out value="${candidateCPDData.surname}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.surname}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>First Name</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.firstName eq hitCandidateDTO.firstName}">
																	<c:out value="${candidateCPDData.firstName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.firstName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Surname at Birth</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.surnameAtBirth eq hitCandidateDTO.surnameAtBirth}">
																	<c:out value="${candidateCPDData.surnameAtBirth}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.surnameAtBirth}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Gender</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${hitCandidateDTO.sex eq candidateCPDData.sex}">
																	<c:choose>
																		<c:when test="${candidateCPDData.sex eq 'M' }">
																			<c:out value="Male"></c:out>
																		</c:when>
																		<c:when test="${candidateCPDData.sex eq 'F'}">
																			<c:out value="Female"></c:out>
																		</c:when>
																		<c:when test="${candidateCPDData.sex eq 'X'}">
																			<c:out value="Unknown"></c:out>
																		</c:when>
																		<c:otherwise>
																			<c:out value="${candidateCPDData.sex}" />
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:choose>
																			<c:when test="${candidateCPDData.sex eq 'M' }">
																				<c:out value="Male"></c:out>
																			</c:when>
																			<c:when test="${candidateCPDData.sex eq 'F'}">
																				<c:out value="Female"></c:out>
																			</c:when>
																			<c:when test="${candidateCPDData.sex eq 'X'}">
																				<c:out value="Unknown"></c:out>
																			</c:when>
																			<c:otherwise>
																				<c:out value="${candidateCPDData.sex}" />
																			</c:otherwise>
																		</c:choose>
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Date Of Birth</td>
														<td>:</td>
														<td><c:choose>
																<c:when test="${cpdDob eq ricDob}">
																	<c:out value="${cpdDob}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${cpdDob}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>House No/Flat No/Building Name</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.flatNoApartmentName eq hitCandidateDTO.flatNoApartmentName}">
																	<c:out value="${candidateCPDData.flatNoApartmentName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.flatNoApartmentName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Street</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.streetName eq hitCandidateDTO.streetName}">
																	<c:out value="${candidateCPDData.streetName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.streetName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Locality</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.locality eq hitCandidateDTO.locality}">
																	<c:out value="${candidateCPDData.locality}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.locality}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>City/Town/Village</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${dataFromCpdTwnVillageDesc eq dataFromRicTwnVillageDesc}">
																	<c:out value="${dataFromCpdTwnVillageDesc}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${dataFromCpdTwnVillageDesc}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>District/Outer Island</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${dataFromCpdDistrictDesc eq dataFromRicDistrictDesc}">
																	<c:out value="${dataFromCpdDistrictDesc}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${dataFromCpdDistrictDesc}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>PostCode</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.postalCode eq hitCandidateDTO.postalCode}">
																	<c:out value="${candidateCPDData.postalCode}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.postalCode}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Marital Status</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.maritalStatus eq hitCandidateDTO.maritalStatus}">
																	<c:choose>
																		<c:when
																			test="${candidateCPDData.maritalStatus eq 'M'}">
																			<c:out value="Married" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.maritalStatus eq 'S'}">
																			<c:out value="Single" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.maritalStatus eq 'O'}">
																			<c:out value="Others" />
																		</c:when>
																		<c:otherwise>
																			<c:out value="${candidateCPDData.maritalStatus}" />
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:choose>
																			<c:when
																				test="${candidateCPDData.maritalStatus eq 'M'}">
																				<c:out value="Married" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.maritalStatus eq 'S'}">
																				<c:out value="Single" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.maritalStatus eq 'O'}">
																				<c:out value="Others" />
																			</c:when>
																			<c:otherwise>
																				<c:out value="${candidateCPDData.maritalStatus}" />
																			</c:otherwise>
																		</c:choose>
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Father's Name</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.fatherFirstName eq hitCandidateDTO.fatherFirstName}">
																	<c:out value="${candidateCPDData.fatherFirstName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.fatherFirstName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Father's Surname</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.fatherSurname eq hitCandidateDTO.fatherSurname}">
																	<c:out value="${candidateCPDData.fatherSurname}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.fatherSurname}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Mother's Name</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.motherFirstName eq hitCandidateDTO.motherFirstName}">
																	<c:out value="${candidateCPDData.motherFirstName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.motherFirstName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Mother's Surname</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.motherSurname eq hitCandidateDTO.motherSurname}">
																	<c:out value="${candidateCPDData.motherSurname}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.motherSurname}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>

													<!-- 
 * Modification History:
* 
 * 23 Sep 2013 (sailaja): To display the Spouse Details.
*/ -->

													<tr>
														<td>Spouse Name</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.spouseFirstName eq hitCandidateDTO.spouseFirstName}">
																	<c:out value="${candidateCPDData.spouseFirstName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.spouseFirstName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Spouse Surname</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.spouseSurname eq hitCandidateDTO.spouseSurname}">
																	<c:out value="${candidateCPDData.spouseSurname}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.spouseSurname}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Option Surname</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.optionSurname eq hitCandidateDTO.optionSurname}">
																	<c:choose>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'OWN'}">
																			<c:out value="Own Surname at Birth" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'SPOUSE'}">
																			<c:out value="Spouse Surname at Birth" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'OWN-SPOUSE'}">
																			<c:out value="Adjoined (Own-Spouse) Surname at Birth" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'SPOUSE-OWN'}">
																			<c:out value="Adjoined (Spouse-Own) Surname at Birth" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'OWN_SPOUSE'}">
																			<c:out value="Adjoined (Own Spouse) Surname at Birth" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'SPOUSE_OWN'}">
																			<c:out value="Adjoined (Spouse Own) Surname at Birth" />
																		</c:when>
																		<c:otherwise>
																			<c:out value="${candidateCPDData.optionSurname}" />
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:choose>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'OWN'}">
																				<c:out value="Own Surname at Birth" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'SPOUSE'}">
																				<c:out value="Spouse Surname at Birth" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'OWN-SPOUSE'}">
																				<c:out
																					value="Adjoined (Own-Spouse) Surname at Birth" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'SPOUSE-OWN'}">
																				<c:out
																					value="Adjoined (Spouse-Own) Surname at Birth" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'OWN_SPOUSE'}">
																				<c:out
																					value="Adjoined (Own Spouse) Surname at Birth" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'SPOUSE_OWN'}">
																				<c:out
																					value="Adjoined (Spouse Own) Surname at Birth" />
																			</c:when>
																			<c:otherwise>
																				<c:out value="${candidateCPDData.optionSurname}" />
																			</c:otherwise>
																		</c:choose>
																	</div>
																</c:otherwise>
															</c:choose></td>
													</tr>
													<!-- Modification end -->

												</table>
											</td>

										</tr>
									</table>
								</c:if>
							</div>
</div>
<div id="refreshCPDData_div" style="display: none;">
<div id="inner_main_right">
								
									<table width="99%" height="200" border="0">
										<tr>
											<td width="320" height="35" align="center"
												style="font-weight: bold" class="sno"><span
												class="table_header">Data from CPD</span></td>
										</tr>
										<tr>
											<td class="demographic-tab-border-right">
												<span style="color: #FF0000; font-size: 15px; height: 20px;">&nbsp;Data refreshed from CPD at <%=new java.util.Date()%></span>
												<span class="refreshCPDData"><img src="<c:url value="/resources/images/refresh_icon.png"/>" width="25" height="20" alt="Refresh CPD Data" border="0" style="float: right;" /></span>
												<c:if test="${empty candidateCPDData}">
													<span style="color: #FF0000; font-size: 15px;">No CPD Data Found.</span>
												</c:if>
												<c:if test="${not empty candidateCPDData}">
												<table width="100%" border="0" cellpadding="2"
													class="data_table2">
													<tr>
														<td height="20px">NIN</td>
														<td>:</td>
														<td id="nin_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.nin eq hitCandidateDTO.nin}">
																	<c:out value="${candidateCPDData.nin}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.nin}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Surname</td>
														<td>:</td>
														<td id="sn_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.surname eq hitCandidateDTO.surname}">
																	<c:out value="${candidateCPDData.surname}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.surname}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>First Name</td>
														<td>:</td>
														<td id="fn_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.firstName eq hitCandidateDTO.firstName}">
																	<c:out value="${candidateCPDData.firstName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.firstName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Surname at Birth</td>
														<td>:</td>
														<td id="snb_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.surnameAtBirth eq hitCandidateDTO.surnameAtBirth}">
																	<c:out value="${candidateCPDData.surnameAtBirth}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.surnameAtBirth}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Gender</td>
														<td>:</td>
														<td id="g_cpd"><c:choose>
																<c:when
																	test="${hitCandidateDTO.sex eq candidateCPDData.sex}">
																	<c:choose>
																		<c:when test="${candidateCPDData.sex eq 'M' }">
																			<c:out value="Male"></c:out>
																		</c:when>
																		<c:when test="${candidateCPDData.sex eq 'F'}">
																			<c:out value="Female"></c:out>
																		</c:when>
																		<c:when test="${candidateCPDData.sex eq 'X'}">
																			<c:out value="Unknown"></c:out>
																		</c:when>
																		<c:otherwise>
																			<c:out value="${candidateCPDData.sex}" />
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:choose>
																			<c:when test="${candidateCPDData.sex eq 'M' }">
																				<c:out value="Male"></c:out>
																			</c:when>
																			<c:when test="${candidateCPDData.sex eq 'F'}">
																				<c:out value="Female"></c:out>
																			</c:when>
																			<c:when test="${candidateCPDData.sex eq 'X'}">
																				<c:out value="Unknown"></c:out>
																			</c:when>
																			<c:otherwise>
																				<c:out value="${candidateCPDData.sex}" />
																			</c:otherwise>
																		</c:choose>
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Date Of Birth</td>
														<td>:</td>
														<td id="dob_cpd"><c:choose>
																<c:when test="${cpdDob eq ricDob}">
																	<c:out value="${cpdDob}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${cpdDob}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>House No/Flat No/Building Name</td>
														<td>:</td>
														<td id="flatNo_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.flatNoApartmentName eq hitCandidateDTO.flatNoApartmentName}">
																	<c:out value="${candidateCPDData.flatNoApartmentName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.flatNoApartmentName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Street</td>
														<td>:</td>
														<td id="StName_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.streetName eq hitCandidateDTO.streetName}">
																	<c:out value="${candidateCPDData.streetName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.streetName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Locality</td>
														<td>:</td>
														<td id="Loca_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.locality eq hitCandidateDTO.locality}">
																	<c:out value="${candidateCPDData.locality}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.locality}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>City/Town/Village</td>
														<td>:</td>
														<td id="TwVillage_cpd"><c:choose>
																<c:when
																	test="${dataFromCpdTwnVillageDesc eq dataFromRicTwnVillageDesc}">
																	<c:out value="${dataFromCpdTwnVillageDesc}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${dataFromCpdTwnVillageDesc}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>District/Outer Island</td>
														<td>:</td>
														<td id="district_cpd"><c:choose>
																<c:when
																	test="${dataFromCpdDistrictDesc eq dataFromRicDistrictDesc}">
																	<c:out value="${dataFromCpdDistrictDesc}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${dataFromCpdDistrictDesc}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>PostCode</td>
														<td>:</td>
														<td id="postalCode_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.postalCode eq hitCandidateDTO.postalCode}">
																	<c:out value="${candidateCPDData.postalCode}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.postalCode}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Marital Status</td>
														<td>:</td>
														<td id="ms_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.maritalStatus eq hitCandidateDTO.maritalStatus}">
																	<c:choose>
																		<c:when
																			test="${candidateCPDData.maritalStatus eq 'M'}">
																			<c:out value="Married" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.maritalStatus eq 'S'}">
																			<c:out value="Single" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.maritalStatus eq 'O'}">
																			<c:out value="Others" />
																		</c:when>
																		<c:otherwise>
																			<c:out value="${candidateCPDData.maritalStatus}" />
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:choose>
																			<c:when
																				test="${candidateCPDData.maritalStatus eq 'M'}">
																				<c:out value="Married" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.maritalStatus eq 'S'}">
																				<c:out value="Single" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.maritalStatus eq 'O'}">
																				<c:out value="Others" />
																			</c:when>
																			<c:otherwise>
																				<c:out value="${candidateCPDData.maritalStatus}" />
																			</c:otherwise>
																		</c:choose>
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Father's Name</td>
														<td>:</td>
														<td id="fan_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.fatherFirstName eq hitCandidateDTO.fatherFirstName}">
																	<c:out value="${candidateCPDData.fatherFirstName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.fatherFirstName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Father's Surname</td>
														<td>:</td>
														<td id="fasn_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.fatherSurname eq hitCandidateDTO.fatherSurname}">
																	<c:out value="${candidateCPDData.fatherSurname}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.fatherSurname}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Mother's Name</td>
														<td>:</td>
														<td id="mn_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.motherFirstName eq hitCandidateDTO.motherFirstName}">
																	<c:out value="${candidateCPDData.motherFirstName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.motherFirstName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Mother's Surname</td>
														<td>:</td>
														<td id="msn_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.motherSurname eq hitCandidateDTO.motherSurname}">
																	<c:out value="${candidateCPDData.motherSurname}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.motherSurname}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>

													<!-- 
 * Modification History:
* 
 * 23 Sep 2013 (sailaja): To display the Spouse Details.
*/ -->

													<tr>
														<td>Spouse Name</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.spouseFirstName eq hitCandidateDTO.spouseFirstName}">
																	<c:out value="${candidateCPDData.spouseFirstName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.spouseFirstName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Spouse Surname</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.spouseSurname eq hitCandidateDTO.spouseSurname}">
																	<c:out value="${candidateCPDData.spouseSurname}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.spouseSurname}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Option Surname</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.optionSurname eq hitCandidateDTO.optionSurname}">
																	<c:choose>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'OWN'}">
																			<c:out value="Own Surname at Birth" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'SPOUSE'}">
																			<c:out value="Spouse Surname at Birth" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'OWN-SPOUSE'}">
																			<c:out value="Adjoined (Own-Spouse) Surname at Birth" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'SPOUSE-OWN'}">
																			<c:out value="Adjoined (Spouse-Own) Surname at Birth" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'OWN_SPOUSE'}">
																			<c:out value="Adjoined (Own Spouse) Surname at Birth" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'SPOUSE_OWN'}">
																			<c:out value="Adjoined (Spouse Own) Surname at Birth" />
																		</c:when>
																		<c:otherwise>
																			<c:out value="${candidateCPDData.optionSurname}" />
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:choose>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'OWN'}">
																				<c:out value="Own Surname at Birth" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'SPOUSE'}">
																				<c:out value="Spouse Surname at Birth" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'OWN-SPOUSE'}">
																				<c:out
																					value="Adjoined (Own-Spouse) Surname at Birth" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'SPOUSE-OWN'}">
																				<c:out
																					value="Adjoined (Spouse-Own) Surname at Birth" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'OWN_SPOUSE'}">
																				<c:out
																					value="Adjoined (Own Spouse) Surname at Birth" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'SPOUSE_OWN'}">
																				<c:out
																					value="Adjoined (Spouse Own) Surname at Birth" />
																			</c:when>
																			<c:otherwise>
																				<c:out value="${candidateCPDData.optionSurname}" />
																			</c:otherwise>
																		</c:choose>
																	</div>
																</c:otherwise>
															</c:choose></td>
													</tr>
													<!-- Modification end -->

												</table>
												</c:if>
											</td>		
										</tr>
									</table>
							</div>
</div>
						</div>
						<div>&nbsp;</div>
				<!-- Additional Information for demographic data from Perso -->
				<div>
				<h4 style="font-size: 15px; color: #0000FF;">Additional Information</h4>
								<label for="Remarks"></label>
								<textarea width="100%" name="Remarks_demographic_data" id="Remarks_demographic_data"
								type="text" cols="400" rows="4" readOnly="readOnly"><c:out value="${persoRemarksEdit}" /></textarea>
					</div>
					</div>
					<!-- Remarks -->
					<div class="TabbedPanelsContent">
						<div class="border_success" style="display: none">
							<div class="success_left">
								<img align='left'
									src="<c:url value="/resources/images/success.jpg" />"
									width="30" height="30" />

							</div>
							<div class="success">
								<span style="font-size: 20px; font-weight: bold; color: #000;">&nbsp;Successfully
									updated the Remarks.</span>
							</div>

						</div>
						<div class="border_error" style="display: none">
							<div class="success_left">
								<img align='left'
									src="<c:url value="/resources/images/alert.png" />" width="30"
									height="30" />
							</div>
							<div class="errors">
								<br /> <span
									style="font-size: 20px; font-weight: bold; color: #000;">&nbsp;Failed
									to update the Remarks.</span> <br />
							</div>
						</div>
						<br />
						<div class="error_msg" style="color: #FF0000; font-weight: bold;"></div>
						<br />

						
							<h4 style="font-size: 25px; color: #0000FF;">Remarks</h4>
							<br />
							
								<label for="Remarks"></label>
								<textarea name="Remarks_tab" id="Remarks_tab"
								cols="200" rows="10"><c:out value="${remarksEdit}"/></textarea>
							<br />
							<br />
								<input type="button" class="button_grey" name="Submit"
									id="Submit_Btn" value="Submit" />
							
						

					</div>

				</c:if>

				<c:if test="${fullyMainCandAmputated eq true}">
					<div class="TabbedPanelsContent">
						<br />
						<div id="inner_main" style="background-color: #FFFFF;">
							<div id="inner_main_left">
								<table width="100%" height="200" border="0">
									<tr>
										<td width="320" height="35" align="center"
											style="font-weight: bold" class="sno"><span
											class="table_header">Data from RIC</span></td>
									</tr>
									<tr>
										<td class="demographic-tab-border-right">
                                          <div id="dataFromRic_Space" style="height: 20px"></div>
											<table width="100%" border="0" cellpadding="2"
												class="data_table2">
												<tr>
													<td height="20">NIN</td>
													<td>:</td>
													<td id="nin_ric"><c:out value="${hitCandidateDTO.nin}" />
													</td>
												</tr>
												<tr>
													<td>Surname</td>
													<td>:</td>
													<td id="sn_ric"><c:out
															value="${hitCandidateDTO.surname}" /></td>
												</tr>
												<tr>
													<td>First Name</td>
													<td>:</td>
													<td id="fn_ric"><c:out
															value="${hitCandidateDTO.firstName}" /></td>
												</tr>
												<tr>
													<td>Surname at Birth</td>
													<td>:</td>
													<td id="snb_ric"><c:out
															value="${hitCandidateDTO.surnameAtBirth}" /></td>
												</tr>
												<tr>
													<td>Gender</td>
													<td>:</td>
													<td id="g_ric"><c:choose>
															<c:when test="${hitCandidateDTO.sex eq 'M'}">
																<c:out value="Male"></c:out>
															</c:when>
															<c:when test="${hitCandidateDTO.sex eq 'F'}">
																<c:out value="Female"></c:out>
															</c:when>
															<c:when test="${hitCandidateDTO.sex eq 'X'}">
																<c:out value="Unknown"></c:out>
															</c:when>
															<c:otherwise>
																<c:out value="${hitCandidateDTO.sex}" />
															</c:otherwise>
														</c:choose></td>
												</tr>
												<tr>
													<td>Date Of Birth</td>
													<td>:</td>
													<td id="dob_ric"><c:out value="${ricDob}" /></td>
												</tr>
												<tr>
													<td>House No/Flat No/Building Name</td>
													<td>:</td>
													<td id="flatNo_ric"><c:out
															value="${hitCandidateDTO.flatNoApartmentName}" /></td>
												</tr>
												<tr>
													<td>Street</td>
													<td>:</td>
													<td id="StName_ric"><c:out
															value="${hitCandidateDTO.streetName}" /></td>
												</tr>
												<tr>
													<td>Locality</td>
													<td>:</td>
													<td id="Loca_ric"><c:out
															value="${hitCandidateDTO.locality}" /></td>
												</tr>
												<tr>
													<td>City/Town/Village</td>
													<td>:</td>
													<td id="TwVillage_ric"><c:out
															value="${dataFromRicTwnVillageDesc}" /></td>
												</tr>
												<tr>
													<td>District/Outer Island</td>
													<td>:</td>
													<td id="district_ric"><c:out
															value="${dataFromRicDistrictDesc}" /></td>
												</tr>
												<tr>
													<td>PostCode</td>
													<td>:</td>
													<td id="postalCode_ric"><c:out
															value="${hitCandidateDTO.postalCode}" /></td>
												</tr>
												<tr>
													<td>Marital Status</td>
													<td>:</td>
													<td id="ms_ric"><c:choose>
															<c:when test="${hitCandidateDTO.maritalStatus eq 'M'}">
																<c:out value="Married" />
															</c:when>
															<c:when test="${hitCandidateDTO.maritalStatus eq 'S'}">
																<c:out value="Single" />
															</c:when>
															<c:when test="${hitCandidateDTO.maritalStatus eq 'O'}">
																<c:out value="Others" />
															</c:when>
															<c:otherwise>
																<c:out value="${hitCandidateDTO.maritalStatus}" />
															</c:otherwise>
														</c:choose>
													</td>
												</tr>
												<tr>
													<td>Father's Name</td>
													<td>:</td>
													<td id="fan_ric"><c:out
															value="${hitCandidateDTO.fatherFirstName}" /></td>
												</tr>
												<tr>
													<td>Father's Surname</td>
													<td>:</td>
													<td id="fasn_ric"><c:out
															value="${hitCandidateDTO.fatherSurname}" /></td>
												</tr>
												<tr>
													<td>Mother's Name</td>
													<td>:</td>
													<td id="mn_ric"><c:out
															value="${hitCandidateDTO.motherFirstName}" /></td>
												</tr>
												<tr>
													<td>Mother's Surname</td>
													<td>:</td>
													<td id="msn_ric"><c:out
															value="${hitCandidateDTO.motherSurname}" /></td>
												</tr>
												<!-- 
 * Modification History:
* 
 * 23 Sep 2013 (sailaja): To display the Spouse Details.
*/ -->

												<tr>
													<td>Spouse Name</td>
													<td>:</td>
													<td><c:out value="${hitCandidateDTO.spouseFirstName}" />
													</td>
												</tr>
												<tr>
													<td>Spouse Surname</td>
													<td>:</td>
													<td><c:out value="${hitCandidateDTO.spouseSurname}" />
													</td>
												</tr>
												<tr>
													<td>Option Surname</td>
													<td>:</td>
													<td><c:choose>
															<c:when test="${hitCandidateDTO.optionSurname eq 'OWN'}">
																<c:out value="Own Surname at Birth" />
															</c:when>
															<c:when
																test="${hitCandidateDTO.optionSurname eq 'SPOUSE'}">
																<c:out value="Spouse Surname at Birth" />
															</c:when>
															<c:when
																test="${hitCandidateDTO.optionSurname eq 'OWN-SPOUSE'}">
																<c:out value="Adjoined (Own-Spouse) Surname at Birth" />
															</c:when>
															<c:when
																test="${hitCandidateDTO.optionSurname eq 'SPOUSE-OWN'}">
																<c:out value="Adjoined (Spouse-Own) Surname at Birth" />
															</c:when>
															<c:when
																test="${hitCandidateDTO.optionSurname eq 'OWN_SPOUSE'}">
																<c:out value="Adjoined (Own Spouse) Surname at Birth" />
															</c:when>
															<c:when
																test="${hitCandidateDTO.optionSurname eq 'SPOUSE_OWN'}">
																<c:out value="Adjoined (Spouse Own) Surname at Birth" />
															</c:when>
															<c:otherwise>
																<c:out value="${hitCandidateDTO.optionSurname}" />
															</c:otherwise>
														</c:choose></td>
												</tr>
												<!-- Modification end -->
											</table>
										</td>
									</tr>
								</table>
							</div>
<div id="oldCPDData">
                               <div id="inner_main_right">
								<c:if test="${empty candidateCPDData}">
									<span style="color: #FF0000; font-size: 15px;">No CPD
										Data Found.</span>
								</c:if>
								<c:if test="${not empty candidateCPDData}">
									<table width="99%" height="200" border="0">
										<tr>
											<td width="320" height="35" align="center"
												style="font-weight: bold" class="sno"><span
												class="table_header">Data from CPD</span></td>
										</tr>

										<tr>
											<td class="demographic-tab-border-right">

												<table width="100%" border="0" cellpadding="2"
													class="data_table2">
													
													<tr>
														<td>NIN</td>
														<td>:</td>
														<td id="nin_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.nin eq hitCandidateDTO.nin}">
																	<c:out value="${candidateCPDData.nin}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.nin}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
														<td><span class="refreshCPDData"><img
													src="<c:url value="/resources/images/refresh_icon.png"/>"
													width="20" height="20" alt="Refresh CPD Data" border="0"
													style="float: right;" /></span></td>
													</tr>
													<tr>
														<td>Surname</td>
														<td>:</td>
														<td id="sn_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.surname eq hitCandidateDTO.surname}">
																	<c:out value="${candidateCPDData.surname}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.surname}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>First Name</td>
														<td>:</td>
														<td id="fn_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.firstName eq hitCandidateDTO.firstName}">
																	<c:out value="${candidateCPDData.firstName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.firstName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Surname at Birth</td>
														<td>:</td>
														<td id="snb_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.surnameAtBirth eq hitCandidateDTO.surnameAtBirth}">
																	<c:out value="${candidateCPDData.surnameAtBirth}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.surnameAtBirth}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Gender</td>
														<td>:</td>
														<td id="g_cpd"><c:choose>
																<c:when
																	test="${hitCandidateDTO.sex eq candidateCPDData.sex}">
																	<c:choose>
																		<c:when test="${candidateCPDData.sex eq 'M' }">
																			<c:out value="Male"></c:out>
																		</c:when>
																		<c:when test="${candidateCPDData.sex eq 'F'}">
																			<c:out value="Female"></c:out>
																		</c:when>
																		<c:when test="${candidateCPDData.sex eq 'X'}">
																			<c:out value="Unknown"></c:out>
																		</c:when>
																		<c:otherwise>
																			<c:out value="${candidateCPDData.sex}" />
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:choose>
																			<c:when test="${candidateCPDData.sex eq 'M' }">
																				<c:out value="Male"></c:out>
																			</c:when>
																			<c:when test="${candidateCPDData.sex eq 'F'}">
																				<c:out value="Female"></c:out>
																			</c:when>
																			<c:when test="${candidateCPDData.sex eq 'X'}">
																				<c:out value="Unknown"></c:out>
																			</c:when>
																			<c:otherwise>
																				<c:out value="${candidateCPDData.sex}" />
																			</c:otherwise>
																		</c:choose>
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Date Of Birth</td>
														<td>:</td>
														<td id="dob_cpd"><c:choose>
																<c:when test="${cpdDob eq ricDob}">
																	<c:out value="${cpdDob}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${cpdDob}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>House No/Flat No/Building Name</td>
														<td>:</td>
														<td id="flatNo_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.flatNoApartmentName eq hitCandidateDTO.flatNoApartmentName}">
																	<c:out value="${candidateCPDData.flatNoApartmentName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.flatNoApartmentName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Street</td>
														<td>:</td>
														<td id="StName_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.streetName eq hitCandidateDTO.streetName}">
																	<c:out value="${candidateCPDData.streetName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.streetName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Locality</td>
														<td>:</td>
														<td id="Loca_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.locality eq hitCandidateDTO.locality}">
																	<c:out value="${candidateCPDData.locality}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.locality}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>City/Town/Village</td>
														<td>:</td>
														<td id="TwVillage_cpd"><c:choose>
																<c:when
																	test="${dataFromCpdTwnVillageDesc eq dataFromRicTwnVillageDesc}">
																	<c:out value="${dataFromCpdTwnVillageDesc}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${dataFromCpdTwnVillageDesc}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>District/Outer Island</td>
														<td>:</td>
														<td id="district_cpd"><c:choose>
																<c:when
																	test="${dataFromCpdDistrictDesc eq dataFromRicDistrictDesc}">
																	<c:out value="${dataFromCpdDistrictDesc}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${dataFromCpdDistrictDesc}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>PostCode</td>
														<td>:</td>
														<td id="postalCode_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.postalCode eq hitCandidateDTO.postalCode}">
																	<c:out value="${candidateCPDData.postalCode}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.postalCode}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Marital Status</td>
														<td>:</td>
														<td id="ms_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.maritalStatus eq hitCandidateDTO.maritalStatus}">
																	<c:choose>
																		<c:when
																			test="${candidateCPDData.maritalStatus eq 'M'}">
																			<c:out value="Married" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.maritalStatus eq 'S'}">
																			<c:out value="Single" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.maritalStatus eq 'O'}">
																			<c:out value="Others" />
																		</c:when>
																		<c:otherwise>
																			<c:out value="${candidateCPDData.maritalStatus}" />
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:choose>
																			<c:when
																				test="${candidateCPDData.maritalStatus eq 'M'}">
																				<c:out value="Married" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.maritalStatus eq 'S'}">
																				<c:out value="Single" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.maritalStatus eq 'O'}">
																				<c:out value="Others" />
																			</c:when>
																			<c:otherwise>
																				<c:out value="${candidateCPDData.maritalStatus}" />
																			</c:otherwise>
																		</c:choose>
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Father's Name</td>
														<td>:</td>
														<td id="fan_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.fatherFirstName eq hitCandidateDTO.fatherFirstName}">
																	<c:out value="${candidateCPDData.fatherFirstName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.fatherFirstName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Father's Surname</td>
														<td>:</td>
														<td id="fasn_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.fatherSurname eq hitCandidateDTO.fatherSurname}">
																	<c:out value="${candidateCPDData.fatherSurname}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.fatherSurname}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Mother's Name</td>
														<td>:</td>
														<td id="mn_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.motherFirstName eq hitCandidateDTO.motherFirstName}">
																	<c:out value="${candidateCPDData.motherFirstName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.motherFirstName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Mother's Surname</td>
														<td>:</td>
														<td id="msn_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.motherSurname eq hitCandidateDTO.motherSurname}">
																	<c:out value="${candidateCPDData.motherSurname}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.motherSurname}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>

													<!-- 
 * Modification History:
* 
 * 23 Sep 2013 (sailaja): To display the Spouse Details.
*/ -->

													<tr>
														<td>Spouse Name</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.spouseFirstName eq hitCandidateDTO.spouseFirstName}">
																	<c:out value="${candidateCPDData.spouseFirstName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.spouseFirstName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Spouse Surname</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.spouseSurname eq hitCandidateDTO.spouseSurname}">
																	<c:out value="${candidateCPDData.spouseSurname}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.spouseSurname}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Option Surname</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.optionSurname eq hitCandidateDTO.optionSurname}">
																	<c:choose>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'OWN'}">
																			<c:out value="Own Surname at Birth" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'SPOUSE'}">
																			<c:out value="Spouse Surname at Birth" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'OWN-SPOUSE'}">
																			<c:out value="Adjoined (Own-Spouse) Surname at Birth" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'SPOUSE-OWN'}">
																			<c:out value="Adjoined (Spouse-Own) Surname at Birth" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'OWN_SPOUSE'}">
																			<c:out value="Adjoined (Own Spouse) Surname at Birth" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'SPOUSE_OWN'}">
																			<c:out value="Adjoined (Spouse Own) Surname at Birth" />
																		</c:when>
																		<c:otherwise>
																			<c:out value="${candidateCPDData.optionSurname}" />
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:choose>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'OWN'}">
																				<c:out value="Own Surname at Birth" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'SPOUSE'}">
																				<c:out value="Spouse Surname at Birth" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'OWN-SPOUSE'}">
																				<c:out
																					value="Adjoined (Own-Spouse) Surname at Birth" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'SPOUSE-OWN'}">
																				<c:out
																					value="Adjoined (Spouse-Own) Surname at Birth" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'OWN_SPOUSE'}">
																				<c:out
																					value="Adjoined (Own Spouse) Surname at Birth" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'SPOUSE_OWN'}">
																				<c:out
																					value="Adjoined (Spouse Own) Surname at Birth" />
																			</c:when>
																			<c:otherwise>
																				<c:out value="${candidateCPDData.optionSurname}" />
																			</c:otherwise>
																		</c:choose>
																	</div>
																</c:otherwise>
															</c:choose></td>
													</tr>
													<!-- Modification end -->

												</table>
											</td>

										</tr>
									</table>
								</c:if>
							</div>
</div>
<div id="refreshCPDData_div" style="display: none;">
<div id="inner_main_right">
								<c:if test="${empty candidateCPDData}">
									<span style="color: #FF0000; font-size: 15px;">No CPD
										Data Found.</span>
								</c:if>
								<c:if test="${not empty candidateCPDData}">
									<table width="99%" height="200" border="0">
										<tr>
											<td width="320" height="35" align="center"
												style="font-weight: bold" class="sno"><span
												class="table_header">Data from CPD</span></td>
										</tr>
										<tr>
											<td class="demographic-tab-border-right">

												<table width="100%" border="0" cellpadding="2"
													class="data_table2">
													 <span style="color: #FF0000; font-size: 15px; height: 20px;">&nbsp;Data refreshed from CPD at <%=new java.util.Date()%></span>
													 <span class="refreshCPDData"><img
													src="<c:url value="/resources/images/refresh_icon.png"/>"
													width="20" height="20" alt="Refresh CPD Data" border="0"
													style="float: right;" /></span>
													<tr>
														<td height="20">NIN</td>
														<td>:</td>
														<td id="nin_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.nin eq hitCandidateDTO.nin}">
																	<c:out value="${candidateCPDData.nin}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.nin}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Surname</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.surname eq hitCandidateDTO.surname}">
																	<c:out value="${candidateCPDData.surname}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.surname}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>First Name</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.firstName eq hitCandidateDTO.firstName}">
																	<c:out value="${candidateCPDData.firstName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.firstName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Surname at Birth</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.surnameAtBirth eq hitCandidateDTO.surnameAtBirth}">
																	<c:out value="${candidateCPDData.surnameAtBirth}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.surnameAtBirth}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Gender</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${hitCandidateDTO.sex eq candidateCPDData.sex}">
																	<c:choose>
																		<c:when test="${candidateCPDData.sex eq 'M' }">
																			<c:out value="Male"></c:out>
																		</c:when>
																		<c:when test="${candidateCPDData.sex eq 'F'}">
																			<c:out value="Female"></c:out>
																		</c:when>
																		<c:when test="${candidateCPDData.sex eq 'X'}">
																			<c:out value="Unknown"></c:out>
																		</c:when>
																		<c:otherwise>
																			<c:out value="${candidateCPDData.sex}" />
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:choose>
																			<c:when test="${candidateCPDData.sex eq 'M' }">
																				<c:out value="Male"></c:out>
																			</c:when>
																			<c:when test="${candidateCPDData.sex eq 'F'}">
																				<c:out value="Female"></c:out>
																			</c:when>
																			<c:when test="${candidateCPDData.sex eq 'X'}">
																				<c:out value="Unknown"></c:out>
																			</c:when>
																			<c:otherwise>
																				<c:out value="${candidateCPDData.sex}" />
																			</c:otherwise>
																		</c:choose>
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Date Of Birth</td>
														<td>:</td>
														<td><c:choose>
																<c:when test="${cpdDob eq ricDob}">
																	<c:out value="${cpdDob}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${cpdDob}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>House No/Flat No/Building Name</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.flatNoApartmentName eq hitCandidateDTO.flatNoApartmentName}">
																	<c:out value="${candidateCPDData.flatNoApartmentName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.flatNoApartmentName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Street</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.streetName eq hitCandidateDTO.streetName}">
																	<c:out value="${candidateCPDData.streetName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.streetName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Locality</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.locality eq hitCandidateDTO.locality}">
																	<c:out value="${candidateCPDData.locality}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.locality}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>City/Town/Village</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${dataFromCpdTwnVillageDesc eq dataFromRicTwnVillageDesc}">
																	<c:out value="${dataFromCpdTwnVillageDesc}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${dataFromCpdTwnVillageDesc}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>District/Outer Island</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${dataFromCpdDistrictDesc eq dataFromRicDistrictDesc}">
																	<c:out value="${dataFromCpdDistrictDesc}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${dataFromCpdDistrictDesc}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>PostCode</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.postalCode eq hitCandidateDTO.postalCode}">
																	<c:out value="${candidateCPDData.postalCode}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.postalCode}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Marital Status</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.maritalStatus eq hitCandidateDTO.maritalStatus}">
																	<c:choose>
																		<c:when
																			test="${candidateCPDData.maritalStatus eq 'M'}">
																			<c:out value="Married" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.maritalStatus eq 'S'}">
																			<c:out value="Single" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.maritalStatus eq 'O'}">
																			<c:out value="Others" />
																		</c:when>
																		<c:otherwise>
																			<c:out value="${candidateCPDData.maritalStatus}" />
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:choose>
																			<c:when
																				test="${candidateCPDData.maritalStatus eq 'M'}">
																				<c:out value="Married" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.maritalStatus eq 'S'}">
																				<c:out value="Single" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.maritalStatus eq 'O'}">
																				<c:out value="Others" />
																			</c:when>
																			<c:otherwise>
																				<c:out value="${candidateCPDData.maritalStatus}" />
																			</c:otherwise>
																		</c:choose>
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Father's Name</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.fatherFirstName eq hitCandidateDTO.fatherFirstName}">
																	<c:out value="${candidateCPDData.fatherFirstName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.fatherFirstName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Father's Surname</td>
														<td>:</td>
														<td id="fasn_cpd"><c:choose>
																<c:when
																	test="${candidateCPDData.fatherSurname eq hitCandidateDTO.fatherSurname}">
																	<c:out value="${candidateCPDData.fatherSurname}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.fatherSurname}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Mother's Name</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.motherFirstName eq hitCandidateDTO.motherFirstName}">
																	<c:out value="${candidateCPDData.motherFirstName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.motherFirstName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Mother's Surname</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.motherSurname eq hitCandidateDTO.motherSurname}">
																	<c:out value="${candidateCPDData.motherSurname}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.motherSurname}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>

													<!-- 
 * Modification History:
* 
 * 23 Sep 2013 (sailaja): To display the Spouse Details.
*/ -->

													<tr>
														<td>Spouse Name</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.spouseFirstName eq hitCandidateDTO.spouseFirstName}">
																	<c:out value="${candidateCPDData.spouseFirstName}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.spouseFirstName}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Spouse Surname</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.spouseSurname eq hitCandidateDTO.spouseSurname}">
																	<c:out value="${candidateCPDData.spouseSurname}" />
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:out value="${candidateCPDData.spouseSurname}" />
																	</div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<td>Option Surname</td>
														<td>:</td>
														<td><c:choose>
																<c:when
																	test="${candidateCPDData.optionSurname eq hitCandidateDTO.optionSurname}">
																	<c:choose>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'OWN'}">
																			<c:out value="Own Surname at Birth" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'SPOUSE'}">
																			<c:out value="Spouse Surname at Birth" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'OWN-SPOUSE'}">
																			<c:out value="Adjoined (Own-Spouse) Surname at Birth" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'SPOUSE-OWN'}">
																			<c:out value="Adjoined (Spouse-Own) Surname at Birth" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'OWN_SPOUSE'}">
																			<c:out value="Adjoined (Own Spouse) Surname at Birth" />
																		</c:when>
																		<c:when
																			test="${candidateCPDData.optionSurname eq 'SPOUSE_OWN'}">
																			<c:out value="Adjoined (Spouse Own) Surname at Birth" />
																		</c:when>
																		<c:otherwise>
																			<c:out value="${candidateCPDData.optionSurname}" />
																		</c:otherwise>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<div class="matchStyle">
																		<c:choose>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'OWN'}">
																				<c:out value="Own Surname at Birth" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'SPOUSE'}">
																				<c:out value="Spouse Surname at Birth" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'OWN-SPOUSE'}">
																				<c:out
																					value="Adjoined (Own-Spouse) Surname at Birth" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'SPOUSE-OWN'}">
																				<c:out
																					value="Adjoined (Spouse-Own) Surname at Birth" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'OWN_SPOUSE'}">
																				<c:out
																					value="Adjoined (Own Spouse) Surname at Birth" />
																			</c:when>
																			<c:when
																				test="${candidateCPDData.optionSurname eq 'SPOUSE_OWN'}">
																				<c:out
																					value="Adjoined (Spouse Own) Surname at Birth" />
																			</c:when>
																			<c:otherwise>
																				<c:out value="${candidateCPDData.optionSurname}" />
																			</c:otherwise>
																		</c:choose>
																	</div>
																</c:otherwise>
															</c:choose></td>
													</tr>
													<!-- Modification end -->

												</table>
											</td>		
										</tr>
									</table>
								</c:if>
							</div>
</div>
				
							</div>
		        <div>&nbsp;</div>
				<!-- Additional Information for demographic data from Perso -->
				<div>
				<h4 style="font-size: 15px; color: #0000FF;">Additional Information</h4>
								<label for="Remarks"></label>
								<textarea name="Remarks_demographic_data" id="Remarks_demographic_data"
								cols="400" rows="4" readOnly="readOnly"><c:out  value="${persoRemarksEdit}" /></textarea>
					</div>
					</div>

					<div class="TabbedPanelsContent">
						<div class="border_success" style="display: none">
							<div class="success_left">
								<img align='left'
									src="<c:url value="/resources/images/success.jpg" />"
									width="30" height="30" />

							</div>
							<div class="success">
								<span style="font-size: 20px; font-weight: bold; color: #000;">&nbsp;Successfully
									updated the Remarks.</span>
							</div>

						</div>
						<div class="border_error" style="display: none">
							<div class="success_left">
								<img align='left'
									src="<c:url value="/resources/images/alert.png" />" width="30"
									height="30" />
							</div>
							<div class="errors">
								<br /> <span
									style="font-size: 20px; font-weight: bold; color: #000;">&nbsp;Failed
									to update the Remarks.</span> <br />
							</div>
						</div>
						<br />
						<div class="error_msg" style="color: #FF0000; font-weight: bold;"></div>
						<br />
						<form:form modelAttribute="hitCandidateDTO" id="remarksForm"
							name="remarksForm" action="" method="post">
							<h4 style="font-size: 25px; color: #0000FF;">Remarks</h4>
							<br />
							
								<label for="Remarks"></label>
								<textarea width="100%" name="Remarks_tab" id="Remarks_tab"
								type="text" cols="200" rows="10"><c:out value="${remarksEdit}"/></textarea>
							
							<p>&nbsp;</p>
							
								<input type="button" class="button_grey" name="Submit"
									id="Submit_Btn" value="Submit" />
							
						</form:form>
					</div>
					
				</c:if>

				<!-- Reference Document Tab  -->
				<div class="TabbedPanelsContent">
					<c:if test="${empty nicTxnDocs}">
						<span style="color: #FF0000; font-size: 12px;">No Scanned
							Documents.</span>
					</c:if>
					<c:if test="${not empty nicTxnDocs}">
						<div>
							<table id="box1_table" width="50%" border="0" cellpadding="0"
								cellspacing="0" bordercolor="#333333"
								style="border: 0px; padding: 5px;">
								<tr>
									<td class="sno" style="font-weight: bold;" height="30px"><span
										class="table_header" style="padding-left: 40px;">Document
											Name</span></td>
									<td class="sno" style="font-weight: bold; padding-left: 15px;"><span
										class="table_header" style="padding-left: 5px;">Document</span>
									</td>
								</tr>
							</table>
						</div>
						<div id="mycustomscroll" class='flexcroll'>
							<table id="box1_table" width="50%" border="0" cellpadding="0"
								cellspacing="0" bordercolor="#333333" class="data_table">
								<c:forEach var="c" items="${nicTxnDocs}" varStatus="status">
									<c:choose>
										<c:when test="${docSize ge 15000}">
											<c:choose>
												<c:when
													test="${c.documentId eq 'SC_USER_DECL' or c.documentId eq 'REF_COL_SLIP'}">
													<tr>
														<td class="nricFormat"><c:out
																value="${c.documentName}" /></td>
														<td class="nricFormat">
															<div class="scanDocViewOnly" data-view="${status.index}"
																data-name="${c.type}">
																<img src=<c:url value='/resources/images/pdf_icon.jpg'/>
																	width="40px" height="40px" title="${c.documentName}" />
															</div>
														</td>
													</tr>
												</c:when>
												<c:otherwise>
													<tr>
														<td class="nricFormat"><c:out
																value="${c.documentName}" /></td>
														<td class="nricFormat">
															<div class="scanDocViewOnly" data-view="${status.index}"
																data-name="${c.type}">
																<img
																	src=<c:url value='/resources/images/document_icon.jpg'/>
																	width="40px" height="40px" title="${c.documentName}" />
															</div>
														</td>
													</tr>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<tr>
												<td class="nricFormat"><c:out value="${c.documentName}" />
												</td>
												<td class="nricFormat">
													<div class="scanDocViewOnly" data-view="${status.index}"
														data-name="${c.type}">
														<img
															src=<c:url value='/resources/images/document_icon.jpg'/>
															width="40px" height="40px" title="${c.documentName}" />
													</div>
												</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</table>
						</div>
					</c:if>
				</div>
				<!-- Reference docs tab end -->
				<!-- Perso Tab start -->
				<div class="TabbedPanelsContent">
				<display:table cellspacing="1" cellpadding="0" id="row"	export="false" class="displayTag" name="persoRemarksDTO">
				  <display:column title="Create Date" property="logCreateTime" style="width:18%" format="{0,date,dd-MMM-yyyy}">
				  </display:column>
				  <display:column property="remarksData" title="Remarks" maxLength="80" />
			    </display:table>
				</div>
				<!-- Perso Tab end -->
				<c:if test="${userRole eq 'SUPERVISOR'}">
				<!--   Rejected Info tab -->
				<div class="TabbedPanelsContent">
				<display:table cellspacing="1" cellpadding="0" id="row"
				export="false" class="displayTag" name="nicRejectionData">
				<display:column title="Reject Reason" property="rejectReason">
				</display:column>
				<display:column property="rejectRemarks" title="Reject Remarks" maxLength="50" />
				<display:column property="rejectionType" title="Rejection Type" maxLength="20" />
				<display:column property="updateBy" title="Update By" maxLength="30" />
				<display:column property="updateWkstnId" title="Update Workstation" maxLength="30" />
				<display:column property="updateDate" title="Update Date" style="width:18%" format="{0,date,dd-MMM-yyyy}"/>
				</display:table>
				</div>
				</c:if>
				<!--   Rejected Info Tab end -->
				</div>

		</div>

	</div>
</div>
</div>
</div>
</form:form>
<br />
<!--Jquery Dialog box div for Reject-->
<div id="dialog-reject" title="Reject Card" style="display: none;">
	<form:form modelAttribute="rejectRemarksForm" id="rejectRemarksForm">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>Are you sure? You
			want to Continue to Reject?
		</p>
		<br />
		<br />
		<span style="font-size: 20px; font-weight: bold; color: #0000FF;">Reason
			for Rejection</span>
	<form:select path="rejectReasons" id="rejectedReason"
			name="rejectedReason" for="" width="20px">
			<form:option value="">Please select...</form:option>
			<c:forEach var="opt" items="${rejectReasonsDTO.rejectReason}">
				<form:option title="${opt.id.codeValue}"
					value="${opt.codeValueDesc}">
					<c:out value="${opt.codeValueDesc}" />
				</form:option>
			</c:forEach>
		</form:select>
		<br />
		<br />
		<h4 style="font-size: 20px; color: #0000FF;">Remarks</h4>
		<form:textarea path="rejectRemarks" id="rejectRemarks" name="rejectedReason"
			type="text" cols="150" rows="10"></form:textarea>
		<form:input path="transactionId" type="hidden" name="txnHiddenID"
			id="txnHiddenID" value="${jobDetails.transactionId}" />
		<!-- Reject Job -- Passing these values -->
		<form:input path="uploadJobId" type="hidden" name="jobidValue"
			id="jobidValue" value="${jobDetails.uploadJobId}" />
		<form:input path="investigationType" type="hidden"
			name="investigationType" id="investigationType"
			value="${jobDetails.investigationType}" />
	</form:form>
</div>

<div class="c"></div>

<!-- content end -->

<script type="text/javascript">
	var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1");
</script>


<script type="text/javascript">
var rotImagId;
var loadImg="0";
	function callBack() {
		this.form.action = "${investigationJobUrl}";
		this.form.submit();
	}

	$('#Submit_Btn')
			.click(
					function() {
						var flag = 0;
						var remarksData = $('#Remarks_tab').val();
						var jobId = '${jobDetails.uploadJobId}';
						//var userId = $('#userId').val();
						//var wkstnId = $('#wkstnId').val();
						if (remarksData == "" || trim(remarksData) == "") {
							flag = flag + 1;
							$(".error_msg").html("Please Enter the Remarks ");
							return false;
						}
						//document.forms["remarksForm"].remarksData = remarksData;
						//document.forms["remarksForm"].jobId = jobId;
						//document.forms["remarksForm"].userId = userId;
						//document.forms["remarksForm"].wkstnId = wkstnId;
						$
								.ajax({
									url : '<c:url value="/servlet/investigation/remarksTab" />',
									data : "remarksData=" + remarksData
											+ "&jobId=" + jobId,
									error : function(response) {
										$(".border_success").hide();
										$(".border_error").show();
									},
									success : function(response) {
										$(".border_error").hide();
										$(".border_success").show();
									}
								});
					});
	
	function trim(str) {
		return str.replace(/^\s+|\s+$/g, "");
	}
</script>


<!-- Script for Reject Card -->
<script type="text/javascript">	
function doSubmitReject() {
	   var reason = $("#rejectedReason").val();
		var remarks = $("#rejectRemarks").val();
        //alert(reason);
       // alert(remarks);
		if (reason == "") {
			alert('Please select Reason For Rejection');
			return false;
		}
		if (remarks == "" || trim(remarks) == "") {
			alert('Please provide Remarks For Rejection');
			return false;
		} 
		
		var url = '${rejectUrl}';
		document.forms["rejectRemarksForm"].action = url;
    	document.forms["rejectRemarksForm"].submit();
	}

	function trim(str) {
		return str.replace(/^\s+|\s+$/g, "");
	}
</script>
<!-- [chris][20130815] added CC logic - start -->
<!-- Jquery dialog box script for View EH button -->
<script>
	$(function() {
		$("#dialog-viewEH")
				.dialog(
						{
							resizable : false,
							modal : true,
							autoOpen : false,
							width : 1050,
							height : 500,
							resizable : false,
							 open : function() {  
								    // Disable Approve if the xml data is already updated
						            var transDataFlag = '${isTransactionLogFlag}'; 
						            if(transDataFlag == 'true'){
						            	$(".ui-dialog-buttonpane button:contains('Approve')").removeClass('button_grey').button("disable");
							            }
						            $('.ui-dialog-buttonpane span').addClass(
						            'button_grey'); 
						           },
							show : {
								effect : "fade",
								duration : 1000
							},
							hide : {
								//effect: "explode",
								duration : 1000
							},
							buttons : {
								Approve : function() {
									var mainTransactionId = '${jobDetails.transactionId}';
									var jobId = '${jobDetails.uploadJobId}';
									var mainTransactionIds = mainTransactionId
											+ "," + jobId;
									$
											.post(
													'${viewEHUrl}',
													$('#jobDetailsForm')
															.serialize(),
															function(data) {
																//alert(data);
																if (data == 'success') {
															$(
																	"#savedialog-confirm")
																	.dialog(
																			'option',
																			'title',
																			'Status');
															$(
																	"#savedialog-confirm")
																	.html(
																			'Successfully updated the applicant details.');
															$(
																	"#savedialog-confirm")
																	.dialog(
																			'open');
														} else if (data == 'fail') {
															$(
																	"#faildialog-confirm")
																	.dialog(
																			'option',
																			'title',
																			'Status');
															$(
																	"#faildialog-confirm")
																	.html(
																			'Failed to update the applicant details.');
															$(
																	"#faildialog-confirm")
																	.dialog(
																			'open');
														}
													});

								},
								Cancel : function() {
									$(this).dialog("close");
								}
							}
						});

		$("#view_eh_btn").click(function() {
			$("#dialog-viewEH").dialog("open");
		});
	});
	function doSubmitViewEH() {
		var mainTransactionId = '${jobDetails.transactionId}';
		this.form.action = "${viewEHUrl}/" + mainTransactionId;
		this.form.submit();
	}
</script>
<!-- [chris][20130815] added CC logic - end -->
<!-- Jquery dialog box script for Approve button -->
<script>
	$(function() {
		$("#dialog-approve").dialog({
			resizable : false,
			modal : true,
			autoOpen : false,
			width : 500,
			resizable : false,
			show : {
				effect : "fade",
				duration : 1000
			},
			hide : {
				//effect: "explode",
				duration : 1000
			},
			buttons : {
				Ok : function() {
					doSubmitApprove();
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			}
		});

		$("#approve_btn").click(function() {
			$("#dialog-approve").dialog("open");
		});
	});
	function doSubmitApprove() {
		var approveRemarks = $("#approveRemarks").val();
		var url = '${approveUrl}';
		document.forms["approveForm"].action = url;
	    document.forms["approveForm"].submit();
	}
</script>

<!-- Jquery Dialog box script for Reject button -->
<script>
	$(function() {
		$("#dialog-reject").dialog({
			resizable : false,
			modal : true,
			autoOpen : false,
			width : 600,
			height : 450,
			resizable : false,
			show : {
				effect : "fade",
				duration : 1000
			},
			hide : {
				// effect: "explode",
				duration : 1000
			},
			buttons : {
				Ok : function() {
					doSubmitReject();

				},
				Cancel : function() {
					$(this).dialog("close");
				}
			}
		});

		$("#reject_btn").click(function() {
			$("#dialog-reject").dialog("open");
		});
	});
</script>

<!-- Jquery dialog box script for Cancel button -->
<script>
	$(function() {
		$("#dialog1").dialog({
			resizable : false,
			modal : true,
			autoOpen : false,
			width : 400,
			resizable : false,
			show : {
				effect : "fade",
				duration : 1000
			},
			hide : {
				// effect: "explode",
				duration : 1000
			},
			buttons : {
				Yes : function() {
					callBack();
				},
				No : function() {
					$(this).dialog("close");
				}
			}
		});

		$("#cancel_btn").click(function() {
			$("#dialog1").dialog("open");
		});

		$("#savedialog-confirm")
				.dialog(
						{
							modal : true,
							autoOpen : false,
							width : 500,
							resizable : true,
							show : {
								effect : "fade",
								duration : 200
							},
							hide : {
							//effect: "explode",
							//duration: 1000
							},
							buttons : {
								Ok : function() {
									$(this).dialog("close");
									document.forms["jobDetailsForm"].action = '${startInvestigationUrl}/'
											+ '${jobDetails.uploadJobId}';
									document.forms["jobDetailsForm"].submit();
								}
							}
						});

		$("#faildialog-confirm").dialog({
			modal : true,
			autoOpen : false,
			width : 500,
			resizable : true,
			show : {
				effect : "fade",
				duration : 200
			},
			hide : {
			//effect: "explode",
			//duration: 1000
			},
			buttons : {
				Ok : function() {
					$(this).dialog("close");
				}
			}
		});
	});
</script>
<!-- jQuery dialog box to cancel the transaction -->
<script>
$(function() {
$("#dialog_cancel_trans").dialog({
	resizable : false,
	modal : true,
	autoOpen : false,
	width : 600,
	height: 400,
	resizable : false,
	show : {
		effect : "fade",
		duration : 1000
	},
	hide : {
		// effect: "explode",
		duration : 1000
	},
	buttons : {
		Yes : function() {
			doCancelTransaction();
		},
		No : function() {
			$(this).dialog("close");
		}
	}
});

$("#cancel_opn_btn").click(function() {
	$("#dialog_cancel_trans").dialog("open");
});

function doCancelTransaction() {
	   var cancelReason = $("#cancelTransaction").val();
		//alert(cancelReason);
		if (cancelReason == "") {
			alert('Please select Reason For Cancelling the Transaction');
			return false;
		}
		var url = '${cancelTransnUrl}';
		document.forms["cancelTransactionForm"].action = url;
    	document.forms["cancelTransactionForm"].submit();
	}
});
</script>

<!-- Jquery Dialog box to popup the scanned document -->
<script> 
	$(function() {
		$("#dialog-scan-doc").dialog({
			resizable : false,
			modal : true,
			autoOpen : false,
			width : 800,
			height : 800,
			resizable : false,
			show : {
				effect : "fade",
				duration : 1000
			},
			hide : {
				//effect: "explode",
				duration : 1000
			},
			 open : function() {
			    var data = $('#dialog-scan-doc').data('docView');
			    var data2 = $('#dialog-scan-doc').data('docName');
			    rotImagId = $('#dialog-scan-doc').data('docName'); 
			    //show(data2 + "Div");
			    RotateScanDoc();
			   },
			   close : function() {
			    var data = $('#dialog-scan-doc').data('docView');
			    var data2 = $('#dialog-scan-doc').data('docName');
			    hide(data2 + "Div");
			   }
		});
	});

	$('body').on(
		    'click',
		    '.scanDocViewOnly',
		    function() {
		     //Scan Doc Viewing
		     var transId = '${jobDetails.transactionId}';
		     var docView = this.getAttribute("data-view");
		     var docName = this.getAttribute("data-name");
		     var docSize = '${docSize}';
		     if ( (docName == 'SC_USER_DECL' && docSize >= 15000)) {
				var url = "${scannedUserDeclPdfDocs}" + "/" + transId + "/" + docName;
				window.open(url);
		    }else if (docName == 'REF_COL_SLIP') {
				var url = "${scannedCollSlipPdfDocs}" + "/" + transId + "/" + docName;
				window.open(url);
			} 
		   else{ 
		     $("#dialog-scan-doc").data('docView', docView).data(
		       'docName', docName).dialog("open");
		}
}); 
</script>


<!-- [chris][20130815] added CC logic - start -->
<!--Jquery Dialog box div for viewEH-->

<div id="dialog-viewEH" title="View Applicant Details"
	style="display: none;">
	<div id="inner_main">
		<div id="inner_main_left">
			<table width="100%" height="200" border="0">
				<tr>
					<td width="320" height="35" align="center"
						style="font-weight: bold" class="sno"><span
						class="table_header">Applicant Details</span></td>
				</tr>
				<tr>
					<td class="demographic-tab-border-left">
						<table width="100%" border="0" cellpadding="2" class="data_table2">
							<tr>
								<td>Surname</td>
								<td>:</td>
								<td><c:out value="${candidateEHData.surname}" /></td>
							</tr>
							<tr>
								<td>First Name</td>
								<td>:</td>
								<td><c:out value="${candidateEHData.firstname}" /></td>
							</tr>
							<tr>
								<td>Surname at Birth</td>
								<td>:</td>
								<td><c:out value="${candidateEHData.surnameatbirth}" /></td>
							</tr>

							<tr>
								<td>Date Of Birth</td>
								<td>:</td>
								<td><c:out value="${candidateEHData.dob}" /></td>
							</tr>
							<tr>
								<td>Gender</td>
								<td>:</td>
								<td><c:choose>
										<c:when test="${candidateEHData.gender eq 'M' }">
											<c:out value="Male"></c:out>
										</c:when>
										<c:when test="${candidateEHData.gender eq 'F'}">
											<c:out value="Female"></c:out>
										</c:when>
										<c:when test="${candidateEHData.gender eq 'X'}">
											<c:out value="Unknown"></c:out>
										</c:when>
										<c:otherwise>
											<c:out value="${candidateEHData.gender}" />
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<td>Marital Status</td>
								<td>:</td>
								<td><c:choose>
										<c:when test="${candidateEHData.maritalStatus eq 'M'}">
											<c:out value="Married" />
										</c:when>
										<c:when test="${candidateEHData.maritalStatus eq 'S'}">
											<c:out value="Single" />
										</c:when>
										<c:when test="${candidateEHData.maritalStatus eq 'O'}">
											<c:out value="Others" />
										</c:when>
										<c:otherwise>
											<c:out value="${candidateEHData.maritalStatus}" />
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<!-- 
 * Modification History:
* 
 * 25 Sep 2013 (sailaja): To display the Spouse Details.
*/ -->
							<tr>
								<td>Spouse Nin</td>
								<td>:</td>
								<td><c:out value="${candidateEHData.spouseNin}" />
								</td>
							</tr>
							<tr>
								<td>Spouse Name</td>
								<td>:</td>
								<td><c:out value="${candidateEHData.spouseFirstName}" />
								</td>
							</tr>
							<tr>
								<td>Spouse Surname</td>
								<td>:</td>
								<td><c:out value="${candidateEHData.spouseSurName}" />
								</td>
							</tr>
							<tr>
								<td>Option Surname</td>
								<td>:</td>
								<td><c:choose>
										<c:when test="${candidateEHData.optionSurname eq 'OWN'}">
											<c:out value="Own Surname at Birth" />
										</c:when>
										<c:when test="${candidateEHData.optionSurname eq 'SPOUSE'}">
											<c:out value="Spouse Surname at Birth" />
										</c:when>
										<c:when
											test="${candidateEHData.optionSurname eq 'OWN-SPOUSE'}">
											<c:out value="Adjoined (Own-Spouse) Surname at Birth" />
										</c:when>
										<c:when
											test="${candidateEHData.optionSurname eq 'SPOUSE-OWN'}">
											<c:out value="Adjoined (Spouse-Own) Surname at Birth" />
										</c:when>
										<c:when
											test="${candidateEHData.optionSurname eq 'OWN_SPOUSE'}">
											<c:out value="Adjoined (Own Spouse) Surname at Birth" />
										</c:when>
										<c:when
											test="${candidateEHData.optionSurname eq 'SPOUSE_OWN'}">
											<c:out value="Adjoined (Spouse Own) Surname at Birth" />
										</c:when>
										<c:otherwise>
											<c:out value="${candidateEHData.optionSurname}" />
										</c:otherwise>
									</c:choose></td>
							</tr>
							<!-- Modification end -->
							<tr>
								<td>Exception Description</td>
								<td>:</td>
								<td><c:out value="${candidateEHData.exceptionDescription}" />
								</td>
							</tr>

						</table></td>
				</tr>
			</table>
		</div>
		<div id="inner_main_right">
			<c:if test="${empty candidateCPDData}">
				<span style="color: #FF0000; font-size: 15px;">No CPD Data
					Found.</span>
			</c:if>
			<c:if test="${not empty candidateCPDData}">
				<table width="100%" height="200" border="0">
					<tr>
						<td width="320" height="35" align="center"
							style="font-weight: bold" class="sno"><span
							class="table_header">Data from CPD</span></td>
					</tr>
					<tr>
						<td class="demographic-tab-border-right">
							<table width="100%" border="0" cellpadding="2"
								class="data_table2">
								<tr>
									<td>Surname</td>
									<td>:</td>
									<td><c:choose>
											<c:when
												test="${candidateCPDData.surname eq candidateEHData.surname}">
												<c:out value="${candidateCPDData.surname}" />
									</c:when>
									<c:otherwise>
										<div class="matchStyle">
											<c:out value="${candidateCPDData.surname}" />
										</div>
									</c:otherwise>
									</c:choose>
									</td>
								</tr>
								<tr>
									<td>First Name</td>
									<td>:</td>
									<td id="fn_ric"><c:choose>
											<c:when
												test="${candidateCPDData.firstName eq candidateEHData.firstname}">
												<c:out value="${candidateCPDData.firstName}" />
									</c:when>
									<c:otherwise>
										<div class="matchStyle">
											<c:out value="${candidateCPDData.firstName}" />
										</div>
									</c:otherwise>
									</c:choose>
									</td>
								</tr>
								<tr>
									<td>Surname at Birth</td>
									<td>:</td>
									<td><c:choose>
											<c:when
												test="${candidateCPDData.surnameAtBirth eq candidateEHData.surnameatbirth}">
												<c:out value="${candidateCPDData.surnameAtBirth}" />
									</c:when>
									<c:otherwise>
										<div class="matchStyle">
											<c:out value="${candidateCPDData.surnameAtBirth}" />
										</div>
									</c:otherwise>
									</c:choose>
									</td>
								</tr>

								<tr>
									<td>Date Of Birth</td>
									<td>:</td>
									<td id="dob_ric"><c:choose>
											<c:when test="${cpdDob eq candidateEHData.dob}">
												<c:out value="${cpdDob}" />
									</c:when>
									<c:otherwise>
										<div class="matchStyle">
											<c:out value="${cpdDob}" />
										</div>
									</c:otherwise>
									</c:choose>
									</td>
								</tr>
								<tr>
									<td>Gender</td>
									<td>:</td>
									<td id="g_ric"><c:choose>
											<c:when
												test="${candidateCPDData.sex eq candidateEHData.gender}">
												<c:choose>
													<c:when test="${candidateCPDData.sex eq 'M' }">
														<c:out value="Male"></c:out>
													</c:when>
													<c:when test="${candidateCPDData.sex eq 'F'}">
														<c:out value="Female"></c:out>
													</c:when>
													<c:when test="${candidateCPDData.sex eq 'X'}">
														<c:out value="Unknown"></c:out>
													</c:when>
													<c:otherwise>
														<c:out value="${candidateCPDData.sex}" />
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<div class="matchStyle">
													<c:choose>
														<c:when test="${candidateCPDData.sex eq 'M' }">
															<c:out value="Male"></c:out>
														</c:when>
														<c:when test="${candidateCPDData.sex eq 'F'}">
															<c:out value="Female"></c:out>
														</c:when>
														<c:when test="${candidateCPDData.sex eq 'X'}">
															<c:out value="Unknown"></c:out>
														</c:when>
														<c:otherwise>
															<c:out value="${candidateCPDData.sex}" />
														</c:otherwise>
													</c:choose>
												</div>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<td>Marital Status</td>
									<td>:</td>
									<td><c:choose>
											<c:when
												test="${candidateCPDData.maritalStatus eq candidateEHData.maritalStatus}">
												<c:choose>
													<c:when test="${candidateCPDData.maritalStatus eq 'M'}">
														<c:out value="Married" />
													</c:when>
													<c:when test="${candidateCPDData.maritalStatus eq 'S'}">
														<c:out value="Single" />
													</c:when>
													<c:when test="${candidateCPDData.maritalStatus eq 'O'}">
														<c:out value="Others" />
													</c:when>
													<c:otherwise>
														<c:out value="${candidateCPDData.maritalStatus}" />
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<div class="matchStyle">
													<c:choose>
														<c:when test="${candidateCPDData.maritalStatus eq 'M'}">
															<c:out value="Married" />
														</c:when>
														<c:when test="${candidateCPDData.maritalStatus eq 'S'}">
															<c:out value="Single" />
														</c:when>
														<c:when test="${candidateCPDData.maritalStatus eq 'O'}">
															<c:out value="Others" />
														</c:when>
														<c:otherwise>
															<c:out value="${candidateCPDData.maritalStatus}" />
														</c:otherwise>
													</c:choose>
												</div>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<!-- 
 * Modification History:
* 
 * 25 Sep 2013 (sailaja): To display the Spouse Details.
*/ -->
								<tr>
									<td>Spouse Nin</td>
									<td>:</td>
									<td><c:choose>
											<c:when
												test="${candidateCPDData.spouseNin eq candidateEHData.spouseNin}">
												<c:out value="${candidateCPDData.spouseNin}" />
											</c:when>
											<c:otherwise>
												<div class="matchStyle">
													<c:out value="${candidateCPDData.spouseNin}" />
												</div>
											</c:otherwise>
										</c:choose></td>
								</tr>

								<tr>
									<td>Spouse Name</td>
									<td>:</td>
									<td><c:choose>
											<c:when
												test="${candidateCPDData.spouseFirstName eq candidateEHData.spouseFirstName}">
												<c:out value="${candidateCPDData.spouseFirstName}" />
											</c:when>
											<c:otherwise>
												<div class="matchStyle">
													<c:out value="${candidateCPDData.spouseFirstName}" />
												</div>
											</c:otherwise>
										</c:choose></td>
								</tr>
								<tr>
									<td>Spouse Surname</td>
									<td>:</td>
									<td><c:choose>
											<c:when
												test="${candidateCPDData.spouseSurname eq candidateEHData.spouseSurName}">
												<c:out value="${candidateCPDData.spouseSurname}" />
											</c:when>
											<c:otherwise>
												<div class="matchStyle">
													<c:out value="${candidateCPDData.spouseSurname}" />
												</div>
											</c:otherwise>
										</c:choose></td>
								</tr>
								<tr>
									<td>Option Surname</td>
									<td>:</td>
									<td><c:choose>
											<c:when
												test="${candidateCPDData.optionSurname eq candidateEHData.optionSurname}">
												<c:choose>
													<c:when test="${candidateEHData.optionSurname eq 'OWN'}">
														<c:out value="Own Surname at Birth" />
													</c:when>
													<c:when test="${candidateEHData.optionSurname eq 'SPOUSE'}">
														<c:out value="Spouse Surname at Birth" />
													</c:when>
													<c:when
														test="${candidateEHData.optionSurname eq 'OWN-SPOUSE'}">
														<c:out value="Adjoined (Own-Spouse) Surname at Birth" />
													</c:when>
													<c:when
														test="${candidateEHData.optionSurname eq 'SPOUSE-OWN'}">
														<c:out value="Adjoined (Spouse-Own) Surname at Birth" />
													</c:when>
													<c:when
														test="${candidateEHData.optionSurname eq 'OWN_SPOUSE'}">
														<c:out value="Adjoined (Own Spouse) Surname at Birth" />
													</c:when>
													<c:when
														test="${candidateEHData.optionSurname eq 'SPOUSE_OWN'}">
														<c:out value="Adjoined (Spouse Own) Surname at Birth" />
													</c:when>
													<c:otherwise>
														<c:out value="${candidateEHData.optionSurname}" />
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<div class="matchStyle">
													<c:choose>
														<c:when test="${candidateEHData.optionSurname eq 'OWN'}">
															<c:out value="Own Surname at Birth" />
														</c:when>
														<c:when
															test="${candidateEHData.optionSurname eq 'SPOUSE'}">
															<c:out value="Spouse Surname at Birth" />
														</c:when>
														<c:when
															test="${candidateEHData.optionSurname eq 'OWN-SPOUSE'}">
															<c:out value="Adjoined (Own-Spouse) Surname at Birth" />
														</c:when>
														<c:when
															test="${candidateEHData.optionSurname eq 'SPOUSE-OWN'}">
															<c:out value="Adjoined (Spouse-Own) Surname at Birth" />
														</c:when>
														<c:when
															test="${candidateEHData.optionSurname eq 'OWN_SPOUSE'}">
															<c:out value="Adjoined (Own Spouse) Surname at Birth" />
														</c:when>
														<c:when
															test="${candidateEHData.optionSurname eq 'SPOUSE_OWN'}">
															<c:out value="Adjoined (Spouse Own) Surname at Birth" />
														</c:when>
														<c:otherwise>
															<c:out value="${candidateEHData.optionSurname}" />
														</c:otherwise>
													</c:choose>
												</div>
											</c:otherwise>
										</c:choose></td>
								</tr>
								<!-- Modification end -->
								<tr>
									<td>&nbsp;</td>
								</tr>
							</table></td>
					</tr>
				</table>
			</c:if>
		</div>
	</div>


</div>
<!-- [chris][20130815] added CC logic - end -->

<!-- Jquery Dialog box div for Approve -->
<div id="dialog-approve" title="Approve Card" style="display: none;">
<form:form modelAttribute="approveForm" id="approveForm">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>Are you sure? You
		want to Continue to Approve?
	</p>
	<br /> <br />
	<h4 style="font-size: 20px; color: #0000FF;">
		Remarks&nbsp;<span style="color: #808080; font-size: 15px;">[Optional]</span>
	</h4>
	<form:textarea path="remarksData" id="approveRemarks" name="approveRemarks" type="text"
		cols="150" rows="10"></form:textarea>
	<form:input type="hidden" path="uploadJobId" value="${jobDetails.uploadJobId}" />
	<form:input type="hidden" path="investigationType" value="${jobDetails.investigationType}" />
	&nbsp;
</form:form>
</div>
<!--Jquery Dialog box div on click of Cancel button-->
<div id="dialog1" title="Cancelled" style="display: none;">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>Are you sure? You
		want to Cancel?
	</p>
</div>

<!--Jquery Dialog box div on click of Cancel Transaction button-->
<div id="dialog_cancel_trans" title="Cancel Transaction" style="display: none;">
<form:form modelAttribute="cancelTransactionForm" id="cancelTransactionForm">
<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>Are you sure? You
		want to Cancel the Transaction?
		<p>
		&nbsp;
		</p>
<form:input path="transactionId" type="hidden" name="txnHiddenID"
			id="txnHiddenID" value="${jobDetails.transactionId}" />
<form:input path="uploadJobId" type="hidden" name="jobidValue"
			id="jobidValue" value="${jobDetails.uploadJobId}" />
<form:input path="investigationType" type="hidden"
			name="investigationType" id="investigationType"
			value="${jobDetails.investigationType}" />
<span style="font-size: 18px; font-weight: bold; color: #0000FF;">Reason
			for Cancellation</span>
<form:select path="cancelTransnOption" id="cancelTransaction"
			name="cancelTransaction" var="cancelTransnOption" multiple="multiple" cssStyle="font-size: 12px; width: 300px;">
			<c:forEach var="opt" items="${cancelTransDTO.cancelTransOptions}">
				<form:option title="${opt.id.codeValue}"
					value="${opt.codeValueDesc}">
					<c:out value="${opt.codeValueDesc}" />
				</form:option>
			</c:forEach>
		</form:select>
</form:form>
</div>

<!-- Jquery Dialog pop up for scanned documents -->
<div id="dialog-scan-doc" title="Scanned Document"
	style="display: none;">
	
	<table>
		<tr>
			<c:forEach var="docItem" items="${nicTxnDocs}" varStatus="status">
				<c:choose>
					<c:when
						test="${docItem.document == null || docItem.document == ''}">
						<div style="display: none;" id="${docItem.type}Div">
							<img width="595px" height="842px" style="align: left;"
								src=<c:url value='/resources/images/No_Image.jpg'/>
								id="documentView${status.index}" alt="No Image" />
						</div>
					</c:when>
					<c:otherwise>
						<div style="display: none;" id="${docItem.type}Div">
							<c:forEach var="docListItem" items="${docItem.document}"
								varStatus="listItemStatus"> 
								<img style="display: none;" title="${docItem.documentName}"
									src="data:image/jpg;base64,${docListItem}" style="align: center" 
									id="${docItem.type}Doc" alt="SCANNED DOCUMENTS"/>
								
							</c:forEach>
						</div>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</tr>
	</table>
	<!-- JQuery Zoom in and out -->
	 <div class="wrapper">
            <div id="viewer" class="viewer iviewer_cursor"></div>
        </div>
</div>
<div id="savedialog-confirm" style="display: none;"></div>
<div id="faildialog-confirm" style="display: none;"></div>
<!-- script for the remarks hit candidate-->
<script type="text/javascript"> 
$(function(){
	

	$("#infoDialog").dialog( {
		autoOpen : false,
		width : 750,
		height : 450,
		modal : true,
		bgiframe : true,
		cache :false,
		closeOnEscape: false
	});
	
	$(".historyEventDataSmark").click(function(){
		var info = $(this).attr('ref');
		//info = info.replace(/\n/g,"<br/>");
		$("#infoDialog").text(info);
		$("#infoDialog").dialog('open');
	});
	
	$(".refreshCPDData").click(function(){
		$
		.post('${refreshUrl}',
				$('#jobDetailsForm')
						.serialize(),
						function(data) {
							//alert(data);
							if (data == 'success') {
						$("#refreshCPDData_div").show();
						$("#oldCPDData").hide();
						$("#dataFromRic_Space").show();
					} else if (data == 'fail') {
						$(
								"#faildialog-confirm")
								.dialog(
										'option',
										'title',
										'Status');
						$(
								"#faildialog-confirm")
								.html(
										'Failed to refresh the CPD data.');
						$(
								"#faildialog-confirm")
								.dialog(
										'open');
					}
				});
		//alert(url);
	});
	
	
});


$("#cancelTransaction").multiselectbox({
	  selectedList: 1,
	  header:true,
	  noneSelectedText: 'Please Select Reason(s)'
	 });


//jQuery Scanned Document Zoom and Rotation
function RotateScanDoc(){
var rotatId="#"+rotImagId+"Doc"; 
var	src_img = $(rotatId).attr('src');
 var iv1 = $("#viewer").iviewer({
			src: src_img
		}); 
	if(loadImg=="0"){ 
		loadImg="1";  
	}else{  
		iv1.iviewer('loadImage', src_img);
						return false;
	}
}

 </script>



<div id="msgDialog" title="Alert">
<div class="isa_info" align="center">
		<span style="font-size: 40" id="msgDialogSpan"></span>
</div>
</div> 
<script>
$( document ).ready(function() {
	$('#oldCPDData').show();
	$('#refreshCPDData_div').hide();
	$('#dataFromRic_Space').hide();
	});
</script>
 <style>
            .viewer
            {
                width: 700px;
                height: 700px;
                border: 1px solid black;
                position: relative;
            }

            .wrapper
            {
                overflow: hidden;
            }
        </style>
 
<!-- Dialog box for the remarks of hit candidate -->
<div id="infoDialog" style="display: none;" title="Candidate Remarks">
</div>
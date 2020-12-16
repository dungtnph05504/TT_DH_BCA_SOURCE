<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="col" uri="colfunction"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:url var="homeUrl" value="/servlet/user/home" />
<c:url var="userJobListUrl" value="/servlet/adminConsole/userManagement" />
<c:url var="roleJobListUrl" value="/servlet/adminConsole/roleManagement" />
<c:url var="workstationJobListUrl"
	value="/servlet/adminConsole/workstationManagement" />
<c:url var="batchJobListUrl"
	value="/servlet/adminConsole/batchJobManagement" />
<c:url var="reportListUrl" value="/servlet/adminConsole/userManagement" />
<c:url var="codeTablesListUrl"
	value="/servlet/adminConsole/codeManagement" />
<c:url var="proofDocMatrixUrl"
	value="/servlet/proofDocMatrixController/proofDocMatrix" />
<c:url var="paymentMatrixUrl"
	value="/servlet/paymentMatrixController/paymentMatrix" />
<c:url var="investigationJobUrl"
	value="/servlet/investigation/investigation" />
<c:url var="investigationProcessUrl"
	value="/servlet/investionProcess/showInvestigation" />
<c:url var="invesProcessUrl"
	value="/servlet/investionProcess/invesProcess" />	
<c:url var="investigationRejectJobUrl"
	value="/servlet/supervisorController/investigationRejectedList" />
<c:url var="investigationSuspendedJobUrl"
	value="/servlet/fraudCaseManagement/investigationSuspendedList" />
<c:url var="investigationAssignedJobUrl"
	value="/servlet/investigationAssigned/investigationAssignedList" />   
<c:url var="searchTranUrl"
	value="/servlet/storage/searchTranaction" />
<c:url var="searchPersonUrl"
	value="/servlet/storage/searchPerson" />	
<c:url var="searchPassportUrl"
	value="/servlet/storage/searchPassport" />
<c:url var="searchImmiHisUrl"
	value="/servlet/central/danhsachlsxnc" />
<c:url var="fraudCaseManagementAssignedJobUrl"
	value="/servlet/fraudCaseManagementAssigned/fraudCaseManagementAssignedList" />
<c:url var="investigationJobAssignmentUrl"
	value="/servlet/investigationAssignment/investigationAssignmentList" />
<c:url var="investigationJobAssignment1Url"
	value="/servlet/investigationAssignment/investigationAssignmentList1" />	
<c:url var="fraudCaseManagementJobAssignmentUrl"
	value="/servlet/fraudCaseManagementAssignment/fraudCaseManagementAssignmentList" />
<c:url var="auditEnquiryAccessLogUrl"
	value="/servlet/nicAuditEnquiry/auditEnquiryAccessLog" />
<c:url var="userSessionEnquiryUrl"
	value="/servlet/userSessionEnquiry/userSessionEnquirySearch" />
<c:url var="batchJobEnquiryUrl"
	value="/servlet/nicEnquiry/batchJobEnquiry" />
<c:url var="transactionEnquiryUrl" value="/servlet/nicEnquiry/jobQueue" />
<c:url var="txnEnquiryUrl" value="/servlet/transactionEnquiry/init" />
<c:url var="nicEnquiryUrl" value="/servlet/nicMainEnquiry/init" />
<c:url var="cardEnquiryUrl" value="/servlet/cardEnquiry/init" />
<c:url var="passportEnquiryUrl" value="/servlet/passportEnquiry/init" />
<c:url var="rePrintUrl" value="/servlet/rePrintController/init" />
<c:url var="dataSynMonitoringUrl"
	value="/servlet/dataSyncMonitorController/init" />
<c:url var="rejectApplicationReportUrl"
	value="/servlet/nicReports/rejectApplicationReport" />
<c:url var="loginLogoffReportUrl"
	value="/servlet/nicReports/loginLogoffReport" />
<c:url var="accessLogReportUrl"
	value="/servlet/nicReports/accessLogReport" />
<c:url var="transactionReportUrl"
	value="/servlet/nicReports/transactionReport" />
<!--  Report Management -->
<c:url var="definitionUrl" value="/servlet/report/definition" />
<c:url var="reportUrl" value="/servlet/report/upload" />
<c:url var="reportGenerationUrl"
	value="/servlet/reportmgmt/generation/'NONE'" />
<!--  Code Management -->
<c:url var="codeMgmtUrl" value="/servlet/codeMgmt/view" />
<!--  Batch Job Management -->
<c:url var="batchJobAdminUrl" value="/servlet/batchJobMgmt/view" />
<c:url var="batchJobMonitorUrl" value="/servlet/batchJobMnt/view" />
<!--  Parameter Management -->
<c:url var="paramAdminUrl"
	value="/servlet/parameterController/getParamList" />
<c:url var="changePasswordUrl"
	value="/servlet/changePasswordController/index" />
<c:url var="logOutUrl" value="/servlet/user/logout" />
<c:url var="txnRptUrl" value="/servlet/txnReport/showricTxnRpt" />
<c:url var="pymtRptUrl" value="/servlet/pymtReport/showricPymtRpt" />
<c:url var="wavRptUrl" value="/servlet/pymtReport/showricWaiverRpt" />
<c:url var="txnUploadRptUrl"
	value="/servlet/txnReport/showricTxnUpldRpt" />
<c:url var="rejTxnRptUrl" value="/servlet/txnReport/showTxnRejRpt" />
<c:url var="crdDwnLdRptUrl"
	value="/servlet/txnReport/showricBatchCardInfoRpt" />
<c:url var="crdColRptUrl"
	value="/servlet/cardStatusReport/showCardCollectedRpt" />
<c:url var="crdRejRptUrl"
	value="/servlet/cardStatusReport/showCardRejectedRpt" />
<c:url var="crdExpRptUrl"
	value="/servlet/cardStatusReport/showCardExpiredRpt" />
<c:url var="crdReActRptUrl"
	value="/servlet/cardStatusReport/showReActivateCard" />
<c:url var="crdDeActRptUrl"
	value="/servlet/cardStatusReport/showDeActivateCard" />
<c:url var="exphandlRptUrl"
	value="/servlet/txnReport/showExceptionHandlingRpt" />
<c:url var="crdDelRptUrl"
	value="/servlet/cardStatusReport/showCardDeliveryRpt" />
<c:url var="unColCrdStRptUrl"
	value="/servlet/cardStatusReport/showUnColCardStRpt" />
<c:url var="exprPrntRptUrl" value="/servlet/txnReport/showExprsPrintRpt" />
<c:url var="crdDelStRptUrl"
	value="/servlet/cardStatusReport/showCardDeliveryRpt" />
<c:url var="lostNfoundRptUrl"
	value="/servlet/txnReport/showlostNfoundRpt" />
<!--  Site Management -->
<%-- <c:url var="siteGroupUrl" value="/servlet/siteMgmt/groupView" />
<c:url var="siteRepoUrl" value="/servlet/siteMgmt/repoView" /> --%>
<c:url var="siteUrl" value="/servlet/siteMgmt/view" />
<c:url var="statRptUrl" value="/servlet/trpStatistic/view" />
<c:url var="statSftpRptUrl" value="/servlet/sftpStatistic/view" />
<c:url var="mainProcessUrl" value="/servlet/workflowProcess/main" />
<c:url var="JobSyncSingerUrl"
	value="/servlet/syncSinger/getSyncSingerJob" />
<c:url var="JobPersoUrl" value="/servlet/investigation/getPersoJob" />
<c:url var="JobApproveStatusUrl"
	value="/servlet/investigation/getApproveJob" />
<c:url var="ListApproveAssignmentUrl"
	value="/servlet/investigation/getApproveAssignment" />
<c:url var="JobInvestigationConfirmUrl" value="/servlet/investigationConfirm/investigationConfirmList" />
<c:url var="JobInvestigationConfirmAgainUrl" value="/servlet/investigationConfirm/JobInvestigationConfirmAgain" />
<c:url var="JobInvestigationApproveUrl" value="/servlet/investigationConfirm/JobInvestigationApprove" />
<c:url var="NoteApproveUrl" value="/servlet/investigationConfirm/noteApprove" />
<c:url var="JobInvestigationPendingBcaUrl" value="/servlet/investigationBca/investigationPendingBcaList" />
<c:url var="JobInvestigationApproveBossUrl" value="/servlet/investigationBoss/investigationApproveBossList" />
<c:url var="JobInvestigationPendingBossUrl" value="/servlet/investigationBoss/investigationPendingBossList1" />
<c:url var="JobInvestigationRejectUrl" value="/servlet/investigationReject/investigationRejectList" />
<c:url var="DestroyPassportUrl" value="/servlet/destroyPassport/showListPassport" />
<c:url var="listHandoverUrl" value="/servlet/listHandover/listHandover" />
<c:url var="listLostUrl" value="/servlet/investigationLost/investigationLostList" />
<c:url var="persoLocationsUrl" value="/servlet/persoLocation/persoLocations" />
<c:url var="inventoryItemsUrl" value="/servlet/persoLocation/inventoryItems" />
<c:url var="inventoryShowUrl" value="/servlet/persoLocation/inventoryShow" />
<c:url var="inventoryUrl" value="/servlet/persoLocation/inventory" />
<c:url var="signerGovsUrl" value="/servlet/signerController/signerGovs" />
<c:url var="pendingPassportNoOUrl" value="/servlet/pendingPassportNo/pendingPassportNoList" />
<c:url var="importTransactionsUrl" value="/servlet/batchJobMgmt/importTransactionsGet" />
<c:url var="decisionManagerUrl" value="/servlet/decisionController/decisionManagerList" />
<c:url var="officalNationUrl" value="/servlet/officeNationController/officalNationList" />
<c:url var="officalVisaUrl" value="/servlet/officeVisaController/officalVisaList" />
<c:url var="jobStatusPPUrl" value="/servlet/investigationConfirm/successPersoList" />
<c:url var="reportCqddUrl" value="/servlet/reportCQDD/reportCqdd" />
<c:url var="queuesJobListUrl" value="/servlet/queuesJobListController/queuesJobList" />
<c:url var="logJobListUrl" value="/servlet/queuesJobListController/listLogJobSchedule" />
<c:url var="JobInvestigationConfirmDSQUrl" value="/servlet/investigationConfirmDSQ/investigationConfirmDSQList" />
<c:url var="showCentralHeadQuarterUrl" value="/servlet/central/headQuarter" />
<c:url var="showPersonalizedUrl" value="/servlet/central/personalized" />
<c:url var="showProcessUrl" value="/servlet/central/process" />
<c:url var="showIssuanceUrl" value="/servlet/central/issuance" />
<c:url var="showSearchXNCUrl" value="/servlet/central/searchxnc" />
<c:url var="statisticalUrl" value="/servlet/central/statistical" />
<c:url var="transmissionUrl" value="/servlet/central/transmission" />
<c:url var="txnPassportUrl" value="/servlet/central/tracuuhosohochieu" />
<c:url var="equalTransactionUrl" value="/servlet/central/equalTransaction" />
<c:url var="searchALLXNCUrl" value="/servlet/central/searchALLXNC" />
<c:url var="changProcessJobUrl" value="/servlet/central/changProcessJob" />
<c:url var="changApiJobUrl" value="/servlet/central/changApiJob" />
<c:url var="showQueueUrl" value="/servlet/central/showQueue" />
<c:url var="showJobUrl" value="/servlet/central/showJob" />
<c:url var="actionUrl" value="/servlet/user/login" />
<c:url var="showAirlineUrl" value="/servlet/adminImmi/showAirline" />
<c:url var="showAirportUrl" value="/servlet/adminImmi/showAirport" />
<c:url var="showFlightRouterUrl" value="/servlet/adminImmi/showFlightRouter" />
<c:url var="showFlightUrl" value="/servlet/adminImmi/showFlight" />
<c:url var="showVisaNoUrl" value="/servlet/adminImmi/showVisaNo" />
<c:url var="showPurposeUrl" value="/servlet/adminImmi/showPurpose" />
<c:url var="showPkdUrl" value="/servlet/pkdCtrl/view" />
<c:url var="reportRegProcessUrl" value="/servlet/central/reportRegProcess" />
<c:url var="reportPackLDSUrl" value="/servlet/central/reportPackView" />
<c:url var="reportPersoUrl" value="/servlet/central/reportPersoView" />
<c:url var="showBorderGateUrl" value="/servlet/borderGate/showBorderGate" />

<c:url var="reportTranLost" value="/servlet/storage/showReportTranLost" />

<!-- edit 3-6-202 -->
<c:url var ="listLogUrl" value="/servlet/listLogWs/init"/>
<c:url var="statusticUrl" value="/servlet/statustical/init"/>
<!-- hoald -->
<c:url var ="listLogCheckConnectionUrl" value="/servlet/listLogCheckConnection/viewList"/>
<c:url var ="listRptDataUrl" value="/servlet/listRptStatisticsTransmitData/viewListRptData"/>

<c:url var="showTemplatePassport" value="/servlet/central/showTemplatePassport" />
</style>
<script type="text/javascript">
	window.history.forward(1);
</script>

<form:form name="investigationMenuForm" id="investigationForm"
	method="post">
	<c:if test="${not empty sessionScope.userSession}">
		<div class="container-fluid banner none-padding">
        <div class="header">
            <div class="col-sm-12 banner">
                <div class="col-sm-2">
                    <a href="${homeUrl}"><img class="logo" src="<c:url value="/resources/style1/images/logo1.png" />"></a>
                </div>
                <div class="col-sm-7" style="margin-top: 5px;">
                <c:if test="${col:isPreviledged('NIC_SOTRAGE',sessionScope.userSession) or
								col:isPreviledged('MAIN_IMMI_HISTORY',sessionScope.userSession) }">
                	<ul class="ul-menu navbar-nav nav-menu">
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">Lưu trữ<span class="caret"></span></a>
								 
								<ul class="dropdown-menu">
									<c:if
										test="${col:isPreviledged('NIC_SOTRAGE',sessionScope.userSession)}">
										<li><a href="#" onclick="storage1()">Tra cứu hộ chiếu</a></li>
										<li><a href="${searchPersonUrl}" onclick=Tools.displayWait();>Danh sách công dân</a></li>
										<li><a href="${searchTranUrl}" onclick=Tools.displayWait();>Danh sách hồ sơ</a></li>
										<li><a href="${searchImmiHisUrl}" onclick=Tools.displayWait();>Tra cứu lịch sử xuất nhập cảnh</a></li>
									</c:if>
								</ul>
								
                        </li>
                    </ul>
                    </c:if>
                    <%-- <ul class="ul-menu navbar-nav nav-menu">
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">Phân công<span class="caret"></span></a>
                              <c:if test="${col:isPreviledged('NIC_INV_INV_ASS',sessionScope.userSession) or
								col:isPreviledged('NIC_INV_INV_ASSMENT',sessionScope.userSession) }">
								<ul class="dropdown-menu">
									<!--<c:if
										test="${col:isPreviledged('NIC_INV_INV_ASSMENT',sessionScope.userSession)}">
										<li><a onclick=assignmentJobFunction();>Phân công theo hồ sơ</a></li>
									</c:if>-->
										<li><a href="${investigationJobAssignment1Url}" onclick=Tools.displayWait();>DS phân công xử lý</a></li>
									<c:if
										test="${col:isPreviledged('NIC_INV_INV_ASS',sessionScope.userSession)}">
										<li><a href="${investigationAssignedJobUrl}" onclick=Tools.displayWait();>DS đã phân công</a></li>
									</c:if>
								</ul>
								
							</c:if>
                        </li>
                    </ul>
                    <ul class="ul-menu navbar-nav nav-menu">
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">Xử lý<span class="caret"></span></a>
                             <c:if
							test="${col:isPreviledged('NIC_INV_INV',sessionScope.userSession) or
						col:isPreviledged('NIC_INV_FCM',sessionScope.userSession) or
						col:isPreviledged('NIC_INV_FCM_ASS',sessionScope.userSession) or
						col:isPreviledged('NIC_INV_FCM_ASSMENT',sessionScope.userSession)
						or
						col:isPreviledged('NIC_INV_CONFIRM',sessionScope.userSession)
						or
						col:isPreviledged('NIC_INV_BCA',sessionScope.userSession)
						or
						col:isPreviledged('NIC_INV_BOSS',sessionScope.userSession)
						or
						col:isPreviledged('NIC_INV_REJECT',sessionScope.userSession)
						or
						col:isPreviledged('NIC_HANDOVER_LIST',sessionScope.userSession)
						or
						col:isPreviledged('NIC_INV_LOST',sessionScope.userSession)
						or
						col:isPreviledged('NIC_PERSO_LOCATION',sessionScope.userSession)
						or
						col:isPreviledged('NIC_PENDING_PASSPORT_NO',sessionScope.userSession)
						 }">
						
								<ul class="dropdown-menu">
									<!--<c:if
										test="${col:isPreviledged('NIC_INV_INV',sessionScope.userSession)}">
										<li><a onclick=jobFunction();>Danh sách xử lý hồ sơ</a></li>
									</c:if>	-->
									<c:if
										test="${col:isPreviledged('NIC_INV_INV',sessionScope.userSession)}">
										<li><a href="${investigationProcessUrl}" onclick=Tools.displayWait();>Xử lý hồ sơ hộ chiếu</a></li>
									</c:if>	
									<!--<c:if
										test="${col:isPreviledged('NIC_INV_INV',sessionScope.userSession)}">
										<li><a onclick=jobFunction2();>Xử lý hồ sơ hộ chiếu 1</a></li>
									</c:if>	-->								
									<c:if
										test="${col:isPreviledged('NIC_INV_CONFIRM',sessionScope.userSession)}">
										<li><a href="${JobInvestigationConfirmUrl}" onclick=Tools.displayWait();>Lập danh sách B</a></li>
									</c:if>
									
									<li><a href="${JobInvestigationConfirmAgainUrl}" onclick=Tools.displayWait();>Lập lại danh sách B</a></li>
									
									<li><a href="${JobInvestigationApproveUrl}" onclick=Tools.displayWait();>Phê duyệt hồ sơ</a></li>
									
									<li><a href="${NoteApproveUrl}" onclick=Tools.displayWait();>Cập nhật thông tin hồ sơ</a></li>
									
									<li><a href="${DestroyPassportUrl}" onclick=Tools.displayWait();>Hủy hộ chiếu</a></li>
								
									<!--<c:if
										test="${col:isPreviledged('NIC_INV_BCA',sessionScope.userSession)}">
										<li><a onclick=jobInvestigationPendingBcaFunction();>Danh sách Xác minh từ BCA</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_INV_BOSS',sessionScope.userSession)}">
										<li><a onclick=jobInvestigationPendingBossFunction();>Danh sách Xét duyệt</a></li>
									</c:if>-->
									<c:if
										test="${col:isPreviledged('NIC_INV_REJECT',sessionScope.userSession)}">
										<li><a href="${JobInvestigationRejectUrl}" onclick=Tools.displayWait();>Danh sách bị từ chối</a></li>
									</c:if>
									<!--<c:if
										test="${col:isPreviledged('NIC_HANDOVER_LIST',sessionScope.userSession)}">
										<li><a onclick=listHandoverFunction();>Danh sách đã bàn giao</a></li>
									</c:if>-->
									<c:if
										test="${col:isPreviledged('NIC_INV_LOST',sessionScope.userSession)}">
										<li><a href="${listLostUrl}" onclick=Tools.displayWait();>Danh sách mất/hỏng</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_PERSO_LOCATION',sessionScope.userSession)}">
										<li><a href="${persoLocationsUrl}" onclick=Tools.displayWait();>Danh sách điểm in</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_PENDING_PASSPORT_NO',sessionScope.userSession)}">
										<li><a href="${pendingPassportNoOUrl}" onclick=Tools.displayWait();>Danh sách chờ cấp số hộ chiếu</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_PENDING_PASSPORT_NO',sessionScope.userSession)}">
										<li><a href="${jobStatusPPUrl}" onclick=Tools.displayWait();>Danh sách trạng thái in</a></li>
									</c:if>

								</ul>
							</c:if>
                        </li>
                    </ul> --%>
                      <c:if
								test="${col:isPreviledged('NIC_INV_INV',sessionScope.userSession) or
										col:isPreviledged('NIC_INV_FCM',sessionScope.userSession)}">
                    <ul class="ul-menu navbar-nav nav-menu">
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">Điều hành<span class="caret"></span></a>
                            

										<ul class="dropdown-menu">										
											<%-- <c:if
												test="${col:isPreviledged('NIC_SYNC_SINGER',sessionScope.userSession)}">
													<li><a href="${statisticalUrl}" onclick=Tools.displayWait();>Thống kê</a></li>
											</c:if> --%>
											<c:if
												test="${col:isPreviledged('NIC_SYNC_SINGER',sessionScope.userSession)}">
													<li><a href="${transmissionUrl}" onclick=Tools.displayWait();>Thống kê truyền nhận</a></li>
											</c:if>
											<c:if
												test="${col:isPreviledged('NIC_INV_CONFIRM',sessionScope.userSession)}">
												<li><a href="${showCentralHeadQuarterUrl}" onclick=Tools.displayWait();>Trung tâm điều hành</a></li>
											</c:if>
											<c:if
												test="${col:isPreviledged('NIC_INV_CONFIRM',sessionScope.userSession)}">
												<li><a href="${showProcessUrl}" onclick=Tools.displayWait();>Xử lý hồ sơ</a></li>
											</c:if>
											<c:if
												test="${col:isPreviledged('NIC_INV_CONFIRM',sessionScope.userSession)}">
												<li><a href="${showPersonalizedUrl}" onclick=Tools.displayWait();>Cá thể hóa</a></li>
											</c:if>
											<c:if
												test="${col:isPreviledged('NIC_INV_CONFIRM',sessionScope.userSession)}">
												<li><a href="${showIssuanceUrl}" onclick=Tools.displayWait();>Phát hành hộ chiếu</a></li>
											</c:if>
											<c:if
												test="${col:isPreviledged('NIC_INV_CONFIRM',sessionScope.userSession)}">
												<li><a href="${showSearchXNCUrl}" onclick=Tools.displayWait();>Kiểm soát xuất nhập cảnh</a></li>
											</c:if>
											<c:if
												test="${col:isPreviledged('NIC_INV_CONFIRM',sessionScope.userSession)}">
												<li><a href="${changProcessJobUrl}" onclick=Tools.displayWait();>Điều phối luồng</a></li>
											</c:if>
											<c:if
												test="${col:isPreviledged('NIC_INV_CONFIRM',sessionScope.userSession)}">
												<li><a href="${changApiJobUrl}" onclick=Tools.displayWait();>Điều phối API</a></li>
											</c:if>
											<c:if
												test="${col:isPreviledged('NIC_INV_CONFIRM',sessionScope.userSession)}">
												<li><a href="${showQueueUrl}" onclick=Tools.displayWait();>Quản lý hàng đợi</a></li>
											</c:if>
											<c:if
												test="${col:isPreviledged('NIC_INV_CONFIRM',sessionScope.userSession)}">
												<li><a href="${showJobUrl}" onclick=Tools.displayWait();>Quản lý hàng đợi công việc</a></li>
											</c:if>
											<!--<c:if
												test="${col:isPreviledged('NIC_INV_CONFIRM',sessionScope.userSession)}">
												<li><a href="${JobInvestigationConfirmDSQUrl}" onclick=Tools.displayWait();>Dữ liệu sinh trắc học</a></li>
											</c:if>-->
											<c:if
												test="${col:isPreviledged('NIC_PERSO_LOCATION',sessionScope.userSession)}">
												<li><a href="${persoLocationsUrl}" onclick=Tools.displayWait();>Trung tâm cá thể hóa</a></li>
											</c:if>
											<c:if
												test="${col:isPreviledged('NIC_PERSO_LOCATION',sessionScope.userSession)}">
												<li><a href="${showAirlineUrl}" onclick=Tools.displayWait();>Quản lý hãng bay</a></li>
											</c:if>
											<c:if
												test="${col:isPreviledged('NIC_PERSO_LOCATION',sessionScope.userSession)}">
												<li><a href="${showAirportUrl}" onclick=Tools.displayWait();>Quản lý sân bay</a></li>
											</c:if>
											<c:if
												test="${col:isPreviledged('NIC_PERSO_LOCATION',sessionScope.userSession)}">
												<li><a href="${showFlightRouterUrl}" onclick=Tools.displayWait();>Quản lý đường bay</a></li>
											</c:if>
											<c:if
												test="${col:isPreviledged('NIC_PERSO_LOCATION',sessionScope.userSession)}">
												<li><a href="${showFlightUrl}" onclick=Tools.displayWait();>Quản lý chuyến bay</a></li>
											</c:if>
											<c:if
												test="${col:isPreviledged('NIC_PERSO_LOCATION',sessionScope.userSession)}">
												<li><a href="${showVisaNoUrl}" onclick=Tools.displayWait();>Quản lý thị thực</a></li>
											</c:if>
											<c:if
												test="${col:isPreviledged('NIC_PERSO_LOCATION',sessionScope.userSession)}">
												<li><a href="${showPurposeUrl}" onclick=Tools.displayWait();>Quản lý mục đích XNC</a></li>
											</c:if>
											<c:if
												test="${col:isPreviledged('NIC_PERSO_LOCATION',sessionScope.userSession)}">
												<li><a href="${showBorderGateUrl}" onclick=Tools.displayWait();>Quản lý của khẩu</a></li>
											</c:if>
											<c:if
												test="${col:isPreviledged('NIC_PERSO_LOCATION',sessionScope.userSession)}">
												<li><a href="${showPkdUrl}" onclick=Tools.displayWait();>PKD</a></li>
											</c:if>
										</ul>
							
							
                        </li>
                    </ul>
                    </c:if>
                     <c:if
							test="${col:isPreviledged('NIC_AUDITENQ',sessionScope.userSession) or
							col:isPreviledged('NIC_USER_SESSION_ENQ',sessionScope.userSession) or
							col:isPreviledged('NIC_TRXENQ',sessionScope.userSession)}">
                    <ul class="ul-menu navbar-nav nav-menu">
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">Tra cứu<span class="caret"></span></a>
                           
			
								<ul class="dropdown-menu">
									<c:if
										test="${col:isPreviledged('NIC_AUDITENQ',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${auditEnquiryAccessLogUrl}">Nhật ký hệ thống</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_USER_SESSION_ENQ',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${userSessionEnquiryUrl}">Nhật ký người dùng</a></li>
									</c:if>
									<c:if test="${col:isPreviledged('NIC_QUEUES_JOB_SCHEDULE',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${queuesJobListUrl}">Tra cứu hàng đợi công việc</a></li>
									</c:if>
									<c:if test="${col:isPreviledged('NIC_QUEUES_JOB_SCHEDULE',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${logJobListUrl}">Nhật ký hàng đợi công việc</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_NICENQ',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${nicEnquiryUrl}">Yêu cầu</a></li>
										
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_CRDENQ',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${cardEnquiryUrl}">YÃªu cáº§u tháº»</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_PASENQ',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${passportEnquiryUrl}">YÃªu cáº§u há» chiáº¿u</a></li>
									</c:if>
									<c:if
													test="${col:isPreviledged('NIC_BATCHJBMONG',sessionScope.userSession)}">
													<li><a onclick=Tools.displayWait();
														href="${batchJobMonitorUrl}">Nhật ký công việc</a></li>
									</c:if>
									
									<c:if
											test="${col:isPreviledged('NIC_OFFICE_VISA',sessionScope.userSession)}">
											<li><a onclick=Tools.displayWait(); href="${reportCqddUrl}">Thống kê giao dịch CQDD</a></li>
										</c:if>
									<c:if
										test="${col:isPreviledged('NIC_TRXENQ',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${transactionEnquiryUrl}">Tra cứu công việc</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_TXNENQ',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${txnEnquiryUrl}">Tra cứu giao dịch</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_TXNENQ',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${txnPassportUrl}">Tra cứu hồ sơ hộ chiếu</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_TXNENQ',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${equalTransactionUrl}">Trao đổi XM, BS hồ sơ</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_TXNENQ',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${searchALLXNCUrl}">Tra cứu tổng hợp xuất nhập cảnh</a></li>
									</c:if>
									<!-- edit 3-6-2020 -->
									<c:if
										test="${col:isPreviledged('NIC_TXNENQ',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${listLogUrl}">Tra cứu nhật ký đồng bộ</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_TXNENQ',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${statusticUrl}">Thống kê hàng đợi</a></li>
									</c:if>	
									<c:if
										test="${col:isPreviledged('NIC_TXNENQ',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${listLogCheckConnectionUrl}">Tra cứu trạng thái kết nối</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_TXNENQ',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${listRptDataUrl}">Tra cứu thống kê truyền nhận</a></li>
									</c:if>
									
							</ul>
						
                        </li>
                    </ul>
                    </c:if>  
                    <!--<ul class="ul-menu navbar-nav nav-menu">
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">Đồng bộ<span class="caret"></span></a>
                              <c:if
								test="${col:isPreviledged('NIC_INV_INV',sessionScope.userSession) or
										col:isPreviledged('NIC_INV_FCM',sessionScope.userSession)}">

										<ul class="dropdown-menu">
											
												<c:if
													test="${col:isPreviledged('NIC_SYNC_SINGER',sessionScope.userSession)}">
														<li><a href="${JobSyncSingerUrl}" onclick=Tools.displayWait();>Đồng bộ dữ liệu đơn sang A72</a></li>
												</c:if>
												<li><a href='${importTransactionsUrl}' >Import hồ sơ</a></li>
												<c:if
													test="${col:isPreviledged('NIC_INV_CONFIRM',sessionScope.userSession)}">
													<li><a href="${JobInvestigationConfirmDSQUrl}" onclick=Tools.displayWait();>Danh sách Kiểm duyệt Hồ sơ (Cơ quan đại diện)</a></li>
												</c:if>
										</ul>
							
							</c:if>
                        </li>
                    </ul>-->
                     <c:if
								test="${col:isPreviledged('NIC_USERMGT',sessionScope.userSession) or
							col:isPreviledged('NIC_ROLEMGT',sessionScope.userSession) or
							col:isPreviledged('NIC_WORKMGT',sessionScope.userSession) or
							col:isPreviledged('NIC_REPORTDEFN',sessionScope.userSession) or
							col:isPreviledged('NIC_GENERATERPT',sessionScope.userSession) or
							col:isPreviledged('NIC_CODEMGT',sessionScope.userSession) or
							col:isPreviledged('NIC_BATCHJBADMIN',sessionScope.userSession) or
							col:isPreviledged('NIC_BATCHJBMONG',sessionScope.userSession) or
							col:isPreviledged('NIC_CODEMGT',sessionScope.userSession) or
							col:isPreviledged('NIC_PROOFDOCMATX',sessionScope.userSession) or
							col:isPreviledged('NIC_PAYMENTMATX',sessionScope.userSession) or
							col:isPreviledged('NIC_PARA_MGMT',sessionScope.userSession)  or
							col:isPreviledged('NIC_SITE_MGMT',sessionScope.userSession)or
							col:isPreviledged('NIC_OFFICE_VISA',sessionScope.userSession)or
							col:isPreviledged('NIC_OFFICE_NATION',sessionScope.userSession)or
							col:isPreviledged('NIC_DECISION',sessionScope.userSession)or
							col:isPreviledged('NIC_SIGNER',sessionScope.userSession)
							 }">
                    <ul class="ul-menu navbar-nav nav-menu">
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">QT hệ thống<span class="caret"></span></a>
                             
							
								<ul class="dropdown-menu">
									<!--<c:if
										test="${col:isPreviledged('NIC_WORKFLOW_PROCESS',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${mainProcessUrl}">Quản lý luồng xử lý</a></li>
									</c:if>-->
									<c:if
										test="${col:isPreviledged('NIC_USERMGT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${userJobListUrl}">Quản lý người dùng</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_ROLEMGT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${roleJobListUrl}">Quản lý phân quyền</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_WORKMGT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${workstationJobListUrl}">Quản lý máy trạm</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_CODEMGT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait(); href="${codeMgmtUrl}">Quản lý danh mục hệ thống</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_PARA_MGMT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${paramAdminUrl}">Cấu hình thông số hệ thống</a></li>
									</c:if>
									<c:if test="${col:isPreviledged('NIC_REPORTDEFN',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${definitionUrl}">Quản lý báo cáo</a></li>
									</c:if>

									<c:if test="${col:isPreviledged('NIC_BATCHJBADMIN',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${batchJobAdminUrl}">Quản lý công việc</a></li>
									</c:if>
												
									
									<!--<c:if
										test="${col:isPreviledged('NIC_PROOFDOCMATX',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${proofDocMatrixUrl}">Proof Document Matrix</a></li>
									</c:if>-->
									<!--<c:if
										test="${col:isPreviledged('NIC_PAYMENTMATX',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${paymentMatrixUrl}">Quản lý thanh toán</a></li>
									</c:if>-->
								
									<c:if
										test="${col:isPreviledged('NIC_SITE_MGMT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait(); href="${siteUrl}">Quản lý trung tâm</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_PERSO_LOCATION',sessionScope.userSession)}">
										<li><a href="${inventoryUrl}" onclick=Tools.displayWait();>Quản lý kho</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_PERSO_LOCATION',sessionScope.userSession)}">
										<li><a href="${inventoryShowUrl}" onclick=Tools.displayWait();>Quản lý phiếu xuất/nhập</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_PERSO_LOCATION',sessionScope.userSession)}">
										<li><a href="${inventoryItemsUrl}" onclick=Tools.displayWait();>Quản lý phôi in</a></li>
									</c:if>

									<c:if
										test="${col:isPreviledged('NIC_PERSO_LOCATION',sessionScope.userSession)}">
										<li><a href="${showTemplatePassport}" onclick=Tools.displayWait();>Template Passport</a></li>
									</c:if>
										<!--<c:if
											test="${col:isPreviledged('NIC_SIGNER',sessionScope.userSession)}">
											<li><a onclick=Tools.displayWait(); href="${signerGovsUrl}">Quản lý chữ ký công văn</a></li>
										</c:if>
											<c:if
											test="${col:isPreviledged('NIC_DECISION',sessionScope.userSession)}">
											<li><a onclick=Tools.displayWait(); href="${decisionManagerUrl}">Quản lý quyết định</a></li>
										</c:if>
										<c:if
											test="${col:isPreviledged('NIC_OFFICE_NATION',sessionScope.userSession)}">
											<li><a onclick=Tools.displayWait(); href="${officalNationUrl}">Quản lý Công hàm</a></li>
										</c:if>
										<c:if
											test="${col:isPreviledged('NIC_OFFICE_VISA',sessionScope.userSession)}">
											<li><a onclick=Tools.displayWait(); href="${officalVisaUrl}">Quản lý QG miễn thị thực</a></li>
										</c:if>-->
							
								</ul>
								
                        </li>
                    </ul>
                    </c:if>
                    <c:if test="${col:isPreviledged('NIC_SYNC_SINGER',sessionScope.userSession)}">
                    <ul class="ul-menu navbar-nav nav-menu">
                    	<li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">Báo cáo<span class="caret"></span></a>   
                            	<ul class="dropdown-menu">
									<li><a onclick=Tools.displayWait();
										href="${reportRegProcessUrl}">Báo cáo tiến trình đăng ký</a></li>
									<li><a onclick=Tools.displayWait();
										href="${reportPackLDSUrl}">Báo cáo tiến trình đóng gói LDS</a></li>
									<li><a onclick=Tools.displayWait();
										href="${reportPersoUrl}">Báo cáo về các gói dữ liệu Perso</a></li>
										<li><a onclick=Tools.displayWait();
										href="${reportTranLost}">Báo cáo về hộ chiếu bị hủy</a></li>
									
								</ul>                       
                        </li>
                    </ul>
                    </c:if>
                </div>
                <div class="col-sm-3 none-padding">
                    <ul class="nav navbar-nav navbar-right nav-menu header-right">
                    	<li class="dropdown">
                            <a href="#" class="hover-a-wsk">
                                <img src="<c:url value="/resources/style1/images/icon-vitri.png" /> ">
                             	<span style="font-size: 12px;">${sessionScope.userSession.placeId}</span>
                            </a>
                        </li>
                        <li class="dropdown">
                            <a href="#" style="padding:0px;margin-top:13px;">
                                <span class="none-padding">|</span>
                            </a>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle hover-a-wsk" data-toggle="dropdown">
                                <img src="<c:url value="/resources/style1/images/icon-user3.png" /> ">
                                <span style="font-size: 12px;">&nbsp;${sessionScope.userSession.userName}</span>
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu login-infor" role="menu">
                                <li>
                                    <a id="btnUser" href="${changePasswordUrl}"><img src="<c:url value="/resources/style1/images/canhan.png" /> ">&nbsp;Thay đổi mật khẩu</a>
                                </li>
                                <li>
                                    <a href="${actionUrl}"><img src="<c:url value="/resources/style1/images/dangxuat.png" /> ">&nbsp;Đăng xuất</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
		<!--<div id=menu>
			<div class="container">
				<div class="row">
					<ul id="menu-page">
						<li><a onclick=Tools.displayWait(); href="${homeUrl}">Trang chủ</a></li>
								
						<c:if
							test="${
						col:isPreviledged('NIC_INV_INV_ASS',sessionScope.userSession) or
						col:isPreviledged('NIC_INV_INV_ASSMENT',sessionScope.userSession) 
						 }">
							<li><span>Phân công</span>
								<ul>
									<c:if
										test="${col:isPreviledged('NIC_INV_INV_ASSMENT',sessionScope.userSession)}">
										<li><a onclick=assignmentJobFunction();>Phân công theo hồ sơ</a></li>
									</c:if>
										<li><a onclick=assignmentJobFunction1();>Phân công theo danh sách</a></li>
									<c:if
										test="${col:isPreviledged('NIC_INV_INV_ASS',sessionScope.userSession)}">
										<li><a onclick=assignedJobFunction();>Danh sách đã phân công</a></li>
									</c:if>
								</ul>
							</li>
						</c:if>
						<c:if
							test="${col:isPreviledged('NIC_INV_INV',sessionScope.userSession) or
						col:isPreviledged('NIC_INV_FCM',sessionScope.userSession) or
						col:isPreviledged('NIC_INV_FCM_ASS',sessionScope.userSession) or
						col:isPreviledged('NIC_INV_FCM_ASSMENT',sessionScope.userSession)
						or
						col:isPreviledged('NIC_INV_CONFIRM',sessionScope.userSession)
						or
						col:isPreviledged('NIC_INV_BCA',sessionScope.userSession)
						or
						col:isPreviledged('NIC_INV_BOSS',sessionScope.userSession)
						or
						col:isPreviledged('NIC_INV_REJECT',sessionScope.userSession)
						or
						col:isPreviledged('NIC_HANDOVER_LIST',sessionScope.userSession)
						or
						col:isPreviledged('NIC_INV_LOST',sessionScope.userSession)
						or
						col:isPreviledged('NIC_PERSO_LOCATION',sessionScope.userSession)
						or
						col:isPreviledged('NIC_PENDING_PASSPORT_NO',sessionScope.userSession)
						 }">
							<li><span>Xử lý</span>
								<ul>
									<c:if
										test="${col:isPreviledged('NIC_INV_INV',sessionScope.userSession)}">
										<li><a onclick=jobFunction();>Danh sách xử lý hồ sơ</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_INV_CONFIRM',sessionScope.userSession)}">
										<li><a onclick=jobInvestigationConfirmFunction();>Danh sách Kiểm duyệt sơ bộ</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_INV_BCA',sessionScope.userSession)}">
										<li><a onclick=jobInvestigationPendingBcaFunction();>Danh sách Xác minh từ BCA</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_INV_BOSS',sessionScope.userSession)}">
										<li><a onclick=jobInvestigationPendingBossFunction();>Danh sách Xét duyệt</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_INV_REJECT',sessionScope.userSession)}">
										<li><a onclick=listInvestigationRejectFunction();>Danh sách bị từ chối</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_HANDOVER_LIST',sessionScope.userSession)}">
										<li><a onclick=listHandoverFunction();>Danh sách đã bàn giao</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_INV_LOST',sessionScope.userSession)}">
										<li><a onclick=jobInvestigationLostFunction();>Danh sách mất/hỏng</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_PERSO_LOCATION',sessionScope.userSession)}">
										<li><a onclick=persoLocationsFunction();>Danh sách điểm in</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_PENDING_PASSPORT_NO',sessionScope.userSession)}">
										<li><a onclick=jobPendingPassportNoFunction();>Danh sách chờ cấp số hộ chiếu</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_PENDING_PASSPORT_NO',sessionScope.userSession)}">
										<li><a onclick=jobStatusPPFunction();>Danh sách trạng thái in</a></li>
									</c:if>

								</ul></li>
						</c:if>
						<c:if
							test="${col:isPreviledged('NIC_INV_INV',sessionScope.userSession) or
									col:isPreviledged('NIC_INV_FCM',sessionScope.userSession)}">
						<li><span>Đồng bộ</span>
							<ul>
								
									<c:if
										test="${col:isPreviledged('NIC_SYNC_SINGER',sessionScope.userSession)}">
											<li><a onclick=jobSyncSingerFunction();>Đồng bộ dữ liệu đơn sang A72</a></li>
									</c:if>
									<li><a href='${importTransactionsUrl}' >Import hồ sơ</a></li>
									<c:if
										test="${col:isPreviledged('NIC_INV_CONFIRM',sessionScope.userSession)}">
										<li><a onclick=jobInvestigationConfirmDSQFunction();>Danh sách Kiểm duyệt Hồ sơ (Cơ quan đại diện)</a></li>
									</c:if>
							</ul>
						</li>
						</c:if>
						<c:if
							test="${col:isPreviledged('NIC_USERMGT',sessionScope.userSession) or
						col:isPreviledged('NIC_ROLEMGT',sessionScope.userSession) or
						col:isPreviledged('NIC_WORKMGT',sessionScope.userSession) or
						col:isPreviledged('NIC_REPORTDEFN',sessionScope.userSession) or
						col:isPreviledged('NIC_GENERATERPT',sessionScope.userSession) or
						col:isPreviledged('NIC_CODEMGT',sessionScope.userSession) or
						col:isPreviledged('NIC_BATCHJBADMIN',sessionScope.userSession) or
						col:isPreviledged('NIC_BATCHJBMONG',sessionScope.userSession) or
						col:isPreviledged('NIC_CODEMGT',sessionScope.userSession) or
						col:isPreviledged('NIC_PROOFDOCMATX',sessionScope.userSession) or
						col:isPreviledged('NIC_PAYMENTMATX',sessionScope.userSession) or
						col:isPreviledged('NIC_PARA_MGMT',sessionScope.userSession)  or
						col:isPreviledged('NIC_SITE_MGMT',sessionScope.userSession)or
						col:isPreviledged('NIC_OFFICE_VISA',sessionScope.userSession)or
						col:isPreviledged('NIC_OFFICE_NATION',sessionScope.userSession)or
						col:isPreviledged('NIC_DECISION',sessionScope.userSession)or
						col:isPreviledged('NIC_SIGNER',sessionScope.userSession)
						 }">
							<li><span>Điều khiển quản trị</span>
								<ul>
									<c:if
										test="${col:isPreviledged('NIC_USERMGT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${userJobListUrl}">Quản lý người dùng</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_ROLEMGT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${roleJobListUrl}">Quản lý phân quyền</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_WORKMGT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${workstationJobListUrl}">Quản lý máy trạm</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_CODEMGT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait(); href="${codeMgmtUrl}">Quản lý danh mục hệ thống</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_PARA_MGMT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${paramAdminUrl}">Cấu hình thông số hệ thống</a></li>
									</c:if>
									<c:if
													test="${col:isPreviledged('NIC_REPORTDEFN',sessionScope.userSession)}">
													<li><a onclick=Tools.displayWait();
														href="${definitionUrl}">Quản lý báo cáo</a></li>
												</c:if>

									<c:if
													test="${col:isPreviledged('NIC_BATCHJBADMIN',sessionScope.userSession)}">
													<li><a onclick=Tools.displayWait();
														href="${batchJobAdminUrl}">Quản lý công việc</a></li>
												</c:if>
												
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_PROOFDOCMATX',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${proofDocMatrixUrl}">Proof Document Matrix</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_PAYMENTMATX',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${paymentMatrixUrl}">Quản lý thanh toán</a></li>
									</c:if>
								
									<c:if
										test="${col:isPreviledged('NIC_SITE_MGMT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait(); href="${siteUrl}">Quản
												lý trung tâm</a></li>
									</c:if>
									

									
										<c:if
											test="${col:isPreviledged('NIC_SIGNER',sessionScope.userSession)}">
											<li><a onclick=Tools.displayWait(); href="${signerGovsUrl}">Quản lý chữ ký công văn</a></li>
										</c:if>
											<c:if
											test="${col:isPreviledged('NIC_DECISION',sessionScope.userSession)}">
											<li><a onclick=Tools.displayWait(); href="${decisionManagerUrl}">Quản lý quyết định</a></li>
										</c:if>
										<c:if
											test="${col:isPreviledged('NIC_OFFICE_NATION',sessionScope.userSession)}">
											<li><a onclick=Tools.displayWait(); href="${officalNationUrl}">Quản lý Công hàm</a></li>
										</c:if>
										<c:if
											test="${col:isPreviledged('NIC_OFFICE_VISA',sessionScope.userSession)}">
											<li><a onclick=Tools.displayWait(); href="${officalVisaUrl}">Quản lý QG miễn thị thực</a></li>
										</c:if>
										
							
				
							
								</ul></li>
								
						<c:if
							test="${col:isPreviledged('NIC_AUDITENQ',sessionScope.userSession) or
						col:isPreviledged('NIC_USER_SESSION_ENQ',sessionScope.userSession) or
						col:isPreviledged('NIC_TRXENQ',sessionScope.userSession)}">
							<li><span>Tra cứu</span>
								<ul>
									<c:if
										test="${col:isPreviledged('NIC_AUDITENQ',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${auditEnquiryAccessLogUrl}">Nhật ký hệ thống</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_USER_SESSION_ENQ',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${userSessionEnquiryUrl}">Nhật ký người dùng</a></li>
									</c:if>
									<c:if test="${col:isPreviledged('NIC_QUEUES_JOB_SCHEDULE',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${queuesJobListUrl}">Tra cứu hàng đợi công việc</a></li>
									</c:if>
									<c:if test="${col:isPreviledged('NIC_QUEUES_JOB_SCHEDULE',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${logJobListUrl}">Nhật ký hàng đợi công việc</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_NICENQ',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${nicEnquiryUrl}">Yêu cầu</a></li>
										
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_CRDENQ',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${cardEnquiryUrl}">YÃªu cáº§u tháº»</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_PASENQ',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${passportEnquiryUrl}">YÃªu cáº§u há» chiáº¿u</a></li>
									</c:if>
									<c:if
													test="${col:isPreviledged('NIC_BATCHJBMONG',sessionScope.userSession)}">
													<li><a onclick=Tools.displayWait();
														href="${batchJobMonitorUrl}">Nhật ký công việc</a></li>
									</c:if>
									
									<c:if
											test="${col:isPreviledged('NIC_OFFICE_VISA',sessionScope.userSession)}">
											<li><a onclick=Tools.displayWait(); href="${reportCqddUrl}">Thống kê giao dịch CQDD</a></li>
										</c:if>
									<c:if
										test="${col:isPreviledged('NIC_TRXENQ',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${transactionEnquiryUrl}">Tra cứu công việc</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_TXNENQ',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${txnEnquiryUrl}">Tra cứu giao dịch</a></li>
									</c:if>
								</ul></li>
						</c:if>

						<c:if
							test="${col:isPreviledged('NIC_GENERATERPT',sessionScope.userSession)}">
						
							<li><a onclick=Tools.displayWait();
								href="${reportGenerationUrl}">Báo cáo</a></li>
						</c:if>
						<c:if
							test="${col:isPreviledged('EPP_TRPSTA_RPT',sessionScope.userSession)}">
							<li><span>Statistics</span>
								<ul>
									<c:if
										test="${col:isPreviledged('EPP_TRPSTA_RPT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait(); href="${statRptUrl}">TRP
												and Statistics</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('EPP_SFTP_STA_RPT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${statSftpRptUrl}">Online SFTP Statistics</a></li>
									</c:if>
								</ul></li>
						</c:if>

						<c:if
							test="${col:isPreviledged('NIC_RIC_TXN_RPT',sessionScope.userSession) or
						col:isPreviledged('NIC_RIC_PYMT_RPT',sessionScope.userSession) or
						col:isPreviledged('NIC_RIC_WAVR_RPT',sessionScope.userSession) or
						col:isPreviledged('NIC_RIC_REJTXN_RPT',sessionScope.userSession) or
						col:isPreviledged('NIC_RIC_CRDDWNLD_RPT',sessionScope.userSession) or
						col:isPreviledged('NIC_RIC_CRDCOL_RPT',sessionScope.userSession) or
						col:isPreviledged('NIC_RIC_CRDREJ_RPT',sessionScope.userSession) or
						col:isPreviledged('NIC_RIC_CRDREACT_RPT',sessionScope.userSession) or
						col:isPreviledged('NIC_RIC_CRDDEACT_RPT',sessionScope.userSession) or
						col:isPreviledged('NIC_RIC_EXPHANDL_RPT',sessionScope.userSession) or
						col:isPreviledged('NIC_RIC_EXPPRNT_RPT',sessionScope.userSession) or
						col:isPreviledged('NIC_RIC_CRDDEL_RPT',sessionScope.userSession) or
						col:isPreviledged('NIC_RIC_UNCOL_RPT',sessionScope.userSession) or
						col:isPreviledged('NIC_RIC_LSTNFND_RPT',sessionScope.userSession)
						}">
							<li><span>RIC Reports</span>
								<ul>
									<c:if
										test="${col:isPreviledged('NIC_RIC_TXN_RPT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait(); href="${txnRptUrl}">Transaction
												Report</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_RIC_PYMT_RPT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait(); href="${pymtRptUrl}">Payment
												Report</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_RIC_WAVR_RPT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait(); href="${wavRptUrl}">Wavier
												Report</a></li>
									</c:if>

									<c:if
										test="${col:isPreviledged('NIC_RIC_REJTXN_RPT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${rejTxnRptUrl}">Rejected Transaction Report</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_RIC_CRDDWNLD_RPT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${crdDwnLdRptUrl}">Card Info Download Report</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_RIC_CRDCOL_RPT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${crdColRptUrl}">Card Collected Status Report</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_RIC_CRDREJ_RPT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${crdRejRptUrl}">Card Rejected Report</a></li>
									</c:if>

									<c:if
										test="${col:isPreviledged('NIC_RIC_CRDREACT_RPT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${crdReActRptUrl}">Card ReActivation Report</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_RIC_CRDDEACT_RPT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${crdDeActRptUrl}">Card DeActivation Report</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_RIC_EXPHANDL_RPT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${exphandlRptUrl}">Exception Handling Report</a></li>
									</c:if>

									<c:if
										test="${col:isPreviledged('NIC_RIC_EXPPRNT_RPT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${exprPrntRptUrl}">Express Printing Report</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_RIC_CRDDEL_RPT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${crdDelStRptUrl}">Card Delivery Status Report</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_RIC_UNCOL_RPT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${unColCrdStRptUrl}">Uncollected Card Status Report
										</a></li>
									</c:if>
									<c:if
										test="${col:isPreviledged('NIC_RIC_LSTNFND_RPT',sessionScope.userSession)}">
										<li><a onclick=Tools.displayWait();
											href="${lostNfoundRptUrl}">Lost And Found Report</a></li>
									</c:if>
								</ul></li>
						</c:if>
						<c:if
							test="${col:isPreviledged('NIC_REPRINT',sessionScope.userSession)}">
							<li><a onclick=Tools.displayWait(); href="${rePrintUrl}">Re-print</a></li>
						</c:if>
						<c:if
							test="${col:isPreviledged('NIC_DATASYNC_MONI',sessionScope.userSession)}">
							<li><a onclick=Tools.displayWait();
								href="${dataSynMonitoringUrl}">Data Sync Monitoring</a></li>
						</c:if>
						<c:if
							test="${col:isPreviledged('NIC_CHANGE_PWD',sessionScope.userSession)}">
							<li><a onclick=Tools.displayWait();
								href="${changePasswordUrl}">Đổi mật khẩu</a></li>
						</c:if>
						<li><a onclick=Tools.displayWait(); href="${logOutUrl}">Thoát</a></li>
					</ul>
				</div>
			</div>
		</div>-->
		<%--<div id="dividerRed" class="displayOn">&nbsp;</div>--%>
	</c:if>
</form:form>
<script>
function importTransactionFunction() {
	document.forms["investigationMenuForm"].action= '${importTransactionsUrl}';
	document.forms["investigationMenuForm"].submit();	
}
function jobSyncSingerFunction() {
	document.forms["investigationMenuForm"].action= '${JobSyncSingerUrl}';
	document.forms["investigationMenuForm"].submit();	
}
function jobPersoFunction() {
	document.forms["investigationMenuForm"].action= '${JobPersoUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function jobInvestigationConfirmFunction() {
	document.forms["investigationMenuForm"].action= '${JobInvestigationConfirmUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function jobInvestigationConfirmFunctionAgain() {
	document.forms["investigationMenuForm"].action= '${JobInvestigationConfirmAgainUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function jobInvestigationApproveFunction() {
	document.forms["investigationMenuForm"].action= '${JobInvestigationApproveUrl}';
	document.forms["investigationMenuForm"].submit();	
}


function jobInvestigationConfirmDSQFunction() {
	document.forms["investigationMenuForm"].action= '${JobInvestigationConfirmDSQUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function showCentralHeadQuarter() {
	document.forms["investigationMenuForm"].action= '${showCentralHeadQuarterUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function showPersonalized() {
	document.forms["investigationMenuForm"].action= '${showPersonalizedUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function showIssuance() {
	document.forms["investigationMenuForm"].action= '${showIssuanceUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function showSearchXNC() {
	document.forms["investigationMenuForm"].action= '${showSearchXNCUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function showStatistical() {
	document.forms["investigationMenuForm"].action= '${statisticalUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function showProcess() {
	document.forms["investigationMenuForm"].action= '${showProcessUrl}';
	document.forms["investigationMenuForm"].submit();	
}


function jobInvestigationLostFunction() {
	document.forms["investigationMenuForm"].action= '${listLostUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function jobInvestigationPendingBcaFunction() {
	document.forms["investigationMenuForm"].action= '${JobInvestigationPendingBcaUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function jobInvestigationPendingBossFunction() {
	document.forms["investigationMenuForm"].action= '${JobInvestigationPendingBossUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function listHandoverFunction() {
	document.forms["investigationMenuForm"].action= '${listHandoverUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function persoLocationsFunction() {
	document.forms["investigationMenuForm"].action= '${persoLocationsUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function jobPendingPassportNoFunction() {
	document.forms["investigationMenuForm"].action= '${pendingPassportNoOUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function jobStatusPPFunction() {
	document.forms["investigationMenuForm"].action= '${jobStatusPPUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function jobApproveStatusFunction() {
	document.forms["investigationMenuForm"].action= '${JobApproveStatusUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function listApproveAssignmentFunction() {
	document.forms["investigationMenuForm"].action= '${ListApproveAssignmentUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function listInvestigationRejectFunction() {
	document.forms["investigationMenuForm"].action= '${JobInvestigationRejectUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function jobFunction() {
	document.forms["investigationMenuForm"].action= '${investigationJobUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function jobFunction1() {
	document.forms["investigationMenuForm"].action= '${investigationProcessUrl}';
	document.forms["investigationMenuForm"].submit();	
}
function jobFunction2() {
	document.forms["investigationMenuForm"].action= '${invesProcessUrl}';
	document.forms["investigationMenuForm"].submit();	
}


function suspendedJobFunction() {
	document.forms["investigationMenuForm"].action= '${investigationSuspendedJobUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function assignedJobFunction() {
	document.forms["investigationMenuForm"].action= '${investigationAssignedJobUrl}';
	document.forms["investigationMenuForm"].submit();	
}

 function storage1(){
	document.forms["investigationMenuForm"].action= '${searchPassportUrl}';
	document.forms["investigationMenuForm"].submit();	
} 

function storage2(){
	alert('${searchPersonUrl}');
	document.forms["investigationMenuForm"].action= '${searchPersonUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function storage3(){
	alert('${searchTranUrl}');
	document.forms["investigationMenuForm"].action= '${searchTranUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function storage4(){
	document.forms["investigationMenuForm"].action= '${searchImmiHisUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function assignedSuspendedJobFunction() {
	document.forms["investigationMenuForm"].action= '${fraudCaseManagementAssignedJobUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function assignmentJobFunction() {
	document.forms["investigationMenuForm"].action= '${investigationJobAssignmentUrl}';
	document.forms["investigationMenuForm"].submit();	
}

function assignmentJobFunction1() {
	document.forms["investigationMenuForm"].action= '${investigationJobAssignment1Url}';
	document.forms["investigationMenuForm"].submit();	
}

function assignmentSuspendedJobFunction() {
	document.forms["investigationMenuForm"].action= '${fraudCaseManagementJobAssignmentUrl}';
	document.forms["investigationMenuForm"].submit();	
} 



</script>
<style>

</style>
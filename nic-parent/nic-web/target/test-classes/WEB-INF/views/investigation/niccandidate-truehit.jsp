<%@page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<c:url var="approveUrl" value="/servlet/investigation/approveJob" />
<c:url var="rejectUrl" value="/servlet/investigation/rejectJob" />
<c:url var="investigationJobUrl" value="/servlet/investigation/investigation_main" />
<c:url var="startInvestigationCompareUrl" value="/servlet/investigation/startInvestigationCompare" />
<c:url var="refreshUrl" 
value="/servlet/investigation/refreshCPDData/${candidateCPDData.nin},${jobDetails.uploadJobId},${jobDetails.transactionId}" />
<c:url var="cancelTransnUrl" value="/servlet/investigation/cancelTransaction" />
<!-- jquery plugin to zoom in and out, rotate the scanned document -->
<script src="<c:url value="/resources/js/jquery.iviewer.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/jquery.mousewheel.min.js"/>"
	type="text/javascript"></script>

 <link rel="stylesheet" type="text/css"
 href="<c:url value="/resources/css/jquery.iviewer.css"/>"></link>

<c:url var="scannedUserDeclPdfDocs"
	value="/servlet/investigation/userDeclarationPdf" />
<c:url var="scannedCollSlipPdfDocs"
	value="/servlet/investigation/collectionSlipPdf" />
<script src="<c:url value="/resources/js/mouseover_investigation.js"/>"
	type="text/javascript"></script>
<script type="text/javascript"
 src="<c:url value="/resources/js/multiSelect/jquery.multiselect.js" />"></script>
 
<link rel="stylesheet" type="text/css"
 href="<c:url value="/resources/css/multiSelect/jquery.multiselect.css"/>"></link>
 
<script type="text/javascript"
 src="<c:url value="/resources/js/multiSelect/jquery.multiselect.filter.js" />"></script>
 
<link rel="stylesheet" type="text/css"
 href="<c:url value="/resources/css/multiSelect/jquery.multiselect.filter.css"/>"></link>

<!-- query dialog box script for Approve button -->
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
	});

	function callBack() {
		this.form.action = "${investigationJobUrl}";
		this.form.submit();
	}
	
</script>


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
	&nbsp;
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
		<form:textarea path="rejectRemarks" id="rejectRemarks"
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
<!-- Jquery Dialog box div on click of Cancel button---->
<div id="dialog1" title="Cancelled" style="display: none;">
  <p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>Are you sure? You want to Cancel?</p>
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

<div id="main">
<div class="content_main">

<div id="heading_report" align="justify">
        Investigation</div>
		<br />
     <form:form commandName="jobDetails" id="form" name="jobDetailsForm" action="" method="post">
				<br />
				<input type="hidden" name="jobidValue" id="jobidValue"
					value="${jobDetails.uploadJobId}" />
                <input type="hidden" name="investigationType" id="investigationType"
					value="${jobDetails.investigationType}" />
				<div id="inner1_main">
					<div id="inner1_main_left">
						<table width="75%" height="100" name="jobDetails" border="0"
							cellpadding="2">
							<tr>
								<td style="font-weight: bold">Job ID</td>
								<td>:</td>
								<td><c:out value="${jobDetails.uploadJobId}" />
                                <input type="hidden" name="jobId" id="jobId" value="${jobDetails.uploadJobId}" />
							</tr>
							<tr>
								<td style="font-weight: bold">Application Ref No</td>
								<td>:</td>
								<td name="mainTransactionId" id="mainTransactionId"><c:out
										value="${jobDetails.transactionId}" /></td>

							</tr>
							<tr>
								<td style="font-weight: bold">Registration Type</td>
								<td>:</td>
								<td><c:out value="${jobDetails.jobType}" /></td>
							</tr>
							<tr>
								<td style="font-weight: bold">Job Type</td>
								<td>:</td>
								<td id="jobTypeValue"><c:out
										value="${jobDetails.investigationType}" /></td>
							</tr>
							<tr>
								<td style="font-weight: bold">Owner</td>
								<td>:</td>
								<td><c:out value="${jobDetails.investigationOfficerId}" />
								</td>
							</tr>
							<tr>
								<td style="font-weight: bold">Number of True Hit</td>
								<td>:</td>
								<td><c:out value="${countTrueHit}" />
								</td>
							</tr>
							<tr>
								<td style="font-weight: bold">Number of False Hit</td>
								<td>:</td>
								<td><c:out value="${countFalseHit}" />
								</td>
							</tr>
							<tr>
								<td style="font-weight: bold">Pending</td>
								<td>:</td>
								<td><c:out value="${countPending}" />
								</td>
							</tr>
							
						</table>
					</div>
<br />
					<div id="inner1_main_right">
						<c:if test="${candidatePhoto!=null}">
							<img align='right' src="data:image/jpg;base64,${candidatePhoto}"
								 height="180" style="position: relative; left: -200px; margin-top: -33px;" />
						</c:if>
						<c:if test="${candidatePhoto==null}">
							<img align='right'
								src=<c:url value='/resources/images/No_Image.jpg'/> height="180"
							width="150" class="img-border" style="position: relative; left: -200px; margin-bottom: 10px;" />
						</c:if>
					</div>
				</div>
				
     <div id="inner_div" align="right">
   <c:choose>
				<c:when test="${jobDetails.investigationType == 'AFIS' and countPending eq 0 and countTrueHit eq 0}">
								<input type="button" id="approve_btn" class="button_grey"
									name="btnNext" value="Approve" />
								<input type="button" class="button_grey_disable"
									disabled="disabled" name="btnNext" value="Reject" />
							</c:when>
							<c:otherwise>
								<input type="button" class="button_grey_disable"
									disabled="disabled" class="button_grey" name="btnNext"
									value="Approve" />
								<input type="button" id="reject_btn" class="button_grey"
									name="btnNext" value="Reject" />
							</c:otherwise>
				</c:choose>
				<input type="button" id="cancel_btn" class="button_grey" name="btnNext" value="Cancel" />
				<input type="button" id="cancel_opn_btn" class="button_grey"
					name="btnNext" value="Cancel Transaction" style="padding-right: 10px;"/>
</div>


<form:form  modelAttribute="hitCandidateDTO" action="/nric/servlet/registration" method="post">
<table class="border_error">
<table width="500" height="62" border="0" cellpadding="5" class="errors">
  <tr>
 <td width="587" height="28"> <p class="text_pad">
    <img align='left' src="<c:url value="/resources/images/alert.png" />" width="30" height="30"/>
      <span style="font-size: 15px; font-weight:bold; color: #000;">Candidate <c:out value="${hit_Nin}" /> is marked as Duplicate Record.</p>
      <p class="text_pad"><span style="color: #000;">&nbsp;Successfully Processed Investigation Job with Job Id: <c:out value="${jobId}" /></span></p>
	  <p class="text_pad"><span style="color: #000;">&nbsp;Transaction rejected for Card Production.</span></p>
	  </td>
  </tr>
 
</table>
</table>
</form:form>


<br />
<!-- Tabs -->
<div id="TabbedPanels1" class="TabbedPanels">
			<ul class="TabbedPanelsTabGroup">
						<li id="hitRecordsTab" class="TabbedPanelsTab" tabindex="0">Hit Records</li>
						<li class="TabbedPanelsTab" tabindex="1">Demographic Data</li>
						<li class="TabbedPanelsTab" tabindex="2">Remarks</li>
						<li class="TabbedPanelsTab" tabindex="3">Reference Document</li>
						<li class="TabbedPanelsTab" tabindex="4">Perso</li>
			</ul>
			<div class="TabbedPanelsContentGroup">
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
												style="font-weight: bold; padding-right: 25px;"><span
												class="table_header">Card Status</span></td>
											<td width="10%" class="sno"
												style="font-weight: bold; padding-right: 35px;"><span
												class="table_header">Photo</span></td>
											<td width="10%" class="sno" style="font-weight: bold;"><span
												class="table_header" style="padding-right: 30px;">Remarks</span>
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
										<input type="hidden" name="hitTransactionId"
											id="hitTransactionId" value="${c.transactionId}" />
										<c:if test="${c.nin=='No Data'}">
											<td width="10%" class="title_link"><c:out
													value="${c.nin}" /></td>
										</c:if>
										<c:if test="${c.nin!='No Data'}">
											<td width="10%" class="title_link">
											<!-- {prasad}changes for the  proxy redirect to login page error [start] -->
											<c:url var="startInvestCompareUrl" value="/servlet/investigation/startInvestigationCompare/${jobDetails.uploadJobId}" />
											<a 
											 onclick=Tools.displayWait();
											 href="${startInvestCompareUrl}"> <c:out value="${c.nin}" />
											</a>
											<!-- end -->
											<%-- <a
												onclick=Tools.displayWait();
												href="${startInvestigationCompareUrl}/${c.transactionId}&&${jobDetails.transactionId}&&${c.searchResultId}&&${jobDetails.uploadJobId}"><c:out
														value="${c.nin}" /> 
											</a> --%>
											<!-- {prasad}changes for the  proxy redirect to login page error [end] -->
											</td>
										</c:if>
										<td width="10%" class="nricFormat"><c:out
														value="${c.transactionId}" />
												</td>
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
													<a href="#"><img src="data:image/jpg;base64,${c.photo}"
														width="80" height="100" class="thumbnail" /> </a>
													<div class="tooltip">
														<img src="data:image/jpg;base64,${c.photo}" alt=""
															width="200" height="185" /> <span class="overlay"></span>
													</div>
											</div>
							</c:if>
							<div class="thumbnail-item">
								<c:if test="${c.photo==null}">
									<a href="#"><img
										src=<c:url value='/resources/images/No_Image.jpg'/> width="80"
										height="100" class="img-border" /> </a>
									<div class="tooltip">
										<img src=<c:url value='/resources/images/No_Image.jpg'/>
											alt="" width="200" height="185" class="img-border" /> <span
											class="overlay"></span>
								</c:if>
							</div>
							</div>

							</td>
							<td width="10%" class="nricFormat">
									<c:if test="${not empty c.remarksData}">
									<span class="historyEventDataSmark pointer" ref="<c:out value="${c.remarksData}" />">
						        <img src="<c:url value="/resources/images/remarks_icon.jpg"/>" width="50" height="50" alt="View Remarks" border="0" style="padding-left: 30px;"/>
					                </span>
									</c:if>
									</td>
							</tr>
							</c:forEach>
							</c:if>

						</table>
						</div>
</form:form>
     </div>
	
	  <!-- Demographic Data Tab -->
					<div class="TabbedPanelsContent">
						<div id="inner_main" style="background-color: #FFFFF;">
							<div id="inner_main_left">
								<table width="100%" height="200" border="0">
									<tr>
										<td width="320" height="35" align="center"
											style="font-weight: bold" class="sno"><span
											class="table_header">Data from RIC</span>
										</td>
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
															value="${hitCandidateDTO.surname}" />
													</td>
												</tr>
												<tr>
													<td>First Name</td>
													<td>:</td>
													<td id="fn_ric"><c:out
															value="${hitCandidateDTO.firstName}" />
													</td>
												</tr>
												<tr>
													<td>Surname at Birth</td>
													<td>:</td>
													<td id="snb_ric"><c:out
															value="${hitCandidateDTO.surnameAtBirth}" />
													</td>
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
														</c:choose>
													</td>
												</tr>
												<tr>
													<td>Date Of Birth</td>
													<td>:</td>
													<td id="dob_ric"><c:out value="${ricDob}" />
													</td>
												</tr>
												<tr>
													<td>House No/Flat No/Building Name</td>
													<td>:</td>
													<td id="flatNo_ric"><c:out
															value="${hitCandidateDTO.flatNoApartmentName}" />
													</td>
												</tr>
												<tr>
													<td>Street</td>
													<td>:</td>
													<td id="StName_ric"><c:out
															value="${hitCandidateDTO.streetName}" />
													</td>
												</tr>
												<tr>
													<td>Locality</td>
													<td>:</td>
													<td id="Loca_ric"><c:out
															value="${hitCandidateDTO.locality}" />
													</td>
												</tr>
												<tr>
													<td>City/Town/Village</td>
													<td>:</td>
													<td id="TwVillage_ric"><c:out
															value="${dataFromRicTwnVillageDesc}" />
													</td>
												</tr>
												<tr>
													<td>District/Outer Island</td>
													<td>:</td>
													<td id="district_ric"><c:out
															value="${dataFromRicDistrictDesc}" />
													</td>
												</tr>
												<tr>
													<td>PostCode</td>
													<td>:</td>
													<td id="postalCode_ric"><c:out
															value="${hitCandidateDTO.postalCode}" />
													</td>
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
														</c:choose></td>
												</tr>
												<tr>
													<td>Father's Name</td>
													<td>:</td>
													<td id="fan_ric"><c:out
															value="${hitCandidateDTO.fatherFirstName}" />
													</td>
												</tr>
												<tr>
													<td>Father's Surname</td>
													<td>:</td>
													<td id="fasn_ric"><c:out
															value="${hitCandidateDTO.fatherSurname}" />
													</td>
												</tr>
												<tr>
													<td>Mother's Name</td>
													<td>:</td>
													<td id="mn_ric"><c:out
															value="${hitCandidateDTO.motherFirstName}" />
													</td>
												</tr>
												<tr>
													<td>Mother's Surname</td>
													<td>:</td>
													<td id="msn_ric"><c:out
															value="${hitCandidateDTO.motherSurname}" />
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
        <td>: </td>
        <td>
        <c:choose>
										<c:when test="${hitCandidateDTO.optionSurname eq 'OWN'}">
											<c:out value="Own Surname at Birth" />
										</c:when>
										<c:when test="${hitCandidateDTO.optionSurname eq 'SPOUSE'}">
											<c:out value="Spouse Surname at Birth" />
										</c:when>
										<c:when test="${hitCandidateDTO.optionSurname eq 'OWN-SPOUSE'}">
											<c:out value="Adjoined (Own-Spouse) Surname at Birth" />
										</c:when>
										<c:when test="${hitCandidateDTO.optionSurname eq 'SPOUSE-OWN'}">
											<c:out value="Adjoined (Spouse-Own) Surname at Birth" />
										</c:when>
										<c:when test="${hitCandidateDTO.optionSurname eq 'OWN_SPOUSE'}">
											<c:out value="Adjoined (Own Spouse) Surname at Birth" />
										</c:when>
										<c:when test="${hitCandidateDTO.optionSurname eq 'SPOUSE_OWN'}">
											<c:out value="Adjoined (Spouse Own) Surname at Birth" />
										</c:when>
										<c:otherwise>
											<c:out value="${hitCandidateDTO.optionSurname}" />
										</c:otherwise>
									</c:choose>		
        </td>
      </tr>
												<!-- Modification end -->
											</table></td>
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
													 <span style="color: #FF0000; font-size: 15px; height: 20px;">Data refreshed from CPD at <%=new java.util.Date()%></span>
													 <span class="refreshCPDData"><img
													src="<c:url value="/resources/images/refresh_icon.png"/>"
													width="20" height="20" alt="Refresh CPD Data" border="0"
													style="float: right;" /></span>
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
						
						</div>
						<div>&nbsp;</div>
				<!-- Additional Information for demographic data from Perso -->
				<div>
				<h4 style="font-size: 15px; color: #0000FF;">Additional Information</h4>
								<label for="Remarks"></label>
								<textarea name="Remarks_demographic_data" id="Remarks_demographic_data"
								cols="400" rows="4" readOnly="readOnly">Remarks: <c:out value="${persoRemarksEdit}" /></textarea>
					</div>
					</div>
					<!-- Remarks Tab -->
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
								</c:if>

							</table>
						</div>
				</div>
				<!-- Reference docs tab end -->
				<!-- Perso Tab start -->
				<div class="TabbedPanelsContent">
				<display:table cellspacing="1" cellpadding="0" id="row"
				export="false" class="displayTag" name="persoRemarksDTO">
				<display:column title="Create Date" property="logCreateTime" style="width:18%" format="{0,date,dd-MMM-yyyy}">
				</display:column>
				<display:column property="remarksData"
					title="Remarks" maxLength="80" />
			     </display:table>
				</div>
				<!-- Perso Tab end -->
				</div>
			 </div>
	 
</div>
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
						var jobId = $("#jobId").val();
						var userId = $('#userId').val();
						var wkstnId = $('#wkstnId').val();
						if (remarksData == "") {
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
											+ "&jobId=" + jobId + "&userId="
											+ userId + "&wkstnId=" + wkstnId,
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
				    rotImagId=$('#dialog-scan-doc').data('docName'); 
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
									src="data:image/jpg;base64,${docListItem}" 
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
		//alert("Refresh button clicked");
		var url = '${refreshUrl}';
		var nin = '${candidateCPDData.nin}';
		//alert(nin);
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
						//$("#savedialog-confirm").dialog('optio')
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


$(".rotateClockWise").click(function(){
	//alert("Rotate the image clockwise");
	//$("#imageRotate").rotate(180);
	$("#icaoRotate").rotate(180);
	//$('#imageRotate').rotate({ angle: 0, animateTo: 180});
	
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
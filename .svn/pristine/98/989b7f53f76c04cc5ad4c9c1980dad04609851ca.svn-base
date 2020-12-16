<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="collection" uri="colfunction" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:url var="homeUrl" value="/servlet/nic/welcome" />
<c:url var="userJobListUrl" value="/servlet/adminConsole/userManagement" />
<c:url var="roleJobListUrl" value="/servlet/roleManagement" />
<c:url var="workstationJobListUrl" value="/servlet/adminConsole/workstationManagement" />
<c:url var="batchJobListUrl" value="/servlet/adminConsole/batchJobManagement" />
<c:url var="reportListUrl" value="/servlet/adminConsole/userManagement" />
<c:url var="codeTablesListUrl" value="/servlet/adminConsole/codeManagement" />
<c:url var="proofDocMatrixUrl" value="/servlet/proofDocMatrixController/proofDocMatrix" />
<c:url var="paymentMatrixUrl" value="/servlet/paymentMatrixController/paymentMatrix" />
<c:url var="investigationJobUrl" value="/servlet/investigation/investigation" />


<c:url var="auditEnquiryUrl" value="/servlet/nicEnquiry/auditEnquiry" />
<c:url var="batchJobEnquiryUrl" value="/servlet/nicEnquiry/batchJobEnquiry" />
<c:url var="enquiryJobQueueUrl" value="/servlet/nicEnquiry/jobQueue" />

<c:url var="rejectApplicationReportUrl" value="/servlet/nicReports/rejectApplicationReport" />
<c:url var="loginLogoffReportUrl" value="/servlet/nicReports/loginLogoffReport" />
<c:url var="accessLogReportUrl" value="/servlet/nicReports/accessLogReport" />
<c:url var="transactionReportUrl" value="/servlet/nicReports/transactionReport" />
<c:url var="siteListUrl" value="/servlet/siteMgmt/siteManagement" />

<div id=menu>
	<ul>
  		<li>
  			<a onclick=Tools.displayWait(); href="${homeUrl}">Home</a>
  		</li>
  		<li>
  			<span>Investigation</span>
  			<ul>
  				<c:if test="${collection:isPreviledged('FunctionID',sessionScope.userSession)}"></c:if>
    			<li><a onclick=Tools.displayWait(); href="${investigationJobUrl}">NIC Investigation</a></li>
    			
  			</ul>
  		</li>  		 
  		<li>
  			<span>Admin Console</span>
  			<ul>
     			<li><a onclick=Tools.displayWait(); href="${userJobListUrl}">User Management</a></li>
     			<li><a onclick=Tools.displayWait(); href="${roleJobListUrl}">Role Management</a></li>
     			<li><a onclick=Tools.displayWait(); href="${workstationJobListUrl}">Workstation Management</a></li>
     			<li><a onclick=Tools.displayWait(); href="${batchJobListUrl}">Batch Job Management</a></li>
     			<li><a onclick=Tools.displayWait(); href="${reportListUrl}">Report Management</a></li>
     			<li><a onclick=Tools.displayWait(); href="${codeTablesListUrl}">Code Management</a></li>
     			<%-- <li><a onclick=Tools.displayWait(); href="${siteListUrl}">Site Management</a></li> --%>
  		     <!-- <li><a onclick=Tools.displayWait(); href="${proofDocMatrixUrl}">Proof Document Matrix</a></li>	
  		     <li><a onclick=Tools.displayWait(); href="${paymentMatrixUrl}">Payment Matrix</a></li> -->
   			</ul>

   		</li> 
   		<li>
   		<span>NIC Enquiry</span>
   		<ul>
  		        <li><a onclick=Tools.displayWait(); href="${auditEnquiryUrl}">Audit Enquiry</a></li>
     			<li><a onclick=Tools.displayWait(); href="${batchJobEnquiryUrl}">Batch Job Enquiry</a></li>
     			<li><a onclick=Tools.displayWait(); href="${enquiryJobQueueUrl}">Transaction Enquiry</a></li>
  		   
  		</ul>
  		</li>
   		<!--<li>
  			<span>Report</span>
  			<ul>
     			<li><a onclick=Tools.displayWait(); href="${rejectApplicationReportUrl}">Reject Application Report</a></li>
				<li><a onclick=Tools.displayWait(); href="${loginLogoffReportUrl}">Login logoff Report</a></li>
				<li><a onclick=Tools.displayWait(); href="${accessLogReportUrl}">Access Log Report</a></li>
				<li><a onclick=Tools.displayWait(); href="${transactionReportUrl}">Transaction Report</a></li>
				<li><a onclick=Tools.displayWait(); href="${transactionStatisticsReportUrl}">Transaction Statistics Report</a></li>
   			</ul>
   		</li>   		
	--></ul>
</div>
<div id="dividerRed" class="displayOn">&nbsp;</div>
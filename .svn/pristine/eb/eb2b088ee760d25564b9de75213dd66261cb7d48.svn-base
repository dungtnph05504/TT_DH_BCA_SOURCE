<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="jobEnqDgDataUrl" value="/servlet/transactionEnquiry/jobEnqDgData" />
<c:url var="jobEnqHitListUrl" value="/servlet/transactionEnquiry/jobEnqHitList" />
<c:url var="txnEnqTransUrl" value="/servlet/transactionEnquiry/txnEnqTrans" />
<c:url var="jobEnqHistoryEventsUrl" value="/servlet/transactionEnquiry/jobEnqHistoryEvents" />
<c:url var="jobEnqDemographicDetailsUrl" value="/servlet/transactionEnquiry/jobEnqDemographicDetails" />
<c:url var="personSummaryUrl" value="/servlet/transactionEnquiry/getPersonSummary" />
<c:url var="passportInfoUrl" value="/servlet/transactionEnquiry/getPassportInfo" />
<c:url var="transDocumentsUrl" value="/servlet/transactionEnquiry/getTransDocuments" />

<c:url var="prioritizeUrl" value="/servlet/transactionEnquiry/prioritize" />
<c:url var="reprintUrl" value="/servlet/transactionEnquiry/reprint" />
<style>
.form-control-1 {
	height: 24px;
    font-size: 12px;
    border-radius: 0px;
    outline: none;
    -webkit-box-shadow: none;
    -moz-box-shadow: none;
    box-shadow: none;
    border-color: rgba(0, 0, 0, 0.44);
    color: rgba(0,0,0,0.87);
    
}
.form-horizontal div input[type="text"] {
	width: 100% !important;
	pointer-events: none;
}
#ajax-load-qa {
	background: rgba(255, 255, 255, .5)
		url('<c:url value="/resources/images/loading_nin.gif" />') 50%
		50% no-repeat;
	position: fixed;
	z-index: 100;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	display: none;
	text-align: center;
}
</style>
<form:form modelAttribute="nicEnqDetailsForm" id="enqDetailsForm" action="/servlet/transactionEnquiry/search">
			<div class="form-horizontal">
              <div class="col-sm-12" style="padding-right:0px; padding-left:0px;">
                  <ul class="nav nav-tabs">
                      <li class="active" id="liInfo"><a data-toggle="tab" href="#" id="transactionInfo" aria-expanded="true">Thông tin giao dịch</a></li>
                      <li class="" id="liDoc"><a data-toggle="tab" href="#" id="transDocuments" aria-expanded="false">Tài liệu</a></li>
                      <li class="" id="liEqual"><a data-toggle="tab" href="#" id="hitList" aria-expanded="false">Danh sách trùng</a></li>
                      <li class="" id="liHis"><a data-toggle="tab" href="#" id="historyEvents" aria-expanded="false">Nhật ký sự kiện</a></li>
                      <li class="" id="liCroot"><a data-toggle="tab" href="#" id="personSummary" aria-expanded="false">Tóm tắt</a></li>
                      <li class="" id="liPass"><a data-toggle="tab" href="#" id="passportInfo" aria-expanded="false">Thông tin HC</a></li>
                  </ul>

                  <div>
                      <div id="home" class="tab-pane fade active in"></div>
                  </div>
              </div>
          </div>	
          <div id="ajax-load-qa"></div>
</form:form>	      
		
<%
	/* ******************************************************************************************************************** */
%>
<%
	/* ************************************************ apply filter - start ********************************************** */
%>
<%
	/* ******************************************************************************************************************** */
%>

<script type="text/javascript">
$(function() {
	var transId = $("#txnId").val();
	if (transId == "" || transId.trim() == "") {
		$('#home').html('Không tìm thấy thông tin giao dịch nào hiển thị');
	} else {
		$.ajax({
			type : "GET",
			url : '${txnEnqTransUrl}/' + transId,
			data : $("#nicEnqDetailsForm").serializeArray(),
			success : function(data) {
				$('#home').html(data);
			}
		});
	}
	
	$('#transactionInfo').on('click', function() {
		$('li').removeClass('active');
		$('li#liInfo').addClass('active');
		//$('div').removeClass('active');
		var txnId = $("#txnId").val();
		$('#ajax-load-qa').show();
		$.ajax({
			type : "GET",
			url : '${txnEnqTransUrl}/' + transId,
			data : $("#nicEnqDetailsForm").serializeArray(),
			success : function(data) {
				$('#home').html(data);
				$('#ajax-load-qa').hide();
			}
		});
	});
	
	$('#transDocuments').on('click', function() {
		$('li').removeClass('active');
		$('li#liDoc').addClass('active');
		var txnId = $("#txnId").val();
		$('#ajax-load-qa').show();
		$.ajax({
			type : "GET",
			url : '${transDocumentsUrl}/' + txnId,
			data : $("#nicEnqDetailsForm").serializeArray(),
			success : function(data) {
				$('#home').html(data);
				$('#ajax-load-qa').hide();
			}
		});
	});
	
	$('#hitList').on('click', function() {
		$('li').removeClass('active');
		$('li#liEqual').addClass('active');

		var txnId = $("#txnId").val();
		$('#ajax-load-qa').show();
		$.ajax({
			type : "GET",
			url : '${jobEnqHitListUrl}/' + txnId,
			data : $("#nicEnqDetailsForm").serializeArray(),
			success : function(data) {
				$('#home').html(data);
				$('#ajax-load-qa').hide();
			}
		});
	});
	
	$('#historyEvents').on('click', function() {
		$('li').removeClass('active');
		$('li#liHis').addClass('active');

		var txnId = $("#txnId").val();
		$('#ajax-load-qa').show();
		$.ajax({
			type : "GET",
			url : '${jobEnqHistoryEventsUrl}/' + txnId,
			data : $("#nicEnqDetailsForm").serializeArray(),
			success : function(data) {
				$('#home').html(data);
				$('#ajax-load-qa').hide();
			}
		});
	});
	
	$('#personSummary').on('click', function() {
		$('li').removeClass('active');
		$('li#liCroot').addClass('active');

		var txnId = $("#txnId").val();
		$('#ajax-load-qa').show();
		$.ajax({
			type : "GET",
			url : '${personSummaryUrl}/' + txnId,
			data : $("#nicEnqDetailsForm").serializeArray(),
			success : function(data) {
				$('#home').html(data);
				$('#ajax-load-qa').hide();
				//alert('1');
			}
		});
	});
	
	$('#passportInfo').on('click', function() {
		$('li').removeClass('active');
		$('li#liPass').addClass('active');

		var txnId = $("#txnId").val();
		$('#ajax-load-qa').show();
		$.ajax({
			type : "GET",
			url : '${passportInfoUrl}/' + txnId,
			data : $("#nicEnqDetailsForm").serializeArray(),
			success : function(data) {
				$('#home').html(data);
				$('#ajax-load-qa').hide();
			}
		});
	});
});

</script>






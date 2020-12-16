<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="issuanceDataUrl" value="/servlet/nicEnquiry/getIssuanceData" />
<script type="text/javascript">
$(function(){
	

	/*$("#infoDialog").dialog( {
		autoOpen : false,
		width : 750,
		height : 450,
		modal : true,
		bgiframe : true,
		cache :false,
		closeOnEscape: false
	});*/
	
	
/* 	$("#histryClose_btn").click(function(){
		 $("#dialog-approve").dialog('close');	
	}); */
	
});




//	$(".historyEventDataSmark").click(function(){ 
function historyEventDataSmark_clicked(itemTriggered){	 
	var info = $(itemTriggered).attr('ref');
	//info = info.replace(/\n/g,"<br/>");
	//$("#infoDialog").dialog('option', 'title', "Dữ liệu log");
	//$("#infoDialog").text(info);
	//$("#infoDialog").dialog('open');
	$('#divTTNK').text(info);
	$('#idTTNK').modal();
}
//	});

//	$(".historyEventCardIssuanceSmark").click(function(){
function historyEventCardIssuanceSmark_clicked(itemTriggered){
	    var logId = $(itemTriggered).attr('ref');
		//$('.modal').show();
		//var titleName = "Chi tiết phát hành";
		//$("#infoDialog").dialog('option', 'title', titleName);
		//$("#infoDialog").dialog("option", "width", 800);
		//$("#infoDialoge").dialog("option", "height", 720);
		//$("#infoDialog").html('Đang tải ...');
		//$("#infoDialog").dialog('open');
		$.ajax({
			type : "GET",
			url : "${issuanceDataUrl}",
			data: {
				logId: logId
	   	 	},
			success : function(data) {
				$('#divTTNK').html(data);
				$('#idTTNK').modal();
				//$("#infoDialog").html(data);
				//$('.modal').hide();
			},
			error :function(e) {
				//$("#infoDialog").html('Đã có lỗi xảy ra trong khi hiển thị chi tiết phát hành.');
				//$('.modal').hide();
			}
		});
}
//	});




//	$(".historyEventSmark").click(function(){
function historyEventSmark_clicked(itemTriggered){
	var info = $(itemTriggered).attr('ref');
	//info = info.replace(/\n/g,"<br/>");
	//$("#infoDialog").dialog('option', 'title', "Thông tin nhật ký");
	//$("#infoDialog").text(info);
	//$("#infoDialog").dialog('open');
	$('#divTTNK').text(info);
	$('#idTTNK').modal();
}
//	});
</script>


<!--<div id="fortable" style="display: none">
	<table id="statResult">
	   <tr>
			<td colspan=4>&nbsp;</td>
		</tr>
	</table>                
</div>-->
<div>
	<table id="tbDetailHits" class="table table-bordered" cellspacing="0" width="100%">
		<thead>
			<tr>
			  <th class="th-sm">Mã nhật ký</th>	
		      <th class="th-sm">Mã hồ sơ
		
		      </th>	
		      <th class="th-sm">Giai đoạn XL
		
		      </th>
		      <th class="th-sm">Trạng thái
		
		      </th>
		      <th class="th-sm">Mã cán bộ
		
		      </th>
		      
		      <th class="th-sm">Ngày tạo
		
		      </th>	
		      <th class="th-sm">TT nhật ký
		
		      </th>	
		      <th class="th-sm">DL nhật ký
		
		      </th>				     							   
		    </tr>
		</thead>
		<tbody>
			
			<c:forEach items="${nicLog}" var="item_">
				<tr>
					<td align="center">${item_.logId}</td>
					<td>${item_.refId}</td>
					<td>${item_.transactionStage}</td>
					<td>${item_.transactionStatus}</td>
					<td>${item_.officerId}</td>
					<td align="center">${item_.logCreateTimeDesc}</td>
					<td align="center">
						<c:if test="${item_.wkstnId == 'N'}">
						</c:if>
						<c:if test="${item_.wkstnId == 'Y'}">
							<span class='glyphicon glyphicon-folder-open' ref='"+${item_.logInfo}+"' onclick='historyEventSmark_clicked(this);' >
							
							</span>
						</c:if>
					</td>
					<td align="center">
						<c:if test="${item_.stageDate == 'N'}">
						</c:if>
						<c:if test="${item_.stageDate == 'Y'}">
							<c:choose>
								<c:when test="${item_.transactionStage == 'ISSUANCE'}">
									<span class='glyphicon glyphicon-folder-open' ref='"+${item_.logId}+"' onclick='historyEventCardIssuanceSmark_clicked(this);' > 
									</span>
								</c:when>
								<c:otherwise>
									<span class='glyphicon glyphicon-folder-open' ref='"+${item_.logData}+"' onclick='historyEventDataSmark_clicked(this);' > 
									</span>
								</c:otherwise>
							</c:choose>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<!-- Message lưu hồ sơ -->
<div class="modal fade" id="idTTNK" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 700px;">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">THÔNG TIN NHẬT KÝ</h5>
        <button type="button" class="close" id="clsThongTin">
          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
        </button>
      </div>
      <div class="modal-body" id="divTTNK" style="height: 200px;overflow: auto;">
      		
      </div>							      
      <div class="modal-footer" style="padding: 5px;">
       		       		
      </div>
    </div>
  </div>
</div>	
<!-- ---------------------------------------------------------------------------->
<c:url var="dataQueryUrl" value="/servlet/transactionEnquiry/jobEnqHistoryEvents/getContent"/>

<script type="text/javascript"> 

	/*$(document).ready(function() {
		$("#statResult").flexigrid({			
			url: "${dataQueryUrl}?txnId=${txnId}",
			dataType : 'json',
			colModel : [
						{display: 'Mã nhật ký'              , name  : 'logId'                  , width : 80 , sortable : true , align: 'center'},				
						{display: 'Mã tham chiếu'        , name  : 'refId'                  , width : 200, sortable : true , align: 'center'},
						{display: 'Giai đoạn '   , name  : 'transactionStage'       , width : 200, sortable : true , align: 'center'},
						{display: 'Trạng thái'  , name  : 'transactionStatus'      , width : 250, sortable : true , align: 'center'},	 
						{display: 'Mã trung tâm'           , name  : 'siteCode'               , width : 100, sortable : true , align: 'center'},	
						{display: 'Mã cán bộ'          , name  : 'officerId'              , width : 100, sortable : true , align: 'center'},	
						{display: 'Ngày tạo'   , name  : 'logCreateTimeDesc'          , width : 150, sortable : true , align: 'center'},	
						{display: 'Thông tin nhật ký.'                                              , width : 70, sortable : false, align: 'center',render: getLogInfo      	},
						{display: 'Dữ liệu nhật ký'                                               , width : 70, sortable : false, align: 'center',render: getLogData		}  
					], 
			 sortname           : "logId",
			 sortorder          : "desc",
			 usepager           : true,
			 useRp              : true,
			 rp                 : 10,
			 showTableToggleBtn : true,   
			 height             : 270,
			 singleSelect       : true,
			 nowrap             : false		
		}); 
	});*/
		
	function getLogCreateTime(content, currentRow){	
		return currentRow.logCreateTime_formatted;
	}
		
	function getLogInfo(content, currentRow){	
		if (!(currentRow.logInfo)){
		   return "&nbsp;";
		} 
		
		if (currentRow.logInfo){	
		   var aString = "";
		   aString = aString + "   <span class='historyEventSmark pointer' ref='"+currentRow.logInfo+"' onclick='historyEventSmark_clicked(this);' >";
		   aString = aString + "      <img src='<c:url value='/resources/images/clipboard_16.png'/>' alt='View Log Information' border='0' />		";
		   aString = aString + "   </span>																											";
		   return aString;
		} 
	} 	
	
	function getLogData(content, currentRow){	
		
		if (!(currentRow.logData)){
		   return "&nbsp;";
		} 

		if (currentRow.logData){
			if (currentRow.transactionStage == 'ISSUANCE'){  
			   var aString = "";
			   aString = aString + "   <span class='historyEventCardIssuanceSmark pointer' ref='"+currentRow.logId+"' onclick='historyEventCardIssuanceSmark_clicked(this);' > 	    							";
			   aString = aString + "      <img width='16px' height='16px' src='<c:url value='/resources/images/info.gif'/>' title='View Issuance Information' alt='View Issuance Information' border='0' />		";
			   aString = aString + "   </span>																																									";
			   return aString; 
			} else {
			   var aString = "";
			   aString = aString + "   <span class='historyEventDataSmark pointer' ref='"+currentRow.logData+"' onclick='historyEventDataSmark_clicked(this);' > 					";
			   aString = aString + "      <img src='<c:url value='/resources/images/clipboard_16.png'/>' alt='View Log Information'  title='View Log Information' border='0' />		";
			   aString = aString + "   </span>																																		";
			   return aString;
			}
		} 
	} 

	 $(document).ready(function() {
		// $("#fortable").show();  
		//$( ".sDiv" ).show();
		$('#clsThongTin').click(function() {
			$('#idTTNK').modal('toggle');
		});
	 });
</script>
 
<!--  
<form:form modelAttribute="nicEnqForm" id="form1" action="/servlet/nicEnquiry/search" method="post">
	<display:table cellspacing="1" cellpadding="0" id="row"	export="false" class="displayTag" name="nicTransactionLogs">
		<display:column title="Log ID"  property="logId" />
		<display:column title="Reference ID"  property="refId" />
		<display:column title="Transaction Stage"  property="transactionStage" />
		<display:column title="Transaction Status"  property="transactionStatus" />
		<display:column title="Site Code"  property="siteCode" />
		<display:column title="Officer Id"  property="officerId" />
		<display:column title="Created Date/Time" property="logCreateTime" format="{0,date,dd-MMM-yyyy HH:mm:ss}"></display:column>
		<display:column title="Log Info." >
			<c:if test="${not empty row.logInfo}">
				<span class="historyEventSmark pointer" ref="${row.logInfo}">
					<img src="<c:url value="/resources/images/clipboard_16.png"/>" alt="View Log Information" border="0" />
				</span>
			</c:if>
		</display:column>
		<display:column title="Log Data" >
			<c:if test="${not empty row.logData}">
			<c:choose>
				<c:when test="${(row.transactionStage eq 'ISSUANCE' )}">
					<span class="historyEventCardIssuanceSmark pointer" ref="<c:out value="${row.logId}" />">
						<img width="16px" height="16px" src="<c:url value="/resources/images/info.gif"/>" title="View Issuance Information" alt="View Issuance Information" border="0" />
					</span>
				</c:when>
				<c:otherwise>
						<span class="historyEventDataSmark pointer" ref="<c:out value="${row.logData}" />">
					<img src="<c:url value="/resources/images/clipboard_16.png"/>" alt="View Log Information"  title="View Log Information" border="0" />
				</span>
				</c:otherwise>
			</c:choose>
			</c:if>
		</display:column>
	</display:table>
	
	<! - -  <table style="width:100%;">
    	<tr>
        	<td align="center" style="padding: 20px; text-align: right;" >
		      <input type="button" class="button_grey" id="histryClose_btn"  value="Close"/>
	        </td>
	    </tr>
      </table> - - >
</form:form> 
-->

<!--<div id="infoDialog" style="display: none;">
</div>-->
	
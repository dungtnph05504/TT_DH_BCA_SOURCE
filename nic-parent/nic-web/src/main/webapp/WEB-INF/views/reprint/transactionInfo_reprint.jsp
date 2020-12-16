<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url var="reprintUrl" value="/servlet/rePrintController/reprintUpdate/${newRegistration.transactionId}" />
<style>
.subjectsmallFontRed {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	text-decoration: none;
	font-size: 11px;
	color: #FF0A0A;
	line-height: normal;
	font-weight: bold;
	text-transform: lower;
	text-align: left;
	height: 25px;
	text-indent: 5px;
}

.description_slimplain_red {
	font-size: 11px;
	font-family: Verdana, Arial, Helvetica, sans-serif;
	text-decoration: none;
	font-weight: normal;
	height: 20px;
	color: #FF0A0A;
}

/* Remarks Styling */
#reprintRemarks {
background: rgb(233,246,253); /* Old browsers */
background: -moz-linear-gradient(top,  rgba(233,246,253,1) 0%, rgba(211,238,251,1) 100%); /* FF3.6+ */
background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,rgba(233,246,253,1)), color-stop(100%,rgba(211,238,251,1))); /* Chrome,Safari4+ */
background: -webkit-linear-gradient(top,  rgba(233,246,253,1) 0%,rgba(211,238,251,1) 100%); /* Chrome10+,Safari5.1+ */
background: -o-linear-gradient(top,  rgba(233,246,253,1) 0%,rgba(211,238,251,1) 100%); /* Opera 11.10+ */
background: -ms-linear-gradient(top,  rgba(233,246,253,1) 0%,rgba(211,238,251,1) 100%); /* IE10+ */
background: linear-gradient(to bottom,  rgba(233,246,253,1) 0%,rgba(211,238,251,1) 100%); /* W3C */
filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#e9f6fd', endColorstr='#d3eefb',GradientType=0 ); /* IE6-9 */
   -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;
  float:none;
  font-size:15px; 
  overflow:auto;
  width:90%;
  height: 40%;
	
	}
	
</style>
<script type="text/javascript">
	$(function() {
		$("#dialog-TransactionLog").dialog({
			modal : true,
			autoOpen : false,
			width : 1200,
			height : 300,
			closeOnEscape : false,
			show : {
				effect : "blind",
				duration : 1000
			},
			hide : {
				//effect : "explode",
				duration : 1000
			}
		});
		$("#viewTransLog").click(function() {
			$("#dialog-TransactionLog").dialog("open");
		});
	});
</script>

<table
	style="border: 1px SOLID; border-radius: 10px; width: 1260px; background-color: #DDDDDD; table-layout: fixed;">
	<tr>
		<c:choose>
			<c:when
				test="${newRegistration.transactionStage == 'PEND_SUPERVISOR'}">
				<td style="padding-left: 5px; text-align: left">
					<div style="height: 15px" id="sub_heading_new">TRANSACTION
						INFORMATION</div>
				</td>
				<td colspan="2" class="description_slimplain_red"
					style="padding-left: 5px; text-align: left">Escalation
					Reason:&nbsp;<span class='subjectsmallFontRed'
					id="escalationReason">${newRegistration.transLog.reason}</span>
				</td>
				<td colspan="4" class="description_slimplain_red"
					style="text-align: left">Remarks:&nbsp;<span
					class='subjectsmallFontRed' id="escalationRemarks">${newRegistration.transLog.remarks}</span>
				</td>
			</c:when>
			<c:otherwise>
				<td colspan="5">
					<div style="height: 15px" id="sub_heading_new">TRANSACTION INFORMATION</div>
					<div align="right">
					    <input type="button" class=" btn_blue gradient" id="reprint_btn" value="RePrint" />
						<input type="button" class=" btn_blue gradient" id="viewTransLog" value="Transaction Log" />
					</div>
				</td>
			</c:otherwise>
		</c:choose>
	</tr>
	<tr>
		<td style="width: 330px; padding-left: 5px; text-align: left"><span
			class='description_slimplain'>Transaction Id:</span>&nbsp;<span
			class='subjectsmall' id="trnasIdSpan">${newRegistration.transactionId}</span>
		</td>
		<td style="width: 250px; text-align: left;"><span
			class='description_slimplain'>Transaction Type:</span>&nbsp;<span
			class='subjectsmall' id="transTypeSpan">${newRegistration.transactionTypeDesc}</span>
		</td>
		<td style="width: 230px; text-align: left;"><span
			class='description_slimplain'>SubType:</span>&nbsp;<span
			class='subjectsmall' id="transSubTypeSpan">${newRegistration.transactionSubTypeDesc}</span>
		</td>
		<td style="width: 230px; text-align: left;"><span
			class='description_slimplain'>Date of Application:</span><span
			class='subjectsmall'>&nbsp;${newRegistration.trnasactionStartDate}</span>
		</td>
		<td style="width: 220px; text-align: left"><span
			class='description_slimplain'>Current Stage:</span><span
			class='subjectsmall'>&nbsp;${newRegistration.transactionStageDesc}</span>
		</td>
	</tr>
</table>
<div id="dialog-TransactionLog" title="Transaction Log" >
	<table>
		<tr>
			<td class="subjectsmall" width="250px">DATE/TIME</td>
			<td class="subjectsmall" width="250px">SITE</td>
			<td class="subjectsmall" width="250px">COUNTER/SERVER</td>
			<td class="subjectsmall" width="250px">USER ID</td>
			<td class="subjectsmall" width="250px">STAGE</td>
			<td class="subjectsmall" width="250px">ACTION</td>
			<td class="subjectsmall" width="250px">REMARKS</td>
		</tr>
		<c:forEach var="transLogItem"
			items="${newRegistration.transactionLogList}">
			<tr>
				<td width="250px" class='description'>${transLogItem.updateTime}</td>
				<td width="250px" class='description'>${transLogItem.ricSitecode}</td>
				<td width="250px" class='description'>${transLogItem.counterId}</td>
				<td width="250px" class='description'>${transLogItem.userId}</td>
				<td width="250px" class='description'>${transLogItem.transactionStage}</td>
				<td width="250px" class='description'>${transLogItem.transactionStatus}</td>
				<td width="250px" class='description'>${transLogItem.remarks}</td>
			</tr>
		</c:forEach>
	</table>
</div>
<div id="savedialog-confirm" style="display: none;"></div>
<div id="faildialog-confirm" style="display: none;"></div>

<!-- Jquery Dialog box div for Reprint -->
<div id="dialog-reprint" title="Reprint" style="display: none;">

	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>Are you sure? You
		want to Continue to Reprint?
	</p>
	<br /> <br />
	<h4 style="font-size: 20px; color: #0000FF;">
		Remarks&nbsp;<span style="color: #808080; font-size: 15px;">*</span>
	</h4>
	<textarea rows="15" cols="180" id="reprintRemarks" name="reprintRemarks"></textarea>
	&nbsp;

</div>
<script>
    $(function() {
		$("#dialog-reprint").dialog({
			resizable : false,
			modal : true,
			autoOpen : false,
			width : 600,
			height: 500,
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
					doSubmitReprint();
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			}
		});

		$("#reprint_btn").click(function() {
			$("#dialog-reprint").dialog("open");
		});
	});
    function doSubmitReprint() {
		var reprintRemarks = $("#reprintRemarks").val();
		var url = '${reprintUrl}'+ "," + reprintRemarks;
	    $.ajax({
	    	   type : "POST",
	    	   url : url,
	    	   success : function(data) {
	    	  $("#savedialog-confirm").dialog('option','title','Status');
		      $("#savedialog-confirm").html('Reprint Request submitted Sucessfully.');
		      $("#savedialog-confirm").dialog('open');
	    	   },
	    	   error : function(e) {
	    	  $("#faildialog-confirm").dialog('option','title','Status');
			  $("#faildialog-confirm").html('Failed to reprint');
			  $("#faildialog-confirm").dialog('open');
	}
	    	   
	    	  });
	}

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
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:url var="showTranLostUrl" value="/servlet/storage/showReportTranLost" />
<c:url var="urlSaveLog" value="/servlet/storage/saveActionLog" />

<%-- <form:form commandName="jobList" id="form" name="form" > --%>
<style>
<!--
-->
.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
	white-space: pre-wrap;
}

.cls-mg-bot {
	margin-top: 10px;
}

.form-group {
	display: inline !important;
}

#ajax-load-qa {
	background: rgba(255, 255, 255, .6)
		url('<c:url value="/resources/images/loading_nin.gif" />') 50% 50%
		no-repeat;
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
<div class="content-item-title">
	<div class="oplog-title__txt">Số lượng hồ sơ bị hủy</div>
</div>
<div class="content-item-main">
	<form:form modelAttribute="formData" name="formData" autocomplete="off">
		<c:if test="${not empty requestScope.Errors}">
			<div
				style="color: Red; background-color: White; border-style: solid; border-color: Red; border-width: thin;">
				<c:forEach items="${requestScope.Errors}" var="errorMessage">
					<li>${errorMessage}</li>
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
					<table width="600" height="10" border="0" cellpadding="5">
						<tr>
							<td
								style="padding-left: 5px; font-weight: bold; vertical-align: top;">
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

		<!--Content start-->


		<div class="content-item-information">
			<div class="form-group form-profile pad-bottom-15">
				<div class="col-sm-12" style="padding-right: 30px;">
					<div class="col-sm-4 pad-top-10 form-group">
						<label class="col-sm-4  pad-top-10  control-label text-right">Ngày Bắt đầu</label>
						<div class="col-sm-8 none-pad-left">
							<form:input path="createDate" id="createDate" type="text" style="width: 100%;" />
						</div>
					</div>
					<div class="col-sm-4 pad-top-10 form-group">
						<label class="col-sm-4  pad-top-10  control-label text-right">Ngày kết thúc</label>
						<div class="col-sm-8 none-pad-left">
							<form:input path="endDate" id="endDate" type="text" style="width: 100%;" />
						</div>
					</div>
					
					<div class="col-sm-4  pad-top-10 form-group">
                            <label class="col-sm-4 control-label text-right pad-top-10">Cơ quan đăng ký hủy</label>
                            <div class="col-sm-8  none-pad-left">
										<form:input path="officeCode" id="officeCode" type="text" style="width: 100%;"/>
                            </div>                          
                        </div>
                        <div class="col-sm-4  pad-top-10 form-group">
                            <label class="col-sm-4 control-label text-right pad-top-10">Lý do hủy</label>
                            <div class="col-sm-8 none-pad-left">
                             	 <form:select path="reason" id="reason" name="typeList" class="priorityId"
											 style="font-family: Arial; font-size: 12px;width: 100%;">
									<form:option value="" style="margin-right: 5px;" label="Tất cả" />
									<form:option value="LOST" style="margin-right: 5px;" label="Mất" />
									<form:option value="RENEW" style="margin-right: 5px;" label="Hủy" />
								</form:select>
                            </div>                          
                        </div>
				</div>

				<div class="col-sm-12" style="padding-right: 30px;">
					<button type="button" style="float: right;"
						onclick=" doSubmitForm();" class="btn_small btn-success">
						<span class="glyphicon glyphicon-search"></span> Tìm kiếm
					</button>
				</div>
			</div>




			<div class="form-group">
				<div class="col-sm-12 none-pad-right none-pad-left"
					style="margin-bottom: 50px;">
					<fieldset class="scheduler-border">
							<legend class="scheduler-border">Thống kê phân tích số lượng hồ sơ</legend>
					<div style="height: 400px;">
						<div >
				   				<div id="bieuDo"></div>		
			   				</div>
			   			


					</div>
<a class="btn_small btn-success" role="button" style="padding:  2px 6px 2px 6px" id="export2pdf">Export to PDF</a>	
			   			
					
					</fieldset>
				</div>
			</div>
		</div>

		<!-- Message lưu hồ sơ -->
		
		<!-- ---------------------------------------------------------------------------->
		<!--<div style="display: flex;flex: 0 auto;justify-content: center;">
	<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
		<div style="margin: 10px"> 
			<button type="button"  onclick="processHS();" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-list-alt"></span> Xử lý hồ sơ
			</button>
		</div>
	</div>	
	</div>-->
		<div id="ajax-load-qa"></div>
		<div id="dialog-confirm"></div>
		<script type="text/javascript">
		var listData = [], obj;
		var listDataDate = [], objDate;
		<c:forEach var="dataChart" items="${dataChart}">
		obj = '${dataChart.quantityList}';
		objDate = '${dataChart.dateList}';
		listData.push(parseInt(obj));
		listDataDate.push(objDate);
		</c:forEach>
		$(document).ready(function(){
			var chart = new Highcharts.chart('bieuDo', {
		    chart: {
		        type: 'column'
		    },
		    title: {
		        text: ''
		    },
		    yAxis: {
		        title: {
		            text: 'Số hồ sơ bị hủy'
		        }
		    },
		    series: [{
		        name: 'Số hồ sơ bị hủy',
		        data: listData
		    }]
		    ,
		    xAxis: {
		        categories: listDataDate
		    },
		    
		    credits: {
		        enabled: false
		    }, exporting: {
	            enabled: true
	        }
		});
			$("#export2pdf").click(function(){
		        chart.exportChart({
		            type: 'application/pdf',
		            filename: 'my-pdf'
		        });
		    });
		});
		    
		
		
		</script>
		<script type="text/javascript">
			$('#createDate').mask("00/00/0000", {
				placeholder : "__/__/____"
			});
			$('#endDate').mask("00/00/0000", {
				placeholder : "__/__/____"
			});
			
			$("#endDate").datepicker({
				//showOn : "button",
				//buttonImage : "<c:url value="/resources/images/calendar9.png" />",
				//buttonImageOnly : true,
				changeMonth : true,
				changeYear : true,
				showSecond : true,
				controlType : 'select',
				dateFormat : 'dd/mm/yy',
				yearRange : "-100:+10"
			}).keyup(function(e) {

			});
			
			function printRp(){
				
				var value = {};
				value['fromDate'] = $('#createDate').val();
				value['toDate'] = $('#endDate').val();
				value['reason'] = $('#reason').val();
				value['officeCode'] = $('#officeCode').val();
				value['functionName'] = "Thống kê hồ sơ hủy";
				var url = '${urlSaveLog}';
				$('#ajax-load-qa').show();
				$('#ajax-load-qa').css('display', 'block');
			   	$.ajax({
			   		url : url,
			   		type: "POST",
			   		cache: false,
			   		contentType : 'application/json',
			   		data : JSON.stringify(value),
			   		success : function(data) {
			  
			   		},
			   		error : function(e){
			   			$('#ajax-load-qa').hide();
			   		}
			   	});
			}

			$("#createDate").datepicker({
				//showOn : "button",
				//buttonImage : "<c:url value="/resources/images/calendar9.png" />",
				//buttonImageOnly : true,
				changeMonth : true,
				changeYear : true,
				showSecond : true,
				controlType : 'select',
				dateFormat : 'dd/mm/yy',
				yearRange : "-100:+10"
			}).keyup(function(e) {

			});
			

			function doSubmitForm() {
				
				if($('#createDate').val().trim() == '' && $('#endDate').val().trim() == ''){
					$.notify('Ngày bắt đầu và ngày kết thúc không được bỏ trống', {
						globalPosition: 'bottom left',
						className: 'warn',
					});
					return;
				}
				var createDateMomentObject = moment($('#createDate').val(), "DD/MM/YYYY"); // 1st argument - string, 2nd argument - format
				var createDate = createDateMomentObject.toDate();
				var endDateMomentObject = moment($('#endDate').val(), "DD/MM/YYYY"); // 1st argument - string, 2nd argument - format
				var endDate = endDateMomentObject.toDate();
				if (createDate > endDate) {
					$.notify('Ngày kết thúc không được nhỏ hơn ngày bắt đầu', {
						globalPosition: 'bottom left',
						className: 'warn',
					});
					return;
					
				}
				document.forms["formData"].action = '${showTranLostUrl}';  
				document.forms["formData"].submit();  
			}
			

			function doSubmitNew1(form) {
				$("#dialog-confirm").dialog('option', 'title',
						'Tình trạng công việc đang chờ xử lý');
				$("#dialog-confirm").html(
						"Có công việc đang chờ xử lý? Bạn có muốn tiếp tục?");
				$("#dialog-confirm").dialog('open');
			}
		</script>

	</form:form>
</div>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<c:url var="url" value="/servlet/batchJobMnt/scheDetail" />
<c:url var="delSchLogUrl" value="/servlet/batchJobMnt/delScheLog" />
<c:url var="delAllLogUrl" value="/servlet/batchJobMnt/delAllLog" />
<c:url var="backUrl" value="/servlet/batchJobMnt/view" />
<c:url var="viewScheLogUrl" value="/servlet/batchJobMnt/viewScheLog" />
<c:url var="getScheDetailUrl" value="/servlet/batchJobMnt/getScheDetail" />
<c:url var="scheDetailUrl" value="/servlet/batchJobMnt/scheDetail"/>
<style>
.fix-form-ra {
	background-color: white;
	margin-top: 30px;
	width: 1200px;
	margin-left: 80px;
	box-shadow: 0px 5px 10px #392f2f;
	padding: 10px;
}	
</style>
<script type="text/javascript">
	var reload = "0";
	$(function() {
		//$('#dtBasicExample').DataTable();
		//$('.dataTables_length').addClass('bs-select');
		//ajaxGetScheDetails();
		var totalPages = ${totalPage};
		var currentPage = ${pageNo};
		window.pagObj = $('#pagination').twbsPagination({
					totalPages: totalPages,
					visiblePages: 10,
					startPage: currentPage,
					next: '<span class=\"glyphicon glyphicon-forward fix-paging\"></span>',
					prev: '<span class=\"glyphicon glyphicon-backward fix-paging\"></span>',
					last: '<span class=\"glyphicon glyphicon-step-forward fix-paging\"></span>',
					first: '<span class=\"glyphicon glyphicon-step-backward fix-paging\"></span>',
					onPageClick: function (event, page) {
						if (currentPage != page) {
							$('#pageNo').val(page);
							document.forms["monitorForm"].action = '${scheDetailUrl}/${eppJobMonitorForm.jobId}';  
						   	document.forms["monitorForm"].submit();  
						}
					}
				});
		
		$("#btn_refresh").click(function() {
			//ajaxGetScheDetails();
		});

		$("#btn_back").click(function() {
			document.forms["monitorForm"].action = '${backUrl}';
			document.forms["monitorForm"].submit();
		});

		$("#dialog-confirm").dialog({
			modal : true,
			autoOpen : false,
			width : 500,
			resizable : false,
			show : {
				effect : "fade",
				duration : 200
			},
			hide : {
				duration : 100
			},
			buttons : {
				Ok : function() {
					callBack(true);
					$(this).dialog("close");
				},
				Cancel : function() {
					callBack(false);
					$(this).dialog("close");
				}
			}
		});

		$("#dialog-confirm-all").dialog({
			modal : true,
			autoOpen : false,
			width : 500,
			resizable : false,
			show : {
				effect : "fade",
				duration : 200
			},
			hide : {
				duration : 100
			},
			buttons : {
				Ok : function() {
					$(this).dialog("close");
					$( "#monitorForm" ).submit();
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			}
		});
		
		$("#dialog-error").dialog({
			modal : true,
			autoOpen : false,
			width : 500,
			resizable : false,
			show : {
				effect : "fade",
				duration : 200
			},
			hide : {
				duration : 100
			},
			buttons : {
				Close : function() {
					$(this).dialog("close");
				}
			}
		});
		
	});

	function callBack(result) {
		if(result) {
			$.ajax({
				type : "GET",
				url : '${delSchLogUrl}/' + $("#delId").val(),
				success : function(data) {
					if (data == 'success') {
						var orig = $("#monitorForm").serialize();
						var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
						var actionUrl = '${getScheDetailUrl}' + '?' + withoutEmpties;
						$("#histListFlexGrid").flexOptions({
							url : actionUrl
						}).flexReload();
						if(!$(".erow").is(':visible')) {
							document.forms["monitorForm"].action = '${backUrl}';
							document.forms["monitorForm"].submit();
						}
					} else {
						$("#dialog-error").dialog('option', 'title', 'Thông báo lỗi');
						$("#dialog-error").html("Không thể xóa lịch sử thực hiện công việc: " + $("#delId").val() + ".");
						$("#dialog-error").dialog('open');
					}
				}
			});
		}
         return result;
     }

	function delHistory(logid) {
		$("#delId").val(logid);
		$("#dialog-confirm").dialog('option', 'title', 'Xóa lịch sử');
		$("#dialog-confirm").html("Xác nhận lịch sử xóa: " + logid + "?");
		$("#dialog-confirm").dialog('open');
	}

	function clearAllHistory() {
		$("#dialog-confirm-all").dialog('option', 'title', 'Xóa toàn bộ lịch sử');
		$("#dialog-confirm-all").html("Xác nhận rõ ràng tất cả hồ sơ lịch sử cho công việc: " + $("#jobId").val() + "?");
		$("#dialog-confirm-all").dialog('open');
		document.forms["monitorForm"].action = '${delAllLogUrl}/'+ $("#jobId").val();
	}

	function ajaxGetScheDetails() {
		var orig = $("#monitorForm").serialize();
		var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
		var actionUrl = '${getScheDetailUrl}' + '?' + withoutEmpties;
		$('#histListFlexGrid').empty();
		$("#histContent").show();
		$('#histListFlexGrid').show();
		if (reload == "0") {
			reload = "1";
			$("#histListFlexGrid").flexigrid(
			{
				url : actionUrl,
				dataType : 'json',
				colModel : [
				{
					display : 'Mã',
					name : 'logId',
					width : 150,
					sortable : true,
					align : 'left',
					render : renderLink
				},
				{
					display : 'Ghi chú',
					name : 'message',
					sortable : true,
					width : 680,
					align : 'left'
				},
				{
					display : 'Trạng thái',
					name : 'status',
					width : 116,
					sortable : false,
					align : 'center',
					render : renderImg
				},
				{
					display : 'Ngày thực hiện',
					name : 'executionDate',
					sortable : true,
					width : 200,
					align : 'left',
					process : function(col) {
						var content = col.innerHTML;
						var currentTime = new Date(parseInt(content));
						if(currentTime == 'Invalid Date' || content == '&nbsp;') {
							col.innerHTML = "";
						} else {
							col.innerHTML = currentTime.toLocaleDateString() + " " + currentTime.toLocaleTimeString();
						}
					}
				},
				{
					display : 'Delete',
					name : 'logId',
					width : 116,
					sortable : false,
					align : 'center',
					process : function(col) {
						var logId = col.innerHTML;
						col.innerHTML = "<a href='javascript:delHistory("
								+ logId
								+ ");' title='Log Id: "
								+ logId
								+ "'> "
								+ "<img src=\"<c:url value='/resources/images/delete.gif'/>\" border='0' width='16' height='16' />"
								+ "</a>";
					}
				} ],
				sortname : "executionDate",
				sortorder : "desc",
				title : 'Danh sách lịch sử thực hiện công việc',
				usepager : true,
				useRp : true,
				rp : 20,
				rpOptions : [ 1, 5, 10, 20, 30, 50 ], //allowed per-page values
				procmsg : 'Đang xử lý, Chờ trong giây lát ...',
				pagestat : 'Hiển thị từ {from} đến {to} của {total} kết quả',
				showToggleBtn : false,
				showTableToggleBtn : true,
				singleSelect : true,
				nowrap : false,
				width : 'auto',
				height : 'auto'
			});
		} else {
			$("#histListFlexGrid").flexOptions({
				url : actionUrl
				, newp : 1
			}).flexReload();
		}
	}

	function renderLink(logId) {
		var actionUrl = '${viewScheLogUrl}/' + logId;
		return "<a href=\""+actionUrl+"\" >" + logId + "</a>";
	}

	function renderImg(status) {
		var result = "";
		if (status == 0) {
			result = "<img src=\"<c:url value='/resources/images/tick_16.gif'/>\" border='0' width='16' height='16' alt='Completed' />";
		} else if (status == 1) {
			result = "<img src=\"<c:url value='/resources/images/warn_16.gif'/>\" border='0' width='16' height='16' alt='Completed' />";
		} else if (status == 2) {
			result = "<img src=\"<c:url value='/resources/images/exclam_16.gif'/>\" border='0' width='16' height='16' alt='Completed' />";
		}

		return result;
	}
</script>

<form:form modelAttribute="eppJobMonitorForm" id="monitorForm" name="monitorForm">
	<div class="form-desing row">
		<div class="new-heading-2">GIÁM SÁT CHI TIẾT CÔNG VIỆC</div>
		<div class="col-md-12">
			<div class="col-md-6" style="padding-left: 0px;">
				<div class="col-md-4" style="padding-left: 0px;">
					<span>Mã công việc:</span>
				</div>
				<div class="col-md-8">
					<span><c:out value="${eppJobMonitorForm.jobId}" /></span>
					<form:hidden id="jobId" path="jobId" />
				</div>
				<div class="col-md-4" style="padding-left: 0px;">
					<span>Tên công việc:</span>
				</div>
				<div class="col-md-8">
					<span><c:out value="${eppJobMonitorForm.jobName}" /></span>
					<form:hidden id="jobName" path="jobName" />
				</div>
			</div>
		</div>
		<fieldset>
			<legend>Danh sách chi tiết công việc</legend>

						<div style="height: 400px;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm">Mã 
								
								      </th>	
								      <th class="th-sm">Ghi chú
								
								      </th>
								      <th class="th-sm">Trạng thái
								
								      </th>
								      <th class="th-sm">Ngày thực hiện
								
								      </th>
								      <th class="th-sm">Xóa
								
								      </th>								   
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>							    									      
									      <td><a style="color: blue;" href="${viewScheLogUrl}/${item.logId}" >${item.logId}</a></td>
									      <td>${item.message}</td>
									      <td>
									      	<c:if test="${item.status == 0}">
									      		<img src="<c:url value='/resources/images/tick_16.gif'/>" border='0' width='16' height='16' alt='Completed' />
									      	</c:if>	
									      	<c:if test="${item.status == 1}">
									      		<img src="<c:url value='/resources/images/warn_16.gif'/>" border='0' width='16' height='16' alt='Completed' />
									      	</c:if>
									      	<c:if test="${item.status == 2}">
									      		<img src="<c:url value='/resources/images/exclam_16.gif'/>" border='0' width='16' height='16' alt='Completed' />
									      	</c:if>
									      </td>	
									      <td>${item.executionDate}</td>
									      <td><a href='javascript:delHistory("${item.logId}");' ><img src="<c:url value='/resources/images/delete.gif'/>" border='0' width='16' height='16' /></a></td>									      						     
									    </tr>
								    </c:forEach>
								  </tbody>
								  <c:if test="${empty jobList}">
								  
								   <tbody class="e-not-found ng-scope"><tr>
								  <td colspan="10" style="height: 362px">
								  <div class="e-not-found-center"><div class="e-not-found-title">Không tìm thấy kết quả</div><div class="e-not-found-title-child">Hãy thử tìm kiếm hoặc sử dụng bộ lọc khác</div></div>
								  </td></tr></tbody>
								  
								
								</c:if>
								</table>
								
						      </div>
						      <table class="table e-grid-table e-border">
                                <tfoot>
                                    <tr>
                                        <th>
                                        
                                          <c:if test="${not empty jobList}">
										
										<div class="e-page-rigth">
											<ul style="float: right;" class="pagination" id="pagination"></ul>
										</div>
										<input type="hidden" name="pageNo" id="pageNo" /> 
									</c:if>
                                        
                                        <div class="e-page-left" style="font-weight: normal;">
											Hiển thị ${startIndex} đến ${endIndex} của ${totalRecord} kết quả
										</div>
                                        
                                          
                                            
                                        </th>
                                    </tr>
                                </tfoot>
                            </table>
                            </fieldset>
		<div style="display: flex;flex: 0 auto;justify-content: center;">
		<div class="waitingWhenDoneWaiting" style="position: fixed; bottom:-5px; border-color:#2a7044 #2a7044 transparent #2a7044 ; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000">
			<div style="margin: 10px"> 
				<button type="button" class="btn btn-success"   onclick="clearAllHistory();" id="btn_clearAll">
					<span class="glyphicon glyphicon-remove"></span> Xóa tất cả
				</button>
				<button type="button" class="btn btn-success"  id="btn_refresh" style="display: none;">
					<span class="glyphicon glyphicon-refresh"></span> Làm mới
				</button>
				<button type="button" class="btn btn-success" id="btn_back">
					<span class="glyphicon glyphicon-arrow-left"></span> Quay lại
				</button>
			</div>
		</div>
		</div>
</div>		
		<input type="hidden" id="delId"/>
</form:form>
<div id="dialog-confirm"></div>
<div id="dialog-confirm-all"></div>
<div id="dialog-error"></div>
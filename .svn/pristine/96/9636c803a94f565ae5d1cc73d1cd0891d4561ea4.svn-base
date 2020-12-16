<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<c:url var="url" value="/servlet/batchJobMnt/view"/>
<c:url var="getJobListUrl" value="/servlet/batchJobMnt/getJobList"/>
<c:url var="scheDetailUrl" value="/servlet/batchJobMnt/scheDetail"/>
<c:url var="batchJobMonitorUrl" value="/servlet/batchJobMnt/view" />
<style>
tr {
	height: 35px;
}
.displayTag tr.odd {
	height: 35px;
}
.displayTag tr.even {
	height: 35px;
}
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
	var autoRefreshValue=30;
	var interval = null;
	var reload = "0";
	$(function() {
		//ajaxGetExecJobs();
		//$('#dtBasicExample').DataTable();
		//$('.dataTables_length').addClass('bs-select');
		var totalPages = ${totalPage};
		var currentPage = ${pageNo};
		var pageSize = ${pageSize};
		window.pagObj = $('#pagination').twbsPagination({
			totalPages: totalPages,
			visiblePages: pageSize,
			startPage: currentPage,
			next: '<span class=\"glyphicon glyphicon-forward fix-paging\"></span>',
			prev: '<span class=\"glyphicon glyphicon-backward fix-paging\"></span>',
			last: '<span class=\"glyphicon glyphicon-step-forward fix-paging\"></span>',
			first: '<span class=\"glyphicon glyphicon-step-backward fix-paging\"></span>',
			onPageClick: function (event, page) {
				if (currentPage != page) {
					$('#pageNo').val(page);
					document.forms["monitorForm"].action = '${batchJobMonitorUrl}';  
					document.forms["monitorForm"].submit();  
				}
			}
		});	
		
	 	$("#autoRefreshInterval").val(autoRefreshValue);
	 	interval = setInterval(function() {
	 		//ajaxGetExecJobs();
		}, autoRefreshValue*1000);
		
		$("#dialog1").dialog({
			autoOpen : false,
			width : 450,
			height : 150,
			modal : true,
			bgiframe: true,
			closeOnEscape : false
		});

		$("#btn_setting").click(function() {
			$('#dialog1').dialog('open');
		});
		
		$("#btn_refresh").click(function() {
			//ajaxGetExecJobs();
		});

		$("#btn_ok").click(function() {
			if(interval != null) {
				clearInterval(interval); 
			}
			autoRefreshValue = $("#autoRefreshInterval").val();
			if(autoRefreshValue == -1) {
				clearInterval(interval); 
			} else {
				interval = setInterval(function() {
					reloadJobList();
				}, autoRefreshValue*1000);
			}
			
			//ajaxGetExecJobs();
			$('#dialog1').dialog('close');
			
			return false;
		});
	});
	
	/*function reloadPage(){
		var orig = $("#monitorForm").serialize();
		var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
		var actionUrl = '${getJobListUrl}' + '?' + withoutEmpties;
		$.ajax({
			url : actionUrl,
			cache: false,
			type: "POST",
			success : function(data) {
				if(data.length > 0){
					var tb = $('#dtBasicExample').DataTable();	
					tb.clear();
		        	for(var i = 0; data.length; i++){
		        		tb.row.add([
									 "<a href=\"${scheDetailUrl}/data[i].jobId\" style=\"color: blue;\">" + data[i].jobId + "</a>",		        		             
		        		             data[i].functionName,
		        		             data[i].wkstnId,
		        		             data[i].userId,
		        		             data[i].serverId,
		        		             data[i].sessionId,
		        		             data[i].errorFlag
		        		]).draw(false);
		        	}
					
				}else{				
					$.alert({
						title: 'Thông báo',
						content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + ' Không tìm thấy dữ liệu',
						 buttons: {   
		        		        "Đóng": function () {}
		        		    }
					});
				}
			}
		});
	}*/

  /*function ajaxGetExecJobs() {
		var orig = $("#monitorForm").serialize();
		var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
		var actionUrl = '${getJobListUrl}' + '?' + withoutEmpties;
		$('#jobListFlexGrid').empty();
		$("#jobMonitor").show();
		$('#jobListFlexGrid').show();
		if (reload == "0") {
			reload = "1";
			$("#jobListFlexGrid").flexigrid({
				url : actionUrl,
				dataType : 'json',
				colModel : [ {
					display : 'Mã công việc',
					name : 'jobId',
					width : 150,
					sortable : true,
					align : 'left',
					render : renderLink
				}, {
					display : 'Tên công việc',
					name : 'jobName',
					sortable : true,
					width : 300,
					align : 'left'
				}, {
					display : 'Ghi chú',
					name : 'status',
					width : 100,
					sortable : false,
					align : 'center',
					render : renderImg
				}, {
					display : 'Mô tả',
					name : 'message',
					width : 500,
					sortable : true,
					align : 'left'
				},{
					display : 'Ngày cập nhật',
					name : 'executionDate',
					sortable : true,
					width : 230,
					align : 'left',
					process: function (col) 
                    {
	                    var content=  col.innerHTML;
	                    var currentTime = new Date(parseInt(content ));
	                    if(currentTime == 'Invalid Date' || content == '&nbsp;') {
							col.innerHTML = "";
						} else {
							col.innerHTML = currentTime.toLocaleDateString() + " " + currentTime.toLocaleTimeString();
						}
                    }
       			}],
				sortname : "createdate",
				sortorder : "desc",
				title : 'Danh sách việc làm',
				usepager : true,
				useRp : true,
				rp : 20,
				rpOptions: [1,5,10,20,30,50], //allowed per-page values
	            procmsg: 'Đang xử lý, Chờ trong giây lát ...',
	            pagestat: 'Hiển thị từ {from} đến {to} của {total} kết quả',
	            showToggleBtn : false,
				showTableToggleBtn : true,
				singleSelect : true,
				nowrap : false,
			 	width : 'auto',
	            height : 'auto',
		       onSuccess : reloadFrom
			});
		} else {
			$("#jobListFlexGrid").flexOptions({
				url : actionUrl,
				newp : 1
			}).flexReload();
		}
	}*/

	function renderLink(jobId) {
		var actionUrl = '${scheDetailUrl}/' + jobId;
		return "<a href=\""+actionUrl+"\" >" + jobId + "</a>";
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

	function reloadFrom() {
		var data = $('#jobListFlexGrid tbody').children().length;
		if(data <= 0) {
			$("#noDataMsg").show();
			$("#jobMonitor").hide();
		} else {
			$("#noDataMsg").hide();
			$("#jobMonitor").show();
		}
	}
</script>
<form:form  id="monitorForm"  name="monitorForm" >
	<div class="form-desing">
		<div class="new-heading-2">NHẬT KÝ CÔNG VIỆC</div>
		<fieldset>
			<legend>Danh sách các công việc</legend>

		<div style="height: 400px;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm">Mã công việc
								
								      </th>	
								      <th class="th-sm">Tên công việc
								
								      </th>
								      <th class="th-sm">Ghi chú
								
								      </th>
								      <th class="th-sm">Mô tả
								
								      </th>
								      <th class="th-sm">Ngày thực hiện
								
								      </th>								   
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>							    									      
									      <td><a style="color: blue;" href="${scheDetailUrl}/${item.jobId}" >${item.jobId}</a></td>
									      <td>${item.jobName}</td>
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
									      <td>${item.message}</td>
									      <td>${item.executionDate}</td>									      						     
									    </tr>
								    </c:forEach>
								  </tbody>
								  <c:if test="${empty jobList}">
								  
								   <tbody class="e-not-found ng-scope"><tr>
								  <td colspan="5" style="height: 362px">
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
	</div>
</form:form>
	<div class="waitingWhenDoneWaiting" style="display:none; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
			<div style="margin: 10px"> 
				<button type="button" class="btn btn-warning"  id="btn_setting">
					<span class="glyphicon glyphicon-cog"></span> Cài đặt
				</button>
				<button type="button" class="btn btn-primary" id="btn_refresh">
					<span class="glyphicon glyphicon-refresh"></span> Làm mới
				</button>
			</div>
	</div>
<div id="dialog1" title="Cài đặt" style="display:none" class="text-center">
	<table align="center">
		<tr>
		   <td align="left"><b>Khoảng cách làm mới tự động hiện tại</b></td>
		   <td><b>: &nbsp;</b></td>
		   <td align="left">
		   <select id="autoRefreshInterval">
				<c:forEach items="${autoRefreshList}" var="i">
				  <option value="${i}"> ${i} </option>
				</c:forEach>
				<option value="-1"> Off </option>
			</select><font size="1">&nbsp;giây</font>
		   </td>
		</tr>
		<tr>
			<td colspan="3" align="center">
				&nbsp;
			</td>
		</tr>
		<tr>
			<td colspan="3" align="center">
				 <input type="submit" class="bttnSize" style="width: 100px;float: right;margin-right: 50px;" id="btn_ok" value="Đồng ý"  />
			</td>
		</tr>
	</table>
</div>  
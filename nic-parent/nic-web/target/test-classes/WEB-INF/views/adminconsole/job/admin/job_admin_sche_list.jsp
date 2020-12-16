<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>


<c:url var="addScheduleUrl" value="/servlet/batchJobMgmt/addSchedule"/>
<c:url var="editScheduleUrl" value="/servlet/batchJobMgmt/editSchedule"/>
<c:url var="delScheduleUrl" value="/servlet/batchJobMgmt/delSchedule"/>
<c:url var="editPageUrl" value="/servlet/batchJobMgmt/editJob"/>
<c:url var="backUrl" value="/servlet/batchJobMgmt/view"/>
<c:url var="getJobScheUrl" value="/servlet/batchJobMgmt/getSchedule"/>
<c:url var="viewPageUrl" value="/servlet/batchJobMgmt/viewJob"/>
<style>
.pading-text {
	padding-left: 0px;
}
table.displayTag>thead>tr {
	height: 35px;
}

.displayTag tr.odd {
	height: 35px;
}
.displayTag tr.even {
	height: 35px;
}
</style>
<form:form  commandName="eppBatchJobForm"  id="scheduleForm"  name="scheduleForm" >
   <!--  <div id="main">-->
     <div class="form-desing"> 
     	<div>
	        	<div class="row">
	        		<div class= "ov_hidden">
     			<div class="new-heading-2">LỊCH LÀM VIỆC</div>
     <input type="hidden" id="prevMode" value="${prevMode}">
     <form:hidden id="mode" path="mode" />
     	  <div class="col-md-12">
     	  		<div class="col-md-6 pading-text">
     	  			<div class="col-md-4 pading-text">
     	  				<span>Mã công việc: </span>
     	  			</div>
     	  			<div class="col-md-8">
     	  				<span><c:out value="${eppBatchJobForm.jobId}" /></span>
						<form:hidden id="jobId" path="jobId" />
     	  			</div>
     	  			<div class="col-md-4 pading-text">
     	  				<span>Tên công việc: </span>
     	  			</div>
     	  			<div class="col-md-8">
     	  				<span><c:out value="${eppBatchJobForm.jobName}" /></span>
						<form:hidden id="jobName" path="jobName" />
     	  			</div>
     	  			<div class="col-md-4 pading-text">
     	  				<span>Tên lớp: </span>
     	  			</div>
     	  			<div class="col-md-8">
     	  				<span><c:out value="${eppBatchJobForm.jobClass}" /></span>
						<form:hidden id="jobClass" path="jobClass" />
     	  			</div>
     	  		</div>
     	  </div>	
     	  					<div>
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="min-width: 150px;">Tên công việc
								
								      </th>	
								      <th class="th-sm" style="min-width: 150px;">Ngày bắt đầu
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Ngày kết thúc
								
								      </th>
								      <th class="th-sm" style="min-width: 150px;">Mô tả
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Kích hoạt
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Chỉnh sửa
								
								      </th>
								      <th class="th-sm" style="min-width: 50px;">Xóa
								
								      </th>
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>							    								      
									      <td>${item.id.scheduleName}</td>
									      <td>${item.startDate}</td>
									      <td>${item.endDate}</td>	
									      <td>${item.scheduleDescription}</td>	
									      <c:choose>
									      		<c:when test="${item.active == true}">
									      			<td><img src="<c:url value='/resources/images/tick.gif'/>" border='0' width='16' height='16' alt='Completed' /></td>
									      		</c:when>
									      		<c:otherwise>
									      			<td><img src="<c:url value='/resources/images/cross.gif'/>" border='0' width='16' height='16' alt='Completed' /></td>
									      		</c:otherwise>
									      </c:choose>									      
									      <td><a href="javascript:editSchedule('${item.id.scheduleName}');"><img src="<c:url value='/resources/images/edit.gif'/>" border='0' width='16' height='16' /></a></td>	
									      <td><a href="javascript:delSchedule('${item.id.scheduleName}');"><img src="<c:url value='/resources/images/delete.gif'/>" border='0' width='16' height='16' /></a></td>						     
									    </tr>
								    </c:forEach>
								  </tbody>
								</table>
								<div class="col-md-5 col-sm-12" style="padding-left: 0px;">
									<div class="dataTables_info">Hiển thị ${startIndex} đến ${endIndex} của ${totalRecord} kết quả</div>
								</div>
								<div class="col-md-7 col-sm-12" style="padding-right: 0px;">
									<ul style="float: right;" class="pagination" id="pagination"></ul>
								</div>			 
								<input type="hidden" name="pageNo" id="pageNo" />
						      </div>	
	   <!--<div class="col-md-12">
			<div id="scheList" style="display: none;">
				<table id="scheListFlexGrid"></table>
			</div>
	   </div>-->
		<div id="noDataMsg" class="col-md-12">
     	  	<span style="padding: 5px; font-style: italic;">Không tìm thấy gì để hiển thị.</span>
     	</div>			
	</div>
	</div>
	</div>
	<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
		<div style="margin: 10px;">
				<button type="button" class="btn btn-danger" id="back_btn">
					<span class="glyphicon glyphicon-remove"></span> Quay lại
				</button>
				<button type="button" class="btn btn-primary" id="schedule_btn">
					<span class="glyphicon glyphicon-plus-sign"></span> Thêm lịch trình
				</button>
		</div>		
	</div>
	</div>
	<!-- </div> -->
</form:form>
<div id="dialog-confirm"></div>
<div id="dialog-error"></div>
<script type="text/javascript">
	var reload = "0";
	$(function() {
		$("#noDataMsg").hide();
		//ajaxGetJobSchedules();
		 //$('#dtBasicExample').DataTable();
        // $('.dataTables_length').addClass('bs-select');
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
					document.forms["scheduleForm"].action = '${viewPageUrl}/${idJob}';  
					document.forms["scheduleForm"].submit();  
				}
			}
		});
		
		$("#schedule_btn").click(function() {
			document.forms["scheduleForm"].action = '${addScheduleUrl}';
			document.forms["scheduleForm"].submit();
		});
		
		$("#back_btn").click(function() {
			var prevMode = $("#prevMode").val();
			if(prevMode == 'EDIT') {
				document.forms["scheduleForm"].action = '${editPageUrl}/'+$("#jobId").val();
			} else {
				document.forms["scheduleForm"].action = '${backUrl}';
			}
			
			document.forms["scheduleForm"].submit();
		});
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
			"Đồng ý" : function() {
				callBack(true);
				$(this).dialog("close");
			},
			"Hủy" : function() {
				callBack(false);
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
			"Đóng" : function() {
				$(this).dialog("close");
			}
		}
	});
    
	function delSchedule(scheName) {
		$("#dialog-confirm").dialog('option', 'title', 'Xóa');
		$("#dialog-confirm").html("Xác nhận xóa Lịch biểu: " + scheName + "?");
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
				"Đồng ý" : function() {
					$.ajax({
						type : "GET",
						url : '${delScheduleUrl}/' + $("#jobId").val()+"/"+scheName,
						success : function(data) {
							if (data == 'success') {
								var orig = $("#scheduleForm").serialize();
								var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
								var actionUrl = '${getJobScheUrl}/' + $("#jobId").val() + '?' + withoutEmpties;
								$("#scheListFlexGrid").flexOptions({
									url : actionUrl
								}).flexReload();
								reloadFrom();
							} else {
								$("#dialog-error").dialog('option', 'title', 'Lỗi');
								$("#dialog-error").html("Không thể xóa lịch làm việc: " + scheName+ ".");
								$("#dialog-error").dialog('open');
							}
						}
					});
					$(this).dialog("close");
				},
				"Hủy" : function() {
					$(this).dialog("close");
				}
			}
		});
		$("#dialog-confirm").dialog('open');
	}
 
 	function editSchedule(scheName) {
		document.forms["scheduleForm"].action = '${editScheduleUrl}/'+$("#jobId").val() + "/" + scheName;
		document.forms["scheduleForm"].submit();
	}
	
	/*function ajaxGetJobSchedules() {
		var orig = $("#scheduleForm").serialize();
		var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
		var actionUrl = '${getJobScheUrl}/' + $("#jobId").val() + '?' + withoutEmpties;
		$('#scheListFlexGrid').empty();
		$("#scheList").show();
		$('#scheListFlexGrid').show();
		if (reload == "0") {
			reload = "1";
			$("#scheListFlexGrid").flexigrid(
			{
				url : actionUrl,
				dataType : 'json',
				colModel : [
				{
					display : 'Tên công việc',
					name : 'id.scheduleName',
					width : 180,
					sortable : true,
					align : 'left'
				},
				{
					display : 'Ngày bắt đầu',
					name : 'startDate',
					sortable : true,
					width : 100,
					align : 'left',
					process : function(col) {
						var content = col.innerHTML;
						var currentTime = new Date(parseInt(content));
						if(currentTime == 'Invalid Date' || content == '&nbsp;') {
							col.innerHTML = "";
						} else {
							col.innerHTML = currentTime.toLocaleDateString();
						}
					}
				},
				{
					display : 'Ngày kết thúc',
					name : 'endDate',
					sortable : true,
					width : 100,
					align : 'left',
					process : function(col) {
						var content = col.innerHTML;
						var currentTime = new Date(parseInt(content));
						if(currentTime == 'Invalid Date' || content == '&nbsp;') {
							col.innerHTML = "";
						} else {
							col.innerHTML = currentTime.toLocaleDateString();
						}
					}
				},
				{
					display : 'Mô tả',
					name : 'scheduleDescription',
					sortable : true,
					width : 582,
					align : 'left'
				},
				{
					display : 'Kích hoạt',
					name : 'active',
					width : 100,
					sortable : false,
					align : 'center',
					render : renderImg
				},
				{
					display : 'Chỉnh sửa',
					name : 'id.scheduleName',
					width : 100,
					sortable : false,
					align : 'center',
					process : function(col) {
						var scheduleName = col.innerHTML;
						col.innerHTML = "<a href=\"javascript:editSchedule('"+scheduleName+"');\" title='Schedule Name: "
							+ scheduleName
							+ "'> "
							+ "<img src=\"<c:url value='/resources/images/edit.gif'/>\" border='0' width='16' height='16' />"
							+ "</a>";
					}
				} ,
				{
					display : 'Xóa',
					name : 'id.scheduleName',
					width : 100,
					sortable : false,
					align : 'center',
					process : function(col) {
						var scheduleName = col.innerHTML;
						col.innerHTML = "<a href=\"javascript:delSchedule('"+scheduleName+"');\" title='Schedule Name: "
						+ scheduleName
						+ "'> "
						+ "<img src=\"<c:url value='/resources/images/delete.gif'/>\" border='0' width='16' height='16' />"
						+ "</a>";
					}
				} ],
				sortname : "startDate",
				sortorder : "desc",
				title : 'Danh sách lịch làm việc',
				usepager : true,
				useRp : true,
				rp : 20,
				rpOptions : [ 1, 5, 10, 20, 30, 50 ], //allowed per-page values
				procmsg : 'Đang xử lý, chờ trong giây lát ...',
				pagestat : 'Hiển thị từ {from} đến {to} của {total} kết quả',
				showToggleBtn : false,
				showTableToggleBtn : true,
				singleSelect : true,
				nowrap : false,
				width : 'auto',
				height : 'auto',
				onSuccess : reloadFrom
			});
		} else {
			$("#scheListFlexGrid").flexOptions({
				url : actionUrl
				, newp : 1
			}).flexReload();
		}
	}*/

	function renderImg(active) {
		var result = "";
		if (active) {
			result = "<img src=\"<c:url value='/resources/images/tick.gif'/>\" border='0' width='16' height='16' alt='Completed' />";
		} else {
			result = "<img src=\"<c:url value='/resources/images/cross.gif'/>\" border='0' width='16' height='16' alt='Completed' />";
		}

		return result;
	}

	function reloadFrom() {
		var data = $('#scheListFlexGrid tbody').children().length;
		if(data <= 0) {
			$("#noDataMsg").show();
			$("#scheList").hide();
		} else {
			$("#noDataMsg").hide();
			$("#scheList").show();
		}
	}
</script>


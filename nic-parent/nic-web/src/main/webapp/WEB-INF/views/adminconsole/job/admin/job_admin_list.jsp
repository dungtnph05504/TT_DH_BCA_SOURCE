<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<c:url var="fwdtoSavePg" value="/servlet/batchJobMgmt/forwardReq"/>
<c:url var="editPageUrl" value="/servlet/batchJobMgmt/editJob"/>
<c:url var="viewPageUrl" value="/servlet/batchJobMgmt/viewJob"/>
<c:url var="getJobsUrl" value="/servlet/batchJobMgmt/getJobs"/>
<c:url var="batchJobAdminUrl" value="/servlet/batchJobMgmt/view" />
<style>
table#row > tr {
	height: 35px;
}
.displayTag tr.odd {
	height: 35px;
}
.displayTag tr.even {
	height: 35px;
}
</style>
<form:form id="eppBatchJobForm"  name="eppBatchJobForm"  method="GET" >
	<div id="content_main">
		<div class="form-desing">
	        	<div class="row">
	        		<div class="ov_hidden">
		<div class="new-heading-2">QUẢN LÝ CÔNG VIỆC</div>
		<c:if test="${not empty requestScope.messages}">
			<div id="successMsg" style="color: green; background-color: White;">
				<c:forEach items="${requestScope.messages}" var="takla">
					<li>${takla}</li>
				</c:forEach>
			</div>
		</c:if>
		<c:if test="${not empty requestScope.Errors}">
			<div id="errorMsg" style="color: red; background-color: White;">
				<c:forEach items="${requestScope.Errors}" var="takla">
					<li>${takla}</li>
				</c:forEach>

			</div>
		</c:if>
							<div>
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="min-width: 150px;">Mã công việc
								
								      </th>	
								      <th class="th-sm" style="min-width: 150px;">Tên công việc
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Mô tả
								
								      </th>
								      <th class="th-sm" style="min-width: 150px;">Người tạo
								
								      </th>
								      <th class="th-sm" style="min-width: 150px;">Ngày tạo
								
								      </th>
								      <th class="th-sm" style="min-width: 100px;">Cập nhật
								
								      </th>
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>							    
									      <td><a style="color: blue;" href="#" onclick="doViewIt('${item.jobId}')">${item.jobId}</a></td>
									      <td>${item.jobName}</td>
									      <td>${item.jobDescription}</td>
									      <td>${item.createby}</td>	
									      <td>${item.createdate}</td>	
									      <td><a href="${editPageUrl}/${item.jobId}"><img src="<c:url value='/resources/images/edit.gif'/>" border='0' width='16' height='16' /></a></td>						     
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
		<!--<div id="jobList" style="display: none;">
			<table id="jobListFlexGrid" ></table>
		</div>-->
		</div>
		</div>
		</div>
		<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
			<div style="margin: 10px"> 			
				 <button type="button" class="btn btn-success"  id="add_btn">
					<span class="glyphicon glyphicon-plus-sign"></span> Thêm mới công việc
				</button>	
			</div>
		</div>
	</div>
</form:form>
<script type="text/javascript">
var reload = "0";
$(function() {

	//ajaxGetJobs();
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
					document.forms["eppBatchJobForm"].action = '${batchJobAdminUrl}';  
					document.forms["eppBatchJobForm"].submit();  
				}
			}
		});
	if($("#successMsg").is(':visible')) {
		setTimeout(function() {
	        $("#successMsg").hide('blind', {}, 1000);
	    }, 15000);
	}

	if($("#errorMsg").is(':visible')) {
		setTimeout(function() {
	        $("#errorMsg").hide('blind', {}, 1000);
	    }, 30000);
	}
	
	
	$("#add_btn").click(function(){	
	    document.forms["eppBatchJobForm"].action = '${fwdtoSavePg}';
	    document.forms["eppBatchJobForm"].submit();	
		
	});
});

function doViewIt(jobId){	
	var actionUrl = '${viewPageUrl}/' + jobId;
	document.forms["eppBatchJobForm"].action = actionUrl;
    document.forms["eppBatchJobForm"].submit();
}

/*function ajaxGetJobs() {
	var orig = $("#eppBatchJobForm").serialize();
	var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
	var actionUrl = '${getJobsUrl}' + '?' + withoutEmpties;
	$('#jobListFlexGrid').empty();
	$("#jobList").show();
	$('#jobListFlexGrid').show();
	if (reload == "0") {
		reload = "1";
		$("#jobListFlexGrid").flexigrid({
			url : actionUrl,
			dataType : 'json',
			colModel : [ {
				display : 'Mã công việc',
				name : 'jobId',
				width : 123,
				sortable : true,
				align : 'left',
				render : renderLink
			}, {
				display : 'Tên công việc',
				name : 'jobName',
				sortable : true,
				width : 250,
				align : 'left'
			}, {
				display : 'Mô tả',
				name : 'jobDescription',
				width : 480,
				sortable : false,
				align : 'left'
			}, {
				display : 'Người tạo',
				name : 'createby',
				width : 150,
				sortable : true,
				align : 'left'
			}, {
				display : 'Ngày tạo',
				name : 'createdate',
				sortable : true,
				width : 150,
				align : 'left',
				process: function (col) 
                {
                    var content=  col.innerHTML;
                    var currentTime = new Date(parseInt(content ));
                    if(currentTime == 'Invalid Date' || content == '&nbsp;') {
						col.innerHTML = "";
					} else {
						col.innerHTML = currentTime.toLocaleDateString();
					}
                }
   			}, {
				display : 'Cập nhật',
				name : 'jobId',
				width : 100,
				sortable : false,
				align : 'center'
				,process : function(col) {
					var jobId = col.innerHTML;
					col.innerHTML = "<a href='${editPageUrl}/"
							+ jobId
							+ "' title='Job Id: "
							+ jobId
							+ "'> "
							+ "<img src=\"<c:url value='/resources/images/edit.gif'/>\" border='0' width='16' height='16' />"
							+ "</a>";
				}
			}],
			sortname : "createdate",
			sortorder : "desc",
			title : 'Danh sách công việc',
			usepager : true,
			useRp : true,
			rp : 20,
			rpOptions: [1,5,10,20,30,50], //allowed per-page values
            procmsg: 'Đang xử lý, chờ trong giây lát ...',
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
	var actionUrl = '${viewPageUrl}/' + jobId;
	return "<a href=\""+actionUrl+"\" >" + jobId + "</a>";
}

function reloadFrom() {
	var data = $('#jobListFlexGrid tbody').children().length;
	if(data <= 0) {
		$("#noDataMsg").show();
		$("#jobList").hide();
	} else {
		$("#noDataMsg").hide();
		$("#jobList").show();
	}
}
</script>


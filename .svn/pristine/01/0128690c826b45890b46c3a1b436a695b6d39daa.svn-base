<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<c:url var="prodUrl" value="/servlet/investigation/startinvestigation" />
<c:url var="searchUrl" value="/servlet/investigation/search" />
<c:url var="newUrl" value="/servlet/fraudCaseManagement/newInvestigation" />
<c:url var="pendingJobUrl" value="/servlet/investigation/pendingJobList" />
<c:url var="investigationAssignedJobUrl"
	value="/servlet/investigationAssigned/investigationAssignedList" />
<c:url var="loadAssignUrl"
	value="/servlet/investigationAssigned/loadAssign" />
	
<c:url var="addAssignUrl"
	value="/servlet/investigationAssigned/addAssign" />	
<style>
.cls-mg-bot {
	margin-top: 10px;
}
fieldset {
	height: 500px;
}
</style>
<form:form commandName="jobList" modelAttribute="form" name="form" >
			<!-- Phúc thêm lưu focus đơn vị tiếp nhận  -->
			<input type="hidden" name="ricId" id="ricId" />
			<c:if test="${not empty requestScope.Errors}">
				<div style="color:Red;background-color:White;border-style:solid;border-color:Red;border-width:thin;">
					<c:forEach items="${requestScope.Errors}" var="errorMessage">
						<li>
							${errorMessage}
						</li>
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
								<td style="padding-left: 5px;font-weight: bold; vertical-align: top;">
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
<div class="form-desing">
   <div> 
      <div>
   <div class="row">
   <div class="ov_hidden">
     <div class="new-heading-2">DANH SÁCH HỒ SƠ ĐÃ PHÂN CÔNG</div>
       	<div style="clear: both"></div>
		<div style="min-height: 10px"></div>
		<div style="border: solid 1px #cccccc; border-radius: 4px; height: 50px;">
			<div style="margin: 2px">
								<div class="col-md-3 col-sm-3">
									<div class="col-md-4 col-sm-4 cls-mg-bot" style="position: relative;">
										<div class="cla-font">Từ ngày</div>
									</div>
									<div class="col-md-8 col-sm-8 cls-mg-bot">
										<form:input id="createDate" name="createDate" path="createDate" cssClass="defaultText"
										size="12" maxlength="12" readonly="true"/>
									</div>
								</div>
								<div class="col-md-3 col-sm-3">
									<div class="col-md-4 col-sm-4 cls-mg-bot">
										<div class="cla-font">Đến ngày</div>
									</div>
									<div class="col-md-8 col-sm-8 cls-mg-bot">
										<form:input id="endDate" name="endDate" path="endDate" cssClass="defaultText"
										size="12" maxlength="12" readonly="true" />
									</div>									
								</div>							
								<div class="col-md-3 col-sm-3">
									<div style="margin-top: 10px;margin-bottom: 10px;">
										<button type="button" onclick="doApplyFilter();" class="btn_small btn-success">
										     <span class="glyphicon glyphicon-search"></span> Tìm kiếm
										</button>	
									</div>
								</div>				
			</div>
			</div>	
	        <div style="clear: both"></div>
	  		<br />
			<div class="col-md-3 col-sm-12">
								<fieldset>
									<legend>Tên cơ quan</legend>
									<table id="tbTenCQ" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
									  <thead>
									    <tr>
									      <th class="th-sm" style="max-width: 150px;">Mã 
									
									      </th>	
									      <th class="th-sm" style="min-width: 150px;">Tên cơ quan
									
									      </th>
									    </tr>
									  </thead>
									  <tbody>
									    <c:forEach items="${listSiteRepository}" var="item">
										    <tr id="${item.key}">
										    	<td>${item.key}</td>
										    	<td>${item.value}</td>	
										    </tr>
									    </c:forEach>
									  </tbody>
									</table>
								</fieldset>
							</div>
							<div class="col-md-6 col-sm-12">
								<fieldset>
									<legend>Danh sách A</legend>
									<table id="tbDanhSachA" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
									  <thead>
									    <tr>
									      <th class="th-sm" style="max-width: 150px;">Số DS tiếp nhận
									
									      </th>	
									      <th class="th-sm" style="min-width: 100px;">Tổng số hồ sơ
									
									      </th>
									      <th class="th-sm" style="min-width: 100px;">Cơ quan
									
									      </th>								     
									      <th class="th-sm" style="max-width: 50px;">Chọn
									      </th>
									    </tr>
									  </thead>
									  <c:if test="${not empty jobList}">
										  <tbody>
										    <c:forEach items="${jobList}" var="item">
											    <tr id="${item.packageId}">
											      <td style="display: none;">${item.stt}</td>	
											      <td><c:url var="jobUrl" value="/servlet/investigationAssignment/investigationAssignmentDetail/${item.packageId}" />
														<a href="#"> <c:out value="${item.packageId}" /></a></td>	
											      <td class="align-right">${item.numberTran}</td>
											      <td>${item.ricName}</td>
											      <td class="align-central"><form:checkbox path="packageId" id="${item.stt}" value="${item.packageId}"
													cssClass="${item.currentlyAssignedToAnInvestigationOfficer}" /></td>
												  <td style="display: none;">${item.packageId}</td>			
											    </tr>
										    </c:forEach>
										  </tbody>
									  </c:if>
									</table>
									<c:if test="${empty jobList}">
									  	<div class="style-no-record">Không tìm thấy kết quả</div>
									</c:if>
									<c:if test="${not empty jobList}">
										<div class="col-md-5 col-sm-12" style="padding-left: 0px;">
											<div class="dataTables_info">Hiển thị ${startIndex} đến ${endIndex} của ${totalRecord} kết quả</div>
										</div>
										<div class="col-md-7 col-sm-12" style="padding-right: 0px;">
											<ul style="float: right;" class="pagination" id="pagination"></ul>
										</div>
										<input type="hidden" name="pageNo" id="pageNo" /> 
									</c:if>
								</fieldset>
							</div>
							<div class="col-md-3 col-sm-12">
								<fieldset>
									<legend>DS cán bộ xử lý</legend>
									<table id="tbCanBoXL" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
									  <thead>
									    <tr>
									      <th class="th-sm" style="max-width: 50px;">
									
									      </th>	
									      <th class="th-sm" style="min-width: 150px;">Họ tên
									
									      </th>
									    </tr>
									  </thead>
									  <tbody>
									    <c:forEach items="${userAssignment}" var="item">
										    <tr>
										    	<td class="align-central"><form:checkbox id="${item.key}_CB"  path="selectedJobIds" value="${item.key}" /></td>
										    	<td>${item.value}</td>	
										    </tr>
									    </c:forEach>
									  </tbody>
									</table>
								</fieldset>
							</div>
							<!-- <div>
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="max-width: 50px;">ID
								
								      </th>	
								      <th class="th-sm" style="min-width: 140px;">Mã giao dịch
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Loại giao dịch
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Loại điều tra
								
								      </th>
								      <th class="th-sm" style="min-width: 130px;">Ngày nộp đơn 
								
								      </th>
								      <th class="th-sm" style="min-width: 130px;">Cán bộ xử lý
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">
								      </th>
								      
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>
									      <td>${item.uploadJobId}</td>	
									      <td>${item.transactionId}</td>
									      <td>${item.jobType}</td>
									      <td>${item.investigationType}</td>
									      <td>${item.dateOfApplication}</td>
									      <td>${item.investigationOfficerId}</td>
									      <td><a href="#" style="color: blue;" onclick="unassignJob('${item.uploadJobId}')">Bỏ xử lý hồ sơ</a> </td>
									      <td><a href="#" style="color: blue;" onclick="unassignAllJobsForUser('${item.investigationOfficerId}')">Bỏ tất cả hồ sơ được giao</a> </td>									      
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
						      </div>-->	  
			<%--				
				int pageSize = 20;
			--%> 
  
		<!--<c:if test="${empty jobList}">
			Không tìm thấy kết quả.
			<br />
		</c:if>
		<c:if test="${not empty jobList}">
  
			<display:table cellspacing="1" cellpadding="0" id="row" sort="external"  partialList="true" size="${totalRecords}"
				export="false" class="displayTag" name="jobList" defaultsort="1"
				defaultorder="ascending" pagesize="${pageSize}"
				requestURI="/servlet/investigationAssigned/investigationAssignedList">
				<display:setProperty name="paging.banner.item_name" value='kết quả'></display:setProperty>
				<display:setProperty name="paging.banner.items_name" value='kết quả'></display:setProperty>
				<display:setProperty name="paging.banner.placement" value='bottom'></display:setProperty>
				<display:setProperty name="paging.banner.no_items_found" value='<span class="pagebanner"> Không tìm thấy dữ liệu. </span>'></display:setProperty>
				<display:setProperty name="paging.banner.one_item_found" value='<span class="pagebanner"> Tìm thấy 1 kết quả. </span>'></display:setProperty>
				<display:setProperty name="paging.banner.all_items_found" value='<span class="pagebanner"> {0} {1} tìm thấy, hiển thị tất cả {2}. </span>'></display:setProperty>
				<display:setProperty name="paging.banner.some_items_found" value='<span class="pagebanner"> {0} {1} tìm thấy, hiển thị từ {2} đến {3}. </span>'></display:setProperty>		
				<display:setProperty name="paging.banner.first" value='<span class="pagelinks"> [Đầu tiên/Trước] {0} [ <a href="{3}">Tiếp</a>/ <a href="{4}">Cuối cùng</a>] </span>'></display:setProperty>		
				<display:setProperty name="paging.banner.last" value='<span class="pagelinks">[ <a href="{1}">Đầu tiên</a>/ <a href="{2}">Trước</a>] {0} [Tiếp/Cuối cùng] </span>'></display:setProperty>								
				<display:setProperty name="paging.banner.full" value='	<span class="pagelinks"> [<a href="{1}">Đầu tiên</a>/ <a href="{2}">Trước</a>] {0} [ <a href="{3}">Tiếp</a>/ <a href="{4}">Cuối cùng </a>]</span>'></display:setProperty>												
				<display:column title="ID" sortable="false" sortProperty="uploadJobId"> 
				  <c:out value="${row.uploadJobId}" />  
				</display:column>
				<display:column property="transactionId" sortable="false"
					title="Mã giao dịch" maxLength="30" />
				<display:column property="jobType" sortable="false"
					title="Loại giao dịch" maxLength="30" />
				<display:column property="investigationType" sortable="false"
					title="Loại điều tra" maxLength="30" >
			    </display:column>
				<display:column property="dateOfApplication" title="Ngày nộp đơn" sortable="false" format="{0,date,dd-MMM-yyyy}" >
				</display:column>
				<display:column property="investigationOfficerId" sortable="false"
					title="Cán bộ xử lý" maxLength="64" >
			    </display:column>
				<display:column title="" sortable="false" > 
					<a href="#" onclick="unassignJob('${row.uploadJobId}')">Bỏ xử lý hồ sơ</a> 
				</display:column>
				<display:column title="" sortable="false" > 
					<a href="#" onclick="unassignAllJobsForUser('${row.investigationOfficerId}')">Bỏ tất cả hồ sơ được giao</a> 
				</display:column>
			</display:table>
		</c:if> -->
		</div>
		<div style="display: flex;flex: 0 auto;justify-content: center;">
		<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
			<div style="margin: 10px"> 
				<button type="button"  onclick="plusAssign();" name="approve" class="btn btn-success">
					  <span class="glyphicon glyphicon-check"></span> Thêm cán bộ
				</button>
			</div>
		</div>
		</div>
	</div>
	</div>
	</div>
	</div>
	<script type="text/javascript">
	var totalPages = ${totalPage};
	var currentPage = ${pageNo};
	var pageSize = ${pageSize};
	window.pagObj = $('#pagination').twbsPagination({
				totalPages: totalPages,
				visiblePages: 5,
				startPage: currentPage,
				next: '<span class=\"glyphicon glyphicon-forward fix-paging\"></span>',
				prev: '<span class=\"glyphicon glyphicon-backward fix-paging\"></span>',
				last: '<span class=\"glyphicon glyphicon-step-forward fix-paging\"></span>',
				first: '<span class=\"glyphicon glyphicon-step-backward fix-paging\"></span>',
				onPageClick: function (event, page) {
					if (currentPage != page) {
						$('#pageNo').val(page);
						$('#ricId').val('${formData.regSiteCode}');	
						document.forms["form"].action = '${investigationAssignedJobUrl}';  
						document.forms["form"].submit();  
					}
				}
			});
			
			function doApplyFilter() {
		
				document.forms["form"].action = '<c:url value="/servlet/investigationAssigned/applyFilter1" />';
				document.forms["form"].submit();
			}

		
		
		function plusAssign(){
			$.confirm({
			    title: 'Xác nhận thay đổi',
			    content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/question-1.png\">" + ' Bạn có muốn tiếp tục thực hiện.',
			    buttons: {			       		       
			        "Có": function () {
			        	var Url = "${addAssignUrl}";
			        	document.forms["form"].action = Url;  
						document.forms["form"].submit();
			        },
			        "Không": function () {
			        }
			    }
			})
		}
	</script>
	
	
    <!-- ========================================================== DIALOG BOX - START ================================================================ -->
		<div id="dialog-confirm-unassignJob"></div>	
		<script type="text/javascript">
		
		$(document).ready(function() {
		    $("table#tbTenCQ > tbody > tr").click(function(){
		    	var stage = $(this).find('td:first').text();
		    	$('#ricId').val(stage);	
		    	document.forms["form"].action = '${investigationAssignedJobUrl}';  
				document.forms["form"].submit();  
		    });
		    
		    $("table#tbDanhSachA > tbody > tr").click(function(){
		    	var packageId = $(this).find('td:last').text();
		    	var i = $(this).find('td:first').text();
		    	var url = "${loadAssignUrl}";
		    	var id = '#' + i;
		    	//alert(id);
		    	//$('#tbDanhSachA > tbody > tr').css('background-color', '#fff');
		    	//$('#tbDanhSachA > tbody > tr > td').css('color', '#333333');
		    	//$('#tbDanhSachA > tbody > tr > td > a').css('color', '#333333');
		    	$('#tbDanhSachA > tbody > tr').removeClass("back-gr");
		    	//$(id).css('background-color', '#7fae46');
		    	//$(id + ' > td').css('color', '#fff');	
		    	//$(id + ' > td > a').css('color', '#fff');	
		    	$(this).addClass("back-gr");
		    	$('#tbDanhSachA input[type="checkbox"]').prop('checked', false);
		    	$('#tbDanhSachA input[type="checkbox"]' + id).prop('checked', true);
		    	
		    	$.ajax({
					type : "POST",
					url : url,
					data : {
						packageNo : packageId
					},
					success: function(data) {
						$('#tbCanBoXL > tbody input[type="checkbox"]').prop('checked', false);
						$('#tbCanBoXL > tbody input[type="checkbox"]').attr("disabled", false);
						if(data.length > 0){
							for(var i = 0; i < data.length; i++){
								var idCech = '#' + data[i] + '_CB';
								$(idCech).prop('checked', true);
								$(idCech).attr("disabled", true);
							}
						}
			        },
			        error: function(e){}
				}); 
		    });

		    if('${formData.regSiteCode}' != ''){
			    $('#${formData.regSiteCode}').css('background-color', '#7fae46');
			    $('#${formData.regSiteCode} > td').css('color', '#fff');	    	
		    }
		    var thanhCong = '${thanhcong}';
		    var loiPhanCong = '${loi}';
		    if(thanhCong != ''){
		    	$.notify(thanhCong, {
					globalPosition: 'bottom left',
					className: 'success',
				});
		    }
			if(loiPhanCong != ''){
				$.notify(loiPhanCong, {
					globalPosition: 'bottom left',
					className: 'error',
				});
		    }
		});
		
		    var jobIdToUnassign_current;
		
			function unassignJob(jobIdToUnassign) { 
			     jobIdToUnassign_current = jobIdToUnassign;
				 $("#dialog-confirm-unassignJob").dialog('option', 'title', 'Bỏ phân công xử lý hồ sơ');
				 $("#dialog-confirm-unassignJob").html("Bạn có muốn bỏ phân công xử lý hồ sơ này không  (Mã công việc "+jobIdToUnassign+")?");
				$( "#dialog-confirm-unassignJob").dialog("open");
			}
		
			$( "#dialog-confirm-unassignJob" ).dialog({
				modal: true,
			      autoOpen: false,
				  width : 500,
				  resizable: true,
			      show: {
			        effect: "fade",
			        duration: 200
			      },
			      hide: { 
			      },
				   buttons:{
			    "Đồng ý": function() {
			    	unassignJob_do();
			    },
				"Hủy": function() {
					$(this).dialog("close");
			    }
			   }
			});
			
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
				if (e.keyCode == 8 || e.keyCode == 46) {
					$.datepicker._clearDate(this);
				}
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
				if (e.keyCode == 8 || e.keyCode == 46) {
					$.datepicker._clearDate(this);
				}
			});
			
			function unassignJob_do() {
				//alert(jobIdToUnassign_current);
				document.forms["form"].action = '<c:url value="/servlet/investigationAssigned/unassignJob/" />'+jobIdToUnassign_current;
				document.forms["form"].submit();
			} 
		</script>
    <!-- ========================================================== DIALOG BOX - END ================================================================ -->
    
    
	
	
    <!-- ========================================================== DIALOG BOX - START ================================================================ -->
		<div id="dialog-confirm-unassignAllJobsForUser"></div>	
		<script type="text/javascript">
		
		    var userIdToUnassign_current;
		
			function unassignAllJobsForUser(userIdToUnassign) { 
			     userIdToUnassign_current = userIdToUnassign;
				 $("#dialog-confirm-unassignAllJobsForUser").dialog('option', 'title', 'Bỏ phân công tất cả hồ sơ');
				 $("#dialog-confirm-unassignAllJobsForUser").html("Bạn có muốn bỏ phân công tất cả các hồ sơ cho người này  (Mã người dùng "+userIdToUnassign+")?");
				$( "#dialog-confirm-unassignAllJobsForUser").dialog("open");
			}
		
			$( "#dialog-confirm-unassignAllJobsForUser" ).dialog({
				modal: true,
			      autoOpen: false,
				  width : 500,
				  resizable: true,
			      show: {
			        effect: "fade",
			        duration: 200
			      },
			      hide: { 
			      },
				   buttons:{
			    "Đồng ý": function() {
			    	unassignAllJobsForUser_do();
			    },
				"Hủy": function() {
					$(this).dialog("close");
			    }
			   }
			});
			
			function unassignAllJobsForUser_do() {
				document.forms["form"].action = '<c:url value="/servlet/investigationAssigned/unassignAllJobsForUser/" />'+userIdToUnassign_current;
				document.forms["form"].submit();
			} 
		</script>
    <!-- ========================================================== DIALOG BOX - END ================================================================ -->
    
</form:form>



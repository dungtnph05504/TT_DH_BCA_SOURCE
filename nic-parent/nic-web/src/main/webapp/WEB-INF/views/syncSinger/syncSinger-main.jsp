<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<c:url var="prodUrl" value="/servlet/investigation/startApproveStatus" />
<c:url var="newUrl" value="/servlet/investigation/newApproveStatus" />
<c:url var="pendingJobUrl" value="/servlet/investigation/pendingJobList" />
<c:url var="JobSyncSingerUrl"
	value="/servlet/syncSinger/getSyncSingerJob" />
<style>
.cls-mg-bot {
	margin-top: 10px;
}
#ajax-load-qa {
	background: rgba(255, 255, 255, .8)
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
<form:form commandName="jobList" modelAttribute="formData" name="formData">

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
   <div class="row">
   <div class="ov_hidden">
   <div id="content_inner"> 
    <div class="new-heading-2">DANH SÁCH DỮ LIỆU ĐỒNG BỘ SANG BCA</div>     
       		<div style="clear: both"></div>
						<div style="min-height: 10px"></div>

						<div style="margin: 2px">
							<div class="cla-form-bca">
								<div style="margin: 2px">
								<div class="col-md-4 col-sm-4" style="margin-bottom: 10px;">
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Mã giao dịch:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<input type="text" name="searchTransactionId" id="searchTransactionId" style="width: 100%;" />										
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Mức độ ưu tiên:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<select id="priorityId"
											 name="priority" class="priorityId"
											 style="font-family: Arial; font-size: 12px;width: 100%;">
											 <c:forEach var="entry" items="${priorityCode}">
												 <option value="${entry.key}" style="margin-right: 5px;" label="${entry.value}" />
											 </c:forEach>
										<select>
									</div>									
								</div>
								<div class="col-md-4 col-sm-4">
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Loại giao dịch:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<select id="transactionTypeId"
											name="transactionType" class="transactionTypeId"
											style="font-family: Arial; font-size: 12px;width: 100%;">
											<c:forEach var="entry" items="${transactionType}">
												 <option value="${entry.key}" style="margin-right: 5px;" label="${entry.value}" />
											</c:forEach>
										<select>
									</div>
									
									
								</div>
								<div class="col-md-4 col-sm-4">
									<div class="col-md-12">
										<div style="margin-top: 10px;">
											<button type="button" onclick="doSubmitSave(this.form);" class="btn_small btn-primary btn-search">
										        <span class="glyphicon glyphicon-search"></span> Tìm kiếm
										    </button>
											<input type="hidden" id="userId" name="userId" value="${sessionScope.userSession.userName}" />
										</div>
									</div>
								</div>

							</div>
						</div>
						</div>

  
      
	  <br />
	 						 <div>
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="max-width: 50px;">ID
								
								      </th>	
								      <th class="th-sm" style="min-width: 130px;">Mã giao dịch
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Họ tên
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Số hộ chiếu
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Ngày nộp đơn
								
								      </th>
								      <th class="th-sm" style="min-width: 130px;">Loại giao dịch
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Ngày phát hành
								
								      </th>
								      <th class="th-sm" style="min-width: 100px;">Ưu tiên
								      </th>
								      <th class="th-sm" style="max-width: 50px;">Chọn
								      </th>
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>
									      <td><c:url var="jobUrl" value="/servlet/investigation/syncSingerDetailId/${item.uploadJobId}" />
												<a id="load-request" style="color: blue;" href="${jobUrl}"> <c:out value="${item.uploadJobId}" /></a></td>	
									      <td>${item.transactionId}</td>
									      <td>${item.fullName}</td>
									      <td>${item.passportNo}</td>
									      <td>${item.dateOfApplication}</td>
									      <td>${item.jobType}</td>
									      <td>${item.estDateOfCollectionString}</td>
									      <td>${item.priorityString}</td>						
									      <td><form:checkbox path="selectedJobIds" value="${item.transactionId}"/></td>	
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
						      <div id="ajax-load-qa"></div>
			<%--			 <div id="ajax-load-qa"></div>	
				int pageSize = 20;
			--%>

		
		<!--<c:if test="${empty jobList}">
			Không bản ghi trong danh sách dữ liệu đồng bộ
			<br />
		</c:if>
		<c:if test="${not empty jobList}">
			<display:table cellspacing="1" cellpadding="0" id="row" sort="external"  partialList="true" size="${totalRecords}"
				export="false" class="displayTag table" name="jobList" defaultsort="1"
				defaultorder="ascending" pagesize="${pageSize}"
				requestURI="/servlet/syncSinger/getSyncSingerJob">
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
				<display:column title="Mã công việc" sortable="false" sortProperty="uploadJobId">

				<c:url var="jobUrl" value="/servlet/investigation/syncSingerDetailId/${row.uploadJobId}" />
				<a href="${jobUrl}"> <c:out value="${row.uploadJobId}" /></a>

				</display:column>
				<display:column property="transactionId" sortable="false"
					title="Mã ứng dụng" maxLength="30" />
				<display:column property="fullName" sortable="false"
									title="Họ tên đầy đủ" maxLength="50" />
				<display:column property="passportNo" sortable="false"
									title="Số hộ chiếu" maxLength="30" />
				<display:column property="dateOfApplication" title="Ngày nộp đơn" sortable="false" format="{0,date,dd-MMM-yyyy}" >
				</display:column>
				<display:column property="jobType" sortable="false"
					title="Loại giao dịch" maxLength="30" />
				<display:column property="estDateOfCollectionString" sortable="false"
					title="Ngày phát hành" maxLength="30" />
				<display:column property="priorityString" sortable="false"
					title="Ưu tiên" maxLength="30" />
				<display:column title="Chọn" sortable="false">
					<form:checkbox path="selectedJobIds" value="${row.transactionId}"/>
				</display:column>	
			</display:table>
		</c:if>-->


		</div>
	</div>
	</div>
	</div>
	</div>
	<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
			<div style="margin: 10px"> 
				<button type="button" class="btn btn-primary" onclick="openBoxReject();" name="approve">
					<span class="glyphicon glyphicon-list-alt"></span> Tạo danh sách
				</button>
				<!--<input type="button" class="btn btn-primary" onclick="openBoxReject();" name="approve"  value="Tạo danh sách" /> 
				&nbsp; -->
			</div>
	</div>
	
	<c:url var="createHandover" value="/servlet/syncSinger/createHandoverSyncA72" />
	
	<div id="dialog-reject-getRemarks" title="Tạo danh sách bàn giao" style="display: none;"> 
			<div class="form-group">		
				<label for="codeHandover" style="display:none">Mã danh sách bàn giao</label>
				<input class="form-control"  id="codeHandover" name="codeHandover" style="display:none"/>	
				<br />
				<label for="nameHandover">Tên danh sách bàn giao</label>
				<input class="form-control" id="nameHandover" name="nameHandover" />
				<!-- <br />
				<div class="form-group">
				  <label for="typeHandover">Loại danh sách:</label>
				  <select class="form-control" id="typeHandover" name="typeHandover">
				    <option value="1">Bàn giao sang Bộ Công an</option>
				    <option value="2">Bàn giao sang Xét duyệt</option>
				  </select>
				</div> -->
			</div> 
	</div>
<div id="dialog-confirm"></div>
	<script type="text/javascript">
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
					document.forms["formData"].action = '${JobSyncSingerUrl}';  
					document.forms["formData"].submit();  
				}
			}
		});
	function openBoxReject(){
		
		  $( "#dialog-reject-getRemarks" ).dialog( "open" ); 
	}
	
	$(function() {
		    $( "#dialog-reject-getRemarks" ).dialog({
				  modal: true,
			      autoOpen: false,
				  width : 600,
				  height: 300,
				  resizable: false,
			      show: {
			        effect: "fade",
			        duration: 1000
			      },
			      hide: { },
			      buttons: {
			         "Tiếp tục": function() {
						var inp = $("#codeHandover").val();
						var inp1 = $("#nameHandover").val();
						/* 
						if ($.trim(inp).length == 0){
							alert ('Chưa nhập mã danh sách!');
							return;
						}    */
						if ($.trim(inp1).length == 0){
							//alert ('Chưa nhập tên danh sách!');
							$.alert({
								title: 'Thông báo',
								content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Chưa nhập tên danh sách",
							});
							return;
						}    
			            $( this ).dialog( "close" );
			            requestApprove(inp, inp1);
			         },
			         "Đóng": function() {
			            $( this ).dialog( "close" );
			         }
			      } 
		    });
	}); 
	
	function requestApprove(code, name) { 
		$.confirm({
		    title: 'Thông báo',
		    content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/tip_1a.png\">" + ' Bạn có muốn tiếp tục thực hiện?',
		    buttons: {
		        OK: function () {
		        	document.forms["formData"].currentlyAssignedToUserId.value =  code;
			      	document.forms["formData"].unassignAllForUserUserId.value  =  name;
			 	  	document.forms["formData"].action = '${createHandover}';  
				   	document.forms["formData"].submit();  
		        },
		        CANCEL: function () {
		        	//return false;
		        }		       
		    }
		})
		/*if(confirm("Có chăc chắn muốn thực hiện hành động này?"))
		   {
				document.forms["formData"].currentlyAssignedToUserId.value =  code;
		      	document.forms["formData"].unassignAllForUserUserId.value  =  name;
		 	  	document.forms["formData"].action = '${createHandover}';  
			   	document.forms["formData"].submit();  

		   }
		  else
		    return false;*/
	}
	
		function doSubmitSave(form) {
			form.action = '<c:url value="/servlet/syncSinger/searchSyncTransactionId" />';//TRUNG SỬA GỌI HÀM BỘ SEARCH
			form.submit();
		}
	</script>
	<script type="text/javascript">
	
		$('#load-request').click(function(){
			$('#ajax-load-qa').css('display', 'block');
		});
	
		function doSubmitNew(form) {
			document.forms["formData"].action = "${newUrl}";
			document.forms["formData"].submit();
		}
		$( "#dialog-confirm" ).dialog({
			modal: true,
		      autoOpen: false,
			  width : 500,
			  resizable: true,
		      show: {
		        effect: "fade",
		        duration: 200
		      },
		      hide: {
		        //effect: "explode",
		        //duration: 1000
		      },
			   buttons:{
		    Ok: function() {
		    	document.forms["formData"].action = "${newUrl}";
				document.forms["formData"].submit();
		    },
			Cancel: function() {
				$(this).dialog("close");
		    }
		   }
		});
		
		function doSubmitNew1(form) {
			 $("#dialog-confirm").dialog('option', 'title', 'Pending Jobs Status');
			 $("#dialog-confirm").html("There are Pending Jobs for Investigation? Do you want to continue?");
			 $("#dialog-confirm").dialog( 'open' );			
		}
	</script>
	
	<form:input    path="currentlyAssignedToUserId"                       type="hidden" name="currentlyAssignedToUserId"                       value="" />
	<form:input    path="unassignAllForUserUserId"                        type="hidden" name="unassignAllForUserUserId"                        value="" />
	<form:input    path="assignToId"                        type="hidden" name="assignToId"                        value="" />
</form:form>



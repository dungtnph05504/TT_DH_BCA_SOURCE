<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<c:url var="prodUrl" value="/servlet/investigation/startApproveStatus" />
<c:url var="newUrl" value="/servlet/investigation/newApproveStatus" />
<c:url var="pendingJobUrl" value="/servlet/investigation/pendingJobList" />
<c:url var="addPaymentMatrix"
	value="/servlet/signerController/addSignerGovs" />
<c:url var="signerGovsUrl" value="/servlet/signerController/signerGovs" />	
<style>
<!--
-->
.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
    white-space: pre-wrap;
}
table.displayTag>thead>tr {
	height: 35px;
}

</style>
<form:form commandName="jobList" id="form" name="form" >

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
						<table width="600" height="40" border="0" cellpadding="5">
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
				

							<div class="new-heading-2">DANH SÁCH CHỮ KÝ CÔNG VĂN</div>  
							
							<!--<input type="button" id="addBtn" class="btn-sm btn-success" value="Thêm mới bản ghi" style="float: right;margin-top: 10px;"/>-->
							<div style="border: solid 1px #cccccc; border-radius: 4px; margin: 2px;width:100%;float:left;margin-top: 10px;">
							<div style="margin: 2px">
								<div class="col-md-6" style="padding-top: 5px;">
									<div class="col-md-4 cls-mg-bot">Số đơn đăng ký</div>
									<div class="col-md-8">
										 <input type="text" name="search_data" id="search_data" value="${txnId}" />				
									</div>
								</div>
								<div class="col-md-12 cls-mg-bot" style="margin-bottom: 10px;">
									<div style="text-align: center;">
										<button type="button" onclick="doSubmitSave(this.form);" class="btn_small btn-primary btn-search">
											<span class="glyphicon glyphicon-search"></span> Tìm kiếm
										</button>
										<!--<input type="button" class="btn_small btn-primary btn-search"  value="Tìm kiếm" onclick="doSubmitSave(this.form);"  />--> 
									</div>
								</div>
							</div>
						</div>								
					

						<br />
						      <div>
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="max-width: 50px;">STT
								
								      </th>	
								      <th class="th-sm" style="min-width: 150px;">Mã người ký
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Tên người ký
								
								      </th>
								      <th class="th-sm" style="min-width: 150px;">Tên cơ quan
								
								      </th>	
								      <th class="th-sm" style="min-width: 150px;">Trạng thái
								
								      </th>
								      <th class="th-sm" style="min-width: 130px;">Ngày tạo
								
								      </th>
								      <th class="th-sm" style="min-width: 130px;">Ngày sửa
								
								      </th>							     
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>							    
									      <td><c:url var="jobUrl"
											value="/servlet/signerController/signerGovsEdit/${item.id}" />
											<a style="color: blue;" href="${jobUrl}"> <c:out value="${item.id}" /></a></td>
									      <td>${item.codeSigner}</td>	
									      <td>${item.nameSigner}</td>	
									      <td>${item.nameGov}</td>	
									      	<c:set var="statusF" value="${item.active}" />
											<c:choose>
												<c:when test="${statusF eq 'Y'}">
													<td><a href="#" style="color:green">Hoạt động</a></td>
												</c:when>
												<c:otherwise>
													<td><a href="#" style="color:red">Tạm khóa</a></td>
												</c:otherwise>
											</c:choose>	
									      <td>${item.createDate}</td>		
									      <td>${item.updateDate}</td>										     
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
						<%--
							int pageSize = 20;
						--%>						
						<!--<c:if test="${empty jobList}">
			Không bản ghi trong danh sách chữ ký công văn
			<br />
						</c:if>
						<c:if test="${not empty jobList}">
							<display:table cellspacing="1" cellpadding="0" id="row"
								sort="external" partialList="true" size="${totalRecords}"
								export="false" class="displayTag table" name="jobList"
								defaultsort="1" defaultorder="ascending" pagesize="${pageSize}"
								requestURI="/servlet/signerController/signerGovsSearch">
							
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
								<display:column title="STT" sortable="false" sortProperty="id">
									
									<c:url var="jobUrl"
										value="/servlet/signerController/signerGovsEdit/${row.id}" />
									<a href="${jobUrl}"> <c:out value="${row.id}" /></a>
								</display:column>
								<display:column property="codeSigner" sortable="false"
									title="Mã người ký" maxLength="30">
								</display:column>
								<display:column property="nameSigner" sortable="false"
									title="Tên người ký" maxLength="200">
								</display:column>
								<display:column property="nameGov" sortable="false"
									title="Tên cơ quan" maxLength="200">
								</display:column>
								<display:column sortable="false" title="Trạng thái">
									<c:set var="statusF" value="${row.active}" />
									<c:choose>
										<c:when test="${statusF eq 'Y'}">
											<a href="#" style="color:green"> Hoạt động</a>
										</c:when>
										<c:otherwise>
											<a href="#" style="color:red"> Tạm khóa</a>
										</c:otherwise>
									</c:choose>
									
								</display:column>
								<display:column property="createDate" title="Ngày tạo"
									sortable="false" format="{0,date,dd-MMM-yyyy}">
								</display:column>
								<display:column property="updateDate" title="Ngày sửa"
									sortable="false" format="{0,date,dd-MMM-yyyy}">
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
				 <button type="button" class="btn btn-success" id="addBtn">
					  <span class="glyphicon glyphicon-plus-sign"></span> Thêm mới
				 </button>
			</div>
	</div>
	<div id="dialog-confirm"></div>
	<script type="text/javascript">
		function doSubmitSave(form) {
			/* form.action = '<c:url value="/servlet/investigation/searchApprove" />';//TRUNG SỬA GỌI HÀM BỘ SEARCH
			form.submit(); */
			//alert('Chức năng này đang được phát triển');
			 $.alert({
					title: 'Thông báo',
					content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/error_1a.png\">" + " Chức năng chưa khả dụng",
				});
		}
	</script>
	<script type="text/javascript">
	// $('#dtBasicExample').DataTable();
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
					document.forms["form"].action = '${signerGovsUrl}';  
					document.forms["form"].submit();  
				}
			}
		});	
	$("#addBtn").click(function(){	
		 document.forms[0].action = '${addPaymentMatrix}';
		 document.forms[0].submit();	

	});
	
		function doSubmitNew(form) {
			document.forms["form"].action = "${newUrl}";
			document.forms["form"].submit();
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
		    "Đồng ý": function() {
		    	document.forms["form"].action = "${newUrl}";
				document.forms["form"].submit();
		    },
			"Hủy": function() {
				$(this).dialog("close");
		    }
		   }
		});
		
		function doSubmitNew1(form) {
			 $("#dialog-confirm").dialog('option', 'title', 'Tình trạng');
			 $("#dialog-confirm").html("Có công việc đang chờ xử lý? Bạn có muốn tiếp tục?");
			 $("#dialog-confirm").dialog( 'open' );			
		}
	</script>
</form:form>



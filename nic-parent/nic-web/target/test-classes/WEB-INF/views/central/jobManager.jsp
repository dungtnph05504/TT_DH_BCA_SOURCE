<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<c:url var="url" value="/servlet/central/showJob" />
<c:url var="urlUpdate" value="/servlet/central/updateJobPriority" />
<style>

.style-width-100 {
	width: 100%;
}
#ajax-load-qa {
	background: rgba(255, 255, 255, .6)
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
.toggle {
	border-radius: 20px;
}
</style>
<div class="content-item-title">
            <div class="oplog-title__txt">Quản lý hàng đợi công việc</div>
        </div>
<div class="content-item-main">
<form:form modelAttribute="formData" name="formData" autocomplete="off"> 
  
   <div class="content-item-information">
			<div class="col-sm-12">
				<fieldset class="scheduler-border">
					<legend>Điều kiện tìm kiếm</legend>
					<div class="form-group" style="margin-top: 15px;">
						<div class="col-sm-4">
							<label class="col-sm-4 control-label text-right">Họ tên:</label>
							<div class="col-sm-8">
								<form:input path="name"  class="style-width-100" id="name" placeholder="" />
							</div>
						</div>
						<div class="col-sm-4">
							<label class="col-sm-4 control-label text-right">Ngày sinh:</label>
							<div class="col-sm-8">
								<form:input path="dateOfBirth"  class="style-width-100" id="dateOfBirth" placeholder="" type="date"/>
							</div>
						</div>
						<div class="col-sm-4">
							<label class="col-sm-4 control-label text-right">Giới tính:</label>
							<div class="col-sm-8">
								<form:select id="gender" path="gender" class="style-width-100">
									<form:option value="">-- Tất cả --</form:option>
									<form:option value="M">Nam</form:option>
									<form:option value="F">Nữ</form:option>
								</form:select>
							</div>
						</div>
					</div>
					<div class="form-group" style="margin-top : 10px;">
						<div class="col-sm-4">
							<label class="col-sm-4 control-label text-right">Số hồ sơ:</label>
							<div class="col-sm-8">
								<form:input path="jobId"  class="style-width-100" id="idTran" placeholder="" />
							</div>
						</div>
						<div class="col-sm-4">
							<label class="col-sm-4 control-label text-right">Số CMND/CCCD:</label>
							<div class="col-sm-8">
								<form:input path="nin"  class="style-width-100" id="idNumber" placeholder="" />
							</div>
						</div>
						<div class="col-sm-4">
							<button type="button" style="float: right;margin-right: 15px;" onclick="doApplyFilter();" class="btn_small btn-success">
					        	<span class="glyphicon glyphicon-search"></span> Tìm kiếm
					   		</button>
						</div>
					</div>
				</fieldset>
			</div>
			<div>
			<div class="col-sm-12" style="margin-bottom: 50px;">
					<fieldset class="scheduler-border">
							<legend class="scheduler-border">Danh sách hồ sơ chờ xử lý</legend>
							<div style="height: 400px;overflow: auto;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="width : 40px;">STT
								      </th>
								      <th class="th-sm" style="width : 200px;">Số hồ sơ
								      </th>
								      <th class="th-sm" style="width : 200px;">Họ và tên
								      </th>
								      <th class="th-sm" style="width : 200px;">Ngày sinh
								      </th>
								      <th class="th-sm" style="width : 150px;">Giới tính
								      </th>
								      <th class="th-sm" style="width : 200px;">Số CMND/CCCD
								      </th>
								      <th class="th-sm" style="width : 100px;">Điểm ưu tiên
								      </th>
								      <th class="th-sm" style="width: 150px;">Thay đổi điểm ưu tiên
								      </th>
								     
								    </tr>
								  </thead>
								   <tbody>
								  <c:if test="${not empty listJob}">
									 
									    <c:forEach items="${listJob}" var="item">
										    <tr>										      
										     	<td align="center">${item.stt}</td>
										      	<td align="center">${item.jobId}</td>
										      	<td align="center">${item.name}</td>
										      	<td align="center">${item.dateOfBirth}</td>
										      	<td align="center">${item.gender}</td>
										      	<td align="center">${item.nin}</td>
										      	<td align="center" id="pri_${item.jobId}">${item.priority}</td>
										      	<td align="center">
										      		<a href="#" onclick="resetPriority('${item.jobId}', '${item.priority}');"><i class="glyphicon glyphicon-edit"></i>Thay đổi</a>
										      	</td>
										    </tr>
									    </c:forEach>
									 
								  </c:if>
								   </tbody>
								    <c:if test="${empty listJob}">
								  
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
                                          <c:if test="${not empty listJob}">
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
		</div>
		</div>
	<div id="ajax-load-qa"></div>
	<!-- Message lưu hồ sơ -->
	<div class="modal fade" id="checkSiteId" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
	  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 400px;">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">NHẬP GIÁ TRỊ ĐIỂM ƯU TIÊN</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
	        </button>
	      </div>
	      <div class="modal-body" id="checkSiteForm"; >
	      		
	      </div>							      
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" onclick="" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> ĐÓNG</span>
	       		</button>
	       		<button type="button" onclick="savePriority();" class="btn btn-success" data-dismiss="modal" aria-label="Close" style="float: right;margin-right: 10px;">
	       			<span class="glyphicon glyphicon-ok btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> LƯU</span>
	       		</button>
	       		
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
				visiblePages: pageSize,
				startPage: currentPage,
				next: '<span class=\"glyphicon glyphicon-forward fix-paging\"></span>',
				prev: '<span class=\"glyphicon glyphicon-backward fix-paging\"></span>',
				last: '<span class=\"glyphicon glyphicon-step-forward fix-paging\"></span>',
				first: '<span class=\"glyphicon glyphicon-step-backward fix-paging\"></span>',
				onPageClick: function (event, page) {
					if (currentPage != page) {
						$('#pageNo').val(page);
						document.forms["formData"].action = '${loadFormQueueUrl}';  
						document.forms["formData"].submit();  
					}
				}
			});
		
	</script>
	<script type="text/javascript">
		
	    function resetPriority(tranId, priority){
			$('#checkSiteForm').html('<div class="form-group"><div class="col-md-10"><label class="col-md-5" style="margin-top: 5px;">Số hồ sơ:</label><div class="col-md-5">' 
					+ '<input disabled="true" class="style-width-50" id="tranIdRs" placeholder="" value =' + tranId + ' /></div></div></div>' 
					+ '<div class="form-group"><div class="col-md-10"><label class="col-md-5" style="margin-top: 5px;">Điểm ưu tiên:</label><div class="col-md-5">' 
					+ '<input class="style-width-50" id="priorityRs" type="number" min="0" value =' + priority + ' /></div></div></div>');
			$('#checkSiteId').modal();
			$('#ajax-load-qa').css('display', 'none');	
	    }
	    
	    function savePriority(){
	    	var tranId = $('#tranIdRs').val();
	    	var priority = $('#priorityRs').val();
	     	 var url = '${urlUpdate}?jobId=' + tranId + '&&priority=' + priority;
		     $('#ajax-load-qa').show();
		   	 $.ajax({
		   		url : url,
		   		type: "POST",
		   		success : function(data) {
		   			$('#ajax-load-qa').hide();
		   			if(data == '1'){
		   				$('#pri_' + tranId).html(priority);
		   				$.notify('Lưu thay đổi thành công.', {
							globalPosition: 'bottom left',
							className: 'success',
						});
		   				document.forms["formData"].action = '${url}';
		   				document.forms["formData"].submit();
		   			}
		   			if(data == '0'){
		   				$.notify('Lưu thay đổi không thành công.', {
							globalPosition: 'bottom left',
							className: 'warn',
						});
		   			}
		   		},
		   		error : function(e){
		   			$('#ajax-load-qa').hide();
		   		}
		   	 });
	    }
	    
	    function doApplyFilter() {
	    	/* if((($('#name').val() == null || $('#name').val() == '') && ($('#gender').val() == null || $('#gender').val() == '') && ($('#dateOfBirth').val() == null || $('#dateOfBirth').val() == '')) && ($('#idTran').val() ==null || $('#idTran').val() == '') && ($('#idNumber').val() ==null || $('#idNumber').val() == '')){
					$.notify('Xin hãy nhập thông tin tìm kiếm.', {
						globalPosition: 'bottom left',
						className: 'warn',
					});
			    	 return;
			} */
			if((($('#name').val() != null || $('#name').val() != '') || ($('#gender').val() != null || $('#gender').val() != '') || ($('#dateOfBirth').val() != null || $('#dateOfBirth').val() != '')) && ($('#idTran').val() ==null || $('#idTran').val() == '') && ($('#idNumber').val() ==null || $('#idNumber').val() == '')){
				if(($('#name').val() == null || $('#name').val() == '') || ($('#gender').val() == null || $('#gender').val() == '') || ($('#dateOfBirth').val() == null || $('#dateOfBirth').val() == '')){
					$.notify('Cần nhập đủ thông tin họ tên, ngày sinh, giới tính.', {
						globalPosition: 'bottom left',
						className: 'warn',
					});
			    	 return;
				}
			}
			document.forms["formData"].action = '${url}';
			document.forms["formData"].submit();
		}
		
	</script>
	
</form:form>
</div>
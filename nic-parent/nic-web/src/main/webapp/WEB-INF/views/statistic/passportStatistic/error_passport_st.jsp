<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<c:url var="url" value="/servlet/central/reportPackView" />
<c:url var="urlPrint" value="/servlet/central/reportPackForm" />
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
<script type="text/javascript">   
    function searchTran() {
    	if(($('#fromDate').val() == null || $('#fromDate').val() == '') && ($('#toDate').val() == null || $('#toDate').val() == '') && ($('#regSiteCode').val() == null || $('#regSiteCode').val() == '')){
				$.notify('Xin hãy nhập thông tin tìm kiếm.', {
					globalPosition: 'bottom left',
					className: 'warn',
				});
		    	 return;
		}
		document.forms["formData"].action = '${url}';
		document.forms["formData"].submit();
	}
    
    function printReport() {
		var totalRc = '${totalRecord}';
		
		var value = {};
		value['fromDate'] = $('#fromDate').val();
		value['toDate'] = $('#toDate').val();
		value['regSiteCode'] = $('#regSiteCode').val();
		value['printSiteCode'] = '';
		$('#printReportForm').html("");
		if(totalRc == null || totalRc == '0' || totalRc == ''){
			$.notify('Không có thông tin để in báo cáo.', {
				globalPosition: 'bottom left',
				className: 'warn',
			});
	    	 return;
		}
		var url = '${urlPrint}';
		$('#ajax-load-qa').show();
		$('#ajax-load-qa').css('display', 'block');
	   	$.ajax({
	   		url : url,
	   		type: "POST",
	   		cache: false,
	   		contentType : 'application/json',
	   		data : JSON.stringify(value),
	   		success : function(data) {
	   			$('#ajax-load-qa').hide();
	   			if(data != ''){
		   			$('#printReportForm').html(data);
		   			$('#printReportId').modal();
	   			}
	   			$('#ajax-load-qa').css('display', 'none');		
				$('#btnPrintReport').attr('disabled', true);
	   		},
	   		error : function(e){
	   			$('#ajax-load-qa').hide();
	   		}
	   	});
    }
		
	</script>
<div class="content-item-title">
            <div class="oplog-title__txt">${title}</div>
        </div>
<div class="content-item-main">
<form:form modelAttribute="formData" name="formData" autocomplete="off" id="formData"> 
  
   <div class="content-item-information">
			<div class="col-sm-12">
				<fieldset class="scheduler-border">
					<legend>Điều kiện tìm kiếm</legend>
					<div class="form-group" style="margin-top: 10px;">
						
						<div class="col-sm-3">
							<label class="col-sm-4 control-label text-right" style="margin-top: 4px">Từ ngày:</label>
							<div class="col-sm-8">
								<form:input path="fromDate"  class="style-width-50" id="fromDate" placeholder="" type="date"/>
							</div>
						</div>
						<div class="col-sm-3">
							<label class="col-sm-4 control-label text-right" style="margin-top: 4px">Đến ngày:</label>
							<div class="col-sm-8">
								<form:input path="toDate"  class="style-width-50" id="toDate" placeholder="" type="date"/>
							</div>
						</div>
						<div class="col-sm-3">
							<label class="col-sm-5 control-label" style="margin-top: 4px; padding: 0px; text-align: center;">${fieldName}:</label>
							<div class="col-sm-7">
								<form:select id="regSiteCode" path="regSiteCode" class="style-width-100">
									<form:option value="">-- Tất cả --</form:option>
									<c:forEach items="${siteList}" var="itemSite">
										<form:option value="${itemSite.siteId}">${itemSite.siteName}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>
						
						<div class="col-sm-3">
							<button type="button" style="float: right;margin-right: 15px; width: 100px;" onclick="printReport();" name="approve" class="btn btn-success">
									<span class="glyphicon glyphicon-print"></span> In báo cáo
							</button>
							<button type="button" style="float: right;margin-right: 15px;width: 100px;" onclick="searchTran();" class="btn btn-success">
					        	<span class="glyphicon glyphicon-search"></span> Tìm kiếm
					   		</button>
						</div>
					</div>
				</fieldset>
			</div>
			<div>
			<div class="col-sm-12" style="margin-bottom: 50px;">
					<fieldset class="scheduler-border">
							<legend class="scheduler-border">Danh sách hồ sơ</legend>
							<div style="height: 430px;overflow: auto;">
								<table id="dtBasicExample" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="width : 45px;">STT
								      </th>
								      <th class="th-sm" style="width : 145px;">Số hồ sơ
								      </th>
								      <th class="th-sm" style="width : 100px;">Số hộ chiếu
								      </th>
								      <th class="th-sm" style="width : 170px;">Họ và tên
								      </th>
								      <th class="th-sm" style="width : 90px;">Ngày sinh
								      </th>
								      <th class="th-sm" style="width : 80px;">Giới tính
								      </th>
								      <th class="th-sm" style="width : 125px;">Số CMND/CCCD
								      </th>
								      <th class="th-sm" style="width : 160px;">Trạng thái hộ chiếu
								      </th>
								      <c:if test="${empty regSiteCode}">
								      <th class="th-sm" style="width: 190px;">Cơ quan đăng ký
								      </th>
								      </c:if>
								      <c:if test="${empty printSiteCode}">
								      <th class="th-sm" style="width: 260px;">Trung tâm in
								      </th>
								      </c:if>
								      <th class="th-sm" style="width: 85px;">Ngày in
								      </th>
								    </tr>
								  </thead>
								   <tbody>
								  
									 
									    <c:forEach items="${listTransaction}" var="item">
										    <tr>										      
										     	<td align="center">${item.stt}</td>
										      	<td align="center">${item.transactionId}</td>
										      	<td align="center">${item.passportNo}</td>
										      	<td align="left">${item.name}</td>
										      	<td align="center">${item.dateOfBirth}</td>
										      	<td align="center">${item.gender}</td>
										      	<td align="center">${item.nin}</td>
										      	<td align="left">${item.documentStatus}</td>
										      	<c:if test="${empty regSiteCode}">
										      	<td align="center">${item.regSiteName}</td>
										      	</c:if>
										      	<c:if test="${empty printSiteCode}">
										      	<td align="center">${item.printSiteName}</td>
										      	</c:if>
										      	<td align="center">${item.printDate}</td>
										    </tr>
									    </c:forEach>
								   </tbody>
								</table>
								
						      </div>
						      
						      <table class="table e-grid-table e-border">
                                <tfoot>
                                    <tr>
                                        <th>
                                          <c:if test="${not empty listTransaction}">
										<div class="e-page-rigth">
											<ul style="float: right;" class="pagination" id="pagination"></ul>
										</div>
										<input type="hidden" name="pageNo" id="pageNo" /> 
										<div class="e-page-left" style="font-weight: normal;">
											Hiển thị ${startIndex} đến ${endIndex} của ${totalRecord} kết quả
										</div>
											</c:if>
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
	<div class="modal fade" id="printReportId" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
	  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1400px;">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">IN BÁO CÁO TIẾN TRÌNH ĐÓNG GÓI</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
	        </button>
	      </div>
	      <div class="modal-body" id="printReportForm"; >
	      		
	      </div>							      
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" onclick="" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot">ĐÓNG</span>
	       		</button>
	       		<button type="button" onclick="printRp();" class="btn btn-success" data-dismiss="modal" aria-label="Close" style="float: right;margin-right: 10px;">
	       			<span class="glyphicon glyphicon-ok btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> IN</span>
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
						document.forms["formData"].action = '${url}';  
						document.forms["formData"].submit();  
					}
				}
			});
	</script>
	
</form:form>
</div>
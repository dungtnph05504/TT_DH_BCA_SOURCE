<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<c:url var="thongtinchitiet" value="/servlet/central/danhsachdphoiin" />
<c:url var="getDanhsachHsUrl" value="/servlet/central/getDanhsachHs" />
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
.cla-font {
    font-weight: 700;
}

</style>
<form:form autocomplete="off" modelAttribute="formData" name="formData" > 
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
      <div class="new-heading-2" style="margin-bottom: 0px;">DANH SÁCH ĐIỀU PHỐI IN HỘ CHIẾU</div>
      <div style="clear: both"></div>
			
						<div>
							<div>
							<div style="margin: 2px">
							<fieldset>
							<legend>Điều kiện tìm kiếm</legend>
							<div class="col-md-11">
								<div class="col-md-4">
									<div class="col-md-4 cls-mg-bot">
										<label class="control-label">Số danh sách B</label>
									</div>
									<div class="col-md-8 cls-mg-bot">
										<form:input path="packageCode" type="text" style="width: 100%;"/>
									</div>
								</div>
								<div class="col-md-4">
									<div class="col-md-4 cls-mg-bot">
										<label class="control-label">Trung tâm XNC</label>
									</div>
									<div class="col-md-8 cls-mg-bot">
										<form:select path="regSiteCode" style="width: 100%;">
											<c:forEach items="${listSiteResXL}" var="item">
												<form:option value="${item.key}">${item.value}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="col-md-4">
									<div class="col-md-4 cls-mg-bot">
										<label class="control-label">Ngày phê duyệt</label>
									</div>
									<div class="col-md-8 cls-mg-bot">
										<form:input path="createDate" type="text" style="width: 100%;"/>
									</div>
								</div>
								</div>								
								<div class="col-md-1">
									<div style="margin: 2px;margin-bottom: 10px;margin-top: 10px;">
										<div>
											<button type="button" onclick="doApplyFilter();" class="btn_small btn-success">
										        <span class="glyphicon glyphicon-search"></span> Tìm kiếm
										    </button>	
											
										</div>
									</div>
								</div>								
								</fieldset>
							</div>
						</div>
			</div>

	  		<br />
			<br />
					<fieldset>
							<legend>Danh sách đã phê duyệt</legend>
							<div style="height: 400px;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="width: 40px;">
										 STT
								      </th>	
								      <th class="th-sm">Số danh sách B
								
								      </th>
								      <th class="th-sm">Ngày đề xuất
								
								      </th>
								      <th class="th-sm">Ngày phê duyệt
								
								      </th>
								      <th class="th-sm">Người duyệt
								
								      </th>
								    </tr>
								  </thead>
									  <tbody>
									  <c:if test="${not empty dsXuLyA}">
									  	 <c:forEach items="${dsXuLyA}" var="item">
										    <tr>
										      <td class="align-central">
										      	  ${item.stt}
											  </td>	
										      <td >${item.packageId}</td>
										      <td align="center">${item.dateIssuce}</td>
										      <td align="center">${item.dateEprity}</td>
										      <td >${item.leaderId}</td>
										    </tr>
									    </c:forEach>
									  </c:if>
									  </tbody>
									  <c:if test="${empty dsXuLyA}">
								  
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
                                        
                                          <c:if test="${not empty dsXuLyA}">
										
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

						<!-- Model phí bổ sung -->
						<div class="modal fade" id="chiTietHS" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
						  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1200px;height: 500px;">
						    <div class="modal-content">
						      	<div class="modal-header">
						        	<h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">THÔNG TIN CHI TIẾT DANH SÁCH HỒ SƠ</h5>
						        	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
						        	</button>
						      	</div>		
						     	<div class="modal-body" style="height: 350px;overflow: auto;padding:0"  id="danhsachHS">
						      	</div>
						    </div>
						  </div>
						</div>	
						<!-- -------------------------------------------------------------------------- -->
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
						document.forms["formData"].action = '${thongtinchitiet}';  
						document.forms["formData"].submit();  
					}
				}
			});
		function doSubmitSave(form) {
			form.action = '<c:url value="/servlet/central/search" />';
			form.submit();
		}
	</script>
	<script type="text/javascript">
		
	
	
	
		
		var pattern = /^([0-2][0-9]|(3)[0-1])(\/)(((0)[0-9])|((1)[0-2]))(\/)\d{4}$/;
		function doApplyFilter() {
			var ngayDuyet = $('#createDate').val();
			if(ngayDuyet.trim() != '' && !ngayDuyet.trim().match(pattern)){
				$.notify('Định dạng ngày duyệt không chính xác.', {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return;
			}
			document.forms["formData"].action = '${thongtinchitiet}';
			document.forms["formData"].submit();
		}
	


		

		
		
		
		
		$('#createDate').mask("00/00/0000", { placeholder: "__/__/____" });
		$("#createDate").datepicker({
			//showOn : "button",
			//buttonImage : "",
			//buttonImageOnly : true,
			changeMonth : true,
			changeYear : true,
			showSecond : true,
			controlType : 'select',
			dateFormat : 'dd/mm/yy',
			yearRange : "-100:+10"
		}).keyup(function(e) {
			
		});
		
		
		
	</script>
	</div>
	</div>
	</div>
</form:form>



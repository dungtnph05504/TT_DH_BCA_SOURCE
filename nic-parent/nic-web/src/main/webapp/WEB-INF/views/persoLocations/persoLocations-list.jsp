<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="persoLocationsUrl" value="/servlet/persoLocation/persoLocations" />
<c:url var="delLocationsUrl" value="/servlet/persoLocation/delLocations" />
<style>
.cls-mg-bot {
	margin-top: 10px;
}
</style>
<c:if test="${not empty requestScope.Errors}">
	<div class="border_error">
		<div class="error_left">
			<img align='left'
				src="<c:url value="/resources/images/error_new.jpg" />" width="30"
				height="30" />
		</div>


		<div class="errors">
			<table width="600" height="10" border="0" cellpadding="5">
				<tr>
					<td
						style="padding-left: 5px; font-weight: bold; vertical-align: top;">
						<c:forEach items="${requestScope.Errors}" var="errorMessage">
									${errorMessage}
							</c:forEach>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<br />
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
					<td
						style="padding-left: 5px; font-weight: bold; vertical-align: top;">
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
<form:form modelAttribute="formData" name="formData" autocomplete="off">
	<div class="form-desing">
		<div>
			<div>
				<div class="row">
					<div class="ov_hidden">
						<div class="new-heading-2">QUẢN LÝ TRUNG TÂM CÁ THỂ HÓA</div>

						<div style="clear: both"></div>


						<div style="margin: 2px">
							<div style="vertical-align: text-top; display: inline-block; width: 100%; float: left">
								<fieldset>
									<legend>Điều kiện tìm kiếm</legend>
								
									<div style="margin: 2px">
										<div class="col-md-4">
											
											<label class="col-md-5 control-label text-right">Mã trung tâm</label>
											<div class="col-md-7">
												<form:input path="searchTransactionId" type="text" style="width: 100%;" />
											</div>
										</div>
										<div class="col-md-4">
											<label class="col-md-5 control-label text-right">Điểm phát hành</label>
											<div class="col-md-7">
												<form:input path="listCode" type="text" style="width: 100%;" />
											</div>
										</div>
										<div class="col-md-4">
											<div class="col-md-12">
												<div style="margin-bottom: 10px;">
													<button type="button" onclick="doApplyFilter();" class="btn_small btn-success">
												        <span class="glyphicon glyphicon-search"></span> Tìm kiếm
												    </button>	
												</div>
											</div>
										</div>
									</div>
								</fieldset>
								<div style="clear: both"></div>	
								
							</div>
						</div>

						<div style="clear: both"></div>

						<br />
							<fieldset>
								<legend>Danh sách trung tâm cá thể hóa</legend>
							
							<div style="height: 400px;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      
								      <th class="th-sm">Mã trung tâm
								
								      </th>
								      <th class="th-sm">Tên trung tâm
								
								      </th>
								      <th class="th-sm">Địa chỉ
								
								      </th>
								      <th class="th-sm">Mô tả
								
								      </th>
								      <th class="th-sm">Điểm phát hành
								
								      </th>
								      <th class="th-sm">Ngày tạo
								
								      </th>
								      <th class="th-sm">Thao tác</th>
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>
									      <td>${item.code}</td>
									      <td>${item.name}</td>
									      <td>${item.address}</td>
									      <td>${item.description}</td>
									      <td>${item.issuePlace}</td>
									      <td align="center">${item.createDate}</td>
									      <td align="center">
									      	<c:url var="editLocationUrl" value="/servlet/persoLocation/editLocation/${item.id}" />
								      		<a href="${editLocationUrl}">
								      			<i class="glyphicon glyphicon-pencil"></i> Sửa
								      		</a>
								      		<a href="javascript:del('${item.id}');">
								      			<i class="glyphicon glyphicon-trash"></i> Xóa
								      		</a>
									      	<c:url var="jobUrl" value="/servlet/persoLocation/detailPersoLocations/${item.idPerso}" />
									      	<a href="${jobUrl}"><i class="glyphicon glyphicon-eye-open"></i>Xem</a>
									      </td>
									    </tr>
								    </c:forEach>
								  </tbody>
								  <c:if test="${empty jobList}">
								  
								   <tbody class="e-not-found ng-scope"><tr>
								  <td colspan="7" style="height: 362px">
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
						  <!-- Message lưu hồ sơ -->
							<div class="modal fade" id="messageLHS" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
							  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 300px;">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">THÔNG BÁO</h5>
							        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
							        </button>
							      </div>
							      <div class="modal-body" id="idMessage">
							      		<div>
							      			<i class="glyphicon glyphicon-question-sign" style="font-size: 2em;"></i>
							      			<p style="display: inline-block;position: absolute;margin-left: 10px;margin-top: 5px;">Bạn có muốn xóa trung tâm này?</p>
							      		</div>
							      </div>							      
							      <div class="modal-footer" style="padding: 5px;">
							       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
							       			<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> KHÔNG</span>
							       		</button>
							       		<button type="button" onclick="deleteForm();" class="btn btn-success" data-dismiss="modal" aria-label="Close" style="float: right;margin-right: 10px;">
							       			<span class="glyphicon glyphicon-ok btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> CÓ</span>
							       		</button>
							       		
							      </div>
							    </div>
							  </div>
							</div>	
							<!-- ----------------------------------------------------------------------------> 



						<%
							/* ******************************************************************************************************************** */
						%>
						<%
							/* ************************************* actions for selected jobs - start ******************************************** */
						%>
						<%
							/* ******************************************************************************************************************** */
						%>

					</div>
				</div>
			</div>
		</div>
		<div style="display: flex;flex: 0 auto;justify-content: center;">
			<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
				<div style="margin: 10px"> 
					<button type="button" class="btn btn-success" id="add_button">
						<span class="glyphicon glyphicon-plus"></span> Thêm mới trung tâm
					</button>
				</div>
			</div>	
		</div>
</form:form>


<%
	/* ******************************************************************************************************************** */
%>
<%
	/* ************************************************ apply filter - start ********************************************** */
%>
<%
	/* ******************************************************************************************************************** */
%>

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
					document.forms["formData"].action = '${persoLocationsUrl}';  
					document.forms["formData"].submit();  
				}
			}
		});	
	function doApplyFilter() {
		document.forms["formData"].action = '<c:url value="/servlet/persoLocation/applyFilter" />';
		document.forms["formData"].submit();
	}
	
	$(function() {
		$('#add_button').click(function(){
			document.forms["formData"].action = '<c:url value="/servlet/persoLocation/addLocation" />';
			document.forms["formData"].submit();
		});
	});
	
	function del(id) {
		$('#messageLHS').modal();
		document.forms["formData"].action = '${delLocationsUrl}/' + id;
	}
	
	function deleteForm(){
		document.forms["formData"].submit();
	}
	
	$(function() {
		var mes = '${mes}';
		if(mes == '1'){
			$.notify('Xóa thành công trung tâm cá thể hóa.', {
				globalPosition: 'bottom left',
				className: 'success',
			});
		}
		if(mes == '0'){
			$.notify('Xóa không thành công trung tâm cá thể hóa.', {
				globalPosition: 'bottom left',
				className: 'warn',
			});
		}
	});
</script>

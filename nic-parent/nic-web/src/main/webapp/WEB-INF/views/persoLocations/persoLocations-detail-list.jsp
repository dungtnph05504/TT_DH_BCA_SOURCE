<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<c:url var="siteRepoUrl" value="/servlet/siteMgmt/getSiteRepo" />
<c:url var="addSiteRepoUrl" value="/servlet/persoLocation/addSiteLocation" />
<c:url var="delSiteRepoUrl" value="/servlet/persoLocation/delSiteLocation" />
<c:url var="delSiteGroupUrl" value="/servlet/siteMgmt/delSiteGroup" />
<c:url var="searchRepoUrl" value="/servlet/siteMgmt/searchRepo" />
<c:url var="getRepoUrl" value="/servlet/persoLocation/detailPersoLocations" />
<c:url var="url" value="/servlet/persoLocation/persoLocations" />
<style>
</style>
<form:form modelAttribute="formData" id="formData" name="formData">
	<!-- <div id="main"> -->
		<div class="form-desing">
		<div>
        	<div class="row">
        		<div class="ov_hidden">
			<div class="new-heading-2">CHI TIẾT TRUNG TÂM CÁ THỂ HÓA</div>  						
			<fieldset>
				<legend>Danh sách chi tiết cá thể hóa</legend>
			
							<div style="height: 400px;overflow: auto;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm">Nhóm TT Cá thể hóa
								
								      </th>	
								      <th class="th-sm">Trung tâm tiếp nhận
								
								      </th>
								      <th class="th-sm">Thao tác
								
								      </th>								 				     
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${JList}" var="item">
									    <tr>							    
									      <td>${item.sitePerso}</td>
									      <td>${item.siteId}</td>		
									      <td align="center">
									      		<c:url var="editSiteLocationUrl" value="/servlet/persoLocation/editSiteLocation/${idPerso}/${item.id}" />
									      		<a href="${editSiteLocationUrl}">
									      			<i class="glyphicon glyphicon-pencil"></i> Sửa
									      		</a>
									      		<a href="javascript:del('${item.id}');">
									      			<i class="glyphicon glyphicon-trash"></i> Xóa
									      		</a>
									      </td>							     
									    </tr>
								    </c:forEach>
								  </tbody>
								  <c:if test="${empty JList}">
								  
								   <tbody class="e-not-found ng-scope"><tr>
								  <td colspan="3" style="height: 362px">
								  <div class="e-not-found-center"><div class="e-not-found-title">Không tìm thấy kết quả</div><div class="e-not-found-title-child">Hãy thử tìm kiếm hoặc sử dụng bộ lọc khác</div></div>
								  </td></tr></tbody>
								  
								
								</c:if>
								</table>
		 
								
						      </div>
						     <table class="table e-grid-table e-border">
	                                <tfoot>
	                                    <tr>
	                                        <th>
	                                        
	                                          <c:if test="${not empty JList}">
											
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

		<div style="display: flex;flex: 0 auto;justify-content: center;">
		<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
			<div style="margin: 10px"> 			
				<button type="button" class="btn btn-success" onclick="cancelPag();" id="can_button">
					<span class="glyphicon glyphicon-remove"></span> Quay lại
				</button>
				<button type="button" class="btn btn-success" id="add_button">
					<span class="glyphicon glyphicon-plus-sign"></span> Thêm mới
				</button>
			</div>
		</div>
		</div>
		</div>
		</div>
		</div>
	<!-- </div> -->
	
</form:form>

<script> 

$(function() {

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
					document.forms["formData"].action = '${getRepoUrl}/${idPerso}';  
					document.forms["formData"].submit();  
				}
			}
		});
	
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
  
	$("#add_button").click(function() {
		document.forms["formData"].action = '${addSiteRepoUrl}/${idPerso}';
		document.forms["formData"].submit();

	});

	function del(id){		
	    $('#messageLHS').modal();
	    document.forms["formData"].action = '${delSiteRepoUrl}/${idPerso}/'+ id;
	}
	
	
	function deleteForm(){
		document.forms["formData"].submit();
	}

	function cancelPag(){	
	    document.forms["formData"].action = '${url}';
		document.forms["formData"].submit();
	}



	
</script>

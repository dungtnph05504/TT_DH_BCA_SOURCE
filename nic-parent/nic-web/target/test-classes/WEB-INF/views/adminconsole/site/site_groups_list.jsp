<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:url var="siteRepoUrl" value="/servlet/siteMgmt/getSiteRepo" />
<c:url var="addSiteGroupUrl" value="/servlet/siteMgmt/addSiteGroup" />
<c:url var="editSiteGroupUrl" value="/servlet/siteMgmt/editSiteGroup" />
<c:url var="delSiteGroupUrl" value="/servlet/siteMgmt/delSiteGroup" />
<c:url var="url" value="/servlet/siteMgmt/view" />

<%-- <c:url var="ajaxUrl" value="/servlet/siteMgmt/ajaxView" /> --%>
<style>

</style>
<form:form modelAttribute="siteGroupForm" id="siteGroupForm" method="POST">
	<!-- <div id="main"> -->
		<div class="form-desing">
		<div>
        	<div class="row">
        		<div class="ov_hidden">
			<div class="new-heading-2">QUẢN LÝ NHÓM TRUNG TÂM XỬ LÝ</div>  
				
			<fieldset>
				<legend>Danh sách các trung tâm xử lý</legend>
			
							<div style="height: 400px; overflow: auto;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm">Mã nhóm trung tâm
								
								      </th>	
								      <th class="th-sm">Tên nhóm trung tâm
								
								      </th>
								      <th class="th-sm">Cấp độ trung tâm
								
								      </th>
								       <th class="th-sm">Vùng/miền
								
								      </th>
								      <th class="th-sm">Thao tác
								
								      </th>
								      
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>							    
									      <td align="center"><c:url var="siteRepoUrl1" value="/servlet/siteMgmt/getSiteRepo/${item.groupId}" />
												<a style="color: blue;" href="${siteRepoUrl1}"> <c:out value="${item.groupId}" /></a></td>
									      <td>${item.groupName}</td>
									      <td>${item.levelNic_des}</td>
									      <td>${item.typeArea}</td>
									      <td align="center">
									      		<c:url var="editSiteGroupUrl1" value="/servlet/siteMgmt/editSiteGroup/${item.groupId}" />
									      		<a href="${editSiteGroupUrl1}" title="Mã nhóm: ${item.groupId}">
									      			<i class="glyphicon glyphicon-pencil"></i> Sửa
									      		</a>
									      		<a href="javascript:del('${item.groupId}');" title="Mã nhóm: ${item.groupId}">
									      			<i class="glyphicon glyphicon-trash"></i> Xóa
									      		</a>
									      </td>								     
									    </tr>
								    </c:forEach>
								  </tbody>
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
		<div style="display: flex;flex: 0 auto;justify-content: center;">
		<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
			<div style="margin: 10px"> 
				<button type="button" class="btn btn-success" id="add_button">
					<span class="glyphicon glyphicon-plus"></span> Thêm mới trung tâm
				</button>
			</div>
		</div>	
		</div>
		</div>
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
	<!--  </div> -->
</form:form>
<div id="dialog-confirm"></div>
<script>
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
					document.forms["siteGroupForm"].action = '${url}';  
					document.forms["siteGroupForm"].submit();  
				}
			}
		});	

	$("#add_button").click(function() {
		document.forms["siteGroupForm"].action = '${addSiteGroupUrl}';
		document.forms["siteGroupForm"].submit();

	});
	
	function deleteForm(){
		document.forms["siteGroupForm"].submit();
	}

	function del(id) {
		//$("#dialog-confirm").dialog('option', 'title', 'Xóa nhóm');
		//$("#dialog-confirm").html(
				//"Bạn có chắc chắn muốn xóa nhóm " + id + "?");
		//$("#dialog-confirm").dialog('open');
		$('#messageLHS').modal();
		document.forms["siteGroupForm"].action = '${delSiteGroupUrl}/' + id;
	}
</script>


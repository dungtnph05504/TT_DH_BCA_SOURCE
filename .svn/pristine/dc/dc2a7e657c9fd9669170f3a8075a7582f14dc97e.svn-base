<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="deleteInvUrl" value="/servlet/persoLocation/deleteInv" />
<c:url var="inventoryUrl" value="/servlet/persoLocation/inventory" />
<c:url var="addInvUrl" value="/servlet/persoLocation/addInventory" />
<c:url var="editInvUrl" value="/servlet/persoLocation/editInventory" />
<c:url var="saveChangeInvUrl" value="/servlet/persoLocation/saveChangeInv" />
<c:url var="reloadInventoryUrl" value="/servlet/persoLocation/reloadInventory" />
<style>
.cls-mg-bot {
	margin-top: 10px;
}
.cls-wth-100 {
	width: 100%;
}
#ajax-load-qa {
	background: rgba(255, 255, 255, .5)
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
<div class="content-item-title">
    <div class="oplog-title__txt">Quản lý kho</div>
</div>
<!--Content start-->
<form:form modelAttribute="formData" name="formData" autocomplete="off">
	<div class="form-desing">
		<div class="row">
			<div class="ov_hidden">
				

				<div style="clear: both"></div>


				
				<div style="clear: both"></div>

				<br />
					<fieldset>
						<legend>Danh sách kho</legend>
					
					<div style="height: 400px;">
				      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
						  <thead>
						    <tr>			      
						      
						      <th class="th-sm">Mã kho
						
						      </th>
						      <th class="th-sm">Tên kho
						
						      </th>
						      <th class="th-sm">Mô tả
						
						      </th>
						      <th class="th-sm">Trạng thái
						
						      </th>
						      <th class="th-sm">Địa chỉ
						
						      </th>						     
						      <th class="th-sm">Thao tác</th>
						    </tr>
						  </thead>
						  <tbody>
						    <c:forEach items="${invenList}" var="_item">
							    <tr>							    
							      <td>${_item.code}</td>
							      <td>${_item.name}</td>
							      <td>${_item.description}</td>
							      <td>
							      	<c:if test="${_item.active == 'Y'}">
							      		Hoạt động
							      	</c:if>
							      	<c:if test="${_item.active == 'N'}">
							      		Không hoạt động
							      	</c:if>
							      </td>							    
							      <td>${_item.address}</td>
							      <td align="center">
							      		<a onclick="editInv('${_item.id}')" href="#">
							      			<i class="glyphicon glyphicon-pencil"></i> Sửa
							      		</a>
							      		<a onclick="delInv('${_item.id}')" href="#">
							      			<i class="glyphicon glyphicon-trash"></i> Xóa
							      		</a>
							      </td>
							    </tr>
						    </c:forEach>
						  </tbody>
						  <c:if test="${empty invenList}">
						  
						   <tbody class="e-not-found ng-scope"><tr>
						  <td colspan="9" style="height: 362px">
						  <div class="e-not-found-center"><div class="e-not-found-title">Không tìm thấy kết quả</div><div class="e-not-found-title-child">Hãy thử tìm kiếm hoặc sử dụng bộ lọc khác</div></div>
						  </td></tr></tbody>
						  
						
						</c:if>
						</table>
						
				      </div>
					      <table class="table e-grid-table e-border">
                               <tfoot>
                                   <tr>
                                       <th>
                                       
                                         <c:if test="${not empty invenList}">
									
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
				  <!-- Model tài liệu -->
					<div class="modal fade" id="editOrSave" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
					  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1000px">
					    <div class="modal-content">
					      <div class="modal-header">
					        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">THÔNG TIN KHO</h5>
					        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
					          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
					        </button>
					      </div>							      
					      <div class="modal-body" style="height: 250px;" id="editOrSaveData">
					       		
					       		
					      </div>
					      <div class="modal-footer" style="padding: 5px;">
					       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
					       			<span class="glyphicon glyphicon-remove"></span> Đóng
					       		</button>
					       		<button type="button" onclick="saveChangeInv();" class="btn btn-success" data-dismiss="modal" aria-label="Close" style="float: right;margin-right: 10px;">
					       			<span class="glyphicon glyphicon glyphicon-ok"></span> Lưu
					       		</button>
					      </div>
					    </div>
					  </div>
					</div>	
					<!-- -------------------------------------------------------------------------- -->
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
					      			<p style="display: inline-block;position: absolute;margin-left: 10px;margin-top: 5px;">Bạn có muốn xóa kho này?</p>
					      		</div>
					      </div>							      
					      <div class="modal-footer" style="padding: 5px;">
					       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
					       			<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> KHÔNG</span>
					       		</button>
					       		<button type="button" onclick="deleteInv();" class="btn btn-success" data-dismiss="modal" aria-label="Close" style="float: right;margin-right: 10px;">
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
		<div id="ajax-load-qa"></div>
	
</form:form>
<div style="display: flex;flex: 0 auto;justify-content: center;">
<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
	<div style="margin: 10px"> 			
		<button type="button" class="btn btn-success" id="btnAddInv">
			<span class="glyphicon glyphicon-plus"></span> Thêm mới kho
		</button>
	</div>
</div>
</div>

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
					document.forms["formData"].action = '${inventoryUrl}';  
					document.forms["formData"].submit();  
				}
			}
		});	
	function doApplyFilter() {
		document.forms["formData"].action = '<c:url value="/servlet/persoLocation/inventory" />';
		document.forms["formData"].submit();
	}
	
	var _itemsId = '';
	function delInv(value){
		_itemsId = value;
		$('#messageLHS').modal();
	}
	function deleteInv(){
		document.forms["formData"].action = '${deleteInvUrl}/' + _itemsId;
		document.forms["formData"].submit();
	}
	
	function editInv(value){
		 $('#ajax-load-qa').show();
		 $.ajax({
				url : '${editInvUrl}/' + value,
				cache: false,
				type: "POST",					
				success : function(data) {
					$('#editOrSaveData').html(data);
					$('#editOrSave').modal();
					$('#ajax-load-qa').hide();
				},
				error: function(e){
					$('#ajax-load-qa').hide();
				}
			 });
	}
	
	function saveChangeInv(){
		 $('#ajax-load-qa').show();
		var tenKho = $('#nameInv').val();
		var maKho = $('#codeInv').val();
		var trangThai = $('#statusInv').val();
		var diaChi = $('#addressInv').val();
		var ghiChu = $('textarea#descInv').val();
		var idKho = $('#itemId').val();
		var yourArray = {};
		yourArray['tenKho'] = tenKho;
		yourArray['maKho'] = maKho;
		yourArray['trangThai'] = trangThai;
		yourArray['diaChi'] = diaChi;
		yourArray['ghiChu'] = ghiChu;
		yourArray['idKho'] = idKho;
		var url = '${saveChangeInvUrl}';
		$.ajax({
			type : "POST",
			url : url,
			data : JSON.stringify(yourArray),
			contentType : 'application/json',
			success: function(data) {
				$('#ajax-load-qa').hide();
				document.forms["formData"].action = '${reloadInventoryUrl}/' + data;
				document.forms["formData"].submit();
		    },
		    error: function(e){
		    	$('#ajax-load-qa').hide();
		    }
		 });
	}
	
	$('#btnAddInv').click(function(){
		 $('#ajax-load-qa').show();
		 $.ajax({
			url : '${addInvUrl}',
			cache: false,
			type: "POST",					
			success : function(data) {
				$('#editOrSaveData').html(data);
				$('#editOrSave').modal();
				$('#ajax-load-qa').hide();
			},
		 	error: function(e){
		 		$('#ajax-load-qa').hide();
		 	}
		 });
	 });

	$(document).ready(function(){
		 var mesDel = '${mesDel}';
		 if(mesDel == '1'){
			 $.notify('Xóa kho thành công.', {
				globalPosition: 'bottom left',
				className: 'success',
			 });
		 }else if(mesDel == '0'){
			 $.notify('Xóa kho không thành công.', {
				globalPosition: 'bottom left',
				className: 'warn',
			 });
		 }
		 
		 var mesSave = '${mesSave}';
		 if(mesSave == '1'){
			 $.notify('Lưu thông tin thành công.', {
				globalPosition: 'bottom left',
				className: 'success',
			 });
		 }else if(mesSave == '0'){
			 $.notify('Lưu thông tin không thành công.', {
				globalPosition: 'bottom left',
				className: 'warn',
			 });
		 }
	});
	
</script>

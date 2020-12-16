<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="deleteItemsUrl" value="/servlet/persoLocation/deleteItems" />
<c:url var="inventoryItemsUrl" value="/servlet/persoLocation/inventoryItems" />
<c:url var="addInvItemsUrl" value="/servlet/persoLocation/addInvItems" />
<c:url var="editInvItemsUrl" value="/servlet/persoLocation/editInvItems" />
<c:url var="saveChangeItemsUrl" value="/servlet/persoLocation/saveChangeItems" />
<c:url var="reloadInvDetailUrl" value="/servlet/persoLocation/reloadInvDetail" />
<c:url var="reloadInvDetailUrl" value="/servlet/persoLocation/reloadInvDetail" />
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
    <div class="oplog-title__txt">Quản lý phôi in</div>
</div>
<!--Content start-->
<form:form modelAttribute="formData" name="formData" autocomplete="off">
	<div class="form-desing">
		<div class="row">
			<div class="ov_hidden">
				

				<div style="clear: both"></div>


				<div style="margin: 2px">
					<div style="vertical-align: text-top; display: inline-block; width: 100%; float: left">
						<fieldset>
							<legend>Thông tin tìm kiếm</legend>
						
							<div style="margin: 2px">
								<div class="col-md-4">
									
									<label class="col-md-4 control-label text-right">Kho</label>
									<div class="col-md-8">
										<form:select path="regSiteCode" class="cls-wth-100">
											<form:option value="">Tất cả</form:option>
											<c:forEach items="${inventoryCodeList}" var="_item">
												<form:option value="${_item.key }">${_item.value }</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="col-md-4">
									<label class="col-md-4 control-label text-right">Số lô</label>
									<div class="col-md-8">
										<form:input path="packageCode" type="text" class="cls-wth-100" />
									</div>
								</div>
								<div class="col-md-4" style="margin-bottom: 10px;">
									<label class="col-md-4 control-label text-right">Trạng thái</label>
									<div class="col-md-8">
										<form:select path="stageLoad" class="cls-wth-100">
											<form:option value="">Tất cả</form:option>
											<form:option value="I">Chưa sử dụng</form:option>
											<form:option value="D">Đã in</form:option>
											<form:option value="H">In hỏng</form:option>
											<form:option value="L">Phôi lỗi</form:option>
										</form:select>
									</div>
								</div>
								<div class="col-md-4">
									<label class="col-md-4 control-label text-right">Mã phiếu</label>
									<div class="col-md-8">
										<form:input path="packageNumber" type="text" class="cls-wth-100" />
									</div>
								</div>
								<div class="col-md-4">
									<label class="col-md-4 control-label text-right">Số series chip</label>
									<div class="col-md-8">
										<form:input path="listName" type="text" class="cls-wth-100" />
									</div>
								</div>
								<div class="col-md-4">									
									<div style="margin-bottom: 10px;float: right;margin-right: 15px;">
										<button type="button" onclick="doApplyFilter();" class="btn_small btn-success">
									        <span class="glyphicon glyphicon-search"></span> Tìm kiếm
									    </button>	
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
						<legend>Danh sách phôi in</legend>
					
					<div style="height: 400px;">
				      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
						  <thead>
						    <tr>
						      
						      <th class="th-sm" style="max-width: 40px;">STT
						
						      </th>
						      <th class="th-sm">Số lô
						
						      </th>
						      <th class="th-sm">Số series chip
						
						      </th>
						      <th class="th-sm">Số phôi(phần chữ)
						
						      </th>
						      <th class="th-sm">Số phôi(phần số)
						
						      </th>
						      <th class="th-sm">Trạng thái
						
						      </th>
						      <th class="th-sm">Mã phiếu
						
						      </th>
						      <th class="th-sm">Kho
						
						      </th>
						      <th class="th-sm">Thao tác</th>
						    </tr>
						  </thead>
						  <tbody>
						    <c:forEach items="${invenList}" var="_item">
							    <tr>
							      <td align="center">${_item.stt}</td>
							      <td>${_item.batchNo}</td>
							      <td>${_item.chipNo}</td>
							      <td>${_item.docChar}</td>
							      <td>${_item.docNum}</td>
							      <td>${_item.status}</td>
							      <td align="right">${_item.invCode}</td>
							      <td>${_item.invName}</td>
							      <td align="center">
							      		<a onclick="editInv('${_item.itemStr}')" href="#">
							      			<i class="glyphicon glyphicon-pencil"></i> Sửa
							      		</a>
							      		<a onclick="delInv('${_item.itemStr}')" href="#">
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
					        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">THÔNG TIN PHÔI IN</h5>
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
					      			<p style="display: inline-block;position: absolute;margin-left: 10px;margin-top: 5px;">Bạn có muốn xóa phôi này?</p>
					      		</div>
					      </div>							      
					      <div class="modal-footer" style="padding: 5px;">
					       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
					       			<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> KHÔNG</span>
					       		</button>
					       		<button type="button" onclick="deleteItems();" class="btn btn-success" data-dismiss="modal" aria-label="Close" style="float: right;margin-right: 10px;">
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
			<span class="glyphicon glyphicon-plus"></span> Thêm mới phôi
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
					document.forms["formData"].action = '${inventoryItemsUrl}';  
					document.forms["formData"].submit();  
				}
			}
		});	
	function doApplyFilter() {
		document.forms["formData"].action = '<c:url value="/servlet/persoLocation/inventoryItems" />';
		document.forms["formData"].submit();
	}
	
	var _itemsId = '';
	function delInv(value){
		_itemsId = value;
		$('#messageLHS').modal();
	}
	function deleteItems(){
		document.forms["formData"].action = '${deleteItemsUrl}/' + _itemsId;
		document.forms["formData"].submit();
	}
	
	function editInv(value){
		 $('#ajax-load-qa').show();
		 $.ajax({
				url : '${editInvItemsUrl}/' + value,
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
		var maPhieu = $('#inventoryItem').val();
		var trangThai = $('#statusDt').val();
		var soLo = $('#batchNoItem').val();
		var soChip = $('#chipNo').val();
		var soPhoiC = $('#docChar').val();
		var soPhoiN = $('#docNum').val();
		var maPhoi = $('#itemId').val();
		var yourArray = {};
		yourArray['maPhieu'] = maPhieu;
		yourArray['trangThai'] = trangThai;
		yourArray['soLo'] = soLo;
		yourArray['soChip'] = soChip;
		yourArray['soPhoiC'] = soPhoiC;
		yourArray['soPhoiN'] = soPhoiN;
		yourArray['maPhoi'] = maPhoi;
		var url = '${saveChangeItemsUrl}';
		$.ajax({
			type : "POST",
			url : url,
			data : JSON.stringify(yourArray),
			contentType : 'application/json',
			success: function(data) {
				$('#ajax-load-qa').hide();
				
				/*if(data == 1){
					$.notify('Lưu thông tin thành công.', {
						globalPosition: 'bottom left',
						className: 'success',
					 });
				}
				if(data == 0){
					$.notify('Lưu thông tin không thành công.', {
						globalPosition: 'bottom left',
						className: 'warn',
					 });
				}*/
				document.forms["formData"].action = '${reloadInvDetailUrl}/' + data;
				document.forms["formData"].submit();
		    },
		    error: function(e){
		    	$('#ajax-load-qa').hide();
		    }
		 });
	}
	
	$(document).ready(function(){
		 var mesDel = '${mesDel}';
		 if(mesDel == '1'){
			 $.notify('Xóa phôi thành công.', {
				globalPosition: 'bottom left',
				className: 'success',
			 });
		 }else if(mesDel == '0'){
			 $.notify('Xóa phôi không thành công.', {
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
		 
		 $('#btnAddInv').click(function(){
			 $('#ajax-load-qa').show();
			 $.ajax({
				url : '${addInvItemsUrl}',
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
	});
	
</script>

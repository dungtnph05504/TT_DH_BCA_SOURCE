<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<c:url var="loadFormApiUrl" value="/servlet/central/changApiJob" />
<c:url var="changeAllApiUrl" value="/servlet/central/changeAllApi" />
<c:url var="getSiteCheckApiUrl" value="/servlet/central/getSiteCheckApi" />
<c:url var="saveSiteCheckUrl" value="/servlet/central/saveSiteCheck" />
<c:url var="createApiUrl" value="/servlet/central/createApi" />
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
            <div class="oplog-title__txt">Quản lý sử dụng các api</div>
        </div>
<div class="content-item-main">
<form:form modelAttribute="formData" name="formData" autocomplete="off"> 
  
   <div class="content-item-information">
			<div class="col-sm-12">
				<fieldset class="scheduler-border">
					<legend>Điều kiện tìm kiếm</legend>
					<div class="form-group" style="margin-top: 15px;">
						<div class="col-sm-3">
							<label class="col-sm-4 control-label text-right">Loại API</label>
							<div class="col-sm-8">
								<form:select id="listCode" path="listCode" class="style-width-100">
									<form:option value="">-- Tất cả --</form:option>
									<form:option value="1">API Xử lý hồ sơ</form:option>
									<form:option value="2">API Cá thể hóa</form:option>
									<form:option value="3">API Xuất nhập cảnh</form:option>
								</form:select>
							</div>
						</div>
						<div class="col-sm-3">
							<label class="col-sm-4 control-label text-right">Tên API</label>
							<div class="col-sm-8">
								<form:input path="name"  class="style-width-100" id="idName" placeholder="" />
							</div>
						</div>
						<div class="col-sm-3">
							<label class="col-sm-4 control-label text-right">Trạng thái</label>
							<div class="col-sm-8">
								<form:select id="idStage" path="stageLoad" class="style-width-100">
									<form:option value="">-- Tất cả --</form:option>
									<form:option value="Y">API ngưng hoạt động</form:option>
									<form:option value="N">API đang hoạt động</form:option>
								</form:select>
							</div>
						</div>
						<div class="col-sm-3">
							<button type="button" style="margin-left: 50px;" onclick="doApplyFilter();" class="btn_small btn-success">
					        	<span class="glyphicon glyphicon-search"></span> Tìm kiếm
					   		</button>
						</div>
					</div>
				</fieldset>
			</div>
			<div>
			<div class="col-sm-12" style="margin-bottom: 50px;">
					<fieldset class="scheduler-border">
							<legend class="scheduler-border">Danh sách các Api</legend>
							<div style="height: 400px;overflow: auto;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      
								      <th class="th-sm" style="max-width: 40px;">STT
								
								      </th>
								      <th class="th-sm">URL
								
								      </th>
								      <th class="th-sm">Tên API
								
								      </th>
								      <th class="th-sm">Mô tả
								
								      </th>
								      <th class="th-sm">Trạng thái
								
								      </th>
								      <th class="th-sm">Trung tâm bị chặn
								
								      </th>
								      <th class="th-sm">Thêm
								      </th>								     
								    </tr>
								  </thead>
								   <tbody>
								  <c:if test="${not empty dsApi}">
									 
									    <c:forEach items="${dsApi}" var="item">
										    <tr>										      
										      <td align="center">${item.stt}</td>
										      <td>${item.uriApi}</td>
										      <td>${item.nameApi}</td>
										      <td>${item.description}</td>
										      <td align="center">
										      		<c:choose>
														<c:when test="${item.closeAll == 'N'}">
															<input type="checkbox" name="chk_stage" value="${item.id}" checked data-toggle="toggle" data-size="mini" data-onstyle="success" data-offstyle="danger" data-width="40" data-height="18" />
														</c:when>
														<c:otherwise>
															<input type="checkbox" name="chk_stage" value="${item.id}" data-toggle="toggle" data-size="mini" data-onstyle="success" data-offstyle="danger" data-width="40" data-height="18" />
														</c:otherwise>
													</c:choose>
										      </td>
										      <td><span id="sp_${item.id}">${item.closeMember}</span></td>
										      <td align="center">
										      		<a href="#" onclick="chiTietHS('${item.id}', '${item.type}');"><i class="glyphicon glyphicon-eye-open"></i>Chi tiết</a>
										      </td>
										      
										    </tr>
									    </c:forEach>
									 
								  </c:if>
								   </tbody>
								    <c:if test="${empty dsApi}">
								  
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
                                        
                                          <c:if test="${not empty dsApi}">
										
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
	  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 310px;">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">DANH SÁCH TRUNG TÂM</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
	        </button>
	      </div>
	      <div class="modal-body" id="checkSiteForm">
	      		
	      </div>							      
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" onclick="" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> ĐÓNG</span>
	       		</button>
	       		<button type="button" onclick="saveCheckSite();" class="btn btn-success" data-dismiss="modal" aria-label="Close" style="float: right;margin-right: 10px;">
	       			<span class="glyphicon glyphicon-ok btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> LƯU</span>
	       		</button>
	       		
	      </div>
	    </div>
	  </div>
	</div>	
	<!-- ---------------------------------------------------------------------------->
	<div class="col-sm-12">
		<div style="display: flex;flex: 0 auto;justify-content: center;">
		<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
			<div style="margin: 10px"> 
				<button type="button"  onclick="createdApi();" name="approve" class="btn btn-success">
					<span class="glyphicon glyphicon-plus"></span> Thêm mới 
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
						document.forms["formData"].action = '${loadFormApiUrl}';  
						document.forms["formData"].submit();  
					}
				}
			});
		function doApplyFilter() {
			document.forms["formData"].action = '${loadFormApiUrl}';  
			document.forms["formData"].submit();  
		}
	</script>
	<script type="text/javascript">
		
		var res = '${result}';
		if(res == '1'){
			$.notify('Thêm Api thành công.', {
				globalPosition: 'bottom left',
				className: 'success',
			});
		}else if(res == '-1'){
			$.notify('Thêm Api không thành công.', {
				globalPosition: 'bottom left',
				className: 'warn',
			});
		}else if(res == '0'){
			$.notify('URL đã tồn tại. Vui lòng xem lại!', {
				globalPosition: 'bottom left',
				className: 'warn',
			});
		}
	
		function createdApi(){
			document.forms["formData"].action = '${createApiUrl}';  
			document.forms["formData"].submit();
		}
		
		var idConfig = '';
	    function chiTietHS(id, type){
	    	idConfig = id;
	    	$('#checkSiteForm').html("");
			var url = '${getSiteCheckApiUrl}/' + id + "/" + type;
			$('#ajax-load-qa').css('display', 'block');
			$.ajax({
				url : url,
				cache: false,
				type: "POST",
				success : function(data) {
					$('#checkSiteForm').html(data);
					$('#checkSiteId').modal();
					$('#ajax-load-qa').css('display', 'none');				
				}
			});
	    }
	    
	    function saveCheckSite(){
	    	var arrSite = new Array();
	    	var strSite = '';
	     	 $("input[name='chkSite']").each(function () {
	     		if(this.checked){
	     			arrSite.push($(this).val());
	     			strSite += $(this).val() + ',';
	     		}
	     	 });
	     	 var url = '${saveSiteCheckUrl}';	
		     $('#ajax-load-qa').show();
		   	 $.ajax({
		   		url : url,
		   		type: "POST",
		   		data: {
		   			idConfig: idConfig,
		   			arrSite : arrSite
		   		},
		   		success : function(data) {
		   			$('#ajax-load-qa').hide();
		   			if(data == '1'){
		   				$('#sp_' + idConfig).html(strSite);
		   				$.notify('Lưu thay đổi thành công.', {
							globalPosition: 'bottom left',
							className: 'success',
						});
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
	    
		$(function() {
		    $('input[name ="chk_stage"]').change(function() {
		    	var valConfig = $(this).val();
		    	var stage = 0;
		        if($(this).prop('checked')){
		        	stage = 1;
		        }
		        var url = '${changeAllApiUrl}';
		        $.ajax({
					url : url,
					cache: false,
					type: "POST",	
					data: {
						idConfig: valConfig,
						stage: stage
					},
					success : function(data) {
						console.log(data == 1 ? "Thành công" : "Thất bại");
					},
				 	error: function(e){
				 		console.log(e);
				 	}
				 });
		    });
		  })
		
		
		
	</script>
	
</form:form>
</div>


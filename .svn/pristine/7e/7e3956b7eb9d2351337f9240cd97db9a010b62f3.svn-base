<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<c:url var="loadFormQueueUrl" value="/servlet/central/showQueue" />
<c:url var="getSiteCheckUrl" value="/servlet/central/getSiteCheck" />
<c:url var="saveSiteCheckUrl" value="/servlet/central/saveSiteCheckQ" />
<c:url var = "getStatusCheckUrl" value="/servlet/central/getStatusCheck"/>
<c:url var="saveStatusCheckUrl" value="/servlet/central/saveStatusCheckQ" />
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
            <div class="oplog-title__txt">Quản lý hàng đợi</div>
        </div>
<div class="content-item-main">
<form:form modelAttribute="formData" name="formData" autocomplete="off"> 
  
   <div class="content-item-information">
			<div class="col-sm-12">
				<fieldset class="scheduler-border">
					<legend>Điều kiện tìm kiếm</legend>
					<div class="form-group" style="margin-top: 15px;">
						<div class="col-sm-4">
							<label class="col-sm-4 control-label text-right">Mã hàng đợi</label>
							<div class="col-sm-8">
								<form:input path="searchTransactionId"  class="style-width-100" id="idTran" placeholder="" />
							</div>
						</div>
						<div class="col-sm-4">
							<label class="col-sm-4 control-label text-right">Kiểu hàng đợi</label>
							<div class="col-sm-8">
								<form:select id="idType" path="typeList" class="style-width-100">
									<form:option value="">-- Tất cả --</form:option>
									<form:option value="HS">Hồ sơ xử lý</form:option>
									<form:option value="DA">Danh sách A</form:option>
									<form:option value="DB">Danh sách B</form:option>
									<form:option value="DC">Danh sách C</form:option>
									<form:option value="BF">Hồ sơ nghi trùng</form:option>
									<form:option value="IN">Cá thể hóa</form:option>
									<form:option value="PS">Thông tin Person</form:option>
									<form:option value="PP">Hộ chiếu</form:option>
									<form:option value="UPP">Cập nhật hộ chiếu</form:option>
								</form:select>
							</div>
						</div>
						
						<div class="col-sm-4">
							<label class="col-sm-4 control-label text-right">Trạng thái</label>
							<div class="col-sm-8">
								<form:select id="idStage" path="stageLoad" class="style-width-100">
									<form:option value="">-- Tất cả --</form:option>
									<form:option value="PENDING">Chưa xử lý</form:option>
									<form:option value="DOING">Đang xử lý</form:option>
									<form:option value="NONE">Lỗi xử lý</form:option>
									<form:option value="DONE">Xử lý thành công</form:option>
								</form:select>
							</div>
						</div>
						<form:hidden path="nation"/>
					</div>
					<div class="form-group">
						<div class="col-sm-12">
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
							<legend class="scheduler-border">Danh sách hàng đợi</legend>
							<div style="height: 400px;overflow: auto;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="width : 40px;">STT
								
								      </th>
								      <th class="th-sm">Mã hàng đợi
								
								      </th>
								      <th class="th-sm">Kiểu hàng đợi
								
								      </th>
								      <th class="th-sm">Nơi nhận
								
								      </th>
								      <th class="th-sm">Trạng thái
								
								      </th>
								      <th class="th-sm">Ngày tạo
								
								      </th>
								      <th class="th-sm">Ngày cập nhật TT 
								
								      </th>
								        <th class="th-sm">Số lần gọi
								
								      </th>
								      
								      <th class="th-sm" style="width: 150px;">Thay đổi nơi nhận
								
								      </th>
								     
								    </tr>
								  </thead>
								   <tbody>
								  <c:if test="${not empty dsQueue}">
									 
									    <c:forEach items="${dsQueue}" var="item">
										    <tr>										      
										     	<td align="center">${item.stt}</td>
										      	<td>${item.code}</td>
										      	<td>${item.type}</td>
										      	<td><span id="sp_${item.id}">${item.site}</span></td>
										      
										      	<td>
										      		<a href="#" onclick="chiTietST('${item.id}','${item.stage}')" id="sta_${item.id}"><i class="glyphicon glyphicon-edit"></i>${item.stage}</a>
										      	</td>
										      	<td align="center">${item.createDate}</td>
										      	<td align="center" id="date_${item.id}">${item.dateUpdate}</td>
										      	<td align="center" id="syn_${item.id}">${item.count}</td>
										      	<td align="center">
										      		<a href="#" onclick="chiTietHS('${item.id}', '${item.stageId}', '${item.typeId}');"><i class="glyphicon glyphicon-edit"></i>Thay đổi</a>
										      	</td>
										    </tr>
									    </c:forEach>
									 
								  </c:if>
								   </tbody>
								    <c:if test="${empty dsQueue}">
								  
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
                                        
                                          <c:if test="${not empty dsQueue}">
										
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
	<div class="modal fade" id="checkStatusId" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
	  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 310px;">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">Danh Sách Trạng thái</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
	        </button>
	      </div>
	      <div class="modal-body" id="checkStatusForm">
	      		
	      </div>							      
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" onclick="" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> ĐÓNG</span>
	       		</button>
	       		<button type="button" onclick="saveCheckStatus();" class="btn btn-success" data-dismiss="modal" aria-label="Close" style="float: right;margin-right: 10px;">
	       			<span class="glyphicon glyphicon-ok btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> LƯU</span>
	       		</button>
	       		
	      </div>
	    </div>
	  </div>
	</div>	
	<!-- <div style="display: flex;flex: 0 auto;justify-content: center;">
	<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
		<div style="margin: 10px"> 
			<button type="button"  onclick="processHS();" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-list-alt"></span> Xử lý hồ sơ
			</button>
		</div>
	</div>	
	</div> -->

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
		function doApplyFilter() {
			document.forms["formData"].action = '${loadFormQueueUrl}';  
			document.forms["formData"].submit();  
		}
	</script>
	<script type="text/javascript">
		
		var idConfig = '';
		var idst='';
		var st = '';
		function chiTietST(id,stage){
			$('#checkStatusForm').html("");
			var url = '${getStatusCheckUrl}/' + id;
			$('#ajax-load-qa').css('display', 'block');
			idst=id;
			st = stage;
			$.ajax({
				url : url,
				cache: false,
				type: "POST",
				success : function(data) {
					$('#checkStatusForm').html(data);
					$('#checkStatusId').modal();
					$('#ajax-load-qa').css('display', 'none');				
				}
			});
		}
		
	    function chiTietHS(id, tage, type){
	    	if(tage != 'PENDING'){
	    		$.notify('Chỉ thay đổi được nơi nhận đối với trạng thái chưa xử lý.', {
					globalPosition: 'bottom left',
					className: 'warn',
				});
	    		return;
	    	}
	    	idConfig = id;
	    	$('#checkSiteForm').html("");
			var url = '${getSiteCheckUrl}/' + type;
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
	    
	    function saveCheckStatus(){
	    	var status = $("input[name='chkSite1']:checked").val();
	    	 var txtSite = $('input[name="chkSite1"]:checked').closest('label').text();
	    	 var url='${saveStatusCheckUrl}/' + idst;
	    	 $('#ajax-load-qa').show();
             if(status === st){
	    		 
	    		 $.notify('Trạng thái đã chọn trùng với trạng thái ban đầu.', {
						globalPosition: 'bottom left',
						className: 'warn',
					});
	    			$('#ajax-load-qa').hide();
	    		//   $('#checkStatusId').modal('hide');
		     		return ;
	    	  }
            // $('#ajax-load-qa').show();
		   	 $.ajax({
		   		url : url,
		   		type: "POST",
		   		data: {
		   		//	idConfig: idConfig,
		   			status : status,
		   			st  : st
		   		},
		   		success : function(data) {
		   			$('#ajax-load-qa').hide();
		   			if(data.count == '1'){
		   				if(st==='NONE' && status==='PENDING'){
		   					$('#syn_' + idst).html(0);
		   				}
		   				$('#sta_' + idst).html('<i class="glyphicon glyphicon-edit"></i>' + txtSite);
		   				
		   			    // st;	
		   			    $('#date_' + idst).html(data.date);
		   				$.notify('Lưu thay đổi thành công.', {
							globalPosition: 'bottom left',
							className: 'success',
						});
		   			    	
		   			}
		   			if(data.count == '0'){
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
	    function saveCheckSite(){
	    	 var site = $("input[name='chkSite']:checked").val();
	    	 //var txtSite = $("input[name='chkSite']:checked").text();
	    	 var txtSite = $('input[name="chkSite"]:checked').closest('label').text()
	     	 var url = '${saveSiteCheckUrl}';	
		     $('#ajax-load-qa').show();
		   	 $.ajax({
		   		url : url,
		   		type: "POST",
		   		data: {
		   			idConfig: idConfig,
		   			site : site
		   		},
		   		success : function(data) {
		   			$('#ajax-load-qa').hide();
		   			if(data == '1'){
		   				$('#sp_' + idConfig).html(txtSite);
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
	    
		
	</script>
	
</form:form>
</div>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<c:url var="deleteAirportUrl" value="/servlet/adminImmi/deleteFlight" />
<c:url var="editAirportUrl" value="/servlet/adminImmi/editFlight" />
<c:url var="saveChangeAirUrl" value="/servlet/adminImmi/saveChangeFlight" />
<c:url var="showAirportUrl" value="/servlet/adminImmi/showFlight" />
<c:url var="saveAirportUrl" value="/servlet/adminImmi/saveFlight" />
<c:url var="loadFlightUrl" value="/servlet/adminImmi/loadFlight" />

<%-- <form:form commandName="jobList" id="form" name="form" > --%>
<style>
.width-100 {
	width: 100%;
}
.mgin-top-10 {
	margin-top: 10px;
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
    <div class="oplog-title__txt">Quản lý dữ liệu chuyến bay</div>
</div>
<div class="content-item-main" style="padding: 0px 30px 0px 30px;">
<form:form modelAttribute="formData" name="formData" autocomplete="off"> 
			<input type="hidden" name="result" id="rsId" />
		    <div class="content-item-information">
		     	<fieldset>
		     		<legend>Điều kiện tìm kiếm</legend>
		     		<div class="form-group mgin-top-10">
		     			<div class="col-sm-4">
		     				<label class="control-lable text-right col-sm-4">Mã chuyến bay</label>
		     				<div class="col-sm-8">
		     					<form:input type="text" class="width-100" path="searchTransactionId" />
		     				</div>
		     			</div>
		     			<div class="col-sm-4">
		     				<label class="control-lable text-right col-sm-4">Đường bay</label>
		     				<div class="col-sm-8">
		     					<form:select path="startYear" class="width-100 sumo-sl" >
		     						<form:option value="">Tất cả</form:option>	
		     						<c:forEach items="${flightMap}" var="_item">
		     							<form:option value="${_item.key}">${_item.value}</form:option>	
		     						</c:forEach>
		     					</form:select>
		     				</div>
		     			</div>
		     			<div class="col-sm-4">
		     				<label class="control-lable text-right col-sm-4">Hãng hàng không</label>
		     				<div class="col-sm-8">
		     					<form:select path="endYear" class="width-100 sumo-sl" >
		     						<form:option value="">Tất cả</form:option>	
		     						<c:forEach items="${airList}" var="_item">
		     							<form:option value="${_item.key}">${_item.value}</form:option>	
		     						</c:forEach>
		     					</form:select>
		     				</div>
		     			</div>
		     			
		     		</div>
		     		<div class="form-group">
		     			<div class="col-sm-12">
		     				<button type="button" onclick="doApplyFilter();" style="float: right;margin-right: 15px;" class="btn_small btn-success">
						        <span class="glyphicon glyphicon-search"></span> Tìm kiếm
						    </button>
		     			</div>
		     		</div>
		     	</fieldset>
		    </div>
			
						

	
			<fieldset>
				<legend>Danh sách các chuyến bay</legend>
			
			<div class="col-sm-12 none-pad-right none-pad-left" style="margin-bottom: 50px;">
					<!--<fieldset class="scheduler-border">
							<legend class="scheduler-border">Hồ sơ cần xử lý</legend>-->
							<div style="height: 400px; overflow: auto;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
								  <thead>
								    <tr>								     
								      <th class="th-sm" style="width: 40px;">STT
								
								      </th>
								      <th class="th-sm">Mã chuyến bay
								
								      </th>							
								      <th class="th-sm">Đường bay
								
								      </th>
								      <th class="th-sm">Hãng hàng không
								
								      </th>
								      <th class="th-sm">Tên chuyến bay
								
								      </th>
								      <th class="th-sm">Ghi chú
								
								      </th>
								      <th class="th-sm">Loại chuyến bay
								
								      </th>
								      <th class="th-sm">Thao tác
								
								      </th>
								    </tr>
								  </thead>
								   <tbody>
								  	<c:if test="${not empty flightList}">
									 
									    <c:forEach items="${flightList}" var="item">
										    <tr>
										      <td class="align-central">
										      	  ${item.stt}
											  </td>	
											  <td>${item.flightNo}</td>
										      <td>${item.flightRouterCode}</td>
										      <td>${item.airlineNo}</td>										      
										      <td>${item.name}</td>
										      <td>${item.note}</td>
										      <td>${item.type}</td>	
										      <td align="center">
										      	 <a href="#" onclick="editAirline('${item.flightNo}', '${item.type}');" >
									      			<i class="glyphicon glyphicon-pencil"></i> Sửa
									      		 </a>
									      		 <a onclick="delAirline('${item.id}');" href="#" >
									      			<i class="glyphicon glyphicon-trash"></i> Xóa
									      		 </a>
										      </td>					     
										    </tr>
									    </c:forEach>
									 
								  </c:if>
								   </tbody>
								    <c:if test="${empty flightList}">
								  
								   <tbody class="e-not-found ng-scope"><tr>
								  <td colspan="8" style="height: 362px">
								  <div class="e-not-found-center"><div class="e-not-found-title">Không tìm thấy kết quả</div><div class="e-not-found-title-child">Hãy thử tìm kiếm hoặc sử dụng bộ lọc khác</div></div>
								  </td></tr></tbody>
								  
								
								</c:if>
								</table>
								
								
						      </div>
						      
						      <table class="table e-grid-table e-border">
                                <tfoot>
                                    <tr>
                                        <th>
                                        
                                          <c:if test="${not empty flightList}">
										
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
						  <!--</fieldset>-->
		
		</div>
	</fieldset>	
	<div id="ajax-load-qa"></div>	
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
	      			<p style="display: inline-block;position: absolute;margin-left: 10px;margin-top: 5px;">Bạn có muốn xóa dữ liệu này?</p>
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
	
	<!-- Message lưu hồ sơ -->
	<div class="modal fade" id="idChiTiet" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
	  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 900px;">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">NHẬP THÔNG TIN CHUYẾN BAY</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
	        </button>
	      </div>
	      <div class="modal-body" id="divChiTiet">
	      		
	      </div>							      
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove"></span> Đóng
	       		</button>
	       		<button type="button" onclick="saveChangeInv();" class="btn btn-success" style="float: right;margin-right: 10px;">
	       			<span class="glyphicon glyphicon glyphicon-ok"></span> Lưu
	       		</button>	       		
	      </div>
	    </div>
	  </div>
	</div>	
	<!-- ---------------------------------------------------------------------------->
	<div style="display: flex;flex: 0 auto;justify-content: center;">
	<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
		<div style="margin: 10px"> 
			<button type="button"  onclick="saveAirline();" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-plus"></span> Thêm mới chuyến bay
			</button>
		</div>
	</div>	
	</div>
<div id="dialog-confirm"></div>
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
						document.forms["formData"].action = '${showAirportUrl}';  
						document.forms["formData"].submit();  
					}
				}
			});
		
		$(document).ready(function() {
			$('.sumo-sl').SumoSelect();
			
			var mesSave = '${mesg}';
			if(mesSave == '1'){
				$.notify('Lưu thông tin chuyến bay thành công.', {
					globalPosition: 'bottom left',
					className: 'success',
				});
			}else if(mesSave == '0'){
				$.notify('Lưu thông tin chuyến bay không thành công', {
					globalPosition: 'bottom left',
					className: 'warn',
				});
			}else if(mesSave == '-1'){
				$.notify('Mã chuyến bay/loại chuyến bay đã tồn tại. Vui lòng thử lại sau', {
					globalPosition: 'bottom left',
					className: 'warn',
				});
			}
			
			var mesDel = '${result}';
			if(mesDel == '1'){
				$.notify('Xóa thông tin chuyến bay thành công.', {
					globalPosition: 'bottom left',
					className: 'success',
				});
			}else if(mesDel == '0'){
				$.notify('Xóa thông tin chuyến bay không thành công', {
					globalPosition: 'bottom left',
					className: 'warn',
				});
			}
			

			
			
		});
	</script>
	<script type="text/javascript">

	function saveChangeInv(){
		
		var codeAir = $('#codeAir').val();
		var typeFlight = $('#typeFlight').val();
		var flightRt = $('#flightRt').val();
		var nameAir = $('#nameAir').val();
		var airlineCode = $('#airlineCode').val();
		var descAir = $('#descAir').val();
		var idItem = $('#itemId').val();
		if(codeAir.trim() == ''){
			$.notify('Mã chuyến bay không được phép bỏ trống.', {
				globalPosition: 'bottom left',
				className: 'warn',
			});
			return;
		}
		if(nameAir.trim() == ''){
			$.notify('Tên chuyến bay không được phép bỏ trống.', {
				globalPosition: 'bottom left',
				className: 'warn',
			});
			return;
		}
		if(flightRt.trim() == ''){
			$.notify('Vui lòng chọn đường bay.', {
				globalPosition: 'bottom left',
				className: 'warn',
			});
			return;
		}
		var yourArray = {};
		yourArray['codeAir'] = codeAir;
		yourArray['typeFlight'] = typeFlight;
		yourArray['flightRt'] = flightRt;
		yourArray['nameAir'] = nameAir;
		yourArray['airlineCode'] = airlineCode;
		yourArray['descAir'] = descAir;
		yourArray['idItem'] = idItem;
		$('#idChiTiet').modal('hide');
		var url = '${saveChangeAirUrl}';
		$('#ajax-load-qa').show();
		$.ajax({
			type : "POST",
			url : url,
			data : JSON.stringify(yourArray),
			contentType : 'application/json',
			success: function(data) {
				$('#ajax-load-qa').hide();
				$('#rsId').val(data);
				document.forms["formData"].action = '${showAirportUrl}';
				document.forms["formData"].submit();
		    },
		    error: function(e){
		    	$('#ajax-load-qa').hide();
		    }
		 });
	}
		

		var idAL = '';
	
		function delAirline(value) {
			
			$('#messageLHS').modal();
			idAL = value;
		}
	
		function deleteForm(){
			document.forms["formData"].action = '${deleteAirportUrl}/' + idAL;
			document.forms["formData"].submit();
		}
		
		function doApplyFilter() {
			document.forms["formData"].action = '<c:url value="/servlet/adminImmi/showAirline" />';
			document.forms["formData"].submit();
		}
	
		function editAirline(value, tp) {
			var urlLink = '${editAirportUrl}';
			$('#divChiTiet').html('');		
			$.ajax({
				type : "POST",
				url : urlLink,
				data : {
					value : value,
					typ : tp
				},
				success: function(data) {
					$('#divChiTiet').html(data);	
					$('#idChiTiet').modal();
					loadFligt();
			    },
			    error: function(e){}
			 });
		}
		
		
		function saveAirline(){
			var urlLink = '${saveAirportUrl}';
			$('#divChiTiet').html('');		
			$.ajax({
				type : "POST",
				url : urlLink,
				success: function(data) {
					$('#divChiTiet').html(data);	
					$('#idChiTiet').modal();
					loadFligt();
			    },
			    error: function(e){}
			 });
		}
		
		function loadFligt(){
			var codeFlight = $('#flightRt').val();
			if(codeFlight != ''){
				var urlLink = '${loadFlightUrl}';
				$('#flightID').html('');		
				$.ajax({
					type : "POST",
					url : urlLink,
					data : {
						codeFlight: codeFlight
					},
					success: function(data) {
						var str = '';
						for(var i = 0; i < data.length; i++){
							str += '<span>' + data[i] + "</span>";
						}
						$('#flightID').html(str);	
				    },
				    error: function(e){}
				 });
			}
		}

		
	
	</script>
	
</form:form>
</div>


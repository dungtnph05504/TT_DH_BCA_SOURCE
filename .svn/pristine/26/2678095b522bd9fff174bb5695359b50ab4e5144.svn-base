<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<c:url var="detailSiteStreamUrl" value="/servlet/central/detailSiteStream" />
<c:url var="updateSiteStreamUrl" value="/servlet/central/updateSiteStream" />
<%-- <form:form commandName="jobList" id="form" name="form" > --%>
<style>
.c-with-100 {
	width: 100%;
}
.toggle {
	border-radius: 20px;
}
</style>
<div class="content-item-title">
            <div class="oplog-title__txt">Chi tiết điều phối luồng</div>
        </div>
<div class="content-item-main">
<form:form modelAttribute="formData" name="formData" > 

  
   <div class="content-item-information">
     	<fieldset>
     		<legend>Thông tin tìm kiếm</legend>

   			<div class="form-group form-profile pad-bottom-15">
   				<div class="col-sm-4">
   					<label class="col-sm-4 control-label text-right">Loại luồng</label>
   					<div class="col-sm-8">
	   					<form:select path="transactionType" class="c-with-100">
	   						<form:option value="">Tất cả</form:option>
	   						<form:option value="XL">Luồng xử lý</form:option>
	   						<form:option value="IN">Luồng cá thể hóa</form:option>
	   						<form:option value="PH">Luồng phát hành</form:option>
	   					</form:select>
   					</div>
   				</div>
   				<div class="col-sm-4">
   					<label class="col-sm-4 control-label text-right">Trạng thái</label>
   					<div class="col-sm-8">
	   					<form:select path="stageLoad" class="c-with-100">
	   						<form:option value="">Tất cả</form:option>
	   						<form:option value="1">Còn hiệu lực</form:option>
	   						<form:option value="0">Hết hiệu lực</form:option>
	   					</form:select>
   					</div>
   				</div>
   				<div class="col-sm-4">
   					<label class="col-sm-4 control-label text-right">Nơi tiếp nhận</label>
   					<div class="col-sm-8">
	   					<form:select path="regSiteCode" class="c-with-100">
	   						<form:option value="">Tất cả</form:option>
	   						<c:forEach items="${siteList}" var="_item">
	   							<form:option value="${_item.key}">${_item.value}</form:option>
	   						</c:forEach>
	   					</form:select>
   					</div>
   				</div>
   			</div>
   			<div class="form-group">	
                <div class="col-sm-12">                 
                    <div style="float: right;margin-right: 15px;">
                        <button type="button" onclick="doApplyFilter();" class="btn_small btn-success">
							<span class="glyphicon glyphicon-search"></span> Tìm kiếm
						</button>	       
					</div>
                </div>                    
            </div>
			</fieldset>
						

	
			<div class="form-group" style="margin-top: 10px;">
			<div class="col-sm-12 none-pad-right none-pad-left">
					<fieldset class="scheduler-border">
							<legend class="scheduler-border">Danh sách luồng điều phối</legend>
							<div style="height: 400px;overflow: auto;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
								  <thead>
								    <tr>								     
								      <th class="th-sm" style="max-width: 40px;">STT
								
								      </th>
								      <th class="th-sm">TT tiếp nhận
								
								      </th>
								      <th class="th-sm">TT điều hướng
								
								      </th>
								      <th class="th-sm">Loại luồng
								
								      </th>
								      <th class="th-sm">Thời gian từ
								
								      </th>
								      <th class="th-sm">Thời gian đến
								
								      </th>
								      <th class="th-sm">Trạng thái
								      
								      </th>
								      <th class="th-sm">Người điều hướng
								      
								      </th>
								      <th class="th-sm">Thao tác</th>
								    </tr>
								  </thead>
								   <tbody>
								  <c:if test="${not empty dsXuLyA}">
									 
									    <c:forEach items="${dsXuLyA}" var="_item">
										    <tr>
												<td align="center">${_item.stt}</td>
												<td>${_item.siteFrom}</td>
												<td>${_item.siteTo}</td>
												<td>${_item.jobType}</td>
												<td align="center">${_item.dateFrom}</td>
												<td align="center">${_item.dateTo}</td>
												<td>${_item.transactionStatus}</td>
												<td>${_item.createBy}</td>
												<td align="center">
													<c:choose>
														<c:when test="${_item.stylePP == 1}">
															<input type="checkbox" name="chk_stage" value="${_item.uploadJobId}" checked data-toggle="toggle" data-size="mini" data-onstyle="success" data-offstyle="danger" data-width="40" data-height="18" />
														</c:when>
														<c:otherwise>
															<input type="checkbox" name="chk_stage" value="${_item.uploadJobId}" data-toggle="toggle" data-size="mini" data-onstyle="success" data-offstyle="danger" data-width="40" data-height="18" />
														</c:otherwise>
													</c:choose>
												</td>
										    </tr>
									    </c:forEach>
									 
								  </c:if>
								   </tbody>
								    <c:if test="${empty dsXuLyA}">
								  
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
					document.forms["formData"].action = '${detailSiteStreamUrl}';  
					document.forms["formData"].submit();  
				}
			}
		});
	function doApplyFilter() {
		document.forms["formData"].action = '${detailSiteStreamUrl}';  
		document.forms["formData"].submit(); 
	}
	$(function() {
	    $('input[name ="chk_stage"]').change(function() {
	    	var valConfig = $(this).val();
	    	var stage = 0;
	        if($(this).prop('checked')){
	        	stage = 1;
	        }
	        var url = '${updateSiteStreamUrl}';
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


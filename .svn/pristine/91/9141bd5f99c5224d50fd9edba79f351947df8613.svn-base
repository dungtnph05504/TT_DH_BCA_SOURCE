<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%-- <c:url var="submitUrl" value="/servlet/codeMgmt" /> --%>
<c:url var="codeValUrl" value="/servlet/codeMgmt/getcodevalues"/>
<c:url var="frdReq" value="/servlet/codeMgmt/requestToSaveUpdate"/>
<c:url var="url" value="/servlet/codeMgmt/view"/>

<style>
tr {
	height: 35px;
}
.displayTag tr.odd {
	height: 35px;
}
.displayTag tr.even {
	height: 35px;
}
</style>
<form:form modelAttribute="codeForm" name="codeForm"  id="codeForm" method="GET" >
<!-- <div id="main"> -->
<div class="form-desing">
                        <div class="row">
                            <div class="ov_hidden">
<div id="content_main" style="background-color: #fff;">
  <div class="new-heading-2">QUẢN LÝ DANH MỤC HỆ THỐNG</div>
  
  						<fieldset>
							<legend>Điều kiện tìm kiếm</legend>
							<div class="form-group" style="margin-top: 5px;">
								<div class="col-md-12 col-sm-12">
									<div class="col-sm-4">
										<div class="col-md-5 col-sm-5 cls-mg-bot" style="margin-top: 3px;">
											<div class="cla-font">Tên bảng mã: </div>
										</div>
										<div class="col-md-7 col-sm-7 cls-mg-bot" style="margin-top: 2px;">
											<input type="text" name="codeId" var="codeId" path="codeId" id="codeId" value="">
										</div> 
									</div>
									<div class="col-sm-4">
										<button type="button" style="float: right;margin-right: 15px;" onclick="window.location='${url}'" class="btn btn-success">
								        	<span class="glyphicon glyphicon-refresh"></span> Hủy tìm kiếm
								   		</button>
										
										<button type="button" id="search_btn_data"
											class="btn btn-success"
											style="display: flow-root;float: right;margin-right: 15px;">
											<span class="glyphicon glyphicon-search"></span> Tìm kiếm
										</button>
									</div>
								</div>
							</div>
						</fieldset>
  
  <fieldset>
  		<legend>Danh sách danh mục hệ thống</legend>
  
				<div style="height: 400px;overflow: auto;">
			      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
					  <thead>
					    <tr>
					      <th class="th-sm" style="max-width: 150px;">Tên bảng mã
					
					      </th>	
					      <th class="th-sm" style="min-width: 150px;">Mã mô tả bảng
					
					      </th>
					    </tr>
					  </thead>
					  <tbody>
					    <c:forEach items="${jobList}" var="item">
						    <tr>							    
						      <td>
						      		<c:url var="codeValUrl1" value="/servlet/codeMgmt/getcodevalues/${item.codeId}" />
									<a style="color: blue;" href="${codeValUrl1}"> <c:out value="${item.codeId}" /> </a>
						      </td>
						      <td>${item.codeDesc}</td>												     
						    </tr>
					    </c:forEach>
					  </tbody>
					</table>		 
					<input type="hidden" name="pageNo" id="pageNo" /> 
			      </div>
			      <table class="table e-grid-table e-border">
                             <tfoot>
                                 <tr>
                                     <th>
                                     
                                       <c:if test="${not empty jobList}">
							
							<div class="e-page-rigth">
								<ul style="float: right;" class="pagination" id="pagination"></ul>
							</div>
							
						</c:if>
                                     
                                     <div class="e-page-left" style="font-weight: normal;">
								Hiển thị ${startIndex} đến ${endIndex} của ${totalRecord} kết quả
							</div>
                                     
                                       
                                         
                                     </th>
                                 </tr>
                             </tfoot>
                         </table>
			 </fieldset>
 <!--<div id="codeTable" class="displayTag" style="background-image:url(/images/head.png');background-repeat: repeat-x;">

	
   <display:table cellspacing="1" cellpadding="0" id="row" export="false" class="displayTag" name="codeList"  partialList="true" size="${totalRecords}"
   defaultsort="1" defaultorder="ascending" pagesize="${pageSize}" requestURI="/servlet/codeMgmt/view">
  <display:setProperty name="paging.banner.item_name" value='kết quả'></display:setProperty>
	<display:setProperty name="paging.banner.items_name" value='kết quả'></display:setProperty>
	<display:setProperty name="paging.banner.placement" value='bottom'></display:setProperty>
	<display:setProperty name="paging.banner.no_items_found" value='<span class="pagebanner"> Không tìm thấy dữ liệu. </span>'></display:setProperty>
	<display:setProperty name="paging.banner.one_item_found" value='<span class="pagebanner"> Tìm thấy 1 kết quả. </span>'></display:setProperty>
	<display:setProperty name="paging.banner.all_items_found" value='<span class="pagebanner"> {0} {1} tìm thấy, hiển thị tất cả {2}. </span>'></display:setProperty>
	<display:setProperty name="paging.banner.some_items_found" value='<span class="pagebanner"> {0} {1} tìm thấy, hiển thị từ {2} đến {3}. </span>'></display:setProperty>		
	<display:setProperty name="paging.banner.first" value='<span class="pagelinks"> [Đầu tiên/Trước] {0} [ <a href="{3}">Tiếp</a>/ <a href="{4}">Cuối cùng</a>] </span>'></display:setProperty>		
	<display:setProperty name="paging.banner.last" value='<span class="pagelinks">[ <a href="{1}">Đầu tiên</a>/ <a href="{2}">Trước</a>] {0} [Tiếp/Cuối cùng] </span>'></display:setProperty>								
	<display:setProperty name="paging.banner.full" value='	<span class="pagelinks"> [<a href="{1}">Đầu tiên</a>/ <a href="{2}">Trước</a>] {0} [ <a href="{3}">Tiếp</a>/ <a href="{4}">Cuối cùng </a>]</span>'></display:setProperty>												
   <display:column title="Tên bảng mã" sortable="true" maxLength="100">
	   	<c:url var="codeValUrl1" value="/servlet/codeMgmt/getcodevalues/${row.codeId}" />
		<a href="${codeValUrl1}"> <c:out value="${row.codeId}" /> </a>
   </display:column>   
   <display:column property="codeDesc" sortable="true" title="Mã mô tả bảng" maxLength="70" />  
   </display:table>   
   <br />	
</div>-->
</div>
</div>
</div>
<div style="display: flex;flex: 0 auto;justify-content: center;">
<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
	<div style="margin: 10px"> 			
		<button type="button" class="btn btn-success" id="add_button">
			 <span class="glyphicon glyphicon-plus"></span> Thêm mới mã
		</button>
	</div>
</div>
</div>
</div>
<!-- </div> -->
</form:form>

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
					document.forms["codeForm"].action = '${url}';  
					document.forms["codeForm"].submit();  
				}
			}
		});	
$("#add_button").click(function(){	
	 document.forms["codeForm"].action = '${frdReq}';
	 document.forms["codeForm"].submit();	

});
$("#search_btn_data").click(function(){
	if($('#codeId').val() == null || $('#codeId').val() == ''){
		$.notify('Xin hãy nhập thông tin tìm kiếm.', {
			globalPosition: 'bottom left',
			className: 'warn',
		});
    	 return;
	}
	document.forms["codeForm"].action = '${url}';
	document.forms["codeForm"].submit();	

});
</script>


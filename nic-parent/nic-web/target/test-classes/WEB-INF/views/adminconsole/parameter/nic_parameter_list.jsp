<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>


<c:url var="paramListUrl" value="/servlet/parameterController/getcodevalues"/>
<c:url var="editParamUrl" value="/servlet/parameterController/editParam"/>
<c:url var="url" value="/servlet/parameterController/addParam"/>
<c:url var="paramAdminUrl"
	value="/servlet/parameterController/getParamList" />
<style>
<!--

-->
table.displayTag>thead>tr {
	height: 35px;
}

.displayTag tr.odd {
	height: 35px;
}
.displayTag tr.even {
	height: 35px;
}
</style>
<script>
$(function(){

	$("#row tr").each(function() {
	    var deletedFlag = $(this).find("td:nth(5)").text();
	    if (deletedFlag == "true") {
	   	 	$(this).attr("class","darkred");	  
	    }
	 });
	
});

</script>


<form:form  modelAttribute="parameterForm"  id="parameterForm"  name="parameterForm"  method="GET" >
<div class="form-desing">
                        <div class="row">
                            <div class="ov_hidden">
<div class="content_main">
 <div class="new-heading-2">CẤU HÌNH THÔNG SỐ HỆ THỐNG</div>
  <!--<c:if test="${parameterForm.status=='success'}">
  <div class="border_success" >

<div class="success_left">
 <img align='left' src="<c:url value="/resources/images/success.jpg" />" width="30" height="30" style="padding:5px 0 0 15px"/>
 
 </div>
<div class="success">
 <table width="800" height="30" border="0" cellpadding="5">
<tr> 
    <td width="587" height="28" style="font-size: 18px; font-weight:bold; color: #000;"><p class="text_pad">&nbsp;<c:out value="${parameterForm.message}" /></p>
    
  </tr>
 </table>
</div>
</div>
</c:if>
<c:if test="${parameterForm.status=='fail'}">
<div class="border_error" >

<div class="success_left">
 <img align='left' src="<c:url value="/resources/images/alert.png" />"  width="30" height="30" style="padding:5px 0 0 15px"/>
 </div>
<div class="errors">

<table width="800" height="30" border="0" cellpadding="5">

  <tr>
    <td width="587" height="28" style="font-size: 18px; font-weight:bold; color: #000;"><p class="text_pad">&nbsp; <c:out value="${parameterForm.message}" /></p>
  </tr>
 
</table>

</div>
</div>
 </c:if> -->
 <fieldset>
			<legend>Điều kiện tìm kiếm</legend>
			<div class="form-group pad-top-10">
				<div class="col-sm-4">
		        	<label class="col-sm-4 control-label text-right">Tên tham số</label>
		        	<div class="col-sm-8 none-pad-left">
		         		<input type="text" class="wtd-100" name="paraName" value="${paraName}" />
		        	</div>
		        	
		        </div>
		        <div class="col-sm-4">
		        	<label class="col-sm-4 control-label text-right">Sắp xếp theo</label>
		        	<div class="col-sm-8 none-pad-left">
		         		<select id="subType" name="subType" style="width: 100%;" name="subType" value="${subType}">
							<option value="">-- Mặc định --</option>
							<option value="id.paraName">Tên tham số</option>
							<option value="createDate">Ngày tạo</option>
							<option value="updateDate">Ngày cập nhật</option>
							
						</select>
		        	</div>
		        </div>
		        <div class="col-sm-4">
		        	<button type="button" style="float: right;margin-right: 15px;" onclick="window.location='${paramAdminUrl}'" class="btn btn-success">
			        	<span class="glyphicon glyphicon-refresh"></span> Hủy tìm kiếm
			   		</button>
		        	<button type="button" style="float: right;margin-right: 15px;" onclick="doApplyFilter();" class="btn btn-success">
			        	<span class="glyphicon glyphicon-search"></span> Tìm kiếm
			   		</button>
			   		
		        </div>
		        
			</div>
		</fieldset>
 				<fieldset>
 					<legend>Danh sách tham số hệ thống</legend>
 			
							<div style="height: 400px;overflow: auto;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm">STT</th>	
								      <th class="th-sm">Tên 
								
								      </th>	
								      <th class="th-sm">Giá trị
								
								      </th>
								      <th class="th-sm">Mô tả
								
								      </th>
								      <th class="th-sm">Phạm vi
								
								      </th>
								      <th class="th-sm">Ngày tạo ra
								
								      </th>
								      <th class="th-sm">Ngày cập nhật
								
								      </th>
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>
									      <td align="center">${item.stt}</td>								    
									      <td><c:url var="editParamUrl1" value="/servlet/parameterController/editParam/${item.id.paraScope}&&${item.id.paraName}&&${item.paraType}&&${item.systemId}" />
												<a style="color: blue;" href="${editParamUrl1}"> <c:out value="${item.id.paraName}" /> </a></td>
									      <td>${item.paraValue}</td>
									      <td>${item.paraDesc}</td>
									      <td>${item.id.paraScope}</td>	
									      <td align="center">${item.createDate}</td>
									      <td align="center">${item.updateDate}</td>								     
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
  <!--<div id="paramTable" class="displayTag" style="background-image:url('<%=request.getContextPath() %>/resources/images/head.png');background-repeat: repeat-x;">
        

			
   <display:table cellspacing="1" cellpadding="0" id="row" sort="external"  partialList="true" size="totalRecords" export="false" class="displayTag" name="paramList" 
   defaultsort="1"  pagesize="${pageSize}" requestURI="/servlet/parameterController/getParamList" >
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
   <display:column sortable="true" sortName="id.paraName" title="Tên" maxLength="50" >
		<c:url var="editParamUrl1" value="/servlet/parameterController/editParam/${row.id.paraScope}&&${row.id.paraName}&&${row.paraType}&&${row.systemId}" />
		<a href="${editParamUrl1}"> <c:out value="${row.id.paraName}" /> </a>
	</display:column>
   <display:column property="paraValue" sortable="true" sortName="paraValue" title="Giá trị" maxLength="50" />
   <display:column property="paraDesc" sortable="true" sortName="paraDesc" title="Mô tả" maxLength="50" />
   <display:column property="id.paraScope" title="Phạm vi"  sortable="true" sortName="id.paraScope" /> 
   <display:column property="createDate" sortable="true" sortName="createDate" title="Ngày tạo ra" maxLength="50"  format="{0,date,dd-MMM-yyyy}"   />
   <display:column property="updateDate" sortable="true" sortName="updateDate" title="Ngày cập nhật" maxLength="50"   format="{0,date,dd-MMM-yyyy}" /> 
          
   </display:table>
    
   <br />	
</div>-->

</div>
<div style="display: flex;flex: 0 auto;justify-content: center;">
<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
	<div style="margin: 10px"> 			
		<button type="button" class="btn btn-success" id="add_button">
			<span class="glyphicon glyphicon-plus"></span> Thêm mới thông số
		</button>
	</div>
</div>
</div>
</div>
</div>
</div>
</form:form>
<%-- <form:hidden path="codeId" id="codeId" />
<form:hidden path="codeValue" id="codeValue" /> --%>


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
					document.forms["parameterForm"].action = '${paramAdminUrl}';  
					document.forms["parameterForm"].submit();  
				}
			}
		});	

$("#add_button").click(function(){	
	 document.forms["parameterForm"].action = '${url}';
	 document.forms["parameterForm"].submit();	

});

$(document).ready(function() {
	var loadMeg = '${parameterForm.status}';
	//alert(loadMeg);
	if(loadMeg == 'success'){
		var meg = '${parameterForm.message}';
		$.notify(meg, {
			globalPosition: 'bottom left',
			className: 'success',
		});
	}else if(loadMeg == 'fail'){
		var meg = '${parameterForm.message}';
		$.notify(meg, {
			globalPosition: 'bottom left',
			className: 'warn',
		});
	}
});
function doApplyFilter(){
	document.forms["parameterForm"].action = '${paramAdminUrl}';  
	document.forms["parameterForm"].submit();  
}
/* function searchSiteRepo() {
    document.forms["siteRepoForm"].action = '${searchRepoUrl}';
	document.forms["siteRepoForm"].submit();
}

$("#reset_button").click(function() {
	$("#searchText").attr('value', '');
	document.forms["siteRepoForm"].action = '${siteRepoUrl}/'+$("#groupId").val();
	document.forms["siteRepoForm"].submit();
});

 $("#searchText").keypress(function (e) {
     if (e.keyCode === 13) {
    	 searchSiteRepo();
     }
 }); */

</script>
 
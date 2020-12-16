<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url value="/servlet/adminConsole/roleManagement/addEditRole" var="add" />
<c:url var="roleJobListUrl" value="/servlet/adminConsole/roleManagement" />
<style>

</style>
<script type="text/javascript">
	var idRole = '';
	function del(id){
		/*$.confirm({
		    title: 'Thông báo',
		    content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/tip_1a.png\">" + ' Bạn có chắc chắn muốn xóa quyền : ' + id + '?',
		    buttons: {
		        OK: function () {
		        	if(id == '${sessionScope.userSession.userName}'){
						
						$.alert({
							title: 'Thông báo',
							content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Bạn không được phép xóa vai trò hiện tại!",
						});
						return;
					}	
					document.getElementById("deleteId").value=id;
					document.getElementById("deleteForm").submit();
		        },
		        CANCEL: function () {
		        	
		        }		       
		    }
		})*/
		idRole = id;
		$('#messageLHS').modal();
		
	}
	
	function processDel(){
		if(idRole == '${sessionScope.userSession.userName}'){
			$.notify('Bạn không được phép xóa quyền hiện tại', {
				globalPosition: 'bottom left',
				className: 'warn',
			});
			return;
		}
		document.getElementById("deleteId").value=idRole;
		document.getElementById("deleteForm").submit();
	}
</script>
<div class="form-desing">
<div>
                        <div class="row">
                            <div class="ov_hidden">
							<div class="new-heading-2">QUẢN LÝ PHÂN QUYỀN</div>
 							
 							<!--<input type="button" id="addBtn-1" onclick="window.location='${add}'" class="btn-sm btn-success" value="Thêm mới quyền" style="float: right;margin-top: 15px;margin-right: 3px;"/>-->
<div style="float: none;margin-left: auto;margin-right: auto;">

	<!--<c:if test="${empty roles || empty roles.rows || roles.rowSize le 0}">
			Không có kết quả để hiển thị.		
	</c:if>-->
	
	<!--<c:if test="${not empty requestScope.Errors}">
		<div style="color:Red;background-color:White;border-style:solid;border-color:Red;border-width:thin;">
			<c:forEach items="${requestScope.Errors}" var="takla">
				<li>
					${takla}
				</li>
			</c:forEach>
			
		</div>
	</c:if>
	
	<c:if test="${not empty requestScope.messages}">
		<div style="color:green;background-color:White;border-style:solid;border-color:green ;border-width:thin;">
			<c:forEach items="${requestScope.messages}" var="takla">
				<li>
					${takla}
				</li>
			</c:forEach>
			
		</div>
	</c:if>-->
	<!--<div style="background-image:url('<%=request.getContextPath() %>/resources/images/head.png');background-repeat: repeat-x;">
		<c:if test="${not empty roles || not empty roles.rows || roles.rowSize gt 0}">-->
		
			<form:form id="formData" name="formData">
		<input type="hidden" name="pageNo" id="pageNo" /> 
		<fieldset>
			<legend>Điều kiện tìm kiếm</legend>
			<div class="form-group pad-top-10">
				<div class="col-sm-4">
		        	<label class="col-sm-4 control-label text-right">Tên quyền</label>
		        	<div class="col-sm-8 none-pad-left">
		         		<input type="text" class="wtd-100" name="roleId" value="${roleId}" />
		        	</div>
		        </div>
		        <div class="col-sm-4">
		        	<button type="button" style="float: right;margin-right: 15px;" onclick="window.location='${roleJobListUrl}'" class="btn btn-success">
			        	<span class="glyphicon glyphicon-refresh"></span> Hủy tìm kiếm
			   		</button>
		        	<button type="button" style="float: right;margin-right: 15px;" onclick="doApplyFilter();" class="btn btn-success">
			        	<span class="glyphicon glyphicon-search"></span> Tìm kiếm
			   		</button>
			   		
		        </div>
		        
			</div>
		</fieldset>
	</form:form>
		
			<c:url value='/servlet/adminConsole/roleManagement' var="ebark"/>
						<fieldset>
							<legend>Danh sách nhóm quyền</legend>
						
							<div style="height: 400px;overflow: auto;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm">STT
								
								      </th>	
								      <th class="th-sm">Tên quyền
								
								      </th>	
								      <th class="th-sm">Mô tả quyền
								
								      </th>
								      <th class="th-sm">Ngày tạo ra
								
								      </th>
								      <th class="th-sm">Ngày cập nhật
								
								      </th>
								      <th class="th-sm">Hiệu lực 
								
								      </th>
								      <th class="th-sm">Thao tác
								
								      </th>
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>
									      <td align="center">${item.stt}</td>								    
									      <td>${item.roleId}</td>
									      <td>${item.roleDesc}</td>
									      <td align="center">${item.createDate}</td>
									      <td align="center">${item.updateDate}</td>
									      <td align="center">
									      	  <c:if test="${item.delFlag == 'Y'}">
									      	  	  <input type="checkbox" disabled="disabled" name="chk_del" />
									      	  </c:if>
									      	  <c:if test="${item.delFlag == 'N'}">
									      	  	  <input type="checkbox" disabled="disabled" checked="checked" name="chk_del" />
									      	  </c:if>
									      </td>
									      <td align="center">
									      		<c:url var="addEditRole1" value="/servlet/adminConsole/roleManagement/addEditRole/${item.roleId}" />
									      		<a href="${addEditRole1}" title="Mã quyền: ${item.roleId}">
									      			<i class="glyphicon glyphicon-pencil"></i> Sửa
									      		</a>
									      		<a href="javascript:del('${item.roleId}');" title="Mã quyền: ${item.roleId}">
									      			<i class="glyphicon glyphicon-trash"></i> Xóa
									      		</a>
									      </td>
									      <!--<td>
									      		<c:url var="addEditRole1" value="/servlet/adminConsole/roleManagement/addEditRole/${item.roleId}" />
												<a href="${addEditRole1}" title="Mã quyền: ${item.roleId}">
													<img src="<c:url value="/resources/images/edit.gif"/>" border="0" width="16" height="16" />
												</a>
									      </td>
									      <td>
									      		<a href="javascript:del('${item.roleId}');" title="Mã quyền: ${item.roleId}">
													<img src="<c:url value="/resources/images/delete.gif"/>" border="0" width="16" height="16" />
												</a>
									      </td>	-->								          						     
									    </tr>
								    </c:forEach>
								  </tbody>
								</table>
								<!--<div class="col-md-5 col-sm-12" style="padding-left: 0px;">
									<div class="dataTables_info">Hiển thị ${startIndex} đến ${endIndex} của ${totalRecord} kết quả</div>
								</div>
								<div class="col-md-7 col-sm-12" style="padding-right: 0px;">
									<ul style="float: right;" class="pagination" id="pagination"></ul>
								</div>-->			 
								
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
		<!--</c:if>-->
		<c:url var="delete" value="/servlet/adminConsole/roleManagement/delete"  />
		<form action="${delete}" id="deleteForm" method="Post">
			<input type="hidden" id="deleteId" name="deleteId">
		</form>
		
		<!--<div id="addBtnTabl" align="right" class="displayTag" style=
		"background-image:url('<%--=request.getContextPath()--%>/resources/images/head.png');background-repeat: repeat-x;">
			<table>
				<tr>
					<td colspan="5" align="center" style="padding: 20px;"><input
						type="button" value="Thêm" style="width: 100px;"
						class="btn_small btn-primary" onclick="window.location='${add}'" /></td>
				</tr>
			</table>
		</div>-->
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
		      			<p style="display: inline-block;position: absolute;margin-left: 10px;margin-top: 5px;">Bạn có muốn xóa quyền này?</p>
		      		</div>
		      </div>							      
		      <div class="modal-footer" style="padding: 5px;">
		       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
		       			<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> KHÔNG</span>
		       		</button>
		       		<button type="button" onclick="processDel();" class="btn btn-success" data-dismiss="modal" aria-label="Close" style="float: right;margin-right: 10px;">
		       			<span class="glyphicon glyphicon-ok btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> CÓ</span>
		       		</button>
		       		
		      </div>
		    </div>
		  </div>
		</div>	
		<!-- ---------------------------------------------------------------------------->
</div>
</div>
</div>
</div>
<!--<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
			<div style="margin: 10px"> 			
				<button type="button" class="btn btn-success" id="addBtn-1" onclick="window.location='${add}'">
					<span class="glyphicon glyphicon-plus-sign"></span> Thêm mới quyền
				</button>
			</div>
</div>-->
<div style="display: flex;flex: 0 auto;justify-content: center;">
<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
	<div style="margin: 10px"> 			
		<button type="button" class="btn btn-success" id="addBtn-1" onclick="window.location='${add}'">
			<span class="glyphicon glyphicon-plus"></span> Thêm mới quyền
		</button>
	</div>
</div>
</div>
</div>
<script type="text/javascript">
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
					document.forms["formData"].action = '${roleJobListUrl}';  
					document.forms["formData"].submit();  
				}
			}
		});	
$(document).ready(function() {
	var error1 = '${Errors}';
	if(error1 != ''){
		$.notify(error1, {
			globalPosition: 'bottom left',
			className: 'warn',
		});
	}
	
	var mess1 = '${messages}';
	if(mess1 != ''){
		$.notify(mess1, {
			globalPosition: 'bottom left',
			className: 'success',
		});
	}
});
function doApplyFilter(){
	document.forms["formData"].action = '${roleJobListUrl}';  
	document.forms["formData"].submit();  
}

</script>
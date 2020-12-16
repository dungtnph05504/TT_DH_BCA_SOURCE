<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<c:url var="userQueryUrl" value="/servlet/adminConsole/userManagement/query_ajax"/>
<c:url var="changePass" value="/servlet/adminConsole/userManagement/changePass"  />
<c:url var="addEditUserUrl1" value="/servlet/adminConsole/userManagement/addEditUser/" />
<c:url var="delete" value="/servlet/adminConsole/userManagement/delete"  />
<c:url value="/servlet/adminConsole/userManagement/addEditUser" var="add" />
<c:url var="userJobListUrl" value="/servlet/adminConsole/userManagement" />

<style>

/* When the body has the loading class, we turn
   the scrollbar off with overflow:hidden */
body.loading {
	overflow: hidden;
}

/* Anytime the body has the loading class, our
   modal element will be visible */
body.loading .modal {
	display: block;
}
.wtd-100 {
	width: 100%;
}
 </style>
<script type="text/javascript">

</script>
<%-- <form:form id="nicReportForm"   method="GET" cssClass="inline"> --%>

<div class="form-desing">
<div>
                        <div class="row">
                            <div class="ov_hidden">
							<div class="new-heading-2">DANH SÁCH NGƯỜI DÙNG HỆ THỐNG</div>
	

<div style="float: none;margin-left: auto;margin-right: auto;">
	<form action="${changePass}" id="changePassForm" method="Post">
		<input type="hidden" id="changePasswordId" name="changePasswordId" />
	</form>

	<form  id="editUserForm" method="Post">
		<!-- <input type="hidden" id="changePasswordId" name="changePasswordId" /> -->
	</form>
	
	<form action="${delete}" id="deleteForm" method="Post">
		<input type="hidden" id="deleteId" name="deleteId" />
	</form>
	<form:form id="formData" name="formData">
		<input type="hidden" name="pageNo" id="pageNo" /> 
		<fieldset>
			<legend>Điều kiện tìm kiếm</legend>
			<div class="form-group pad-top-10">
				<div class="col-sm-4">
		        	<label class="col-sm-4 control-label text-right">Tên cán bộ</label>
		        	<div class="col-sm-8 none-pad-left">
		         		<input type="text" class="wtd-100" name="nameUser" value="${nameUser}" />
		        	</div>
		        </div>
		        <div class="col-sm-4">
		        	<label class="col-sm-4 control-label text-right">Tên đăng nhập</label>
		        	<div class="col-sm-8 none-pad-left">
		         		<input type="text" class="wtd-100" name="loginUser" value="${loginUser}" />
		        	</div>
		        </div>
		        <div class="col-sm-4">
		        	<label class="col-sm-4 control-label text-right">Đơn vị</label>
		        	<div class="col-sm-8 none-pad-left">
		         		<select name="siteName" class="wtd-100">
		         			<option value="">Tất cả</option>
		         			<c:forEach items="${siteList}" var="_item">
		         				<c:choose>
		         					<c:when test="${_item.key == siteName}">
					         			<option selected="selected" value="${_item.key}">${_item.value}</option>	
		         					</c:when>
		         					<c:otherwise>
		         						<option value="${_item.key}">${_item.value}</option>	
		         					</c:otherwise>
		         				</c:choose>
		         			</c:forEach>
		         		</select>
		        	</div>
		        </div>
			</div>
			<div class="form-group pad-top-10">
		        <div class="col-sm-4">
		        	<label class="col-sm-4 control-label text-right">Hiệu lực</label>
		        	<div class="col-sm-8 none-pad-left">
		         		<select name="stageUser" class="wtd-100">
		         			<c:forEach items="${stageList}" var="_item">
			         			<c:choose>
		         					<c:when test="${_item.key == stageUser}">
					         			<option selected="selected" value="${_item.key}">${_item.value}</option>	
		         					</c:when>
		         					<c:otherwise>
		         						<option value="${_item.key}">${_item.value}</option>	
		         					</c:otherwise>
		         				</c:choose>
		         			</c:forEach>
		         		</select>
		        	</div>
		        </div>
		        <div class="col-sm-8">
		        	<button type="button" style="float: right;margin-right: 15px;" onclick="doApplyFilter();" class="btn btn-success">
			        	<span class="glyphicon glyphicon-search"></span> Tìm kiếm
			   		</button>
		        </div>
			</div>
		</fieldset>
	</form:form>
	<fieldset>
		<legend>Danh sách cán bộ</legend>
	
							<div style="height: 400px;overflow: auto;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="max-width: 40px;">STT
								
								      </th>	
								      <th class="th-sm">Tên cán bộ
								
								      </th>
								      <th class="th-sm">Tên đăng nhập
								
								      </th>
								      <th class="th-sm">Nhóm quyền
								
								      </th>
								      <!--<th class="th-sm">Phòng ban
								
								      </th>-->
								      <th class="th-sm">Chức vụ
								
								      </th>
								      <th class="th-sm">Đơn vị
								
								      </th>
								      <th class="th-sm">Ngày hiệu lực
								
								      </th>
								      <!--<th class="th-sm" style="max-width: 80px;">Ký duyệt
								
								      </th>-->
								      <th class="th-sm" style="max-width: 80px;">Hiệu lực
								
								      </th>
								      <th class="th-sm" style="width: 200px;">Thao tác
								
								      </th>
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>							    
									      <td align="center">${item.stt}</td>
									      <td>${item.userName}</td>
									      <td>${item.userId}</td>
									      <td>${item.roles}</td>
									      <!--<td></td>-->
									      <td>${item.position}</td>
									      <td>${item.siteCode}</td>
									      <td>${item.createDate}</td>
									      <!--<td align="center"><input type="checkbox" disabled="disabled" name="chk_approve" /></td>-->
									      <td align="center">
									      	  <c:if test="${item.deleteFlag == 'Y'}">
									      	  	  <input type="checkbox" disabled="disabled" name="chk_del" />
									      	  </c:if>
									      	  <c:if test="${item.deleteFlag == 'N'}">
									      	  	  <input type="checkbox" disabled="disabled" checked="checked" name="chk_del" />
									      	  </c:if>	
									      </td>
									      <td align="center">
									      		<a onclick="changePass('${item.userId}')" href="#">
									      			<i class="glyphicon glyphicon-edit"></i> Đổi mật khẩu
									      		</a>
									      		<a onclick="editUser('${item.userId}')" href="#">
									      			<i class="glyphicon glyphicon-pencil"></i> Sửa
									      		</a>
									      		<a onclick="del('${item.userId}')" href="#">
									      			<i class="glyphicon glyphicon-trash"></i> Xóa
									      		</a>
									      </td>
									      <!--<td><a href="#" onclick="changePass('${item.userId}')"> Đặt lại mật khẩu </a></td>
									      <td><a href="#" onclick="editUser('${item.userId}')"> <img src='<c:url value='/resources/images/edit.gif'/>' border='0' width='16' height='16' /> </a></td>
									      <td><a href="#" onclick="del('${item.userId}')"> <img src='<c:url value='/resources/images/delete.gif'/>' border='0' width='16' height='16' /></a></td>-->									     						     
									    </tr>
								    </c:forEach>
								  </tbody>
								</table>
								<!--<div class="col-md-5 col-sm-12" style="padding-left: 0px;">
									<div class="dataTables_info">Hiển thị ${startIndex} đến ${endIndex} của ${totalRecord} kết quả</div>
								</div>
								<div class="col-md-7 col-sm-12" style="padding-right: 0px;">
									<ul style="float: right;" class="pagination" id="pagination"></ul>
								</div>	-->		 
								
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
	
	<!--<div id="fortable" style="display: none">
		<table id="statResult">
		   <tr>
				<td colspan=4>&nbsp;</td>
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
	      			<p style="display: inline-block;position: absolute;margin-left: 10px;margin-top: 5px;">Bạn có muốn xóa người dùng này?</p>
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
</div>
</div>
</div>
</div>
</div>
<div style="display: flex;flex: 0 auto;justify-content: center;">
<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
	<div style="margin: 10px"> 			
		<button type="button" class="btn btn-success" onclick="window.location='${add}'">
			<span class="glyphicon glyphicon-plus"></span> Thêm mới người dùng
		</button>
	</div>
</div>
</div>
<div id="dialog-confirm"></div>
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
					document.forms["formData"].action = '${userJobListUrl}';  
					document.forms["formData"].submit();  
				}
			}
		});	
	function doApplyFilter(){
		document.forms["formData"].action = '${userJobListUrl}';  
		document.forms["formData"].submit();  
	}
	
	function del(id){		
		
		if(id == '${sessionScope.userSession.userName}'){
			//alert("Bạn không được phép xóa việc sử dụng hiện tại!!!");
			//$.alert({
				//title: 'Thông báo',
				//content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Bạn không được phép xóa việc sử dụng hiện tại!",
			//});
			$.notify('Bạn không được phép xóa người dùng hiện tại!', {
				globalPosition: 'bottom left',
				className: 'warn',
			});
			return;
		}
		//$("#dialog-confirm").dialog('option', 'title', 'Xóa người dùng');
	   // $("#dialog-confirm").html("Bạn có chắc chắn muốn xóa người dùng : "+ id +"?");
	    //$("#dialog-confirm").dialog( 'open' );	
	    $('#messageLHS').modal();
		document.getElementById("deleteId").value=id;	
	}
	
	function changePass(id){
			document.getElementById("changePasswordId").value=id;
			document.getElementById("changePassForm").submit();
	}

	function editUser(id){
		document.forms["editUserForm"].action = '${addEditUserUrl1}' + id;
	    document.forms["editUserForm"].submit();
	}

	  /*$("#statResult").flexigrid({			
		url: "${userQueryUrl}",
		dataType : 'json',
		colModel : [
					{display: '<spring:message code="label.table.col.userName" text="Tên người dùng" />', name : 'userId', width : 300, sortable : true, align: 'left'},				
					{display: '<spring:message code="label.table.col.firstName" text="Họ" />', name : 'firstName', width : 340, sortable : false, align: 'left'},
					{display: '<spring:message code="label.table.col.createdDate" text="Ngày tạo ra" />', name : 'fmtCreateDate', width : 172, sortable : false,  align: 'center'},
					{display: '<spring:message code="label.table.col.updateDate" text="Ngày cập nhật" />', name : 'fmtUpdateDate', width : 172, sortable : false,  align: 'center'},					
					{display: '<spring:message code="label.table.col.controls" text="Kiểm soát" />', width : 150, sortable : false, align: 'center',render: resetPwdLink},
					{display: '<spring:message code="label.table.col.edit" text="Sửa" />', width : 100, sortable : false, align: 'center',render: editLink},
					{display: '<spring:message code="label.table.col.delete" text="Xóa" />', width : 100, sortable : false, align: 'center',render: deleteLink}
					//{display: 'Date', width : 172, sortable : false, align: 'left', render: formatCreatedDateLink}						
				],
		searchitems : [
			{display: 'Tên đăng nhập', name : 'userId', isdefault: true}
			],

		sortname: "userId",
		sortorder: "asc",
		 title : '<spring:message code="label.table.title.user_mgmt" text="Quản lý người dùng" />',
		 usepager : true,
		 useRp : true,
		 rp : 10,
		 showTableToggleBtn : true,   
		 height: 250,
		 singleSelect : true,
		 nowrap : false		
	});*/

	 function formateDate(date){
		 
				return "";
	}
		
	function formatCreatedDateLink(content, currentRow){	
		//alert(currentRow.createDate);
		$.alert({
			title: 'Thông báo',
			content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + currentRow.createDate,
		});
		return formateDate(currentRow.createDate);		
	} 

	function formatUpdatedDateLink(content, currentRow){		
		return formateDate(currentRow.updateDate);
	} 
		
	function resetPwdLink(content, currentRow){		
		return "<a href=\"#\" onclick=\"changePass('" + currentRow.userId +"')\"> Đặt lại mật khẩu </a>";
	} 
	
	function editLink(content, currentRow){		
		return "<a href=\"#\" onclick=\"editUser('" + currentRow.userId +"')\"> <img src='<c:url value='/resources/images/edit.gif'/>' border='0' width='16' height='16' /> </a>";
	} 	
	
	function deleteLink(content, currentRow){		
		return "<a href=\"#\" onclick=\"del('" + currentRow.userId +"')\"> <img src='<c:url value='/resources/images/delete.gif'/>' border='0' width='16' height='16' /></a>";
	} 
	
	function deleteForm(){
		document.getElementById("deleteForm").submit();
	}

	$(function() {
	    $( "#dialog-confirm" ).dialog({
		modal: true,
	      autoOpen: false,
		  width : 500,
		  resizable: true,
	      show: {
	        effect: "fade",
	        duration: 200
	      },
	      hide: {
	        //effect: "explode",
	        //duration: 1000
	      },
		   buttons:{
		    "Đồng ý": function() {
		    	$(this).dialog("close");
		    	document.getElementById("deleteForm").submit();
		    },
			"Hủy": function() {
				$(this).dialog("close");
		    }
	 	  }
	    });
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
 </script>
  <%-- </form:form> --%>
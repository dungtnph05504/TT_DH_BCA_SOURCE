<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<c:url var="workstationQueryUrl" value="/servlet/adminConsole/workstationManagement/query_ajax"/>
<c:url var="delete" value="/servlet/adminConsole/workstationManagement/delete"/>
<c:url var="edit" value="/servlet/adminConsole/workstationManagement/addEditWorkStation/"/>
<c:url value="/servlet/adminConsole/workstationManagement/addEditWorkStation" var="add" />
<c:url var="workstationJobListUrl"
	value="/servlet/adminConsole/workstationManagement" />
<style>
table#row > tr {
	height: 35px;
}
.displayTag tr.odd {
	height: 35px;
}
.displayTag tr.even {
	height: 35px;
}
</style>
<script type="text/javascript">

</script>
<div class="form-desing">
<div>
                        <div class="row">
                            <div class="ov_hidden">
							<div class="new-heading-2">QUẢN LÝ MÁY TRẠM</div>
							
							<!--<input type="button" id="addBtn-1" onclick="window.location='${add}'" class="btn-sm btn-success" value="Thêm mới máy trạm" style="margin-top: -30px;margin-left: 1033px;font-weight: 250;"/>-->
							</div>

<div style="float: none;margin-left: auto;margin-right: auto;">

	
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
	
	<!--<div id="fortable" style="display: none">
		<table id="statResult">
		   <tr>
				<td colspan=4>&nbsp;</td>
			</tr>
		</table>  
                               
	</div>	-->
	<fieldset>
		<legend>Danh sách máy trạm</legend>
	
							<div style="height: 400px;overflow: auto;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm">STT
								      
								      </th>	
								      <th class="th-sm">Mã máy trạm
								
								      </th>	
								      <th class="th-sm">Mô tả
								
								      </th>
								      <th class="th-sm">Ngày tạo
								
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
									      <td>${item.wkstnId}</td>
									      <td>${item.wkstnDesc}</td>
									      <td align="center">${item.fmtCreateDate}</td>
									      <td align="center">${item.fmtUpdateDate}</td>
									      <td align="center">
									      	  <c:if test="${item.delFlag == 'Y'}">
									      	  	  <input type="checkbox" disabled="disabled" name="chk_del" />
									      	  </c:if>
									      	  <c:if test="${item.delFlag == 'N'}">
									      	  	  <input type="checkbox" disabled="disabled" checked="checked" name="chk_del" />
									      	  </c:if>
									      </td>	
									      <td align="center">		
									      		<a href="#" onclick="editWorkstation('${item.wkstnId}')">
									      			<i class="glyphicon glyphicon-pencil"></i> Sửa
									      		</a>
									      		<a href="#" onclick="del('${item.wkstnId}')">
									      			<i class="glyphicon glyphicon-trash"></i> Xóa
									      		</a>
									      </td>							     
									      <!--<td><a href="#" onclick="editWorkstation('${item.wkstnId}')"> <img src='<c:url value='/resources/images/edit.gif'/>' border='0' width='16' height='16' /> </a></td>
									      <td><a href="#" onclick="del('${item.wkstnId}')"> <img src='<c:url value='/resources/images/delete.gif'/>' border='0' width='16' height='16' /></a></td>	-->								     						     
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
	<form action="${delete}" id="deleteForm" method="Post">
		<input type="hidden" id="deleteId" name="deleteId">
	</form>
	<form:form id="formData" name="formData">
		<input type="hidden" name="pageNo" id="pageNo" /> 
	</form:form>
	
	<form id="editForm" method="Post" > </form>
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
	      			<p style="display: inline-block;position: absolute;margin-left: 10px;margin-top: 5px;">Bạn có muốn xóa máy trạm này?</p>
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
<div style="display: flex;flex: 0 auto;justify-content: center;">
<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
	<div style="margin: 10px"> 			
		<button type="button" class="btn btn-success" onclick="window.location='${add}'" id="addBtn-1">
			<span class="glyphicon glyphicon-plus"></span> Thêm mới máy trạm
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
					document.forms["formData"].action = '${workstationJobListUrl}';  
					document.forms["formData"].submit();  
				}
			}
		});
	var idWs = '';
	function del(id){
		/*$.confirm({
		    title: 'Thông báo',
		    content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/tip_1a.png\">" + ' Bạn có chắc chắn muốn xóa máy trạm : ' + id + '?',
		    buttons: {
		        OK: function () {
		        	if(id == '${sessionScope.userSession.workstationId}'){
						//alert("Bạn không được phép xóa máy trạm hiện tại!!!");
						$.alert({
							title: 'Thông báo',
							content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Bạn không được phép xóa máy trạm hiện tại!",
						});
						return;
					}	
					document.getElementById("deleteId").value=id;
					document.getElementById("deleteForm").submit();
		        },
		        CANCEL: function () {
		        	//return false;
		        }		       
		    }
		})*/
		idWs = id;
		$('#messageLHS').modal();
	}	
	
	function processDel(){
		if(idWs == '${sessionScope.userSession.workstationId}'){			
			$.notify('Bạn không được phép xóa máy trạm hiện tại.', {
				globalPosition: 'bottom left',
				className: 'warn',
			});
			return;
		}	
		document.getElementById("deleteId").value=idWs;
		document.getElementById("deleteForm").submit();
	}

	function editWorkstation(id){
		document.forms["editForm"].action = '${edit}' + id;
	    document.forms["editForm"].submit();
	}

	$("#statResult").flexigrid({			
		url: "${workstationQueryUrl}",
		dataType : 'json',
		colModel : [
					{display: 'Mã máy trạm', name : 'wkstnId', width : 300, sortable : true, align: 'left'},				
					{display: 'Mô tả', name : 'wkstnDesc', width : 340, sortable : true, align: 'left'},
					{display: 'Ngày tạo', name : 'fmtCreateDate', width : 172, sortable : false,  align: 'left'},
					{display: 'Ngày cập nhật', name : 'fmtUpdateDate', width : 172, sortable : false,  align: 'left'},		
					{display: 'Sửa', width : 100, sortable : false, align: 'left',render: editLink},
					{display: 'Xóa', width : 100, sortable : false, align: 'left',render: deleteLink}					
				],
		searchitems : [
			{display: 'Mã máy trạm', name : 'wkstnId', isdefault: true}
			],

		sortname: "wkstnId",
		sortorder: "asc",
		 title : 'Quản lý máy trạm',
		 usepager : true,
		 useRp : true,
		 rp : 10,
		 showTableToggleBtn : true,   
		 height: 250,
		 singleSelect : true,
		 nowrap : false		
	}); 

	function formatUpdatedDateLink(content, currentRow){		
		return formateDate(currentRow.updateDate);
	} 
		
	function editLink(content, currentRow){		
		return "<a href=\"#\" onclick=\"editWorkstation('" + currentRow.wkstnId +"')\"> <img src='<c:url value='/resources/images/edit.gif'/>' border='0' width='16' height='16' /> </a>";
	} 	
	
	function deleteLink(content, currentRow){		
		return "<a href=\"#\" onclick=\"del('" + currentRow.wkstnId +"')\"> <img src='<c:url value='/resources/images/delete.gif'/>' border='0' width='16' height='16' /></a>";
	} 

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
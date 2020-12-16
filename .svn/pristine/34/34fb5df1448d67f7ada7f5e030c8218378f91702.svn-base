<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<c:url var="siteRepoUrl" value="/servlet/siteMgmt/getSiteRepo" />
<c:url var="addSiteRepoUrl" value="/servlet/siteMgmt/addSiteRepo" />
<c:url var="delSiteRepoUrl" value="/servlet/siteMgmt/delSiteRepo" />
<c:url var="delSiteGroupUrl" value="/servlet/siteMgmt/delSiteGroup" />
<c:url var="searchRepoUrl" value="/servlet/siteMgmt/searchRepo" />
<c:url var="getRepoUrl" value="/servlet/siteMgmt/getSiteRepo" />
<c:url var="url" value="/servlet/siteMgmt/view" />
<style>
</style>
<form:form modelAttribute="siteRepoForm" id="siteRepoForm" name="siteRepoForm">
	<!-- <div id="main"> -->
		<div class="form-desing">
		<div>
        	<div class="row">
        		<div class="ov_hidden">
			<div class="new-heading-2">QUẢN LÝ TRUNG TÂM</div> 
			<div>
				<fieldset class="scheduler-border">
					<legend>Điều kiện tìm kiếm</legend>
					<div class="form-group" style="margin-top: 10px;">
						<div class="col-sm-3">
							<label class="col-sm-6 control-label text-right" style="margin-top: 4px">Mã cơ quan cấp HCĐT:</label>
							<div class="col-sm-6">
								<form:input path="siteId"  class="style-width-50" id="siteCode" placeholder="" type="text"/>
							</div>
						</div>
						<div class="col-sm-3">
							<button type="button" style="float: right;margin-right: 15px;width: 100px;" onclick="searchSiteRepo();" class="btn btn-success">
					        	<span class="glyphicon glyphicon-search"></span> Tìm kiếm
					   		</button>
						</div>
					</div>
				</fieldset>
			</div> 				
			<c:if test="${siteRepoForm.status=='success'}">
				<div class="border_success">
					<!--<div class="success"> -->
					<div class="success_left">
						<img align='left' src="<c:url value="/resources/images/success.jpg" />" width="30" height="30" style="padding:5px 0 0 15px"/>
					</div>
					<div class="success">
						<table width="800" height="30" border="0" cellpadding="5">
							<tr>
								<td width="587" height="28" style="font-size: 18px; font-weight: bold; color: #000;">
									<p class="text_pad">&nbsp;<c:out value="${siteRepoForm.message}" /></p>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</c:if>
			<c:if test="${siteRepoForm.status=='fail'}">
				<div class="border_error">
					<!--<div class="errors"> -->
					<div class="success_left">
						<img align='left' src="<c:url value="/resources/images/alert.png" />" width="30" height="30" style="padding:5px 0 0 15px"/>
					</div>
					<div class="errors">
						<table width="800" height="30" border="0" cellpadding="5">
							<tr>
								<td width="587" height="28" style="font-size: 18px; font-weight: bold; color: #000;">
									<p class="text_pad">&nbsp; <c:out value="${siteRepoForm.message}" /></p>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</c:if>
			<c:if test="${siteRepoForm.status=='added'}">
				<div class="border_error">
					<!--<div class="errors"> -->
					<div class="success_left">
						<img align='left' src="<c:url value="/resources/images/alert.png" />" width="30" height="30" style="padding:5px 0 0 15px"/>
					</div>
					<div class="errors" style="height: 18px">
						<table width="800" height="30" border="0" cellpadding="5">
							<tr>
								<td width="587" height="28" style="font-size: 18px; font-weight: bold; color: #000;">Thêm mới thành công :
									<p class="text_pad">
										&nbsp;<c:out value="${siteRepoForm.siteId}" />
									</p>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</c:if>
							<div style="height: 400px;overflow: auto;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm">Nhóm
								
								      </th>	
								      <th class="th-sm">Trung tâm
								
								      </th>
								      <th class="th-sm">Quốc gia
								
								      </th>
								      <th class="th-sm">Thành phố
								
								      </th>	
								      <th class="th-sm">Địa chỉ
								
								      </th>	
								      <th class="th-sm">Thẩm quyền
								
								      </th>	
								      <th class="th-sm">Thao tác
								
								      </th>								     
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>							    
									      <td>${item.siteGroups.groupName}</td>
									      <td>${item.siteName}</td>	
									      <td>${item.country}</td>	
									      <td>${item.city}</td>	
									      <td>${item.address}</td>	
									      <td>${item.authority}</td>	
									      <td align="center">
									      		<c:url var="editSiteRepoUrl1" value="/servlet/siteMgmt/editSiteRepo/${item.siteId}" />
									      		<a onclick="doEditSiteRepo('${item.siteId}')" href="#">
									      			<i class="glyphicon glyphicon-pencil"></i> Sửa
									      		</a>
									      		<a href="javascript:del('${item.siteId}');">
									      			<i class="glyphicon glyphicon-trash"></i> Xóa
									      		</a>
									      </td>							     
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
		</div>
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
		      			<p style="display: inline-block;position: absolute;margin-left: 10px;margin-top: 5px;">Bạn có muốn xóa trung tâm này?</p>
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

		<div style="display: flex;flex: 0 auto;justify-content: center;">
		<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
			<div style="margin: 10px"> 			
				<button type="button" class="btn btn-success" onclick="cancelPag();" id="can_button">
					<span class="glyphicon glyphicon-remove"></span> Quay lại
				</button>
				<button type="button" class="btn btn-success" id="add_button">
					<span class="glyphicon glyphicon-plus-sign"></span> Thêm mới
				</button>
			</div>
		</div>
		</div>
		</div>
		</div>
		</div>
	<!-- </div> -->
	<form:hidden  id="groupId"  path="groupId"/>
</form:form>
<div id="dialog-confirm"></div>
<div id="dialog-confirm-del"></div>
<script> 

function getAUrlUniquifier(){
	return (new Date().getTime()).toString();
} 

function doEditSiteRepo(siteIdToDo){
	window.location.href = '<c:url value="/servlet/siteMgmt/editSiteRepo/" />' + siteIdToDo + '/' + getAUrlUniquifier();
} 

$( document ).ready(function() {
	$("#searchText").attr('focus', true);
});

$(function() {

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
					document.forms["siteRepoForm"].action = '${getRepoUrl}/${idGroup}';  
					document.forms["siteRepoForm"].submit();  
				}
			}
		});
  /*  $( "#dialog-confirm" ).dialog({
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
        duration: 1000
      },
	   buttons:{
    'Đồng ý': function() {
    	$(this).dialog("close");
    	document.forms["siteRepoForm"].submit();
    },
	'Hủy': function() {
		$(this).dialog("close");
    }
   }
    });
    $( "#dialog-confirm-del" ).dialog({
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
            duration: 1000
          },
    	   buttons:{
        Ok: function() {
        	$(this).dialog("close");
        	document.forms["siteRepoForm"].action = '${delSiteGroupUrl}/'+$("#groupId").val();
    		document.forms["siteRepoForm"].submit();
        },
    	Cancel: function() {
    		$(this).dialog("close");
        }
       }
        });*/
  });
  
	$("#add_button").click(function() {
		document.forms["siteRepoForm"].action = '${addSiteRepoUrl}';
		document.forms["siteRepoForm"].submit();

	});

	function del(id){		
	    $('#messageLHS').modal();
	    document.forms["siteRepoForm"].action = '${delSiteRepoUrl}/'+ id;
	}
	
	$("#del_button").click(function(){	
		$("#dialog-confirm-del").dialog('option', 'title', 'Xóa trong nhóm');
	    $("#dialog-confirm-del").html('Bạn có chắc chắn muốn xóa ['+$("#groupId").val()+'] group?');
	    $("#dialog-confirm-del").dialog( 'open' );

	});
	
	function deleteForm(){
		document.forms["siteRepoForm"].submit();
	}

	function cancelPag(){	
	    document.forms["siteRepoForm"].action = '${url}';
		document.forms["siteRepoForm"].submit();
	}

	function searchSiteRepo() {
		if($('#siteCode').val() == null || $('#siteCode').val() == ''){
			$.notify('Xin hãy nhập thông tin tìm kiếm.', {
				globalPosition: 'bottom left',
				className: 'warn',
			});
	    	 return;
		}
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
     });
	
</script>

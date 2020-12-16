<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<c:url var="prodUrl" value="/servlet/investigation/startinvestigation" />
<c:url var="newUrl" value="/servlet/investigation/newInvestigation" />
<c:url var="pendingJobUrl" value="/servlet/investigation/pendingJobList" />
<c:url var="investigationJobUrl" value="/servlet/investigation/investigation" />
<c:url var="invesProcessUrl" value="/servlet/investionProcess/showInvestigation" />
<c:url var="nextProcessUrl" value="/servlet/investionProcess/invesProcess" />
<c:url var="showMessageUrl" value="/servlet/investionProcess/showMessage" />
<c:url var="testOfficeIdUrl" value="/servlet/investionProcess/testOfficeId" />
<%-- <form:form commandName="jobList" id="form" name="form" > --%>
<style>
<!--

-->
.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
    white-space: pre-wrap;
}
.cls-mg-bot {
	margin-top: 10px;
}

</style>
<div class="content-item-title">
            <div class="oplog-title__txt">Xử lý hồ sơ hộ chiếu</div>
        </div>
<div class="content-item-main">
<form:form modelAttribute="formData" name="formData" > 
			<c:if test="${not empty requestScope.Errors}">
				<div style="color:Red;background-color:White;border-style:solid;border-color:Red;border-width:thin;">
					<c:forEach items="${requestScope.Errors}" var="errorMessage">
						<li>
							${errorMessage}
						</li>
					</c:forEach>
					
				</div>
			</c:if>
			
			<c:if test="${not empty requestScope.messages}">
				<div class="border_success">
				<div class="success_left">
				<img align='left'
					src="<c:url value="/resources/images/success.jpg" />" width="30"
					height="30" />
                </div>
			
				
					 <div class="success">
						<table width="600" height="10" border="0" cellpadding="5">
							<tr> 
								<td style="padding-left: 5px;font-weight: bold; vertical-align: top;">
					    			<c:forEach items="${requestScope.messages}" var="infoMessage">
											${infoMessage}
									</c:forEach>
								</td>
							  </tr>
						</table>
					</div>
				</div>
				<br />
		</c:if>

		<!--Content start-->

  
   <div class="content-item-information">
     
   <div class="form-group form-profile pad-bottom-15">
                        <div class="col-sm-8">
                            <label class="col-sm-2 control-label text-right pad-top-10">Số danh sách</label>
                            <div class="col-sm-2 pad-top-10 none-pad-left">
                              <form:input path="packageCode" type="text" style="width: 100%;"/>
                            </div>
                            <label class="col-sm-2 control-label text-right pad-top-10 none-pad-left" style="width: 11%">Trạng thái</label>
                            <div class="col-sm-2 pad-top-10 none-pad-left" style="width: 19%">
                                <form:select path="typeList" id="typeList" name="typeList" class="priorityId"
											 style="font-family: Arial; font-size: 12px;width: 100%;">
											<form:option value="0" style="margin-right: 5px;" label="Tất cả" />
											<form:option value="1" style="margin-right: 5px;" label="Chưa xử lý" />
											<form:option value="2" style="margin-right: 5px;" label="Đang xử lý" />
											<form:option value="3" style="margin-right: 5px;" label="Đã xử lý" />
											<form:option value="4" style="margin-right: 5px;" label="Đợi bổ sung" />
										</form:select>
                            </div>
                            <div class="col-sm-4 pad-top-10">
                               	<button type="button" onclick="doApplyFilter();" class="btn_small btn-success">
										        <span class="glyphicon glyphicon-search"></span> Tìm kiếm
										    </button>	       </div>
                        </div>
                        <div class="col-xs-2 col-xs-offset-2 pad-top-10">
                        
                            <label class="bold-label ng-binding">Tổng số hồ sơ: <span id="tongSoHS">${total}</span></label>
                        </div>
                    </div>
			
						

	
			<div class="form-group">
			<div class="col-sm-12 none-pad-right none-pad-left">
					<fieldset class="scheduler-border">
							<legend class="scheduler-border">Hồ sơ cần xử lý</legend>
							<div style="height: 400px;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="max-width: 40px;">
										  <input type="checkbox" id="idCheckAll" />	
								      </th>	
								      <th class="th-sm">Số hồ sơ
								
								      </th>
								      <th class="th-sm">Số danh sách
								
								      </th>
								      <th class="th-sm">Họ tên
								
								      </th>
								      <th class="th-sm">Ngày sinh
								
								      </th>
								      <th class="th-sm">Giới tính
								
								      </th>
								      <th class="th-sm">CMND/CCCD
								
								      </th>
								      <th class="th-sm">Nơi sinh
								      </th>
								      <th class="th-sm">Cán bộ XL
								      </th>
								      <th class="th-sm">Trạng thái
								      </th>
								    </tr>
								  </thead>
								   <tbody>
								  <c:if test="${not empty dsXuLyA}">
									 
									    <c:forEach items="${dsXuLyA}" var="item">
										    <tr>
										      <td class="align-central">
										      	  <c:choose>
										      	  		<c:when test="${item.flagOfficerId == 0}">
												      		<form:checkbox path="selectedJobIds" value="${item.uploadJobId}"
															cssClass="${item.currentlyAssignedToAnInvestigationOfficer}" />
										      	  			<input type="text" id="idshow_${item.uploadJobId}" value="${item.priority}" style="display: none;">
										      	  		</c:when>
										      	  		<c:otherwise>
										      	  			<form:checkbox path="selectedJobIds" disabled="true" value="${item.uploadJobId}"
															cssClass="${item.currentlyAssignedToAnInvestigationOfficer}" />
										      	  			<input type="text" id="idshow_${item.uploadJobId}" value="${item.priority}" style="display: none;">
										      	  		</c:otherwise>
										      	  </c:choose>	
											  </td>	
										      <td>${item.transactionId}</td>
										      <td>${item.packageId}</td>
										      <td>${item.fullName}</td>
										      <td class="align-central">${item.dob}</td>
										      <td>${item.gender}</td>
										      <td>${item.nin}</td>
										      <td>${item.placeOfBirth}</td>
										      <td>${item.leaderId}</td>
										      <td>${item.stageList}</td>		
										    </tr>
									    </c:forEach>
									 
								  </c:if>
								   </tbody>
								    <c:if test="${empty dsXuLyA}">
								  
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
	      			<p style="display: inline-block;position: absolute;margin-left: 10px;margin-top: 5px;">Bạn có muốn tra cứu tiếp hồ sơ?</p>
	      		</div>
	      </div>							      
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" onclick="btnXuLyHoSo('N');" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> KHÔNG</span>
	       		</button>
	       		<button type="button" onclick="btnXuLyHoSo('Y');" class="btn btn-success" data-dismiss="modal" aria-label="Close" style="float: right;margin-right: 10px;">
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
			<button type="button"  onclick="processHS();" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-list-alt"></span> Xử lý hồ sơ
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
						document.forms["formData"].action = '${invesProcessUrl}';  
						document.forms["formData"].submit();  
					}
				}
			});
		function doSubmitSave(form) {
			form.action = '<c:url value="/servlet/investigation/search" />';
			form.submit();
		}
	</script>
	<script type="text/javascript">
		var check = '';
		function processHS(){
			var url = '${showMessageUrl}';
			var arrJob = '';
			var arrWfj = '';
			var arrChke = document.getElementsByName("selectedJobIds");
			//alert(arrChke.length);
			for(var i = 0; i < arrChke.length; i++){
				if(arrChke[i].checked == true){
					//alert($('#idshow_' + arrChke[i].value).val());
					arrJob += $('#idshow_' + arrChke[i].value).val() + ",";
					arrWfj += arrChke[i].value + ",";
				}
			}
			var mess = checkRong();
			if(mess != ''){
				$.notify(mess, {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return;
			}
			testOfficeId(arrWfj);
			//alert(check);
			if(check != ''){
				var mes = 'Hồ sơ ' + check + ' đã được cán bộ khác xử lý!';
				$.notify(mes, {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return;
			}
			$.ajax({
				url : url,
				cache: false,
				type: "POST",
				data: {
					arrJob : arrJob
				},
				success : function(data) {
					if(data == 'Y'){
						btnXuLyHoSo('N');
					}else{
						$("#messageLHS").modal();
					}	
				}
			});
		}
		
		function testOfficeId(arrJob){
			var url = '${testOfficeIdUrl}';
			$.ajax({
				url : url,
				cache: false,
				async:false,
				type: "POST",
				data: {
					arrJob : arrJob
				},
				success : function(data) {
					check = data;
				}
			});
		}
	
	
		function btnXuLyHoSo(value) {
			
			/* $.confirm({
			    title: 'Thông báo',
			    content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/question-1.png\">" + ' Bạn có muốn tra cứu tiếp hồ sơ',
			    buttons: {
			        "Có": function () {
						document.forms["formData"].action = '<c:url value="/servlet/investionProcess/invesProcess" />';
						document.forms["formData"].submit();
			        },
			        "Không": function () {
			        	//return false;
			        }		       
			    }
			}) */
			document.forms["formData"].action = '${nextProcessUrl}/' + value;
			document.forms["formData"].submit();
		}
	
		function doApplyFilter() {
			document.forms["formData"].action = '<c:url value="/servlet/investionProcess/showInvestigation" />';
			document.forms["formData"].submit();
		}
	
		function doSubmitNew(form) {
			document.forms["form"].action = "${newUrl}";
			document.forms["form"].submit();
		}
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
		    	document.forms["form"].action = "${newUrl}";
				document.forms["form"].submit();
		    },
			"Hủy": function() {
				$(this).dialog("close");
		    }
		   }
		});
		
		function doSubmitNew1(form) {
			 $("#dialog-confirm").dialog('option', 'title', 'Tình trạng công việc đang chờ xử lý');
			 $("#dialog-confirm").html("Có công việc đang chờ xử lý? Bạn có muốn tiếp tục?");
			 $("#dialog-confirm").dialog( 'open' );			
		}
		
		function checkRong(){
			var kiemTra = false;
			var chkHoSo = document.getElementsByName("selectedJobIds");
        	for(var i = 0; i < chkHoSo.length; i++){
        		if(chkHoSo[i].checked){
        			kiemTra = true;
        			break;
        		}
        	}
        	if(kiemTra){
        		return '';
        	}else{
        		return 'Bạn chưa chọn hồ sơ nào';
        	}
		}
		
		//var stage = 0;
		$('#idCheckAll').change(function(){
			var total = document.getElementsByName("selectedJobIds");
			var root = document.getElementById("idCheckAll");
			for(var i = 0; i < total.length; i++){
				if(total[i].disabled == true){
					continue;
				}
				if(root.checked == true){
					total[i].checked = true;
				}else{
					total[i].checked = false;
				}
			}
		});
		
		$("#createDate").datepicker({
			showOn : "button",
			buttonImage : "<c:url value="/resources/images/calendar.gif" />",
			buttonImageOnly : true,
			changeMonth : true,
			changeYear : true,
			showSecond : true,
			controlType : 'select',
			dateFormat : 'dd-M-yy',
			yearRange : "-100:+10"
		}).keyup(function(e) {
			if (e.keyCode == 8 || e.keyCode == 46) {
				$.datepicker._clearDate(this);
			}
		});
		
		$('#createDate').datepicker().datepicker('setDate', "");
	</script>
	
</form:form>
</div>


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
<c:url var="invesProcessUrl" value="/servlet/storage/searchPerson" />
<c:url var="nextProcessUrl" value="/servlet/investionProcess/invesProcess" />
<c:url var="showMessageUrl" value="/servlet/investionProcess/showMessage" />
<c:url var="testOfficeIdUrl" value="/servlet/investionProcess/testOfficeId" />
<c:url var="getInfoPersonUrl" value="/servlet/storage/getInfoPerson" />
<c:url var="getdatatableSearchPersonUrl" value="/servlet/storage/getdatatableSearchPerson" />
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

.form-group {
	 display: inline !important;
}
#ajax-load-qa {
	background: rgba(255, 255, 255, .6)
		url('<c:url value="/resources/images/loading_nin.gif" />') 50% 50%
		no-repeat;
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
            <div class="oplog-title__txt">Danh sách công dân</div>
        </div>
<div class="content-item-main">
<form:form modelAttribute="formData" name="formData" autocomplete="off"> 
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
   						
                        <div class="col-sm-12" style="padding-right: 30px;">
                        <div class="col-sm-4 form-group">
                        	<label class="col-sm-4 pad-top-10 control-label text-right ">Họ và tên</label>
                        	<div class="col-sm-8 pad-top-10 none-pad-left">
	                        	<form:input path="name" id="name" type="text" style="width: 100%;"/>
                        	</div>
                        </div>
                        <div class="col-sm-4 form-group">
                        	<label class="col-sm-4 pad-top-10 control-label text-right ">Số CMND/CCCD</label>
                        	<div class="col-sm-8 pad-top-10 none-pad-left">
	                        	<form:input path="nin" id="nin" type="text" style="width: 100%;"/>
                        	</div>
                        </div>		
                        <div class="col-sm-4 form-group">
                            <label class="col-sm-4 control-label text-right pad-top-10">Có dữ liệu sinh trắc</label>
                            <div class="col-sm-8 pad-top-10 none-pad-left">
                             	 <form:select path="typeList" id="typeList" name="typeList" class="priorityId"
											 style="font-family: Arial; font-size: 12px;width: 100%;">
									<form:option value="" style="margin-right: 5px;" label="Tất cả" />
									<form:option value="Y" style="margin-right: 5px;" label="Có" />
									<form:option value="N" style="margin-right: 5px;" label="Không có" />									
								</form:select>
                            </div>                          
                        </div>
                        </div>
                        
                          <div class="col-sm-12" style="padding-right: 30px;">
                        
                        <div class="col-sm-4 form-group">
                            <label class="col-sm-4 control-label text-right">Giới tính</label>
                            <div class="col-sm-8 none-pad-left">
                             	 <form:select path="gender" id="gender" name="gender" class="priorityId"
											 style="font-family: Arial; font-size: 12px;width: 100%;">
									<form:option value="" style="margin-right: 5px;" label="Tất cả" />
									<form:option value="M" style="margin-right: 5px;" label="Nam" />
									<form:option value="F" style="margin-right: 5px;" label="Nữ" />									
								</form:select>
                            </div>                                                    
                        </div>
                         <div class="col-sm-4 form-group">
                        	<label class="col-sm-4 control-label text-right">Ngày sinh</label>
                        	<div class="col-sm-8 none-pad-left">
	                        	<form:input path="dob" id="dob" type="text" style="width: 100%;"/>
                        	</div>
                        </div>
                         <div class="col-sm-4 form-group">
                        	<label class="col-sm-4 control-label text-right">Nơi sinh</label>
                        	<div class="col-sm-8 none-pad-left">
	                        	 <form:select path="pob" id="pob" name="pob" class="priorityId"
											 style="font-family: Arial; font-size: 12px;width: 100%;">						
									<c:forEach items="${placeOfBirth}" var="entry">
										<form:option value="${entry.key}" style="margin-right: 5px;" label="${entry.value}" />									
									</c:forEach>
								</form:select>
                        	</div>
                        </div>
                        </div>
                        
                        <div class="col-sm-12" style="padding-right: 30px;">
                          	<button type="button" style="float: right;" onclick="doApplyFilter();" class="btn_small btn-success">
					        	<span class="glyphicon glyphicon-search"></span> Tìm kiếm
					   		</button>	       
					   </div>
                    </div>
			
						

	
			<div class="form-group">
			<div class="col-sm-12 none-pad-right none-pad-left" style="margin-bottom: 50px;">
					<!--<fieldset class="scheduler-border">
							<legend class="scheduler-border">Hồ sơ cần xử lý</legend>-->
							<div style="height: 400px;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
								  <thead>
								    <tr>								     
								      <th class="th-sm" style="width: 40px;">STT
								
								      </th>
								      <th class="th-sm">Họ tên
								
								      </th>
								      <th class="th-sm">Giới tính
								
								      </th>
								      <th class="th-sm">Ngày sinh
								
								      </th>
								      <th class="th-sm">Nơi sinh
								
								      </th>
								      <th class="th-sm">Số CMND/CCCD
								
								      </th>
								      <th class="th-sm">Ngày cấp CMND/CCCD
								      </th>
								      <th class="th-sm">Nơi cấp
								      </th>	
								      <th class="th-sm">Thao tác
								      </th>
								    </tr>
								  </thead>
								
								    <c:if test="${empty dsXuLyA}">
								  
								   <tbody class="e-not-found ng-scope"><tr>
								  <td colspan="9" style="height: 362px">
								  <div class="e-not-found-center"><div class="e-not-found-title">Không tìm thấy kết quả</div><div class="e-not-found-title-child">Hãy thử tìm kiếm hoặc sử dụng bộ lọc khác</div></div>
								  </td></tr></tbody>
								  
								
								</c:if>
								</table>
								
								
						      </div>
						      
						      <table class="table e-grid-table e-border">
                                <tfoot>
                                    <tr>
                                        <th>
										<div class="e-page-rigth">
											<ul style="float: right;" class="pagination" id="pagination"></ul>
										</div>
										<input type="hidden" name="pageNo" id="pageNo" /> 
                                        <div class="e-page-left" style="font-weight: normal;" id="showTotal"> 
											Hiển thị ${startIndex} đến ${endIndex} của ${totalRecord} kết quả
										</div>
                                            
                                        </th>
                                    </tr>
                                </tfoot>
                            </table>
						  <!--</fieldset>-->
		
		</div>
		</div>
		</div>
	
	<!-- Message lưu hồ sơ -->
	<div class="modal fade" id="idChiTiet" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
	  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1200px;">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">CHI TIẾT CÔNG DÂn</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
	        </button>
	      </div>
	      <div class="modal-body" id="divChiTiet" style="height: 700px;">
	      		
	      </div>							      
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove"></span > Đóng
	       		</button>	       		
	      </div>
	    </div>
	  </div>
	</div>	
	<!-- ---------------------------------------------------------------------------->
	<!--<div style="display: flex;flex: 0 auto;justify-content: center;">
	<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
		<div style="margin: 10px"> 
			<button type="button"  onclick="processHS();" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-list-alt"></span> Xử lý hồ sơ
			</button>
		</div>
	</div>	
	</div>-->
		<div id="ajax-load-qa"></div>
<div id="dialog-confirm"></div>
	<script type="text/javascript">
	var pattern1 = /^([0-2][0-9]|(3)[0-1])(\/)(((0)[0-9])|((1)[0-2]))(\/)\d{4}$/;
	var pattern2 = /^(((0)[0-9])|((1)[0-2]))(\/)\d{4}$/;
	var pattern3 = /^\d{4}$/;

		function doSubmitSave(form) {
			form.action = '<c:url value="/servlet/investigation/search" />';
			form.submit();
		}
		$(document).ready(function() {
			$('.priorityId').SumoSelect();
			
			
			$('#dob').keyup(function(e){
				   var dFather =  $('#dob').val();
				   switch(dFather.length) {
					   case 5:
						   dFather = dFather.replace('/', '');
					     break;
					   case 8:
						   dFather = reverseString(dFather);
						   dFather = dFather.replace('/', '');
						   dFather = reverseString(dFather);
					     // code block
					     break;
					   default:
					     // code block
					     break;
					}
				   //alert(dFather);
				   $('#dob').val(dFather);
				   $(this).lockCursor();
			 });
		});
			jQuery.fn.lockCursor = function() {
			    return this.each(function() { 
			        if (this.setSelectionRange) { 
			            var len = $(this).val().length * 2; 
			            this.setSelectionRange(len, len);
			        } else {
			            $(this).val($(this).val()); 
			       }
			    });
			};
			function reverseString(str) {
			    // Step 1. Use the split() method to return a new array
			    var splitString = str.split(""); // var splitString = "hello".split("");
			    // ["h", "e", "l", "l", "o"]
			 
			    // Step 2. Use the reverse() method to reverse the new created array
			    var reverseArray = splitString.reverse(); // var reverseArray = ["h", "e", "l", "l", "o"].reverse();
			    // ["o", "l", "l", "e", "h"]
			 
			    // Step 3. Use the join() method to join all elements of the array into a string
			    var joinArray = reverseArray.join(""); // var joinArray = ["o", "l", "l", "e", "h"].join("");
			    // "olleh"
			    
			    //Step 4. Return the reversed string
			    return joinArray; // "olleh"
			}
	</script>
	<script type="text/javascript">
		$('#dob').mask("00/00/0000", { placeholder: "__/__/____" });
		function chiTietHS(val, tranId){
			$('#divChiTiet').html("");
			var url = '${getInfoPersonUrl}/' + val + '/' + tranId;
			$('#ajax-load-qa').css('display', 'block');
			$.ajax({
				url : url,
				cache: false,
				type: "POST",
				success : function(data) {
					$('#divChiTiet').html(data);
					$('#ajax-load-qa').css('display', 'none');				
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
			document.forms["formData"].action = '${nextProcessUrl}/' + value;
			document.forms["formData"].submit();
		}
		$("#dob").datepicker({
			//showOn : "button",
			//buttonImage : "<c:url value="/resources/images/calendar9.png" />",
			//buttonImageOnly : true,
			changeMonth : true,
			changeYear : true,
			showSecond : true,
			controlType : 'select',
			dateFormat : 'dd/mm/yy',
			yearRange : "-100:+10"
		}).keyup(function(e) {

		});
	
		
		function doApplyPagination(){
			var pattern1 = /^([0-2][0-9]|(3)[0-1])(\/)(((0)[0-9])|((1)[0-2]))(\/)\d{4}$/;
			var pattern2 = /^(((0)[0-9])|((1)[0-2]))(\/)\d{4}$/;
			var pattern3 = /^\d{4}$/;
			var dobF = $('#dob').val();
			var nameF = $('#name').val();
			var ninF = $('#nin').val();
			var pobF = $('#pob').val();
			if(dobF === '' && nameF === '' && ninF === '' && pobF === ''){
				$.notify('Cần nhập ít nhất 1 tiêu chí tìm kiếm.', {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return;
			}
			
			if(dobF != '' && !dobF.match(pattern1) && !dobF.match(pattern2) && !dobF.match(pattern3)){
				$.notify('Định dạng ngày sinh không đúng.', {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return;
			}
			//dũng sửa code load form bằng ajax
			var gender = $('#gender').val();
			var typeList = $("#typeList").val();
			var pageNo = $("#pageNo").val();
			$('#ajax-load-qa').css('display', 'block');
			$('#ajax-load-qa').show();
			var investigationAssignmentData = {
					"dob": dobF,
					"name" : nameF,
					"nin" : ninF,
					"pob" : pobF,
					"gender" : gender,
					 "typeList" : typeList,
					 "pageNo" : pageNo
					 };
			var url = '${getdatatableSearchPersonUrl}/';
			 $.ajax({
				url : url,
				data : JSON.stringify(investigationAssignmentData),  
				dataType : 'json',
				contentType : 'application/json; charset=utf-8',
				method: 'POST',
				success : function(data) {
					$('#dtBasicExample > tbody').html(data.dataHtml); 
					$('#showTotal').text("Hiển thị "+data.startIndex+" đến "+data.endIndex+" của "+data.totalRecord+" kết quả");
						var totalPages = data.totalPage;
						var currentPage = data.pageNo;
						var pageSize = data.pageSize; 
					$('#pagination').twbsPagination('destroy');
					$('#pagination').twbsPagination({
											totalPages : data.totalPage,
											visiblePages :  data.pageSize,
											startPage :  data.pageNo,
											next : '<span class=\"glyphicon glyphicon-forward fix-paging\"></span>',
											prev : '<span class=\"glyphicon glyphicon-backward fix-paging\"></span>',
											last : '<span class=\"glyphicon glyphicon-step-forward fix-paging\"></span>',
											first : '<span class=\"glyphicon glyphicon-step-backward fix-paging\"></span>',
											onPageClick : function(event, page) {
												if (currentPage != page) {
													$('#pageNo').val(page);
													doApplyPagination(); 
												}
											} 
										});
					$('#ajax-load-qa').hide();
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					$('#ajax-load-qa').hide();
				}
			}); 
		}
		function doApplyFilter() {
			var pattern1 = /^([0-2][0-9]|(3)[0-1])(\/)(((0)[0-9])|((1)[0-2]))(\/)\d{4}$/;
			var pattern2 = /^(((0)[0-9])|((1)[0-2]))(\/)\d{4}$/;
			var pattern3 = /^\d{4}$/;
			var dobF = $('#dob').val();
			var nameF = $('#name').val();
			var ninF = $('#nin').val();
			var pobF = $('#pob').val();
			if(dobF === '' && nameF === '' && ninF === '' && pobF === ''){
				$.notify('Cần nhập ít nhất 1 tiêu chí tìm kiếm.', {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return;
			}
			
			if(dobF != '' && !dobF.match(pattern1) && !dobF.match(pattern2) && !dobF.match(pattern3)){
				$.notify('Định dạng ngày sinh không đúng.', {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return;
			}
			//dũng sửa code load form bằng ajax
			var gender = $('#gender').val();
			var typeList = $("#typeList").val();
			var pageNo = "";
			$('#ajax-load-qa').css('display', 'block');
			$('#ajax-load-qa').show();
			var investigationAssignmentData = {
					"dob": dobF,
					"name" : nameF,
					"nin" : ninF,
					"pob" : pobF,
					"gender" : gender,
					 "typeList" : typeList,
					 "pageNo" : pageNo
					 };
			var url = '${getdatatableSearchPersonUrl}/';
			 $.ajax({
				url : url,
				data : JSON.stringify(investigationAssignmentData),  
				dataType : 'json',
				contentType : 'application/json; charset=utf-8',
				method: 'POST',
				success : function(data) {
					$('#dtBasicExample > tbody').html(data.dataHtml); 
					$('#showTotal').text("Hiển thị "+data.startIndex+" đến "+data.endIndex+" của "+data.totalRecord+" kết quả");
						var totalPages = data.totalPage;
						var currentPage = data.pageNo;
						var pageSize = data.pageSize; 
					$('#pagination').twbsPagination('destroy');
					$('#pagination').twbsPagination({
											totalPages : data.totalPage,
											visiblePages :  data.pageSize,
											startPage :  data.pageNo,
											next : '<span class=\"glyphicon glyphicon-forward fix-paging\"></span>',
											prev : '<span class=\"glyphicon glyphicon-backward fix-paging\"></span>',
											last : '<span class=\"glyphicon glyphicon-step-forward fix-paging\"></span>',
											first : '<span class=\"glyphicon glyphicon-step-backward fix-paging\"></span>',
											onPageClick : function(event, page) {
												if (currentPage != page) {
													$('#pageNo').val(page);
													doApplyPagination(); 
												}
											} 
										});
					$('#ajax-load-qa').hide();
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					$('#ajax-load-qa').hide();
				}
			}); 
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
	
	</script>
	
</form:form>
</div>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 

<c:url var="addOfficalNation" value="/servlet/officeNationController/addOfficalNation" />
<c:url var="updateOfficalNation" value="/servlet/officeNationController/updateOfficalNation" />
<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
<c:url var="officalNationUrl" value="/servlet/officeNationController/officalNationList" />
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
		<c:if test="${not empty message}">
				<div class="border_success">
				<div class="success_left">
				<img align='left'
					src="<c:url value="/resources/images/info.gif" />" width="30"
					height="30" />
                </div>
			
				
					 <div class="success">
						<table width="600" height="10" border="0" cellpadding="5">
							<tr> 
								<td style="padding-left: 5px;font-weight: bold; vertical-align: top;">
					    			<c:forEach items="${message}" var="infoMessage">
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
		<div class="form-desing">
		 <div>
   <div class="row">
   <div class="ov_hidden">
   <div id="content_inner"> 
   
     
        <div class="new-heading-2">DANH SÁCH TỜ KHAI CÔNG HÀM</div>
        
        <!--<input type="button" id="addBtn" class="btn-sm btn-success" value="Thêm mới bản ghi" style="float: right;margin-bottom: 10px;"/>-->
       	<div style="border: solid 1px #cccccc; border-radius: 4px; margin: 2px;width:100%;float:left;margin-top: 10px;">
							<div style="margin: 2px">
								<div class="col-md-4" style="padding-top: 5px;">
									<div class="col-md-5 cls-mg-bot">Số tờ khai</div>
									<div class="col-md-7">
										<form:input type="text" style="height: 27px;width:100%" path="searchTransactionId" />
									</div>
								</div>
								<div class="col-md-4" style="padding-top: 5px;">
									<div class="col-md-5 cls-mg-bot">Trạng thái</div>
									<div class="col-md-7">
										<select style="width: 100%;" name="statusTT" id="statusTT" class="js-example-basic-single">
											<c:set var="transitN" value="${formData.currentlyAssignedToUserId}" />
											<option value="">-- Chọn trạng thái -- </option>
											<c:choose>
												<c:when test="${not fn:contains(transitN, 'REGISTRATION')}">
													<option value="REGISTRATION">Khởi tạo/ lưu tạm</option>
												</c:when>
												<c:otherwise>
													<option selected="true" value="REGISTRATION">Khởi tạo/ lưu tạm</option>
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${not fn:contains(transitN, 'VERIFY')}">
													<option value="VERIFY">Xác nhận</option>
												</c:when>
												<c:otherwise>
													<option selected="true" value="VERIFY">Xác nhận</option>
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${not fn:contains(transitN, 'RECEIVED')}">
													<option value="RECEIVED">Đã nhận</option>
												</c:when>
												<c:otherwise>
													<option selected="true" value="RECEIVED">Đã nhận</option>
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${not fn:contains(transitN, 'PROCESSING')}">
													<option value="PROCESSING">Chờ xử lý (Giai đoạn lấy thị thực)</option>
												</c:when>
												<c:otherwise>
													<option selected="true" value="PROCESSING">Chờ xử lý (Giai đoạn lấy thị thực)</option>
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${not fn:contains(transitN, 'REFUSE')}">
													<option value="REFUSE">Từ chối của quản trị hệ thống</option>
												</c:when>
												<c:otherwise>
													<option selected="true" value="REFUSE">Từ chối của quản trị hệ thống</option>
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${not fn:contains(transitN, 'REFUSE_DSQ')}">
													<option value="REFUSE_DSQ">Từ chối từ đại sứ quán</option>
												</c:when>
												<c:otherwise>
													<option selected="true" value="REFUSE_DSQ">Từ chối từ đại sứ quán</option>
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${not fn:contains(transitN, 'REFUSE_CLS')}">
													<option value="REFUSE_CLS">Từ chối từ cục lãnh sự</option>
												</c:when>
												<c:otherwise>
													<option selected="true" value="REFUSE_CLS">Từ chối từ cục lãnh sự</option>
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${not fn:contains(transitN, 'NATION_VISA')}">
													<option value="NATION_VISA">Không cần cấp do đã có miễn thị thực Quốc gia</option>
												</c:when>
												<c:otherwise>
													<option selected="true" value="NATION_VISA">Không cần cấp do đã có miễn thị thực Quốc gia</option>
												</c:otherwise>
											</c:choose>
									</select>
									</div>
								</div>
								<div class="col-md-4" style="margin-bottom: 10px;margin-top: 5px;">
									<div style="text-align: center;">
										<button type="button" onclick="doSubmitSave(this.form);" class="btn_small btn-primary btn-search">
											<span class="glyphicon glyphicon-search"></span> Tìm kiếm
										</button>
										<!--<input type="button" class="btn_small btn-primary btn-search"  value="Tìm kiếm" onclick="doSubmitSave(this.form);"  />--> 
									</div>
								</div>
							</div>
						</div>	
       <div id="heading_right">
       <table width="100%" border="0" class="table_cont">

  
</table>

       </div>
     </div>
      
	  <br />
	   <div>
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="max-width: 50px;">STT
								
								      </th>	
								      <th class="th-sm" style="min-width: 110px;">Số tờ khai
								
								      </th>
								      <th class="th-sm" style="min-width: 110px;">Số quyết định
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Tên người cử đi
								
								      </th>	
								      <th class="th-sm" style="min-width: 110px;">Số hộ chiếu
								
								      </th>	
								      <th class="th-sm" style="min-width: 110px;">Số visa
								
								      </th>	
								      <th class="th-sm" style="min-width: 110px;">Trạng thái
								
								      </th>	
								      <th class="th-sm" style="min-width: 110px;">Quốc gia
								
								      </th>	
								      <th class="th-sm" style="min-width: 120px;">Ngày tạo
								
								      </th>		
								      <th class="th-sm" style="min-width: 0px;">
								
								      </th>						     
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>							    
									      <td><c:url var="jobUrl" value="/servlet/officeNationController/updateOfficalNation/${item.id}" />
											  <a style="color: blue;" href="${jobUrl}"> <c:out value="${item.id}" /></a></td>									      
									      <td>${item.officalNationNo}</td>	
									      <td>${item.decisionNumber}</td>	
									      <td>${item.fullname}</td>
									      <td>${item.passportNo}</td>
									      <td>${item.visaNo}</td>
									      <td>${item.statusS}</td>
									      <td>${item.nationCode}</td>
									      <td>${item.createDate}</td>
									      <td><c:set var="statusF" value="${item.status}" ></c:set>
											<c:if test="${statusF eq 'REGISTRATION'}">
												<input id="edit${item.id}" class="btn_small btn-primary" type="button" name="edit" value="Sửa" onclick="editOfficalNation('${item.officalNationNo}')"/>
												&nbsp;
												<input id="verify${item.id}" class="btn_small btn-success" type="button" name="verify" value="Xác nhận" onclick="verifyOfficalNation('${item.officalNationNo}')"/>
												&nbsp;
												<input id="delete${item.id}" class="btn_small btn-danger" type="button" name="delete" value="Xóa" onclick="deleteOfficalNation('${item.officalNationNo}')"/>
											</c:if>
											<c:if test="${statusF eq 'VERIFY'}">
												<input id="cancel${item.id}" class="btn_small btn-success" type="button" name="cancel" value="Từ chối" onclick="cancelOfficalNation('${item.officalNationNo}')"/>
												&nbsp;
												<input id="nationTT${item.id}" class="btn_small btn-primary" type="button" name="nationTT" value="QG miễn TT" onclick="nationTTOfficalNation('${item.officalNationNo}')"/>
												&nbsp;
												<input id="sending${item.id}" class="btn_small btn-success" type="button" name="sending" value="Xin công hàm" onclick="sendingOfficalNation('${item.officalNationNo}')"/>
											</c:if>	</td>						     				     
									    </tr>
								    </c:forEach>
								  </tbody>
								</table>
								<div class="col-md-5 col-sm-12" style="padding-left: 0px;">
									<div class="dataTables_info">Hiển thị ${startIndex} đến ${endIndex} của ${totalRecord} kết quả</div>
								</div>
								<div class="col-md-7 col-sm-12" style="padding-right: 0px;">
									<ul style="float: right;" class="pagination" id="pagination"></ul>
								</div>			 
								<input type="hidden" name="pageNo" id="pageNo" /> 
						      </div>
			<%--
				int pageSize = 20;
			--%>
		<!--<c:if test="${empty jobList}">
			Không bản ghi trong danh sách Tờ khai công hàm
			<br />
		</c:if>
		<c:if test="${not empty jobList}">
			
			<display:table cellspacing="1" cellpadding="0" id="row" sort="external"  partialList="true" size="${totalRecords}"
				export="false" class="displayTag table" name="jobList" defaultsort="1"
				defaultorder="ascending" pagesize="${pageSize}"
				requestURI="/servlet//nationTTOfficalNation/officalNationSearch">
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
				<display:column title="STT" sortable="false" sortProperty="id">

				<c:url var="jobUrl" value="/servlet/officeNationController/updateOfficalNation/${row.id}" />
				<a href="${jobUrl}"> <c:out value="${row.id}" /></a>
				</display:column>
				<display:column property="officalNationNo" sortable="false"
					title="Số tờ khai" maxLength="15" >
				</display:column>
				<display:column property="decisionNumber" sortable="false"
					title="Số quyết định" maxLength="15" >
				</display:column>
				<display:column property="fullname" sortable="false"
					title="Tên người cử đi" maxLength="30" >
				</display:column>
				<display:column property="passportNo" sortable="false"
					title="Số hộ chiếu" maxLength="10" >
				</display:column>
				<display:column property="visaNo" sortable="false"
					title="Số Visa" maxLength="20" >
				</display:column>
				<display:column property="statusS" sortable="false"
					title="Trạng thái" maxLength="20" >
				</display:column>				
				<display:column property="nationCode" sortable="false"
					title="Quốc gia" maxLength="10" >
				</display:column>
				<display:column property="createDate" title="Ngày tạo" 
					sortable="false" format="{0,date,dd-MMM-yyyy}" >
				</display:column>
				<display:column>
					<c:set var="statusF" value="${row.status}" ></c:set>
					<c:if test="${statusF eq 'REGISTRATION'}">
						<input id="edit${row.id}" class="btn_small btn-primary" type="button" name="edit" value="Sửa" onclick="editOfficalNation('${row.officalNationNo}')"/>
						&nbsp;
						<input id="verify${row.id}" class="btn_small btn-success" type="button" name="verify" value="Xác nhận" onclick="verifyOfficalNation('${row.officalNationNo}')"/>
						&nbsp;
						<input id="delete${row.id}" class="btn_small btn-danger" type="button" name="delete" value="Xóa" onclick="deleteOfficalNation('${row.officalNationNo}')"/>
					</c:if>
					<c:if test="${statusF eq 'VERIFY'}">
						<input id="cancel${row.id}" class="btn_small btn-success" type="button" name="cancel" value="Từ chối" onclick="cancelOfficalNation('${row.officalNationNo}')"/>
						&nbsp;
						<input id="nationTT${row.id}" class="btn_small btn-primary" type="button" name="nationTT" value="QG miễn TT" onclick="nationTTOfficalNation('${row.officalNationNo}')"/>
						&nbsp;
						<input id="sending${row.id}" class="btn_small btn-success" type="button" name="sending" value="Xin công hàm" onclick="sendingOfficalNation('${row.officalNationNo}')"/>
					</c:if>
					
					
				</display:column>
				
			</display:table>
		</c:if>-->
		<div id="" align="right">
			
		</div>

		</div>
	</div>
	</div>
 	<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
			<div style="margin: 10px"> 			
				<button type="button" class="btn btn-success" id="addBtn">
					<span class="glyphicon glyphicon-plus-sign"></span> Thêm mới
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
					document.forms["formData"].action = '${officalNationUrl}';  
					document.forms["formData"].submit();  
				}
			}
		});	
		function editPerson(no) {
			 document.forms[0].action = '${showBusinesslist}' + '/' + no;
			 document.forms[0].submit();	
		}
		function doSubmitSave(form) {
			/* form.action = '<c:url value="/servlet/investigation/searchApprove" />';//TRUNG SỬA GỌI HÀM BỘ SEARCH
			form.submit(); */
			document.forms["formData"].action = '<c:url value="/servlet/officeNationController/applyFilter" />';
			document.forms["formData"].submit();
			//alert('Chức năng này đang được phát triển');
		}
	</script>
	<script type="text/javascript">
		$("#addBtn").click(function(){	
			 document.forms[0].action = '${addOfficalNation}';
			 document.forms[0].submit();	
	
		});
	
		function editOfficalNation(id){	
			 document.forms[0].action = '${updateOfficalNation}' + '/' + id;
			 document.forms[0].submit();	
	
		};
		
		function deleteOfficalNation(id){	
			if(!confirm('Có chắc muốn xóa bản ghi này?')){return false;}
			else
				{
					$.post('<c:url value="/servlet/officeNationController/deleteOfficalNation" />' + '/' + id ,id,
				             function(data){
				   if(data=='success'){
				   	   $("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#savedialog-confirm").html('Đã xóa thành công Tờ khai công hàm :  '+ id);
				       $("#savedialog-confirm").dialog( 'open' );
				    }else if(data=='duplicate'){
				       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#faildialog-confirm").html('Công hàm:  '+ id + ' không còn ở trạng thái khởi tạo nên không thực hiện ở trạng thái này');
				       $("#faildialog-confirm").dialog( 'open' );
				    }else if(data== 'notfound'){
				    	$("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
					       $("#faildialog-confirm").html('Không tìm thấy Tờ khai công hàm:  '+ id);
					       $("#faildialog-confirm").dialog( 'open' );
				    }
				    else {
					       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
					       $("#faildialog-confirm").html('Không thể xóa Tờ khai công hàm:  '+ id);
					       $("#faildialog-confirm").dialog( 'open' );
					}
				   });
			}
		};
		
		function cancelOfficalNation(id){	
			if(!confirm('Có chắc muốn từ chối tờ khai công hàm này?')){return false;}
			else
				{
					$.post('<c:url value="/servlet/officeNationController/cancelOfficalNation" />' + '/' + id ,id,
				             function(data){
				   if(data=='success'){
				   	   $("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#savedialog-confirm").html('Đã từ chối thành công Tờ khai Công hàm :  '+ id);
				       $("#savedialog-confirm").dialog( 'open' );
				    }else if(data=='duplicate'){
				       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#faildialog-confirm").html('Tờ khai công hàm:  '+ id + ' không còn ở trạng thái khởi tạo nên không thực hiện ở trạng thái này');
				       $("#faildialog-confirm").dialog( 'open' );
				    }else if(data== 'notfound'){
				    	$("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
					       $("#faildialog-confirm").html('Không tìm thấy Tờ khai công hàm:  '+ id);
					       $("#faildialog-confirm").dialog( 'open' );
				    }
				    else {
					       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
					       $("#faildialog-confirm").html('Không thể từ chối Tờ khai công hàm:  '+ id);
					       $("#faildialog-confirm").dialog( 'open' );
					}
				   });
			}
		};
		
		function verifyOfficalNation(id){	
			if(!confirm('Có chắc muốn xác nhận tờ khai công hàm này?')){return false;}
			else
				{
					$.post('<c:url value="/servlet//officeNationController/verifyOfficalNation" />' + '/' + id ,id,
				             function(data){
				   if(data=='success'){
				   	   $("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#savedialog-confirm").html('Đã xác nhận thành công Tờ khai Công hàm :  '+ id);
				       $("#savedialog-confirm").dialog( 'open' );
				    }else if(data=='duplicate'){
				       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#faildialog-confirm").html('Tờ khai công hàm:  '+ id + ' không còn ở trạng thái khởi tạo nên không thực hiện ở trạng thái này');
				       $("#faildialog-confirm").dialog( 'open' );
				    }else if(data== 'notfound'){
				    	$("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
					       $("#faildialog-confirm").html('Không tìm thấy Tờ khai công hàm:  '+ id);
					       $("#faildialog-confirm").dialog( 'open' );
				    }
				    else {
					       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
					       $("#faildialog-confirm").html('Không thể xác nhận Tờ khai công hàm:  '+ id);
					       $("#faildialog-confirm").dialog( 'open' );
					}
				   });
			}
		};
		
		function sendingOfficalNation(id){	
			if(!confirm('Có chắc muốn Gửi tờ khai xin công hàm này?')){return false;}
			else
				{
					$.post('<c:url value="/servlet/officeNationController/sendingOfficalNation" />' + '/' + id ,id,
				             function(data){
				   if(data=='success'){
				   	   $("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#savedialog-confirm").html('Đã Gửi tờ khai xin Công hàm :  '+ id + ' thành công');
				       $("#savedialog-confirm").dialog( 'open' );
				    }else if(data=='duplicate'){
				       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#faildialog-confirm").html('Tờ khai công hàm:  '+ id + ' không còn ở trạng thái xác nhận nên không thực hiện ở trạng thái này');
				       $("#faildialog-confirm").dialog( 'open' );
				    }else if(data== 'notfound'){
				    	$("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
					       $("#faildialog-confirm").html('Không tìm thấy Tờ khai công hàm:  '+ id);
					       $("#faildialog-confirm").dialog( 'open' );
				    }
				    else {
					       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
					       $("#faildialog-confirm").html('Không thể Gửi tờ khai xin công hàm:  '+ id);
					       $("#faildialog-confirm").dialog( 'open' );
					}
				});
			}
		};
		
		function nationTTOfficalNation(id){	
			if(!confirm('Có chắc không muốn xin công hàm này do đã có thị thực quốc gia?')){return false;}
			else
				{
					$.post('<c:url value="/servlet/officeNationController/nationTTOfficalNation" />' + '/' + id ,id,
				             function(data){
				   if(data=='success'){
				   	   $("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#savedialog-confirm").html('Xác nhận thành công');
				       $("#savedialog-confirm").dialog( 'open' );
				    }else if(data=='duplicate'){
				       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#faildialog-confirm").html('Tờ khai công hàm không còn ở trạng thái xác nhận nên không thực hiện ở trạng thái này');
				       $("#faildialog-confirm").dialog( 'open' );
				    }else if(data== 'notfound'){
				    	$("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
					       $("#faildialog-confirm").html('Không tìm thấy Tờ khai công hàm:  '+ id);
					       $("#faildialog-confirm").dialog( 'open' );
				    }
				    else {
					       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
					       $("#faildialog-confirm").html('Xác nhận không thành công');
					       $("#faildialog-confirm").dialog( 'open' );
					}
				});
			}
		};
		
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
			 $("#dialog-confirm").dialog('option', 'title', 'Tình trạng');
			 $("#dialog-confirm").html("Có công việc đang chờ xử lý? Bạn có muốn tiếp tục?");
			 $("#dialog-confirm").dialog( 'open' );			
		}
		
		$(function() {
			$("#statusTT").change(function(){
				document.forms["formData"].currentlyAssignedToUserId.value = "";
	    		var code = $("#statusTT").val();
	    		document.forms["formData"].currentlyAssignedToUserId.value = code;
	    	})
			
			$("#savedialog-confirm" ).dialog({
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
			    Ok: function() {    	
			    	$(this).dialog("close");
			    	window.location.reload();
			    }
			   }
			 });
		 
			 $( "#faildialog-confirm" ).dialog({
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
				    Ok: function() {    	
				    	$(this).dialog("close");
				    }
				   }
		 	});
		});
		
		$(document).ready(function() {
		    $('.js-example-basic-multiple').select2();
		    $('.js-example-basic-single').select2();
		});
	</script>
</form:form>
<div id="savedialog-confirm" style="display: none;"></div>
<div id="faildialog-confirm" style="display: none;"></div>
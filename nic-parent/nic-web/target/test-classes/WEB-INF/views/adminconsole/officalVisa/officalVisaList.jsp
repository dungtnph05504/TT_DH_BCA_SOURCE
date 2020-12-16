<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<c:url var="addOfficalVisa" value="/servlet/officeVisaController/addOfficalVisa" />
<c:url var="updateOfficalVisa" value="/servlet/officeVisaController/updateOfficalVisa" />
<c:url var="officalVisaUrl" value="/servlet/officeVisaController/officalVisaList" />
<form:form commandName="jobList" id="form" name="form" >

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
		<div class="form-desing">
		 <div>
   <div class="row">
   <div class="ov_hidden">
   <div id="content_inner"> 
     <div class="new-heading-2">DANH SÁCH QUỐC GIA MIỄN THỊ THỰC</div>
      
	  <br />
 		<div>
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="max-width: 50px;">STT
								
								      </th>	
								      <th class="th-sm" style="min-width: 120px;">Quốc gia
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Loại hộ chiếu
								
								      </th>
								      <th class="th-sm" style="min-width: 150px;">Thời hạn tối thiểu
								
								      </th>	
								      <th class="th-sm" style="min-width: 150px;">Số ngày ở tối đa
								
								      </th>	
								      <th class="th-sm" style="min-width: 160px;">Ngày nhập cảnh gần nhất tối thiểu
								
								      </th>	
								      <th class="th-sm" style="min-width: 100px;">Trạng thái
								
								      </th>	
								      <th class="th-sm" style="min-width: 0px;">Ngày tạo
								
								      </th>								     
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>							    
									      <td><c:url var="jobUrl" value="/servlet/paymentMatrixController/officalVisaEdit/${item.id}" />
												<a style="color: blue;" href="#" onclick="editOfficalVisa('${item.id}')"> <c:out value="${item.id}" /></a></td>							      
									      <td>${item.countryCode}</td>	
									      <td>${item.passportType}</td>	
									      <td>${item.passportExpiredday}</td>	
									      <td>${item.stayday}</td>	
									      <td>${item.maxLastemmiday}</td>	
									      <td>${item.statusS}</td>	
									      <td>${item.createDate}</td>	
									      <td>
									      		<c:if test="${item.statusS eq 'REGISTRATION'}">
													<button type="button" class="btn btn-danger" id="delete${item.id}" onclick="deleteOfficalVisa('${item.id}')">
														<span class="glyphicon glyphicon-remove"></span> Xóa
													</button>
												</c:if>
									      </td>										     					     
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
			Không bản ghi trong danh sách Quốc gia miễn thị thực
			<br />
		</c:if>
		<c:if test="${not empty jobList}">
			
			<display:table cellspacing="1" cellpadding="0" id="row" sort="external"  partialList="true" size="${totalRecords}"
				export="false" class="displayTag table" name="jobList" defaultsort="1"
				defaultorder="ascending" pagesize="${pageSize}"
				requestURI="/servlet/paymentMatrixController/officalVisaSearch">											
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
				<c:url var="jobUrl" value="/servlet/paymentMatrixController/officalVisaEdit/${row.id}" />
				<a href="#" onclick="editOfficalVisa('${row.id}')"> <c:out value="${row.id}" /></a>
				</display:column>
				<display:column property="countryCode" sortable="false"
					title="Quốc gia" maxLength="15" >
				</display:column>
				<display:column property="passportType" sortable="false"
					title="Loại hộ chiếu" maxLength="15" >
				</display:column>
				<display:column property="passportExpiredday" sortable="false"
					title="Thời hạn tối thiếu hộ chiếu" maxLength="30" >
				</display:column>
				<display:column property="stayday" sortable="false"
					title="Số ngày được ở tối đa" maxLength="10" >
				</display:column>
				<display:column property="maxLastemmiday" sortable="false"
					title="Ngày nhập cảnh gần nhất tối thiểu" maxLength="20" >
				</display:column>
				<display:column property="statusS" sortable="false"
					title="Trạng thái" maxLength="20" >
				</display:column>
				<display:column property="createDate" title="Ngày tạo" 
					sortable="false" format="{0,date,dd-MMM-yyyy}" >
				</display:column>
				<display:column>
					<c:if test="${statusS eq 'REGISTRATION'}">
						<button type="button" class="btn btn-danger" id="delete${row.id}" onclick="deleteOfficalVisa('${row.id}')">
							<span class="glyphicon glyphicon-remove"></span> Xóa
						</button>
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
					document.forms["form"].action = '${officalVisaUrl}';  
					document.forms["form"].submit();  
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
			alert('Chức năng này đang được phát triển');
		}
	</script>
	<script type="text/javascript">
	$('#dtBasicExample').DataTable();
	$('.dataTables_length').addClass('bs-select');
		$("#addBtn").click(function(){	
			 document.forms[0].action = '${addOfficalVisa}';
			 document.forms[0].submit();	
	
		});
	
		function editOfficalVisa(id){	
			 document.forms[0].action = '${updateOfficalVisa}' + '/' + id;
			 document.forms[0].submit();	
	
		};
		
		function deleteOfficalVisa(id){	
			if(!confirm('Có chắc muốn xóa bản ghi này?')){return false;}
			else
				{
					$.post('<c:url value="/servlet/paymentMatrixController/deleteOfficalVisa" />' + '/' + id ,id,
				             function(data){
				   if(data=='success'){
				   	   $("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#savedialog-confirm").html('Đã xóa thành công Quốc gia miễn thị thực mã :  '+ id);
				       $("#savedialog-confirm").dialog( 'open' );
				    }else if(data== 'notfound'){
				    	$("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
					       $("#faildialog-confirm").html('Không tìm thấy Quốc gia miễn thị thực mã:  '+ id);
					       $("#faildialog-confirm").dialog( 'open' );
				    }
				    else {
					       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
					       $("#faildialog-confirm").html('Không thể xóa Quốc gia miễn thị thực mã:  '+ id);
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
		    Ok: function() {
		    	document.forms["form"].action = "${newUrl}";
				document.forms["form"].submit();
		    },
			Cancel: function() {
				$(this).dialog("close");
		    }
		   }
		});
		
		function doSubmitNew1(form) {
			 $("#dialog-confirm").dialog('option', 'title', 'Pending Jobs Status');
			 $("#dialog-confirm").html("Có công việc đang chờ xử lý? Bạn có muốn tiếp tục?");
			 $("#dialog-confirm").dialog( 'open' );			
		}
		
		$(function() {
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
	</script>
</form:form>
<div id="savedialog-confirm" style="display: none;"></div>
<div id="faildialog-confirm" style="display: none;"></div>
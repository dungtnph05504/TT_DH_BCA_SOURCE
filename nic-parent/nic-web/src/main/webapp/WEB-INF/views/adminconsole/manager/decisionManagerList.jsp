<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 

<c:url var="addDecision" value="/servlet/decisionController/addDecisionManager" />
<c:url var="showBusinesslist" value="/servlet/decisionController/addBusinessList" />
<c:url var="updateDecision" value="/servlet/decisionController/updateDecisionManager" />
<c:url var="psUrl" value="/servlet/decisionController/signerList" />
<c:url var="decisionManagerUrl" value="/servlet/decisionController/decisionManagerList" />
<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
<script type="text/javascript">
<!--

//-->
$(document).ready(function() {
    $('.js-example-basic-multiple').select2();
    $('.js-example-basic-single').select2();
});
</script>
<style>
	.ui-datepicker-trigger {
	   margin-left: 190px;
	}
	.cls-mg-bot {
		margin-top: 10px;
	}
</style>
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
		<div class="form-desing">
		 <div>
   <div class="row">
   <div class="ov_hidden">
   <div id="content_inner"> 
     <div class="new-heading-2">DANH SÁCH QUYẾT ĐỊNH CỬ ĐI CÔNG TÁC</div>
      <div class="cla-form-bca" style="margin-top: 10px;">
								<div style="margin: 2px">
								<div class="col-md-4 col-sm-4">
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Cơ quan ký:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<select style="width: 100%;" name="competentAuthoritiesTxt" id="competentAuthoritiesTxt" class="js-example-basic-single">
										<option value="">-- Chọn nơi ký -- </option>
										<c:set var="transitN" value="${formData.currentlyAssignedToUserId}" />
										<c:if test="${not empty codeGov}">
											<c:forEach var="entry" items="${codeGov}">
												<c:choose>
													<c:when test="${empty transitN}">
														<option value="${entry.id.codeValue}">${entry.codeValueDesc}</option>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${not (transitN == entry.id.codeValue)}">
																<option value="${entry.id.codeValue}">${entry.codeValueDesc}</option>
															</c:when>
															<c:otherwise>
																<option value="${entry.id.codeValue}" selected="true">${entry.codeValueDesc}</option>
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:if>
									</select>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Người ký:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<c:choose>
										<c:when test="${not empty codeSigner}">
											<select style="width: 100%;" name="signerTxt" id="signerTxt" class="js-example-basic-single">
													<option value="">-- Chọn người ký -- </option>
													<c:set var="transitNN" value="${formData.unassignAllForUserUserId}" />
													<c:forEach var="entry" items="${codeSigner}">
														<c:choose>
															<c:when test="${not (transitNN == entry.codeSigner)}">
																<option value="${entry.codeSigner}">${entry.nameSigner}</option>
															</c:when>
															<c:otherwise>
																<option value="${entry.codeSigner}" selected="true">${entry.nameSigner}</option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
											</select>
										</c:when>
										<c:otherwise>
											<select style="width: 100%;" name="signerTxt" id="signerTxt" class="js-example-basic-single">
													<option value="">--- Chọn người ký --- </option>
											</select>
										</c:otherwise>
									</c:choose>
									</div>						
								</div>
								<div class="col-md-4 col-sm-4">
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Từ ngày:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot" style="position: relative;">
										<form:input type="text" style="height: 27px;margin-bottom: -20px;" path="createDate" id="createDate" class="form-control" />
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Đến ngày:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot" style="position: relative;">
										<form:input type="text" style="height: 27px;margin-bottom: -20px;" path="issueDate" id="issueDate" class="form-control" />
									</div>								
								</div>
								<div class="col-md-4 col-sm-4">
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Số quyết định:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										 <form:input type="text" style="height: 27px;" path="searchTransactionId" class="form-control" />
									</div>
									<div class="col-md-12">
										<div style="margin-bottom: 10px;margin-top: 5px;">
											<button type="button" onclick="doSubmitSave(this.form);" id="userId" name="userId" class="btn_small btn-primary btn-search">
										        <span class="glyphicon glyphicon-search"></span> Tìm kiếm
										    </button>
										</div>
									</div>
								</div>							

							</div>
						</div>
	  <br />
	    					<div>
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="max-width: 50px;">STT
								
								      </th>	
								      <th class="th-sm" style="min-width: 140px;">Số quyết định
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Số người đi
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Người ký
								
								      </th>	
								      <th class="th-sm" style="min-width: 120px;">Ngày ký
								
								      </th>	
								      <th class="th-sm" style="min-width: 140px;">Cơ quan thẩm quyền
								
								      </th>	
								      <th class="th-sm" style="min-width: 130px;">Quốc gia đến
								
								      </th>	
								      <th class="th-sm" style="min-width: 120px;">Ngày tạo
								
								      </th>	
								      <th class="th-sm" style="min-width: 190px;">
								
								      </th>							     
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>							    
									      <td><c:url var="jobUrl" value="/servlet/decisionController/decisionManagerEdit/${item.id}" />
												<a style="color: blue;" href="#" onclick="editDecision('${item.decisionNumber}')"> <c:out value="${item.id}" /></a></td>							      
									      <td>${item.decisionNumber}</td>	
									      <td>${item.numberPerson}</td>	
									      <td>${item.signer}</td>	
									      <td>${item.signDate}</td>	
									      <td>${item.competentAuthorities}</td>	
									      <td>${item.countryPlan}</td>	
									      <td>${item.createDate}</td>	
									      	<td><button type="button" class="btn btn-primary btn-xs" onclick="editPerson('${item.decisionNumber}')" id="addPerson${item.id}">
												<span class="glyphicon glyphicon-th-list"></span> DS người cử đi
											</button>
											<c:if test="${item.numberPerson == 0}">
												<button type="button" class="btn btn-danger btn-xs"   onclick="deleteDecision('${item.decisionNumber}')" id="delete${item.id}">
													<span class="glyphicon glyphicon-remove"></span> Xóa
												</button>
												
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
			Không bản ghi trong danh sách quyết định cử đi công tác
			<br />
		</c:if>
		<c:if test="${not empty jobList}">
			
			<display:table cellspacing="1" cellpadding="0" id="row" sort="external"  partialList="true" size="${totalRecords}"
				export="false" class="displayTag table" name="jobList" defaultsort="1"
				defaultorder="ascending" pagesize="${pageSize}"
				requestURI="/servlet/decisionController/decisionManagerSearch">
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
	
				<c:url var="jobUrl" value="/servlet/decisionController/decisionManagerEdit/${row.id}" />
				<a href="#" onclick="editDecision('${row.decisionNumber}')"> <c:out value="${row.id}" /></a>
				</display:column>
				<display:column property="decisionNumber" sortable="false"
					title="Số quyết định" maxLength="30" >
				</display:column>
				<display:column property="numberPerson" sortable="false"
					title="Số người cử đi" maxLength="3" >
				</display:column>
				<display:column property="signer" sortable="false"
					title="Tên người ký" maxLength="20" >
				</display:column>
				<display:column property="signDate" sortable="false"
					title="Ngày ký" format="{0,date,dd-MMM-yyyy}">
				</display:column>
				<display:column property="competentAuthorities" sortable="false"
					title="Tên cơ quan thẩm quyền" maxLength="20" >
				</display:column>
				<display:column property="countryPlan" sortable="false"
					title="Quốc gia đến" maxLength="10" >
				</display:column>
				<display:column property="createDate" title="Ngày tạo" 
					sortable="false" format="{0,date,dd-MMM-yyyy}" >
				</display:column>
				<display:column>
					
					<button type="button" class="btn btn-primary" onclick="editPerson('${row.decisionNumber}')" id="addPerson${row.id}">
						<span class="glyphicon glyphicon-th-list"></span> DS người cử đi
					</button>
					<c:if test="${row.numberPerson == 0}">
						<button type="button" class="btn btn-danger"   onclick="deleteDecision('${row.decisionNumber}')" id="delete${row.id}">
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
	 <div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
			<div style="margin: 10px"> 			
				 	<button type="button" class="btn btn-success" id="addBtn";">
						<span class="glyphicon glyphicon-plus-sign"></span> Thêm mới quyết định
					</button> 
			</div>
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
					document.forms["formData"].action = '${decisionManagerUrl}';  
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
			document.forms["formData"].action = '<c:url value="/servlet/decisionController/applyFilter" />';
			document.forms["formData"].submit();
			//alert('Chức năng này đang được phát triển');
		}
	</script>
	<script type="text/javascript">
		function ClearDDL(){
			$("#signerTxt").empty();
			$('#signerTxt')
	        .append($("<option></option>")
	                   .attr("value","")
	                   .text("-- Chọn người ký --")); 
			document.forms["formData"].unassignAllForUserUserId.value = "";
		}
	
		$(function() {
    		$("#competentAuthoritiesTxt").change(function(){
	    		ClearDDL();
	    		var id = $("#competentAuthoritiesTxt").val();
	    		document.forms["formData"].currentlyAssignedToUserId.value = id;
	    	  	$.ajax({
	    		      type: "GET",
	    		      url: "${psUrl}", 
	    		      data: {id : id},//JSON.stringify(id),
	    		      dataType: "json",
	    		      contentType: "application/json; charset=utf-8",
	    		      success: function(response) {
	    		    	  if(response != null && response.length > 0){
		    		    	  for (var i = 0; i < response.length; i++) {
			    		    	  $('#signerTxt')
			    		          .append($("<option></option>")
			    		                     .attr("value",response[i].codeSigner)
			    		                     .text(response[i].nameSigner)); 
		    		    	  }
	    		    	  }
	    	          },
	    	          error: function(e) {
	    	             //alert('Error: ' + e);
	    	          } 
	    		});
      		});
    		
	    	$("#signerTxt").change(function(){
	    		var code = $("#signerTxt").val();
	    		document.forms["formData"].unassignAllForUserUserId.value = code;
	    	})
    	})
	
		$("#addBtn").click(function(){	
			 document.forms[0].action = '${addDecision}';
			 document.forms[0].submit();	
	
		});
	
		function editDecision(id){	
			 document.forms[0].action = '${updateDecision}' + '/' + id;
			 document.forms[0].submit();	
	
		};
		
		function deleteDecision(id){	
			$.confirm({
			    title: 'Thông báo',
			    content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/tip_1a.png\">" + ' Có chắc muốn xóa bản ghi này?',
			    buttons: {
			        OK: function () {
			        	$.post('<c:url value="/servlet/decisionController/deleteDecision" />' + '/' + id ,id,
					             function(data){
					   if(data=='success'){
					   	   $("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
					       $("#savedialog-confirm").html('Đã xóa thành công Quyết định :  '+ id);
					       $("#savedialog-confirm").dialog( 'open' );
					    }else if(data=='duplicate'){
					       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
					       $("#faildialog-confirm").html('Số quyết định:  '+ id + ' đã có danh sách thông tin người cử đi nên không thể thực hiện thao tác này');
					       $("#faildialog-confirm").dialog( 'open' );
					    }
					    else {
						       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
						       $("#faildialog-confirm").html('Không thể xóa Quyết định:  '+ id);
						       $("#faildialog-confirm").dialog( 'open' );
						}
					   });
			        },
			        CANCEL: function () {
			        	//return false;
			        }		       
			    }
			})
			/*if(!confirm('Có chắc muốn xóa bản ghi này?')){return false;}
			else
				{
					$.post('<c:url value="/servlet/decisionController/deleteDecision" />' + '/' + id ,id,
				             function(data){
				   if(data=='success'){
				   	   $("#savedialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#savedialog-confirm").html('Đã xóa thành công Quyết định :  '+ id);
				       $("#savedialog-confirm").dialog( 'open' );
				    }else if(data=='duplicate'){
				       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
				       $("#faildialog-confirm").html('Số quyết định:  '+ id + ' đã có danh sách thông tin người cử đi nên không thể thực hiện thao tác này');
				       $("#faildialog-confirm").dialog( 'open' );
				    }
				    else {
					       $("#faildialog-confirm").dialog('option', 'title', 'Thông báo');
					       $("#faildialog-confirm").html('Không thể xóa Quyết định:  '+ id);
					       $("#faildialog-confirm").dialog( 'open' );
					}
				   });
			}*/
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
		
		$("#issueDate").datepicker({
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
		
		$('#issueDate').datepicker().datepicker('setDate', "");
	</script>
</form:form>
<div id="savedialog-confirm" style="display: none;"></div>
<div id="faildialog-confirm" style="display: none;"></div>
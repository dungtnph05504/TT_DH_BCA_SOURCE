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
<c:url var="thongtinchitiet" value="/servlet/central/danhsachdphoiin" />
<c:url var="getDanhsachHsUrl" value="/servlet/central/getDanhsachHs" />
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
.cla-font {
    font-weight: 700;
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
   <div>
   <div class="row">
   <div class="ov_hidden">
      <div class="new-heading-2" style="margin-bottom: 0px;">DANH SÁCH ĐIỀU PHỐI IN HỘ CHIẾU</div>
      <div style="clear: both"></div>
			
						<div>
							<div>
							<div style="margin: 2px">
							<fieldset>
							<legend>Điều kiện tìm kiếm</legend>
							<div class="col-md-11 col-sm-11">
								<div class="col-md-4 col-sm-4">
									<div class="col-md-4 col-sm-4 cls-mg-bot">
										<div class="cla-font">TT Xử lý:</div>
									</div>
									<div class="col-md-8 col-sm-8 cls-mg-bot">
										<form:select path="regSiteCode" id="regSiteCode"
											name="regSiteCode" class="regSiteCode"
											style="font-family: Arial; font-size: 12px;width: 100%;" disabled="disabled">
											<c:forEach var="entry" items="${listTTxuly}">
												<form:option value="${entry.key}" style="margin-right: 5px;" label="${entry.value}" />
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="col-md-4 col-sm-4">
									<div class="col-md-4 col-sm-4 cls-mg-bot">
										<div class="cla-font">TT cá thể hóa:</div>
									</div>
									<div class="col-md-8 col-sm-8 cls-mg-bot">
										<form:select path="priority" id="priority"
											name="priority" class="priority"
											style="font-family: Arial; font-size: 12px;width: 100%;">
											<c:forEach var="entry" items="${listTTcathe}">
												<form:option value="${entry.key}" style="margin-right: 5px;" label="${entry.value}" />
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="col-md-4 col-sm-4">
									<div class="col-md-4 col-sm-4 cls-mg-bot">
										<div class="cla-font">Số danh sách:</div>
									</div>
									<div class="col-md-8 col-sm-8 cls-mg-bot">
										<form:input path="packageCode" type="text" style="width: 100%;"/>
									</div>
								</div>
								</div>								
								<div class="col-md-1 col-sm-1">
									<div style="margin: 2px;margin-bottom: 10px;margin-top: 10px;">
										<div>
											<button type="button" onclick="doApplyFilter();" class="btn_small btn-success">
										        <span class="glyphicon glyphicon-search"></span> Tìm kiếm
										    </button>	
											
										</div>
									</div>
								</div>
								<div class="col-md-11 col-sm-11">
									<div class="col-md-4 col-sm-4">
										<div class="col-md-4 col-sm-4 cls-mg-bot">
											<div class="cla-font">Từ ngày:</div>
										</div>
										<div class="col-md-8 col-sm-8 cls-mg-bot">
											<input type="text" placeholder="Từ ngày" readonly="readonly" autocomplete="off" path="createBy" id="createBy" name="createBy" class="createBy" style="width: 100%;font-size: 12px;">
										</div>
									</div>
									<div class="col-md-4 col-sm-4">
										<div class="col-md-4 col-sm-4 cls-mg-bot">
											<div class="cla-font">Đến ngày:</div>
										</div>
										<div class="col-md-8 col-sm-8 cls-mg-bot">
											<input type="text" placeholder="Đến ngày" readonly="readonly" autocomplete="off" path="endDate" id="endDate" name="endDate" class="endDate" style="width: 100%;font-size: 12px;">
										</div>
									</div>	
								</div>
								</fieldset>
							</div>
						</div>
			</div>

	  		<br />
			<br />
					<fieldset>
							<legend>Danh sách điều phối</legend>
							<div>
						      	<table id="dtBasicExample" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm">
										 STT
								      </th>	
								      <th class="th-sm">
											<input type="checkbox" >
								      </th>
								      <th class="th-sm">Số danh sách
								
								      </th>
								      <th class="th-sm">Ngày gửi
								
								      </th>
								      <th class="th-sm">TT xử lý
								
								      </th>
								      <th class="th-sm">TT cá thể hóa
								
								      </th>
								    </tr>
								  </thead>
								  <c:if test="${not empty dsXuLyA}">
									  <tbody>
									  	 <c:forEach items="${dsXuLyA}" var="item">
										    <tr>
										      <td class="align-central">
										      	  ${item.stt}
											  </td>	
											  <td class="align-central">
											  	<input type="checkbox" >
											  </td>
										      <td data-toggle="modal" onclick="showDanhsachHS('${item.packageId}');" data-target="#chiTietHS">${item.packageId}</td>
										      <td class="align-central" data-toggle="modal" onclick="showDanhsachHS('${item.packageId}');" data-target="#chiTietHS">${item.dateNin}</td>
										      <td data-toggle="modal" onclick="showDanhsachHS('${item.packageId}');" data-target="#chiTietHS">${item.listCode}</td>
										      <td data-toggle="modal" onclick="showDanhsachHS('${item.packageId}');" data-target="#chiTietHS">${item.listName}</td>
										    </tr>
									    </c:forEach>
									  </tbody>
								  </c:if>
								</table>
								<c:if test="${empty dsXuLyA}">
									 <div class="style-no-record">Không tìm thấy kết quả</div>
								</c:if>
								<c:if test="${not empty dsXuLyA}">
									<div class="col-md-5 col-sm-12" style="padding-left: 0px;">
										<div class="dataTables_info">Hiển thị ${startIndex} đến ${endIndex} của ${totalRecord} kết quả</div>
									</div>
									<div class="col-md-7 col-sm-12" style="padding-right: 0px;">
										<ul style="float: right;" class="pagination" id="pagination"></ul>
									</div>	
									<input type="hidden" name="pageNo" id="pageNo" />
								</c:if> 
						      </div>
						  </fieldset>
		</div>
	</div>

						<!-- Model phí bổ sung -->
						<div class="modal fade" id="chiTietHS" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
						  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1200px;height: 500px;">
						    <div class="modal-content">
						      	<div class="modal-header">
						        	<h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">THÔNG TIN CHI TIẾT DANH SÁCH HỒ SƠ</h5>
						        	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
						        	</button>
						      	</div>		
						     	<div class="modal-body" style="height: 350px;overflow: auto;padding:0"  id="danhsachHS">
						      	</div>
						    </div>
						  </div>
						</div>	
						<!-- -------------------------------------------------------------------------- -->
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
						document.forms["formData"].action = '${thongtinchitiet}';  
						document.forms["formData"].submit();  
					}
				}
			});
		function doSubmitSave(form) {
			form.action = '<c:url value="/servlet/central/search" />';
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
			document.forms["formData"].action = '${thongtinchitiet}';
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
		
		$("#createBy").datepicker({
			//showOn : "button",
			//buttonImage : "",
			//buttonImageOnly : true,
			changeMonth : true,
			changeYear : true,
			showSecond : true,
			controlType : 'select',
			dateFormat : 'dd/mm/yy',
			yearRange : "-100:+10"
		}).keyup(function(e) {
			if (e.keyCode == 8 || e.keyCode == 46) {
				$.datepicker._clearDate(this);
			}
		});
		//$('#createBy').datepicker().datepicker('setDate', new Date());
		$("#endDate").datepicker({
			//showOn : "button",
			//buttonImage : "",
			//buttonImageOnly : true,
			changeMonth : true,
			changeYear : true,
			showSecond : true,
			controlType : 'select',
			dateFormat : 'dd/mm/yy',
			yearRange : "-100:+10"
		}).keyup(function(e) {
			if (e.keyCode == 8 || e.keyCode == 46) {
				$.datepicker._clearDate(this);
			}
		});
		
		function showDanhsachHS(value){
			$('#danhsachHS').html("");
			var url = '${getDanhsachHsUrl}';
			$.ajax({
				url : url,
				cache: false,
				data : JSON.stringify(value),
				contentType : 'application/json',
				type: "POST",
				success : function(data) {
					$('#danhsachHS').html(data);							
				}
			});
		}	
		//$('#endDate').datepicker().datepicker('setDate', new Date());
	</script>
	</div>
	</div>
	</div>
</form:form>



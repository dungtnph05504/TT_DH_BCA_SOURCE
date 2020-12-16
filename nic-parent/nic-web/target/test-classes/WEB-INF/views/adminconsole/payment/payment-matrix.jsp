<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<c:url var="updatePaymentMatrixUrl"
	value="/servlet/paymentMatrixController/updatePaymentMatrix" />
<c:url var="updateParameterUrl"
	value="/servlet/paymentMatrixController/updateParameter" />
<c:url var="addPaymentMatrix"
	value="/servlet/paymentMatrixController/addPaymentMatrix" />
<c:url var="addParameterUrl" value="/servlet/paymentMatrixController/addParameter" />
<style>
#heading_report-1 {
	text-align: center;
    float: none;
    height: 45px;
    line-height: 45px;
    width: 300px;
    font-weight: bold;
    margin-bottom: 10px;
    color: #FFF;
    background: #c1c8ce;
}

.open-2 {
	display: none;
	margin-top: 50px;
}
.open-1 {
	margin-top: 50px;
}
.color-1 {
	color: white;
}
.color-2 {
	color: #508ccc;
}
table#row > tr {
	height: 35px;
}
.displayTag tr.odd {
	height: 35px;
}
.displayTag tr.even {
	height: 35px;
}
.fonts1 {
	height: 40px;
	font-size: 18px;
}
</style>
<body>

<div class="form-desing">
<div>
     <div class="row">
         <div class="ov_hidden">
		<div id="cls2" class="new-heading-2 col-md-6 fonts1">QUẢN LÝ THÔNG SỐ THANH TOÁN</div>
		<div id="cls3" class="new-heading-2 col-md-6">CẤU HÌNH PHÍ</div>
		
<c:if test="${paymentMatrixDefForm.status=='success'}">
<div class="border_success" style="display: true">

<!--<div class="success"> -->
<div class="success_left">
 <img align='left' src="<c:url value="/resources/images/success.jpg" />" width="30" height="30" style="padding:5px 0 0 15px"/>
 
 </div>
<div class="success">
 <table width="800" height="30" border="0" cellpadding="5">
<tr> 
    <td width="587" height="28" style="font-size: 18px; font-weight:bold; color: #000;"> <p class="text_pad">&nbsp;Đã khắc phục thành công Ma trận thanh toán: <c:out value="${paymentMatrixDefForm.transactionType}" /></p>
    
  </tr>
 </table>
</div>
</div>
</c:if>
<c:if test="${paymentMatrixDefForm.status=='fail'}">
<div class="border_error">
<!--<div class="errors"> -->
<div class="success_left">
 <img align='left' src="<c:url value="/resources/images/alert.png" />"  width="30" height="30" style="padding:5px 0 0 15px"/>
 </div>
<div class="errors"  style="display: none;">

<table width="800" height="30" border="0" cellpadding="5">

  <tr>
    <td width="587" height="28" style="font-size: 18px; font-weight:bold; color: #000;"> <p class="text_pad">&nbsp;Không thể xóa: <c:out value="${paymentMatrixDefForm.transactionType}" /></p>
  </tr>
 
</table>

</div>
</div>
</c:if>
		               		<div class="open-1">
						      	<table id="dtBasicExample1" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="max-width: 150px;">Tên 
								
								      </th>	
								      <th class="th-sm" style="min-width: 150px;">Giá trị
								
								      </th>
								      <th class="th-sm" style="min-width: 150px;">Phạm vi
								
								      </th>							     
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList1}" var="item">
									    <tr>							    
									      <td>${item.id.paraName}</td>									   
									      <td>${item.paraShortValue}</td>	
									      <td>${item.id.paraScope}</td>							     
									    </tr>
								    </c:forEach>
								  </tbody>
								</table>
						      </div>
						      
						      <div class="open-2">
						      	<table id="dtBasicExample2" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="max-width: 140px;">Loại giao dịch
								
								      </th>	
								      <th class="th-sm" style="min-width: 140px;">Kiểu giao dịch
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Phí giao dịch
								
								      </th>	
								      <th class="th-sm" style="min-width: 100px;">Giảm giá
								
								      </th>	
								      <th class="th-sm" style="min-width: 130px;">Được tạo bởi
								
								      </th>	
								      <th class="th-sm" style="min-width: 130px;">Ngày tạo
								
								      </th>	
								      <th class="th-sm" style="min-width: 130px;">Cập nhật bởi
								
								      </th>	
								      <th class="th-sm" style="min-width: 130px;">Ngày cập nhật
								
								      </th>	
								      <th class="th-sm" style="min-width: 50px;">Xóa?
								
								      </th>							     
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList2}" var="item">
									    <tr>							    
									      <td><c:url var="updatePaymentMatrixUrl1" value="/servlet/paymentMatrixController/updatePaymentMatrix/${item.id.transactionType},${item.id.transactionSubtype},${item.id.noOfTimeLost}" />
		 									<a style="color: blue;" href="${updatePaymentMatrixUrl1}"> 
		 									<c:out value="${item.id.transactionType}" /> </a></td>									   
									      <td>${item.id.transactionSubtype}</td>	
									      <td>${item.feeAmount}</td>
									      <td>${item.reduceRateFlag}</td>
									      <td>${item.createBy}</td>
									      <td>${item.createDateTime}</td>
									      <td>${item.updateBy}</td>
									      <td>${item.updateDateTime}</td>
									      <td>${item.deleteFlag}</td>
									      							     
									    </tr>
								    </c:forEach>
								  </tbody>
								</table>
						      </div>
		<!--<form:form modelAttribute="paymentParamForm" commandName="paramList" class="open-1"
			id="form" action="/servlet/paymentMatrixController/paymentMatrix"
			method="post" >
			<display:table cellspacing="0" cellpadding="0" id="row"
				export="false" class="displayTag" name="paramList" defaultsort="1"
				defaultorder="ascending" pagesize="10"
				requestURI="/servlet/paymentMatrixController/paymentMatrix" partialList="true" size="${totalRecords}">
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
				<display:column title="Tên" sortable="true">
					 <c:out value="${row.id.paraName}" />
				</display:column>
				<display:column property="paraShortValue" sortable="true"
					title="Giá trị" maxLength="20" />
				<display:column property="id.paraScope" sortable="true"
					title="Phạm vi" maxLength="10" />
			</display:table>
		</form:form>-->
		<!--<form:form commandName="paymentList" id="form1" class="open-2" 
			action="/servlet/paymentMatrixController/paymentMatrix" method="post">

			<br />
		
			<display:table cellspacing="o" cellpadding="0" id="rowPaymentList"
				export="false" class="displayTag" name="paymentList" defaultsort="1"
				defaultorder="ascending" pagesize="10"
				requestURI="/servlet/paymentMatrixController/paymentMatrix" partialList="true" size="${totalPaymentRecords}">
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
				<display:column title="Loại giao dịch" sortable="true" >
					<c:url var="updatePaymentMatrixUrl1" value="/servlet/paymentMatrixController/updatePaymentMatrix/${rowPaymentList.id.transactionType},${rowPaymentList.id.transactionSubtype},${rowPaymentList.id.noOfTimeLost}" />
		 			<a href="${updatePaymentMatrixUrl1}"> <c:out
							value="${rowPaymentList.id.transactionType}" /> </a>
				</display:column>
				<display:column property="id.transactionSubtype" sortable="true"
					title="Kiểu giao dịch" maxLength="20" />
				<display:column property="feeAmount" sortable="true"
					title="Số tiền phí giao dịch" maxLength="10" />
				<display:column property="reduceRateFlag" sortable="true"
					title="Giảm giá" maxLength="5" />
				<display:column property="createBy" sortable="true"
					title="Được tạo bởi" maxLength="10" />
				<display:column property="createDateTime" sortable="true"
					title="Ngày tạo" maxLength="10" format="{0,date,dd-MMM-yyyy}"  />
				<display:column property="updateBy" sortable="true"
					title="Cập nhật bởi" maxLength="10" />
				<display:column property="updateDateTime" sortable="true"
					title="Ngày cập nhật" maxLength="10" format="{0,date,dd-MMM-yyyy}" />
				<display:column property="deleteFlag" sortable="true"
					title="Xóa?" maxLength="5" />
			</display:table>
		</form:form>-->
	

	</div>
</div>
</div>
</div>
<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
			<div style="margin: 10px"> 			
				 	<button type="button" class="btn btn-success" id="addBtn">
						 <span class="glyphicon glyphicon-plus-sign"></span> Thêm mới thanh toán
					</button> 
			</div>
</div>
	<div id="dialog-confirm"></div>
	<div id="dialog-confirm1"></div>
	<!-- script for the delete for payment parameter-->
	<script>
		$(function() {
			$("#dialog-confirm1").dialog({
				modal : true,
				autoOpen : false,
				width : 500,
				resizable : true,
				show : {
					effect : "fade",
					duration : 200
				},
				hide : {
				//effect: "explode",
				//duration: 1000
				},
				buttons : {
					Ok : function() {
						window.location.assign("parameter-mgmt-delete.html");
					},
					Cancel : function() {
						$(this).dialog("close");
					}
				}
			});
		});
		
		$("#addBtn").click(function(){	
			 document.forms[0].action = '${addPaymentMatrix}';
			 document.forms[0].submit();	

		});
	</script>
	<script type="text/javascript">
		function deleteParameter(MIN_CITIZEN_AGE_FOR_REDUCE_RATE, obj) {
			$("#dialog-confirm1").dialog('option', 'title',
					'Delete ParameterName');
			$("#dialog-confirm1").html(
					'Bạn có muốn xóa tên thông số('
							+ MIN_CITIZEN_AGE_FOR_REDUCE_RATE
							+ ')?<br>Please OK to confirm.');
			$("#dialog-confirm1").dialog('open');
		}
	</script>

	<!-- script for the delete for payment matrix-->
	<script>
		$(function() {
			 $('#dtBasicExample1').DataTable();
			 $('.dataTables_length').addClass('bs-select');
			 $('#dtBasicExample2').DataTable();
			 $('.dataTables_length').addClass('bs-select');
			$("#dialog-confirm").dialog({
				modal : true,
				autoOpen : false,
				width : 500,
				resizable : true,
				show : {
					effect : "fade",
					duration : 200
				},
				hide : {
				//effect: "explode",
				//duration: 1000
				},
				buttons : {
					Ok : function() {
						window.location.assign("payment-matrix-one.html");
					},
					Cancel : function() {
						$(this).dialog("close");
					}
				}
			});
			$("#cls2").click(function(){
				$('.open-1').css('display', 'block');
				$('.open-2').css('display', 'none');
				$('#cls2').removeClass().addClass("new-heading-2 col-md-6 fonts1");
				$('#cls3').removeClass().addClass("new-heading-2 col-md-6");
			});
			
			$("#cls3").click(function(){
				$('.open-1').css('display', 'none');
				$('.open-2').css('display', 'block');
				$('#cls2').removeClass().addClass("new-heading-2 col-md-6");
				$('#cls3').removeClass().addClass("new-heading-2 col-md-6 fonts1");
			});
		});
	</script>
	<script type="text/javascript">
		function deletePayment(TranType, obj) {
			$("#dialog-confirm").dialog('option', 'title',
					'Delete Payment Matrix');
			$("#dialog-confirm").html(
					'Bạn có muốn xóa ma trận thanh toán(' + TranType
							+ ')?<br>Please OK to confirm.');
			$("#dialog-confirm").dialog('open');
		}
		
	</script>


</body>

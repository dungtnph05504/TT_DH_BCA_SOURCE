<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<script src="https://cdn.ckeditor.com/4.12.1/standard/ckeditor.js"></script>
<c:url var="prodUrl" value="/servlet/investigation/startinvestigation" />
<c:url var="newUrl" value="/servlet/investigation/newInvestigation" />
<c:url var="pendingJobUrl" value="/servlet/investigation/pendingJobList" />
<c:url var="investigationJobUrl" value="/servlet/investigation/investigation" />
<c:url var="invesProcessUrl" value="/servlet/investionProcess/showInvestigation" />
<c:url var="nextProcessUrl" value="/servlet/investionProcess/invesProcess" />
<c:url var="showMessageUrl" value="/servlet/investionProcess/showMessage" />
<c:url var="searchTraoDoiXMUrl" value="/servlet/central/searchTraoDoiXM" />
<c:url var="inXacMinhTraoDoiUrl" value="/servlet/central/inXacMinhTraoDoi" />
<c:url var="inBoTucHSUrl" value="/servlet/central/inBoTucHS" />
<c:url var="inDanhSachHSUrl" value="/servlet/central/inDanhSachHS" />
<%-- <form:form commandName="jobList" id="form" name="form" > --%>
<style>

.cls-mg-bot {
	margin-top: 10px;
}
.mr5_border1_p10 {
	margin-right: 5px;
    border: 1px solid #ddd;
}

.form-control {
	height: 20px !important;
}
.font-12 {
	font-weight: normal;
   
    font-size: 12px;
}
.titlechuhoso {
    width: 100%;
    float: left;
    text-align: center;
    margin-bottom: 5px;
}
.titledshoso {
    width: 45%;
    float: left;
    text-align: center;
}
.w45Pc_fl_mb5 {
    width: 45%;
    float: left;
    margin-bottom: 5px;
}
.listdshoso {
    height: 227px;
    overflow-y: auto;
    border: 1px solid #ddd;
}
.btnchucnang {
    width: 10%;
    float: left;
    padding-top: 30px;
    text-align: center;
}
.size-btn{
	width: 40%;
    margin-top: 10px;
}
#ajax-load-qa {
	background: rgba(255, 255, 255, .5)
		url('<c:url value="/resources/images/loading_nin.gif" />') 50%
		50% no-repeat;
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
            <div class="oplog-title__txt">Công văn xác minh, trao đổi, bổ túc hồ sơ xin cấp hộ chiếu</div>
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

  
    <div class="content-item-information" style="margin-top: 50px;">
    	<div class="form-group" style="margin-bottom: 30px;">
    		<div class="col-sm-2 mr5_border1_p10">
    			<div>
    				<label class="control-label">Số danh sách bàn giao</label>
    				<input type="text" id="soDSBanGiao" class="form-control" />
    			</div>
    			<div style="text-align: center;margin-top: 10px;">
    				<button class="btn btn-success" type="button" onclick="searchDanhSach();"> Danh sách </button>
    			</div>
    			
    			<div style="margin-top: 10px;">
    				<label class="control-label">Người duyệt</label>
    				<select class="form-group">
    					<c:forEach items="${usersKhuVuc}" var="iem">
	    					<option value="${iem.key}">${iem.value}</option>
    					</c:forEach>
    				</select>
    			</div>
    			<div>
    				<label class="control-label">Chức vụ</label>
    				<input type="text" class="form-control" />
    			</div>
    			<div>
    				<label class="control-label">Ngày duyệt</label>
    				<input type="text" class="form-control" id="createDate"/>
    			</div>
    			
    			<div style="margin-bottom: 5px;margin-top: 10px;">
    				<input type="radio" value="1" id="rdButon1" name="rdoButton" checked="checked" />
    				<label class="font-12">In xác minh, trao đổi</label>
    			</div>
    			<div style="margin-bottom: 5px;">
    				<input type="radio" value="2" id="rdButon2" name="rdoButton"/>
    				<label class="font-12">In không cấp hộ chiếu</label>
    			</div>
    			<div style="margin-bottom: 5px;">
    				<input type="radio" value="3" id="rdButon3" name="rdoButton"/>
    				<label class="font-12">In bổ túc hồ sơ</label>
    			</div>
    			
    		</div>
    		<div class="col-sm-10 mr5_border1_p10">
    			<div class="titlechuhoso">
    				<span>CHỦ HỒ SƠ</span>
    			</div>
    			<div class="titledshoso">
    				<span>Danh sách hồ sơ</span>
    			</div>
    			<div class="titledshoso">
    				<span>Danh sách in đề xuất</span>
    				
    			</div>
    			<div class="w45Pc_fl_mb5 listdshoso">
    				<table id="tbDanhSachHS" style="margin-top: 0px;" class="table table-hover table-sm">
	    				  <tbody id="tbdDanhSachHS">
	    				  </tbody>	
    				</table>
    			</div>
    			<div class="btnchucnang">
    				<div>
	    				<input id="addBtnId" type="button"
									class="btn_small btn-success btn-with size-btn" value=">" align="center"  />
    				</div>
    				<div>
	    				<input id="addAllBtnId" type="button"
									class="btn_small btn-success btn-with size-btn" value=">>" align="center"  />
    				</div>
    				<div>
						<input id="removBtnId" type="button"
									class="btn_small btn-success btn-with size-btn" value="<" align="center"  />
    				</div>
    				<div>
						<input id="removAllBtnId" type="button"
									class="btn_small btn-success btn-with size-btn" value="<<" align="center"  />						
    				</div>
    			</div>
    			<div class="w45Pc_fl_mb5 listdshoso">
    				<table id="tbDanhSachDX" style="margin-top: 0px;"  class="table table-hover">
	    				<tbody id="tbdDanhSachDX">
	    				</tbody>	
    				</table>
    			</div>
    			<div class="w45Pc_fl_mb5 tongsohoso">
    				<label class="control-label">Tổng số hồ sơ</label>
    				<input type="text" id="txtTSHaSo" style="border: 1px solid #9e9e9e;height: 20px;width: 100px;" />
    			</div>
    			<div class="w45Pc_fr_mb5 tongsohosoduocchon">
    				<input type="text" id="txtTSHaSoDC" style="border: 1px solid #9e9e9e;height: 20px;float: right;width: 100px;" />
    				<label class="control-label" style="float: right;">Tổng số hồ sơ được chọn</label>
    			</div>   			
				 <div class="col-md-12">  
				 	 <label class="control-label">Nội dung trao đổi, xác minh / Lý do chưa được XC, Nội dung hồ sơ còn thiếu <i> (Nhập tối đa 500 ký tự)</i></label>	
				     <textarea name="editor1"></textarea>  
				 </div> 
    		</div>
    	</div> 
	</div>
	<div id="ajax-load-qa"></div>
	
	<!-- Message in danh sách hồ sơ -->
	<div class="modal fade" id="danhSachHS" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
	  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1000px;">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title txtH52" id="exampleModalLongTitle" style="display: inline-block;">IN TRAO ĐỔI XÁC MINH</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
	        </button>
	      </div>
	      <div class="modal-body" id="idDanhSachHS">
	      		
	      </div>							      
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" class="btn btn-warning"  data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove"></span > Đóng
	       		</button>
	       		<button type="button" onclick="inDanhSach1();" class="btn btn-success" style="float: right;margin-right: 10px;">
	       			<span class="glyphicon glyphicon-print"></span > In thông tin
	       		</button>
	       		
	      </div>
	    </div>
	  </div>
	</div>	
	<!-- ---------------------------------------------------------------------------->
	
	<!-- Message lưu hồ sơ -->
	<div class="modal fade" id="traoDoiMes" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
	  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1000px;">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">IN TRAO ĐỔI XÁC MINH</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
	        </button>
	      </div>
	      <div class="modal-body" id="idTraoDoiMes">
	      		
	      </div>							      
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" class="btn btn-warning"  data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove"></span > Đóng
	       		</button>
	       		<button type="button" onclick="inTranDoi1();" class="btn btn-success" style="float: right;margin-right: 10px;">
	       			<span class="glyphicon glyphicon-print"></span > In thông tin
	       		</button>
	       		
	      </div>
	    </div>
	  </div>
	</div>	
	<!-- ---------------------------------------------------------------------------->
	
	
	
	<!-- Message in bổ túc hs -->
	<div class="modal fade" id="boTucHoSo" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
	  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1000px;">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">IN PHIẾU BÁO BỔ TÚC HỒ SƠ</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
	        </button>
	      </div>
	      <div class="modal-body" id="idBoTucHS">
	      		
	      </div>							      
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" class="btn btn-warning"  data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove"></span > Đóng
	       		</button>
	       		<button type="button" onclick="inBoTucHS();" class="btn btn-success" style="float: right;margin-right: 10px;">
	       			<span class="glyphicon glyphicon-print"></span > In thông tin
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
				<span class="glyphicon glyphicon-print"></span> In chi tiết HS
			</button>
			<button type="button"  onclick="printDSHS();" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-print"></span> In danh sách HS
			</button>
		</div>
	</div>	
	</div>
<div id="dialog-confirm"></div>

	<script type="text/javascript">
		CKEDITOR.replace( 'editor1' );
	</script>
	<script type="text/javascript">
	
		var transId = '';
		var nametb = '';
		var tbt = '';
		var count = 0;
		var count1 = 0;
		var arr = [];
		var arrSearch = [];
		var danhSachBanGiao = '';
		var kind = '';
		function searchDanhSach(){
			tbt = '';
			arr = [];
			$("#tbDanhSachHS tbody").empty();
			$("#tbDanhSachDX tbody").empty();
			var soDanhSach = $('#soDSBanGiao').val();
			if(soDanhSach == ''){
				$.notify("Chưa nhập số danh sách bàn giao.", {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return; 
			}
			var url = '${searchTraoDoiXMUrl}';
			danhSachBanGiao = soDanhSach;
			$.ajax({
				url : url,
				cache: false,
				async:false,
				type: "POST",
				data: {
					danhsachA : soDanhSach
				},
				success : function(data) {
					//handoverId = soDanhSach;
					if(data.length > 0){
						//var tb1 = document.getElementById("tbdDanhSachHS");
						var str = '';
						for(var i = 0; i < data.length; i++){
							str += '<tr id="tr_'+data[i].transactionId+'"' + "onclick=\"clkRowTable('"+data[i].transactionId+"', '"+data[i].fullName+"');\"><td>" + data[i].fullName + "</td></tr>";
							
							/*8var row = tb1.insertRow(i);
							var cell1 = row.insertCell(0);
							cell1.innerHTML = data[i];*/
							arrSearch.push(data[i].transactionId);
						}
						tbt = str;
						count = data.length;
						$('#tbdDanhSachHS').html(str);
						$('#txtTSHaSo').val(data.length);
						$('#txtTSHaSoDC').val('0');
					}else{
						$.notify("Số danh sách vừa nhập không tồn tại.", {
							globalPosition: 'bottom left',
							className: 'warn',
						});
						count1 = 0; 
						$('#txtTSHaSo').val('0');
						$('#txtTSHaSoDC').val('0');
					}
				}
			});
		}
		
		function printDSHS(){
			var vl = $("input[name='rdoButton']:checked").val();			
			 var txt = '';
			 //alert(rows.length);
			 for (i = 0; i < arr.length; i++) {				 
				 txt += arr[i] + ",";
			 }
			 //alert(txt);
			 if(arr.length < 1){
				$.notify("Chưa có hồ sơ nào được chọn.", {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return; 
			 }
			 if(vl == '1'){
				 $('h5.txtH52').text('IN XÁC MINH TRAO ĐỔI');
			 }else if(vl == '2'){
				 $('h5.txtH52').text('IN KHÔNG CẤP HỘ CHIẾU');
			 }else if(vl == '3'){
				 //alert('3');
				 $('h5.txtH52').text('IN BỔ TÚC HỒ SƠ');
			 }
			 $('#ajax-load-qa').css('display', 'block');	
			 $('#idDanhSachHS').html('');
			 var url = '${inDanhSachHSUrl}';
			$.ajax({
				url : url,
				cache: false,
				async:false,
				type: "POST",
				data: {
					danhsach : txt,
					danhSachA : danhSachBanGiao
				},
				success : function(data) {
					$('#ajax-load-qa').css('display', 'none');	
					$('#idDanhSachHS').html(data);
					$('#danhSachHS').modal();
				}
			});
		}
		
		function processHS(){
			 var vl = $("input[name='rdoButton']:checked").val();			
			 var txt = '';
			 //alert(rows.length);
			 for (i = 0; i < arr.length; i++) {				 
				 txt += arr[i] + ",";
			 }
			 //alert(txt);
			 if(arr.length < 1){
					$.notify("Chưa có hồ sơ nào được chọn.", {
						globalPosition: 'bottom left',
						className: 'warn',
					});
					return; 
				 }
			 if(vl == '1'){				 
				 $('#ajax-load-qa').css('display', 'block');	
				 $('#idTraoDoiMes').html('');
				 var url = '${inXacMinhTraoDoiUrl}';
					$.ajax({
						url : url,
						cache: false,
						async:false,
						type: "POST",
						data: {
							danhsach : txt
						},
						success : function(data) {
							$('#ajax-load-qa').css('display', 'none');	
							$('#idTraoDoiMes').html(data);
							$('#traoDoiMes').modal();
						}
					});
			 }else if(vl == '2'){
				 
			 }else if(vl == '3'){
				 //alert(vl);
				 $('#ajax-load-qa').css('display', 'block');	
				 $('#idBoTucHS').html('');
				 var url = '${inBoTucHSUrl}';
					$.ajax({
						url : url,
						cache: false,
						async:false,
						type: "POST",
						data: {
							danhsach : txt
						},
						success : function(data) {
							$('#ajax-load-qa').css('display', 'none');	
							$('#idBoTucHS').html(data);
							$('#boTucHoSo').modal();
						}
					});
			 }
		}
		
	
		function doApplyFilter() {
			document.forms["formData"].action = '<c:url value="/servlet/investionProcess/showInvestigation" />';
			document.forms["formData"].submit();
		}
			
		
		
		
		
		$("#createDate").datepicker({
			//showOn : "button",
			//buttonImage : "<c:url value="/resources/images/calendar.gif" />",
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
		
		$('#createDate').datepicker().datepicker('setDate', new Date());
		
		function clkRowTable(value, name){
			transId = value;
			nametb = name;
			kind = 'N';
			$('#tbDanhSachHS tr').removeClass("back-gr");
			$('#tbDanhSachDX tr').removeClass("back-gr");
			/*var tb1 = document.getElementById('tbDanhSachHS');
			for(var i = 0; i < tb1.rows.length; i++)
			{
				tb1.rows[i].classList.remove("back-gr");
			}*/
			$('#tr_' + value).addClass("back-gr");
		}
		
		function clkRowTable1(value, name){
			transId = value;
			nametb = name;
			kind = 'B';
			/*var tb1 = document.getElementById('tbDanhSachDX');
			for(var i = 0; i < tb1.rows.length; i++)
			{
				tb1.rows[i].classList.remove("back-gr");
			}*/
			$('#tbDanhSachHS tr').removeClass("back-gr");
			$('#tbDanhSachDX tr').removeClass("back-gr");
			$('#tr_' + value).addClass("back-gr");
		}
		
		$('#addBtnId').click(function(){
			if(transId == '' || nametb == '' || kind == 'B'){
				return;
			}
			$('#tr_' + transId).remove();
			var str = '<tr id="tr_'+transId+'"' + "onclick=\"clkRowTable1('"+transId+"', '"+nametb+"');\"><td>" + nametb + "</td></tr>";
			$('#tbdDanhSachDX').append(str);
			var count = Number($('#txtTSHaSo').val()) - 1;
			var count1 = Number($('#txtTSHaSoDC').val()) + 1;	
			$('#txtTSHaSo').val(count);
			$('#txtTSHaSoDC').val(count1);
			arr.push(transId);
			transId = '';
			nametb = '';
		});
		$('#addAllBtnId').click(function(){
			$('#tbdDanhSachHS').html('');
			$('#tbdDanhSachDX').html(tbt);
			var count1 = Number($('#txtTSHaSo').val()) +  Number($('#txtTSHaSoDC').val());
			var count = 0;	
			$('#txtTSHaSo').val(count);
			$('#txtTSHaSoDC').val(count1);
			arr = arrSearch;
			transId = '';
			nametb = '';
		});
		$('#removBtnId').click(function(){
			if(transId == '' || nametb == '' || kind == 'N'){
				return;
			}
			$('#tr_' + transId).remove();
			var str = '<tr id="tr_'+transId+'"' + "onclick=\"clkRowTable('"+transId+"', '"+nametb+"');\"><td>" + nametb + "</td></tr>";
			$('#tbdDanhSachHS').append(str);
			var count = Number($('#txtTSHaSo').val()) + 1;
			var count1 = Number($('#txtTSHaSoDC').val()) - 1;	
			$('#txtTSHaSo').val(count);
			$('#txtTSHaSoDC').val(count1);
			var i = arr.indexOf(transId);
			if (i != -1) {
			    arr.splice(i,1);
			}
			transId = '';
			nametb = '';
		});
		$('#removAllBtnId').click(function(){
			$('#tbdDanhSachHS').html(tbt);
			$('#tbdDanhSachDX').html('');
			var count = Number($('#txtTSHaSo').val()) +  Number($('#txtTSHaSoDC').val());
			var count1 = 0;	
			$('#txtTSHaSo').val(count);
			$('#txtTSHaSoDC').val(count1);
			arr = [];
			transId = '';
			nametb = '';
		});
		
		
		
	</script>
	
</form:form>
</div>


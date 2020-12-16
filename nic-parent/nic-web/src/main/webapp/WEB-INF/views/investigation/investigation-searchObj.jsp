<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="JobInvestigationConfirmUrl" value="/servlet/investigationConfirm/investigationConfirmList" />
<c:url var="searchInvesBUrl" value="/servlet/investigationConfirm/searchInvesB" />
<c:url var="detailInvesBUrl" value="/servlet/investigationConfirm/detailInvesB" />
<c:url var="getInfoTransactionIdUrl" value="/servlet/investionProcess/getInfoTransactionByNin" />
<c:url var="getDocumentUrl" value="/servlet/investionProcess/getDocument" />
<c:url var="showDocumentUrl" value="/servlet/investionProcess/showDocument" />
<c:url var="nextProcessUrl" value="/servlet/investionProcess/invesProcess" />
<style>
.cls-mg-bot {
	margin-top: 10px;
}
table.displayTag>thead>tr {
	height: 35px;
}
.displayTag tr.odd {
	height: 35px;
}
.displayTag tr.even {
	height: 35px;
}
.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
    white-space: pre-wrap;
}
.form-left {
	border: 1px solid rgb(226, 235, 242);
	width: 75%;	
	padding-bottom: 50px;
	float: left;
	-webkit-transition: width 0.5s;
	-webkit-transition: width 0.5s;
}
.form-right {
	width: 25%;	
	float: left;
	height: 600px;
	transition: width 0.5s;
	-webkit-transition: width 0.5s;
}
.form-central {
	width: 1%;
	float: left;
	height: 700px;
	text-align: center;
	
}
.clss-font {
	font-size: 12px !important;
}
.top-mag-10 {
	margin-top: 10px;
}
.ys-search {
	display: none;
}
.opentb-2 {
}

.dataTables_length {
	display: none;
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
<c:url var="passportUrl" value="/servlet/investigationConfirm/passportNoEdit" />
<c:if test="${not empty requestScope.Errors}">
	<div class="border_error">
		<div class="error_left">
			<img align='left'
				src="<c:url value="/resources/images/error_new.jpg" />" width="30"
				height="30" />
		</div>


		<div class="errors">
			<table width="600" height="10" border="0" cellpadding="5">
				<tr>
					<td
						style="padding-left: 5px; font-weight: bold; vertical-align: top;">
						<c:forEach items="${requestScope.Errors}" var="errorMessage">
									${errorMessage}
							</c:forEach>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<br />
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
					<td
						style="padding-left: 5px; font-weight: bold; vertical-align: top;">
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
<form:form modelAttribute="formData" name="formData">
	<c:forEach items="${dsJob}" var="chk_process">
		<form:hidden path="selectedJobIds" value="${chk_process}"/>
	</c:forEach>
	<c:forEach items="${loadJob}" var="chk_load">
		<form:hidden path="loadJobIds" value="${chk_load}"/>
	</c:forEach>
	<form:hidden path="jobId" value="${jobCompare}"/>
	<form:hidden path="stageLoad" value="${stage}"/>
	<div class="form-desing">
		<div>
			<div>
				<div class="row">
					<div class="ov_hidden">

						<div class="new-heading-2">TRA CỨU ĐỐI TƯỢNG
							<div style="color: red;float: right;margin-right: 40px;font-size: 20px;">${stt} <span style="font-size: 20px;">/</span>${count}</div>
						</div>
						<div style="clear: both"></div>

						<div style="min-height: 10px"></div>
						<div>
							<div class="col-md-8 col-sm-8">
								<fieldset>
									<legend>Thông tin hồ sơ</legend>
									<div class="col-md-9">
										<div class="col-md-4">
											<div>Họ tên</div>
											<input type="text" value="${rootEntity.fullName_O}" readonly="readonly"/>
										</div>
										<div class="col-md-4">
											<div>Ngày sinh</div>
											<input type="text" value="${rootEntity.dob_O}" readonly="readonly"/>
										</div>
										<div class="col-md-4">
											<div>Giới tính</div>
											<input type="text" value="${rootEntity.gender_O}" readonly="readonly"/>
										</div>
										<div class="col-md-4">
											<div>Số hộ chiếu</div>
											<input type="text" value="${rootEntity.passportNo_O}" readonly="readonly"/>
										</div>
										<div class="col-md-4">
											<div>CMND/CCCD</div>
											<input type="text" value="${rootEntity.nin_O}" readonly="readonly"/>
										</div>
										<div class="col-md-4">
											<div>Quốc tịch</div>
											<input type="text" value="${rootEntity.nationality_O}" readonly="readonly"/>
										</div>
										<div class="col-md-8">
											<div>Địa chỉ</div>
											<input type="text" value="${rootEntity.address_O}" style="width: 100%;" readonly="readonly"/>
										</div>
										<div class="col-md-4">
											<div>Nơi sinh</div>
											<input type="text" value="${rootEntity.pob_O}" readonly="readonly"/>
										</div>
									</div>
									<div class="col-md-3">
										<div id="dialog-image_photoCompare" style="display: block;width: 135px;height: 180px;">
											<div class="centerCaption">       
										         <div style="text-align:center">							             									                 
													    <c:choose>
										                    <c:when test="${not empty rootEntity.image_O}">
										                        <div>
										                            <img src="data:image/jpg;base64,${rootEntity.image_O}"
										                                 class="img-border doGetAJpgSafeVersion" height="180" width="135" />
										
										                        </div>
										                    </c:when>
										                    <c:otherwise>
										                        <div>
										                            <img src="<c:url value='/resources/images/No_Image.jpg' />" class="img-border" height="180" width="135" title="Hit Candidate" />										                            
										                        </div>
										                    </c:otherwise>
										                </c:choose>								       					                       										                  								                    									               
										         </div>								        
										    </div>
									    </div>
									</div>
								</fieldset>
							</div>	
							<div class="col-md-4 col-sm-4">
								<fieldset>
									<legend>Thông tin đối tượng nghi trùng</legend>
									<div class="col-md-5">
										<div id="dialog-image_photoCompare" style="display: block;width: 135px;height: 180px;">
											<div class="centerCaption">       
										         <div style="text-align:center">							             									                 
													    <c:choose>
										                    <c:when test="${not empty photoTrung}">
										                        <div>
										                            <img src="data:image/jpg;base64,${photoTrung}"
										                                 class="img-border doGetAJpgSafeVersion" id="photoTarget" height="180" width="135" />
										
										                        </div>
										                    </c:when>
										                    <c:otherwise>
										                        <div>
										                            <img src="<c:url value='/resources/images/No_Image.jpg' />" id="photoTarget" class="img-border" height="180" width="135" title="Hit Candidate" />										                            
										                        </div>
										                    </c:otherwise>
										                </c:choose>								       					                       										                  								                    									               
										         </div>								        
										    </div>
									    </div>
									</div>
									<div class="col-md-7">
										<div>Họ và tên khác: <label id="id_nickname">${fakeEntity.nickName}</label></div>
										<div>Nơi sinh: <label id="id_noisinh">${fakeEntity.placeOfBirthId}</label></div>
										<div>Cấm xuất: <label id="id_lydocamxuat">${fakeEntity.regCause}</label></div>
									</div>
								</fieldset>
							</div>							
						</div>
						<div id="ajax-load-qa"></div>
						<div class="col-md-5">
							<fieldset>
								<legend>Ghi chú</legend>
								<div id="chiChu" style="height: 100px; overflow: auto;">
									<div>${noteAprovePerson}</div>
									<div>${noteAproveObj}</div>
									<div>${noteAproveNin}</div>
								</div>
							</fieldset>
							<fieldset>
								<legend>Thêm ghi chú</legend>
								<form:textarea rows="" cols="" style="width: 100%;height: 100%;height: 100px; overflow: auto;" path="listName"></form:textarea>
							</fieldset>
						</div>
						<div class="col-md-7">
							<fieldset>
								<legend>Danh sách đối tượng nghi trùng</legend>
								<div style="overflow: auto;height: 300px;">
								      	<table id="dsDen" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
										  <thead>
										    <tr>
										      <th class="th-sm">Họ tên
										
										      </th>											 
										      <th class="th-sm">Giới tính
										
										      </th>
										      <th class="th-sm">Ngày sinh
										
										      </th>
										      <th class="th-sm">CMND/CCCD
										
										      </th>										      
										      <th class="th-sm">Nơi sinh
										
										      </th>
										     
										      <th class="th-sm">Quốc tịch
										      </th>	
										      <th class="th-sm" style="display: none;"></th>							     
										    </tr>
										  </thead>										
										 	 <c:if test="${not empty listBlack}">	
											  	<tbody>
												   <c:forEach items="${listBlack}" var="itm_trung">
													    <tr>
													      	<td>${itm_trung.name}
													      		<form:checkbox path="processJobIds" id="chk_${itm_trung.id}" style="display:none;"  value="${itm_trung.id}"/>
													      		<form:checkbox path="saveFullName" id="chkName_${itm_trung.id}" style="display:none;"  value="${itm_trung.name}"/>
													      	</td>
													      	<td>${itm_trung.gender}</td>
													      	<td class="align-central">${itm_trung.dateOfBirth}</td>
													      	<td>${itm_trung.idNumber}</td>
													      	<td>${itm_trung.placeOfBirthId}</td>	
													      	<td>${itm_trung.currentNationalityId}</td>													      													      	  											      													  
													    	<td style="display: none;">${itm_trung.id}</td>
													    </tr>											   
												   </c:forEach>
												</tbody>								
											</c:if> 
										</table>
										<c:if test="${empty listBlack}">
										  	<div class="style-no-record opentb-1">Không tìm thấy kết quả</div>
										</c:if>
							      </div>
							</fieldset>
						</div>
					



						<%
							/* ******************************************************************************************************************** */
						%>
						<%
							/* ************************************* actions for selected jobs - start ******************************************** */
						%>
						<%
							/* ******************************************************************************************************************** */
						%>

					</div>
				</div>
			</div>
		</div>
	</div>
	<div style="display: flex;flex: 0 auto;justify-content: center;">
	<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px;border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
		<div style="margin: 10px"> 
			<button type="button"  onclick="showDocument();" name="approve" data-toggle="modal" data-target="#taiLieuDK" class="btn btn-success">
				<span class="glyphicon glyphicon-file"></span> Tài liệu đính kèm
			</button>
			<button type="button" id="btn_Trung"  onclick="saveEqual();" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-ok"></span> Xác định trùng
			</button>
			<button type="button"  onclick="noSaveEqual();" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-check"></span> Không trùng
			</button>
			<button type="button"  onclick="" data-toggle="modal" data-target="#messageLHS" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-stop"></span> Ngừng tra cứu
			</button>
			<button type="button"  onclick="" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-print"></span> In thông báo
			</button>
			<button type="button" onclick="printDecision();" name="approve" data-toggle="modal" data-target="#inToKhai" class="btn btn-success">
				<span class="glyphicon glyphicon-file"></span> Tờ khai hành chính
			</button>
		</div>
	</div>	
	</div>
	<!-- Model tài liệu -->
	<div class="modal fade" id="taiLieuDK" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
	  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 900px">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">TÀI LIỆU ĐÍNH KÈM</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
	        </button>
	      </div>							      
	      <div class="modal-body" style="height: 400px;overflow: auto;" id="idTaiLieu">
	       		
	       		
	      </div>
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove"></span> Đóng
	       		</button>
	       		
	      </div>
	    </div>
	  </div>
	</div>	
	<!-- -------------------------------------------------------------------------- -->
	<!-- Model tờ khai -->
	<div class="modal fade" id="inToKhai" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
	  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 900px">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">TỜ KHAI HỒ SƠ</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
	        </button>
	      </div>							      
	      <div class="modal-body" style="height: 400px;overflow: auto;" id="idToKhai">
	       		
	       		
	      </div>
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove"></span> Đóng
	       		</button>
	       		
	      </div>
	    </div>
	  </div>
	</div>	
	<!-- -------------------------------------------------------------------------- -->
	
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
	      			<p style="display: inline-block;position: absolute;margin-left: 10px;margin-top: 5px;">Bạn có ngừng tra cứu hồ sơ này?</p>
	      		</div>
	      </div>							      
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> KHÔNG</span>
	       		</button>
	       		<button type="button" onclick="stopCheck();" class="btn btn-success" data-dismiss="modal" aria-label="Close" style="float: right;margin-right: 10px;">
	       			<span class="glyphicon glyphicon-ok btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> CÓ</span>
	       		</button>
	       		
	      </div>
	    </div>
	  </div>
	</div>	
	<!-- ---------------------------------------------------------------------------->

	<c:url var="createHandover" value="/servlet/investigationConfirm/createHandover" />
<%
	/* ******************************************************************************************************************** */
%>
<%
	/* ************************************************ apply filter - start ********************************************** */
%>
<%
	/* ******************************************************************************************************************** */
%>

<script type="text/javascript">
	var ID_BLACK = 'null';
	var tbChiTiet = $('#dsDen').DataTable({
		"ordering": false,
		"pagingType": "simple"
	});
	
	
	function historyEventDataSmark_clicked(itemTriggered){	 
		//info = info.replace(/\n/g,"<br/>");
		$("#infoDialog").dialog('option', 'title', "Log Data");
		$("#infoDialog").text(itemTriggered);
		$("#infoDialog").dialog('open');
	}
	
	function stopCheck() {
		
		
		
		document.forms["formData"].action = '${nextProcessUrl}/${stage}';
		document.forms["formData"].submit();
	}
	
	
	/*tạm đóng chưa có dữ liệu đê thực hiện chức năng này*/
	function showDocument(){
		$('#idTaiLieu').html("");
		var url = '${getDocumentUrl}/${idMaster}';
		$('#ajax-load-qa').css('display', 'block');

		$.ajax({
			url : url,
			cache: false,
			type: "POST",
			success : function(data) {
				if(data != ''){
					$('#idTaiLieu').html(data);
				}else{
					$('#idTaiLieu').html("<div>Không có tài liệu.</div>");
				}
				$('#ajax-load-qa').css('display', 'none');				
			}
		});
	}
	
	function printDecision(){
		$('#idToKhai').html("");
		var url = '${showDocumentUrl}/${idMaster}';
		$('#ajax-load-qa').css('display', 'block');

		$.ajax({
			url : url,
			cache: false,
			type: "POST",
			success : function(data) {
				if(data != ''){
					$('#idToKhai').html(data);
				}else{
					$('#idToKhai').html("<div>Không có tờ khai.</div>");
				}			
				$('#ajax-load-qa').css('display', 'none');				
			}
		});
	}
	/*end*/

	$(function(){
		
		var loiKhopTT = '${loiKhop}';
		if(loiKhopTT != ''){
			$.notify(loiKhopTT, {
				globalPosition: 'bottom left',
				className: 'error',
			});
	    }
		
		/*---Trường hợp không có hồ sơ trùng thoát ra ngay---*/
		

		if('${action}' == 'Y'){
			if('${khongTrung}' == 'Y'){				
				noSaveEqual();
			}
		}else{
			//alert('1');
			stopCheck();
		}
		//----------------------------------------------------
		
		if('${showBody}' == 'N'){
			$('#dsDen tbody').css('display', 'none');
		}else{
			ID_BLACK = $("#dsDen tbody tr:nth-child(1) td:last").text();
		}
		
		if(ID_BLACK != ''){
			
			$("#dsDen tbody tr:nth-child(1)").addClass("back-gr");
			$("#dsDen tbody tr:first input[type='checkbox']").prop('checked', true);
		}
		//var tdTD = $("#tbChiTietDS tbody tr:nth-child(1) td:last").text();
		//alert(tdTD);
		
		//$('#img_' + tdTD).css('display', 'block');
		//$('#SHS_' + tdTD).css('display', 'block');
		 //var tbChiTiet = $('#dsNghiTrung').DataTable();
	     
		   $('#dsDen tbody').on('click', 'tr', function(){
		    	 var dataDSD = tbChiTiet.row(this).data();
		    	 if(ID_BLACK == dataDSD[6]){
		    		 return;
		    	 }
		    	 $('#ajax-load-qa').css('display', 'block');	
		    	 $('#dsDen > tbody > tr').removeClass("back-gr");
		    	 $("#dsDen tbody input[type='checkbox']").prop('checked', false);
		    	 $(this).addClass("back-gr");
		    	 ID_BLACK = dataDSD[6];
		    	 $("#chk_" + ID_BLACK).prop('checked', true);
		    	 $("#chkName_" + ID_BLACK).prop('checked', true);
		         var url = '${getInfoTransactionIdUrl}/' + ID_BLACK;
			     $.ajax({
						type : "POST",
						url : url,
						success: function(data) {
							$('#id_nickname').text(data.nickName);
							$('#id_noisinh').text(data.placeOfBirthId);
							$('#id_lydocamxuat').text(data.regCause);
							$('#ajax-load-qa').css('display', 'none');
							if(data.photo != '' && data.photo != null){
								$('#photoTarget').attr('src','data:image/jpg;base64,' + data.photo);
							}
							else
							{
								$('#photoTarget').attr('src','<c:url value="/resources/images/No_Image.jpg"/>');
							}
				        },
				        error: function(e){}
				 });
		    });
		
		
		
		$("#infoDialog").dialog( {
			autoOpen : false,
			width : 750,
			height : 120,
			modal : true,
			bgiframe : true,
			cache :false,
			closeOnEscape: false
		});
	});
	
	
	function saveEqual() {
		document.forms["formData"].action = '<c:url value="/servlet/investionProcess/saveEqualJobObject/Y" />';
		document.forms["formData"].submit();
	}
	
	function noSaveEqual(){
		document.forms["formData"].action = '<c:url value="/servlet/investionProcess/saveEqualJobObject/N" />';
		document.forms["formData"].submit();
	}
	
	function openBoxReject(){
		  $( "#dialog-reject-getRemarks" ).dialog( "open" ); 
	}
	
		$(function() {
		    $( "#dialog-reject-getRemarks" ).dialog({
				  modal: true,
			      autoOpen: false,
				  width : 600,
				  height: 350,
				  resizable: false,
			      show: {
			        effect: "fade",
			        duration: 1000
			      },
			      hide: { },
			      buttons: {
			         "Đồng ý": function() {
						var inp = $("#codeHandover").val();
						var inp1 = $("#nameHandover").val();
						var inp2 = $("#typeHandover").val();
						
						/* if ($.trim(inp).length == 0){
							alert ('Chưa nhập mã danh sách!');
							return;
						}  */  
						if ($.trim(inp1).length == 0){
							//alert ('Chưa nhập tên danh sách!');
							$.alert({
								title: 'Thông báo',
								content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Chưa nhập tên danh sách",
							});
							return;
						}    
			            $( this ).dialog( "close" );
			            requestApprove(inp, inp1, inp2);
			         },
			         "Hủy": function() {
			            $( this ).dialog( "close" );
			         }
			      } 
		    });
	  }); 

	function doApplyFilter() {
		document.forms["formData"].action = '<c:url value="/servlet/investigationConfirm/applyFilter" />';
		document.forms["formData"].submit();
	}
	
	function requestApprove(code, name, type) { 
		$.confirm({
		    title: 'Thông báo',
		    content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/tip_1a.png\">" + ' Có chăc chắn muốn thực hiện hành động này?',
		    buttons: {
		        "Có": function () {
		        	document.forms["formData"].currentlyAssignedToUserId.value =  code;
			      	document.forms["formData"].unassignAllForUserUserId.value  =  name;
			      	document.forms["formData"].assignToId.value  =  type;
			 	  	document.forms["formData"].action = '${createHandover}';  
				   	document.forms["formData"].submit(); 
		        },
		        "Không": function () {
		        	//return false;
		        }		       
		    }
		})
		/*if(confirm("Có chăc chắn muốn thực hiện hành động này?"))
		   {
				document.forms["formData"].currentlyAssignedToUserId.value =  code;
		      	document.forms["formData"].unassignAllForUserUserId.value  =  name;
		      	document.forms["formData"].assignToId.value  =  type;
		 	  	document.forms["formData"].action = '${createHandover}';  
			   	document.forms["formData"].submit();  

		   }
		  else
		    return false;*/
	}
	
	/*=====================================================================*/
	/*=========== XỬ LÝ THAY ĐỔI SỐ HỘ CHIẾU ==============================*/
	function openChangePassport(code, np){
		  document.getElementById('transLbl').innerHTML = code;
		  document.getElementById('nowPeriod').innerHTML = np;
		  $("#inputPeriod").val(np);
		  $( "#dialog-changePeriodValidate" ).dialog( "open" ); 
	}
	
	$(function() {
		
		
	
		$('#chk1').click(function(){
			var ghiChu = '';
			var item = document.getElementsByName("ghiChuChk");
        	for(var i = 0; i < item.length; i++){
        		if(item[i].checked){
        			ghiChu += item[i].value + " ";
        		}
        	}
        	$("textarea#ghiChuDeXuat").val(ghiChu);
		});
		
		$('#chk2').click(function(){
			var ghiChu = '';
			var item = document.getElementsByName("ghiChuChk");
        	for(var i = 0; i < item.length; i++){
        		if(item[i].checked){
        			ghiChu += item[i].value + " ";
        		}
        	}
        	$("textarea#ghiChuDeXuat").val(ghiChu);
		});
		
		$('#luuGhiChu').click(function(){
			var importGhiChu = $("textarea#ghiChuDeXuat").val();
			var item = document.getElementsByName("noDungDeXuat");
        	for(var i = 0; i < item.length; i++){
        		item[i].value = importGhiChu;
        	}
		});
		
		$('div#divKhuVuc input[type="checkbox"]').click(function(){
			var chkKhuVuc = document.getElementsByName("ricKhuVuc");
			var strKhuVuc = '';
            for(var j = 0; j < chkKhuVuc.length; j++){
            	if(chkKhuVuc[j].checked){
            		strKhuVuc += $('label#' + chkKhuVuc[j].value).text() + ",";
            	}
            }
        	$('textarea#strKhuVuc').val(strKhuVuc.substring(0, strKhuVuc.length - 1));
		});
	  }); 
	/*=====================================================================*/
	/*=========== END -- XỬ LÝ THAY ĐỔI SỐ HỘ CHIẾU ==============================*/
	
	
	/*$("#createDate").datepicker({
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
	
	$('#createDate').datepicker().datepicker('setDate', "");*/
</script>
	
	<form:input    path="currentlyAssignedToUserId"                       type="hidden" name="currentlyAssignedToUserId"                       value="" />
	<form:input    path="unassignAllForUserUserId"                        type="hidden" name="unassignAllForUserUserId"                        value="" />
	<form:input    path="assignToId"                        type="hidden" name="assignToId"                        value="" />
</form:form>




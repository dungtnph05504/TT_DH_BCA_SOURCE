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
<c:url var="getFacialByIdUrl" value="/servlet/investionProcess/getFacialById" />
<c:url var="getDocumentUrl" value="/servlet/investionProcess/getDocument" />
<c:url var="showDocumentUrl" value="/servlet/investionProcess/showDocument" />
<c:url var="getFacialSubByIdUrl" value="/servlet/investionProcess/getFacialSubById" />
<style>
.cls-mg-bot {
	margin-top: 10px;
}
.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
    white-space: pre-wrap;
}
.form-left {
	
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
<div class="content-item-title">
            <div class="oplog-title__txt">Xử lý hồ sơ hộ chiếu</div>
        </div>
<form:form modelAttribute="formData" name="formData">
	<form:hidden path="stageLoad" value="${stage}"/>

				<div class="row" style="margin: :0">
					<div class="content-item-information">

					
						
						<div class="col-sm-10 no-padding-right" style=" width: 80%;">
							<div class="col-md-12 col-sm-12" style="padding: 0px;">
								<fieldset  class="scheduler-border">
									<legend  class="scheduler-border">Danh sách hồ sơ</legend>
									<div style="overflow: auto;height: 350px;overflow-y: scroll;overflow-x: hidden;">
								      	<table id="tbChiTietDS" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
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
										      <th class="th-sm">Ngày khớp CN
										
										      </th>
										      <th class="th-sm">Ngày tra ĐT
										      </th>
										      <th class="th-sm">Ngày tra CMND
										      </th>	
										      <th style="display: none;"></th>									     
										    </tr>
										  </thead>
										  <!--<c:if test="${not empty chiTietDS}">-->
											  <tbody>
											    <c:forEach items="${chiTietDS}" var="item">
												    <tr id="bgr_${item.transactionId}">
												      	<td>${item.fullName}
												      		<form:checkbox path="processJobIds" id="chk_${item.transactionId}" style="display:none;"  value="${item.uploadJobId}"/>
												      		<form:hidden path="selectedJobIds" value="${item.uploadJobId}"/>
												      	</td>
												      	<td>${item.gender}</td>
												      	<td class="align-central">${item.dob}</td>
												      	<td>${item.nin}</td>
												      	<td>${item.placeOfBirth}</td>
												      	<td class="align-central">${item.dateApprovePerson}</td>													      	
												      	<td class="align-central">${item.dateApproveInvestigation}</td>
												      	<td class="align-central">${item.dateApproveNin}</td>	
												      	<td style="display: none;">${item.transactionId}</td>											      													  
												    </tr>
											    </c:forEach>
											  </tbody>
										 <!-- </c:if>-->
										</table>
										<c:if test="${empty chiTietDS}">
										  	<div class="style-no-record opentb-1">Không tìm thấy kết quả</div>
										</c:if>
							      </div>
								</fieldset>
								<fieldset  class="scheduler-border">
									<legend  class="scheduler-border">Trẻ em đi kèm</legend>
									<div class="col-md-10 col-sm-10">
										<div style="max-height: 200px;overflow-y: scroll;overflow-x: hidden; ">
											<table id="tbTreEm" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
											  <thead>
											    <tr>
											      <th class="th-sm">Họ tên
											
											      </th>											 
											      <th class="th-sm">Giới tính
											
											      </th>
											      <th class="th-sm">Ngày sinh
											
											      </th>										     
											      <th class="th-sm">Nơi sinh
											
											      </th>		
											      <th class="th-sm" style="display: none;">
											      
											      </th>								     									     
											    </tr>
											  </thead>
											  <!--<c:if test="${not empty chiTietDS}">-->
												  <tbody>
												    <c:forEach items="${dsTreEm}" var="item">
													    <tr>												      	
													      	<td>${item.name}</td>
													      	<td>${item.gender}</td>
													      	<td align="center">${item.dateOfBirth}</td>
													      	<td>${item.placeOfBirthId}</td>
													    	<td style="display: none;">${item.id}</td>												     
													    </tr>
												    </c:forEach>
												  </tbody>
											 <!-- </c:if>-->
											</table>
											<c:if test="${empty dsTreEm}">
											  	<div class="style-no-record opentb-1">Không tìm thấy kết quả</div>
											</c:if>	
										</div>
									</div>
									<div class="col-md-2 col-sm-2">
										<div id="dialog-image_photoCompare" style="display: block;width: 135px;height: 180px;">
											<div class="centerCaption">       
										         <div style="text-align:center">							             									                 
													    <c:choose>
										                    <c:when test="${not empty photoTreEm}">
										                        <div id="anhTreEm">
										                            <img src="data:image/jpg;base64,${photoTreEm}"
										                                 class="img-border doGetAJpgSafeVersion" height="180" width="135" />
										
										                        </div>
										                    </c:when>
										                    <c:otherwise>
										                        <div id="anhTreEm">
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
						</div>
						<div id="ajax-load-qa"></div>
						<!--<div class="form-central">
							<a href="#" id="no-search" class="no-search">
								<span class="glyphicon glyphicon-forward"></span>
							</a>
							<a href="#" id="ys-search" class="ys-search">
								<span class="glyphicon glyphicon-backward"></span>
							</a>
						</div>-->
						<div class="col-sm-2 none-pad-left" style=" width: 20%;">
							<div class="col-md-12 col-sm-12">
								<div class="col-md-12 col-sm-12">
								<div style="text-align: center;">
										<!--<div id="dsTransaction" style="text-align: center;">
										<c:forEach items="${chiTietDS}" var="itemTran">
										</c:forEach>
										</div>-->
										<div id="maGiaoDich" style="font-weight: bold;">Số hồ sơ: ${soHoSo}</div>
										<div id="dialog-image_photoCompare" style="height: 180px;margin-top: 10px;float: none;width: 100%;margin-bottom: 10px;">
											<div class="centerCaption">       
										         <div style="text-align:center">							             									                 
													    <c:choose>
										                    <c:when test="${not empty photoStr}">
										                        <div id="coAnhMat">
										                            <img src="data:image/jpg;base64,${photoStr}"
										                                 class="doGetAJpgSafeVersion" width="135" />
										
										                        </div>
										                    </c:when>
										                    <c:otherwise>
										                        <div id="coAnhMat">
										                      
										                            <img src="<c:url value='/images/No_Image.jpg' />" class="img-border" height="180" width="135" title="Hit Candidate" />
										                            <!--<c:forEach items="${chiTietDS}" var="itemImg">
										                           
										                            	 <c:if test="${itemImg.photoStr != ''}">
											                            	 <img id="img_${itemImg.transactionId}" style="display: none;" src="data:image/jpg;base64,${itemImg.photoStr}"
											                                 class="img-border doGetAJpgSafeVersion" height="180" width="135" />	
										                            	 </c:if>
										                            	 <c:if test="${itemImg.photoStr == ''}">
										                            	 	 <img id="img_${itemImg.transactionId}" style="display: none;" src="<c:url value='/resources/images/No_Image.jpg' />" class="img-border" height="180" width="135" title="Hit Candidate" />
										                            	 </c:if>										                            	 
										                            </c:forEach>-->
										                        </div>
										                    </c:otherwise>
										                </c:choose>									       					                       										                  								                    									               
										         </div>								        
										    </div>
									    </div>
									</div>
								</div>
								<div class="col-md-12 col-sm-12">
									<fieldset class="scheduler-border">
										<legend class="scheduler-border">Ghi chú</legend>
										<div style="padding: 10px;height: 350px;overflow: auto;">
										<div id="ghiChuP">${noteAprovePerson}</div>
										<div id="ghiChuO">${noteAproveObj}</div>
										<div id="ghiChuN">${noteAproveNin}</div>
										</div>
									</fieldset>
								</div>
							</div>
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
	      			<p style="display: inline-block;position: absolute;margin-left: 10px;margin-top: 5px;">Bạn có chắc chắn lưu hồ sơ không?</p>
	      		</div>
	      </div>							      
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> KHÔNG</span>
	       		</button>
	       		<button type="button" onclick="nextProcess();" class="btn btn-success" data-dismiss="modal" aria-label="Close" style="float: right;margin-right: 10px;">
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
			<button type="button"  onclick="showDocument();" name="approve" data-toggle="modal" data-target="#taiLieuDK" class="btn btn-success">
				<span class="glyphicon glyphicon-file"></span> Tài liệu đính kèm
			</button>
			<button type="button"  onclick="checkInfo();" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-ok"></span> Khớp cá nhân
			</button>
			<button type="button"  onclick="searchObject();" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-check"></span> Tra cứu ĐT
			</button>
			<button type="button"  onclick="searchCMND();" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-check"></span> Tra CMND
			</button>
			<button type="button" onclick="saveProcess();" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-save-file"></span> Lưu hồ sơ
			</button>
			<button type="button"  onclick="printDecision();" name="approve" data-toggle="modal" data-target="#inToKhai" class="btn btn-success">
				<span class="glyphicon glyphicon-print"></span> In tờ khai
			</button>
			<button type="button"  onclick="backAgain();" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-circle-arrow-left"></span> Quay lại
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
	      <div class="modal-body" style="height: 480px;overflow: auto;padding-left: 20px;" id="idTaiLieu">
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
	      <div class="modal-body" style="height: 480px;overflow: auto;" id="idToKhai"></div>							      
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove"></span> Đóng
	       		</button>
	       		
	      </div>
	    </div>
	  </div>
	</div>	
	<!-- -------------------------------------------------------------------------- -->
	
	<!--<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
			<div style="margin: 10px"> 
				<button type="button"  onclick="openBoxReject();" name="approve" class="btn btn-primary">
					  <span class="glyphicon glyphicon-list-alt"></span> Tạo danh sách
				</button>
				
			</div>
	</div>-->
	<c:url var="createHandover" value="/servlet/investigationBca/createHandover" />
	


	

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
	var DOC_ID = '';
	$('#tbChiTietDS').DataTable({
		"ordering": false,
		"pagingType": "simple"
	});
	$('#tbTreEm').DataTable({
		"ordering": false,
		"pagingType": "simple"
	});
	function historyEventDataSmark_clicked(itemTriggered){	 
		//info = info.replace(/\n/g,"<br/>");
		$("#infoDialog").dialog('option', 'title', "Log Data");
		$("#infoDialog").text(itemTriggered);
		$("#infoDialog").dialog('open');
	}

	$(function(){
		
		if('${slTreEm}' == 'Y'){
			$("#tbTreEm tbody tr:first").addClass("back-gr");
		}else{
			$("#tbTreEm tbody tr").hide();
		}
		
		$("#bgr_${soHoSo}").addClass("back-gr");
		$("#chk_${soHoSo}").prop('checked', true);
		DOC_ID = $("#tbChiTietDS tbody tr:first td:last").text();
		
		 var tbChiTiet = $('#tbChiTietDS').DataTable();
	     
		   $('#tbChiTietDS tbody').on('click', 'tr', function(){
		    	 var dataCTHS = tbChiTiet.row(this).data();
		    	 if(DOC_ID == dataCTHS[8]){
			        	return;
			     }	
		    	 $('#ajax-load-qa').css('display', 'block');	
		    	 $('#tbChiTietDS > tbody > tr').removeClass("back-gr");
		    	 $(this).addClass("back-gr");
		    	 $("#tbChiTietDS tbody input[type='checkbox']").prop('checked', false);
		    	 $("#chk_" + dataCTHS[8]).prop('checked', true);
		    	 DOC_ID = dataCTHS[8];
		         var url = '${getFacialByIdUrl}/' + dataCTHS[8];
			     $.ajax({
						type : "POST",
						url : url,
						success: function(data) {
							$('#ajax-load-qa').css('display', 'none');
							$("#coAnhMat").html(data.photoStr);
							$('#anhTreEm').html(data.photoTreEm);	
							//$("#khongCoAnh").html(data.photoStr);
							$('#maGiaoDich').text('Số hồ sơ: ' + data.transactionId);
							$('#ghiChuP').text(data.noteApprovePerson);
							$('#ghiChuO').text(data.noteApproveObj);
							$('#ghiChuN').text(data.noteApproveNin);
							var tbTreEm = $('#tbTreEm').DataTable();	
							tbTreEm.clear();
							if(data.listInfo.length > 0){
								for(var i = 0; i < data.listInfo.length; i++){
									tbTreEm.row.add([
				        		             data.listInfo[i].name,
				        		             data.listInfo[i].gender,
				        		             data.listInfo[i].dateOfBirth,	
				        		             data.listInfo[i].placeOfBirthId,
				        		             data.listInfo[i].id
					        		]).draw(false);
									$('#tbTreEm tr:eq('+(i+1)+') td:last').hide();
									
					        	}
								$('#tbTreEm tbody tr').show();
								$("#tbTreEm tbody tr:first").addClass("back-gr");
							}else{
								tbTreEm.clear().draw();
								$('#tbTreEm tbody tr').hide();
							}
				        },
				        error: function(e){}
				 });
		    });
		
		   var tbTreEm = $('#tbTreEm').DataTable();
		   var PERSON_ID = '';
		   $('#tbTreEm tbody').on('click', 'tr', function(){
		    	 var dataTBTE = tbTreEm.row(this).data();		    	
		    	 if(PERSON_ID == dataTBTE[4]){
			        	return;
			     }		    	
		    	 $('#tbTreEm > tbody > tr').removeClass("back-gr");
		    	 $(this).addClass("back-gr");		    	 
		    	 PERSON_ID = dataTBTE[4];
		         var url = '${getFacialSubByIdUrl}';
			     $.ajax({
						type : "POST",
						url : url,
						data: {
							transId: DOC_ID,
							personId: PERSON_ID
						},
						success: function(data) {
							$('#anhTreEm').html(data);	
				        },
				        error: function(e){}
				 });
		    });
		   
		$("#createDate").datepicker({
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
	
	function saveProcess() {
		var rowCount = $('#tbChiTietDS tr').length;
		var check = [];
		var boolPro = false;
		for(var i = 1; i < rowCount; i++){
			var data5 = document.getElementById("tbChiTietDS").rows[i].cells[5].innerHTML;
			var data6 = document.getElementById("tbChiTietDS").rows[i].cells[6].innerHTML;
			var data7 = document.getElementById("tbChiTietDS").rows[i].cells[7].innerHTML;
			if(data5 == '' || data6 == '' || data7 == ''){
				check.push(1); 
			}else{
				check.push(0); 
			}
			
			/*if(data5 == ''){
				$.notify('Hồ sơ chưa đủ ngày tra cứu!', {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return;
			}else if(data6 == ''){
				$.notify('Hồ sơ chưa đủ ngày tra cứu!', {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return;
			}else if(data7 == ''){
				$.notify('Hồ sơ chưa đủ ngày tra cứu!', {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return;
			}*/
			
		}
		for(var i = 0; i < check.length; i++){
			if(check[i] == 0){
				boolPro = true;
				break;
			}
		}
		if(boolPro){
			$("#messageLHS").modal();			
		}else{
			$.notify('Hồ sơ chưa đủ ngày tra cứu!', {
				globalPosition: 'bottom left',
				className: 'warn',
			});
			return;
		}
		//$("#messageLHS").modal();
	}
	
	function nextProcess(){
		
		document.forms["formData"].action = '<c:url value="/servlet/investionProcess/saveProcess" />';
		document.forms["formData"].submit();
	}
	
	function backAgain(){
		document.forms["formData"].action = '<c:url value="/servlet/investionProcess/invesProcessAgain" />';
		document.forms["formData"].submit();
	}
	
	function checkInfo(){
		document.forms["formData"].action = '<c:url value="/servlet/investionProcess/checkInfoByJob" />';
		document.forms["formData"].submit();
	}
	
	function searchObject(){
		document.forms["formData"].action = '<c:url value="/servlet/investionProcess/searchObjectJob" />';
		document.forms["formData"].submit();
	}
	
	function searchCMND(){
		document.forms["formData"].action = '<c:url value="/servlet/investionProcess/searchNinJob" />';
		document.forms["formData"].submit();
	}
	
	function showDocument(){
		$('#idTaiLieu').html("");
		var url = '${getDocumentUrl}/' + DOC_ID;
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
		var url = '${showDocumentUrl}/' + DOC_ID;
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

	function doSubmitSave() {
		
		$('#ajax-load-qa').css('display', 'block');
		var packageNumber = $('#packageNumber').val();
		var createDate = $('#createDate').val();
		var endDate = $('#endDate').val();
		var styleList = $('#styleList').find(":selected").val();
		$.ajax({
			url : '${searchInvesBUrl}',
			cache: false,
			type: "POST",
			data:{
				packageNumber: packageNumber,
				createDate: createDate,
				endDate: endDate,
				styleList: styleList,
			},
			success : function(data) {
				$('#ajax-load-qa').css('display', 'none');
				var tb = $('#tbDanhSachHS').DataTable();	
				tb.clear();
				if(data.length > 0){
					$('.opentb-2').css('display', 'none');
		        	for(var i = 0; i < data.length; i++){
		        		tb.row.add([
		        		             data[i].packageId,
		        		             data[i].packageDate,
		        		             data[i].ricName	        		            
		        		]).draw(false);
		        	}					
				}else{					
					$('.opentb-2').css('display', 'block');
				}
			}
		});
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
	
	function showProofDoc(transId, docView, docName, dataType, docDesc) {
		
		var url = '${showDocumentUrl}/' + transId + "/" + docDesc + "/" + docView;
		$.ajax({
			url : url,
			cache: false,
			type: "POST",
			success : function(data) {
				$('#idTaiLieuChiTiet').html(data);
			}
		});
		
		/*if ((dataType == 'JPEG')) {

			window.open('data:image/jpg;base64,' + docView,'Image','width=largeImage.stylewidth,height=largeImage.style.height,resizable=1');
			
		} else {
			var url = "${showPDFProofDoc}" + "/" + transId + "/" + docName;
			window.open(url);
		}*/
	}
	function showImage(url) {
		   
		  window.open(url,'_blank');
		  return;  
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
		
		    $("#dialog-changePeriodValidate" ).dialog({
				  modal: true,
			      autoOpen: false,
				  width : 600,
				  height: 290,
				  resizable: false,
			      show: {
			        effect: "fade",
			        duration: 1000
			      },
			      hide: { },
			      buttons: {
			         "Xác nhận": function() {
						var inp = $("#inputPeriod").val();
						var code = document.getElementById('transLbl').innerText;
						if ($.trim(inp).length == 0){
							//alert ('Không được để trống năm hộ chiếu');
							$.alert({
								title: 'Thông báo',
								content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Không được để trống năm hộ chiếu",
							});
							return;
						}   
						if (isNaN(inp)) 
						{
						    //alert("Giá trị nhập vào phải là số");
						    $.alert({
								title: 'Thông báo',
								content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Giá trị nhập vào phải là số",
							});
						    return ;
						}
						
			            $( this ).dialog( "close" );
			            var contents = {
								   passportNo:  "",
								   passportYear: inp,
								   transactionId: code
						}
			            $.ajax({
					 	      type: "POST",
					 	      url: "${passportUrl}", 
					 	      data: contents,
					 	      dataType: "json",
					 	      contentType: "application/json; charset=utf-8",
					 	      success: function(response) {
					 	    	 		//alert('Đã cập nhật thành công!');
					 	    	 		$.alert({
											title: 'Thông báo',
											content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/success_1a.png\">" + " Đã cập nhật thành công!",
										});
				                    	window.location.reload(true);
				                },
				                error: function(e) {
				                    //alert('Error: ' + e);
				                	$.alert({
										title: 'Thông báo',
										content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/success_1a.png\">" + " Đã có lỗi xảy ra: " + e,
									});
				                } 
					 	});
			         },
			         "Đóng": function() {
			            $( this ).dialog( "close" );
			         }
			      } 
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




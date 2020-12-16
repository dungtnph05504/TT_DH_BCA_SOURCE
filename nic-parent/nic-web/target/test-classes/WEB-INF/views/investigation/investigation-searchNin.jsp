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
<c:url var="nextProcessUrl" value="/servlet/investionProcess/invesProcess" />
<c:url var="historyNinUrl" value="/servlet/investionProcess/showHistoryNin" />
<style>
.cls-mg-bot {
	margin-top: 10px;
}

.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
    white-space: pre-wrap;
}

.clss-font {
	font-size: 12px !important;
}
.top-mag-10 {
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
div#form_tt1 input[type='text'] {
	width: 100%;
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

						<div class="new-heading-2">TRA CỨU THÔNG TIN CMND/CCCD TẠI C53
							<div style="color: red;float: right;margin-right: 40px;font-size: 20px;">${stt} <span style="font-size: 20px;">/</span>${count}</div>
						</div>
						<div style="clear: both"></div>

						<div style="min-height: 10px"></div>
						<div>
						<div class="col-sm-10 pad-top-20" id="form_tt1">
                                <fieldset class="scheduler-border">
                                    <legend class="scheduler-border"><label class="bold-label">Thông tin tra cứu</label></legend>
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <div class="col-sm-4">
                                                <div class="epp-form-group">
                                                    <div class="control-label">Họ tên</div>
                                                    <input type="text" placeholder="" value="${rostEntity.fullName_O}" readonly="">
                                                </div>
                                            </div>
                                            <div class="col-sm-2">
                                                <div class="epp-form-group">
                                                    <div class="control-label">Giới tính</div>
                                                    <input type="text" placeholder="" value="${rostEntity.gender_O}" readonly="">
                                                </div>
                                            </div>
                                            <div class="col-sm-2">
                                                <div class="epp-form-group">
                                                    <div class="control-label">Ngày sinh</div>
                                                    <input type="text" placeholder="" value="${rostEntity.dob_O}" readonly="">
                                                </div>
                                            </div>
                                            <div class="col-sm-2">
                                                <div class="epp-form-group">
                                                    <div class="control-label">Số CMND/CCCD</div>
                                                    <input type="text" placeholder="" value="${rostEntity.nin_O}" readonly="">
                                                </div>
                                            </div>
                                            <div class="col-sm-2">
                                                <div class="epp-form-group">
                                                    <div class="control-label">Ngày cấp</div>
                                                    <input type="text" placeholder="" value="${rostEntity.dateNin_O}" readonly="">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-4">
                                                <div class="epp-form-group">
                                                    <div class="control-label">Họ tên cha</div>
                                                    <input type="text"  placeholder="" value="${rostEntity.fullNameFather_O}" readonly="">
                                                </div>
                                            </div>
                                            <div class="col-sm-4">
                                                <div class="epp-form-group">
                                                    <div class="control-label">Họ tên mẹ</div>
                                                    <input type="text"  placeholder="" value="${rostEntity.fullNameMother_O}" readonly="">
                                                </div>
                                            </div>
                                            <div class="col-sm-4">
                                                <div class="epp-form-group">
                                                    <div class="control-label">Họ tên vợ chồng</div>
                                                    <input type="text"  placeholder="" value="${rostEntity.fullNameSpouser_O}" readonly="">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                            </div>	
                            <div class="col-sm-2 pad-top-10">                                
                                <div class="col-sm-12 text-center">
                                    <div id="dialog-image_photoCompare" style="display: block;width: 135px;height: 180px;">
											<div class="centerCaption">       
										         <div style="text-align:center">							             									                 
													    <c:choose>
										                    <c:when test="${not empty rostEntity.image_O}">
										                        <div>
										                            <img src="data:image/jpg;base64,${rostEntity.image_O}"
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
                            </div>
                            <div class="col-md-12 col-sm-12" id="form_tt2">
                            	<fieldset class="scheduler-border">
	                            <legend class="scheduler-border"><label class="bold-label">Thông tin CMND/CCCD C53</label></legend>
	                            <label class="bold-label ng-binding" style="padding-left: 10px;">Số bản ghi: ${countTransaction}</label>
	                            <div class="col-md-12 pad-80">
	                                <div class="form-group">
	                                    <div class="col-sm-4">
	                                        <div class="epp-form-group">
	                                            <div class="control-label">Họ tên</div>
	                                            <input type="text" placeholder="" value="${entity_o.name}" readonly="">
	                                        </div>
	                                    </div>
	                                    <div class="col-sm-2">
	                                        <div class="epp-form-group">
	                                            <div class="control-label">Giới tính</div>
	                                            <input type="text" placeholder="" value="${entity_o.gender}" readonly="" value="">
	                                        </div>
	                                    </div>
	                                    <div class="col-sm-2">
	                                        <div class="epp-form-group">
	                                            <div class="control-label">Ngày sinh</div>
	                                            <input type="text" placeholder="" value="${entity_o.dateOfBirth}" readonly="">
	                                        </div>
	                                    </div>
	                                    <div class="col-sm-2">
	                                        <div class="epp-form-group">
	                                            <div class="control-label">Số CMND/CCCD</div>
	                                            <input type="text" placeholder="" value="" readonly="">
	                                        </div>
	                                    </div>
	                                    <div class="col-sm-2">
	                                        <div class="epp-form-group">
	                                            <div class="control-label">Ngày cấp</div>
	                                            <input type="text" placeholder="" value="${entity_o.dateOfIssue}" readonly="">
	                                        </div>
	                                    </div>
	                                </div>
	                                <div class="form-group">
	                                    <div class="col-sm-4">
	                                        <div class="epp-form-group">
	                                            <div class="control-label">Họ tên cha</div>
	                                            <input type="text" placeholder="" value="${entity_o.fatherName}" readonly="">
	                                        </div>
	                                    </div>
	                                    <div class="col-sm-4">
	                                        <div class="epp-form-group">
	                                            <div class="control-label">Họ tên mẹ</div>
	                                            <input type="text"  placeholder="" value="${entity_o.motherName}" readonly="">
	                                        </div>
	                                    </div>
	                                    <div class="col-sm-4">
	                                        <div class="epp-form-group">
	                                            <div class="control-label">Họ tên vợ chồng</div>
	                                            <input type="text" placeholder="" value="${entity_o.spouseName}" readonly="">
	                                        </div>
	                                    </div>
	                                </div>
	                                <div class="form-group">
	                                    <div class="col-sm-4">
	                                        <div class="epp-form-group">
	                                            <div class="control-label">Tên khác1</div>
	                                            <input type="text"  placeholder="" readonly="">
	                                        </div>
	                                    </div>
	                                    <div class="col-sm-4">
	                                        <div class="epp-form-group">
	                                            <div class="control-label">Tên khác2</div>
	                                            <input type="text" placeholder="" readonly="">
	                                        </div>
	                                    </div>
	                                    <div class="col-sm-4">
	                                        <div class="epp-form-group">
	                                            <div class="control-label">Tên khác3</div>
	                                            <input type="text"  placeholder="" readonly="">
	                                        </div>
	                                    </div>
	                                </div>
	                                <div class="form-group">
	                                    <div class="col-sm-4">
	                                        <div class="epp-form-group">
	                                            <div class="control-label">Ngày cấp1</div>
	                                            <input type="text"  placeholder="" readonly="">
	                                        </div>
	                                    </div>
	                                    <div class="col-sm-4">
	                                        <div class="epp-form-group">
	                                            <div class="control-label">Ngày cấp2</div>
	                                            <input type="text" placeholder="" readonly="">
	                                        </div>
	                                    </div>
	                                </div>
	                            </div>	                     
		                        <div class="col-md-12 col-sm-12">
		                        	<div class="col-md-1 col-sm-1"></div>
		                        	<div class="col-md-5 col-sm-5">
		                        		<label style="font-weight: bold;">Ảnh mặt trước</label>
		                        		<div class="col-md-12" style="border: 1px solid;">
		                        			<div class="col-md-12" style="text-align: center;padding: 10px 0px 10px 0px;">
		                        				<c:choose>
								                    <c:when test="${not empty imgNinBefore}">
								                        <div>
								                            <img src="data:image/jpg;base64,${imgNinBefore}"
								                                 class="img-border doGetAJpgSafeVersion" width="300" />
								
								                        </div>
								                    </c:when>
								                    <c:otherwise>
								                        <div>
								                            <img src="<c:url value='/resources/images/noDocument.png' />" class="img-border" width="300" title="Hit Candidate" />										                            
								                        </div>
								                    </c:otherwise>
								                </c:choose>		
		                        			</div>
		                        		</div>
		                        	</div>
		                        	<div class="col-md-5 col-sm-5">
		                        		<label style="font-weight: bold;">Ảnh mặt sau</label>
		                        		<div class="col-md-12" style="border: 1px solid;">
		                        			<div class="col-md-12" style="text-align: center;padding: 10px 0px 10px 0px;">
		                        				<c:choose>
								                    <c:when test="${not empty imgNinAfter}">
								                        <div>
								                            <img src="data:image/jpg;base64,${imgNinAfter}"
								                                 class="img-border doGetAJpgSafeVersion" width="300" />
								
								                        </div>
								                    </c:when>
								                    <c:otherwise>
								                        <div>
								                            <img src="<c:url value='/resources/images/noDocument.png' />" class="img-border" width="300" title="Hit Candidate" />										                            
								                        </div>
								                    </c:otherwise>
								                </c:choose>		
		                        			</div>
		                        		</div>
		                        		
		                        	</div>
		                        	<div class="col-md-1 col-sm-1"></div>
		                        </div>
	                        </fieldset>
                        </div>
                        <div class="col-md-12 col-sm-12">
                        	<div class="form-horizontal">
		                        <div class="col-sm-6 none-pad-left">
		                            <fieldset class="scheduler-border">
		                                <legend class="scheduler-border">Ghi chú</legend>
		                                <div class="col-sm-12" style="height:100px; overflow:auto">                                
		                                	<div>${noteAprovePerson}</div>
											<div>${noteAproveObj}</div>
											<div>${noteAproveNin}</div>
											
		                                </div>
		                            </fieldset>
		                        </div>
		                        <div class="col-sm-6 none-pad-right">
		                            <fieldset class="scheduler-border">
		                                <legend class="scheduler-border">Thêm ghi chú</legend>
		                                <div class="col-xs-12 pad-bottom-5">
		                                    <textarea class="form-control" rows="7" placeholder="nhập nội dung" id="GhiChu" name="GhiChu"></textarea>
		                                </div>
		                            </fieldset>
		                        </div>
		                    </div>
                        </div>
						<div id="ajax-load-qa"></div>
					



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
	<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
		<div style="margin: 10px"> 
			<button type="button"  onclick="showDocument();" name="approve" data-toggle="modal" data-target="#taiLieuDK" class="btn btn-success">
				<span class="glyphicon glyphicon-file"></span> Tài liệu đính kèm
			</button>
			<button type="button"  onclick="checkInfo();" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-ok"></span> Tra tiếp
			</button>
			<button type="button"  onclick="showHistoryNin();" data-toggle="modal" data-target="#inLichSuCMND" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-print"></span> In lịch sử CMND
			</button>
			<button type="button" onclick="printDecision();" name="approve" data-toggle="modal" data-target="#inToKhai" class="btn btn-success">
				<span class="glyphicon glyphicon-print"></span> In tờ khai
			</button>
			<button type="button" onclick="stopCheck();" name="approve" class="btn btn-success">
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
	
	<!-- Model tờ khai -->
	<div class="modal fade" id="inLichSuCMND" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
	  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 900px">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">LỊCH SỬ CHỨNG MINH NHÂN DÂN</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
	        </button>
	      </div>							      
	      <div class="modal-body" style="height: 200px;overflow: auto;" id="idCMND">
	       		
	       		
	      </div>
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove"></span> Đóng
	       		</button>
	       		<button type="button" onclick="inLichSu();" id="idLichSu" class="btn btn-success" style="float: right;margin-right: 10px;">
	       			<span class="glyphicon glyphicon-print"></span> In 
	       		</button>
	      </div>	
	    </div>
	  </div>
	</div>	
	<!-- -------------------------------------------------------------------------- -->

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
	
	

	$(function(){
		
		$("#tbChiTietDS tbody tr:nth-child(1)").css("background-color", "#7fae46");
		$("#chk_${soHoSo}").prop('checked', true);
		
		 var tbChiTiet = $('#tbChiTietDS').DataTable();
	     
		   $('#tbChiTietDS tbody').on('click', 'tr', function(){
		    	 $('#ajax-load-qa').css('display', 'block');	
		    	 var dataCTHS = tbChiTiet.row(this).data();
		    	 $('#tbChiTietDS > tbody > tr').css('background-color', '#fff');
		    	 $(this).css('background-color', '#7fae46');
		    	 $("#tbChiTietDS tbody input[type='checkbox']").prop('checked', false);
		    	 $("#chk_" + dataCTHS[8]).prop('checked', true);
		         var url = '${getFacialByIdUrl}/' + dataCTHS[8];
			     $.ajax({
						type : "POST",
						url : url,
						success: function(data) {
							
							$('#ajax-load-qa').css('display', 'none');
							$("#coAnhMat").html(data.photoStr);
							$("#khongCoAnh").html(data.photoStr);
							$('#maGiaoDich').text('Số hồ sơ: ' + data.transactionId);
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
	
	function showHistoryNin(){
		$('#idCMND').html('');
		var urlLink = '${historyNinUrl}/${jobCompare}';
		$('#ajax-load-qa').css('display', 'block');
		$.ajax({
			type : "POST",
			url : urlLink,
			success: function(data) {
				$('#idCMND').html(data);
				$('#ajax-load-qa').css('display', 'none');
				if('${countTransaction}' == '0'){
					$.notify('Không có lịch sử chứng minh nhân dân!', {
						globalPosition: 'bottom left',
						className: 'warn',
					});
					$('#idLichSu').hide();
				}else{
					$('#idLichSu').show();
				}
		    },
		    error: function(e){}
		 });
	}
	
	
	function checkInfo() {
		//alert('${stage}');
		document.forms["formData"].action = '<c:url value="/servlet/investionProcess/nextCheckNin" />';
		document.forms["formData"].submit();
	}
	
	function stopCheck() {
		document.forms["formData"].action = '${nextProcessUrl}/${stage}';
		document.forms["formData"].submit();
	}
	
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

	
	
	/*=====================================================================*/
	/*=========== XỬ LÝ THAY ĐỔI SỐ HỘ CHIẾU ==============================*/
	function openChangePassport(code, np){
		  document.getElementById('transLbl').innerHTML = code;
		  document.getElementById('nowPeriod').innerHTML = np;
		  $("#inputPeriod").val(np);
		  $( "#dialog-changePeriodValidate" ).dialog( "open" ); 
	}
	
	$(function() {
		
		/*---Trường hợp không có hồ sơ trùng thoát ra ngay---*/
		
		
		if('${action}' == 'Y'){
			if('${khongTrung}' == 'Y'){
				//checkInfo();
			}
		}else{
			//alert('1');
			stopCheck();
		}
		
		
		//----------------------------------------------------
	
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
	})
		
		    
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

</form:form>




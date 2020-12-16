<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%request.setCharacterEncoding("UTF-8");%>
<c:url var="reprocessUrl" value="/servlet/workflowProcess/reprocess" />
<c:url var="reprocessNoUrl" value="/servlet/workflowProcess/reprocessNo" />
<c:url var="listWorkflowUrl" value="/servlet/workflowProcess/listWorkflow" />
<c:url var="updateVerifyUrl" value="/servlet/workflowProcess/updateVerify" />
<c:url var="listAfterWFNoUrl" value="/servlet/workflowProcess/listAfterwfNo" />
<c:url var="findEnWorkflowUrl" value="/servlet/workflowProcess/findEnWorkflow" />
<c:url var="changeWorkflowEndUrl" value="/servlet/workflowProcess/changeWorkflowEnd" />
<c:url var="changeWorkflowEndNoUrl" value="/servlet/workflowProcess/changeWorkflowEndNo" />
<c:url var="listUserUrl" value="/servlet/workflowProcess/listUserEnd" />
<c:url var="changeUserUrl" value="/servlet/workflowProcess/changeUserEnd" />
<c:url var="changePageUrl" value="/servlet/workflowProcess/main" />
<c:url var="resetAllUrl" value="/servlet/workflowProcess/resetAll" />
<style>
.stepwizard-step p {
  /*   margin-top: 0px;
    color:#337ab7; */
    font-style: italic;
}
.stepwizard-row {
    display: table-row;
}
.stepwizard {
    display: table;
    width: 100%;
    position: relative;
}
.stepwizard-step button[disabled] {
    /*opacity: 1 !important;
    filter: alpha(opacity=100) !important;*/
}
.stepwizard .btn.disabled, .stepwizard .btn[disabled], .stepwizard fieldset[disabled] .btn {
    opacity:1 !important;
    color:#bbb;
}

.stepwizard-step {
	padding: 0px;
}
.btn-circle {
    width: 135px;
    height: 70px;
    text-align: center;
    padding: 25px 0;
    font-size: 11px;
    line-height: 1.428571429;
    border-radius: 20px;
	background: #337ab7;
	color: #fff;
	text-transform: uppercase;
}

.btn-circle-unactive {
    width: 135px;
    height: 70px;
    text-align: center;
    padding: 25px 0;
    font-size: 11px;
    line-height: 1.428571429;
    border-radius: 20px;
	background: #e6e6e6;
	color: #000;
	text-transform: uppercase;
}

.glyphicon {
    font-size: 30px;
    color: green;
    padding-left: 10px;
}

.glyphicon-arrow-down {
    padding-left: 25px;
    padding-top: 5px;
	color: orange;
}

.col-xs-3 {
    width: 16.5%;
}


.singer{
	width: 50%;
	padding-left: 60px;
}
.btn-fix-ngang{
	width: 700px;
    height: 6px;
    padding: 0px;
    position: relative;
    margin: 80px 0px 5px;
    background: #337ab7;
}

.alll{
    width: 700px;
}
.left{
	float: left;
}

.right{
	float: right;
}

.fix-form-ra {
	background-color: white;
	width: 1200px;
	box-shadow: 0px 5px 10px #392f2f;
	padding: 10px;
	margin: 0 auto;
	margin-top: 30px;
}

.myTitleClass .ui-dialog-titlebar {
    background:green;
}
</style>
<div class="fix-form-ra">
	<div id="heading_report" align="justify" colspan="2" style="padding: 2px;float:left">Quản lý luồng xử lý</div>
	<div class="col-sm-3" style="float:right;margin-bottom:30px">	
		<label>Cán bộ hiện tại chỉ định xử lý: </label>
		<div class="input-group mb-2">
			<input type="search" class="form-control" style="text-align: center;color: green;font-weight: 700;" value="${assignName}" readonly>
			<span class="input-group-btn">
				<button class="btn btn-info" type="button" onclick="changeUserSpecial('${assignName}')">Thay đổi</button>
			</span>
		</div>
	</div>
	<c:if test="${empty jobList}">
		<div class="container" style="padding-top:20px; margin-right: -40px;">
			Không có đủ dữ liệu để hiển thị luồng xử lý
			<br />
		</div>
	</c:if>
	<c:if test="${not empty jobList}">
		<div class="container" style="padding-top:20px; margin-right: -40px;">
			<div class="stepwizard" style="padding-bottom: 5px;">
				<div class="stepwizard-row setup-panel">
					<div class="stepwizard-step col-xs-3"> 
						<a type="button" class="btn btn-success btn-circle">Đồng bộ</a>
						<a href="#" onclick="changeWflEnd('UPLOADED', 1)">
							<span class="glyphicon glyphicon-arrow-down singer" style="padding-top: 25px;padding-left: 50px; color:green"></span>
						</a>
						<!--<p><small>Shipper</small></p>-->
					</div>
				
					<div class="stepwizard-step col-xs-3"> 
						<!--<p><small>Destination</small></p>-->
					</div>
					<div class="stepwizard-step col-xs-3"> 
						<a href="#step-3" type="button" class="btn btn-default btn-fix-ngang"></a>
						<div class="alll">
							<div class="left">
								<a id="invesdown" href="#">
									<c:choose>
										<c:when test="${endinvesVer && !endpresentVer}">
											<span id="invesdown_span" class="glyphicon glyphicon-arrow-down" onclick="changeVerifyEnd('INVESTIGATION')"></span>
										</c:when>    
			    						<c:otherwise>
			    							<span id="invesdown_span" class="glyphicon glyphicon-arrow-down" style="visibility: hidden;"></span>
			    						</c:otherwise>
									</c:choose>
								</a>
								<a id="invesup" href="#">
									<c:choose>
										<c:when test="${invesVer}">
											<span id="invesup_span" class="glyphicon glyphicon-arrow-up" ></span>
										</c:when>    
			    						<c:otherwise>
			    							<span id="invesup_span" class="glyphicon glyphicon-arrow-up" style="visibility: hidden;"></span>
			    						</c:otherwise>
									</c:choose>
								</a>
							</div>
							<div class="left" style="padding-left: 95px;">
								<a id="presendown" href="#">
									<c:choose>
										<c:when test="${!endinvesVer && endpresentVer}">
											<span  id="presendown_span" class="glyphicon glyphicon-arrow-down" onclick="changeVerifyEnd('PRESENT_APPROVAL')"></span>
										</c:when>    
			    						<c:otherwise>
			    							<span id="presendown_span" class="glyphicon glyphicon-arrow-down" style="visibility: hidden;"></span>
			    						</c:otherwise>
									</c:choose>
								</a>
								<a id="presenup" href="#">
									<c:choose>
										<c:when test="${presentVer}">
											<span  id="presenup_span" class="glyphicon glyphicon-arrow-up"></span>
										</c:when>    
			    						<c:otherwise>
			    							<span id="glyphicon-arrow-up" class="glyphicon glyphicon-arrow-up" style="visibility: hidden;"></span>
			    						</c:otherwise>
									</c:choose>
								</a>
							</div>
							<div class="right">
								<a id="leaderdown" href="#">
									<c:choose>
										<c:when test="${endleaderVer}">
											<span  id="leaderdown_span" class="glyphicon glyphicon-arrow-down" style="padding: 0px 115px;" onclick="changeVerifyEnd('LEADERS_APPROVAL')"></span>
										</c:when>    
			    						<c:otherwise>
			    							<span  id="leaderdown_span" class="glyphicon glyphicon-arrow-down" style="visibility: hidden;padding: 0px 115px;"></span>
			    						</c:otherwise>
									</c:choose>
								</a>
								<a id="persodown" href="#">
									<c:choose>
										<c:when test="${endpersoVer}">
											<span  id="persodown_span" class="glyphicon glyphicon-arrow-down" style="padding: 0px 40px;padding-top: 5px;" onclick="changeVerifyEnd('PERSO')"></span>
										</c:when>    
			    						<c:otherwise>
			    							<span  id="persodown_span" class="glyphicon glyphicon-arrow-down" style="visibility: hidden;padding: 0px 40px;padding-top: 5px;"></span>
			    						</c:otherwise>
									</c:choose>
								</a>
							</div>
						</div>
						<!--<p><small>Schedule</small></p>-->
					</div>
					<div class="stepwizard-step col-xs-3" style="padding: 0px 95px;"> 
						<c:choose>
    						<c:when test="${verification.status == 1}">
								<a id="idVERIFICATION" type="button" class="btn btn-success btn-circle" onclick="reprocess_xm('VERIFICATION')">Xác minh</a>
							</c:when>    
    						<c:otherwise>
    							<a id="idVERIFICATION" type="button" class="btn btn-success btn-circle-unactive" onclick="reprocess_xm('VERIFICATION')">Xác minh</a>
						 	</c:otherwise>
						</c:choose>
						<!--<p><small>Cargo</small></p>-->
					</div>
				</div>
			</div>
			<div class="stepwizard" style="padding-top:10px">
				<div class="stepwizard-row setup-panel" >
					<div class="stepwizard-step col-xs-3"> 
						<c:choose>
    						<c:when test="${checkCPD.status == 1}">
								<a id ="idCHECK_CPD" type="button" class="btn btn-success btn-circle" onclick="reprocess('CHECK_CPD')">Kiểm tra CPD <p>(Có lỗi)</p></a>
							</c:when>    
    						<c:otherwise>
    							<a id="idCHECK_CPD" type="button" class="btn btn-success btn-circle-unactive" onclick="reprocess('CHECK_CPD')">Kiểm tra CPD <p>(Có lỗi)</p></a>
						 	</c:otherwise>
						</c:choose>
						<a href="#" onclick="changeWflEnd('CHECK_CPD', 2)">
							<span class="glyphicon glyphicon-arrow-right" style="color:red"></span>
						</a>
						<!--<p><small>Destination</small></p>-->
					</div>
					<div class="stepwizard-step col-xs-3"> 
						<c:choose>
    						<c:when test="${assignment_b}">
								<a id ="idASSIGNMENT" type="button" class="btn btn-success btn-circle" onclick="reprocess('ASSIGNMENT')">Phân công</a>
							</c:when>    
    						<c:otherwise>
    							<a id="idASSIGNMENT" type="button" class="btn btn-success btn-circle-unactive" onclick="reprocess('idASSIGNMENT')">Phân công</a>
						 	</c:otherwise>
						</c:choose>
						<a href="#" onclick="changeWflEnd('ASSIGNMENT', 3)">
							<span class="glyphicon glyphicon-arrow-right"></span>
						</a>
						<!--<p><small>Schedule</small></p>-->
					</div>
					<div class="stepwizard-step col-xs-3"> 
						<c:choose>
    						<c:when test="${investigation_b}">
								<a id ="idINVESTIGATION" type="button" class="btn btn-success btn-circle" onclick="reprocess('INVESTIGATION')">Xử lý</a>
							</c:when>    
    						<c:otherwise>
    							<a id ="idINVESTIGATION" type="button" class="btn btn-success btn-circle-unactive" onclick="reprocess('INVESTIGATION')">Xử lý</a>
						 	</c:otherwise>
						</c:choose>
						<a href="#" onclick="changeWflEnd('INVESTIGATION', 4)">
							<span class="glyphicon glyphicon-arrow-right"></span>
						</a>
						<!--<p><small>Cargo</small></p>-->
					</div>
					<div class="stepwizard-step col-xs-3"> 
						<c:choose>
    						<c:when test="${presentapproval_b}">
								<a id ="idPRESENT_APPROVAL" type="button" class="btn btn-success btn-circle" onclick="reprocess('PRESENT_APPROVAL')">Đề xuất</a>
							</c:when>    
    						<c:otherwise>
    							<a id ="idPRESENT_APPROVAL" type="button" class="btn btn-success btn-circle-unactive" onclick="reprocess('PRESENT_APPROVAL')">Đề xuất</a>
						 	</c:otherwise>
						</c:choose>
						<a href="#" onclick="changeWflEnd('PRESENT_APPROVAL', 5)">
							<span class="glyphicon glyphicon-arrow-right"></span>
						</a>
						<!--<p><small>Cargo</small></p>-->
					</div>
					<div class="stepwizard-step col-xs-3"> 
						<c:choose>
    						<c:when test="${leaderapproval_b}">
								<a id ="idLEADERS_APPROVAL" type="button" class="btn btn-success btn-circle" onclick="reprocess('LEADERS_APPROVAL')">Lãnh đạo duyệt</a>
							</c:when>    
    						<c:otherwise>
    							<a id ="idLEADERS_APPROVAL" type="button" class="btn btn-success btn-circle-unactive" onclick="reprocess('LEADERS_APPROVAL')">Lãnh đạo duyệt</a>
						 	</c:otherwise>
						</c:choose>
						<span class="glyphicon glyphicon-arrow-right"></span>
						<!--<p><small>Cargo</small></p>-->
					</div>
					<div class="stepwizard-step col-xs-3"> 
						<a type="button" class="btn btn-success btn-circle">Cá thể hóa</a>
						<!--<p><small>Cargo</small></p>-->
					</div>
				</div>
			</div>
			
			<%-- <div class="stepwizard" style="padding-top:10px">
				<div class="stepwizard-row setup-panel">
					<div class="stepwizard-step col-xs-3"> 
						<!--<p><small>Shipper</small></p>-->
					</div>
					<div class="stepwizard-step col-xs-3"> 
						<!--<p><small>Destination</small></p>-->
					</div>
					<div class="stepwizard-step col-xs-3"> 
						<!--<p><small>Schedule</small></p>-->
					</div>
					<div class="stepwizard-step col-xs-3"> 
						<c:choose>
    						<c:when test="${leaderapproval.status == 1}">
								<a id ="idLEADERS_APPROVAL" type="button" class="btn btn-success btn-circle" onclick="reprocess('LEADERS_APPROVAL')">Lãnh đạo duyệt</a>
							</c:when>    
    						<c:otherwise>
    							<a id ="idLEADERS_APPROVAL" type="button" class="btn btn-success btn-circle-unactive" onclick="reprocess('LEADERS_APPROVAL')">Lãnh đạo duyệt</a>
						 	</c:otherwise>
						</c:choose>
						<span class="glyphicon glyphicon-arrow-down singer"></span>
						<!--<p><small>Cargo</small></p>-->
					</div>
				</div>
			</div>	
			
			<div class="stepwizard" style="padding-top:10px">
				<div class="stepwizard-row setup-panel">
					<div class="stepwizard-step col-xs-3"> 
						<!--<p><small>Shipper</small></p>-->
					</div>
					<div class="stepwizard-step col-xs-3"> 
						<!--<p><small>Destination</small></p>-->
					</div>
					<div class="stepwizard-step col-xs-3"> 
						<!--<p><small>Schedule</small></p>-->
					</div>
					<!-- <div class="stepwizard-step col-xs-3"> 
						<a type="button" class="btn btn-success btn-circle">Cá thể hóa</a>
						<p><small>Cargo</small></p>
					</div> -->
				</div>
			</div>	 --%>
			<div style="float: left;padding: 30px 0px 0px;">
				<a id ="resetAll" type="button" class="btn btn-warning">
					Khởi tạo lại
					<span class="glyphicon glyphicon-refresh" style="font-size:inherit; color:#fff"></span>
				</a>
			</div>
			
			<div style="float: left;padding: 30px 0px 0px;">
				<span>Chú thích </span>
				<a type="button" class="btn btn-sm btn-primary">
					
				</a>
				Đang hoạt động
				<a type="button" class="btn btn-sm btn-primary" style="background: #e6e6e6; color: #000;">
					
				</a>
				Đang đóng
			</div>
			<div style="float: right;padding: 30px 40px 0px;">
				<form:form name="investigationMenuForm" id="investigationForm" method="post">
					<a id ="changePage" type="button" class="btn btn-success">
						Chuyển trang
						<span class="glyphicon glyphicon-share-alt" style="font-size:inherit; color:#fff"></span>
					</a>
				</form:form>
			</div>
		</div>
	</c:if>
</div>
<div id="dialog-approve-confirm" title="Thay đổi trạng thái" style="display: none;"> 
		<p>
					<span class="ui-icon ui-icon-alert"
							style="float: left; margin: 0 7px 20px 0;"> </span>  
							Có chắc chắn muốn thay đổi không? 
		</p>    
</div> 

<div id="dialog-approve-confirm-no" title="Thay đổi trạng thái" style="display: none;"> 
		<p>
					<span class="ui-icon ui-icon-alert"
							style="float: left; margin: 0 7px 20px 0;"> </span>  
							Có chắc chắn muốn thay đổi không? 
		</p>    
</div> 

<div id="dialog-reset-confirm" title="Khởi tạo lại toàn bộ tiến trình" style="display: none;"> 
		<p>
					<span class="ui-icon ui-icon-alert"
							style="float: left; margin: 0 7px 20px 0;"> </span>  
							Có chắc chắn muốn khởi tạo lại không? Dữ liệu trước đó sẽ không được lưu lại 
		</p>    
</div> 

<div id="dialog-verify-change" title="Thay đổi kết thúc của tiến trình Xác minh" style="display: none;"> 
		<p>
					<span class="ui-icon ui-icon-alert"
							style="float: left; margin: 0 7px 20px 0;"> </span>  
							Tiến trình Xác minh đang có kết thúc là <b style="color:red" id="idwfChange"></b>
					
		</p>    
		<p>Chọn tiến trình thay thế tiến trình kết thúc trên: </p>
		<select class="form-control" id="select_status" name="select_status">
		<option></option>
		</select>
</div> 

<div id="dialog-workflow-change-end" title="Thay đổi kết thúc của tiến trình" style="display: none;"> 
		<p>
					<span class="ui-icon ui-icon-alert"
							style="float: left; margin: 0 7px 20px 0;"> </span>  
							Tiến trình <b id="wflStart"></b> đang có kết thúc là <b style="color:red" id="idwfChangeEnd"></b>
					
		</p>    
		<p>Chọn tiến trình thay thế tiến trình kết thúc trên: </p>
		<select class="form-control" id="select_status_end" name="select_status_end">
		<option></option>
		</select>
</div> 

<div id="dialog-workflow-change-end-no" title="Thay đổi kết thúc của tiến trình" style="display: none;"> 
		<p>
					<span class="ui-icon ui-icon-alert"
							style="float: left; margin: 0 7px 20px 0;"> </span>  
							Tiến trình <b id="wflStart"></b> đang có kết thúc là <b style="color:red" id="idwfChangeEnd"></b>
					
		</p>    
		<p>Chọn tiến trình thay thế tiến trình kết thúc trên: </p>
		<select class="form-control" id="select_status_end" name="select_status_end">
		<option></option>
		</select>
</div> 

<div id="dialog-user-change" title="Đổi cán bộ xử lý mặc định" style="display: none;"> 
		<p>
			<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"> </span>  
				Cán bộ mặc định xử lý đang là: <b style="color:red" id="idUserSpecial"></b>
					
		</p>    
		<p>Chọn cán bộ thay thế: </p>
			<select class="form-control" id="select_user_special" name="select_user_special">
			<option></option>
		</select>
</div> 
<script>
	var idpr = '';
	var unactive_class = 'btn-circle-unactive';
	var idwfls = '';
	var keyS = '';
	var numS = 0;
	var isNo = false;
	
	function changeWflEnd(k, n){
		var nameS = "Đồng bộ";
		 switch(k) {
			 case "CHECK_CPD":
				 nameS = "Kiểm tra CPD";
			  	break;
			 case "ASSIGNMENT":
				 nameS = "Phân công";
			  	break;
			 case "INVESTIGATION":
				 nameS = "Xử lý";
			  	break;
			 case "PRESENT_APPROVAL":
				 nameS = "Đề xuất";
			  	break;
			 default:
		}
		$('#wflStart').html("");
		$('#wflStart').html("" + nameS); 
		$('#dialog-workflow-change-end-no').dialog('option', 'title', 'Thay đổi kết thúc của tiến trình ' + nameS);
		$.ajax({
			type : "GET",
			url : '${findEnWorkflowUrl}/' + k,
			dataType : 'text',
			success : function(data) {
				$('#idwfChangeEnd').html("");
				 var ndata = "Đồng bộ";
				 switch(data) {
				 	case "UPLOADED":
					 ndata = "Đồng bộ";
				  	break;
					 case "CHECK_CPD":
						 ndata = "Kiểm tra CPD";
					  	break;
					 case "ASSIGNMENT":
						 ndata = "Phân công";
					  	break;
					 case "INVESTIGATION":
						 ndata = "Xử lý";
					  	break;
					 case "PRESENT_APPROVAL":
						 ndata = "Đề xuất";
					  	break;
					 case "LEADERS_APPROVAL":
						 ndata = "Lãnh đạo duyệt";
					  	break;
					 case "PERSO":
						 ndata = "Cá thể hóa";
					  	break;
					 default:
				}
				$('#idwfChangeEnd').html(ndata);
				
				$.ajax({
					type : "GET",
					url : '${listAfterWFNoUrl}/' + n,
					dataType : 'json',
					success : function(json) {
						var $el = $("#select_status_end");
						$el.empty(); // remove old options
						/*  $el.append($("<option></option>")
						         .attr("value", '').text('Please Select')); */
						$el.append(json);
						idwfls = k + '-' + n;
						keyS = k;
						numS = n;
						$("#dialog-workflow-change-end-no").dialog("open");
					},
					error : function(e) {
						alert("Đã có lỗi xảy ra.After Error: " + e.responseText);
					}
				});
			},
			error : function(e) {
				alert("Đã có lỗi xảy ra.Find Error: " + e.responseText);
			}
		});
	}

	function reprocess_xm(k) {
		$("#dialog-approve-confirm-no").dialog("open");
		idpr = 'id' + k;
	}

	function reprocess(k) {
		$("#dialog-approve-confirm-no").dialog("open");
		idpr = 'id' + k;
	}

	$(function() {
		$("#dialog-approve-confirm-no").dialog({
			modal : true,
			autoOpen : false,
			width : 600,
			height : 200,
			resizable : false,
			show : {
				effect : "fade",
				duration : 1000
			},
			hide : {},
			buttons : {
				"Có" : function() {
					var res = idpr.replace("id", "");
					var name = "Lãnh đạo duyệt";
					switch (res) {
					case "INVESTIGATION":
						name = "Xử lý";
						break;
					case "PRESENT_APPROVAL":
						name = "Đề xuất";
						break;
					default:
						// code to be executed if n is different from case 1 and 2
					}
					$.ajax({
						type : "POST",
						url : "${reprocessNoUrl}",
						data : idpr,
						dataType : "text",
						success : function(data) {
							if (data == 1) {
								$('#idwfChange').html("");
								$('#idwfChange').html(name);
								$.ajax({
									type : "GET",
									url : '${listWorkflowUrl}/' + res,
									dataType : 'json',
									success : function(json) {
										var $el = $("#select_status");
										$el.empty();
										$el.append(json);
									}
								});
								isNo = true;
								$("#dialog-verify-change").dialog("open");
							} else if (data == 0) {
								alert('Cập nhật trạng thái thành công');
								idpr = '';
								location.reload();
							} else if (data == -1) {
								alert('Sai mã hoặc để trống mã');
							} else if (data == -2) {
								alert('Không tìm thấy mã');
							} else if (data == -99) {
								alert('Lỗi trong quá trình cập nhật');
							}
						},
						error : function(e) {
							alert("Đã có lỗi xảy ra");
						}
					});
					$(this).dialog("close");
				},
				"Đóng" : function() {
					$(this).dialog("close");
				}
			}
		});
		
		$("#dialog-approve-confirm").dialog({
			modal : true,
			autoOpen : false,
			width : 600,
			height : 200,
			resizable : false,
			show : {
				effect : "fade",
				duration : 1000
			},
			hide : {},
			buttons : {
				"Có" : function() {
					var res = idpr.replace("id", "");
					var name = "Lãnh đạo duyệt";
					switch (res) {
					case "INVESTIGATION":
						name = "Xử lý";
						break;
					case "PRESENT_APPROVAL":
						name = "Đề xuất";
						break;
					default:
						// code to be executed if n is different from case 1 and 2
					}
					$.ajax({
						type : "POST",
						url : "${reprocessUrl}",
						data : idpr,
						dataType : "text",
						success : function(data) {
							if (data == 1) {
								$('#idwfChange').html("");
								$('#idwfChange').html(name);
								$.ajax({
									type : "GET",
									url : '${listWorkflowUrl}/' + res,
									dataType : 'json',
									success : function(json) {
										var $el = $("#select_status");
										$el.empty(); // remove old options
										/*  $el.append($("<option></option>")
										         .attr("value", '').text('Please Select')); */
										$el.append(json);
									}
								});
								$("#dialog-verify-change").dialog("open");
							} else if (data == 0) {
								alert('Cập nhật trạng thái thành công');
								idpr = '';
								location.reload();
							} else if (data == -1) {
								alert('Sai mã hoặc để trống mã');
							} else if (data == -2) {
								alert('Không tìm thấy mã');
							} else if (data == -99) {
								alert('Lỗi trong quá trình cập nhật');
							}
						},
						error : function(e) {
							alert("Đã có lỗi xảy ra");
						}
					});
					$(this).dialog("close");
				},
				"Đóng" : function() {
					$(this).dialog("close");
				}
			}
		});

		$("#dialog-verify-change")
				.dialog(
						{
							modal : true,
							autoOpen : false,
							dialogClass: 'myTitleClass',
							width : 600,
							height : 200,
							resizable : false,
							show : {
								effect : "fade",
								duration : 1000
							},
							hide : {},
							buttons : {
								"Có" : function() {
									var conceptVal = $(
											"#select_status option:selected")
											.val();
									$.ajax({
												type : "POST",
												url : "${updateVerifyUrl}",
												data : conceptVal,
												dataType : "text",
												success : function(data) {
													if (data == 0) {
														conceptVal = '';
														if (idpr != '') {
															if(isNo){
																$.ajax({
																	type : "POST",
																	url : "${reprocessNoUrl}",
																	data : idpr,
																	dataType : "text",
																	success : function(
																			data) {
																		if (data == 0) {
																			alert('Cập nhật trạng thái thành công');
																			idpr = '';
																			isNo = false;
																			location.reload();
																		} else if (data == -1) {
																			alert('Sai mã hoặc để trống mã');
																		} else if (data == -2) {
																			alert('Không tìm thấy mã');
																		} else if (data == -99) {
																			alert('Lỗi trong quá trình cập nhật');
																		}
																	},
																	error : function(
																			e) {
																		alert("Đã có lỗi xảy ra");
																}
															});
															}
															else
																{
															$.ajax({
																		type : "POST",
																		url : "${reprocessUrl}",
																		data : idpr,
																		dataType : "text",
																		success : function(
																				data) {
																			if (data == 0) {
																				alert('Cập nhật trạng thái thành công');
																				idpr = '';
																				location.reload();
																			} else if (data == -1) {
																				alert('Sai mã hoặc để trống mã');
																			} else if (data == -2) {
																				alert('Không tìm thấy mã');
																			} else if (data == -99) {
																				alert('Lỗi trong quá trình cập nhật');
																			}
																		},
																		error : function(
																				e) {
																			alert("Đã có lỗi xảy ra");
																	}
																});
															}
														} else if (idwfls != '') {
															$("#dialog-workflow-change-end").dialog("close");
															alert('Thay đổi thành công');
															changeWflEnd(keyS,numS);
															 /* $.ajax({
																		type : "POST",
																		url : "${changeWorkflowEndUrl}",
																		data : idwfls,
																		dataType : "text",
																		success : function(
																				data) {
																			if (data == 'OK') {
																				alert('Cập nhật trạng thái thành công');
																				Cleaner();
																				location.reload();
																			} else if (data == 'NULL') {
																				alert('Sai mã hoặc để trống mã');
																			} else if (data == 'OFF') {
																				alert('Không tìm thấy mã');
																			} else if (data == 'UNUSED') {
																				alert('Luồng kết thúc được lựa chọn đã đóng');
																			} else if (data == 'ERROR') {
																				alert('Lỗi trong quá trình cập nhật');
																			}
																		},
																		error : function(e) {
																			alert("Đã có lỗi xảy ra");
																		}
															}); */
														}
														else {
															alert('Thay đổi thành công');
															location.reload();
														}
													} else if (data == -1) {
														alert('VER: Sai mã hoặc để trống mã');
													} else if (data == -2) {
														alert('VER: Không tìm thấy mã');
													} else if (data == -99) {
														alert('VER: Lỗi trong quá trình cập nhật');
													}
												},
												error : function(e) {
													alert("Đã có lỗi xảy ra");
												}
											});

								},
								"Đóng" : function() {
									$(this).dialog("close");
								}
							}
						});

		$("#dialog-workflow-change-end-no").dialog({
			modal : true,
			autoOpen : false,
			width : 600,
			height : 200,
			resizable : false,
			show : {	
				effect : "fade",
				duration : 1000
			},
			hide : {},
			buttons : {
				"Có" : function() {
					var conceptVal = $("#select_status_end option:selected").val();
					idwfls = idwfls + '-' + conceptVal;
					$.ajax({
						type : "POST",
						url : "${changeWorkflowEndNoUrl}",
						data : idwfls,
						dataType : "text",
						success : function(data) {
							var name = '';
							switch (data) {
								case "UPLOADED":
									name = "Đồng bộ";
							  		break;
								 case "CHECK_CPD":
									 name = "Kiểm tra CPD";
								  	break;
								 case "ASSIGNMENT":
									 name = "Phân công";
								  	break;
								 case "INVESTIGATION":
									 name = "Xử lý";
								  	break;
								 case "PRESENT_APPROVAL":
									 name = "Đề xuất";
								  	break;
								 case "LEADERS_APPROVAL":
									 name = "Lãnh đạo duyệt";
								  	break;
								 case "PERSO":
									 name = "Cá thể hóa";
								  	break;
								 default:
							}
							if (name != '') {
								$('#idwfChange').html("");
								$('#idwfChange').html(name);
								$.ajax({
									type : "GET",
									url : '${listWorkflowUrl}/' + data,
									dataType : 'json',
									success : function(json) {
										var $el = $("#select_status");
										$el.empty();
										$el.append(json);
									}
								});
								isNo = true;
								$("#dialog-verify-change").dialog("open");
							} else if (data == 'OK') {
									alert('Thay đổi thành công');
									location.reload();
							} else if (data == 'NULL') {
								alert('Sai mã hoặc để trống mã');
							} else if (data == 'OFF') {
								alert('Không tìm thấy mã');
							} else if (data == 'UNUSED') {
								alert('Luồng kết thúc được lựa chọn đã đóng');
							} else if (data == 'ERROR') {
								alert('Lỗi trong quá trình cập nhật');
							}
						},
						error : function(e) {
							alert("Đã có lỗi xảy ra");
						}
					});
					
				},
				"Đóng" : function() {
					$(this).dialog("close");
				}
			}
		});	
		
	$("#dialog-workflow-change-end").dialog({
			modal : true,
			autoOpen : false,
			width : 600,
			height : 200,
			resizable : false,
			show : {	
				effect : "fade",
				duration : 1000
			},
			hide : {},
			buttons : {
				"Có" : function() {
					var conceptVal = $("#select_status_end option:selected").val();
					idwfls = idwfls + '-' + conceptVal;
					$.ajax({
						type : "POST",
						url : "${changeWorkflowEndUrl}",
						data : idwfls,
						dataType : "text",
						success : function(data) {
							var name = '';
							switch (data) {
								case "UPLOADED":
									name = "Đồng bộ";
							  		break;
								 case "CHECK_CPD":
									 name = "Kiểm tra CPD";
								  	break;
								 case "ASSIGNMENT":
									 name = "Phân công";
								  	break;
								 case "INVESTIGATION":
									 name = "Xử lý";
								  	break;
								 case "PRESENT_APPROVAL":
									 name = "Đề xuất";
								  	break;
								 case "LEADERS_APPROVAL":
									 name = "Lãnh đạo duyệt";
								  	break;
								 case "PERSO":
									 name = "Cá thể hóa";
								  	break;
								 default:
							}
							if (name != '') {
								$('#idwfChange').html("");
								$('#idwfChange').html(name);
								$.ajax({
									type : "GET",
									url : '${listWorkflowUrl}/' + data,
									dataType : 'json',
									success : function(json) {
										var $el = $("#select_status");
										$el.empty();
										$el.append(json);
									}
								});
								$("#dialog-verify-change").dialog("open");
							} else if (data == 'OK') {
									alert('Thay đổi thành công');
									location.reload();
							} else if (data == 'NULL') {
								alert('Sai mã hoặc để trống mã');
							} else if (data == 'OFF') {
								alert('Không tìm thấy mã');
							} else if (data == 'UNUSED') {
								alert('Luồng kết thúc được lựa chọn đã đóng');
							} else if (data == 'ERROR') {
								alert('Lỗi trong quá trình cập nhật');
							}
						},
						error : function(e) {
							alert("Đã có lỗi xảy ra");
						}
					});
					
				},
				"Đóng" : function() {
					$(this).dialog("close");
				}
			}
		});
	
		$("#dialog-user-change").dialog({
			modal : true,
			autoOpen : false,
			width : 600,
			height : 200,
			resizable : false,
			show : {	
				effect : "fade",
				duration : 1000
			},
			hide : {},
			buttons : {
				"Có" : function() {
					var conceptVal = $("#select_user_special option:selected").val();
					$.ajax({
						type : "POST",
						url : "${changeUserUrl}",
						data : conceptVal,
						dataType : "text",
						success : function(data) {
							if (data == 0) {
								alert('Cập nhật thành công');
							}
							else if (data == -99) {
								alert('Lỗi trong quá trình cập nhật');
							}
							location.reload();
						},
						error : function(e) {
							alert("Đã có lỗi xảy ra");
						}
					});
				},
				"Đóng" : function() {
					$(this).dialog("close");
				}
			}
		});
		
		$("#changePage").click(function(){
			document.forms["investigationMenuForm"].action= '${changePageUrl}';
			document.forms["investigationMenuForm"].submit();	
	    });
		
		$("#dialog-reset-confirm").dialog({
			modal : true,
			autoOpen : false,
			width : 600,
			height : 200,
			resizable : false,
			show : {	
				effect : "fade",
				duration : 1000
			},
			hide : {},
			buttons : {
				"Có" : function() {
					$.ajax({
						type : "GET",
						url : "${resetAllUrl}",
						dataType : "text",
						success : function(data) {
							if (data == 0) {
								alert('Khởi tạo lại thành công');
							}
							else if (data == -99) {
								alert('Lỗi trong quá trình Khởi tạo lại');
							}
							location.reload();
						},
						error : function(e) {
							alert("Đã có lỗi xảy ra");
						}
					});	
				},
				"Đóng" : function() {
					$(this).dialog("close");
				}
			}
		});
		
		$("#resetAll").click(function(){
			$("#dialog-reset-confirm").dialog("open");
	    });
	});

	function Cleaner(){
		idpr = '';
		keyS = '';
		idwfls = '';
		numS = 0;
	}
	
	function changeVerifyEnd(key) {
		var name = "Lãnh đạo duyệt";
		switch (key) {
		case "INVESTIGATION":
			name = "Xử lý";
			break;
		case "PRESENT_APPROVAL":
			name = "Đề xuất";
			break;
		case "PERSO":
			name = "Cá thể hóa";
			break;	
		default:
		}
		$('#idwfChange').html("");
		$('#idwfChange').html(name);
		$.ajax({
			type : "GET",
			url : '${listWorkflowUrl}/' + key,
			dataType : 'json',
			success : function(json) {
				var $el = $("#select_status");
				$el.empty(); // remove old options
				/*  $el.append($("<option></option>")
				         .attr("value", '').text('Please Select')); */
				$el.append(json);
			}
		});
		$("#dialog-verify-change").dialog("open");
	}
	
	function changeUserSpecial(u){
		$('#idUserSpecial').html(u);
		
		$.ajax({
			type : "GET",
			url : '${listUserUrl}/' + u,
			dataType : 'json',
			success : function(json) {
				var $el = $("#select_user_special");
				$el.empty();
				$el.append(json);
			}
		});
		$("#dialog-user-change").dialog("open");
	}
</script>
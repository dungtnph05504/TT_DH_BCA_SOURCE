<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="cancelUrl" value="/servlet/adminConsole/workstationManagement" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style>
.fix-bottom {
	margin-top: 50px;
}
.btn-with {
	width: 30px;
}
</style>
<script type="text/javascript">
$(document).ready(
		function() {

			$('#addAllBtnId').click(
					function() {
						$('#unassignedRoles option').prop('selected', 'selected');
						$('#unassignedRoles option:selected').each(
								function() {
									$('#assignedRoles').append(
											"<option value='"
													+ $(this).val() + "'>"
													+ $(this).text()
													+ "</option>");
									$(this).remove();
								});
					});

			$('#addBtnId').click(
					function() {
						$('#unassignedRoles option:selected').each(
								function() {
									$('#assignedRoles').append(
											"<option value='"
													+ $(this).val() + "'>"
													+ $(this).text()
													+ "</option>");
									$(this).remove();
								});
					});

			$('#removBtnId').click(
					function() {
						$('#assignedRoles :selected').each(
								function() {
									$('#unassignedRoles').append(
											"<option value='"
													+ $(this).val() + "'>"
													+ $(this).text()
													+ "</option>");
									$(this).remove();
								});
					});

			$('#removAllBtnId').click(
					function() {
						$('#assignedRoles option')
								.prop('selected', 'selected');
						$('#assignedRoles :selected').each(
								function() {
									$('#unassignedRoles').append(
											"<option value='"
													+ $(this).val() + "'>"
													+ $(this).text()
													+ "</option>");
									$(this).remove();
								});
					});

		});
</script>

<!-- <div id="main"> -->
	<div id="content_main">
<c:url var="url" value="/servlet/adminConsole/workstationManagement/saveWorkstation"/>
	<!--<c:if test="${not empty requestScope.Errors}">
		<div style="color:Red;background-color:White;border-style:solid;border-color:Red;border-width:thin;">
			<c:forEach items="${requestScope.Errors}" var="takla">
				<li>
					${takla}
				</li>
			</c:forEach>
			
		</div>
	</c:if>-->
<form:form modelAttribute="addEditWorkstationModel" action="${url}" method="POST" cssClass="inline" cssStyle="">
		<div class="container">
                        <div class="row">
                            <div class="ov_hidden">
			<!--<table id="heading_left" style="width: 25%;height: 35px;">
				<tr>
					<c:if test="${empty workstation}">
						<td class='subHeading' id="heading_left" colspan='4' style="padding: 5px">Thêm máy trạm</td>
					</c:if>
					<c:if test="${not empty workstation}">
						<td class='subHeading' id="heading_left" colspan='4' style="padding: 5px">Sửa máy trạm</td>
					</c:if>
				</tr>
			</table>-->

					<c:if test="${empty workstation}">
						<div class="new-heading-2">THÊM MÁY TRẠM</div>
					</c:if>
					<c:if test="${not empty workstation}">
						<div class="new-heading-2">CẬP NHẬT MÁY TRẠM</div>
					</c:if>
			<fieldset>
				<legend>Thông tin máy trạm</legend>
				
				<div class="col-md-12">
					<div class="col-md-6">
						<div class="col-md-4 fix-bottom">
						<label for="countryCode">Mã máy trạm<i style="color: red">(*)</i></label>
						</div>
						<div class="col-md-8 fix-bottom">
							<c:if test="${empty workstation}">
								<input id="wkstnId" name="wkstnId" class="form-control" type="text"
								value="" size="30" maxlength="30" />
							</c:if>
							<c:if test="${not empty workstation}">
								<input id="wkstnId" name="wkstnId" class="form-control" type="text"
								value="${workstation.wkstnId}" size="30" maxlength="30" disabled="disabled" />
							</c:if>						
						</div>
						<div class="col-md-4 fix-bottom">
							<label for="countryCode">Mô tả máy trạm<i style="color: red">(*)</i></label>
						</div>
						<div class="col-md-8 fix-bottom">
							<input id="wkstnDesc" name="wkstnDesc" class="form-control" type="text"
							value="${workstation.wkstnDesc}" size="30" maxlength="30" />
						</div>
						<div class="col-md-4 fix-bottom">
							<label for="countryCode">Mã trung tâm<i style="color: red">(*)</i></label>
						</div>
						<div class="col-md-8 fix-bottom">
							<select id="siteCode" name="siteCode" class="form-control">										
								<option value=""  label="--- Chọn ---" />
								<c:forEach var="maplist" items="${addEditWorkstationModel.sitCodeMap}" varStatus="status1">												
									<c:choose>
										<c:when test="${maplist.key == workstation.siteCode}">
											<option value="${maplist.key}" selected="selected" label="${maplist.value }" />
										</c:when>
										<c:otherwise>
											<option value="${maplist.key}" label="${maplist.value }" />
										</c:otherwise>
									</c:choose>
								</c:forEach>										 
							</select>							
						</div>
					</div>
					<div class="col-md-6" style="margin-top: 10px;">
						<div class="col-md-5" style="text-align: center;padding: 0px;">
							<!--<input type="button" class="btn btn-primary" value="Vai trò chưa được phân quyền" style="margin-bottom: 10px;"/>-->
							<fieldset>
								<legend>Quyền chưa phân</legend>
								<form:select id="unassignedRoles" path="unassignedRoles" cssStyle="width: 200px; height: 200px" multiple="true" >
										<c:forEach var="maplist" items="${addEditWorkstationModel.unassignedRoleMaps}" >
											<form:option value="${maplist.key}" label="${maplist.value }"/>
										</c:forEach>
								</form:select>
							</fieldset>
						</div>
						<div class="col-md-2" style="padding-left: 30px;margin-top: 50px;">
							<table>
								<tr>
									<td style="height: 40px;"><input id="addAllBtnId" type="button"
									class="btn_small btn-success btn-with" value=">>" align="center"  /></td>
								</tr>
								<tr>
									<td style="height: 40px;"><input id="addBtnId" type="button"
									class="btn_small btn-success btn-with" value=">" align="center"  /></td>
								</tr>
								<tr>
									<td style="height: 40px;"><input id="removBtnId" type="button"
									class="btn_small btn-success btn-with" value="<" align=" center"  /></td>
								</tr>
								<tr>
									<td style="height: 40px;"><input id="removAllBtnId" type="button"
									class="btn_small btn-success btn-with" value="<<" align=" center"  /></td>
								</tr>
							</table>
						</div>
						<div class="col-md-5" style="text-align: center;padding: 0px;">
							<!--<input type="button" class="btn btn-primary" value="Vai trò được chỉ định" style="margin-bottom: 10px;"/>-->
							<fieldset>
								<legend>Quyền đã được phân</legend>
								<form:select id="assignedRoles" path="assignedRoles" cssStyle="width: 200px; height: 200px" multiple="true" >
									<c:forEach var="maplist" items="${addEditWorkstationModel.assignedRoleMaps}" >
										<form:option value="${maplist.key}" label="${maplist.value }"/>
									</c:forEach>
								</form:select>
							</fieldset>
						</div>
				</div>
			</div>
			</fieldset>
			<!--<div class="col-md-12" style="margin-top: 50px;text-align: right;">
				<button type="button" class="btn btn-danger"   onclick="doSubmitCancel();" id="back_btn">
					<span class="glyphicon glyphicon-remove"></span> Hủy
				</button>
				<button type="button" class="btn btn-primary"  onclick="window.location.href='${requestScope['javax.servlet.forward.request_uri']}'" id="reset_btn">
					<span class="glyphicon glyphicon-refresh"></span> Làm mới
				</button>
				<button type="button" class="btn btn-success" onclick="doSubmitSave(this.form);">
					<span class="glyphicon glyphicon-ok"></span> Lưu
				</button>
			</div>-->
			<div style="display: flex;flex: 0 auto;justify-content: center;">
			<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
				<div style="margin: 10px"> 			
					<button type="button" class="btn btn-success"   onclick="doSubmitCancel();" id="back_btn">
						<span class="glyphicon glyphicon-remove"></span> Hủy
					</button>
					<button type="button" class="btn btn-success"  onclick="window.location.href='${requestScope['javax.servlet.forward.request_uri']}'" id="reset_btn">
						<span class="glyphicon glyphicon-refresh"></span> Làm mới
					</button>
					<button type="button" class="btn btn-success" onclick="doSubmitSave(this.form);">
						<span class="glyphicon glyphicon-ok"></span> Lưu
					</button>
				</div>
			</div>
			</div>
			<!--<table style="width:100%;background-color:#E3F1FE" cellspacing="0"class="data_table" cellpadding="0">
			
						<tr>
								<td></td>
								<td></td>
								<td rowspan="9">
									<table width="50%">
										<tr>
											<td class="btn btn-primary" style="font-weight: bold;">Vai trò chưa được phân quyền&nbsp;&nbsp;</td>
											<td></td>
											<td class="btn btn-primary" style="font-weight: bold;width: 206px;">Vai trò được chỉ định&nbsp;&nbsp;</td>
										</tr>
										<tr>
											<td>
												
												<%-- <form:select path="unassignedRoles" items="${addEditWorkstationModel.unassignedRoles}"  cssStyle="width: 200px; height: 200px" /> --%>
												<form:select id="unassignedRoles" path="unassignedRoles" cssStyle="width: 200px; height: 200px" multiple="true" >
									   	        	<c:forEach var="maplist" items="${addEditWorkstationModel.unassignedRoleMaps}" >
														<form:option value="${maplist.key}" label="${maplist.value }"/>
													</c:forEach>
									   	        </form:select>
												
											</td>
											<td>
												<table>
													<tr>
														<td><input id="addAllBtnId" type="button"
															class="btn_small btn-primary" value=">>" style="width: 30px;" align="center" /></td>
													</tr>
													<tr>
														<td><input id="addBtnId" type="button"
															class="btn_small btn-primary" value=">" style="width: 30px;" align="center" /></td>
													</tr>
													<tr>
														<td><input id="removBtnId" type="button"
															class="btn_small  btn-primary" value="<" style="width: 30px;" align=" center" /></td>
													</tr>
													<tr>
														<td><input id="removAllBtnId" type="button"
															class="btn_small btn-primary" value="<<" style="width: 30px;" align=" center" /></td>
													</tr>
												</table>
											</td>
											<td>
												<%-- <form:select path="assignedRoles" items="${addEditWorkstationModel.assignedRoles}"  cssStyle="width: 200px; height: 200px" /> --%>
												<form:select id="assignedRoles" path="assignedRoles" cssStyle="width: 200px; height: 200px" multiple="true" >
									   	        	<c:forEach var="maplist" items="${addEditWorkstationModel.assignedRoleMaps}" >
														<form:option value="${maplist.key}" label="${maplist.value }"/>
													</c:forEach>
									   	        </form:select>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							
							<c:if test="${empty workstation}">
							<tr>
								<td style="border: none; font-weight: bold">Mã máy trạm<span style="COLOR: #ff0000;">*</span></td>
								<td style="border: none;">
									<c:if test="${empty workstation}">
										<input id="wkstnId"
											name="wkstnId" class="defaultText" type="text"
											value="" size="30" maxlength="30" />
									</c:if>
									<c:if test="${not empty workstation}">
										<input id="wkstnId"
											name="wkstnId" class="defaultText" type="text"
											value="${workstation.wkstnId}" size="30" maxlength="30" disabled="disabled" />
									</c:if>
									
								</td>
							</tr>
							
							</c:if>
							<c:if test="${not empty workstation}">
							<tr>
								<td style="border: none; font-weight: bold">Mã máy trạm</td>
								<td style="border: none;"><input id="wkstnId"
									name="wkstnId" class="defaultText" type="text" disabled="disabled"
									value="${workstation.wkstnId}" size="30" maxlength="30" /></td>
							</tr>
							<br />
							</c:if>
							<tr>
								<td style="border: none; font-weight: bold">Mô tả máy trạm<span style="COLOR: #ff0000;">*</span></td>
								<td style="border: none;"><input id="wkstnDesc"
									name="wkstnDesc" class="defaultText" type="text"
									value="${workstation.wkstnDesc}" size="30" maxlength="30" /></td>
							</tr>
			</table>-->
							
							<!--<table style="background-image:url('<%--=request.getContextPath() --%>/resources/images/head.png');background-repeat: repeat-x;height: 40px;">
							<tr>
								<td  align="right" style="border: none; font-weight: bold;text-align: center;" > <input
									type="button" class="btn_small btn-primary" value="Lưu" style="width: 60px;"
									onclick="doSubmitSave(this.form);" />&nbsp;&nbsp;&nbsp; <input
									type="button" class="btn_small btn-primary" id="reset_btn" onclick="window.location.href='${requestScope['javax.servlet.forward.request_uri']}'" style="width: 60px;"
									value="Làm lại" />&nbsp;&nbsp;&nbsp; <input
									type="button" class="btn_small btn-primary" id="cancel_btn" onclick="doSubmitCancel();" style="width: 60px;"
									value="Hủy" /></td>
							</tr>
						</table>-->
		</div>
		</div>
		</div>
	</form:form>
	</div>
<!-- </div> -->
<script type="text/javascript">
	function doSubmitSave(form) {
		if(checkForValidForm()){
			 document.getElementById('wkstnId').disabled=false;
			$('#unassignedRoles option').prop('selected', 'selected');
			$('#assignedRoles option').prop('selected', 'selected');
			form.submit();
		}
	}
	
	function doSubmitCancel(){
		document.forms[0].action = "${cancelUrl}";
		document.forms[0].submit();
	}
	
	function checkForValidForm(){
		var wkstnId = $('#wkstnId').val();
		
		if(wkstnId=="" || trim(wkstnId)== ""){
			//alert('Hãy nhập mã máy trạm');
			/*$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Hãy nhập mã máy trạm!",
			});*/
			$.notify('Chưa nhập mã máy trạm.', {
				globalPosition: 'bottom left',
				className: 'warn',
			});
			return false;
		}
		
		
		var wkstnDesc = $('#wkstnDesc').val();
		
		if(wkstnDesc=="" || trim(wkstnDesc)== ""){
			//alert('Vui lòng mô tả máy trạm');
			/*$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Vui lòng mô tả máy trạm!",
			});*/
			$.notify('Chưa nhập mô tả máy trạm.', {
				globalPosition: 'bottom left',
				className: 'warn',
			});
			return false;
		}
		
		var siteCode = $('select#siteCode').val();
		
		if(siteCode=="" || trim(siteCode)== ""){
			//alert('Vui lòng mô tả máy trạm');
			/*$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Vui lòng mô tả máy trạm!",
			});*/
			$.notify('Chưa chọn trung tâm.', {
				globalPosition: 'bottom left',
				className: 'warn',
			});
			return false;
		}
		
		return true;
	}
	
	 function trim(str) {
	    return str.replace(/^\s+|\s+$/g,"");
	  }
</script>

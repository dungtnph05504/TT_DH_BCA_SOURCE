<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="cancelUrl" value="/servlet/adminConsole/roleManagement" />
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
							$('#unassignedFunctions option').prop('selected', 'selected');
							$('#unassignedFunctions option:selected').each(
									function() {
										$('#assignedFunctions').append(
												"<option value='"
														+ $(this).val() + "'>"
														+ $(this).text()
														+ "</option>");
										$(this).remove();
									});
						});

				$('#addBtnId').click(
						function() {
							$('#unassignedFunctions option:selected').each(
									function() {
										$('#assignedFunctions').append(
												"<option value='"
														+ $(this).val() + "'>"
														+ $(this).text()
														+ "</option>");
										$(this).remove();
									});
						});

				$('#removBtnId').click(
						function() {
							$('#assignedFunctions :selected').each(
									function() {
										$('#unassignedFunctions').append(
												"<option value='"
														+ $(this).val() + "'>"
														+ $(this).text()
														+ "</option>");
										$(this).remove();
									});
						});

				$('#removAllBtnId').click(
						function() {
							$('#assignedFunctions option')
									.prop('selected', 'selected');
							$('#assignedFunctions :selected').each(
									function() {
										$('#unassignedFunctions').append(
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
	<c:url var="url" value="/servlet/adminConsole/roleManagement/saveRole"/>
	<!--<c:if test="${not empty requestScope.Errors}">
		<div style="color:Red;background-color:White;border-style:solid;border-color:Red;border-width:thin;">
			<c:forEach items="${requestScope.Errors}" var="takla">
				<li>
					${takla}
				</li>
			</c:forEach>
			
		</div>
	</c:if>-->
<form:form modelAttribute="addEditRoleModel" action="${url}" method="POST" cssClass="inline" cssStyle="">
   <div class="container">
   <div class="row">
   <div class="ov_hidden">
			<!--<table class="heading_report" id="heading_left" style="width: 25%;height: 35px;">
				<tr>
					<c:if test="${empty role}">
						<td class='subHeading' id="heading_left" colspan='4' style="padding: 5px">THÊM QUYỀN</td>
					</c:if>
					<c:if test="${not empty role}">
						<td class='subHeading'  id="heading_left" colspan='4' style="padding: 5px">SỬA QUYỀN</td>
					</c:if>
				</tr>
			</table>-->
					<c:if test="${empty role}">
						<div class="new-heading-2">THÊM QUYỀN</div>
					</c:if>
					<c:if test="${not empty role}">
						<div class="new-heading-2">SỬA QUYỀN</div>
					</c:if>
			<fieldset>
				<legend>Thông tin quyền</legend>
	
				<div class="col-md-12">
					<div class="col-md-6">
						<div class="col-md-4 fix-bottom">
						<label for="countryCode">Tên quyền<i style="color: red">(*)</i></label>
						</div>
						<div class="col-md-8 fix-bottom">
							<c:if test="${empty role}">
								<input id="roleId" name="roleId" class="form-control" type="text"
									value="" size="30" maxlength="30" />
							</c:if>
							<c:if test="${not empty role}">
									<input id="roleId" name="roleId" class="form-control" type="text"
									value="${role.roleId}" size="30" maxlength="30" disabled="disabled" />
							</c:if>
						</div>
						<div class="col-md-4 fix-bottom">
							<label for="countryCode">Mô tả quyền<i style="color: red">(*)</i></label>
						</div>
						<div class="col-md-8 fix-bottom">
							<input id="roleDesc" name="roleDesc" class="form-control" type="text"
							value="${role.roleDesc}" size="30" maxlength="30" />
						</div>
						<div class="col-md-4 fix-bottom">
							<label for="countryCode">Đơn vị<i style="color: red">(*)</i></label>
						</div>
						<div class="col-md-8 fix-bottom">
							<select id="systemId" name="systemId" class="form-control">										
								<option value=""  label="--- Chọn ---" />
								<c:forEach var="maplist" items="${addEditRoleModel.systemMap}" varStatus="status1">												
									<c:choose>
										<c:when test="${maplist.key == role.systemId}">
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
							<!--<input type="button" class="btn btn-primary" value="Chức năng không được giao" style="margin-bottom: 10px;"/>-->
							<fieldset>
						 	 	<legend>Chức năng chưa giao</legend>
								<form:select id="unassignedFunctions" path="unassignedFunctions" cssStyle="width: 200px; height: 200px" multiple="true" >
									<c:forEach var="maplist" items="${addEditRoleModel.unassignedFuncMaps}" >
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
							<!--<input type="button" class="btn btn-primary" value="Chức năng được giao" style="margin-bottom: 10px;"/>-->
							<fieldset>
						 	 	<legend>Chức năng được giao</legend>
								<form:select id="assignedFunctions" path="assignedFunctions" cssStyle="width: 200px; height: 200px"  multiple="true" >
									<c:forEach var="maplist" items="${addEditRoleModel.assignedFuncMaps}" >
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
				<button type="button" class="btn btn-primary" onclick="window.location.href='${requestScope['javax.servlet.forward.request_uri']}'" id="reset_btn">
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
					<button type="button" class="btn btn-success" onclick="window.location.href='${requestScope['javax.servlet.forward.request_uri']}'" id="reset_btn">
						<span class="glyphicon glyphicon-refresh"></span> Làm mới
					</button>
					<button type="button" class="btn btn-success" onclick="doSubmitSave(this.form);">
						<span class="glyphicon glyphicon-ok"></span> Lưu
					</button>
				</div>
			</div>
			</div>
			<!--<table style="width:100%;background-color:#E3F1FE" cellspacing="0"class="data_table" cellpadding="0"><tr>
				
							<tr>
								<td></td>
								<td></td>
								<td rowspan="9">
									<table width="50%">
										<tr>
											<td class="btn btn-primary" style="font-weight: bold;">Chức năng không được giao&nbsp;&nbsp;</td>
											<td></td>
											<td class="btn btn-primary" style="font-weight: bold;width: 206px;">Chức năng được giao&nbsp;&nbsp;</td>
										</tr>
										<tr>
											<td>												
												<%-- <form:select path="unassignedFunctions" items="${addEditRoleModel.unassignedFunctions}"  cssStyle="width: 200px; height: 200px" /> --%>
												<form:select id="unassignedFunctions" path="unassignedFunctions" cssStyle="width: 200px; height: 200px" multiple="true" >
									   	        	<c:forEach var="maplist" items="${addEditRoleModel.unassignedFuncMaps}" >
														<form:option value="${maplist.key}" label="${maplist.value }"/>
													</c:forEach>
									   	        </form:select>
											</td>
											<td>
												<table>
													<tr>
														<td><input id="addAllBtnId" type="button"
															class="btn_small btn-primary" value=">>" align="center" style="width: 30px;" /></td>
													</tr>
													<tr>
														<td><input id="addBtnId" type="button"
															class="btn_small btn-primary" value=">" align="center" style="width: 30px;" /></td>
													</tr>
													<tr>
														<td><input id="removBtnId" type="button"
															class="btn_small btn-primary" value="<" align=" center" style="width: 30px;" /></td>
													</tr>
													<tr>
														<td><input id="removAllBtnId" type="button"
															class="btn_small btn-primary" value="<<" align=" center" style="width: 30px;" /></td>
													</tr>
												</table>
											</td>
											<td>
												<%-- <form:select  path="assignedFunctions" items="${addEditRoleModel.assignedFunctions}"  cssStyle="width: 200px; height: 200px" /> --%>
												<form:select id="assignedFunctions" path="assignedFunctions" cssStyle="width: 200px; height: 200px"  multiple="true" >
									   	        	<c:forEach var="maplist" items="${addEditRoleModel.assignedFuncMaps}" >
														<form:option value="${maplist.key}" label="${maplist.value }"/>
													</c:forEach>
									   	        </form:select>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							
							
							<tr>
								<td style="border: none; font-weight: bold">Tên quyền<span style="COLOR: #ff0000;">*</span></td>
								<td style="border: none;">
									
									<c:if test="${empty role}">
										<input id="roleId"
											name="roleId" class="defaultText" type="text"
											value="" size="30" maxlength="30" />
									</c:if>
									<c:if test="${not empty role}">
										<input id="roleId"
											name="roleId" class="defaultText" type="text"
											value="${role.roleId}" size="30" maxlength="30" disabled="disabled" />
									</c:if>
								</td>
							</tr>
							
							
							<tr>
								<td style="border: none; font-weight: bold">Mô tả quyền<span style="COLOR: #ff0000;">*</span></td>
								<td style="border: none;"><input id="roleDesc"
									name="roleDesc" class="defaultText" type="text"
									value="${role.roleDesc}" size="30" maxlength="30" /></td>
							</tr>
							
							</table>-->
							<!--<table style="background-image:url('<%--=request.getContextPath() --%>/resources/images/head.png');background-repeat: repeat-x;height: 40px;">
							<tr>
								<td  align="right" style="border: none; font-weight: bold;text-align: center;" > 
								<input
									type="button" class="btn_small btn-primary" value="Lưu" style="width: 60px;"
									onclick="doSubmitSave(this.form);" />&nbsp;&nbsp;&nbsp;&nbsp; <input
									type="button" id="reset_btn" class="btn_small btn-primary" onclick="window.location.href='${requestScope['javax.servlet.forward.request_uri']}'" style="width: 60px;"
									value="làm lại" />
									&nbsp;&nbsp;&nbsp;&nbsp; 
									<input
									type="button" class="btn_small btn-primary" value="Hủy" style="width: 60px;"
									onclick="doSubmitCancel();" />
									</td>
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
			document.getElementById('roleId').disabled=false;
			$('#unassignedFunctions option').prop('selected', 'selected');
			$('#assignedFunctions option').prop('selected', 'selected');
			form.submit();
		}
	}
	
	
	function checkForValidForm(){
		var roleId = $('#roleId').val();
		
		if(roleId=="" || trim(roleId)== ""){
			//alert('Vui lòng nhập tên quyền');
			/*$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Vui lòng nhập tên quyền!",
			});*/
			$.notify('Vui lòng nhập tên quyền.', {
				globalPosition: 'bottom left',
				className: 'warn',
			});
			return false;
		}
		
		
		var roleDesc = $('#roleDesc').val();
		
		if(roleDesc=="" || trim(roleDesc)== ""){
			//alert('Vui lòng nhập mô tả quyền');
			/*$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Vui lòng nhập mô tả quyền!",
			});*/
			$.notify('Vui lòng nhập mô tả quyền.', {
				globalPosition: 'bottom left',
				className: 'warn',
			});
			return false;
		}
		
		var systemId = $('select#systemId').val();
		
		if(systemId=="" || trim(systemId)== ""){
			//alert('Vui lòng nhập mô tả quyền');
			/*$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Vui lòng nhập mô tả quyền!",
			});*/
			$.notify('Vui lòng chọn đơn vị thực thi.', {
				globalPosition: 'bottom left',
				className: 'warn',
			});
			return false;
		}
		
		return true;
	}
	function doSubmitCancel(){
		document.forms[0].action = "${cancelUrl}";
		document.forms[0].submit();
	}
	
	 function trim(str) {
	    return str.replace(/^\s+|\s+$/g,"");
	  }
</script>


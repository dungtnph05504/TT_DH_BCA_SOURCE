<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<c:url var="saveApiFormUrl" value="/servlet/central/saveApiForm" />
<style>

.style-width-100 {
	width: 100%;
}

.st-mgt-10 {
	margin-top: 10px;
}

</style>
<div class="content-item-title">
    <div class="oplog-title__txt">Thêm mới api quản lý</div>
</div>
<div class="content-item-main" style="margin: 0px 50px 0px 50px;">
<form:form modelAttribute="formData" name="formData" autocomplete="off"> 
  
   <div class="content-item-information">
			<div class="col-sm-12">
				<fieldset class="scheduler-border">
					<legend>Nhập thông tin API</legend>						
						<div class="col-sm-6">
							<label class="col-sm-4 control-label text-right st-mgt-10">URL<span style="color: red;padding-left: 5px;">*</span></label>
							<div class="col-sm-8 st-mgt-10">
								<form:input type="text" path="url" id="url" class="style-width-100" />
							</div>
							<label class="col-sm-4 control-label text-right st-mgt-10">Tên API<span style="color: red;padding-left: 5px;">*</span></label>
							<div class="col-sm-8 st-mgt-10">
								<form:input path="nameApi"  class="style-width-100" id="idName" placeholder="" />
							</div>
							<label class="col-sm-4 control-label text-right st-mgt-10">Loại APi<span style="color: red;padding-left: 5px;">*</span></label>
							<div class="col-sm-8 st-mgt-10">
								<form:select path="typeList" class="style-width-100">
									<form:option value="1">API xử lý hồ sơ</form:option>
									<form:option value="2">API Cá thể hóa</form:option>
									<form:option value="3">API Xuất nhập cảnh</form:option>
								</form:select>
							</div>
							<label class="col-sm-4 control-label text-right st-mgt-10">Mô tả<span style="color: red;padding-left: 5px;">*</span></label>
							<div class="col-sm-8 st-mgt-10">
								<form:textarea rows="4" path="listName" id="desc" class="style-width-100" cols="" />
							</div>
						</div>
						<div class="col-sm-6">
							<label class="col-sm-4 st-mgt-10 control-label text-right">Trạng thái<span style="color: red;padding-left: 5px;">*</span></label>
							<div class="col-sm-8 st-mgt-10">
								<form:select class="style-width-100" path="typeInvestigation">
									<form:option value="N">Đang hoạt động</form:option>
									<form:option value="Y">Không hoạt đông</form:option>
								</form:select>
							</div>
							<label class="col-sm-4 st-mgt-10 control-label text-right">Chặn trung tâm</label>
							<div class="col-sm-8 st-mgt-10">
								<div style="height: 200px;overflow: auto;border: 1px solid #cecccc;padding: 10px;">
									<c:forEach items="${siteList}"  var="_item">
										<div class="checkbox" style="margin-bottom: 5px;"> 
										  <label>
										  		<form:checkbox path="loadJobIds" name="chkSite" value="${_item.key}" />${_item.value}
										  </label>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>						
					
				</fieldset>
			</div>
		</div>
	<div id="ajax-load-qa"></div>
	<!-- ---------------------------------------------------------------------------->
	<div class="col-sm-12">
		<div style="display: flex;flex: 0 auto;justify-content: center;">
		<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
			<div style="margin: 10px"> 
				<button type="button"  onclick="saveApi();" name="approve" class="btn btn-success">
					<span class="glyphicon glyphicon-ok"></span> Lưu thông tin
				</button>
			</div>
		</div>	
		</div> 
	</div>

	<script type="text/javascript">
	    
	    function saveApi(){
	    	
	    	var txtUrl = $('#url').val();
	    	var txtName = $('#idName').val();
	    	var txtDesc = $('#desc').val();
	    	var err_msg = '';
	    	if(txtUrl == ''){
	    		err_msg = 'URL không được để trống.';
	    	}else if(txtName == ''){
	    		err_msg = 'Tên API không được để trống.';
	    	}else if(txtDesc == ''){
	    		err_msg = 'Mô tả API không được để trống.';
	    	}
	    	if(err_msg != ''){
	    		$.notify(err_msg, {
					globalPosition: 'bottom left',
					className: 'warn',
				});
	    		return;
	    	}
	    	document.forms["formData"].action = '${saveApiFormUrl}';  
			document.forms["formData"].submit();
	    }
	    
	</script>
	
</form:form>
</div>


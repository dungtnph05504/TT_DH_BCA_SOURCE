<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<c:url var="loadSiteStreamUrl" value="/servlet/central/loadSiteStream" />
<c:url var="saveSiteStreamUrl" value="/servlet/central/saveSiteStream" />
<c:url var="thongtinchitietUrl" value="/servlet/central/detailSiteStream" />
<%-- <form:form commandName="jobList" id="form" name="form" > --%>
<style>
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
.magin-lr-50 {
	margin: 10px 30px 130px 30px;
}
.c-hide {
	display: none;
}
.c-text-100 {
	width: 100%;
    height: 28px;
}
</style>
<div class="content-item-title">
     <div class="oplog-title__txt">Cấu hình luồng trung tâm</div>
 </div>
<div class="content-item-main">
<form:form modelAttribute="formData" name="formData" autocomplete="off"> 
	<div class="row magin-lr-50">
		<div class="col-sm-12">
			<fieldset>
				<legend>Thay đổi luồng trung tâm</legend>
				<div class="col-sm-8" style="margin-top: 20px;">
				<div class="form-group" style="margin-bottom: 30px;">
					<div class="col-sm-6">
						<label class="col-sm-4">Luồng thay đổi</label>
						<div class="col-sm-8">
							<select id="keyType" class="selectClass">
								<option value="XL">Thay đổi luồng xử lý</option>
								<option value="IN">Thay đổi luồng cá thể hóa</option>
								<option value="PH">Thay đổi luồng phát hành</option>
							</select>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group" id="idXL">
							<label class="col-sm-4">TT Xử lý</label>
							<div class="col-sm-8">
								<select id="keySite" class="selectClass">
									<c:forEach items="${groupList}" var="_item">
										<c:choose>
											<c:when test="${_item.key == '18A'}">
												<option value="${_item.key}" selected="selected">${_item.value}</option>
											</c:when>
											<c:otherwise>
												<option value="${_item.key}">${_item.value}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group" id="idIN">
							<label class="col-sm-4">TT Cá thể hóa</label>
							<div class="col-sm-8">
								<select id="keyPrint" class="selectClass">
									<c:forEach items="${persoList}" var="_item">
										<option value="${_item.key}">${_item.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group" id="idPH">
							<label class="col-sm-4">TT phát hành</label>
							<div class="col-sm-8">
								<select id="keyIssue" class="selectClass">
									<c:forEach items="${siteList}" var="_item">
										<option value="${_item.key}">${_item.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-6">
						<label class="col-sm-4">Hiệu lực từ<span style="color: red;padding: 5px;">*</span></label>
						<div class="col-sm-8">
							<input type="text" id="idHieuLucTu" class="c-text-100"/>
						</div>
					</div>
					<div class="col-sm-6">
						<label class="col-sm-4">Hiệu lực đến<span style="color: red;padding: 5px;">*</span></label>
						<div class="col-sm-8">
							<input type="text" id="idHieuLucDen" class="c-text-100"/>
						</div>
					</div>
				</div>
				</div>
				<div class="col-sm-4">	
					<div class="panel panel-success">
				      <div class="panel-heading">Đơn vị tiếp nhận</div>
				      <div id="idSiteChk" class="panel-body" style="height: 150px;overflow: auto;">
				      		<c:forEach items="${siteList}"  var="_item">
								<div class="checkbox" style="margin-bottom: 5px;"> 
								  <label><input type="checkbox" name="chkSite" value="${_item.key}">${_item.value}</label>
								</div>
							</c:forEach>
				      </div>
				    </div>			
					<!--<label class="col-sm-4">Đơn vị tiếp nhận</label>
					<div class="col-sm-8">
						<div id="idSiteChk" style="height: 150px; overflow: auto;border: 1px solid #a9a6a6;padding: 5px;">
							<c:forEach items="${siteList}"  var="_item">
								<div class="checkbox" style="margin-bottom: 5px;"> 
								  <label><input type="checkbox" name="chkSite" value="${_item.key}">${_item.value}</label>
								</div>
							</c:forEach>
						</div>
					</div>	-->			
				</div>
			</fieldset>
		</div>
	</div>
	<div class="row">
         <div class="col-xl-12 col-sm-12" style="background-color: #000;text-align: center;height: 150px;padding: 60px;font-size: 20px;">
            <a href="${thongtinchitietUrl}">
                <div class="card text-white epp-bg-dark o-hidden h-100">
                    <div class="card-body">
                        <div class="mr-5" style="font-size: 20px; font-weight: 600; color: #fff;">Thông tin chi tiết</div>
                    </div>
                </div>
            </a>
        </div>
    </div>
		
<div id="ajax-load-qa"></div>
<div style="display: flex;flex: 0 auto;justify-content: center;">
<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
	<div style="margin: 10px"> 
		<button type="button" id="btn_stream" name="approve" class="btn btn-success">
			  <span class="glyphicon glyphicon-check"></span> Xác nhận thay đổi
		</button>
	</div>
</div>
</div>
<script type="text/javascript">

/*function btnSaveStream(){
	 var site = '';
  	 var type =	$('#keyType').val();
  	 if(type == 'XL'){
  		 site = $('#keySite').val();
  	 }else if(type == 'IN'){
  		 site = $('#keyPrint').val();
  	 }else if(type == 'PH'){
  		 site = $('#keyIssue').val();
  	 }
  	 var arrSite = new Array();
  	 $("input[name='chkSite']").each(function () {
  		if(this.checked){
  			arrSite.push($(this).val());
  		}
  	 });
  	 var dateFrom = $('#idHieuLucTu').val();
  	 var dateTo = $('#idHieuLucDen').val();
    var url = '${saveSiteStreamUrl}';
	 $.ajax({
		url : url,
		type: "POST",
		data: {
			site : site,
			type : type,
			dateFrom : dateFrom,
			dateTo : dateTo,
			arrSite : arrSite
		},
		success : function(data) {
			
		},
		error : function(e){}
	 });
}*/
var pattern = /^([0-2][0-9]|(3)[0-1])(\/)(((0)[0-9])|((1)[0-2]))(\/)\d{4} (\d{2}):(\d{2})$/;

function loadSite(){
	 var site = '';
	 var type =	$('#keyType').val();
	 if(type == 'XL'){
		 site = $('#keySite').val();
	 }else if(type == 'IN'){
		 site = $('#keyPrint').val();
	 }else if(type == 'PH'){
		 site = $('#keyIssue').val();
	 }
	 var url = '${loadSiteStreamUrl}';
	 $.ajax({
		url : url,
		type: "POST",
		data: {
			site : site,
			type : type
		},
		success : function(data) {
			$('#idSiteChk').find('input[type=checkbox]').removeAttr('disabled');
			$("input[name='chkSite']").each(function () {
				$(this).prop("checked", false);
			});				
			$("input[name='chkSite']").each(function () {
				for(var i = 0; i < data.length; i++){
					if($(this).val() == data[i].siteId && data[i].stage == '0'){
						$(this).prop("disabled", true);
						break;
					}	
					if($(this).val() == data[i].siteId && data[i].stage == '1'){
						$(this).prop("checked", true);
						break;
					}	
				}
		    });
		},
		error : function(e){}
	 });
}

$(document).ready(function() {
	
	$('.selectClass').SumoSelect();
	$('#idIN').hide();
	$('#idPH').hide();
	
	loadSite();//load mặc định ban đầu
	
    $('#keyType').change(function(){
    	var key = $('#keyType').val();
    	if(key == 'XL'){
    		$('#idIN').hide();
    		$('#idPH').hide(); 	
    		$('#idXL').show();
    	}
		if(key == 'IN'){
			$('#idIN').show();
    		$('#idPH').hide(); 	
    		$('#idXL').hide();
    	}
		if(key == 'PH'){
			$('#idIN').hide();
    		$('#idPH').show(); 	
    		$('#idXL').hide();
		}
		
    });
    
    $('#btn_stream').click(function(){  	 
    	 var site = '';
     	 var type =	$('#keyType').val();
     	 if(type == 'XL'){
     		 site = $('#keySite').val();
     	 }else if(type == 'IN'){
     		 site = $('#keyPrint').val();
     	 }else if(type == 'PH'){
     		 site = $('#keyIssue').val();
     	 }     	
     	 var arrSite = new Array();
     	 $("input[name='chkSite']").each(function () {
     		if(this.checked){
     			arrSite.push($(this).val());
     		}
     	 });    
     	 var dateFrom = $('#idHieuLucTu').val();
     	 var mesFrom = '';
     	 if(dateFrom.trim() == ''){
     		mesFrom = 'Ngày có hiệu lực không được bỏ trống.'; 
     	 }else{
     		 if(!dateFrom.match(pattern)){
     			mesFrom = 'Ngày có hiệu lực sai định dạng.'; 
     		 }
     	 }
     	 if(mesFrom != ''){
     		$.notify(mesFrom, {
				globalPosition: 'bottom left',
				className: 'warn',
			});
     		return;
     	 }
     	 var dateTo = $('#idHieuLucDen').val();
     	 var mesTo = '';
     	 if(dateTo.trim() == ''){
     		mesTo = 'Ngày hết hiệu lực không được bỏ trống.';
    	 }else {
    		 if(!dateTo.match(pattern)){
      			mesFrom = 'Ngày hết hiệu lực sai định dạng.'; 
      		 }
    	 }
     	 if(mesTo != ''){
     		$.notify(mesTo, {
				globalPosition: 'bottom left',
				className: 'warn',
			});
     		return;
     	 }
	     var url = '${saveSiteStreamUrl}';	
	     $('#ajax-load-qa').show();
	   	 $.ajax({
	   		url : url,
	   		type: "POST",
	   		data: {
	   			site : site,
	   			type : type,
	   			dateFrom : dateFrom,
	   			dateTo : dateTo,
	   			arrSite : arrSite
	   		},
	   		success : function(data) {
	   			$('#ajax-load-qa').hide();
	   			if(data == '1'){
	   				$.notify('Lưu thay đổi thành công.', {
						globalPosition: 'bottom left',
						className: 'success',
					});
	   			}
	   			if(data == '0'){
	   				$.notify('Lưu thay đổi không thành công.', {
						globalPosition: 'bottom left',
						className: 'warn',
					});
	   			}
	   		},
	   		error : function(e){
	   			$('#ajax-load-qa').hide();
	   		}
	   	 });
    });
    
    
    $('#keySite, #keyPrint, #keyIssue').change(function(){
    	 var site = '';
    	 var type =	$('#keyType').val();
    	 if(type == 'XL'){
    		 site = $('#keySite').val();
    	 }else if(type == 'IN'){
    		 site = $('#keyPrint').val();
    	 }else if(type == 'PH'){
    		 site = $('#keyIssue').val();
    	 }
    	 var url = '${loadSiteStreamUrl}';
		 $.ajax({
			url : url,
			type: "POST",
			data: {
				site : site,
				type : type
			},
			success : function(data) {
				$('#idSiteChk').find('input[type=checkbox]').removeAttr('disabled');
				$("input[name='chkSite']").each(function () {
					$(this).prop("checked", false);
				});				
				$("input[name='chkSite']").each(function () {
					for(var i = 0; i < data.length; i++){
						if($(this).val() == data[i].siteId && data[i].stage == '0'){
							$(this).prop("disabled", true);
							break;
						}	
						if($(this).val() == data[i].siteId && data[i].stage == '1'){
							$(this).prop("checked", true);
							break;
						}	
					}
			    });
			},
			error : function(e){}
		 });
    });
    
	/*$('#idIN').change(function(){
	    	
	});
	
	$('#idPH').change(function(){
	 	
	});*/
    
   /* $("#idHieuLucTu").datepicker({	
		changeMonth : true,
		changeYear : true,
		showSecond : true,
		controlType : 'select',
		dateFormat : 'dd-M-yy',
		yearRange : "-100:+10"
	}).keyup(function(e) {
		
	});
	
	$('#idHieuLucTu').datepicker().datepicker('setDate', new Date());*/
	
	//$('#idHieuLucDen').datetimepicker();
	$("#idHieuLucTu").datetimepicker({format: 'dd/mm/yyyy hh:ii'});
	$("#idHieuLucDen").datetimepicker({format: 'dd/mm/yyyy hh:ii'});
	
	/*$("#idHieuLucDen").datepicker({		
		changeMonth : true,
		changeYear : true,
		showSecond : true,
		controlType : 'select',
		dateFormat : 'dd-M-yy',
		yearRange : "-100:+10"
	}).keyup(function(e) {
		
	});
	
	$('#idHieuLucDen').datepicker().datepicker('setDate', new Date());*/
});
</script>
	
</form:form>
</div>


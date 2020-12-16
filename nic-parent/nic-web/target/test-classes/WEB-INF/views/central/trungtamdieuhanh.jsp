<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

<c:url var="ttluutruUrl"
	value="/servlet/central/ttluutru" />
<c:url var="ttdieuhanhbngURL"
	value="/servlet/central/ttdieuhanhbng" />
<c:url var="ttcaURL"
	value="/servlet/central/ttca" />
<c:url var="ttxncURL"
	value="/servlet/central/ttchxnc" />
<c:url var="ttcmndURL"
	value="/servlet/central/ttcmnd" />
<c:url var="ttdancuURL"
	value="/servlet/central/ttdancu" />
<c:url var="showPersonalizedUrl" value="/servlet/central/personalized" />
<c:url var="showIssuanceUrl" value="/servlet/central/issuance" />
<c:url var="proccessDoc" value="/servlet/central/process" />
<c:url var="settingParam" value="/servlet/parameterController/getParamList" />
<c:url var="searchxnc" value="/servlet/central/searchxnc" />
<c:url var="sodohethong" value="/servlet/central/sodohethong" />
<%-- <form:form commandName="jobList" id="form" name="form" > --%>
<style>
<!--

-->
.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
    white-space: pre-wrap;
}
.cls-mg-bot {
	margin-top: 10px;
}

.display-2 {
	float: left;
}
.img-icon-s {
	padding: 10px;
    display: block;
    margin: auto;
    height: 80px;
    width: auto !important;
}
.font-header {
	font-size: 18px;
    font-weight: bold;
    margin-bottom: 10px;
}
</style>
<form:form modelAttribute="formData" name="formData" > 

		<!--Content start-->
<div class="form-desing">
   <div> 
	   <div>
		   <div class="row">
		   	   <div class="ov_hidden">
			   <div class="new-heading-2">TRUNG TÂM ĐIỀU HÀNH</div>
       
       		   <div class="col-md-12" style="margin-top: 30px;">
       		   		<div class="font-header">Trung tâm điều khiển</div>
       		   		<div class="slick-slide slick-current slick-active display-2" data-slick-index="0" aria-hidden="false" style="width: 165px;">
	       		   		<div>
	         				<div style="width: 100%; display: inline-block;">                                          
		                         <a href="${proccessDoc}" class="col-xs-12 none-padding" tabindex="3">
		                             <span class="col-xs-12 icon none-padding">
		                                 <div class="div-add">TT TIẾP NHẬN/XỬ LÝ</div>
		                                 <img class="img-icon-s" src="<c:url value="/resources/images/icon_welcome/tiep_nhan.png"/>" class="img-bg">
		                             </span>
		                         </a>                                         
	                     	</div>
	                   </div>
                   </div>
                   <div class="slick-slide slick-current slick-active display-2" data-slick-index="0" aria-hidden="false" style="width: 165px;">
	                   <div>
	         				<div style="width: 100%; display: inline-block;">                                          
		                         <a href="${showPersonalizedUrl}" class="col-xs-12 none-padding" tabindex="3">
		                             <span class="col-xs-12 icon none-padding">
		                                 <div class="div-add">CÁ THỂ HÓA</div>
		                                 <img class="img-icon-s" src="<c:url value="/resources/images/icon_welcome/tiep_nhan.png"/>" class="img-bg">
		                             </span>
		                         </a>                                         
	                     	</div>
	                   </div>
                   </div>
                   <div class="slick-slide slick-current slick-active display-2" data-slick-index="0" aria-hidden="false" style="width: 165px;">
	                   <div>
	         				<div style="width: 100%; display: inline-block;">                                          
		                         <a href="${showIssuanceUrl}" class="col-xs-12 none-padding" tabindex="3">
		                             <span class="col-xs-12 icon none-padding">
		                                 <div class="div-add">PHÁT HÀNH HỘ CHIẾU</div>
		                                 <img class="img-icon-s" src="<c:url value="/resources/images/icon_welcome/tiep_nhan.png"/>" class="img-bg">
		                             </span>
		                         </a>                                         
	                     	</div>
	                   </div>
                   </div>
                   <div class="slick-slide slick-current slick-active display-2" data-slick-index="0" aria-hidden="false" style="width: 165px;">
	                   <div>
	         				<div style="width: 100%; display: inline-block;">                                          
		                         <a href="#" class="col-xs-12 none-padding" tabindex="3">
		                             <span class="col-xs-12 icon none-padding">
		                                 <div class="div-add">BIỂU ĐỒ THỐNG KÊ</div>
		                                 <img class="img-icon-s" src="<c:url value="/resources/images/icon_welcome/tiep_nhan.png"/>" class="img-bg">
		                             </span>
		                         </a>                                         
	                     	</div>
	                   </div>
                   </div>
                   <div class="slick-slide slick-current slick-active display-2" data-slick-index="0" aria-hidden="false" style="width: 165px;">
	                   <div>
	         				<div style="width: 100%; display: inline-block;">                                          
		                         <a href="${sodohethong}" class="col-xs-12 none-padding" tabindex="3">
		                             <span class="col-xs-12 icon none-padding">
		                                 <div class="div-add">SƠ ĐỒ TRIỂN KHAI</div>
		                                 <img class="img-icon-s" src="<c:url value="/resources/images/icon_welcome/tiep_nhan.png"/>" class="img-bg">
		                             </span>
		                         </a>                                         
	                     	</div>
	                   </div>
                   </div>
                   <div class="slick-slide slick-current slick-active display-2" data-slick-index="0" aria-hidden="false" style="width: 165px;">
	                   <div>
	         				<div style="width: 100%; display: inline-block;">                                          
		                         <a href="${settingParam}" class="col-xs-12 none-padding" tabindex="3">
		                             <span class="col-xs-12 icon none-padding">
		                                 <div class="div-add">QUẢN TRỊ</div>
		                                 <img class="img-icon-s" src="<c:url value="/resources/images/icon_welcome/tiep_nhan.png"/>" class="img-bg">
		                             </span>
		                         </a>                                         
	                     	</div>
	                   </div>
                   </div>
       		   </div>
  			   <div class="col-md-12" style="margin-top: 30px;">
  			   		<div class="font-header">Giao tiếp nội bộ</div>
  			   		<div class="slick-slide slick-current slick-active display-2" data-slick-index="0" aria-hidden="false" style="width: 165px;">
	  			   		<div>
	         				<div style="width: 100%; display: inline-block;">                                          
		                         <a href="${ttluutruUrl}" class="col-xs-12 none-padding" tabindex="3">
		                             <span class="col-xs-12 icon none-padding">
		                                 <div class="div-add">TT LƯU TRỮ</div>
		                                 <img class="img-icon-s" src="<c:url value="/resources/images/icon_welcome/tiep_nhan.png"/>" class="img-bg">
		                             </span>
		                         </a>                                         
	                     	</div>
	                   </div>
                   </div>
                   <div class="slick-slide slick-current slick-active display-2" data-slick-index="0" aria-hidden="false" style="width: 165px;">
	                   <div>
	         				<div style="width: 100%; display: inline-block;">                                          
		                         <a href="${ttcaUrl}" class="col-xs-12 none-padding" tabindex="3">
		                             <span class="col-xs-12 icon none-padding">
		                                 <div class="div-add">TT CA</div>
		                                 <img class="img-icon-s" src="<c:url value="/resources/images/icon_welcome/tiep_nhan.png"/>" class="img-bg">
		                             </span>
		                         </a>                                         
	                     	</div>
	                   </div>
                   </div>
                   <div class="slick-slide slick-current slick-active display-2" data-slick-index="0" aria-hidden="false" style="width: 165px;">
	                   <div>
	         				<div style="width: 100%; display: inline-block;">                                          
		                         <a href="${searchxnc}" class="col-xs-12 none-padding" tabindex="3">
		                             <span class="col-xs-12 icon none-padding">
		                                 <div class="div-add">TT KIỂM SOÁT XNC</div>
		                                 <img class="img-icon-s" src="<c:url value="/resources/images/icon_welcome/tiep_nhan.png"/>" class="img-bg">
		                             </span>
		                         </a>                                         
	                     	</div>
	                   </div>
                   </div>
  			   </div>	
      		   <div class="col-md-12" style="margin-top: 30px;">
      		   		<div class="font-header">Giao tiếp với hệ thống ngoài</div>
      		   		<div class="slick-slide slick-current slick-active display-2" data-slick-index="0" aria-hidden="false" style="width: 165px;">
	      		   		<div>
	         				<div style="width: 100%; display: inline-block;">                                          
		                         <a href="${ttdieuhanhbngURL}" class="col-xs-12 none-padding" tabindex="3">
		                             <span class="col-xs-12 icon none-padding">
		                                 <div class="div-add">TT ĐH BỘ NGOẠI GIAO</div>
		                                 <img class="img-icon-s" src="<c:url value="/resources/images/icon_welcome/tiep_nhan.png"/>" class="img-bg">
		                             </span>
		                         </a>                                         
	                     	</div>
	                   </div>
                   </div>
                   <div class="slick-slide slick-current slick-active display-2" data-slick-index="0" aria-hidden="false" style="width: 165px;">
                   <div>
         				<div style="width: 100%; display: inline-block;">                                          
	                         <a href="${ttchxncUrl}" class="col-xs-12 none-padding" tabindex="3">
	                             <span class="col-xs-12 icon none-padding">
	                                 <div class="div-add">TT CHỈ HUY XNC BĐ BP</div>
	                                 <img class="img-icon-s" src="<c:url value="/resources/images/icon_welcome/tiep_nhan.png"/>" class="img-bg">
	                             </span>
	                         </a>                                         
                     	</div>
                   </div>
                   </div>
                   <div class="slick-slide slick-current slick-active display-2" data-slick-index="0" aria-hidden="false" style="width: 165px;">
                   <div>
         				<div style="width: 100%; display: inline-block;">                                          
	                         <a href="${ttcmndUrl}" class="col-xs-12 none-padding" tabindex="3">
	                             <span class="col-xs-12 icon none-padding">
	                                 <div class="div-add">CMND/CCCD</div>
	                                 <img class="img-icon-s" src="<c:url value="/resources/images/icon_welcome/tiep_nhan.png"/>" class="img-bg">
	                             </span>
	                         </a>                                         
                     	</div>
                   </div>
                   </div>
                   <div class="slick-slide slick-current slick-active display-2" data-slick-index="0" aria-hidden="false" style="width: 165px;">
                   <div>
         				<div style="width: 100%; display: inline-block;">                                          
	                         <a href="${ttdancuUrl}" class="col-xs-12 none-padding" tabindex="3">
	                             <span class="col-xs-12 icon none-padding">
	                                 <div class="div-add">DÂN CƯ</div>
	                                 <img class="img-icon-s" src="<c:url value="/resources/images/icon_welcome/tiep_nhan.png"/>" class="img-bg">
	                             </span>
	                         </a>                                         
                     	</div>
                   </div>
                   </div>
      		   </div>	

			   </div>
		   </div>
<div id="dialog-confirm"></div>
<script type="text/javascript">
	var totalPages = ${totalPage};
	var currentPage = ${pageNo};
	var pageSize = ${pageSize};
	window.pagObj = $('#pagination').twbsPagination({
				totalPages: totalPages,
				visiblePages: pageSize,
				startPage: currentPage,
				next: '<span class=\"glyphicon glyphicon-forward fix-paging\"></span>',
				prev: '<span class=\"glyphicon glyphicon-backward fix-paging\"></span>',
				last: '<span class=\"glyphicon glyphicon-step-forward fix-paging\"></span>',
				first: '<span class=\"glyphicon glyphicon-step-backward fix-paging\"></span>',
				onPageClick: function (event, page) {
					if (currentPage != page) {
						$('#pageNo').val(page);
						document.forms["formData"].action = '${invesProcessUrl}';  
						document.forms["formData"].submit();  
					}
				}
			});
	function doSubmitSave(form) {
		form.action = '<c:url value="/servlet/investigation/search" />';
		form.submit();
	}
</script>
	
</div>
</div>
</div>
</form:form>



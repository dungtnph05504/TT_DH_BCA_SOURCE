<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/HomePage.css"/>"></link>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/slick.css"/>"></link>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/slick-theme.css"/>"></link>
<script type="text/javascript" src="<c:url value="/resources/js/owl.carousel.js"/>"></script> 
<script type="text/javascript" src="<c:url value="/resources/js/slick.js"/>"></script> 
<script type="text/javascript" src="<c:url value="/resources/js/custom.js"/>"></script> 
<style>
body {
	background: url('<c:url value="/resources/images/bg_home.png" />') no-repeat;
}
.img-icon-s{
	width: 80px !important;
    height: 80px !important;
    margin: auto;
}
.display-1, .display-2, .display-3, .display-4, .display-5 {
	display: block;
}
.display-6  {
	display: none;
}
.main-footer{
display: none;
}
</style>
<script type="text/javascript">
var item = 6;
$(document).ready(function() {
	$('#clkNext').click(function(){
		item--;
		if(item < 1){
			item = 6;
		}
		for(var i = 1; i < 7; i++){
			if(i != item){
				$('.display-' + i).css('display', 'block');				
			}
		}
		$('.display-' + item).css('display', 'none');
	});
	$('#clkBack').click(function(){
		item++;
		if(item > 6){
			item = 1;
		}
		for(var i = 1; i < 7; i++){
			if(i != item){
				$('.display-' + i).css('display', 'block');				
			}
		}
		
		$('.display-' + item).css('display', 'none');
	});
});

</script>
<div id="global_error_div"></div>

<form:form method="GET" cssClass="inline">
<div class="ov_hidden">
	    	<div class="col-sm-12 none-padding bg-content container" style="height: 612px;">
                <div class="container none-padding">
                    <div class="col-xs-12 none-padding  ">
                        <div class="col-xs-12 none-padding bg-map " style="height: 437px;">
                            <img src="<c:url value="/resources/images/bando-02.png" />" class="img-bg">
                        </div>
                    </div>
                   <div class="container main clearfix none-padding">
                        <div class="col-md-12 bg-menu">
                            <div class="center slider  slick-initialized slick-slider slick-no-slide"><div class="slick-list draggable"><div class="slick-track" style="opacity: 1; width: 187px; transform: translate3d(0px, 0px, 0px);">
                           		 <div class="slick-slide slick-current slick-active display-1" data-slick-index="0" aria-hidden="false" style="width: 165px;">
	                            	<div>
	                            		<div style="width: 100%; display: inline-block;">
	
	                                            <a href="#" class="col-xs-12 none-padding" tabindex="1">
	                                                <span class="col-xs-12 icon none-padding">
	                                                    <div class="div-add">PHÊ DUYỆT</div>
	                                                    <img class="img-icon-s" src="<c:url value="/resources/images/icon_welcome/tiep_nhan.png" />" class="img-bg">
	                                                </span>
	                                            </a>
	                                        </div>
	                                      </div>
                                    </div>
                                    <div class="slick-slide slick-current slick-active display-2" data-slick-index="0" aria-hidden="false" style="width: 165px;">
                                    	<div>
                            				<div style="width: 100%; display: inline-block;">                                         
	                                            <a href="#" class="col-xs-12 none-padding" tabindex="2">
	                                                <span class="col-xs-12 icon none-padding">
	                                                    <div class="div-add">ĐIỀU TRA</div>
	                                                    <img class="img-icon-s" src="<c:url value="/resources/images/icon_welcome/tiep_nhan.png" />" class="img-bg">
	                                                </span>
	                                            </a>                                           
                                        	</div>
                                      </div>
                                    </div>
                                    <div class="slick-slide slick-current slick-active display-3" data-slick-index="0" aria-hidden="false" style="width: 165px;">
                                    	  <div>
	                            				<div style="width: 100%; display: inline-block;">                                          
	                                            <a href="#" class="col-xs-12 none-padding" tabindex="3">
	                                                <span class="col-xs-12 icon none-padding">
	                                                    <div class="div-add">BÁO CÁO</div>
	                                                    <img class="img-icon-s" src="<c:url value="/resources/images/icon_welcome/tiep_nhan.png"/>" class="img-bg">
	                                                </span>
	                                            </a>                                         
	                                        </div>
	                                      </div>
                                    </div>
                                    <div class="slick-slide slick-current slick-active display-4" data-slick-index="0" aria-hidden="false" style="width: 165px;">
                                    	<div>
                            				<div style="width: 100%; display: inline-block;">

                                           
                                            <a href="#" class="col-xs-12 none-padding" tabindex="4">
                                                <span class="col-xs-12 icon none-padding">
                                                    <div class="div-add">LÀM MỚI</div>
                                                    <img class="img-icon-s" src="<c:url value="/resources/images/icon_welcome/tiep_nhan.png"/>" class="img-bg">
                                                </span>
                                            </a>
                                          
                                        </div>
                                      </div>
                                    </div>
                                    <div class="slick-slide slick-current slick-active display-5" data-slick-index="0" aria-hidden="false" style="width: 165px;">
                                    	<div>
                            				<div style="width: 100%; display: inline-block;">

                                            
                                            <a href="#" class="col-xs-12 none-padding" tabindex="5">
                                                <span class="col-xs-12 icon none-padding">
                                                    <div class="div-add">THAY THẾ </div>
                                                    <img class="img-icon-s" src="<c:url value="/resources/images/icon_welcome/tiep_nhan.png"/>" class="img-bg">
                                                </span>
                                            </a>
                                         
                                        </div>
                                      </div>
                                    </div>
                                    <div class="slick-slide slick-current slick-active display-6" data-slick-index="0" aria-hidden="false" style="width: 165px;">
                                    	<div>
                            				<div style="width: 100%; display: inline-block;">

                                            <a href="#" class="col-xs-12 none-padding" tabindex="6">
                                                <span class="col-xs-12 icon none-padding">
                                                    <div class="div-add">MẤT HỎNG</div>
                                                    <img class="img-icon-s" src="<c:url value="/resources/images/icon_welcome/tiep_nhan.png"/>" class="img-bg">
                                                </span>
                                            </a>
                                         
                                        </div>
                                      </div>
                                    </div>
                                  </div></div></div>
                            <div class="col-md-12 none-padding button-menu">
                                <span class="left"><a href="#"><img class="img-hidden" id="clkBack" src="<c:url value="/resources/images/pre.png" />"></a></span>
                                <span class="right"><a href="#"><img class="img-hidden" id="clkNext" src="<c:url value="/resources/images/next.png" />"></a></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
         </div>
<!--<div class="container">
          <div class="row">
                            <div class="roundedBorder ov_hidden bg_images_hihi">
                            <div class="left_pane m_top_15">                   
                    			<img src="<c:url value="/resources/images/icon_welcome/vietnam.png"/>" alt="register">
                			</div>
                            <div class="right_pane">
                                <div class="col-md-4 m_top_15">
                                    <div class="icon_button">
                                        <div class="img_title"><a href="#"><img src="<c:url value="/resources/images/icon_welcome/note.png"/>" alt="register"></a></div>
                                        <div class="img_title2"><a href="#"><img src="<c:url value="/resources/images/icon_welcome/note_green.png"/>" alt="register"></a></div>
                                        <div class="name_title"><h2><a href="#">Phê duyệt</a></h2></div>
                                    </div>
                                </div>
                                <div class="col-md-4 m_top_15">
                                    <div class="icon_button">                                       
                                        <div class="img_title"><a href="#"><img src="<c:url value="/resources/images/icon_welcome/verification.png"/>" alt="register"></a></div>
                                        <div class="img_title2"><a href="#"><img src="<c:url value="/resources/images/icon_welcome/verification-green.png"/>" alt="register"></a></div>
                                        <div class="name_title"><h2><a href="#">Điều tra</a></h2></div>
                                    </div>
                                </div>
                                <div class="col-md-4 m_top_15">
                                    <div class="icon_button">
                                        <div class="img_title"><a href="#"><img src="<c:url value="/resources/images/icon_welcome/passport.png"/>" alt="register"></a></div>
                                        <div class="img_title2"><a href="#"><img src="<c:url value="/resources/images/icon_welcome/passport-green.png"/>" alt="register"></a></div>
                                        <div class="name_title"><h2><a href="#">Báo cáo</a></h2></div>
                                    </div>
                                </div>
                                <div class="col-md-4 m_top_15">
                                    <div class="icon_button">
                                        <div class="img_title"><a href="#"><img src="<c:url value="/resources/images/icon_welcome/warning.png"/>" alt="register"></a></div>
                                        <div class="img_title2"><a href="#"><img src="<c:url value="/resources/images/icon_welcome/warning-green.png"/>" alt="register"></a></div>
                                        <div class="name_title"><h2><a href="#">Làm mới</a></h2></div>
                                    </div>
                                </div>
                                <div class="col-md-4 m_top_15">
                                    <div class="icon_button">
                                        <div class="img_title"><a href="#"><img src="<c:url value="/resources/images/icon_welcome/money.png"/>" alt="register"></a></div>
                                        <div class="img_title2"><a href="#"><img src="<c:url value="/resources/images/icon_welcome/money-green.png"/>" alt="register"></a></div>
                                        <div class="name_title"><h2><a href="#">Thay thế</a></h2></div>
                                    </div>
                                </div>
                                <div class="col-md-4 m_top_15">
                                    <div class="icon_button">
                                        <div class="img_title"><a href="#"><img src="<c:url value="/resources/images/icon_welcome/hospital-history.png"/>" alt="register"></a></div>
                                        <div class="img_title2"><a href="#"><img src="<c:url value="/resources/images/icon_welcome/hospital-history-green.png"/>" alt="register"></a></div>
                                        <div class="name_title"><h2><a href="#">Mất hỏng</a></h2></div>
                                    </div>
                                </div>
                                </div>
                            </div>
                        </div>
                    </div>-->
</form:form>
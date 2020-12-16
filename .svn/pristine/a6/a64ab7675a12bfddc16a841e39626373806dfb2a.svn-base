<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

<c:url var="turnTTluutru"
	value="/servlet/central/turnttluutru" />
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

#bieuDoCaTheHoa {
	width: 100%;
	height: 100%;
	margin: 0 auto
}
</style>
<form:form modelAttribute="formData" name="formData" > 

<div class="content-person-item container-fluid" style="min-height:500px;">
            <div class="content-item-title">
                <div class="oplog-title__txt">TRUNG TÂM LƯU TRỮ</div>
            </div>
            <div class="content-item-main">
                <div class="form-group">
                    <div class="col-sm-12 pad-top-10 none-pad-left none-pad-right">
                        <fieldset class="scheduler-border none-pad-right" style="border:0px !important">
                            <legend class="scheduler-border"><label class="bold-label" style="font-size:18px;">Kết nối:</label></legend>
                            <div class="form-group" style="height:390px;width:100%;">
                                <div class="col-sm-12" style="overflow: auto;">
                                    
                                	<table class="table e-grid-table table-bordered table-hover e-table-fix-head">
                                        <thead class="table-thead">
                                            <tr>
                                                <th class="e-cursor-pointer" style="background-color: rgb(230, 230, 230); position: relative; top: -0.7px;"><div class="e-th-table-boder text-center">STT</div></th>
                                                <th class="e-cursor-pointer" style="background-color: rgb(230, 230, 230); position: relative; top: -0.7px;"><div class="e-th-table-boder text-center">Tên kết nối </div></th>
                                                <th class="e-cursor-pointer" style="width: 90px; background-color: rgb(230, 230, 230); position: relative; top: -0.7px;"><div class="e-th-table-boder text-center">Tình trạng </div></th>
                                            </tr>
                                        </thead>
                                        <tbody class="table-body">
                                            <tr class="trBody">
                                                <td class="text-center">${sttMain}</td>
                                                <td class="text-left"><a href="#"><span>${nameMain}</span></a></td>
                                                <td class="text-left">
                                                	<c:choose>
								                   	 	<c:when test="${not valueMain}">
								                           <a href="#" onclick="turnttluutru()"><img style="width:30px;margin-left:20px;" src="/eppcentral/resources/style1/images/power-off-icon.jpg"></a> 
								                    	</c:when>
								                    	<c:otherwise>
								                           <a href="#" onclick="turnttluutru()"><img style="width:30px;margin-left:20px;" src="/eppcentral/resources/style1/images/power-on-icon.jpg"></a>										                           
								                    	</c:otherwise>
							               		 	</c:choose>	
                                                </td>
                                            </tr>
                                            <tr class="trBody">
                                                <td class="text-center">${sttSub}</td>
                                                <td class="text-left"><a href="#"><span>${nameSub}</span></a></td>
                                                <td class="text-left">
                                                <c:choose>
								                    <c:when test="${not valueSub}">
								                           <a href="#" onclick="turnttluutru()"><img style="width:30px;margin-left:20px;" src="/eppcentral/resources/style1/images/power-off-icon.jpg"></a>
								                    </c:when>
								                    <c:otherwise>
								                            <a href="#" onclick="turnttluutru()"><img style="width:30px;margin-left:20px;" src="/eppcentral/resources/style1/images/power-on-icon.jpg"></a>										                           
								                    </c:otherwise>
							               		 </c:choose>	
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
            </div>
        </div>
</form:form>

<script type="text/javascript">

function turnttluutru() {
	
	document.forms["formData"].action = '${turnTTluutru}';  
	document.forms["formData"].submit();
}

$(document).ready(function() {
	 var thanhCong = '${thanhcong}';
	    var loiPhanCong = '${loi}';
	    if(thanhCong != ''){
	    	$.notify(thanhCong, {
				globalPosition: 'bottom left',
				className: 'success',
			});
	    }
		if(loiPhanCong != ''){
			$.notify(loiPhanCong, {
				globalPosition: 'bottom left',
				className: 'error',
			});
	    }
})
</script>

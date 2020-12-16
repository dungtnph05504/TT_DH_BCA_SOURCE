<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

<c:url var="ttdieuhanhbngURL"
	value="/servlet/central/turnttdieuhanhbng" />
<c:url var="detailURL"
	value="/servlet/central/detailAPI" />
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
                <div class="oplog-title__txt">TRUNG TÂM ĐIỀU HÀNH BỘ NGOẠI GIAO</div>
            </div>
            <div class="content-item-main">
                <div class="form-group">
                    <div class="col-sm-12 pad-top-10 none-pad-left none-pad-right">
                        <fieldset class="scheduler-border none-pad-right" style="border:0px !important">
                            <legend class="scheduler-border"><label class="bold-label" style="font-size:18px;">Kết nối:</label></legend>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-12 control-label">Thông tin kết nối</label>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Loại kết nối:</label>
                                    <label class="col-sm-10 control-label">${mainTypeAPI}</label>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">URI/URL:</label>
                                    <label class="col-sm-10 control-label">${mainUrlAPI}</label>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Trạng thái:</label>
                                    <div class="col-sm-10">
                                        <!-- <img style="width:30px;" src="/Contents/Images/power-on-icon.jpg"> -->
                                         <c:choose>
											<c:when test="${not mainStatusAPI}">
												<a href="#" onclick="turnttdieuhanh('WS_MAIN')"><img
													style="width: 30px; margin-left: 20px;"
													src="/eppcentral/resources/style1/images/power-off-icon.jpg"></a>
											</c:when>
											<c:otherwise>
												<a href="#" onclick="turnttdieuhanh('WS_MAIN')"><img
													style="width: 30px; margin-left: 20px;"
													src="/eppcentral/resources/style1/images/power-on-icon.jpg"></a>
											</c:otherwise>
										</c:choose>	
                                    </div>

                                </div>
                            </div>
                            <br>
                            <br>
                            <legend class="scheduler-border"><label class="bold-label" style="font-size:18px; margin-top: 10px;">Danh sách dịch vụ:</label></legend>
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
                                        	 <c:forEach items="${dsApi}" var="item">
                                        	  	<tr>
                                                <tr class="trBody" ng-repeat="item in ListConnect">
                                                <td class="text-center">${item.stt}</td>
                                                <td class="text-left"><a href="#" onclick="detailAPI('${item.paraName}')"><span>${item.description}</span></a></td>
                                                <td class="text-left">
                                                   <c:choose>
														<c:when test="${not item.isOpen}">
															<a href="#" onclick="turnttdieuhanh('${item.paraName}')"><img
																style="width: 30px; margin-left: 20px;"
																src="/eppcentral/resources/style1/images/power-off-icon.jpg"></a>
														</c:when>
														<c:otherwise>
															<a href="#" onclick="turnttdieuhanh('${item.paraName}')"><img
																style="width: 30px; margin-left: 20px;"
																src="/eppcentral/resources/style1/images/power-on-icon.jpg"></a>
														</c:otherwise>
													</c:choose>	
                                                </td>
                                         	</c:forEach>
                                            </tr><!-- end ngRepeat: item in ListConnect -->
                                        </tbody>
                                    </table></div>
                            	</div>
                        </fieldset>
                    </div>
                </div>
            </div>
        </div>
</form:form>

<script type="text/javascript">

function turnttdieuhanh(p) {
	
	document.forms["formData"].action = '${ttdieuhanhbngURL}/' + p;  
	document.forms["formData"].submit();
}

function detailAPI(p) {
	
	document.forms["formData"].action = '${detailURL}/' + p;  
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

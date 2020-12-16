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
            <div class="oplog-title__txt">DÂN CƯ</div>
        </div>
        <div class="content-item-main">
            <div class="form-group">
                <div class="col-sm-12 pad-top-10 none-pad-left none-pad-right">
                    <fieldset class="scheduler-border none-pad-right" style="border:0px !important">
                        <legend class="scheduler-border"><label class="bold-label" style="font-size:18px;">Chờ kết nối</label></legend>
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

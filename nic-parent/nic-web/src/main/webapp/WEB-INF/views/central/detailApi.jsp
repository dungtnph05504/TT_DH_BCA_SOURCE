<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

<c:url var="testDataUrl"
	value="/servlet/central/testDataApi" />
<c:url var="ttdieuhanhbngURL"
	value="/servlet/central/ttdieuhanhbng" />
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
                    <div class="col-sm-3 pad-top-10 none-pad-left none-pad-right">

                    </div>
                    <div class="col-sm-6 pad-top-10 none-pad-left none-pad-right">
                        <fieldset class="scheduler-border none-pad-right" style="border:0px !important">
                            <legend class="scheduler-border"><label class="bold-label" style="font-size:18px;">Cập nhật kết quả xử lý của tuyến sau</label></legend>
                           
                            <div class="form-group">
                                <label class="col-sm-12 control-label">Thông tin kết nối</label>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Loại kết nối:</label>
                                <label class="col-sm-10 control-label">${masterAPI}</label>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">URI/URL:</label>
                                <label class="col-sm-10 control-label">${hrefAPI}</label>
                                <input id="typeAPI" value="${typeAPI}" type="text" hidden="hidden">
                                <input id="hrefAPI" value="${hrefAPI}" type="text" hidden="hidden">
                                 <input id="paraName" value="${paraName}" type="text" hidden="hidden">
                            </div>
                            <div class="form-group">
                                <label class="col-sm-12 control-label">Tham số đầu vào</label>
                            </div>
                            <div class="form-group">
                                    <label class="col-sm-2">Token</label><!-- <span style="color:red">*</span> -->
                                    <div class="col-sm-10">
                                        <input class="form-control" ng-model="Token" type="text">
                                    </div>
                            </div>
                            <div class="form-group">
                                    <label class="col-sm-2">Tham số</label><!-- <span style="color:red">*</span> -->
                                    <div class="col-sm-10">
                                        <input id="inputData" class="form-control" type="text">
                                    </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-12 control-label"></label>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2">Kết quả</label>
                                <div class="col-sm-10">
                                    <textarea id="resultData" class="form-control ng-binding" rows="10"></textarea>

                                </div>
                            </div>
                        </fieldset>
                    </div>
                    <div class="col-sm-3 pad-top-10 none-pad-left none-pad-right">
                    </div>
                </div>
            </div>
        </div>
        <div style="display: flex;flex: 0 auto;justify-content: center;">
			<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
				<div style="margin: 10px"> 
					<button type="button" onclick="processAPI();" name="approve" class="btn btn-success">
						   Truy vấn
					</button>
					<button type="button" onclick="backPage();" name="back" class="btn btn-success">
						   Quay lại
					</button>
				</div>
			</div>
		</div>
</form:form>

<script type="text/javascript">

function backPage() {
	
	document.forms["formData"].action = '${ttdieuhanhbngURL}';  
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

function processAPI(){
	var type = $('#typeAPI').val();
	var href = $('#hrefAPI').val();
	var input = $('#inputData').val();
	var param = $('#paraName').val();
	
	var data = { "typeAPI": type, "href": href, "input": input, "param": param}
	var url = '${testDataUrl}';
	$.ajax({
		url : url,
		data: JSON.stringify(data),
		contentType : 'application/json',
		type: 'POST',
		success : function(data) {
			$('#resultData').append(data);	
		},
		error : function(e) {
	        console.log("error: " + e);
	    }
	});
}
</script>

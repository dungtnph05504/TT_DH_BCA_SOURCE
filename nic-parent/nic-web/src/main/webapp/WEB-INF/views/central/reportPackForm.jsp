<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<style>
.cls-div {
	font-size:14px;
	border: solid 1px #000000; 
	margin: 20px auto; 
	box-shadow: 10px 10px #888888; 
	padding:10px;
	height:auto;
}
.tableReportLDS tr:hover {
    background-color: white;
}
td{
    font-size:14px;
}
div#theDeXuat * {
    font-size: 14px !important;
    font-family: 'Times New Roman';
}
</style>
<c:url var="urlSaveLog" value="/servlet/central/saveActionLog" />
<div class="cls-div" id="theDeXuat">

    <div style="font-weight:bold;width:100%;text-align:center;font-size:16px;">BÁO CÁO TIẾN TRÌNH ĐÓNG GÓI LDS</div>
    <div>Từ ngày: <span style="font-weight:bold;" id="fromDate">${fromDate}</span></div>
    <div>Đến ngày: <span style="font-weight:bold;" id="toDate">${toDate}</span></div>
    <c:if test="${not empty regSiteCode}">
    <div>Cơ quan đăng ký: <span style="font-weight:bold;" id="regSiteCode">${regSiteCode}</span></div>
    </c:if>
    <c:if test="${not empty printSiteCode}">
    <div>Cơ quan in: <span style="font-weight:bold;" id="printSiteCode">${printSiteCode}</span></div>
    </c:if>
    <br>
    <div style="width:100%;">	
						      	<table id="dtBasicExample" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="width : 45px;">STT
								      </th>
								      <th class="th-sm" style="width : 145px;">Số hồ sơ
								      </th>
								      <th class="th-sm" style="width : 100px;">Số hộ chiếu
								      </th>
								      <th class="th-sm" style="width : 170px;">Họ và tên
								      </th>
								      <th class="th-sm" style="width : 90px;">Ngày sinh
								      </th>
								      <th class="th-sm" style="width : 80px;">Giới tính
								      </th>
								      <th class="th-sm" style="width : 125px;">Số CMND/CCCD
								      </th>
								      <th class="th-sm" style="width : 160px;">Trạng thái hộ chiếu
								      </th>
								      <c:if test="${empty regSiteCode}">
								      <th class="th-sm" style="width: 190px;">Cơ quan đăng ký
								      </th>
								      </c:if>
								      <c:if test="${empty printSiteCode}">
								      <th class="th-sm" style="width: 260px;">Trung tâm in
								      </th>
								      </c:if>
								      <th class="th-sm" style="width: 85px;">Ngày in
								      </th>
								    </tr>
								  </thead>
								   <tbody>
									    <c:forEach items="${listTransaction}" var="item">
										    <tr>										      
										     	<td align="center">${item.stt}</td>
										      	<td align="center">${item.transactionId}</td>
										      	<td align="center">${item.passportNo}</td>
										      	<td align="left">${item.name}</td>
										      	<td align="center">${item.dateOfBirth}</td>
										      	<td align="center">${item.gender}</td>
										      	<td align="center">${item.nin}</td>
										      	<td align="left">${item.documentStatus}</td>
										      	<c:if test="${empty regSiteCode}">
										      	<td align="center">${item.regSiteName}</td>
										      	</c:if>
										      	<c:if test="${empty printSiteCode}">
										      	<td align="center">${item.printSiteName}</td>
										      	</c:if>
										      	<td align="center">${item.printDate}</td>
										    </tr>
									    </c:forEach>
								   </tbody>
								    
								</table>
        <table class="tableReportLDS" style="width:100%;margin-top:10px;margin-bottom:10px;border-collapse:separate;">
            <thead>
                <tr>
                    <td></td>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td style="height:192px;">
                        <div style="width:100%;float:left;padding-top:10px;">
                            <div style="width:50%;float:left;">
                                <div style="text-align:center;font-weight:bold;">LÃNH ĐẠO</div>
                                <div style="height:50px;width:100%;"></div>
                                <div style="text-align:center;font-weight:bold;">${lanhDao}</div>
                            </div>
                            <div style="width:50%;float:left;">
                                <div style="text-align:center;font-weight:bold;">CÁN BỘ IN</div>
                                <div style="height:50px;width:100%;"></div>
                                <div style="text-align:center;font-weight:bold;">${printer}</div>
                            </div>
                        </div>
                        <div style="padding-left:15px;padding-top:10px;width:100%;float:left;">Tổng cộng: ${totalRecord} hồ sơ hộ chiếu.</div>
                        <div style="padding-left:15px;padding-top:10px;width:100%;float:left;">Nhận lúc ..... giờ ..... ngày ..... / ..... / 20...</div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</div>			      
		
<%
	/* ******************************************************************************************************************** */
%>
<%
	/* ************************************************ apply filter - start ********************************************** */
%>
<%
	/* ******************************************************************************************************************** */
%>

<script type="text/javascript">
$(function(){
	
	
})	
function printRp(){
	
	var value = {};
	value['fromDate'] = $('#fromDate').val();
	value['toDate'] = $('#toDate').val();
	value['regSiteCode'] = $('#regSiteCode').val();
	value['printSiteCode'] = $('#printSiteCode').val();
	value['functionName'] = "Báo cáo tiến trình đóng gói";
	var url = '${urlSaveLog}';
	$('#ajax-load-qa').show();
	$('#ajax-load-qa').css('display', 'block');
   	$.ajax({
   		url : url,
   		type: "POST",
   		cache: false,
   		contentType : 'application/json',
   		data : JSON.stringify(value),
   		success : function(data) {
  
   		},
   		error : function(e){
   			$('#ajax-load-qa').hide();
   		}
   	});
	
	var mywindow = window.open('', '', 'height=800,width=1400');

    // mywindow.document.write('<html><head><title>' + document.title  + '</title>');
    mywindow.document.write('<html><body>');
    // mywindow.document.write('<h1>' + document.title  + '</h1>');
    mywindow.document.write(document.getElementById("theDeXuat").innerHTML);
    mywindow.document.write('</body></html>');

    mywindow.document.close(); // necessary for IE >= 10
    mywindow.focus(); // necessary for IE >= 10*/

    mywindow.print();
    //mywindow.close();

    return true;
}
		
</script>

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
div#theDeXuatK * {
    font-size: 14px !important;
    font-family: 'Times New Roman';
}
</style>

<div class="cls-div" id="theDeXuatK">
    <div style="padding:5px 5px 3px 5px; border: 1px solid #000; text-align:center; float:right; width:100px">
                Mẫu DS-03
            </div>
            <table class="tableReportLDS" cellpadding="20" cellspacing="0" border="0" style="width:100%">
                <tbody><tr>
                    <td style="width:35%; text-align:center">
                        TỔNG CỤC AN NINH  <br>
                        <b> CỤC QUẢN LÝ XNC </b> <br>
                        <hr style="padding:0; margin:0 auto; height:0; background-color:#000; border:0; border-bottom:1px solid #000; width:135px; position:relative; left:-3px">
                        <p style="padding:0; margin:0 auto; position:relative; top:10px;"> Số: ....... / TB-CX(P5) </p>
                    </td>
                    <td style="width:65%; text-align:center;">
                        <p style="padding:0; margin:0 auto; letter-spacing:2px"> CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM </p>
                        <b>Độc lập - Tự do - Hạnh phúc </b> <br>
                        <hr style="padding:0; margin:0 auto; height:0; background-color:#000; border:0; border-bottom:1px solid #000; width:190px; position:relative; left:-2px">
                        <i style="padding-left:70px; position:relative; top:10px ; font-size:14px;font-style: italic;">${entityOffer.noiXL}, ngày ${entityOffer.ngayHienTai} tháng ${entityOffer.thangHienTai} năm ${entityOffer.namHienTai} </i>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" style="width:100%; text-align:center;margin:0 auto">
                        <p style="text-align:center;font-weight: bold;"><br> Kính gửi: ${entityOffer.noiXL}</p>
                        <b style="line-height:2; font-size:18px"> DANH SÁCH </b><br>
                        <span>
                            Công dân Việt Nam chưa được Cục quản lý xuất nhập cảnh cấp hộ chiếu.
                        </span>
                    </td>
                </tr>
            </tbody></table>
            <table class="tableReportLDS" cellpadding="10" cellspacing="0" style="width:100%; margin-top:15px;border:1px solid #000;">
                <tbody><tr>
                    <th style="width:7px; border-right:1px solid #000; border-bottom:1px solid #000;padding:2px 2px 2px 2px;"><b>STT</b></th>
                    <th style="width:150px;  border-bottom:1px solid #000;padding:2px 2px 2px 5px;"><b>DS PA08</b></th>
                    <th style="border-bottom:1px solid #000">
                        <div style="width:30%; text-align:left; padding:2px 2px 2px 2px; margin:0; float:left"> <b>Số biên nhận </b></div>
                        <div style="text-align:center; padding:2px 2px 2px 2px; margin:0 auto; float:left"><b> Thông tin cá nhân </b></div>
                    </th>
                </tr>
                
                <c:forEach items="${listOffer}" var="entity">
                <tr>
                    <td style="text-align:center; border-right:1px solid #000;border-bottom:1px solid #000;">${entity.stt}</td>
                    <td style="text-align:center;border-bottom:1px solid #000;" valign="top">
                        <span style="font-weight:bold;">${entity.soDanhSach}</span>  <br>
                        <div>
                           	<div id="dialog-image_photoCompare" style="display: block;width: 90px;height: 130px;">
								<div class="centerCaption">       
							         <div style="text-align:center;" id="anhMatXNC">	
							 							             									                 
										    <c:choose>
							                    <c:when test="${not empty entity.anhMat}">
							                            <img src="data:image/jpg;base64,${entity.anhMat}"
							                                 class="img-border doGetAJpgSafeVersion" height="130px" width="90px" />
							                    </c:when>
							                    <c:otherwise>
							                      
							                            <img src="<c:url value='/resources/images/No_Image.jpg' />" class="img-border" height="130px" width="90px" />										                           
							                    </c:otherwise>
							                </c:choose>									       					                       										                  								                    									               
							         </div>								        
							    </div>
						    </div>
                       </div>
                    </td>
                    <td valign="top" style="border-bottom:1px solid #000;padding-bottom:7px;">
                        <span style="font-weight:bold;">${entity.soBn}</span>  <br>
                        <table cellpadding="0" cellspacing="0" style="line-height:2;width:100%;">
                            <tbody><tr>
                                <td style="width:60%;">1. Họ và tên: <span style="font-weight:bold;" >${entity.hoTen}</span> </td>
                                <td style="width:40%;">2. Giới tính: <span style="font-weight:bold;" >${entity.gioiTinh}</span></td>
                            </tr>
                            <tr>
                                <td style="width:60%;">3. Ngày sinh: <span style="font-weight:bold;" >${entity.ngaySinh}</span></td>
                                <td style="width:40%;">4. Nơi sinh: <span style="font-weight:bold;" >${entity.noiSinh}</span></td>
                            </tr>
                            <tr>
                                <td colspan="2">5. Số CMND/CCCD: <span style="font-weight:bold;">${entity.soCMND}</span></td>
                            </tr>
                            <tr>
                                <td colspan="2">6. Đ/chỉ thường trú: ${entity.diaChi}</td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <div style="white-space: normal;">7. Lý do chưa cấp hộ chiếu: <p style="white-space:pre-line;">${entity.noiDungDeXuat}</p></div>
                                     <br>
                                </td>
                            </tr>
                        </tbody></table>
                    </td>
                </tr>
                </c:forEach>
            </tbody></table>
            <p>
                Tổng cộng <b> ${entityOffer.soLuongHS} </b>  hồ sơ, <b> ${entityOffer.soNguoi} </b>  người
            </p>
            <table class="tableReportLDS" cellpadding="20" cellspacing="0" border="0" style="width:100%">
                <tbody><tr>
                    <td style="width:40%; text-align:center;vertical-align:top">
                        <p style="padding:0; margin:0 auto; letter-spacing:2px; font-weight:bold;"><b> Cán bộ đề xuất </b></p><br>
                        <p style="padding-top:50px; font-family:&#39;Times New Roman&#39;, Arial; font-size:12pt;font-weight:bold;"> ${entityOffer.canBo}  </p>
                    </td>
                    <td style="width:60%; text-align:center;">
                        <p style="padding:0; margin:0 auto; letter-spacing:2px; font-weight:bold;"><b> ${entityOffer.chucVu} </b></p><br>
                        <p style="padding-top:50px; font-family:&#39;Times New Roman&#39;, Arial; font-size:12pt;font-weight:bold;"> ${entityOffer.lanhDao} </p>
                    </td>
                </tr>
            </tbody></table>
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
function inDeXuatK(){
	//var printContents = document.getElementById("theDeXuat").innerHTML;
	//var originalContents = document.body.innerHTML;

    //document.body.innerHTML = printContents;
	//window.print();
	//document.body.innerHTML = originalContents;
	// var divToPrint=document.getElementById('theDeXuat');

	// var newWin=window.open('','Print-Window');

	//newWin.document.open();

	// newWin.document.write('<html><body onload="window.print()">'+divToPrint.innerHTML+'</body></html>');
	var mywindow = window.open('', '', 'height=800,width=1200');

    // mywindow.document.write('<html><head><title>' + document.title  + '</title>');
    mywindow.document.write('<html><body>');
    // mywindow.document.write('<h1>' + document.title  + '</h1>');
    mywindow.document.write(document.getElementById("theDeXuatK").innerHTML);
    mywindow.document.write('</body></html>');

    mywindow.document.close(); // necessary for IE >= 10
    mywindow.focus(); // necessary for IE >= 10*/

    mywindow.print();
    mywindow.close();

    return true;
}
		
</script>






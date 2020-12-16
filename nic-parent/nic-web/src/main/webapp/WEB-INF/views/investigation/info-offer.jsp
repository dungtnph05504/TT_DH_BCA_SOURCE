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

<div class="cls-div" id="theDeXuat">
    <div style="border:1px solid;text-align:center;width:85px;float: right;">Mẫu DS-B</div>
    <br>
    <div style="width:100%;margin-top:5px;margin-left:20px;min-height:70px;">
        <div style="width:50%;float:left;">
            
            <div>Số: <span style="font-weight:bold;">${entityOffer.soDeXuat}</span></div>
            <div>Ngày hẹn trả kết quả: <span style="font-weight:bold;">${entityOffer.ngayHenTra}</span></div>
            <div>Trả kết quả tại: <span style="font-weight:bold;">${entityOffer.noiTra}</span></div>
        </div>
        <div style="width:50%;float:left;padding-left: 200px;">
            <div style="font-style: italic;">${entityOffer.noiXL}, ngày ${entityOffer.ngayHienTai} tháng ${entityOffer.thangHienTai} năm ${entityOffer.namHienTai}</div>
        </div>
    </div>
    <div style="font-weight:bold;width:100%;text-align:center;font-size:16px;padding-top:20px;"><span style="font-style: italic;">Kính gửi</span>: LÃNH ĐẠO PHÒNG</div>
    <div style="font-weight:bold;width:100%;text-align:center;font-size:16px;">DANH SÁCH</div>
    <div style="width:100%;text-align:center;">Những người đủ điều kiện cấp, sửa đổi hộ chiếu</div>
    <br>
    <div style="width:100%;">
        <table class="tableReportLDS" style="width:100%;border-collapse:separate;">
            <thead>
                <tr>
                    <td style="width:8%;">
                        <div style="text-align:center;font-weight:bold;">STT</div>
                    </td>
                    <td style="width:24%;">
                        <div style="text-align:left;font-weight:bold;">Số máy tính/Ảnh</div>
                    </td>
                    <td style="width:68%;">
                        <div style="text-align:center;font-weight:bold;">Thông tin cá nhân</div>
                    </td>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${listOffer}" var="entity">
                    <tr>
                        <td style="vertical-align:top;padding-bottom:10px;"><div style="text-align:center;padding-top:5px;">${entity.stt}</div></td>
                        <td style="vertical-align:top;padding-bottom:10px;">
                            <div style="text-align:left;font-weight:bold;padding-top:5px;">${entity.soDanhSach}</div>
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
                        <td style="vertical-align:top;padding-bottom:10px;">
                            <div style="font-weight:bold;padding-top:5px;">${entity.soHoSo}</div>
                            <div style="width:100%;">
                                <div style="width:50%;float:left;">
                                    <div style="padding-top:5px;">1. Họ và tên: <span style="font-weight:bold;">${entity.hoTen}</span></div>
                                    <div style="padding-top:10px;">3. Ngày sinh: <span style="font-weight:bold;">${entity.ngaySinh}</span></div>
                                    <div style="padding-top:10px;">5. Số CMND/CCCD: <span style="font-weight:bold;">${entity.soCMND}</span></div>
                                </div>
                                <div style="width:50%;float:left;">
                                    <div style="padding-top:5px;">2. Giới tính: <span style="font-weight:bold;">${entity.gioiTinh}</span></div>
                                    <div style="padding-top:10px;">4. Nơi sinh: <span style="font-weight:bold;">${entity.noiSinh}</span></div>
                                    <div style="padding-top:10px;">6. Số HC:<span style="font-weight:bold;">${entity.soHC}</span></div>
                                </div>
                                <div style="width:100%;float:left;">
                                    <div style="padding-top:10px;">7. Nội dung đề xuất: <p style="font-weight:bold;white-space:pre-line;margin-left:35px;">${entity.noiDungDeXuat}</p></div>
                                </div>
                                <div style="width:100%;float:left;">
                                    <div style="padding-top:10px;white-space: normal;">8. Ghi chú: <span style="font-weight:bold;" >${entity.ghiChu}</span></div>
                                </div>
                            </div>
                        </td>
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
                                <div style="text-align:center;font-weight:bold;">${entityOffer.lanhDao}</div>
                            </div>
                            <div style="width:50%;float:left;">
                                <div style="text-align:center;font-weight:bold;">CÁN BỘ ĐỀ XUẤT</div>
                                <div style="height:50px;width:100%;"></div>
                                <div style="text-align:center;font-weight:bold;">${entityOffer.canBo}</div>
                            </div>
                        </div>
                        <div style="padding-left:15px;padding-top:10px;width:100%;float:left;">Tổng cộng: ${entityOffer.soLuongHS} hồ sơ, ${entityOffer.soNguoi} người</div>
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
function inDeXuat(){
	var mywindow = window.open('', '', 'height=800,width=1200');

    // mywindow.document.write('<html><head><title>' + document.title  + '</title>');
    mywindow.document.write('<html><body>');
    // mywindow.document.write('<h1>' + document.title  + '</h1>');
    mywindow.document.write(document.getElementById("theDeXuat").innerHTML);
    mywindow.document.write('</body></html>');

    mywindow.document.close(); // necessary for IE >= 10
    mywindow.focus(); // necessary for IE >= 10*/

    mywindow.print();
    mywindow.close();

    return true;
}
		
</script>






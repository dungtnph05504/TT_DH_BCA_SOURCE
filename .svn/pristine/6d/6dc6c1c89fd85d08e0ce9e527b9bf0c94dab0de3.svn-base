
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="getDetailXNCTransactionUrl" value="/servlet/investionProcess/getDetailXNCTransaction" />
<style>
.bkg-tranfer tr:hover {
        background-color: transparent !important;
        margin-left: 10px;
    }
</style>

<div id="divDanhSachHS">
   <div class="row" style="font-family:&#39;Times New Roman&#39;, Arial, Tahoma; font-size:12pt">
       <div class="col-sm-12">
           <div style="width:900px; border: 0px solid #999; margin:0 auto; position:relative">
               <div id="idMaudanhsach" style="padding: 5px 5px 3px; border: 1px solid rgb(0, 0, 0); text-align: center; float: right; width: 100px; display: none;">
                   Mẫu DS-03
               </div>
               <table class="bkg-tranfer" cellpadding="20" cellspacing="0" border="0" style="width:100%">
                   <tbody><tr>
                       <td style="width:40%; text-align:center">
                           TỔNG CỤC AN NINH  <br>
                           <b> CỤC QUẢN LÝ XNC </b> <br>
                           <hr style="padding:0; margin:0 auto; height:0; background-color:#000; border:0; border-bottom:1px solid #000; width:135px; position:relative; left:-3px">
                           <p style="padding:0; margin:0 auto; position:relative; top:10px;"> Số: ....... / TB - BTHS (P3) </p>
                       </td>
                       <td style="width:60%; text-align:center;">
                           <p style="padding:0; margin:0 auto; letter-spacing:2px"> CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM </p>
                           <b>Độc lập - Tự do - Hạnh phúc </b> <br>
                           <hr style="padding:0; margin:0 auto; height:0; background-color:#000; border:0; border-bottom:1px solid #000; width:190px; position:relative; left:-2px">
                           <i style="padding-left:70px; position:relative; top:10px ; font-size:14px;">..............., ngày ${root.ngay} tháng ${root.thang} năm ${root.nam} </i>
                       </td>
                   </tr>
                   <tr>
                       <td colspan="2" style="width:100%; text-align:center;margin:0 auto">
                           <p style="text-align:left;padding-left:300px;"><br> Kính gửi: </p>
                           <b style="line-height:2; font-size:18px"> DANH SÁCH </b><br>
                           <span>
                               Công dân Việt Nam cần xác minh, trao đổi
                           </span>
                           <br>
                           <div></div>
                       </td>
                   </tr>
               </tbody></table>
               <table class="bkg-tranfer" cellpadding="10" cellspacing="0" style="width:880px; margin-top:15px;border:1px solid #000;">
                   <tbody><tr>
                       <th style="width:7px; border-right:1px solid #000; border-bottom:1px solid #000"><b>STT</b></th>
                       <th style="width:150px;  border-bottom:1px solid #000"><b>DS PA08</b></th>
                       <th style="border-bottom:1px solid #000">
                           <div style="width:100%; text-align:left; padding:0; margin:0; float:left"> <b>Số biên nhận </b></div>
                           
                       </th>
                       <th style="border-bottom:1px solid #000">
                           <div style="text-align:center; padding:0; margin:0 auto; float:left"><b> Thông tin cá nhân </b></div>
                       </th>
                   </tr>
                   <c:forEach items="${dsDTO}" var="entity">
           
                   <tr>
                       <td style="text-align:center; border-right:1px solid #000;border-bottom:1px solid #000;">${entity.stt}</td>
                       <td style="text-align:center;border-bottom:1px solid #000;" valign="top">
                           ${entity.noA} <br>
                           
                       </td>
                       <td valign="top" style="border-bottom:1px solid #000;padding-bottom:7px;">
                           ${entity.recieptNo} <br>
                           <c:choose>
			                    <c:when test="${not empty entity.photoStr}">
			                        <div id="anhToKhai">
			                            <img src="data:image/jpg;base64,${entity.photoStr}" id="zoomImg"
			                                 class="img-border doGetAJpgSafeVersion" height="150" width="115" />
			
			                        </div>
			                    </c:when>
			                    <c:otherwise>
			                        <div id="anhToKhai">
			                      
			                            <img src="<c:url value='/resources/images/No_Image.jpg' />" id="zoomImg" class="img-border" height="150" width="115" />										                           
			                        </div>
			                    </c:otherwise>
			                </c:choose>
                       </td>
                       <td>
                           <br>
                           <table cellpadding="0" cellspacing="0" style="line-height:2">
                               <tbody><tr>
                                   <td>1. Họ và tên: ${entity.fullName} </td>
                                   <td style="padding-left:15px;">4. Giới tính: ${entity.gender}</td>
                               </tr>
                               <tr>
                                   <td colspan="2">3. Ngày sinh: ${entity.dob}</td>
                               </tr>
                               <tr>
                                   <td colspan="2">5. Số CMND/CCCD: ${entity.nin}</td>
                               </tr>
                               <tr>
                                   <td colspan="2">6. Đ/chỉ thường trú: ${entity.address1}</td>
                               </tr>
                               <tr>
                                   <td colspan="2">7. Cần bổ túc: ${entity.noteInvestigation}<br></td>
                               </tr>
                           </tbody></table>
                       </td>
                   </tr></c:forEach>
               </tbody></table>
               <p>
                   Tổng cộng <b> ${root.count} </b>  hồ sơ, <b> ${root.archiveCodeStt} </b>  người
               </p>
               <table class="bkg-tranfer" cellpadding="20" cellspacing="0" border="0" style="width:100%">
                   <tbody><tr>
                       <td style="width:40%; text-align:center;vertical-align:top">
                           
                       </td>
                       <td style="width:60%; text-align:center; padding-top:20px">
                           <p style="padding:0; margin:0 auto; letter-spacing:2px"><b> PHÓ TRƯỞNG PHÒNG </b></p><br>
                           <p style="padding-top:50px; font-family:&#39;Times New Roman&#39;, Arial; font-size:12pt;">   </p>

                           
                       </td>
                   </tr>
               </tbody></table>
           </div>
       </div>
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


	function inDanhSach1(){
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
	    mywindow.document.write(document.getElementById("divDanhSachHS").innerHTML);
	    mywindow.document.write('</body></html>');

	    mywindow.document.close(); // necessary for IE >= 10
	    mywindow.focus(); // necessary for IE >= 10*/

	    mywindow.print();
	    mywindow.close();

	    return true;
	}

</script>






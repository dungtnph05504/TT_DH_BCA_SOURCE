
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="getDetailXNCTransactionUrl" value="/servlet/investionProcess/getDetailXNCTransaction" />
<style>
.style_div1 {
    border-bottom: 1px dotted;
    float: left;
    text-align: center;
    margin: 0 auto;
    position: relative;
    top: -3px;
}

.style_div2 {
    width: 100%;
    padding-bottom: 15px;
    float: left;
}

.style_div3 {
    width: 100%;
    float: left;
}
.bkg-tranfer tr:hover {
        background-color: transparent !important;
        margin-left: 10px;
    }
</style>

<div id="divTraoDoiXM">
<c:forEach items="${dsDTO}" var="entity">

<div class="row" style="page-break-after: always;">
   <div class="col-sm-12">
       <div style="width:900px; border: 0px solid #999; margin:0 auto; position:relative">
           <table class="bkg-tranfer" cellpadding="20" cellspacing="0" border="0" style="width:100%">
               <tbody><tr>
                   <td style="width:40%; text-align:center;  font-family:&#39;Times New Roman&#39;, Arial; font-size:12pt;">
                       TỔNG CỤC AN NINH  <br>
                       <b style="font-family:&#39;Times New Roman&#39;, Arial; font-size:12pt; font-weight:bold"> CỤC QUẢN LÝ XNC </b>
                       <p style="padding:0; margin:0 auto; position:relative; top:10px;"> Số: ....... / P3(A08) </p>
                   </td>
                   <td style="width:60%; text-align:center; padding-top:0;">
                       <p style="padding:0; margin:0 auto; letter-spacing:2px; font-weight:normal; font-family:&#39;Times New Roman&#39;, Arial; font-size:12pt;"> CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM </p>
                       <p style="padding:0; margin:0 auto; font-family:&#39;Times New Roman&#39;, Arial; font-size:12pt; font-weight:bold">Độc lập - Tự do - Hạnh phúc </p>
                       <hr style="padding:0; margin:0 auto; height:0; background-color:#000; border:0; border-bottom:1px dotted #000; width:190px; position:relative; left:-2px">
                       <i style="padding-left:70px; position:relative; top:10px ; font-size:14px;">.........., Ngày ${entity.ngay} tháng ${entity.thang} năm ${entity.nam} </i>
                   </td>
               </tr>
               <tr>
                   <td colspan="2" style="width:100%; text-align:center; padding:15px  0 0 0; margin:0 auto ">
                       <p style="padding:15px 0 0 150px "><u style="font-style:italic">Kính gửi:  </u>...........................................................</p>
                       <b style="line-height:1; font-family:&#39;Times New Roman&#39;, Arial; font-size:13pt; letter-spacing:2px;"> PHIẾU TRAO ĐỔI XÁC MINH </b><br>
                       <p style="padding:0; font-family:&#39;Times New Roman&#39;, Arial; font-size:12pt;">V/v công dân Việt Nam đề nghị cấp hộ chiếu</p>
                   </td>
               </tr>

           </tbody></table>
           <table class="bkg-tranfer" cellpadding="10" cellspacing="0" style="width:100%; margin-top:15px;border:0;">
               <tbody><tr>
                   <td>
                       <div style="width:89.555%; padding-left:50px; clear:both; margin:0 auto; font-family:&#39;Times New Roman&#39;, Arial, Tahoma; font-size:12pt;">
                           <p> Cục QLXNC nhận hồ sơ đề nghị cấp hộ chiếu phổ thông của:</p>
                       </div>
                    
                   </td>
               </tr>
               <tr>
                   <td style="font-family:&#39;Times New Roman&#39;, Arial, Tahoma; font-size:12pt;">
                       <div style="position:absolute; float:right; z-index:12; right:90px; margin-top:-100px">
                       		<c:choose>
			                    <c:when test="${not empty entity.photoStr}">
			                        <div id="anhToKhai">
			                            <img src="data:image/jpg;base64,${entity.photoStr}" id="zoomImg"
			                                 class="img-border doGetAJpgSafeVersion" height="180" width="135" />
			
			                        </div>
			                    </c:when>
			                    <c:otherwise>
			                        <div id="anhToKhai">
			                      
			                            <img src="<c:url value='/resources/images/No_Image.jpg' />" id="zoomImg" class="img-border" height="180" width="135" />										                           
			                        </div>
			                    </c:otherwise>
			                </c:choose>	
                       </div>
                       <div style="padding:5px 15px; width:90%; margin:0 auto; z-index:10">
                           <div style="width:80%;   clear:both"><div style="float:left; width:60%"><div style="float:left">Họ tên:</div>  <div class="style_div1" style="width:85%;  text-transform:uppercase; font-weight:bold ">  ${entity.fullName} </div></div><div style="width:40%; float:left"><div style="float:left">Giới tính:</div>  <div class="style_div1" style="width:75%; font-weight:bold">${entity.gender}</div> </div> </div>
                           <div style="width:80%; padding-top:15px; clear:both"><div style="float:left; width:35%"><div style="float:left">Ngày sinh:</div>  <div class="style_div1" style="width:65% ; font-weight:bold"> ${entity.dob} </div></div><div style="width:65%; float:left"><div style="float:left">Tại:</div>  <div class="style_div1" style="width:92%; font-weight:bold">${entity.placeOfBirth}</div> </div> </div>
                           <div style="width:90%; padding-top:15px; clear:both"><div style="float:left; width:100%"><div style="float:left">Nghề nghiệp:</div>  <div class="style_div1" style="width:75%; font-weight:bold">   ${entity.job} </div></div></div>
                           <div style="width:100%; padding:15px 0; float:left">
                               <div style="float:left; width:35%"><div style="float:left">Quốc tịch:</div>  <div class="style_div1" style="width:70%; font-weight:bold"> ${entity.nationality} </div></div><div style="width:30%; float:left">
                                   <div style="float:left">Tôn giáo:</div>                                    
                                   <div class="style_div1" style="width:70%;  font-weight:bold"> ${entity.religion} </div>
                               </div><div style="width:35%; float:left">
                                   <div style="float:left">Dân tộc:</div>
                                   <div class="style_div1" style="width:68%; font-weight:bold"> ${entity.nation}</div>
                               </div>
                           </div>
                           <div class="style_div2"><div class="style_div3"><div style="float:left">Hộ khẩu thường trú:</div>  <div class="style_div1" style="width:79%; font-weight:bold">${entity.address1} </div></div></div>
                           <div class="style_div2"><div class="style_div3"><div style="float:left">Nơi tạm trú dài hạn:</div>  <div class="style_div1" style="width:79%; font-weight:bold"> ${entity.address2}  </div></div></div>
                           <div class="style_div2"><div style="float:left; width:50%"><div style="float:left">Số chứng minh thư:</div>  <div class="style_div1" style="width:63%; font-weight:bold"> ${entity.nin} </div></div><div style="width:50%; float:left"><div style="float:left">Ngày cấp:</div>  <div class="style_div1" style="width:74.555%;  font-weight:bold"> ${entity.dateNin} </div> </div> </div>
                           <div class="style_div2"><div class="style_div3"><div style="float:left">Mục đích xuất cảnh:</div>  <div class="style_div1" style="width:78.777%; font-weight:bold"> ${entity.noteInvestigation} </div></div></div>
                           <div style="clear:both; padding-top:25px">Thân nhân:</div>
                       </div>
                       <div style="padding:5px 15px; width:90%; margin:0 auto; padding-left:90px">
                           

                       </div>
                       <div style="clear:both; position:relative; padding-left:65px;  font-weight:bold">Đề nghị xác minh thêm.</div>
                   </td>
               </tr>
           </tbody></table>
           <table class="bkg-tranfer" cellpadding="20" cellspacing="0" border="0" style="width:100%; margin-top:30px;">
               <tbody><tr>
                   <td style="width:40%; text-align:center;vertical-align:top; "></td>
                   <td style="width:60%; text-align:center; ">
                       <b style="font-family:&#39;Times New Roman&#39;, Arial; font-size:12pt;"> PHÓ TRƯỞNG PHÒNG  </b>
                       <p style="padding-top:50px; font-family:&#39;Times New Roman&#39;, Arial; font-size:12pt;">  </p>
                    </td>
                </tr>
            </tbody></table>
        </div>
    </div>
</div>
</c:forEach>
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


	function inTranDoi1(){
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
	    mywindow.document.write(document.getElementById("divTraoDoiXM").innerHTML);
	    mywindow.document.write('</body></html>');

	    mywindow.document.close(); // necessary for IE >= 10
	    mywindow.focus(); // necessary for IE >= 10*/

	    mywindow.print();
	    mywindow.close();

	    return true;
	}

</script>






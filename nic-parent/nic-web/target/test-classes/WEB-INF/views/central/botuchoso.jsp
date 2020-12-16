
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

<div id="divBoTucHS">
<c:forEach items="${dsDTO}" var="entity">
<div class="row" style="page-break-after: always;">
<div class="col-sm-12">
    <div style="width:900px; border: 0px solid #999; margin:0 auto; position:relative">
        <table class="bkg-tranfer" cellpadding="20" cellspacing="0" border="0" style="width:100%">
            <tbody><tr>
                <td style="width:40%; text-align:center;  font-family:&#39;Times New Roman&#39;, Arial; font-size:12pt;">
                    TỔNG CỤC AN NINH  <br>
                    <b style="font-family:&#39;Times New Roman&#39;, Arial; font-size:12pt; font-weight:bold"> CỤC QUẢN LÝ XNC </b>
                    <hr style="padding:0; margin:0 auto; height:0; background-color:#000; border:0; border-bottom:1px solid #000; width:135px; position:relative; left:-3px">
                    <p style="padding:0; margin:0 auto; position:relative; top:10px;"> Số: ....... / A08(P3) </p>
                </td>
                <td style="width:60%; text-align:center;">
                    <p style="padding:0; margin:0 auto; letter-spacing:2px; font-weight:normal; font-family:&#39;Times New Roman&#39;, Arial; font-size:12pt;"> CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM </p>
                    <p style="padding:0; margin:0 auto; font-family:&#39;Times New Roman&#39;, Arial; font-size:12pt; font-weight:bold">Độc lập - Tự do - Hạnh phúc </p>
                    <hr style="padding:0; margin:0 auto; height:0; background-color:#000; border:0; border-bottom:1px solid #000; width:190px; position:relative; left:-2px">
                    <i style="padding-left:70px; position:relative; top:10px ; font-size:14px;">.........., Ngày ${entity.ngay} tháng ${entity.thang} năm ${entity.nam} </i>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="width:100%; text-align:center; padding:15px  0 0 0; margin:0 auto ">
                    <b style="line-height:2; font-size:18px"> PHIẾU BÁO BỔ TÚC HỒ SƠ </b><br>
                </td>
            </tr>

        </tbody></table>
        <table class="bkg-tranfer" cellpadding="10" cellspacing="0" style="width:100%; margin-top:15px;border:0; padding-left:20px">
            <tbody><tr>
                <td>
                    <div style="width:75%; padding-left:50px; clear:both; margin:0 auto; font-family:&#39;Times New Roman&#39;, Arial, Tahoma; font-size:12pt;">
                        <p><i>Kính gửi: </i> <b>Ông    ${entity.fullName}</b></p>
                        <p><i>Địa chỉ: </i> <b> ${entity.address1} </b></p>
                    </div>
                </td>
            </tr>
            <tr>

                <td style="font-family:&#39;Times New Roman&#39;, Arial, Tahoma; font-size:12pt;">
                    <div style="padding:5px 15px; width:90%; margin:0 auto"> 
                        <div id="NoiDungTraoDoi"></div> 
                    </div>
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
</div></c:forEach>
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


	function inBoTucHS(){
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
	    mywindow.document.write(document.getElementById("divBoTucHS").innerHTML);
	    mywindow.document.write('</body></html>');

	    mywindow.document.close(); // necessary for IE >= 10
	    mywindow.focus(); // necessary for IE >= 10*/

	    mywindow.print();
	    mywindow.close();

	    return true;
	}

</script>






<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="getDetailXNCTransactionUrl" value="/servlet/investionProcess/getDetailXNCTransaction" />
<style>

</style>

<div id="divLichSuCMND">
	<table id="tbLichSuCMND" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
		  <thead>
		    <tr>
		      <th class="th-sm" style="width: 40px;">STT
		
		      </th>	
		      <th class="th-sm">Họ tên
		
		      </th>
		      <th class="th-sm">Giới tính	
		
		      </th>								     
		      <th class="th-sm">Ngày sinh
		      
		      </th>
		      <th class="th-sm">Số CMT
		      
		      </th>
		      <th class="th-sm">Ngày cấp
		      
		      </th>
		    </tr>
		  </thead>
		  <c:if test="${not empty jobList}">
			  <tbody>
			    <c:forEach items="${jobList}" var="item">
				    <tr>
				     	<td class="align-central">${item.stt}</td>
				     	<td>${item.hoTen}</td>
				     	<td>${item.gioiTinh}</td>
				     	<td class="align-central">${item.ngaySinh}</td>
				     	<td>${item.cmnd}</td>
				     	<td class="align-central">${item.ngayCap}</td>
				    </tr>
			    </c:forEach>
			  </tbody>
		  </c:if>
	</table>
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


	function inLichSu(){
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
	    mywindow.document.write(document.getElementById("divLichSuCMND").innerHTML);
	    mywindow.document.write('</body></html>');

	    mywindow.document.close(); // necessary for IE >= 10
	    mywindow.focus(); // necessary for IE >= 10*/

	    mywindow.print();
	    mywindow.close();

	    return true;
	}

</script>






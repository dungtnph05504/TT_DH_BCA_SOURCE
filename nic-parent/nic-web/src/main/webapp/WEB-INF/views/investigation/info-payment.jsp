<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<style>

</style>
	
    <table class="table table-bordered table-sm table-hover" cellspacing="0" width="100%" style="margin: 0;">
	  <thead>
	    <tr>
	      <th class="th-sm" style="max-width: 40px;">
	
	      </th>	
	      <th class="th-sm">Mã
	
	      </th>
	      <th class="th-sm">Chi phí
	
	      </th>
	      <th class="th-sm">Tên
	
	      </th>
	    </tr>
	   </thead>
	   <tbody>
		   <c:forEach items="${PhiBoSung}" var="phi_item">
		   		<tr>
		   			<td class="align-central">
		   				<c:if test="${phi_item.trangThai == '0'}">
			      			<input type="checkbox" name="payChked" value="${phi_item.maPhuPhi}" />
			      		</c:if>
			      		<c:if test="${phi_item.trangThai == '1'}">
			      			<input type="checkbox" name="payChked" checked="checked" value="${phi_item.maPhuPhi}" />
			      		</c:if>
		   			</td>
		   			<td>${phi_item.maPhuPhi}</td>
		   			<td class="align-right">${phi_item.soTien}</td>
		   			<td>${phi_item.tenPhuPhi}</td>
		   		</tr>
		   </c:forEach>
		    
	  </tbody>
	</table>
	<c:if test="${empty PhiBoSung}">
	  	<div class="style-no-record opentb-1">Không tìm thấy kết quả</div>
	</c:if>

					
					      
		
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
	$('#idMaPhiChinh').change(function(){
		var phiChinh = $("#idMaPhiChinh option:selected" ).val();
		$('#phiTheoMa').text(phiChinh);
	});
});	
	

</script>






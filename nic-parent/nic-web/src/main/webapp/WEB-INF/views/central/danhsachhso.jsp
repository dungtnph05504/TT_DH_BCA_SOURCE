<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<style>

</style>

<div class="col-sm-12 pad-bottom-5 pad-top-10">
	<label class="col-sm-1 control-label text-right" style="padding: 0px;">TT xử lý</label>
	<div class="col-sm-3">
		<input type="text" class="form-control col-sm-4 " value="${danhsach.ttxuly}">
	</div>
	<label class="col-sm-1 control-label text-right" style="padding: 0px;">TT cá thể hóa</label>
	<div class="col-sm-3">
		<input type="text" class="form-control col-sm-4" value="${danhsach.ttcathe}">
	</div>
	<label class="col-sm-1 control-label text-right" style="padding: 0px;">Số DS</label>
	<div class="col-sm-3">
		<input type="text" class="form-control col-sm-4" value="${danhsach.packageId}">
	</div>
</div>

<div class="col-sm-12 pad-bottom-5 pad-top-15">
<table class="table table-bordered table-sm table-hover" cellspacing="0" width="100%" style="margin: 0;">
	  <thead>
	    <tr>
	      <th class="th-sm">Số hồ sơ
	
	      </th>
	      <th class="th-sm">Họ tên
	
	      </th>
	      <th class="th-sm">Ngày sinh
	
	      </th>
	      <th class="th-sm">Nơi sinh
	
	      </th>
	      <th class="th-sm">Số CMND/CCCD
	
	      </th>
	      <th class="th-sm">Nơi trả HC
	
	      </th>
	      <th class="th-sm">Loại HC
	
	      </th>
	    </tr>
	   </thead>
	   <tbody>
		   <c:forEach items="${danhsach.chitiet}" var="phi_item">
		   		<tr>
		   			<td>${phi_item.packageId}</td>
		   			<td>${phi_item.fullName}</td>
		   			<td>${phi_item.dob}</td>
		   			<td>${phi_item.pob}</td>
		   			<td>${phi_item.nin}</td>
		   			<td>${phi_item.issuePlace}</td>
		   			<td>${phi_item.typeP}</td>
		   		</tr>
		   </c:forEach>
		    
	  </tbody>
	</table>
	<c:if test="${empty danhsach.chitiet}">
	  	<div class="style-no-record opentb-1">Không tìm thấy kết quả</div>
	</c:if>
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







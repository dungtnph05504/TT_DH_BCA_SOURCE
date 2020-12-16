<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<style>
.cls-with-100 {
	width: 100%;
}

.cls-btm-15 {
	margin-bottom: 15px;
}
</style>

<div class="new-heading-2">
	<c:if test="${style == 'add'}">
		THÊM MỚI PHIẾU X/N
	</c:if>
	<c:if test="${style == 'edit'}">
		CẬP NHẬT PHIẾU X/N
	</c:if>
</div>

<div class="col-md-12 col-md-12">
	<div class="col-md-6">
		<label class="col-md-4 control-label text-right cls-btm-15">Mã kho</label>
		<div class="col-md-8 cls-btm-15">
			<select id="invNo" class="cls-with-100">
				<c:forEach items="${invList}" var="_item">					
					<c:choose>
						<c:when test="${_item.key == dto.itemStr}">
							<option selected="selected" value="${_item.key}">${_item.value}</option>
						</c:when>
						<c:otherwise>
							<option value="${_item.key}">${_item.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
		<label class="col-md-4 control-label text-right cls-btm-15">Số bàn giao</label>
		<div class="col-md-8 cls-btm-15">
			<input type="text" id="handoverNo" class="cls-with-100" value="${dto.handoverNo}"/>
		</div>
		<label class="col-md-4 control-label text-right cls-btm-15">Ngày tiếp nhận</label>
		<div class="col-md-8 cls-btm-15">
			<input type="text" id="receiptDate" class="cls-with-100" value="${dto.receiptDate}"/>
		</div>
		<label class="col-md-4 control-label text-right cls-btm-15">Loại HC/VT</label>
		<div class="col-md-8 cls-btm-15">
			<select id="categoryItem" class="cls-with-100">
				<c:forEach items="${categoryList}" var="_item">					
					<c:choose>
						<c:when test="${_item.key == dto.category}">
							<option selected="selected" value="${_item.key}">${_item.value}</option>
						</c:when>
						<c:otherwise>
							<option value="${_item.key}">${_item.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="col-md-6">
		<label class="col-md-4 control-label text-right cls-btm-15">Người tiếp nhận</label>
		<div class="col-md-8 cls-btm-15">
			<input type="text" id="nameInv" class="cls-with-100" value="${dto.receiptName}"/>
		</div>
		<label class="col-md-4 control-label text-right cls-btm-15">Người bàn giao</label>
		<div class="col-md-8 cls-btm-15">
			<input type="text" id="handoverInv" class="cls-with-100" value="${dto.handoverName}"/>
		</div>
		<label class="col-md-4 control-label text-right cls-btm-15">Số lô</label>
		<div class="col-md-8 cls-btm-15">
			<input type="text" id="batchInv" class="cls-with-100" value="${dto.batchNo}"/>
		</div>
	</div>
	<input type="hidden" id="itemId" value="${dto.item}"/>
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
	$("#receiptDate").datepicker({
		changeMonth : true,
		changeYear : true,
		showSecond : true,
		controlType : 'select',
		dateFormat : 'dd/mm/yy',
		yearRange : "-100:+10"
	}).keyup(function(e) {
		if (e.keyCode == 8 || e.keyCode == 46) {
			$.datepicker._clearDate(this);
		}
	});
	
})	

</script>






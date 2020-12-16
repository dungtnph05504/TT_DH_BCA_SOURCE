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
		THÊM MỚI PHÔI IN
	</c:if>
	<c:if test="${style == 'edit'}">
		CẬP NHẬT PHÔI IN
	</c:if>
</div>

<div class="col-md-12 col-md-12">
	<div class="col-md-6">
		<label class="col-md-4 control-label text-right cls-btm-15">Mã phiếu X/N</label>
		<div class="col-md-8 cls-btm-15">
			<select id="inventoryItem" class="cls-with-100">
				<c:forEach items="${itemList}" var="_item">
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
		<label class="col-md-4 control-label text-right cls-btm-15">Trạng thái</label>
		<div class="col-md-8 cls-btm-15">
			<select id="statusDt" class="cls-with-100">								
				<c:forEach items="${statusList}" var="_item">					
					<c:choose>
						<c:when test="${_item.key == dto.status}">
							<option selected="selected" value="${_item.key}">${_item.value}</option>
						</c:when>
						<c:otherwise>
							<option value="${_item.key}">${_item.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>		
			</select>
		</div>
		<label class="col-md-4 control-label text-right cls-btm-15">Số lô</label>
		<div class="col-md-8 cls-btm-15">
			<select id="batchNoItem" class="cls-with-100">
				<c:forEach items="${batchList}" var="_item">					
					<c:choose>
						<c:when test="${_item.key == dto.batchNo}">
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
		<label class="col-md-4 control-label text-right cls-btm-15">Chip series no</label>
		<div class="col-md-8 cls-btm-15">
			<input type="text" id="chipNo" class="cls-with-100" value="${dto.chipNo}"/>
		</div>
		<label class="col-md-4 control-label text-right cls-btm-15">Số phôi(Chữ)</label>
		<div class="col-md-8 cls-btm-15">
			<input type="text" id="docChar" class="cls-with-100" value="${dto.docChar}"/>
		</div>
		<label class="col-md-4 control-label text-right cls-btm-15">Số phôi(Số)</label>
		<div class="col-md-8 cls-btm-15">
			<input type="text" id="docNum" class="cls-with-100" value="${dto.docNum }"/>
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
	
	
})	

</script>






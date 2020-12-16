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
		THÊM MỚI KHO
	</c:if>
	<c:if test="${style == 'edit'}">
		CẬP NHẬT KHO
	</c:if>
</div>

<div class="col-md-12 col-md-12">
	<div class="col-md-6">
		<label class="col-md-4 control-label text-right cls-btm-15">Tên kho</label>
		<div class="col-md-8 cls-btm-15">
			<input type="text" id="nameInv" class="cls-with-100" value="${dto.name}"/>
		</div>
		<label class="col-md-4 control-label text-right cls-btm-15">Mã kho</label>
		<div class="col-md-8 cls-btm-15">
			<input type="text" id="codeInv" class="cls-with-100" value="${dto.code}"/>
		</div>
		<label class="col-md-4 control-label text-right cls-btm-15">Trạng thái</label>
		<div class="col-md-8 cls-btm-15">
			<select id="statusInv" class="cls-with-100">
				<c:forEach items="${statusList}" var="_item">					
					<c:choose>
						<c:when test="${_item.key == dto.active}">
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
		<label class="col-md-4 control-label text-right cls-btm-15">Địa chỉ</label>
		<div class="col-md-8 cls-btm-15">
			<input type="text" id="addressInv" class="cls-with-100" value="${dto.address}"/>
		</div>
		<label class="col-md-4 control-label text-right cls-btm-15">Ghi chú</label>
		<div class="col-md-8 cls-btm-15">
			<textarea rows="4" cols="" id="descInv" class="cls-with-100">
				${dto.description}
			</textarea>
		</div>
	</div>
	<input type="hidden" id="itemId" value="${dto.id}"/>
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






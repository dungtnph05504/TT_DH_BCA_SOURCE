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


<div class="col-md-12 col-md-12">
	<div class="col-md-6">
		<label class="col-md-4 control-label text-right cls-btm-15">Mã chuyến bay</label>
		<div class="col-md-8 cls-btm-15">
			<input type="text" id="codeAir" class="cls-with-100" value="${dto.flightNo}"/>
		</div>
		<label class="col-md-4 control-label text-right cls-btm-15">Loại</label>
		<div class="col-md-8 cls-btm-15">
			<select id="typeFlight" class="cls-with-100">
				<option value="X">Xuất</option>
				<option value="N">Nhập</option>
			</select>
		</div>
		<label class="col-md-4 control-label text-right cls-btm-15">Đường bay</label>
		<div class="col-md-8 cls-btm-15">
			<select id="flightRt" class="cls-with-100">
				<c:forEach items="${flightMap}" var="_item">					
					<c:choose>
						<c:when test="${_item.key == dto.flightRouterCode}">
							<option selected="selected" value="${_item.key}">${_item.value}</option>
						</c:when>
						<c:otherwise>
							<option value="${_item.key}">${_item.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
		<label class="col-md-4 control-label text-right cls-btm-15">DS chuyến bay</label>
		<div class="col-md-8 cls-btm-15">
			<div id="flightID" style="border: 1px solid #e1dada;overflow: auto;height: 100px;padding: 5px;">
						
			</div>
		</div>
	</div>
	<div class="col-md-6">
		<label class="col-md-4 control-label text-right cls-btm-15">Tên chuyến bay</label>
		<div class="col-md-8 cls-btm-15">
			<input type="text" id="nameAir" class="cls-with-100" value="${dto.name}"/>
		</div>
		<label class="col-md-4 control-label text-right cls-btm-15">Hãng hàng không</label>
		<div class="col-md-8 cls-btm-15">
			<select id="airlineCode" class="cls-with-100">
				<c:forEach items="${airList}" var="_item">					
					<c:choose>
						<c:when test="${_item.key == dto.airlineCode}">
							<option selected="selected" value="${_item.key}">${_item.value}</option>
						</c:when>
						<c:otherwise>
							<option value="${_item.key}">${_item.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
		<label class="col-md-4 control-label text-right cls-btm-15">Ghi chú</label>
		<div class="col-md-8 cls-btm-15">
			<textarea rows="4" cols="" id="descAir" class="cls-with-100">${dto.note}</textarea>
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
$('#flightRt').change(function(){
	loadFligt();
});
</script>






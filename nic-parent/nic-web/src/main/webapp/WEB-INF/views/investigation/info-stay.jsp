<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<style>

</style>
<div class="new-heading-2">PHIẾU KHAI BÁO TẠM TRÚ</div>
<div class="col-sm-12 none-padding">
<div class="col-sm-5">
    <div style="height: 200px; overflow: auto;">
	<table id="tbTamTru" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
	<thead>
	  <tr>
	    <th class="th-sm">Từ ngày
	
	    </th>											 
	    <th class="th-sm">Đến ngày
	
	    </th>
	    <th class="th-sm">Buồng
	
	    </th>										     
	    <th class="th-sm">Số KS nhà trọ/chủ nhà
	
	    </th>										     									     
	  </tr>
	</thead>
	<!--<c:if test="${not empty chiTietDS}">-->
	<tbody>
	  <c:forEach items="${dsTamTru}" var="item">
	<tr>												      	
	  	<td></td>
	  	<td></td>
	  	<td></td>
	  	<td></td>																													     
	</tr>
	</c:forEach>
	</tbody>
	<!-- </c:if>-->
	</table>
	<c:if test="${empty dsTamTru}">
	<div class="style-no-record opentb-1">Không tìm thấy kết quả</div>
	</c:if>
	       </div>
	    </div>
	    <div class="col-sm-7">
	        <div class="form-horizontal form-profile">
	            <div class="col-sm-5">
	                <div class="epp-form-group">
	                    <label class="control-label">Họ tên</label>
	                    <input type="text" class="form-control">
	                </div>
	            </div>
	            <div class="col-sm-3">
	                <div class="epp-form-group">
	                    <label class="control-label">Giới tính</label>
	                    <input type="text" class="form-control">
	                </div>
	            </div>
	            <div class="col-sm-4">
	                <div class="epp-form-group">
	                    <label class="control-label">Ngày sinh</label>
	                    <input type="text" class="form-control">
	                </div>
	            </div>
	            <div class="col-sm-5">
	                <div class="epp-form-group">
	                    <label class="control-label">Số hộ chiếu</label>
	                    <input type="text" class="form-control">
	                </div>
	            </div>
	            <div class="col-sm-7">
	                <div class="epp-form-group">
	                    <label class="control-label">Quốc tịch</label>
	                    <input type="text" class="form-control">
	                </div>
	            </div>
	            <div class="col-sm-4">
	                <div class="epp-form-group">
	                    <label class="control-label">Mục đích</label>
	                    <input type="text" class="form-control">
	                </div>
	            </div>
	            <div class="col-sm-4">
	                <div class="epp-form-group">
	                    <label class="control-label">Số thẻ thường trú/tạm trú</label>
	                    <input type="text" class="form-control">
	                </div>
	            </div>
	            <div class="col-sm-4">
	                <div class="epp-form-group">
	                    <label class="control-label">Hạn tạm trú</label>
	                    <input type="text" class="form-control">
	                </div>
	            </div>
	            <div class="col-sm-12">
	                <div class="epp-form-group">
	                    <label class="control-label">Nơi tạm trú</label>
	                    <input type="text" class="form-control">
	                </div>
	            </div>
	            <div class="col-sm-7">
	                <div class="epp-form-group">
	                    <label class="control-label">Cửa khẩu nhập</label>
	                    <input type="text" class="form-control">
	                </div>
	            </div>
	            <div class="col-sm-5">
	                <div class="epp-form-group">
	                    <label class="control-label">Ngày nhập cảnh</label>
	                    <input type="text" class="form-control">
	                </div>
	            </div><div class="col-sm-7">
	                <div class="epp-form-group">
	                    <label class="control-label">Nơi sinh</label>
	                    <input type="text" class="form-control">
	                </div>
	            </div>
	            <div class="col-sm-5">
	                <div class="epp-form-group">
	                    <label class="control-label">Nghề nghiệp</label>
	                    <input type="text" class="form-control">
	                </div>
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
	
	

</script>






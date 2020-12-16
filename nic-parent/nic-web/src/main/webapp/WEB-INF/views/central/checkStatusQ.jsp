<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<style>

</style>
<div class="panel panel-success">
<div class="panel-heading">Trạng thái</div>
	<div id="idStatusssChk" class="panel-body" style="height: 150px;overflow: auto;">
		
		<div class="checkbox" style="margin-bottom: 5px;"> 
			<label><input type="radio" name="chkSite1" value="PENDING" style="margin-right: 5px;">Chưa xử lý</label>
		</div>
		<div class="checkbox" style="margin-bottom: 5px;"> 
			<label><input type="radio" name="chkSite1" value="DOING" style="margin-right: 5px;">Đang xử lý</label>
		</div>
		<div class="checkbox" style="margin-bottom: 5px;"> 
			<label><input type="radio" name="chkSite1" value="NONE" style="margin-right: 5px;">Xử lý lỗi</label>
		</div>
		<div class="checkbox" style="margin-bottom: 5px;"> 
			<label><input type="radio" name="chkSite1" value="DONE" style="margin-right: 5px;">Xử lý thành công</label>
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
 var st =  "${st}";
$(document).ready(function() {
	//alert(st);
	//$("input[name='"+chkSite+"'][value='"+value+"']").prop('checked', true);
	//$("input[name='chkSite'][value='DOING']").prop('checked', true);
 	$("input[name='chkSite1'][value='"+st+"']").prop('checked', true);
});

</script>






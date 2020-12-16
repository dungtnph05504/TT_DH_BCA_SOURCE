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
<div class="panel-heading">Đơn vị tiếp nhận</div>
	<div id="idSiteChk" class="panel-body" style="height: 150px;overflow: auto;">
		<c:forEach items="${siteList}"  var="_item">
		<div class="checkbox" style="margin-bottom: 5px;"> 
			<label><input type="radio" name="chkSite" value="${_item.key}" style="margin-right: 5px;">${_item.value}</label>
		</div>
		</c:forEach>
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
$(document).ready(function() {
	
});

</script>






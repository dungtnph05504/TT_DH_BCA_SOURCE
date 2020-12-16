<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
	
$(document).ready(function() {

	$("#row").flexigrid({
									title : 'Customised Query Result',
									usepager : false,
									useRp : false,
									showTableToggleBtn : true,
									width : 'auto',
									height :350,
									singleSelect : true,
									nowrap : false

								});

	});
	
</script>
<div class="container">

        	<div class="row">
        		<div class="roundedBorder ov_hidden">
	<display:table cellspacing="0" cellpadding="0" id="row"
		name="list" export="false" requestURI="/servlet/reportmgmt/getCustomTableData">


	</display:table>
	</div>
	</div>
</div>
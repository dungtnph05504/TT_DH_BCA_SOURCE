<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%> 
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%> 

		 
													
										
<body>

  <div class="waitingWhileWaiting" > 
		<div style="font-color:#fff; margin-top:50px; font-size: 1.5em; font-weight: bold; text-align:center" >
			Yêu cầu của bạn đang được xử lý. Xin vui lòng đợi trong giât lát.
  </div>

  <div class="waitingWhenDoneWaiting" style="display:none;" >
	<c:if test="${image!=null}">
			<img src="data:image/jpg;base64,${image}" class="doGetAJpgSafeVersion"
			style="vertical-align:  middle; text-align:  center; margin:  auto;" 
			/>
	</c:if> 
	<c:if test="${image==null}">
			Không có ảnh.
	</c:if>
  </div>
</body>

										
<script>
 
	document.title = document.title + '  -  ' + '<c:out value="${description}" />'
	 	
</script>
<script> 
    //var aValidBase64String = "iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==";

	$(document).ready(function() { 
		 
			
		$(".waitingWhileWaiting").css( "display", "none");
		$(".waitingWhenDoneWaiting").css( "display", "block");
	});
</script>	
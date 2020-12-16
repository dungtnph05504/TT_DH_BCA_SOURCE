<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" language="javascript">
		    function showStackTrace() {
		      document.getElementById('stacktrace').style.display = "block";
		    }
		    
		    function toogleStackTrace()
		    {
		    	if(document.getElementById('stacktrace').style.display == "block")
		    	{
		    		document.getElementById('stacktrace').style.display = "none";
		    	}
		    	else
		    	{
		    		document.getElementById('stacktrace').style.display = "block";
		    	}
		    }
</script>

<table width="90%" cellpadding="5" cellspacing="5" border="0" align="center" class="mainBody">
    <tr>
      <td align="center" class="errorTitle">
        Thông báo lỗi
        <hr size="1"/>
      </td>
    </tr>
	<c:if test="${not empty exception}" >
	    <tr>
	      <td><b>Đã xảy ra lỗi khi hiển thị trang:</b></td>
	    </tr>
	    <tr>
	      <td>[<a href="javascript:toogleStackTrace()">Xem chi tiết</a>]</td>
	    </tr>
	    <tr>
	      <td>
	      	<div id="stacktrace" style="display:none;width: 950px;" >
		      	<br />
		      	<br />
	      		<c:out value="${messageStack}"/>
	      	</div>
	      </td>
	    </tr>
	</c:if>
  </table>
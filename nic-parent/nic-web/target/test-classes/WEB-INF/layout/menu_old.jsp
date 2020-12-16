<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url var="exceptionUrl" value="/servlet/exception/retrieveException" />
<div id="menu">
	<ul>
		<li><a href="${exceptionUrl}">Home</a></li>
		<li><a href="#" onmouseover="mopen('m1')"
			onmouseout="mclosetime()">RIC Services<span class="downArrow"></span></a>

			<div id="m1" onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
				<a href="${exceptionUrl}">Registration Counter</a><br /> 
				<a href="${exceptionUrl}">Verification Counter</a> <br />
				<a href="${exceptionUrl}">Card Issuance</a> <br />
				<a href="${exceptionUrl}">Exception Handling</a> <br />
				<a href="${exceptionUrl}">Status Enquiry</a>
			</div></li>


		<li><a href="${exceptionUrl}">Logout</a></li>
	</ul>
</div>
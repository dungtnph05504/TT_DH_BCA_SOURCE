<%@page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
	
</script>
<style>
	#page_heading_p {
	    text-align: center;
	    color: rgb(25, 25, 112);
	    font-family: 'Times New Roman';
	    font-size: 21px;
	    font-weight: bold;
	    /* padding-top: 25px; */
	}
</style>
<div class=header>
<div class="bg_header">
<div class="container">
                    <div class="row">
                    <div class="header-content">
								<div class="logo col-md-3"><img
									src="<c:url value="/resources/images/icon_welcome/quochuy.png"/>"
									alt="SYSTEM LOGO"></div>
								<!-- height="60px" width="210px" -->
								<!-- <td height="20px"></td>
         						<td width="120px"></td> -->
								<div class="col-md-6" id="page_heading">
								<p id="page_heading_p">BỘ NGOẠI GIAO</p>
								${fnSelected}
								</div>
								<div class="displayDate col-md-3" style="margin-top: 30px;"><c:if
										test="${not empty sessionScope.userSession}">
		  								Xin chào ${sessionScope.userSession.firstName} 
		  							</c:if> <br /> <c:if test="${not empty sessionScope.userSession}">
		  							Đăng nhập bởi: ${sessionScope.userSession.userName}
		  							</c:if> <br /> <br /> 
		  							<!--<script>
												var myVar = setInterval(
														function() {
															myTimer()
														}, 1000);
												function myTimer() {
													var today = getCurrentDisplayDate();
													document
															.getElementById("timeDisplay").innerHTML = today;
												}
											</script>
									<div id="timeDisplay">&nbsp;</div> <br />
									<c:if test="${not empty sessionScope.userSession}">
										<span id="workStationDisplay" style="font-size: 12px;">Máy trạm: ${sessionScope.userSession.workstationId}</span>
									</c:if>-->
									</div>
	<c:url var="url" value="/servlet/user/logout" />
	<form action="${url}" method="Post" id="logoutForm"></form>
	</div>
	</div>
	</div>
</div>
</div>
<applet height="0" width="0" name="EppApplet" id="EppApplet"
	code="com.nec.asia.applet.EppApplet">
	<param name="codebase"
		value="${pageContext.request.contextPath}/applet/" />
	<param name="archive" value="nic-applet.jar,spid6.jar" />
	<param name="type" value="application/x-java-applet;" />
	<param name="scriptable" value="true" />
	<param name="mayscript" value="true" />
</applet>
<applet height="0" width="0" name="investigationApplet" id="investigationApplet"
	code="com.nec.asia.applet.InvestigationApplet.class">
	<param name="codebase"
		value="${pageContext.request.contextPath}/applet/" />
	<param name="archive" value="nic-applet.jar,spid6.jar" />
	<param name="scriptable" value="true" />
	<param name="mayscript" value="true" />
	<param name="verify" value="N">
</applet>
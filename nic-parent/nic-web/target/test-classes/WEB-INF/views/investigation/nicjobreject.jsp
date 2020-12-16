<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


	<div id="main">
		<DIV class=content>
			<A id=top href="http://203.127.251.87:9082/nric/servlet/"></A>
			<DIV id=message>
				<!-- Model.errorList -->
			</DIV>
		</DIV>
		<BR>

		<div class="content_main">
			
			<div id="global_icon_div"></div>
			<div id="global_error_div"></div>
			<br />

			<div class="border_error">
				<!--<div class="errors"> -->
				<div class="success_left">
					<img align='left'
						src="<c:url value="/resources/images/alert.png" />" width="30"
						height="30" />
				</div>
				<div class="errors">

					<table width="800" height="62" border="0" cellpadding="5">

						<tr>
							<span style="font-size: 20px; font-weight: bold; color: #000;">&nbsp;Trạng thái điều tra</span>
							<td width="587" height="28"
								style="font-size: 18px; font-weight: bold; color: #000;">
								<p class="text_pad">
									&nbsp;Kiểm tra đã được xử lý thành công với mã công việc:
									<c:out value="${rejectJobId}" />
								</p>
								<p class="text_pad"
									style="font-size: 12px; font-weight: normal; color: #000;">&nbsp;Giao dịch bị từ chối với sản xuất thẻ.</p>
							</td>
						</tr>

					</table>

				</div>
			</div>

		
	<br />
	<br />
	<br />
<br />
<br />
<br />
<br />
<br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
	</div>
	</div>
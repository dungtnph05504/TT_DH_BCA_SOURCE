<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


	<div id="content_main">
		<div class="displayTag">
			<table style="width: 100%; font-size: 12px;" cellspacing="0" cellpadding="0">
				<tr>
					<td style="padding: 5px; font-weight: bold; width: 15%;"> Mã giao dịch</td>
					<td style="padding: 5px; width: 2%;">:</td>
					<td style="padding: 5px; width: 33%;"><c:out
							value="${nicTransaction.transactionId}"></c:out></td>
					<td style="padding: 5px; font-weight: bold; width: 15%;">Ngày trên ứng dụng</td>
					<td style="padding: 5px; width: 2%;">:</td>
					<td style="padding: 5px; width: 33%;"><c:out
							value="${nicTransaction.datTimeOfApplication}"></c:out></td>
				</tr>
				<tr>
					<td style="padding: 5px; font-weight: bold; width: 15%;">Ngày đăng ký</td>
					<td style="padding: 5px; width: 2%;">:</td>
					<td style="padding: 5px; width: 33%;"><c:out
							value="${nicTransaction.datTimeOfApplication}"></c:out></td>
					<td style="padding: 5px; font-weight: bold; width: 15%;">Ngày tạo</td>
					<td style="padding: 5px; width: 2%;">:</td>
					<td style="padding: 5px; width: 33%;"><c:out
							value="${nicTransaction.createDateTime}"></c:out></td>
				</tr>
				<tr>
					<td style="padding: 5px; font-weight: bold; width: 15%;">Loại giao dịch</td>
					<td style="padding: 5px; width: 2%;">:</td>
					<td style="padding: 5px; width: 33%;"><c:out
							value="${nicTransaction.transactionType}"></c:out></td>
					<td style="padding: 5px; font-weight: bold; width: 15%;">Người tạo</td>
					<td style="padding: 5px; width: 2%;">:</td>
					<td style="padding: 5px; width: 33%;"><c:out
							value="${nicTransaction.createBy}"></c:out></td>
				</tr>
				<tr>
					<td style="padding: 5px; font-weight: bold; width: 15%;">Trạng thái giao dịch</td>
					<td style="padding: 5px; width: 2%;">:</td>
					<td style="padding: 5px; width: 33%;"><c:out
							value="${nicTransaction.transactionStatus}"></c:out></td>
					<td style="padding: 5px; font-weight: bold; width: 15%;">Ngày cập nhật</td>
					<td style="padding: 5px; width: 2%;">:</td>
					<td style="padding: 5px; width: 33%;"><c:out
							value="${nicTransaction.updateDateTime}"></c:out></td>
				</tr>

				<tr>
					<td style="padding: 5px; font-weight: bold; width: 15%;">Trung tâm đăng ký</td>
					<td style="padding: 5px; width: 2%;">:</td>
					<td style="padding: 5px; width: 33%;"><c:out
							value="${nicTransaction.regSiteCode}"></c:out></td>
					<td style="padding: 5px; font-weight: bold; width: 15%;">Người cập nhật</td>
					<td style="padding: 5px; width: 2%;">:</td>
					<td style="padding: 5px; width: 33%;"><c:out
							value="${nicTransaction.updateBy}"></c:out></td>
				</tr>
				<tr>
					<td style="padding: 5px; font-weight: bold; width: 15%;">Trung tâm phát hành</td>
					<td style="padding: 5px; width: 2%;">:</td>
					<td style="padding: 5px; width: 33%;"><c:out
							value="${nicTransaction.issSiteCode}"></c:out></td>
					<td style="padding: 5px; font-weight: bold; width: 15%;">Số hộ chiếu cũ</td>
					<td style="padding: 5px; width: 2%;">:</td>
					<td style="padding: 5px; width: 33%;"><c:out
							value="${nicTransaction.prevPassportNo}"></c:out></td>

				</tr>
				<tr>
					<td style="padding: 5px; font-weight: bold; width: 15%;">Loại hộ chiếu</td>
					<td style="padding: 5px; width: 2%;">:</td>
					<td style="padding: 5px; width: 33%;"><c:out
							value="${nicTransaction.passportType}"></c:out></td>
					<td style="padding: 5px; font-weight: bold; width: 15%;">Ngày phát hành HC cũ</td>
					<td style="padding: 5px; width: 2%;">:</td>
					<td style="padding: 5px; width: 33%;"><c:out
							value="${nicTransaction.prevIssueDate}"></c:out></td>
				</tr>
			</table>
		</div>
	</div>




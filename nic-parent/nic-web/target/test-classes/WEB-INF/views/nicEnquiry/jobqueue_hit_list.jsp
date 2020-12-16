<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.doGetAJpgSafeVersion {
	visibility: hidden;
}

.NotTheSame {
	color: #FF0000;
}
</style>

	<c:if test="${empty hitCandidatesList}">
		<span style="color: #FF0000; font-size: 12px;">Không có hồ sơ nào bị trùng.</span>
	</c:if>
	<c:if test="${not empty hitCandidatesList}">
		<!--<display:table cellspacing="1" cellpadding="0" id="row" export="false"
			class="displayTag" name="hitCandidatesList">
			<display:column title="Mã giao dịch" property="transactionId" />
			<display:column title="Tên" property="firstName" />
			<display:column title="Họ" property="surname" />
			<display:column title="Xác minh" property="verifyDecision" />
			<display:column title="Trùng" property="hitFingers" />
			<display:column title="Tỷ lệ trùng" property="matchingScore" />
			<display:column title="Ảnh">
				<img src="data:image/jpg;base64,${row.photo}"
					class="thumbnail doGetAJpgSafeVersion" width="75" height="100" />
			</display:column>
		</display:table>-->
		<table id="tbDetailHits" class="table table-bordered" cellspacing="0" width="100%">
			<thead>
				<tr>
				  <th class="th-sm">STT</th>	
			      <th class="th-sm">Mã giao dịch
			
			      </th>	
			      <th class="th-sm">Họ tên
			
			      </th>
			      <th class="th-sm">Xác minh
			
			      </th>
			      <th class="th-sm">Trùng 
			
			      </th>
			      <th class="th-sm">Tỷ lệ trùng
			
			      </th>
			      
			      <th class="th-sm">Ảnh
			
			      </th>				     							   
			    </tr>
			</thead>
			<tbody>
				<% int stt = 1;%>
				<c:forEach items="${hitCandidatesList}" var="item_">
					<tr>
						<td align="center"><%=stt%></td>
						<td>${item_.transactionId}</td>
						<td>${item_.firstName}</td>
						<td>${item_.verifyDecision}</td>
						<td>${item_.hitFingers}</td>
						<td align="right">${item_.matchingScore}</td>
						<td align="center">
							<img src="data:image/jpg;base64,${item_.photo}"
							class="thumbnail doGetAJpgSafeVersion" width="75" height="100" />
						</td>
					</tr>
					<%stt++; %>
				</c:forEach>
			</tbody>
		</table>
	</c:if>


<script> 
    
	$(document).ready(function() { 		
		$(".doGetAJpgSafeVersion").each(function() {
			var anApplet = document.getElementById('investigationApplet');
			if (!anApplet) {
			    $( this ).css( "visibility", "visible");
				return;
			}
			
			var currentValue=$( this ).attr( "src" );    
			  
			if (!(currentValue.substring(0,27)=='data:image/jpg;base64,     ')) {
			    $( this ).css( "visibility", "visible");
				return;
			}
			 
			var newSrc = 'data:image/jpg;base64,' + document.investigationApplet.getAJpgSafeVersion(currentValue.substring(27)); 
			 
		    $( this ).attr( "src", newSrc);
		    $( this ).css( "visibility", "visible");
		}); 

	});
</script>
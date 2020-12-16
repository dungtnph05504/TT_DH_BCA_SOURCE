<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@page import="org.apache.commons.codec.binary.Base64"%> 
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.nec.asia.nic.investigation.controller.InvestigationHit"%>  
<c:url var="backBtnUrl" value="/servlet/persoLocation/persoLocations" />
<c:url var="homeUrl" value="/servlet/user/home" />			
<!-- TRUNG THÊM THÔNG TIN -->
<c:url var="txnDetailInitUrl" value="/servlet/investigationConfirm/txnDetailTrans" />
<c:url var="startDetailPersoLocationsUrl" value="/servlet/persoLocation/startDetailPersoLocations" />
<!-- END -->			
<c:set var="zero" scope="page" value="0"/>	
<% int zeru = 0;%> 	
	
<script src="<c:url value="/resources/js/mouseover_image.js"/>"
	type="text/javascript"></script> 

													
<style>
	.cls-mg-bot {
	margin-top: 10px;
}
tr {
	height: 35px;
}
.color-table-0 {
	background-color: #fff;
}
.color-table-1 {
	background-color: #F4F9FE;
}
.displayTag tr.odd {
	height: 35px;
}
.displayTag tr.even {
	height: 35px;
}
.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
    white-space: pre-wrap;
}
	.doGetAJpgSafeVersion { 
	     visibility:  hidden;
	} 
	 
	.NotTheSame{
		 color:     #FF0000; 
	}	
													
</style>
													
<style>
	
	.theBlockLeft {
		display: inline-block; 
		width: 43%;
		max-width: 43%;
		margin-right:1%;
		float:left;
	} 
	
	.theBlockRight {
		display: inline-block; 
		width: 43%;
		max-width: 43%;
		margin-right:1%;
		float:left;
	}
	
	.theBlockRightThird {
		display: inline-block; 
		width: 11%;
		max-width: 11%;
		float:right;
	}
	
	.theOneRow {
		margin-top:  5px;
	}

</style>

													
<style>
	
	/*.noHit_theBlock { 
		width: 43%;
		max-width: 43%;
		margin-right:28%;
		margin-left:28%;
	}  */

</style>
	
											
											
											
<style>
     .oneDocArea {
    	 width: 30%;
         margin-right: 2%;
     	 display:  inline-block;
         float:  left;
     }
		
     .theDocArea {
    	 margin-right:4%;
     }

</style>									
										
		
	<body>
		<form:form modelAttribute="formData" name="formData">
		
		<c:if test="${not empty requestScope.Errors}">
		<div style="color:Red;background-color:White;border-style:solid;border-color:Red;border-width:thin;">
			<c:forEach items="${requestScope.Errors}" var="errorMessage">
				<li>
					${errorMessage}
				</li>
			</c:forEach>
			
		</div>
	</c:if>
	
	<c:if test="${not empty requestScope.messages}">
		<div class="border_success">
		<div class="success_left">
		<img align='left'
			src="<c:url value="/resources/images/success.jpg" />" width="30"
			height="30" />
              </div>
	
		
			 <div class="success">
				<table width="600" height="40" border="0" cellpadding="5">
					<tr> 
						<td style="padding-left: 5px;font-weight: bold; vertical-align: top;">
			    			<c:forEach items="${requestScope.messages}" var="infoMessage">
									${infoMessage}
							</c:forEach>
						</td>
					  </tr>
				</table>
			</div>
		</div>
		<br />
	</c:if>
 
<!--Content start-->
	<div id="dialog-approve"></div>
	<div style="padding-left: 20px;padding-right: 20px;background-color: #fff;margin-bottom: 60px;">
		<div>
			<div>
				<div class="row">
					<div class="ov_hidden">
						<div class="new-heading-2">CHI TIẾT ĐIỂM IN VỚI ĐIỂM PHÁT HÀNH</div>

						


						<br />

						
						<fieldset>
							<legend>Chi tiết điểm in và phát hành</legend>
					
						<div style="height: 400px;">
							<table class="table table-bordered table-sm" id="dtBasicExample" cellspacing="0" width="100%">
								<thead>
									<tr>
									<th class="th-sm" style="min-width: 150px;">Điểm in</th>
									<th class="th-sm" style="min-width: 150px;">Điểm phát hành</th>
									<th class="th-sm" style="max-width: 50px;">Chọn</th>
								</thead>
								<tbody>
									<c:forEach var="row" items="${jobList}">											
										<tr>
											<td>
												${persoLocation}
											</td>
											<td>${row.siteId}</td>
											<td align="center">
												 <c:choose>
									                 <c:when test="${row.stagePrint==true}">
									                   <input type="checkbox" id="chk" name="chkSms" 
									                        value="${row.siteId}" checked="checked"/>
									                 </c:when>
									                 <c:otherwise>
									                     <input type="checkbox" id="chk" name="chkSms" 
									                           value="${row.siteId}"/>
									                 </c:otherwise>
									              </c:choose>
											</td>
										</tr>
									</c:forEach> 
								</tbody>
								<c:if test="${empty jobList}">
								  
								   <tbody class="e-not-found ng-scope"><tr>
								  <td colspan="3" style="height: 362px">
								  <div class="e-not-found-center"><div class="e-not-found-title">Không tìm thấy kết quả</div><div class="e-not-found-title-child">Hãy thử tìm kiếm hoặc sử dụng bộ lọc khác</div></div>
								  </td></tr></tbody>
								  
								
								</c:if>
							</table>
						</div>	
						<table class="table e-grid-table e-border">
                             <tfoot>
                                 <tr>
                                     <th>
                                     
                                       <c:if test="${not empty jobList}">
							
											<div class="e-page-rigth">
												<ul style="float: right;" class="pagination" id="pagination"></ul>
											</div>
											<input type="hidden" name="pageNo" id="pageNo" /> 
										</c:if>
                                     
	                                    <div class="e-page-left" style="font-weight: normal;">
											Hiển thị ${startIndex} đến ${endIndex} của ${totalRecord} kết quả
										</div>        
                                    </th>
                                 </tr>
                             </tfoot>
                         </table> 
					</fieldset>



						<%
							/* ******************************************************************************************************************** */
						%>
						<%
							/* ************************************* actions for selected jobs - start ******************************************** */
						%>
						<%
							/* ******************************************************************************************************************** */
						%>

					</div>
				</div>
			</div>
		</div>
	</div>


<div style="display: flex;flex: 0 auto;justify-content: center;">
<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
	<div style="margin: 10px"> 
		<button type="button" class="btn btn-success"  id="backBtn"  name="back">
			<span class="glyphicon glyphicon-remove"></span> Quay lại
		</button>
		<button type="button" class="btn btn-success" onclick="requestApprove();" name="approve">
			<span class="glyphicon glyphicon-ok"></span> Lưu lại
		</button>
	</div>
</div>	
</div>		
<c:url var="updatePersoLocations" value="/servlet/persoLocation/updatePersoLocations" />
<script>
//$('#dtBasicExample').DataTable();
//$('.dataTables_length').addClass('bs-select');
var totalPages = ${totalPage};
var currentPage = ${pageNo};
var pageSize = ${pageSize};
window.pagObj = $('#pagination').twbsPagination({
			totalPages: totalPages,
			visiblePages: pageSize,
			startPage: currentPage,
			next: '<span class=\"glyphicon glyphicon-forward fix-paging\"></span>',
			prev: '<span class=\"glyphicon glyphicon-backward fix-paging\"></span>',
			last: '<span class=\"glyphicon glyphicon-step-forward fix-paging\"></span>',
			first: '<span class=\"glyphicon glyphicon-step-backward fix-paging\"></span>',
			onPageClick: function (event, page) {
				if (currentPage != page) {
					$('#pageNo').val(page);
					document.forms["formData"].action = '${startDetailPersoLocationsUrl}/${idJob}';  
					document.forms["formData"].submit();  
				}
			}
		});	
		$('#backBtn').click(function(e) { 
		    e.preventDefault();  
		    formData.action = "${backBtnUrl}";
		    formData.submit();
		}); 
		
		function requestApprove() { 
			 	  	document.forms["formData"].action = '${updatePersoLocations}';  
				   	document.forms["formData"].submit();  
		}
	
</script>

	<%  /* ******************************************************************************************************************** */ %>	
	<%  /* ******************************************** reject - get remarks - end ******************************************** */ %>	
	<%  /* ******************************************************************************************************************** */ %>	
	<form:input    path="uploadJobId"                             type="hidden" name="uploadJobId"                             value="${idhidden}" />
	</form:form>
</body>

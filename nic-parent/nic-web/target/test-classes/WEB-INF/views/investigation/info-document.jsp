<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<style>

</style>


<c:if test="${empty proofDocList}">
	<span style="color: #FF0000; font-size: 12px;">Không có tài liệu.</span>
</c:if>
<c:if test="${not empty proofDocList}">
	<div>
		<table id="box1_table" width="50%" border="0" cellpadding="0"
			cellspacing="0" bordercolor="#333333"
			style="border: 0px; padding: 5px;">
			<tr>
				<td class="sno" style="font-weight: bold;" height="30px"><span
					class="table_header">Danh sách tài liệu</span></td>
			</tr>
		</table>
	</div>
	<!--  -->
	<div id="mycustomscroll" class='flexcroll'>
		<table  id="box1_table" width="50%" border="0" cellpadding="0"
			cellspacing="0" bordercolor="#333333" class="data_table">
			<c:forEach var="c" items="${proofDocList}" varStatus="status">
														
			  <c:if test="${empty c.dmsLink}">  
				
				<c:choose>
					<c:when test="${c.type eq 'JPEG'}">
						<tr>
							<td class="nricFormat"><c:out
									value="${c.purpose}" /></td>
							<td class="nricFormat">
								<div class="scanDocViewOnly" data-view="${status.index}"
									data-name="${c.documentName}" data-type="${c.type}" >
									<img data-toggle="modal" data-target="#chiTietTaiLieu"
										src=<c:url value='/resources/images/document_icon.jpg'/>
										width="40px" height="40px" title="${c.purpose}" 
										onclick="showProofDoc('${c.transactionNo}', '${c.serialNumber}',	'${c.documentName}','${c.type}','${c.purpose}');"/>
								</div>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td class="nricFormat"><c:out value="${c.purpose}" />
							</td>
							<td class="nricFormat">
								<div class="scanDocViewOnly" data-view="${status.index}"
									data-name="${c.documentName}" data-type="${c.type}" >
									<img data-toggle="modal" data-target="#chiTietTaiLieu"
										src=<c:url value='/resources/images/pdf_icon.jpg'/>
										width="40px" height="40px" title="${c.purpose}" onclick="showProofDoc('${c.transactionNo}', '${c.serialNumber}',	'${c.documentName}','${c.type}','${c.purpose}');"/>
								</div>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
			  </c:if>  
			 								
			  <c:if test="${not empty c.dmsLink}">  
						<tr>
							<td class="nricFormat"><c:out
									value="${c.purpose}" /></td>
							<td class="nricFormat">
								<div class="scanDocViewOnly" data-view="${status.index}"
									data-name="${c.documentName}" data-type="${c.type}" >
									<img
										src=<c:url value='/resources/images/document_icon.jpg'/>
										width="40px" height="40px" title="${c.purpose}"  
										onclick="showImage('<c:out value="${c.dmsLink}" />')" />
								</div>
							</td>
						</tr>
			  </c:if>  
			  
			</c:forEach>
		</table>
	</div>
	</c:if>
	<br />
	
      
    <div id="dialog-scan-doc" title="Scanned Document" style="display: none;">	
	<table>
		<tr>
			<c:forEach var="docItem" items="${proofDocList}" varStatus="status">
				<c:choose>
					<c:when
						test="${docItem.document == null || docItem.document == ''}">
						<div style="display: none;" id="${docItem.documentName}Div">
							<img width="595px" height="842px" style="align: left;"
								src=<c:url value='/resources/images/No_Image.jpg'/>
								id="documentView${status.index}" alt="No Image" />
						</div>
					</c:when>
					<c:otherwise>
						<div style="display: none;" id="${docItem.documentName}Div">
							<c:forEach var="docListItem" items="${docItem.document}"
								varStatus="listItemStatus"> 
								<img style="display: none;" title="${docItem.purpose}"
									src="data:image/jpg;base64,${docListItem}" style="align: center" 
									id="${docItem.documentName}Doc" alt="Proof Document"/>
								
							</c:forEach>
						</div>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</tr>
	</table>
	
	 <!-- JQuery Zoom in and out -->
	 <div class="wrapper">
            <div id="viewer" class="viewer iviewer_cursor"></div>
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


$(function(){
	
});	
	

</script>






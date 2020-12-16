<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<script type="text/javascript">

/*$(function(){
	
	$("#dialog-scan-doc").dialog({
		resizable : false,
		modal : true,
		autoOpen : false,
		width : 800,
		height : 800,
		resizable : false,
		show : {
			effect : "fade",
			duration : 1000
		},
		hide : {
			//effect: "explode",
			duration : 1000
		},
		 open : function() {
		    var data = $('#dialog-scan-doc').data('docView');
		    var data2 = $('#dialog-scan-doc').data('docName');
		    rotImagId = $('#dialog-scan-doc').data('docName'); 
		    //show(data2 + "Div");
		    RotateScanDoc();
		   },
		   close : function() {
		    var data = $('#dialog-scan-doc').data('docView');
		    var data2 = $('#dialog-scan-doc').data('docName');
		    hide(data2 + "Div");
		   }
	});
});	*/
</script>




	<c:if test="${empty proofDocList}">
		<span style="color: #FF0000; font-size: 12px;">Không có tài liệu.</span>
	</c:if>
	<c:if test="${not empty proofDocList}">
		<%--<div class="displayTag" style="background-image:url('<%=request.getContextPath() %>/resource/images/head.png');background-repeat: repeat-x;">
				<display:table id="proofDocList" export="false" class="displayTag"
					name="${proofDocList.rows}" defaultsort="1" sort='external'
					pagesize="${pageSize}" size="${proofDocList.total}" partialList="true"
					requestURI="/servlet/nicEnquiry/getTransDocuments/${txnId}">
					<display:column property="documentName" sortable="true" title="Document Name"
						sortName="documentName" />
					<display:column property="document" 
						title="Document" />
				</display:table>
			</div> --%>
						<div>
							<table id="box1_table" width="50%" border="0" cellpadding="0"
								cellspacing="0" bordercolor="#333333"
								style="border: 0px; padding: 5px;">
								<tr>
									<td class="sno" style="font-weight: bold;" height="30px"><span
										class="table_header" style="padding-left: 40px;">Tên tài liệu</span></td>
									<td class="sno" style="font-weight: bold; padding-left: 15px;"><span
										class="table_header" style="padding-left: 5px;">Tài liệu</span>
									</td>
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
														<img
															src=<c:url value='/resources/images/document_icon.jpg'/>
															width="40px" height="40px" title="${c.purpose}" 
															onclick="showProofDoc('${c.transactionNo}', '${status.index}',	'${c.documentName}','${c.type}','${c.purpose}');"/>
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
														<img
															src=<c:url value='/resources/images/pdf_icon.jpg'/>
															width="40px" height="40px" title="${c.purpose}" onclick="showProofDoc('${c.transactionNo}', '${status.index}',	'${c.documentName}','${c.type}','${c.purpose}');"/>
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
	
	  <!-- <table style="width:100%;">
    	<tr>
        	<td align="center" style="padding: 20px; text-align: right;" >
		      <input type="button" class="button_grey" id="docs_cls_btn"  value="Close"/>
	        </td>
	    </tr>
      </table> -->
      
      <div id="dialog-scan-doc" title="Scanned Document"
	style="display: none;">	
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
      


  <script> 
	  function showImage(url) {
		   
			  window.open(url,'_blank');
			  return;  
	  }
  </script>
  

	
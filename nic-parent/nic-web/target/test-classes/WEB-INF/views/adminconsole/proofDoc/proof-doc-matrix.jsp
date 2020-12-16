<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<c:url var="addProofDocUrl" value="/servlet/proofDocMatrixController/addProofDocMatrix" />
<c:url var="updateProofDocUrl" value="/servlet/proofDocMatrixController/updateProofDoc" />
<c:url var="proofDocMatrixUrl" value="/servlet/proofDocMatrixController/proofDocsMatrix" />

<form:form modalAttribute="proofDocumentDef" commandName="list" id="proofDocumentDef"  name="proofDocumentDef"
	action="/servlet/proofDocMatrixController/paymentMatrix" method="post">
<div id="main">
<div id="content_main">
	<div class="container">
		        	<div class="row">
		        		<div class="roundedBorder ov_hidden">
  <div id="heading_report" align="justify" colspan='2' style='padding:2px'>
        Proof Document</div>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<c:if test="${proofDocumentDef.status=='success'}">
<br />
<div class="border_success" style="display: true">
<br />
<!--<div class="success"> -->
<div class="success_left">
 <img align='left' src="<c:url value="/resources/images/success.jpg" />" width="30" height="30" style="padding:5px 0 0 15px"/>
 
 </div>
<div class="success">
 <table width="800" height="30" border="0" cellpadding="5">
<tr> 
    <td width="587" height="28" style="font-size: 18px; font-weight:bold; color: #000;"> <p class="text_pad">&nbsp;Successfully Deleted The Proof Document Details: <c:out value="${proofDocumentDef.documentId}" /></p>
    
  </tr>
 </table>
</div>
</div>
</c:if>
<c:if test="${proofDocumentDef.status=='fail'}">
<br />
<div class="border_error">
<!--<div class="errors"> -->
<div class="success_left">
 <img align='left' src="<c:url value="/resources/images/alert.png" />"  width="30" height="30" style="padding:5px 0 0 15px"/>
 </div>
<div class="errors"  style="display: none;">

<table width="800" height="30" border="0" cellpadding="5">

  <tr>
    <td width="587" height="28" style="font-size: 18px; font-weight:bold; color: #000;"> <p class="text_pad">&nbsp;Failed To Delete Proof Document Details: <c:out value="${proofDocumentDef.documentId}" /></p>
  </tr>
 
</table>

</div>
</div>
</c:if>

<br /><br />
 <div id="TabbedPanels1" class="TabbedPanels">
	   <ul class="TabbedPanelsTabGroup">
	     <li class="TabbedPanelsTab" id="proofDocList" >Proof Document List</li>
         <li class="TabbedPanelsTab" id="proofDocMatrix" >Proof Document Matrix</li>
	   </ul>
	   <div class="TabbedPanelsContentGroup">
	     <div class="TabbedPanelsContent">

	<display:table cellspacing="1" cellpadding="0"  sort="external"  id="row" partialList="true" size="${totalRecords}"
				export="false" class="displayTag" name="list" defaultsort="1"
				 pagesize="${pageSize}"
				requestURI="/servlet/proofDocMatrixController/proofDocMatrix">
				
	
             <display:column title="Document Id" sortable="true" sortName="id.documentId">
				<c:url var="updateProofDocUrl1" value="/servlet/proofDocMatrixController/updateProofDoc/${row.id.documentId},${row.documentDesc},${row.id.transactionType},${row.id.transactionSubtype},${row.requireIndicator}" />
						 <a href="${updateProofDocUrl1}"> 
						 	<c:out value="${row.id.documentId}" /> 
						 </a>
				</display:column>
				<display:column  sortName="documentDesc" property="documentDesc" sortable="true" title="Document Name" maxLength="20" />
				<display:column property="id.transactionType" sortable="true" sortName="id.transactionType"  title="Transaction Type" maxLength="30" />
                 <display:column property="id.transactionSubtype" sortName="id.transactionSubtype" sortable="true" title="Transaction SubType" maxLength="30" />
					<display:column  property="createBy" sortable="true" sortName="createBy"
					title="Created By" maxLength="30" />
					<display:column property="createDate" sortable="true" sortName="createDate" title="Created Date" maxLength="30"  format="{0,date,dd-MMM-yyyy}"/>
					<display:column property="updateBy" sortable="true" sortName="updateBy" 
					title="Updated By" maxLength="30" />
					<display:column property="updateDate" sortName="updateDate"  sortable="true" title="Updated Date" maxLength="30"  format="{0,date,dd-MMM-yyyy}"/>
					<display:column property="requireIndicator" sortable="true" sortName="requireIndicator" 
					title="Indicator" maxLength="10" />
	</display:table>

	<br />
	<div id="" align="right">
	<input type="button" class="button_grey" id="addBtn"  value="Add"/>
     </div>
	</div>

<div class="TabbedPanelsContent" style="background-color: lightblue;">
  <br />
 <table width="95%" border="1"  style="font-size: 7pt;margin-left: 30px;">
 
  <tr>
    <td width="50" rowspan="3" align="center">No.</td> 
	<td width="180" rowspan="3" align="center">Document</td>
  </tr>
 
  <tr height="38" border="1"																																																																																												>
   <!-- Start Dynamic colspan added by Prasad -->
    <td height="65" colspan="${proofDocumentDef.newRegistration.size()}" align="center">New Registration</td> 
    <td colspan="${proofDocumentDef.replacement.size()}" align="center">Replacement</td>  
    <td width="57" colspan="${proofDocumentDef.updateParticulars.size()}" align="center">Update Particulars</td>
   <!--End  Dynamic colspan added by Prasad -->
  </tr>
  
  <tr>
   <c:forEach var="regMap" items="${proofDocumentDef.newRegistration}" varStatus="status1">  
    <td width="70" height="30" align="left">${regMap}</td>   
  </c:forEach>
    <c:forEach var="repMap" items="${proofDocumentDef.replacement}" varStatus="status2">   
    <td width="75" height="3" align="left">${repMap}</td>  
   </c:forEach>
   <c:forEach var="upMap" items="${proofDocumentDef.updateParticulars}" varStatus="status3">   
    <td width="70" height="30" align="left">${upMap}</td>  
   </c:forEach>		
  </tr>
  <%int i=1;%>
  <c:forEach var="clmMap" items="${proofDocumentDef.columnMap}" varStatus="status6">
  
  <tr height="38">
  <td align="center"><%=i%></td>
    <td align="center"><c:out value="${clmMap.key}" /></td>
     <c:forEach var="clmMapVal" items="${clmMap.value}" varStatus="status7">
        <td style="padding-left: 5px;">${clmMapVal}</td>
       
	 </c:forEach>
  </tr> 
   <%++i;%>
  </c:forEach>
</table>
 </div>

 </div>
 </div>
</div>	
</div>
</div>
</div>
</div>
 </form:form>
<div id="dialog-confirm"></div>
<!-- script for the delete -->
<script>
  $(function() {
    $( "#dialog-confirm" ).dialog({
	modal: true,
      autoOpen: false,
	  width : 500,
	  resizable: true,
      show: {
        effect: "fade",
        duration: 200
      },
      hide: {
        //effect: "explode",
        //duration: 1000
      },
	   buttons:{
    Ok: function() {
		window.location.assign("proof-doc-matrix-one.html");
    },
	Cancel: function() {
		$(this).dialog("close");
    }
   }
    });
  });
</script>
<script type="text/javascript">
        function deleteProofDoc(REG00006, obj){
            $("#dialog-confirm").dialog('option', 'title', 'Delete Proof Document');
            $("#dialog-confirm").html('Do you want to delete the Proof Document('+REG00006+')?<br>Please OK to confirm.');
            $("#dialog-confirm").dialog( 'open' );
        }
    </script>
<script type="text/javascript">
var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1");

/* $('#proofDocMatrix').die();
$("#proofDocMatrix").click(function() {	
alert("proofmatrix");
	 document.forms["proofDocumentDef"].action = '${proofDocMatrixUrl}';
	 document.forms["proofDocumentDef"].submit();
	
}); */

</script>
<script>
$("#addBtn").click(function(){	
	 document.forms["proofDocumentDef"].action = '${addProofDocUrl}';
	 document.forms["proofDocumentDef"].submit();	

});

</script>
</body>

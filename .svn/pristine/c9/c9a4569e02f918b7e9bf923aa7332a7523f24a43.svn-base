<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<c:url var="url" value="/servlet/sftpStatistic/view"/>
<style>
	#transList a hover { color: #0e4954; text-decoration: underline; font-weight: bold; }
	#transList a:focus { color: #ff9124; font-weight: bold; }
	
	
}
</style>

<script type="text/javascript">

$("#refresh_btn").click(function() {
	
/* 	if(validateForm()) {
		document.forms["statRptForm"].action = '${search}';
		document.forms["statRptForm"].submit();
	}
 */	
});


function onRefresh () {
	document.forms[0].action = '${url}';
	document.forms[0].submit();
	
}
	
</script>
<form  method="get">
 <div class="main_content">
 <div id="heading_report" align="justify" style='padding:2px'>Total Number of Files in each Folder</div>
  <br /><br /><br />
               <div id="dropdown3" class="displayTag">
                    <table id="tablepaging" class="displayTag" border="4" cellpadding="4"   >
                      	<tr style="background-color: #E3F1FE;" style="font-family:verdana;" >
                      		<td align="center">
                      			<b> Perso Data </b>
                      		</td>
                      		<td>
                      			<b> Dispatch Info </b>
                      		</td>
                      		<td>
   			                   	<b> Import </b>
                      		</td>
                      		<td>
                      			<b> Pending Perso </b>
                      		</td>
                      		<td>
                      			<b> Printed </b>
                      		</td>
                      		<td>
                      			<b> QC Completed </b>
                      		</td>
                      		<td>
                      			<b> Rejected </b>
                      		</td>
                      		<td>
                      			<b> Error </b>
                      		</td>
                      	</tr>
                      	<tr style="background-color: #FFFFFF;" >
                      		<td> <div align="right"> ${persoData} </div>  </td>
                      		<td> <div align="right"> ${dispatchInfo} </div>  </td>
                      		<td> <div align="right"> ${imported} </div> </td>
                      		<td> <div align="right"> ${pendingPerso} </div> </td>
                      		<td> <div align="right"> ${printed} </div> </td>
                      		<td> <div align="right"> ${qcCompleted} </div> </td>
                      		<td> <div align="right"> ${rejected} </div> </td>
                      		<td> <div align="right"> ${error} </div> </td>
                      		
                      	</tr>
                    </table>
                    <table style="width: 100%; text-align: right;">
				<tr>
					<td colspan="2" align="right" style="padding: 10px; text-align: right;">
						<input type="button" id="refresh_btn" class="button_grey" value="Refresh" onclick="javascript:onRefresh();" />&nbsp; 
					</td>
				</tr>
			</table>
 		</div>
 		
 </div>
  <div id="msgDialog" title="Alert" />
   <div align="left">
 <ol id="msgDialogSpan" >
 </ol>
</div>
</form>
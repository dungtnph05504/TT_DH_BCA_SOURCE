<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- <script type="text/javascript">
$(function(){
	$("#demographicInfoClose_btn").click(function(){
		 $("#dialog-approve").dialog('close');	
	});
	
});
</script> -->


<form:form modelAttribute="nicEnqdemographicForm" id="form1" action="/servlet/nicEnquiry/search" method="post">
 <div id="inner_main" style="font-size:12px;">
     <div id="inner_main_left" style="font-size:12px;">
     <table width="100%" height="200" border="0" style="font-size:12px;">
<tr>
  <td width="320" height="35" align="center" style="font-weight: bold" class="sno"><span class="table_header">Người nộp đơn</span></td>
</tr>
<tr>
  <td class="img-border"><table width="100%" border="0" cellpadding="2" class="data_table2">
    <tr>
      <td style="font-size:12px;">CMND/CCCD</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="rnin" style="font-size:12px;"><c:out value="${ricAndCpdData.nin}"/></span></td>
    </tr>
    <tr>
      <td style="font-size:12px;">Họ</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="rsurname" style="font-size:12px;"><c:out value="${ricAndCpdData.surnameFull}"/></span></td>
    </tr>
	  <tr>
      <td style="font-size:12px;">Tên</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="rfname" style="font-size:12px;"><c:out value="${ricAndCpdData.firstnameFull}"/></span></td>
    </tr>
	   <tr>
   <!--    <td style="font-size:12px;">Surname at Birth</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="rsurnameBirth" style="font-size:12px;"><c:out value="${ricAndCpdData.surnameBirthFull}"/></span></td>
    </tr>-->
    <tr>
      <td style="font-size:12px;">Giới tính</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="rgender" style="font-size:12px;"><c:out value="${ricAndCpdData.gender}"/></span></td>
    </tr>
    <tr>
      <td style="font-size:12px;">Ngày sinh</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="rdateOfBirth" style="font-size:12px;"><c:out value="${ricDOB}"/></span></td>
    </tr>
    <tr>
      <td style="font-size:12px;">Địa chỉ</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="raddressLine1" style="font-size:12px;"><c:out value="${ricAndCpdData.addressLine1}"/></span></td>
    </tr>
     <tr>
   <!--     <td style="font-size:12px;">Phố, đường</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="raddressLine2" style="font-size:12px;"><c:out value="${ricAndCpdData.addressLine2}"/></span></td>
    </tr>-->
  <!--  <tr>
      <td style="font-size:12px;">Locality</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="raddressLine3" style="font-size:12px;"><c:out value="${ricAndCpdData.addressLine3}"/></span></td>
    </tr>-->
 <!--   <tr>
      <td style="font-size:12px;">City/Town/Village</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="raddressLine4" style="font-size:12px;"><c:out value="${townVillageDesc}"/></span></td>
    </tr>-->
 <!--   <tr>
      <td style="font-size:12px;">District/Outer Island</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="raddressLine5" style="font-size:12px;"><c:out value="${districtDesc}"/></span></td>
    </tr>-->
  <!--   <tr>
      <td style="font-size:12px;">Postal Code</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="raddressLine6" style="font-size:12px;"><c:out value="${ricAndCpdData.addressLine6}"/></span></td>
    </tr>-->
	 <!--   <tr>
      <td style="font-size:12px;">Tình trạng hôn nhân</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="rmaritalStatus" style="font-size:12px;"><c:out value="${ricAndCpdData.maritalStatus}"/></span></td>
    </tr>
	   <tr>
      <td style="font-size:12px;">Father's Name</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="rfatherName" style="font-size:12px;"><c:out value="${ricAndCpdData.fatherName}"/></span></td>
    </tr>
	   <tr>
      <td style="font-size:12px;">Father's Surname</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="rfatherSurname" style="font-size:12px;"><c:out value="${ricAndCpdData.fatherSurname}"/></span></td>
    </tr>
	   <tr>
      <td style="font-size:12px;">Mother's Name</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="rmotherName" style="font-size:12px;"><c:out value="${ricAndCpdData.motherName}"/></span></td>
    </tr>
	   <tr>
      <td style="font-size:12px;">Mother's Surname</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="rmotherSurname" style="font-size:12px;"><c:out value="${ricAndCpdData.motherSurname}"/></span></td>
    </tr> -->
  </table></td>
</tr>
</table>


     </div>
     <div id="inner_main_right">
      <table width="100%" height="200" border="0" style="font-size:12px;">
<tr>
  <td width="320" height="35" align="center" style="font-weight: bold" class="sno"><span class="table_header">Đối tượng trùng</span></td>
</tr>
<tr>
  <td class="img-border" style="font-size:12px;"><table width="100%" border="0" cellpadding="2" class="data_table2" style="font-size:12px;">
   <tr>
      <td style="font-size:12px;">CMND/CCCD</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="cnin" style="font-size:12px;"><c:out value="${ricAndCpdData.cpdNin}"/></span></td>
    </tr>
    <tr>
      <td style="font-size:12px;">Họ</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="csurname" style="font-size:12px;"><c:out value="${ricAndCpdData.cpdSurnameFull}"/></span></td>
    </tr>
	   <tr>
      <td style="font-size:12px;">Tên</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="cfname" style="font-size:12px;"><c:out value="${ricAndCpdData.cpdFirstnameFull}"/></span></td>
    </tr>
	   <tr>
  <!--   <td style="font-size:12px;">Surname at Birth</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="csurnameBirth" style="font-size:12px;"><c:out value="${ricAndCpdData.cpdSurnameBirthFull}"/></span></td>
    </tr>-->
    <tr>
      <td style="font-size:12px;">Giới tính</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="cgender" style="font-size:12px;"><c:out value="${ricAndCpdData.cpdGender}"/></span></td>
    </tr>
    <tr>
      <td style="font-size:12px;">Ngày sinh</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="cdateOfBirth" style="font-size:12px;"><c:out value="${cpdDOB}"/></span></td>
    </tr>
    <tr>
      <td style="font-size:12px;">Địa chỉ</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="caddressLine1" style="font-size:12px;"><c:out value="${ricAndCpdData.cpdAddressLine1}"/></span></td>
    </tr>
     <tr>
  <!--     <td style="font-size:12px;">Street</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="caddressLine2" style="font-size:12px;"><c:out value="${ricAndCpdData.cpdAddressLine2}"/></span></td>
    </tr>
    <tr>
      <td style="font-size:12px;">Locality</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="caddressLine3" style="font-size:12px;"><c:out value="${ricAndCpdData.cpdAddressLine3}"/></span></td>
    </tr>
    <tr>
      <td style="font-size:12px;">City/Town/Village</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="caddressLine4" style="font-size:12px;"><c:out value="${cpdTownVillageDesc}"/></span></td>
    </tr>
    <tr>
      <td style="font-size:12px;">District/Outer Island</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="caddressLine5" style="font-size:12px;"><c:out value="${cpdDistrictDesc}"/></span></td>
    </tr>
     <tr>
      <td style="font-size:12px;">Postal Code</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="caddressLine6" style="font-size:12px;"><c:out value="${ricAndCpdData.cpdAddressLine6}"/></span></td>
    </tr>-->
	   <tr>
      <td style="font-size:12px;">Tình trạng hôn nhân</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="cmaritalStatus" style="font-size:12px;"><c:out value="${ricAndCpdData.cpdMaritalStatus}"/></span></td>
    </tr>
	   <tr>
  <!--    <td style="font-size:12px;">Father's Name</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="cfatherName" style="font-size:12px;"><c:out value="${ricAndCpdData.cpdFatherName}"/></span></td>
    </tr>
	   <tr>
      <td style="font-size:12px;">Father's Surname</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="cfatherSurname" style="font-size:12px;"><c:out value="${ricAndCpdData.cpdFatherSurname}"/></span></td>
    </tr>
	   <tr>
      <td style="font-size:12px;">Mother's Name</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="cmotherName" style="font-size:12px;"><c:out value="${ricAndCpdData.cpdMotherName}"/></span></td>
    </tr>
	   <tr>
      <td style="font-size:12px;">Mother's Surname</td>
      <td style="font-size:12px;">:</td>
      <td style="font-size:12px;"><span id="cmotherSurname" style="font-size:12px;"><c:out value="${ricAndCpdData.cpdMotherSurname}"/></span>
       <input type="hidden" id="jid" name="jid">
       <input type="hidden" id="txnId" name="txnId">
      </td>
      
    </tr>-->
   
  </table></td>
</tr>
  </table>
  
    
      
       </div>
       <div class="c"></div>
     
     </div>
	<br />
	<br />
	
	 <!-- <table style="width:100%;">
    	<tr>
        	<td align="center" style="padding: 20px; text-align: right;" >
		      <input type="button" class="button_grey" id="demographicInfoClose_btn"  value="Close"/>
	        </td>
	    </tr>
      </table> -->
</form:form>


	
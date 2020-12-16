<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="clickTransactionUrl" value="/servlet/investigationAssignment/clickTransaction" />
<style>
.close {
  position: absolute;
  top: 15px;
  right: 35px;
  color: #f1f1f1;
  font-size: 40px;
  font-weight: bold;
  transition: 0.3s;
}

.close:hover,
.close:focus {
  color: #bbb;
  text-decoration: none;
  cursor: pointer;
}

#myImg {
  border-radius: 5px;
  cursor: pointer;
  transition: 0.3s;
}

#myImg:hover {opacity: 0.7;}

/* The Modal (background) */
.modalImg  {
  display: none; /* Hidden by default */
  position: fixed; /* Stay in place */
  z-index: 500; /* Sit on top */
  padding-top: 100px; /* Location of the box */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: rgba(0,0,0,0.9); /* Black w/ opacity */
}

/* Modal Content (image) */
.modal-content-img {
  margin: auto;
  display: block;
  width: 80%;
  max-width: 700px;
}



/* Add Animation */
.modal-content-img  {  
  -webkit-animation-name: zoom;
  -webkit-animation-duration: 0.6s;
  animation-name: zoom;
  animation-duration: 0.6s;
}

@-webkit-keyframes zoom {
  from {-webkit-transform:scale(0)} 
  to {-webkit-transform:scale(1)}
}

@keyframes zoom {
  from {transform:scale(0)} 
  to {transform:scale(1)}
}
/* 100% Image Width on Smaller Screens */
@media only screen and (max-width: 700px){
  .modal-content-img {
    width: 100%;
  }
  
.scheduler-border {
	padding-left: 0px !important;
    padding-right: 0px !important;
} 

#dataTables_wrapper {
	padding: 0px !important;
}  
</style>
<div class="col-sm-12 none-padding pad-top-10">
      <div class="col-lg-12 mar-bottom-20">
          <div class="e-col-sm-7 col-sm-7">
              <fieldset class="scheduler-border">
                  <legend class="scheduler-border">Danh sách hồ sơ</legend>
                  <div class="col-sm-12" style="height:335px; overflow:auto;padding: 0px;">
                      
                  <table id="tbChiTietDS" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
                          <thead>
                              <tr>
                                  <th class="e-cursor-pointer" style="background-color: rgb(230, 230, 230); position: relative; top: -0.7px;"><div class="e-th-table-boder">Số biên nhận</div></th>
                                  <th class="e-cursor-pointer" style="background-color: rgb(230, 230, 230); position: relative; top: -0.7px;"><div class="e-th-table-boder">Số hồ sơ</div></th>
                                  <th class="e-cursor-pointer" style="background-color: rgb(230, 230, 230); position: relative; top: -0.7px;"><div class="e-th-table-boder">Họ tên</div></th>
                                  <th class="e-cursor-pointer" style="background-color: rgb(230, 230, 230); position: relative; top: -0.7px;"><div class="e-th-table-boder">G.Tính</div></th>
                                  <th class="e-cursor-pointer" style="background-color: rgb(230, 230, 230); position: relative; top: -0.7px;"><div class="e-th-table-boder">Ngày sinh</div></th>
                              </tr>
                          </thead>
                          <tbody>
                          	  <c:forEach items="${dsChiTiet}" var="item">
	                              <tr>	               
	                                  <td>${item.recieptNo}</td>
	                                  <td>${item.transactionId}</td>
	                                  <td>${item.fullName}</td>
	                                  <td>${item.gender}</td>
	                                  <td class="align-central">${item.dob}</td>
	                              </tr>
                          	  </c:forEach>	
                          </tbody>
                      </table></div>
              </fieldset>
          </div>
          <div class="e-col-sm-5 col-sm-5">
              <fieldset class="scheduler-border">
                  <legend class="scheduler-border">Thông tin chi tiết</legend>
                  <table width="100%" class="borderless">
                      <tbody><tr>
                          <td colspan="2">Ảnh hồ sơ</td>
                          <td colspan="2">Ảnh tờ khai</td>
                      </tr>
                      <tr>
                          <td colspan="2">
                             	<c:choose>
				                    <c:when test="${not empty dataRoot.imgFacial}">
				                        <div id="anhMat">
				                            <img src="data:image/jpg;base64,${dataRoot.imgFacial}"
				                                 class="img-border doGetAJpgSafeVersion" height="180" width="135" />
				
				                        </div>
				                    </c:when>
				                    <c:otherwise>
				                        <div id="anhMat">
				                      
				                            <img src="<c:url value='/resources/images/No_Image.jpg' />" class="img-border" height="180" width="135" />										                           
				                        </div>
				                    </c:otherwise>
				                </c:choose>		
                          </td>
                          <td colspan="2">
                                <c:choose>
				                    <c:when test="${not empty dataRoot.imgDoc}">
				                        <div id="anhToKhai">
				                            <img src="data:image/jpg;base64,${dataRoot.imgDoc}" id="zoomImg"
				                                 class="img-border doGetAJpgSafeVersion" height="180" width="135" />
				
				                        </div>
				                    </c:when>
				                    <c:otherwise>
				                        <div id="anhToKhai">
				                      
				                            <img src="<c:url value='/resources/images/No_Image.jpg' />" id="zoomImg" class="img-border" height="180" width="135" />										                           
				                        </div>
				                    </c:otherwise>
				                </c:choose>	
                          </td>
                      </tr>
                      <tr>
                          <td>Ngày lập DS</td>
                          <td colspan="2">Số CMND/CCCD</td>
                          <td>Nơi sinh</td>
                      </tr>
                      <tr style="border:1px solid">
                          <td>${dataRoot.createDateDS}</td>
                          <td colspan="2"><span id="idCMND">${dataRoot.nin}</span></td>
                          <td><span id="idPOB">${dataRoot.pob}</span></td>
                      </tr>
                      <tr>
                          <td colspan="4" style="text-align: left">Nơi thường trú</td>
                      </tr>
                      <tr style="border:1px solid">
                          <td colspan="4"><span id="idDiaChi">${dataRoot.address}</span></td>
                      </tr>
                      <tr>
                          <td colspan="4" style="text-align: left">Ý kiến địa phương</td>
                      </tr>
                      <tr style="border:1px solid;height: 35px;">
                          <td colspan="4"><span id="idYKien"></span></td>
                      </tr>
                  </tbody></table>
              </fieldset>
          </div>
      </div>
  </div>
  <div id="myModalImg" class="modalImg">
	  <span id="closeImg" class="close">&times;</span>
	  <img class="modal-content-img" id="img01">
	  
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

var modal = document.getElementById("myModalImg");
var img = document.getElementById("zoomImg");
var modalImg = document.getElementById("img01");

img.onclick = function(){
  modal.style.display = "block";
  modalImg.src = this.src;
}

var tbXNC = $('#tbChiTietDS').DataTable({
	"ordering": false,
	"pagingType": "simple"
});
var ID_DXNC = '';
$(document).ready(function() {
	
	$('#tbChiTietDS_wrapper .dataTables_length').hide();
	$("#tbChiTietDS tbody tr:first").addClass("back-gr");
	$('#tbChiTietDS tbody').on('click', 'tr', function(){
   	 var dataNT = tbXNC.row(this).data();
   	 if(ID_DXNC == dataNT[1]){
   		 return;
   	 }   	
   	 $('#tbChiTietDS > tbody > tr').removeClass("back-gr");
   	 $(this).addClass("back-gr");   	
   	 ID_DXNC = dataNT[1];
     var Url = '${clickTransactionUrl}/' + ID_DXNC;					
	 $.ajax({
		type : "POST",
		url : Url,
		success: function(data) {
			//alert(data.passportType);
			img.src = data.imgDoc;
			$('#anhMat').html(data.imgFacial);
			//$('#anhToKhai').html(data.imgDoc);
			$('#idCMND').text(data.nin);
			$('#idPOB').text(data.pob);
			$('#idDiaChi').text(data.address);
			//$('#mucDichXNC').val(data.purposeCode);
	    },
	    error: function(e){}
	 });	
   });
	
   $('#closeImg').click(function(){
	   $('#myModalImg').hide();
   });
});
	

</script>






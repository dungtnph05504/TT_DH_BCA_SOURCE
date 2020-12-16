<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:url var="changeStatusDetail" value="/servlet/central/changeStatus" />
<style>
.style-width-100 {
	width: 100%;
}

#ajax-load-qa {
	background: rgba(255, 255, 255, .6)
		url('<c:url value="/resources/images/loading_nin.gif" />') 50% 50%
		no-repeat;
	position: fixed;
	z-index: 100;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	display: none;
	text-align: center;
}

.toggle {
	border-radius: 20px;
}
</style>
<div class="content-item-main" style="margin: 0px 50px 0px 50px;">
	<form modelAttribute="formDataAdd" name="formDataAdd"
		autocomplete="off" id="formData" enctype="multipart/form-data">

		<div class="content-item-information">
			<div class="col-sm-12">
				<fieldset class="scheduler-border">
					<legend>Nhập thông tin Passport</legend>
					<div class="col-sm-6">
					<label
							class="col-sm-4 control-label text-right st-mgt-10">Tên quốc gia<span style="color: red; padding-left: 5px;"></span>
						</label>
						<div class="col-sm-8 st-mgt-10">
						<input path="nameNationVie" class="style-width-100"
								id="nameNationVie" placeholder="" value="${queue.nameNationVie}" readonly="readonly" />
						</div>
						<br></br> <label
							class="col-sm-4 control-label text-right st-mgt-10">Kiểu hộ chiếu<span style="color: red; padding-left: 5px;"></span>
						</label>
						<div class="col-sm-8 st-mgt-10">
							<input path="nameNationVie" class="style-width-100"
								id="nameNationVie" placeholder="" value="${queue.passPortType}" readonly="readonly" />
						</div>

						<br></br> <label
							class="col-sm-4 control-label text-right st-mgt-10">Năm phát hành<span
							style="color: red; padding-left: 5px;"></span></label>
						<div class="col-sm-8 st-mgt-10">
						<input path="nameNationVie" class="style-width-100"
								id="nameNationVie" placeholder="" value="${queue.yearIssue}" readonly="readonly" />
						</div>
					</div>
					<div class="col-sm-6">
					 <label
							class="col-sm-4 control-label text-right st-mgt-10">Miêu tả<span
							style="color: red; padding-left: 5px;"></span></label>
						<div class="col-sm-8 st-mgt-10">
							<input rows="4" path="description" id="desc"
								class="style-width-100"  readonly="readonly" cols="" value="${queue.description}" />
						</div>
					</div>

				</fieldset>
			</div>
		</div>
		<div class="content-item-information">
			<div class="col-sm-12">
				<fieldset class="scheduler-border">
					<legend>Ảnh</legend>
					<div class="col-sm-12">
						<label class="col-sm-2 control-label text-right st-mgt-10">Ảnh
							mặt trước<span style="color: red; padding-left: 5px;"></span>
						</label>
						<div class="col-sm-10 st-mgt-10">
							
	<c:choose>
	<c:when test="${imgpre eq ''}">
	<label>Không tìm thấy ảnh</label>
	</c:when>
	<c:otherwise>
	<img alt="" src="data:image/png;base64, ${imgpre}" style="width: 80%; padding: 10px 10px 10px 10px;">
	</c:otherwise>
	</c:choose>
						</div>
						</div>
						<br></br>
						<hr/>
						<div class="col-sm-12">
						<br></br> <label
							class="col-sm-2 control-label text-right st-mgt-10">Ảnh
							mặt trước UV<span style="color: red; padding-left: 5px;"></span>
						</label>
						<div class="col-sm-10 st-mgt-10">
							
	<c:choose>
	<c:when test="${imgpreuv eq ''}">
	<label>Không tìm thấy ảnh</label>
	</c:when>
	<c:otherwise>
	<img alt="" src="data:image/png;base64, ${imgpreuv}" style="width: 80%; padding: 10px 10px 10px 10px;">
	</c:otherwise>
	</c:choose>
						
					

					</div>
					</div>
					<br></br>
						<hr/>
					<div class="col-sm-12">
						<label class="col-sm-2 control-label text-right st-mgt-10">Ảnh
							mặt<span style="color: red; padding-left: 5px;"></span>
						</label>
						<div class="col-sm-10 st-mgt-10">
							
	<c:choose>
	<c:when test="${imgface eq ''}">
	<label>Không tìm thấy ảnh</label>
	</c:when>
	<c:otherwise>
	<img alt=""  src="data:image/png;base64, ${imgface}" style="width: 80%; padding: 10px 10px 10px 10px;">
	</c:otherwise>
	</c:choose>
						</div>
						
					</div>
					<br></br>

				</fieldset>
			</div>
		</div>

		<div id="ajax-load-qa"></div>
	
	<!-- 	<div class="col-sm-12">
			<div style="display: flex; flex: 0 auto; justify-content: center;">
				<div class="waitingWhenDoneWaiting"
					style="display: block; position: fixed; bottom: -5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000; border-color: #0c537f #0c537f transparent #0c537f;">
					<div style="margin: 10px">
						<button type="button" onclick="addPassport();" name="approve"
							class="btn btn-success">
							<span class="glyphicon glyphicon-ok"></span> Thêm Passport
						</button>
					</div>
				</div>
			</div>
		</div> -->

		<script type="text/javascript">
		 $('input[id^="statusDetail"]').change(function(e) {
			 $('#ajax-load-qa').css('display', 'block');
				$('#ajax-load-qa').show();
				var url = '${changeStatusDetail}/' + ${queue.id};
				var status = '';
				alert(url);
				if ($(this).is(":checked"))
				{
					status = 'true';
				}else{
					status = 'false';
				}
				var obj = {
						"status" : status 
				};
					$.ajax({
								url : url,
								data : JSON.stringify(obj),  
								dataType : 'json',
								contentType : 'application/json; charset=utf-8',
							    method: 'POST',
								success : function(data) {
									if (data == 1) {
										$('#ajax-load-qa').hide();
										$.notify('Thay đổi trạng thái thành công', {
											globalPosition : 'bottom left',
											className : 'success',
										});
									} else {
										$('#ajax-load-qa').hide();

										$.notify('Thay đổi trạng thái thất bại', {
											globalPosition : 'bottom left',
											className : 'warn',
										});
									}
								},
								error : function(e) {
									$('#ajax-load-qa').hide();

									$.notify('Có lỗi ', {
										globalPosition : 'bottom left',
										className : 'warn',
									});
								}
							});
			});
		</script>

	</form>
</div>







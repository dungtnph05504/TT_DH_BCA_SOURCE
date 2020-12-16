<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
			<c:url var="savePassport" value="/servlet/central/editTemplatePassport" />
			<c:url var="notfound" value="iVBORw0KGgoAAAANSUhEUgAAAKUAAAAjCAYAAAD1wsofAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAALUSURBVHhe7ZeLkYQgDIZty4Zsx262mS2GIzzkoZjA6pjd+78Z587FhCT8RJwMAMqAKIE6IEqgDogSqAOiBOqAKIE6IEqgDogSqAOiBOqAKIE6IEqgDogSmNcymWlazCvcP82JKF9mmSaz5JG+Fht8/O1t1rkaP8Q/pynpiF8Me/FJ3MBI/fyaTPNqf72W9zqbee3xKo2/H7ko36uZC5EKggo2lCwl/YQw3bynixgW+o7qWtrzSxbVP3N7/Wyz6RMk8bgoU3ES9wV1Jbwo7+UzUWrmYVEeFzYG5f+61+A0m3LD5WN05Ts92odOJbL3l2RX+86yt83nKJ+puhAdVWzy8RWf8qxjPIafP+af57j3vR0x3JVi9P7rzulr2dP1Wv5TfK31kcU/Ai/KlV7BdfJECibuFpfgJt4wnm2lspDRPiVS2of7zd4/3/uacXNyndKdlascw/mZ5osCiwvQE0N7fq5+3jYrXzWemsYGxczlmnHun1sfPv5RWFHSpPsdSfigmkVx58naLi8kY39QdLfAhQFPWxQZTVH635IPH/OVomznf0A17kSwOeiPbUfhn4tvIH4hF7y+wy2RB0X/70SZ2/BJXVH0rxZl+FD0jSFcu/EQ92ETYDj1z8UniH+Qrg+dsksxQWWLmujplHRbFax4WMb3itLXqpirqk/0Qc/QPH0blvPPxcev3yhCURJ1ErKi5kI6OpOc219wcCafnB/3zD2ibM8vq1+aK9RzGw84m8Usc2+tOP9cfNz4OB2itLgCx0QkQYVE41WM8fZODLn9zoeMsuPGxfPzF77zcZfrBaK0nM0vz9/arXV9iVDjwpGMc/9cfHz8o5yI8mHceScuYKTe3SDWZECTatErStepKlGGg/kvLcCnlEei30CvKC27Dx0IcmOrzY8JklAtSvA/gSiBOiBKoA6IEqgDogTqgCiBOiBKoA6IEqgDogTqgCiBMoz5A97w2sXgKqyFAAAAAElFTkSuQmCC"/>
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

	<form:form modelAttribute="formDataAdd" name="formDataAdd"
		autocomplete="off" enctype="multipart/form-data" id="import-form">
		<div class="content-item-information">
			<div class="col-sm-12">
				<fieldset class="scheduler-border">
					<legend>Nhập thông tin Passport</legend>
					<div class="col-sm-6">
						<label class="col-sm-4 control-label text-right st-mgt-10">Nation<span
							style="color: red; padding-left: 5px;"></span></label>
						<div class="col-sm-8 st-mgt-10">
							<input path="nameNationVie" class="style-width-100"
								id="nameNationVie" placeholder="" value="${queue.nation}" readonly="readonly" />
						</div>
						<br></br> <label
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
						<br></br> <label
							class="col-sm-4 control-label text-right st-mgt-10">Description<span
							style="color: red; padding-left: 5px;">*</span></label>
						<div class="col-sm-8 st-mgt-10">
							<input rows="4" path="description" id="desc"
								class="style-width-100" cols="" value="${queue.description}" />
						</div>
					</div>

				</fieldset>
			</div>
		</div>
	
		<div class="content-item-information">
				<fieldset class="scheduler-border">
					<legend>Ảnh</legend>
					<div class="col-sm-12">
						<label class="col-sm-2 control-label text-right st-mgt-10">Ảnh
							mặt trước<span style="color: red; padding-left: 5px;"></span>
						</label>
						<div class="col-sm-10 st-mgt-10">
							<input type="file" name="imgPre" id="imgPre" />
							<c:choose>
	<c:when test="${imgpre eq ''}">
	<img alt="" id="resultImgPre" src="data:image/png;base64, ${notfound}" style="width: 80%; padding: 10px 10px 10px 10px;">
	</c:when>
	<c:otherwise>
	<img alt="" id="resultImgPre" src="data:image/png;base64, ${imgpre}" style="width: 80%; padding: 10px 10px 10px 10px;">
	</c:otherwise>
	</c:choose>
						</div>
						</div>
						<br></br>
						<hr/>
						<div class="col-sm-12">
						 <label
							class="col-sm-2 control-label text-right st-mgt-10">Ảnh
							mặt trước UV<span style="color: red; padding-left: 5px;"></span>
						</label>
						<div class="col-sm-10 st-mgt-10">
							<input type="file" name="imgPreUV" id="imgPreUV" />
							<c:choose>
	<c:when test="${imgpreuv eq ''}">
	<img alt="" id="resultImgPreUv	" src="data:image/png;base64, ${notfound}" style="width: 80%; padding: 10px 10px 10px 10px;">
	</c:when>
	<c:otherwise>
	<img alt="" id="resultImgPreUv" src="data:image/png;base64, ${imgpreuv}" style="width: 80%; padding: 10px 10px 10px 10px;">
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
							<input type="file" name="imgFace" id="imgFace" />
							<c:choose>
	<c:when test="${imgface eq ''}">
	<img alt="" id="resultImgFace" src="data:image/png;base64, ${notfound}" style="width: 80%; padding: 10px 10px 10px 10px;">
	</c:when>
	<c:otherwise>
	<img alt="" id="resultImgFace" src="data:image/png;base64, ${imgface}" style="width: 80%; padding: 10px 10px 10px 10px;">
	</c:otherwise>
	</c:choose>
						</div>
						
					</div>
					<br></br>
					<hr/>

				</fieldset>
			
		</div>
	
	<div id="ajax-load-qa"></div>
</form:form>
	<script type="text/javascript">
	

		   $('input[name=imgPre]').change(function(e) {
			   getBase64(document.getElementById('imgPre').files[0], function(e){
					img64 = e.target.result;
					$('#resultImgPre').attr('src', img64);
				} );
			});
		   
		   
		   $('input[name=imgPreUV]').change(function(e) {
			   getBase64(document.getElementById('imgPreUV').files[0], function(e){
					img64 = e.target.result;
					$('#resultImgPreUv').attr('src', img64);
				} );
			});
		   
		   $('input[name=imgFace]').change(function(e) {
			   getBase64(document.getElementById('imgFace').files[0], function(e){
					img64 = e.target.result;
					$('#resultImgFace').attr('src', img64);
				} );
			});
		   
		 function getBase64(file, result) {
		   var reader = new FileReader();
		   reader.readAsDataURL(file);
		   reader.onload = result;
		   reader.onerror = function (error) {
		     console.log('Error: ', error);
		   };
		} 
		 function savePassport() {
				$('#ajax-load-qa').css('display', 'block');
				$('#ajax-load-qa').show();
				var err_msg = '';
				var url = '${savePassport}/' + ${queue.id};
				var desc = $('#desc').val();
				var imgPre = $('#resultImgPre').attr('src');
				var imgPreUV = $('#resultImgPreUv').attr('src');
				var imgFace = $('#resultImgFace').attr('src');
				if (desc == '') {
					err_msg = 'Mô tả Passport không được để trống.';
				}
				if (err_msg != '') {
					$.notify(err_msg, {
						globalPosition : 'bottom left',
						className : 'warn',
					});
					return;
				} 
				var status = '';
				if ($('#statusEditform').is(":checked"))
				{
					status = 'true';
				}else{
					status = 'false';
				}
				var obj = {
						"status" : status,
						 "description" : desc,
						 "imgPre": imgPre,
						 "imgPreUV" : imgPreUV,
						 "imgFace" : imgFace  
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
										$.notify('Lưu thành công', {
											globalPosition : 'bottom left',
											className : 'success',
										});
										document.forms["formData"].action = '${loadFormQueueUrl}';
										document.forms["formData"].submit();
									} else {
										$('#ajax-load-qa').hide();

										$.notify('TemplatePassport đã tồn tại', {
											globalPosition : 'bottom left',
											className : 'warn',
										});
									}
								},
								error : function(e) {
									$('#ajax-load-qa').hide();

									$.notify('Có lỗi khi lưu', {
										globalPosition : 'bottom left',
										className : 'warn',
									});
								}
							});
		 }
	</script>


</div>







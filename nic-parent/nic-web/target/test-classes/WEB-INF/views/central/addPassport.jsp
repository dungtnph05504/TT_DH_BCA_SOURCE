<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:url var="addPassport" value="/servlet/central/addPassport" />
<c:url var="getvalueNationName" value="/servlet/central/getvalueNationName" />
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
					<legend>Nhập thông tin hộ chiếu</legend>
					<div class="col-sm-6">
						<label class="col-sm-4 control-label text-right st-mgt-10">Nation<span
							style="color: red; padding-left: 5px;">*</span></label>
						<div class="col-sm-8 st-mgt-10">
							<form:select path="nameNationVie" class="style-width-100" id="nameNationVie">
							<form:option value="">Chọn mã quốc gia</form:option>
								<c:forEach items="${nation}" var="nation">
								<form:option value="${nation}">${nation}</form:option>
								</c:forEach>
							</form:select>
						</div>
							<form:input type="hidden" path="nation" class="style-width-100"
								id="SelectNation" />
						<br></br> <label
							class="col-sm-4 control-label text-right st-mgt-10">Kiểu hộ chiếu<span style="color: red; padding-left: 5px;">*</span>
						</label>
						<div class="col-sm-8 st-mgt-10">
							<form:select path="passPortType" id="passPortType"
								class="style-width-100">
								<form:option value="P">Hộ chiếu phổ thông</form:option>
								<form:option value="PO">Hộ chiếu ngoại giao</form:option>
								<form:option value="PD">Hộ chiếu công vụ</form:option>
							</form:select>
						</div>

						<br></br> <label
							class="col-sm-4 control-label text-right st-mgt-10">Năm
							phát hành<span style="color: red; padding-left: 5px;">*</span>
						</label>
						<div class="col-sm-8 st-mgt-10">
							<input path="yearIssue" class="style-width-100"
								id="yearIssue" />
						</div>

					</div>
					<div class="col-sm-6">
						<label class="col-sm-4 st-mgt-10 control-label text-right">Trạng
							thái<span style="color: red; padding-left: 5px;">*</span>
						</label>
						<div class="col-sm-8 st-mgt-10">
							<form:select class="style-width-100" path="status" id="statusadd">
								<form:option value="true" selected="true">Hoạt động</form:option>
								<form:option value="false">Không hoạt động</form:option>

							</form:select>
						</div>
						<br></br> <label
							class="col-sm-4 control-label text-right st-mgt-10">Miêu tả<span
							style="color: red; padding-left: 5px;">*</span></label>
						<div class="col-sm-8 st-mgt-10">
							<form:textarea rows="4" path="description" id="description"
								class="style-width-100"  />
						</div>
					</div>

				</fieldset>
			</div>
		</div>
	
		<div class="content-item-information">
			<div class="col-sm-12">
				<fieldset class="scheduler-border">
					<legend>Ảnh</legend>
					<div class="col-sm-6">
						<label class="col-sm-4 control-label text-right st-mgt-10">Ảnh
							mặt trước<span style="color: red; padding-left: 5px;"></span>
						</label>
						<div class="col-sm-8 st-mgt-10">
							<input type="file" name="imgPre" id="imgPre" />
						</div>
						<br></br> <label
							class="col-sm-4 control-label text-right st-mgt-10">Ảnh
							mặt trước UV<span style="color: red; padding-left: 5px;"></span>
						</label>
						<div class="col-sm-8 st-mgt-10">
							<input type="file" name="imgPreUV" id="imgPreUV" />
						</div>
						<br></br>

					</div>
					<div class="col-sm-6">
						<label class="col-sm-4 control-label text-right st-mgt-10">Ảnh
							mặt<span style="color: red; padding-left: 5px;"></span>
						</label>
						<div class="col-sm-8 st-mgt-10">
							<input type="file" name="imgFace" id="imgFace" />
						</div>
						<br></br>
						<input type="hidden" value="a" id="resultImgPre" />
						<input type="hidden" value="a" id="resultImgPreUv" />
						<input type="hidden" value="a" id="resultImgFace" />
					</div>

				</fieldset>
			</div>
		</div>
	
	<div id="ajax-load-qa"></div>
	
</form:form>
	<script type="text/javascript">
	$('select[id^="nameNationVie"]').change(
			function(e) {
				var nation = $('#nameNationVie').val();
				var url = '${getvalueNationName}'+'/' + nation;
				$.ajax({
					url : url,
				    method: 'POST',
					success : function(data) {
						if (data != null) {
							$('#SelectNation').val(data);
						} else {
							$.notify('Thay đổi trạng thái thất bại', {
								globalPosition : 'bottom left',
								className : 'warn',
							});
						}
					},
					error : function(e) {
						$.notify('Có lỗi ', {
							globalPosition : 'bottom left',
							className : 'warn',
						});
					}
				});
			});

		   $('input[name=imgPre]').change(function(e) {
			   getBase64(document.getElementById('imgPre').files[0], function(e){
					img64 = e.target.result;
					$('#resultImgPre').val(img64);
				} );
			});
		   
		   
		   $('input[name=imgPreUV]').change(function(e) {
			   getBase64(document.getElementById('imgPreUV').files[0], function(e){
					img64 = e.target.result;
					$('#resultImgPreUv').val(img64);
				} );
			});
		   
		   $('input[name=imgFace]').change(function(e) {
			   getBase64(document.getElementById('imgFace').files[0], function(e){
					img64 = e.target.result;
					$('#resultImgFace').val(img64);
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

		function addPassport() {
			var err_msg = '';
			var url = '${addPassport}';
			$('#ajax-load-qa').css('display', 'block');
			$('#ajax-load-qa').show();
			var nation = $('#SelectNation').val();
			var nameNationVie = $('#nameNationVie').val();
			var passPortType = $('#passPortType').val();
			var yearIssue = $('#yearIssue').val();
			var status = $('#statusadd').val();
			var desc = $('#description').val();
			var imgPre = $('#resultImgPre').val();
			var imgPreUV = $('#resultImgPreUv').val();
			var imgFace = $('#resultImgFace').val();
		 if (desc == '') {
				err_msg = 'Mô tả Passport không được để trống.';
				$('#ajax-load-qa').hide();
				
			} else if (nation == '') {
				err_msg = 'Hãy chọn quốc gia';
				$('#ajax-load-qa').hide();
			} else if (yearIssue == '') {
				 err_msg = 'Năm phát hành không được để trống';
				 $('#ajax-load-qa').hide();
			}
		 if (yearIssue != 0) {
			 var text = /^[0-9]+$/;
		        if ((yearIssue != "") && (!text.test(yearIssue))) {
		            err_msg = 'Hãy nhập năm là chữ số';
					$('#ajax-load-qa').hide();
		        }

		        if (yearIssue.length != 4) {
		        	 err_msg = 'Năm không đúng, vui lòng kiểm tra lại';
						$('#ajax-load-qa').hide();
		        }
		        var current_year=new Date().getFullYear();
		        if((yearIssue < 1920) || (yearIssue > current_year))
		            {
		           
		            err_msg = 'Chỉ được nhập vào năm từ 1920 đến năm hiện tại';
		            $('#ajax-load-qa').hide();
		            }
		    } 
			if (err_msg != '') {
				$.notify(err_msg, {
					globalPosition : 'bottom left',
					className : 'warn',
				});
				return;
			} 
			var obj = {
					"nation": nation,
					"nameNationVie" : nameNationVie,
					"passPortType" : passPortType,
					"yearIssue" : yearIssue,
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
									$('#addPassport').modal('hide');
									document.forms["formData"].action = '${loadFormQueueUrl}';
									document.forms["formData"].submit();
									$('#ajax-load-qa').hide();
									$.notify('Thêm mới thành công', {
										globalPosition : 'bottom left',
										className : 'success',
									});
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
								$.notify('Có lỗi khi thêm mới', {
									globalPosition : 'bottom left',
									className : 'warn',
								});
							}
						});
		}
	</script>


</div>







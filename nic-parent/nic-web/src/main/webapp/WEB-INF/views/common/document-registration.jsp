<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url var="addDocScanurl" value="/servlet/registration/addDocScan"></c:url>
<script type="text/javascript">
	$(function() {
		var divDocId;

		$("#addMoreScanDocDialog").dialog({
			modal : true,
			autoOpen : false,
			width : 300,
			height : 200,
			closeOnEscape : false,
			show : {
				effect : "blind",
				duration : 500
			},
			hide : {
				//effect : "explode",
				duration : 1000
			},
			buttons : {
				Ok : function() {
					var docname = $("input:text[id='scanDocId']").val();
					$.fn.myFunction(docname);
					$(this).dialog("close");
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			}
		});
		$("#addMoreScanDoc").click(function() {
			$("#scanDocId").val("");
			$('#addMoreScanDocDialog').dialog("open");
		});
		$.fn.myFunction = function(docname) {
			doViewIt(docname);
		};
		$("#dialog-docView")
				.dialog(
						{
							modal : true,
							autoOpen : false,
							width : 800,
							height : 500,
							closeOnEscape : false,
							show : {
								effect : "blind",
								duration : 1000
							},
							hide : {
								//effect : "explode",
								duration : 1000
							},
							open : function() {
								var data = $('#dialog-docView').data('docView');
								document.getElementById("documentView" + data).src = "data:image/jpg;base64,"
										+ $("#scandocument" + data).val();
								show("documentView" + data);
							},
							close : function() {
								var data = $('#dialog-docView').data('docView');
								hide("documentView" + data);
							}
						});
		var DELAY = 500, clicks = 0, timer = null;
		$('body')
				.on(
						'click',
						'.scanDoc',
						function() {
							clicks++;
							//alert(clicks);
							if (clicks == 1) {
								var divId = this.id;
								var docName = this.getAttribute("data-name");
								var docView = this.getAttribute("data-view");
								timer = setTimeout(
										function() {
											var resValue = document.registrationApplet
													.scanDocument();
											if (resValue == 0) {
												var scannedImage = document.registrationApplet
														.getImageScanValue();
												$
														.post(
																'<c:url value="/servlet/registration/setDocumentList" />',
																{
																	docName : docName,
																	imageData : scannedImage
																}, function(
																		data) {

																});
												document
														.getElementById("documentView"
																+ docView).src = "data:image/jpg;base64,"
														+ scannedImage;
												$('#scandocument' + docView)
														.val(scannedImage);
												$("#" + divId)
														.css(
																{
																	'background-image' : 'url(<c:url value="/images/document_after_scan_image.jpg" />)'
																});
											}
											clicks = 0;
										}, DELAY);

							} else {
								clearTimeout(timer); //prevent single-click action
								//Scan Doc Viewing
								var docView = this.getAttribute("data-view");
								//alert(docView);
								$("#dialog-docView").data('docView', docView)
										.dialog("open");
								clicks = 0; //after action performed, reset counter
							}

						});

		$('body')
				.on(
						'click',
						'.scanDocAfter',
						function() {
							clicks++;
							if (clicks == 1) {
								var divId = this.id;
								var docName = this.getAttribute("data-name");
								var docView = this.getAttribute("data-view");
								timer = setTimeout(
										function() {
											var resValue = document.registrationApplet
													.scanDocument();
											if (resValue == 0) {
												var scannedImage = document.registrationApplet
														.getImageScanValue();
												$
														.post(
																'<c:url value="/servlet/registration/setDocumentList" />',
																{
																	docName : docName,
																	imageData : scannedImage
																}, function(
																		data) {

																});
												document
														.getElementById("documentView"
																+ docView).src = "data:image/jpg;base64,"
														+ scannedImage;
												$('#scandocument' + docView)
														.val(scannedImage);
												$("#" + divId)
														.css(
																{
																	'background-image' : 'url(<c:url value="/resources/images/document_after_scan_image.jpg" />)'
																});
											}
											clicks = 0;
										}, DELAY);

							} else {
								clearTimeout(timer); //prevent single-click action
								//Scan Doc Viewing
								var docView = this.getAttribute("data-view");
								$("#dialog-docView").data('docView', docView)
										.dialog("open");
								clicks = 0; //after action performed, reset counter
							}

						});
		$('body').on('click', '.scanDocViewOnly', function() {
			//Scan Doc Viewing
			var docView = this.getAttribute("data-view");
			//alert(docView);
			$("#dialog-docView").data('docView', docView).dialog("open");

		});
		$('#exitView').click(function(event) {
			$("#dialog-docView").dialog('close');
		});
		$("#dialog-deleteConfirm").dialog({
			resizable : false,
			modal : true,
			autoOpen : false,
			width : 500,
			resizable : false,
			show : {
				effect : "fade",
				duration : 1000
			},
			hide : {
				//effect: "explode",
				duration : 1000
			},
			buttons : {
				Ok : function() {
					$.fn.delDocFunction(divDocId);
					$(this).dialog("close");
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			}
		});
		$.contextMenu({
			selector : '.context-menu-one',
			items : {
				"Delete" : {
					name : "Delete",
					icon : 'delete',
					callback : function() {
						divDocId = $(this).attr('data-name');
						$("#dialog-deleteConfirm").dialog("open");
					}
				}
			}
		});
		$.fn.delDocFunction = function(divDocId) {
			doDeleteIt(divDocId);
		};
	});

	function doViewIt(docname) {
		$
				.ajax({
					type : "POST",
					url : "${addDocScanurl}/" + docname,
					success : function(data) {
						$("#documentListTR").empty();
						var tableDesign = '<td><table><tr>';
						$
								.each(
										data,
										function(index, value) {
											if (index == 8) {
												tableDesign += '</tr><tr>';
											}
											tableDesign += '<td><input id="scandocumentname'+index+'" type="hidden" name="documentList['+index+'].documentName" value="'+value.documentName+'"/>'
													+ '<input id="scandocumenttype'+index+'" type="hidden" name="documentList['+index+'].type" value="'+value.type+'"/>'
													+ '<input id="scandocumentindicator'+index+'" type="hidden" name="documentList['+index+'].indicator" value="'+value.indicator+'"/>'
													+ '<input id="scandocument'+index+'" type="hidden" name="documentList['+index+'].document" value="'+value.document+'"/>';

											if (value.document == null
													|| value.document == '') {
												tableDesign += '<div id="scandocDiv'+index+'" data-view="'+index+'" data-name="'+value.documentName+'" class="scanDoc context-menu-one box menu-1" >';
											} else {
												tableDesign += '<div id="scandocDiv'+index+'" data-view="'+index+'" data-name="'+value.documentName+'" class="scanDocAfter context-menu-one box menu-1" >';
											}
											tableDesign += '<p class="" id="docname" style="text-align: left; valign: top; margin: 0; padding-left: 3px; padding-top: 35px; font-size: 10px; font-weight: bold">';
											tableDesign += value.documentName;
											if(value.indicator=='M'){
												tableDesign +='<span style="COLOR: #ff0000;">*</span>';
											}
											tableDesign += '</p></div></td>';

										});
						$("#documentListTR").append(tableDesign);
						$("#documentListTR").append('</tr>');
					},
					error : function(e) {
						alert('Error: ' + e);
					}
				});
	}
	function doDeleteIt(docname) {
		$
				.ajax({
					type : "POST",
					url : "${delDocScanurl}/" + docname,
					success : function(data) {
						$("#documentListTR").empty();
						var divContent = '<td><table><tr>';
						$
								.each(
										data,
										function(index, value) {
											if (index == 8) {
												divContent += '</tr><tr>';
											}
											divContent += '<td><input id="scandocumentname'+index+'" type="hidden" name="documentList['+index+'].documentName" value="'+value.documentName+'"/>'
													+ '<input id="scandocumenttype'+index+'" type="hidden" name="documentList['+index+'].type" value="'+value.type+'"/>'
													+ '<input id="scandocumentindicator'+index+'" type="hidden" name="documentList['+index+'].indicator" value="'+value.indicator+'"/>'
													+ '<input id="scandocument'+index+'" type="hidden" name="documentList['+index+'].document" value="'+value.document+'"/>';

											if (value.document == null
													|| value.document == '') {
												divContent += '<div id="scandocDiv'+index+'" data-view="'+index+'" data-name="'+value.documentName+'" class="scanDoc context-menu-one box menu-1" >';
											} else {
												divContent += '<div id="scandocDiv'+index+'" data-view="'+index+'" data-name="'+value.documentName+'" class="scanDocAfter context-menu-one box menu-1" >';
											}
											divContent += '<p class="" id="docname" style="text-align: left; valign: top; margin: 0; padding-left: 3px; padding-top: 35px; font-size: 10px; font-weight: bold">';
											divContent += value.documentName;
											if(value.indicator=='M'){
												divContent +='<span style="COLOR: #ff0000;">*</span>';
											}
											divContent += '</p></div></td>';

										});
						$("#documentListTR").append(divContent);
						$("#documentListTR").append('</tr>');
					},
					error : function(e) {
						alert('Error: ' + e);
					}
				});

	}
</script>
<div id="addScanDocDiv">
	<c:choose>
		<c:when test="${isUpdate == 'Y' || isUpdateVerify == 'Y'}">
			<!-- <table style="border: 1px SOLID; border-radius: 10px; width: 500px; margin-top: 1px; background-color: #FFFFC6; height: 185px">-->
			<table style="border: 1px SOLID; border-radius: 10px; width: 500px; margin-top: 1px; background-color: #FFFFC6; height: 145px">
		</c:when>
		<c:otherwise>
			<!-- <table style="border: 1px SOLID; border-radius: 10px; width: 665px; margin-top: 1px; background-color: #FFFFC6; height: 185px">-->
			<table style="border: 1px SOLID; border-radius: 10px; width: 650px; margin-top: 1px; background-color: #FFFFC6; height: 145px">
		</c:otherwise>	
	</c:choose>	
		<tr>
			<td colspan="8">
				<table>
					<tr><td><div id="sub_heading_new" style="height: 5px">SCAN DOCUMENTS</div></td>
						 
						<!--  <td><div><c:choose>
					<c:when
						test="${newRegistration.comingFromRegistration==true || newRegistration.comingFromVerification==true || isUpdateVerify == 'Y'  || isUpdateConfirm == 'Y'}">
						<input type="button" class="btn_blue gradient"
							style="width: 100px; display: none;" id="addMoreScanDoc"
							value="Add More" />
					</c:when>
					<c:otherwise>
						<input type="button" class="btn_blue gradient"
							style="width: 100px" id="addMoreScanDoc" value="Add More" />
					</c:otherwise>
				</c:choose></div></td>-->
				<div><c:choose>
					<c:when
						test="${newRegistration.comingFromRegistration==true || newRegistration.comingFromVerification==true}">
						<input type="button" class="btn_blue gradient"
							style="width: 100px; display: none;" id="addMoreScanDoc"
							value="Add More" />
					</c:when>
					<c:otherwise>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="btn_blue gradient"
							style="width: 100px" id="addMoreScanDoc" value="Add More" /></td>
					</c:otherwise>
				</c:choose></div>
					</tr>
				</table> 
				 
			</td>
			 
		</tr>
		<tr id="documentListTR">
			<td>
				<table>
					<tr>
						<c:forEach var="docItem" items="${newRegistration.documentList}"
							varStatus="status">
							<form:hidden path="documentList[${status.index}].documentName"
								id="scandocumentName${status.index}" />
							<form:hidden path="documentList[${status.index}].document"
								id="scandocument${status.index}" />
							<form:hidden path="documentList[${status.index}].type"
								id="scandocumentType${status.index}" />
							<form:hidden path="documentList[${status.index}].indicator"
								id="scandocumentIndicator${status.index}" />
							<td><c:choose>
									<c:when
										test="${newRegistration.comingFromRegistration==true || newRegistration.comingFromVerification==true}">
										<div id="scandocDiv${status.count}"
											data-view="${status.index}"
											data-name="${docItem.documentName}" class="scanDoc" >
											<p class="" id="docname"
												style="text-align: left; margin: 0; padding-left: 3px; padding-top: 35px; font-size: 10px; font-weight: bold">${docItem.documentName}<c:if test="${docItem.indicator=='M'}"><span style="COLOR: #ff0000;">*</span></c:if></p>
										 </div> 
										 <script type="text/javascript">
												var index = "${status.index}";
												var count = "${status.count}";
												var n = $(
														"#scandocument" + index)
														.val();
												if (n == null || n == '') {
													$("#scandocDiv" + count)
															.css(
																	{
																		'background-image' : 'url(<c:url value="/resources/images/document_before_scan_image.jpg" />)'
																	});
												} else {
													$("#scandocDiv" + count)
															.css(
																	{
																		'background-image' : 'url(<c:url value="/resources/images/document_after_scan_image.jpg" />)'
																	});
												}
											</script>
									</c:when>
									<c:otherwise>
										<div id="scandocDiv${status.count}"
											data-view="${status.index}"
											data-name="${docItem.documentName}"
											class="scanDoc context-menu-one box menu-1">
											<p class="" id="docname"
												style="text-align: left; margin: 0; padding-left: 3px; padding-top: 35px; font-size: 10px; font-weight: bold">${docItem.documentName}<c:if test="${docItem.indicator=='M'}"><span style="COLOR: #ff0000;">*</span></c:if>
												 </p>
											<script type="text/javascript">
												var index = "${status.index}";
												var count = "${status.count}";
												var n = $(
														"#scandocument" + index)
														.val();
												if (n == null || n == '') {
													$("#scandocDiv" + count)
															.css(
																	{
																		'background-image' : 'url(<c:url value="/resources/images/document_before_scan_image.jpg" />)'
																	});
												} else {
													$("#scandocDiv" + count)
															.css(
																	{
																		'background-image' : 'url(<c:url value="/resources/images/document_after_scan_image.jpg" />)'
																	});
												}
											</script>
										</div>
									</c:otherwise>
								</c:choose></td>
						</c:forEach>
					</tr>
				</table>
			</td>
		</tr> 
		<!-- 
		<tr>
			<td colspan="8"><c:choose>
					<c:when
						test="${newRegistration.comingFromRegistration==true || newRegistration.comingFromVerification==true}">
						<span class='description_slimplain'
							style="font-size: 12px; padding-left: 3px; display: none;">Scan
							:</span>
						<span style="display: none;" class='subjectsmall'> Single
							Click</span>
						<span class='description_slimplain'
							style="font-size: 12px; padding-left: 3px; display: none;">View
							:</span>
						<span class='subjectsmall' style="display: none;"> Double
							Click</span>
						<span class='description_slimplain'
							style="font-size: 12px; padding-left: 3px; display: none;">Delete
							:</span>
						<span class='subjectsmall' style="display: none;"> Right
							Click</span>
					</c:when>
					<c:otherwise>
						<span class='description_slimplain'
							style="font-size: 12px; padding-left: 3px">Scan :</span>
						<span class='subjectsmall'> Single Click</span>
						<span class='description_slimplain'
							style="font-size: 12px; padding-left: 3px">View :</span>
						<span class='subjectsmall'> Double Click</span>
						<span class='description_slimplain'
							style="font-size: 12px; padding-left: 3px">Delete :</span>
						<span class='subjectsmall'> Right Click</span>
					</c:otherwise>
				</c:choose></td>
		</tr>
		 -->
	</table>
</div>
<div id="addMoreScanDocDialog" title="Add More Documents">
	<table>
		<tr>
			<td><b>Documnet Name</b></td>
			<td><input type="text" name="scanDocName" id="scanDocId">
			</td>
		</tr>
	</table>
</div>
<div id="dialog-docView" title="View Document" style="width: 800px">
	<table>
		<tr>
			<td style="width: 100px;"><input class="btn_blue gradient"
				type="button" id="exitView" width="100px" value="Return" /></td>
		</tr>
		<tr>
			<td><c:forEach var="docItem"
					items="${newRegistration.documentList}" varStatus="status">
					<c:choose>
						<c:when
							test="${docItem.document == null || docItem.document == ''}">
							<img width="700px" height="600px"
								style="align: left; display: none;"
								src="<c:url value="/images/No_Image.jpg " />"
								id="documentView${status.index}" alt="DEPARTMENT LOGO" />
						</c:when>
						<c:otherwise>
							<img width="700px" height="600px"
								style="align: left; display: none;"
								src="data:image/jpg;base64,${docItem.document}"
								id="documentView${status.index}" alt="DEPARTMENT LOGO" />
						</c:otherwise>
					</c:choose>
				</c:forEach></td>
		</tr>
	</table>
</div>
<div id="dialog-deleteConfirm" title="Confirmation">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>Are you sure to
		delete the document?
	</p>
</div>


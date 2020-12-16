<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<style>
#form-vantay {
	transition: height 1s;
	-webkit-transition: height 1s;
	height: 0px;
	overflow: hidden !important;
}
.font-chuthe {
	font-weight: bold;
	color: red;
}
.img-srol {
	transition: height 1s;
	-webkit-transition: height 1s;
	height: 0px;
}
</style>
<div>
		<div>
			<div style="background: #ddd;padding: 2px 10px;">
				<h4> Đối sánh vân tay
					<a href="#" id="hienThiVanTay" style="float: right;"><span class="glyphicon glyphicon-menu-right"></span></a>
					<a href="#" id="anVanTay" style="display: none;float: right;"><span class="glyphicon glyphicon-menu-down"></span></a>
				</h4>
			</div>
		</div>
		<div id="form-vantay">
		<div class="col-md-12 col-sm-12">
		<table width="100%" border="0" style="margin-top: 10px;">
			<tr>
				<td style=" text-align: center; border: none;"
					class="text"><b>Ngón cái phải: ${fpMapScore1}</b></td>
				<td style= "text-align: center; border: none;"
					class="text"><b>Ngón trỏ phải: ${fpMapScore2}</b></td>
				<td style=" text-align: center; border: none;"
					class="text"><b>Ngón giữa phải: ${fpMapScore3}</b></td>
				<td style="text-align: center; border: none;"
					class="text"><b>Ngón áp út phải: ${fpMapScore4}</b></td>
				<td style="text-align: center; border: none;"
					class="text"><b>Ngón út phải: ${fpMapScore5}</b></td>
			</tr>
			
			<tr>
				
					<td align="center">
					
						<table>
							<tr>	
								<td colspan="3" align="center">
									<c:choose>
										<c:when test="${viewFPFalg eq 'Y' }">
											<c:choose>
												<c:when test="${fpMap1.baseImageConvert != 'N'}">
													<img src="data:image/jpg;base64,${fpMap1.baseImageConvert}" id="root1" width="75px" class="img-srol"  alt="Fingerprint Image"
													 <%--   Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />													
										
												</c:when>
												<c:otherwise>
													<img id="root1" data-index="1" width="75px" class="img-srol" src="<c:url value="/resources/images/No_Image_Small.jpg"/>" />
												</c:otherwise>
			
											</c:choose>											
											<c:choose>
												<c:when test="${fpMapDoiChieu1.baseImageConvert != 'N'}">
													<img src="data:image/jpg;base64,${fpMapDoiChieu1.baseImageConvert}" id="sub1" width="75px" class="img-srol"  alt="Fingerprint Image"
													 <%--   Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />													
										
												</c:when>
												<c:otherwise>
													<img id="sub1" data-index="1" width="75px" class="img-srol" src="<c:url value="/resources/images/No_Image_Small.jpg"/>" />
												</c:otherwise>
			
											</c:choose>	
										</c:when>										
									</c:choose>
									
								</td>
							</tr>
						</table>																																
					</td>
					<td align="center">
					
						<table>
							<tr>	
								<td colspan="3" align="center">
									<c:choose>
										<c:when test="${viewFPFalg eq 'Y' }">
											<c:choose>
												<c:when test="${fpMap2.baseImageConvert != 'N'}">
													<img src="data:image/jpg;base64,${fpMap2.baseImageConvert}" id="root2" width="75px" class="img-srol"  alt="Fingerprint Image"
													 <%--   Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />													
										
												</c:when>
												<c:otherwise>
													<img id="root2" data-index="2" width="75px" class="img-srol" src="<c:url value="/resources/images/No_Image_Small.jpg"/>" />
												</c:otherwise>
			
											</c:choose>											
											<c:choose>
												<c:when test="${fpMapDoiChieu2.baseImageConvert != 'N'}">
													<img src="data:image/jpg;base64,${fpMapDoiChieu2.baseImageConvert}" id="sub2" width="75px" class="img-srol"  alt="Fingerprint Image"
													 <%--   Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />													
										
												</c:when>
												<c:otherwise>
													<img id="sub2" data-index="2" width="75px" class="img-srol" src="<c:url value="/resources/images/No_Image_Small.jpg"/>" />
												</c:otherwise>
			
											</c:choose>	
										</c:when>
										<c:otherwise>																					
										</c:otherwise>
									</c:choose>
									
								</td>
							</tr>
						</table>																																
					</td>
					<td align="center">
					
						<table>
							<tr>	
								<td colspan="3" align="center">
									<c:choose>
										<c:when test="${viewFPFalg eq 'Y' }">
											<c:choose>
												<c:when test="${fpMap3.baseImageConvert != 'N'}">
													<img src="data:image/jpg;base64,${fpMap3.baseImageConvert}" id="root3" width="75px" class="img-srol"  alt="Fingerprint Image"
													 <%--   Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />													
										
												</c:when>
												<c:otherwise>
													<img id="root3" data-index="3" width="75px" class="img-srol" src="<c:url value="/resources/images/No_Image_Small.jpg"/>" />
												</c:otherwise>
			
											</c:choose>	
											<c:choose>
												<c:when test="${fpMapDoiChieu3.baseImageConvert != 'N'}">
													<img src="data:image/jpg;base64,${fpMapDoiChieu3.baseImageConvert}" id="sub3" width="75px" class="img-srol"  alt="Fingerprint Image"
													 <%--   Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />													
										
												</c:when>
												<c:otherwise>
													<img id="sub3" data-index="3" width="75px" class="img-srol" src="<c:url value="/resources/images/No_Image_Small.jpg"/>" />
												</c:otherwise>
			
											</c:choose>											
						
										</c:when>
										<c:otherwise>																					
										</c:otherwise>
									</c:choose>
									
								</td>
							</tr>
						</table>																																
					</td>
					<td align="center">
					
						<table>
							<tr>	
								<td colspan="3" align="center">
									<c:choose>
										<c:when test="${viewFPFalg eq 'Y' }">
											<c:choose>
												<c:when test="${fpMap4.baseImageConvert != 'N'}">
													<img src="data:image/jpg;base64,${fpMap4.baseImageConvert}" id="root4" width="75px" class="img-srol"  alt="Fingerprint Image"
													 <%--   Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />													
										
												</c:when>
												<c:otherwise>
													<img id="root4" data-index="4" width="75px" class="img-srol" src="<c:url value="/resources/images/No_Image_Small.jpg"/>" />
												</c:otherwise>
			
											</c:choose>	
											<c:choose>
												<c:when test="${fpMapDoiChieu4.baseImageConvert != 'N'}">
													<img src="data:image/jpg;base64,${fpMapDoiChieu4.baseImageConvert}" id="sub4" width="75px" class="img-srol"  alt="Fingerprint Image"
													 <%--   Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />													
										
												</c:when>
												<c:otherwise>
													<img id="sub4" data-index="4" width="75px" class="img-srol" src="<c:url value="/resources/images/No_Image_Small.jpg"/>" />
												</c:otherwise>
			
											</c:choose>											
						
										</c:when>
										<c:otherwise>																					
										</c:otherwise>
									</c:choose>
									
								</td>
							</tr>
						</table>																																
					</td>
					<td align="center">
					
						<table>
							<tr>	
								<td colspan="3" align="center">
									<c:choose>
										<c:when test="${viewFPFalg eq 'Y' }">
											<c:choose>
												<c:when test="${fpMap5.baseImageConvert != 'N'}">
													<img src="data:image/jpg;base64,${fpMap5.baseImageConvert}" id="root5" width="75px" class="img-srol"  alt="Fingerprint Image"
													 <%--   Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />													
										
												</c:when>
												<c:otherwise>
													<img id="root5" data-index="5" width="75px" class="img-srol" src="<c:url value="/resources/images/No_Image_Small.jpg"/>" />
												</c:otherwise>
			
											</c:choose>	
											<c:choose>
												<c:when test="${fpMapDoiChieu5.baseImageConvert != 'N'}">
													<img src="data:image/jpg;base64,${fpMapDoiChieu5.baseImageConvert}" id="sub5" width="75px" class="img-srol"  alt="Fingerprint Image"
													 <%--   Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />													
										
												</c:when>
												<c:otherwise>
													<img id="sub5" data-index="5" width="75px" class="img-srol" src="<c:url value="/resources/images/No_Image_Small.jpg"/>" />
												</c:otherwise>
			
											</c:choose>											
						
										</c:when>
										<c:otherwise>																					
										</c:otherwise>
									</c:choose>
									
								</td>
							</tr>
						</table>																																
					</td>
			</tr>
		</table>
	    </div>
		<div class="col-md-12 col-sm-12">
		<table width="100%" border="0" style="margin-top: 30px;">
			<tr>
				<td style="width: 10%; text-align: center; border: none;"
					class="text"><b>Ngón cái trái: ${fpMapScore6}</b></td>
				<td style="width: 10%; text-align: center; border: none;"
					class="text"><b>Ngón trỏ trái: ${fpMapScore7}</b></td>
				<td style="width: 10%; text-align: center; border: none;"
					class="text"><b>Ngón giữa trái: ${fpMapScore8}</b></td>
				<td style="width: 10%; text-align: center; border: none;"
					class="text"><b>Ngón áp út trái: ${fpMapScore9}</b></td>
				<td style="width: 10%; text-align: center; border: none;"
					class="text"><b>Ngón út trái: ${fpMapScore10}</b></td>
			</tr>
			<tr >
				
					<td align="center">
					
					<table>
							<tr>	
								<td colspan="3" style="text-align: center;">
									<c:choose>
										<c:when test="${viewFPFalg eq 'Y' }">
											<c:choose>
												<c:when test="${fpMap6.baseImageConvert != 'N'}">
													<img src="data:image/jpg;base64,${fpMap6.baseImageConvert}" id="root6" width="75px" class="img-srol"  alt="Fingerprint Image"  <%--  Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />

												</c:when>
												<c:otherwise>
													<img id="root6" data-index="6" width="75px" class="img-srol" src="<c:url value="/resources/images/No_Image_Small.jpg"/>" />
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${fpMapDoiChieu6.baseImageConvert != 'N'}">
													<img src="data:image/jpg;base64,${fpMapDoiChieu6.baseImageConvert}" id="sub6" width="75px" class="img-srol"  alt="Fingerprint Image"  <%--  Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />

												</c:when>
												<c:otherwise>
													<img id="sub6" data-index="6" width="75px" class="img-srol" src="<c:url value="/resources/images/No_Image_Small.jpg"/>" />
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
										
											
										</c:otherwise>
									</c:choose>
									
								</td>
							</tr>
						</table>
						
					</td>
					<td align="center">
					
					<table>
							<tr>	
								<td colspan="3" style="text-align: center;">
									<c:choose>
										<c:when test="${viewFPFalg eq 'Y' }">
											<c:choose>
												<c:when test="${fpMap7.baseImageConvert != 'N'}">
													<img src="data:image/jpg;base64,${fpMap7.baseImageConvert}" id="root7" width="75px" class="img-srol"  alt="Fingerprint Image"  <%--  Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />

												</c:when>
												<c:otherwise>
													<img id="root7" data-index="7" width="75px" class="img-srol" src="<c:url value="/resources/images/No_Image_Small.jpg"/>" />
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${fpMapDoiChieu7.baseImageConvert != 'N'}">
													<img src="data:image/jpg;base64,${fpMapDoiChieu7.baseImageConvert}" id="sub7" width="75px" class="img-srol"  alt="Fingerprint Image"  <%--  Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />

												</c:when>
												<c:otherwise>
													<img id="sub7" data-index="7" width="75px" class="img-srol" src="<c:url value="/resources/images/No_Image_Small.jpg"/>" />
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
										
											
										</c:otherwise>
									</c:choose>
									
								</td>
							</tr>
						</table>
						
					</td>
					<td align="center">
					
					<table>
							<tr>	
								<td colspan="3" style="text-align: center;">
									<c:choose>
										<c:when test="${viewFPFalg eq 'Y' }">
											<c:choose>
												<c:when test="${fpMap8.baseImageConvert != 'N'}">
													<img src="data:image/jpg;base64,${fpMap8.baseImageConvert}" id="root8" width="75px" class="img-srol"  alt="Fingerprint Image"  <%--  Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />

												</c:when>
												<c:otherwise>
													<img id="root8" data-index="8" width="75px" class="img-srol" src="<c:url value="/resources/images/No_Image_Small.jpg"/>" />
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${fpMapDoiChieu8.baseImageConvert != 'N'}">
													<img src="data:image/jpg;base64,${fpMapDoiChieu8.baseImageConvert}" id="sub8" width="75px" class="img-srol"  alt="Fingerprint Image"  <%--  Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />

												</c:when>
												<c:otherwise>
													<img id="sub8" data-index="8" width="75px" class="img-srol" src="<c:url value="/resources/images/No_Image_Small.jpg"/>" />
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
										
											
										</c:otherwise>
									</c:choose>
									
								</td>
							</tr>
						</table>
						
					</td>
					<td align="center">
					
					<table>
							<tr>	
								<td colspan="3" style="text-align: center;">
									<c:choose>
										<c:when test="${viewFPFalg eq 'Y' }">
											<c:choose>
												<c:when test="${fpMap9.baseImageConvert != 'N'}">
													<img src="data:image/jpg;base64,${fpMap9.baseImageConvert}" id="root9" width="75px" class="img-srol"  alt="Fingerprint Image"  <%--  Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />

												</c:when>
												<c:otherwise>
													<img id="root9" data-index="9" width="75px" class="img-srol" src="<c:url value="/resources/images/No_Image_Small.jpg"/>" />
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${fpMapDoiChieu9.baseImageConvert != 'N'}">
													<img src="data:image/jpg;base64,${fpMapDoiChieu9.baseImageConvert}" id="sub9" width="75px" class="img-srol"  alt="Fingerprint Image"  <%--  Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />

												</c:when>
												<c:otherwise>
													<img id="sub9" data-index="9" width="75px" class="img-srol" src="<c:url value="/resources/images/No_Image_Small.jpg"/>" />
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
										
											
										</c:otherwise>
									</c:choose>
									
								</td>
							</tr>
						</table>
						
					</td>
					<td align="center">
					
					<table>
							<tr>	
								<td colspan="3" style="text-align: center;">
									<c:choose>
										<c:when test="${viewFPFalg eq 'Y' }">
											<c:choose>
												<c:when test="${fpMap10.baseImageConvert != 'N'}">
													<img src="data:image/jpg;base64,${fpMap10.baseImageConvert}" id="root10" width="75px" class="img-srol"  alt="Fingerprint Image"  <%--  Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />

												</c:when>
												<c:otherwise>
													<img id="root10" data-index="10" width="75px" class="img-srol" src="<c:url value="/resources/images/No_Image_Small.jpg"/>" />
												</c:otherwise>
											</c:choose>
											<c:choose>
												<c:when test="${fpMapDoiChieu10.baseImageConvert != 'N'}">
													<img src="data:image/jpg;base64,${fpMapDoiChieu10.baseImageConvert}" id="sub10" width="75px" class="img-srol"  alt="Fingerprint Image"  <%--  Verify Score : ${fpInfo.value.fpVerifyScr} --%>" />

												</c:when>
												<c:otherwise>
													<img id="sub10" data-index="10" width="75px" class="img-srol" src="<c:url value="/resources/images/No_Image_Small.jpg"/>" />
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
										
											
										</c:otherwise>
									</c:choose>
									
								</td>
							</tr>
						</table>
						
					</td>
						
				
			</tr>
		</table>
		</div>
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
	$('#hienThiVanTay').click(function(){
		$('#anVanTay').css('display', 'block');
		$('#hienThiVanTay').css('display', 'none');
		$('#form-vantay').css('height', '350px');
		$('.img-srol').css('height', '100px');
	})
	
	$('#anVanTay').click(function(){
		$('#anVanTay').css('display', 'none');
		$('#hienThiVanTay').css('display', 'block');
		$('#form-vantay').css('height', '0px');
		$('.img-srol').css('height', '0px');
	})
});	
	

</script>






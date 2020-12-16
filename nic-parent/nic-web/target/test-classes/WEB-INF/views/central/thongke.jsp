<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

<c:url var="getBieuDo" value="/servlet/central/bieuDoTheoDoiXLHS" />
<c:url var="getThongkeXLHS" value="/servlet/central/thongkeXLHS" />
<c:url var="getXNC" value="/servlet/central/bieuDoXuatNhapCanh" />
<c:url var="getTraHC" value="/servlet/central/bieuDoTraHoChieu" />
<c:url var="getCathe" value="/servlet/central/bieuDoCaTheHoa1" />
<c:url var="pingServerUrl" value="/servlet/user/pingServer" />
<c:url var="getNewTran" value="/servlet/central/newTransactionProcess" />
<c:url var="getLogCheckConn" value="/servlet/central/logCheckConnection" />
<c:url var="getNewImmi" value="/servlet/central/newImmihistory" />
<c:url var="getDulieuTN" value="/servlet/central/dulieutn" />
<c:url var="getPhoiIn" value="/servlet/central/bieuDoPhoiIn" />
<%-- <form:form commandName="jobList" id="form" name="form" > --%>


<link rel="stylesheet" type="text/css" href="/eppcentral/resources/style1/plugins/breaking-news-ticker/breaking-news-ticker.css">
<script src="/eppcentral/resources/style1/plugins/breaking-news-ticker/breaking-news-ticker.min.js"></script>
<style>
<!--

-->
.main-footer {
    display: none;
}

.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
    white-space: pre-wrap;
}
.cls-mg-bot {
	margin-top: 10px;
}

#bieuDoTheoDoiXLHS {
	width: 100%;
	height: 100%;
	margin: 0 auto
}

#bieuDoCaTheHoa1 {
	width: 100%;
	height: 100%;
	margin: 0 auto
}

#bieuDoCaTheHoa2 {
	width: 100%;
	height: 100%;
	margin: 0 auto
}

#tbXLHS input[type="text"] {
	width: 100%;
	text-align: right;
}

#tbTruyenNhanDL input[type="text"] {
	width: 100%;
	text-align: right;
	font-size: 18px;
	min-width: 40px;
}

#tbTruyenNhanDL tr td {
	text-align: left;
	font-size: 20px;
	padding: 10px 8px;
}

#tbXLHS tr td {
	text-align: left;
	font-size: 20px;
}

.font-bold {
    font-weight: bold;
}

.n_panel.d_panel {
    /* width: 100%; */
    position: absolute;
    top: 21px;
    right: 50px;
    display: block;
    z-index: 889;
    color: black;
    height: 40px;
    transition: all 0.4s;
    bottom: -350px;
    background: white;
}

.n_panel .fixWidth {
    position: relative;
}

.n_panel .wheels {
    /* width: 100%; */
    overflow: hidden;
}

.n_panel .wheel {
    position: relative;
    z-index: 0;
    float: left;
    width: 320px;
    overflow: hidden;
    transition: width 0.3s ease;
}

.n_panel .wheel .selectContainer {
    display: block;
    text-align: center;
    padding: 0;
    transition: transform 0.18s ease-out;
}

.n_panel .wheel .selectContainer li {
    font-size: 20px;
    display: block;
    height: 40px;
    line-height: 40px;
    padding: 0px 10px;
    cursor: pointer;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.n_panel .selectLine {
    height: 40px;
    width: 100%;
    position: absolute;
    top: 0px;
    pointer-events: none;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
    border: 1px solid #DCDCDC;
}

.n_panel .shadowMask {
    position: absolute;
    top: 0;
    width: 100%;
    height: 90px;
    background: linear-gradient(to bottom, rgba(255, 255, 255, 0), rgba(255, 255, 255, 0), #ffffff);
    opacity: 0.9;
    pointer-events: none;
}


div#bieuDoTraHoChieu * {
    font-size: 19px !important;
}

div#bieuDoCaTheHoa1 *, div#bieuDoCaTheHoa2 *,  div#bieuDoTheoDoiXLHS *, div#bieuDoXuatNhapCanh *{
    font-size: 19px !important;
}
</style>

<link rel="stylesheet" type="text/css" href="./breaking-news-ticker.css">
<form:form modelAttribute="formData" name="formData" > 

		<!--Content start-->
<div class="form-desing">
   <div> 
	   <div>
		   <div class="row">
		   	   <div class="ov_hidden">
			  <!--  <div class="new-heading-2">THỐNG KÊ</div> -->
			   		<div class="col-md-7">
			   			<fieldset>
			   				<legend style="font-size: 30px;">THEO DÕI XỬ LÝ HỒ SƠ</legend>
			   				<div class="n_panel d_panel">
			                    <div class="fixWidth">
			                        <div class="wheels">
			                            <div class="wheel" style ="width: 520px;"">
			                            <div id="newsTickerNewTrans">
				                            <div class="bn-news" id="myMarquee"">
				                                 <ul id="loadNewTrans" class="selectContainer" style="white-space: nowrap;">
				                                </ul>
				                            </div>
				                            </div>
			                            </div>
			                        </div>
			                        <div class="selectLine"></div>
			                        <div class="shadowMask"></div>
			                    </div>
			                </div>
			   				<div class="col-md-4">
				   				<div class="chart" id="bieuDoTheoDoiXLHS"></div>		
			   				</div>
			   				<div class="col-md-2">
			   				</div>
			   				<div class="col-md-6" style="margin-top: 55px;">
			   					<div id="tbXLHS">
					   				<table class="dt-table table">
					   					<tbody>
												<tr>
													<td style="width: 300px;"></td>
													<td class="text-center font-bold">MB</td>
													<!-- <td class="text-center font-bold">MT</td> -->
													<td class="text-center font-bold">MN</td>
												</tr>
												<tr>
													<td>TS HS trong ngày</td>
													<c:if test="${not empty reportMB}">
														<td><input style="background: #eee;" id="reporttotal_MB" type="text" readonly="readonly" value="${reportMB.dto.amountTotal}"/></td>
													</c:if>
													<%-- <c:if test="${not empty reportMT}">
														<td><input style="background: #eee;" id="reporttotal_MT" type="text" readonly="readonly" value="${reportMT.dto.amountTotal}"/></td>
													</c:if> --%>
													<c:if test="${not empty reportMN}">
														<td><input style="background: #eee;" id="reporttotal_MN" type="text" readonly="readonly" value="${reportMN.dto.amountTotal}"/></td>
													</c:if>
													
													<c:if test="${empty reportMB}">
														<td><input style="background: #eee;" id="reporttotal_MB" type="text" readonly="readonly"/></td>
													</c:if>
													<%-- <c:if test="${empty reportMT}">
														<td><input style="background: #eee;" id="reporttotal_MT" type="text" readonly="readonly"/></td>
													</c:if> --%>
													<c:if test="${empty reportMN}">
														<td><input style="background: #eee;" id="reporttotal_MN" type="text" readonly="readonly"/></td>
													</c:if>
												</tr>
												<tr>
													<td>Số HS duyệt cấp</td>
													<c:if test="${not empty reportMB}">
														<td><input style="background: #eee;" id="reportapprove_MB" type="text" readonly="readonly" value="${reportMB.dto.amountApprove}"/></td>
													</c:if>
													<%-- <c:if test="${not empty reportMT}">
														<td><input style="background: #eee;" id="reportapprove_MT" type="text" readonly="readonly" value="${reportMT.dto.amountApprove}"/></td>
													</c:if> --%>
													<c:if test="${not empty reportMN}">
														<td><input style="background: #eee;" id="reportapprove_MN" type="text" readonly="readonly" value="${reportMN.dto.amountApprove}"/></td>
													</c:if>
													
													<c:if test="${empty reportMB}">
														<td><input style="background: #eee;" id="reportapprove_MB" type="text" readonly="readonly"/></td>
													</c:if>
													<%-- <c:if test="${empty reportMT}">
														<td><input style="background: #eee;" id="reportapprove_MT" type="text" readonly="readonly"/></td>
													</c:if> --%>
													<c:if test="${empty reportMN}">
														<td><input style="background: #eee;" id="reportapprove_MN" type="text" readonly="readonly"/></td>
													</c:if>
												</tr>
												<tr>
													<td>Số HS bị từ chối</td>
													<c:if test="${not empty reportMB}">
														<td><input style="background: #eee;" id="reportreject_MB" type="text" readonly="readonly" value="${reportMB.dto.amountReject}"/></td>
													</c:if>
													<%-- <c:if test="${not empty reportMT}">
														<td><input style="background: #eee;" id="reportreject_MT" type="text" readonly="readonly" value="${reportMT.dto.amountReject}"/></td>
													</c:if> --%>
													<c:if test="${not empty reportMN}">
														<td><input style="background: #eee;" id="reportreject_MN" type="text" readonly="readonly" value="${reportMN.dto.amountReject}"/></td>
													</c:if>
													
													<c:if test="${empty reportMB}">
														<td><input style="background: #eee;" id="reportreject_MB" type="text" readonly="readonly"/></td>
													</c:if>
													<%-- <c:if test="${empty reportMT}">
														<td><input style="background: #eee;" id="reportreject_MT" type="text" readonly="readonly"/></td>
													</c:if> --%>
													<c:if test="${empty reportMN}">
														<td><input style="background: #eee;" id="reportreject_MN" type="text" readonly="readonly"/></td>
													</c:if>
												</tr>
												<tr>
													<td>Số HS sắp hết hạn</td>
													<c:if test="${not empty reportMB}">
														<td><input style="background: #eee;" id="reportexpire_MB" type="text" readonly="readonly" value="${reportMB.dto.amountAboutToExpire}"/></td>
													</c:if>
													<%-- <c:if test="${not empty reportMT}">
														<td><input style="background: #eee;" id="reportexpire_MT" type="text" readonly="readonly" value="${reportMT.dto.amountAboutToExpire}"/></td>
													</c:if> --%>
													<c:if test="${not empty reportMN}">
														<td><input style="background: #eee;" id="reportexpire_MN" type="text" readonly="readonly" value="${reportMN.dto.amountAboutToExpire}"/></td>
													</c:if>
													
													<c:if test="${empty reportMB}">
														<td><input style="background: #eee;" id="reportexpire_MB" type="text" readonly="readonly"/></td>
													</c:if>
													<%-- <c:if test="${empty reportMT}">
														<td><input style="background: #eee;" id="reportexpire_MT" type="text" readonly="readonly"/></td>
													</c:if> --%>
													<c:if test="${empty reportMN}">
														<td><input style="background: #eee;" id="reportexpire_MN" type="text" readonly="readonly"/></td>
													</c:if>
												</tr>
												<tr>
													<td>Số HS quá hạn</td>
													<c:if test="${not empty reportMB}">
														<td><input style="background: #eee;" id="reportover_MB" type="text" readonly="readonly" value="${reportMB.dto.amountOutOfDate}"/></td>
													</c:if>
													<%-- <c:if test="${not empty reportMT}">
														<td><input style="background: #eee;" id="reportover_MT" type="text" readonly="readonly" value="${reportMT.dto.amountOutOfDate}"/></td>
													</c:if> --%>
													<c:if test="${not empty reportMN}">
														<td><input style="background: #eee;" id="reportover_MN" type="text" readonly="readonly" value="${reportMN.dto.amountOutOfDate}"/></td>
													</c:if>
													
													<c:if test="${empty reportMB}">
														<td><input style="background: #eee;" id="reportover_MB" type="text" readonly="readonly"/></td>
													</c:if>
													<%-- <c:if test="${empty reportMT}">
														<td><input style="background: #eee;" id="reportover_MT" type="text" readonly="readonly"/></td>
													</c:if> --%>
													<c:if test="${empty reportMN}">
														<td><input style="background: #eee;" id="reportover_MN" type="text" readonly="readonly"/></td>
													</c:if>
												</tr>

											</tbody>
					   				</table>			
			   					</div>
			   				</div>	
			   			</fieldset>
			   		</div>
			   		<div class="col-md-5">
			   			<fieldset>
			   				<legend style="font-size: 30px;">CÁ THỂ HÓA</legend>
			   				<div class="col-md-6">
				   				<div id="bieuDoCaTheHoa1"></div>		
			   				</div>
			   				<div class="col-md-6">
				   				<div id="bieuDoCaTheHoa2"></div>				
			   				</div>
			   			</fieldset>
			   		</div>
       				<div class="col-md-4">
       					<fieldset>
       						<legend style="font-size: 30px;">TRẢ HỘ CHIẾU</legend>
       						<div id="bieuDoTraHoChieu" style="overflow: hidden;margin-bottom: 13px;"></div>
       					</fieldset>
       				</div>
       				
       				<div class="col-md-4">
       					<fieldset>
       						<legend style="font-size: 30px;">XNC TẠI CKHK</legend>
       						<div id="bieuDoXuatNhapCanh" style="overflow: hidden;margin-bottom: 13px;"></div>
       						<div class="n_panel d_panel" style="margin: 408px 60px;">
								<div class="fixWidth">
									<div class="wheels">
										<div class="wheel">
			                            <div id="newsTickerNewImmi1">
				                            <div class="bn-news">
				                                 <ul id="loadNewImmi1" class="selectContainer" style="white-space: nowrap;">
				                                </ul>
				                            </div>
			                            </div>
			                            </div>
									</div>
									<div class="selectLine"></div>
									<div class="shadowMask"></div>
								</div>
							</div>
       					</fieldset>
       				</div>
       				<div class="col-md-4">
       					<fieldset style="height: 470px;">
       						<legend style="font-size: 30px;">TRUYỀN NHẬN DỮ LIỆU</legend>
       						<div id="tbTruyenNhanDL">
       							<table class="dt-table table">
				   					<tbody>
					   					<tr>
					                        <td colspan="5">Trả KQ tra cứu</td>
				                            <td class="text-center" colspan="5"><input type="text" id="trakq" class="form-control" value="${rptStatic.trakq}" readonly=""></td>
				                        </tr>
				                        <tr>
				                            <td style="width: 50%"></td>
				                            <td class="text-center font-bold">NB</td>
				                            <td class="text-center font-bold">ĐN</td>
				                            <td class="text-center font-bold">TSN</td>
				                            <td class="text-center font-bold">PQ</td>
				                            <td class="text-center font-bold">CR</td>
				                            <td class="text-center font-bold">BQP</td>
				                        </tr>
				                        <tr>
				                            <td>Gửi DL XNC</td>
				                            <c:forEach items="${rptStatic.lstRpt}" var="item">
				                            	<c:choose>
											         <c:when test = "${item.siteCode == 'NB'}">
											            <td class="text-center"><input type="text" id="guidl_Nb" class="form-control dl-ip" value="${item.guidl_Nb}" readonly=""></td>
											         </c:when>
											         <c:when test = "${item.siteCode == 'DN'}">
											            <td class="text-center"><input type="text" id="guidl_Dn" class="form-control dl-ip" value="${item.guidl_Nb}" readonly=""></td>
											         </c:when>
											         <c:when test = "${item.siteCode == 'TSN'}">
											            <td class="text-center"><input type="text" id="guidl_Tsn" class="form-control dl-ip" value="${item.guidl_Nb}" readonly=""></td>
											         </c:when>
											         <c:when test = "${item.siteCode == 'PQ'}">
											            <td class="text-center"><input type="text" id="guidl_Pq" class="form-control dl-ip" value="${item.guidl_Nb}" readonly=""></td>
											         </c:when>
											         <c:when test = "${item.siteCode == 'CR'}">
											            <td class="text-center"><input type="text" id="guidl_Cr" class="form-control dl-ip" value="${item.guidl_Nb}" readonly=""></td>
											         </c:when>
											         <c:when test = "${item.siteCode == 'BQP-MC'}">
				                            			<td class="text-center"><input type="text" id="guidl_Bqp" class="form-control dl-ip" value="${item.guidl_Nb}" readonly=""></td>
											         </c:when>
											         <c:otherwise>
											         </c:otherwise>
											    </c:choose>
				                       		</c:forEach>
				                        </tr>
				                        <tr>
				                            <td>Nhận DL XNC</td>
				                            <c:forEach items="${rptStatic.lstRpt}" var="item">
				                            	<c:choose>
											         <c:when test = "${item.siteCode == 'NB'}">
											            <td class="text-center"><input type="text" id="nhandl_Nb" class="form-control dl-ip" value="${item.nhandl_Nb}" readonly=""></td>
											         </c:when>
											         <c:when test = "${item.siteCode == 'DN'}">
											            <td class="text-center"><input type="text" id="nhandl_Dn" class="form-control dl-ip" value="${item.nhandl_Nb}" readonly=""></td>
											         </c:when>
											         <c:when test = "${item.siteCode == 'TSN'}">
											            <td class="text-center"><input type="text" id="nhandl_Tsn" class="form-control dl-ip" value="${item.nhandl_Nb}" readonly=""></td>
											         </c:when>
											         <c:when test = "${item.siteCode == 'PQ'}">
											            <td class="text-center"><input type="text" id="nhandl_Pq" class="form-control dl-ip" value="${item.nhandl_Nb}" readonly=""></td>
											         </c:when>
											         <c:when test = "${item.siteCode == 'CR'}">
											            <td class="text-center"><input type="text" id="nhandl_Cr" class="form-control dl-ip" value="${item.nhandl_Nb}" readonly=""></td>
											         </c:when>
											         <c:when test = "${item.siteCode == 'BQP-MC'}">
				                            			<td class="text-center"><input type="text" id="nhandl_Bqp" class="form-control dl-ip" value="${item.nhandl_Nb}" readonly=""></td>
											         </c:when>
											         <c:otherwise>
											         </c:otherwise>
											    </c:choose>
				                       		</c:forEach>               
				                        </tr>
				                        <tr>
				                            <td>Gửi TT Hộ chiếu</td>
				                            <c:forEach items="${rptStatic.lstRpt}" var="item">
				                            	<c:choose>
											         <c:when test = "${item.siteCode == 'NB'}">
											            <td class="text-center"><input type="text" id="guihc_Nb" class="form-control dl-ip" value="${item.guihc_Bqp}" readonly=""></td>
											         </c:when>
											         <c:when test = "${item.siteCode == 'DN'}">
											            <td class="text-center"><input type="text" id="guihc_Dn" class="form-control dl-ip" value="${item.guihc_Bqp}" readonly=""></td>
											         </c:when>
											         <c:when test = "${item.siteCode == 'TSN'}">
											            <td class="text-center"><input type="text" id="guihc_Tsn" class="form-control dl-ip" value="${item.guihc_Bqp}" readonly=""></td>
											         </c:when>
											         <c:when test = "${item.siteCode == 'PQ'}">
											            <td class="text-center"><input type="text" id="guihc_Pq" class="form-control dl-ip" value="${item.guihc_Bqp}" readonly=""></td>
											         </c:when>
											          <c:when test = "${item.siteCode == 'CR'}">
											            <td class="text-center"><input type="text" id="guihc_Cr" class="form-control dl-ip" value="${item.guihc_Bqp}" readonly=""></td>
											         </c:when>
											         <c:when test = "${item.siteCode == 'BQP-MC'}">
											            <td class="text-center"><input type="text" id="guihc_Bqp" class="form-control dl-ip" value="${item.guihc_Bqp}" readonly=""></td>
											         </c:when>
											         <c:otherwise>
											         </c:otherwise>
											    </c:choose>
				                       		</c:forEach>
				                        </tr>
				                        <tr>
				                            <td>Gửi TT GT mất, hủy</td>
				                            <c:forEach items="${rptStatic.lstRpt}" var="item">
				                            	<c:choose>
											         <c:when test = "${item.siteCode == 'NB'}">
											            <td class="text-center"><input type="text" id="guimh_Nb" class="form-control dl-ip" value="${item.guimh_Bqp}" readonly=""></td>
											         </c:when>
											         <c:when test = "${item.siteCode == 'DN'}">
											              <td class="text-center"><input type="text" id="guimh_Dn" class="form-control dl-ip" value="${item.guimh_Bqp}" readonly=""></td>
											         </c:when>
											         <c:when test = "${item.siteCode == 'TSN'}">
											            <td class="text-center"><input type="text" id="guimh_Tsn" class="form-control dl-ip" value="${item.guimh_Bqp}" readonly=""></td>
											         </c:when>
											         <c:when test = "${item.siteCode == 'PQ'}">
											            <td class="text-center"><input type="text" id="guimh_Pq" class="form-control dl-ip" value="${item.guimh_Bqp}" readonly=""></td>
											         </c:when>
											         <c:when test = "${item.siteCode == 'CR'}">
											            <td class="text-center"><input type="text" id="guimh_Cr" class="form-control dl-ip" value="${item.guimh_Bqp}" readonly=""></td>
											         </c:when>
											         <c:when test = "${item.siteCode == 'BQP-MC'}">
											            <td class="text-center"><input type="text" id="guimh_Bqp" class="form-control dl-ip" value="${item.guimh_Bqp}" readonly=""></td>
											         </c:when>
											         <c:otherwise>
											         </c:otherwise>
											    </c:choose>
				                       		</c:forEach>
				                        </tr>
				                        <tr>
				                            <td colspan="5">Gửi TT Hộ chiếu ký CA cho BNG</td>
				                            <td class="text-center" colspan="5"><input type="text" id="kyca_bng" class="form-control" value="${rptStatic.kyca_bng}" readonly=""></td>
				                        </tr>
				                        
				                    </tbody>
				   				</table>
       						</div>
       						<div class="n_panel d_panel" style="bottom: 0; top: auto;">
		                    <div class="fixWidth">
		                        <div class="wheels">
		                            <div class="wheel" style="width: 420px;">
		                            <div id="newsTickerLogCheckConn">
			                            <div class="bn-news" id="myMarqueeLogConn">
			                                 <ul id="loadLogCheckConn" class="selectContainer" style="white-space: nowrap;">
			                                </ul>
			                            </div>
			                            </div>
		                            </div>
		                        </div>
		                        <div class="selectLine"></div>
		                        <div class="shadowMask"></div>
		                    </div>
			                </div>
       						<!-- <div class="n_panel d_panel" style="margin: 409px 50px;">
								<div class="fixWidth">
									<div class="wheels">
										<div class="wheel">
											
											   <div id="newsTickerNewImmi">
				                            <div class="bn-news">
				                                 <ul id="loadNewImmi" class="selectContainer" style="white-space: nowrap;">
				                                </ul>
				                            </div>
				                            </div>
										</div>
									</div>
									<div class="selectLine"></div>
									<div class="shadowMask"></div>
								</div>
							</div> -->
       					</fieldset>
       				</div>
			   </div>
		   </div>
<div id="dialog-confirm"></div>
<script type="text/javascript">
	
	/*Highcharts.chart('bieuDoCaTheHoa', {
	
	    title: {
	        text: ''
	    },
	
	    subtitle: {
	        text: ''
	    },
	
	    yAxis: {
	        title: {
	            text: 'Giá trị'
	        }
	    },
	    legend: {
	        layout: 'vertical',
	        align: 'right',
	        verticalAlign: 'middle'
	    },
	
	    plotOptions: {
	        series: {
	            label: {
	                connectorAllowed: false
	            },
	            pointStart: 2010
	        }
	    },
	
	    series: [{
	        name: 'Installation',
	        data: [43934, 52503, 57177, 69658, 97031, 119931, 137133, 154175]
	    }, {
	        name: 'Manufacturing',
	        data: [24916, 24064, 29742, 29851, 32490, 30282, 38121, 40434]
	    }, {
	        name: 'Sales & Distribution',
	        data: [11744, 17722, 16005, 19771, 20185, 24377, 32147, 39387]
	    }, {
	        name: 'Project Development',
	        data: [null, null, 7988, 12169, 15112, 22452, 34400, 34227]
	    }, {
	        name: 'Other',
	        data: [12908, 5948, 8105, 11248, 8989, 11816, 18274, 18111]
	    }],
	
	    responsive: {
	        rules: [{
	            condition: {
	                maxWidth: 500
	            },
	            chartOptions: {
	                legend: {
	                    layout: 'horizontal',
	                    align: 'center',
	                    verticalAlign: 'bottom'
	                }
	            }
	        }]
	    }
	
	});*/

	/*Highcharts.chart('bieuDoCaTheHoa', {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: ''
	    },
	    yAxis: {
	        title: {
	            text: 'Giá trị'
	        }
	    },
	    xAxis: {
	        categories: ['TT Cá thể hóa Miền Bắc', 'TT Cá thể hóa Miền Nam']
	    },
	    credits: {
	        enabled: false
	    },
	    series: [{
	        name: 'Đã sử dụng',
	        data: [49, 3]
	    }, {
	        name: 'Hỏng',
	        data: [5, 0]
	    }, {
	        name: 'Chưa sử dụng',
	        data: [246, 197]
	    }]
	});*/

	/* Highcharts.chart('bieuDoTraHoChieu', {
	    chart: {
	        type: 'bar'
	    },
	    title: {
	        text: ''
	    },
	    xAxis: {
	        categories: ['PA_HP', 'PA_TB', 'A72HCM', 'A72DN', 'PA_CT']
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: ''
	        }
	    },
	    legend: {
	        reversed: true
	    },
	    plotOptions: {
	        series: {
	            stacking: 'normal'
	        }
	    },
	    credits: {
	        enabled: false
	    },
	    series: [{
	        name: 'Chưa trả',
	        data: [5, 3, 4, 7, 2]
	    }, {
	        name: 'Đã trả',
	        data: [2, 2, 3, 2, 1]
	    }]
	}); */

	$(document).ready(function() {
		loadChartXLStatus();
		loadChartImmigrationStatus();
		loadChartIssuanceStatus();
		loadChartPrintStatus();
		newTransactionProcess();
		newImmihistory();
		logCheckConnection();
		loadPhoiin();

		/* window.setInterval(function() {
			loadChartXLStatusRedraw();
			loadChartImmigrationStatusRedraw();
			loadChartIssuanceStatusRedraw();
			loadChartPrintStatusRedraw();
			newTransactionProcess();
			newImmihistory();
			thongkeXLHS();
		}, 120000); */
		
		window.setInterval(function() {
			loadChartXLStatusRedraw();
		}, 12000);
		
		window.setInterval(function() {
			loadChartImmigrationStatusRedraw();
		}, 10000);
		
		window.setInterval(function() {
			loadChartIssuanceStatusRedraw();
		}, 9000);
		
		window.setInterval(function() {
			loadChartPrintStatusRedraw();
		}, 8000);
		
		window.setInterval(function() {
			newTransactionProcess();
		}, 7000);
		
		window.setInterval(function() {
			newImmihistory();
			dulieutruyennhan();
			loadPhoiinRedraw();
		}, 6000);

		window.setInterval(function() {
			thongkeXLHS();
		}, 5000);
		
		window.setInterval(function() {
			sessPingServer();
		}, 17000);
		
		window.setInterval(function() {
			logCheckConnection();
		}, 5000);
	});

	function loadChartXLStatus() {
		$.ajax({
					url : '${getBieuDo}',
					dataType : "json",
					type : "GET",
					contentType : 'application/json; charset=utf-8',
					success : function(response) {
						//var data = response.data;
						response.credits = {
								enabled : false
						};
						response.chart = {
							type : 'column'
						};
						response.plotOptions = {
							column : {
								stacking : 'normal'
							}
						};
						response.yAxis = {
							min : 0,
							title : {
								text : ''
							},
							stackLabels : {
								enabled : true,
								style : {
									fontWeight : 'bold',
									color : ( // theme
									Highcharts.defaultOptions.title.style && Highcharts.defaultOptions.title.style.color)
											|| 'gray'
								}
							}
						};
						response.tooltip = {
							headerFormat : '<b>{point.x}</b><br/>',
							pointFormat : '{series.name}: {point.y}<br/>Tổng số: {point.stackTotal}'
						};
						Highcharts.chart('bieuDoTheoDoiXLHS', response);
						//setInterval(function(){ loadChartXLStatusRedraw()}, 30000);
						/*  var chart = $('#bieuDoTheoDoiXLHS').highcharts();
						 Highcharts.each(chart.options.series, function (series, i) {
						     chart.series[i].setData(response.series[i].data, false);
						 }); */
						//chart.redraw();
					},
					error : function(xhr) {
						console.log('error');
					}
				});
	}

	function loadChartXLStatusRedraw() {
		$.ajax({
			url : '${getBieuDo}',
			dataType : "json",
			type : "GET",
			contentType : 'application/json; charset=utf-8',
			cache : false,
			success : function(response) {
				//var data = response.data;
				var chart = $('#bieuDoTheoDoiXLHS').highcharts();
				Highcharts.each(chart.options.series, function(series, i) {
					chart.series[i].setData(response.series[i].data, false);
				});
				chart.redraw(); 
				// $scope.$apply();

			},
			error : function(xhr) {
				console.log('error');
			}
		});
	}
	
	function loadPhoiin() {
		$.ajax({
					url : '${getPhoiIn}',
					dataType : "json",
					type : "GET",
					contentType : 'application/json; charset=utf-8',
					success : function(response) {
						//var data = response.data;
						response.credits = {
								enabled : false
						};
						response.chart = {
							type : 'column'
						};
						response.plotOptions = {
							column : {
								stacking : 'normal'
							}
						};
						response.yAxis = {
							min : 0,
							title : {
								text : ''
							},
							stackLabels : {
								enabled : true,
								style : {
									fontWeight : 'bold',
									color : ( // theme
									Highcharts.defaultOptions.title.style && Highcharts.defaultOptions.title.style.color)
											|| 'gray'
								}
							}
						};
						response.tooltip = {
							headerFormat : '<b>{point.x}</b><br/>',
							pointFormat : '{series.name}: {point.y}<br/>Tổng số: {point.stackTotal}'
						};
						Highcharts.chart('bieuDoCaTheHoa2', response);
						//setInterval(function(){ loadChartXLStatusRedraw()}, 30000);
						/*  var chart = $('#bieuDoTheoDoiXLHS').highcharts();
						 Highcharts.each(chart.options.series, function (series, i) {
						     chart.series[i].setData(response.series[i].data, false);
						 }); */
						//chart.redraw();
					},
					error : function(xhr) {
						console.log('error');
					}
				});
	}

	function loadPhoiinRedraw() {
		$.ajax({
			url : '${getPhoiIn}',
			dataType : "json",
			type : "GET",
			contentType : 'application/json; charset=utf-8',
			cache : false,
			success : function(response) {
				//var data = response.data;
				var chart = $('#bieuDoCaTheHoa2').highcharts();
				Highcharts.each(chart.options.series, function(series, i) {
					chart.series[i].setData(response.series[i].data, false);
				});
				chart.redraw();
				// $scope.$apply();

			},
			error : function(xhr) {
				console.log('error');
			}
		});
	}
	
	function thongkeXLHS() {
		$.ajax({
			url : '${getThongkeXLHS}',
			dataType : "json",
			type : "GET",
			contentType : 'application/json; charset=utf-8',
			cache : false,
			success : function(response) {
				if (response != null) {
					for (var i = 0; i < response.length; i++) {
						if(response[i].siteGroup == 'MB'){
							$('#reporttotal_MB').prop('readonly',false);
							$('#reporttotal_MB').val(response[i].dto.amountTotal);
							$('#reporttotal_MB').prop('readonly',true);
							
							$('#reportapprove_MB').prop('readonly',false);
							$('#reportapprove_MB').val(response[i].dto.amountApprove);
							$('#reportapprove_MB').prop('readonly',true);
							
							$('#reportreject_MB').prop('readonly',false);
							$('#reportreject_MB').val(response[i].dto.amountReject);
							$('#reportreject_MB').prop('readonly',true);
							
							$('#reportexpire_MB').prop('readonly',false);
							$('#reportexpire_MB').val(response[i].dto.amountAboutToExpire);
							$('#reportexpire_MB').prop('readonly',true);
							
							$('#reportover_MB').prop('readonly',false);
							$('#reportover_MB').val(response[i].dto.amountOutOfDate);
							$('#reportover_MB').prop('readonly',true);
						}
						else if (response[i].siteGroup == 'MT'){
							$('#reporttotal_MT').prop('readonly',false);
							$('#reporttotal_MT').val(response[i].dto.amountTotal);
							$('#reporttotal_MT').prop('readonly',true);
							
							$('#reportapprove_MT').prop('readonly',false);
							$('#reportapprove_MT').val(response[i].dto.amountApprove);
							$('#reportapprove_MT').prop('readonly',true);
							
							$('#reportreject_MT').prop('readonly',false);
							$('#reportreject_MT').val(response[i].dto.amountReject);
							$('#reportreject_MT').prop('readonly',true);
							
							$('#reportexpire_MT').prop('readonly',false);
							$('#reportexpire_MT').val(response[i].dto.amountAboutToExpire);
							$('#reportexpire_MT').prop('readonly',true);
							
							$('#reportover_MT').prop('readonly',false);
							$('#reportover_MT').val(response[i].dto.amountOutOfDate);
							$('#reportover_MT').prop('readonly',true);
						}
						else{
							$('#reporttotal_MN').prop('readonly',false);
							$('#reporttotal_MN').val(response[i].dto.amountTotal);
							$('#reporttotal_MN').prop('readonly',true);
							
							$('#reportapprove_MN').prop('readonly',false);
							$('#reportapprove_MN').val(response[i].dto.amountApprove);
							$('#reportapprove_MN').prop('readonly',true);
							
							$('#reportreject_MN').prop('readonly',false);
							$('#reportreject_MN').val(response[i].dto.amountReject);
							$('#reportreject_MN').prop('readonly',true);
							
							$('#reportexpire_MN').prop('readonly',false);
							$('#reportexpire_MN').val(response[i].dto.amountAboutToExpire);
							$('#reportexpire_MN').prop('readonly',true);
							
							$('#reportover_MN').prop('readonly',false);
							$('#reportover_MN').val(response[i].dto.amountOutOfDate);
							$('#reportover_MN').prop('readonly',true);
						}
					}
				}
			},
			error : function(xhr) {
				console.log('error');
			}
		});
	}
	
	function dulieutruyennhan() {
		$.ajax({
			url : '${getDulieuTN}',
			dataType : "json",
			type : "GET",
			contentType : 'application/json; charset=utf-8',
			cache : false,
			success : function(response) {
				if (response != null) {
					$('#trakq').prop('readonly',false);
					$('#trakq').val(response.trakq);
					$('#trakq').prop('readonly',true);
					$('#kyca_bng').prop('readonly',false);
					$('#kyca_bng').val(response.kyca_bng);
					$('#kyca_bng').prop('readonly',true);
					
					for (var i = 0; i < response.lstRpt.length; i++) {
						if(response.lstRpt[i].siteCode == 'NB'){
							$('#guidl_Nb').prop('readonly',false);
							$('#guidl_Nb').val(response.lstRpt[i].guidl_Nb);
							$('#guidl_Nb').prop('readonly',true);
							
							$('#nhandl_Nb').prop('readonly',false);
							$('#nhandl_Nb').val(response.lstRpt[i].nhandl_Nb);
							$('#nhandl_Nb').prop('readonly',true);
							
							$('#guihc_Nb').prop('readonly',false);
							$('#guihc_Nb').val(response.lstRpt[i].guihc_Bqp);
							$('#guihc_Nb').prop('readonly',true);
							
							$('#guimh_Nb').prop('readonly',false);
							$('#guimh_Nb').val(response.lstRpt[i].guimh_Bqp);
							$('#guimh_Nb').prop('readonly',true);
						}
						else if(response.lstRpt[i].siteCode == 'DN'){
							$('#guidl_Dn').prop('readonly',false);
							$('#guidl_Dn').val(response.lstRpt[i].guidl_Nb);
							$('#guidl_Dn').prop('readonly',true);
							
							$('#nhandl_Dn').prop('readonly',false);
							$('#nhandl_Dn').val(response.lstRpt[i].nhandl_Nb);
							$('#nhandl_Dn').prop('readonly',true);
							
							$('#guihc_Dn').prop('readonly',false);
							$('#guihc_Dn').val(response.lstRpt[i].guihc_Bqp);
							$('#guihc_Dn').prop('readonly',true);
							
							$('#guimh_Dn').prop('readonly',false);
							$('#guimh_Dn').val(response.lstRpt[i].guimh_Bqp);
							$('#guimh_Dn').prop('readonly',true);
						}
						else if(response.lstRpt[i].siteCode == 'TSN'){
							$('#guidl_Tsn').prop('readonly',false);
							$('#guidl_Tsn').val(response.lstRpt[i].guidl_Nb);
							$('#guidl_Tsn').prop('readonly',true);
							
							$('#nhandl_Tsn').prop('readonly',false);
							$('#nhandl_Tsn').val(response.lstRpt[i].nhandl_Nb);
							$('#nhandl_Tsn').prop('readonly',true);
							
							$('#guihc_Tsn').prop('readonly',false);
							$('#guihc_Tsn').val(response.lstRpt[i].guihc_Bqp);
							$('#guihc_Tsn').prop('readonly',true);
							
							$('#guimh_Tsn').prop('readonly',false);
							$('#guimh_Tsn').val(response.lstRpt[i].guimh_Bqp);
							$('#guimh_Tsn').prop('readonly',true);
						} 
						else if(response.lstRpt[i].siteCode == 'BQP-MC'){
							$('#guidl_Bqp').prop('readonly',false);
							$('#guidl_Bqp').val(response.lstRpt[i].guidl_Nb);
							$('#guidl_Bqp').prop('readonly',true);
							
							$('#nhandl_Bqp').prop('readonly',false);
							$('#nhandl_Bqp').val(response.lstRpt[i].nhandl_Nb);
							$('#nhandl_Bqp').prop('readonly',true);
							
							$('#guihc_Bqp').prop('readonly',false);
							$('#guihc_Bqp').val(response.lstRpt[i].guihc_Bqp);
							$('#guihc_Bqp').prop('readonly',true);
							
							$('#guimh_Bqp').prop('readonly',false);
							$('#guimh_Bqp').val(response.lstRpt[i].guimh_Bqp);
							$('#guimh_Bqp').prop('readonly',true);
						}
					}
				}
			},
			error : function(xhr) {
				console.log('error');
			}
		});
	}

	function loadChartImmigrationStatus() {
		$.ajax({
					url : '${getXNC}',
					dataType : "json",
					type : "GET",
					contentType : 'application/json; charset=utf-8',
					cache : false,
					success : function(response) {
						//var data = response.data;
						response.credits = {
								enabled : false
						};
						response.chart = {
							type : 'column'
						};
						response.plotOptions = {
							column : {
								stacking : 'normal'
							}
						};
						response.yAxis = {
							min : 0,
							title : {
								text : ''
							},
							stackLabels : {
								enabled : true,
								style : {
									fontWeight : 'bold',
									color : ( // theme
									Highcharts.defaultOptions.title.style && Highcharts.defaultOptions.title.style.color)
											|| 'gray'
								}
							}
						};
						tooltip = {
							headerFormat : '<b>{point.x}</b><br/>',
							pointFormat : '{series.name}: {point.y}<br/>Tổng số: {point.stackTotal}'
						};
						Highcharts.chart('bieuDoXuatNhapCanh', response);
						//setInterval(function(){ loadChartImmigrationStatusRedraw()}, 30000);
						////$scope.$apply();

					},
					error : function(xhr) {
						console.log('error');
					}
				});
	}

	function loadChartImmigrationStatusRedraw() {
		$.ajax({
			url : '${getXNC}',
			dataType : "json",
			type : "GET",
			contentType : 'application/json; charset=utf-8',
			cache : false,
			success : function(response) {
				var chart = $('#bieuDoXuatNhapCanh').highcharts();
				Highcharts.each(chart.options.series, function(series, i) {
					chart.series[i].setData(response.series[i].data, false);
				});
				chart.redraw();
			},
			error : function(xhr) {
				console.log('error');
			}
		});
	}

	function loadChartIssuanceStatus() {
		$.ajax({
					url : '${getTraHC}',
					dataType : "json",
					type : "GET",
					contentType : 'application/json; charset=utf-8',
					cache : false,
					success : function(response) {
						response.credits = {
								enabled : false
						};
						response.chart = {
							type : 'bar'
						};
						response.plotOptions = {
							series : {
								stacking : 'normal'
							}
						};
						response.yAxis = {
							min : 0,
							title : {
								text : ''
							},
							stackLabels : {
								enabled : true,
								style : {
									fontWeight : 'bold',
									color : (Highcharts.defaultOptions.title.style && Highcharts.defaultOptions.title.style.color)
											|| 'gray'
								}
							}
						};
						response.tooltip = {
							headerFormat : '<b>{point.x}</b><br/>',
							pointFormat : '{series.name}: {point.y}<br/>Tổng số: {point.stackTotal}'
						};
						Highcharts.chart('bieuDoTraHoChieu', response);
						//setInterval(function(){ loadChartIssuanceStatusRedraw();}, 30000);
						$('#bieuDoTraHoChieu').highcharts().yAxis[0].update({
						    visible: false
						});
					},
					error : function(xhr) {
						console.log('error');
					}
				});
	}

	function loadChartIssuanceStatusRedraw() {
		$.ajax({
			url : '${getTraHC}',
			dataType : "json",
			type : "GET",
			contentType : 'application/json; charset=utf-8',
			cache : false,
			success : function(response) {
				var chart = $('#bieuDoTraHoChieu').highcharts();
				Highcharts.each(chart.options.series, function(series, i) {
					chart.series[i].setData(response.series[i].data, false);
				});
				$('#bieuDoTraHoChieu').highcharts().yAxis[0].update({
				    visible: false
				});
				chart.redraw();
			},
			error : function(xhr) {
				console.log('error');
			}
		});
	}

	function loadChartPrintStatus() {
		$.ajax({
					url : '${getCathe}',
					dataType : "json",
					type : "GET",
					contentType : 'application/json; charset=utf-8',
					cache : false,
					success : function(response) {
						response.credits = {
								enabled : false
						};
						response.chart = {
							type : 'column'
						};
						response.plotOptions = {
							column : {
								stacking : 'normal',
								dataLabels : {
									enabled : true
								}
							}
						};
						response.yAxis = {
							min : 0,
							title : {
								text : ''
							},
							stackLabels : {
								enabled : true,
								style : {
									fontWeight : 'bold',
									color : (Highcharts.defaultOptions.title.style && Highcharts.defaultOptions.title.style.color)
											|| 'gray'
								}
							}
						};
						response.tooltip = {
							headerFormat : '<b>{point.x}</b><br/>',
							pointFormat : '{series.name}: {point.y}<br/>Tổng số: {point.stackTotal}'
						};
						Highcharts.chart('bieuDoCaTheHoa1', response);
						//setInterval(function(){ loadChartPrintStatusRedraw()}, 30000);
					},
					error : function(xhr) {
						console.log('error');
					}
				});
	}

	function loadChartPrintStatusRedraw() {
		$.ajax({
			url : '${getCathe}',
			dataType : "json",
			type : "GET",
			contentType : 'application/json; charset=utf-8',
			cache : false,
			success : function(response) {
				var chart = $('#bieuDoCaTheHoa1').highcharts();
				Highcharts.each(chart.options.series, function(series, i) {
					chart.series[i].setData(response.series[i].data, false);
				});
				chart.redraw();
			},
			error : function(xhr) {
				console.log('error');
			}
		});
	}

	function sessPingServer(form) {
		var ajaxUrl = '${pingServerUrl}';
		$.ajax({
			url : ajaxUrl,
			cache : false,
			success : function(data) {
				//TODO put any logic required here
			},
			error : function(e) {
				redirectForSessionTimeout();
			}
		});
	}

	function newTransactionProcess() {
		$.ajax({
			url : '${getNewTran}',
			dataType : "json",
			type : "GET",
			contentType : 'application/json; charset=utf-8',
			cache : false,
			success : function(response) {
				if (response != null) {
					//$('#loadNewTrans').empty();
					
					
					if($('#loadNewTrans li').length < 1)
					{
						for (var i = 0; i < response.length; i++) {
							$("#loadNewTrans").append(
									'<li>' + response[i].fullName + ' - '
											+ response[i].regSiteCode + ' - '
											+ response[i].stageList + '</li>');
						}

						$('#newsTickerNewTrans').breakingNews({
							effect: 'slide-right'
						});
					}else{
						$( "#loadNewTrans li" ).each(function( index ) {
							  $( this ).text(response[index].fullName + ' - ' + response[index].regSiteCode + ' - '+ response[index].stageList) ;
						});
					}
				
				}
			},
			error : function(xhr) {
				console.log('error');
			}
		});
	}
	
	function logCheckConnection() {
		$.ajax({
			url : '${getLogCheckConn}',
			dataType : "json",
			type : "GET",
			contentType : 'application/json; charset=utf-8',
			cache : false,
			success : function(response) {
				if (response != null) {
					
						if (response.length > 0) {
							if($('#loadLogCheckConn li').length != response.length)
							{
								$("#loadLogCheckConn").empty();
								for (var i = 0; i < response.length; i++) {
									$("#loadLogCheckConn").append(
											'<li style="color:red">'
													+ response[i].siteName + ' - '
													+ response[i].stage + '</li>');
								}
								$('#newsTickerLogCheckConn').breakingNews({
									effect: 'slide-right'
								});
							}else{
								$( "#loadLogCheckConn li" ).each(function( index ) {
									  $( this ).text(response[index].siteName + ' - ' + response[index].stage) ;
									  $( this ).css('color', 'red');
								});
							}
						}else{
							if($('#loadLogCheckConn li').length != 1)
							{
								$("#loadLogCheckConn").empty();
								$("#loadLogCheckConn").append(
										'<li style="color:blue">'
												+ 'KẾT NỐI ỔN ĐỊNH' + '</li>');
							}else{
								$( "#loadLogCheckConn li" ).each(function( index ) {
									  $( this ).text('KẾT NỐI ỔN ĐỊNH') ;
									  $( this ).css('color', 'blue');
								});
							}
						}
				}
			},
			error : function(xhr) {
				console.log('error');
			}
		});
	}

	function newImmihistory() {
		$.ajax({
			url : '${getNewImmi}',
			dataType : "json",
			type : "GET",
			contentType : 'application/json; charset=utf-8',
			cache : false,
			success : function(response) {
				if (response != null) {
					//$('#loadNewImmi').empty();
					

					if($('#loadNewImmi li').length < 1)
					{
						for (var i = 0; i < response.length; i++) {
							$("#loadNewImmi").append(
									'<li>' + response[i].fullName + ' - '
											+ response[i].regSiteCode + ' - '
											+ response[i].stageList + '</li>');
						}

						$('#newsTickerNewImmi').breakingNews({
							effect: 'slide-right'
						});
					}else{
						$( "#loadNewImmi li" ).each(function( index ) {
							  $( this ).text(response[index].fullName + ' - ' + response[index].regSiteCode + ' - '+ response[index].stageList) ;
						});
					}



					
					if($('#loadNewImmi1 li').length < 1)
					{
						for (var i = 0; i < response.length; i++) {
							$("#loadNewImmi1").append(
									'<li>' + response[i].fullName + ' - '
											+ response[i].regSiteCode + ' - '
											+ response[i].stageList + '</li>');
						}

						$('#newsTickerNewImmi1').breakingNews({
							effect: 'slide-right'
						});
					}else{
						$( "#loadNewImmi1 li" ).each(function( index ) {
							  $( this ).text(response[index].fullName + ' - ' + response[index].regSiteCode + ' - '+ response[index].stageList) ;
						});
					}
				}
			},
			error : function(xhr) {
				console.log('error');
			}
		});
	}
	Highcharts.chart('bieuDoTheoDoiXLHS', {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: ''
	    },
	    xAxis: {
	    	categories: ['AA', 'CC']
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: 'Số lượng hồ sơ'
	        },
	        stackLabels: {
	            enabled: true,
	            style: {
	                fontWeight: 'bold',
	                color: ( // theme
	                    Highcharts.defaultOptions.title.style &&
	                    Highcharts.defaultOptions.title.style.color
	                ) || 'gray'
	            }
	        }
	    },
	    tooltip: {
	        headerFormat: '<b>{point.x}</b><br/>',
	        pointFormat: '{series.name}: {point.y}<br/>Total: {point.stackTotal}'
	    },
	    credits: {
	        enabled: false
	    },
	    plotOptions: {
	        column: {
	            stacking: 'normal',
	            dataLabels: {
	                enabled: true
	            }
	        }
	    }/* ,
	    series: [{
	        name: 'Chưa xử lý',
	        color : '#7cb5ec',
	        data: [17, 0]
	    }, {
	        name: 'Chưa xử lý(Cảnh báo)',
	        color : '#e22222',
	        data: [0, 50]
	    }, {
	        name: 'Đã XL',
	        color : '#90ed7d',
	        data: [18, 3]
	    }] */
	});
	

	/*Highcharts.chart('bieuDoXuatNhapCanh', {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: ''
	    },
	    xAxis: {
	    	categories: ['TSN', 'DN', 'NB']
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: ''
	        },
	        stackLabels: {
	            enabled: true,
	            style: {
	                fontWeight: 'bold',
	                color: ( 
	                    Highcharts.defaultOptions.title.style &&
	                    Highcharts.defaultOptions.title.style.color
	                ) || 'gray'
	            }
	        }
	    },
	    legend: {
	        align: 'lert',
	        x: 0,
	        verticalAlign: 'bottom',
	        y: 25,
	        floating: true,
	        backgroundColor:
	            Highcharts.defaultOptions.legend.backgroundColor || 'white',
	        borderColor: '#CCC',
	        borderWidth: 1,
	        shadow: false
	    },
	    tooltip: {
	        headerFormat: '<b>{point.x}</b><br/>',
	        pointFormat: '{series.name}: {point.y}<br/>Total: {point.stackTotal}'
	    },
	    credits: {
	        enabled: false
	    },
	    plotOptions: {
	        column: {
	            stacking: 'normal',
	            dataLabels: {
	                enabled: true
	            }
	        }
	    },
	    series: [{
	        name: 'Vi phạm XC',
	        data: [1, 2, 3, 1]
	    }, {
	        name: 'Xuất cảnh',
	        data: [3, 17]
	    }, {
	        name: 'Vi phạm NC',
	        data: [3, 17]
	    }, {
	        name: 'Nhập cảnh',
	        data: [3, 17]
	    }]
	});	*/

	/* var chartXNC = new Highcharts.chart('bieuDoXuatNhapCanh', {

	    chart: {
	        type: 'column'
	    },

	    title: {
	        text: ''
	    },

	    xAxis: {
	        categories: ['TSN', 'DN', 'NB']
	    },
	    credits: {
	        enabled: false
	    },
	    yAxis: {
	        allowDecimals: false,
	        min: 0,
	        title: {
	            text: ''
	        }
	    },

	    tooltip: {
	        formatter: function () {
	            return '<b>' + this.x + '</b><br/>' +
	                this.series.name + ': ' + this.y + '<br/>' +
	                'Total: ' + this.point.stackTotal;
	        }
	    },

	    plotOptions: {
	        column: {
	            stacking: 'normal'
	        }
	    },

	    series: [{
	        name: 'Vi phạm XC',
	        data: [1, 2, 0],
	        stack: ''
	    }, {
	        name: 'Xuất cảnh',
	        data: [3, 13, 23],
	        stack: ''
	    }, {
	        name: 'Vi phạm NC',
	        data: [0, 3, 1],
	        stack: ''
	    }, {
	        name: 'Nhập cảnh',
	        data: [6, 17, 18],
	        stack: ''
	    }]
	}); */

	/* Highcharts.chart('bieuDoCaTheHoa1', {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: ''
	    },
	    xAxis: {
	    	categories: ['MN', 'MB']
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: 'Số lượng phôi'
	        },
	        stackLabels: {
	            enabled: true,
	            style: {
	                fontWeight: 'bold',
	                color: ( // theme
	                    Highcharts.defaultOptions.title.style &&
	                    Highcharts.defaultOptions.title.style.color
	                ) || 'gray'
	            }
	        }
	    },
	    legend: {
	        align: 'lert',
	        x: 0,
	        verticalAlign: 'bottom',
	        y: 25,
	        floating: true,
	        backgroundColor:
	            Highcharts.defaultOptions.legend.backgroundColor || 'white',
	        borderColor: '#CCC',
	        borderWidth: 1,
	        shadow: false
	    },
	    tooltip: {
	        headerFormat: '<b>{point.x}</b><br/>',
	        pointFormat: '{series.name}: {point.y}<br/>Total: {point.stackTotal}'
	    },
	    credits: {
	        enabled: false
	    },
	    plotOptions: {
	        column: {
	            stacking: 'normal',
	            dataLabels: {
	                enabled: true
	            }
	        }
	    },
	    series: [{
	        name: 'Chưa in',
	        data: [5, 5]
	    }, {
	        name: 'Đã in',
	        data: [3, 17]
	    }]
	}); */

	/*Highcharts.chart('bieuDoCaTheHoa2', {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: ''
	    },
	    xAxis: {
	    	categories: ['MN', 'MB']
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: 'Số lượng phôi'
	        },
	        stackLabels: {
	            enabled: true,
	            style: {
	                fontWeight: 'bold',
	                color: ( 
	                    Highcharts.defaultOptions.title.style &&
	                    Highcharts.defaultOptions.title.style.color
	                ) || 'gray'
	            }
	        }
	    },
	    legend: {
	        align: 'lert',
	        x: 0,
	        verticalAlign: 'bottom',
	        y: 25,
	        floating: true,
	        backgroundColor:
	            Highcharts.defaultOptions.legend.backgroundColor || 'white',
	        borderColor: '#CCC',
	        borderWidth: 1,
	        shadow: false
	    },
	    tooltip: {
	        headerFormat: '<b>{point.x}</b><br/>',
	        pointFormat: '{series.name}: {point.y}<br/>Total: {point.stackTotal}'
	    },
	    plotOptions: {
	        column: {
	            stacking: 'normal',
	            dataLabels: {
	                enabled: true
	            }
	        }
	    },
	    series: [{
	        name: 'Chưa sử dụng',
	        data: [195, 246]
	    }, {
	        name: 'Lỗi, hỏng',
	        data: [2, 6]
	    },{
	        name: 'Đã sử dụng',
	        data: [3, 48]
	    }, , {
	        name: 'Chưa sử dụng(CB)',
	        data: [205, 300]
	    },]
	});*/

	 Highcharts.chart('bieuDoCaTheHoa2',{
		chart : {
			type : 'column'
		},
		title : {
			text : ''
		},
		xAxis : {
			categories : [ 'AA', 'CC' ]
		},
		yAxis : {
			min : 0,
			title : {
				text : ''
			},
			stackLabels : {
				enabled : true,
				style : {
					fontWeight : 'bold',
					color : ( // theme
					Highcharts.defaultOptions.title.style && Highcharts.defaultOptions.title.style.color)
							|| 'gray'
				}
			}
		},
		tooltip : {
			headerFormat : '<b>{point.x}</b><br/>',
			pointFormat : '{series.name}: {point.y}<br/>Total: {point.stackTotal}'
		},
		credits : {
			enabled : false
		},
		plotOptions : {
			column : {
				stacking : 'normal',
				dataLabels : {
					enabled : true
				}
			}
		}/* ,
		series : [ {
			name : 'Chưa SD',
			color : '#7cb5ec',
			data : [ 0, 30 ]
		}, {
			name : 'Lỗi, hỏng',
			color : '#434348',
			data : [ 2, 6 ]
		}, {
			name : 'Đã SD',
			color : '#90ed7d',
			data : [ 13, 48 ]
		}, {
			name : 'Chưa SD (CB)',
			color : '#e22222',
			data : [ 195, 0]
		} ] */
	}); 
</script>
	
	
	
	<script>
	

	</script>
</div>
</div>
</div>
</form:form>



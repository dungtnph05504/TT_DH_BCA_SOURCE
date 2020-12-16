<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


                    <div class="col-sm-3 speech-bubble" style="margin-left:60px;font-weight: bold;">DANH SÁCH HỒ SƠ</div>
                    <div class="col-sm-3 speech-bubble7" style="width:255px;font-weight: bold;">XỬ LÝ HỒ SƠ</div>
                    <div class="col-sm-3 speech-bubble8" style="width:255px;font-weight: bold;">IN HỘ CHIẾU</div>
                    <div class="col-sm-3 speech-bubble9" style="margin-right:0px;font-weight: bold;">PHÁT HÀNH HỘ CHIẾU</div>
                    <div class="col-sm-12" style="height:450px;">
                    	<c:if test="${not empty danhsach.step1}">
                    		<c:choose>
							    <c:when test="${danhsach.step1.isPick}">
							        <div class="speech-bubble2 hhhh" style="top:20px;left:45px;background:#DDDDDD;">
							    </c:when>
							    <c:otherwise>
							        <div class="speech-bubble2" style="top:20px;left:45px;background:#DDDDDD;">
							    </c:otherwise>
							</c:choose>
		                      <!-- ngRepeat: item in tientrinhA.listDSA track by $index -->
		                      <div class="ng-scope">
		                          <p style="margin:0px;"><b class="ng-binding">${danhsach.step1.typePackage} ${danhsach.step1.packageId}</b></p>
		                          <p style="margin:0px;" class="ng-binding">Nơi TN:${danhsach.step1.regSiteCode}</p>
		                          <p style="margin:0px;" class="ng-binding">Số lượng:${danhsach.step1.amount} hồ sơ</p>
		                          <p style="margin:0px;" class="ng-binding">${danhsach.step1.date}</p>
		                      </div><!-- end ngRepeat: item in tientrinhA.listDSA track by $index -->            
		                   </div>     
                        </c:if>
                        <c:if test="${not empty danhsach.step2}">
                         	<c:forEach items="${danhsach.step2}" var="item">
                         		<c:if test="${item.status == 3}">
                         			 <c:choose>
									    <c:when test="${item.isPick}">
									    	<div class="speech-bubble2 hhhh" style="top:20px;left:350px;background:#0033FF;color:white;" ng-show="dsb">
									    </c:when>
									    <c:otherwise>
									        <div class="speech-bubble2" style="top:20px;left:350px;background:#0033FF;color:white;" ng-show="dsb">
									    </c:otherwise>
									</c:choose>
					                    <!-- ngRepeat: item in tientrinhA.listXLA track by $index -->              
						                <div class="ng-scope">
				                            <p style="margin:0px;"><b class="ng-binding">${item.typePackage} ${item.packageId}</b></p>
				                            <p style="margin:0px;" class="ng-binding">${item.regSiteCode}</p>
				                            <p style="margin:0px;" class="ng-binding">Số lượng:${item.amount} hồ sơ</p>
				                            <p style="margin:0px;" class="ng-binding">${item.date}</p>
				                        </div>
			                        </div>
                         		</c:if>
                         		<c:if test="${item.status == 2}">
		                         			<c:choose>
											    <c:when test="${item.isPick}">
											    	<div class="speech-bubble2 hhhh" style="top:120px;left:350px;background:#009933;color:white;" ng-show="daxl">
											    </c:when>
											    <c:otherwise>
											        <div class="speech-bubble2" style="top:120px;left:350px;background:#009933;color:white;" ng-show="daxl">
											    </c:otherwise>
											</c:choose>
		                        	
					                       <!-- ngRepeat: item in tientrinhA.listXLA track by $index -->
					                       <div class="ng-scope">
					                            <p style="margin:0px;"><b class="ng-binding">${item.typePackage} ${item.packageId}</b></p>
					                            <p style="margin:0px;" class="ng-binding">${item.regSiteCode}</p>
					                            <p style="margin:0px;" class="ng-binding">Số lượng:${item.amount} hồ sơ</p>
					                            <p style="margin:0px;" class="ng-binding">${item.date}</p>
				                        	</div>
					                	</div>
                         		</c:if>
	                        	<c:if test="${item.status == 1}">
				                        <c:choose>
										    <c:when test="${item.isPick}">
										    	<div class="speech-bubble2 hhhh" style="top:220px;left:350px;background:#DDDDDD;" ng-show="chuaxl">
										    </c:when>
										    <c:otherwise>
										        <div class="speech-bubble2" style="top:220px;left:350px;background:#DDDDDD;" ng-show="chuaxl">
										    </c:otherwise>
										</c:choose>
			                            <div class="ng-scope">
			                                <p style="margin:0px;"><b class="ng-binding">${item.typePackage} ${item.packageId}</b></p>
			                                <p style="margin:0px;" class="ng-binding">${item.regSiteCode}</p>
			                                <p style="margin:0px;" class="ng-binding">Số lượng:${item.amount} hồ sơ</p>
			                                <p style="margin:0px;" class="ng-binding">${item.date}</p>
			                            </div><!-- end ngRepeat: itemB in tientrinhA.listDSB track by $index -->
			                        </div>
		                        </c:if>
	                        </c:forEach>
                        </c:if>
                        
                        <c:if test="${not empty danhsach.step3}">
	                        <c:forEach items="${danhsach.step3}" var="item">
	                        	<c:if test="${item.status == 1}">
		                        		<c:choose>
										    <c:when test="${item.isPick}">
										    	<div class="speech-bubble2 hhhh"  style="top:220px;left:655px;background:#DDDDDD;" ng-show="chuain">
										    </c:when>
										    <c:otherwise>
										        <div class="speech-bubble2"  style="top:220px;left:655px;background:#DDDDDD;" ng-show="chuain">
										    </c:otherwise>
										</c:choose>
	                        		
			                            <!-- ngRepeat: item in tientrinhA.listDIN track by $index -->
			                            <div class="ng-scope">
				                            <p style="margin:0px;"><b class="ng-binding">${item.typePackage} ${item.packageId}</b></p>
				                            <p style="margin:0px;" class="ng-binding">${item.regSiteCode}</p>
				                            <p style="margin:0px;" class="ng-binding">Số lượng:${item.amount} hồ sơ</p>
				                            <p style="margin:0px;" class="ng-binding">${item.date}</p>
				                        </div>
			                        </div>
	                        	</c:if>
	                        	<c:if test="${item.status == 2}">
		                        		<c:choose>
										    <c:when test="${item.isPick}">
										    	<div class="speech-bubble2 hhhh" style="top:120px;left:655px;background:#009933;color:white;" ng-show="dtra">
										    </c:when>
										    <c:otherwise>
										        <div class="speech-bubble2" style="top:120px;left:655px;background:#009933;color:white;" ng-show="dtra">
										    </c:otherwise>
										</c:choose>
	                        		
			                            <!-- ngRepeat: item in tientrinhA.listDIN track by $index -->
			                            <div class="ng-scope">
				                            <p style="margin:0px;"><b class="ng-binding">${item.typePackage} ${item.packageId}</b></p>
				                            <p style="margin:0px;" class="ng-binding">${item.regSiteCode}</p>
				                            <p style="margin:0px;" class="ng-binding">Số lượng:${item.amount} hồ sơ</p>
				                            <p style="margin:0px;" class="ng-binding">${item.date}</p>
				                        </div>
			                        </div>
	                        	</c:if>
	                        	<c:if test="${item.status == 3}">
				                        <c:choose>
										    <c:when test="${item.isPick}">
										    	<div class="speech-bubble2 hhhh" style="top:20px;left:655px;background:#0033FF;color:white;">
										    </c:when>
										    <c:otherwise>
										        <div class="speech-bubble2" style="top:20px;left:655px;background:#0033FF;color:white;">
										    </c:otherwise>
										</c:choose>
			                            <!-- ngRepeat: item in tientrinhA.listDSC track by $index -->
			                            <div class="ng-scope">
			                                <p style="margin:0px;"><b class="ng-binding"> ${item.typePackage} ${item.packageId}</b></p>
			                                <p style="margin:0px;" class="ng-binding">${item.regSiteCode}</p>
			                                <p style="margin:0px;" class="ng-binding">Số lượng:${item.amount} hồ sơ</p>
			                                <p style="margin:0px;" class="ng-binding">${item.date}</p>
			                            </div><!-- end ngRepeat: item in tientrinhA.listDSC track by $index -->
			                        </div>
	                        	</c:if>
	                        </c:forEach>
	                    </c:if>
	                    <c:if test="${not empty danhsach.step4}">
	                    	<c:forEach items="${danhsach.step4}" var="item">
	                        	<c:if test="${item.status == 1}">
				                        <div class="speech-bubble2" style="top:20px;left:960px;background:#009933;color:white;" ng-show="dtra">
				                            <div class="ng-scope">
				                                <p class="ng-binding">Số lượng:${item.amount} hồ sơ</p>
				                            </div><!-- end ngRepeat: item in tientrinhA.listCTRA track by $index -->
			                        	</div>
		                        </c:if>
		                        <c:if test="${item.status == 2}">
				                       	<div class="speech-bubble2" style="top:120px;left:960px;background:#DDDDDD;" ng-show="ctra">
				                            <div class="ng-scope">
				                                <p class="ng-binding">Số lượng:${item.amount} hồ sơ</p>
				                            </div><!-- end ngRepeat: item in tientrinhA.listCTRA track by $index -->
			                        	</div>
		                        </c:if>
		                        <c:if test="${item.status == 3}">
				                        <div class="speech-bubble2 ng-hide" style="top:220px;left:960px;background:#ff0000;color:white;" ng-show="tral">
				                            <div class="ng-scope">
				                                <p class="ng-binding">Số lượng:${item.amount} hồ sơ</p>
				                            </div><!-- end ngRepeat: item in tientrinhA.listCTRA track by $index -->
			                        	</div>
		                        </c:if>
	                        </c:forEach>
						</c:if>
						
						<c:if test="${not empty danhsach.step2}">
						 	<div class="speech-bor3" style="top:60px;left:235px;width:50px" ng-show="daxlchuaxldsb"></div>
							<c:forEach items="${danhsach.step2}" var="item">
								<c:if test="${item.status == 3}">
			                        <div class="speech-bor" style="top:60px;left:285px;width:59px" ng-show="dsb"></div>
			                    </c:if>
			                    <c:if test="${item.status == 2}">
			                        <div class="speech-bor" style="top:160px;left:285px;width:59px" ng-show="daxl"></div>
			                        <div class="speech-bor2" style="top:60px;left:285px;height:100px" ng-show="daxlchuaxl"></div>
			                     </c:if>
			                     <c:if test="${item.status == 1}">
			                     	<div class="speech-bor2" style="top:60px;left:285px;height:100px" ng-show="daxlchuaxl"></div>
			                        <div class="speech-bor" style="top:260px;left:285px;width:59px" ng-show="chuaxl"></div>
			                        <div class="speech-bor2" style="top:160px;left:285px;height:100px" ng-show="chuaxl"></div>
								 </c:if>
							</c:forEach>
						</c:if>
                        <c:if test="${not empty danhsach.step3}">
                        	<div class="speech-bor3" style="top:60px;left:540px;width:50px" ng-show="dainchuaindsc"></div>
                        	<c:forEach items="${danhsach.step3}" var="item">
                        		<c:if test="${item.status == 3}">
		                       	 	<div class="speech-bor" style="top:60px;left:590px;width:59px" ng-show="dsc"></div>
		                        </c:if>
			                    <c:if test="${item.status == 2}">
			                        <div class="speech-bor" style="top:160px;left:590px;width:59px" ng-show="dain"></div>
			                        <div class="speech-bor2" style="top:60px;left:590px;height:100px" ng-show="dainchuain"></div>
		                        </c:if>
			                    <c:if test="${item.status == 1}">
			                    	<div class="speech-bor2" style="top:60px;left:590px;height:100px" ng-show="dainchuain"></div>
			                        <div class="speech-bor" style="top:260px;left:590px;width:59px" ng-show="chuain"></div>
			                        <div class="speech-bor2" style="top:160px;left:590px;height:100px" ng-show="chuain"></div>
								</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${not empty danhsach.step4}">
                        	<div class="speech-bor3" style="top:60px;left:845px;width:50px" ng-show="tralctradtra"></div>
                        	<c:forEach items="${danhsach.step4}" var="item">
                        		<c:if test="${item.status == 1}">
                        			<div class="speech-bor ng-hide" style="top:60px;left:895px;width:59px" ng-show="dtra"></div>
                         		</c:if>
			                    <c:if test="${item.status == 2}">
			                        <div class="speech-bor" style="top:160px;left:895px;width:59px" ng-show="ctra"></div>
			                        <div class="speech-bor2" style="top:60px;left:895px;height:100px" ng-show="tralctra"></div>
                        		</c:if>
			                    <c:if test="${item.status == 3}">
			                    	<div class="speech-bor2" style="top:60px;left:895px;height:100px" ng-show="tralctra"></div>
			                        <div class="speech-bor" style="top:260px;left:895px;width:59px" ng-show="tral"></div>
			                        <div class="speech-bor2" style="top:160px;left:895px;height:100px" ng-show="tral"></div>
								</c:if>
							</c:forEach>
						</c:if>
                    </div>
                    <div class="col-sm-12" style="text-align:right">
                        <b>Xử lý hồ sơ:</b>
                        <span style="padding:2px 5px 2px 5px;background:#DDDDDD;">Chưa xử lý</span>
                        <span style="padding:2px 5px 2px 5px;background:#009933;color:white;">Đã xử lý</span>
                        <span style="padding:2px 5px 2px 5px;background:#0033FF;color:white;margin-right:15px;">Đã ký duyệt</span>

                        <b>In hộ chiếu:</b>
                        <span style="padding:2px 5px 2px 5px;background:#DDDDDD;">Chưa in</span>
                        <span style="padding:2px 5px 2px 5px;background:#009933;color:white;">Đã in</span>
                        <span style="padding:2px 5px 2px 5px;background:#0033FF;color:white;margin-right:15px;">Đã phát hành</span>

                        <b>Phát hành hộ chiếu:</b>
                        <span style="padding:2px 5px 2px 5px;background:#DDDDDD;">Chưa trả</span>
                        <span style="padding:2px 5px 2px 5px;background:#009933;color:white;">Đã trả</span>
                        <span style="padding:2px 5px 2px 5px;background:#ff0000;color:white;margin-right:53px;">Trả lại</span>
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







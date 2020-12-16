<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:url var="submitUrl" value="/servlet/report/save"/>
<c:url var="hiddenInptUrl" value="/servlet/report/query_ajax"/>
 <c:url var="url" value="/servlet/report"/>
 <c:url var="frdDefUrl" value="/servlet/report/view"/>
 <c:url var="frdEditPgUrl" value="/servlet/report/edit"/>
 <c:url var="frdCnclRqUrl" value="/servlet/report/cancelFrdToReqPage"/>
 <c:url var="definitionUrl" value="/servlet/report/definition" />
 
 

<form:form modelAttribute="nicReportForm"  id="nicReportForm"  name="nicReportForm"   method="GET" cssClass="inline">
<!--  <div id="main"> -->
 <div class="form-desing">
 	<div>
	        	<div class="row">
	        		<div class="ov_hidden">
 					<div class="new-heading-2">QUẢN LÝ BÁO CÁO</div>               
               <div id="dropdown3">
                    <div class="col-md-6" style="padding-left: 0px;">
						<div class="col-md-4">
							<span class="cla-font">Lựa chọn danh mục:</span>
						</div>
						<div class="col-md-8">
							<form:select id="reportCategory" path="reportCategory" style="width: 70%;">
								<form:option value="NONE" label="--- Chọn ---" />
								<form:options items="${reportCategoryList}" itemLabel="codeValueDesc" itemValue="id.codeValue" />
							</form:select>
						</div>
					</div>
                  </div>        
                <p>&nbsp;</p>
                <!--newly added-->
                <!--<div class="col-md-12">
	                <div id="fortable" style="display: none">
	                    <table id="statResult">
	                       <tr>
	                            <td colspan=4>&nbsp;</td>
	                        </tr>
	                    </table>                                     
	              </div>
                </div>-->
                <div style="margin-top: 50px;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="max-width: 150px;">Mã báo cáo
								
								      </th>	
								      <th class="th-sm" style="min-width: 150px;">Tên báo cáo
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Mô tả
								
								      </th>
								      <th class="th-sm" style="min-width: 150px;">Ưu tiên báo cáo
								
								      </th>								     
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>							    
									      <td>${item.reportId}</td>
									      <td><a style="color: blue;" href="#" onclick="doViewIt('${item.reportId}')">${item.reportName}</a></td>
									      <td>${item.description}</td>	
									      <td>${item.reportPriority}</td>							     
									    </tr>
								    </c:forEach>
								  </tbody>
								</table>
								<div class="col-md-5 col-sm-12" style="padding-left: 0px;">
									<div class="dataTables_info">Hiển thị ${startIndex} đến ${endIndex} của ${totalRecord} kết quả</div>
								</div>
								<div class="col-md-7 col-sm-12" style="padding-right: 0px;">
									<ul style="float: right;" class="pagination" id="pagination"></ul>
								</div>			 
								<input type="hidden" name="pageNo" id="pageNo" /> 
						      </div>
 <form:hidden path="reportId" id="reportId" name="reportId"/>
 <form:hidden  id="categoryLoad" path="categoryLoad" name="categoryLoad"/>

 
 <script type="text/javascript">
 //$('#dtBasicExample').DataTable();
 //$('.dataTables_length').addClass('bs-select');
var totalPages = ${totalPage};
var currentPage = ${pageNo};
var pageSize = ${pageSize};
window.pagObj = $('#pagination').twbsPagination({
			totalPages: totalPages,
			visiblePages: pageSize,
			startPage: currentPage,
			next: '<span class=\"glyphicon glyphicon-forward fix-paging\"></span>',
			prev: '<span class=\"glyphicon glyphicon-backward fix-paging\"></span>',
			last: '<span class=\"glyphicon glyphicon-step-forward fix-paging\"></span>',
			first: '<span class=\"glyphicon glyphicon-step-backward fix-paging\"></span>',
			onPageClick: function (event, page) {
				if (currentPage != page) {
					$('#pageNo').val(page);
					document.forms["nicReportForm"].action = '${hiddenInptUrl}/${idReport}';  
					document.forms["nicReportForm"].submit();  
				}
			}
		});
/* $("#statResult").flexigrid({		
		
		dataType : 'json',
		colModel : [
					{display: 'Mã báo cáo', name : 'reportId', width : 308, sortable : true, align: 'left'},
					{display: 'Tên báo cáo', name : 'reportName', width : 324, sortable : true, align: 'left',render: renderLink},
					{display: 'Mô tả', name : 'description', width : 419, sortable : true, align: 'left'},
					{display: 'Ưu tiên báo cáo', name : 'reportPriority', width : 172, align: 'left'}
									
				],
		sortname: "reportId",
		sortorder: "asc",
		 title : 'Quản lý báo cáo',
		 usepager : true,
		 useRp : true,
		 rp : 5,
		 showTableToggleBtn : true,   
		 height: 250,
		 singleSelect : true,
		 nowrap : false
		
	});*/
 $("#reportCategory").change(function(){
	var id= $("#reportCategory").val();
	var url = "${hiddenInptUrl}/"+ id;
	document.forms["nicReportForm"].action = url;
    document.forms["nicReportForm"].submit();
		/*$.ajax({
			url : url,
			cache: false,
			type: "POST",			
			success : function(data) {
				if(data.length > 0){
					var tb = $('#dtBasicExample').DataTable();	
					tb.clear();
		        	for(var i = 0; data.length; i++){
		        		tb.row.add([
		        		             data[i].reportId,
		        		             "<a href=\"#\" style=\"color: blue;\" onclick=\"doViewIt('" + data[i].reportId +"')\">" + data[i].reportName +"</a>",
		        		             data[i].description,
		        		             data[i].reportPriority
		        		]).draw(false);
		        	}
					
				}else{
					$.alert({
						title: 'Thông báo',
						content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Không tìm thấy dữ liệu",
						buttons: {"Đóng": function () {}}
					});
				}
			}
		});*/
});

function renderLink(content, currentRow){		
	return "<a href=\"#\" onclick=\"doViewIt('" + currentRow.reportId +"')\">" + currentRow.reportName +"</a>";
} 
 

function doViewIt(reportId){	
	document.forms["nicReportForm"].reportId.value = reportId;	
	document.forms["nicReportForm"].action = '${frdEditPgUrl}';
    document.forms["nicReportForm"].submit();
}

$(document).ready(function() {
	 /*var reportCategory=$("#reportCategory").val();
	 var categoryLoad =$("#categoryLoad").val();
	 if(categoryLoad == 'Y'){	
		$("#fortable").show(); 
		$('#statResult').flexOptions({		
			url: "${hiddenInptUrl}/"+reportCategory	 
		}).flexReload(); 	 
		$("#fortable").show(); 
	 }*/
	$("#addmain").click(function(){	
	       document.forms["nicReportForm"].action = '${frdDefUrl}';
		   document.forms["nicReportForm"].submit();	
	});
	
	$("#btn_cancel").click(function(){  
		   		    
			document.forms["nicReportForm"].action = '${definitionUrl}';
		    document.forms["nicReportForm"].submit();
	});
 });


    
</script>
 </div>
 </div>
 </div>
 <div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
			<div style="margin: 10px"> 			
				 	<button type="button" class="btn btn-danger" id="btn_cancel">
						<span class="glyphicon glyphicon-remove"></span> Hủy bỏ
					</button>
 					<button type="button" class="btn btn-success" id="addmain">
						<span class="glyphicon glyphicon-plus-sign"></span> Thêm mới 
					</button>  
			</div>
</div>
 </div>
<!--  </div> -->
 </form:form>

   
 

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:url var="submitUrl" value="/servlet/reportmgmt/reportGeneration" />
<c:url var="hiddenInptUrl" value="/servlet/reportmgmt/query_ajax" />
<c:url var="url" value="/servlet/reportmgmt" />
<style>
.displayTag tr.odd {
	height: 35px;
}
.displayTag tr.even {
	height: 35px;
}
</style>
<script type="text/javascript">
</script>
<form:form commandName="nicReportForm" id="form" action="${submitUrl}" method="GET" cssClass="inline">
	<div class="form-desing">
		<div>
	        	<div class="row">
	        		<div class="ov_hidden">
		<!-- <div class="content"> -->
		<div class="new-heading-2">QUẢN LÝ BÁO CÁO</div>

		<div id="global_icon_div"></div>
		<div id="global_error_div"></div>
		<div id="dropdown3">
			<div class="col-md-4" style="padding-left: 0px;">
				<div class="col-md-5">
					<span class="cla-font">Lựa chọn danh mục:</span>
				</div>
				<div class="col-md-7">
					<form:select id="reportCategory" path="reportCategory" style="width: 100%;">
						<form:option value="NONE" label="--- Chọn ---" />
						<form:options items="${reportCategoryList}" itemLabel="codeValueDesc" itemValue="id.codeValue" />
					</form:select>
				</div>
			</div>
			
		</div>
		<p>&nbsp;</p>
		<!--newly added-->
		<div style="margin-top: 60px;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="max-width: 150px;">Mã báo cáo
								
								      </th>	
								      <th class="th-sm" style="min-width: 150px;">Tên báo cáo
								
								      </th>
								      <th class="th-sm" style="min-width: 150px;">Mô tả
								
								      </th>
								      <th class="th-sm" style="min-width: 150px;">Ưu tiên báo cáo
								
								      </th>							   
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>							    									      									      									      
									      <td>${item.reportId}</td>
									      <td><a style="color: blue;" href="#" onclick="doViewIt('${item.reportId},${item.reportName}')">${item.reportName}</a></td>
									      <td>${item.description}</td>
									      <td>${item.reportPriority}</td>								      						     
									    </tr>
								    </c:forEach>
								  </tbody>
								</table>
						      </div>
		<!--<div class="col-md-12">
			<div id="fortable" style="display: none">
				<table id="statResult"></table>
			</div>
		</div>-->
	</div>
</div>
</div>
</div>
	<!--  </div> -->
	<input type="hidden" id="currentURL" name="currentURL" value="${submitUrl}" />

<script type="text/javascript">
$('#dtBasicExample').DataTable();
$('.dataTables_length').addClass('bs-select');
/*  $("#dropdown").change(function(){	
	 var id=$("#dropdown option").filter(":selected").text();
 show("fortable");
}); */
 /*$("#statResult").flexigrid({

		dataType : 'json',
		colModel : [
					{display: 'Mã báo cáo', name : 'reportId', width : 381, sortable : true, align: 'left'},
					{display: 'Tên báo cáo', name : 'reportName', width : 312, sortable : true, align: 'left',render: renderLink},
					{display: 'Mô tả', name : 'description', width : 307, sortable : true, align: 'left'},
					{display: 'Ưu tiên báo cáo', name : 'reportPriority', width : 224, align: 'left'}
				
					
				],
			sortname: "reportId",
			sortorder: "asc",
			title : 'Danh sách loại báo cáo',
			usepager : false,
			useRp : true,
			rp : 10,
			showTableToggleBtn : true,   
			height: 250,
			singleSelect : true,
			nowrap : false
		
	});*/
 function renderLink(content, currentRow){
		return  "<a href=\"#\" onclick=\"doViewIt('" + currentRow.reportId +","+currentRow.reportName + "')\">" + currentRow.reportName +"</a>";
	} 

function doViewIt(id){
	
	var category =$("#reportCategory").val();	//$("#reportCategory option").filter(":selected").text();
	id = id+","+category;
	window.location.href="${url}/reportGeneration/" + id;
	/* var url = $("#currentURL").val()+ '?id=' + id;
	
	$("#form").action = url;
	$("#form").submit(); */
	
}

function reportCategoryChange(){
	  var id= $("#reportCategory").val();
	  var url = "${hiddenInptUrl}/"+id
	  $.ajax({
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
		        		             "<a style=\"color:blue;\" href=\"#\" onclick=\"doViewIt('" + data[i].reportId +","+data[i].reportName + "')\">" + data[i].reportName +"</a>",
		        		             data[i].description,
		        		             data[i].reportPriority
		        		]).draw(false);
		        	}
					
				}else{							
					$.alert({
						title: 'Thông báo',
						content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + ' Không tìm thấy dữ liệu',
						 buttons: {   
		        		        "Đóng": function () {}
		        		    }
					});
				}
			}
		});
		/*show("fortable");
		$("#statResult").flexOptions(
			{
				url: "${hiddenInptUrl}/"+id

			}).flexReload();*/
			
}
	
$("#reportCategory").change(function(){
	 ////var id=$("#reportCategory option").filter(":selected").text();
	//  var id= $("#reportCategory").val();
	//show("fortable");
	//$("#statResult").flexOptions(
	//	{
	//		url: "${hiddenInptUrl}/"+id

	//	}).flexReload();
		reportCategoryChange();
	});
$(document).ready(function() {
	//reportCategoryChange();
});

</script>
</form:form>
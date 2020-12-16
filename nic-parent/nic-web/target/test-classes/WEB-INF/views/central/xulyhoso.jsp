<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<c:url var="loadProcessUrl" value="/servlet/central/loadProcess" />
<%-- <form:form commandName="jobList" id="form" name="form" > --%>
<c:url var="dulieuxulyhsUrl" value="/servlet/central/dulieuxulyhs" />
<c:url var="thongtinchitiet" value="/servlet/central/danhsachxlcapphat" />
<style>
<!--

-->
.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
    white-space: pre-wrap;
}
.cls-mg-bot {
	margin-top: 10px;
}

#bieuDoCaTheHoa {
	width: 100%;
	height: 100%;
	margin: 0 auto
}
</style>
<form:form modelAttribute="formData" name="formData" > 

		<!--Content start-->
<div class="form-desing">
   <div> 
	   <div>
		   <div class="row">
		   	   <div class="ov_hidden">
			   <div class="new-heading-2">XỬ LÝ HỒ SƠ</div>
       				<div id="bieuDoXLHoSo"></div>
			   </div>
		   </div>
		   	<div class="row">
		        <div class="col-xl-12 col-sm-12" style="background-color: #000;text-align: center;height: 150px;padding: 60px;font-size: 20px;">
		            <a href="${thongtinchitiet}">
		                <div class="card text-white epp-bg-dark o-hidden h-100">
		                    <div class="card-body">
		                        <div class="mr-5" style="font-size: 20px; font-weight: 600; color: #fff;">Thông tin chi tiết</div>
		                    </div>
		                </div>
		            </a>
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
	var chart = new Highcharts.chart('bieuDoXLHoSo', {
	    title: {
	        text: ''
	    },
	    xAxis: {
	        categories: ['0', '0']
	    },
	    labels: {
	        items: [{
	            html: 'Tổng số hồ sơ',
	            style: {
	                left: '80px',
	                top: '18px',
	                color: ( 
	                    Highcharts.defaultOptions.title.style &&
	                    Highcharts.defaultOptions.title.style.color
	                ) || 'black'
	            }
	        }]
	    },
	    credits: {
	        enabled: false
	    },
	});

	//nvp đóng 3/12/2019
	/*$(document).ready(function() {
		var totalNowN = ${totalNowN};
		var totalBeforeN = ${totalBeforeN};
		var arrayN = [totalNowN, totalBeforeN];
		var totalN = totalNowN + totalBeforeN;
		
		var totalNowY = ${totalNowY};
		var totalBeforeY = ${totalBeforeY};
		var arrayY = [totalNowY, totalBeforeY];
		var totalY = totalNowY + totalBeforeY;
		
		var totalNowC = ${totalNowC};
		var totalBeforeC = ${totalBeforeC};
		var arrayC = [totalNowC, totalBeforeC];
		var totalC = totalNowC + totalBeforeC;
		
		chart.series[0].setData(arrayC);
		chart.series[1].setData(arrayY);
		chart.series[2].setData(arrayN);
		
		var tbNow = (totalNowN + totalNowY + totalNowC) / 3;
		var tbBefore = (totalBeforeN + totalBeforeY + totalBeforeC) / 3;
		var arrayTB = [tbNow, tbBefore];
		chart.series[3].setData(arrayTB);
		
		var arrayTT = [totalC, totalY, totalN];
		chart.series[4].setData(arrayTT);
		
		
		var namHienTai = '${namHienTai}';
		var namTruocDo = '${namTruocDo}';
		
		var arrayPoint = [namHienTai, namTruocDo];
		chart.xAxis[0].setCategories(arrayPoint);
	});*/
	$(document).ready(function() {
		$.ajax({
			url : '${dulieuxulyhsUrl}',
			type : "GET",
			success : function(response) {
				chart.xAxis[0].setCategories(response.xAxis.categories);
				for(var i = 0; i < response.series.length; i++){
					if(response.series[i].type == 'column'){
						chart.addSeries({   
							type: 'column',
						    name: response.series[i].name,
						    data: response.series[i].data
						}, false);
					}
					if(response.series[i].type == 'spline'){
						chart.addSeries({   
							type: 'spline',
						    name: response.series[i].name,
						    data: response.series[i].data
						}, false);
					}
					if(response.series[i].type == 'pie'){
						chart.addSeries({   
							type: 'pie',
						    name: response.series[i].name,
						    data: response.series[i].dataPie,
						    center: [30, 80],
					        size: 100,
					        showInLegend: false,
					        dataLabels: {
					            enabled: false
					        }
						}, false);
					}
				}
				chart.redraw();
			},
			error : function(xhr) {
				console.log('error');
			}
		});
	});
</script>
	
</div>
</div>
</div>
</form:form>



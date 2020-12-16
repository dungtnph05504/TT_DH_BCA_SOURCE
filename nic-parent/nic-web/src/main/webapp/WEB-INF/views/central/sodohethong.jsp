<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<c:url var="thongtinchitiet" value="/servlet/central/danhsachdphoiin" />
<%-- <form:form commandName="jobList" id="form" name="form" > --%>
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
.toggle {
    position: relative;
    overflow: hidden;
}
.toggle.btn {
    min-width: 59px;
    min-height: 34px;
}
.toggle.btn-sm {
    min-width: 50px;
    min-height: 30px;
}
.toggle.off .toggle-group {
    left: -100%;
}
.toggle-group {
    position: absolute;
    width: 200%;
    top: 0;
    bottom: 0;
    left: 0;
    transition: left .35s;
    -webkit-transition: left .35s;
    -moz-user-select: none;
    -webkit-user-select: none;
}
.toggle.off .toggle-group {
    left: -100%;
}
.toggle-on {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 50%;
    margin: 0;
    border: 0;
    border-radius: 0;
}
.toggle-on.btn {
    padding-right: 24px;
}
.toggle-on.btn-sm {
    padding-right: 20px;
}
.toggle-off.btn {
    padding-left: 24px;
}
.toggle-off.btn-sm {
    padding-left: 20px;
}
.toggle-handle {
    position: relative;
    margin: 0 auto;
    padding-top: 0;
    padding-bottom: 0;
    height: 100%;
    width: 0;
    border-width: 0 1px;
}
.toggle.btn-sm {
    min-width: 50px;
    min-height: 30px;
}
.toggle-on.btn-sm {
    padding-right: 20px;
}
.toggle-on {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 50%;
    margin: 0;
    border: 0;
    border-radius: 0;
}
#worker_table .btn span, #worker_table .btn span:hover {
    color: #313131;
}
.highcharts-container span {
    background-color: inherit !important;
}
</style>
<form:form modelAttribute="formData" name="formData" > 

		<!--Content start-->
<div class="form-desing">
   <div> 
	   <div>
		   <div class="row">
		   	   <div class="ov_hidden">
			   <div class="new-heading-2">SƠ ĐỒ HỆ THỐNG</div>
       				<div id="bieuDo1"></div>
       				<div id="chart1" data-highcharts-chart="0" style="overflow: hidden;"><div id="highcharts-zmyjfnd-0" dir="ltr" class="highcharts-container " style="position: relative; overflow: hidden; width: 1540px; height: 600px; text-align: left; line-height: normal; z-index: 0; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);"><svg version="1.1" class="highcharts-root" style="font-family:&quot;Lucida Grande&quot;, &quot;Lucida Sans Unicode&quot;, Arial, Helvetica, sans-serif;font-size:12px;" xmlns="http://www.w3.org/2000/svg" width="1540" height="600" viewBox="0 0 1540 600"><desc>Created with Highcharts 7.1.3</desc><defs><clipPath id="highcharts-zmyjfnd-1-"><rect x="0" y="0" width="532" height="1520" fill="none"></rect></clipPath></defs><rect fill="#ffffff" class="highcharts-background" x="0" y="0" width="1540" height="600" rx="0" ry="0"></rect><rect fill="none" class="highcharts-plot-background" x="10" y="53" width="1520" height="532"></rect><rect fill="none" class="highcharts-plot-border" data-z-index="1" x="10" y="53" width="1520" height="532"></rect><g class="highcharts-series-group" data-z-index="3"><g data-z-index="0.1" class="highcharts-series highcharts-series-0 highcharts-organization-series  highcharts-tracker     highcharts-series-hover" transform="translate(1530,585) rotate(90) scale(-1,1) scale(1 1)" clip-path="url(#highcharts-zmyjfnd-1-)" width="1520" height="532"><path fill="none" d="M 466.5 760.5 L 438.5 760.5" r="3" stroke="#666666" stroke-width="1" class="highcharts-link highcharts-point highcharts-null-point        highcharts-point-hover"></path><path fill="none" d="M 373.5 760.5 L 345.5 760.5" r="3" stroke="#666666" stroke-width="1" class="highcharts-link highcharts-point highcharts-null-point         highcharts-point-inactive"></path><path fill="none" d="M 280.5 760.5 L 182.5 760.5 C 172.5 760.5 172.5 760.5 172.5 770.5 L 172.5 1323.5 C 172.5 1333.5 172.5 1333.5 162.5 1333.5 L 158.5 1333.5" r="3" stroke="#666666" stroke-width="1" class="highcharts-link highcharts-point highcharts-null-point       highcharts-point-inactive"></path><path fill="none" d="M 280.5 760.5 L 182.5 760.5 C 172.5 760.5 172.5 760.5 172.5 770.5 L 172.5 941.5 C 172.5 951.5 172.5 951.5 162.5 951.5 L 158.5 951.5" r="3" stroke="#666666" stroke-width="1" class="highcharts-link highcharts-point highcharts-null-point       highcharts-point-inactive"></path><path fill="none" d="M 280.5 760.5 L 182.5 760.5 C 172.5 760.5 172.5 760.5 172.5 750.5 L 172.5 578.5 C 172.5 568.5 172.5 568.5 162.5 568.5 L 158.5 568.5" r="3" stroke="#666666" stroke-width="1" class="highcharts-link highcharts-point highcharts-null-point       highcharts-point-inactive"></path><path fill="none" d="M 280.5 760.5 L 182.5 760.5 C 172.5 760.5 172.5 760.5 172.5 750.5 L 172.5 196.5 C 172.5 186.5 172.5 186.5 162.5 186.5 L 158.5 186.5" r="3" stroke="#666666" stroke-width="1" class="highcharts-link highcharts-point highcharts-null-point       highcharts-point-inactive"></path><path fill="none" d="M 93.5 1509.5 L 43.5 1509.5 C 33.5 1509.5 33.5 1509.5 33.5 1499.5 L 33.5 1499.5" r="3" stroke="#666666" stroke-width="1" class="highcharts-link highcharts-point highcharts-null-point     highcharts-point-inactive"></path><rect x="466.5" y="574.5" width="65" height="372" display="" fill="silver" rx="3" ry="3" stroke="white" stroke-width="1" class="highcharts-node highcharts-point highcharts-node        highcharts-point-hover"></rect><rect x="373.5" y="574.5" width="65" height="372" display="" fill="#007ad0" rx="3" ry="3" stroke="white" stroke-width="1" class="highcharts-node highcharts-point highcharts-node          highcharts-point-hover"></rect><rect x="280.5" y="574.5" width="65" height="372" display="" fill="#980104" rx="3" ry="3" stroke="white" stroke-width="1" class="highcharts-node highcharts-point highcharts-node         highcharts-point-inactive"></rect><rect x="93.5" y="1147.5" width="65" height="372" display="" fill="#007ad0" rx="3" ry="3" stroke="white" stroke-width="1" class="highcharts-node highcharts-point highcharts-node       highcharts-point-inactive"></rect><rect x="93.5" y="765.5" width="65" height="372" display="" fill="#007ad0" rx="3" ry="3" stroke="white" stroke-width="1" class="highcharts-node highcharts-point highcharts-node       highcharts-point-inactive"></rect><rect x="93.5" y="382.5" width="65" height="372" display="" fill="#007ad0" rx="3" ry="3" stroke="white" stroke-width="1" class="highcharts-node highcharts-point highcharts-node       highcharts-point-inactive"></rect><rect x="93.5" y="0.5" width="65" height="372" display="" fill="#007ad0" rx="3" ry="3" stroke="white" stroke-width="1" class="highcharts-node highcharts-point highcharts-node       highcharts-point-inactive"></rect><rect x="0.5" y="1147.5" width="65" height="352" display="" fill="#359154" rx="3" ry="3" stroke="white" stroke-width="1" class="highcharts-node highcharts-point highcharts-node     highcharts-point-inactive"></rect></g><g data-z-index="0.1" class="highcharts-markers highcharts-series-0 highcharts-organization-series      highcharts-series-hover" transform="translate(1530,585) rotate(90) scale(-1,1) scale(1 1)"></g></g><text x="770" text-anchor="middle" class="highcharts-title" data-z-index="4" style="color:#333333;font-size:18px;fill:#333333;" y="24"><tspan></tspan></text><text x="770" text-anchor="middle" class="highcharts-subtitle" data-z-index="4" style="color:#666666;fill:#666666;" y="52"></text><g data-z-index="6" class="highcharts-data-labels highcharts-series-0 highcharts-organization-series  highcharts-tracker     highcharts-series-hover" transform="translate(10,53) scale(1 1)" opacity="1"><g class="highcharts-label highcharts-data-label highcharts-data-label-color-undefined highcharts-tracker" data-z-index="1" transform="translate(579,6)" opacity="NaN"><rect fill="none" class="highcharts-label-box highcharts-data-label-box" x="0" y="0" width="105" height="68" rx="0" ry="0"></rect></g><g class="highcharts-label highcharts-data-label highcharts-data-label-color-undefined highcharts-tracker" data-z-index="1" transform="translate(579,99)" opacity="NaN"><rect fill="none" class="highcharts-label-box highcharts-data-label-box" x="0" y="0" width="96" height="87" rx="0" ry="0"></rect></g><g class="highcharts-label highcharts-data-label highcharts-data-label-color-undefined highcharts-tracker" data-z-index="1" transform="translate(579,192)" opacity="NaN"><rect fill="none" class="highcharts-label-box highcharts-data-label-box" x="0" y="0" width="133" height="68" rx="0" ry="0"></rect></g><g class="highcharts-label highcharts-data-label highcharts-data-label-color-undefined highcharts-tracker" data-z-index="1" transform="translate(6,379)" opacity="NaN"><rect fill="none" class="highcharts-label-box highcharts-data-label-box" x="0" y="0" width="133" height="68" rx="0" ry="0"></rect></g><g class="highcharts-label highcharts-data-label highcharts-data-label-color-undefined highcharts-tracker" data-z-index="1" transform="translate(388,379)" opacity="NaN"><rect fill="none" class="highcharts-label-box highcharts-data-label-box" x="0" y="0" width="133" height="49" rx="0" ry="0"></rect></g><g class="highcharts-label highcharts-data-label highcharts-data-label-color-undefined highcharts-tracker" data-z-index="1" transform="translate(771,379)" opacity="NaN"><rect fill="none" class="highcharts-label-box highcharts-data-label-box" x="0" y="0" width="105" height="144" rx="0" ry="0"></rect></g><g class="highcharts-label highcharts-data-label highcharts-data-label-color-undefined highcharts-tracker" data-z-index="1" transform="translate(1153,379)" opacity="NaN"><rect fill="none" class="highcharts-label-box highcharts-data-label-box" x="0" y="0" width="133" height="106" rx="0" ry="0"></rect></g><g class="highcharts-label highcharts-data-label highcharts-data-label-color-undefined highcharts-tracker" data-z-index="1" transform="translate(26,472)" opacity="NaN"><rect fill="none" class="highcharts-label-box highcharts-data-label-box" x="0" y="0" width="133" height="49" rx="0" ry="0"></rect></g></g><g class="highcharts-legend" data-z-index="7"><rect fill="none" class="highcharts-legend-box" rx="0" ry="0" x="0" y="0" width="8" height="8" visibility="hidden"></rect><g data-z-index="1"><g></g></g></g></svg><div class="highcharts-data-labels highcharts-series-0 highcharts-organization-series  highcharts-tracker     highcharts-series-hover" style="position: absolute; left: 10px; top: 53px; opacity: 1; visibility: inherit;"><div class="highcharts-label highcharts-data-label highcharts-data-label-color-undefined highcharts-tracker" style="position: absolute; left: 579px; top: 6px; opacity: 1; width: 361px; height: 54px;"><span data-z-index="1" style="position: absolute; font-family: &quot;Lucida Grande&quot;, &quot;Lucida Sans Unicode&quot;, Arial, Helvetica, sans-serif; font-size: 13px; white-space: nowrap; font-weight: normal; color: black; margin-left: 0px; margin-top: 0px; left: 0px; top: 0px; width: 100%; height: 100%; overflow: hidden;"><div style="width:100%;height:100%;display:flex;flex-direction:row;align-items:center;justify-content:center;"><img src="https://cdn2.iconfinder.com/data/icons/whcompare-isometric-web-hosting-servers/50/desktop-pc-512.png" style="max-height:100%;border-radius:50%;max-width:30%;"><div style="width:70%;padding:0;text-align:center;white-space:normal;"><h4 style="margin:0;">CSDL XNC</h4><p style="margin:0;">192.168.1.224</p></div></div></span></div><div class="highcharts-label highcharts-data-label highcharts-data-label-color-undefined highcharts-tracker" style="position: absolute; left: 579px; top: 99px; opacity: 1; width: 361px; height: 54px;"><span data-z-index="1" style="position: absolute; font-family: &quot;Lucida Grande&quot;, &quot;Lucida Sans Unicode&quot;, Arial, Helvetica, sans-serif; font-size: 13px; white-space: nowrap; font-weight: normal; color: black; margin-left: 0px; margin-top: 0px; left: 0px; top: 0px; width: 100%; height: 100%; overflow: hidden;"><div style="width:100%;height:100%;display:flex;flex-direction:row;align-items:center;justify-content:center;"><img src="https://cdn2.iconfinder.com/data/icons/whcompare-isometric-web-hosting-servers/50/desktop-pc-512.png" style="max-height:100%;border-radius:50%;max-width:30%;"><div style="width:70%;padding:0;text-align:center;white-space:normal;"><h4 style="margin:0;">Xuất Nhập Cảnh</h4><p style="margin:0;">192.168.1.16</p></div></div></span></div><div class="highcharts-label highcharts-data-label highcharts-data-label-color-undefined highcharts-tracker" style="position: absolute; left: 579px; top: 192px; opacity: 1; width: 361px; height: 54px;"><span data-z-index="1" style="position: absolute; font-family: &quot;Lucida Grande&quot;, &quot;Lucida Sans Unicode&quot;, Arial, Helvetica, sans-serif; font-size: 13px; white-space: nowrap; font-weight: normal; color: white; margin-left: 0px; margin-top: 0px; left: 0px; top: 0px; width: 100%; height: 100%; overflow: hidden;"><div style="width:100%;height:100%;display:flex;flex-direction:row;align-items:center;justify-content:center;"><img src="https://cdn2.iconfinder.com/data/icons/whcompare-isometric-web-hosting-servers/50/desktop-pc-512.png" style="max-height:100%;border-radius:50%;max-width:30%;"><div style="width:70%;padding:0;text-align:center;white-space:normal;"><h4 style="margin:0;">TT Điều Hành QG</h4><p style="margin:0;">192.168.1.11:8084</p></div></div></span></div><div class="highcharts-label highcharts-data-label highcharts-data-label-color-undefined highcharts-tracker" style="position: absolute; left: 6px; top: 379px; opacity: 1; width: 361px; height: 54px;"><span data-z-index="1" style="position: absolute; font-family: &quot;Lucida Grande&quot;, &quot;Lucida Sans Unicode&quot;, Arial, Helvetica, sans-serif; font-size: 13px; white-space: nowrap; font-weight: normal; color: white; margin-left: 0px; margin-top: 0px; left: 0px; top: 0px; width: 100%; height: 100%; overflow: hidden;"><div style="width:100%;height:100%;display:flex;flex-direction:row;align-items:center;justify-content:center;"><img src="https://cdn2.iconfinder.com/data/icons/whcompare-isometric-web-hosting-servers/50/desktop-pc-512.png" style="max-height:100%;border-radius:50%;max-width:30%;"><div style="width:70%;padding:0;text-align:center;white-space:normal;"><h4 style="margin:0;">TT Lưu Trữ QG</h4><p style="margin:0;">192.168.1.15:8082</p></div></div></span></div><div class="highcharts-label highcharts-data-label highcharts-data-label-color-undefined highcharts-tracker" style="position: absolute; left: 388px; top: 379px; opacity: 1; width: 361px; height: 54px;"><span data-z-index="1" style="position: absolute; font-family: &quot;Lucida Grande&quot;, &quot;Lucida Sans Unicode&quot;, Arial, Helvetica, sans-serif; font-size: 13px; white-space: nowrap; font-weight: normal; color: white; margin-left: 0px; margin-top: 0px; left: 0px; top: 0px; width: 100%; height: 100%; overflow: hidden;"><div style="width:100%;height:100%;display:flex;flex-direction:row;align-items:center;justify-content:center;"><img src="https://cdn2.iconfinder.com/data/icons/whcompare-isometric-web-hosting-servers/50/desktop-pc-512.png" style="max-height:100%;border-radius:50%;max-width:30%;"><div style="width:70%;padding:0;text-align:center;white-space:normal;"><h4 style="margin:0;">CA</h4><p style="margin:0;">192.168.1.223:271</p></div></div></span></div><div class="highcharts-label highcharts-data-label highcharts-data-label-color-undefined highcharts-tracker" style="position: absolute; left: 771px; top: 379px; opacity: 1; width: 361px; height: 54px;"><span data-z-index="1" style="position: absolute; font-family: &quot;Lucida Grande&quot;, &quot;Lucida Sans Unicode&quot;, Arial, Helvetica, sans-serif; font-size: 13px; white-space: nowrap; font-weight: normal; color: white; margin-left: 0px; margin-top: 0px; left: 0px; top: 0px; width: 100%; height: 100%; overflow: hidden;"><div style="width:100%;height:100%;display:flex;flex-direction:row;align-items:center;justify-content:center;"><img src="https://cdn2.iconfinder.com/data/icons/whcompare-isometric-web-hosting-servers/50/desktop-pc-512.png" style="max-height:100%;border-radius:50%;max-width:30%;"><div style="width:70%;padding:0;text-align:center;white-space:normal;"><h4 style="margin:0;">TT Xử Lý Cấp Phát HCĐT Tại A08</h4><p style="margin:0;">192.168.1.222</p></div></div></span></div><div class="highcharts-label highcharts-data-label highcharts-data-label-color-undefined highcharts-tracker" style="position: absolute; left: 1153px; top: 379px; opacity: 1; width: 361px; height: 54px;"><span data-z-index="1" style="position: absolute; font-family: &quot;Lucida Grande&quot;, &quot;Lucida Sans Unicode&quot;, Arial, Helvetica, sans-serif; font-size: 13px; white-space: nowrap; font-weight: normal; color: white; margin-left: 0px; margin-top: 0px; left: 0px; top: 0px; width: 100%; height: 100%; overflow: hidden;"><div style="width:100%;height:100%;display:flex;flex-direction:row;align-items:center;justify-content:center;"><img src="https://cdn2.iconfinder.com/data/icons/whcompare-isometric-web-hosting-servers/50/desktop-pc-512.png" style="max-height:100%;border-radius:50%;max-width:30%;"><div style="width:70%;padding:0;text-align:center;white-space:normal;"><h4 style="margin:0;">TT Xử Lý Cấp Phát HCĐT Tại PA08</h4><p style="margin:0;">192.168.1.11:1521</p></div></div></span></div><div class="highcharts-label highcharts-data-label highcharts-data-label-color-undefined highcharts-tracker" style="position: absolute; left: 26px; top: 472px; opacity: 1; width: 341px; height: 54px;"><span data-z-index="1" style="position: absolute; font-family: &quot;Lucida Grande&quot;, &quot;Lucida Sans Unicode&quot;, Arial, Helvetica, sans-serif; font-size: 13px; white-space: nowrap; font-weight: normal; color: white; margin-left: 0px; margin-top: 0px; left: 0px; top: 0px; width: 100%; height: 100%; overflow: hidden;"><div style="width:100%;height:100%;display:flex;flex-direction:row;align-items:center;justify-content:center;"><img src="https://cdn2.iconfinder.com/data/icons/whcompare-isometric-web-hosting-servers/50/desktop-pc-512.png" style="max-height:100%;border-radius:50%;max-width:30%;"><div style="width:70%;padding:0;text-align:center;white-space:normal;"><h4 style="margin:0;">BMS</h4><p style="margin:0;">192.168.1.15:1921</p></div></div></span></div></div></div></div>
			   </div>
		   </div>
		  <div class="col-sm-12 content-person-item container-fluid">
        <div class="content-item-title">
            <div class="oplog-title__txt">Danh sách máy chủ</div>
        </div>
        <table id="worker_table" class="table table-bordered tbl_head01" style="margin-bottom: 50px;">
            <thead>
                <tr>
                    <th style="width: 50px">#</th>
                    <th style="width: 300px">Địa chỉ IP</th>
                    <th style="width: 100px">Trạng thái</th>
                    <th style="width: 100px">CPU</th>
                    <th style="width: 100px">RAM</th>
                    <th style="width: 100px">HDD (Free)</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <tr ip="192.168.1.225">
                    <td>1</td>
                    <td prod="ip" valign="middle"><span class="glyphicon glyphicon-ok-circle"></span><a href="http://192.168.1.15:8084/de-tool/admin/workerDetail&amp;workerIp=192.168.1.225">192.168.1.225</a></td>
                    <td prod="status" align="center" valign="middle">
                    <input type="checkbox" checked data-toggle="toggle">
                    </td>
                    <td prod="cpu" valign="middle" style="text-align:center"><span style="">0.61% </span></td>
                    <td prod="ram" valign="middle" style="text-align:center"><span style="">8.20% </span></td>
                    <td prod="hdd(Avail)" valign="middle"><span style="">1841.79 GB </span> </td>
                    <td prod="action">
                        <a href="javascript:void(0)" class="btn btn-default m-r-5" title="Edit Configuration"> <span>Stop</span></a>
                    </td>
                </tr>
                <tr ip="192.168.1.220">
                    <td>2</td>
                    <td prod="ip" valign="middle"><span class="glyphicon glyphicon-ok-circle"></span><a href="http://192.168.1.15:8084/de-tool/admin/workerDetail&amp;workerIp=192.168.1.220">192.168.1.220</a></td>
                    <td prod="status" align="center" valign="middle">
                      <input type="checkbox" checked data-toggle="toggle">
                    </td>
                    <td prod="cpu" valign="middle" style="text-align:center"><span style="">81.5% </span></td>
                    <td prod="ram" valign="middle" style="text-align:center"><span style="">25.82% </span></td>
                    <td prod="hdd(Avail)" valign="middle"><span style="">1849.94 GB </span> </td>
                    <td prod="action">
                        <a href="javascript:void(0)" class="btn btn-default m-r-5"  title="Edit Configuration"> <span>Stop</span></a>
                    </td>
                </tr>
                <tr ip="192.168.1.113">
                    <td>3</td>
                    <td prod="ip" valign="middle"><span class="glyphicon glyphicon-ok-circle"></span><a href="http://192.168.1.15:8084/de-tool/admin/workerDetail&amp;workerIp=192.168.1.113">192.168.1.113</a></td>
                    <td prod="status" align="center" valign="middle">
                         <input type="checkbox" checked data-toggle="toggle">
                    </td>
                    <td prod="cpu" valign="middle" style="text-align:center"><span style="">27.99% </span></td>
                    <td prod="ram" valign="middle" style="text-align:center"><span style="">26.70% </span></td>
                    <td prod="hdd(Avail)" valign="middle"><span style="">1245.62 GB </span> </td>
                    <td prod="action">
                        <a href="javascript:void(0)" class="btn btn-default m-r-5" title="Edit Configuration"> <span>Stop</span></a>
                    </td>
                </tr>
                <tr ip="192.168.1.222">
                    <td>4</td>
                    <td prod="ip" valign="middle"><span class="glyphicon glyphicon-ok-circle" style="color: #42B72A"></span><a href="http://192.168.1.15:8084/de-tool/admin/workerDetail&amp;workerIp=192.168.1.222">192.168.1.222</a></td>
                    <td prod="status" align="center" valign="middle">
                          <input type="checkbox" data-toggle="toggle">
                    </td>
                    <td prod="cpu" valign="middle" style="text-align:center"><span style="">67.36% </span></td>
                    <td prod="ram" valign="middle" style="text-align:center"><span style="">19.59% </span></td>
                    <td prod="hdd(Avail)" valign="middle"><span style="">17.71 GB </span> </td>
                    <td prod="action">
                        <a href="javascript:void(0)" class="btn btn-default m-r-5"  title="Edit Configuration"> <span>Start</span></a>
                    </td>
                </tr>
                <tr ip="192.168.1.223">
                    <td>5</td>
                    <td prod="ip" valign="middle"><span class="glyphicon glyphicon-ok-circle" style="color: #42B72A"></span><a href="http://192.168.1.15:8084/de-tool/admin/workerDetail&amp;workerIp=192.168.1.223">192.168.1.223</a></td>
                    <td prod="status" align="center" valign="middle">
                       
                           <input type="checkbox"  data-toggle="toggle">
                    </td>
                    <td prod="cpu" valign="middle" style="text-align:center"><span style="">58.69% </span></td>
                    <td prod="ram" valign="middle" style="text-align:center"><span style="">22.27% </span></td>
                    <td prod="hdd(Avail)" valign="middle"><span style="">17.25 GB </span> </td>
                    <td prod="action">
                        <a href="javascript:void(0)" class="btn btn-default m-r-5"  title="Edit Configuration"> <span>Start</span></a>
                    </td>
                </tr>
                <tr ip="192.168.1.224">
                    <td>6</td>
                    <td prod="ip" valign="middle"><span class="glyphicon glyphicon-ok-circle" style="color: #42B72A"></span><a href="http://192.168.1.15:8084/de-tool/admin/workerDetail&amp;workerIp=192.168.1.224">192.168.1.224</a></td>
                    <td prod="status" align="center" valign="middle">
                         <input type="checkbox"  data-toggle="toggle">
                    </td>
                    <td prod="cpu" valign="middle" style="text-align:center"><span style="">85.81% </span></td>
                    <td prod="ram" valign="middle" style="text-align:center"><span style="">21.41% </span></td>
                    <td prod="hdd(Avail)" valign="middle"><span style="">18.43 GB </span> </td>
                    <td prod="action">
                        <a href="javascript:void(0)" class="btn btn-default m-r-5"  title="Edit Configuration"> <span>Start</span></a>
                    </td>
                </tr>
                <tr ip="192.168.1.11">
                    <td>7</td>
                    <td prod="ip" valign="middle"><span class="glyphicon glyphicon-ok-circle" style="color: #42B72A"></span><a href="http://192.168.1.15:8084/de-tool/admin/workerDetail&amp;workerIp=192.168.1.11">192.168.1.11</a></td>
                    <td prod="status" align="center" valign="middle">
                        <input type="checkbox"  data-toggle="toggle">
                    </td>
                    <td prod="cpu" valign="middle" style="text-align:center"><span style="">66.98% </span></td>
                    <td prod="ram" valign="middle" style="text-align:center"><span style="">61.23% </span></td>
                    <td prod="hdd(Avail)" valign="middle"><span style="">1386.42 GB </span> </td>
                    <td prod="action">
                        <a href="javascript:void(0)" class="btn btn-default m-r-5"  title="Edit Configuration"> <span>Start</span></a>
                    </td>
                </tr>
                <tr ip="192.168.1.15">
                    <td>8</td>
                    <td prod="ip" valign="middle"><span class="glyphicon glyphicon-ok-circle" style="color: #42B72A"></span><a href="http://192.168.1.15:8084/de-tool/admin/workerDetail&amp;workerIp=192.168.1.15">192.168.1.15</a></td>
                    <td prod="status" align="center" valign="middle">
                          <input type="checkbox"  data-toggle="toggle">
                    </td>
                    <td prod="cpu" valign="middle" style="text-align:center"><span style="">75.74% </span></td>
                    <td prod="ram" valign="middle" style="text-align:center"><span style="">25.90% </span></td>
                    <td prod="hdd(Avail)" valign="middle"><span style="">1246.79 GB </span> </td>
                    <td prod="action">
                        <a href="javascript:void(0)" class="btn btn-default m-r-5"  title="Edit Configuration"> <span>Start</span></a>
                    </td>
                </tr>
                <tr ip="192.168.1.16">
                    <td>9</td>
                    <td prod="ip" valign="middle"><span class="glyphicon glyphicon-ok-circle" style="color: #42B72A"></span><a href="http://192.168.1.15:8084/de-tool/admin/workerDetail&amp;workerIp=192.168.1.16">192.168.1.16</a></td>
                    <td prod="status" align="center" valign="middle">
                         <input type="checkbox"  data-toggle="toggle">
                    </td>
                    <td prod="cpu" valign="middle" style="text-align:center"><span style="">64.55% </span></td>
                    <td prod="ram" valign="middle" style="text-align:center"><span style="">26.97% </span></td>
                    <td prod="hdd(Avail)" valign="middle"><span style="">1522.98 GB </span> </td>
                    <td prod="action">
                        <a href="javascript:void(0)" class="btn btn-default m-r-5" title="Edit Configuration"> <span>Start</span></a>
                    </td>
                </tr>



            </tbody>
        </table>
    </div>

<script type="text/javascript">
        Highcharts.chart('bieuDo', {
            chart: {
                height: 600,
                inverted: true
            },

            title: {
                text: 'Sơ đồ triển khai'
            },

            series: [{
                type: 'organization',
                name: 'SERVER',
                keys: ['from', 'to'],
                data: [
                    ['Shareholders', 'Board'],
                    ['Board', 'CEO'],
                    ['CEO', 'CTO'],
                    ['CEO', 'CPO'],
                    ['CEO', 'CSO'],
                    ['CEO', 'CMO'],
                    ['CTO', 'Product']
                ],
                levels: [{
                    level: 0,
                    color: 'silver',
                    dataLabels: {
                        color: 'black'
                    },
                    height: 25
                }, {
                    level: 1,
                    color: 'silver',
                    dataLabels: {
                        color: 'black'
                    },
                    height: 25
                }, {
                    level: 2,
                    color: '#980104'
                }, {
                    level: 4,
                    color: '#359154'
                }],
                nodes: [{
                    id: 'Shareholders',
                    title: '192.168.1.224',
                    name: 'CSDL XNC',
                    image: 'https://cdn2.iconfinder.com/data/icons/whcompare-isometric-web-hosting-servers/50/desktop-pc-512.png'
                }, {
                    id: 'Board',
                    title: '192.168.1.16',
                    name: 'Xuất Nhập Cảnh',
                    color: '#007ad0',
                    image: 'https://cdn2.iconfinder.com/data/icons/whcompare-isometric-web-hosting-servers/50/desktop-pc-512.png',
                }, {
                    id: 'CEO',
                    title: '192.168.1.11:8084',
                    name: 'TT Điều Hành QG',
                    image: 'https://cdn2.iconfinder.com/data/icons/whcompare-isometric-web-hosting-servers/50/desktop-pc-512.png'
                }, {
                    id: 'CTO',
                    title: '192.168.1.15:8082',
                    name: 'TT Lưu Trữ QG',
                    column: 4,
                    image: 'https://cdn2.iconfinder.com/data/icons/whcompare-isometric-web-hosting-servers/50/desktop-pc-512.png',
                    layout: 'hanging'
                }, {
                    id: 'CPO',
                    title: '192.168.1.223:271',
                    name: 'CA',
                    column: 4,
                    image: 'https://cdn2.iconfinder.com/data/icons/whcompare-isometric-web-hosting-servers/50/desktop-pc-512.png',
                }, {
                    id: 'CSO',
                    title: '192.168.1.222',
                    name: 'TT Xử Lý Cấp Phát HCĐT Tại A08',
                    column: 4,
                    image: 'https://cdn2.iconfinder.com/data/icons/whcompare-isometric-web-hosting-servers/50/desktop-pc-512.png',
                    layout: 'hanging'
                }, {
                    id: 'CMO',
                    title: '192.168.1.11:1521',
                        name: 'TT Xử Lý Cấp Phát HCĐT Tại PA08',
                    column: 4,
                    image: 'https://cdn2.iconfinder.com/data/icons/whcompare-isometric-web-hosting-servers/50/desktop-pc-512.png',
                    layout: 'hanging'
                }, {
                    id: 'Product',
                    title: '192.168.1.15:1921',
                    name: 'BMS',
                    image: 'https://cdn2.iconfinder.com/data/icons/whcompare-isometric-web-hosting-servers/50/desktop-pc-512.png'
                }],
                colorByPoint: false,
                color: '#007ad0',
                dataLabels: {
                    color: 'white'
                },
                borderColor: 'white',
                nodeWidth: 65
            }],
            tooltip: {
                outside: true
            },
            credits: {
                enabled: false
            }

        });
    </script>


	
</div>
</div>
</div>
</form:form>



<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
$(document)
.ready(function () {
$("#applicationHistoryGrid").flexigrid({
    dataType: 'json',
    //url: "application_history_rep.json",
    colModel: [{
            display: 'Transaction Date',
            name: 'Transaction_date',
            width: 120,
            align: 'center',
            hide: false
        }, {
            display: 'First Name',
            name: 'name',
            sortable: true,
            align: 'center',
            width: 150
        }, {
            display: 'Surname',
            name: 'surname',
            align: 'center',
            width: 70
        }, {
            display: 'Transaction Type',
            name: 'Transaction_Type',
            width: 120,
            align: 'center'
        }, {
            display: 'RIC Office',
            name: 'RIC_Office',
            align: 'center',
            hide: true
        }, {
            display: 'Remarks',
            name: 'remark',
            align: 'center',
            hide: false
        }, {
            display: '',
            name: 'view_details',
            align: 'center',
            hide: true
        }
    ],
    searchitems: [{
            display: 'Transaction Id',
            name: 'Transaction_id'
        }, {
            display: 'First Name',
            name: 'name'
        }, {
            display: 'NIN',
            name: 'NIN',
            isdefault: true
        }
    ],
    sortname: "name",
    sortorder: "asc",
    title: 'Application History',
    usepager: false,
    useRp: true,
    rp: 15,
    //showTableToggleBtn: true,
    height: 100,
    singleSelect: true,
    nowrap: true
});

$.ajax({
    url: "${applicationHistoryLoad}",
    success: function (data) {
        if (!data.total <= 1) {
            $("#applicationHistoryGrid")
                .html(
                "    <span style='align:center;color:blue;font-weight:bold'> &nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No Records Found for this NIN</span>");

        }
        if (data.total > 0) {
            $('#applicationHistoryGrid')
                .flexAddData(data); //.flexReload();
        }
    }
});

});
</script>
<table style="border: 1px SOLID; border-radius: 10px; margin-top: 0px; background-color: #FFFFC6">
    						<tr>
        						<td>
            						<div style="width: 645px;">
                						<table id="applicationHistoryGrid"></table>
            						</div>
        						</td>
    						</tr>
						</table>
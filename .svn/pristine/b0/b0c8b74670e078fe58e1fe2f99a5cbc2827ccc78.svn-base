<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<c:url var="url" value="/servlet/batchJobMgmt/viewJob"/>
<c:url var="delScheduleUrl" value="/servlet/batchJobMgmt/delSchedule"/>
<c:url var="saveSchUrl" value="/servlet/batchJobMgmt/saveSchedule"/>
<style>
.fix-bottom-1 {
	margin-top: 20px;
}
.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
    white-space: pre-wrap;
}
.text-span-1 {
	font-size: 14px;
	margin-right: 5px;
}
</style>
<script type="text/javascript">
	var today = new Date();
	$(function() {		
		initPage();
		// Uppercase input text
		$('#scheduleName').keyup(function(){
		    this.value = this.value.toUpperCase();
		});

		$.datepicker.setDefaults({
			duration : '',
			showTime : false,
			constrainInput : true,
			stepMinutes : 1,
			stepHours : 1,
			altTimeField : '',
			time24h : true,
			changeMonth : true,
			changeYear : true,
			dateFormat : 'mm/dd/yy',
			minDate : today,
			maxDate: '+10y',
			inline : true
		});

		$('#durationStartDate').datepicker({
			onSelect : function(selectedDate) {
				$('#durationEndDate').datepicker('option', 'minDate', selectedDate);
			}
		});
		
		$('#durationEndDate').datepicker({
			onSelect : function(selectedDate) {
				$('#durationStartDate').datepicker('option', 'maxDate', selectedDate);
			}
		});
	
		$("#endDateNo").click(function() {
			$("#durationEndDate").val("");
			$("#endDate").hide(100);
			 var maxDate = new Date();
			 maxDate.setFullYear(maxDate.getFullYear() + 10); // Add 10 years
			 $('#durationStartDate').datepicker('option', 'maxDate', maxDate);
		});

		$("#endDateYes").click(function() {
			$("#endDate").show(100);
		});

		$("#freqOccurType").change(function() {
			occurTypeOnChange();
		});

		/* $( "#actionOnSuccessType" ).change(function() {
			actionOnChange();
		});

		$( "#actionOnFailureType" ).change(function() {
			actionOnChange();
		}); */

		$("#freqYearlyMonth").change(function() {
			yearlyMonthOnChange();
		});

		$("#amendAct").click(function() {
			if(validateForm()) {
				$("#scheduleForm").attr("action", '${saveSchUrl}');
				$("#scheduleForm").submit();
			}
		});

		$("#delAct").click(function() {
			var jobId = $("#jobId").val();
			var scheduleName = $("#scheduleName").val();

			$("#dialog-confirm").dialog('option', 'title', 'Xóa lịch trình');
			$("#dialog-confirm").html("Xác nhận xóa lịch trình: " + scheduleName+ "?");
			$("#dialog-confirm").dialog({
				modal : true,
				autoOpen : false,
				width : 500,
				resizable : false,
				show : {
					effect : "fade",
					duration : 200
				},
				hide : {
					duration : 100
				},
				buttons : {
					"Đồng ý" : function() {
						$
								.ajax({
									type : "GET",
									url : '${delScheduleUrl}/'
											+ jobId
											+ "/"
											+ scheduleName,
									success : function(
											data) {
										if (data == 'success') {
											var prevMode = $(
													"#prevMode")
													.val();
											var mode = $(
													"#mode")
													.val();
											$(
													"#mode")
													.val(
															prevMode);
											$(
													"#scheduleForm")
													.attr(
															"action",
															'${url}/'
																	+ jobId);
											$(
													"#scheduleForm")
													.submit();

										} else {
											$(
													"#dialog-error")
													.dialog(
															'option',
															'title',
															'Lỗi');
											$(
													"#dialog-error")
													.html(
															"Không thể xóa lịch trình công việc "
																	+ scheduleName
																	+ ".");
											$(
													"#dialog-error")
													.dialog(
															'open');
										}
									}
								});
						$(this).dialog("close");
					},
					"Hủy" : function() {
						$(this).dialog("close");
					}
				}
			});
			$("#dialog-confirm").dialog('open');
		});

		$("#saveAct").click(function() {
			if(validateForm()) {
				$("#scheduleForm").attr("action", '${saveSchUrl}');
				$("#scheduleForm").submit();
			}
		});

		$("#calAct").click(function() {
			var prevMode = $("#prevMode").val();
			var mode = $("#mode").val();
			$("#mode").val(prevMode);
			$("#scheduleForm").attr("action", '${url}/' + $("#jobId").val());
			$("#scheduleForm").submit();

		});

		$("#resetAct").click(function() {
			$("#scheduleForm")[0].reset();
			initPage();
			
		});

		/* $("#checkAllAct").click(function() {
			var checkbox_selector = '#dayOfWeek span input[type=checkbox]';
		    if ($(this).is(':checked')) {
		    	$(checkbox_selector).attr('checked', 'checked');
		    	$(checkbox_selector).attr('disabled', 'disabled');
		    } else {
		    	$(checkbox_selector).removeAttr('checked');
		    	$(checkbox_selector).removeAttr('disabled');
		    }
		}); */

		$("#dialog-confirm").dialog({
			modal : true,
			autoOpen : false,
			width : 500,
			resizable : false,
			show : {
				effect : "fade",
				duration : 200
			},
			hide : {
				duration : 100
			},
			buttons : {
				"Đồng ý" : function() {
					callBack(true);
					$(this).dialog("close");
				},
				"Hủy" : function() {
					callBack(false);
					$(this).dialog("close");
				}
			}
		});

		$("#dialog-error").dialog({
			modal : true,
			autoOpen : false,
			width : 500,
			resizable : false,
			show : {
				effect : "fade",
				duration : 200
			},
			hide : {
				duration : 100
			},
			buttons : {
				"Đóng" : function() {
					$(this).dialog("close");
				}
			}
		});

	});

	function initPage() {
		$("#triggerTime").show();
		$("#dayOfWeek").hide();
		$("#dayOfMonth").hide();
		$("#yearlyMonth").hide();
		$("#yearlyDayOfMonth").hide();
		$("#repeatCount").hide();
		$("#repeatIntervalSecond").hide();
		$("#specifyEndDate").show();
		$("#specifyEndDateYesNo").show();
		$("#endDate").show();
		/*if ($('#endDateYes').is(':checked')) {
			$("#endDate").show();
		}
		if ($('#endDateNo').is(':checked')) {
			$("#endDate").hide();
		}*/
		if ($('#endDateYes').is(':checked')) {
			$("#endDate").show();
		}

		if ($('#endDateNo').is(':checked')) {
			$("#endDate").hide();
		}
		yearlyMonthOnChange();
		occurTypeOnChange();
		/* actionOnChange(); */
	}

	function occurTypeOnChange() {
		var freqOccur = $("#freqOccurType").val();
		if (freqOccur == "2") {
			$("#actionOnSuccess").show();
			$("#actionOnFailure").show();
			$("#triggerTime").show();
			$("#dayOfWeek").hide();
			$("#dayOfMonth").hide();
			$("#yearlyMonth").hide();
			$("#yearlyDayOfMonth").hide();
			$("#repeatCount").hide();
			$("#repeatIntervalSecond").hide();
			$("#specifyEndDate").show();
			$("#specifyEndDateYesNo").show();
			$('#endDateYes').attr('checked');
		} else if (freqOccur == "3") {
			$("#actionOnSuccess").show();
			$("#actionOnFailure").show();
			$("#triggerTime").show();
			$("#dayOfWeek").show();
			$("#dayOfMonth").hide();
			$("#yearlyMonth").hide();
			$("#yearlyDayOfMonth").hide();
			$("#repeatCount").hide();
			$("#repeatIntervalSecond").hide();
			$("#specifyEndDate").show();
			$("#specifyEndDateYesNo").show();
			$('#endDateYes').attr('checked');
		} else if (freqOccur == "4") {
			$("#actionOnSuccess").show();
			$("#actionOnFailure").show();
			$("#triggerTime").show();
			$("#dayOfWeek").hide();
			$("#dayOfMonth").show();
			$("#yearlyMonth").hide();
			$("#yearlyDayOfMonth").hide();
			$("#repeatCount").hide();
			$("#repeatIntervalSecond").hide();
			$("#specifyEndDate").show();
			$("#specifyEndDateYesNo").show();
			$('#endDateYes').attr('checked');
		} else if (freqOccur == "5") {
			$("#actionOnSuccess").show();
			$("#actionOnFailure").show();
			$("#triggerTime").show();
			$("#dayOfWeek").hide();
			$("#dayOfMonth").hide();
			$("#yearlyMonth").show();
			$("#yearlyDayOfMonth").show();
			$("#repeatCount").hide();
			$("#repeatIntervalSecond").hide();
			$("#specifyEndDate").show();
			$("#specifyEndDateYesNo").show();
			$('#endDateYes').attr('checked');
		} else if (freqOccur == "6") {
			$("#actionOnSuccess").hide();
			$("#actionOnFailure").hide();
			//$( "#actionEmailAddress").hide();
			$("#triggerTime").hide();
			$("#dayOfWeek").hide();
			$("#dayOfMonth").hide();
			$("#yearlyMonth").hide();
			$("#yearlyDayOfMonth").hide();
			$("#repeatCount").show();
			$("#repeatIntervalSecond").show();
			$("#specifyEndDate").show();
			$("#specifyEndDateYesNo").show();
			$('#endDateYes').attr('checked');
		} else if (freqOccur == "7") {
			$("#actionOnSuccess").hide();
			$("#actionOnFailure").hide();
			//$( "#actionEmailAddress").hide();
			$("#triggerTime").hide();
			$("#dayOfWeek").hide();
			$("#dayOfMonth").hide();
			$("#yearlyMonth").hide();
			$("#yearlyDayOfMonth").hide();
			$("#repeatCount").hide();
			$("#repeatIntervalSecond").show();
			$("#specifyEndDate").show();
			$("#specifyEndDateYesNo").show();
			$('#endDateYes').attr('checked');
		} else if (freqOccur == "1") {
			$("#actionOnSuccess").show();
			$("#actionOnFailure").show();
			$("#triggerTime").show();
			$("#dayOfWeek").hide()
			$("#dayOfMonth").hide();
			$("#yearlyMonth").hide();
			$("#yearlyDayOfMonth").hide();
			$("#repeatCount").hide();
			$("#repeatIntervalSecond").hide();
			$("#specifyEndDate").hide();
			$("#specifyEndDateYesNo").hide();
			$('#endDateNo').attr('checked');
			var maxDate = new Date();
			 maxDate.setFullYear(maxDate.getFullYear() + 10); // Add 10 years
			 $('#durationStartDate').datepicker('option', 'maxDate', maxDate);
			 $("#durationEndDate").val("");
		}

		/* 	if($('#actionOnSuccess').is(':visible') && $('#actionOnFailure').is(':visible')) {
		    actionOnChange();
		} */

		if ($('#endDateYes').is(':checked')
				&& $('#specifyEndDateYesNo').is(':visible')) {
			$("#endDate").show();
		}

		if ($('#endDateNo').is(':checked')|| ($('#endDateYes').is(':checked') && !$('#specifyEndDateYesNo').is(':visible'))) {
			$("#endDate").hide();
		}
	}

	/* function actionOnChange() {
		var onSuccessType = $( "#actionOnSuccessType").val();
		var onFailureType = $( "#actionOnFailureType").val();
		
		if ((onSuccessType == "2")||(onSuccessType == "4")||(onFailureType == "2")||(onFailureType == "4")) {
			$( "#actionEmailAddress").show();
		}else{
			$( "#actionEmailAddress").hide();
		}		
	} */

	function yearlyMonthOnChange() {

		var month31Days = [ "01", "03", "05", "07", "08", "10", "12" ];
		var monthValue = $("#freqYearlyMonth").val();
		var dayOptionsLength = $("#freqYearlyDayOfMonth option").size();
		if (month31Days.indexOf(monthValue) == -1) {
			if (monthValue == "02") {
				if (dayOptionsLength >= 30) {
					$("#freqYearlyDayOfMonth option[value='30']").remove();
					$("#freqYearlyDayOfMonth option[value='31']").remove();
				}
			} else {
				if (dayOptionsLength >= 30) {
					$("#freqYearlyDayOfMonth option[value='31']").remove();
				} else {
					$("#freqYearlyDayOfMonth").append(
							'<option value="30">30</option>');
				}
			}
		} else {
			if (dayOptionsLength == 29) {
				$("#freqYearlyDayOfMonth").append(
						'<option value="30">30</option>');
				$("#freqYearlyDayOfMonth").append(
						'<option value="31">31</option>');
			} else if (dayOptionsLength == 30) {
				$("#freqYearlyDayOfMonth").append(
						'<option value="31">31</option>');
			}
		}
	}

	/* function validateEmail() {
	    var expr = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	    if($('#actionEmailAddress').is(':visible')) {
	        if($('#emailAddress').val() != '') {
	            var strMail = $('#emailAddress').val();
	            alert('strMail='+strMail);
	            var mailList = strMail.split(";");
	            var mail;
	            for(mail in mailList) {
	                if(!expr.test(mailList[mail]) && mailList[mail] != '') {
	                	 $('#emailAddress').css("background-color", "#F7F8E0");
	                	return false;
	                }
	            }
	        } 
	    }
	    $('#emailAddress').css("background-color", "white");
	    return true;
	} */
	function validateForm() {
		// Check requirement data
		if(isEmpty($("#scheduleName").val())) {
			//alert('Tên lịch không được để trống.');
			$.alert({
					title: 'Thông báo',
					content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Tên lịch không được để trống",
			});
			return false;
		}

		if(!$("#scheduleEnabled").is(':checked')) {
			return true;
		}
		
		// Frequency: Daily
		if($("#freqOccurType").val() == "2") {
			return checkDailyJobValid();
		}
		// Frequency: Weekly
		else if($("#freqOccurType").val() == "3") {
			return checkWeeklyJobValid();
		}
		// Frequency: Monthly
		else if($("#freqOccurType").val() == "4") {
			return checkMonthlyJobValid();
		}
		// Frequency: Yearly
		else if($("#freqOccurType").val() == "5") {
			return checkYearlyJobValid();
		}
		// Frequency: RepeatAtInterval
		else if($("#freqOccurType").val() == "6") {
			return checkRepeatJobValid();
		}
		// Frequency: RepeatInDefinitely
		else if($("#freqOccurType").val() == "7") {
			return checkRepeatDefJobValid();
		}
		// Frequency: Specific
		else if($("#freqOccurType").val() == "1") {
			return checkSpecificJobValid();
		}
		
	}

	function isEmpty(str) {
		if(str == null || str == 'undefined' || str.trim().length <= 0) {
			return true;
		}
		return false;
	}
	
	function isTimeValid(hour, min, suffix) {
		var currentTime = new Date();
	    var curHour = currentTime.getHours();
	    var curMin = currentTime.getMinutes();

	    if(suffix == 'PM') {
	    	hour = hour + 12;
		}
		
		if(hour == 24) {
			return true;
		}

		if(hour < curHour) {
			return false;
		} else if(hour > curHour) {
			return true;
		} else {
			if(curMin > min) {
				return false;
			} else {
				return true;
			}
		}
	}

	// Daily schedule job exec check
	function checkDailyJobValid() {
		if(isEmpty($("#durationStartDate").val())) {
			//alert('Không cho phép ngày bắt đầu để trống.!');
			$.alert({
					title: 'Thông báo',
					content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Không cho phép ngày bắt đầu để trống",
			});
			return false;
		} else {
			if(!isEmpty($("#durationEndDate").val())) {
				// Check time is not past.
				var timeType = "";
				if($("#timeAm").is(':checked')) {
					timeType = "AM";
				} else if($("#timePm").is(':checked')) {
					timeType = "PM";
				}
				if(!isTimeValid($("#freqDailyTriggerTimeHr").val(), $("#freqDailyTriggerTimeMin").val(), timeType)) {
					//alert('Lịch trình thời gian ngừng việc không hợp lệ.');
					$.alert({
							title: 'Thông báo',
							content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Lịch trình thời gian ngừng việc không hợp lệ",
					});
					return false;
				}
			}
		}
		return true;
	}

	// Weekly schedule job exec check
	function checkWeeklyJobValid() {
		if(daysSelected().length <= 0) {
			//alert('Các ngày trong tuần phải được chọn.');
			$.alert({
					title: 'Thông báo',
					content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Các ngày trong tuần phải được chọn",
			});
			return false;
		}
		if(isEmpty( $("#durationStartDate").val())) {
			//lert('Không cho phép bắt đầu ngày để trống!');
			$.alert({
					title: 'Thông báo',
					content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Không cho phép bắt đầu ngày để trống!",
			});
			return false;
		} else {
			if(!isEmpty($("#durationEndDate").val())) {
				var start = $("#durationStartDate").datepicker("getDate");
		     	var end = $("#durationEndDate").datepicker("getDate");
		     	var daysNo = (end - start) / (1000 * 60 * 60 * 24);
				if(daysNo == 0) {
					//alert('Công việc phân bổ thời gian ngày không hợp lệ.');
					$.alert({
							title: 'Thông báo',
							content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Công việc phân bổ thời gian ngày không hợp lệ",
					});
					return false;
				} else {
					var todayNo = today.getDay();
					var dayArr = this.getDurationDateArr();
					var sDayArr = this.daysSelected();
					var days = ["Chủ nhật", "Thứ 2", "Thứ 3","Thứ 4", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7"];
					for(sday = 0; sday < sDayArr.length; sday ++) {
						if((dayArr.indexOf(sDayArr[sday]) > 0) 
							|| (dayArr.indexOf(sDayArr[sday]) == 0 && (daysNo >= 7 || days.indexOf(sDayArr[sday]) > todayNo))) {
							return true;
						}
					}
					return false;
				}
			} 
		}

		return true;
	}

	// Monthly schedule job exec check
	function checkMonthlyJobValid() {
		var dayOfMonth = $("#freqMonthlyDayOfMonth").val();
		if(isEmpty($("#durationStartDate").val())) {
			//alert('Không cho phép để trống ngày bắt đầu!');
			$.alert({
					title: 'Thông báo',
					content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Không cho phép để trống ngày bắt đầu",
			});
			return false;
		} else {
			if(!isEmpty($("#durationEndDate").val())) {
				// Check time is not past.
		     	var startDate = $("#durationStartDate").datepicker("getDate");
		     	var endDate = $("#durationEndDate").datepicker("getDate");
		     	var selStartDate = $("#durationStartDate").datepicker("getDate");
		     	var selEndDate = $("#durationEndDate").datepicker("getDate");
		     	selStartDate.setDate(dayOfMonth);
		     	selEndDate.setDate(dayOfMonth);

		     	var dateDiff1 = (selStartDate - startDate)/ (1000 * 60 * 60 * 24);
		     	var dateDiff2 = (selEndDate - startDate)/ (1000 * 60 * 60 * 24);
		     	var dateDiff3 = (endDate - selStartDate)/ (1000 * 60 * 60 * 24);
		    	var dateDiff4 = (endDate - selEndDate)/ (1000 * 60 * 60 * 24);

		    	var selMonth = selStartDate.getMonth();
		    	var daysNo = new Date(selStartDate).getDate();
		    	//alert(selMonth + "||" + daysNo);
		    	$.alert({
							title: 'Thông báo',
							content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + selMonth + "||" + daysNo,
						});
		     	if(calTotalMonthsDiff(startDate, endDate) > 1) {
			     	return true;
			    } else if(calTotalMonthsDiff(startDate, endDate) == 1) {
			    	 if(dayOfMonth > daysNo) {
				    	//alert('Giá trị không hợp lệ cho Ngày của tháng: '+dayOfMonth+' - phải nằm trong khoảng từ 01 đến '+daysNo+'.');
						$.alert({
							title: 'Thông báo',
							content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Giá trị không hợp lệ cho Ngày của tháng: " +dayOfMonth+"\n phải nằm trong khoảng từ 01 đến "+daysNo,
						});
				    	return false; 
					}
					return true;
				} 
		     
			    if((dateDiff1 >= 0 || dateDiff2 >= 0) && (dateDiff3 > 0 || dateDiff4 > 0) ) {
				    if(dayOfMonth > daysNo) {
				    	//alert('Giá trị không hợp lệ cho Ngày của tháng: '+dayOfMonth+' - phải nằm trong khoảng từ 01 đến '+daysNo+'.');
						$.alert({
							title: 'Thông báo',
							content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + " Giá trị không hợp lệ cho Ngày của tháng: " +dayOfMonth+"\n phải nằm trong khoảng từ 01 đến "+daysNo,
						});
				    	return false; 
					} 
				    
				    return true;
				}

		     	//alert('Công việc phân bổ thời gian ngày không hợp lệ.');
		     	$.alert({
							title: 'Thông báo',
							content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + "Công việc phân bổ thời gian ngày không hợp lệ",
						});
		     	return false;
			}
			
		}
		return true;
	}

	// Calculate total months 
	calTotalMonthsDiff = function(firstDate, secondDate) {
        var fm = firstDate.getMonth();
        var fy = firstDate.getFullYear();
        var sm = secondDate.getMonth();
        var sy = secondDate.getFullYear();
        var months = Math.abs(((fy - sy) * 12) + fm - sm);
        var firstBefore = firstDate > secondDate;
        firstDate.setFullYear(sy);
        firstDate.setMonth(sm);
        firstBefore ? firstDate < secondDate ? months-- : "" : secondDate < firstDate ? months-- : "";
        return months;
	}

	// RepeatAtInterval schedule job exec check
	function checkRepeatJobValid() {
		if(isEmpty($("#durationStartDate").val())) {
			//alert('Không cho phép để trống ngày bắt đầu!');
			$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Không cho phép để trống ngày bắt đầu!",
			});
			return false;
		} 
		return true;
	}

	// Yearly schedule job exec check
	function checkYearlyJobValid() {
		
		if(isEmpty($("#durationStartDate").val())) {
			//alert('Không cho phép để trống ngày bắt đầu!');
			$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Không cho phép để trống ngày bắt đầu!",
			});
			return false;
		} else {
			if(!isEmpty($("#durationEndDate").val())) {
				// Check time is not past.
				var dayOfMonth = $("#freqMonthlyDayOfMonth").val();
				var monthOfYear = $("#freqYearlyMonth").val();
				var startDate = $("#durationStartDate").datepicker("getDate");
		     	var endDate = $("#durationEndDate").datepicker("getDate");
		     	var selDate = $("#durationStartDate").datepicker("getDate");
		     	selDate.setMonth(monthOfYear, dayOfMonth);
		     	//alert(selDate);
		     	$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + selDate,
			});
		     	if(selDate >= startDate && selDate < endDate) {
			     }
		     	if(monthOfYear == '02' && dayOfMonth == '29') {
			     	
				}
		     	
				
			}
		}
		return true;
	}
	
	// RepeatInDefinitely schedule job exec check
	function checkRepeatDefJobValid() {
		if(isEmpty($("#durationStartDate").val())) {
			//alert('Empty start date not allowed!');
			$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Không cho phép để trống ngày bắt đầu!",
			});
			return false;
		} 
		return true;
	}

	// Specific schedule job exec check
	function checkSpecificJobValid() {
		if(isEmpty($("#durationStartDate").val())) {
			//alert('Không cho phép để trống ngày bắt đầu!');
			$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Không cho phép để trống ngày bắt đầu!",
			});
			return false;
		} else {
			// Check time is not past.
			var timeType = "";
			if($("#timeAm").is(':checked')) {
				timeType = "AM";
			} else if($("#timePm").is(':checked')) {
				timeType = "PM";
			}
			if(!isTimeValid($("#freqDailyTriggerTimeHr").val(), $("#freqDailyTriggerTimeMin").val(), timeType)) {
				//alert('Lịch trình thời gian ngừng việc không hợp lệ.');
				$.alert({
							title: 'Thông báo',
							content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + "Lịch trình thời gian ngừng việc không hợp lệ",
						});
				return false;
			}
		}
		return true;
	}
	
	// Get days of week
	function daysSelected() {
		var testval = [];
		 $('.daysOfWeek:checked').each(function() {
		   	testval.push($(this).val());
		 });

		 return testval;
	} 

	function getDurationDateArr() {
		var start = $("#durationStartDate").datepicker("getDate");
     	var end = $("#durationEndDate").datepicker("getDate");
     	var days = (end - start) / (1000 * 60 * 60 * 24);
    	var durationDate = [];
    	var startDate = new Date(start);
    	for(day = 0; day <= days; day ++) {
    		var date = new Date(start);
        	date.setDate(startDate.getDate() + day);
        	var tempDay = "";
        	if(date.getDay() == 0) {
        		tempDay = "SUN";
            } else if(date.getDay() == 1) {
            	tempDay = "MON";
            } else if(date.getDay() == 2) {
            	tempDay = "TUE";
            } else if(date.getDay() == 3) {
            	tempDay = "WED";
            } else if(date.getDay() == 4) {
            	tempDay = "THU";
            } else if(date.getDay() == 5) {
            	tempDay = "FRI";
            } else if(date.getDay() == 6) {
            	tempDay = "SAT";
            }
        	if(durationDate.indexOf(tempDay) < 0) {
        		durationDate.push(tempDay);
            } 
    		
    		if(durationDate.length == 7) {
        		break;
        	}
        }

        return durationDate;
	}

</script>
<style type="text/css">
    #dayOfWeek span {
        padding-right: 10px;
    }
</style>
<form:form  modelAttribute="eppJobScheduleForm" id="scheduleForm" >
<div id="content_main"> 
	<div class="container">
	        	<div class="row">
	        		<div class="roundedBorder ov_hidden">
			       <div class="new-heading-2">LƯU LỊCH CÔNG VIỆC</div>
			      
         		  <div class="col-md-12">
         		  		<div class="col-md-12">
							<div class="col-md-2 fix-bottom-1">
								<label for="countryCode">Mã công việc:</label>
							</div>
							<div class="col-md-10 fix-bottom-1" style="padding-left: 5px;">
								<span class="form-control"><c:out value="${eppJobScheduleForm.jobId}" /></span>
								<form:hidden id="jobId" path="jobId" />
							</div>
							<div class="col-md-2 fix-bottom-1">
								<label for="countryCode">Tên công việc:</label>
							</div>
							<div class="col-md-10 fix-bottom-1" style="padding-left: 5px;">
								<span class="form-control"><c:out value="${eppJobScheduleForm.jobName}" /></span>
								<form:hidden id="jobName" path="jobName" />
							</div>
							<div class="col-md-2 fix-bottom-1">
								<label for="countryCode">Tên lớp:</label>
							</div>
							<div class="col-md-10 fix-bottom-1" style="padding-left: 5px;">
								<span class="form-control"><c:out value="${eppJobScheduleForm.jobClass}" /></span>
					 			<form:hidden id="jobClass" path="jobClass" />								
							</div>
         		  		</div>
         		  </div>
         		   <div class="col-md-12">
         		  		<div class="col-md-6">
							<div class="col-md-4 fix-bottom-1">
								<label for="countryCode">Tên lịch:</label>
							</div>
							<div class="col-md-8 fix-bottom-1">
								<c:if test="${eppJobScheduleForm.mode == 'AMEND'}">
									 <span class="form-control"> <c:out value="${eppJobScheduleForm.scheduleName}" /> </span>
									 <form:hidden id="scheduleName" path="scheduleName" />			
								</c:if>
								<c:if test="${eppJobScheduleForm.mode == 'ADD'}">
									<form:input id="scheduleName" class="form-control" path="scheduleName"  type="text" size='80' />
								</c:if>
							</div>
							<div class="col-md-4 fix-bottom-1">
								<label for="countryCode">Đã bật lịch:</label>
							</div>
							<div class="col-md-8 fix-bottom-1" style="height: 30px;">
								<form:checkbox id="scheduleEnabled" class="form-control" path="scheduleEnabled" size='20' style="cursor: pointer;" />
							</div>
							<div class="col-md-4 fix-bottom-1">
								<label for="countryCode">Sự xuất hiện:</label>
							</div>
							<div class="col-md-8 fix-bottom-1">
								<form:select id="freqOccurType" class="form-control" path="freqOccurType" style="cursor: pointer;">
									<c:forEach var="maplist" items="${eppJobScheduleForm.freqOccurTypeList}" varStatus="status1">
										<form:option value="${maplist.value}" label="${maplist.label }"/>
									</c:forEach>
								</form:select>							
							</div>
							<div id="specifyEndDate" class="col-md-4 fix-bottom-1">
								<label for="countryCode">Chỉ định ngày kết thúc:</label>
							</div>
							<div id="specifyEndDateYesNo" class="col-md-8 fix-bottom-1" style="height: 30px;">
								<c:if test="${eppJobScheduleForm.durationEndDateEnabled == 'No'}">
									<form:radiobutton  id="endDateYes" path="durationEndDateEnabled" value="Yes" style="margin-right: 5px;" />
										<span class="text-span-1">Có</span>
									<form:radiobutton  id="endDateNo" path="durationEndDateEnabled" value="No" checked="true" style="margin-right: 5px;"/>
										<span class="text-span-1">Không</span>
								</c:if>
								<c:if test="${empty eppJobScheduleForm.durationEndDateEnabled or eppJobScheduleForm.durationEndDateEnabled == 'Yes' }">
									<form:radiobutton  id="endDateYes" path="durationEndDateEnabled" value="Yes"  checked="true" style="margin-right: 5px;"/>
										<span class="text-span-1">Có</span>
									<form:radiobutton  id="endDateNo" path="durationEndDateEnabled" value="No" style="margin-right: 5px;"/>
										<span class="text-span-1">Không</span>
								</c:if>							
							</div>
         		  		</div>
         		  		<div class="col-md-6">
         		  			<div id="actionOnSuccess" class="col-md-12">
								<div class="col-md-4 fix-bottom-1" >
									<label for="countryCode">Hành động thành công:</label>
								</div>
								<div class="col-md-8 fix-bottom-1">
									<form:select id="actionOnSuccessType" class="form-control" path="actionOnSuccessType" style="cursor: pointer;">
										<c:forEach var="maplist" items="${eppJobScheduleForm.successTypeList}" varStatus="status1">
											<form:option value="${maplist.value}" label="${maplist.label }"/>
										</c:forEach>
									</form:select>
								</div>
         		  			</div>
         		  			<div id="actionOnFailure" class="col-md-12">
								<div  class="col-md-4 fix-bottom-1" >
									<label for="countryCode">Hành động thất bại:</label>
								</div>
								<div  class="col-md-8 fix-bottom-1">
									<form:select id="actionOnFailureType" class="form-control" path="actionOnFailureType" style="cursor: pointer;">
										<c:forEach var="maplist" items="${eppJobScheduleForm.failureTypeList}" varStatus="status1">
											<form:option value="${maplist.value}" label="${maplist.label }"/>
										</c:forEach>
									</form:select>
								</div>
         		  			</div>
         		  			<div class="col-md-12">
								<div class="col-md-4 fix-bottom-1">
									<label for="countryCode">Ngày bắt đầu:</label>
								</div>
								<div class="col-md-8 fix-bottom-1">
									<form:input class="pickDateTimeForLocale form-control" path="durationStartDate" type="text" id="durationStartDate" value="${eppJobScheduleForm.durationStartDate}" size="10"	readonly="true" />							
								</div>
         		  			</div>
							<div id="endDate" class="col-md-12">
								<div class="col-md-4 fix-bottom-1">
									<label for="countryCode">Ngày kết thúc:</label>
								</div>
								<div class="col-md-8 fix-bottom-1" >
									<form:input class="pickDateTimeForLocale form-control" path="durationEndDate" type="text" id="durationEndDate" value="${eppJobScheduleForm.durationEndDate}" size="10"	readonly="true" />							
								</div>
							</div>
         		  		</div>
         		  </div>
         		  <div class="col-md-12">
         		  		<div  id="triggerTime" class="col-md-6">
         		  			<div class="col-md-4 fix-bottom-1">
								<label for="countryCode">Thời gian kích hoạt:</label>
							</div>
							<div class="col-md-8 fix-bottom-1">
								<form:select id="freqDailyTriggerTimeHr" path="freqDailyTriggerTimeHr" style="height: 34px;font-size: 14px;margin-right: 5px;">
	                     		<c:forEach var="maplist" items="${eppJobScheduleForm.hourList}" varStatus="status1">
									<form:option value="${maplist.value}" label="${maplist.label }"/>
								</c:forEach>
							   </form:select><span class="text-span-1">giờ</span>
							   <form:select id="freqDailyTriggerTimeMin" path="freqDailyTriggerTimeMin" style="height: 34px;font-size: 14px;margin-right: 5px;">
							   <c:forEach var="maplist" items="${eppJobScheduleForm.minList}" varStatus="status1">
									<form:option value="${maplist.value}" label="${maplist.label }"/>
								</c:forEach>
							   </form:select><span class="text-span-1">phút</span>
							   <c:if test="${eppJobScheduleForm.freqDailyTriggerTimeFormat == 'PM'}">
									<form:radiobutton id="timeAm" name="freqDailyTriggerTimeAm"  path="freqDailyTriggerTimeFormat" value="AM" style="margin-right: 5px;"/>
							    <span class="text-span-1">sáng</span>
							    <form:radiobutton id="timePm" name="freqDailyTriggerTimePm" path="freqDailyTriggerTimeFormat" value="PM" checked="true" style="margin-right: 5px;"/>
							     <span class="text-span-1">chiều</span>
								</c:if>
								<c:if test="${empty eppJobScheduleForm.freqDailyTriggerTimeFormat or eppJobScheduleForm.freqDailyTriggerTimeFormat == 'AM' }">
									<form:radiobutton id="timeAm" name="freqDailyTriggerTimeAm"  path="freqDailyTriggerTimeFormat" value="AM" checked="true" style="margin-right: 5px;"/>
							    <span class="text-span-1">sáng</span>
							    <form:radiobutton id="timePm" name="freqDailyTriggerTimePm" path="freqDailyTriggerTimeFormat" value="PM" style="margin-right: 5px;"/>
							    <span class="text-span-1">chiều</span>
								</c:if>
							</div>
         		  		</div>
         		  		<div id="repeatIntervalSecond" class="col-md-6">
         		  			<div class="col-md-4 fix-bottom-1">
								<label for="countryCode">Khoảng thời gian:</label>
							</div>
							<div class="col-md-8 fix-bottom-1">
								<form:input name="freqRepeatIntervalSecond" class="form-control" path="freqRepeatIntervalSecond" maxlength="20" size="20"  onkeypress='return event.charCode >= 48 && event.charCode <= 57'></form:input>
							</div>
         		  		</div>
         		  </div>
         		  <div id="dayOfWeek" class="col-md-12 fix-bottom-1">
         		  	  		<div class="col-md-2" style="padding-left: 20px;">
         		  	  				<label for="countryCode">Ngày trong tuần:</label>
         		  	  		</div>
         		  	  		<div class="col-md-10">
         		  	  				<form:checkboxes id="freqWeeklyDayOfWeek" element="span" items="${eppJobScheduleForm.weeklyDayList}" itemLabel="label"  itemValue="value" name="freqWeeklyDayOfWeek" path="freqWeeklyDayOfWeek" class="daysOfWeek"/>
         		  	  		</div>
         		  </div>
         		   <div id="dayOfMonth" class="col-md-12 fix-bottom-1">
         		  	  	<div class="col-md-6">
         		  	  			<div class="col-md-4">
         		  	  				<label for="countryCode">Ngày trong tháng:</label>
         		  	  			</div>
         		  	  			<div class="col-md-8">
         		  	  				<form:select id="freqMonthlyDayOfMonth" path="freqMonthlyDayOfMonth" class="form-control">
										<c:forEach var="maplist" items="${eppJobScheduleForm.dayList}" varStatus="status1">
											<form:option value="${maplist.value}" label="${maplist.label }"/>
										</c:forEach>
								   	</form:select>
         		  	  			</div>
         		  	  	</div>
         		  </div>
         		   <div id="yearlyMonth" class="col-md-12 fix-bottom-1">
         		  	  	<div class="col-md-6">
         		  	  			<div class="col-md-4">
         		  	  				<label for="countryCode">Tháng:</label>
         		  	  			</div>
         		  	  			<div class="col-md-8">
         		  	  				<form:select id="freqYearlyMonth" path="freqYearlyMonth" class="form-control">
										<c:forEach var="maplist" items="${eppJobScheduleForm.monthList}" varStatus="status1">
											<form:option value="${maplist.value}" label="${maplist.label }"/>
										</c:forEach>
								   	</form:select>
         		  	  			</div>
         		  	  	</div>
         		  </div>

         		  <div id="yearlyDayOfMonth" class="col-md-12 fix-bottom-1">
         		  	  	<div class="col-md-6">
         		  	  			<div class="col-md-4">
         		  	  				<label for="countryCode">Ngày của tháng:</label>
         		  	  			</div>
         		  	  			<div class="col-md-8">
         		  	  				<form:select id="freqYearlyDayOfMonth" path="freqYearlyDayOfMonth" class="form-control">
										<c:forEach var="maplist" items="${eppJobScheduleForm.yearDayList}" varStatus="status1">
											<form:option value="${maplist.value}" label="${maplist.label }"/>
										</c:forEach>
									</form:select>
         		  	  			</div>
         		  	  	</div>
         		  </div>
				 <div id="repeatCount" class="col-md-12 fix-bottom-1">
         		  	  	<div class="col-md-6">
         		  	  			<div class="col-md-4">
         		  	  				<label for="countryCode">Không lặp lại:</label>
         		  	  			</div>
         		  	  			<div class="col-md-8">
         		  	  				<form:input type="text" name="freqRepeatIntervalCount"  path="freqRepeatIntervalCount" class="form-control"  size="10" maxlength="10" onkeypress='return event.charCode >= 48 && event.charCode <= 57'/>
         		  	  			</div>
         		  	  	</div>
         		  </div>
         		   <div class="col-md-12" style="text-align: right;margin-top: 30px;">
			      		<c:if test="${eppJobScheduleForm.mode == 'AMEND'}">
			      			<button type="button" class="btn btn-warning" id="delAct">
								<span class="glyphicon glyphicon-trash"></span> Xóa
							</button>
							<button type="button" class="btn btn-info" id="amendAct">
								<span class="glyphicon glyphicon-ok"></span> Sửa đổi
							</button>
							<!--<input type="button" id="delAct" class="btn btn-warning" value="Xóa" />
							<input type="button" id="amendAct" class="btn btn-info" value="Sửa đổi" />-->
						</c:if> 
						<button type="button" class="btn btn-danger" id="calAct">
							<span class="glyphicon glyphicon-remove"></span> Hủy
						</button>
						<button type="button" class="btn btn-primary" id="resetAct">
							<span class="glyphicon glyphicon-refresh"></span> Làm mới
						</button>	
						<!--<input type="button" id="calAct" class="btn btn-danger"  value="Hủy" />
						<input type="button" id="resetAct" class="btn btn-primary"  value="Làm mới" />-->
						<c:if test="${eppJobScheduleForm.mode == 'ADD'}">
							<!--<input type="button" id="saveAct" class="btn btn-success" value="Lưu" />-->
							<button type="button" class="btn btn-success" id="saveAct">
								<span class="glyphicon glyphicon-ok"></span> Lưu
							</button>
						</c:if> 
         		  </div>  
          <!--<div class="displayTag" style="background-image:url('<%=request.getContextPath() %>/resources/images/head.png');background-repeat: repeat-x; margin-bottom: 5px;">
			<table style="width:100%;">
				<c:if test='${not empty eppJobScheduleForm.mode}'>
				<tr><td width="15%" style="padding: 10px 5px 5px 10px;"><span>Mã công việc: </span></td>
					<td style="padding: 10px 5px 5px 10px;"><span><c:out value="${eppJobScheduleForm.jobId}" /></span>
				<form:hidden id="jobId" path="jobId" />
				</td></tr>
				<tr><td width="15%" style="padding: 0 0 5px 10px;"><span>Tên công việc: </span></td>
				<td style="padding: 0 0 5px 10px;"><span><c:out value="${eppJobScheduleForm.jobName}" /></span>
				<form:hidden id="jobName" path="jobName" />
				</td></tr>
				<tr><td style="padding: 0 0 10px 10px;"><span>Tên lớp: </span></td>
				<td style="padding: 0 0 10px 10px;"> <span><c:out value="${eppJobScheduleForm.jobClass}" /></span>
				 <form:hidden id="jobClass" path="jobClass" />
				</td></tr>
				</c:if>
		   </table> 
	   </div>-->
	 <form:hidden id="mode" path="mode" />
	 <form:hidden id="prevMode" path="prevMode" />
	 <!--<div class="displayTag" style="background-image:url('<%=request.getContextPath() %>/resources/images/head.png');background-repeat: repeat-x; margin-bottom: 5px;">
		<table style="width:100%;">
			<tr>
				<td width="15%" style="padding: 10px 5px 5px 10px;"><span>Tên Lịch:</span></td>
				<c:if test="${eppJobScheduleForm.mode == 'AMEND'}">
				<td style="padding: 10px 5px 5px 10px;"><span> <c:out value="${eppJobScheduleForm.scheduleName}" /> </span>
				 <form:hidden id="scheduleName" path="scheduleName" />
				</td>
				</c:if>
				<c:if test="${eppJobScheduleForm.mode == 'ADD'}">
				<td style="padding: 10px 5px 5px 10px;"><span><form:input id="scheduleName" path="scheduleName"  type="text" size='80' /></span></td>
				</c:if>
			</tr>
			<tr>
				<td width="15%" style="padding: 0 0 10px 10px;"><span>Đã Bật Lịch:</span></td>
				<td style="padding: 0 0 10px 10px;"><span><form:checkbox id="scheduleEnabled" path="scheduleEnabled" size='20' style="cursor: pointer;" /></span></td>
			</tr>
		</table>
		<%-- </div>
		<div class="displayTag" style="background-image:url('<%=request.getContextPath() %>/resources/images/head.png');background-repeat: repeat-x; margin-bottom: 5px;"> --%>
			<table  style="width:100%;">
			<tr>
				<td width="15%" style="padding: 10px 5px 5px 10px;"><span>Sự xuất hiện :</span></td>
				<td style="padding: 10px 5px 5px 10px;"><span>
					<form:select id="freqOccurType" path="freqOccurType" style="cursor: pointer;">
						<c:forEach var="maplist" items="${eppJobScheduleForm.freqOccurTypeList}" varStatus="status1">
							<form:option value="${maplist.value}" label="${maplist.label }"/>
						</c:forEach>
					</form:select>
				</span></td>
			</tr>
			<tr id="actionOnSuccess">
				<td width="15%" style="padding: 0 0 5px 10px;"><span>Hành động thành công:</span></td>
				<td style="padding: 0 0 5px 10px;"><span>
					<form:select id="actionOnSuccessType" path="actionOnSuccessType" style="cursor: pointer;">
					<c:forEach var="maplist" items="${eppJobScheduleForm.successTypeList}" varStatus="status1">
						<form:option value="${maplist.value}" label="${maplist.label }"/>
					</c:forEach>
					</form:select>
				</span></td>
			</tr>
			<tr id="actionOnFailure">
				<td width="15%" style="padding: 0 0 5px 10px;"><span>Hành động thất bại:</span></td>
				<td style="padding: 0 0 5px 10px;"><span>
					<form:select id="actionOnFailureType" path="actionOnFailureType" style="cursor: pointer;">
					<c:forEach var="maplist" items="${eppJobScheduleForm.failureTypeList}" varStatus="status1">
						<form:option value="${maplist.value}" label="${maplist.label }"/>
					</c:forEach>
					</form:select></span></td>
			</tr>
			<%-- <tr id="actionEmailAddress">
				<td width="15%" style="padding: 010px;"><span>10px;">Email Addresses(;):</span></td>
				<td style="padding: 0 0 5px 10px;"><span><form:input id="emailAddress" path="emailAddress" size='80' maxlength="80" /></span></td>
			</tr> --%>
			
			<tr>
				<td colspan="2" style="padding-bottom: 5px;" />
			</tr>
		</table>
		</div>-->
		<!--<div class="displayTag" style="background-image:url('<%=request.getContextPath() %>/resources/images/head.png');background-repeat: repeat-x; margin-bottom: 5px;">
			<table  style="width:100%;">
				<tr>
				<td colspan="2" style="padding-top: 10px;" />
			</tr>
				<tr id="triggerTime">
                      <td width="15%" style="padding: 0 0 5px 10px;"><span>Thời gian kích hoạt: </span></td>
                      <td style="padding: 0 0 5px 10px;"><span>
                      		<form:select id="freqDailyTriggerTimeHr" path="freqDailyTriggerTimeHr" style="cursor: pointer;">
                     		<c:forEach var="maplist" items="${eppJobScheduleForm.hourList}" varStatus="status1">
								<form:option value="${maplist.value}" label="${maplist.label }"/>
							</c:forEach>
						   </form:select>&nbsp;hr
						   <form:select id="freqDailyTriggerTimeMin" path="freqDailyTriggerTimeMin" style="cursor: pointer;">
						   <c:forEach var="maplist" items="${eppJobScheduleForm.minList}" varStatus="status1">
								<form:option value="${maplist.value}" label="${maplist.label }"/>
							</c:forEach>
						   </form:select>&nbsp;min&nbsp;&nbsp;
						   <c:if test="${eppJobScheduleForm.freqDailyTriggerTimeFormat == 'PM'}">
								<form:radiobutton id="timeAm" name="freqDailyTriggerTimeAm"  path="freqDailyTriggerTimeFormat" value="AM" style="cursor: pointer;"/>
						    AM&nbsp;
						    <form:radiobutton id="timePm" name="freqDailyTriggerTimePm" path="freqDailyTriggerTimeFormat" value="PM" checked="true" style="cursor: pointer;"/>
						     PM
							</c:if>
							<c:if test="${empty eppJobScheduleForm.freqDailyTriggerTimeFormat or eppJobScheduleForm.freqDailyTriggerTimeFormat == 'AM' }">
								<form:radiobutton id="timeAm" name="freqDailyTriggerTimeAm"  path="freqDailyTriggerTimeFormat" value="AM" checked="true" style="cursor: pointer;"/>
						    AM&nbsp;
						    <form:radiobutton id="timePm" name="freqDailyTriggerTimePm" path="freqDailyTriggerTimeFormat" value="PM" style="cursor: pointer;"/>
						     PM
							</c:if>
                       </span></td>
                 	
				</tr>
				<tr id="dayOfWeek">
					<td width="15%" style="padding: 0 0 5px 10px;"><span>Day of Week:</span></td>
					<td style="padding: 0 0 5px 10px;"><span>
						<%-- <form:checkbox path="" id="checkAllAct" value="ALL" label="All"/>&nbsp;&nbsp;&nbsp; --%>
						<form:checkboxes id="freqWeeklyDayOfWeek" element="span" items="${eppJobScheduleForm.weeklyDayList}" itemLabel="label"  itemValue="value" name="freqWeeklyDayOfWeek" path="freqWeeklyDayOfWeek" style="cursor: pointer;" class="daysOfWeek"/>
					</span></td>
				</tr>
				<tr id="dayOfMonth">
					<td width="15%" style="padding: 0 0 5px 10px;"><span>Day of Month: </span>
					</td>
					<td style="padding: 0 0 5px 10px;"><span>
					<form:select id="freqMonthlyDayOfMonth" path="freqMonthlyDayOfMonth" style="cursor: pointer;">
					<c:forEach var="maplist" items="${eppJobScheduleForm.dayList}" varStatus="status1">
						<form:option value="${maplist.value}" label="${maplist.label }"/>
					</c:forEach>
				   	</form:select>
					</span></td>
				</tr>
				<tr id="yearlyMonth">
					<td width="15%" style="padding: 0 0 5px 10px;"><span>Month: </span>
					</td>
					<td style="padding: 0 0 5px 10px;"><span>
						<form:select id="freqYearlyMonth" path="freqYearlyMonth" style="cursor: pointer;">
						<c:forEach var="maplist" items="${eppJobScheduleForm.monthList}" varStatus="status1">
							<form:option value="${maplist.value}" label="${maplist.label }"/>
						</c:forEach>
					   	</form:select>
					</span></td>
				</tr>
				<tr id="yearlyDayOfMonth">
					<td width="15%" style="padding: 0 0 5px 10px;"><span>Day of Month: </span>
					</td>
					<td style="padding: 0 0 5px 10px;"><span>
					<form:select id="freqYearlyDayOfMonth" path="freqYearlyDayOfMonth" style="cursor: pointer;">
						<c:forEach var="maplist" items="${eppJobScheduleForm.yearDayList}" varStatus="status1">
							<form:option value="${maplist.value}" label="${maplist.label }"/>
						</c:forEach>
					</form:select>
					</span></td>
				</tr>
				<tr id="repeatCount">
					<td width="15%" style="padding: 0 0 5px 10px;"><span> No of Repeats: </span>
					</td>
					<td style="padding: 0 0 5px 10px;"><span>
						<form:input type="text" name="freqRepeatIntervalCount"  path="freqRepeatIntervalCount" size="10" maxlength="10" onkeypress='return event.charCode >= 48 && event.charCode <= 57'/>
					</span></td>
				</tr>
				<tr id="repeatIntervalSecond">
					<td width="15%" style="padding: 0 0 5px 10px;"><span>Interval Seconds: </span>
					</td>
					<td style="padding: 0 0 5px 10px;"><span>
						<form:input name="freqRepeatIntervalSecond" path="freqRepeatIntervalSecond" maxlength="20" size="20"  onkeypress='return event.charCode >= 48 && event.charCode <= 57'></form:input>
					</span></td>
				</tr>
				<tr>
					<td colspan="2" style="padding-bottom: 5px;" />
				</tr>
			</table>
			</div>-->
			<!--<div class="displayTag" style="background-image:url('<%--=request.getContextPath() --%>/resources/images/head.png');background-repeat: repeat-x; margin-bottom: 5px;">
			<table  style="width:100%;">
				<tr>
					<td width="15%" style="padding: 10px 5px 5px 10px;"><span>Start Date: </span>
					</td>
					<td width="35%" style="padding: 10px 5px 5px 10px; text-align: left;"> <span>
						<form:input class="pickDateTimeForLocale" style="cursor: pointer;" path="durationStartDate" type="text" id="durationStartDate" value="${eppJobScheduleForm.durationStartDate}" size="10"	readonly="true" />
					</span></td>
					<td id="hiddenTd" colspan="2" display="none" />
					<td width="15%" id="specifyEndDate" style="padding: 10px 5px 5px 10px;"><span>Specify End Date: </span>
					</td>
					<td id="specifyEndDateYesNo" style="padding: 10px 5px 5px 10px;"><span>
					<c:if test="${eppJobScheduleForm.durationEndDateEnabled == 'No'}">
						<form:radiobutton id="endDateYes" path="durationEndDateEnabled" value="Yes" style="cursor: pointer;" />
							Yes&nbsp;
						<form:radiobutton id="endDateNo" path="durationEndDateEnabled" value="No" checked="true" style="cursor: pointer;"/>
							No
					</c:if>
					<c:if test="${empty eppJobScheduleForm.durationEndDateEnabled or eppJobScheduleForm.durationEndDateEnabled == 'Yes' }">
						<form:radiobutton id="endDateYes" path="durationEndDateEnabled" value="Yes"  checked="true" style="cursor: pointer;"/>
							Yes&nbsp;
						<form:radiobutton id="endDateNo" path="durationEndDateEnabled" value="No" style="cursor: pointer;"/>
							No
					</c:if>
					</span></td>
				</tr>
				<tr id="endDate">
					<td width="15%" style="padding: 0 0 5px 10px;"><span>End Date: </span>
					</td>
					<td colspan="4" style="padding: 0 0 5px 10px;"><span>
						<form:input class="pickDateTimeForLocale" style="cursor: pointer;" path="durationEndDate" type="text" id="durationEndDate" value="${eppJobScheduleForm.durationEndDate}" size="10"	readonly="true" />
					</span></td>
				</tr>
				<tr>
					<td colspan="2" style="padding-bottom: 5px;" />
				</tr>
			</table>
			</div>-->

		<!--<div id="addbutton" class="displayTag" style="background-image:url('<%--=request.getContextPath()--%>/resources/images/head.png');background-repeat: repeat-x;height: 50px;margin-top: 5px;">
		<table width="100%" height="46px">
				<tr>
					<td style="text-align: right; padding-right: 10px;"><span>
						<c:if test="${eppJobScheduleForm.mode == 'AMEND'}">
							<input type="button" id="amendAct" class="button_grey" style="width:100px; height: 25px;" value="Amend" />
							<input type="button" id="delAct" class="button_grey" style="width:100px; height: 25px;" value="Delete Job" />
						</c:if> 
						<c:if test="${eppJobScheduleForm.mode == 'ADD'}">
							<input type="button" id="saveAct" class="button_grey" style="width:100px; height: 25px;" value="Save Schedule" />
						</c:if> 
						<input type="button" id="resetAct" class="button_grey" style="width:100px; height: 25px;" value="Reset" />
						<input type="button" id="calAct" class="button_grey" style="width:100px; height: 25px;" value="Cancel" />
						</span></td>
				</tr>
			</table>
		</div>-->
		</div>
		</div>
		</div>
</div>
</form:form>
<div id="dialog-confirm"></div>
<div id="dialog-error"></div>
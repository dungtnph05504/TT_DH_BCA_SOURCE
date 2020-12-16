/** Do not change this section, preload javascript function here **/
$(document).ready(function() {

	var windowWidth = document.documentElement.clientWidth;
	var menuWidth = $("#menu > ul > li").length * 120;
	//alert(menuWidth);
	//alert(windowWidth);
	$.datepicker.regional["vi-VN"] =
	{
		closeText: "Đóng",
		prevText: "Trước",
		nextText: "Sau",
		currentText: "Hôm nay",
		monthNames: ["tháng 1", "tháng 2", "tháng 3", "tháng 4", "tháng 5", "tháng 6", "tháng 7", "tháng 8", "tháng 9", "tháng 10", "tháng 11", "tháng 12"],
		monthNamesShort: ["tháng 1", "tháng 2", "tháng 3", "tháng 4", "tháng 5", "tháng 6", "tháng 7", "tháng 8", "tháng 9", "tháng 10", "tháng 11", "tháng 12"],
		dayNames: ["Chủ nhật", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy"],
		dayNamesShort: ["CN", "Hai", "Ba", "Tư", "Năm", "Sáu", "Bảy"],
		dayNamesMin: ["CN", "T2", "T3", "T4", "T5", "T6", "T7"],
		weekHeader: "Tuần",
		dateFormat: "dd/mm/yy",
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: ""
	};

	$.datepicker.setDefaults($.datepicker.regional["vi-VN"]);
	
	$('.datepicker').datepicker();

	if (menuWidth > (windowWidth)) {
		$(".content").css("width", windowWidth - 40);
		$("#divider").css("width", menuWidth);
		$("#shadow-container").css("width", menuWidth + 100);
	}
	if (menuWidth < (windowWidth)) {
		$("#shadow-container").css("width", windowWidth - 40);
	}

	$(".defaultText").focus(function(srcc) {
		if ($(this).val() == $(this)[0].title) {
			$(this).removeClass("defaultTextActive");
			$(this).val("");
		}
	});

	$(".defaultText").blur(function() {
		if ($(this).val() == "") {
			$(this).addClass("defaultTextActive");
			$(this).val($(this)[0].title);
		}
	});

	$(".defaultText").blur();

	/** to set current tab on menu based on existing module**/
	//alert(document.forms[0].module.value);
	if (menuWidth >= 0) {
		$('#menu').removeClass('displayOff').addClass('displayOn');
		$('#dividerRed').removeClass('displayOff').addClass('displayOn');
	}

	//Tools.bindButtonClickEvent();
	$("input").keypress(function(evt) {
		//Deterime where our character code is coming from within the event
		var charCode = evt.charCode || evt.keyCode;
		//			alert(charCode);

		if (charCode == 13) { //Enter key's keycode
			return false;
		}
	});

	// Display waiting when form submit
	$('form').submit(function() {
		Tools.displayWait();
	});
	/*
	$("form").bind("keypress", function(e) {
	    if (e.keyCode == 13) return false;
	});*/

});

/** Customise function from here onward **/
function setDecision(flag) {
	document.forms[0].decisionFlag.value = flag;
}

//To use for popup window
//Based on windName to decide on window size
function popupWindow(url, windName) {
	var width; //window width
	var height; //window height
	var left; //window left position	
	var top; //window top position	

	if (windName == "cardHistory") {
		width = 1050;
		height = 300;
	} else if (windName == "biodata") {
		width = 1050;
		height = 450;
	} else if (windName == "cardTran") {
		width = 1200;
		height = 300;
	} else if (windName == "proxy") {
		width = 850;
		height = 480;
	}

	left = (screen.width - width) / 2;
	top = (screen.height - height) / 2;

	newWind = window.open(url, windName, "width=" + width + ",height=" + height
			+ ",top=" + top + ",left=" + left + ",toolbar=0,scrollbars=1");

	if (window.focus)
		newWind.focus();
}

//added for html
function triggerChangeField(txtfieldId, spnfieldId) {
	var txtBox = document.getElementById(txtfieldId);
	if (txtBox.disabled) {
		txtBox.disabled = false;
	} else {
		txtBox.disabled = true;
	}
	if (spnfieldId) {
		var spanBox = document.getElementById(spnfieldId);
		if (!txtBox.disabled) {
			//spanBox.style.display="inline";
		} else {
			spanBox.style.display = "none";
		}
	}
}

//move from replacement-lost-page2.html
//To display the collection date for the selected VIP indicator----------------------------------------------------------------------
//document.getElementById("dateICCollect").value = ... only works in IE
function displayCollectionDate(value) {
	var time = new Date();
	var datestr = getDateStr(time);
	// A - 1 MONTH
	if (value == 'A') {
		datestr = getDateStr(time, -1, 1);
		$('input[id$=dateICCollect.field]').val('' + datestr);
		// B - 3 MONTHS
	} else if (value == 'B') {
		datestr = getDateStr(time, -1, 3);
		$('input[id$=dateICCollect.field]').val('' + datestr);
		// Y - ON THE SPOT
	} else if (value == 'Y') {
		$('input[id$=dateICCollect.field]').val('' + datestr);
	} else if (value == 'C') {
		datestr = getDateStr(time, 14);
		$('input[id$=dateICCollect.field]').val('' + datestr);
	} else if (value == 'S') {
		datestr = getDateStr(time, 21);
		$('input[id$=dateICCollect.field]').val('' + datestr);
		// empty string
	} else if (value == '') {
		$('input[id$=dateICCollect.field]').val('');
	} else {
		datestr = getDateStr(time);
		$('input[id$=dateICCollect.field]').val('' + datestr);
	}
}

function getDateStr(dateObj, addedDay, addedMth) {
	var month = dateObj.getMonth() + 1;
	var date = dateObj.getDate();
	var year = dateObj.getYear();
	if (addedDay && addedDay != -1) {
		date = date + addedDay;
	}
	if (addedMth) {
		month = month + addedMth;
	}
	if (date > 29) {
		date = date - 29;
		month = month + 1;
	}
	if (month > 12) {
		month = month - 12;
		year = year + 1;
	}
	var datestr = '';
	datestr += filledZero(date, 2) + '/' + filledZero(month, 2) + '/'
			+ (year + 1900);
	return datestr;
}

function getCurrentDisplayDate() {
	var today = '';
	var objToday = new Date(), weekday = new Array('Sun', 'Mon', 'Tue', 'Wed',
			'Thu', 'Fri', 'Sat'), dayOfWeek = weekday[objToday.getDay()], domEnder = new Array(
			'th', 'st', 'nd', 'rd', 'th', 'th', 'th', 'th', 'th', 'th'),
	// domEnder = new Array( '', '', '', '', '', '', '', '', '', '' ),

	//dayOfMonth = today + (objToday.getDate() < 10) ? '' + objToday.getDate() + domEnder[objToday.getDate()] : objToday.getDate(),// + domEnder[parseFloat(("" + objToday.getDate()).substr(("" + objToday.getDate()).length - 1))],
	dayOfMonth = today + objToday.getDate(), // + domEnder[parseFloat(("" + objToday.getDate()).substr(("" + objToday.getDate()).length - 1))],

	months = new Array('Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug',
			'Sep', 'Oct', 'Nov', 'Dec'), curMonth = months[objToday.getMonth()], curYear = objToday
			.getFullYear(), curHour = objToday.getHours() > 12 ? objToday
			.getHours() - 12 : (objToday.getHours() < 10 ? "0"
			+ objToday.getHours() : objToday.getHours()), curMinute = objToday
			.getMinutes() < 10 ? "0" + objToday.getMinutes() : objToday
			.getMinutes(), curSeconds = objToday.getSeconds() < 10 ? "0"
			+ objToday.getSeconds() : objToday.getSeconds(), curMeridiem = objToday
			.getHours() > 12 ? "PM" : "AM";
	today = " " + dayOfWeek + ", " + dayOfMonth + " " + curMonth + " "
			+ curYear + " " + curHour + ":" + curMinute + ":" + curSeconds
			+ curMeridiem;
	return today;
}

function filledZero(number, noOfDigit) {
	var maxLimit = Math.pow(10, noOfDigit - 1);
	var str = '';
	if (number < maxLimit) {
		for ( var i = 0; i < noOfDigit - 1; i++) {
			str += '0';
		}
		str += number;
	} else {
		str += number;
	}
	return str;
}

function show(layer_ref) {
	var state = 'block';
	if (document.all) { //IS IE 4 or 5 (or 6 beta)
		eval("document.all." + layer_ref + ".style.display = state");
	}
	if (document.layers) { //IS NETSCAPE 4 or below 
		document.layers[layer_ref].display = state;
	}
	if (document.getElementById && !document.all) {
		hza = document.getElementById(layer_ref);
		hza.style.display = state;
	}
}

function hide(layer_ref) {
	var state = 'none';
	if (document.all) { //IS IE 4 or 5 (or 6 beta) 
		eval("document.all." + layer_ref + ".style.display = state");
	}
	if (document.layers) { //IS NETSCAPE 4 or below 
		document.layers[layer_ref].display = state;
	}
	if (document.getElementById && !document.all) {
		hza = document.getElementById(layer_ref);
		hza.style.display = state;
	}
}
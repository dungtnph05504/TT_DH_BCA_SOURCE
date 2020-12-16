$(document).ajaxComplete(function (event, request, settings) {
    if (request.status === 400)
        window.location.href = "/Sys/Login";
});

$(document).ajaxError(function (event, jqxhr, settings, exception) {
    if (jqxhr.status === 400) {
        window.location.href = '/Sys/Login';
    }
});

//Kiểm tra chuỗi có chứa ký tự đặc biệt, trả về kiểu bool: true/false
function containsSpecialCharacters(str) {
    var regex = /[ !@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/g;
    return regex.test(str);
}

//Kiểm tra định dạng Email, trả về kiểu bool: true/false
function IsEmail(email) {
    var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if (!regex.test(email)) {
        return false;
    } else {
        return true;
    }
}

$("#menu-toggle").click(function (e) {
    e.preventDefault();
    $("#wrapper").toggleClass("toggled");
    $("#sb-on-off").toggleClass("glyphicon-forward glyphicon-backward");
});

function GetPageSize() {
    var result = 0;

    $.ajax({
        url: '/Login/GetPageSize',
        method: 'GET',
        async: false,
        success: function (size) {
            result = parseInt(size);
        }
    });

    return result;
}

function GetDateYYYYMMDD(ddMMyyyy) {
    try {
        if (ddMMyyyy) {
            var date = ddMMyyyy.split('/');
            var yyyy = date[2];
            var MM = date[1];
            var dd = date[0];
            return yyyy + MM + dd;
        }
        else
            return "";
    } catch (e) {
        return ddMMyyyy;
    }
}

function GetDateDDMMYYYY(yyyyMMdd) {
    try {
        if (yyyyMMdd) {
            var yyyy = yyyyMMdd.substring(0, 4);
            var MM = yyyyMMdd.substring(4, 6);
            var dd = yyyyMMdd.substring(6, 8);
            return dd + '/' + MM + '/' + yyyy;
        }
        else
            return "";
    } catch (e) {
        return yyyyMMdd;
    }
}

//Lấy định dạng ngày tháng năm (yyyy-mm-dd) truyền lên API phân hệ trong
function GetDateTimeFormat(dateInput) {
    if (dateInput instanceof Date) {
        var date = new Date(dateInput);
        if (!isNaN(date)) {
            var dd = date.getDate();
            var mm = date.getMonth() + 1;
            var yyyy = date.getFullYear();

            if (dd < 10) {
                dd = '0' + dd;
            }

            if (mm < 10) {
                mm = '0' + mm;
            }

            return yyyy + '-' + mm + '-' + dd;
        } else if (dateInput.split('/').length > 0) {
            var arrDate = dateInput.split('/');
            return arrDate[2] + '-' + arrDate[1] + '-' + arrDate[0];
        } else {
            return "2017-01-01";
        }
    } else {
        var arrDate = dateInput.split('/');
        return arrDate[2] + '-' + arrDate[1] + '-' + arrDate[0];
    }
}

//Lấy định dạng ngày tháng năm (dd/mm/yyyy) truyền lên API phân hệ trong
function GetDateTimeFormatSIM(dateInput) {
    if (dateInput instanceof Date) {
        var date = new Date(dateInput);
        if (!isNaN(date)) {
            var dd = date.getDate();
            var mm = date.getMonth() + 1;
            var yyyy = date.getFullYear();

            if (dd < 10) {
                dd = '0' + dd;
            }

            if (mm < 10) {
                mm = '0' + mm;
            }

            return dd + '/' + mm + '/' + yyyy;
        } else if (dateInput.split('/').length > 0) {
            var arrDate = dateInput.split('/');
            return arrDate[0] + '/' + arrDate[1] + '/' + arrDate[2];
        } else {
            return "01/01/2017";
        }
    } else {
        var arrDate = dateInput.split('/');
        return arrDate[0] + '/' + arrDate[1] + '/' + arrDate[2];
    }
}

//Lấy định dạng ngày tháng năm (dd/mm/yyyy) truyền lên datetime picker
function GetDateTimeToday() {
    var date = new Date();
    var dd = date.getDate();
    var mm = date.getMonth() + 1;
    var yyyy = date.getFullYear();

    if (dd < 10) {
        dd = '0' + dd;
    }

    if (mm < 10) {
        mm = '0' + mm;
    }

    return dd + '/' + mm + '/' + yyyy;
}

function loadIframe(url) {
    var ifr = $('<iframe/>', {
        id: 'AdminFrame',
        src: url,
        frameborder: 0,
        style: 'display:none;zoom:0.60;width:99.6%;height:700px',
        load: function () {
            $("#loadingaj").hide();
            $(this).show();
        }
    });
    $('#WrapIframe').html(ifr);
}

function ConvertFingerBMPToWSQ(ws_urlbase, inImgStr) {
    jQuery.support.cors = true;
    var resultString = "";
    var input_format = 1;
    var output_format = 9;
    var input_cb = 24;
    var input_w = 512;
    var input_h = 512;
    var output_cb = 24;
    var output_w = 512;
    var output_h = 512;
    var postData = {
        InImgBuffer: inImgStr,
        InImgBufSize: inImgStr.length,
        InImgFormat: input_format,
        InImgColorbit: input_cb,
        InImgWidth: input_w,
        InImgHeight: input_h,
        CvtImgFormat: output_format,
        CvtImgColorbit: output_cb,
        CvtImgWidth: output_w,
        CvtImgHeight: output_h
    };
    var urlString = ws_urlbase + "SPID_ImageConvert";
    $.ajax({
        type: 'POST',
        async: false,
        cache: false,
        url: urlString,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: true,
        data: JSON.stringify(postData),
        success: function (response) {
            var cvt_result = response;
            resultString = cvt_result.CvtImgBuffer;
        },

        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
    return resultString;
}

function ConvertFingerWSQToFMP5(ws_urlbase, inImgStr) {
    jQuery.support.cors = true;
    var resultString = "";
    var input_format = 9;
    //creating json multiple object
    var ImageData = {
        ImgBuffer: inImgStr,
        ImgBufSize: inImgStr.length,
        ImgFormat: input_format,    // IMAGE FORMAT: 0-RAW, 1-BMP, 9-WSQ
        ImgColorbit: 8,
        ImgWidth: 512,
        ImgHeight: 512,
        ImgDPI: 500
    };
    var MinutiaData = {
        MnuFormat: 4096    // MINUTIA FORMAT: 4096-FMP5, 4105-PC2
    };
    var postData = {
        img: ImageData,
        mnu: MinutiaData
    };
    var urlString = ws_urlbase + "SPID_FPCreateMinutia";
    $.ajax({
        type: 'POST',
        cache: false,
        async: false,
        url: urlString,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: true,
        data: JSON.stringify(postData),

        success: function (response) {
            var fpmnu_result = response;
            resultString = fpmnu_result.MnuBuffer;
        },

        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
            alert(textStatus);
        }
    });
    return resultString;
}


function ConvertFingerWSQToBMP(ws_urlbase, inImgStr) {

    jQuery.support.cors = true;
    var resultString = "";
    var input_format = 9;
    var output_format = 1;
    var input_cb = 8;
    var input_w = 512;
    var input_h = 512;
    var output_cb = 8;
    var output_w = 512;
    var output_h = 512;
    var postData = {
        InImgBuffer: inImgStr,
        InImgBufSize: inImgStr.length,
        InImgFormat: input_format,
        InImgColorbit: input_cb,
        InImgWidth: input_w,
        InImgHeight: input_h,
        CvtImgFormat: output_format,
        CvtImgColorbit: output_cb,
        CvtImgWidth: output_w,
        CvtImgHeight: output_h
    };
    var urlString = ws_urlbase + "SPID_ImageConvert";
    $.ajax({
        type: 'POST',
        cache: false,
        async: false,
        url: urlString,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: true,
        data: JSON.stringify(postData),
        success: function (response) {
            var cvt_result = response;
            resultString = cvt_result.CvtImgBuffer;
        },

        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
    return resultString;
}

function GetDateTime14(dateTime14) {
    try {
        if (dateTime14) {
            var yyyy = dateTime14.substring(0, 4);
            var MM = dateTime14.substring(4, 6);
            var dd = dateTime14.substring(6, 8);
            var hh = dateTime14.substring(8, 10);
            var mm = dateTime14.substring(10, 12);
            var ss = dateTime14.substring(12, 14);
            return dd + '/' + MM + '/' + yyyy + ' ' + hh + ':' + mm + ':' + ss;
        }
        else
            return "";
    } catch (e) {
        return dateTime14;
    }
}

//require numeral.min.js
function formartCurrency(value) {
    var currVal = numeral(value).format('0,0');
    try {
        var roundValue = roundNumber(currVal);
        currVal = numeral(roundValue).format('0,0');
        return currVal;
    } catch (e) {
        return currVal;
    }
}

//require numeral.min.js
function roundNumber(value) {
    try {
        var currVal = value;
        var arr = value.split(',');
        if (arr.length > 1) {
            var minCurrcy = arr[arr.length - 1];
            var beforeDot = '';
            for (var i = 0; i < arr.length; i++) {
                if (i != arr.length - 1)
                    beforeDot += arr[i];
            }
            var currValue = parseFloat(beforeDot + '.' + minCurrcy);
            var roundValue = Math.round(currValue) * 1000;
            currVal = roundValue;
        }
        return currVal;
    } catch (e) {
        return currVal;
    }
}

/*Json response must contain: status= '00' || '01'*/
function SetMessage(data) {
    var code = data["status"];
    if (code == "00")
        toastr["error"](data["message"]);
    else {
        toastr["success"](data["message"]);
    }
}

function printReport(url) {
    var ifr = $('<div/>', {
        id: 'AdminFrame',
        src: url,
        frameborder: 0,
        style: 'display:none;zoom:0.60;width:99.6%;height:700px',
        load: function () {
        }
    });



    var gridContent = "";

    var newWindow = window.open('', '', 'width=800, height=500'),
    document = newWindow.document.open(),

    pageContent =
        '<!DOCTYPE html>\n' +
        '<html>\n' +
        '<head>\n' +
        '<meta charset="utf-8" />\n' +
        '</head>\n' +
        '<body>\n' + gridContent + '\n</body>\n</html>';
    document.write(ifr);
    document.close();
    newWindow.print();
};

function ImageConvert(ws_urlbase, inImgStr) {
    jQuery.support.cors = true;
    var resultString;
    var input_format = 3;
    var output_format = 6;
    var input_cb = 24;
    var input_w = 480;
    var input_h = 640;
    var output_cb = 24;
    var output_w = 480;
    var output_h = 640;
    var postData = {
        InImgBuffer: inImgStr,
        InImgBufSize: inImgStr.length,
        InImgFormat: input_format,
        InImgColorbit: input_cb,
        InImgWidth: input_w,
        InImgHeight: input_h,
        CvtImgFormat: output_format,
        CvtImgColorbit: output_cb,
        CvtImgWidth: output_w,
        CvtImgHeight: output_h
    };
    var urlString = ws_urlbase + "SPID_ImageConvert";
    $.ajax({
        type: 'POST',
        cache: false,
        url: urlString,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        crossDomain: true,
        data: JSON.stringify(postData),
        success: function (response) {
            var cvt_result = response;
            resultString = cvt_result.CvtImgBuffer;
            // console.log(resultString);
            return resultString;
        },

        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
            return resultString;
        }
    });

    return resultString;
}

function validate(form) {
    var err = true
    $("form#" + form + " input[type='text'], form#" + form + " select, form#" + form + " textarea,form#" + form + " input[type='file']").each(function () {
        if ($(this).attr("validate")) {
            if ($(this).hasClass("combobox")) {
                if ($(this).next("span.combo").find("input[type='hidden']").val() === "") {
                    alert($(this).closest("div").prev("label").text().replace('*', '') + 'không được để trống');
                    $(this).focus();
                    err = false
                    return false;
                }
            } else if ($(this).val() === "") {
                alert($(this).closest("div").prev("label").text().replace('*', '') + 'không được để trống');
                $(this).focus();
                err = false
                return false;
            }

        }
    });
    return err;
}




function validate_tk(form) {
    var err = true;
    var rad = false
    $("form#" + form + " input[type='text'], form#" + form + " select, form#" + form + " textarea, form#" + form + " input[type='radio'], form#" + form + " input[type='file']").each(function () {
        if ($(this).hasClass("epp-radio")) {
            var radio = $(this).attr("name");

            if ($('input[name=' + radio + ']:checked').length <= 0) {
                if (!rad) {
                    gAlert($(this).closest("div").prev("label").text().replace('*', '') + 'không được để trống');
                }
                rad = true;
                err = false;
                return false;
            }
        }
        if ($(this).attr("validate")) {
            if ($(this).hasClass("combobox")) {
                if ($(this).next("span.combo").find("input[type='hidden']").val() === "") {
                    gAlert($(this).closest("div").prev("label").text().replace('*', '') + 'không được để trống');
                    $(this).focus();
                    err = false
                    return false;
                }
            } else if ($(this).val() === "") {
                gAlert($(this).closest("div").prev("label").text().replace('*', '') + 'không được để trống');
                $(this).focus();
                err = false
                return false;
            }

        }
    });
    return err;
}

function CheckDateSumbitForm(index, date, phone) {
    var check = true;
    switch (index) {
        case "0":
            if (!isDate(date)) {
                gAlert('Định dạng ngày sinh không chính xác!');
                check = false;
                return false;
            }
            if (!isPhoneNumber(phone)) {
                gAlert('Định dạng số điện thoại không chính xác!');
                check = false;
                return false;
            }
            return check;
            break;
        case "1":
            if (!isDateMoth(date)) {
                gAlert('Định dạng ngày sinh không chính xác!');
                check = false;
                return false;
            }
            if (!isPhoneNumber(phone)) {
                gAlert('Định dạng số điện thoại không chính xác!');
                check = false;
                return false;
            }
            return check;
            break;
        case "2":
            if (!isPhoneNumber(phone)) {
                gAlert('Định dạng số điện thoại không chính xác!');
                check = false;
                return false;
            }
            return check;
            break;
    }
}

function checkform(form, ns, phone, idnumber, childOne, chidTwo, checkfather, checkmother, checkspouse, dateF, dateM) {
    var data = [];
    var err = true;
    var rad = false;
    $("form#" + form + " input[type='text'], form#" + form + " select, form#" + form + " textarea, form#" + form + " input[type='radio'], form#" + form + " input[type='file']").each(function () {
        if ($(this).attr("validate")) {
            if ($(this).hasClass("epp-radio")) {
                var radio = $(this).attr("name");
                if ($('input[name=' + radio + ']:checked').length <= 0) {
                    if (!rad) {
                        var result = $(this).closest("div").prev("label").text().replace('*', '') + 'không được để trống';
                        data.push(result);
                    }
                    $(this).removeClass('e-validate-radio');
                    err = false;
                    rad = true;
                } else {
                    $(this).addClass('e-validate-radio');
                }
            }
            if ($(this).hasClass("combobox")) {
                if ($(this).next("span.combo").find("input[type='hidden']").val() === "") {

                    $(this).next("span.combo").find("input[type='hidden']").addClass('e-validate');
                } else {
                    $(this).next("span.combo").find("input[type='hidden']").removeClass('e-validate');
                }
            } else if ($(this).val() === "") {
                var result = $(this).closest("div").prev("label").text().replace('*', '') + 'không được để trống';
                data.push(result);
                $(this).addClass('e-validate');
                err = false;
            }
            else {
                $(this).removeClass('e-validate');
            }

        }
    });
    

    if (!isPhoneNumber(phone)) {
        data.push("Định dạng số điện thoại không chính xác");
        err = false;
    }
    if (checkfather) {
        $("form#" + form + " input[type='text'], form#" + form + " select, form#" + form + " textarea, form#" + form + " input[type='radio'], form#" + form + " input[type='checkbox']").each(function () {
            if ($(this).attr("father-validate")) {
                if ($(this).hasClass("epp-radio")) {
                    var radio = $(this).attr("name");
                    if ($('input[name=' + radio + ']:checked').length <= 0) {
                        if (!rad) {
                            var textString = $(this).closest("div").prev("label").text().replace('*', '');
                            alert(12);
                            if (textString.indexOf('Cha') == -1) {
                                var result = '12. ' + $(this).closest("div").prev("label").text().replace('*', '') + ' Cha không được để trống';
                                data.push(result);
                            } else {
                                var result = $(this).closest("div").prev("label").text().replace('*', '') + ' không được để trống';
                                data.push(result);
                            }
                        }
                        $(this).removeClass('e-validate-radio');
                        err = false;
                        rad = true;
                    } else {
                        $(this).addClass('e-validate-radio');
                    }
                }
                if ($(this).hasClass("combobox")) {
                    if ($(this).next("span.combo").find("input[type='hidden']").val() === "") {

                        $(this).next("span.combo").find("input[type='hidden']").addClass('e-validate');
                    } else {
                        $(this).next("span.combo").find("input[type='hidden']").removeClass('e-validate');
                    }
                } else if ($(this).val() === "") {
                    var textString = $(this).closest("div").prev("label").text().replace('*', '');
                    if (textString.indexOf('Cha') == -1) {
                        var result = '12. ' + $(this).closest("div").prev("label").text().replace('*', '') + ' Cha không được để trống';
                        data.push(result);
                    } else {
                        var result =  $(this).closest("div").prev("label").text().replace('*', '') + ' không được để trống';
                        data.push(result);
                    }
                    $(this).addClass('e-validate');
                    err = false;
                }
                else {
                    $(this).removeClass('e-validate');
                }

            }
        });
    } else {
        $("form#" + form + " input[type='text'], form#" + form + " select, form#" + form + " textarea, form#" + form + " input[type='radio'], form#" + form + " input[type='checkbox']").each(function () {
            if ($(this).attr("father-validate")) {
                $(this).removeClass('e-validate');
            }
        })
    }
    if (checkmother) {
        $("form#" + form + " input[type='text'], form#" + form + " select, form#" + form + " textarea, form#" + form + " input[type='radio'], form#" + form + " input[type='checkbox']").each(function () {
            if ($(this).attr("mother-validate")) {
                if ($(this).hasClass("epp-radio")) {
                    var radio = $(this).attr("name");
                    if ($('input[name=' + radio + ']:checked').length <= 0) {
                        if (!rad) {
                            var textString = $(this).closest("div").prev("label").text().replace('*', '');
                            if (textString.indexOf('Mẹ') == -1) {
                                var result = '12. ' + $(this).closest("div").prev("label").text().replace('*', '') + ' Mẹ không được để trống';
                                data.push(result);
                            } else {
                                var result = '12. ' + $(this).closest("div").prev("label").text().replace('*', '') + ' không được để trống';
                                data.push(result);
                            }
                        }
                        $(this).removeClass('e-validate-radio');
                        err = false;
                        rad = true;
                    } else {
                        $(this).addClass('e-validate-radio');
                    }
                }
                if ($(this).hasClass("combobox")) {
                    if ($(this).next("span.combo").find("input[type='hidden']").val() === "") {

                        $(this).next("span.combo").find("input[type='hidden']").addClass('e-validate');
                    } else {
                        $(this).next("span.combo").find("input[type='hidden']").removeClass('e-validate');
                    }
                } else if ($(this).val() === "") {
                    var textString = $(this).closest("div").prev("label").text().replace('*', '');
                    if (textString.indexOf('Mẹ') == -1) {
                        var result = '12. ' + $(this).closest("div").prev("label").text().replace('*', '') + ' Mẹ không được để trống';
                        data.push(result);
                    } else {
                        var result = '12. ' + $(this).closest("div").prev("label").text().replace('*', '') + ' không được để trống';
                        data.push(result);
                    }
                    $(this).addClass('e-validate');
                    err = false;
                }
                else {
                    $(this).removeClass('e-validate');
                }

            }
        });
    } else {
        $("form#" + form + " input[type='text'], form#" + form + " select, form#" + form + " textarea, form#" + form + " input[type='radio'], form#" + form + " input[type='checkbox']").each(function () {
            if ($(this).attr("mother-validate")) {
                $(this).removeClass('e-validate');
            }
        })
    }
    if (checkspouse) {
        $("form#" + form + " input[type='text'], form#" + form + " select, form#" + form + " textarea, form#" + form + " input[type='radio'], form#" + form + " input[type='file']").each(function () {
            if ($(this).attr("spouse-validate")) {
                if ($(this).hasClass("epp-radio")) {
                    var radio = $(this).attr("name");
                    if ($('input[name=' + radio + ']:checked').length <= 0) {
                        if (!rad) {
                            var textString = $(this).closest("div").prev("label").text().replace('*', '');
                            if (textString.indexOf('Vợ/Chồng') == -1) {
                                var result = '12. ' + $(this).closest("div").prev("label").text().replace('*', '') + ' Vợ/Chồng không được để trống';
                                data.push(result);
                            } else {
                                var result = '12. ' + $(this).closest("div").prev("label").text().replace('*', '') + ' không được để trống';
                                data.push(result);
                            }
                        }
                        $(this).removeClass('e-validate-radio');
                        err = false;
                        rad = true;
                    } else {
                        $(this).addClass('e-validate-radio');
                    }
                }
                if ($(this).hasClass("combobox")) {
                    if ($(this).next("span.combo").find("input[type='hidden']").val() === "") {

                        $(this).next("span.combo").find("input[type='hidden']").addClass('e-validate');
                    } else {
                        $(this).next("span.combo").find("input[type='hidden']").removeClass('e-validate');
                    }
                } else if ($(this).val() === "") {
                    var textString = $(this).closest("div").prev("label").text().replace('*', '');
                    if (textString.indexOf('Vợ/Chồng') == -1) {
                        var result = '12. ' + $(this).closest("div").prev("label").text().replace('*', '') + ' Vợ/Chồng không được để trống';
                        data.push(result);
                    } else {
                        var result = '12. ' + $(this).closest("div").prev("label").text().replace('*', '') + ' không được để trống';
                        data.push(result);
                    }
                    $(this).addClass('e-validate');
                    err = false;
                }
                else {
                    $(this).removeClass('e-validate');
                }

            }
        });
    } else {
        $("form#" + form + " input[type='text'], form#" + form + " select, form#" + form + " textarea, form#" + form + " input[type='radio'], form#" + form + " input[type='file']").each(function () {
            if ($(this).attr("spouse-validate")) {
                $(this).removeClass('e-validate');
            }
        })
    }
    if (childOne) {
        $("form#" + form + " input[type='text'], form#" + form + " select, form#" + form + " textarea, form#" + form + " input[type='radio'], form#" + form + " input[type='file']").each(function () {
            if ($(this).attr("child-one-validate")) {
                if ($(this).hasClass("epp-radio")) {
                    var radio = $(this).attr("name");
                    if ($('input[name=' + radio + ']:checked').length <= 0) {
                        if (!rad) {
                            var result = '15. ' + $(this).closest("div").prev("label").text().replace('*', '') + ' con thứ nhất không được để trống';
                            data.push(result);
                        }
                        $(this).removeClass('e-validate-radio');
                        err = false;
                        rad = true;
                    } else {
                        $(this).addClass('e-validate-radio');
                    }
                }
                if ($(this).hasClass("combobox")) {
                    if ($(this).next("span.combo").find("input[type='hidden']").val() === "") {

                        $(this).next("span.combo").find("input[type='hidden']").addClass('e-validate');
                    } else {
                        $(this).next("span.combo").find("input[type='hidden']").removeClass('e-validate');
                    }
                } else if ($(this).val() === "") {
                    var result = '15. ' + $(this).closest("div").prev("label").text().replace('*', '') + ' con thứ nhất không được để trống';
                    data.push(result);
                    $(this).addClass('e-validate');
                    err = false;
                }
                else {
                    $(this).removeClass('e-validate');
                }

            }
        });
    } else {
        $("form#" + form + " input[type='text'], form#" + form + " select, form#" + form + " textarea, form#" + form + " input[type='radio'], form#" + form + " input[type='file']").each(function () {
            if ($(this).attr("child-one-validate")) {
                $(this).removeClass('e-validate');
                $(this).addClass('e-validate-radio');
            }
        })
    }
    if (chidTwo) {
        $("form#" + form + " input[type='text'], form#" + form + " select, form#" + form + " textarea, form#" + form + " input[type='radio'], form#" + form + " input[type='file']").each(function () {
            if ($(this).attr("child-two-validate")) {
                if ($(this).hasClass("epp-radio")) {
                    var radio = $(this).attr("name");
                    if ($('input[name=' + radio + ']:checked').length <= 0) {
                        if (!rad) {
                            var result = '16. ' + $(this).closest("div").prev("label").text().replace('*', '') + ' con thứ hai không được để trống';
                            data.push(result);
                        }
                        $(this).removeClass('e-validate-radio');
                        err = false;
                        rad = true;
                    } else {
                        $(this).addClass('e-validate-radio');
                    }
                }
                if ($(this).hasClass("combobox")) {
                    if ($(this).next("span.combo").find("input[type='hidden']").val() === "") {

                        $(this).next("span.combo").find("input[type='hidden']").addClass('e-validate');
                    } else {
                        $(this).next("span.combo").find("input[type='hidden']").removeClass('e-validate');
                    }
                } else if ($(this).val() === "") {
                    var result = '16. ' + $(this).closest("div").prev("label").text().replace('*', '') + ' con thứ hai không được để trống';
                    data.push(result);
                    $(this).addClass('e-validate');
                    err = false;
                }
                else {
                    $(this).removeClass('e-validate');
                }

            }
        });
    } else {
        $("form#" + form + " input[type='text'], form#" + form + " select, form#" + form + " textarea, form#" + form + " input[type='radio'], form#" + form + " input[type='file']").each(function () {
            if ($(this).attr("child-two-validate")) {
                $(this).removeClass('e-validate');
                $(this).addClass('e-validate-radio');
            }
        })
    }

    if (!ns) {
        data.push("Định dạng ngày sinh không chính xác");
        err = false;
    }
    if (idnumber != null && idnumber != undefined) {
        if (idnumber.length != 9 && idnumber.length != 12) {
            data.push("Số CMND/CCCD gồm 9 hoặc 12 chữ số");
            err = false;
        }
    }
    if (dateF == 1 || dateF == 2) {
        data.push("Định dạng ngày sinh Cha không chính xác");
        err = false;
    } else if (dateF == 3) {
        data.push("Ngày sinh Cha phải lớn hơn năm 1900");
        err = false;
    }
    if (dateM == 1 || dateM == 2) {
        data.push("Định dạng ngày sinh Mẹ không chính xác");
        err = false;
    } else if (dateM == 3) {
        data.push("Ngày sinh Mẹ phải lớn hơn năm 1900");
        err = false;
    }
    if (data.length > 0) {
        TAlert(data);
    }
    return err;
}

function isPhoneNumber(phone) {
    var check = true;
    if (phone == undefined)
        return false;
    if (phone.length < 9) {
        return false;
    } else if (phone[0] != "0") {
        return false;
    }
    return check;
}


function isDateMoth(txtDate) {
    var currVal = txtDate;
    if (currVal == '')
        return false;

    var rxDatePattern = /^(\d{1,2})(\/|-)(\d{4})$/;
    var dtArray = currVal.match(rxDatePattern); // is format OK?
    console.log(dtArray);
    if (dtArray == null)
        return false;
    var dtMonth = dtArray[1];
    var dtYear = dtArray[3];

    if (dtMonth < 1 || dtMonth > 12)
        return false;
    return true;
}

function isDate(txtDate) {
    var currVal = txtDate;
    if (currVal == '')
        return false;

    //Declare Regex  
    var rxDatePattern = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
    var dtArray = currVal.match(rxDatePattern); // is format OK?
    //var rxDatePatternMoth = /^(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
    //var dtArrayMoth = currVal.match(rxDatePatternMoth);
    console.log(dtArray);
    if (dtArray == null)
        return false;

    //Checks for dd/mm/yyyy format.
    var dtDay = dtArray[1];
    var dtMonth = dtArray[3];
    var dtYear = dtArray[5];

    if (dtMonth < 1 || dtMonth > 12)
        return false;
    else if (dtDay < 1 || dtDay > 31)
        return false;
    else if ((dtMonth == 4 || dtMonth == 6 || dtMonth == 9 || dtMonth == 11) && dtDay == 31)
        return false;
    else if (dtMonth == 2) {
        var isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
        if (dtDay > 29 || (dtDay == 29 && !isleap))
            return false;
    }

    return true;
}

function GetDate(date) {
    
    var result = "";
    if (date.length >= 8) {
        result = date.substring(6, 8) + "/" + date.substring(4, 6) + "/" + date.substring(0, 4);
    }
    return result;
}

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#Icon').attr('src', e.target.result);
            $('#Icon').show();
        }

        reader.readAsDataURL(input.files[0]);
    }
}

function CheckDateParent(date) {
    var check = 0;
    if (date !== undefined && date !== null) {
        if (date.length > 7) {
            if (!isDate(date)) {
                check = 1;
            }
        } else if (date.length > 4 && date.length <= 7) {
            if (!isDateMoth(date)) {
                //alert('Định dạng ngày sinh ' + title + ' không chính xác!');
                check = 2;
            }
        } else if (date.length <= 4) {
            var d = parseInt(date);
            if (d <= 1900) {
                //  alert('Năm sinh phải lớn hơn năm 1900');
                check = 3;
            }
        }
    }
    return check;
}

$(document).ready(function () {
    $('.datepicker').datetimepicker({
        useCurrent: false,
        locale: 'vi',
        format: 'DD/MM/YYYY',
    });

    $('.validate-number').keypress(function (evt) {
        var charCode = (evt.which) ? evt.which : event.keyCode
        if (charCode > 31 && (charCode < 48 || charCode > 57))
            return false;

        return true;
    });

    $('.e-table-fix-head').tableHeadFixer({ 'foot': true, 'head': true });
});


$(document).on('change', '.check-all', function () {
    if (this.checked)
        $(this).parents('table').find('.check-one').each(function () {
            if (!$(this).is(':disabled'))
                $(this).prop('checked', true);
        });
    else
        $(this).parents('table').find('.check-one').prop('checked', false);
})

$(document).on('change', 'input[type=checkbox]', function () {
    var checked = $('.check-one:checked');
    if (checked.length > 0)
        $('.btn-main').removeClass("disabled");
    else
        $('.btn-main').addClass("disabled");
})
(function (app) {
    app.controller('tiepnhancontroller', TiepNhanController);
    TiepNhanController.$inject = ['$scope','$filter', 'apiservices', '$rootScope', '$window', '$timeout', '$rootScope', 'dataperson'];
    function TiepNhanController($scope, $filter,  apiservices, $rootScope, $window, $timeout, $rootScope, dataperson) {

        var $seft = this;
        var tokhaiurl = "/ToKhai/"
        var captcha = "";
        $scope.checksumit = true;
        $scope.document = {
            IS_EPASSPORT: "Y",
            DATE_OF_DELIVERY: ''
        }

        $scope.person = [{
            DATE_OF_BIRTH: ""
        }]

        $scope.NgaySinh = "0";
        $('#ps-ngaysinh').mask("00/00/0000", { placeholder: "__/__/____" });
        $scope.document.IS_EPASSPORT = "Y";
        GetPlace();
        $seft.captchaCode = '';
        $seft.SubmitPerson = function (person, document, family) {

            if (!$seft.checked) {
                $seft.e_check = 'e-show-line';

            } else {
                $seft.e_check = 'e-hide-line';
                person[0].PLACE_OF_BIRTH_ID = person[0].PLACE_OF_BIRTH_ID == undefined ? null : person[0].PLACE_OF_BIRTH_ID.ID;
                person[0].PLACE_OF_ID_ISSUE_ID = person[0].PLACE_OF_ID_ISSUE_ID == undefined ? '' : person[0].PLACE_OF_ID_ISSUE_ID.ID;
                person[0].ETHNIC = person[0].ETHNIC == undefined ? null : person[0].ETHNIC.FLEX_VALUE;
                person[0].RELIGION = person[0].RELIGION == undefined ? null : person[0].RELIGION.FLEX_VALUE;
                person[0].TYPE_ = 'HS';
                document.TYPE_ = document.TYPE_ == undefined ? null : document.TYPE_.FLEX_VALUE;
                var RESIDENT_PLACE_NAME = person[0].RESIDENT_PLACE_ID == undefined ? null : person[0].RESIDENT_PLACE_ID.NAME;
                var RESIDENT_AREA_NAME = person[0].RESIDENT_AREA_ID == undefined ? null : person[0].RESIDENT_AREA_ID.NAME;
                person[0].RESIDENT_PLACE_ID = person[0].RESIDENT_PLACE_ID == undefined ? null : person[0].RESIDENT_PLACE_ID.ID;
                person[0].RESIDENT_AREA_ID = person[0].RESIDENT_AREA_ID == undefined ? null : person[0].RESIDENT_AREA_ID.ID;
                person[0].TMP_PLACE_ID = person[0].TMP_PLACE_ID == undefined ? null : person[0].TMP_PLACE_ID.ID;
                person[0].TMP_AREA_ID = person[0].TMP_AREA_ID == undefined ? null : person[0].TMP_AREA_ID.ID;
                if (family[0] != undefined) {
                    family[0].RELATIONSHIP = 'FATHER';
                    family[0].GENDER = 'MALE';
                }

                if (family[1] != undefined) {
                    family[1].RELATIONSHIP = 'MOTHER';
                    family[1].GENDER = 'FEMALE';
                }

                if (family[2] != undefined) {
                    family[2].RELATIONSHIP = 'SPOUSE';
                }
                if (person[1] != undefined) {
                    person[1].TYPE_ = 'FA';
                    person[1].PLACE_OF_BIRTH_ID = person[1].PLACE_OF_BIRTH_ID == undefined ? '' : person[1].PLACE_OF_BIRTH_ID.ID;
                }
                if (person[2] != undefined) {
                    person[2].TYPE_ = 'FA';
                    person[2].PLACE_OF_BIRTH_ID = person[2].PLACE_OF_BIRTH_ID == undefined ? '' : person[2].PLACE_OF_BIRTH_ID.ID;
                }
                $scope.OFFICE_MAP = $scope.OFFICE_MAP == undefined ? null : $scope.OFFICE_MAP.ID;
             //   alert(document.RECEIPT_STATUS);
            //    $scope.document.RECEIPT_STATUS = 'I';
                //var check = validate('e-tiepnhan');
                //if (check) {

                var cDate = CheckDateSumbitForm($scope.NgaySinh, $scope.person[0].DATE_OF_BIRTH, $scope.document.PHONE_NO);
                if (cDate) {
                    $seft.pageloadding = true;
                    var model = {
                        person,
                        document,
                        family: Object.values(family)
                    }
                   
                    if ($scope.checksumit) {
                        apiservices.post(tokhaiurl + 'InsertToKhai', model, function (respone) {
                            $seft.showstatus = '3';
                            $seft.registrationNo = respone.data.data.document.REGISTRATION_NO;
                            $seft.personid = respone.data.data.person[0].ID;
                            $scope.checksumit = false;
                            $seft.pageloadding = false;
                        }, function (fail) {
                            toastr["error"]("Có lỗi xảy ra! Gửi tờ khai không thành công");
                            $scope.checksumit = false;
                            $seft.pageloadding = false;
                        });
                    } else {
                
                        if ($scope.family[0] != undefined) {
                            $scope.family[0].RELATIONSHIP = 'FATHER';
                            $scope.family[0].GENDER = 'MALE';
                        }
                        if ($scope.family[1] != undefined) {
                            $scope.family[1].RELATIONSHIP = 'MOTHER';
                            $scope.family[1].GENDER = 'FEMALE';
                        }
                        if ($scope.family[2] != undefined) {
                            $scope.family[2].RELATIONSHIP = 'SPOUSE';
                        }
                        if ($scope.person[1] != undefined) {
                            $scope.person[1].TYPE_ = 'FA';
                        }
                        if ($scope.person[2] != undefined) {
                            $scope.person[2].TYPE_ = 'FA';
                        }
                        apiservices.post(tokhaiurl + 'UpdateToKhai', model, function (respone) {
                            dataperson.PERSON_ID = person[0].ID;
                            dataperson.DOCUMENT_ID = document.ID;
                            dataperson.NAME = person[0].NAME;
                            dataperson.REGISTRATION_NO = document.REGISTRATION_NO;
                            dataperson.ID_NUMBER = person[0].ID_NUMBER;
                            dataperson.DATE_OF_BIRTH = person[0].DATE_OF_BIRTH;
                            dataperson.RESIDENT_ADDRESS = document.RESIDENT_ADDRESS + ', ' + RESIDENT_AREA_NAME + ', ' + RESIDENT_PLACE_NAME;
                            dataperson.IS_EPASSPORT = document.IS_EPASSPORT;
                            dataperson.activeTab = document.IS_EPASSPORT;
                            $seft.showstatus = '3';
                            $seft.registrationNo = document.REGISTRATION_NO;
                            $seft.personid = person[0].ID;
                            $scope.checksumit = false;
                            $seft.pageloadding = false;

                        }, function (fail) {
                            toastr["error"]("Có lỗi xảy ra! Gửi tờ khai không thành công");
                            $scope.checksumit = false;
                            $seft.pageloadding = false;
                        });
                    }
                }
                //  }
            }
        }

        $scope.$on('fireLoadPersonEvent', function (event, personId) {

            $seft.captchaCode = '';
            $seft.checked = false;
            $('#e-tiepnhan input').removeClass('e-validate');
            $('#e-tiepnhan select').removeClass('e-validate');
            $('#e-tiepnhan input[type="radio"]').addClass('e-validate-radio');
            $('#e-tiepnhan input[type="radio"]').addClass('epp-radio');
            $seft.showstatus = '1';
            var config = {
                params: {
                    personId: personId,
                }
            }
            apiservices.get(tokhaiurl + 'GetPersonToUpdate', config, function (data) {
                console.log(data);
                GetPlace();
                $scope.checksumit = false;
                $('#e-tokhai').modal('show');
                $scope.person[0] = data.data.data.person;
                var dateB = $scope.person[0].DATE_OF_BIRTH.toString().split('/');
                if (dateB.length == 3) {
                    $scope.NgaySinh = '0';
                } else if (dateB.length == 2) {
                    $scope.NgaySinh = '1';
                } else if (dateB.length == 1) {
                    $scope.NgaySinh = '2';
                }
                $scope.person[0].PLACE_OF_BIRTH_ID = GetPlaceByid(data.data.data.person.PLACE_OF_BIRTH_ID);
                $scope.person[0].PLACE_OF_ID_ISSUE_ID = GetOfficeByid(data.data.data.person.PLACE_OF_ID_ISSUE_ID);
                $scope.person[0].ETHNIC = GetDanTocByid(data.data.data.person.ETHNIC);
                $scope.person[0].RELIGION = GetTonGiaoByid(data.data.data.person.RELIGION);
                $scope.person[0].DATE_OF_ID_ISSUE = data.data.data.person.DATE_OF_ID_ISSUE != null ? GetDate(data.data.data.person.DATE_OF_ID_ISSUE) : '';
                $scope.document = data.data.data.document;
                GetAreaCus(
                    data.data.data.person.RESIDENT_PLACE_ID,
                    data.data.data.person.RESIDENT_AREA_ID,
                    data.data.data.person.TMP_PLACE_ID,
                    data.data.data.person.TMP_AREA_ID,
                    false);
              //  GetArea(data.data.data.person.RESIDENT_PLACE_ID, data.data.data.person.RESIDENT_AREA_ID, false);
                $scope.person[0].RESIDENT_PLACE_ID = GetPlaceByid(data.data.data.person.RESIDENT_PLACE_ID);
                $scope.document.PREV_DATE_OF_ISSUE = data.data.data.document.PREV_DATE_OF_ISSUE != null ? GetDate(data.data.data.document.PREV_DATE_OF_ISSUE) : '';
               // GetAreaTmp(data.data.data.person.TMP_PLACE_ID, data.data.data.person.TMP_AREA_ID, false);
                $scope.person[0].TMP_PLACE_ID = GetPlaceByid(data.data.data.person.TMP_PLACE_ID);
                $scope.document.TYPE_ = GetDocType(data.data.data.document.TYPE_);
                $scope.family = [null, null, null];
                if (data.data.data.family.length > 0) {
                    for (var i = 0; i < data.data.data.family.length; i++) {
                        if (data.data.data.family[i].RELATIONSHIP == "FATHER") {
                            $scope.family[0] = data.data.data.family[i];
                            if ($scope.family[0] != undefined) {
                                $scope.family[0].RELATIONSHIP = 'FATHER';
                                $scope.family[0].GENDER = 'MALE';
                            }
                        } else if (data.data.data.family[i].RELATIONSHIP == "MOTHER") {
                            $scope.family[1] = data.data.data.family[i];
                            if ($scope.family[1] != undefined) {
                                $scope.family[1].RELATIONSHIP = 'MOTHER';
                                $scope.family[1].GENDER = 'FEMALE';
                            }
                        } else if (data.data.data.family[i].RELATIONSHIP == "SPOUSE") {
                            $scope.family[2] = data.data.data.family[i];
                            if ($scope.family[2] != undefined) {
                                $scope.family[2].RELATIONSHIP = 'SPOUSE';
                            }
                        }
                    }
                }
                
                //$scope.family[0] = data.data.data.family[0];
                //if ($scope.family[0] != undefined) {
                //    $scope.family[0].RELATIONSHIP = 'FATHER';
                //    $scope.family[0].GENDER = 'MALE';
                //}
                //$scope.family[1] = data.data.data.family[1];
                //if ($scope.family[1] != undefined) {
                //    $scope.family[1].RELATIONSHIP = 'MOTHER';
                //    $scope.family[1].GENDER = 'MALE';
                //}
                //$scope.family[2] = data.data.data.family[2];
                //if ($scope.family[2] != undefined) {
                //    $scope.family[2].RELATIONSHIP = 'SPOUSE';
                //}

                if (data.data.data.document_child.length > 0) {
                    $scope.person[1] = data.data.data.document_child[0];

                    if ($scope.person[1] != undefined) {
                        var pl = GetPlaceByid(data.data.data.document_child[0].PLACE_OF_BIRTH_ID);
                        $scope.person[1].PLACE_OF_BIRTH_ID = pl;
                    } else {
                        $scope.person[1] = {
                            NAME: null,
                            DATE_OF_BIRTH: null,
                            GENDER: null,
                            PLACE_OF_BIRTH_ID: null
                        }
                    }
                    $scope.person[2] = data.data.data.document_child[1];
                    if ($scope.person[2] != undefined) {
                        var pl = GetPlaceByid(data.data.data.document_child[1].PLACE_OF_BIRTH_ID);
                        $scope.person[2].PLACE_OF_BIRTH_ID = pl;
                    } else {
                        $scope.person[2] = {
                            NAME: null,
                            DATE_OF_BIRTH: null,
                            GENDER: null,
                            PLACE_OF_BIRTH_ID: null
                        }
                    }
                } else {
                    $scope.person[1] = {
                        NAME: null,
                        DATE_OF_BIRTH: null,
                        GENDER: null,
                        PLACE_OF_BIRTH_ID: null
                    }
                    $scope.person[2] = {
                        NAME: null,
                        DATE_OF_BIRTH: null,
                        GENDER: null,
                        PLACE_OF_BIRTH_ID: null
                    }
                }
                $scope.OFFICE_MAP = GetOfficeByid(data.data.data.document.OFFICE_ID);
                $scope.PlaceOffice = $scope.OFFICE_MAP.ADDRESS;
                
                $scope.checksumit = false;
            });
        })

        $scope.$on('fireAddPersonEvent', function (event, personId) {

            $seft.showstatus = '1';
            $seft.captchaCode = '';
            $seft.checked = false;
            if ($scope.checksumit == false) {
                $scope.checksumit = true;
                $scope.NgaySinh = '0';
                $scope.person = null;
                $scope.person = [
                    { DATE_OF_BIRTH: "" },
                    { NAME: null },
                    { NAME: null }
                ]
                $scope.document = null;
                $scope.document = {
                    IS_EPASSPORT: "Y",
                    DATE_OF_DELIVERY: '',
                }
                $scope.family = [];
                $scope.OFFICE_MAP = null;
                $scope.PlaceOffice = null;
                GetPlace();

            }

        })

        $seft.PrintDeclaration = function (personid) {
            if (personid != null || personid != "") {
                $('#e-print-declaration').modal('show');
                loadIframe("/TiepNhan/InToKhai?personId=" + personid);
            } else {
                toastr["error"]("Có lỗi xảy ra!. Lệnh in không được thực hiện");
            }
        }

        $seft.MakeCalendar = function () {
            if ($seft.checked) {
                $seft.e_check = 'e-hide-line';
                $('#e-make-calendar').modal('show');
            } else {
                $seft.e_check = 'e-show-line';
            }
        }

        $seft.ComeBack = function () {
            $seft.showstatus = '1';
        }

        $scope.eppPrint = function () {
            var ua = window.navigator.userAgent;
            var msie = ua.indexOf("MSIE ");
            var iframe = document.getElementById("AdminFrame");
            if (msie > 0) {
                iframe.contentWindow.document.execCommand('print', false, null);
            } else {
                iframe.contentWindow.print();
            }
        }

        $seft.ChangeCaptCha = function () {
            GetCaptCha();
        }

        $seft.CheckInformarion = function () {
            var index = $scope.NgaySinh;
            var checkChildOne = false, checkChildTwo = false;
            var checkfather = false, checkmother = false, checkspouse = false;
            if ($scope.person[1] != undefined && $scope.person[1] != null) {
                if (($scope.person[1].NAME != undefined && $scope.person[1].NAME != null && $scope.person[1].NAME != "") || ($scope.person[1].DATE_OF_BIRTH != undefined && $scope.person[1].DATE_OF_BIRTH != null && $scope.person[1].DATE_OF_BIRTH != "") || ($scope.person[1].GENDER != undefined && $scope.person[1].GENDER != null && $scope.person[1].GENDER != "") || ($scope.person[1].PLACE_OF_BIRTH_ID != undefined && $scope.person[1].PLACE_OF_BIRTH_ID != null && $scope.person[1].PLACE_OF_BIRTH_ID != "")) {
                    checkChildOne = true;
                }
            }
            if ($scope.person[2] != undefined && $scope.person[2] != null) {
                if (($scope.person[2].NAME != undefined && $scope.person[2].NAME != null && $scope.person[2].NAME != "") || ($scope.person[2].DATE_OF_BIRTH != undefined && $scope.person[2].DATE_OF_BIRTH != null && $scope.person[2].DATE_OF_BIRTH != "") || ($scope.person[2].GENDER != undefined && $scope.person[2].GENDER != null && $scope.person[2].GENDER != "") || ($scope.person[2].PLACE_OF_BIRTH_ID != undefined && $scope.person[2].PLACE_OF_BIRTH_ID != null && $scope.person[2].PLACE_OF_BIRTH_ID != "")) {
                    checkChildTwo = true;
                }
            }
            if ($scope.family[0] != undefined && $scope.family[0] != null) {
                if (($scope.family[0].NAME != undefined && $scope.family[0].NAME != null && $scope.family[0].NAME != "") || ($scope.family[0].LOST != undefined && $scope.family[0].LOST != null && $scope.family[0].LOST != "" && $scope.family[0].LOST != "N")) {
                    checkfather = true;
                }
            }
            if ($scope.family[1] != undefined && $scope.family[1] != null) {
                if (($scope.family[1].NAME != undefined && $scope.family[1].NAME != null && $scope.family[1].NAME != "") || ($scope.family[1].LOST != undefined && $scope.family[1].LOST != null && $scope.family[1].LOST != "" && $scope.family[1].LOST != "N")) {
                    checkmother = true;
                }
            }
            if ($scope.family[2] != undefined && $scope.family[2] != null) {
                if (($scope.family[2].NAME != undefined && $scope.family[2].NAME != null && $scope.family[2].NAME != "")) {
                    checkspouse = true;
                }
            }
            switch (index) {
                case "0":
                    var cd = $scope.person[0].DATE_OF_BIRTH != null ? isDate($scope.person[0].DATE_OF_BIRTH) : '';
                    var dateF = $scope.family[0] != null ? CheckDateParent($scope.family[0].DATE_OF_BIRTH) : 0;
                    var dateM = $scope.family[1] != null ? CheckDateParent($scope.family[1].DATE_OF_BIRTH) : 0;
                    if (checkform('e-tiepnhan', cd, $scope.document.PHONE_NO, $scope.person[0].ID_NUMBER, checkChildOne, checkChildTwo, checkfather, checkmother, checkspouse, dateF, dateM)) {
                        //var checkMa = CheckValueData();k
                        //if (checkMa) {
                        //    GetCaptCha();
                        //    $seft.showstatus = '2';
                        //}
                        $seft.showstatus = '2';
                    }
                    break;
                case "1":
                    var cd = $scope.person[0].DATE_OF_BIRTH != null ? isDateMoth($scope.person[0].DATE_OF_BIRTH) : '';
                    var dateF = $scope.family[0] != null ? CheckDateParent($scope.family[0].DATE_OF_BIRTH) : 0;
                    var dateM = $scope.family[1] != null ? CheckDateParent($scope.family[1].DATE_OF_BIRTH) : 0;
                    if (checkform('e-tiepnhan', cd, $scope.document.PHONE_NO, $scope.person[0].ID_NUMBER, checkChildOne, checkChildTwo, checkfather, checkmother, checkspouse, dateF, dateM)) {
                        //var checkMa = CheckValueData();
                        //if (checkMa) {
                        //    GetCaptCha();
                        //    $seft.showstatus = '2';
                        //}
                        $seft.showstatus = '2';
                    }
                    break;
                case "2":
                    var dateF = $scope.family[0] != null ? CheckDateParent($scope.family[0].DATE_OF_BIRTH) : 0;
                    var dateM = $scope.family[1] != null ? CheckDateParent($scope.family[1].DATE_OF_BIRTH) : 0;
                    if (checkform('e-tiepnhan', true, $scope.document.PHONE_NO, $scope.person[0].ID_NUMBER, checkChildOne, checkChildTwo, checkfather, checkmother, checkspouse, dateF, dateM)) {
                        //  var checkMa = CheckValueData();
                        //if (checkMa) {
                        //    GetCaptCha();
                        //    $seft.showstatus = '2';
                        //}
                        $seft.showstatus = '2';
                    }
                    break;
            }

        }


        $seft.ChangeNgaySinh = function () {
            var index = $scope.NgaySinh;
            switch (index) {
                case "0":
                    $('#ps-ngaysinh').mask("00/00/0000", { placeholder: "__/__/____" });
                    break;
                case "1":
                    $('#ps-ngaysinh').mask("00/0000", { placeholder: "__/____" });
                    break;
                case "2":
                    $('#ps-ngaysinh').mask("0000", { placeholder: "____" });
                    break;
            }

        }

        $seft.ChangeFDate = function () {
            var date = $scope.family[0].DATE_OF_BIRTH;
            if (date.length <= 4) {
                $('#f-date').mask("0000", { placeholder: "____" });
            }
            //else if (date.length > 4 && date.length <= 7) {
            //    $('#f-date').mask("00/0000", { placeholder: "__/____" });
            //} else {
            //    $('#f-date').mask("00/00/0000", { placeholder: "__/__/____" });
            //}
        }

        $seft.ChangeMDate = function () {
            var date = $scope.family[1].DATE_OF_BIRTH;
            if (date.length <= 4) {
                $('#m-date').mask("0000", { placeholder: "____" });
            }
            //else if (date.length > 4 && date.length <= 7) {
            //    $('#m-date').mask("00/0000", { placeholder: "__/____" });
            //} else {
            //    $('#m-date').mask("00/00/0000", { placeholder: "__/__/____" });
            //}
        }


        $scope.ChangeArea = function (item) {
            GetArea(item, null, true);
        }

        $scope.ChangeAreaTmp = function (item) {
            GetAreaTmp(item, null, true);
        }

        $scope.EppMakeCadenler = function () {
            var dateMMDDYYYRegex = /^(\d{1,2})\/(\d{1,2})\/(\d{4}) (\d{2}):(\d{2})$/;
            var dateArray = $scope.emakecalendar.match(dateMMDDYYYRegex);
            var dataConvert = new Date(parseInt(dateArray[3]), parseInt(dateArray[2]) - 1, parseInt(dateArray[1]), dateArray[4], dateArray[5]);
            var currentdate = new Date();
            var cd = new Date(currentdate.getFullYear(), currentdate.getMonth(), currentdate.getDate(), currentdate.getHours(), currentdate.getMinutes());
            if (dataConvert < cd) {
                gAlert("Đã quá thời gian đặt lịch. Đề nghị chọn thời điểm khác");
            } else {
                var config = {
                    params: {
                        date: $scope.emakecalendar,
                    }
                }
                apiservices.get('/api/declaration/getdateperson', config, function (respone) {
                    if (respone.data >= 10) {
                        gAlert("Khung giờ đặt lịch đã đầy. Vui lòng chọn thời điểm khác");
                    } else {
                        $scope.document.DATE_OF_DELIVERY = $scope.emakecalendar;
                        toastr["success"]("Đặt lịch thành công!");
                    }
                });
            }
        }

        $scope.OfficeReceive = function (item) {
            if (item != null) {
                $scope.document.OFFICE_ID = item.ID;
                $scope.PlaceOffice = item.ADDRESS;
            } else {
                $scope.PlaceOffice = null;
            }
        }

        function ConverDate(date) {
            var dateMMDDYYYRegex = /^(\d{1,2})\/(\d{1,2})\/(\d{4})$/;
            var dateArray = (date).match(dateMMDDYYYRegex);
            var dataConvert = new Date(parseInt(dateArray[3]), parseInt(dateArray[2]) - 1, parseInt(dateArray[1]));
            return dataConvert;
        }

        function GetPlace() {
            $seft.pageloadding = true;
            apiservices.get(tokhaiurl + 'getplace', null, function (respone) {
                $scope.Places = respone.data.data;

                GetOffice();
                $seft.pageloadding = false;
            });
        }

        function GetPlaceByid(id) {
            if ($scope.Places.length > 0) {
                for (var i = 0; i < $scope.Places.length; i++) {
                    if ($scope.Places[i].ID == id) {
                        return $scope.Places[i];
                    }
                }
            }
            return null;
        }

        function GetOffice() {
            apiservices.get(tokhaiurl + 'getoffice', null, function (respone) {
                $scope.offices = respone.data.data;
                $scope.officesCM = $filter("filter")($scope.offices, { TYPE: 'CAP_CMT' }, true);
                $scope.officesTN = $filter("filter")($scope.offices, { TYPE: 'DONVI' }, true);
                for (var i = 0; i < $scope.offices.length; i++) {
                    if ($scope.offices[i].ID == office) {
                        $scope.OFFICE_MAP = $scope.offices[i];
                        $scope.PlaceOffice = $scope.offices[i].ADDRESS;
                        $scope.document.OFFICE_ID = $scope.offices[i].ID;
                    }
                }

                GetCategoryDanToc();
            });
        }

        function GetOfficeByid(id) {
            if ($scope.offices.length > 0) {
                for (var i = 0; i < $scope.offices.length; i++) {
                    if ($scope.offices[i].ID == id) {
                        return $scope.offices[i];
                    }
                }
            }
            return null;
        }

        function GetArea(id, ida, status) {
            var config = {
                params: {
                    placeId: id,
                }
            }
            apiservices.get(tokhaiurl + 'getarea', config, function (respone) {
                $scope.areas = respone.data.data;
                if (!status) {
                    $scope.person[0].RESIDENT_AREA_ID = GetAreaByid(ida);
                }
            });
            $timeout(
                function () { $('.selectpicker').selectpicker("refresh") },
                100
            );
        }

        function GetAreaByid(id) {

            if ($scope.areas.length > 0) {
                for (var i = 0; i < $scope.areas.length; i++) {
                    if ($scope.areas[i].ID == id) {
                        return $scope.areas[i];
                    }
                }
            }
            return null;
        }

        function GetAreaByidtmp(id) {
            if ($scope.areastmp.length > 0) {
                for (var i = 0; i < $scope.areastmp.length; i++) {
                    if ($scope.areastmp[i].ID == id) {
                        return $scope.areastmp[i];
                    }
                }
            }
            return null;
        }

        function GetAreaTmp(id, ida, status) {

            var config = {
                params: {
                    placeId: id,
                }
            }
            apiservices.get(tokhaiurl + 'getarea', config, function (respone) {
                $scope.areastmp = respone.data.data;
                if (!status) {
                    $scope.person[0].TMP_AREA_ID = GetAreaByidtmp(ida);
                }
            });
            $timeout(
                function () { $('.selectpicker').selectpicker("refresh") },
                100
            );
        }

        function GetAreaCus(id, ida, idt, idat, status) {
            var config = {
                params: {
                    placeId: id,
                }
            }
            apiservices.get(tokhaiurl + 'getarea', config, function (respone) {
                console.log(respone);
                $scope.areas = respone.data.data;
                if (!status) {
                    $scope.person[0].RESIDENT_AREA_ID = GetAreaByid(ida);
                    GetAreaTmp(idt, idat, status);
                }
            });

        }


        function GetCategoryDanToc() {
            apiservices.get(tokhaiurl + 'GetEthnic', null, function (respone) {
                $scope.categorydt = respone.data.data;
                GetCategoryTonGiao();
            });
        }

        function GetDanTocByid(id) {
            if ($scope.categorydt.length > 0) {
                for (var i = 0; i < $scope.categorydt.length; i++) {
                    if ($scope.categorydt[i].FLEX_VALUE == id) {
                        return $scope.categorydt[i];
                    }
                }
            }
            return null;
        }


        function GetCategoryTonGiao() {
            apiservices.get(tokhaiurl + 'GetReligion', null, function (respone) {
                $scope.categorytg = respone.data.data;
                GetDocumnetType();
            });
        }

        function GetTonGiaoByid(id) {
            if ($scope.categorytg.length > 0) {
                for (var i = 0; i < $scope.categorytg.length; i++) {
                    if ($scope.categorytg[i].FLEX_VALUE == id) {
                        return $scope.categorytg[i];
                    }
                }
            }
            return null;
        }

        function GetDocumnetType() {
            apiservices.get(tokhaiurl + 'GetDocType', null, function (respone) {
                $scope.doctype = respone.data.data;
                GetCaptCha();
            });
        }

        function GetDocType(id) {
            if ($scope.doctype.length > 0) {
                for (var i = 0; i < $scope.doctype.length; i++) {
                    if ($scope.doctype[i].FLEX_VALUE == id) {
                        return $scope.doctype[i];
                    }
                }
            }
            return null;
        }

        function CheckValueData() {
            var ck = false;
            var value = $seft.captchaCode;
            if (captcha.toUpperCase() === value) {
                ck = true;
            } else {
                gAlert("Nhập mã xác nhận sai");
                return false;
            }
            return ck;
        }

        function GetCaptCha() {
            $.ajax({
                type: 'GET',
                url: tokhaiurl + "CaptCha",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                crossDomain: true,
                success: function (response) {
                    $scope.captcha = response.img;
                    captcha = response.data;
                    $('.selectpicker').selectpicker("refresh")
                    $scope.$apply();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR);
                    console.log(textStatus);
                    console.log(errorThrown);
                }
            });
        }

        var endWeek = false;
        var logic = function (currentDateTime) {
            if (currentDateTime.getDay() == 6) {
                endWeek = true;
                this.setOptions({
                    allowTimes: [
                        '08:00', '08:30', '09:00', '09:30', '10:00', '10:30', '11:00'
                    ]
                });
            } else
                if (endWeek == true) {
                    endWeek = false;
                    this.setOptions({
                        allowTimes: [
                            '08:00', '08:30', '09:00', '09:30', '10:00', '10:30', '11:00',
                            '13:30', '14:00', '14:30', '15:00', '15:30'
                        ]
                    });
                }
        };

        $(function () {
            //   $.e_datetimepicker.setLocale('vi');

            $('#datetimepicker3').e_datetimepicker({
                i18n: {
                    vi: {
                        months: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5',
                            'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10',
                            'Tháng 11', 'Tháng 12', ],
                        dayOfWeek: ["CN", "T2", "T3", "T4", "T5", "T6", "T7", ]
                    }
                },
                format: 'd/m/Y H:i',
                inline: true,
                dayOfWeekStart: 1,
                disabledWeekDays: [0],
                minDate: '-1970/01/01',
                maxDate: '+1970/01/11',
                value: new Date('d/m/Y'),
                defaultTime: '08:00',
                allowTimes: [
                  '08:00', '08:30', '09:00', '09:30', '10:00', '10:30', '11:00',
                  '13:30', '14:00', '14:30', '15:00', '15:30'
                ],
                onGenerate: function (ct, $input) {
                    $scope.emakecalendar = $input.val();
                    //  $('#date').val($input.val());
                },
                onChangeDateTime: function (dp, $input) {
                    $scope.emakecalendar = $input.val();
                    // $('#date').val($input.val());
                },
                onChangeDateTime: logic,
                onShow: logic
            });
        });
    }

})(angular.module('e-app'));
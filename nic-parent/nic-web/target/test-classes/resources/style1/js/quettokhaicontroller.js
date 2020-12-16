(function (app) {
    app.controller('quettokhaicontroller', QuetToKhaiController);
    QuetToKhaiController.$inject = ['$scope', '$filter', 'apiservices', '$rootScope', '$window', '$timeout', '$rootScope'];
    function QuetToKhaiController($scope, $filter, apiservices, $rootScope, $window, $timeout, $rootScope) {
        var $seft = this;
        $scope.AnhTrangB64 = 'data:image/png;base64,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';
        $scope.currentPage = 1;
        $scope.pageSize = 10;
        $scope.totalRecord = 10;
        $scope.totalRecordHangDoi = 10;
        $scope.itemPage = {
            "type": "select",
            "value": $scope.pageSize,
            "values": [10, 15, 20, 50]
        };
        $scope.TongTienDN = 0;
        
        $scope.CheckBN = false;
        $scope.CheckTiepNhan = false;
        $scope.DisabledPrevTK = true;
        $scope.DisabledNextTK = true;
        $scope.DisableQuetTK = false;
        $scope.IdGiayToKhac = 1;
        $scope.maxTK = 0;
        $scope.selectedRowLSCHC = 0;
        $scope.currentTK = 0;
        var CurrentIndex = 0;
        var Index = 1;
        var CurrentImg = 1;
        $scope.tokhai = {};
        $scope.PrintBN = {};
        // Vân tay
        var dataPrint = [];
        var dataPrintBmp = [];
        //
        $scope.hangdoi = {};
        $scope.selectedRowHangDoi = -1;
        $scope.OfficeName = "";

        $scope.LichSuCapPhat = [];

        $scope.RoleBtnSearch = false;
        $scope.RoleBtnQuetToKhai = false;
        $scope.RoleBtnChupVanTay = false;
        $scope.RoleBtnAddAttachment = false;
        $scope.RoleBtnAddImageChild = false;
        $scope.RoleBtnLichSuCapPhatHC = false;
        $scope.RoleBtnInBienNhan = false;
        $scope.RoleBtnInLaiBienNhan = false;
        $scope.RoleBtnCancel = false;
        $scope.RoleBtnCreateDS = false;
        $scope.SearchLS = {};
        angular.element(document).ready(function () {

            // Lấy danh mục
            $.ajax({
                type: 'POST',
                dataType: 'json',
                cache: false,
                async: false,
                url: '/TiepNhan/GetListRoleButtom',
                data: { actionName: "QuetToKhai" },
                success: function (response) {
                    if (response.Buttoms != null) {
                        angular.forEach(response.Buttoms, function (item) {
                            if (item == 'btnTNSearch') {
                                $scope.RoleBtnSearch = true;
                            }
                            if (item == 'btnTNQuetToKhai') {
                                $scope.RoleBtnQuetToKhai = true;
                            }
                            if (item == 'btnTNChupVanTay') {
                                $scope.RoleBtnChupVanTay = true;
                            }
                            if (item == 'btnTNAddAttachment') {
                                $scope.RoleBtnAddAttachment = true;
                            }
                            if (item == 'btnTNAddImageChild') {
                                $scope.RoleBtnAddImageChild = true;
                            }
                            if (item == 'btnTNLichSuCapPhatHC') {
                                $scope.RoleBtnLichSuCapPhatHC = true;
                            }
                            if (item == 'btnTNInBienNhan') {
                                $scope.RoleBtnInBienNhan = true;
                            }
                            if (item == 'btnTNInLaiBienNhan') {
                                $scope.RoleBtnInLaiBienNhan = true;
                            }
                            if (item == 'btnTNCancel') {
                                $scope.RoleBtnCancel = true;
                            }
                            if (item == 'btnTNCreateDS') {
                                $scope.RoleBtnCreateDS = true;
                            }
                        });
                    }

                    $scope.$apply();
                }
                , error: function (xhr) {
                    toastr['error']('Kiểm tra lại kết nối mạng!');
                }
            });
        });

        $scope.ListDeNghiChonBN = [];
        $scope.ListHangDoi = [];
        $scope.ListDeNghi = [];
        $scope.ListGender = [];
        LoadDSDeNghi();
        $("#btnInBienNhan").prop('disabled', true);
        $scope.ListOffice = [];
        $scope.ListOfficeLSCHC = [];
        LoadOffice();
        LoadGender();
        $scope.LichSuCapPhatDetail = {};
        $scope.SearchLS.GENDER = "TATCA";
        $scope.ListDayInWeek = [
            { 'id': 0, 'name': 'Chủ nhật' },
            { 'id': 1, 'name': 'Thứ hai' },
            { 'id': 2, 'name': 'Thứ ba' },
            { 'id': 3, 'name': 'Thứ tư' },
            { 'id': 4, 'name': 'Thứ năm' },
            { 'id': 5, 'name': 'Thứ sáu' },
            { 'id': 6, 'name': 'Thứ bảy' }
        ];

        $scope.ListType = [
            { 'id': 'ONLINE', 'type': 'Hộ chiếu điện tử' },
            { 'id': 'NORMAL', 'type': 'Hộ chiếu thường' },
            { 'id': 'HNDWRT', 'type': 'Viết tay' },
        ];
        //$scope.tokhai.DOCUMENT_TYPE = $scope.ListType[0];
        $scope.tokhai.DOCUMENT_TYPE = 'ONLINE';

        $scope.ListTypeNgaySinh = [
            { 'id': '0', 'type': 'Đầy đủ ngày, tháng, năm' },
            { 'id': '1', 'type': 'Chỉ có tháng và năm sinh' },
            { 'id': '2', 'type': 'Chỉ có năm sinh' }
        ];
        $scope.tokhai.NgaySinh = $scope.ListTypeNgaySinh[0];
        $scope.NgaySinh = $scope.ListTypeNgaySinh[0];

        $scope.ListDeNghiChon = [];


        $scope.ListToKhai = [];
        $scope.ListGiayToKhac = [];

        $scope.IdTreEm = 1;
        $scope.ListTreEm = [];
        $scope.selectedRowTreEm = -1;
        $scope.CurrentTreEm = {};

        $("#divTE_TK").hide();
        $("#divTE_Ten").show();

        var today = new Date();
        //$scope.$watch('tokhai.DATE_OF_BIRTH', function (newValue) {
        //    $scope.tokhai.DATE_OF_BIRTH = $filter('date')(newValue, 'dd/MM/yyyy');
        //});

        $scope.tokhai.DOCUMENTARY_ISSUED = today;
        $scope.$watch('tokhai.DOCUMENTARY_ISSUED', function (newValue) {
            $scope.tokhai.DOCUMENTARY_ISSUED = $filter('date')(newValue, 'dd/MM/yyyy');
        });

        var issueDateWeekDay = 0;
        function SetIssueDate() {
            var workday = 1;
            var countday = 1;
            var issueDate = new Date();

            while (workday < 9) {
                var d = new Date();
                d.setDate(d.getDate() + countday);

                var wd = d.getDay();

                if (wd != 0 && wd != 6) {
                    workday++;
                }
                else {
                }

                countday++;
            }

            issueDate.setDate(issueDate.getDate() + (countday - 1));
            issueDateWeekDay = issueDate.getDay();

            $scope.tokhai.DATE_OF_ISSUE = issueDate;
            var day = issueDate.getDay();

            var searchDIW = $filter("filter")($scope.ListDayInWeek, { id: day }, true);
            if (searchDIW.length > 0) {
                $scope.tokhai.DATE_IN_WEEK = searchDIW[0];
            }
        }
        $scope.$watch('tokhai.DATE_OF_ISSUE', function (newValue) {
            $scope.tokhai.DATE_OF_ISSUE = $filter('date')(newValue, 'dd/MM/yyyy');
        });
        SetIssueDate();

        $scope.ChangeDateIssue = function () {
            var issue = $scope.tokhai.DATE_OF_ISSUE.split("/");
            var issuedate = new Date(issue[2], issue[1] - 1, issue[0]);
            var day = issuedate.getDay();

            var searchDIW = $filter("filter")($scope.ListDayInWeek, { id: day }, true);
            if (searchDIW.length > 0) {
                $scope.tokhai.DATE_IN_WEEK = searchDIW[0];
            }
        }



        $scope.hangdoi = {
            TuNgay: today,
            DenNgay: today
        }
        $scope.$watch('hangdoi.TuNgay', function (newValue) {
            $scope.hangdoi.TuNgay = $filter('date')(newValue, 'dd/MM/yyyy');
        });
        $scope.$watch('hangdoi.DenNgay', function (newValue) {
            $scope.hangdoi.DenNgay = $filter('date')(newValue, 'dd/MM/yyyy');
        });

        //$scope.NgaySinh = "0";
        $('#dtpNgaySinh').mask("00/00/0000", { placeholder: "__/__/____" });
        $scope.ChangeNgaySinh = function () {
            var index = $scope.NgaySinh;
            switch (index) {
                case "0":
                    $('#dtpNgaySinh').mask("00/00/0000", { placeholder: "__/__/____" });
                    break;
                case "1":
                    $('#dtpNgaySinh').mask("00/0000", { placeholder: "__/____" });
                    break;
                case "2":
                    $('#dtpNgaySinh').mask("0000", { placeholder: "____" });
                    break;
            }
        }

        $scope.ThemTreEm = function () {
            ThemTreEm();
        }
        $scope.TenTreEmPress = function (keyEvent) {
            if (keyEvent.which === 13)
                ThemTreEm();
        }
        function ThemTreEm() {
            if ($scope.ListTreEm != null && $scope.ListTreEm.length >= 2) {
                toastr["error"]("Chỉ được thêm tối đa 2 trẻ em !");
                return;
            }
            if ($scope.TenTreEm == null || $scope.TenTreEm == '') {
                toastr["error"]("Chưa nhập họ tên trẻ em !");
                return;
            }

            var treem = {
                'DOC_ID': 0,
                'REG_NO': '',
                'MA': $scope.IdTreEm,
                'TEN': $scope.TenTreEm,
                'ANH': ''
            };

            $scope.ListTreEm.push(treem);
            $scope.TenTreEm = '';
            $scope.IdTreEm++;
        }
        $scope.XoaTreEm = function (item) {
            gConfirm('Có chắc xóa bản ghi này ?', function () {
                var searchItem = $filter("filter")($scope.ListTreEm, { MA: item }, true);
                if (searchItem.length > 0) {
                    var index = $scope.ListTreEm.indexOf(searchItem[0]);
                    $scope.ListTreEm.splice(index, 1);
                    $scope.$apply();
                }
            })
        }
        $scope.SelectTreEm = function (index, treem) {
            $scope.selectedRowTreEm = index;
            $scope.CurrentTreEm = treem;
            if ($scope.CurrentTreEm != null && $scope.CurrentTreEm.TEN != '') {
                if ($scope.CheckTiepNhan == false) {
                    $("#btnQuetAnhTreEm").prop('disabled', false);
                }

                var imgsrc = '';
                if ($scope.CurrentTreEm.ANH != null && $scope.CurrentTreEm.ANH != '')
                    imgsrc = 'data:image/png;base64, ' + $scope.CurrentTreEm.ANH;
                else
                    imgsrc = 'data:image/png;base64,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';

                $('#imgTreEm').attr('src', imgsrc);
            }
        }
        $scope.search = {};
        $scope.ChangeRegNoTE = function () {

            if ($scope.RegNoTE != null) {
                $scope.search.DOC_ID = $scope.RegNoTE.DOC_ID;
            }

            var anhtrang = 'data:image/png;base64,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';
            $scope.selectedRowTreEm = -1;
            $scope.CurrentTreEm = {};
            $('#imgTreEm').attr('src', anhtrang);
        }

        $scope.InLai = function () {
            var id = $scope.tokhai.RECEIPT_ID;
            if (id != null && id != '') {
                $scope.pageloadding = true;
                $("#quayquaytiepnhan").removeClass("ng-hide");
                $('#WrapIframe').empty();
                var drl = "/GiayBienNhan/Inbiennhan" + "?id=" + id + "&needPrint=0";
                loadIframe(drl);
                $('#report').modal('show');
            }
        }

        $scope.InBienNhan = function (tokhai) {
            //if (validate('e-tokhai') == false) {
            //    return;
            //}
            if (tokhai.FULL_NAME == null || tokhai.FULL_NAME == '') {
                toastr["error"]("Chưa nhập họ tên !");
                return;
            }
            if (tokhai.PID_NO == null || tokhai.PID_NO == '' || tokhai.PID_NO.length > 12) {
                toastr["error"]("Số CMND/CCCD không hợp lệ !");
                return;
            }
            if (tokhai.DATE_OF_ISSUE == null || tokhai.DATE_OF_ISSUE == '') {
                toastr["error"]("Chưa chọn ngày hẹn trả !");
                return;
            }
            
            //// xóa đề nghị null khỏi danh sách đề nghị
            //var searchItem = $filter("filter")($scope.ListDeNghiChon, { MADENGHI: "" }, true);
            //if (searchItem.length > 0) {
            //    for (var i = 0; i < searchItem.length; i++) {
            //        var index = $scope.ListDeNghiChon.indexOf(searchItem[i]);
            //        $scope.ListDeNghiChon.splice(index, 1);
            //    }
            //}
            //// xóa đề nghị mặc định khỏi ds đề nghị
            //var searchItem0 = $filter("filter")($scope.ListDeNghiChon, { MADENGHI: "0" }, true);
            //if (searchItem0.length > 0) {
            //    for (var i = 0; i < searchItem0.length; i++) {
            //        var index = $scope.ListDeNghiChon.indexOf(searchItem0[i]);
            //        $scope.ListDeNghiChon.splice(index, 1);
            //    }
            //}
            var listDeNghi = $scope.ListDeNghiChon.filter(function (x) {
                return (x.MADENGHI != "" && x.MADENGHI != "0");
            })
            if (listDeNghi == null || listDeNghi.length <= 0) {
                toastr["error"]("Chưa chọn đề nghị !");
                return;
            }

            if (tokhai.NOTE != null && tokhai.NOTE != '' && tokhai.NOTE.length > 300) {
                toastr["error"]("Nội dung ghi chú chỉ được nhập tối đa 400 ký tự !");
                return;
            }

            if (($scope.ListToKhai == null || $scope.ListToKhai.length <= 0) && ($scope.ListGiayToKhac == null || $scope.ListGiayToKhac.length <= 0)) {
                toastr["error"]("Chưa có thêm tệp đính kèm !");
                return;
            }

            var isValidTreEm = true;
            $scope.ListTreEm.forEach(function (entry) {
                if (entry.DOC_ID == 0 && entry.ANH == '') {
                    isValidTreEm = false;
                }
            });
            if (!isValidTreEm) {
                toastr["error"]("Chưa nhập đủ ảnh trẻ em !");
                return;
            }

            var isValid = true;
            var qty = 0;
            $scope.ListDeNghiChon.forEach(function (entry) {
                qty += entry.soluong;
                if (entry.soluong <= 0) {
                    isValid = false;
                }
            });

            if (!isValid || qty < $scope.ListToKhai.length) {
                toastr["error"]("Số lượng đề nghị không hợp lệ !");
                return;
            }

            //tokhai.DOCUMENT_TYPE_ID = tokhai.DOCUMENT_TYPE.id;
            tokhai.DOCUMENT_TYPE_ID = tokhai.DOCUMENT_TYPE;
            $scope.ListToKhai.forEach(function (entry) {
                //entry.DOCUMENT_TYPE_ID = entry.DOCUMENT_TYPE.id;
                entry.DOCUMENT_TYPE_ID = entry.DOCUMENT_TYPE;
                //if (entry.DOCUMENTARY_ISSUED != null && entry.DOCUMENTARY_ISSUED != '') {
                //    entry.DOCUMENTARY_ISSUED = new Date(entry.DOCUMENTARY_ISSUED).toISOString();
                //}
            });

            //var ingiayhen = false;
            //if ($scope.InGiayHen != null && $scope.InGiayHen == true) {
            //    ingiayhen = true;
            //}

            $seft.pageloadding = true;

            $.ajax({
                url: "/TiepNhan/InBienNhan",
                type: 'POST',
                data: {
                    tokhai: tokhai,
                    listtokhai: $scope.ListToKhai,
                    listdenghi: listDeNghi,
                    listgiayto: $scope.ListGiayToKhac,
                    listtreem: $scope.ListTreEm
                    //,ingiayhen: ingiayhen
                },
                success: function (data) {
                    $seft.pageloadding = false;

                    LoadHangDoi();
                    if (data.message == "" || data.message == null) {
                        LoadDataPrintBN(tokhai, data.receipt_no);
                        //location.reload();
                        toastr["success"]("Tiếp nhận hồ sơ thành công !");
                        var id = data.receipt_id;
                        if (id != null && id != '') {
                            $scope.pageloadding = true;
                            $('#modalBienNhan').modal('show');
                        }
                    }
                    else {
                        console.log(data.message);
                        toastr["error"]("Có lỗi xảy ra(" + data.message + "), vui lòng kiểm tra lại !");
                    }
                    ClearText();
                    $scope.$apply();
                }, error: function (jqXHR, textStatus, errorMessage) {
                    $seft.pageloadding = false;
                    console.log(errorMessage);
                    $scope.$apply();
                }
            });


        }

        function ClearText() {
            var dateinweek = $scope.tokhai.DATE_IN_WEEK;

            $scope.IdGiayToKhac = 1;
            $scope.maxTK = 0;
            $scope.currentTK = 0;
            var CurrentIndex = 0;
            var Index = 1;
            var CurrentImg = 1;
            $scope.tokhai = {};

            $scope.ListDeNghiChon = [];
            AddRowDeNghi();
            $scope.ListToKhai = [];
            $scope.ListGiayToKhac = [];

            $scope.tokhai.DATE_IN_WEEK = dateinweek;
            //$scope.tokhai.DOCUMENT_TYPE = "";
            $scope.tokhai.DOCUMENT_TYPE = 'ONLINE';
            //$scope.tokhai.DOCUMENT_TYPE = $scope.ListType[0];
            var anhtrang = 'data:image/png;base64,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';
            $('#imgToKhai1').attr('src', anhtrang);
            $('#imgPerson').attr('src', anhtrang);

            $scope.IdTreEm = 1;
            $scope.ListTreEm = [];
            $scope.selectedRowTreEm = -1;
            $scope.CurrentTreEm = {};
            $('#imgTreEm').attr('src', anhtrang);
            $scope.RegNoTE = '';
            $scope.search.DOC_ID = -1;

            $scope.NgaySinh = 0;

            $("#divTE_TK").hide();
            $("#divTE_Ten").show();

            $scope.CheckNopHS = true;
            $scope.tokhai.PLACE_OF_RECEIPT = $scope.ListOffice[0].NAME;
            $("#cbOffice").prop('disabled', true);
            //$("#btnInBienNhan").prop('disabled', true);
            SetIssueDate();

            var searchDIW = $filter("filter")($scope.ListDayInWeek, { id: issueDateWeekDay }, true);
            if (searchDIW.length > 0) {
                $scope.tokhai.DATE_IN_WEEK = searchDIW[0];
            }

            $scope.tokhai.DOCUMENTARY_ISSUED = new Date();

            $scope.tokhai.IS_POST_OFFICE = false;
           // var documentType = $scope.tokhai.DOCUMENT_TYPE.id;
            var documentType = $scope.tokhai.DOCUMENT_TYPE;
            if (documentType == "HNDWRT") {
                $("#btnInBienNhan").prop('disabled', false);
                $scope.DisableQuetTK = true;
            }
            else {
                $("#btnInBienNhan").prop('disabled', true);
                $scope.DisableQuetTK = false;
            }
            //$scope.$apply();
        }

        $scope.UpdateToKhai = function (field, ctr) {
            if ($scope.ListToKhai.length > 0) {
                var tokhai = $scope.ListToKhai[CurrentIndex];
                tokhai[field] = ctr;
                if (field == 'DOCUMENT_TYPE') {
                    CheckDocumentType();
                }
                // Hiển thị lịch sử cấp phát hộ chiếu
                //TimKiemLSCHC();
            }

            //if (field == "PID_NO") {
            //    // Hiển thị lịch sử cấp phát hộ chiếu
            //    TimKiemLSCHC();
            //}

            if (field == 'DOCUMENT_TYPE') {                                                                                                                 
                CheckDocumentType();
                if (ctr == "HNDWRT") {
                    $("#btnInBienNhan").prop('disabled', false);
                    $scope.DisableQuetTK = true;
                }
                else {
                    $("#btnInBienNhan").prop('disabled', true);
                    $scope.DisableQuetTK = $scope.cvt;
                }
            }
            else {
                //var documentType = $scope.tokhai.DOCUMENT_TYPE.id;
                var documentType = $scope.tokhai.DOCUMENT_TYPE;
                if (documentType == "HNDWRT") {
                    $("#btnInBienNhan").prop('disabled', false);
                    $scope.DisableQuetTK = true;
                }
                //else {
                //    $("#btnInBienNhan").prop('disabled', true);
                //    $scope.DisableQuetTK = $scope.cvt;
                //}
            }
            
        }

        //MADENGHI
        $scope.ChonDeNghi = function (index, item) {
            $scope.CheckBN = false;
            var array = item.TENDENGHI.split('(');
            if (array[0] != null && array[0] != "") {
                var searchItem = $filter("filter")($scope.ListDeNghiChon, { MADENGHI: array[0] }, true);
                if (searchItem.length > 0) {
                    $scope.XoaDeNghi(array[0], index);
                    if (array[0] != "0") {
                        toastr["error"]("Đề nghị vừa chọn đã có trong danh sách đề nghị đã chọn!");
                    }
                    AddRowDeNghi();
                }
                else {
                    var searchItem1 = $filter("filter")($scope.ListDeNghi, { MADENGHI: array[0] }, true);
                    if (searchItem1.length > 0) {
                        $scope.ListDeNghiChon[index].MADENGHI = searchItem1[0].MADENGHI;
                        $scope.ListDeNghiChon[index].DONGIA = searchItem1[0].DONGIA;
                        $scope.ListDeNghiChon[index].DVT = searchItem1[0].DVT;
                        $scope.ListDeNghiChon[index].TENDENGHI = searchItem1[0].TENDENGHI;

                        if ($scope.ListDeNghiChon[index].SOLUONG == null || $scope.ListDeNghiChon[index].SOLUONG <= 0) {
                            $scope.ListDeNghiChon[index].SOLUONG = 1;
                            item.SOLUONG = 1;
                            $scope.ListDeNghiChon[index].THANHTIEN = searchItem1[0].DONGIA;
                        }
                        else {
                            item.SOLUONG = $scope.ListDeNghiChon[index].SOLUONG;
                            item.DONGIA = $scope.ListDeNghiChon[index].DONGIA;
                            item.TENDENGHI = $scope.ListDeNghiChon[index].TENDENGHI;
                            item.DVT = $scope.ListDeNghiChon[index].DVT;
                            item.THANHTIEN = $scope.ListDeNghiChon[index].SOLUONG * searchItem1[0].DONGIA;
                            $scope.ListDeNghiChon[index].SOLUONG = $scope.ListDeNghiChon[index].SOLUONG;
                            $scope.ListDeNghiChon[index].THANHTIEN = $scope.ListDeNghiChon[index].SOLUONG * searchItem1[0].DONGIA;
                        }
                        AddRowDeNghi();
                    }
                }
            }
            $scope.TongTienDN = 0;
            if ($scope.ListDeNghiChon != null && $scope.ListDeNghiChon.length > 0) {
                for (var i = 0; i < $scope.ListDeNghiChon.length; i++) {
                    $scope.TongTienDN = $scope.TongTienDN + $scope.ListDeNghiChon[i].THANHTIEN;
                }
            }
        }

        $scope.ThemDeNghi = function () {
            AddRowDeNghi();
        }

        function AddRowDeNghi() {
            //var searchItem = $filter("filter")($scope.ListDeNghiChon, { MADENGHI: $scope.ListDeNghi[0].MADENGHI }, true);
            //if (searchItem.length == 0 || searchItem == null) {
            //    var denghi = {
            //        MADENGHI: "0",
            //        TENDENGHI: "",
            //        DONGIA: 0,
            //        DVT: "VND",
            //        SOLUONG: 1,
            //        THANHTIEN: 0
            //    }
            //    $scope.ListDeNghiChon.push(denghi);
            //}

            var searchItem = $filter("filter")($scope.ListDeNghiChon, { MADENGHI: "" }, true);
            if (searchItem.length == 0 || searchItem == null) {
                var denghi = {
                    MADENGHI: "0",
                    TENDENGHI: "",
                    DONGIA: 0,
                    DVT: "VND",
                    SOLUONG: 1,
                    THANHTIEN: 0
                }
                $scope.ListDeNghiChon.push(denghi);
            }
        }

        $scope.XoaDeNghi = function (ma, index) {
            $scope.ListDeNghiChon.splice(index, 1);
            if ($scope.ListDeNghiChon == null || $scope.ListDeNghiChon.length == 0) {
                AddRowDeNghi();
            }
            //var searchItem = $filter("filter")($scope.ListDeNghiChon, { MADENGHI: ma }, true);
            //if (searchItem.length > 0) {
            //    var index = $scope.ListDeNghiChon.indexOf(searchItem[0]);
            //    $scope.ListDeNghiChon.splice(index, 1);
            //}
            $scope.TongTienDN = 0;
            if ($scope.ListDeNghiChon != null && $scope.ListDeNghiChon.length > 0) {
                for (var i = 0; i < $scope.ListDeNghiChon.length; i++) {
                    $scope.TongTienDN = $scope.TongTienDN + $scope.ListDeNghiChon[i].THANHTIEN;
                }
            }
        }

        $scope.UpdateSoLuongDeNghi = function (index, soLuong) {

            if (soLuong == null || soLuong <= 0) {
                soLuong = 1;
                $scope.ListDeNghiChon[index].SOLUONG = 1;
            }
            else {
                $scope.ListDeNghiChon[index].SOLUONG = soLuong;
            }
            $scope.ListDeNghiChon[index].THANHTIEN = $scope.ListDeNghiChon[index].SOLUONG * $scope.ListDeNghiChon[index].DONGIA;

            $scope.TongTienDN = 0;
            if ($scope.ListDeNghiChon != null && $scope.ListDeNghiChon.length > 0) {
                for (var i = 0; i < $scope.ListDeNghiChon.length; i++) {
                    $scope.TongTienDN = $scope.TongTienDN + $scope.ListDeNghiChon[i].THANHTIEN;
                }
            }
        }

        $scope.SearchHangDoi = function () {
            LoadHangDoi();
        }

        $("#frmUpload").submit(function () {
            //check file size
            var input = document.getElementById('file');

            if (input.files != null && input.files.length > 0) {
                var file = input.files[0];
                if (file.size > 1200000) {
                    toastr["error"]("Quét tờ khai thất bại, không thể quét file có dung lượng lớn hơn 1MB");
                    $("#file").val('');
                    return false;
                }
            }
            ClearBienNhan();
            var formData = new FormData(this);
            $seft.pageloadding = true;
            $.ajax({
                url: "/TiepNhan/UpAnh",
                type: 'POST',
                data: formData,
                async: true,
                success: function (data) {
                    $seft.pageloadding = false;
                    //$('#imgToKhai').attr('src', data.filename);

                    var tokhai = data.tokhai;

                    if ($scope.selectedRowHangDoi > -1) {
                        $scope.selectedRowHangDoi = -1;
                        $scope.ListToKhai = [];
                        $scope.ListDeNghiChon = [];
                        $scope.ListGiayToKhac = [];
                        AddRowDeNghi();
                    }

                    if (data.type == 0 && tokhai.NAME != null && tokhai.NAME != '' && data.message == '') {

                        var searchItemTK = $filter("filter")($scope.ListToKhai, { REGISTRATION_NO: tokhai.REGISTRATION_NO }, true);
                        if (searchItemTK.length > 0) {
                            toastr["error"]("Tờ khai bạn đã quét rồi. Xin chọn tờ khai khác!");
                        }
                        else {

                            $scope.ListToKhai.push(
                                {
                                    'DOC_ID': tokhai.ID,
                                    'FULL_NAME': tokhai.NAME,
                                    'DATE_OF_BIRTH': tokhai.DATE_OF_BIRTH,
                                    'DATE_OF_BIRTH_FORMATTED': tokhai.DATE_OF_BIRTH_FORMATTED,
                                    'PID_NO': tokhai.ID_NUMBER,
                                    'ADDRESS': tokhai.RESIDENT_ADDRESS + ', ' + tokhai.RESIDENT_AREA + ', ' + tokhai.RESIDENT_PLACE,
                                    'PREV_PASSPORT_NO': tokhai.PREV_PASSPORT_NO,
                                    'AMOUNT_OF_DOCUMENT': '',
                                    'ADDED_DOCUMENTS': '',
                                    'DOCUMENTARY_NO': '',
                                    'DOCUMENTARY_ISSUED': '',
                                    'DOCUMENT_TYPE': tokhai.DOCUMENT_TYPE,
                                    'REGISTRATION_NO': tokhai.REGISTRATION_NO,
                                    'IMAGEB64': tokhai.IMAGEB64,
                                    'PERSON_IMG': tokhai.PERSON_IMG,
                                    'REG_NO_TE': tokhai.REGISTRATION_NO + " - " + tokhai.NAME,
                                    'INDEX': Index,
                                    'IS_CHARED': tokhai.IS_CHARED,
                                    'DELIVERY_AT_OFFICE': "Y",
                                    'PLACE_OF_RECEIPT': $scope.ListOffice[0].NAME,
                                    'SEARCH_NAME': tokhai.SEARCH_NAME,
                                    'GENDER': tokhai.GENDER,
                                    'PLACE_OF_BIRTH_ID': tokhai.PLACE_OF_BIRTH_ID,
                                    'PERSON_ID': tokhai.PERSON_ID,
                                }
                            );
                            Index++;
                            $scope.RegNoTE = $scope.ListToKhai[0];
                            $scope.search.DOC_ID = $scope.RegNoTE.DOC_ID;
                            //show img
                            //var idimg = '#imgToKhai' + CurrentImg;
                            $('#imgToKhai1').attr('src', data.filename);


                            $scope.tokhai.AMOUNT_OF_PASSPORT = $scope.ListToKhai.length;
                            $scope.tokhai.AMOUNT_OF_REGISTRATION = $scope.ListToKhai.length;
                            $scope.tokhai.RECEIPT_NO = '';
                            $scope.tokhai.RECEIPT_ID = '';

                            $scope.maxTK = $scope.ListToKhai.length;
                            CurrentIndex = $scope.ListToKhai.length - 1;

                            if ($scope.ListToKhai.length <= 1) {
                                $scope.DisabledPrevTK = true;
                                $scope.DisabledNextTK = true;
                            }
                            else {
                                $scope.currentTK = CurrentIndex + 1;
                                if (CurrentIndex == 0) {
                                    $scope.DisabledPrevTK = true;
                                    $scope.DisabledNextTK = false;
                                }
                                else if (CurrentIndex == ($scope.ListToKhai.length - 1)) {
                                    $scope.DisabledPrevTK = false;
                                    $scope.DisabledNextTK = true;
                                }
                                else {
                                    $scope.DisabledPrevTK = false;
                                    $scope.DisabledNextTK = false;
                                }
                            }

                            ShowToKhai(CurrentIndex);
                            if ($scope.tokhai.RECEIPT_NO != null && $scope.tokhai.RECEIPT_NO != "") {
                                LoadDataPrintBN($scope.tokhai, $scope.tokhai.RECEIPT_NO);
                            }

                            //tre em
                            $("#divTE_TK").show();
                            $("#divTE_Ten").hide();
                            if (data.listtreem != null) {
                                data.listtreem.forEach(function (entry) {
                                    $scope.ListTreEm.push(entry);
                                });
                            }
                            if (tokhai.DOCUMENT_TYPE == "ONLINE") {
                                $scope.cvt = false;
                                GetDataVanTay();
                            }
                            // Hiển thị lịch sử cấp phát hộ chiếu
                            TimKiemLSCHC();
                            //  $('#quetvantay').modal('show'); // ThaoNV
                            $("#btnInBienNhan").prop('disabled', false);
                            $scope.CheckTiepNhan = false;
                            $scope.CheckBN = false;
                            $scope.$apply();
                        }

                    } else {
                        if (data.message == '') {
                            //var gt = {
                            //    id: $scope.IdGiayToKhac,
                            //    b64: data.b64
                            //}

                            //$scope.ListGiayToKhac.push(gt);
                            //$scope.IdGiayToKhac++;
                            //$scope.$apply();

                            toastr["error"]("Quét tờ khai thất bại, vui lòng kiểm tra và thử lại");
                        }
                        else {
                            toastr["error"](data.message);
                        }
                    }

                    $("#file").val('');
                },
                cache: false,
                contentType: false,
                processData: false
            });

            return false;
        });

        $("#frmUploadAttachment").submit(function () {

            var input = document.getElementById('fileAttachment');
            var listFile = input.files;

            var sogt = $scope.ListGiayToKhac.length;

            if (sogt + listFile.length > 20) {
                toastr["error"]("Chỉ có thể tải lên tối đa 20 tệp đính kèm !");
                $("#fileAttachment").val('');
                return false;
            }

            var formData = new FormData();

            var i = 0, len = listFile.length, img, reader, file;

            for (; i < len; i++) {
                file = listFile[i];

                if (window.FileReader) {
                    reader = new FileReader();
                    reader.onloadend = function (e) {
                        //showUploadedItem(e.target.result, file.fileName);
                    };
                    reader.readAsDataURL(file);
                }
                if (formData) {
                    formData.append("file", file);
                }
            }

            $seft.pageloadding = true;
            $.ajax({
                url: "/TiepNhan/UpDinhKem",
                type: 'POST',
                data: formData,
                success: function (data) {
                    $seft.pageloadding = false;
                    data.listfile.forEach(function (entry) {
                        entry.ID = $scope.IdGiayToKhac;
                        $scope.ListGiayToKhac.push(entry);
                        $scope.IdGiayToKhac++;
                    });

                    if (data.fileloi != '') {
                        toastr["error"]("Không thể tải lên các file có dung lượng lớn hơn 1Mb hoặc file không phải định dạng ảnh: " + data.fileloi);
                    }
                    $("#fileAttachment").val('');
                    $scope.$apply();
                },
                error: function (jqXHR, textStatus, errorMessage) {
                    $seft.pageloadding = false;
                    $("#fileAttachment").val('');
                    $scope.$apply();
                },
                cache: false,
                contentType: false,
                processData: false
            });

            $("#fileAttachment").val('');
            return false;
        })

        $("#frmUploadTreEm").submit(function () {
            var formData = new FormData(this);
            $seft.pageloadding = true;
            $.ajax({
                url: "/TiepNhan/UpAnhTreEm",
                type: 'POST',
                data: formData,
                async: true,
                success: function (data) {
                    $seft.pageloadding = false;

                    $scope.CurrentTreEm.ANH = data.b64;

                    var imgsrc = 'data:image/png;base64, ' + data.b64;
                    $('#imgTreEm').attr('src', imgsrc);

                    $("#fileTreEm").val('');
                },
                error: function (jqXHR, textStatus, errorMessage) {
                    $seft.pageloadding = false;
                    console.log(errorMessage);
                },
                cache: false,
                contentType: false,
                processData: false
            });

            return false;
        });

        function ShowToKhai(index) {
            var tokhai = $scope.ListToKhai[index];

            //var ngaysinh = new Date();
            //if (tokhai.DATE_OF_BIRTH != '')
            //{
            //    var d = parseInt(tokhai.DATE_OF_BIRTH.replace(/\D/g, ''));
            //    ngaysinh = new Date(d);
            //}


            if (tokhai.FULL_NAME != '') {
                $scope.tokhai.DOC_ID = tokhai.DOC_ID;
                $scope.tokhai.FULL_NAME = tokhai.FULL_NAME;
                $scope.tokhai.DATE_OF_BIRTH = tokhai.DATE_OF_BIRTH;
                $scope.tokhai.PID_NO = tokhai.PID_NO;
                $scope.tokhai.SEARCH_NAME = tokhai.SEARCH_NAME;
                $scope.tokhai.GENDER = tokhai.GENDER;
                $scope.tokhai.PLACE_OF_BIRTH_ID = tokhai.PLACE_OF_BIRTH_ID;
                $scope.tokhai.ADDRESS = tokhai.ADDRESS;
                $scope.tokhai.ADDRESS_TEXT = tokhai.ADDRESS;
                $scope.tokhai.PREV_PASSPORT_NO = tokhai.PREV_PASSPORT_NO;
                $scope.tokhai.AMOUNT_OF_DOCUMENT = tokhai.AMOUNT_OF_DOCUMENT;
                $scope.tokhai.ADDED_DOCUMENTS = tokhai.ADDED_DOCUMENTS;
                $scope.tokhai.DOCUMENTARY_NO = tokhai.DOCUMENTARY_NO;
                $scope.tokhai.PERSON_ID = tokhai.PERSON_ID;
                if (tokhai.DATE_OF_BIRTH != null) {
                    var countNgaySinh = (tokhai.DATE_OF_BIRTH.match(/\//g) || []).length
                    if (countNgaySinh == 2) {
                        $scope.NgaySinh = "0";
                    }
                    else if (countNgaySinh == 1) {
                        $scope.NgaySinh = "1";
                    } else {
                        $scope.NgaySinh = "2";
                    }
                }
                $scope.tokhai.DELIVERY_AT_OFFICE = tokhai.DELIVERY_AT_OFFICE;
                if (tokhai.DELIVERY_AT_OFFICE == "Y") {
                    $scope.CheckNopHS = true;
                }
                else {
                    $scope.CheckNopHS = false;
                }

                var today = new Date();
                $scope.tokhai.DOCUMENTARY_ISSUED = today;
                if (tokhai.DOCUMENTARY_ISSUED != '') {
                    $scope.tokhai.DOCUMENTARY_ISSUED = tokhai.DOCUMENTARY_ISSUED;
                }

                $scope.tokhai.NOTE = tokhai.NOTE;
                $scope.tokhai.DOCUMENT_TYPE = tokhai.DOCUMENT_TYPE;
                $scope.tokhai.REGISTRATION_NO = tokhai.REGISTRATION_NO;
                $scope.currentTK = index + 1;

                var imgsrc = 'data:image/png;base64, ' + tokhai.IMAGEB64;
                $('#imgToKhai1').attr('src', imgsrc);

                var anhmat = 'data:image/png;base64, ' + tokhai.PERSON_IMG;
                $('#imgPerson').attr('src', anhmat);

                //Type ngay sinh
                var countNgaySinh = (tokhai.DATE_OF_BIRTH.match(/\//g) || []).length
                var TypeNgaySinh = "0";
                if (countNgaySinh == 2) {
                    TypeNgaySinh = "0";
                }
                else if (countNgaySinh == 1) {
                    TypeNgaySinh = "1";
                } else {
                    TypeNgaySinh = "2";
                }
                var searchItemNS = $filter("filter")($scope.ListTypeNgaySinh, { id: TypeNgaySinh }, true);
                if (searchItemNS.length > 0) {
                    $scope.NgaySinh = searchItemNS[0];
                }

                //var searchItem = $filter("filter")($scope.ListType, { id: tokhai.DOCUMENT_TYPE }, true);
                //if (searchItem.length > 0) {
                    //$scope.tokhai.DOCUMENT_TYPE = searchItem[0];
                   // $scope.tokhai.DOCUMENT_TYPE = searchItem[0].id;
                //}
                
                $scope.ChangeDateIssue();
                CheckDocumentType();

                //$("#btnInBienNhan").prop('disabled', false);
                //$("#btnInLai").prop('disabled', true);               
                //$("#liHuy").addClass('DisabledButton');
                //$scope.CheckBN = false;

                $("#btnInBienNhan").prop('disabled', true);
                $("#btnInLai").prop('disabled', true);
                if (tokhai.RECEIPT_ID != null && tokhai.RECEIPT_ID != '') {
                    $("#btnInLai").prop('disabled', false);
                }
                else {
                    $("#btnInBienNhan").prop('disabled', false);
                }
                $("#liHuy").addClass('DisabledButton');
                $scope.CheckBN = true;
                if (tokhai.IS_CHARED == 'N' && tokhai.RECEIPT_STATUS == 'R') {
                    $("#liHuy").removeClass('DisabledButton');
                    $scope.CheckBN = false;
                }

                if (tokhai.RECEIPT_STATUS == 'H') {
                    $("#liHuy").addClass('DisabledButton');
                }

                //$scope.$apply();
            }
        }

        $scope.ChangeNoiNhanKetQua = function () {
            $scope.CheckNopHS = false;
        }


        $scope.CheckNoiNhanHS = function () {
            if ($scope.CheckNopHS == true) {
                $scope.tokhai.PLACE_OF_RECEIPT = $scope.ListOffice[0].NAME;
                $scope.tokhai.DELIVERY_AT_OFFICE == "Y";
                //$("#cbOffice").prop('disabled', true);
            }
            else {
                $scope.tokhai.DELIVERY_AT_OFFICE == "N";
            }
        }

        $scope.XoaGiayTo = function (ID) {
            gConfirm('Có chắc xóa giấy tờ này ?', function () {
                var searchItem = $filter("filter")($scope.ListGiayToKhac, { ID: ID }, true);
                if (searchItem.length > 0) {
                    var index = $scope.ListGiayToKhac.indexOf(searchItem[0]);
                    $scope.ListGiayToKhac.splice(index, 1);
                    $scope.$apply();
                }
            })
        }

        $scope.XoaToKhai = function () {
            if ($scope.maxTK > 0) {
                gConfirm('Có chắc xóa tờ khai này ?', function () {
                    var tokhai = $scope.ListToKhai[CurrentIndex];
                    $scope.ListToKhai.splice(CurrentIndex, 1);
                    CurrentIndex = 0;
                    $scope.maxTK--;

                    if ($scope.maxTK > 0) {
                        ShowToKhai(CurrentIndex);
                    }
                    else {
                        location.reload();
                    }

                    $scope.$apply();
                })
            }
        }

        $scope.ShowToKhaiHangDoi = function (index, tokhai) {
            $scope.selectedRowHangDoi = index;
            if (tokhai != null && tokhai.FULL_NAME != '') {

                
                $scope.tokhai.DOC_ID = tokhai.DOC_ID;
                $scope.tokhai.RECEIPT_ID = tokhai.RECEIPT_ID;
                $scope.tokhai.RECEIPT_NO = tokhai.RECEIPT_NO;
                $scope.tokhai.FULL_NAME = tokhai.FULL_NAME;
                $scope.tokhai.DELIVERY_AT_OFFICE = tokhai.DELIVERY_AT_OFFICE;
                if (tokhai.DELIVERY_AT_OFFICE == "Y") {
                    $scope.CheckNopHS = true;
                }
                else {
                    $scope.CheckNopHS = false;
                }
                //if (havebd) {
                //    $scope.tokhai.DATE_OF_BIRTH = tokhai.DATE_OF_BIRTH;
                //}
                $scope.tokhai.GENDER = tokhai.GENDER;
                $scope.tokhai.PLACE_OF_BIRTH_ID = tokhai.PLACE_OF_BIRTH_ID;
                $scope.tokhai.SEARCH_NAME = tokhai.SEARCH_NAME;
                $scope.tokhai.DATE_OF_BIRTH = tokhai.DATE_OF_BIRTH_TEXT;
                $scope.tokhai.PID_NO = tokhai.PID_NO;
                $scope.tokhai.ADDRESS = tokhai.ADDRESS;
                $scope.tokhai.ADDRESS_TEXT = tokhai.ADDRESS_TEXT;
                $scope.tokhai.PREV_PASSPORT_NO = tokhai.PREV_PASSPORT_NO;
                $scope.tokhai.PERSON_ID = tokhai.PERSON_ID;
                $scope.tokhai.AMOUNT_OF_DOCUMENT = tokhai.AMOUNT_OF_DOCUMENT;
                $scope.tokhai.ADDED_DOCUMENTS = tokhai.ADDED_DOCUMENTS;
                $scope.tokhai.DOCUMENTARY_NO = tokhai.DOCUMENTARY_NO;
                $scope.tokhai.DOCUMENTARY_ISSUED = tokhai.DOCUMENTARY_ISSUED_TEXT;
                $scope.tokhai.DATE_OF_ISSUE = tokhai.DATE_OF_ISSUE_TEXT;
                $scope.tokhai.NOTE = tokhai.NOTE;
                $scope.tokhai.DOCUMENT_TYPE = tokhai.DOCUMENT_TYPE;
                $scope.tokhai.DATE_IN_WEEK = tokhai.DATE_IN_WEEK;
                $scope.tokhai.AMOUNT_OF_PASSPORT = tokhai.AMOUNT_OF_PASSPORT;
                $scope.tokhai.AMOUNT_OF_REGISTRATION = tokhai.AMOUNT_OF_REGISTRATION;
                $scope.tokhai.PLACE_OF_RECEIPT = tokhai.PLACE_OF_RECEIPT;
                $scope.tokhai.IS_HAND_OVERED = tokhai.IS_HAND_OVERED;
                $scope.tokhai.IS_POST_OFFICE = false;
                if (tokhai.IS_POST_OFFICE_CHAR != null && tokhai.IS_POST_OFFICE_CHAR == "Y") {
                    $scope.tokhai.IS_POST_OFFICE = true;
                }
                if (tokhai.RECEIPT_STATUS == "R" || tokhai.RECEIPT_STATUS == "H") {
                    $scope.CheckTiepNhan = true;
                }
                else {
                    $scope.CheckTiepNhan = false;

                }

               // var searchItem = $filter("filter")($scope.ListType, { id: tokhai.DOCUMENT_TYPE }, true);
                //if (searchItem.length > 0) {
                    //$scope.tokhai.DOCUMENT_TYPE = searchItem[0];
                    //$scope.tokhai.DOCUMENT_TYPE = searchItem[0].id;
                //}
                CheckDocumentTypeHangDoi(tokhai);

                if (tokhai.RECEIPT_ID == "" && tokhai.RECEIPT_ID == null) {
                    var searchPlace = $filter("filter")($scope.ListOffice, { NAME: tokhai.PLACE_OF_RECEIPT }, true);
                    if (searchPlace.length > 0) {
                        $scope.tokhai.PLACE_OF_RECEIPT = searchPlace[0].NAME;
                    } else {
                        $scope.tokhai.PLACE_OF_RECEIPT = $scope.ListOffice[0].NAME;
                    }
                }
                else {
                    $scope.tokhai.PLACE_OF_RECEIPT = tokhai.PLACE_OF_RECEIPT;
                }

                //Type ngay sinh
                if (tokhai.DATE_OF_BIRTH != null) {
                    var countNgaySinh = (tokhai.DATE_OF_BIRTH.match(/\//g) || []).length
                    var TypeNgaySinh = "0"
                    if (countNgaySinh == 2) {
                        TypeNgaySinh = "0";
                    }
                    else if (countNgaySinh == 1) {
                        TypeNgaySinh = "1";
                    } else {
                        TypeNgaySinh = "2";
                    }
                    var searchItemNS = $filter("filter")($scope.ListTypeNgaySinh, { id: TypeNgaySinh }, true);
                    if (searchItemNS.length > 0) {
                        $scope.NgaySinh = searchItemNS[0];
                    }
                }


                $scope.ChangeDateIssue();

                //$scope.tokhai.REGISTRATION_NO = tokhai.REGISTRATION_NO;
                $scope.ListToKhai = [];
                $scope.maxTK = 0;
                $scope.currentTK = 0;
                CurrentIndex = 0;
                Index = 1;
                CurrentImg = 1;

                $('#imgToKhai1').attr('src', '');
                $('#imgPerson').attr('src', '');
                //$('#imgToKhai2').attr('src', '');
                //$('#imgToKhai3').attr('src', '');

                $("#btnInBienNhan").prop('disabled', true);
                $("#btnInLai").prop('disabled', true);
                if (tokhai.RECEIPT_ID != null && tokhai.RECEIPT_ID != '') {
                    $("#btnInLai").prop('disabled', false);
                }

                //$("#liCapNhat").prop('disabled', true);
                //$("#liHuy").prop('disabled', true);

                //$("#liCapNhat").addClass('DisabledButton');
                $("#liHuy").addClass('DisabledButton');
                $scope.CheckBN = true;
                if (tokhai.IS_CHARED == 'N' && tokhai.RECEIPT_STATUS == 'R') {
                    //$("#liCapNhat").prop('disabled', false);
                    //$("#liHuy").prop('disabled', false);
                    //$("#liCapNhat").removeClass('DisabledButton');
                    $("#liHuy").removeClass('DisabledButton');
                    $scope.CheckBN = false;
                }

                if (tokhai.RECEIPT_STATUS == 'H') {
                    $("#liHuy").addClass('DisabledButton');
                }

                //Load de nghi
                $seft.pageloadding = true;
                $.ajax({
                    url: "/TiepNhan/GetDeNghiHoSo",
                    type: 'POST',
                    data: {
                        receipt_id: tokhai.RECEIPT_ID,
                        receipt_no: tokhai.RECEIPT_NO,
                        doc_id: tokhai.DOC_ID
                    },
                    success: function (data) {
                        $scope.ListDeNghiChon = [];
                        data.list.forEach(function (entry) {
                            var denghi = {
                                'MADENGHI': entry.REQUIREMENT_CODE,
                                'TENDENGHI': entry.DESCRIPTION,
                                'DONGIA': entry.PRICE,
                                'DVT': entry.UNIT,
                                'SOLUONG': entry.AMOUNT,
                                'THANHTIEN': entry.TOTAL
                            }
                            $scope.ListDeNghiChon.push(denghi);
                        });
                        if ($scope.CheckTiepNhan == false) {
                            AddRowDeNghi();
                        }
                        if (data.listtokhai.length > 0) {

                            $scope.currentTK = 1;
                            $scope.maxTK = data.listtokhai.length;
                            if (data.listtokhai.length <= 1) {
                                $scope.DisabledPrevTK = true;
                                $scope.DisabledNextTK = true;
                            }
                            else {
                                $scope.DisabledPrevTK = true;
                                $scope.DisabledNextTK = false;
                            }
                        }
                        else {
                            $scope.DisabledPrevTK = true;
                            $scope.DisabledNextTK = true;
                        }

                        $('#imgToKhai1').attr('src', $scope.AnhTrangB64);
                        if (data.b64 != null && data.b64 != '') {
                            var imgsrc = 'data:image/png;base64, ' + data.b64;
                            $('#imgToKhai1').attr('src', imgsrc);
                        }

                        $('#imgPerson').attr('src', $scope.AnhTrangB64);
                        if (data.anhmat != null && data.anhmat != '') {
                            var anhmat = 'data:image/png;base64, ' + data.anhmat;
                            $('#imgPerson').attr('src', anhmat);
                        }

                        $scope.ListGiayToKhac = data.listgiayto;
                        $scope.ListTreEm = data.listtreem;
                        if (tokhai.DOC_ID != null && tokhai.DOC_ID > 0) {
                            $("#divTE_TK").show();
                            $scope.ListToKhai = data.listtokhai;
                            $scope.RegNoTE = $scope.ListToKhai[0];
                            if ($scope.RegNoTE != null && $scope.RegNoTE.DOC_ID != null) {
                                $scope.search.DOC_ID = $scope.RegNoTE.DOC_ID;
                            }
                        }
                        else {
                            $("#divTE_TK").hide();
                        }
                        $("#divTE_Ten").hide();

                        $scope.selectedRowTreEm = -1;
                        $scope.CurrentTreEm = {};
                        $('#imgTreEm').attr('src', $scope.AnhTrangB64);

                        //Get van tay
                        if ($scope.tokhai.DOCUMENT_TYPE == "ONLINE") {
                            $scope.cvt = false;
                            GetDataVanTay();
                        }
                       // GetDataVanTay();
                        LoadDataPrintBN(tokhai, tokhai.RECEIPT_NO);
                        $seft.pageloadding = false;
                        $scope.$apply();
                    }, error: function (jqXHR, textStatus, errorMessage) {
                        $seft.pageloadding = false;
                        console.log(errorMessage);
                    }
                });

                LoadDataPrintBN(tokhai, tokhai.RECEIPT_NO);
            }
        }

        $scope.HuyBienNhan = function () {
            if ($scope.tokhai != null) {
                if ($scope.tokhai.IS_HAND_OVERED == 'N') {
                    gConfirm('Có chắc chắn muốn hủy biên nhận này ?', function () {
                        $seft.pageloadding = true;
                        $.ajax({
                            url: "/TiepNhan/HuyBienNhan",
                            type: 'POST',
                            data: {
                                receipt_no: $scope.tokhai.RECEIPT_NO
                            },
                            success: function (data) {
                                $seft.pageloadding = false;
                                if (data.status = 1) {
                                    toastr["success"]("Hủy biên nhận thành công !");
                                    LoadHangDoi();
                                    ClearText();
                                }
                                else {
                                    toastr["error"]("Hủy biên nhận thất bại, có lỗi xảy ra !");
                                }
                                $scope.$apply();
                            }, error: function (jqXHR, textStatus, errorMessage) {
                                $seft.pageloadding = false;
                                console.log(errorMessage);
                            }
                        });
                    })
                }
                else {
                    toastr["error"]("Hồ sơ đã lập danh sách tiếp nhận. Không được hủy !");
                }
            }
        };

        $scope.CapNhat = function () {
            if ($scope.tokhai != null) {
                gConfirm('Có chắc chắn muốn cập nhật ?', function () {
                    var tokhai = $scope.tokhai;

                    if (tokhai.FULL_NAME == null || tokhai.FULL_NAME == '') {
                        toastr["error"]("Chưa nhập họ tên !");
                        return;
                    }
                    if (tokhai.PID_NO == null || tokhai.PID_NO == '' || tokhai.PID_NO.length > 12) {
                        toastr["error"]("Số CMND/CCCD không hợp lệ !");
                        return;
                    }
                    if (tokhai.DATE_OF_ISSUE == null || tokhai.DATE_OF_ISSUE == '') {
                        toastr["error"]("Chưa chọn ngày hẹn trả !");
                        return;
                    }
                    if ($scope.ListDeNghiChon.length == 0) {
                        toastr["error"]("Chưa chọn đề nghị !");
                        return;
                    }

                    var isValid = true;
                    var qty = 0;
                    $scope.ListDeNghiChon.forEach(function (entry) {
                        qty += entry.soluong;
                        if (entry.soluong <= 0) {
                            isValid = false;
                        }
                    });

                    if (!isValid || qty <= 0) {
                        toastr["error"]("Số lượng đề nghị không hợp lệ !");
                        return;
                    }

                    tokhai.PLACE_OF_RECEIPT = tokhai.PLACE_OF_RECEIPT;
                    //tokhai.DOCUMENT_TYPE_ID = tokhai.DOCUMENT_TYPE.id;
                    tokhai.DOCUMENT_TYPE_ID = tokhai.DOCUMENT_TYPE;

                    $seft.pageloadding = true;
                    $.ajax({
                        url: "/TiepNhan/CapNhatBienNhan",
                        type: 'POST',
                        data: {
                            tokhai: tokhai,
                            listdenghi: $scope.ListDeNghiChon,
                            listgiayto: $scope.ListGiayToKhac
                        },
                        success: function (data) {
                            $seft.pageloadding = false;
                            if (data.message == 'success') {
                                toastr["success"]("Cập nhật biên nhận thành công !");
                                LoadHangDoi();
                            }
                            else {
                                toastr["error"]("Cập nhật biên nhận thất bại, có lỗi xảy ra !");
                                console.log(data.message);
                            }
                            $scope.$apply();
                        }, error: function (jqXHR, textStatus, errorMessage) {
                            $seft.pageloadding = false;
                            console.log(errorMessage);
                        }
                    });
                })
            }
        };

        function LoadHangDoi() {
            ClearBienNhan();
            $scope.DisabledPrevTK = true;
            $scope.DisabledNextTK = true;
            $scope.ListHangDoi = [];
            //var tungay = new Date($scope.hangdoi.TuNgay).toISOString();
            //var denngay = new Date($scope.hangdoi.DenNgay).toISOString();
            $seft.pageloadding = true;
            $.ajax({
                url: "/TiepNhan/GetListHangDoi",
                type: 'POST',
                data: {
                    search_text: $scope.hangdoi.TimKiem,
                    from_date: $scope.hangdoi.TuNgay,
                    to_date: $scope.hangdoi.DenNgay,
                    status: $scope.hangdoi.TrangThai,
                },
                success: function (data) {
                    $scope.ListHangDoi = data.list;
                    $scope.totalRecordHangDoi = $scope.ListHangDoi.length;
                    if (data.officeName != null) {
                        $scope.OfficeName = data.officeName;
                    }
                    $seft.pageloadding = false;
                    $scope.$apply();
                }, error: function (jqXHR, textStatus, errorMessage) {
                    $seft.pageloadding = false;
                    $scope.$apply();
                }
            });
        }
        setTimeout(function () {
            LoadHangDoi();

            $scope.CheckNopHS = true;
            if ($scope.ListOffice != null && $scope.ListOffice.length > 0) {
                $scope.tokhai.PLACE_OF_RECEIPT = $scope.ListOffice[0].NAME;
            }
            $("#cbOffice").prop('disabled', true);

            var searchDIW = $filter("filter")($scope.ListDayInWeek, { id: issueDateWeekDay }, true);
            if (searchDIW.length > 0) {
                $scope.tokhai.DATE_IN_WEEK = searchDIW[0];
            }
        }, 500);

        //action: 0: next, 1: back
        $scope.ChuyenToKhai = function (action) {
            if (action == 0 && CurrentIndex < $scope.ListToKhai.length - 1) {
                CurrentIndex++;
            }

            if (action == 1 && CurrentIndex != 0) {
                CurrentIndex--;
            }
            if ($scope.ListToKhai.length <= 1) {
                $scope.DisabledPrevTK = true;
                $scope.DisabledNextTK = true;
            }
            else {
                $scope.currentTK = CurrentIndex + 1;
                if (CurrentIndex == 0) {
                    $scope.DisabledPrevTK = true;
                    $scope.DisabledNextTK = false;
                }
                else if (CurrentIndex == ($scope.ListToKhai.length - 1)) {
                    $scope.DisabledPrevTK = false;
                    $scope.DisabledNextTK = true;
                }
                else {
                    $scope.DisabledPrevTK = false;
                    $scope.DisabledNextTK = false;
                }
            }

            ShowToKhai(CurrentIndex);
        }

        $scope.ShowDetailLichSuHC = function (index, item) {
            $scope.LichSuCapPhatDetail = item;
            $scope.selectedRowLSCHC = index;
        }

        function LoadDSDeNghi() {
            $seft.pageloadding = true;
            $.ajax({
                url: "/TiepNhan/GetListDeNghi",
                type: 'POST',
                success: function (data) {
                    $scope.ListDeNghi = data.list;
                    //$scope.denghi = data.list[0];
                    //$scope.ChonDeNghi();
                    $scope.$apply();
                    $seft.pageloadding = false;
                    $scope.deNghi = {
                        MADENGHI: "",
                        TENDENGHI: "",
                        DONGIA: 0,
                        DVT: "VND",
                        SOLUONG: 0,
                        THANHTIEN: 0
                    }
                    AddRowDeNghi();



                }, error: function (jqXHR, textStatus, errorMessage) {
                    $seft.pageloadding = false;
                }
            });
        }

        function LoadOffice() {
            $seft.pageloadding = true;
            $.ajax({
                url: "/TiepNhan/GetListOffice",
                type: 'POST',
                success: function (data) {
                    $seft.pageloadding = false;
                    if (data.list != null) {
                        $scope.ListOffice = data.list;
                        $scope.tokhai.PLACE_OF_RECEIPT = data.list[0].NAME;
                    }
                    $scope.$apply();
                }, error: function (jqXHR, textStatus, errorMessage) {
                    $seft.pageloadding = false;
                    $scope.$apply();
                }
            });
        }
        function LoadGender() {
            $seft.pageloadding = true;
            $.ajax({
                url: "/TiepNhan/GetListGender",
                type: 'POST',
                success: function (data) {
                    $seft.pageloadding = false;
                    if (data.genders != null) {
                        $scope.ListGender = data.genders;
                    }
                    if (data.offices != null) {
                        $scope.ListOfficeLSCHC = data.offices;
                    }
                    $scope.$apply();
                }, error: function (jqXHR, textStatus, errorMessage) {
                    $seft.pageloadding = false;
                    $scope.$apply();
                }
            });
        }

        function loadIframe(url) {
            var ifr = $('<iframe/>', {
                id: 'AdminFrame',
                src: url,
                frameborder: 0,
                style: 'width:100%;height:100%',
                load: function () {
                    $(this).show();
                    $scope.pageloadding = false;
                    $("#quayquaytiepnhan").addClass("ng-hide");
                }
            });
            $('#WrapIframe').html(ifr);
        }

        $scope.InfoLichSuCapPhat = {};

        //$("#modalHC").on('shown.bs.modal', function (e) {

        //    TimKiemLSCHC();
        //});

        $scope.SearchLichSuCapHC = function () {
            TimKiemLSCHC();
        }

        function TimKiemLSCHC() {

            $scope.LichSuCapPhat = [];
            $scope.SearchLS.FULL_NAME = "";
            $scope.SearchLS.DATE_OF_BIRTH = "";
            $scope.SearchLS.GENDER = "";
            $scope.SearchLS.PLACE_OF_BIRTH = 0;
            $scope.SearchLS.ID_NUMBER = $scope.tokhai.PID_NO;
            $scope.SearchLS.PERSON_ID = $scope.tokhai.PERSON_ID;
            $scope.SearchLS.PREV_PASSPORT_NO = $scope.tokhai.PREV_PASSPORT_NO;

            if (
                //($scope.SearchLS.FULL_NAME == null || $scope.SearchLS.FULL_NAME == "") &&
                ($scope.SearchLS.ID_NUMBER == null || $scope.SearchLS.ID_NUMBER == "")) {
                toastr["error"]("Bạn chưa chọn hồ sơ cần tra cứu.");
                return;
            }
            else {
                $.ajax({
                    url: "/TiepNhan/GetLichSuCapPhatHC",
                    type: 'POST',
                    data: {
                        PID_NO: $scope.SearchLS.ID_NUMBER, SEARCH_NAME: $scope.SearchLS.FULL_NAME, GENDER: $scope.SearchLS.GENDER,
                        DATE_OF_BIRTH: $scope.SearchLS.DATE_OF_BIRTH, PLACE_OF_BIRTH_ID: $scope.SearchLS.PLACE_OF_BIRTH,
                        PERSON_ID: $scope.SearchLS.PERSON_ID, PREV_PASSPORT_NO: $scope.SearchLS.PREV_PASSPORT_NO
                    },
                    success: function (data) {
                        $scope.LichSuCapPhat = data.list;
                        if (data.list.length > 0) {
                            $scope.InfoLichSuCapPhat.NGAY_TIEP_NHAN_TEXT = data.list[0].NGAY_TIEP_NHAN_TEXT;
                            $scope.LichSuCapPhatDetail = $scope.LichSuCapPhat[0];
                            $('#modalHC').modal('show');
                        }
                        else {
                            $('#modalHC').modal('hide');
                        }
                        $seft.pageloadding = false;
                        $scope.$apply();
                    }, error: function (jqXHR, textStatus, errorMessage) {
                        $seft.pageloadding = false;
                        $scope.$apply();
                    }
                });
            }
        }

        // Chụp vân tay
        $seft.eppSave = function () {
            $seft.pageloadding = true;
            $.ajax({
                type: 'POST',
                cache: false,
                url: "/TiepNhan/DeleteEppDocAttachment",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                crossDomain: true,
                data: JSON.stringify({ 'documentId': $scope.tokhai.DOC_ID, 'dStatus': true }),
                success: function (data) {
                    listData = [];
                    ip = 0;
                    for (var i = 0; i < dataPrint.length; i++) {
                        objectTenPrint = {
                            DOCUMENT_ID: $scope.tokhai.DOC_ID,
                            TYPE_: 'FINGERPRINT',
                            FILE_NAME: $scope.tokhai.PERSON_ID + '_' + dataPrint[i].name,
                            FILE_ID: '',
                            DESCRIPTION: 'Vân tay',
                            SERIAL_NO: dataPrint[i].name,
                            BMP_BASE64: dataPrint[i].img, // Lưu PC2
                            WSQ_BASE64: dataPrintBmp[i].img // Lưu WSQ
                        }
                        listData.push(objectTenPrint);
                    }
                    PostDataTenPrint(listData[ip]);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR);
                    console.log(textStatus);
                    console.log(errorThrown);
                }
            });
        }

        function PostDataTenPrint(data) {
            $.ajax({
                type: 'POST',
                cache: false,
                url: "/TiepNhan/InsertEppDocAttachment",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                crossDomain: true,
                data: JSON.stringify({ 'eda': data }),
                success: function (response) {
                    if (ip < dataPrint.length) {
                        ip++;
                        PostDataTenPrint(listData[ip]);
                    } else {
                        toastr["success"]("Lưu dữ liệu thành công!");
                        $seft.pageloadding = false;
                        $scope.$apply();
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR);
                    console.log(textStatus);
                    console.log(errorThrown);
                }
            });
        }

        $seft.TenPrintCapture = function (id, name) {
            InitTPDevice(id, name);
        }

        function GetDataVanTay() {
            ping($rootScope.baseUrl, function (status, e) {
                if (status.status != 0) {
                    $.ajax({
                        type: 'GET',
                        cache: false,
                        url: '/TiepNhan/GetPerson',
                        contentType: "application/json; charset=utf-8",
                        dataType: "json",
                        crossDomain: true,
                        data: { regNo: $scope.tokhai.REGISTRATION_NO, idNumber: $scope.tokhai.PID_NO },
                        success: function (response) {
                            var data = response;
                            if (data.person != null) {
                                if (data.attachment.length > 0) {
                                    dataPrint = [];
                                    dataPrintBmp = [];
                                    if (data.attachment.length > 0) {
                                        for (var i = 0; i < data.attachment.length; i++) {

                                            if (data.attachment[i].TYPE_ == 'MINUTIAE_PC2' && data.attachment[i].WSQ_BASE64 != null) {
                                                var objQ = { name: data.attachment[i].SERIAL_NO, img: data.attachment[i].WSQ_BASE64 };
                                                dataPrint.push(objQ);
                                            }
                                            if (data.attachment[i].TYPE_ == 'FINGERPRINT' && data.attachment[i].BMP_BASE64 != null) {
                                                var objB = { name: data.attachment[i].SERIAL_NO, img: data.attachment[i].BMP_BASE64 };
                                                dataPrintBmp.push(objB);
                                            }
                                        }
                                    }
                                    if (dataPrintBmp.length > 0) {
                                        $seft.isfp = true;
                                        for (var i = 0; i < dataPrintBmp.length; i++) {
                                            switch (dataPrintBmp[i].name) {
                                                case 1:
                                                    $seft.fp1 = "data:image/png;base64," + ConvertFingerWSQToBMP($rootScope.baseUrl, dataPrintBmp[i].img);
                                                    break;
                                                case 2:
                                                    $seft.fp2 = "data:image/png;base64," + ConvertFingerWSQToBMP($rootScope.baseUrl, dataPrintBmp[i].img);
                                                    break;
                                                case 3:
                                                    $seft.fp3 = "data:image/png;base64," + ConvertFingerWSQToBMP($rootScope.baseUrl, dataPrintBmp[i].img);
                                                    break;
                                                case 4:
                                                    $seft.fp4 = "data:image/png;base64," + ConvertFingerWSQToBMP($rootScope.baseUrl, dataPrintBmp[i].img);
                                                    break;
                                                case 5:
                                                    $seft.fp5 = "data:image/png;base64," + ConvertFingerWSQToBMP($rootScope.baseUrl, dataPrintBmp[i].img);
                                                    break;
                                                case 6:
                                                    $seft.fp6 = "data:image/png;base64," + ConvertFingerWSQToBMP($rootScope.baseUrl, dataPrintBmp[i].img);
                                                    break;
                                                case 7:
                                                    $seft.fp7 = "data:image/png;base64," + ConvertFingerWSQToBMP($rootScope.baseUrl, dataPrintBmp[i].img);
                                                    break;
                                                case 8:
                                                    $seft.fp8 = "data:image/png;base64," + ConvertFingerWSQToBMP($rootScope.baseUrl, dataPrintBmp[i].img);
                                                    break;
                                                case 9:
                                                    $seft.fp9 = "data:image/png;base64," + ConvertFingerWSQToBMP($rootScope.baseUrl, dataPrintBmp[i].img);
                                                    break;
                                                case 10:
                                                    $seft.fp10 = "data:image/png;base64," + ConvertFingerWSQToBMP($rootScope.baseUrl, dataPrintBmp[i].img);
                                                    break;
                                            }

                                        }
                                    } else {
                                        $seft.icaoface1 = null;
                                        $scope.icaofaceimg = null;
                                        $seft.isface = false;
                                        $seft.isfp = false;
                                        $seft.fp1 = null;
                                        $seft.fp2 = null;
                                        $seft.fp3 = null;
                                        $seft.fp4 = null;
                                        $seft.fp5 = null;
                                        $seft.fp6 = null;
                                        $seft.fp7 = null;
                                        $seft.fp8 = null;
                                        $seft.fp9 = null;
                                        $seft.fp10 = null;
                                        // $seft.TenPrintCapture($scope.tokhai.DOC_ID, $scope.tokhai.FULL_NAME);
                                    }
                                }
                                else {
                                    $seft.icaoface1 = null;
                                    $scope.icaofaceimg = null;
                                    $seft.isface = false;
                                    $seft.isfp = false;
                                    $seft.fp1 = null;
                                    $seft.fp2 = null;
                                    $seft.fp3 = null;
                                    $seft.fp4 = null;
                                    $seft.fp5 = null;
                                    $seft.fp6 = null;
                                    $seft.fp7 = null;
                                    $seft.fp8 = null;
                                    $seft.fp9 = null;
                                    $seft.fp10 = null;
                                    // $seft.TenPrintCapture($scope.tokhai.DOC_ID, $scope.tokhai.FULL_NAME);
                                }

                            }
                            else {
                                $scope.imageSrc = null;
                                $seft.isfacet = false;
                                $seft.icaoface1 = null;
                                $scope.icaofaceimg = null;
                                $seft.isface = false;
                                $seft.isfp = false;
                                $seft.fp1 = null;
                                $seft.fp2 = null;
                                $seft.fp3 = null;
                                $seft.fp4 = null;
                                $seft.fp5 = null;
                                $seft.fp6 = null;
                                $seft.fp7 = null;
                                $seft.fp8 = null;
                                $seft.fp9 = null;
                                $seft.fp10 = null;
                                // $seft.TenPrintCapture($scope.tokhai.DOC_ID, $scope.tokhai.FULL_NAME);
                            }
                            $seft.pageloadding = false;
                            $scope.$apply();

                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.log(jqXHR);
                            console.log(textStatus);
                            console.log(errorThrown);
                        }
                    });
                }
            })

        }

        function ping(host, pong) {
            var started = new Date().getTime();

            var http = new XMLHttpRequest();

            http.open("GET", host, /*async*/true);
            http.onreadystatechange = function () {
                if (http.readyState == 4) {
                    if (pong != null) {
                        pong(http);
                    }
                }
            };
            try {
                http.send(null);
            } catch (exception) {
                // this is expected
            }

        }

        function InitTPDevice(id, name) {
            $seft.pageloadding = true;
            jQuery.support.cors = true;
            var device_id = 3;
            var show_init = true;
            var postData = {
                deviceID: device_id,
                bShow: show_init
            };
            var dataSet = JSON.stringify(postData);
            var urlString = $rootScope.baseUrl + "SPID_TP_InitDev";

            $.ajax({
                type: 'POST',
                cache: false,
                url: urlString,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                crossDomain: true,
                data: JSON.stringify(postData),
                success: function (response) {
                    CaptureTenprintSC(id, name);
                    $seft.pageloadding = false;
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    toastr["error"]("Máy client không có kết nối SPID. Vui lòng kiểm tra kết nối.");
                    $seft.pageloadding = false;
                    $scope.$apply();
                }
            });
        }

        function CaptureTenprint() {
            jQuery.support.cors = true;
            var capture_mode = 0;
            var finger_pos = 0;
            var postData = {
                mode: capture_mode,
                fingerPos: finger_pos
            };
            var dataSet = JSON.stringify(postData);
            var urlString = $rootScope.baseUrl + "SPID_TP_Capture";
            $.ajax({
                type: 'POST',
                cache: false,
                url: urlString,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                crossDomain: true,
                data: JSON.stringify(postData),

                success: function (response) {
                    var tp_result = response;
                    if (tp_result.Result == 0) {
                        var fpImage1 = tp_result.FPImg1;
                        $seft.isfp = true;
                        $seft.fp1 = "data:image/bmp;base64," + fpImage1;
                        $scope.$apply();
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR);
                    console.log(textStatus);
                    console.log(errorThrown);
                }
            });
        }

        function CaptureTenprintSC(id, name) {
            jQuery.support.cors = true;
            dataPrint = [];
            dataPrintBmp = [];
            var sc_id = id;
            var sc_name = name;
            var sc_dob = '19900101';
            var postData = {
                strID: sc_id,
                strName: sc_name,
                strDOB: sc_dob
            };
            var dataSet = JSON.stringify(postData);
            var urlString = $rootScope.baseUrl + "SPID_TP_CaptureSC";
            $.ajax({
                type: 'POST',
                cache: false,
                url: urlString,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                crossDomain: true,
                data: JSON.stringify(postData),

                success: function (response) {
                    var sc_result = response;
                    if (sc_result.Result == 0) {
                        dataPrint.push({ name: '1', img: sc_result.PC2_1 });
                        dataPrint.push({ name: '2', img: sc_result.PC2_2 });
                        dataPrint.push({ name: '3', img: sc_result.PC2_3 });
                        dataPrint.push({ name: '4', img: sc_result.PC2_4 });
                        dataPrint.push({ name: '5', img: sc_result.PC2_5 });
                        dataPrint.push({ name: '6', img: sc_result.PC2_6 });
                        dataPrint.push({ name: '7', img: sc_result.PC2_7 });
                        dataPrint.push({ name: '8', img: sc_result.PC2_8 });
                        dataPrint.push({ name: '9', img: sc_result.PC2_9 });
                        dataPrint.push({ name: '10', img: sc_result.PC2_10 });
                        dataPrintBmp.push({ name: '1', img: sc_result.FPImg1 });
                        dataPrintBmp.push({ name: '2', img: sc_result.FPImg2 });
                        dataPrintBmp.push({ name: '3', img: sc_result.FPImg3 });
                        dataPrintBmp.push({ name: '4', img: sc_result.FPImg4 });
                        dataPrintBmp.push({ name: '5', img: sc_result.FPImg5 });
                        dataPrintBmp.push({ name: '6', img: sc_result.FPImg6 });
                        dataPrintBmp.push({ name: '7', img: sc_result.FPImg7 });
                        dataPrintBmp.push({ name: '8', img: sc_result.FPImg8 });
                        dataPrintBmp.push({ name: '9', img: sc_result.FPImg9 });
                        dataPrintBmp.push({ name: '10', img: sc_result.FPImg10 });
                        $seft.isfp = true;
                        var image_format = sc_result.FPImgFormat;

                        if (image_format == 1 || image_format == 3 || image_format == 4 ||
                            image_format == 7 || image_format == 12) {
                            var fp1 = sc_result.FPImg1;
                            var fp2 = sc_result.FPImg2;
                            var fp3 = sc_result.FPImg3;
                            var fp4 = sc_result.FPImg4;
                            var fp5 = sc_result.FPImg5;
                            var fp6 = sc_result.FPImg6;
                            var fp7 = sc_result.FPImg7;
                            var fp8 = sc_result.FPImg8;
                            var fp9 = sc_result.FPImg9;
                            var fp10 = sc_result.FPImg10;
                            var slap1 = sc_result.LeftSlap;
                            var slap2 = sc_result.RightSlap;
                            var slap3 = sc_result.ThumbSlap;
                            switch (image_format) {
                                case 1:
                                    $seft.fpp = fp1;
                                    $seft.fp1 = "data:image/bmp;base64," + fp1;
                                    $seft.fp2 = "data:image/bmp;base64," + fp2;
                                    $seft.fp3 = "data:image/bmp;base64," + fp3;
                                    $seft.fp4 = "data:image/bmp;base64," + fp4;
                                    $seft.fp5 = "data:image/bmp;base64," + fp5;
                                    $seft.fp6 = "data:image/bmp;base64," + fp6;
                                    $seft.fp7 = "data:image/bmp;base64," + fp7;
                                    $seft.fp8 = "data:image/bmp;base64," + fp8;
                                    $seft.fp9 = "data:image/bmp;base64," + fp9;
                                    $seft.fp10 = "data:image/bmp;base64," + fp10;
                                    $seft.slap1 = "data:image/bmp;base64," + slap1;
                                    $seft.slap2 = "data:image/bmp;base64," + slap2;
                                    $seft.slap3 = "data:image/bmp;base64," + slap3;
                                    break;
                                case 3:
                                    $seft.fp1 = "data:image/jpeg;base64," + fp1;
                                    $seft.fp2 = "data:image/jpeg;base64," + fp2;
                                    $seft.fp3 = "data:image/jpeg;base64," + fp3;
                                    $seft.fp4 = "data:image/jpeg;base64," + fp4;
                                    $seft.fp5 = "data:image/jpeg;base64," + fp5;
                                    $seft.fp6 = "data:image/jpeg;base64," + fp6;
                                    $seft.fp7 = "data:image/jpeg;base64," + fp7;
                                    $seft.fp8 = "data:image/jpeg;base64," + fp8;
                                    $seft.fp9 = "data:image/jpeg;base64," + fp9;
                                    $seft.fp10 = "data:image/jpeg;base64," + fp10;
                                    $seft.slap1 = "data:image/jpeg;base64," + slap1;
                                    $seft.slap2 = "data:image/jpeg;base64," + slap2;
                                    $seft.slap3 = "data:image/jpeg;base64," + slap3;
                                    break;
                                case 4:
                                    $seft.fp1 = "data:image/tif;base64," + fp1;
                                    $seft.fp2 = "data:image/tif;base64," + fp2;
                                    $seft.fp3 = "data:image/tif;base64," + fp3;
                                    $seft.fp4 = "data:image/tif;base64," + fp4;
                                    $seft.fp5 = "data:image/tif;base64," + fp5;
                                    $seft.fp6 = "data:image/tif;base64," + fp6;
                                    $seft.fp7 = "data:image/tif;base64," + fp7;
                                    $seft.fp8 = "data:image/tif;base64," + fp8;
                                    $seft.fp9 = "data:image/tif;base64," + fp9;
                                    $seft.fp10 = "data:image/tif;base64," + fp10;
                                    $seft.slap1 = "data:image/tif;base64," + slap1;
                                    $seft.slap2 = "data:image/tif;base64," + slap2;
                                    $seft.slap3 = "data:image/tif;base64," + slap3;
                                    break;
                                case 7:
                                    $seft.fp1 = "data:image/png;base64," + fp1;
                                    $seft.fp2 = "data:image/png;base64," + fp2;
                                    $seft.fp3 = "data:image/png;base64," + fp3;
                                    $seft.fp4 = "data:image/png;base64," + fp4;
                                    $seft.fp5 = "data:image/png;base64," + fp5;
                                    $seft.fp6 = "data:image/png;base64," + fp6;
                                    $seft.fp7 = "data:image/png;base64," + fp7;
                                    $seft.fp8 = "data:image/png;base64," + fp8;
                                    $seft.fp9 = "data:image/png;base64," + fp9;
                                    $seft.fp10 = "data:image/png;base64," + fp10;
                                    $seft.slap1 = "data:image/png;base64," + slap1;
                                    $seft.slap2 = "data:image/png;base64," + slap2;
                                    $seft.slap3 = "data:image/png;base64," + slap3;
                                    break;
                                case 12:
                                    $seft.fp1 = "data:image/gif;base64," + fp1;
                                    $seft.fp2 = "data:image/gif;base64," + fp2;
                                    $seft.fp3 = "data:image/gif;base64," + fp3;
                                    $seft.fp4 = "data:image/gif;base64," + fp4;
                                    $seft.fp5 = "data:image/gif;base64," + fp5;
                                    $seft.fp6 = "data:image/gif;base64," + fp6;
                                    $seft.fp7 = "data:image/gif;base64," + fp7;
                                    $seft.fp8 = "data:image/gif;base64," + fp8;
                                    $seft.fp9 = "data:image/gif;base64," + fp9;
                                    $seft.fp10 = "data:image/gif;base64," + fp10;
                                    $seft.slap1 = "data:image/gif;base64," + slap1;
                                    $seft.slap2 = "data:image/gif;base64," + slap2;
                                    $seft.slap3 = "data:image/gif;base64," + slap3;
                                    break;
                            }
                        }
                        else if (image_format == 9) {
                            var fp1 = ConvertFingerWSQToBMP($rootScope.baseUrl, sc_result.FPImg1);
                            var fp2 = ConvertFingerWSQToBMP($rootScope.baseUrl, sc_result.FPImg2);
                            var fp3 = ConvertFingerWSQToBMP($rootScope.baseUrl, sc_result.FPImg3);
                            var fp4 = ConvertFingerWSQToBMP($rootScope.baseUrl, sc_result.FPImg4);
                            var fp5 = ConvertFingerWSQToBMP($rootScope.baseUrl, sc_result.FPImg5);
                            var fp6 = ConvertFingerWSQToBMP($rootScope.baseUrl, sc_result.FPImg6);
                            var fp7 = ConvertFingerWSQToBMP($rootScope.baseUrl, sc_result.FPImg7);
                            var fp8 = ConvertFingerWSQToBMP($rootScope.baseUrl, sc_result.FPImg8);
                            var fp9 = ConvertFingerWSQToBMP($rootScope.baseUrl, sc_result.FPImg9);
                            var fp10 = ConvertFingerWSQToBMP($rootScope.baseUrl, sc_result.FPImg10);
                            var slap1 = ConvertFingerWSQToBMP($rootScope.baseUrl, sc_result.LeftSlap);
                            var slap2 = ConvertFingerWSQToBMP($rootScope.baseUrl, sc_result.RightSlap);
                            var slap3 = ConvertFingerWSQToBMP($rootScope.baseUrl, sc_result.ThumbSlap);

                            $seft.fp1 = "data:image/png;base64," + fp1;
                            $seft.fp2 = "data:image/png;base64," + fp2;
                            $seft.fp3 = "data:image/png;base64," + fp3;
                            $seft.fp4 = "data:image/png;base64," + fp4;
                            $seft.fp5 = "data:image/png;base64," + fp5;
                            $seft.fp6 = "data:image/png;base64," + fp6;
                            $seft.fp7 = "data:image/png;base64," + fp7;
                            $seft.fp8 = "data:image/png;base64," + fp8;
                            $seft.fp9 = "data:image/png;base64," + fp9;
                            $seft.fp10 = "data:image/png;base64," + fp10;
                            $seft.slap1 = "data:image/png;base64," + slap1;
                            $seft.slap2 = "data:image/png;base64," + slap2;
                            $seft.slap3 = "data:image/png;base64," + slap3;
                        }
                    }
                    $scope.$apply();
                    UninitTPDevice();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR);
                    console.log(textStatus);
                    console.log(errorThrown);
                }
            });
        }

        function UninitTPDevice() {
            jQuery.support.cors = true;

            var urlString = $rootScope.baseUrl + "SPID_TP_CloseDev";

            $.ajax({
                type: 'POST',
                cache: false,
                url: urlString,
                crossDomain: true,
                success: function (response) {
                },

                error: function (jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR);
                    console.log(textStatus);
                    console.log(errorThrown);
                }
            });
        }

        function CheckDocumentType() {
            //if ($scope.tokhai != null && $scope.tokhai.DOCUMENT_TYPE != null && $scope.tokhai.DOCUMENT_TYPE.id == 'ONLINE') {
            if ($scope.tokhai != null && $scope.tokhai.DOCUMENT_TYPE == 'ONLINE') {
                $scope.cvt = false;
            } else {
                $scope.cvt = true;
            }
        }
        function CheckDocumentTypeHangDoi(tokhai) {
            //if (tokhai != null && tokhai.DOCUMENT_TYPE != null && tokhai.DOCUMENT_TYPE.id == 'ONLINE') {
            if (tokhai != null && tokhai.DOCUMENT_TYPE == 'ONLINE') {
                if (tokhai.RECEIPT_STATUS != 'U') {
                    $scope.cvt = true;
                } else {
                    $scope.cvt = false;
                }
            } else {
                $scope.cvt = true;
            }
        }

        /////////////////////////Print biên nhận

        function ClearBienNhan() {
            $scope.PrintBN.Base64BarCode = "";
            $scope.PrintBN.UserName = "";
            $scope.PrintBN.TitleBN = "";
            $scope.PrintBN.TextDate = "";
            $scope.PrintBN.TextKemTheo = "";
            $scope.ListDeNghiChonBN = [];
            $scope.PrintBN.RECEIPT_NO = "";
            $scope.PrintBN.FULL_NAME = "";
            $scope.PrintBN.PID_NO = "";
            $scope.PrintBN.ADDRESS = "";
            $scope.PrintBN.NOTE = "";
            $scope.PrintBN.DATE_OF_ISSUE = "";
        }

        function LoadDataPrintBN(toKhai, receipt_no) {
            var date = new Date();
            $scope.PrintBN.TextDate = $scope.OfficeName + ", ngày " + date.getDate() + " tháng " + (date.getMonth() + 1) + " năm " + date.getFullYear();
            $scope.PrintBN.TextKemTheo = toKhai.AMOUNT_OF_REGISTRATION + " tờ khai, " + toKhai.AMOUNT_OF_PASSPORT + " hộ chiếu";



            var dstreem = $scope.ListDeNghiChon.filter(function (x) {
                return (x.MADENGHI !== "" && x.MADENGHI !== null && x.MADENGHI !== "0");
            })
            if (dstreem != null && dstreem.length > 0) {
                $scope.ListDeNghiChonBN = dstreem;
            }
            if (toKhai.DATE_OF_ISSUE_TEXT != null && toKhai.DATE_OF_ISSUE_TEXT != "" && toKhai.DATE_OF_ISSUE_TEXT !=  "undefined") {
                $scope.PrintBN.DATE_OF_ISSUE = toKhai.DATE_OF_ISSUE_TEXT;
            }
            else {
                $scope.PrintBN.DATE_OF_ISSUE = toKhai.DATE_OF_ISSUE;
            }
            $scope.PrintBN.RECEIPT_NO = receipt_no;
            $scope.PrintBN.FULL_NAME = toKhai.FULL_NAME;
            $scope.PrintBN.PID_NO = toKhai.PID_NO;
            $scope.PrintBN.ADDRESS = toKhai.ADDRESS;
            $scope.PrintBN.NOTE = toKhai.NOTE;
            $scope.PrintBN.Base64BarCode = "";
            $scope.PrintBN.UserName = "";
            $scope.PrintBN.TitleBN = "";
            if ($scope.PrintBN.RECEIPT_NO != null && $scope.PrintBN.RECEIPT_NO != '') {
                $.ajax({
                    url: "/TiepNhan/GetInfoPrintBienNhan",
                    type: 'POST',
                    data: {
                        receiptNo: $scope.PrintBN.RECEIPT_NO
                    },
                    success: function (data) {
                        //data:image/png;base64,
                        if (data != null) {
                            $scope.PrintBN.Base64BarCode = data.Base46BarCode;
                            $scope.PrintBN.UserName = data.UserName;
                            $scope.PrintBN.TitleBN = data.TitleBN;
                            $scope.$apply();
                        }
                    }, error: function (jqXHR, textStatus, errorMessage) {
                        console.log(errorMessage);
                    }
                });
            }
        }

        $("#modalBienNhan").on('shown.bs.modal', function (e) {

        });

        $scope.printData = function (printSectionId) {
            var title = "BienNhan_" + DateString(new Date());
            var innerContents = document.getElementById(printSectionId).innerHTML;
            var popupWinindow = window.open('', '_blank', 'width=600,height=700,scrollbars=no,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
            popupWinindow.document.open();
            popupWinindow.document.write('<html><head><title>' + title + '</title> </head><body onload="window.print()">' + innerContents + '</html>');
            popupWinindow.document.close();
        }

        //format date
        function DateString(date) {
            if (date != undefined && date != "" && date.length != 10) {
                var utcDate = new Date(Date.UTC(date.getFullYear(), date.getMonth(), date.getDate()));
                date = (moment(utcDate).format('DD/MM/YYYY')).toString();
            }
            return date;
        }
        //////////////////////////End

    }

})(angular.module('e-app'));



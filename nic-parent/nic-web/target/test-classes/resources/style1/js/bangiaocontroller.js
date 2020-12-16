(function (app) {
    app.controller('bangiaocontroller', BanGiaoController);
    BanGiaoController.$inject = ['$scope', '$filter', 'apiservices', '$rootScope', '$window', '$timeout', '$rootScope'];
    function BanGiaoController($scope, $filter, apiservices, $rootScope, $window, $timeout, $rootScope) {
        var $seft = this;
        $scope.currentPage = 1;
        $scope.pageSize = 10;
        $scope.totalRecord = 10;

        $scope.itemPage = {
            "type": "select",
            "value": $scope.pageSize,
            "values": [10, 15, 20, 50]
        };
        $scope.XemTruocKhiIn = true;
        $scope.DisabledInLai = true;
        $scope.DisabledLapDS = true;
        $scope.DisabledKySo = true;
        $scope.bangiao = {};
        $scope.PrintDSBG = {};
        $scope.ListBanGiao = [];
        $scope.ListBangiaoChon = [];
        $scope.ListBangiaoChonReport = [];
        $scope.ListNguoiNhap = [];
        $scope.ListNguoiBanGiao = [];
        $scope.DSBanGiaoInLaiID = 0;
        LoadDSUser();
       
        $scope.RoleBtnSearchBG = false;
        $scope.RoleBtnSignerBG = false;
        $scope.RoleBtnCreateDSBG = false;
        $scope.RoleBtnExportExcelBG = false;

        angular.element(document).ready(function () {

            // Lấy danh mục
            $.ajax({
                type: 'POST',
                dataType: 'json',
                cache: false,
                async: false,
                url: '/TiepNhan/GetListRoleButtom',
                data: { actionName:"QuetToKhai"},
                success: function (response) {
                    if (response.Buttoms != null) {
                        angular.forEach(response.Buttoms, function (item) {
                            if (item == 'btnBGSearch') {
                                $scope.RoleBtnSearchBG = true;
                            }
                            if (item == 'btnBGSigner') {
                                $scope.RoleBtnSignerBG = true;
                            }
                            if (item == 'btnBGCreateDS') {
                                $scope.RoleBtnCreateDSBG = true;
                            }
                            if (item == 'btnBGExportExcel') {
                                $scope.RoleBtnExportExcelBG = true;
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


        var today = new Date();
        $scope.bangiao = {
            TuNgay: today,
            DenNgay: today
        }
        $scope.$watch('bangiao.TuNgay', function (newValue) {
            $scope.bangiao.TuNgay = $filter('date')(newValue, 'dd/MM/yyyy');
        });
        $scope.$watch('bangiao.DenNgay', function (newValue) {
            $scope.bangiao.DenNgay = $filter('date')(newValue, 'dd/MM/yyyy');
        });

        $scope.AnThongBao = function () {
            $("#divThongBao").hide();
        }
        $scope.ChonBanGiao = function (bg) {
            if (bg.check == true) {
                $scope.ListBangiaoChon.push(bg);
            }
            else {
                $scope.ListBangiaoChon.splice($scope.ListBangiaoChon.indexOf(bg), 1);
            }
            DisabledEnbalsed();
            DisabledEnbalsedKySo();
        }

        function DisabledEnbalsed() {
            $scope.DisabledInLai = true;
            $scope.DisabledLapDS = true;
            if ($scope.ListBangiaoChon.length > 0) {
                var searchPlace = $filter("filter")($scope.ListBangiaoChon, { IS_HAND_OVERED: "Y" }, true);
                if (searchPlace.length > 0) {
                    $scope.DisabledLapDS = true;
                    var searchPlaceN = $filter("filter")($scope.ListBangiaoChon, { IS_HAND_OVERED: "N" }, true);
                    if (searchPlaceN.length > 0) {
                        $scope.DisabledInLai = true;
                        $scope.DSBanGiaoInLaiID = 0;
                    }
                    else {
                        $scope.DSBanGiaoInLaiID = $scope.ListBangiaoChon[0].HANDOVER_ID;
                        $scope.DisabledInLai = false;
                        for (var i = 0; i < $scope.ListBangiaoChon.length; i++) {
                            if ($scope.ListBangiaoChon[i].HANDOVER_ID != $scope.DSBanGiaoInLaiID) {
                                $scope.DisabledInLai = true;
                                $scope.DSBanGiaoInLaiID = 0;
                            }
                        }
                    }
                } else {
                    $scope.DSBanGiaoInLaiID = 0;
                    $scope.DisabledInLai = true;
                    $scope.DisabledLapDS = true;
                    var valid = true;
                    if ($scope.ListBangiaoChon.length > 1) {
                        var type = $scope.ListBangiaoChon[0].IS_ONLINE;
                        for (var i = 0; i < $scope.ListBangiaoChon.length; i++) {
                            if ($scope.ListBangiaoChon[i].IS_ONLINE != type) {
                                $scope.DisabledInLai = true;
                                $scope.DSBanGiaoInLaiID = 0;
                            }
                        }
                    }

                    if (valid == true) {
                        $scope.DisabledLapDS = false;
                        //$("#btn_Lapds").prop('disabled', false);
                        $("#divThongBao").hide();
                    }
                    else {
                        $scope.DisabledLapDS = true;
                        //$("#btn_Lapds").prop('disabled', true);
                        $("#divThongBao").show();
                        $scope.ThongBaoBG = "Không thể lập danh sách chứa 2 loại hồ sơ cùng 1 lúc.";
                    }
                }
            }
            else {
                $scope.DisabledInLai = true;
                $scope.DisabledLapDS = true;
                checkall = false;
                $scope.DSBanGiaoInLaiID = 0;
            }
        }

        function DisabledEnbalsedKySo() {
            if ($scope.ListBangiaoChon.length > 0) {
                var searchPlaceDaKy = $filter("filter")($scope.ListBangiaoChon, { SIGN_STATUS: 1 }, true);
                var searchPlaceChuaKy = $filter("filter")($scope.ListBangiaoChon, { SIGN_STATUS: 0 }, true);
                if ((searchPlaceDaKy.length > 0 && searchPlaceChuaKy.length > 0) || (searchPlaceDaKy.length > 0 && searchPlaceChuaKy.length == 0)) {
                    $scope.DisabledKySo = true;
                }
                if (searchPlaceDaKy.length == 0 && searchPlaceChuaKy.length > 0) {
                    $scope.DisabledKySo = false;
                }
            }
            else {
                $scope.DisabledKySo = true;
            }
        }

        $scope.ExportExcel = function () {
            var receipt_no = $scope.bangiao.SoTiepNhan;
            var handover_no = $scope.bangiao.SoBangiao;
            var from_date = $scope.bangiao.TuNgay;
            var to_date = $scope.bangiao.DenNgay;
            var status = $scope.bangiao.TinhTrang;
            var nguoi_nhap = $scope.bangiao.nguoinhap.ID;
            var nguoi_ban_giao = $scope.bangiao.nguoibangiao.ID;
            var is_online = $scope.bangiao.IS_ONLINE

            console.log($scope.bangiao);
            console.log(from_date);
            console.log(to_date);

            var url = "/TiepNhan/ExportBanGiao?receipt_no=" + receipt_no + "&handover_no=" + handover_no
                + "&from_date=" + from_date + "&to_date=" + to_date + "&status=" + status + "&nguoi_nhap=" + nguoi_nhap + "&nguoi_ban_giao=" + nguoi_ban_giao + "&is_online = " + is_online;
            console.log(url);
            window.location.href = url;
        }

        $scope.LapDanhSachBG = function () {
            if ($scope.ListBangiaoChon.length == 0) {
                toastr["error"]("Bạn cần phải chọn hồ sơ để lập danh sách tiếp nhận !");
                return;
            }

            var dabg = false;
            $scope.ListBangiaoChon.forEach(function (entry) {
                if (entry.IS_HAND_OVERED == 'Y') {
                    toastr["error"]("Không thể chọn hồ sơ đã được tiếp nhận !");
                    dabg = true;
                }
            });

            if (dabg) {
                return;
            }

            // Kiểm tra xem danh sách hồ sơ có khác ngày hẹn trả không
            var dateHenTra = $scope.ListBangiaoChon[0].DATE_OF_ISSUE_TEXT;
            var dsCheck = $scope.ListBangiaoChon.filter(function (x) {
                return x.DATE_OF_ISSUE_TEXT != dateHenTra;
            })
            if (dsCheck != null && dsCheck.length > 0) {
                toastr["error"]("Bạn đã chọn các hồ sơ có ngày hẹn trả khác nhau để lập danh sách tiếp nhận.");
            }

            var xemin = false;
            if ($scope.XemTruocKhiIn != null && $scope.XemTruocKhiIn == true) {
                xemin = true;
            }
            $scope.PrintDSBG.CountHC = 0;
            $scope.PrintDSBG.Count = 0;
            $scope.PrintDSBG.TextDate = "";
            $scope.ListBangiaoChonReport = [];
            $scope.PrintDSBG.handover_no = "";
            $scope.PrintDSBG.userName = "";
            $.ajax({
                url: "/TiepNhan/LapDanhSachBG",
                type: 'POST',
                data: {
                    list: $scope.ListBangiaoChon,
                    xemin: xemin
                },
                success: function (data) {
                    if (data.status == 1) {
                        $scope.PrintDSBG.handover_no = data.handover_no;
                        $scope.PrintDSBG.userName = data.userName;
                        $scope.ListBangiaoChonReport = $scope.ListBangiaoChon;
                        for (var i = 0; i < $scope.ListBangiaoChonReport.length; i++) {
                            $scope.PrintDSBG.CountHC = $scope.PrintDSBG.CountHC + $scope.ListBangiaoChonReport[i].AMOUNT_OF_PASSPORT;
                        }
                        var date = new Date();
                        $scope.PrintDSBG.TextDate = "Ngày " + date.getDate() + " tháng " + (date.getMonth()+1) + " năm " + date.getFullYear();
                        $scope.PrintDSBG.Count = $scope.ListBangiaoChonReport.length;
                        $scope.DisabledLapDS = true;
                        $scope.ThongBaoBG = 'Lập danh sách tiếp nhận hồ sơ thành công, số tiếp nhận: ' + data.handover_no;
                        $("#divThongBao").show();
                        $("#btnSearchHangDoi").click();

                        if (xemin == true) {
                            //$("#myModal").modal('toggle');

                            var id = data.handover_id;
                            if (id != null && id != '') {
                                $scope.pageloadding = true;
                                $("#quayquaytiepnhan").removeClass("ng-hide");
                                //$('#WrapIframeBG').empty();
                                //var drl = "/DanhSachBanGiao/dsbangiao?id=" + id + "&needPrint=0";
                                //loadIframeBG(drl);
                                $('#reportBG').modal('show');
                                $scope.$apply();
                            }
                        }
                        $scope.ListBangiaoChon = [];
                        LoadDSBanGiao();
                        $scope.XemTruocKhiIn = true;
                    }
                    else {
                        toastr["error"](data.message);
                    }

                }, error: function (jqXHR, textStatus, errorMessage) {
                    console.log(errorMessage);
                }
            });
        }

        $scope.HidePopupReportBG = function () {
            $('#reportBG').modal('hide');
        }

        $scope.KySoDanhSachBG = function () {
            if ($scope.ListBangiaoChon.length == 0) {
                toastr["error"]("Bạn cần phải chọn danh sách tiếp nhận hoặc hồ sơ để thực hiện ký số !");
                return;
            }

            var countChuaTN = 0;
            $scope.ListBangiaoChon.forEach(function (entry) {
                if (entry.IS_HAND_OVERED == 'N') {
                    countChuaTN++;
                }
            });

            var xemin = false;
            if ($scope.XemTruocKhiIn != null && $scope.XemTruocKhiIn == true) {
                xemin = true;
            }

            // Kiểm tra xem danh sách hồ sơ có khác ngày hẹn trả không
            var dateHenTra = $scope.ListBangiaoChon[0].DATE_OF_ISSUE_TEXT;
            var dsCheck = $scope.ListBangiaoChon.filter(function (x) {
                return x.DATE_OF_ISSUE_TEXT != dateHenTra;
            })
            if (dsCheck != null && dsCheck.length > 0) {
                toastr["error"]("Bạn đã chọn các hồ sơ có ngày hẹn trả khác nhau để lập danh sách tiếp nhận.");
            }
            $scope.PrintDSBG.TextDate = "";
            $scope.PrintDSBG.CountHC = 0;
            $scope.PrintDSBG.Count = 0;
            $scope.ListBangiaoChonReport = [];

            $scope.PrintDSBG.handover_no = "";
            $scope.PrintDSBG.userName = "";

            $.ajax({
                url: "/TiepNhan/KySoLapDanhSachBG",
                type: 'POST',
                data: {
                    list: $scope.ListBangiaoChon,
                    xemin: xemin
                },
                success: function (data) {
                    if (data.status == 1) {
                        $scope.DisabledLapDS = true;
                        $scope.DisabledKySo = true;
                        $scope.PrintDSBG.handover_no = data.handover_no;
                        $scope.PrintDSBG.userName = data.userName;
                        var dsReport = $scope.ListBangiaoChon.filter(function (x) {
                            return x.IS_HAND_OVERED == "N";
                        })

                        if (dsReport != null && dsReport.length > 0) {
                            $scope.ListBangiaoChonReport = dsReport;
                            for (var i = 0; i < dsReport.length; i++) {
                                $scope.PrintDSBG.CountHC = $scope.PrintDSBG.CountHC + dsReport[i].AMOUNT_OF_PASSPORT;
                            }
                        }
                        var date = new Date();
                        $scope.PrintDSBG.TextDate = "Ngày " + date.getDate() + " tháng " + (date.getMonth()+1) + " năm " + date.getFullYear();
                        $scope.PrintDSBG.Count = $scope.ListBangiaoChonReport.length;
                        if (countChuaTN > 0) {
                            toastr["success"]('Ký số danh sách tiếp nhận và lập danh sách tiếp nhận thành công, số tiếp nhận: ' + data.handover_no);
                            $scope.ThongBaoBG = 'Ký số danh sách tiếp nhận và lập danh sách tiếp nhận thành công, số tiếp nhận: ' + data.handover_no;
                        }
                        else {
                            toastr["success"]("Ký số danh sách tiếp nhận thành công.!");
                            $scope.ThongBaoBG = 'Ký số danh sách tiếp nhận thành công.';
                        }
                        $("#divThongBao").show();
                        $("#btnSearchHangDoi").click();

                        if (xemin == true && countChuaTN > 0) {
                            //$("#myModal").modal('toggle');
                            var id = data.handover_id;
                            if (id != null && id != '') {
                                $scope.pageloadding = true;
                                $("#quayquaytiepnhan").removeClass("ng-hide");
                                //$('#WrapIframeBG').empty();
                                //var drl = "/DanhSachBanGiao/dsbangiao?id=" + id + "&needPrint=0";
                                //loadIframeBG(drl);
                                $('#reportBG').modal('show');
                                $scope.$apply();
                            }
                        }
                        $scope.ListBangiaoChon = [];
                        LoadDSBanGiao();
                        $scope.XemTruocKhiIn = true;
                    }
                    else {
                        toastr["error"](data.message);
                    }
                }, error: function (jqXHR, textStatus, errorMessage) {
                    console.log(errorMessage);
                }
            });
        }



        $scope.InLaiDanhSachBG = function () {
            $scope.DisabledKySo = true;
            if ($scope.DSBanGiaoInLaiID > 0) {
                $scope.DisabledInLai = true;
                $scope.DisabledLapDS = true;

                var xemin = false;
                if ($scope.XemTruocKhiIn != null && $scope.XemTruocKhiIn == true) {
                    xemin = true;
                }
                if (xemin == true) {
                    $("#myModal").modal('toggle');
                    var id = $scope.DSBanGiaoInLaiID;
                    if (id != null && id != '') {
                        $scope.pageloadding = true;
                        $("#quayquaytiepnhan").removeClass("ng-hide");
                        $('#WrapIframeBG').empty();
                        var drl = "/DanhSachBanGiao/dsbangiao?id=" + id + "&needPrint=0";
                        loadIframeBG(drl);
                        $('#reportBG').modal('show');
                    }
                }
                $scope.checkall = false;
                $scope.DSBanGiaoInLaiID = 0;
            } else {
                toastr["error"]("Bạn chưa chọn danh sách tiếp nhận hồ sơ cần in lại !");
            }
        }

        $scope.TimKiemBG = function () {

            LoadDSBanGiao();
        }

        $scope.CheckAll = function () {
            $scope.ListBangiaoChon = [];
            if ($scope.checkall == true) {
                $scope.ListBanGiao.forEach(function (entry) {
                    //if (entry.IS_HAND_OVERED == 'N') {
                    entry.check = true;
                    $scope.ListBangiaoChon.push(entry);
                    //}
                });
            }
            else {
                $scope.ListBanGiao.forEach(function (entry) {
                    entry.check = false;
                });
            }

            DisabledEnbalsed();
            DisabledEnbalsedKySo();
        }

        $scope.CheckDateTuNgay = function () {
            if ($scope.bangiao.TuNgay == null || $scope.bangiao.TuNgay == '') {
                $scope.bangiao.TuNgay = today;
            }
        }
        $scope.CheckDateDenNgay = function () {
            if ($scope.bangiao.DenNgay == null || $scope.bangiao.DenNgay == '') {
                $scope.bangiao.DenNgay = today;
            }
        }

        $("#myModal").on('shown.bs.modal', function (e) {
            $scope.bangiao = {};
            $scope.bangiao.TuNgay = today;
            $scope.bangiao.DenNgay = today;
            $scope.bangiao.SoBangiao = '';
            $scope.bangiao.SoTiepNhan = '';
            $scope.bangiao.TinhTrang = 'N'
            $scope.bangiao.nguoibangiao = $scope.ListNguoiBanGiao[0];
            $scope.bangiao.TuNgay = today;
            $scope.bangiao.nguoinhap = $scope.ListNguoiNhap[0];

            $("#divThongBao").hide();

            setTimeout(function () {
                LoadDSBanGiao();
            }, 200);
        });

        $("#reportBG").on('shown.bs.modal', function (e) {
            var zIndex = 1040 + (10 * $('.modal:visible').length);
            $(this).css('z-index', zIndex);
        });

        function LoadDSBanGiao() {
            $scope.DisabledInLai = true;
            $scope.DisabledLapDS = true;
            $scope.DisabledKySo = true;
            $scope.checkall = false;
            $scope.DSBanGiaoInLaiID = 0;
            $scope.ListBanGiao = [];
            $scope.ListBangiaoChon = [];
            var idnguoinhap = '';
            if ($scope.bangiao.nguoinhap != null) {
                idnguoinhap = $scope.bangiao.nguoinhap.ID;
            }
            var idnguoibangiao = '';
            if ($scope.bangiao.nguoibangiao != null) {
                idnguoibangiao = $scope.bangiao.nguoibangiao.ID;
            }

            $seft.pageloadding = true;
            $.ajax({
                url: "/TiepNhan/GetListBanGiao",
                type: 'POST',
                data: {
                    receipt_no: $scope.bangiao.SoTiepNhan,
                    handover_no: $scope.bangiao.SoBangiao,
                    from_date: $scope.bangiao.TuNgay,
                    to_date: $scope.bangiao.DenNgay,
                    status: $scope.bangiao.TinhTrang,
                    nguoi_nhap: idnguoinhap,
                    nguoi_ban_giao: idnguoibangiao,
                    is_online: $scope.bangiao.IS_ONLINE
                },
                success: function (data) {
                    //data.list.forEach(function (entry) {
                    //    $scope.ListBanGiao.push(entry);
                    //});
                    $scope.ListBanGiao = data.list;
                    $scope.totalRecord = $scope.ListBanGiao.length;
                    $scope.$apply();
                    $seft.pageloadding = false;
                }, error: function (jqXHR, textStatus, errorMessage) {
                    $seft.pageloadding = false;
                    console.log(errorMessage);
                }
            });
        }

        function LoadDSUser() {
            $.ajax({
                url: "/TiepNhan/GetListUser",
                type: 'POST',
                success: function (data) {
                    console.log(data);
                    $scope.ListNguoiNhap = data.list;
                    $scope.ListNguoiBanGiao = data.list;
                    $scope.bangiao.nguoinhap = data.list[0];
                    $scope.bangiao.nguoibangiao = data.list[0];
                    $scope.$apply();
                }, error: function (jqXHR, textStatus, errorMessage) {
                    console.log(errorMessage);
                }
            });
        }

        function loadIframeBG(url) {
            var ifr = $('<iframe/>', {
                id: 'AdminFrameBG',
                src: url,
                frameborder: 0,
                style: 'width:100%;height:100%',
                load: function () {
                    $(this).show();
                    $scope.pageloadding = false;
                    $("#quayquaytiepnhan").addClass("ng-hide");
                }
            });
            $('#WrapIframeBG').html(ifr);
        }

        $scope.printData = function (printSectionId) {
            var title = "DanhSachBanGiao_" + DateString(new Date());
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
    }

})(angular.module('e-app'));
(function (app) {
    //app = angular.module('app', ['ui.bootstrap']);

    app.controller('uploaddatatopacontroller', XuLyHoSoController);
    XuLyHoSoController.$inject = ['$scope', '$filter', 'apiservices', '$rootScope', '$window', '$timeout', '$rootScope'];
    function XuLyHoSoController($scope, $filter, apiservices, $rootScope, $window, $timeout, $rootScope) {
        $scope.timKiem = {};
        function FormatDate(date) {
            if (date.length != undefined) {
                var n = date.indexOf("/");
                if (n >= 0) {
                    return ConvertDatetime(date);
                } else {
                    return ConvertDatetime(date);
                }
            } else {
                return ConvertDatetime(date);
            }
        }

        // date to yyyymmdd
        function ConvertDatetime(dateInput) {
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

                    return yyyy + mm+ dd;
                } else if (dateInput.split('/').length > 0) {
                    var arrDate = dateInput.split('/');
                    return arrDate[2] + arrDate[1] + arrDate[0];
                } else {
                    return "20170101";
                }
            } else {
                var arrDate = dateInput.split('/');
                return arrDate[2] + arrDate[1] + arrDate[0];
            }
        }

        $scope.UploadDataToPA = function () {
            $scope.pageloadding = true;
            var fromDate = "";
            if ($scope.timKiem.fromDate != null && $scope.timKiem.fromDate != "")
                fromDate = ConvertDatetime($scope.timKiem.fromDate);
            
            $.ajax({
                url: "/ToKhai/UploadDataToPA",
                type: 'POST',
                dataType: 'json',
                cache: false,
                async: true,
                data: {
                    from_date: fromDate
                },
                success: function (data) {
                    if (data != null && data.success == true) {
                        toastr["success"](data.messeage);
                    } else {
                        toastr["error"](data.messeage);
                    }
                    //$('#UploadDataToPA').modal('hide');
                    $scope.pageloadding = false;
                    $scope.$apply();
                }, error: function (jqXHR, textStatus, errorMessage) {
                    $scope.pageloadding = false;
                    $scope.$apply();
                }
            });
        };

       
    }

})(angular.module('e-app'));


var app;

(function () {
    app = angular.module("e-app", ['angularUtils.directives.dirPagination', 'LocalStorageModule']);
    app.run(['$rootScope', function ($rootScope) {
        $rootScope.baseUrl = baseUrl;
        $rootScope.apiUrl = apiUrl;
    }]);
    app.directive('customzdatetime', function () {
        return {
            restrict: 'A',
            require: 'ngModel',
            link: function (scope, element, attrs, ngModelCtrl) {
                element.datetimepicker({
                    useCurrent: false,
                    locale: 'vi',
                    minDate: '01/01/1900',
                    format: attrs.formatdate === undefined ? 'DD/MM/YYYY' : attrs.formatdate,
                }).on('dp.change', function (e) {
                    // var date = (e.date._d.getDate() - 2) + '-' + (e.date._d.getMonth() + 1) + '-' + e.date._d.getFullYear();
                    var date = e.date._d;
                    if (date != undefined) {
                        var date = (e.date._d.getDate() > 9 ? e.date._d.getDate() : '0' + e.date._d.getDate()) + '/' + ((e.date._d.getMonth() + 1) > 9 ? (e.date._d.getMonth() + 1) : '0' + (e.date._d.getMonth() + 1)) + '/' + e.date._d.getFullYear();
                        //  console.log(e.date._d);
                        var date = e.date._d;
                        ngModelCtrl.$setViewValue(date);
                        scope.$apply();
                    }
                }).on('focusout', function (e) {
                    ngModelCtrl.$setViewValue(e.target.value);
                });
            }
        };
    });

    app.directive('kcustomzdatetime', function () {
        return {
            restrict: 'A',
            require: 'ngModel',
            link: function (scope, element, attrs, ngModelCtrl) {
                element.datetimepicker({
                    useCurrent: false,
                    locale: 'vi',
                    minDate: '01/01/1900',
                    format: attrs.formatdate === undefined ? 'DD/MM' : attrs.formatdate,
                }).on('dp.change', function (e) {
                    // var date = (e.date._d.getDate() - 2) + '-' + (e.date._d.getMonth() + 1) + '-' + e.date._d.getFullYear();
                    var date = e.date._d;
                    if (date != undefined) {
                        var date = (e.date._d.getDate() > 9 ? e.date._d.getDate() : '0' + e.date._d.getDate()) + '/' + ((e.date._d.getMonth() + 1) > 9 ? (e.date._d.getMonth() + 1) : '0' + (e.date._d.getMonth() + 1));
                        //  console.log(e.date._d);
                        var date = e.date._d;
                        ngModelCtrl.$setViewValue(date);
                        scope.$apply();
                    }
                }).on('focusout', function (e) {
                    ngModelCtrl.$setViewValue(e.target.value);
                });
            }
        };
    });
    
    app.service('dataperson', function () {
        this.person = null;
    });
    app.factory('fileReader', ['$q', '$log', function ($q, $log) {
        var onLoad = function (reader, deferred, scope) {
            return function () {
                scope.$apply(function () {
                    deferred.resolve(reader.result);
                });
            };
        };

        var onError = function (reader, deferred, scope) {
            return function () {
                scope.$apply(function () {
                    deferred.reject(reader.result);
                });
            };
        };

        var onProgress = function (reader, scope) {
            return function (event) {
                scope.$broadcast("fileProgress",
                    {
                        total: event.total,
                        loaded: event.loaded
                    });
            };
        };

        var getReader = function (deferred, scope) {
            var reader = new FileReader();
            reader.onload = onLoad(reader, deferred, scope);
            reader.onerror = onError(reader, deferred, scope);
            reader.onprogress = onProgress(reader, scope);
            return reader;
        };

        var readAsDataURL = function (file, scope) {
            var deferred = $q.defer();

            var reader = getReader(deferred, scope);
            reader.readAsDataURL(file);

            return deferred.promise;
        };

        return {
            readAsDataUrl: readAsDataURL
        };
    }]);
    app.directive('ttable', function ($compile) {
        return {
            restrict: 'A',
            replace: true,
            scope: {
                ttableLength: '@',
            },
            link: function (scope, element, attrs, ngModelCtrl) {
                var num = scope.$eval(attrs.ttableLength);
                var parent = element.closest('div');
                var eheight = parent.attr('e-heigth') - 38;
                var colspan = element.find('thead').find('tr').find('th').length;
                var $tbody, $tr, $td, $div1, $div2, $div3;
                console.log(scope.ttableLength);
                $tbody = $('<tbody ng-if="ttableLength <1" class="e-not-found"/>');
                $tr = $('<tr/>')
                $td = $('<td colspan = ' + colspan + ' style="height: ' + eheight + 'px" />');
                $div1 = $('<div class="e-not-found-center"/>');
                $div2 = $('<div class="e-not-found-title" />').html('Không tìm thấy kết quả');
                $div3 = $('<div class="e-not-found-title-child"/>').html('Hãy thử tìm kiếm hoặc sử dụng bộ lọc khác');
                $div1.append($div2);
                $div1.append($div3);
                $td.append($div1);
                $tr.append($td);
                $tbody.append($tr);
                $compile($tbody)(scope);
                element.append($tbody);
            }
        };
    })

    app.directive("ngFileSelect", function () {

        return {
            link: function ($scope, el) {

                el.bind("change", function (e) {

                    $scope.file = (e.srcElement || e.target).files[0];
                    $scope.getFile();
                });

            }

        };
    });
    app.directive('selectoption', function () {
        return {
            restrict: 'C',
            require: 'ngModel',
            link: function (scope, element, attrs, ngModelCtrl) {
                element.selectpicker({
                    dropupAuto: false
                });
            }
        };
    });
    app.directive('toUppercase', function () {

        return {
            restrict: 'A',
            require: 'ngModel',
            link: function (scope, element, attrs, ctrl) {
                function parser(value) {
                    if (ctrl.$isEmpty(value)) {
                        return value;
                    }
                    var formatedValue = value.toUpperCase();
                    if (ctrl.$viewValue !== formatedValue) {
                        ctrl.$setViewValue(formatedValue);
                        ctrl.$render();
                    }
                    return formatedValue;
                }

                function formatter(value) {
                    if (ctrl.$isEmpty(value)) {
                        return value;
                    }
                    return value.toUpperCase();
                }

                ctrl.$formatters.push(formatter);
                ctrl.$parsers.push(parser);
            }
        };
    });
})();
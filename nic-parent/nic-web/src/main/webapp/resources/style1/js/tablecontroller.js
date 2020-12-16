(function (app) {
    app.controller('tablecontroller', TableController);
    TableController.$inject = ['$scope', 'apiservices', '$rootScope', '$window', '$timeout', '$rootScope'];
    function TableController($scope, apiservices, $rootScope, $window, $timeout, $rootScope) {
        $scope.currentPage = 1;
        $scope.pageSize = 10;
        $scope.totalRecord = 150;

        $scope.itemPage = {
            "type": "select",
            "value": $scope.pageSize,
            "values": [10, 15, 20, 50]
        };
        $scope.data = [];

        var dishes = [
          'noodles',
          'sausage',
          'beans on toast',
          'cheeseburger',
          'battered mars bar',
          'crisp butty',
          'yorkshire pudding',
          'wiener schnitzel',
          'sauerkraut mit ei',
          'salad',
          'onion soup',
          'bak choi',
          'avacado maki'
        ];
        var sides = [
          'with chips',
          'a la king',
          'drizzled with cheese sauce',
          'with a side salad',
          'on toast',
          'with ketchup',
          'on a bed of cabbage',
          'wrapped in streaky bacon',
          'on a stick with cheese',
          'in pitta bread'
        ];
        for (var i = 1; i <= 100; i++) {
            var dish = dishes[Math.floor(Math.random() * dishes.length)];
            var side = sides[Math.floor(Math.random() * sides.length)];
            var obj = {
                check: false,
                meal: 'meal ' + i + ': ' + dish + ' ' + side
            }
            $scope.data.push(obj);
        }

        this.CheckAll = function () {
       
            for (var i = 0; i < $scope.data.length; i++) {
                $scope.data[i].check = true;
            }
            $scope.$apply();
        }
        $scope.pageChangeHandler = function (num) {
            console.log($scope.currentPage);
            console.log($scope.pageSize);
            console.log('meals page changed to ' + num);
        };

        $scope.InitPortraitDevice = function () {
            InitPortraitDevice();
        }

        $scope.CapturePortrait = function () {
            CapturePortrait();
        }

        $scope.UninitPortraitDevice = function () {
            UninitPortraitDevice();
        }

        

        function InitPortraitDevice() {
        
            jQuery.support.cors = true;

            var di = document.getElementById("device_id");
            var device_id = di.options[di.selectedIndex].value;
            //alert("Selected Device ID is: " + device_id);

            var postData = {
                deviceID: device_id
            };
            var dataSet = JSON.stringify(postData);
            //alert("JSON.stringify(postData) = " + dataSet);
            console.log(dataSet);

            var urlString = $rootScope.baseUrl + "SPID_Portrait_InitDev";

            $.ajax({
                type: 'POST',
                cache: false,
                url: urlString,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                crossDomain: true,
                data: JSON.stringify(postData),

                success: function (response) {
                    //alert(response);
                    $('#result_data').empty();
                    $('#result_data').append('<p><strong> Portrait Device Init Result: ' + response + '</strong></p>');
                    ReInitPortraitUI(true);
                },

                error: function (jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR);
                    console.log(textStatus);
                    console.log(errorThrown);
                    alert(textStatus);
                }
            });
        }

        function CapturePortrait() {
            jQuery.support.cors = true;
            var device_index = document.getElementById("device_index").value;
            if (device_index == null) di = 0;
            var capture_mode = document.getElementById("capture_mode").value;
            var format = document.getElementById("image_format").value;
            var pt_width = document.getElementById("pt_width").value;
            var pt_height = document.getElementById("pt_height").value;
            var fixed = document.querySelector('input[name = "fixed"]:checked').value;

            var ptData = {
                Mode: capture_mode,
                Format: format,
                Width: pt_width,
                Height: pt_height,
                FixedTrack: fixed,
                DeviceIndex: device_index
            };
            console.log(ptData);
            var postData = {
                ptInfo: ptData
            };

            var urlString = $rootScope.baseUrl + "SPID_Portrait_Capture";

            $.ajax({
                type: 'POST',
                cache: false,
                url: urlString,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                crossDomain: true,
                data: JSON.stringify(postData),

                success: function (response) {
                    var result = response;
                    $('#result_data').empty();
                    $('#result_data').append('<p><strong> Portrait Capture Return Value: ' + result.Result + '</strong></p>');

                    if (result.Result == 0) {
                        $('#image1').empty();
                        $('#image2').empty();
                        $('#portrait1').empty();
                        var ptImage = result.PTImg;
                        document.getElementById("portrait1").src = "data:image/bmp;base64," + ptImage;
                    }
                },

                error: function (jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR);
                    console.log(textStatus);
                    console.log(errorThrown);
                    alert(textStatus);
                }
            });
        }
        function UninitPortraitDevice() {
            jQuery.support.cors = true;

            var urlString = $rootScope.baseUrl + "SPID_Portrait_CloseDev";

            $.ajax({
                type: 'POST',
                cache: false,
                url: urlString,
                crossDomain: true,

                success: function (response) {
                    alert(response);
                    $('#result_data').empty();
                    $('#result_data').append('<p><strong> Portrait Device UnInit Result: ' + response + '</strong></p>');
                    ReInitPortraitUI(false);
                },

                error: function (jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR);
                    console.log(textStatus);
                    console.log(errorThrown);
                    alert(textStatus);
                }
            });
        }
        function ReInitPortraitUI(init) {
            //document.getElementById("portrait1").src = "";
            //$('#fp1').empty();

            //var btn = document.getElementById("btn_PTInit");
            //var btn1 = document.getElementById("btn_PTCapture");
            //var btn2 = document.getElementById("btn_PTUninit");

            //if (init == true) {
            //    btn.disabled = true;
            //    btn1.disabled = false;
            //    btn2.disabled = false;
            //} else {
            //    btn.disabled = false;
            //    btn1.disabled = true;
            //    btn2.disabled = true;
            //}
        }

    }

})(angular.module('e-app'));
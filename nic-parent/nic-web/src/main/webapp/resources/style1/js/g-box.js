function gAlert(message) {
    bootbox.alert({
        size: 'small',
        title: "<label>Thông báo</label>",
        message: '<i style="display: inline-block;" class="glyphicon glyphicon-exclamation-sign gi-2x"></i> <p style="display: inline-block; position: absolute; margin-left: 10px; margin-top: 5px;">' + message + '</p>',
        buttons: {
            ok: {
                label: '<i class="glyphicon glyphicon-remove"></i> Đóng',
                className: 'btn-warning'
            }
        }
    })
}

function TAlert(data) {
    var $ul, $li;
    $ul = $('<ul class="epp-ul-imf"/>');
    for (var i = 0; i < data.length; i++) {
        $li = $('<li/>')
        var $imf = $('<i style="display: inline-block;" class="glyphicon glyphicon-exclamation-sign gi-2x"></i> <p style="display: inline-block; position: absolute; margin-left: 10px; margin-top: 5px;">' + data[i] + '</p>');
        $li.append($imf);
        $ul.append($li);
    }
    bootbox.alert({
        size: 'default',
        title: "<label>Thông báo</label>",
        message: $ul,
        buttons: {
            ok: {
                label: '<i class="glyphicon glyphicon-remove"></i> Đóng',
                className: 'btn-warning'
            }
        }
    })
}

function gConfirm(message, callback) {
    bootbox.confirm({
        size: 'small',
        title: "<label>Thông báo</label>",
        message: '<i style="display: inline-block;" class="glyphicon glyphicon-question-sign gi-2x"></i> <p style="display: inline-block; position: absolute; margin-left: 10px; margin-top: 5px;">' + message + '</p>',
        buttons: {
            confirm: {
                label: '<i class="glyphicon glyphicon-ok"></i> Có',
                className: 'color-blue'
            },
            cancel: {
                label: '<i class="glyphicon glyphicon-remove"></i> Không',
                className: 'btn-warning'
            }
        },
        callback: function (result) {
            if (result)
                callback();
        }
    })
}
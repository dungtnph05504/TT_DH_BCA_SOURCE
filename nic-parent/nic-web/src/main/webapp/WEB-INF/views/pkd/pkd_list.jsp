<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>


<c:url var="uploadFile" value="/servlet/pkdCtrl/save"/>
<c:url var="deleteFile" value="/servlet/pkdCtrl/delete"/>
<c:url var="deleteFolder" value="/servlet/pkdCtrl/deleteF"/>
<c:url var="addFolder" value="/servlet/pkdCtrl/addF"/>
<style>
table#row > tr {
	height: 35px;
}
.displayTag tr.odd {
	height: 35px;
}
.displayTag tr.even {
	height: 35px;
}

.custom-menu {
    display: none;
    z-index: 1000;
    position: absolute;
    overflow: hidden;
    border: 1px solid #CCC;
    white-space: nowrap;
    font-family: sans-serif;
    background: #FFF;
    color: #333;
    border-radius: 5px;
    padding: 0;
}

/* Each of the items in the list */
.custom-menu li {
    padding: 8px 12px;
    cursor: pointer;
    list-style-type: none;
    transition: all .3s ease;
    user-select: none;
}

.custom-menu li:hover {
    background-color: #DEF;
}
</style>
<div class="form-desing">
	<div class="row">
		<div class="ov_hidden">
			<div class="content_main">
				<div class="new-heading-2">${fnSelected}</div>
				<fieldset>
					<legend>PKD</legend>

					<div style="height: 400px; overflow: auto;">
						<ul role="tree" aria-labelledby="tree_label" id="pkdID">
							<li role="treeitem" aria-expanded="false" tabindex="-1">
								${pkdHtml}</li>
						</ul>
					</div>

				</fieldset>

			</div>
		</div>
	</div>
</div>

<ul class="custom-menu">
    <li data-action="three" id="OpenFileUpload"><span class="glyphicon glyphicon-upload"></span>Thêm tệp tin mới</li>
    <li data-action="first" data-toggle="modal" data-target="#thumucmoi"><span class="glyphicon glyphicon-plus-sign"></span>Thêm thư mục mới</li>
    <li data-action="second"><span class="glyphicon glyphicon-minus-sign"></span>Xóa thư mục</li>
</ul>

<div style="display: flex;flex: 0 auto;justify-content: center;">
<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
	<div style="margin: 10px"> 	
  		<button type="button" class="btn btn-success" id="btnReload">
			<span class="glyphicon glyphicon-repeat"></span> Tải lại
		</button>
		<!-- <button type="button" class="btn btn-success" id="OpenFileUpload">
			<span class="glyphicon glyphicon-upload"></span> Upload tệp tin
		</button> -->
		<button type="button" class="btn btn-success" id="btnRemove">
			<span class="glyphicon glyphicon-trash"></span> Xóa tệp tin
		</button>	
		 <input type="file" id="imgupload" style="display:none"/>
	</div>
</div>
</div>

<div class="modal fade" id="thumucmoi" role="dialog"
	aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document"
		style="width: 500px">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLongTitle"
					style="display: inline-block;">Nhập tên thư mục</h5>
				<p style="color:white; margin-top:8px" class="category" id="urladdnew"></p>
				<%-- <button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<img style="width: 25px; height: 25px;"
						src="<c:url value='/resources/images/dongform.png' />"
						title="Đóng" />
				</button> --%>
			</div>
			<div class="modal-body" id="idTaiLieu">
				 	<label class="control-label"> Tên thư mục <i style="color:red">(*)</i></label>
                                    <input type="text" name="planmae" id="txt_name" maxlength="30" class="form-control" />
				</div>
			<div class="modal-footer" style="padding: 5px;">
				<a class="btn btn-success" id="btnAdd" style="float: right;"><span class="glyphicon glyphicon-ok"></span> Lưu</a>
				<button type="button" class="btn btn-warning" data-dismiss="modal"
					aria-label="Close" style="float: right; margin-right: 10px;">
					<span class="glyphicon glyphicon-remove"></span> Đóng
				</button>

			</div>
		</div>
	</div>
</div>

<script>
//$('#dtBasicExample').DataTable();
//$('.dataTables_length').addClass('bs-select');

var ffocus = "";

$(function() {
	$('#btnAdd').click(function () {    	
    	if ($("#txt_name").val() != null && $("#txt_name").val() != '') {
    		var yourArray = {};
    		yourArray['ffocus'] = ffocus;
    		yourArray['name'] = $('#txt_name').val();
            $.ajax({
                url: '${addFolder}',
                data: JSON.stringify(yourArray),
                type: 'POST', 
                contentType : 'application/json',
                success: function (d) {
                    if (d == 'error') {
                    	$.notify("Có lỗi khi tạo thư mục.", {
	    					globalPosition: 'bottom left',
	    					className: 'warn',
	    				});
                    }  else {
                    	$.notify("Tạo thư mục thành công.", {
	    					globalPosition: 'bottom left',
	    					className: 'success',
	    				});
                        setTimeout(function () {
                            location.reload();
                        }, 1000);
                    }
                },
                error: function () {
                	$.notify("Lỗi hệ thống.", {
    					globalPosition: 'bottom left',
    					className: 'warn',
    				});
                }
            });
        }
        else {
        	$.notify("Nhập tên thư mục.", {
				globalPosition: 'bottom left',
				className: 'warn',
			});
        }
    });
	
    var loadMeg = '${codeValueForm.status}';
    if(loadMeg == 'success'){
    	var meg = '${codeValueForm.message}';
    	$.notify(meg, {
			globalPosition: 'bottom left',
			className: 'success',
		});
    }else if(loadMeg == 'fail'){
    	var meg = '${codeValueForm.message}';
    	$.notify(meg, {
			globalPosition: 'bottom left',
			className: 'warn',
		});
    }else if(loadMeg == 'newCode'){
    	var codeId = '${codeValueForm.codeId}';
    	$.notify('Thêm thành công mã: ' + codeId, {
			globalPosition: 'bottom left',
			className: 'success',
		});
    }

    $('#OpenFileUpload').click(function () { $('#imgupload').trigger('click'); });

    $("#imgupload").change(function () {
        var formData = new FormData();
        var totalFiles = document.getElementById("imgupload").files.length;
        for (var i = 0; i < totalFiles; i++) {
            var file = document.getElementById("imgupload").files[i];
            formData.append("imgupload", file);
            formData.append("ffocus", ffocus);
        }

        $.ajax({
            type: "POST",
            url: '${uploadFile}',
            data: formData,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            success: function (data) {
            	if(data == 'error'){
            		$.notify("Đã có lỗi khi upload.", {
    					globalPosition: 'bottom left',
    					className: 'warn',
    				});
            	}
            	else{
            		$.notify("Upload thành công.", {
    					globalPosition: 'bottom left',
    					className: 'success',
    				});
                    setTimeout(function () {
                        location.reload();
                    }, 1000);
            	}
            },
            error: function (error) {
            	$.notify("Lỗi hệ thống.", {
					globalPosition: 'bottom left',
					className: 'warn',
				});
            }
        })
    });

    $('#btnReload').click(function () { location.reload(); });

    $('#btnRemove').click(function () {
        var arr = [];
        var formData = new FormData();
        $('input[name=objid]:checked').each(function () {
            arr.push($(this).val());
        });
        formData.append("ids", arr);
         if (arr.length <= 0) {
        	 $.notify("Chưa chọn tệp tin.", {
					globalPosition: 'bottom left',
					className: 'warn',
				});
            }
            else {
                var r = confirm("Bạn thật sự chắc chắn muốn xóa?");
                if (r) {
                    $.ajax({
                        url: '${deleteFile}',
                        data: formData,
                        type: 'POST', 
                        contentType : 'application/json',
                        success: function (d) {
                            if (d == 'error') {
                            	$.notify("Đã có lỗi khi xóa.", {
                					globalPosition: 'bottom left',
                					className: 'warn',
                				});
                            } else {
                            	$.notify("Xóa tệp tin thành công.", {
                					globalPosition: 'bottom left',
                					className: 'success',
                				});
                                setTimeout(function () {
                                    location.reload();
                                }, 1000);
                            }
                        },
                        error: function () {
                        	$.notify("Lỗi hệ thống.", {
            					globalPosition: 'bottom left',
            					className: 'warn',
            				});
                        }
                    });
                }
          }
    });
    
    $(document).bind("contextmenu", function (event) {
        ffocus = "";
        // Avoid the real one
        event.preventDefault();

        // Show contextmenu
        $(".custom-menu").finish().toggle(100).
            // In the right position (the mouse)
            css({
                top: event.pageY + "px",
                left: event.pageX + "px"
            });
        ffocus = $("#pkdID").find('.focus').attr("url");
    });


    // If the document is clicked somewhere
    $(document).bind("mousedown", function (e) {

        // If the clicked element is not the menu
        if (!$(e.target).parents(".custom-menu").length > 0) {

            // Hide it
            $(".custom-menu").hide(100);
        }
    });


    // If the menu element is clicked
    $(".custom-menu li").click(function () {

        // This is the triggered action name
        switch ($(this).attr("data-action")) {

            // A case for each action. Your actions here
            case "first":
                if (ffocus != null && ffocus != '') {
                    $("#urladdnew").html("Đường dẫn tạo thư mục: " + ffocus);
                }
                else {
                    $("#urladdnew").html("Thư mục mới sẽ được tạo trong thư mục gốc.");
                }
                $("#txt_name").val();
                $('#AddNewmodal').modal('show');
                break;
            case "second":
                if (ffocus != null && ffocus != '') {
                    var r = confirm("Chắc chắn muốn xóa thư mục [ " + ffocus + " ]?");
                    if (r) {
                    	var yourArray = {};
                		yourArray['ffocus'] = ffocus;
                         $.ajax({
                            url: '${deleteFolder}',
                            data: JSON.stringify(yourArray),
                            type: 'POST', 
                            contentType : 'application/json',
                            success: function (d) {
                                if (d == 'error') {
                                	$.notify("Có lỗi khi xóa thư mục.", {
                    					globalPosition: 'bottom left',
                    					className: 'warn',
                    				});
                                } else {
                                	$.notify("Xóa thư mục thành công.", {
                    					globalPosition: 'bottom left',
                    					className: 'success',
                    				});
                                	
                                    setTimeout(function () {
                                        location.reload();
                                    }, 1000);
                                }
                            },
                            error: function () {
                            	$.notify("Lỗi hệ thống.", {
                					globalPosition: 'bottom left',
                					className: 'warn',
                				});
                            }
                        });
                    }
                }
                break;
        }

        // Hide it AFTER the action was triggered
        $(".custom-menu").hide(100);
    });
});

</script>
 
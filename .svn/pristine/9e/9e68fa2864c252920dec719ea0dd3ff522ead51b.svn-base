function InitPortraitDevice() {
  jQuery.support.cors = true;

 
  var postData = {
    deviceID: TP_DEVICE_ID
  };
  var dataSet = JSON.stringify(postData);
 

  var urlString = ws_urlbase + "SPID_Portrait_InitDev";

  $.ajax({
    type: 'POST',
    cache: false,
    url: urlString,
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    crossDomain: true,
    data: JSON.stringify(postData),

    success: function (response) {
    	 return response;
     
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

  var urlString = ws_urlbase + "SPID_Portrait_CloseDev";

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
      alert(textStatus);
    }
  });
}
function ReInitPortraitUI(init) {
  document.getElementById("portrait1").src = "";
  $('#fp1').empty();

  var btn = document.getElementById("btn_PTInit");
  var btn1 = document.getElementById("btn_PTCapture");
  var btn2 = document.getElementById("btn_PTUninit");

  if (init == true) {
    btn.disabled = true;
    btn1.disabled = false;
    btn2.disabled = false;
  } else {
    btn.disabled = false;
    btn1.disabled = true;
    btn2.disabled = true;
  }
}




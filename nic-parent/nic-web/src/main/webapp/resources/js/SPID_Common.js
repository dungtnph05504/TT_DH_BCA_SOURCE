
//Service
var ws_port = 8088;
var ws_urlbase = "http://localhost:" + ws_port + "/SPIDService/";


//Ten print
var TP_DEVICE_ID = 3; //Suprema RealScan
var TP_SHOW_INIT =1;

//Photo
var PC_DEVICE_ID = 11; // 11Webcam
var PC_DEVICE_INDEX = 0;
var PC_CAPTURE_MODE = 5; //PORTRAIT_ICAO_FACE
var PC_IMAGE_FORMAT = 3;// JPG
var PC_WIDTH= 480;
var PC_HEIGHT = 640;
var PC_FIXED_TRACK = 0;

function str2ByteArr(str) {
  var bytes = [];

  for (var i = 0; i < str.length; ++i) {
    bytes.push(str.charCodeAt(i));
    bytes.push(0);
  }
  return bytes;
}

function arrayBufferToBase64(buffer) {
  var binary = '';
  var bytes = new Uint8Array(buffer);
  var len = bytes.byteLength;
  for (var i = 0; i < len; i++) {
    binary += String.fromCharCode(bytes[i]);
  }
  return window.btoa(binary);
}

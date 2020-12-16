package com.nec.asia.nic.comp.trans.domain;

public class ConvertQueue {
	public static String[] objectType={"HS","DA","DB","DC","UPP","HSF","BF","PS"};
    public static String convertStringToCode(String objecType){
   	 switch (objecType) {
			case "Hồ sơ xử lý":
				objecType = "HS";
				break;
			case "Hồ sơ nghi trùng":
				objecType = "BF";
				break;
			case "Danh sách A":
				objecType = "DA";
					break;
			case "Danh sách B":
				objecType = "DB";
				break;
			case "Danh sách C":
				objecType = "DC";
				break;
			case "Hồ sơ xử lý đầy đủ":
				objecType = "HSF";
				break;
			case "Thông tin person":
				objecType = "PS";
				break;
			case "Hộ chiếu":
				objecType = "PP";
				break;
			case "Cập nhật hộ chiếu":
				objecType = "UPP";
				break;	
			default:
				break;
			}
   	 return objecType;
    }
    public static String convertCodeToString(String objecType){
      	 switch (objecType) {
   			case "HS":
   				objecType = "Hồ sơ xử lý";
   				break;
   			case "BF":
   				objecType = "Hồ sơ nghi trùng";
   				break;
   			case "DA":
   				objecType = "Danh sách A";
   					break;
   			case "DB":
   				objecType = "Danh sách B";
   				break;
   			case "DC":
   				objecType = "Danh sách C";
   				break;
   			case "HSF":
   				objecType = "Hồ sơ xử lý đầy đủ";
   				break;
   			case "PS":
   				objecType = "Thông tin person";
   				break;
   			case "UPP":
   				objecType = "Cập nhật hộ chiếu";
   				break;	
   			default:
   				break;
   			}
      	 return objecType;
       }
    public static String converCodeToStatus(String status){
    	String statusStr= "";
    	switch (status) {
		case "PENDING":
			statusStr = "Chưa xử lý";
			break;
		case "DOING":
			statusStr = "Đang xử lý";
			break;
		case "NONE":
			statusStr = "Lỗi xử lý";
			break;
		case "DONE":
			statusStr = "Xử lý thành công";
			break;
		default:
			break;
		}
    	return statusStr;
    }
    
}

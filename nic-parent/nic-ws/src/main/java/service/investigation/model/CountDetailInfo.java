package service.investigation.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "CountDetailInfo")
@XmlType(name = "CountDetailInfo", propOrder = { "abtcCount",
		"giayPhepXNCCount", "troLaiQTCount", "thoiQTCount", "hoiHuongCount",
		"xacMinhNSCount", "vkHoiHuongCount", "hsViPhamCount",
		"thongTinCapHCCount", "lichSuXuatNhapCanhCount"

})
public class CountDetailInfo {

	private int abtcCount; //ABTC
	private int giayPhepXNCCount; //XN
	private int troLaiQTCount; //TLQT
	private int thoiQTCount; //TQT
	private int hoiHuongCount; //HH
	private int xacMinhNSCount; //NS
	private int vkHoiHuongCount; //VH
	private int hsViPhamCount; //VP
	private int thongTinCapHCCount; //HC
	private int lichSuXuatNhapCanhCount; //LSXNC
	//

	public int getAbtcCount() {
		return abtcCount;
	}

	public int getLichSuXuatNhapCanhCount() {
		return lichSuXuatNhapCanhCount;
	}

	public void setLichSuXuatNhapCanhCount(int lichSuXuatNhapCanhCount) {
		this.lichSuXuatNhapCanhCount = lichSuXuatNhapCanhCount;
	}

	public void setAbtcCount(int abtcCount) {
		this.abtcCount = abtcCount;
	}

	public int getGiayPhepXNCCount() {
		return giayPhepXNCCount;
	}

	public void setGiayPhepXNCCount(int giayPhepXNCCount) {
		this.giayPhepXNCCount = giayPhepXNCCount;
	}

	public int getTroLaiQTCount() {
		return troLaiQTCount;
	}

	public void setTroLaiQTCount(int troLaiQTCount) {
		this.troLaiQTCount = troLaiQTCount;
	}

	public int getThoiQTCount() {
		return thoiQTCount;
	}

	public void setThoiQTCount(int thoiQTCount) {
		this.thoiQTCount = thoiQTCount;
	}

	public int getHoiHuongCount() {
		return hoiHuongCount;
	}

	public void setHoiHuongCount(int hoiHuongCount) {
		this.hoiHuongCount = hoiHuongCount;
	}

	public int getXacMinhNSCount() {
		return xacMinhNSCount;
	}

	public void setXacMinhNSCount(int xacMinhNSCount) {
		this.xacMinhNSCount = xacMinhNSCount;
	}

	public int getVkHoiHuongCount() {
		return vkHoiHuongCount;
	}

	public void setVkHoiHuongCount(int vkHoiHuongCount) {
		this.vkHoiHuongCount = vkHoiHuongCount;
	}

	public int getHsViPhamCount() {
		return hsViPhamCount;
	}

	public void setHsViPhamCount(int hsViPhamCount) {
		this.hsViPhamCount = hsViPhamCount;
	}

	public int getThongTinCapHCCount() {
		return thongTinCapHCCount;
	}

	public void setThongTinCapHCCount(int thongTinCapHCCount) {
		this.thongTinCapHCCount = thongTinCapHCCount;
	}

}

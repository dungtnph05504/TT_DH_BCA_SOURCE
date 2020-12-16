package service.perso.model;

import com.nec.asia.nic.comp.trans.dto.LdsResponseWsDto;

public class ReprintPassportInfo {
	private Boolean checkUpdateStatusPassport;
	private Boolean checkUpdatePassportNo;
	private LdsResponseWsDto packLDSInfo;
	public Boolean getCheckUpdatePassportNo() {
		return checkUpdatePassportNo;
	}
	public void setCheckUpdatePassportNo(Boolean checkUpdatePassportNo) {
		this.checkUpdatePassportNo = checkUpdatePassportNo;
	}
	public Boolean getCheckUpdateStatusPassport() {
		return checkUpdateStatusPassport;
	}
	public void setCheckUpdateStatusPassport(Boolean checkUpdateStatusPassport) {
		this.checkUpdateStatusPassport = checkUpdateStatusPassport;
	}
	public LdsResponseWsDto getPackLDSInfo() {
		return packLDSInfo;
	}
	public void setPackLDSInfo(LdsResponseWsDto packLDSInfo) {
		this.packLDSInfo = packLDSInfo;
	}
}

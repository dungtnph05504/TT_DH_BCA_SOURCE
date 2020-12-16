package service.investigation.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ObjDetailBuffPerson")
@XmlType(name = "ObjDetailBuffPerson", propOrder = { "transactionId",
		"personCode", "photo", "matchPointDetai", "FP_01", "FP_02", "FP_03",
		"FP_04", "FP_05", "FP_06", "FP_07", "FP_08", "FP_09", "FP_10",
		"countDetail"

})
public class ObjDetailBuffPerson {

	private String transactionId;
	private String personCode;
	private String photo;
	private String matchPointDetai;
	private String FP_01;
	private String FP_02;
	private String FP_03;
	private String FP_04;
	private String FP_05;
	private String FP_06;
	private String FP_07;
	private String FP_08;
	private String FP_09;
	private String FP_10;
	private CountDetailInfo countDetail;
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getPersonCode() {
		return personCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getMatchPointDetai() {
		return matchPointDetai;
	}
	public void setMatchPointDetai(String matchPointDetai) {
		this.matchPointDetai = matchPointDetai;
	}
	public String getFP_01() {
		return FP_01;
	}
	public void setFP_01(String fP_01) {
		FP_01 = fP_01;
	}
	public String getFP_02() {
		return FP_02;
	}
	public void setFP_02(String fP_02) {
		FP_02 = fP_02;
	}
	public String getFP_03() {
		return FP_03;
	}
	public void setFP_03(String fP_03) {
		FP_03 = fP_03;
	}
	public String getFP_04() {
		return FP_04;
	}
	public void setFP_04(String fP_04) {
		FP_04 = fP_04;
	}
	public String getFP_05() {
		return FP_05;
	}
	public void setFP_05(String fP_05) {
		FP_05 = fP_05;
	}
	public String getFP_06() {
		return FP_06;
	}
	public void setFP_06(String fP_06) {
		FP_06 = fP_06;
	}
	public String getFP_07() {
		return FP_07;
	}
	public void setFP_07(String fP_07) {
		FP_07 = fP_07;
	}
	public String getFP_08() {
		return FP_08;
	}
	public void setFP_08(String fP_08) {
		FP_08 = fP_08;
	}
	public String getFP_09() {
		return FP_09;
	}
	public void setFP_09(String fP_09) {
		FP_09 = fP_09;
	}
	public String getFP_10() {
		return FP_10;
	}
	public void setFP_10(String fP_10) {
		FP_10 = fP_10;
	}
	public CountDetailInfo getCountDetail() {
		return countDetail;
	}
	public void setCountDetail(CountDetailInfo countDetail) {
		this.countDetail = countDetail;
	}

}

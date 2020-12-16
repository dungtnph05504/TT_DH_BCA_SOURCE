package service.transaction.model;
/*
 * Đầu vào cho service tìm kiếm danh sách hồ sơ hộ chiếu để hủy.
 */
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="PassportLostInput")
@XmlType(name="PassportLostInput", propOrder={"passportNo", "archiveCode"})
public class PassportLostInput {
	private String passportNo;
	private String archiveCode;
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	public String getArchiveCode() {
		return archiveCode;
	}
	public void setArchiveCode(String archiveCode) {
		this.archiveCode = archiveCode;
	}
}

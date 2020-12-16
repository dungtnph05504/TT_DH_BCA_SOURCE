package service.transaction.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ArchiveDocument")
@XmlType(name = "ArchiveDocument", propOrder = { "archiveCode",
		"type", "regOfficeCode", "createdDate", "createdName", "listDocument", "packNo", "createdUserName" })
public class ArchiveDocument {

	private String archiveCode;
	private String type;
	private String regOfficeCode;
	private String createdDate;
	private String createdUserName;
	private String createdName;
	private String packNo;
	private List<ListDocument> listDocument;

	public String getCreatedUserName() {
		return createdUserName;
	}

	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}

	public String getPackNo() {
		return packNo;
	}

	public void setPackNo(String packNo) {
		this.packNo = packNo;
	}

	public String getArchiveCode() {
		return archiveCode;
	}

	public void setArchiveCode(String archiveCode) {
		this.archiveCode = archiveCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRegOfficeCode() {
		return regOfficeCode;
	}

	public void setRegOfficeCode(String regOfficeCode) {
		this.regOfficeCode = regOfficeCode;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedName() {
		return createdName;
	}

	public void setCreatedName(String createdName) {
		this.createdName = createdName;
	}

	public List<ListDocument> getListDocument() {
		return listDocument;
	}

	public void setListDocument(List<ListDocument> listDocument) {
		this.listDocument = listDocument;
	}

}

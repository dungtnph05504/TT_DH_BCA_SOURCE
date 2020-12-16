package service.transaction.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="Documents")
@XmlType(name="Documents", propOrder={"documentList"})
public class Documents {
	private List<TransactionDocument> documentList;
	
	public Documents() {
		super();
	}

	public Documents(List<TransactionDocument> documentList) {
		super();
		this.documentList = documentList;
	}

	public List<TransactionDocument> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<TransactionDocument> documentList) {
		this.documentList = documentList;
	}
	
}

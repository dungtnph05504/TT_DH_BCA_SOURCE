package service.perso.model.sync;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Attachment")
@XmlType ( name = "Attachment",  propOrder = { "id", "type", "serialNo", "base64" } )
public class Attachment {

	private Long id;
	private String type;
	private Integer serialNo;
	private String base64;
	
	public Attachment(){
		
	}
	
	public Attachment(Long id, String type, Integer serialNo, String base64) {
		super();
		this.id = id;
		this.type = type;
		this.serialNo = serialNo;
		this.base64 = base64;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}
}

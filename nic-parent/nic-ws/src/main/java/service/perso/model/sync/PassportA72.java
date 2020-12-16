package service.perso.model.sync;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.nec.asia.nic.util.DateHandler;

@XmlRootElement(name = "PassportA72")
@XmlType ( name = "PassportA72",  propOrder = { "id", "type", "signerposition", "signername", "createst", 
		"passportno", "dateofexpiry", "dateofissue", "fpencode", "isepassport", "createdby", "icaoline1",
		"icaoline2", "updatets", "status", "person", "fpposition", "fp_wsq_1", "fp_wsq_2" , "idQueue", "issuingAuthority"} )
public class PassportA72 {

	private String id;
	private String type;
	private String signerposition;
	private String signername;
	@JsonSerialize(using=DateHandler.class)
	private Date createst;
	private String passportno;
	@JsonSerialize(using=DateHandler.class)
    private Date dateofexpiry;
	@JsonSerialize(using=DateHandler.class)
    private Date dateofissue;
    private String fpencode;
    private String isepassport;
    private String createdby;
    private String icaoline1;
    private String icaoline2;
    @JsonSerialize(using=DateHandler.class)
    private Date updatets;
    private String status;
    private Person person;
    //khac
    private String fpposition;
    private byte[] fp_wsq_1;
    private byte[] fp_wsq_2;
    
    private Long idQueue;
    private String issuingAuthority;
    
    public PassportA72() {
		
	}
    
	public PassportA72(String id, String type, String signerposition,
			String signername, Date createst, String passportno,
			Date dateofexpiry, Date dateofissue, String fpencode,
			String isepassport, String createdby, String icaoline1,
			String icaoline2, Date updatets, String status, Person person,
			String fpposition, byte[] fp_wsq_1, byte[] fp_wsq_2) {
		super();
		this.id = id;
		this.type = type;
		this.signerposition = signerposition;
		this.signername = signername;
		this.createst = createst;
		this.passportno = passportno;
		this.dateofexpiry = dateofexpiry;
		this.dateofissue = dateofissue;
		this.fpencode = fpencode;
		this.isepassport = isepassport;
		this.createdby = createdby;
		this.icaoline1 = icaoline1;
		this.icaoline2 = icaoline2;
		this.updatets = updatets;
		this.status = status;
		this.person = person;
		this.fpposition = fpposition;
		this.fp_wsq_1 = fp_wsq_1;
		this.fp_wsq_2 = fp_wsq_2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSignerposition() {
		return signerposition;
	}

	public void setSignerposition(String signerposition) {
		this.signerposition = signerposition;
	}

	public String getSignername() {
		return signername;
	}

	public void setSignername(String signername) {
		this.signername = signername;
	}

	public Date getCreatest() {
		return createst;
	}

	public void setCreatest(Date createst) {
		this.createst = createst;
	}

	public String getPassportno() {
		return passportno;
	}

	public void setPassportno(String passportno) {
		this.passportno = passportno;
	}

	public Date getDateofexpiry() {
		return dateofexpiry;
	}

	public void setDateofexpiry(Date dateofexpiry) {
		this.dateofexpiry = dateofexpiry;
	}

	public Date getDateofissue() {
		return dateofissue;
	}

	public void setDateofissue(Date dateofissue) {
		this.dateofissue = dateofissue;
	}

	public String getFpencode() {
		return fpencode;
	}

	public void setFpencode(String fpencode) {
		this.fpencode = fpencode;
	}

	public String getIsepassport() {
		return isepassport;
	}

	public void setIsepassport(String isepassport) {
		this.isepassport = isepassport;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getIcaoline1() {
		return icaoline1;
	}

	public void setIcaoline1(String icaoline1) {
		this.icaoline1 = icaoline1;
	}

	public String getIcaoline2() {
		return icaoline2;
	}

	public void setIcaoline2(String icaoline2) {
		this.icaoline2 = icaoline2;
	}

	public Date getUpdatets() {
		return updatets;
	}

	public void setUpdatets(Date updatets) {
		this.updatets = updatets;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getFpposition() {
		return fpposition;
	}

	public void setFpposition(String fpposition) {
		this.fpposition = fpposition;
	}

	public byte[] getFp_wsq_1() {
		return fp_wsq_1;
	}

	public void setFp_wsq_1(byte[] fp_wsq_1) {
		this.fp_wsq_1 = fp_wsq_1;
	}

	public byte[] getFp_wsq_2() {
		return fp_wsq_2;
	}

	public void setFp_wsq_2(byte[] fp_wsq_2) {
		this.fp_wsq_2 = fp_wsq_2;
	}

	public Long getIdQueue() {
		return idQueue;
	}

	public void setIdQueue(Long idQueue) {
		this.idQueue = idQueue;
	}

	public String getIssuingAuthority() {
		return issuingAuthority;
	}

	public void setIssuingAuthority(String issuingAuthority) {
		this.issuingAuthority = issuingAuthority;
	}
    
    
}

package service.perso.model.sync;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.nec.asia.nic.util.DateHandler;

@XmlRootElement(name = "Person")
@XmlType ( name = "Person",  propOrder = { "gender", "dateofbirth", "_dateofbirth", "idnumber", 
		"name", "placeofbirth", "placeofidissue", "nationality", "attachment", "idPerson",
		"pobCode", "pobName", "issueCode", "issueName", "address", "fatherName", "fatherDob", "motherName", "motherDob","defDateOfBirth","pobA08Id"} )
public class Person {

	private String gender;
	@JsonSerialize(using=DateHandler.class)
	private Date dateofbirth;
	@JsonSerialize(using=DateHandler.class)
	private Date _dateofbirth;
	private String idnumber;
    private String name;
    private String placeofbirth;
    private String placeofidissue;
    private String nationality;
    private String idPerson;
    
    private String pobCode;
    private String pobName;
    private String issueCode;
    private String issueName;
    private String address;
    private String fatherName;
    @JsonSerialize(using=DateHandler.class)
    private Date fatherDob;
    private String motherName;
    @JsonSerialize(using=DateHandler.class)
    private Date motherDob;
    private String defDateOfBirth; //kieu ngay sinh
    private String pobA08Id; //ma noi sinh A08
    private List<Attachment> attachment = new ArrayList<Attachment>();
    
    public Person(){
    	
    }
   
	public Person(String gender, Date dateofbirth, Date _dateofbirth,
			String idnumber, String name, String placeofbirth,
			String placeofidissue, String nationality, String idPerson,
			String pobCode, String pobName, String issueCode, String issueName,
			String address, String fatherName, Date fatherDob,
			String motherName, Date motherDob, List<Attachment> attachment) {
		super();
		this.gender = gender;
		this.dateofbirth = dateofbirth;
		this._dateofbirth = _dateofbirth;
		this.idnumber = idnumber;
		this.name = name;
		this.placeofbirth = placeofbirth;
		this.placeofidissue = placeofidissue;
		this.nationality = nationality;
		this.idPerson = idPerson;
		this.pobCode = pobCode;
		this.pobName = pobName;
		this.issueCode = issueCode;
		this.issueName = issueName;
		this.address = address;
		this.fatherName = fatherName;
		this.fatherDob = fatherDob;
		this.motherName = motherName;
		this.motherDob = motherDob;
		this.attachment = attachment;
	}
	
	public String getPobA08Id() {
		return pobA08Id;
	}

	public void setPobA08Id(String pobA08Id) {
		this.pobA08Id = pobA08Id;
	}

	public String getDefDateOfBirth() {
		return defDateOfBirth;
	}

	public void setDefDateOfBirth(String defDateOfBirth) {
		this.defDateOfBirth = defDateOfBirth;
	}

	public String getPobCode() {
		return pobCode;
	}

	public void setPobCode(String pobCode) {
		this.pobCode = pobCode;
	}

	public String getPobName() {
		return pobName;
	}

	public void setPobName(String pobName) {
		this.pobName = pobName;
	}

	public String getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(String issueCode) {
		this.issueCode = issueCode;
	}

	public String getIssueName() {
		return issueName;
	}

	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public Date getFatherDob() {
		return fatherDob;
	}

	public void setFatherDob(Date fatherDob) {
		this.fatherDob = fatherDob;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public Date getMotherDob() {
		return motherDob;
	}

	public void setMotherDob(Date motherDob) {
		this.motherDob = motherDob;
	}

	

	public String getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(String idPerson) {
		this.idPerson = idPerson;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public Date get_dateofbirth() {
		return _dateofbirth;
	}

	public void set_dateofbirth(Date _dateofbirth) {
		this._dateofbirth = _dateofbirth;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlaceofbirth() {
		return placeofbirth;
	}

	public void setPlaceofbirth(String placeofbirth) {
		this.placeofbirth = placeofbirth;
	}

	public String getPlaceofidissue() {
		return placeofidissue;
	}

	public void setPlaceofidissue(String placeofidissue) {
		this.placeofidissue = placeofidissue;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public List<Attachment> getAttachment() {
		return attachment;
	}

	public void setAttachment(List<Attachment> attachment) {
		this.attachment = attachment;
	}
    
    
}

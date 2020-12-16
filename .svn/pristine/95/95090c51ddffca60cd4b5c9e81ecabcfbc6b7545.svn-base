package com.nec.asia.nic.comp.job.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO class for NIC-Perso Request.
 * 
 * Sample XML
 * <DataExchange source = "com.nec.asia.nic.dataexchange.persorequest" target = "com.nec.asia.perso.dataexchange.persorequest" version = "1.0" xmlns:ns2 = "http://trans.dx.nic.asia.nec.com/">
    <Perso
        CollectionSite = "RICHQ"
        Express = "0"
        Conversion = "1"
        TransactionID = "X10000000A"
        ReprintCount = "1"
        EstCollectionDate = "31 Dec 2013">
        <Surname_1>Test Surname 1</Surname_1>
        <Surname_2></Surname_2>
        <Firstname_1>Test Firstname 1</Firstname_1>
        <Firstname_2></Firstname_2>
        <SurnameAtBirth_1>Test Surname At Birth 1</SurnameAtBirth_1>
        <SurnameAtBirth_2></SurnameAtBirth_2>
        <DataOfBirth>30 May 1979</DataOfBirth>
        <AdditionalInformation></AdditionalInformation>
        <IDNumber>H3001234567091</IDNumber>
        <Gender>M</Gender>
        <Photo></Photo>
        <PhotoSmall></PhotoSmall>
        <Signature></Signature>
        <DateOfIssue>27 Nov 2012</DateOfIssue>
        <COM></COM>
        <DG1></DG1>
        <DG2></DG2>
        <DG3></DG3>
        <DG11></DG11>
        <DG13></DG13>
        <DG17></DG17>
        <SOD></SOD>
    </Perso>
</DataExchange>
 *
 */

/* 
 * Modification History:
 * 
 * 13 Jul 2013 (chris): Add field SC
 * 07 Nov 2013 (chris): Add attribute EstCollectionDate
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Perso")
public class PersoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "Surname_1")
    protected String surname1;
	@XmlElement(name = "Surname_2")
    protected String surname2;
    @XmlElement(name = "Firstname_1")
    protected String firstname1;
    @XmlElement(name = "Firstname_2")
    protected String firstname2;
    @XmlElement(name = "SurnameAtBirth_1")
    protected String surnameAtBirth1;
    @XmlElement(name = "SurnameAtBirth_2")
    protected String surnameAtBirth2;
    @XmlElement(name = "DataOfBirth")
    protected String dataOfBirth; /* Date-Format: DD MMM YYYY */
    @XmlElement(name = "AdditionalInformation")
    protected String additionalInformation;
    @XmlElement(name = "IDNumber")
    protected String idNumber;
    @XmlElement(name = "Gender", required = true)
    protected String gender;
    @XmlElement(name = "Photo", required = true)
    protected String photo;
    @XmlElement(name = "PhotoSmall", required = true)
    protected String photoSmall;
    @XmlElement(name = "Signature")
    protected String signature;
    @XmlElement(name = "SC")
    protected String sc;
    @XmlElement(name = "DateOfIssue", required = true)
    protected String dateOfIssue; /* Date-Format: DD MMM YYYY */
    @XmlElement(name = "COM", required = true)
    protected String com;
    @XmlElement(name = "DG1", required = true)
    protected String dg1;
    @XmlElement(name = "DG2", required = true)
    protected String dg2;
    @XmlElement(name = "DG3", required = true)
    protected String dg3;
    @XmlElement(name = "DG11", required = true)
    protected String dg11;
    @XmlElement(name = "DG13", required = true)
    protected String dg13;
    @XmlElement(name = "DG17", required = true)
    protected String dg17;
    @XmlElement(name = "SOD", required = true)
    protected String sod;
    @XmlAttribute(name = "CollectionSite")
    protected String collectionSite;
    @XmlAttribute(name = "Express")
    protected String express;
    @XmlAttribute(name = "Conversion")
    protected String conversion;
    @XmlAttribute(name = "TransactionID")
    protected String transactionID;
    @XmlAttribute(name = "ReprintCount")
    protected String reprintCount;
    @XmlAttribute(name = "EstCollectionDate")
    protected String estCollectionDate; /* Date-Format: DD MMM YYYY */
	
    public PersoDTO() {}
    
    public String getSurname1() {
		return surname1;
	}
	public void setSurname1(String surname1) {
		this.surname1 = surname1;
	}
	public String getSurname2() {
		return surname2;
	}
	public void setSurname2(String surname2) {
		this.surname2 = surname2;
	}
	public String getFirstname1() {
		return firstname1;
	}
	public void setFirstname1(String firstname1) {
		this.firstname1 = firstname1;
	}
	public String getFirstname2() {
		return firstname2;
	}
	public void setFirstname2(String firstname2) {
		this.firstname2 = firstname2;
	}
	public String getSurnameAtBirth1() {
		return surnameAtBirth1;
	}
	public void setSurnameAtBirth1(String surnameAtBirth1) {
		this.surnameAtBirth1 = surnameAtBirth1;
	}
	public String getSurnameAtBirth2() {
		return surnameAtBirth2;
	}
	public void setSurnameAtBirth2(String surnameAtBirth2) {
		this.surnameAtBirth2 = surnameAtBirth2;
	}
	public String getDataOfBirth() {
		return dataOfBirth;
	}
	public void setDataOfBirth(String dataOfBirth) {
		this.dataOfBirth = dataOfBirth;
	}
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getPhotoSmall() {
		return photoSmall;
	}
	public void setPhotoSmall(String photoSmall) {
		this.photoSmall = photoSmall;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getSC() {
		return sc;
	}
	public void setSC(String sc) {
		this.sc = sc;
	}
	public String getDateOfIssue() {
		return dateOfIssue;
	}
	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}
	public String getCom() {
		return com;
	}
	public void setCom(String com) {
		this.com = com;
	}
	public String getDg1() {
		return dg1;
	}
	public void setDg1(String dg1) {
		this.dg1 = dg1;
	}
	public String getDg2() {
		return dg2;
	}
	public void setDg2(String dg2) {
		this.dg2 = dg2;
	}
	public String getDg3() {
		return dg3;
	}
	public void setDg3(String dg3) {
		this.dg3 = dg3;
	}
	public String getDg11() {
		return dg11;
	}
	public void setDg11(String dg11) {
		this.dg11 = dg11;
	}
	public String getDg13() {
		return dg13;
	}
	public void setDg13(String dg13) {
		this.dg13 = dg13;
	}
	public String getDg17() {
		return dg17;
	}
	public void setDg17(String dg17) {
		this.dg17 = dg17;
	}
	public String getSod() {
		return sod;
	}
	public void setSod(String sod) {
		this.sod = sod;
	}
	public String getCollectionSite() {
		return collectionSite;
	}
	public void setCollectionSite(String collectionSite) {
		this.collectionSite = collectionSite;
	}
	public String getExpress() {
		return express;
	}
	public void setExpress(String express) {
		this.express = express;
	}
	public String getConversion() {
		return conversion;
	}
	public void setConversion(String conversion) {
		this.conversion = conversion;
	}
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public String getReprintCount() {
		return reprintCount;
	}
	public void setReprintCount(String reprintCount) {
		this.reprintCount = reprintCount;
	}
	public String getEstCollectionDate() {
		return estCollectionDate;
	}
	public void setEstCollectionDate(String estCollectionDate) {
		this.estCollectionDate = estCollectionDate;
	}
}

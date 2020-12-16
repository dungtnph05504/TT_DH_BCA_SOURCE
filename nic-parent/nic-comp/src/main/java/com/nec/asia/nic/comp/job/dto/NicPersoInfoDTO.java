package com.nec.asia.nic.comp.job.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * original format:
 * <NicPersoInfo Nin="A0123456789123 " PersoRefID="1" TransactionID="X10000000A" ReprintCount="0">
    <LogData>
      <PackageID>PKG.RICHQ.1307040002</PackageID>
      <Ccn>123456789</Ccn>
      <SAMKeyVersion>1</SAMKeyVersion>
    </LogData>
  </NicPersoInfo>
 * 
 * new format:
 * <NicPersoInfo PackageID="PKG.RICHQ.1307040002">
	<CardData Nin="A0123456789120" PersoRefID="1" TransactionID="X10000000A" ReprintCount="0" Ccn="123456780" SAMKeyVersion="1" />
	<CardData Nin="A0123456789121" PersoRefID="2" TransactionID="X10000000B" ReprintCount="0" Ccn="123456781" SAMKeyVersion="1" />
	<CardData Nin="A0123456789122" PersoRefID="3" TransactionID="X10000000C" ReprintCount="0" Ccn="123456782" SAMKeyVersion="1" />
	<CardData Nin="A0123456789123" PersoRefID="4" TransactionID="X10000000D" ReprintCount="0" Ccn="123456783" SAMKeyVersion="1" />
	<CardData Nin="A0123456789124" PersoRefID="5" TransactionID="X10000000E" ReprintCount="0" Ccn="123456784" SAMKeyVersion="1" />
	<CardData Nin="A0123456789125" PersoRefID="6" TransactionID="X10000000F" ReprintCount="0" Ccn="123456785" SAMKeyVersion="1" />
   </NicPersoInfo>
 *
 * perso rejection data:
 * <DataExchange xmlns:ns2="http://trans.dx.nic.asia.nec.com/" source="com.nec.asia.qc.component" target="com.nec.asia.nic" version="1.0">
     <NicPersoInfo Nin="S201070140446F" PersoRefID="15596" TransactionID="CCPL-2013-001026" ReprintCount="0">
        <LogInfo>
          <Reason>PERSO QC PERMANENT REJECT</Reason>
          <Remark>perso reject remark</Remark>
        </LogInfo>
     </NicPersoInfo>
   </DataExchange>
 *   
 */

/* 
 * Modification History:
 *  
 * 04 Sep 2013 (chris): Deprecated old DTO fields.
 * 03 Dec 2013 (chris): remove Deprecated annotation for rejection data
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="NicPersoInfo")
public class NicPersoInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "CardData")
	protected List<CardDataDTO> cardDataList;
	
    /* TODO remove this */
	@Deprecated
    @XmlElement(name = "LogData")    
    protected PersoLogDataDTO logData;
    
    @XmlElement(name = "LogInfo")
    protected LogInfoDTO logInfo;
    
    @XmlAttribute(name = "Nin")
    protected String nin;
    @XmlAttribute(name = "PersoRefID")
    protected String persoRefID;
    @XmlAttribute(name = "TransactionID")
    protected String transactionID;
    @XmlAttribute(name = "ReprintCount")
    protected String reprintCount;
    
    @XmlAttribute(name = "PackageID")
    protected String packageID;
    
    public NicPersoInfoDTO() {}
    

    public List<CardDataDTO> getCardDataList() {
    	if (cardDataList==null) {
    		cardDataList = new ArrayList<CardDataDTO>();
    	}
        return cardDataList;
    }

    public void setCardDataList(List<CardDataDTO> cardDataList) {
        this.cardDataList = cardDataList;
    }
    
    @Deprecated
    public PersoLogDataDTO getLogData() {
        return logData;
    }
    
    @Deprecated
    public void setLogData(PersoLogDataDTO logData) {
        this.logData = logData;
    }

    public LogInfoDTO getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(LogInfoDTO value) {
        this.logInfo = value;
    }
    
    public String getNin() {
        return nin;
    }

    public void setNin(String value) {
        this.nin = value;
    }

    public String getPersoRefID() {
        return persoRefID;
    }

    public void setPersoRefID(String value) {
        this.persoRefID = value;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String value) {
        this.transactionID = value;
    }

    public String getReprintCount() {
        return reprintCount;
    }

    public void setReprintCount(String value) {
        this.reprintCount = value;
    }

	public String getPackageID() {
		return packageID;
	}

	public void setPackageID(String packageID) {
		this.packageID = packageID;
	}

}

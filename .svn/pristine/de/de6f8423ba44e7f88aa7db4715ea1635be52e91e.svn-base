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

public class RequestLDSPack implements Serializable {
	private String sFinger1;
	private String Position1;
	private String sFinger2;
	private String Position2;
	
	public String getsFinger1() {
		return sFinger1;
	}
	public void setsFinger1(String sFinger1) {
		this.sFinger1 = sFinger1;
	}
	public String getPosition1() {
		return Position1;
	}
	public void setPosition1(String position1) {
		Position1 = position1;
	}
	public String getsFinger2() {
		return sFinger2;
	}
	public void setsFinger2(String sFinger2) {
		this.sFinger2 = sFinger2;
	}
	public String getPosition2() {
		return Position2;
	}
	public void setPosition2(String position2) {
		Position2 = position2;
	}
	
	
}

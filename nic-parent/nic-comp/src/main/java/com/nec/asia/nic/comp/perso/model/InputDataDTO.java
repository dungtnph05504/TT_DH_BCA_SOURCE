package com.nec.asia.nic.comp.perso.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * Sample XML:
 * 
<InputData>
  <Units>
    <Unit Name="ID60_SampleNecJob" Type="Job" Priority="2">
      <Comment></Comment>
      <Product>Demo</Product>
      <CustomerUnitData InputFormat="Hex"></CustomerUnitData>
      <UnitMatching InputFormat="Hex"></UnitMatching>
      <Unit Name="T0000000" Type="Card" Priority="2">
        <DataFields>
          <DataField Name="DocumentNumber">
            <Value InputFormat="Text">A0000000</Value>
          </DataField>
          .....
          <DataField Name="SOD">
            <Value InputFormat="Hex">0102030405060708090A0B0C0D0E0F10</Value>
          </DataField>
        </DataFields>
      </Unit>
    </Unit>
  </Units>
</InputData>
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="InputData")
public class InputDataDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String DATE_FORMAT = "dd-MM-yyyy";
	public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
	
	@XmlElement(name="Units")
    protected UnitDTO units;
	
	
	public InputDataDTO() {
		super();
	}


	public UnitDTO getUnits() {
		return units;
	}


	public void setUnits(UnitDTO units) {
		this.units = units;
	}

}

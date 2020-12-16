package service.perso.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.nec.asia.nic.comp.trans.domain.EppBlacklist;
import com.nec.asia.nic.comp.trans.domain.EppBlacklistAttachment;
import com.nec.asia.nic.comp.trans.domain.EppIdentification;
import com.nec.asia.nic.comp.trans.domain.EppIdentificationAttachmnt;

@XmlRootElement(name="FullIdentification")
@XmlType ( name = "FullIdentification",  propOrder = { "identifi", "atachment" } )

public class FullIdentification {
	private EppIdentification identifi;
	private EppIdentificationAttachmnt atachment;
	
	public EppIdentification getIdentifi() {
		return identifi;
	}
	public void setIdentifi(EppIdentification identifi) {
		this.identifi = identifi;
	}
	public EppIdentificationAttachmnt getAtachment() {
		return atachment;
	}
	public void setAtachment(EppIdentificationAttachmnt atachment) {
		this.atachment = atachment;
	}
	
	
}

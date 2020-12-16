package service.perso.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.nec.asia.nic.comp.trans.domain.EppBlacklist;
import com.nec.asia.nic.comp.trans.domain.EppBlacklistAttachment;

@XmlRootElement(name="FullBlackList")
@XmlType ( name = "FullBlackList",  propOrder = { "blacklist", "atachment" } )

public class FullBlackList {
	private EppBlacklist blacklist;
	private EppBlacklistAttachment atachment;
	
	public EppBlacklist getBlacklist() {
		return blacklist;
	}
	public void setBlacklist(EppBlacklist blacklist) {
		this.blacklist = blacklist;
	}
	public EppBlacklistAttachment getAtachment() {
		return atachment;
	}
	public void setAtachment(EppBlacklistAttachment atachment) {
		this.atachment = atachment;
	}
	
	
}

package service.perso.model.sync;

import java.util.ArrayList;
import java.util.List;

import com.nec.asia.nic.comp.trans.domain.EppBlacklist;
import com.nec.asia.nic.comp.trans.domain.EppBlacklistAttachment;
import com.nec.asia.nic.comp.trans.domain.EppIdentification;
import com.nec.asia.nic.comp.trans.domain.EppIdentificationAttachmnt;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.EppPersonAttchmnt;

public class DataSyncOther {
	private EppIdentification identi;
	private List<EppIdentificationAttachmnt> listidentiAttach = new ArrayList<EppIdentificationAttachmnt>();
	private EppBlacklist blacklist;
	private List<EppBlacklistAttachment> listBlacklist = new ArrayList<EppBlacklistAttachment>();
	
	public EppIdentification getIdenti() {
		return identi;
	}
	public void setIdenti(EppIdentification identi) {
		this.identi = identi;
	}
	public List<EppIdentificationAttachmnt> getListidentiAttach() {
		return listidentiAttach;
	}
	public void setListidentiAttach(
			List<EppIdentificationAttachmnt> listidentiAttach) {
		this.listidentiAttach = listidentiAttach;
	}
	public EppBlacklist getBlacklist() {
		return blacklist;
	}
	public void setBlacklist(EppBlacklist blacklist) {
		this.blacklist = blacklist;
	}
	public List<EppBlacklistAttachment> getListBlacklist() {
		return listBlacklist;
	}
	public void setListBlacklist(List<EppBlacklistAttachment> listBlacklist) {
		this.listBlacklist = listBlacklist;
	}
	
}

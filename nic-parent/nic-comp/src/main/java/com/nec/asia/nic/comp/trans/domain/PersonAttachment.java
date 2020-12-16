package com.nec.asia.nic.comp.trans.domain;

import java.util.LinkedList;
import java.util.List;

import net.sf.jasperreports.engine.util.LinkedMap;

public class PersonAttachment implements java.io.Serializable {

	private List<AttachmentDoc> perattachments = new LinkedList<AttachmentDoc>();
	
	public PersonAttachment(){
		
	}

	public List<AttachmentDoc> getPerattachments() {
		return perattachments;
	}

	public void setPerattachments(List<AttachmentDoc> perattachments) {
		this.perattachments = perattachments;
	}
}

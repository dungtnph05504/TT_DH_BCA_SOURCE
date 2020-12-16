package com.nec.asia.nic.comp.trans.domain;

import java.io.Serializable;

public class TemplatePassportImage implements Serializable{

		private static final long serialVersionUID = 5329465863548480774L;
		private long id;
		private long templatePassportId;
		private String docType;
		private byte[] docData;
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public long getTemplatePassportId() {
			return templatePassportId;
		}
		public void setTemplatePassportId(long templatePassportId) {
			this.templatePassportId = templatePassportId;
		}
		public String getDocType() {
			return docType;
		}
		public void setDocType(String docType) {
			this.docType = docType;
		}
		public byte[] getDocData() {
			return docData;
		}
		public void setDocData(byte[] docData) {
			this.docData = docData;
		}
		
		@Override
		public String toString() {
			return "TemplatePassportImage [id=" + id + ", templatePassportId="
					+ templatePassportId + ", docType=" + docType  + "]";
		}
		public TemplatePassportImage(long id, long templatePassportId,
				String docType, byte[] docData) {
			super();
			this.id = id;
			this.templatePassportId = templatePassportId;
			this.docType = docType;
			this.docData = docData;
		}
		public TemplatePassportImage() {
		}
		
		
		
}

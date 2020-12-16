package com.nec.asia.nic.investigation.controller;

import org.apache.commons.lang.StringUtils;


import com.nec.asia.nic.framework.common.ImageProperties;
import com.nec.asia.nic.framework.common.ImageUtil;
import com.nec.asia.nic.investigation.Base64Jp2HeaderHelper;

public class AttachmentEntry {

	public static final String INTERNAL_URL_PATH = "/servlet/documentAttachment/displayDocumentAttachment";
	
	public static final String INTERNAL__URL_DELIMITER = "/";

	private String attachmentTypeDescription;
	private String link;
	private byte[] image;

	private byte[] imageJpgSafe;

	public AttachmentEntry() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AttachmentEntry(String attachmentTypeDescription, String link) {
		super();
		this.attachmentTypeDescription = attachmentTypeDescription;
		this.link = link;
	}

	public AttachmentEntry(String attachmentTypeDescription, String link, byte[] image, boolean handleJp2) {
		super();
		this.attachmentTypeDescription = attachmentTypeDescription;
		this.link = link;
		this.image = image;
		if (handleJp2) {
			this.imageJpgSafe = this.getImage_jpgSafe(image);
		} else {
			this.imageJpgSafe = image;
		}
	}

	private byte[] getImage_jpgSafe(byte[] anImage) {

		if (anImage == null) {
			return null;
		}

		// get type
		String imageType = null;
		try {
			imageType = new ImageUtil().checkImageType(anImage);
		} catch (Exception e) {
			return null;
		}

		// if jpg, return
		if (StringUtils.isNotBlank(imageType) && imageType.equalsIgnoreCase(ImageUtil.IMAGE_JPG)) {
			return anImage;
		}

		// if J2K, return
		if (StringUtils.isNotBlank(imageType) && imageType.equalsIgnoreCase(ImageUtil.IMAGE_J2K)) {
			try {
				//return SpidHelper.getInstance("Y").convertImageFormat(anImage, SpidHelper.IMAGE_J2K,
						//SpidHelper.IMAGE_JPG);
			} catch (Exception e) {
				return null;
			}
		}

		return null;
	}

	public ImageProperties getImageProperties() {
		return this.getImageProperties(this.image);
	}

	private ImageProperties getImageProperties(byte[] anImage) {

		byte[] theJpg = this.getImageJpgSafe();

		if (theJpg == null) {
			return null;
		}

		try {
			return new ImageUtil().getImageProperties(theJpg);
		} catch (Exception e) {
			return null;
		}
	}

	public String getImageString() {

		if (this.image == null) {
			return null;
		}

		return new Base64Jp2HeaderHelper().getBase64WithJp2Header(this.getImageJpgSafe());
	}

	public String getAttachmentTypeDescription() {
		return attachmentTypeDescription;
	}

	public void setAttachmentTypeDescription(String attachmentTypeDescription) {
		this.attachmentTypeDescription = attachmentTypeDescription;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public byte[] getImageJpgSafe() {
		return imageJpgSafe;
	}

	public void setImageJpgSafe(byte[] imageJpgSafe) {
		this.imageJpgSafe = imageJpgSafe;
	}

}

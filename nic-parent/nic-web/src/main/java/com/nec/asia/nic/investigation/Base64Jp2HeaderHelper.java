package com.nec.asia.nic.investigation;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.nec.asia.nic.framework.common.ImageUtil;

public class Base64Jp2HeaderHelper {

	public static final String JP2_HEADER = "     ";

	public String getBase64WithJp2Header(byte[] image) {

		String base64String = Base64.encodeBase64String(image);

		String imageType = null;
		{
			try {
				imageType = new ImageUtil().checkImageType(image);
			} catch (Exception e) {
				return base64String;
			}
		}

		if (StringUtils.isBlank(imageType)) {
			return base64String;
		}

		if (imageType.equalsIgnoreCase(ImageUtil.IMAGE_J2K)) {
			return Base64Jp2HeaderHelper.JP2_HEADER + base64String;
		}

		return base64String;
	}
}

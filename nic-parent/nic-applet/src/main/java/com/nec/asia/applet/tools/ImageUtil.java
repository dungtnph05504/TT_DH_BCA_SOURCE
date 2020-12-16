package com.nec.asia.applet.tools;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageUtil {

	public static final String IMAGE_GIF = "IMAGE_GIF";
	public static final String IMAGE_PNG = "IMAGE_PNG";
	public static final String IMAGE_MNG = "IMAGE_MNG";
	public static final String IMAGE_JNG = "IMAGE_JNG";
	public static final String IMAGE_JPG = "IMAGE_JPG";
	public static final String IMAGE_BMP = "IMAGE_BMP";
	public static final String IMAGE_PCX = "IMAGE_PCX";
	public static final String IMAGE_IFF = "IMAGE_IFF";
	public static final String IMAGE_RAS = "IMAGE_RAS";
	public static final String IMAGE_PNM = "IMAGE_PNM";
	public static final String IMAGE_PSD = "IMAGE_PSD";
	public static final String IMAGE_SWF = "IMAGE_SWF";
	public static final String IMAGE_ICO = "IMAGE_ICO";
	public static final String IMAGE_TGA = "IMAGE_TGA";
	public static final String IMAGE_J2K = "IMAGE_J2K";
	public static final String IMAGE_WSQ = "IMAGE_WSQ";
	public static final String IMAGE_TIF = "IMAGE_TIF";

	public String checkImageType(byte[] fileBytes) throws IOException {
		InputStream is = new BufferedInputStream(new ByteArrayInputStream(fileBytes));
		// If we can't read ahead safely, just give up on guessing
		if (!is.markSupported())
			return null;

		is.mark(12);
		int c1 = is.read();
		int c2 = is.read();
		int c3 = is.read();
		int c4 = is.read();
		int c5 = is.read();
		int c6 = is.read();
		int c7 = is.read();
		int c8 = is.read();
		int c9 = is.read();
		int c10 = is.read();
		int c11 = is.read();
		is.reset();

		if (c1 == 0x47 && c2 == 0x49 && c3 == 0x46) {
			return IMAGE_GIF;
		} else if (c1 == 0x89 && c2 == 0x50 && c3 == 0x4e) {
			return IMAGE_PNG;
		} else if (c1 == 0x8a && c2 == 0x4d && c3 == 0x4e) {
			return IMAGE_MNG;
		} else if (c1 == 0x8B && c2 == 0x4a && c3 == 0x4e) {
			return IMAGE_JNG;
		} else if (c1 == 0xff && c2 == 0xd8) {
			return IMAGE_JPG;
		} else if (c1 == 0x42 && c2 == 0x4d) {
			return IMAGE_BMP;
		} else if (c1 == 0x0a && c2 < 0x06 && c3 == 0x01) {
			return IMAGE_PCX;
		} else if (c1 == 0x46 && c2 == 0x4f && c3 == 0x52) {
			return IMAGE_IFF;
		} else if (c1 == 0x59 && c2 == 0xa6 && c3 == 0x6a) {
			return IMAGE_RAS;
		} else if (c1 == 0x50 && c2 >= 0x31 && c2 <= 0x36) {
			return IMAGE_PNM;
		} else if (c1 == 0x38 && c2 == 0x42 && c3 == 0x50) {
			return IMAGE_PSD;
		} else if (c1 == 0x46 && c2 == 0x57) {
			return IMAGE_SWF;
		} else if (c1 == 0x00 && c2 == 0x00 && (c3 == 0x01 || c3 == 0x02)) {
			return IMAGE_ICO;
		} else if ((c2 == 0x01 && (c3 == 0x01 || c3 == 0x09 || c3 == 0x20 || c3 == 0x21))
				|| (c2 == 0x00 && (c3 == 0x02 || c3 == 0x03 || c3 == 0x0A || c3 == 0x0B))) {
			return IMAGE_TGA;
		} else if ((c1 == 0xFF && c2 == 0x4F && c3 == 0xFF) || (c1 == 0x00 && c2 == 0x00 && c3 == 0x00 && c4 == 0x0C
				&& c5 == 0x6A && c6 == 0x50 && c7 == 0x20 && c8 == 0x20)) {
			return IMAGE_J2K;
		} else if (c1 == 0xFF && c2 == 0xA0 && c3 == 0xFF) {
			return IMAGE_WSQ;
		} else if ((c1 == 0x4D && c2 == 0x4D) || (c1 == 0x49 && c2 == 0x49)) {
			return IMAGE_TIF;
		}
		return null;
	}

	public ImageProperties getImageProperties(byte[] docData) throws IOException {
		BufferedImage bufferedImage = this.getBufferedImage(docData);
		return new ImageProperties(bufferedImage.getWidth(), bufferedImage.getHeight());
	}

	public BufferedImage getBufferedImage(byte[] data) throws IOException {
		InputStream in = new ByteArrayInputStream(data);
		return ImageIO.read(in);
	}
}

package com.nec.asia.applet.tools.jp2;

import com.nec.asia.applet.tools.ImageUtil;
import com.nec.asia.applet.tools.StringUtils;

import sg.com.nec.spid.Imaging;

public class Jp2ConverterSpidThread implements Runnable {

	byte[] inputImage;

	boolean doneProcessing;
	Exception exceptionThrown;

	byte[] outputImage;

	public Jp2ConverterSpidThread(byte[] inputImage) {
		super();
		this.inputImage = inputImage;
	}

	@Override
	public void run() {

		if (!(this.isCandidateForConversion(this.getInputImage()))) {
			this.setOutputImage(this.getInputImage());
			this.setDoneProcessing(true);
			return;
		}

		this.convert();
	}

	private void convert() {

		try {
			byte[] inData = this.getInputImage();

			byte[] jpg = this.convertImageFormat(inData, IMAGE_J2K, IMAGE_JPG);

			this.setOutputImage(jpg);
			this.setDoneProcessing(true);
		} catch (Exception e) {
			this.setExceptionThrown(e);
			this.setDoneProcessing(true);
		}
	}

	public final static int IMAGE_RAW = 0;
	public final static int IMAGE_JPG = 3;
	public final static int IMAGE_J2K = 6;

	private synchronized byte[] convertImageFormat(byte[] imageBuf, int inFormat, int outFormat) {

		Imaging imaging = new Imaging();

		long statusCode = 0;
		if (inFormat == IMAGE_RAW) {
			statusCode = imaging.ImageFormatConvert(imageBuf, imageBuf.length, 512, 512, 8, inFormat, outFormat);
		} else {
			statusCode = imaging.ImageFormatConvert(imageBuf, imageBuf.length, 0, 0, 0, inFormat, outFormat);
		}

		if (statusCode == 0) {
			return imaging.outBuf;
		} else {
			return null;
		}
	}

	private boolean isCandidateForConversion(byte[] anImage) {

		try {
			if (anImage == null) {
				return false;
			}

			String imageType = new ImageUtil().checkImageType(anImage);

			if (new StringUtils().isNotBlank(imageType) && imageType.equalsIgnoreCase(ImageUtil.IMAGE_J2K)) {
				return true;
			}

			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public byte[] getInputImage() {
		return inputImage;
	}

	public void setInputImage(byte[] inputImage) {
		this.inputImage = inputImage;
	}

	public boolean isDoneProcessing() {
		return doneProcessing;
	}

	public void setDoneProcessing(boolean doneProcessing) {
		this.doneProcessing = doneProcessing;
	}

	public byte[] getOutputImage() {
		return outputImage;
	}

	public void setOutputImage(byte[] outputImage) {
		this.outputImage = outputImage;
	}

	public Exception getExceptionThrown() {
		return exceptionThrown;
	}

	public void setExceptionThrown(Exception exceptionThrown) {
		this.exceptionThrown = exceptionThrown;
	}
}

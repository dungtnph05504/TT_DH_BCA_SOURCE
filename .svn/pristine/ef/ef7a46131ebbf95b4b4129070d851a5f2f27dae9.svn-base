package com.nec.asia.applet.tools.jp2;

import java.util.Date;

//@Service("jp2ConverterSpid")
public class Jp2ConverterSpidImpl implements Jp2Converter {

	@Override
	public byte[] toJpg(byte[] jp2) throws Jp2ConversionException, Jp2ConversionTimeOutException {

		try {
			Jp2ConverterSpidThread threadHelper = new Jp2ConverterSpidThread(jp2);
			Thread thread = new Thread(threadHelper);
			thread.start();

			long timeStartMilliseconds = new Date().getTime();
			while (!(threadHelper.isDoneProcessing())) {
				if (new Date().getTime() - timeStartMilliseconds > Constants.JP2_CONVERSION__TIME_LIMIT__MILLISECONDS) {
					throw new Jp2ConversionTimeOutException();
				}

				Thread.sleep(Constants.JP2_CONVERSION__WAIT_BETWEEN_CHECKS__MILLISECONDS);
			}

			if (threadHelper.getExceptionThrown() != null) {
				throw new Jp2ConversionException(threadHelper.getExceptionThrown());
			}

			return threadHelper.getOutputImage();
		} catch (Jp2ConversionTimeOutException e) {
			throw e;
		} catch (Jp2ConversionException e) {
			throw e;
		} catch (Exception e) {
			throw new Jp2ConversionException(e);
		}

	}

}

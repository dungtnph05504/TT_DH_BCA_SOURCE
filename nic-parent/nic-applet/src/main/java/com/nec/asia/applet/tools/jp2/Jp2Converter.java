package com.nec.asia.applet.tools.jp2;

public interface Jp2Converter {

	public byte[] toJpg(byte[] jp2) throws Jp2ConversionException, Jp2ConversionTimeOutException;
}

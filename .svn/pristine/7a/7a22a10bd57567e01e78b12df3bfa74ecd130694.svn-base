package com.nec.asia.nic.utils;

import com.thoughtworks.xstream.XStream;

public class XmlMessageUtil {

	private static XStream xStream = null;
	
	public static XStream getXmlParser() {
		if (xStream==null) {
			xStream = new XStream();
		}
		return xStream;
	}
	
	public static String parseObjectToXml(Object object) {
		String xml = null;
		if (object != null) {
			xml = getXmlParser().toXML(object);
		}
		return xml;
	}
	
	public static Object parseXmlToObject(String xml) {
		Object object = null;
		if (xml != null) {
			object = getXmlParser().fromXML(xml);
		}
		return object;
	}
}

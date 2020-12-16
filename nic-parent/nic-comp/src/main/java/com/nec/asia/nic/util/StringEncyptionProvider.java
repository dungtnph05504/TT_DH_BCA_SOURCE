package com.nec.asia.nic.util;

public interface StringEncyptionProvider {
	String ecrypt(String readableString) throws Exception;
	String decrypt(String encryptedString) throws Exception;
}

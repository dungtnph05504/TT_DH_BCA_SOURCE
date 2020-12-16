package com.nec.asia.nic.utils.encryption;

public interface StringEncyptionProvider {
	String ecrypt(String readableString) throws Exception;
	String decrypt(String encryptedString) throws Exception;
}

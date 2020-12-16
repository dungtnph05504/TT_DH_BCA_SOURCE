package com.nec.asia.nic.enquiry.controller;

public class FingerprintInfo{
	
	private String fpImage;
	private Integer fpQuaScr;
	private String fpIndicator;
	private Integer goodFPQuaScr;
	private Integer accetableFPQuaScr;
	private Integer fpVerifyScr;
	private boolean encodeFlag = false;
	private String baseImageConvert;
	
	public String getBaseImageConvert() {
		return baseImageConvert;
	}
	public void setBaseImageConvert(String baseImageConvert) {
		this.baseImageConvert = baseImageConvert;
	}
	/**
	 * @return the fpImage
	 */
	public String getFpImage() {
		return fpImage;
	}
	/**
	 * @param fpImage the fpImage to set
	 */
	public void setFpImage(String fpImage) {
		this.fpImage = fpImage;
	}
	/**
	 * @return the fpQuaScr
	 */
	public Integer getFpQuaScr() {
		return fpQuaScr;
	}
	/**
	 * @param fpQuaScr the fpQuaScr to set
	 */
	public void setFpQuaScr(Integer fpQuaScr) {
		this.fpQuaScr = fpQuaScr;
	}
	/**
	 * @return the fpVerifyScr
	 */
	public Integer getFpVerifyScr() {
		return fpVerifyScr;
	}
	/**
	 * @param fpVerifyScr the fpVerifyScr to set
	 */
	public void setFpVerifyScr(Integer fpVerifyScr) {
		this.fpVerifyScr = fpVerifyScr;
	}
	/**
	 * @return the fpIndicator
	 */
	public String getFpIndicator() {
		return fpIndicator;
	}
	/**
	 * @param fpIndicator the fpIndicator to set
	 */
	public void setFpIndicator(String fpIndicator) {
		this.fpIndicator = fpIndicator;
	}
	/**
	 * @return the encodeFlag
	 */
	public boolean isEncodeFlag() {
		return encodeFlag;
	}
	/**
	 * @param encodeFlag the encodeFlag to set
	 */
	public void setEncodeFlag(boolean encodeFlag) {
		this.encodeFlag = encodeFlag;
	}
	/**
	 * @return the goodFPQuaScr
	 */
	public Integer getGoodFPQuaScr() {
		return goodFPQuaScr;
	}
	/**
	 * @param goodFPQuaScr the goodFPQuaScr to set
	 */
	public void setGoodFPQuaScr(Integer goodFPQuaScr) {
		this.goodFPQuaScr = goodFPQuaScr;
	}
	/**
	 * @return the accetableFPQuaScr
	 */
	public Integer getAccetableFPQuaScr() {
		return accetableFPQuaScr;
	}
	/**
	 * @param accetableFPQuaScr the accetableFPQuaScr to set
	 */
	public void setAccetableFPQuaScr(Integer accetableFPQuaScr) {
		this.accetableFPQuaScr = accetableFPQuaScr;
	}
}

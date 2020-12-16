package com.nec.asia.nic.common;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.nec.asia.nic.framework.admin.site.domain.SiteGroups;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;

public class EppSiteRepoForm {

	private String siteId;
	private String siteFunctionType;
	private String country;
	private String siteName;
	private String address;
	private String zipCode;
	private String city;
	private String region;
	private String siteNumber;
	private String activeIndicator;
	private String createVersion;
	private String updateVersion;
	private String authority;
	private String groupId;
	private String groupName;
	private String groupCreateVersion;
	private String groupUpdateVersion;
	private String levelNic;
	private String levelNic_des;
	private String status;
	private String message;
	private Map<String, String> sysSiteGroupMap;
	private Map<String, String> sysNationalityMap;
	private Map<String, String> sysRegionMap;
	private Map<String, String> sysSiteFunctionTypeMap;
	private Map<String, String> sysSiteCodeMap;	
	private String searchText;
	private String mode;
	private String typeArea;
	private String archiveCode;
	
	public String getTypeArea() {
		return typeArea;
	}
	public void setTypeArea(String typeArea) {
		this.typeArea = typeArea;
	}
	public String getLevelNic() {
		return levelNic;
	}
	public void setLevelNic(String levelNic) {
		this.levelNic = levelNic;
	}
	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}
	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	/**
	 * @return the siteId
	 */
	public String getSiteId() {
		return siteId;
	}
	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	/**
	 * @return the siteFunctionType
	 */
	public String getSiteFunctionType() {
		return siteFunctionType;
	}
	/**
	 * @param siteFunctionType the siteFunctionType to set
	 */
	public void setSiteFunctionType(String siteFunctionType) {
		this.siteFunctionType = siteFunctionType;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the siteName
	 */
	public String getSiteName() {
		return siteName;
	}
	/**
	 * @param siteName the siteName to set
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}
	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}
	/**
	 * @return the siteNumber
	 */
	public String getSiteNumber() {
		return siteNumber;
	}
	/**
	 * @param siteNumber the siteNumber to set
	 */
	public void setSiteNumber(String siteNumber) {
		this.siteNumber = siteNumber;
	}
	/**
	 * @return the activeIndicator
	 */
	public String getActiveIndicator() {
		return activeIndicator;
	}
	/**
	 * @param activeIndicator the activeIndicator to set
	 */
	public void setActiveIndicator(String activeIndicator) {
		this.activeIndicator = activeIndicator;
	}
	/**
	 * @return the createVersion
	 */
	public String getCreateVersion() {
		return createVersion;
	}
	/**
	 * @param createVersion the createVersion to set
	 */
	public void setCreateVersion(String createVersion) {
		this.createVersion = createVersion;
	}
	/**
	 * @return the updateVersion
	 */
	public String getUpdateVersion() {
		return updateVersion;
	}
	/**
	 * @param updateVersion the updateVersion to set
	 */
	public void setUpdateVersion(String updateVersion) {
		this.updateVersion = updateVersion;
	}
	/**
	 * @return the authority
	 */
	public String getAuthority() {
		return authority;
	}
	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return the groupCreateVersion
	 */
	public String getGroupCreateVersion() {
		return groupCreateVersion;
	}
	/**
	 * @param groupCreateVersion the groupCreateVersion to set
	 */
	public void setGroupCreateVersion(String groupCreateVersion) {
		this.groupCreateVersion = groupCreateVersion;
	}
	/**
	 * @return the groupUpdateVersion
	 */
	public String getGroupUpdateVersion() {
		return groupUpdateVersion;
	}
	/**
	 * @param groupUpdateVersion the groupUpdateVersion to set
	 */
	public void setGroupUpdateVersion(String groupUpdateVersion) {
		this.groupUpdateVersion = groupUpdateVersion;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the sysSiteGroupMap
	 */
	public Map<String, String> getSysSiteGroupMap() {
		return sysSiteGroupMap;
	}
	/**
	 * @param sysSiteGroupMap the sysSiteGroupMap to set
	 */
	public void setSysSiteGroupMap(Map<String, String> sysSiteGroupMap) {
		this.sysSiteGroupMap = sysSiteGroupMap;
	}
	/**
	 * @return the sysNationalityMap
	 */
	public Map<String, String> getSysNationalityMap() {
		return sysNationalityMap;
	}
	/**
	 * @param sysNationalityMap the sysNationalityMap to set
	 */
	public void setSysNationalityMap(Map<String, String> sysNationalityMap) {
		this.sysNationalityMap = sysNationalityMap;
	}
	/**
	 * @return the sysRegionMap
	 */
	public Map<String, String> getSysRegionMap() {
		return sysRegionMap;
	}
	/**
	 * @param sysRegionMap the sysRegionMap to set
	 */
	public void setSysRegionMap(Map<String, String> sysRegionMap) {
		this.sysRegionMap = sysRegionMap;
	}
	/**
	 * @return the sysSiteFunctionTypeMap
	 */
	public Map<String, String> getSysSiteFunctionTypeMap() {
		return sysSiteFunctionTypeMap;
	}	
	/**
	 * @return the sysSiteCodeMap
	 */
	public Map<String, String> getSysSiteCodeMap() {
		return sysSiteCodeMap;
	}
	/**
	 * @param sysSiteCodeMap the sysSiteCodeMap to set
	 */
	public void setSysSiteCodeMap(Map<String, String> sysSiteCodeMap) {
		this.sysSiteCodeMap = sysSiteCodeMap;
	}
	/**
	 * @param sysSiteFunctionTypeMap the sysSiteFunctionTypeMap to set
	 */
	public void setSysSiteFunctionTypeMap(Map<String, String> sysSiteFunctionTypeMap) {
		this.sysSiteFunctionTypeMap = sysSiteFunctionTypeMap;
	}
	
	/**
	 * @return the searchText
	 */
	public String getSearchText() {
		return searchText;
	}
	/**
	 * @param searchText the searchText to set
	 */
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
	public void parse(SiteRepository siteRepo) {
		if(siteRepo != null) {
			this.siteId = siteRepo.getSiteId();
			this.siteFunctionType = siteRepo.getSiteFunctionType();
			this.country = siteRepo.getCountry();
			this.siteName = siteRepo.getSiteName();
			this.address = siteRepo.getAddress();
			this.zipCode = siteRepo.getZipCode();
			this.city = siteRepo.getCity();
			this.region = siteRepo.getRegion();
			this.siteNumber = (null == siteRepo.getSiteNumber())? StringUtils.EMPTY: siteRepo.getSiteNumber().toString();
			this.activeIndicator = (siteRepo.getActiveIndicator() == null)? StringUtils.EMPTY: siteRepo.getActiveIndicator().trim();
			this.createVersion = (null == siteRepo.getCreateVersion())? StringUtils.EMPTY: siteRepo.getCreateVersion().toString();
			this.updateVersion = (null == siteRepo.getUpdateVersion())? StringUtils.EMPTY: siteRepo.getUpdateVersion().toString();
			this.authority = siteRepo.getAuthority();
			this.groupId = siteRepo.getSiteGroups().getGroupId();
			this.groupName = (siteRepo.getSiteGroups().getGroupName().isEmpty()? StringUtils.EMPTY : siteRepo.getSiteGroups().getGroupName());
			this.groupCreateVersion = (siteRepo.getSiteGroups().getCreateVersion() == null? StringUtils.EMPTY : siteRepo.getSiteGroups().getCreateVersion().toString());
			this.groupUpdateVersion = (siteRepo.getSiteGroups().getUpdateVersion() == null? StringUtils.EMPTY : siteRepo.getSiteGroups().getUpdateVersion().toString());
			this.status = StringUtils.EMPTY;
			this.message = StringUtils.EMPTY;
			this.levelNic = siteRepo.getSiteGroups().getLevelNic();
		}
	}
	
	public SiteGroups getSiteGroups() {
		return new SiteGroups(groupId, groupName, 
				StringUtils.isEmpty(groupCreateVersion)? null:Integer.parseInt(groupCreateVersion), 
				StringUtils.isEmpty(groupUpdateVersion)? null:Integer.parseInt(groupUpdateVersion), null, levelNic, levelNic_des, typeArea, archiveCode);
	}
	
	public SiteRepository getSiteRepository() {
		
		return new SiteRepository(siteId, new SiteGroups(groupId), siteFunctionType, country, siteName, address,
				zipCode, city, region, StringUtils.isBlank(siteNumber)? null:Integer.parseInt(siteNumber), activeIndicator, 
				StringUtils.isEmpty(createVersion)? null:Integer.parseInt(createVersion),
				StringUtils.isEmpty(updateVersion)? null:Integer.parseInt(updateVersion), authority);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EppSiteRepoForm [siteId=" + siteId + ", siteFunctionType=" + siteFunctionType + ", country=" + country
				+ ", siteName=" + siteName + ", address=" + address + ", zipCode=" + zipCode + ", city=" + city
				+ ", region=" + region + ", siteNumber=" + siteNumber + ", activeIndicator=" + activeIndicator
				+ ", createVersion=" + createVersion + ", updateVersion=" + updateVersion + ", authority=" + authority
				+ ", groupId=" + groupId + ", groupName=" + groupName + ", groupCreateVersion=" + groupCreateVersion
				+ ", groupUpdateVersion=" + groupUpdateVersion + ", status=" + status + ", message=" + message + "]";
	}
	
}

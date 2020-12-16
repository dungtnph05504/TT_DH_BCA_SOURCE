package com.nec.asia.nic.framework.admin.site.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author tuenv
 *
 */
public class SiteGroups implements java.io.Serializable {

	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    private String groupId;
	private String groupName;
	private Integer createVersion;
	private Integer updateVersion;
	private String levelNic;
	private String levelNic_des;
	private String typeArea;
	private String archiveCode;
	
	private Set<SiteRepository> siteRepositories = new HashSet<SiteRepository>(0);

	public SiteGroups() {
	}

	public SiteGroups(String groupId) {
		this.groupId = groupId;
	}

	public SiteGroups(String groupId, String groupName, Integer createVersion, Integer updateVersion,
			Set<SiteRepository> siteRepositories, String levelNic, String levelNic_des, String typeArea, String archiveCode) {
		this.groupId = groupId;
		this.groupName = groupName;
		this.createVersion = createVersion;
		this.updateVersion = updateVersion;
		this.siteRepositories = siteRepositories;
		this.levelNic = levelNic;
		this.levelNic_des = levelNic_des;
		this.typeArea = typeArea;
		this.archiveCode = archiveCode;
	}

	public String getTypeArea() {
		return typeArea;
	}

	public void setTypeArea(String typeArea) {
		this.typeArea = typeArea;
	}

	public String getLevelNic_des() {
		return levelNic_des;
	}

	public void setLevelNic_des(String levelNic_des) {
		this.levelNic_des = levelNic_des;
	}

	public String getLevelNic() {
		return levelNic;
	}

	public void setLevelNic(String levelNic) {
		this.levelNic = levelNic;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getCreateVersion() {
		return this.createVersion;
	}

	public void setCreateVersion(Integer createVersion) {
		this.createVersion = createVersion;
	}

	public Integer getUpdateVersion() {
		return this.updateVersion;
	}

	public void setUpdateVersion(Integer updateVersion) {
		this.updateVersion = updateVersion;
	}

	public Set<SiteRepository> getSiteRepositories() {
		return this.siteRepositories;
	}

	public void setSiteRepositories(Set<SiteRepository> siteRepositories) {
		this.siteRepositories = siteRepositories;
	}

	public String getArchiveCode() {
		return archiveCode;
	}

	public void setArchiveCode(String archiveCode) {
		this.archiveCode = archiveCode;
	}

	
}

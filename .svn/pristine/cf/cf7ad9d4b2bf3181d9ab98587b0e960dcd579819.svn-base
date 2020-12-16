package com.nec.asia.nic.comp.trans.utils;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


/**
 * The Class ReportDefinition.
 *
 * @author Peddi Swapna
 * @version 1.0
 * @since 20 Dec 2013
 */
@XStreamAlias("category")
public class ReportCategory {
	
	/** The label. */
	@XStreamAsAttribute
	@XStreamAlias("name")
	private String label;

	/**
	 * Instantiates a new report category.
	 */
	public ReportCategory() {
		
	}
	
	/**
	 * Instantiates a new report category.
	 *
	 * @param label the label
	 */
	public ReportCategory(String label) {
		this.label=label;
	}
	
	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 *
	 * @param label the new label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * Gets the report category list.
	 *
	 * @param labelList the label list
	 * @return the report category list
	 */
	public static List<ReportCategory> getReportCategoryList(List<String> labelList) {
		List<ReportCategory> categoryList = new ArrayList<ReportCategory>();
		for(String label:labelList){
			categoryList.add(new ReportCategory(label));
		}
		return categoryList;
	}
}

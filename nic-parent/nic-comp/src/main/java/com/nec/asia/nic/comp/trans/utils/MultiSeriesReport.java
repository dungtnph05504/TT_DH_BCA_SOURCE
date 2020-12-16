package com.nec.asia.nic.comp.trans.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class MultiSeriesReport {
   	
	/** The categories. */
	private List<ReportCategory> categories = new ArrayList<ReportCategory>();
   	
   	/** The dataset. */
	   @XStreamImplicit(itemFieldName="dataset")
	private List<ReportDataSet> dataset=new ArrayList<ReportDataSet>();
   	
	/** The series names. */
	private transient String[] seriesNames;
	
	/** The categories list. */
	private transient List<String> categoriesList = new ArrayList<String>();
   	
	   /** The report data set map. */
	   private transient HashMap<String, ReportDataSet> reportDataSetMap = new HashMap<String, ReportDataSet>();
   	
	/**
	 * Adds the data.
	 *
	 * @param seriesName the series name
	 * @param name the name
	 * @param value the value
	 * @param hoverText the hover text
	 */
	public void addData(String seriesName, String name, String value, String hoverText) {
		if(!categoriesList.contains(name)) {
			categoriesList.add(name);
			categories.add(new ReportCategory(name));
		}
		getReportDataSet(seriesName).add(value, hoverText,"JavaScript:myJS('"+seriesName+","+name+"');");
	}
	
	/**
	 * Adds the data.
	 *
	 * @param seriesName the series name
	 * @param name the name
	 * @param value the value
	 * @param hoverText the hover text
	 * @param color the color
	 */
	public void addData(String seriesName, String name, String value, String hoverText, String color) {
		if(!categoriesList.contains(name)) {
			categoriesList.add(name);
			categories.add(new ReportCategory(name));
		}
		getReportDataSet(seriesName).add(value, color, hoverText);
	}

	/**
	 * Adds the series data.
	 *
	 * @param name the name
	 * @param seriesValues the series values
	 */
	public void addSeriesData(String name, Object... seriesValues) {
		if(!categoriesList.contains(name)) {
			categoriesList.add(name);
			categories.add(new ReportCategory(name));
		}
		if(seriesNames.length!=seriesValues.length) {
			throw new RuntimeException("seriesNames.length is not equal to seriesValues.length");
		}
		for(int i=0;i<seriesNames.length;i++) {
			getReportDataSet(seriesNames[i]).add((String)seriesValues[i], null,"JavaScript:myJS(' ');");
		}
	}
	
	/**
	 * Gets the dataset.
	 *
	 * @return the dataset
	 */
	public List<ReportDataSet> getDataset() {
		return dataset;
	}

	/**
	 * Sets the dataset.
	 *
	 * @param dataset the new dataset
	 */
	public void setDataset(List<ReportDataSet> dataset) {
		this.dataset = dataset;
	}

	/**
	 * Gets the categories.
	 *
	 * @return the categories
	 */
	public List<ReportCategory> getCategories() {
		return categories;
	}

	/**
	 * Sets the categories.
	 *
	 * @param categories the new categories
	 */
	public void setCategories(List<ReportCategory> categories) {
		this.categories = categories;
	}


	/**
	 * Sets the series names.
	 *
	 * @param seriesNames the new series names
	 */
	public void setSeriesNames(String[] seriesNames) {
		this.seriesNames = seriesNames;
		if(seriesNames!=null && seriesNames.length>0) {
			for(int i=0;i<seriesNames.length;i++) {
				ReportDataSet reportDataSet = new ReportDataSet(seriesNames[i], null ,null);
				reportDataSetMap.put(seriesNames[i], reportDataSet);
			}
			dataset = new ArrayList<ReportDataSet>(reportDataSetMap.values());
		}
	}
	
	/**
	 * Gets the report data set.
	 *
	 * @param seriesName the series name
	 * @return the report data set
	 */
	public ReportDataSet getReportDataSet(String seriesName){
		if(!reportDataSetMap.containsKey(seriesName)) {
			ReportDataSet reportDataSet = new ReportDataSet(seriesName, null,null);
			reportDataSetMap.put(seriesName, reportDataSet);
			dataset.add(reportDataSet);
		}
		return reportDataSetMap.get(seriesName);
	}
	

}

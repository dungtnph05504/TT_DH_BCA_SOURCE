/**
 * 
 */
package com.nec.asia.nic.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author prasad_karnati
 *
 */
public class QueryUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(QueryUtil.class);
	
	public  Map<String,List<String>> getColumnNames(String tableQuery) {
		tableQuery = tableQuery.toUpperCase();
		if (tableQuery.contains("SELECT")) {
			tableQuery = tableQuery.replaceFirst("SELECT", " ").trim();
		}

		String [] columnsArray=tableQuery.split(",");
		ArrayList<String> list= new ArrayList<String>();
		String tableName="";
		for (int i = 0; i < columnsArray.length; i++) {
			if (!columnsArray[i].contains("From")
					&& !columnsArray[i].contains("FROM")
					&& !columnsArray[i].contains("from")) {
				list.add(columnsArray[i]);
			}
			if (columnsArray[i].contains("From")
					|| columnsArray[i].contains("FROM")
					|| columnsArray[i].contains("from")) {
				String array = columnsArray[i].trim();
				String[] tableArray = array.split(" ");
				list.add(tableArray[0].trim());
				for (int j = 0; j < tableArray.length; j++) {

					if (j > 0) {

						if (!tableArray[j].equals(" ")
								&& !tableArray[j].equals("")) {
							if (!tableArray[j].equals("from")
									&& !tableArray[j].equals("FROM")
									&& !tableArray[j].equals("from")) {
								logger.debug("===========" + tableArray[j]);
								if (tableArray[j].trim().contains(" ")) {
									String[] tableArrays = array.split(" ");
									tableName = tableArrays[0];
								} else if (tableArray[j].contains(";")) {
									tableName = tableArray[j].replaceAll(";"," ").trim();

								} else {
									tableName = tableArray[j].trim();
								}
							}
						}
					}
				}
			}
		 }	
		 
		 if(list.contains("FROM")){
			 list.remove("FROM");
		 }
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put(tableName, list);
		System.out.println("===" + list + "=====" + tableName);
		return map;

	}
	
	public static void main(String[] args) {

		String query = " seLeCt  ITEM_NUMBER,  NIN_NUMBER,  APP_REF_NUMBER,  FIRST_NAME,  SURNAME,  BIRTH_SURNAME,  GENDER   ,BIRTH_DATE, TXN_TYPE,  TXN_STATUS,  TXN_DATE,  OFFICE_NAME,  SERVED_BY,  REJECTED_BY,  REJECT_COMMENTS   from  NIC_REJECT_APP;";
		QueryUtil queryUtil = new QueryUtil();
		Map<String, List<String>> map = (Map<String, List<String>>) queryUtil.getColumnNames(query);

		for (Map.Entry<String, List<String>> emap : map.entrySet()) {
			String key = emap.getKey();
			ArrayList<String> value = (ArrayList<String>) emap.getValue();
			System.out.println("===" + key + "=====" + value);

		}
		Integer i = 20;

		if (i == 20) {
			System.out.println("============================11");

		}
		System.out.println("============================33");
		String s = query.toLowerCase();
		System.out.println("============================33" + s);

	}

}

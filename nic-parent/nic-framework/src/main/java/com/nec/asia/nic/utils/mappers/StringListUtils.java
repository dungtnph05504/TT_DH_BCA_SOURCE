package com.nec.asia.nic.utils.mappers;


import java.util.LinkedList;
import java.util.List;


public class StringListUtils {
	public static boolean isInList(String str,String[] list){
		List<String> lst = new LinkedList<String>();
		for (String string : list) {
			lst.add(string);
		}
		return isInList(str, lst);
	}
	
	public static boolean isInList(String str,List<String> list){
		return list.contains(str);
	}
}

package com.nec.asia.nic.util;

public class pagingUtil {
	public static int totalPage(int total, int pageSize){
		int totalPage = 1;
		if(total < pageSize){
			return totalPage;
		}else{
			int pageMax = total / pageSize;
			int memberPageLast = total % pageSize;
			if(memberPageLast > 0){
				pageMax += 1;
			}
			return pageMax;
		}
	}
	
}

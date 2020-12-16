package com.nec.asia.nic.comp.trans.domain;

import java.util.Date;

public class TempSync {
   private String status;
   private String date;
   private int count;
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public int getCount() {
	return count;
}
public void setCount(int sync_Retry) {
	this.count = sync_Retry;
}
   
}

package com.nec.asia.nic.statistical.controller;

import java.util.Date;

public class StatusticalForm {
   private String dateFrom;
   private String dateTo;
   private String loaiHangDoi;
   private String receiver;
public String getDateFrom() {
	return dateFrom;
}
public void setDateFrom(String dateFrom) {
	this.dateFrom = dateFrom;
}
public String getDateTo() {
	return dateTo;
}
public void setDateTo(String dateTo) {
	this.dateTo = dateTo;
}
public String getLoaiHangDoi() {
	return loaiHangDoi;
}
public void setLoaiHangDoi(String hangDoi) {
	this.loaiHangDoi = hangDoi;
}
public String getReceiver() {
	return receiver;
}
public void setReceiver(String receiver) {
	this.receiver = receiver;
}
   
}

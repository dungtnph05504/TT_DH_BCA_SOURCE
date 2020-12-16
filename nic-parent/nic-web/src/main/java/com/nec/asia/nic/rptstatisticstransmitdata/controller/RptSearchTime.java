package com.nec.asia.nic.rptstatisticstransmitdata.controller;

public class RptSearchTime {
	private String timeFrom;
	private String timeTo;
	private String type;
	public RptSearchTime(String timeFrom, String timeTo, String type) {
		super();
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;
		this.type = type;
	}
	public RptSearchTime() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getTimeFrom() {
		return timeFrom;
	}
	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}
	public String getTimeTo() {
		return timeTo;
	}
	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}

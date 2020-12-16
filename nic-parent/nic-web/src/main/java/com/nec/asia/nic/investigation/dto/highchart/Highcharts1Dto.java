package com.nec.asia.nic.investigation.dto.highchart;

import java.util.ArrayList;
import java.util.List;

public class Highcharts1Dto {
	private TitleDto title;
	private XAxisDto xAxis;
	private List<Series1Dto> series = new ArrayList<Series1Dto>();
	
	public TitleDto getTitle() {
		return title;
	}
	public void setTitle(TitleDto title) {
		this.title = title;
	}
	public XAxisDto getxAxis() {
		return xAxis;
	}
	public void setxAxis(XAxisDto xAxis) {
		this.xAxis = xAxis;
	}
	public List<Series1Dto> getSeries() {
		return series;
	}
	public void setSeries(List<Series1Dto> series) {
		this.series = series;
	}
	
	
}

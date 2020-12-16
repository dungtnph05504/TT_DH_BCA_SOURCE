package com.nec.asia.nic.investigation.dto.highchart;

import java.util.ArrayList;
import java.util.List;

public class HighchartsDto {
	
	private TitleDto title;
	private XAxisDto xAxis;
	private List<SeriesDto> series = new ArrayList<SeriesDto>();
	
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
	public List<SeriesDto> getSeries() {
		return series;
	}
	public void setSeries(List<SeriesDto> series) {
		this.series = series;
	}
	
}

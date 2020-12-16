package com.nec.asia.nic.util;

import java.io.InputStream;
import java.sql.Connection; 
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;


public class ReportUtil { 
	public static byte[] getPdfReport(Map<String, Object> parameterMap,
			InputStream reportStream,Connection conn) {
		byte[] baos;
		JasperPrint print; 
		try {
			JasperReport jasperReport = JasperCompileManager
					.compileReport(reportStream);  
			print = JasperFillManager.fillReport(jasperReport, parameterMap,
					conn);  
			baos = JasperExportManager.exportReportToPdf(print);
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		} 
		return baos;
	} 

}

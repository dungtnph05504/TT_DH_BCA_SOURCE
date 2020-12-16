package com.nec.asia.nic.admin.acl;

import java.util.Date;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.bufPersonInvestigation.service.BufPersonInvestigationService;
import com.nec.asia.nic.comp.trans.domain.BufEppPersonInvestigation;
import com.nec.asia.nic.framework.controller.AbstractController;


public class TestDataBuf extends AbstractController{
	
	@Autowired
	private BufPersonInvestigationService bufPersonInvestigationService;
	
/*	@Test
	public void TestData(){
		BufEppPersonInvestigation objBuf = new BufEppPersonInvestigation();
		objBuf.setTransactionId("TESING");
		objBuf.setCreateDatetime(new Date());
		objBuf.setCreateBy("SYSTEM");
		objBuf.setCreateWkstnId("SYSTEM-MACHINE");
		objBuf.setDataHistoryPassport("TEST THOI");
		objBuf.setDataImmihistory("TEST THOI");
		try {
			bufPersonInvestigationService.updateAndCreate(objBuf);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	@Test
	public void TestDataXML(){
		String xmlPerson = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><EPP_PERSONS><EPP_PERSON></EPP_PERSON></EPP_PERSONS>";
		JSONObject xmlJSONObj = XML.toJSONObject(xmlPerson);
		if(xmlJSONObj != null){
			JSONObject resp = xmlJSONObj.getJSONObject("EPP_PERSONS");
			JSONArray arrayResp = resp.getJSONArray("EPP_PERSON");
			for (int i = 0; i < arrayResp.length(); i++) {
				JSONObject myResponse = new JSONObject();
				myResponse = arrayResp.getJSONObject(i);
				String xmlData = myResponse.getString("PLACE_OF_BIRTH_ID");
				String xmlData1 = myResponse.getString("SEARCH_NAME");
				String xmlData2 = myResponse.getString("NAME");
				String xmlData3 = String.valueOf(myResponse.getInt("DATE_OF_BIRTH"));
				String xmlData4 = myResponse.getString("GENDER");
				String xmlData5 = String.valueOf(myResponse.getInt("ID"));
				String xmlData6 = myResponse.getString("TYPE_");
				String xmlData7 = myResponse.getString("NATIONALITY_ID");
				String a ="";
			}
		}
	}
}

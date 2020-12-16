package com.nec.asia.nic.comp.trans.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.xxxx.inventory.ws.Inventory;
import com.nec.asia.nic.comp.trans.service.InventoryRefService;
import com.nec.asia.nic.comp.trans.service.exception.InventoryRefServiceException;

@Service("inventoryRefService")
public class InventoryRefServiceImpl implements InventoryRefService {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//@Autowired
	//private Inventory inventoryWS; 

	@Override
	public boolean updateDispatchedStatus(List<String> documentNoList, String siteCode, String remark)
			throws InventoryRefServiceException {
		boolean updated = false;
		try {
			//i_OperationName = "dispatched"
			//i_Parameter = "{\"document_numbers\" : [\"D2344342\" , \"D2344343\" , \"D2344344\"] , \"location\" : \"0810\" ,  \"remark\" : \"dispatched with some remark\"}"
			
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("document_numbers", documentNoList);
			map.put("location", siteCode);
			map.put("remark", remark);
			
			String operationName = "dispatched";
			String parameters = mapToJson(map);
			String result = null; //inventoryWS.executeRequest(operationName, parameters);
			//"{\"ReturnCode":"XXXXXX\"}"
			Map<String, Object> resultMap = jsonToMap(result);
			String returnCode = (String) resultMap.get("ReturnCode");
			logger.info("inventoryWS.executeRequest({}, {}) return {}", new Object[] {operationName, parameters, returnCode});
			
			if (StringUtils.startsWith(returnCode, "4") || "500540".equals(returnCode)) {
				updated = true;
			}
		} catch (Exception e) {
			throw new InventoryRefServiceException(e);
		}
		return updated;
	}
	

	@Override
	public boolean updateIssuedStatus(List<String> documentNoList, String remark) throws InventoryRefServiceException {
		boolean updated = false;
		try {
			//i_OperationName = "issued"
			//i_Parameter = "{\"document_numbers\" : [\"D2344342\" ,  \"D2344343\" , \"D2344344\"] , \"remark\" : null}"
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("document_numbers", documentNoList);
			map.put("remark", remark);
			
			String operationName = "issued";
			String parameters = mapToJson(map);
			String result = null; //inventoryWS.executeRequest(operationName, parameters);
			Map<String, Object> resultMap = jsonToMap(result);
			String returnCode = (String) resultMap.get("ReturnCode");
			logger.info("inventoryWS.executeRequest({}, {}) return {}", new Object[] {operationName, parameters, returnCode});
			
			if (StringUtils.startsWith(returnCode, "4")) {
				updated = true;
			}
		} catch (Exception e) {
			throw new InventoryRefServiceException(e);
		}
		return updated;
	}

	@Override
	public boolean updateRejectedStatus(List<String> documentNoList, String remark) throws InventoryRefServiceException {
		boolean updated = false;
		try {
			//_OperationName = "rejected"
			//i_Parameter = "{\"document_numbers\" : [\"D2344342\" ,  \"D2344343\" , \"D2344344\"] , \"remark\" : \"some rejection reasons\"}"
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("document_numbers", documentNoList);
			map.put("remark", remark);
			
			String operationName = "rejected";
			String parameters = mapToJson(map);
			String result = null;//inventoryWS.executeRequest(operationName, parameters);
			Map<String, Object> resultMap = jsonToMap(result);
			String returnCode = (String) resultMap.get("ReturnCode");
			logger.info("inventoryWS.executeRequest({}, {}) return {}", new Object[] {operationName, parameters, returnCode});
			
			if (StringUtils.startsWith(returnCode, "4")) {
				updated = true;
			}
		} catch (Exception e) {
			throw new InventoryRefServiceException(e);
		}
		return updated;
	}

	private static String mapToJson(Map<String, Object> map) {
		StringBuffer sbJson = new StringBuffer();
		sbJson.append("{");
		int entryCount = 0;
		for (Entry<String, Object> entry : map.entrySet()) {
			String key = "\""+entry.getKey()+"\"";
			StringBuffer sbValue = new StringBuffer();
			if (entry.getValue()==null) {
				//sbValue.append("\"").append("\"");
				sbValue.append(entry.getValue());
			} else if (entry.getValue() instanceof String) {
				sbValue.append("\"").append(entry.getValue()).append("\"");
			} else if (entry.getValue() instanceof Collection) {
				@SuppressWarnings("unchecked")
				Collection<String> list = (Collection<String>) entry.getValue();
				sbValue.append("[");
				int itemCount = 0;
				for (String item : list) {
					if (itemCount>0) 
						sbValue.append(",");
					if (item==null)
						sbValue.append("\"").append("\"");
					else
						sbValue.append("\"").append(item).append("\"");
					itemCount++;
				}
				sbValue.append("]");
			}
			if (entryCount>0) 
				sbJson.append(",");
			sbJson.append(key).append(":").append(sbValue.toString());
			entryCount++;
		}
		sbJson.append("}");
		return sbJson.toString();
	}

	private static Map<String, Object> jsonToMap(String json) {
		Map<String, Object> map = new TreeMap<String, Object>();
		String temp = StringUtils.trim(json);
		if (StringUtils.startsWith(temp, "{") && StringUtils.endsWith(temp, "}")) {
			temp = StringUtils.substring(temp, 1, temp.length()-1);
			int startIndex = StringUtils.indexOf(temp, "\"");
			while (startIndex!=-1) {
				String remains = StringUtils.substring(temp, startIndex+1);
				int endIndex = StringUtils.indexOf(remains, "\"");
				String key = StringUtils.substring(temp, startIndex+1, startIndex+1+endIndex);
				temp = StringUtils.substring(temp, startIndex+endIndex+1);
				startIndex = StringUtils.indexOf(temp, ":");
				
				temp = StringUtils.substring(temp, startIndex+1);
				temp = StringUtils.trim(temp);
				if (StringUtils.startsWith(temp, "[")) {
					List<String> valueList = new java.util.ArrayList<String>();
					endIndex = StringUtils.indexOf(temp, "]");
					String valuePart = StringUtils.substring(temp, 1, endIndex);
					String[] values = valuePart.split(",");
					for (String item: values) {
						item = StringUtils.trim(item);
						if (StringUtils.startsWith(item, "\"") && StringUtils.endsWith(item, "\"")) {
							item = StringUtils.substring(item, 1, item.length()-1);
							valueList.add(item);
						}
					}
					map.put(key, valueList);
					temp = StringUtils.substring(temp, endIndex+1);
					startIndex = StringUtils.indexOf(temp, "\"");
				} else if (StringUtils.startsWith(temp, "\"")) {
					String value = null;
					remains = StringUtils.substring(temp, 1);
					endIndex = StringUtils.indexOf(remains, "\"");
					value = StringUtils.substring(temp, startIndex, startIndex+endIndex);
					map.put(key, value);
					temp = StringUtils.substring(remains, endIndex+1);
					startIndex = StringUtils.indexOf(temp, "\"");
				} else if (StringUtils.startsWith(temp, "null")) {
					String value = null;
					map.put(key, value);
					temp = StringUtils.substring(temp, 4);
					startIndex = StringUtils.indexOf(temp, "\"");
				}
			}
		}
		
		return map;
	}
	
//	public static void main(String[] args) {
//		List<String> documentNoList = java.util.Arrays.asList(new String[]{"D2344342","D2344343","D2344344"});
//		String siteCode = "0810";
//		String remark = "dispatched with some remark";
//		remark = null;
//		System.out.println("Input:");
//		Map<String, Object> map = new TreeMap<String, Object>();
//		map.put("document_numbers", documentNoList);
//		map.put("location", siteCode);
//		map.put("remark", remark);
//		
//		String json = mapToJson(map);
//		System.out.println(json);
//
//		Map<String, Object> mapResult = jsonToMap(json);
//		System.out.println(mapResult.keySet());
//		System.out.println(mapResult.values());
//		System.out.println("Output:");
//		json = "{\"ReturnCode\":\"XXXXXX\"}";
//		System.out.println(json);
//		mapResult = jsonToMap(json);
//		System.out.println(mapResult.keySet());
//		System.out.println(mapResult.values());
//	}
}

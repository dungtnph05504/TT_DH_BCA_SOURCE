package com.nec.asia.nic.admin;

import org.springframework.stereotype.Component;

import com.nec.asia.nic.admin.acl.Resource;
import com.nec.asia.nic.framework.admin.ApplicationScopeResourceLoaderService;


/**
 * demo code table implementation 
 * 
 * using mock data here, only for demo
 * 
 * @author bright_zheng
 *
 */
@Component("applicationScopeResourceLoaderService")
public class ApplicationResourceLoaderServiceImpl implements ApplicationScopeResourceLoaderService {
	
	public String[] loadAllCodeTypes(){
		return new String[]{"Gender","Education"};
	}
	
	public Object[] getCodesByType(String codeType){
		if(codeType.equals("Gender")){
			CodeTable g1 = new CodeTable("Gender","Male","m","Male Gender");
			CodeTable g2 = new CodeTable("Gender","Female","f","Female Gender");
			return new CodeTable[]{g1,g2};
		}else if(codeType.equals("Education")){
			CodeTable e1 = new CodeTable("Education","Degree","1","Degree Level");
			CodeTable e2 = new CodeTable("Education","Master","2","Master Level");	
			return new CodeTable[]{e1,e2};
		}
		return null;
	}

	@Override
	public boolean getUIResourceByUser(String userId, String resourceCode) {
		//suppose current user has just one resource here.
		Resource resource = new Resource(1L, "R1", "Test Resource 1", "/hotels/search");
		if(resource.getCode().equals(resourceCode)){
			return true;
		}
		return false;
	}

	@Override
	public boolean getUIResourceByUserByWS(String s, String s1, String s2) {
		// TODO Auto-generated method stub
		return false;
	}
}

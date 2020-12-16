package com.nec.asia.nic.admin.acl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.nec.asia.nic.framework.admin.acl.impl.AbstractAclService;
import com.nec.asia.nic.framework.admin.rbac.domain.Functions;
import com.nec.asia.nic.framework.admin.rbac.service.FunctionService;
import com.nec.asia.nic.web.session.UserSession;

/**
 * Demo implementation of AclService
 * 
 * @author bright_zheng
 *
 */

/* 
 * Modification History:
 * 
 * 20 Mar 2014 (chris): to cache the function resources in static resourceMap.
 */

@Component("aclService")
public class AclServiceImpl extends AbstractAclService<Resource> {

	private FunctionService functionService;
	private static Map<String, Resource> resourceMap = null;
	
	@Override
	public String getResourceUri(Resource resource) {
		return resource.getUri();
	}

//	@Override
//	public Resource[] getGrantedResources(HttpServletRequest request) {
//		// get the resources from current session
//		//Resource[] resources = (Resource[]) request.getSession(false)
//		//							.getAttribute(Constants.CURRENT_USER_GRANTED_RESOURCES);
//		//return resources;
//		
//		//TODO: mock data here
////		Resource resource1 = new Resource(1L, "R1", "Test Resource 1", "/hotels/search");
////		Resource resource2 = new Resource(2L, "R2", "Test Resource 2", "/hotels*");
////		Resource resource3 = new Resource(2L, "R3", "Test Resource 3", "/hotels.*");
////		Resource resource4 = new Resource(3L, "R4", "Test Resource 4", "/hotels/*");
////		Resource resource5 = new Resource(3L, "R5", "Test Resource 5", "/hotels/**");
////		Resource resource6 = new Resource(4L, "R6", "Test Resource 6", "/**");
////		Resource[] set1 = new Resource[]{resource1, resource2, resource3, resource4, resource5, resource6};
//		//Resource[] set2 = new Resource[]{resource6, resource5, resource4, resource3, resource2, resource1};
//		List<Resource> resources = new LinkedList<Resource>();
//		HttpSession session = request.getSession();
//		UserSession userSession = (UserSession) session.getAttribute("userSession");
//		mapToResource(resources, userSession);
//		Resource[] arr = resources.toArray(new Resource[resources.size()]);
//		return arr;
//	}
//
//	private void mapToResource(List<Resource> resources,UserSession userSession){
//		if(userSession == null)
//			return;
//		if(userSession.getFunctions() == null || userSession.getFunctions().size() <=0)
//			return;
//		for(String function: userSession.getFunctions()){
//			Functions func = functionService.findById(function);
//			if(func!=null){
//				Resource resource = new Resource();
//				resource.setCode(function);
//				resource.setName(function);
//				resource.setUri(func.getFunctionUrl());
//				resources.add(resource);
//			}
//		}
//	}
	
	//20 Mar 2014 (chris) - to cache the function resources in static resourceMap. - start
	@Override
	public Resource[] getGrantedResources(HttpServletRequest request) {
		List<Resource> resources = new ArrayList<Resource>();
		
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		if (userSession!=null && CollectionUtils.isNotEmpty(userSession.getFunctions())) {
			for(String function: userSession.getFunctions()){
				Resource resource = null;
				if (this.getResourceMap().get(function)!=null) {
					resource = this.getResourceMap().get(function);
				} else {
					Functions func = functionService.findById(function);
					if(func!=null){
						resource = new Resource();
						resource.setCode(function);
						resource.setName(function);
						resource.setUri(func.getFunctionUrl());
					}
				}
				if (resource!=null) {
					resources.add(resource);
				}
			}
		}
		return resources.toArray(new Resource[resources.size()]);
	}
	//20 Mar 2014 (chris) - to cache the function resources in static resourceMap. - end
	
	public Map<String, Resource> getResourceMap() {
		if (resourceMap==null) {
			resourceMap = new HashMap<String, Resource>();
			List<Functions> functionList = functionService.findAll();
			for (Functions func: functionList) {
				if (resourceMap.get(func.getFunctionId())==null) {
					Resource resource = new Resource();
					resource.setCode(func.getFunctionId());
					resource.setName(func.getFunctionId());
					resource.setUri(func.getFunctionUrl());
					resourceMap.put(func.getFunctionId(), resource);
				}
			}
		}
		return resourceMap;
	}

	/**
	 * @return the functionService
	 */
	public FunctionService getFunctionService() {
		return functionService;
	}

	/**
	 * @param functionService the functionService to set
	 */
	public void setFunctionService(FunctionService functionService) {
		this.functionService = functionService;
	}

}

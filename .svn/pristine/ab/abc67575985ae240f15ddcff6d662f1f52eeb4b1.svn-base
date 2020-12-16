package com.nec.asia.nic.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationObjectSupport;

import com.nec.asia.nic.framework.admin.ApplicationScopeResourceLoaderService;

/**
 * Application scope loader is a tool for exposing data to application scope
 * and jstl can access it through standard EL.
 * <p>
 * The data including:
 * 1. Class level constant. 
 * 		Because JSTL can NOT access our constant class properties directly,
 * 		it plays a wrapper role for that
 * 2. Code table data store in database. 
 * 		We can load this data from database
 * 		and put it to application scope 
 * <p>
 * It need to be configured in spring context:
 * <code>
  
 	<bean name="applicationScopeLoader" class="com.nec.asia.nric.utils.ApplicationScopeLoader" lazy-init="false">
 		<property name="constantClass">
			<map>
				<entry key="Constants" value="com.nec.asia.nric.framework.Constants"/>
				<!-- 
				<entry key ="Constants2" value="another.constants.class"/>
				-->
			</map>
		</property>
	</bean>
	
 * </code>
 * <p>
 * So we can get this data by standard EL syntax.
 * <p>
 * 1) For Constants, the syntax will be:
 * 	${[ConstantsKey].[ConstantPropertyInRealClass]}
 * For example:
 * <code>
 * 	${Constants.PAGE_SIZE_DEFAULT}
 * </code>
 * <p>
 * 2) For CodeTable, the syntax will be:
 * 	${CodeTable.[code_type]}, to get the code data list of specific type
 * 	and then we can iterate it by <c:forEach/> tag or bind it to Spring tags
 * For example:
 * a) get and iterate it:
 * <code>
 * 	<c:forEach var="ct" items="${CodeTable.Gender}">
  	-->codeName: ${ct.codeName},  
  	-->codeValue: ${ct.codeValue},  
  	-->codeDesc: ${ct.codeDesc}
	</c:forEach> 
 * </code>
 * b) bind it to Spring <form:select/> tag
 * <code>
   <form:select path="gender">
	<form:option value="-" label="--Please Select"/>
	<form:options items="${CodeTable.Gender}" itemValue="codeValue" itemLabel="codeName"/>
   </form:select>
   </code>
 * 
 * @author bright_zheng
 *
 */
public class ApplicationScopeLoader extends WebApplicationObjectSupport {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationScopeLoader.class);
	
	/** constant classes which can be injected by spring */
	private Map<String, String> constantClass;
	
	/** code table service */
	private final ApplicationScopeResourceLoaderService applicationScopeResourceLoaderService
		= ApplicationContextManager
			.getBean("applicationScopeResourceLoaderService",ApplicationScopeResourceLoaderService.class);
	
	/** code table context to retrieve type */
	private final static String CODE_TABLE_CONTEXT_NAME = "CodeTable";

	protected void initApplicationContext() {
		try {
			//build constants and put it to the application scope
			this.buildConstants();
			
			//build code table and put it to the application scope
			this.buildCodeTables();
		} catch (Exception e) {
			logger.error("Errors occurred while exposing Constants and Codetable to application scope",e);
		}
	}
	
	/**
	 * build constants map
	 * 
	 * @return constants map
	 * @throws ClassNotFoundException 
	 */
	private void buildConstants() throws ClassNotFoundException{
		Map<String, Object> result = new HashMap<String, Object>();
		for(Map.Entry<String, String> map: constantClass.entrySet()){
			//the constant group name
			String k = map.getKey();
			//the class
			String v = map.getValue();
			Class<?> clazz = this.getClass().getClassLoader().loadClass(v);
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				int modifier = field.getModifiers();
				//must be final & public
				if (Modifier.isFinal(modifier) && !Modifier.isPrivate(modifier)){
					String name = null;
					Object value = null;
					try {
						name = field.getName();
						value = field.get(this);
						logger.debug("building constants{}: name={},value={}", 
								new Object[]{k, name,value});
						result.put(name, value);
					} catch (IllegalAccessException e) {
						logger.error("Errors occurred while exposing Constants[name={},value={}] to application scope",
								new Object[]{name,value});
					}
				}
			}

			logger.info("put constant [{}] to application scope.", k);
			this.getServletContext().setAttribute(k, result);
		}
	}
	
	/**
	 * build code tables
	 * each code table will be group by the type
	 * for example, if there is a code table type 'gender'
	 * then will be put to 
	 */
	private void buildCodeTables(){
		String[] codeTypes = applicationScopeResourceLoaderService.loadAllCodeTypes();
		for(String codeType: codeTypes){
			this.buildOneCodeTableByCodeType(codeType);
		}
	}
	/**
	 * extract given codes to a sub array of codes by code type
	 * @param codes
	 * @return
	 */
	private void buildOneCodeTableByCodeType(String codeType){
		Map<Object,Object> codeMap = new HashMap<Object,Object>() {
            private static final long serialVersionUID = -1759893921358235848L;
            
            /**
             * get from the service, not raw data for avoiding non-refresh issue
             */
            public Object get(Object key) {
            	if (key==null) return null;
                return applicationScopeResourceLoaderService.getCodesByType(String.valueOf(key));
            }

            public boolean containsKey(Object key) {
                return true;
            }
        };
		
		logger.debug("put code table of [{}] to application scope.", codeType);
		this.getServletContext().setAttribute(CODE_TABLE_CONTEXT_NAME, codeMap);
	}

	public Map<String, String> getConstantClass() {
		return constantClass;
	}

	public void setConstantClass(Map<String, String> constantClass) {
		this.constantClass = constantClass;
	}
}

package com.nec.asia.nic.comp.admin.code.utils;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.nec.asia.nic.comp.admin.code.domain.VRicCode;
import com.nec.asia.nic.comp.admin.code.domain.VRicCodeValue;
import com.nec.asia.nic.comp.admin.code.domain.VRicParameter;

@Component("codeRetrievalHelper")
public class CodeRetrievalHelper {

	//private SessionFactory sessionFactory;
	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		//this.sessionFactory = sessionFactory;
		this.hibernateTemplate =  new HibernateTemplate(sessionFactory);
	}
	
	public String getCodeXml() {
		String codeXml = null;
		List<VRicCode> resultList = (List<VRicCode>) this.hibernateTemplate.find("from VRicCode");
		if (CollectionUtils.isNotEmpty(resultList)) {
			codeXml = resultList.get(0).getCodeXml();
		}
		return codeXml;
	}
	
	public String getCodeValueXml() {
		String codeValueXml = null;
		List<VRicCodeValue> resultList = (List<VRicCodeValue>) this.hibernateTemplate.find("from VRicCodeValue");
		if (CollectionUtils.isNotEmpty(resultList)) {
			codeValueXml = resultList.get(0).getCodeValueXml();
		}
		return codeValueXml;
	}
	
	public String getParameterXml() {
		String paraXml = null;
		List<VRicParameter> resultList = (List<VRicParameter>) this.hibernateTemplate.find("from VRicParameter");
		if (CollectionUtils.isNotEmpty(resultList)) {
			paraXml = resultList.get(0).getParaXml();
		}
		return paraXml;
	}
	
	//to do in later phase
	/*private String parseOracleXmlTypeToString(CallableStatement stmt, String columName) throws SQLException {
		XMLType xmlType = null;
        String xmlString = null;
        try {
            OPAQUE op = (oracle.sql.OPAQUE) stmt.getObject(columName);
            if (op != null) {
                xmlType = XMLType.createXML(op);
            }
            xmlString = xmlType.getStringVal();
        } finally {
            if (null != xmlType) {
                xmlType.close();
            }
        }
        return xmlString;
	}*/
	
	//to do in later phase
	/*public String getCodeDeltaXml() {
		String codeXml = null;
		codeXml = (String) this.hibernateTemplate.execute(
				new HibernateCallback<String>() {
					public String doInHibernate(Session session)
							throws HibernateException, SQLException {
						String xml = null;
						String dateString = "20130530000000";
						String queryString = //"select ROWID, code_xml from epp_central_sys.v_ric_code";
							"CALL epp_central_sys.get_config()";
							//"CALL epp_central_sys.get_delta_config(:configName, :lastUpdateDate)";
						
					    org.hibernate.engine.SessionFactoryImplementor sessionFactoryImplementation = (org.hibernate.engine.SessionFactoryImplementor) session.getSessionFactory();
					    org.hibernate.connection.ConnectionProvider connectionProvider = sessionFactoryImplementation.getConnectionProvider();
					    java.sql.Connection connection = connectionProvider.getConnection(); //((SessionImpl)session).connection()
						
						CallableStatement stmt = connection.prepareCall(queryString);
						stmt.execute();
						xml = parseOracleXmlTypeToString(stmt, "delta_rows_data");
						//stmt.get
//						Query query = session.createSQLQuery(queryString)
//						.addEntity(VRicCode.class)
//						//.setParameter("configName", "CODE")
//						//.setParameter("lastUpdateDate", dateString) //new Date())
//						;
//						
//						List<VRicCode> resultList = query.list();
//						if (CollectionUtils.isNotEmpty(resultList)) {
//							xml = resultList.get(0).getCodeXml();
//						}
						return xml;
					}  
		        });
		return codeXml;
	}*/
}

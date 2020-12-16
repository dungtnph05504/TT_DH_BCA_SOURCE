package com.nec.asia.nic.comp.dataAccess.type;

import java.io.Serializable;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

//import oracle.jdbc.OracleResultSet;
//import oracle.sql.OPAQUE;
//import oracle.xdb.XMLType;

public class StringOracleXmlType /*implements UserType, Serializable*/ {
	/*
	private static final long serialVersionUID = 2308230823023l;
    private static final int[] SQL_TYPES = new int[] { oracle.xdb.XMLType._SQL_TYPECODE };
    
    public int[] sqlTypes() {
        return SQL_TYPES;
    }
    
    public Class returnedClass() {
        return String.class;
    }
 
    public int hashCode(Object x) {
        return x.hashCode();
    }
 
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return (String) cached;
    }
 
    public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
    }
 
    public Object replace(Object _orig, Object _tar, Object _owner) {
        return deepCopy(_orig);
    }
 
    public boolean equals(Object arg0, Object arg1) throws HibernateException {
        if (arg0 == null && arg1 == null)
            return true;
        else if (arg0 == null && arg1 != null)
            return false;
        else
            return arg0.equals(arg1);
    }
 
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null)
            return null;
 
        return value; //(Document) ((Document) value).cloneNode(true);
    }
 
    public boolean isMutable() {
        return false;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        XMLType xmlType = null;
        String xmlString = null;
        try {
            OPAQUE op = null;
            OracleResultSet ors = null;
            if (rs instanceof OracleResultSet) {
                ors = (OracleResultSet) rs;
                op = ors.getOPAQUE(names[0]);
            } else {
                //throw new UnsupportedOperationException("ResultSet needs to be of type OracleResultSet");
                op = (oracle.sql.OPAQUE) rs.getObject(names[0]);
            }
            //op = ors.getOPAQUE(names[0]);
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
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        try {
//          Connection conn = st.getConnection();
//          try {
//              if (conn instanceof org.apache.commons.dbcp.DelegatingConnection) {
//                  System.out.println("detected apache commons dbcp datasource");
//                  conn = ((org.apache.commons.dbcp.DelegatingConnection) conn).getInnermostDelegate();
//              }
//              if (st.getConnection().isWrapperFor(OracleConnection.class)) {
//                  conn = st.getConnection().unwrap(OracleConnection.class);
//              }
//          } catch (Exception ex) {
//              ex.printStackTrace();
//              throw new SQLException("Could not unwrap for OracleConnection", ex);
//          }
//          
//            XMLType xmlType = null;
//            if (value != null) {
//              String xmlString = (String) value;
//                xmlType = XMLType.createXML(conn, xmlString);
//            } else {
//                xmlType = XMLType.createXML(conn, "");
//            }
// 
//            ((OraclePreparedStatement) st).setObject(index, xmlType);
            
            if (value != null) {
                String string = (String) value;
                StringReader reader = new StringReader( string );
                st.setCharacterStream( index, reader, string.length() );
            } else {
                st.setNull( index, sqlTypes()[0] );
            }

        } catch (Exception e) {
            throw new SQLException("Could not convert String for storage", e);
        }
    }
    */
 
    /*protected static String domToString(Document _document)
            throws TransformerException {
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(_document);
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        transformer.transform(source, result);
        return sw.toString();
    }
 
    protected static Document stringToDom(String xmlSource)
            throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                xmlSource.getBytes("UTF-8"));
        Document document = builder.parse(inputStream);
        return document;
    }*/
}
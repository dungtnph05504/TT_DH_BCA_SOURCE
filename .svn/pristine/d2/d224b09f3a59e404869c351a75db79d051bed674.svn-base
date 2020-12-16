<%@ page language="java" import="java.util.*,java.net.*,javax.naming.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String hostname = "";

try {
	hostname = InetAddress.getLocalHost().getHostName().toUpperCase();
} catch (UnknownHostException e) {
	hostname="";
}

%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>EPP Web Services</title>
  </head>
<body>
<h2>EPP Web Services</h2>
<a href='<%=basePath+"services/" %>'>view details</a>
<br/><br/><br/><br/>

<% out.println("System ["+hostname+"] Time:"+System.currentTimeMillis()+" - "+new Date(System.currentTimeMillis())); %><br/>
<%
try {
	javax.naming.InitialContext ic = new javax.naming.InitialContext();
    String message = (String)ic.lookup("java:comp/env/msg");
    System.out.println( message +" @java:comp/env/msg ");
} catch (Exception e) {
	//e.printStackTrace();
	System.out.println("Error @ java:comp/env/msg : " + e.getMessage());
}
try{
	//System.setProperty("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
	//System.setProperty("java.naming.factory.url.pkgs","org.jboss.naming:org.jnp.interfaces");
	//System.setProperty("java.naming.provider.url","jnp://localhost:1099");
	javax.naming.Context ctx = new javax.naming.InitialContext();
    if(ctx == null ) 
        throw new Exception("Boom - No Context");

    javax.sql.DataSource ds = 
          (javax.sql.DataSource) ctx.lookup("jdbc/idserverDatasource");

    if (ds != null) {
    	java.sql.Connection conn = ds.getConnection();
            
      if(conn != null)  {
          System.out.println("Got Connection "+conn.toString());
          java.sql.Statement stmt = conn.createStatement();
          java.sql.ResultSet rst = stmt.executeQuery("select SYSDATE from Dual");
          if(rst.next()) {
             String date = rst.getString(1);
             out.println("<p> Date return: <br/>"+date+"</p>");
          }
          conn.close();
      }
    }
  }catch(Exception e) {
    //e.printStackTrace();
    System.out.println("Error @ jdbc/idserverDatasource: " + e.getMessage());
  }
  String[] names = {
		  "/nicDatasource",
		  "java:/nicDatasource",
		  "java:comp/nicDatasource",
		  "java:comp/env/nicDatasource",
		  "/eppDatasource",
		  "java:/eppDatasource",
		  "java:comp/eppDatasource",
		  "java:comp/env/eppDatasource",}; 
  for (int i=0; i<names.length; i++) {
	  String jndiName = names[i];//"java:comp/jdbc/DSTest";
	  try { 
		  if (jndiName!=null && jndiName.length()>0) {
		    javax.naming.Context ctx = new javax.naming.InitialContext();
		    javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup(jndiName);
		    System.out.println("****** '"+jndiName+"' : "+ds);
		  
		    if (ds != null) {
		    	java.sql.Connection conn = ds.getConnection();		            
		      if(conn != null)  {
		          System.out.println("Got Connection "+conn.toString());
		          java.sql.Statement stmt = conn.createStatement();
		          java.sql.ResultSet rst = stmt.executeQuery("select SYSDATE from Dual");
		          if(rst.next()) {
		             String date = rst.getString(1);
		             out.println("<p> Date return from '"+jndiName+"' : <br/>"+date+"</p>");
		          }
		          conn.close();
		      }
		    }
		  } else {
			  System.out.println();
		  }
	  } catch (Exception e) {
		  System.out.println("Error @ "+jndiName+": " + e.getLocalizedMessage()+" "+e.getClass()+" "+e.getCause());
	  }
  }

%>
</body>
</html>

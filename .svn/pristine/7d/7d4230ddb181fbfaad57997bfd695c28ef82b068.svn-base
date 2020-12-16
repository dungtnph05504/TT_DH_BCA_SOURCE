
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.framework.admin.code.service.ParametersService;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * To test the junit, please change the nic.properties to use datasource=jdbcDatasource 
 *
 */
public class TestBuild extends TestCase {
	public static ApplicationContext context = null;

	public TestBuild() {
		init();
	}

	public void init() {
		try {
			context = new ClassPathXmlApplicationContext(new String[] {"nic/spring-context.xml"}); //"nic/spring-local-datasource.xml", 
		} catch (Error e) {
			e.printStackTrace();
		}
	}

	public void testGetBean() {
		log("getBean - start");

		try {
			Object result = context.getBean(ParametersService.class);
			if (result!=null) {
				log("Build is Okay, 'ParametersService '  is up: "+result);
				log("Monitoring the quartz status...");
				Thread.sleep(60*1000);
				log("Closing the thread...");
			} else {
				log("Build may not okay, Please check in details.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		log("getBean - end");
	}

	public static void log(String message) {
		System.out.println(message);
	}

}

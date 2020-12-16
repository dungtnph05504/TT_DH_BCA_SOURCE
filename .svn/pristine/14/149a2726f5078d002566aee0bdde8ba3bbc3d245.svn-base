package com.nec.asia.nic.test.biometric;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.springframework.context.ApplicationContext;

import com.nec.asia.idserver.agent.payload.model.IdserverImageFace;
import com.nec.asia.idserver.agent.payload.model.IdserverImageFingerprint;
import com.nec.asia.idserver.agent.payload.model.IdserverPersonDetail;
import com.nec.asia.idserver.agent.payload.model.type.IdserverFaceFormat;
import com.nec.asia.idserver.agent.service.IdserverAgentService;
import com.nec.asia.nic.framework.springSupport.SpringBeanHandler;
import com.nec.asia.nic.framework.springSupport.SpringServiceManager;

public class TestBase extends TestCase {
	
	//protected static final String SPRING_SERVICE_XML = "com/nec/asia/nic/resources/spring/services/spring-services.xml";

	protected static final String SPRING_SERVICE_XML = "spring-context.xml";
	public static final String FINGERPRINT_DATA_ROOT = "/fingerprints/";
	public static final String FACIAL_DATA_ROOT = "/facial/";
	
	protected static final String SYSTEM_ID = "DS_NIC";
	
	protected ApplicationContext context = null;
	
    
    @Before
	public void setUp() throws Exception {
		SpringBeanHandler.setContext(SPRING_SERVICE_XML);
		context = SpringBeanHandler.getContext();
	}
    
    public void xtestConnectivity () throws Exception {
        IdserverAgentService biometricService = (IdserverAgentService )SpringServiceManager.getBean("idserverAgentService");
        biometricService.testConnectionAction();	    	
    }
    
    protected IdserverImageFingerprint buildFingerprint(
			String setNo,
			String fingerPosition,
			boolean isDefault) throws FileNotFoundException,
			IOException {
		//preparation
		//wsq, the original image
		byte[] wsq = this.loadFingerprintImage(setNo + "/" + fingerPosition);
		if (wsq==null) return null; //return immediately if can not load image
		IdserverImageFingerprint imgFingerprint = new IdserverImageFingerprint(); 
		imgFingerprint.setFingerPosition(new Short(fingerPosition));
		imgFingerprint.setImageData(wsq);
		imgFingerprint.setDefaultFinger(isDefault);

		return imgFingerprint;
	}
    
    protected IdserverImageFace buildFace(String who, IdserverFaceFormat imageFormat)
			throws FileNotFoundException, IOException {
		byte[] facial = this.loadFacialImage(who);
		
		IdserverImageFace face = new IdserverImageFace();
		face.setImageData(facial);
		face.setImageFormat(imageFormat);
		return face;
    }	

	
	/**
	    First Name
		Last Name
		Gender
		Nationality
	 */
	protected IdserverPersonDetail buildPersonDetail(
			String firstName, 
			String lastName,
			String gender,
			String nationality) {
		IdserverPersonDetail personDetail = new IdserverPersonDetail();
		personDetail.setFirstName(firstName);
		personDetail.setLastName(lastName);
		//personDetail.setFullName(firstName + " " + lastName);
		//personDetail.setCountybirth("MUS");
		//personDetail.setBirthdate("12302010");
		personDetail.setGender(gender);
		personDetail.setNationality(nationality);
		
		return personDetail;
	}

    
    protected byte[] loadFingerprintImage(String index){
		try {
			String fileName = this.parseRootFolder() + FINGERPRINT_DATA_ROOT + index + ".wsq";
			byte[] data = FileUtils.readFileToByteArray(new File(fileName));
			return data;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
    
    protected byte[] loadFingerprintTemplate(String index){
		try {
			String fileName = this.parseRootFolder() + FINGERPRINT_DATA_ROOT + index + ".xml";
			byte[] data = FileUtils.readFileToByteArray(new File(fileName));
			return data;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
    
    protected byte[] loadFacialImage(String name){
		try {
			String fileName = this.parseRootFolder() + FACIAL_DATA_ROOT + name + ".jpg";
			byte[] data = FileUtils.readFileToByteArray(new File(fileName));
			return data;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
    
    
    private String parseRootFolder(){
		String root = this.getClass().getResource("/").getPath();
		return root.substring(0, root.length()-1);
	}
}

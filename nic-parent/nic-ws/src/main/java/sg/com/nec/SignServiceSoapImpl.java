package sg.com.nec;

//import org.apache.commons.codec.binary.Base64;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import sg.com.nec.RequestSignedResult;
//import sg.com.nec.SignServiceSoap;
//
//import com.nec.asia.nic.comp.trans.service.DocumentSigningService;
//import com.nec.asia.nic.comp.trans.service.exception.DocumentSigningServiceException;

//@javax.jws.WebService(name = "SignServiceSoap", serviceName = "SignService", 
//        targetNamespace = "http://www.nec.com.sg", 
//        //wsdlLocation = "classpath:wsdl/ca/in.signservice.wsdl")
//        wsdlLocation = "file:/home/nic/wsdl/ca/in.signservice.wsdl")
public class SignServiceSoapImpl {
//public class SignServiceSoapImpl implements SignServiceSoap {
//	private static Logger logger = LoggerFactory.getLogger(SignServiceSoapImpl.class);
//	
//	@Autowired
//	private DocumentSigningService documentSigningService = null;
//	
//	@Override
//	public RequestSignedResult sign(String transactionId, byte[] hash) {
//		RequestSignedResult requestSignedResult = null;
//		logger.info("SignServiceSoapImpl.sign(): begin");
//		try {
//			logger.debug("[sign]: transactionId:{}, hash:{}", new Object[]{ transactionId, hash});
//			String hashBase64String = Base64.encodeBase64String(hash);
//			requestSignedResult = documentSigningService.signDocument(transactionId, hashBase64String);
//		} catch (DocumentSigningServiceException e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//		logger.info("SignServiceSoapImpl.sign(): end");
//		return requestSignedResult;
//	}

}

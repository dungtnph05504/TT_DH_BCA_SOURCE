import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.dx.idserver.ImageFingerprintDto;
import com.nec.asia.nic.dx.idserver.ImageType;
import com.nec.asia.nic.dx.idserver.MatchResultDto;
import com.nec.asia.nic.dx.trans.MainTransaction;
import com.nec.asia.nic.dx.trans.TransactionRetrievalDataType;
import com.nec.asia.nic.dx.trans.TransactionRetrievalFilter;
import com.nec.asia.nic.dx.trans.TransactionRetrievalRecordType;
import com.nec.asia.nic.dx.trans.TransactionRetrievalResult;
import com.nec.asia.nic.dx.ws.FaultException;
import com.nec.asia.nic.dx.ws.IdserverWS;
import com.nec.asia.nic.dx.ws.TransactionWS;

public final class Client {

    static ClassPathXmlApplicationContext context;

    static {
    	context = new ClassPathXmlApplicationContext(new String[] {"client-beans.xml"});
    	String userName = System.getenv("USERNAME");
    	String computerName = System.getenv("COMPUTERNAME");
    	String userDomain = System.getenv("USERDOMAIN");
    	String userDnsDomain = System.getenv("USERDNSDOMAIN");
    	
    	System.out.println("userName:"+userName);
    	System.out.println("computerName:"+computerName);
    	System.out.println("userDomain:"+userDomain);
    	System.out.println("userDnsDomain:"+userDnsDomain);
    	
    	//assume approxDob is in the format of DG's Date Sharing format: DD-MM-YYYY
//    	String approxDob = "01-12-1900";
//		String dayPart = StringUtils.substring(approxDob, 0, 2);
//		String monthPart = StringUtils.substring(approxDob, 3, 5);
//		String yearPart = StringUtils.substring(approxDob, 6, 10);
//		String printDob = dayPart+monthPart+yearPart;
//		System.out.println("dayPart:"+dayPart);
//		System.out.println("monthPart:"+monthPart);
//		System.out.println("yearPart:"+yearPart);
//		System.out.println("printDob:"+printDob);
//		
//		
//		prepareCcn();
		String word1 = "20140101";
		String word2 = "20140102";
		System.out.println(word1+" compareTo "+word2+" = "+(word1.compareTo(word2)));
		word1 = "20140101";
		word2 = "20131231";
		System.out.println(word1+" compareTo "+word2+" = "+(word1.compareTo(word2)));
		word1 = "20131231";
		word2 = "20131231";
		System.out.println(word1+" compareTo "+word2+" = "+(word1.compareTo(word2)));
    }
    
    
    private Client() {
    }

    public static void main(String args[]) throws Exception {
    	
    	callTransWS();
//    	callAdminWS();
//    	callPersoWS();
//    	callDxWS();
//    	callPackWS();
//    	callAfisWS();
    	
//    	WSUtil util = context.getBean(WSUtil.class);
//    	util.testWS();
//    	String xml = "";
//    	xml = FileUtils.readFileToString(new File("C:/PROD-DEDUP/TransactionRetrievalResponse-44.xml"));
//    	parseTransactionRetrievalResponse(xml);
//    	xml = FileUtils.readFileToString(new File("C:/PROD-DEDUP/TransactionRetrievalResponse-40.xml"));
//    	parseTransactionRetrievalResponse(xml);
    }
    
    
    
    
    private static TransactionRetrievalResult parseTransactionRetrievalResponse(String xml) {
    	TransactionRetrievalResult transDTO = null;
    	try { 			
			JAXBContext jc = JAXBContext.newInstance(com.nec.asia.nic.dx.trans.ObjectFactory.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			transDTO = ((JAXBElement<TransactionRetrievalResult>) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()))).getValue();
			
//			System.out.println(ReflectionToStringBuilder.toString(transDTO, ToStringStyle.MULTI_LINE_STYLE));
//			MainTransaction dto = transDTO.getTransactions().get(0);
//			List<Fingerprint> fingerprintList = dto.getRegistrationData().getFingerprintInfo().getFingerprints();
//			String transactionId = dto.getTransactionID();
//			for (Fingerprint fp : fingerprintList) {
//				String fpPosition = fp.getFingerprintPosition();
//				byte[] fpData = fp.getFingerprintData();
//				File file = new File("C:/PROD-DEDUP/"+transactionId+"-FP"+fpPosition+".wsq");
//				FileUtils.writeByteArrayToFile(file, fpData);
//			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return transDTO;
    }
    
//    private static MainTransaction parseTransactionXML(String xml) {
//    	//String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><DataExchange xmlns=\"http://dx.nec.com/\" source=\"com.nec.asia.perso.dataexchange.nicperso\" target=\"com.nec.asia.nic.dataexchange.nicperso\" version=\"1.0\"><NicPackageStatusUpdate DispatchID=\"X10000000A\" Status=\"DispatchedToRIC\"><PackageID>Pkg1</PackageID><PackageID>Pkg2</PackageID><PackageID>Pkg3</PackageID></NicPackageStatusUpdate></DataExchange>";
//    	MainTransaction transDTO = null;
//    	try { 			
//			JAXBContext jc = JAXBContext.newInstance(com.nec.asia.nic.dx.trans.ObjectFactory.class);
//			Unmarshaller unmarshaller = jc.createUnmarshaller();
//			transDTO = ((JAXBElement<MainTransaction>) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()))).getValue();
//			
//			System.out.println(ReflectionToStringBuilder.toString(transDTO, ToStringStyle.MULTI_LINE_STYLE));
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//			e.printStackTrace();
//		}
//		return transDTO;
//    }
    public static void callTransWS() throws IOException, FaultException {
    	TransactionWS client = (TransactionWS) context.getBean("transactionWS");
    	
    	String transactionId = "CCPL-2013-000102";
    	String xml = "";
//    	xml = FileUtils.readFileToString(new File("test/main/resources/upload.xml"));
    	//"<MainTransaction xmlns = \"http://trans.dx.nic.asia.nec.com/\" xmlns:ns2 = \"http://common.dx.nic.asia.nec.com/\" xmlns:ns3 = \"http://idserver.dx.nic.asia.nec.com/\"          xmlns:ns4 = \"http://ws.dx.nic.asia.nec.com/\">            <TransactionID>RICHQ-2013-B001070</TransactionID>            <QueueNumber></QueueNumber>            <ApplnRefNo>RICHQ-2013-001070</ApplnRefNo>            <Nin>X161111111111X</Nin>            <DateOfApplication>2013-06-17T16:33:56.647+08:00</DateOfApplication>            <RegSiteCode>HQRIC</RegSiteCode>            <IssSiteCode>RIC03</IssSiteCode>            <TransactionType>REG</TransactionType>            <TransactionSubtype>REG_CITIZEN</TransactionSubtype>            <TransactionStatus>RIC_UPLOAD_FAIL_2</TransactionStatus>            <CreateDate>2013-06-17T16:33:56.647+08:00</CreateDate>            <RegistrationData>                <TransactionID>RICHQ-2013-R001070</TransactionID>                <RegistrationCompleteFlag>Y</RegistrationCompleteFlag>                <RegistrationOfficerID></RegistrationOfficerID>                <RegistrationCompleteTime>2013-06-17T16:34:08.730+08:00</RegistrationCompleteTime>                <PaymentCompleteFlag>N</PaymentCompleteFlag>                <TransactionCompletedFlag>N</TransactionCompletedFlag>                <TransactionUploadedCompletedFlag>N</TransactionUploadedCompletedFlag>                <FullAmputatedFlag>N</FullAmputatedFlag>                <PartialAmputatedFlag>N</PartialAmputatedFlag>                <CreateBy></CreateBy>                <CreateDate>2013-06-17T16:34:04.807+08:00</CreateDate>                <UpdateBy>Officer B</UpdateBy>                <UpdateDate>2013-06-17T16:34:43.583+08:00</UpdateDate>                <PersonDetail>                    <SurnameFull>Sharma</SurnameFull>                    <SurnameLenExceedFlag>N</SurnameLenExceedFlag>                    <FirstnameFull>Aparna</FirstnameFull>                    <FirstnameLine1></FirstnameLine1>                    <FirstnameLine2></FirstnameLine2>                    <FirstnameLenExceedFlag>N</FirstnameLenExceedFlag>                    <SurnameAtBirthFull>Singh</SurnameAtBirthFull>                    <SurnameAtBirthLenExceedFlag>N</SurnameAtBirthLenExceedFlag>                    <DateOfBirth>1984-01-13T00:00:00+08:00</DateOfBirth>                    <PrintDOB></PrintDOB>                    <Gender>F</Gender>                    <MaritalStatus>M</MaritalStatus>                </PersonDetail>                <Address>                    <AddressLine1>27 ,Periwinkle</AddressLine1>                    <AddressLine2>5,East Street</AddressLine2>                    <AddressLine3>Central Civil Status Office</AddressLine3>                    <AddressLine4>The City Council of Port Louis</AddressLine4>                    <AddressUpdateFlag>N</AddressUpdateFlag>                    <UpdateBy></UpdateBy>                    <UpdateWkstnID></UpdateWkstnID>                </Address>                <Relationships>                    <Surname>Singh</Surname>                    <Firstname>Anurag</Firstname>                    <Nin></Nin>                    <RelationshipType>FATHER</RelationshipType>                </Relationships>                <Relationships>                    <Surname>Singh</Surname>                    <Firstname>Anurag</Firstname>                    <Nin></Nin>                    <RelationshipType>MOTHER</RelationshipType>                </Relationships>                <FacialInfo>                    <FacialIndicator>N</FacialIndicator>                    <FacialImages>                        <FacialData></FacialData>                        <DocType>PH_CAP</DocType>                    </FacialImages>                    <FacialImages>                        <FacialData></FacialData>                        <DocType>PH_CHIP</DocType>                    </FacialImages>                    <FacialImages>                        <FacialData></FacialData>                        <DocType>PH_GREY</DocType>                    </FacialImages>                    <FacialImages>                        <FacialData></FacialData>                        <DocType>TH_CAP</DocType>                    </FacialImages>                    <QtfResult xmlns:xsi = \"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil = \"true\"/>                    <CreateBy>OfficerA</CreateBy>                    <CreateDate>2013-06-17T16:34:04.810+08:00</CreateDate>                </FacialInfo>                <FingerprintInfo>                    <CmlafTemplate xmlns:xsi = \"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil = \"true\"/>                </FingerprintInfo>                <SignatureInfo>                    <SignatureIndicator>Y</SignatureIndicator>                    <SignatureData></SignatureData>                    <SignatureFP>N</SignatureFP>                    <CreateBy>Officer B</CreateBy>                    <CreateDate>2013-06-17T16:34:43.583+08:00</CreateDate>                </SignatureInfo>            </RegistrationData>        </MainTransaction>"
//    	MainTransaction trans = parseTransactionXML(xml);
//		MainTransaction trans = new MainTransaction();
//		trans.setTransactionID(transactionId);
//		trans.setApplnRefNo(transactionId);
//		trans.setApplnRefNo("RICHQ-2012-000001");
//		trans.setDateOfApplication(new Date());
//		trans.setRegSiteCode("CCPL");
//		trans.setIssSiteCode("CCPL");
//		trans.setNin("K090999123456C");
//    	if (trans!=null) {
//			String response;
//			try {
//				response = client.uploadTransaction(trans);
//				System.out.println("[uploadTransaction] Response: " + response);
//			} catch (FaultException e) {
//				e.printStackTrace();
//			}
//			
//    	}
    	
        TransactionRetrievalFilter filter = new TransactionRetrievalFilter();
        filter.setTransactionID("RICHQ-2013-001530");//("RICHQ-2013-001122");
//        filter.setNin("A000000000003P");//"x012222222222X");
        filter.getDataType().add(TransactionRetrievalDataType.ALL);
        filter.setRecordType(TransactionRetrievalRecordType.ALL);
        TransactionRetrievalResult result = client.retrieveTransaction(filter);
        List<MainTransaction> transDTOList = result.getTransactions();
        System.out.println("[retrieveTransaction] Response: " + transDTOList+", "+transDTOList.size());
        for (MainTransaction transDTO : transDTOList) {
        	System.out.println("[retrieveTransaction] transDTO: " + ReflectionToStringBuilder.toString(transDTO, ToStringStyle.MULTI_LINE_STYLE));
        	System.out.println("[retrieveTransaction] transDTO.registrationData: " + ReflectionToStringBuilder.toString(transDTO.getRegistrationData(), ToStringStyle.MULTI_LINE_STYLE));
        	if (transDTO.getRegistrationData()!=null) {
	        	System.out.println("[retrieveTransaction] transDTO.registrationData.fingerprintInfo: " + ReflectionToStringBuilder.toString(transDTO.getRegistrationData().getFingerprintInfo(), ToStringStyle.MULTI_LINE_STYLE));
        	}
        	System.out.println("[retrieveTransaction] transDTO.logs: " + ReflectionToStringBuilder.toString(transDTO.getTransactionLogs(), ToStringStyle.MULTI_LINE_STYLE));
        }
    	
//    	
//        CitizenRefRetrievalFilter input = new CitizenRefRetrievalFilter();
//        input.setNin("G121290778910C");
//        input.setSurname("Goh");
//        CitizenRefRetrievalResult citizenResult = client.retrieveCitizenRef(input);
//        List<CitizenRef> citizenRefList = citizenResult.getCitizenRefs();
//        System.out.println("[retrieveCitizenRef] Response: " + citizenRefList);
//        for (CitizenRef citizen: citizenRefList) {
//        	System.out.println("[retrieveCitizenRef] citizen: " + ReflectionToStringBuilder.toString(citizen, ToStringStyle.MULTI_LINE_STYLE));
//        }
    }
    
    
    private static String computeCheckSum(String inputXML) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	String resultCS = null;
    	if (StringUtils.isNotBlank(inputXML)) {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.reset();
			byte origData[] = inputXML.getBytes("UTF-8");			
			md.update(origData);
			byte byteData[] = md.digest();
			StringBuffer sbo = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sbo.append(String.format("%02X", byteData[i] & 0xFF));
			}
			System.out.println("Ori format : " + sbo.toString());
			resultCS = sbo.toString();
//			// convert the byte to hex format method 1
//			StringBuffer sb = new StringBuffer();
//			for (int i = 0; i < byteData.length; i++) {
//				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
//			}
//
//			System.out.println("Hex format : " + sb.toString());
//			resultCS = sb.toString();
		}
    	return resultCS;
    }
        
    public static void callAfisWS() {
    	IdserverWS client = (IdserverWS) context.getBean("afisWS");
		try {
	    	List<ImageFingerprintDto> fingerprints = new ArrayList<ImageFingerprintDto>();
	    	ImageFingerprintDto fingerprintDto = new ImageFingerprintDto();
	    	fingerprintDto.setFingerPosition((short) 1);
	    	fingerprintDto.setImageType(ImageType.WSQ);
	    	fingerprintDto.setImageData(FileUtils.readFileToByteArray(new File("C:/worktool/workspace/NIC/nic-parent/nic-comp/test/main/resources/fingerprints/set1/1.wsq")));
			fingerprints.add(fingerprintDto);
			MatchResultDto result = client.identification(fingerprints);
			System.out.println("[identification] result: " + ReflectionToStringBuilder.toString(result, ToStringStyle.MULTI_LINE_STYLE));
		} catch (FaultException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private static String prepareCcn() {
		String ccn = "";
		final int MAX = 100000000; 
		int number = (int) ((Math.random() * MAX) % MAX);
		ccn = String.format("%08d", number);
		ccn += computeCCNCheckSum(ccn);
		System.out.println(ccn +" valid: "+validateCCN(ccn));
		return ccn;
	}
	private static String computeCCNCheckSum(String ccn8Digit) {
		String checkSum = "0";
		int digit = 10 - doLuhn(ccn8Digit, true) % 10;
		checkSum = "" + digit;
		return checkSum;
	}

	private static boolean validateCCN(String s) {
		return doLuhn(s, false) % 10 == 0;
	}

	private static int doLuhn(String s, boolean evenPosition) {
		int sum = 0;
		for (int i = s.length() - 1; i >= 0; i--) {
			int n = Integer.parseInt(s.substring(i, i + 1));
			if (evenPosition) {
				n *= 2;
				if (n > 9) {
					n = (n % 10) + 1;
				}
			}
			sum += n;
			evenPosition = !evenPosition;
		}
		return sum;
	}
}

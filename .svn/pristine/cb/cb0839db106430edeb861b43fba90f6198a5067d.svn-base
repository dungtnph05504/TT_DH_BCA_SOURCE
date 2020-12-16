package com.nec.asia.nic.comp.trans.service.impl;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.idserver.agent.payload.model.result.IdserverMatchResult;
import com.nec.asia.idserver.agent.payload.model.result.IdserverScore;
import com.nec.asia.idserver.agent.payload.model.result.IdserverSubject;
import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.service.InquirySearchResultService;

import junit.framework.TestCase;

public class InquirySearchResultServiceTestCase extends TestCase {
	protected static final Logger logger = LoggerFactory.getLogger(InquirySearchResultServiceTestCase.class);
	
	public static ApplicationContext context = null;
	public static InquirySearchResultService service = null;
	
	public String transactionId = "TRANSACTIONID_03";
	public String nicId = "NIC_ID_03";
	
	public InquirySearchResultServiceTestCase() {
		init();
	}

	public void init() {
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			service = context.getBean(InquirySearchResultServiceImpl.class);
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	
	public void xtestfindSearchResult() {
		log("start findSearchResultById - find()");
		try {
		
			IdserverMatchResult matchResult = service.findSearchResultById(10);
			for(IdserverSubject subject : matchResult.getSubjectList()){
				logger.info("RegistrationId = {}; PersonIdentifier = {}; DataSource = {}, HitDecision = {}, Scores = {}",
						new Object[]{
							subject.getRegistrationId(),
							subject.getPersonIdentifier(),
							subject.getDataSource(),
							subject.getHitDecision(),
							ReflectionToStringBuilder.toString(subject.getScoreList()) 
							}
				);
				for (IdserverScore score : subject.getScoreList()) {
					System.out.println("FP:"+ score.getFingerPosition() +"="+ score.getScore());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end findSearchResultById - find()");
	}
	
	private IdserverMatchResult prepareCPDHitList() {
		IdserverMatchResult cpdMatchResult = new IdserverMatchResult();
		cpdMatchResult.setHasHit(true);
		IdserverSubject hitSubject1 = new IdserverSubject();
		hitSubject1.setRegistrationId("004-2016-000003");
		hitSubject1.setPersonIdentifier("004-2016-000003");
		hitSubject1.setHitDecision(true);
		IdserverScore score1 = new IdserverScore();
		score1.setFingerPosition((short) 99);
		score1.setScore(9999);
		hitSubject1.getScoreList().add(score1);
		cpdMatchResult.getSubjectList().add(hitSubject1);
		return cpdMatchResult;
	}
	
	//[2]
	public void xtestSaveSearchResult() {
		log("start saveSearchResult - find()");
		try {
			String typeOfSearch = "CPD"; 
			IdserverMatchResult matchResult = this.prepareCPDHitList(); 
			Long workflowJobId = 11L; 
			String transactionId = "004-2016-000009";
			String afisKeyNo = "004-2016-000009";
			long searchResultId = service.saveSearchResult(typeOfSearch, matchResult, workflowJobId, transactionId, afisKeyNo);
			log("searchResultId: "+searchResultId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end saveSearchResult - find()");
	}
	
	public void testSaveOrUpdate() {
		String typeOfSearch = "CPD"; 
		Long workflowJobId = 11L; 
		String transactionId = "004-2016-000009";
		String afisKeyNo = "004-2016-000009";
		
		String transactionIdHit = "004-2016-000003";
		String afisKeyNoHit = "004-2016-000009";
		
		NicSearchResult searchResult = new NicSearchResult();
		searchResult.setWorkflowJobId(workflowJobId);
		searchResult.setAfisKeyNo(afisKeyNo);
		searchResult.setTransactionId(transactionId);
		searchResult.setCreateDatetime(new Date());
		searchResult.setTypeSearch(typeOfSearch);
		searchResult.setHasHit(true);
		
		NicSearchHitResult hitCandidate1 = new NicSearchHitResult();
		hitCandidate1.setTransactionIdHit(transactionIdHit);
		hitCandidate1.setAfisKeyNoHit(afisKeyNoHit);
		hitCandidate1.setHitDecision(true);
		hitCandidate1.setHitInfo("Firstname, Surname");
		hitCandidate1.setDataSource("CPD");
		hitCandidate1.setCreateDatetime(new Date());
//		hitCandidate1.setSearchResult(searchResult);
		hitCandidate1.setSearchResultId(searchResult.getSearchResultId());
		searchResult.getHitList().add(hitCandidate1);
		
		try {
			long searchResultId = service.save(searchResult);
			log("searchResultId: "+searchResultId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void log(String message) {
		System.out.println("<<< "+message+" >>>");
	}
}

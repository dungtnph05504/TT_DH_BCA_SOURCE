package com.nec.asia.nic.comp.perso.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.perso.domain.PersoDispatchInfo;
import com.nec.asia.nic.comp.trans.dao.NicTransactionDao;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.utils.DateUtil;

/**
 * A wee test for Livescan submit file.
 * This test loads a 'generic' NIST, modifies the TOT and TCN, then submits to
 * the livescan input directory. Note that this directory is local. this code
 * would need to be run as a service on the servers jboss, or scp/ftp the files
 *
 * @author Charlie Williams
 * @version 0.1
 * @since 7 Nov 2014
 */

/*
 * Modification History:
 */

public class PersoInfoDaoTestCase {

    /** The _logger. */
    private static Logger _logger = Logger.getLogger(PersoInfoDaoTestCase.class);

    public final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    public static ApplicationContext context = null;

    private PersoInfoDao persoInfoDao;


    private NicTransactionDao transactionDao;
    @Before
    public void setUp() {
        try {
            context = new ClassPathXmlApplicationContext("spring-context.xml");
            persoInfoDao = context.getBean("persoInfoDao", PersoInfoDao.class);
            transactionDao = context.getBean("transactionDao", NicTransactionDao.class);
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        try {

        } catch (Exception ex) {
            _logger.info("EXCEPTION " + ex.getMessage());
        }
    }
    
    @Test
    public void testInsertPersoStatus() {
        _logger.debug("start testInsertPersoStatus");
        try {
            
            List<NicTransaction> transactionList = transactionDao.findAll();
            _logger.debug(transactionList.size());
//            PersoStatus persoStatus = new PersoStatus();
//            persoStatus.setApplnRefNo("ARN00001");
//            persoStatus.setChipSerialNo("00000000000000");
//            persoStatus.setCreateDatetime(new Date());
//            persoStatus.setDateOfExpiry(new Date());
//            persoStatus.setDateOfIssue(new Date());
//            persoStatus.setErrorCode("00001");
//            persoStatus.setErrorMessage("00001");
//            persoStatus.setPassportNo("00001");
//            persoStatus.setPrintingSite("00001");
//            persoStatus.setSourceLocation("00001");
//            persoStatus.setStatusDate(new Date());
//            persoStatus.setTransactionId("00001");
//            persoStatus.setTransactionStatus("00001");
//            
//            persoInfoDao.createPersoStatus(persoStatus);

        } catch (Exception e) {
            e.printStackTrace();
        }
        _logger.debug(" end  testInsertPersoStatus");
    }
    
    @Ignore
    @Test
    public void testInsertDispatchInfo() {
        _logger.debug("start testInsertDispatchInfo");
        try {
            PersoDispatchInfo dispatchInfo = new PersoDispatchInfo();

            dispatchInfo.setBatchNumber("00001");
            dispatchInfo.setApplnRefNo("ARN00001");
            dispatchInfo.setBatchSize(100);
            dispatchInfo.setCollectionDate(DateUtil.formatDate(new Date(), DateUtil.FORMAT_YYYYdashMMdashDD));
            dispatchInfo.setCollectionSite("270");
            dispatchInfo.setDocumentType("2222222");
            dispatchInfo.setPriority(1);
            dispatchInfo.setSourceLocation("00001.xml");
            
            persoInfoDao.createPersoDispatchInfo(dispatchInfo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        _logger.debug(" end  testInsertDispatchInfo");
    }

}

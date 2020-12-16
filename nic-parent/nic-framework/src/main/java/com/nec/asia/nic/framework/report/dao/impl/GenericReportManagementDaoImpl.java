/**
 * 
 */
package com.nec.asia.nic.framework.report.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.audit.domain.AuditAccessLogs;
import com.nec.asia.nic.framework.admin.audit.domain.AuditSessionLogs;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.framework.report.dao.GenericReportManagementDao;
import com.nec.asia.nic.framework.report.domain.NicReportMenuForm;
import com.nec.asia.nic.framework.report.domain.NicTransactionsStatistics;
import com.nec.asia.nic.framework.report.domain.VNicRejectTransaction;
import com.nec.asia.nic.framework.report.domain.VNicTransaction;
import com.nec.asia.nic.framework.report.domain.VNicTransactions;
import com.nec.asia.nic.framework.report.dto.AuditAccessLogDto;
import com.nec.asia.nic.framework.report.dto.AuditSessionLogDto;
import com.nec.asia.nic.framework.report.dto.NicTransactionsStatisticsDto;
import com.nec.asia.nic.framework.report.dto.VNicRejectTransactionDto;
import com.nec.asia.nic.framework.report.dto.VNicTransactionDto;
import com.nec.asia.nic.utils.DateUtil;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Prasad_Karnati
 */
@Repository("genericReportManagementDao")
public class GenericReportManagementDaoImpl extends GenericHibernateDao<Object, Integer> implements GenericReportManagementDao {
    private static final Logger logger = LoggerFactory.getLogger(GenericReportManagementDaoImpl.class);

    @SuppressWarnings("unchecked")
    public JRDataSource getDataForPdf(String namedQuery) throws Exception {
        Session session = null;
        try {
            if (namedQuery.contains("VNicTransaction")) {
                namedQuery = namedQuery + " ORDER BY dateOfApplication";
            } else if (namedQuery.contains("VNicRejectTransaction")) {
                namedQuery = namedQuery + " ORDER BY dateOfApplication";
            } else if (namedQuery.contains("AuditAccessLog")) {
                namedQuery = namedQuery + " ORDER BY auditDate";
            } else if (namedQuery.contains("AuditSessionLog")) {
                namedQuery = namedQuery + " ORDER BY loginDate";
            } else if (namedQuery.contains("NicTransactionsStatistics")) {
                namedQuery = namedQuery + " ORDER BY dateOfApplication";
            }

            session = getHibernateTemplate().getSessionFactory().openSession();
            List<Object> dtos = session.createQuery(namedQuery).list();

            return new JRBeanCollectionDataSource(dtos);
        } catch (Exception ex) {
            logger.error("Error occurred while generating the report. Exception:" + ex.getMessage());
            throw new Exception("Error occurred while generating the report. Exception:" + ex.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List<Object> getFlexiGridData(NicReportMenuForm form) throws Exception {
        Session session = null;
        try {
            logger.debug("Enter Into getFlexiGridData  ");
            session = getHibernateTemplate().getSessionFactory().openSession();
            List<Object> dtos = session.createQuery(form.getNamedQuery()).list();

            logger.debug(" Exit from getFlexiGridData  " + dtos.size());
            //System.out.println(" Exit getFlexiGridData  ===================> " + dtos.size());
            return dtos;
        } catch (Exception ex) {
            logger.error("Error occurred while generating the report. Exception:" + ex.getMessage());
            throw new Exception("Error occurred while generating the report. Exception:" + ex.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getColumnAndParameterList(String tableName) throws Exception {
        Session session = null;
        try {
            session = getHibernateTemplate().getSessionFactory().openSession();
            Map x = session.getSessionFactory().getAllClassMetadata();
            Map<String, String> columnMap = new HashMap<String, String>();
            for (Iterator i = x.values().iterator(); i.hasNext();) {

                SingleTableEntityPersister y = (SingleTableEntityPersister) i.next();
                // logger.debug("====================>"+y.getName() + "=========================> " + y.getTableName());	
                //System.out.println("====================>"+y.getName() + "=========================> " + y.getTableName());
                if (tableName != null && !tableName.equals("") && !tableName.equals(" ")) {
                    if (y.getTableName().contains(tableName)) {
                        /*
                         * System.out
                         * .println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm"
                         * + y.getPropertyNames().length);
                         */
                        for (int j = 0; j < y.getPropertyNames().length; j++) {

                            logger.debug("PropertyNames List " + y.getPropertyNames()[j] + " ==> " + (y.getPropertyColumnNames(j).length > 0 ? y.getPropertyColumnNames(j)[0] : " ..."));

                            columnMap.put(y.getPropertyNames()[j], (y.getPropertyColumnNames(j).length > 0 ? y.getPropertyColumnNames(j)[0] : " No Column"));
                            columnMap.put("objName", y.getName());
                            columnMap.put("tableName", y.getTableName());

                        }
                    }
                }

            }

            return columnMap;
        } catch (Exception ex) {
            logger.error("Error occurred while generating the report. Exception:" + ex.getMessage());
            throw new Exception("Error occurred while generating the report. Exception:" + ex.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @SuppressWarnings("unchecked")
    public PaginatedResult<Object> getReportData(String query, int pageNo, int pageSize, String tableName) throws Exception {
        Session session = null;
        try {
            PaginatedResult<Object> pagenatedReportData = null;

            session = getHibernateTemplate().getSessionFactory().openSession();

            List<Object> list = session.createQuery(query).list();
            int total = list.size();

            list = session.createQuery(query).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

            try {

                List<Object> listObjs = new ArrayList<Object>();
                if (list != null && !list.isEmpty() && list.size() > 0) {
                    if (tableName.equals("com.nec.asia.nic.framework.report.domain.VNicTransaction")) {
                        Iterator itr = list.iterator();
                        while (itr.hasNext()) {
                            VNicTransactionDto vNicTransactions = new VNicTransactionDto();
                            VNicTransaction record = (VNicTransaction) itr.next();
                            vNicTransactions.setApplnRefNo(record.getApplnRefNo());
                            vNicTransactions.setNin(record.getNin());
                            if (record.getDateOfApplication() != null) {
                                vNicTransactions.setApproveDate(DateUtil.parseDate(record.getDateOfApplication()));
                            }
                            vNicTransactions.setApprovedBy(record.getApprovedBy());
                            if (record.getDateOfApplication() != null) {
                                vNicTransactions.setDateOfApplication(DateUtil.parseDate(record.getDateOfApplication()));
                            }
                            if (record.getDateOfBirth() != null) {
                                vNicTransactions.setDateOfBirth(DateUtil.parseDate(record.getDateOfBirth()));
                            }
                            vNicTransactions.setFirstnameFull(record.getFirstnameFull());
                            vNicTransactions.setGender(record.getGender());
                            // vNicTransactions.setInvestigationOfficer(record.getInvestigationOfficer());
                            // vNicTransactions.setInvestigationStatus(record.getInvestigationStatus());
                            // vNicTransactions.setIssSiteCode(record.getIssSiteCode());
                            vNicTransactions.setServedBy(record.getServedBy());
                            // vNicTransactions.setSiteCode(record.getSiteCode());
                            vNicTransactions.setSurnameBirthFull(record.getSurnameBirthFull());
                            vNicTransactions.setSurnameFull(record.getSurnameFull());
                            if (record.getTransactionDate() != null) {
                                vNicTransactions.setTransactionDate(DateUtil.parseDate(record.getTransactionDate()));
                            }
                            vNicTransactions.setTransactionId(record.getTransactionId());
                            vNicTransactions.setTransactionStatus(record.getTransactionStatus());
                            // vNicTransactions.setTransactionSubtype(record.getTransactionSubtype());
                            vNicTransactions.setTransactionType(record.getTransactionType());
                            listObjs.add(vNicTransactions);
                            pagenatedReportData = new PaginatedResult<Object>(total, pageNo, listObjs);
                        }
                    } else if (tableName.equals("com.nec.asia.nic.framework.report.domain.VNicTransactions")) {
                        Iterator itr = list.iterator();
                        while (itr.hasNext()) {
                            VNicTransactionDto vNicTransactions = new VNicTransactionDto();
                            VNicTransactions record = (VNicTransactions) itr.next();
                            vNicTransactions.setApplnRefNo(record.getApplnRefNo());
                            vNicTransactions.setNin(record.getNin());
                            if (record.getDateOfApplication() != null) {
                                vNicTransactions.setApproveDate(DateUtil.parseDate(record.getDateOfApplication()));
                            }
                            vNicTransactions.setApprovedBy(record.getApprovedBy());
                            if (record.getDateOfApplication() != null) {
                                vNicTransactions.setDateOfApplication(DateUtil.parseDate(record.getDateOfApplication()));
                            }
                            if (record.getDateOfBirth() != null) {
                                vNicTransactions.setDateOfBirth(DateUtil.parseDate(record.getDateOfBirth()));
                            }
                            vNicTransactions.setFirstnameFull(record.getFirstnameFull());
                            vNicTransactions.setGender(record.getGender());
                            vNicTransactions.setInvestigationOfficer(record.getInvestigationOfficer());
                            vNicTransactions.setInvestigationStatus(record.getInvestigationStatus());
                            vNicTransactions.setIssSiteCode(record.getIssSiteCode());
                            vNicTransactions.setServedBy(record.getServedBy());
                            vNicTransactions.setSiteCode(record.getSiteCode());
                            vNicTransactions.setSurnameBirthFull(record.getSurnameBirthFull());
                            vNicTransactions.setSurnameFull(record.getSurnameFull());
                            if (record.getTransactionDate() != null) {
                                vNicTransactions.setTransactionDate(DateUtil.parseDate(record.getTransactionDate()));
                            }
                            vNicTransactions.setTransactionId(record.getTransactionId());
                            vNicTransactions.setTransactionStatus(record.getTransactionStatus());
                            vNicTransactions.setTransactionSubtype(record.getTransactionSubtype());
                            vNicTransactions.setTransactionType(record.getTransactionType());
                            listObjs.add(vNicTransactions);
                            pagenatedReportData = new PaginatedResult<Object>(total, pageNo, listObjs);
                        }
                    } else if (tableName.equals("com.nec.asia.nic.framework.report.domain.VNicRejectTransaction")) {

                        Iterator itr = list.iterator();
                        while (itr.hasNext()) {
                            VNicRejectTransactionDto vNicRejectTransaction = new VNicRejectTransactionDto();
                            VNicRejectTransaction record = (VNicRejectTransaction) itr.next();
                            vNicRejectTransaction.setApplnRefNo(record.getApplnRefNo());
                            vNicRejectTransaction.setNin(record.getNin());
                            vNicRejectTransaction.setApproveDate(DateUtil.parseDate(record.getDateOfApplication()));
                            vNicRejectTransaction.setApprovedBy(record.getApprovedBy());
                            vNicRejectTransaction.setDateOfApplication(DateUtil.parseDate(record.getDateOfApplication()));
                            vNicRejectTransaction.setDateOfBirth(DateUtil.parseDate(record.getDateOfBirth()));
                            vNicRejectTransaction.setFirstnameFull(record.getFirstnameFull());
                            vNicRejectTransaction.setGender(record.getGender());
                            vNicRejectTransaction.setSurnameBirthFull(record.getSurnameBirthFull());
                            vNicRejectTransaction.setSurnameFull(record.getSurnameFull());
                            vNicRejectTransaction.setTransactionDate(DateUtil.parseDate(record.getTransactionDate()));
                            vNicRejectTransaction.setTransactionId(record.getTransactionId());
                            vNicRejectTransaction.setTransactionSubtype(record.getTransactionSubtype());
                            vNicRejectTransaction.setTransactionType(record.getTransactionType());
                            vNicRejectTransaction.setRejectDate(DateUtil.parseDate(record.getRejectDate()));
                            listObjs.add(vNicRejectTransaction);
                            pagenatedReportData = new PaginatedResult<Object>(total, pageNo, listObjs);
                        }
                    } else if (tableName.equals("com.nec.asia.nic.framework.report.domain.AuditSessionLog")) { //TODO refactor

                        Iterator itr = list.iterator();
                        while (itr.hasNext()) {
                            AuditSessionLogDto auditSessionLogDto = new AuditSessionLogDto();
                            AuditSessionLogs record = (AuditSessionLogs) itr.next();

                            auditSessionLogDto.setArchiveBy(record.getArchiveBy());
                            if (record.getArchiveDate() != null) {
                                auditSessionLogDto.setArchiveDate(DateUtil.parseDate(record.getArchiveDate()));
                            }
                            if (record.getArchiveFlag() != null) {
                                auditSessionLogDto.setArchiveFlag(record.getArchiveFlag());
                            }

                            if (record.getLoginDate() != null) {
                                auditSessionLogDto.setLoginDate(DateUtil.parseDate(record.getLoginDate()));
                            }
                            if (record.getLogoutDate() != null) {
                                auditSessionLogDto.setLogoutDate(DateUtil.parseDate(record.getLogoutDate()));
                            }
                            if (record.getSessionId() != null) {
                                auditSessionLogDto.setSessionId(record.getSessionId());
                            }
                            if (record.getSessionLogId() > 0) {
                                auditSessionLogDto.setSessionLogId(record.getSessionLogId());
                            }

                            if (record.getTimeTaken() != null && record.getTimeTaken() > 0) {
                                auditSessionLogDto.setTimeTaken(String.valueOf(record.getTimeTaken() / 60000));
                            }

                            auditSessionLogDto.setUserId(record.getUserId());
                            auditSessionLogDto.setWkstnId(record.getWkstnId());

                            listObjs.add(auditSessionLogDto);
                            pagenatedReportData = new PaginatedResult<Object>(total, pageNo, listObjs);
                        }
                    } else if (tableName.equals("com.nec.asia.nic.framework.report.domain.AuditAccessLog")) { //TODO refactor

                        Iterator itr = list.iterator();
                        while (itr.hasNext()) {
                            AuditAccessLogDto auditAccessLogDto = new AuditAccessLogDto();
                            AuditAccessLogs record = (AuditAccessLogs) itr.next();
                            auditAccessLogDto.setUserId(record.getUserId());
                            auditAccessLogDto.setWkstnId(record.getWkstnId());
                            auditAccessLogDto.setFunctionName(record.getFunctionName());
                            auditAccessLogDto.setErrorFlag(record.getErrorFlag());
                            auditAccessLogDto.setParamValues(record.getParamValues());
                            auditAccessLogDto.setAccessLogData(record.getAccessLogData());
                            if (record.getAuditDate() != null) {
                                auditAccessLogDto.setAuditDate(DateUtil.parseDate(record.getAuditDate()));
                            }

                            listObjs.add(auditAccessLogDto);
                            pagenatedReportData = new PaginatedResult<Object>(total, pageNo, listObjs);
                        }

                    } else if (tableName.equals("com.nec.asia.nic.framework.report.domain.NicTransactionsStatistics")) {

                        Iterator itr = list.iterator();
                        while (itr.hasNext()) {
                            NicTransactionsStatisticsDto nicTransactionsStatisticsDto = new NicTransactionsStatisticsDto();
                            NicTransactionsStatistics record = (NicTransactionsStatistics) itr.next();

                            if (record.getCreateDate() != null) {
                                nicTransactionsStatisticsDto.setCreateDate(DateUtil.parseDate(record.getCreateDate()));
                            }
                            if (record.getDateOfApplication() != null) {
                                nicTransactionsStatisticsDto.setDateOfApplication(DateUtil.parseDate(record.getDateOfApplication()));
                            }
                            nicTransactionsStatisticsDto.setIssSiteCode(record.getIssSiteCode());
                            nicTransactionsStatisticsDto.setNumOfApproveTxn(record.getNumOfApproveTxn());
                            nicTransactionsStatisticsDto.setNumOfErrorProcessTxn(record.getNumOfErrorProcessTxn());
                            nicTransactionsStatisticsDto.setNumOfPendingInvestTxn(record.getNumOfPendingInvestTxn());
                            nicTransactionsStatisticsDto.setNumOfPendingProcessTxn(record.getNumOfPendingProcessTxn());
                            nicTransactionsStatisticsDto.setNumOfRejectTxn(record.getNumOfRejectTxn());
                            nicTransactionsStatisticsDto.setNumOfTotalTxns(record.getNumOfTotalTxns());
                            nicTransactionsStatisticsDto.setNumOfTxnRecieved(record.getNumOfTxnRecieved());
                            nicTransactionsStatisticsDto.setRegSiteCode(record.getRegSiteCode());
                            nicTransactionsStatisticsDto.setTransactionSubtype(record.getTransactionSubtype());
                            nicTransactionsStatisticsDto.setTransactionType(record.getTransactionType());
                            listObjs.add(nicTransactionsStatisticsDto);
                            pagenatedReportData = new PaginatedResult<Object>(total, pageNo, listObjs);
                        }

                    } else {
                        //
                        pagenatedReportData = new PaginatedResult<Object>(total, pageNo, list);
                    }
                } else {
                    pagenatedReportData = new PaginatedResult<Object>(total, pageNo, list);
                }

            } catch (Exception exp) {
                pagenatedReportData = new PaginatedResult<Object>(total, pageNo, null);
                ;
            }

            return pagenatedReportData;
        } catch (Exception ex) {
            logger.error("Error occurred while generating the report. Exception:" + ex.getMessage());
            throw new Exception("Error occurred while generating the report. Exception:" + ex.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Session session = getHibernateTemplate().getSessionFactory().openSession();
            SessionFactoryImpl sessionFactory = (SessionFactoryImpl) session.getSessionFactory();
            ConnectionProvider connectionProvider = sessionFactory.getConnectionProvider();
            connection = connectionProvider.getConnection();
//
//            Session session = getHibernateTemplate().getSessionFactory().openSession();
//            SessionFactoryImplementor sessionFactory = (SessionFactoryImplementor) session.getSessionFactory();
//            ServiceRegistryImplementor serviceRegistry = sessionFactory.getServiceRegistry();
//            connection = serviceRegistry.getService(ConnectionProvider.class).getConnection();

        } catch (Exception exp) {
            exp.getStackTrace();
        }

        return connection;
    }

    @Override
    public List getDynaJasperReportData(String query) throws Exception {
        Connection connection = null;
        List<DynaBean> results = new ArrayList<DynaBean>(0);
        try {

            connection = this.getConnection();
            ReportRowSetDynaClass resultSet = null;
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            rs.setFetchDirection(ResultSet.FETCH_FORWARD);
            resultSet = new ReportRowSetDynaClass(rs, false);
            if (resultSet != null) {
                results = resultSet.getRows();
            }
        } catch (Exception exp) {
            exp.getStackTrace();
            if (connection != null) {
                connection.close();
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return results;

    }

    public void closeConnection(Connection connection) throws Exception {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception exp) {
            logger.error("Error occurred while closing the connection");
            throw new Exception("Error occurred while closing the connection");
        }
    }

}

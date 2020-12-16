package com.nec.asia.nic.admin.audit;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequest;

import com.nec.asia.nic.framework.admin.audit.Auditable.Severity;
import com.nec.asia.nic.framework.admin.audit.impl.AbstractAuditService;
import com.nec.asia.nic.framework.admin.audit.impl.AuditBean;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.web.session.UserSession;


/**
 * Default implementation of audit.
 * 
 * @author 
 *
 */
public class NicAuditServiceImpl extends AbstractAuditService {
	private static Logger logger = LoggerFactory.getLogger(NicAuditServiceImpl.class);
	
	//@Autowired
	//private AuditLogServiceImpl auditLogService;

	@Override
	/**
	 * Override the doAudit to not log as error for business exception 
	 */
	public void doAudit(ProceedingJoinPoint pjp, 
			Severity severity, 
			Throwable throwable, 
			long timeMs){
		//extract audit info from parameters
		logger.info("doAudit({}, {}, {}, {})", new Object[]{pjp, throwable, throwable, timeMs} );
		String clazz = pjp.getSignature().getDeclaringTypeName();
		String method = pjp.getSignature().getName();
		logger.debug("audit for [{}].[{}]", new Object[]{clazz, method} );
		//do not insert audit for form validation
		Object[] args = pjp.getArgs();
		if (args.length>=3 && args[2] instanceof Boolean) {
			Boolean ajaxSubmit = (Boolean)args[2];
			logger.debug("ajax form submit value== "+ajaxSubmit.booleanValue());
			
			if(ajaxSubmit != null && !ajaxSubmit.booleanValue()){
				AuditBean auditBean = this.extractToAuditBean(args);
				
				//set the following properties by default
				auditBean.setElapsedTime(timeMs);
				auditBean.setSeverity(severity);
				if(throwable==null){ //TODO to add list of exception which should be consider as success.
					auditBean.setCode(DEFAULT_SECCESS_CODE);
				}else{
					auditBean.setCode(DEFAULT_FAILURE_CODE);
					auditBean.setMessage(throwable.getMessage());
				}
				this.store(auditBean);
			}
		} else {
			int index=0;
			for (Object obj : args) {
				logger.debug("args[{}] = {}", index++, obj);
			}
		}
	}
	
	/**
	 * extract some info to construct AuditBean
	 * @see AuditBean
	 */
	protected AuditBean extractToAuditBean(Object[] args){
		logger.debug("extracting args to auditBean");
		
		WebRequest request = (WebRequest)args[0];

		String nin = "";
		String transactionId = "";
		String userID = "";
		String wsID = "";
		String time = "";
		String subject = "";
		String functionID = "";
		
		//TODO check how to get the transaction identifiers from webrequest.
		nin = (String) request.getAttribute("", WebRequest.SCOPE_REQUEST);
		transactionId = (String) request.getAttribute("", WebRequest.SCOPE_REQUEST);
		UserSession userModel = (UserSession) request.getAttribute("userSession", WebRequest.SCOPE_GLOBAL_SESSION);
		if (userModel != null) {
			userID = userModel.getUserId();
			wsID = userModel.getWorkstationId();
		}
		subject = nin;
		
		//TODO: To remove after all login has been implemented.
		if(StringUtils.isEmpty(userID)){
			userID = "DEFAULT-USER";
		}
		if(StringUtils.isEmpty(wsID)){
			wsID = "DEFAULT-WS";
		}
		if(StringUtils.isEmpty(nin)){
			subject = transactionId;
		}
		time = DateUtil.parseDate(DateUtil.getSystemDate(), DateUtil.FORMAT_YYYYMMDDHHMMSSSSS);
		
		AuditBean auditBean = new AuditBean();
		auditBean.setSubject(subject);
		auditBean.setWhat(functionID);
		auditBean.setWhen(time);
		auditBean.setWhere(wsID);
		auditBean.setWho(userID);
		//module;		//e.g. Registration
		
		logger.debug("args: "+ArrayUtils.toString(args));
		
		logger.debug("nin ........... "+nin);
		logger.debug("tid ........... "+transactionId);
		logger.debug("subject ........... "+subject);
		logger.debug("userID ........... "+userID);
		logger.debug("wsID ........... "+wsID);
		logger.debug("functionID ........... "+functionID);
		
		return auditBean;
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class)
	public void store(AuditBean auditBean){
		
		super.store(auditBean);
		//TODO when decide where to store the data
//		Date currentDate = Calendar.getInstance().getTime();
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmmss");
//		logger.debug(sdf.format(currentDate));
//		
//		
//		Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
//		
//		String subject = auditBean.getWho();
//		
//		if(StringUtils.length(subject) > 9){
//			subject = StringUtils.substring(subject, 0, 9);
//		}
//		AuditLog auditLog = new AuditLog();
//		auditLog.setAuditDateTime(now);
//		auditLog.setAuditId(currentDate.getTime()+"_"+subject);
//		auditLog.setFunctionId(auditBean.getWhat());
//		auditLog.setStatus(auditBean.getCode());
//		auditLog.setSubjectId(auditBean.getSubject());
//		auditLog.setUserId(auditBean.getWho());
//		auditLog.setWrkStnId(auditBean.getWhere());
//		
//		auditLogService.insert(auditLog);
	}
}

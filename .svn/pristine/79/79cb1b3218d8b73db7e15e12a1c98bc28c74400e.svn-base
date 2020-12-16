package com.nec.asia.nic.framework.transaction;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.nec.asia.nic.utils.ApplicationContextManager;

/**
 * Used for higher layer then service to control bigger transaction by manual
 * Demo code:
 * <code>
 * Long party_id = (Long)new TransactionExecutor().execute(new TransactionCallback(){
		public Object process(Object clazz, Object o) throws TransactionException{
			Person person = (Person)o;
			
			BusinessService service = ((PersonBusinessAction)clazz).getService();
			Long id = (Long) ((PersonService)service).insert(person);
			
			//update card status
			
			//insert a collection record(status=not collected)
			
			return id;
		}
	},this,person);
 * </code>
 * 
 * @author bright_zheng
 *
 */
public class TransactionExecutor {
	
	public Object execute(TransactionCallback callback, Object clazz, Object o) throws TransactionException{
		Object ret = null;
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName("CustomTx");
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		PlatformTransactionManager txManager = (PlatformTransactionManager) ApplicationContextManager.getBean("transactionManager");
		TransactionStatus status = txManager.getTransaction(def);
		try {
			ret = callback.process(clazz, o);
			
			txManager.commit(status);
		}catch (Exception ex) {
			txManager.rollback(status);
			throw new TransactionException(ex);
		}
		
		return ret;
	}
}

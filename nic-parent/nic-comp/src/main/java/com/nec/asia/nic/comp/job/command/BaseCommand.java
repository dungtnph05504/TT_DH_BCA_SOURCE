package com.nec.asia.nic.comp.job.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nec.asia.nic.comp.trans.domain.SyncQueueJob;
import com.nec.asia.nic.comp.trans.service.SyncQueueJobService;


/**
 * 
 * @author johnpaul_millan
 *
 * command handler class
 * 
 */
public abstract class BaseCommand<T> implements Command<T>{
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static final String STATUS_NO_REQUIRED = "-1";
	public static final String STATUS_INITIAL = "00";
	public static final String STATUS_INPROGRESS = "01";
	public static final String STATUS_COMPLETED = "02";
	public static final String STATUS_COMPLETED_WITH_HIT = "03";
	public static final String STATUS_ERROR = "09";
	public static final String STATUS_PASS_BY_RECOUNT_MAX = "08";
	
	//TRUNG THEM DANH CHO XML
	public static final String STATUS_XML_COMPLETED = "00";
	public static final String STATUS_XML_FAILED = "01";
	
	public static final String STAGE_COMPLETED = "_COMPLETED";
	public static final String STAGE_COMPLETED_WITH_HIT = "_COMPLETED_WITH_HIT";
	public static final String STAGE_PASS_BY_RECOUNT_MAX = "_PASS_BY_RECOUNT_MAX";
	public static final String STAGE_ERROR = "_ERROR";
	
	public static final String GOTO_ERROR_CMD = "09";
	
	//public static final String FULL_AMPUTEE = "_FULL_AMPUTEE";
	public static final String WITHOUT_FP = "_NO_FP";
	
	public static final String CAR_ISS = "CAR_ISS";
	public static final String CAR_TER = "CAR_TER";
	public static final String CAR_REA = "CAR_REA";
	public static final String CAR_SUP = "CAR_SUP";
	
	public static final String ACTIVE = "ACTIVE";
	public static final String TERMINATED = "TERMINATED";
	public static final String SUSPENDED = "SUSPENDED";
	
	public static final int INT_PASS_RECOUNT_MAX = 3;

	public static final String CODE_STATUS_QUEUE_PENDING = "PENDING";
	
	private Command<T> parent;
	private Map<String, Command<T>> childs;
	private String state;



	@Override
	public void execute(T obj) {
		// TODO Auto-generated method stub
		
		doSomething((T) obj);
		String state = getState((T) obj);
		
		// true state
		// get childs(status);
		List<Command<T>> children = getChilds(state);
		// for each child call execute
		
		//System.out.println("\n BaseCommand children:"+children.size());
		
		for (Command<T> command : children) {
			command.execute((T)obj);
		}
		if (CollectionUtils.isEmpty(children)) {
			logger.info("[{}] No more command to execute.", this.getClass());
		}
	}
	
	

	public abstract void  doSomething(T obj);
		
	void setState(String state){
		this.state=state;
	}
	
	String getState(T obj){
		return this.state;
	}
		
	
	@Override
	public void addChild(String state, Command<T> childCommand) {
		
		if (childs == null){
			childs = new HashMap<String, Command<T>>();
		}
		
		childs.put(state, childCommand);
		
	}

	@Override
	public void setChilds(Map<String, Command<T>> childs) {
		
		this.childs = childs;
		
	}

	@Override
	public void removeChild(String state, Command<T> childCommand) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	@Override
	public Command<T> getParent() {
		return parent;
	}

	@Override
	public Map<String, Command<T>> getChilds() {
		return childs;
	}

	@Override
	public List<Command<T>> getChilds(String state) {
		List<Command<T>> list = new ArrayList<Command<T>>();
		
		if(childs !=null){
		
			Command<T> cmd = childs.get(state);
			if(cmd != null){
				list.add(cmd);
			}
		}
		
		return list;
	}


	
	@Override
	public void setParent(Command commandParent) {
		// TODO Auto-generated method stub
		
	}
	
}

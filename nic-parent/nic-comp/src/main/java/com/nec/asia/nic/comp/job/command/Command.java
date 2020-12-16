package com.nec.asia.nic.comp.job.command;

import java.util.List;
import java.util.Map;

import com.nec.asia.nic.comp.trans.domain.SyncQueueJob;
import com.nec.asia.nic.comp.trans.service.SyncQueueJobService;


public interface Command<T> {

	void execute(T obj);
	
	Command getParent();
	
	Map getChilds();
	
	List<Command<T>> getChilds(String state);
	
	void setParent(Command commandParent);
	
	void addChild(String state, Command<T> childCommand);
	
	void setChilds(Map<String, Command<T>> childs);
	
	void removeChild(String state, Command<T> childCommand);
	
	void doSomething(T obj);

}

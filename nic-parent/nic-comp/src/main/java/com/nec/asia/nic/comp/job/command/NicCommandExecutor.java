package com.nec.asia.nic.comp.job.command;


public class NicCommandExecutor<T> {

	
	//invoker
    private Command<T> command;
    public void setCommand(Command<T> command)
    {
        this.command = command;
        //System.out.println("\n Executor setcommand");
    }
    
   
    public void doCommand(T obj)
    {
    	//System.out.println("\n Executor docommand:"+this);
        command.execute(obj);
    }
    

	
	
}

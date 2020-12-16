package com.nec.asia.nic.investigation.dto;

import java.util.ArrayList;
import java.util.List;

public class ListWorkflowDto {
	private WorkflowDto step1;
	private List<WorkflowDto> step2 = new ArrayList<WorkflowDto>();
	private List<WorkflowDto> step3 = new ArrayList<WorkflowDto>();
	private List<WorkflowDto> step4 = new ArrayList<WorkflowDto>();
	
	public WorkflowDto getStep1() {
		return step1;
	}
	public void setStep1(WorkflowDto step1) {
		this.step1 = step1;
	}
	public List<WorkflowDto> getStep2() {
		return step2;
	}
	public void setStep2(List<WorkflowDto> step2) {
		this.step2 = step2;
	}
	public List<WorkflowDto> getStep3() {
		return step3;
	}
	public void setStep3(List<WorkflowDto> step3) {
		this.step3 = step3;
	}
	public List<WorkflowDto> getStep4() {
		return step4;
	}
	public void setStep4(List<WorkflowDto> step4) {
		this.step4 = step4;
	}
	
	
}

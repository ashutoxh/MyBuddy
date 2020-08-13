package com.ashutoxh.buddy.buddy.genericResponse;

public class GenericResponse {
	
	String result;
	String taskResponse;
	
	public GenericResponse(String result, String taskResponse) {
		super();
		this.result = result;
		this.taskResponse = taskResponse;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getTaskResponse() {
		return taskResponse;
	}
	public void setTaskResponse(String taskResponse) {
		this.taskResponse = taskResponse;
	}

}

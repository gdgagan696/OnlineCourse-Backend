package com.demo.OnlineCourse.dto;

public class ResponseDto {

	private String responseMsg;

	public ResponseDto() {}
	
	public ResponseDto(String responseMsg) {
		super();
		this.responseMsg = responseMsg;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	
	
}

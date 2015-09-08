package com.starterkit.views.model;

public enum Status {
	DONE("Done"), FAILED("Failed"), CANCELED("Canceled"), IN_PROGRESS("In progress"), NOT_STARTED("Not started");
	
	private String value;
	
	private Status(String status){
		this.value = status;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public static Status getStatusByValue(String value){
		for(Status s : values()){
			if(s.getValue().equals(value)){
				return s;
			}
		}
		return null;
	}
}


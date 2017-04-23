package com.aiblockchain.model.hana;

public class HanaInfo {
	private String hanaObjectType;
	private Object hanaObject;
	
	
	public HanaInfo(String hanaObjectType, Object hanaInfo) {
		this.hanaObjectType = hanaObjectType;
		this.hanaObject = hanaInfo;
	}
	public String getHanaObjecttype() {
		return hanaObjectType;
	}
	public void setHanaObjecttype(String hanaObjectType) {
		this.hanaObjectType = hanaObjectType;
	}
	public Object getHanaObject() {
		return hanaObject;
	}
	public void setHanaObject(Object hanaObject) {
		this.hanaObject = hanaObject;
	}	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
	    sb.append(hanaObjectType);
	    return sb.toString();
	}
}

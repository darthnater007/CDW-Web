package com.cdw.util;


public class CDWMaintenanceReturn {
	private String result;
	private String message;
	public static final String SUCCESS="success";
	public static final String FAILURE="failure";
	
	public CDWMaintenanceReturn() {
		result = "Initialized result, not yet set.";
		message = "Initialized message, not yet set.";
	}
	public CDWMaintenanceReturn(String result, String message) {
		super();
		this.result = result;
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static CDWMaintenanceReturn getMaintReturn(Object obj) {
		CDWMaintenanceReturn r = new CDWMaintenanceReturn();
		if (obj!=null) {
			r.setResult(SUCCESS);
			r.setMessage(obj.getClass().getSimpleName()+" maintenance success");
		}
		else {
			r.setResult(FAILURE);
			// Can't use obj.getClass() because obj is null
			r.setMessage("Not found");
		}
		return r;

	}

	/*
	 * Get a maintenance return obj and include a message
	 */
	public static CDWMaintenanceReturn getMaintReturn(Object obj, String msg) {
		CDWMaintenanceReturn r = new CDWMaintenanceReturn();
		if (obj!=null) {
			r.setResult(SUCCESS);
			r.setMessage(obj.getClass().getSimpleName()+" maintenance success");
		}
		else {
			r.setResult(FAILURE);
			// Can't use obj.getClass() because obj is null
			r.setMessage("msg");
		}
		return r;

	}
	
	public static CDWMaintenanceReturn getMaintReturnError(Object obj, String msg) {
		CDWMaintenanceReturn r = new CDWMaintenanceReturn();
		String errMsg = "";
		r.setResult(FAILURE);
		if (obj!=null) {
			errMsg = (obj.getClass().getSimpleName()+" maintenance error: ");
		}
		else {
			// Can't use obj.getClass() because obj is null
			errMsg = "Maintenance error: ";			
		}
		errMsg+=msg;
		r.setMessage(errMsg);
		return r;

	}
}


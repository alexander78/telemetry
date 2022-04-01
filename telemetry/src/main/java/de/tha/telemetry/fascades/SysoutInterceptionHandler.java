package de.tha.telemetry.fascades;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.tha.telemetry.anotations.InterceptorHandler;

public class SysoutInterceptionHandler implements InterceptorHandler {

	@Override
	public void precall(String methodName, Date callEntryTime, Object[] params) {
		System.err.println(
				"Method " + methodName + " is called at " + SimpleDateFormat.getDateInstance().format(callEntryTime)
						+ (params != null ? " with following params:" : " with no params."));
		if(params != null) {
			System.err.println(params);
		}

	}

	@Override
	public void postcall(String methodName, Date callEntryTime, Object params) {
		System.err.println(
				"Method " + methodName + " was called at " + SimpleDateFormat.getDateInstance().format(callEntryTime)
						+ (params != null ? " with followint result:" : " with no result."));
		if(params != null) {
			System.err.println(params);
		}	
	}

}

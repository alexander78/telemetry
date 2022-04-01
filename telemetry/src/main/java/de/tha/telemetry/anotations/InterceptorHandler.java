package de.tha.telemetry.anotations;

import java.util.Date;

public interface InterceptorHandler {

	void precall(String methodName, Date callEntryTime, Object[] params);

	void postcall(String methodName, Date callEntryTime, Object params);
	
}

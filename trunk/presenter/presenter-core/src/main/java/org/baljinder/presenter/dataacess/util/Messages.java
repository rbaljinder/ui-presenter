package org.baljinder.presenter.dataacess.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Messages {

	private static Log logger = LogFactory.getLog(Messages.class);
	
	//this is really basic
	public static String resloveMessage(String originalmessage,String []param){
		int paramIndex =0;
		String message = originalmessage;
		try{
			for(String aParam: param){
				message = message.replace("{"+paramIndex+"}", aParam);
				paramIndex++;
			}
		}catch(Throwable th){
			logger.warn("Defensively logging: mismatch in the param passed["+param.length+"] expected for the message", th.getCause());
			return originalmessage;
		}	
		return message;
	}
	
	
}

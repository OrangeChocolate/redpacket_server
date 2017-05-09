package com.redpacket.server.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redpacket.server.model.Option;

public class Utils {
	public static class GeneralResponse{
		private String result;
		private Object data;
		public GeneralResponse(String result, Object data) {
			this.result = result;
			this.data = data;
		}
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public Object getData() {
			return data;
		}
		public void setData(Object data) {
			this.data = data;
		}
		
	}
	
	public static void tokenInvalidateResponse(HttpServletResponse response) {
		// https://brendangraetz.wordpress.com/2010/06/17/use-servlet-filters-for-user-authentication/
//		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		PrintWriter out;
		try {
			out = response.getWriter();
			GeneralResponse tokenInvalidateGeneralResponse = new GeneralResponse("401", "Token invalidate");
			String responseString = new ObjectMapper().writeValueAsString(tokenInvalidateGeneralResponse);
			out.print(responseString);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Option merge(Option existOption, Option option) {
		if(option.getEnable() != null) {
			existOption.setEnable(option.getEnable());
		}
		if(option.getName() != null) {
			existOption.setName(option.getName());
		}
		if(option.getValue() != null) {
			existOption.setValue(option.getValue());
		}
		return existOption;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T mergeObjects(T exist, T patch) {
		Class<?> clazz = exist.getClass();
		try {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (!field.getName().equals("serialVersionUID")) {
					field.setAccessible(true);
					Object valueMerge = field.get(patch);
					if (valueMerge != null) {
						field.set(exist, valueMerge);
					}
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return (T) exist;
	}
}

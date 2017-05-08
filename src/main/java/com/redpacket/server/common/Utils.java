package com.redpacket.server.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

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
}

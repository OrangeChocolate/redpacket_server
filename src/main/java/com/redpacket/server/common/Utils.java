package com.redpacket.server.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redpacket.server.model.Option;
import com.redpacket.server.model.ProductDetail;

public class Utils {
	
	public static final Logger logger = LoggerFactory.getLogger(Utils.class);
	
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
				if (!field.getName().equals("serialVersionUID") && !field.getName().equals("id")) {
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
	
	public static String getFullURL(HttpServletRequest request) {
	    StringBuffer requestURL = request.getRequestURL();
	    String queryString = request.getQueryString();

	    if (queryString == null) {
	        return requestURL.toString();
	    } else {
	        return requestURL.append('?').append(queryString).toString();
	    }
	}
	
	// http://10176523.cn/archives/53
	private static String digths = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	* 将long转为62进制,长度为4位，不足补0
	* @param id
	* @return
	*/
	public static String _10_62(long id) {
	    StringBuffer str = new StringBuffer("");
	    Stack<Character> s = new Stack<Character>();
	    long num = id;
	    while (num != 0) {
	        s.push(digths.charAt((int) (num % 62)));
	        num /= 62;
	    }
	    while (!s.isEmpty()) {
	        str.append(s.pop());
	    }
	    return StringUtils.leftPad(str.toString(), 4, "0");
	}

	/**
	* 将64位字符转为10进制,长度为4位，不足补0
	* @param c
	* @return
	*/
	public static String _62_10(String c) {
	    if(c==null||c.isEmpty()){return "-1";}
	    if(!c.matches("[0-9a-zA-Z]+")){return "-1";}
	    char[] charArr2 = c.toCharArray();
	    long l = 0;
	    for (int i = 0; i < charArr2.length; i++) {
	        l += digths.indexOf(charArr2[i]) * (Math.pow(62, (charArr2.length - i - 1)));
	    }
	    return StringUtils.leftPad(String.valueOf(l), 4, "0");
	}
	
	
	/**
	 * 获取指定内容的HMAC值，返回前三个字符
	 * @param message
	 * @return
	 */
	public static String calculateSaltHash(String message) {
		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(Configuration.secret.getBytes(), "HmacSHA256");
			sha256_HMAC.init(secret_key);
			String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes()));
			return hash.substring(0, 3);
		} catch (Exception e) {
			System.out.println("Error");
		}
		return null;
	}
	
	public static String getProductScanUrlPath(Long productId, Long productDetailNum) {
		String productIdEncoded64 = Utils._10_62(productId);
		String productDetailNumEncoded64 = Utils._10_62(productDetailNum);
		String productIdAndProductDetailNumString = productIdEncoded64 + productDetailNumEncoded64;
		String hash = Utils.calculateSaltHash(productIdAndProductDetailNumString);
		return String.format("/p/%s/%s/%s", productIdEncoded64, productDetailNumEncoded64, hash);
	}

	// productScanUrlPath的一个列子是"/p/0001/0001/LtI" -split-> ("","p","0001","0001","LtI")
	public static boolean checkProductScanUrlPath(String productScanUrlPath) {
		String[] split = productScanUrlPath.split("/");
		if(split.length < 5) {
			logger.info("invalide productScanUrlPath: {}", productScanUrlPath);
			return false;
		}
		String productIdString = split[2];
		String productDetailNumString = split[3];
		String hash = split[4];
		String productIdAndProductDetailNumString = productIdString + productDetailNumString;
		String CalculateHash = Utils.calculateSaltHash(productIdAndProductDetailNumString);
		return hash.equals(CalculateHash);
	}
	
	private static SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");

	public static Date getDateBegin(String dateString) {
		Date date = null;
		try {
			date = parser.parse(dateString);
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getDateEnd(String dateString) {
		Date date = null;
		try {
			date = parser.parse(dateString);
			date.setHours(23);
			date.setMinutes(59);
			date.setSeconds(59);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getDateBegin(Date currentDate) {
		Date date = new Date(currentDate.getTime());
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		return date;
	}

	public static Date getDateEnd(Date currentDate) {
		Date date = new Date(currentDate.getTime());
		date.setHours(23);
		date.setMinutes(59);
		date.setSeconds(59);
		return date;
	}

	public static int getRandom(int randomMinAmount, int randomMaxAmount) {
		return (int)(Math.random() * (randomMaxAmount - randomMinAmount) + randomMinAmount);
	}
}

package com.redpacket.server.common;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.ssl.SSLContexts;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.redpacket.server.ApplicationProperties;
import com.redpacket.server.model.SendRedPack;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.internal.platform.Platform;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by 桑博 on 2016/9/6.
 */
public class Tool {
	
    @Autowired
    private ApplicationProperties applicationProperties;


    /**
     * 获取订单号
     * @return
     */
    public static String getOrderNum(){

        return String.valueOf(System.currentTimeMillis())+getRandom4();

    }


    /**
     * 获取一个4位随机数
     * @return
     */
    public static int getRandom4(){
        int x = RandomUtils.nextInt(1000,9999);
        return x;
    }

    /**
     * 获取UUID
     * @return
     */
    public static String returnUUID() {
        return parseStrToMd5L32(UUID.randomUUID().toString());
    }

    /**
     * 获取md5
     * @param str
     * @return
     */
    public static String parseStrToMd5L32(String str) {
        return parseStrToMd5L32(str,"utf-8");
    }

    public static String parseStrToMd5L32(String str,String charset) {
        String reStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes(charset));
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes) {
                int bt = b & 0xff;
                if (bt < 16) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            reStr = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return reStr;
    }

    /**
     * 将map转换成url
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        map = new TreeMap<String, String>(map);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if(entry.getValue() == null){
                continue;
            }
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = StringUtils.substringBeforeLast(s, "&");
        }
        return s;
    }

    /**
     * Sha1加密方法
     */
    public static String getSha1(String decript) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static Map toMap(Object bean) {
        Class<? extends Object> clazz = bean.getClass();
        Map<Object, Object> returnMap = new HashMap<Object, Object>();
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = null;
                    result = readMethod.invoke(bean, new Object[0]);
                    if (null != propertyName) {
                        propertyName = propertyName.toString();
                    }
                    if (null != result) {
                        result = result.toString();
                        returnMap.put(propertyName, result);
                    }
                }
            }
        } catch (IntrospectionException e) {
            System.out.println("分析类属性失败");
        } catch (IllegalAccessException e) {
            System.out.println("实例化 JavaBean 失败");
        } catch (IllegalArgumentException e) {
            System.out.println("映射错误");
        } catch (InvocationTargetException e) {
            System.out.println("调用属性的 setter 方法失败");
        }
        return returnMap;
    }

    /**
     * 凑成xml格式字符串
     *
     * @return
     */
    public static String getSoapRequestData(Map<String, String> map) throws JSONException {

        StringBuffer sb = new StringBuffer();

        sb.append("<xml>");

        for (Map.Entry<String, String> entry : map.entrySet()) {

            sb.append("<" + entry.getKey() + ">" + entry.getValue() + "</" + entry.getKey() + ">");
        }

        sb.append("</xml>");
        return sb.toString();
    }

    public static SSLContext getSSL(String certPath, String certSecret) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        //证书位置自己定义
        FileInputStream instream = new FileInputStream(new File(certPath));
        try {
            keyStore.load(instream, certSecret.toCharArray());
        } finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, certSecret.toCharArray())
                .build();
        return sslcontext;
    }



	public static OkHttpClient buildHttpClient(String certPath, String apiSecret) {
        //为http请求设置证书
		SSLSocketFactory socketFactory = null;
		try {
			socketFactory = Tool.getSSL(certPath, apiSecret).getSocketFactory();
		} catch (UnrecoverableKeyException | KeyManagementException | KeyStoreException | CertificateException
				| NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
        X509TrustManager x509TrustManager = Platform.get().trustManager(socketFactory);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().sslSocketFactory(socketFactory, x509TrustManager).build();
		return okHttpClient;
	}


	public static Request buildRequest(String soapRequestData) {
		//发起请求前准备
        RequestBody body = RequestBody.create(MediaType.parse("text/xml;charset=UTF-8"), soapRequestData);
        Request request = new Request.Builder()
                .url("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack")
                .post(body)
                .build();
		return request;
	}


	public static String generateSoapRequestData(SendRedPack sendRedPack, String apiSecret) {
        //将实体类转换为url形式
		String urlParamsByMap = Tool.getUrlParamsByMap(Tool.toMap(sendRedPack));
        //拼接我们在前期准备好的API密钥，前期准备第5条
        urlParamsByMap += "&key=" + apiSecret;
        //进行签名，需要说明的是，如果内容包含中文的话，要使用utf-8进行md5签名，不然会签名错误
        String sign = Tool.parseStrToMd5L32(urlParamsByMap).toUpperCase();
        sendRedPack.setSign(sign);
        //微信要求按照参数名ASCII字典序排序，这里巧用treeMap进行字典排序
        TreeMap treeMap = new TreeMap(Tool.toMap(sendRedPack));
        //然后转换成xml格式
        String soapRequestData = null;
		try {
			soapRequestData = Tool.getSoapRequestData(treeMap);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return soapRequestData;
	}

}

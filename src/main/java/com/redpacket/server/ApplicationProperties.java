package com.redpacket.server;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {
	
    /**
     * 设置扫描网址hash的secret
     */
    private String hash_secret;
	
    /**
     * 设置腾讯地图的key
     */
    private String map_key;
    
    /**
     * 商户号
     */
    private String mch_id;
    
    /**
     * 主机的IP地址
     */
    private String host_ip_address;
    
    /**
     * 服务端域名
     */
    private String domain;
    
    /**
     * 商户平台的API秘钥
     */
    private String mch_api_secret;
    
    /**
     * 证书秘钥
     */
    private String mch_cert_secret;
    
    /**
     * 商户平台的证书文件路劲
     */
    private String mch_cert_path;
    
	public String getHash_secret() {
		return hash_secret;
	}

	public void setHash_secret(String hash_secret) {
		this.hash_secret = hash_secret;
	}

	public String getMap_key() {
		return map_key;
	}

	public void setMap_key(String map_key) {
		this.map_key = map_key;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getHost_ip_address() {
		return host_ip_address;
	}

	public void setHost_ip_address(String host_ip_address) {
		this.host_ip_address = host_ip_address;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getMch_api_secret() {
		return mch_api_secret;
	}

	public void setMch_api_secret(String mch_api_secret) {
		this.mch_api_secret = mch_api_secret;
	}

	public String getMch_cert_secret() {
		return mch_cert_secret;
	}

	public void setMch_cert_secret(String mch_cert_secret) {
		this.mch_cert_secret = mch_cert_secret;
	}

	public String getMch_cert_path() {
		return mch_cert_path;
	}

	public void setMch_cert_path(String mch_cert_path) {
		this.mch_cert_path = mch_cert_path;
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
            ToStringStyle.MULTI_LINE_STYLE);
    }
}

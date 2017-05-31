package com.redpacket.server.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redpacket.server.ApplicationMessageConfiguration;
import com.redpacket.server.ApplicationProperties;
import com.redpacket.server.common.Configuration;
import com.redpacket.server.common.GeneralResponse;
import com.redpacket.server.common.Utils;
import com.redpacket.server.model.City;
import com.redpacket.server.model.Option;
import com.redpacket.server.model.Product;
import com.redpacket.server.model.ProductDetail;
import com.redpacket.server.model.RedPacket;
import com.redpacket.server.model.WechatUser;
import com.redpacket.server.service.ProductDetailService;
import com.redpacket.server.service.ProductService;
import com.redpacket.server.service.RedPacketService;
import com.redpacket.server.service.WechatUserService;
import com.redpacket.server.wechat.config.WechatMpProperties;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import model.SendRedPack;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;
import tool.Tool;

@Controller
@RequestMapping("/")
@EnableConfigurationProperties({WechatMpProperties.class, ApplicationProperties.class})
public class WebController {
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private WechatMpProperties wechatMpProperties;

    @Autowired
    private ApplicationProperties applicationProperties;
    
    @Autowired
    private ProductDetailService productDetailService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private WechatUserService wechatUserService;
    
    @Autowired
    private RedPacketService redPacketService;

    @Autowired
    private WxMpService wxService;
    
    @Autowired
    private ApplicationMessageConfiguration applicationMessageConfiguration;

    @Autowired
    private WxMpMessageRouter router;
    
    private static String OAUTH2_REDIRECT_PATH = "/oauth2/authorize";
    
    // https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842&token=&lang=zh_CN
    // 以snsapi_base为scope发起的网页授权，是用来获取进入页面的用户的openid的，并且是静默授权并自动跳转到回调页的。用户感知的就是直接进入了回调页（往往是业务页面）
    // 以snsapi_userinfo为scope发起的网页授权，是用来获取用户的基本信息的。但这种授权需要用户手动同意，并且由于用户同意过，所以无须关注，就可在授权后获取该用户的基本信息。 
    private static String OAUTH2_SCOPE = "snsapi_userinfo";
    private static String OAUTH2_STATE = "STATE";

	@RequestMapping(value = "p/{p_id}/{p_num}/{checksum}", method = RequestMethod.GET)
	public String productScan(@PathVariable("p_id") String p_id, 
			@PathVariable("p_num") String p_num, 
			@PathVariable("checksum") String checksum, Model model, HttpServletRequest request) throws WxErrorException, MalformedURLException {

	    URL url = new URL(request.getRequestURL().toString());
	    String protocol = url.getProtocol();
	    String host  = url.getHost();
	    int port = url.getPort();
	    if(port == -1) {
	    	port = 80;
	    }
	    String path = url.getPath();
	    

		// 检测checksum
		boolean isUrlValid = Utils.checkProductScanUrlPath(path);
		if(!isUrlValid) {
			model.addAttribute("error_message", applicationMessageConfiguration.scanUrlInvalidate);
			return "product-scan-error";
		}
		// 检测产品是否可以扫码
		long productId = Long.parseLong(Utils._62_10(p_id));
		long productDetailNum = Long.parseLong(Utils._62_10(p_num));
		ProductDetail productDetail = productDetailService.findByProductIdAndProductDetailNum(productId, productDetailNum);
		if(productDetail == null) {
			model.addAttribute("error_message", applicationMessageConfiguration.scanItemNotFound);
			return "product-scan-error";
		}
		// 检测产品是否启用现金红包
		if(!productDetail.getEnable()) {
			model.addAttribute("error_message", applicationMessageConfiguration.scanItemNotEnable);
			return "product-scan-error";
		}
		// 检测产品是否已扫码领取过红包
		if(productDetail.isScanned()) {
			model.addAttribute("error_message", applicationMessageConfiguration.scanItemHasUsed);
			return "product-scan-error";
		}
		
		String requestUrl = Utils.getFullURL(request);
		WxJsapiSignature jsapiSignature = wxService.createJsapiSignature(requestUrl);
		
	    String redirectURI = String.format("%s://%s%s", protocol, host, OAUTH2_REDIRECT_PATH);
	    // 这里需要把扫码相关信息带到微信认证的成功页面，这里使用state参数，传入path
		String oauth2AuthorizationUrl = wxService.oauth2buildAuthorizationUrl(redirectURI, OAUTH2_SCOPE, path);
		
		model.addAttribute("jsapiSignature", jsapiSignature);
		model.addAttribute("p_id", p_id);
		model.addAttribute("p_num", p_num);
		model.addAttribute("checksum", checksum);
		model.addAttribute("oauth2AuthorizationUrl", oauth2AuthorizationUrl);
		
        return "product-scan";
	}
	

	@RequestMapping(value = "oauth2/authorize", method = RequestMethod.GET)
	public String productScan(@RequestParam("code") String code, Model model, HttpServletRequest request) {
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
		WxMpUser wxMpUser = null;
		WxJsapiSignature jsapiSignature = null;
		String requestUrl = Utils.getFullURL(request);
		try {
			wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);
			wxMpUser = wxService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
			jsapiSignature = wxService.createJsapiSignature(requestUrl);
		} catch (WxErrorException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return "wx-user-error";
		}
		model.addAttribute("jsapiSignature", jsapiSignature);
		model.addAttribute("wxMpOAuth2AccessToken", wxMpOAuth2AccessToken);
		model.addAttribute("wxMpUser", wxMpUser);
		return "wx-user";
	}
	

	@RequestMapping(value = "p/sharedtimeline", method = RequestMethod.GET)
	public @ResponseBody GeneralResponse<String> productSharedTimeline(@RequestParam String state, @RequestParam String openId) {
		String path = state;
		boolean isValidateScanPath = Utils.checkProductScanUrlPath(path);
		if(!isValidateScanPath) {
			return new GeneralResponse<String>(GeneralResponse.ERROR, applicationMessageConfiguration.scanUrlInvalidate);
		}
		String[] split = path.split("/");
		long productId = Long.parseLong(Utils._62_10(split[2]));
		long productDetailNum = Long.parseLong(Utils._62_10(split[3]));
		ProductDetail productDetail = productDetailService.findByProductIdAndProductDetailNum(productId, productDetailNum);
		if(productDetail == null) {
			return new GeneralResponse<String>(GeneralResponse.ERROR, applicationMessageConfiguration.scanItemNotFound);
		}
		if(!productDetail.getEnable()) {
			return new GeneralResponse<String>(GeneralResponse.ERROR, applicationMessageConfiguration.scanItemNotEnable);
		}
		// 检测用户是否可以领取红包
		List<RedPacket> redPacketsTotal = redPacketService.findByUserOpenId(openId);
		List<RedPacket> redPacketsDaily = redPacketService.findByUserOpenIdCurrentDay(openId, new Date());
		Option maxRedpacketsUserTotalOption = Configuration.getOption(Configuration.max_redpackets_user_total_key);
		Option maxRedpacketsUserDailyOption = Configuration.getOption(Configuration.max_redpackets_user_daily_key);
		if(redPacketsTotal != null && maxRedpacketsUserTotalOption.getEnable() 
				&& redPacketsTotal.size() >= Integer.parseInt(maxRedpacketsUserTotalOption.getValue())) {
			return new GeneralResponse<String>(GeneralResponse.ERROR, applicationMessageConfiguration.scanItemRedpacketExceedTotal);
		}
		if(redPacketsDaily != null && maxRedpacketsUserDailyOption.getEnable() 
				&& redPacketsDaily.size() >= Integer.parseInt(maxRedpacketsUserDailyOption.getValue())) {
			return new GeneralResponse<String>(GeneralResponse.ERROR, applicationMessageConfiguration.scanItemRedpacketExceedDaily);
		}
		// 检测产品是否可以领取红包，主要是城市信息
		Product product = productService.findById(productId);
		if(product == null) {
			return new GeneralResponse<String>(GeneralResponse.ERROR, applicationMessageConfiguration.scanItemNotFound);
		}
		WechatUser wechatUser = wechatUserService.findByOpenId(openId);
		if(wechatUser == null) {
			return new GeneralResponse<String>(GeneralResponse.ERROR, applicationMessageConfiguration.scanItemUserNotFound);
		}
		Option forceCityCheckOption = Configuration.getOption(Configuration.force_city_check_key);
		String actualCityString = wechatUser.getActualCity();
		City actualCity = new City(actualCityString);
		Set<City> allowSellCities = product.getAllowSellCities();
		if(forceCityCheckOption.getEnable() && allowSellCities.contains(actualCity)) {
			return new GeneralResponse<String>(GeneralResponse.ERROR, applicationMessageConfiguration.scanItemCityNotMatch);
		}
		// 发红包啦
		int amount = 0;
		if(product.isRandomRedpacket()) {
			amount = Utils.getRandom(product.getRandomMinAmount(), product.getRandomMaxAmount());
		}
		else {
			amount = product.getAverageAmount();
		}
		productDetail.setScanned(true);
		productDetailService.saveOrUpdate(productDetail);
		RedPacket redPacket = new RedPacket(wechatUser, productDetail, amount, new Date());
		redPacketService.saveOrUpdate(redPacket);
		// 调用商户平台api发放现金红包
		

        //具体参数查看具体实体类，实体类中的的参数参考微信的红包发放接口，这里你直接用map，进行设置参数也可以。。。
        SendRedPack sendRedPack = new SendRedPack(
                Utils.getRandomString(),
                Utils.getMchBillNo(openId),
                applicationProperties.getMch_id(),
                wechatMpProperties.getAppId(),
                Configuration.getOption(Configuration.wechat_send_name_key).getValue(),
                openId,
                amount,
                1,
                Configuration.getOption(Configuration.wechat_wishing_key).getValue(),
                applicationProperties.getHostIpAddress(),
                Configuration.getOption(Configuration.wechat_act_name_key).getValue(),
                Configuration.getOption(Configuration.wechat_remark_key).getValue(),
                "PRODUCT_2"
        );


        //将实体类转换为url形式
        String urlParamsByMap = Tool.getUrlParamsByMap(Tool.toMap(sendRedPack));
        //拼接我们在前期准备好的API密钥，前期准备第5条
        urlParamsByMap += "&key=填写API密钥";
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
        //发起请求前准备
        RequestBody body = RequestBody.create(MediaType.parse("text/xml;charset=UTF-8"), soapRequestData);
        Request request = new Request.Builder()
                .url("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack")
                .post(body)
                .build();
        //为http请求设置证书
        SSLSocketFactory socketFactory = null;
		try {
			socketFactory = Tool.getSSL().getSocketFactory();
		} catch (UnrecoverableKeyException | KeyManagementException | KeyStoreException | CertificateException
				| NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
        X509TrustManager x509TrustManager = Platform.get().trustManager(socketFactory);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().sslSocketFactory(socketFactory, x509TrustManager).build();
        //得到输出内容
        Response response;
		try {
			response = okHttpClient.newCall(request).execute();
	        String content = response.body().string();
	        System.out.println(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new GeneralResponse<String>(GeneralResponse.SUCCESS, applicationMessageConfiguration.scanItemRedpacketGot);
	}

}

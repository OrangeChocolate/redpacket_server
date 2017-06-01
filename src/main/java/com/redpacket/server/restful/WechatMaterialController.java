package com.redpacket.server.restful;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.redpacket.server.common.Utils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialArticleUpdate;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialCountResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialFileBatchGetResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNewsBatchGetResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialVideoInfoResult;

@CrossOrigin
@Api(tags = { "wechatMaterial" })
@RestController
@RequestMapping("/api/wechatMaterial")
// https://github.com/chanjarster/weixin-java-tools/wiki/MP_%E6%B0%B8%E4%B9%85%E7%B4%A0%E6%9D%90%E7%AE%A1%E7%90%86
public class WechatMaterialController {

	public static final Logger logger = LoggerFactory.getLogger(WechatMaterialController.class);
	
	// https://mp.weixin.qq.com/wiki?id=mp1444738729&t=0.6939455203510161
	public static enum MediaType {
		image, voice, video, thumb;
	}

	@Autowired
	private WxMpService wxService;

	@ApiOperation(value = "添加图文永久素材", notes = "图文素材支持单图文和多图文，由类 WxMpMaterialNews 进行封装，图文的数据通过类 WxMpMaterialNews.WxMpMaterialNewsArticle 封装", authorizations = { @Authorization(value = "token") })
	@RequestMapping(value = "/materialNewsInfo/", method = RequestMethod.POST, produces = "application/json")
	public WxMpMaterialUploadResult materialNewsUpload(@RequestBody WxMpMaterialNews news) throws WxErrorException {
		WxMpMaterialUploadResult materialNewsUpload = wxService.getMaterialService().materialNewsUpload(news);
		return materialNewsUpload;
	}

	@ApiOperation(value = "添加其他类型永久素材", notes = "非图文永久素材由类 WxMpMaterial 封装，对于非视频类的永久素材，构造时传入素材文件对象file和素材名name即可，素材名会显示在公众平台官网素材管理模块中，其余两个字段可设置为null或者空字符串。视频类永久素材需要在构造时传入视频名title和简介introduction，目前已知视频支持mp4格式", authorizations = { @Authorization(value = "token") })
	@RequestMapping(value = "/materialFileUpload/{mediaType}", method = RequestMethod.POST, consumes = "multipart/form-data", produces = "application/json")
	public WxMpMaterialUploadResult materialNewsUpload(@PathVariable MediaType mediaType, @RequestPart(required = true) MultipartFile file,
			@RequestParam String name, @RequestParam(required=false) String videoTitle, @RequestParam(required=false) String videoIntroduction) throws WxErrorException {
		WxMpMaterial material = new WxMpMaterial(name, Utils.getTempFile(file), videoTitle, videoIntroduction);
		WxMpMaterialUploadResult materialFileUpload = wxService.getMaterialService().materialFileUpload(mediaType.name(), material);
		return materialFileUpload;
	}

	@ApiOperation(value = "获取图文永久素材", notes = "根据 media_id 获取图文消息", authorizations = { @Authorization(value = "token") })
	@RequestMapping(value = "/materialNewsInfo/{media_id}", method = RequestMethod.GET, produces = "application/json")
	public WxMpMaterialNews materialNewsInfo(@PathVariable String media_id) throws WxErrorException {
		WxMpMaterialNews materialNewsInfo = wxService.getMaterialService().materialNewsInfo(media_id);
		return materialNewsInfo;
	}

	@ApiOperation(value = "获取视频永久素材", notes = "根据 media_id 获取声音或者图片的输入流", authorizations = { @Authorization(value = "token") })
	@RequestMapping(value = "/materialImageOrVoiceDownload/{media_id}", method = RequestMethod.GET, produces = "application/json")
	public InputStream materialImageOrVoiceDownload(@PathVariable String media_id) throws WxErrorException {
		InputStream materialImageOrVoiceDownload = wxService.getMaterialService().materialImageOrVoiceDownload(media_id);
		return materialImageOrVoiceDownload;
	}
	
	@ApiOperation(value = "获取视频永久素材", notes = "根据 media_id 获取视频信息，返回结果中包含视频下载地址", authorizations = { @Authorization(value = "token") })
	@RequestMapping(value = "/materialVideoInfo/{media_id}", method = RequestMethod.GET, produces = "application/json")
	public WxMpMaterialVideoInfoResult materialVideoInfo(@PathVariable String media_id) throws WxErrorException {
		WxMpMaterialVideoInfoResult materialVideoInfo = wxService.getMaterialService().materialVideoInfo(media_id);
		return materialVideoInfo;
	}
	
	@ApiOperation(value = "删除素材", notes = "根据 media_id 删除素材", authorizations = { @Authorization(value = "token") })
	@RequestMapping(value = "/{media_id}", method = RequestMethod.DELETE, produces = "application/json")
	public boolean materialDelete(@PathVariable String media_id) throws WxErrorException {
		boolean materialDelete = wxService.getMaterialService().materialDelete(media_id);
		return materialDelete;
	}
	
	@ApiOperation(value = "修改永久图文素材", notes = "根据更新图文对象更新图文素材，对于多图文消息，如需更新其中某一个，需要设置更新对象中的index属性", authorizations = { @Authorization(value = "token") })
	@RequestMapping(value = "/materialNewsUpdate/", method = RequestMethod.PUT, produces = "application/json")
	public boolean materialVideoInfo(@RequestBody WxMpMaterialArticleUpdate wxMpMaterialArticleUpdate) throws WxErrorException {
		boolean materialNewsUpdate = wxService.getMaterialService().materialNewsUpdate(wxMpMaterialArticleUpdate);
		return materialNewsUpdate;
	}

	@ApiOperation(value = "获取素材总数", notes = "获取素材总数，返回结果由类 WxMpMaterialCountResult 封装", authorizations = { @Authorization(value = "token") })
	@RequestMapping(value = "/materialCount", method = RequestMethod.GET, produces = "application/json")
	public WxMpMaterialCountResult materialCount() throws WxErrorException {
		WxMpMaterialCountResult materialCount = wxService.getMaterialService().materialCount();
		return materialCount;
	}

	@ApiOperation(value = "根据类别分页获取非图文素材列表", notes = "获取素材总数，返回结构由类 WxMpMaterialFileBatchGetResult 封装", authorizations = { @Authorization(value = "token") })
	@RequestMapping(value = "/materialFileBatchGet", method = RequestMethod.GET, produces = "application/json")
	public WxMpMaterialFileBatchGetResult materialFileBatchGet(@RequestParam String type, @RequestParam int offset, @RequestParam int count) throws WxErrorException {
		WxMpMaterialFileBatchGetResult materialFileBatchGet = wxService.getMaterialService().materialFileBatchGet(type, offset, count);
		return materialFileBatchGet;
	}

	@ApiOperation(value = "根据类别分页获取图文素材列表", notes = "获取素材总数，返回结构由类 WxMpMaterialNewsBatchGetResult 封装", authorizations = { @Authorization(value = "token") })
	@RequestMapping(value = "/materialNewsBatchGet", method = RequestMethod.GET, produces = "application/json")
	public WxMpMaterialNewsBatchGetResult materialNewsBatchGet(@RequestParam int offset, @RequestParam int count) throws WxErrorException {
		WxMpMaterialNewsBatchGetResult materialNewsBatchGet = wxService.getMaterialService().materialNewsBatchGet(offset, count);
		return materialNewsBatchGet;
	}
}

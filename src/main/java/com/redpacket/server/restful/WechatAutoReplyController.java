package com.redpacket.server.restful;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.redpacket.server.common.Utils;
import com.redpacket.server.model.WechatAutoReply;
import com.redpacket.server.service.WechatAutoReplyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import me.chanjar.weixin.common.exception.WxErrorException;

@CrossOrigin
@Api(tags = { "wechatAutoReply" })
@RestController
@RequestMapping("/api/wechatAutoReply")
public class WechatAutoReplyController {

	public static final Logger logger = LoggerFactory.getLogger(WechatAutoReplyController.class);

	@Autowired
	private WechatAutoReplyService wechatAutoReplyService;

	@ApiOperation(value = "get all wechatAutoReply", notes = "get all wechatAutoReply", authorizations = { @Authorization(value = "token") })
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public List<WechatAutoReply> findAll() throws WxErrorException {
		return wechatAutoReplyService.findAll();
	}
	
	@ApiOperation(value = "get a wechatAutoReply", notes = "get a wechatAutoReply", authorizations = { @Authorization(value = "token") })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public WechatAutoReply findById(@PathVariable long id) throws WxErrorException {
		return wechatAutoReplyService.findById(id);
	}

	@ApiOperation(value = "create wechatAutoReply", notes = "create menu in json", authorizations = { @Authorization(value = "token") })
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
	public WechatAutoReply create(@RequestBody WechatAutoReply wechatAutoReply) throws WxErrorException {
		return wechatAutoReplyService.save(wechatAutoReply);
	}

	@ApiOperation(value = "update wechatAutoReply", notes = "update menu in json", authorizations = { @Authorization(value = "token") })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public WechatAutoReply update(@PathVariable long id, @RequestBody WechatAutoReply wechatAutoReply) throws WxErrorException {
		WechatAutoReply origniAutoReply = wechatAutoReplyService.findById(id);
		// Fix HibernateException: identifier of an instance of WechatAutoReply was altered from x to y
		wechatAutoReply.setId(null);
		WechatAutoReply mergeObjects = Utils.mergeObjects(origniAutoReply, wechatAutoReply);
		return wechatAutoReplyService.save(mergeObjects);
	}

	@ApiOperation(value = "delete a wechatAutoReply", notes = "delete a wechatAutoReply", authorizations = { @Authorization(value = "token") })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public WechatAutoReply delete(@PathVariable long id) throws WxErrorException {
		WechatAutoReply wechatAutoReply = wechatAutoReplyService.findById(id);
		wechatAutoReplyService.delete(id);
		return wechatAutoReply;
	}
}

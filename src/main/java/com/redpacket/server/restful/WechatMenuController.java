package com.redpacket.server.restful;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;

@CrossOrigin
@Api(tags = { "wechatMenu" })
@RestController
@RequestMapping("/api/wechatMenu")
// https://github.com/chanjarster/weixin-java-tools/wiki/MP_%E8%87%AA%E5%AE%9A%E4%B9%89%E8%8F%9C%E5%8D%95%E7%AE%A1%E7%90%86
public class WechatMenuController {

	public static final Logger logger = LoggerFactory.getLogger(WechatMenuController.class);

	@Autowired
	private WxMpService wxService;

	@ApiOperation(value = "获得自定义菜单", notes = "获得自定义菜单", authorizations = { @Authorization(value = "token") })
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public WxMpMenu menuGet() throws WxErrorException {
		WxMpMenu wxMpMenu = wxService.getMenuService().menuGet();
		return wxMpMenu;
	}

	@ApiOperation(value = "删除自定义菜单", notes = "删除自定义菜单", authorizations = { @Authorization(value = "token") })
	@RequestMapping(value = "/", method = RequestMethod.DELETE, produces = "application/json")
	public WxMpMenu menuDelete() throws WxErrorException {
		WxMpMenu wxMpMenu = wxService.getMenuService().menuGet();
		wxService.getMenuService().menuDelete();
		return wxMpMenu;
	}

	@ApiOperation(value = "创建自定义菜单", notes = "创建自定义菜单", authorizations = { @Authorization(value = "token") })
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
	public WxMpMenu menuCreate(@RequestBody WxMenu menu) throws WxErrorException {
		String menuId = wxService.getMenuService().menuCreate(menu);
		WxMpMenu wxMpMenu = wxService.getMenuService().menuGet();
		return wxMpMenu;
	}
}

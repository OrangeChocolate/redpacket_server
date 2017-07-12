package com.redpacket.server.restful;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.redpacket.server.common.GeneralResponse;
import com.redpacket.server.service.ExtraFeatureService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@Api(tags = { "extra" })
@RestController
@RequestMapping("/extra/")
public class ExtraFeatureController {

	public static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);

	@Autowired
	private ExtraFeatureService extraFeatureService;

	@ApiOperation(value = "update user location", notes = "update user location")
	@RequestMapping(value = "location/{openId}/{latitude}/{longitude}", method = RequestMethod.PUT, produces = "application/json")
	public GeneralResponse<String> updateLocation(@PathVariable String openId, @PathVariable double latitude,
			@PathVariable double longitude) {
		String cityString = extraFeatureService.updateLocation(openId, latitude, longitude);
		return new GeneralResponse<String>(cityString != null ? GeneralResponse.SUCCESS : GeneralResponse.ERROR, cityString);

	}

	@RequestMapping(value = "redirect")
	public ModelAndView redirect(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "redirect_code", required = false, defaultValue = "301") int redirect_code,
			@RequestParam(name = "url", required = false, defaultValue = "http://inthb.cn/redpacket-share.html") String url) {
		HttpStatus redirectHttpStatus = (redirect_code == 301) ? HttpStatus.MOVED_PERMANENTLY : HttpStatus.FOUND;
		request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, redirectHttpStatus);
		return new ModelAndView("redirect:" + url);
	}

	@RequestMapping(value = "redirect_refresh")
	public ModelAndView redirect_refresh(Model model,
			@RequestParam(name = "url", required = false, defaultValue = "http://inthb.cn/redpacket-share.html") String url) {
		model.addAttribute("url", String.format("0;URL='%s'", url));
		return new ModelAndView("redpacket-redirect");
	}

}

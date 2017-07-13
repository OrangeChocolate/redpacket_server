package com.redpacket.server.restful;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.redpacket.server.common.CustomErrorType;
import com.redpacket.server.common.Utils;
import com.redpacket.server.model.ProductDetail;
import com.redpacket.server.model.RedPacket;
import com.redpacket.server.service.RedPacketService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@CrossOrigin
@Api(tags={"redPacket"})
@RestController
@RequestMapping("/api/redPacket/")
public class RedPacketController {
	
	public static final Logger logger = LoggerFactory.getLogger(RedPacketController.class);
	
	@Autowired
	private RedPacketService redPacketService;
	
	@ApiOperation(value = "List all redPacket", notes = "List all redPacket in json response", authorizations={@Authorization(value = "token")})
	@ApiImplicitParams({ @ApiImplicitParam(name = "wechatNickname", paramType = "query"),
		@ApiImplicitParam(name = "page", defaultValue="0", paramType = "query"),
		@ApiImplicitParam(name = "size", defaultValue = "10", paramType = "query"),
		@ApiImplicitParam(name = "sort", defaultValue = "updateDate,desc", paramType = "query")})
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<RedPacket>> get(HttpServletRequest request, HttpServletResponse response,
			@Spec(path = "wechatNickname", spec = Like.class) Specification<RedPacket> spec,
	        @PageableDefault(size = 1000, sort = "createDateTime", direction=Direction.DESC) Pageable pageable) {
		Page<RedPacket> page = redPacketService.findAll(spec, pageable);
		List<RedPacket> redPackets = page.getContent();
		Utils.setExtraHeader(response, page);
		return new ResponseEntity<List<RedPacket>>(redPackets, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Get a redPacket", notes = "Get a redPacket by id with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RedPacket> get(@PathVariable Long id) {
		RedPacket redPacket = redPacketService.findById(id);
		if(redPacket == null) {
            logger.error("RedPacket with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("RedPacket with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RedPacket>(redPacket, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Get a redPacket by product detail id", notes = "Get a redPacket by product detail id with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/productDetail/{productDetailId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<RedPacket>> getByProductDetailId(@PathVariable Long productDetailId) {
		List<RedPacket> redPackets = redPacketService.findByProductDetailId(productDetailId);
		if(redPackets == null) {
            logger.error("RedPacket with productDetailId {} not found.", productDetailId);
            return new ResponseEntity(new CustomErrorType("RedPacket with productDetailId " + productDetailId + " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<RedPacket>>(redPackets, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Create a redPacket", notes = "Create a redPacket with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<RedPacket> create(@RequestBody RedPacket redPacket) {
		if(redPacket.getUser() == null) {
            logger.error("RedPacket name or amount is empty.");
            return new ResponseEntity(new CustomErrorType("RedPacket name or amount is empty."), HttpStatus.BAD_REQUEST);
		}
		// delete the id safely
		redPacket.setId(null);
		RedPacket pesistedRedPacket = redPacketService.saveOrUpdate(redPacket);
		return new ResponseEntity<RedPacket>(pesistedRedPacket, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Update a redPacket", notes = "Update a redPacket with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<RedPacket> update(@PathVariable("id") Long id, @RequestBody RedPacket redPacket) {
		RedPacket pesistedRedPacket = redPacketService.findById(id);
		if(pesistedRedPacket == null) {
            logger.error("RedPacket with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("RedPacket with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		pesistedRedPacket = redPacketService.saveOrUpdate(redPacket);
		return new ResponseEntity<RedPacket>(pesistedRedPacket, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Delete a redPacket", notes = "Delete a redPacket by id with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<RedPacket> delete(@PathVariable Long id) {
		RedPacket redPacket = redPacketService.findById(id);
		if(redPacket == null) {
            logger.error("RedPacket with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("RedPacket with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		redPacketService.delete(redPacket);
		return new ResponseEntity<RedPacket>(redPacket, HttpStatus.OK);
	}
}

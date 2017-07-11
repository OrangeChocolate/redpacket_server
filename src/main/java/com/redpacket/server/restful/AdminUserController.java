package com.redpacket.server.restful;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.redpacket.server.common.CustomErrorType;
import com.redpacket.server.common.Utils;
import com.redpacket.server.model.AdminUser;
import com.redpacket.server.service.AdminUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@CrossOrigin
@Api(tags={"admin"})
@RestController
@RequestMapping("/api/admin/")
public class AdminUserController {
	
	public static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);
	
	@Autowired
	private AdminUserService adminUserService;
	
	@ApiOperation(value = "List all admin", notes = "List all admin in json response", authorizations={@Authorization(value = "token")})
	@ApiImplicitParams({ @ApiImplicitParam(name = "username", paramType = "query"),
		@ApiImplicitParam(name = "page", defaultValue="0", paramType = "query"),
		@ApiImplicitParam(name = "size", defaultValue = "10", paramType = "query"),
		@ApiImplicitParam(name = "sort", defaultValue = "updateDate,desc", paramType = "query") })
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<AdminUser>> findAll(HttpServletRequest request, HttpServletResponse response,
			@Spec(path = "username", spec = Like.class) Specification<AdminUser> spec,
        @PageableDefault(size = 1000, sort = "id") Pageable pageable) {
		Page<AdminUser> page = adminUserService.findAll(spec, pageable);
		List<AdminUser> adminUsers = page.getContent();
		Utils.setExtraHeader(response, page);
		return new ResponseEntity<List<AdminUser>>(adminUsers, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Get a admin", notes = "Get a admin by id with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<AdminUser> findById(@PathVariable Long id) {
		AdminUser adminUser = adminUserService.findById(id);
		if(adminUser == null) {
            logger.error("AdminUser with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<AdminUser>(adminUser, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Create a admin", notes = "Create a admin with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<AdminUser> create(@RequestBody AdminUser adminUser) {
		if(StringUtils.isEmpty(adminUser.getUsername()) || StringUtils.isEmpty(adminUser.getPassword())) {
            logger.error("AdminUser username or password is empty.");
            return new ResponseEntity(new CustomErrorType("AdminUser username or password is empty."), HttpStatus.BAD_REQUEST);
		}
		// delete the id safely
		adminUser.setId(null);
		AdminUser pesistedAdminUser = adminUserService.saveOrUpdate(adminUser);
		return new ResponseEntity<AdminUser>(pesistedAdminUser, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Update a admin", notes = "Update a admin with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<AdminUser> update(@PathVariable("id") Long id, @RequestBody AdminUser adminUser) {
		AdminUser pesistedAdminUser = adminUserService.findById(id);
		if(pesistedAdminUser == null) {
            logger.error("AdminUser with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		pesistedAdminUser.setUsername(adminUser.getUsername());
		pesistedAdminUser.setPassword(adminUser.getPassword());
		pesistedAdminUser.setRoles(adminUser.getRoles());
		pesistedAdminUser = adminUserService.saveOrUpdate(pesistedAdminUser);
		return new ResponseEntity<AdminUser>(pesistedAdminUser, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Delete a admin", notes = "Delete a admin by id with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<AdminUser> delete(@PathVariable Long id) {
		AdminUser adminUser = adminUserService.findById(id);
		if(adminUser == null) {
            logger.error("AdminUser with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("AdminUser with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		adminUserService.delete(adminUser);
		return new ResponseEntity<AdminUser>(adminUser, HttpStatus.OK);
	}
}

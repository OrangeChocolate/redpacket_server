package com.redpacket.server.restful;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.redpacket.server.ApplicationProperties;
import com.redpacket.server.common.CustomErrorType;
import com.redpacket.server.common.Utils;
import com.redpacket.server.model.Product;
import com.redpacket.server.service.ProductDetailService;
import com.redpacket.server.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@CrossOrigin
@Api(tags={"product"})
@RestController
@RequestMapping("/api/product/")
public class ProductController {
	
	public static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;

	@Autowired
	private ProductDetailService productDetailService;
	
	@Autowired
	private ApplicationProperties applicationProperties;
	
	@ApiOperation(value = "List all product", notes = "List all product in json response", authorizations={@Authorization(value = "token")})
	@ApiImplicitParams({ @ApiImplicitParam(name = "productName", paramType = "query"),
			@ApiImplicitParam(name = "enable", dataType="boolean", paramType = "query"),
			@ApiImplicitParam(name = "page", defaultValue="0", paramType = "query"),
			@ApiImplicitParam(name = "size", defaultValue = "10", paramType = "query"),
			@ApiImplicitParam(name = "sort", defaultValue = "updateDate,desc", paramType = "query") })
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Product>> get(HttpServletRequest request, HttpServletResponse response, 
			@And({@Spec(path = "productName", spec = Like.class), @Spec(path = "enable", spec = Equal.class)}) Specification<Product> spec,
	        @PageableDefault(size = 1000, sort = "id") Pageable pageable) {
		Page<Product> page = productService.findAll(spec, pageable);
		List<Product> products = page.getContent();
		Utils.setExtraHeader(response, page);
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Get a product", notes = "Get a product by id with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Product> get(@PathVariable Long id) {
		Product product = productService.findById(id);
		if(product == null) {
            logger.error("Product with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Product with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Create a product", notes = "Create a product with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Product> create(@RequestBody Product product) {
		if(StringUtils.isEmpty(product.getName()) || product.getAmount() == 0) {
            logger.error("Product name or amount is empty.");
            return new ResponseEntity(new CustomErrorType("Product name or amount is empty."), HttpStatus.BAD_REQUEST);
		}
		// delete the id safely
		product.setId(null);
		Product pesistedProduct = productService.saveOrUpdate(product);
		return new ResponseEntity<Product>(pesistedProduct, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Update a product", notes = "Update a product with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Product> update(@PathVariable("id") Long id, @RequestBody Product product) {
		Product pesistedProduct = productService.findById(id);
		if(pesistedProduct == null) {
            logger.error("Product with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Product with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		pesistedProduct.setName(product.getName());
		pesistedProduct.setAmount(product.getAmount());
		pesistedProduct.setAllowSellCities(product.getAllowSellCities());
		pesistedProduct = productService.saveOrUpdate(pesistedProduct);
		return new ResponseEntity<Product>(pesistedProduct, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Delete a product", notes = "Delete a product by id with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Product> delete(@PathVariable Long id) {
		Product product = productService.findById(id);
		if(product == null) {
            logger.error("Product with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Product with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		productService.delete(product);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@ApiOperation(value = "Get a product detail scan url file", notes = "Get a product detail scan url", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "scanUrlFile/{id}", method = RequestMethod.GET)
	public ResponseEntity<Resource> getProductDetailScanUrlFile(@PathVariable Long id) {
		List<String> productScanPath = productDetailService.getProductScanPath(id);
		String fileContents = productScanPath.stream().map(path -> {
			return applicationProperties.getDomain() + path;
		}).collect(Collectors.joining("\r\n"));
		// https://stackoverflow.com/questions/35680932/download-a-file-from-spring-boot-rest-service
//		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		ByteArrayResource resource = null;
		try {
			resource = new ByteArrayResource(fileContents.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
            logger.error("Product getProductDetailScanUrlFile error with {}", e.getMessage());
			e.printStackTrace();
		}

	    return ResponseEntity.ok()
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource);
	}
}

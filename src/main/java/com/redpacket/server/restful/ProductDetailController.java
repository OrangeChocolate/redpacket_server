package com.redpacket.server.restful;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.redpacket.server.ApplicationProperties;
import com.redpacket.server.common.CustomErrorType;
import com.redpacket.server.model.ProductDetail;
import com.redpacket.server.model.SimpleEnableItem;
import com.redpacket.server.service.ProductDetailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin
@Api(tags={"productDetail"})
@RestController
@RequestMapping("/api/productDetail/")
public class ProductDetailController {
	
	public static final Logger logger = LoggerFactory.getLogger(ProductDetailController.class);
	
	@Autowired
	private ProductDetailService productDetailService;
	
	@Autowired
	private ApplicationProperties applicationProperties;
	
	@ApiOperation(value = "List all productDetail", notes = "List all productDetail in json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<ProductDetail>> get() {
		List<ProductDetail> productDetails = productDetailService.findAll();
		return new ResponseEntity<List<ProductDetail>>(productDetails, HttpStatus.OK);
	}
	
	@ApiOperation(value = "List all productDetail", notes = "List all productDetail in json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{product_id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<ProductDetail>> getByProductId(@PathVariable(name="product_id") Long productId) {
		List<ProductDetail> productDetails = productDetailService.findByProductId(productId);
		return new ResponseEntity<List<ProductDetail>>(productDetails, HttpStatus.OK);
	}
	
	@ApiOperation(value = "List all productDetail scan path", notes = "List all productDetail scan path in json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/scanPath/{product_id}", method = RequestMethod.GET, produces = "application/json")
	public List<String> getScanPathByProductId(@PathVariable(name="product_id") Long productId) {
		List<String> productScanPath = productDetailService.getProductScanPath(productId);
		return productScanPath;
	}
	
	@ApiOperation(value = "List all productDetail scan url", notes = "List all productDetail scan url in json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/scanUrl/{product_id}", method = RequestMethod.GET, produces = "application/json")
	public List<String> getScanUrlByProductId(@PathVariable(name="product_id") Long productId) {
		List<String> productScanPath = productDetailService.getProductScanPath(productId);
		List<String> productScanUrl = productScanPath.stream().map(path -> {
			return applicationProperties.getDomain() + path;
		}).collect(Collectors.toList());
		return productScanUrl;
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Get a productDetail", notes = "Get a productDetail by id with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{product_detail_id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ProductDetail> getByProductDetailId(@PathVariable(name="product_detail_id") Long productDetailId) {
		ProductDetail productDetail = productDetailService.findByProductDetailId(productDetailId);
		if(productDetail == null) {
            logger.error("ProductDetail with product_detail_id {} not found.", productDetailId);
            return new ResponseEntity(new CustomErrorType("ProductDetail with product_detail_id " + productDetailId + " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ProductDetail>(productDetail, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Get a productDetail scan path", notes = "Get a productDetail scan path by id with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/scanPath/{product_id}/{product_detail_num}", method = RequestMethod.GET, produces = "application/json")
	public String getScanPathByProductIdAndProductDetailNum(@PathVariable(name="product_id") Long productId, @PathVariable(name="product_detail_num") Long productDetailNum) {
		ProductDetail productDetail = productDetailService.findByProductIdAndProductDetailNum(productId, productDetailNum);
		if(productDetail == null) {
            logger.error("ProductDetail with product_id {} and product_detail_num {} not found.", productId, productDetailNum);
            return null;
		}
		String productScanPath = productDetailService.getProductScanPath(productId, productDetailNum);
		return productScanPath;
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Get a productDetail scan url", notes = "Get a productDetail scan url by id with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/scanUrl/{product_id}/{product_detail_num}", method = RequestMethod.GET, produces = "application/json")
	public String getScanUrlByProductIdAndProductDetailNum(@PathVariable(name="product_id") Long productId, @PathVariable(name="product_detail_num") Long productDetailNum) {
		ProductDetail productDetail = productDetailService.findByProductIdAndProductDetailNum(productId, productDetailNum);
		if(productDetail == null) {
            logger.error("ProductDetail with product_id {} and product_detail_num {} not found.", productId, productDetailNum);
            return null;
		}
		String productScanUrl = applicationProperties.getDomain() + productDetailService.getProductScanPath(productId, productDetailNum);
		return productScanUrl;
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Create a productDetail", notes = "Create a productDetail with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<ProductDetail> create(@RequestBody ProductDetail productDetail) {
		ProductDetail pesistedProductDetail = productDetailService.saveOrUpdate(productDetail);
		return new ResponseEntity<ProductDetail>(pesistedProductDetail, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Update a productDetail", notes = "Update a productDetail with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{product_detail_id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<ProductDetail> update(@PathVariable(name="product_detail_id") Long productDetailId, @RequestBody ProductDetail productDetail) {
		ProductDetail pesistedProductDetail = productDetailService.findByProductDetailId(productDetailId);
		if(productDetail == null) {
            logger.error("ProductDetail with product_detail_id {} not found.", productDetailId);
            return new ResponseEntity(new CustomErrorType("ProductDetail with product_detail_id " + productDetailId + " not found"), HttpStatus.NOT_FOUND);
		}
		pesistedProductDetail = productDetailService.saveOrUpdate(productDetail);
		return new ResponseEntity<ProductDetail>(pesistedProductDetail, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Delete a productDetail", notes = "Delete a productDetail by id with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{product_detail_id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<ProductDetail> delete(@PathVariable(name="product_detail_id") Long product_detail_id) {
		ProductDetail pesistedProductDetail = productDetailService.findByProductDetailId(product_detail_id);
		if(pesistedProductDetail == null) {
            logger.error("ProductDetail with id {} not found.", product_detail_id);
            return new ResponseEntity(new CustomErrorType("ProductDetail with id " + product_detail_id +  " not found"), HttpStatus.NOT_FOUND);
		}
		productDetailService.delete(product_detail_id);
		return new ResponseEntity<ProductDetail>(pesistedProductDetail, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Enable a list of product", notes = "Enable a list of product with json array", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/updateEnable/{enable}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<List<ProductDetail>> updateEnable(@PathVariable(name="enable") boolean enable, @RequestBody List<Long> productDetailIds) {
		List<ProductDetail> updatedProductDetails = productDetailService.batchEable(productDetailIds, enable);
		return new ResponseEntity<List<ProductDetail>>(updatedProductDetails, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Enable a list of product", notes = "Enable a list of product with json array", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/updateEnable/", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<List<ProductDetail>> updateEnable(@RequestBody List<SimpleEnableItem> productDetailIds) {
		List<ProductDetail> updatedProductDetails = productDetailService.batchMixedEable(productDetailIds);
		return new ResponseEntity<List<ProductDetail>>(updatedProductDetails, HttpStatus.OK);
	}
}

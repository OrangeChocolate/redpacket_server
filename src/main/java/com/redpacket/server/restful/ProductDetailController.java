package com.redpacket.server.restful;

import java.util.List;

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

import com.redpacket.server.common.CustomErrorType;
import com.redpacket.server.common.SwaggerSecurityDefinition;
import com.redpacket.server.model.ProductDetail;
import com.redpacket.server.model.ProductDetail.ProductDetailPrimaryKey;
import com.redpacket.server.service.ProductDetailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin
@Api(tags={"productDetail"})
@RestController
@RequestMapping("/api/productDetail/")
public class ProductDetailController implements SwaggerSecurityDefinition {
	
	public static final Logger logger = LoggerFactory.getLogger(ProductDetailController.class);
	
	@Autowired
	private ProductDetailService productDetailService;
	
	@ApiOperation(value = "List all productDetail", notes = "List all productDetail in json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<ProductDetail>> get() {
		List<ProductDetail> productDetails = productDetailService.findAll();
		return new ResponseEntity<List<ProductDetail>>(productDetails, HttpStatus.OK);
	}
	
	@ApiOperation(value = "List all productDetail", notes = "List all productDetail in json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{product_id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<ProductDetail>> get(@PathVariable(name="product_id") Long productId) {
		List<ProductDetail> productDetails = productDetailService.findByProductId(productId);
		return new ResponseEntity<List<ProductDetail>>(productDetails, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Get a productDetail", notes = "Get a productDetail by id with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{product_id}/{product_detail_id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ProductDetail> get(@PathVariable(name="product_id") Long productId, @PathVariable(name="product_detail_id") Long productDetailId) {
		ProductDetail productDetail = productDetailService.findByProductIdAndProductDetailId(productId, productDetailId);
		if(productDetail == null) {
            logger.error("ProductDetail with product_id {} and product_detail_id {} not found.", productId, productDetailId);
            return new ResponseEntity(new CustomErrorType("ProductDetail with product_id " + productId + " and product_detail_id " + productDetailId + " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ProductDetail>(productDetail, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Create a productDetail", notes = "Create a productDetail with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{product_id}/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<ProductDetail> create(@RequestBody ProductDetail productDetail) {
		ProductDetail pesistedProductDetail = productDetailService.saveOrUpdate(productDetail);
		return new ResponseEntity<ProductDetail>(pesistedProductDetail, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Update a productDetail", notes = "Update a productDetail with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{product_id}/{product_detail_id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public ResponseEntity<ProductDetail> update(@PathVariable(name="product_id") Long productId, @PathVariable(name="product_detail_id") Long productDetailId, @RequestBody ProductDetail productDetail) {
		ProductDetail pesistedProductDetail = productDetailService.findByProductIdAndProductDetailId(productId, productDetailId);
		if(productDetail == null) {
            logger.error("ProductDetail with product_id {} and product_detail_id {} not found.", productId, productDetailId);
            return new ResponseEntity(new CustomErrorType("ProductDetail with product_id " + productId + " and product_detail_id " + productDetailId + " not found"), HttpStatus.NOT_FOUND);
		}
		pesistedProductDetail = productDetailService.saveOrUpdate(productDetail);
		return new ResponseEntity<ProductDetail>(pesistedProductDetail, HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Delete a productDetail", notes = "Delete a productDetail by id with json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/{product_id}/{product_detail_id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<ProductDetail> delete(@PathVariable(name="product_id") Long productId, @PathVariable(name="product_detail_id") Long productDetailId) {
		ProductDetail pesistedProductDetail = productDetailService.findByProductIdAndProductDetailId(productId, productDetailId);
		if(pesistedProductDetail == null) {
            logger.error("ProductDetail with product_id {} and product_detail_id {} not found.", productId, productDetailId);
            return new ResponseEntity(new CustomErrorType("ProductDetail with product_id " + productId + " and product_detail_id " + productDetailId + " not found"), HttpStatus.NOT_FOUND);
		}
		ProductDetailPrimaryKey productDetailPrimaryKey = new ProductDetail.ProductDetailPrimaryKey(productId, productDetailId);
		productDetailService.delete(productDetailPrimaryKey);
		return new ResponseEntity<ProductDetail>(pesistedProductDetail, HttpStatus.OK);
	}
}

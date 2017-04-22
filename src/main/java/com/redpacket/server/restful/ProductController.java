package com.redpacket.server.restful;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.redpacket.server.common.SwaggerSecurityDefinition;
import com.redpacket.server.model.Product;
import com.redpacket.server.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin
@Api(tags={"product"})
@RestController
@RequestMapping("/api/product/")
public class ProductController implements SwaggerSecurityDefinition {
	
	public static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	
	@ApiOperation(value = "List all product", notes = "List all product in json response", authorizations={@Authorization(value = "token")})
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Product>> get() {
		List<Product> products = productService.findAll();
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
}

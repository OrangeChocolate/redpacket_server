package com.redpacket.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.redpacket.server.ApplicationProperties;
import com.redpacket.server.common.Utils;
import com.redpacket.server.model.AdminUser;
import com.redpacket.server.model.Product;
import com.redpacket.server.model.ProductDetail;
import com.redpacket.server.model.SimpleEnableItem;
import com.redpacket.server.repository.ProductDetailRepository;


@Service
public class ProductDetailService {

	public static final Logger logger = LoggerFactory.getLogger(ProductDetailService.class);
	
	@Autowired
	private ProductDetailRepository productDetailRepository;
	
	@Autowired
	private ApplicationProperties applicationProperties;
	
	public List<ProductDetail> findAll() {
		return productDetailRepository.findAllByOrderByProductIdAscProductDetailNumAsc();
	}

	public Page<ProductDetail> findAll(Specification<ProductDetail> spec, Pageable pageable) {
		return productDetailRepository.findAll(spec, pageable);
	}

	public ProductDetail saveOrUpdate(ProductDetail productDetail) {
		return productDetailRepository.save(productDetail);
	}

	public void delete(ProductDetail productDetail) {
		productDetailRepository.delete(productDetail);
	}

	public List<ProductDetail> findByProductId(Long productId) {
		return productDetailRepository.findByProductIdOrderByProductDetailNumAsc(productId);
	}

	public ProductDetail findByProductIdAndProductDetailNum(Long productId, Long productDetailNum) {
		return productDetailRepository.findByProductIdAndProductDetailNum(productId, productDetailNum);
	}

	public void delete(Long id) {
		productDetailRepository.delete(id);
	}

	public ProductDetail findByProductDetailId(Long productDetailId) {
		return productDetailRepository.findOne(productDetailId);
	}

	public List<ProductDetail> batchEable(List<Long> productDetailIds, boolean enable) {
		List<ProductDetail> productDetails = productDetailRepository.findByIdIn(productDetailIds);
		productDetails.stream().forEach(productDetail -> productDetail.setEnable(enable));
		List<ProductDetail> updatedProductDetails = productDetailRepository.save(productDetails);
		return updatedProductDetails;
	}

	public List<ProductDetail> batchMixedEable(List<SimpleEnableItem> productEnableItems) {
		List<Long> productDetailIds = productEnableItems.stream().map(simpleEnableItem -> {
			return simpleEnableItem.getId();
		}).collect(Collectors.toList());
		List<Boolean> productDetailEnables = productEnableItems.stream().map(simpleEnableItem -> {
			return simpleEnableItem.isEnable();
		}).collect(Collectors.toList());
		List<ProductDetail> productDetails = productDetailRepository.findByIdIn(productDetailIds);
		for(int i = 0; i < productDetails.size(); i ++) {
			ProductDetail productDetail = productDetails.get(i);
			boolean productDetailEnable = productDetailEnables.get(i);
			productDetail.setEnable(productDetailEnable);
		}
		List<ProductDetail> updatedProductDetails = productDetailRepository.save(productDetails);
		return updatedProductDetails;
	}
	
	public List<String> getProductScanPath(Long productIdForLookup) {
		List<String> productScanUrlPaths = new ArrayList<String>();
		productDetailRepository.findByProductIdOrderByProductDetailNumAsc(productIdForLookup).stream().forEach(productDetail -> {
			long productId = productDetail.getProductId();
			long productDetailNum = productDetail.getProductDetailNum();
			productScanUrlPaths.add(Utils.getProductScanUrlPath(applicationProperties.getHash_secret(), productId, productDetailNum));
		});
		return productScanUrlPaths;
	}

	public String getProductScanPath(Long productId, Long productDetailNum) {
		ProductDetail productDetail = productDetailRepository.findByProductIdAndProductDetailNum(productId, productDetailNum);
		if(productDetail == null) {
			logger.info("productDetial with productId {}, productDetailNum {} not found", productId, productDetailNum);
			return "product detail not found!";
		}
		String scanUrlPath = Utils.getProductScanUrlPath(applicationProperties.getHash_secret(), productId, productDetailNum);
		return scanUrlPath;
	}
	
	public boolean checkProductScanPath(String productScanUrlPath, boolean checkHash) {
		String[] split = productScanUrlPath.split("/");
		if(split.length < 4) {
			logger.info("invalide productScanUrlPath: {}", productScanUrlPath);
			return false;
		}
		String productIdString = split[1];
		String productDetailNumString = split[2];
		String hash = split[3];
		long productId = Long.parseLong(Utils._62_10(productIdString));
		long productDetailNum = Long.parseLong(Utils._62_10(productDetailNumString));
		ProductDetail productDetail = productDetailRepository.findByProductIdAndProductDetailNum(productId, productDetailNum);
		if(productDetail == null) {
			logger.info("invalide productScanUrlPath: {}, product detail not found", productScanUrlPath);
			return false;
		}
		String productIdAndProductDetailNumString = productIdString + productDetailNumString;
		String CalculateHash = Utils.calculateSaltHash(applicationProperties.getHash_secret(), productIdAndProductDetailNumString);
		return hash.equals(CalculateHash);
	}

}

package com.redpacket.server.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redpacket.server.model.Product;
import com.redpacket.server.model.ProductDetail;
import com.redpacket.server.model.SimpleEnableItem;
import com.redpacket.server.repository.ProductDetailRepository;

@Service
public class ProductDetailService {
	
	@Autowired
	private ProductDetailRepository productDetailRepository;
	
	public List<ProductDetail> findAll() {
		return productDetailRepository.findAll();
	}

	public ProductDetail saveOrUpdate(ProductDetail productDetail) {
		return productDetailRepository.save(productDetail);
	}

	public void delete(ProductDetail productDetail) {
		productDetailRepository.delete(productDetail);
	}

	public List<ProductDetail> findByProductId(Long productId) {
		return productDetailRepository.findByProductId(productId);
	}

	public ProductDetail findByProductIdAndProductDetailNum(Long productId, Long productDetailId) {
		return productDetailRepository.findByProductIdAndProductDetailNum(productId, productDetailId);
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

}

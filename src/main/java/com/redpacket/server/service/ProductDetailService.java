package com.redpacket.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redpacket.server.model.ProductDetail;
import com.redpacket.server.model.ProductDetail.ProductDetailPrimaryKey;
import com.redpacket.server.repository.ProductDetailRepository;

@Service
public class ProductDetailService {
	
	@Autowired
	private ProductDetailRepository productDetailRepository;
	
	public List<ProductDetail> findAll() {
		return productDetailRepository.findAll();
	}
	
	public ProductDetail findById(ProductDetailPrimaryKey productDetailPrimaryKey) {
		return productDetailRepository.findOne(productDetailPrimaryKey);
	}

	public ProductDetail saveOrUpdate(ProductDetail productDetail) {
		return productDetailRepository.save(productDetail);
	}

	public void delete(ProductDetail productDetail) {
		productDetailRepository.delete(productDetail);
	}

	public List<ProductDetail> findByProductId(Long productId) {
		return productDetailRepository.findByProductDetailPrimaryKeyProductId(productId);
	}

	public ProductDetail findByProductIdAndProductDetailId(Long productId, Long productDetailId) {
		return productDetailRepository.findByProductDetailPrimaryKeyProductIdAndProductDetailPrimaryKeyProductDetailId(productId, productDetailId);
	}

	public void delete(ProductDetailPrimaryKey productDetailPrimaryKey) {
		productDetailRepository.delete(productDetailPrimaryKey);
	}

}

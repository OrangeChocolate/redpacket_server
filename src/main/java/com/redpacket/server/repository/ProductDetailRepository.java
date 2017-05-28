package com.redpacket.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redpacket.server.model.ProductDetail;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

	List<ProductDetail> findAllByOrderByProductIdAscProductDetailNumAsc();

	List<ProductDetail> findByProductIdOrderByProductDetailNumAsc(Long productId);

	ProductDetail findByProductIdAndProductDetailNum(Long productId, Long productDetailNum);

	List<ProductDetail> findByIdIn(List<Long> productDetailIds);

}

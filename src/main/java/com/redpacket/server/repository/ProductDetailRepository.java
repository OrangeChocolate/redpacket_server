package com.redpacket.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redpacket.server.model.Product;
import com.redpacket.server.model.ProductDetail;
import com.redpacket.server.model.ProductDetail.ProductDetailPrimaryKey;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, ProductDetailPrimaryKey> {

	List<ProductDetail> findByProductId(Long productId);

	ProductDetail findByProductIdAndProductDetailId(Long productId, Long productDetailId);

}

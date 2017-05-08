package com.redpacket.server.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redpacket.server.model.Product;
import com.redpacket.server.model.ProductDetail;
import com.redpacket.server.repository.ProductDetailRepository;
import com.redpacket.server.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductDetailRepository productDetailRepository;
	
	public List<Product> findAll() {
		return productRepository.findAll();
	}
	
	public Product findById(long id) {
		return productRepository.findOne(id);
	}

	public Product saveOrUpdate(Product product) {
		Product persistedProduct = productRepository.save(product);
		int amount = product.getAmount();
		Set<ProductDetail> productDetails = new TreeSet<>();
		for(int i = 0; i < amount; i ++) {
			productDetails.add(new ProductDetail(product, (long)i + 1));
		}
		persistedProduct.setProductDetails(productDetails);
		return productRepository.save(persistedProduct);
	}

	public void delete(Product product) {
		productRepository.delete(product);
	}

}

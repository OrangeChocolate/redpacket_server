package com.redpacket.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redpacket.server.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	public Product findByName(String string);

}

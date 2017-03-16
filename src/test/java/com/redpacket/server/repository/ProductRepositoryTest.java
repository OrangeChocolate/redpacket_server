package com.redpacket.server.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.redpacket.server.model.City;
import com.redpacket.server.model.Product;

// see https://github.com/spring-projects/spring-boot/blob/master/spring-boot-samples/spring-boot-sample-data-jpa/src/test/java/sample/data/jpa/service/CityRepositoryIntegrationTests.java
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductRepositoryTest {
	
	@Autowired
	ProductRepository productRepository;
	@Autowired
	CityRepository cityRepository;
	
	public void setup() {
		productRepository.deleteAllInBatch();
		cityRepository.deleteAllInBatch();
	}

	@Test
	public void test_a_insertCity() {
		
		// http://stackoverflow.com/questions/3413092/difference-between-setup-and-setupbeforeclass
		setup();
		City city1 = new City("昆明");
		City city2 = new City("北京");
		cityRepository.save(city1);
		cityRepository.save(city2);
		List<City> cities = cityRepository.findAll();
		assertThat(cities.size()).isEqualTo(2);
	}
	
	@Test
	public void test_b_insertProduct() {
		Product product = new Product("product1", 1000, "sample product");
		productRepository.save(product);
		Product savedProduct = productRepository.findByName("product1");
		assertThat(savedProduct).isNotNull();
	}
	
	@Test
	@Transactional
	public void test_c_findRelatedCity() {
		Product product = productRepository.findByName("product1");
		List<City> cityList = cityRepository.findAll();
		product.setAllowSellCities(new HashSet<>(cityList));
		productRepository.save(product);
		
		Product savedProduct = productRepository.findByName("product1");
		// see http://stackoverflow.com/questions/11746499/solve-failed-to-lazily-initialize-a-collection-of-role-exception
//		Hibernate.initialize(savedProduct.getAllowSellCities());
		Set<City> allowSellCities = savedProduct.getAllowSellCities();
		System.out.println(product);
		for(City city : allowSellCities) {
			System.out.println(city);
		}
		assertThat(savedProduct.getAllowSellCities().size()).isEqualTo(2);
	}
}

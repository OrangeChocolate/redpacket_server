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
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.redpacket.server.model.City;
import com.redpacket.server.model.Product;

// see https://github.com/spring-projects/spring-boot/blob/master/spring-boot-samples/spring-boot-sample-data-jpa/src/test/java/sample/data/jpa/service/CityRepositoryIntegrationTests.java
@RunWith(SpringRunner.class)
//@SpringBootTest
// see http://www.lucassaldanha.com/unit-and-integration-tests-in-spring-boot/
@DataJpaTest
// use configured mysql database for testing
//@AutoConfigureTestDatabase(replace=Replace.NONE)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductRepositoryTest {
	
	@Autowired
	ProductRepository productRepository;
	@Autowired
	CityRepository cityRepository;

	private void insertCity() {
		City city1 = new City("昆明");
		City city2 = new City("北京");
		cityRepository.save(city1);
		cityRepository.save(city2);
	}

	private void insertProduct() {
		Product product = new Product("product1", 1000, "sample product", null);
		productRepository.save(product);
	}

	private void insertCityToProduct() {
		Product product = productRepository.findByName("product1");
		List<City> cityList = cityRepository.findAll();
		product.setAllowSellCities(new HashSet<>(cityList));
		productRepository.save(product);
	}

	@Test
	public void test_a_insertCity() {
		insertCity();
		List<City> cities = cityRepository.findAll();
		assertThat(cities.size()).isEqualTo(2);
	}
	
	@Test
	public void test_b_insertProduct() {
		insertProduct();
		Product savedProduct = productRepository.findByName("product1");
		assertThat(savedProduct).isNotNull();
	}
	
	@Test
	public void test_c_findRelatedCity() {
		insertCity();
		insertProduct();
		insertCityToProduct();
		Product savedProduct = productRepository.findByName("product1");
		// see http://stackoverflow.com/questions/11746499/solve-failed-to-lazily-initialize-a-collection-of-role-exception
		Hibernate.initialize(savedProduct.getAllowSellCities());
		Set<City> allowSellCities = savedProduct.getAllowSellCities();
		System.out.println(savedProduct);
		for(City city : allowSellCities) {
			System.out.println(city);
		}
		assertThat(savedProduct.getAllowSellCities().size()).isEqualTo(2);
	}
}

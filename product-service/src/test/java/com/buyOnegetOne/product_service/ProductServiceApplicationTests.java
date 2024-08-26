package com.buyOnegetOne.product_service;

import io.restassured.RestAssured;
//import org.hamcrest.Matcher;
import io.restassured.parsing.Parser;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
//import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
//import org.testcontainers.containers.MongoDBContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

//	// Create a MongoDB container
//	@ServiceConnection
//	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

	@LocalServerPort
	private Integer port;

	// Start the container before the tests
	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

//	static {
//		mongoDBContainer.start();
//	}

	@Test
	void ShouldCreateProduct() {
		String requestBody = """
				{
					"name": "Product 1",
					"description": "Product 1 description",
					"price": 1000
					
				}
				""";
		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/product/addProduct")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("Product 1"))
				.body("description", Matchers.equalTo("Product 1 description"))
				.body("price", Matchers.equalTo("1000"))
				.body("message",Matchers.equalTo("Product saved successfully"));  // Check the plain text response

	}



}

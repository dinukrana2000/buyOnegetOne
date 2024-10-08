package com.buyOnegetOne.order_service;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;

import static org.hamcrest.MatcherAssert.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTests {

//	@ServiceConnection
//	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.0.39");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

//	static {
//		mySQLContainer.start();
//	}

	@Test
	void shouldSubmitOrder() {
		String submitOrderJson = """
                {
                      "orderLineItemsDtoList": [
				                                      {
				                                          "skuCode": "iphone_15",
				                                          "price": 1000,
				                                          "quantity": 1
				                                      }
				                                  ]
                }
                """;


		var responseBodyString = RestAssured.given()
				.contentType("application/json")
				.body(submitOrderJson)
				.when()
				.post("/api/order/addOrder")
				.then()
				.log().all()
				.statusCode(201)
				.extract()
				.body().asString();

		assertThat(responseBodyString, Matchers.is("Order saved successfully"));
	}

}

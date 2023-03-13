package com.hendisantika;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsInAnyOrder;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-testcontainers-demo2
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/13/23
 * Time: 08:23
 * To change this template use File | Settings | File Templates.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PersonResourceTests {

    @Autowired
    WebApplicationContext wac;

    @Test
    void testFindAllReturnsName() {
        given()
                .webAppContextSetup(wac)
                .when()
                .get("/persons")
                .then()
                .status(HttpStatus.OK)
                .body(
                        "_embedded.persons.firstName", containsInAnyOrder("Uzumaki", "Haruno", "Uchiha", "Hatake",
                                "Asuma"),
                        "_embedded.persons.lastName", containsInAnyOrder("Naruto", "Sakura", "Sasuke", "Kakashi",
                                "Sarutobi")
                );
        // @formatter:on
    }
}

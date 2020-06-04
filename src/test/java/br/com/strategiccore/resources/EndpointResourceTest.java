package br.com.strategiccore.resources;

import br.com.strategiccore.entities.Endpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class EndpointResourceTest {

    static final int validId = 1;
    static final Endpoint endpoint = new Endpoint();

    @Test
    @Order(1)
    public void testEndpointCreate() {
        endpoint.setName("name");
        endpoint.setUrl("url");

        given()
                .when()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(endpoint)
                .post("/endpoint")
                .then()
                .statusCode(200)
                .body("id", equalTo(validId))
                .and().body("name", equalTo(endpoint.getName()))
                .and().body("url", equalTo(endpoint.getUrl()))
                .and().body("web", equalTo(endpoint.isWeb()));
    }

    @Test
    @Order(2)
    public void testEndpointUpdateSuccess() {
        endpoint.setName("new_name");
        endpoint.setUrl("new_url");
        endpoint.setWeb(false);

        given()
                .when()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(endpoint)
                .pathParam("id", validId)
                .put("/endpoint/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(validId))
                .and().body("name", equalTo(endpoint.getName()))
                .and().body("url", equalTo(endpoint.getUrl()))
                .and().body("web", equalTo(endpoint.isWeb()));
    }

    @Test
    @Order(3)
    public void testEndpointUpdateError() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(endpoint)
                .pathParam("id", validId + 999)
                .put("/endpoint/{id}")
                .then()
                .statusCode(500);
    }

    @Test
    @Order(4)
    public void testEndpointGetByIdSuccess() {
        given()
                .when()
                .accept(ContentType.JSON)
                .pathParam("id", validId)
                .get("/endpoint/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(validId))
                .and().body("name", equalTo(endpoint.getName()))
                .and().body("url", equalTo(endpoint.getUrl()))
                .and().body("web", equalTo(endpoint.isWeb()));
    }

    @Test
    @Order(5)
    public void testEndpointGetByIdError() {
        given()
                .when()
                .accept(ContentType.JSON)
                .pathParam("id", validId + 999)
                .get("/endpoint/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(6)
    public void testEndpointGelAllSuccess() {
        given()
                .when()
                .accept(ContentType.JSON)
                .get("/endpoint")
                .then()
                .statusCode(200)
                .body("$.size()", is(1));
    }

    @Test
    @Order(7)
    public void testEndpointDeleteSuccess() {
        given()
                .when()
                .accept(ContentType.JSON)
                .pathParam("id", validId)
                .delete("/endpoint/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(validId))
                .and().body("name", equalTo(endpoint.getName()))
                .and().body("url", equalTo(endpoint.getUrl()))
                .and().body("web", equalTo(endpoint.isWeb()));
    }

    @Test
    @Order(8)
    public void testEndpointGelAllEmpty() {
        given()
                .when()
                .accept(ContentType.JSON)
                .get("/endpoint")
                .then()
                .statusCode(200)
                .body("$.size()", is(0));
    }
}

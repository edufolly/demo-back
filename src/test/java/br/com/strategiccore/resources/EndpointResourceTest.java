package br.com.strategiccore.resources;

import br.com.strategiccore.entities.Endpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

/**
 * @author Eduardo Folly
 */
@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class EndpointResourceTest {

    static final int validId = 101;
    static final int invalidId = 999;
    static final int perPage = 10;
    static final long syncTimestamp = 0;
    static final Endpoint endpoint = new Endpoint();
    static long testStart;

    @BeforeAll
    public static void testEndpointInit() {
        testStart = new Date().getTime();
    }

    @Test
    @Order(1)
    public void testEndpointSyncCountInitialSuccess() {
        given()
                .when()
                .accept(ContentType.TEXT)
                .queryParam("t", syncTimestamp)
                .get("/endpoint/sync/count")
                .then()
                .statusCode(200)
                .contentType(ContentType.TEXT)
                .body(is(String.valueOf(validId - 1)));
    }

    @Test
    @Order(2)
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
                .contentType(ContentType.JSON)
                .body("id", equalTo(validId))
                .and().body("name", equalTo(endpoint.getName()))
                .and().body("url", equalTo(endpoint.getUrl()))
                .and().body("web", equalTo(endpoint.isWeb()));
    }

    @Test
    @Order(3)
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
                .contentType(ContentType.JSON)
                .body("id", equalTo(validId))
                .and().body("name", equalTo(endpoint.getName()))
                .and().body("url", equalTo(endpoint.getUrl()))
                .and().body("web", equalTo(endpoint.isWeb()));
    }

    @Test
    @Order(4)
    public void testEndpointUpdateError() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(endpoint)
                .pathParam("id", invalidId)
                .put("/endpoint/{id}")
                .then()
                .statusCode(500);
    }

    @Test
    @Order(5)
    public void testEndpointGetByIdSuccess() {
        given()
                .when()
                .accept(ContentType.JSON)
                .pathParam("id", validId)
                .get("/endpoint/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(validId))
                .and().body("name", equalTo(endpoint.getName()))
                .and().body("url", equalTo(endpoint.getUrl()))
                .and().body("web", equalTo(endpoint.isWeb()));
    }

    @Test
    @Order(6)
    public void testEndpointGetByIdError() {
        given()
                .when()
                .accept(ContentType.JSON)
                .pathParam("id", invalidId)
                .get("/endpoint/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(7)
    public void testEndpointGetAllSuccess() {
        given()
                .when()
                .accept(ContentType.JSON)
                .get("/endpoint")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$.size()", is(100));
    }

    @Test
    @Order(8)
    public void testEndpointGetAllPerPageSuccess() {
        given()
                .when()
                .accept(ContentType.JSON)
                .queryParam("per_page", perPage)
                .get("/endpoint")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$.size()", is(perPage));
    }

    @Test
    @Order(9)
    public void testEndpointSyncCountSuccess() {
        given()
                .when()
                .accept(ContentType.TEXT)
                .queryParam("t", syncTimestamp)
                .get("/endpoint/sync/count")
                .then()
                .statusCode(200)
                .contentType(ContentType.TEXT)
                .body(is(String.valueOf(validId)));
    }

    @Test
    @Order(10)
    public void testEndpointSyncCountError() {
        given()
                .when()
                .accept(ContentType.TEXT)
                .get("/endpoint/sync/count")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(11)
    public void testEndpointSyncCountTestAddedSuccess() {
        given()
                .when()
                .accept(ContentType.TEXT)
                .queryParam("t", testStart)
                .get("/endpoint/sync/count")
                .then()
                .statusCode(200)
                .contentType(ContentType.TEXT)
                .body(is(String.valueOf(1)));
    }

    @Test
    @Order(12)
    public void testEndpointDeleteSuccess() {
        given()
                .when()
                .accept(ContentType.JSON)
                .pathParam("id", validId)
                .delete("/endpoint/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(validId))
                .and().body("name", equalTo(endpoint.getName()))
                .and().body("url", equalTo(endpoint.getUrl()))
                .and().body("web", equalTo(endpoint.isWeb()));
    }

    @Test
    @Order(13)
    public void testEndpointDeleteLastSuccess() {
        given()
                .when()
                .accept(ContentType.JSON)
                .pathParam("id", validId - 1)
                .delete("/endpoint/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(validId - 1));
    }

    @Test
    @Order(14)
    public void testEndpointGetAllAfterDeleteSuccess() {
        given()
                .when()
                .accept(ContentType.JSON)
                .get("/endpoint")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$.size()", is(99));
    }

    @Test
    @Order(15)
    public void testEndpointSyncCountFinalSuccess() {
        given()
                .when()
                .accept(ContentType.TEXT)
                .queryParam("t", syncTimestamp)
                .get("/endpoint/sync/count")
                .then()
                .statusCode(200)
                .contentType(ContentType.TEXT)
                .body(is(String.valueOf(validId)));
    }
}

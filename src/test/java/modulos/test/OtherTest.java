package modulos.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("Validando o móduto test")
public class OtherTest {
    @Test
    @DisplayName("Validar Test")
    public void testPostValidToken() {

        baseURI = "https://dummyjson.com";

        given()
                .when()
                .get("/test")
                .then()
                .assertThat()
                .statusCode(200)
                .body("status", equalTo("ok"))
                .body("method", equalTo("GET"));
    }
}
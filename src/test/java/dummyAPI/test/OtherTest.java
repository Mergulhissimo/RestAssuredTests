package dummyAPI.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
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
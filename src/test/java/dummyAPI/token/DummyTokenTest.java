package dummyAPI.token;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import static io.restassured.RestAssured.*;

public class DummyTokenTest {
    @DisplayName("Pegar Token")
    public static String findDummyToken() {
            String token;
            baseURI = "https://dummyjson.com";
            token = given()
                    .contentType(ContentType.JSON)
                    .body("{\n" +
                            "    \"username\": \"kminchelle\",\n" +
                            "    \"password\": \"0lelplR\"\n" +
                            "}")
                    .when()
                    .post("/auth/login")
                    .then()
                    .extract()
                    .path("token");
                    return token;
    }
}
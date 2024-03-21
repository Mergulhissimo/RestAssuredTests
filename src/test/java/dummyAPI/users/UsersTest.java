package dummyAPI.users;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static dummyAPI.token.DummyTokenTest.findDummyToken;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes de API Rest do Módulo de Usuários")
public class UsersTest {
    @Test
    @DisplayName("Validar Token")
    public void testPostValidToken(){

        baseURI = "https://dummyjson.com";

        String token = findDummyToken();
        assertThat(token, notNullValue());
    }

    @Test
    @DisplayName("Busca Usuário")
    public void testGetValidUser(){

        baseURI = "https://dummyjson.com";

        given()
        .when()
            .get("/users")
        .then()
                .assertThat()
                .statusCode(200)
                .assertThat()
                .body("users.username", notNullValue())
                .body("users.password", notNullValue());
    }
}

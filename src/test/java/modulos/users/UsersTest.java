package modulos.users;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes de API Rest do Módulo de Usuários")
public class UsersTest {
    @Test
    @DisplayName("Validar Token")
    //esse teste está com bug porque era esperado um 201 e veio um 200
    public void testPostValidToken(){

        baseURI = "https://dummyjson.com";

        String token = given()
                    .contentType(ContentType.JSON)
                    .body("{\n" +
                        "    \"username\": \"kminchelle\",\n" +
                        "    \"password\": \"0lelplR\"\n" +
                        "}")
                .when()
                    .post("/auth/login")

                .then()
                    .assertThat()
                    .statusCode(201)
                    .extract()
                    .path("token");
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

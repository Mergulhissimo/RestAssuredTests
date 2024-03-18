package modulos.products;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
@DisplayName("Testes de API Rest do Módulo de Produto")
public class ProductTest {
    private String token;
    private String expiredToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTUsInVzZXJuYW1lIjoia21pbmNoZWxsZSIsImVtYWlsIjoia21pbmNoZWxsZUBxcS5jb20iLCJmaXJzdE5hbWUiOiJKZWFubmUiLCJsYXN0TmFtZSI6IkhhbHZvcnNvbiIsImdlbmRlciI6ImZlbWFsZSIsImltYWdlIjoiaHR0cHM6Ly9yb2JvaGFzaC5vcmcvSmVhbm5lLnBuZz9zZXQ9c2V0NCIsImlhdCI6MTcxMDc4NjAwMiwiZXhwIjoxNzEwNzg5NjAyfQ.tJOumk94qSdYFsjKF8q5Mim6-ijAPZKlHrXWRp_muoE";
    @BeforeEach
    public void beforeEach(){
        baseURI = "https://dummyjson.com";
        this.token = given()
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
    }

    @Test
    @DisplayName("Cadastrar Produto Válido")
    //atualmente essa API está com bug, ja que era esperado um retorno 201 e está vindo um 200
    public void testPostValidProduct(){

        baseURI = "https://dummyjson.com";

        given()
            .contentType(ContentType.JSON)
            .body("{\n" +
                        "    \"title\": \"Perfume Oil\",\n" +
                        "    \"description\": \"Mega Discount, Impression of A...\",\n" +
                        "    \"price\": 13,\n" +
                        "    \"discountPercentage\": 8.4,\n" +
                        "    \"rating\": 4.26,\n" +
                        "    \"stock\": 65,\n" +
                        "    \"brand\": \"Impression of Acqua Di Gio\",\n" +
                        "    \"category\": \"fragrances\",\n" +
                        "    \"thumbnail\": \"https://i.dummyjson.com/data/products/11/thumnail.jpg\"\n" +
                        "}")
        .when()
            .post("/products/add")

        .then()
            .assertThat()
            .statusCode(201)
            .body("title", equalTo("Perfume Oil"))
            .body("price", equalTo("13,"))
            .body("id", notNullValue());
    }
    @Test
    @DisplayName("Busca Produto")
    public void testGetValidProduct(){

        baseURI = "https://dummyjson.com";

        given()
        .when()
            .get("/products")

        .then()
            .assertThat()
            .statusCode(200)
            .body("products.title", notNullValue())
            .body("products.price", notNullValue())
            .body("products.id", notNullValue());
    }
    @Test
    @DisplayName("Busca Produto Valido por ID")
    public void testGetValidProductByID(){

        baseURI = "https://dummyjson.com";


        given()

        .when()
            .get("/products/1")

        .then()
            .assertThat()
            .statusCode(200)
            .body("id", equalTo(1));
    }
    @Test
    @DisplayName("Busca Produto Invalido por ID")
    public void testGetInvalidProductByID(){
        // Dados da API
        baseURI = "https://dummyjson.com";

        given()

        .when()
            .get("/products/0")

        .then()
            .assertThat()
            .statusCode(404)
            .body("message", equalTo("Product with id '0' not found"));
    }

    @Test
    @DisplayName("Busca Produto Valido por Usuário")
    public void testGetValidUserProducts(){

        baseURI = "https://dummyjson.com";

        given()
            .contentType(ContentType.JSON)
            .header("Authorization","Bearer " + this.token)
        .when()
            .get("/auth/products")

        .then()
            .assertThat()
            .statusCode(200)
            .body("products.title", notNullValue())
            .body("products.price", notNullValue())
            .body("products.id", notNullValue());
    }

    @Test
    @DisplayName("Busca Produto Sem autorização por Usuário")
    public void testGetForbiddenUserProducts(){
        // Dados da API
        baseURI = "https://dummyjson.com";

        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/auth/products")

        .then()
            .assertThat()
            .statusCode(403)
            .body("message", equalTo("Authentication Problem"));
    }

    @Test
    @DisplayName("Busca Produto Valido por Usuário")
    //Como não sei o que define um token expirado, gerei um token hardcoded na expectativa de que, após um tempo, ele fosse expirar.
    public void testGetUnauthorizedUserProducts(){
        // Dados da API
        baseURI = "https://dummyjson.com";
        //encontra produto
        given()
            .contentType(ContentType.JSON)
            .header("Authorization","Bearer " + this.expiredToken)
        .when()
            .get("/auth/products")
        .then()
            .assertThat()
            .statusCode(401)
            .body("message", equalTo("Invalid/Expired Token!"))
            .body("name", equalTo("JsonWebTokenError"));
    }

}




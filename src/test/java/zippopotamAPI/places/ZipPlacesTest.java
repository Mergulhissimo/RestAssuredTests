package zippopotamAPI.places;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZipPlacesTest {

    private static RequestSpecification requestSpec;
    private static ResponseSpecification responseSpec;


    @BeforeAll
    public static void createRequestSpecification() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://api.zippopotam.us")
                .build();
    }

    @BeforeAll
    public static void createResponseSpecification() {
        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void getCaliforniaZip() {

        given()
                .spec(requestSpec)
        .when()
                .get("/us/90210")
                .then()
                .spec(responseSpec)
                .assertThat()
                .body("places[0].state", equalTo("California"))
                .body("places.'place name'", hasItem("Beverly Hills"))
                .body("places.'place name'", hasSize(1));
    }

    @ParameterizedTest
    @CsvSource({
            "us, 90210, Beverly Hills",
            "us, 12345, Schenectady",
            "ca, B2R, Waverley"
    })
    public void getDifferentCountriesAndCodes(String countryCode, String zipCode, String expectedPlaceName) {


        given()
                .spec(requestSpec)
                .when()
                .pathParam("countryCode", countryCode)
                .pathParam("zipCode", zipCode)
                .get("/{countryCode}/{zipCode}")
                .then()
                .spec(responseSpec)
                .assertThat()
                .body("places[0].'place name'", equalTo(expectedPlaceName));
    }

}

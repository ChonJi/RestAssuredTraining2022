package tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class BaseTest {

    private static ReadProperties readProperties = new ReadProperties();

    @BeforeAll
    public static void setUp() {
        baseURI = readProperties.readBaseUri();
        requestSpecification = given()
                .queryParam("key", readProperties.readKey())
                .queryParam("token", readProperties.readToken())
                .when()
                .contentType(ContentType.JSON);
    }

    @Test
    public void dupa() {

    }
}

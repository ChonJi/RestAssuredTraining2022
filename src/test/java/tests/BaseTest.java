package tests;

import helpers.ReadProperties;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;

public class BaseTest {

    protected final static String BOARDS_URL = "members/me/boards";

    @BeforeAll
    public static void setUp() {
        ReadProperties readProperties = new ReadProperties();
        baseURI = readProperties.readBaseUri();
        requestSpecification = given()
                .queryParam("key", readProperties.readKey())
                .queryParam("token", readProperties.readToken())
                .when()
                .contentType(ContentType.JSON);
    }
}

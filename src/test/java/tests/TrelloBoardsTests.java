package tests;

import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.config.ParamConfig.UpdateStrategy.REPLACE;
import static io.restassured.config.ParamConfig.paramConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TrelloBoardsTests extends BaseTest {

    @Test
    @Order(0)
    public void shouldAuthenticationFailOnNoCredentials() {
        changeQueryParams(Map.of("key", "null", "token", "null"))
                .get(BOARDS_URL)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body(equalTo("invalid key"));
    }

    @Test
    @Order(1)
    public void shouldAuthenticationFailOnWrongKey() {
        changeQueryParams(Map.of("key", "Invalid key"))
                .get(BOARDS_URL)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body(equalTo("invalid key"));
    }

    @Test
    @Order(2)
    public void shouldAuthenticationFailOnNoToken() {
        changeQueryParams(Map.of("token", ""))
                .get(BOARDS_URL)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(equalTo("invalid token"));
    }

    @Test
    @Order(3)
    public void shouldAuthenticationFailOnWrongToken() {
        changeQueryParams(Map.of("token", "$%^&*()(*^%@@"))
                .get(BOARDS_URL)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body(equalTo("invalid token"));
    }

    @Test
    @Order(4)
    public void shouldAuthenticateOnDefaultSetup() {
        when()
                .get(BOARDS_URL)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    @Order(5)
    public void shouldListAllBoards() {
        var response = when().get(BOARDS_URL);
        var objects = response.jsonPath().getList("$");
        assertTrue(objects.size() == 1);
    }

    /**
     * Replaces default key param to any given
     * used for wrong credentials tests
     * @param key
     * @return RequestSpecification chain ended on when()
     */
    private RequestSpecification replaceKeyInQueryParam(Object key) {
        return given()
                .config(config().paramConfig(paramConfig().queryParamsUpdateStrategy(REPLACE)))
                .queryParam("key", key);
    }

    /**
     * Replaces default token param to any given
     * used for wrong credentials tests
     * @param token
     * @return RequestSpecification chain ended on when()
     */
    private RequestSpecification replaceTokenInQueryParam(Object token) {
        return given()
                .config(config().paramConfig(paramConfig().queryParamsUpdateStrategy(REPLACE)))
                .queryParam("token", token);
    }

    /**
     * Replaces any default query params
     * and builds given RequestSpecification
     * @param queryParams
     * @return RequestSpecification
     */
    private RequestSpecification changeQueryParams(Map<String, Object> queryParams) {
        var request = given().config(config().paramConfig(paramConfig().queryParamsUpdateStrategy(REPLACE)));
        queryParams.keySet().forEach(k -> request.queryParam(k, queryParams.get(k)));
        return request;
    }

}

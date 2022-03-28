package tests;

import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static io.restassured.config.ParamConfig.UpdateStrategy.REPLACE;
import static io.restassured.config.ParamConfig.paramConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TrelloBoardsTests extends BaseTest {

    @Test
    @Order(1)
    public void shouldAuthenticationFailedOnWrongKey() {
        replaceKeyInQueryParam("Invalid key")
                .get(BOARDS_URL)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body(equalTo("invalid key"));
    }

    @Test
    @Order(2)
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
    private RequestSpecification replaceKeyInQueryParam(String key) {
        return given()
                .config(config().paramConfig(paramConfig().queryParamsUpdateStrategy(REPLACE)))
                .queryParam("key", key).when();
    }
}

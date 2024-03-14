import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
@DisplayName("Getting the list of orders tests")
public class GetOrdersTests extends BaseTest {

    private final String ORDERS_URN = "/api/v1/orders";
    @Test
    @DisplayName("Getting the list of orders")
    @Description("Test of order list retrieval")
    public void testGetOrders() {
        Response ordersResponse = given()
                .contentType(ContentType.JSON)
                .when()
                .get(ORDERS_URN);
        ordersResponse
                .then().statusCode(200)
                .assertThat()
                .body("", hasKey("orders"));
    }

}

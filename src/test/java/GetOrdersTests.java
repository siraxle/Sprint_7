import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;

@DisplayName("Getting the list of orders tests")
public class GetOrdersTests extends CourierHelper {

    @Test
    @DisplayName("Getting the list of orders")
    @Description("Test of order list retrieval")
    public void testGetOrders() {
        Response ordersResponse = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/orders");
        ordersResponse.then().statusCode(200);
    }

}

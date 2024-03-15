import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.hasKey;

@DisplayName("Getting the list of orders tests")
public class GetOrdersTests extends BaseTest {

    private static final OrdersHelper ORDER_HELPER = new OrdersHelper();

    @Test
    @DisplayName("Getting the list of orders")
    @Description("Test of order list retrieval")
    public void testGetOrders() {
        Response ordersResponse = ORDER_HELPER.getOrders();
        ordersResponse
                .then()
                .statusCode(200)
                .assertThat()
                .body("", hasKey("orders"));
    }

}

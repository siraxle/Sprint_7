import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.*;

import static io.restassured.RestAssured.given;

public class OrdersHelper {

    private static final String ORDERS_URN = "/api/v1/orders";

    private static final String BASE_URL = BaseTest.BASE_URL;

    @Step("Get the list of orders")
    public Response getOrders() {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URL + ORDERS_URN);
    }

    @Step("Order creating")
    public Response createOrder(String firstName, String lastName, String address, int metroStation,
                                String phone, int rentTime, String deliveryDate, String comment, String[] colors) {
        Map<String, Object> body = createOrderJson(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, colors);
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .when()
                .post(BASE_URL + ORDERS_URN);
    }

    @Step("Json creating for order request")
    private Map<String, Object> createOrderJson(String firstName, String lastName, String address, int metroStation,
                                                String phone, int rentTime, String deliveryDate, String comment, String[] colors) {
        Map<String, Object> orderDetails = new HashMap<>();
        orderDetails.put("firstName", firstName);
        orderDetails.put("lastName", lastName);
        orderDetails.put("address", address);
        orderDetails.put("metroStation", metroStation);
        orderDetails.put("phone", phone);
        orderDetails.put("rentTime", rentTime);
        orderDetails.put("deliveryDate", deliveryDate);
        orderDetails.put("comment", comment);

        if (colors != null && colors.length > 0) {
            List<String> colorList = Arrays.asList(colors);
            orderDetails.put("color", colorList);
        } else {
            orderDetails.put("color", Collections.emptyList());
        }

        return orderDetails;
    }

}

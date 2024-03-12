import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
@DisplayName("Tests for Orders API")
public class OrdersAPITests extends BaseTest {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final int metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] colors;

    public OrdersAPITests(String firstName, String lastName, String address, int metroStation,
                          String phone, int rentTime, String deliveryDate, String comment, String[] colors) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06",
                        "Saske, come back to Konoha", new String[]{"BLACK"}},
                {"Sasuke", "Uzumaki", "Hidden Leaf Village", 3, "+7 800 123 45 67", 3, "2020-07-07",
                        "I will protect Konoha", new String[]{"GREY"}},
                {"Sakura", "Haruno", "Konoha, 55 apt.", 1, "+7 800 987 65 43", 7, "2020-08-08",
                        "Where is Naruto?", new String[]{"BLACK", "GREY"}},
                {"Kakashi", "Hatake", "Hidden Leaf Village, Hokage Tower", 2, "+7 800 111 22 33", 2, "2020-09-09",
                        "Sharingan secrets", null}
        });
    }

    @Test
    @DisplayName("Creating an order")
    @Description("The test checks the successful creation of an order with 1 color, all colors, or no color specified")
    public void testCreateOrder() {
        Response response = createOrder();

        response.then()
                .statusCode(201)
                .body("track", notNullValue());
    }

    @Step("Order creating")
    public Response createOrder() {
        String jsonBody = createOrderJson();
        return given()
                .header("Content-type", "application/json")
                .body(jsonBody)
                .when()
                .post("/api/v1/orders");
    }


    @Step("Json creating for order request")
    private String createOrderJson() {
        String colorJson = "null";
        if (colors != null) {
            StringBuilder colorsBuilder = new StringBuilder("[");
            for (int i = 0; i < colors.length; i++) {
                colorsBuilder.append("\"").append(colors[i]).append("\"");
                if (i < colors.length - 1) {
                    colorsBuilder.append(",");
                }
            }
            colorsBuilder.append("]");
            colorJson = colorsBuilder.toString();
        }

        return String.format(
                "{" +
                        "\"firstName\":\"%s\"," +
                        "\"lastName\":\"%s\"," +
                        "\"address\":\"%s\"," +
                        "\"metroStation\":%d," +
                        "\"phone\":\"%s\"," +
                        "\"rentTime\":%d," +
                        "\"deliveryDate\":\"%s\"," +
                        "\"comment\":\"%s\"," +
                        "\"color\":%s" +
                        "}",
                firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, colorJson
        );
    }

}

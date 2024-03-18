import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class CourierHelper {
    private static final String LOGIN_URN = "/api/v1/courier/login";
    private static final String COURIER_URN = "/api/v1/courier/";

    private static final String BASE_URL = BaseTest.BASE_URL;
    @Step("Courier authorization with login '{login}' and password '{password}'")
    public Response loginCourier(String login, String password) {
        Map<String, Object> body = new HashMap<>();
        body.put("login", login);
        body.put("password", password);

        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(BASE_URL + LOGIN_URN);
    }

    @Step("Generate Unique Login")
    public String generateUniqueLogin() {
        return "courier_" + System.currentTimeMillis();
    }

    @Step("Generate Password")
    public String generatePassword() {
        Random random = new Random();
        return String.valueOf(1000 + random.nextInt(9000));
    }


    @Step("Generate First Name")
    public String generateFirstName() {
        return "name_" + System.currentTimeMillis();
    }


    @Step("Delete a courier")
    public void deleteCourier(String courierId) {
        Map<String, Object> body = new HashMap<>();
        body.put("id", courierId);

        given()
                .header("Content-type", "application/json")
                .body(body)
                .when()
                .delete(BASE_URL + COURIER_URN + courierId)
                .then()
                .statusCode(200);
    }

    @Step("Creating a courier")
    public Response createCourier(CourierCreateRequest courierCreateRequest) {
        return given()
                .header("Content-type", "application/json")
                .body(courierCreateRequest)
                .when()
                .post(BASE_URL + COURIER_URN);
    }

}

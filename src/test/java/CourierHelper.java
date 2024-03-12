import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class CourierHelper extends BaseTest {


    @Step("Courier authorization with login '{login}' and password '{password}'")
    public Response loginCourier(String login, String password) {
        return given()
                .contentType(ContentType.JSON)
                .body("{\"login\": \"" + login + "\", \"password\": \"" + password + "\"}")
                .when()
                .post("/api/v1/courier/login");
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


    @Step("Creating a courier with a unique login, password and firstName")
    public String[] createCourier() {
        String uniqueLogin = generateUniqueLogin();
        String password = generatePassword();
        String firstName = generateFirstName();

        given()
                .header("Content-type", "application/json")
                .body("{\"login\":\"" + uniqueLogin + "\",\"password\":\"" + password + "\",\"firstName\":\"" + firstName + "\"}")
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201);
        return new String[]{uniqueLogin, password, firstName};
    }

    @Step("Delete a courier")
    public void deleteCourier(String courierId) {
        given()
                .header("Content-type", "application/json")
                .body("{\n" +
                        "    \"id\": \"" + courierId + "\"\n" +
                        "}")
                .when()
                .delete("/api/v1/courier/" + courierId)
                .then()
                .statusCode(200);
    }


    @Step("Creating a courier with a seted uniqueLogin, password and firstName")
    public Response createCourier(String uniqueLogin, String password, String firstName) {
        return given()
                .header("Content-type", "application/json")
                .body("{\"login\":\"" + uniqueLogin + "\",\"password\":\"" + password + "\",\"firstName\":\"" + firstName + "\"}")
                .when()
                .post("/api/v1/courier");
    }




}

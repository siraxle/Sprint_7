import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Courier Creation Tests for Courier API")
public class CourierCreationTests extends CourierHelper {

    @Test
    @DisplayName("Create a Courier")
    @Description("Verify that a courier can be successfully created")
    public void testCreateCourier() {
        String uniqueLogin = generateUniqueLogin();
        String password = generatePassword();
        String uniqueFirstName = generateFirstName();
        Response response = createCourier(uniqueLogin, password, uniqueFirstName);
        response.then()
                .assertThat()
                .statusCode(201)
                .assertThat()
                .body("ok", equalTo(true));

        LoginResponse loginResponse = loginCourier(uniqueLogin, password).body().as(LoginResponse.class);
        deleteCourier(loginResponse.getId());
    }

    @Test
    @DisplayName("Attempt to Create Duplicate Courier")
    @Description("Verify that creating two couriers with the same login is not allowed")
    public void testCreateDuplicateCourier() {
        String uniqueLogin = generateUniqueLogin();
        String password = generatePassword();
        String uniqueFirstName = generateFirstName();
        createCourier(uniqueLogin, password, uniqueFirstName);

        given()
                .header("Content-type", "application/json")
                .body("{\"login\":\"" + uniqueLogin + "\",\"password\":\"" + password + "\",\"firstName\":\"" + uniqueFirstName + "\"}")
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(409)
                .assertThat()
                .body("message", equalTo("Этот логин уже используется."));

        LoginResponse loginResponse = loginCourier(uniqueLogin, password).body().as(LoginResponse.class);
        deleteCourier(loginResponse.getId());
    }

    @Test
    @DisplayName("Create Courier with Missing Mandatory Fields")
    @Description("Verify that all mandatory fields are required to create a courier")
    public void testCreateCourierWithMissingFields() {
        given()
                .header("Content-type", "application/json")
                .body("{\"login\":\"login\"}")
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create Courier and Validate Response Code")
    @Description("Verify that the API responds with the correct status code")
    public void testCreateCourierAndValidateResponseCode() {
        String uniqueLogin = generateUniqueLogin();
        String password = generatePassword();
        String uniqueFirstName = generateFirstName();
        Response response = createCourier(uniqueLogin, password, uniqueFirstName);
        response.then()
                .assertThat()
                .statusCode(201);
        LoginResponse loginResponse = loginCourier(uniqueLogin, password).body().as(LoginResponse.class);
        deleteCourier(loginResponse.getId());
    }

    @Test
    @DisplayName("Create Courier and Verify Successful Response")
    @Description("Verify that a successful courier creation response contains 'ok: true'")
    public void testCreateCourierAndVerifySuccessResponse() {
        String uniqueLogin = generateUniqueLogin();
        String password = generatePassword();
        String uniqueFirstName = generateFirstName();
        Response response = createCourier(uniqueLogin, password, uniqueFirstName);

        response.then()
                .assertThat()
                .body("ok", equalTo(true));

        LoginResponse loginResponse = loginCourier(uniqueLogin, password).body().as(LoginResponse.class);
        deleteCourier(loginResponse.getId());

    }

    @Test
    @DisplayName("Create Courier with Missing Field and Verify Error Response")
    @Description("Verify that creating a courier with a missing field results in an error response")
    public void testCreateCourierWithMissingFieldAndVerifyError() {
        String password = generatePassword();
        String uniqueFirstName = generateFirstName();
        given()
                .header("Content-type", "application/json")
                .body("{\"password\":\"" + password + "\",\"firstName\":\"" + uniqueFirstName + "\"}")
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Attempt to Create Courier with Existing Login")
    @Description("Verify that creating a courier with an existing login results in an error response")
    public void testCreateCourierWithExistingLoginAndVerifyError() {
        String[] uniqueCreeds = createCourier();
        given()
                .header("Content-type", "application/json")
                .body("{\"login\":\"" + uniqueCreeds[0] + "\",\"password\":\"" + uniqueCreeds[1] + "\",\"firstName\":\"" + uniqueCreeds[2] + "\"}")
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat().statusCode(409);
        LoginResponse loginResponse = loginCourier(uniqueCreeds[0], uniqueCreeds[1]).body().as(LoginResponse.class);
        deleteCourier(loginResponse.getId());
    }

}
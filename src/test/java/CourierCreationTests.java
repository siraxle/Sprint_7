import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.preemptive;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Courier Creation Tests for Courier API")
public class CourierCreationTests {
    private final CourierHelper courierHelper = new CourierHelper();

    @Test
    @DisplayName("Create a Courier")
    @Description("Verify the response body that a courier can be successfully created")
    public void testCreateCourier() {
        String uniqueLogin = courierHelper.generateUniqueLogin();
        String password = courierHelper.generatePassword();
        String uniqueFirstName = courierHelper.generateFirstName();
        CourierCreateRequest courierCreateRequest = new CourierCreateRequest(uniqueLogin, password, uniqueFirstName);
        Response response = courierHelper.createCourier(courierCreateRequest);
        response.then()
                .assertThat()
                .body("ok", equalTo(true));

        LoginResponse loginResponse = courierHelper.loginCourier(uniqueLogin, password).body().as(LoginResponse.class);
        courierHelper.deleteCourier(loginResponse.getId());
    }

    @Test
    @DisplayName("Attempt to Create Duplicate Courier")
    @Description("Verify the message that creating two couriers with the same login is not allowed")
    public void testCreateDuplicateCourier() {
        String uniqueLogin = courierHelper.generateUniqueLogin();
        String password = courierHelper.generatePassword();
        String uniqueFirstName = courierHelper.generateFirstName();
        CourierCreateRequest courierCreateRequest = new CourierCreateRequest(uniqueLogin, password, uniqueFirstName);

        courierHelper.createCourier(courierCreateRequest);
        courierHelper.createCourier(courierCreateRequest)
                .then()
                .assertThat()
                .body("message", equalTo("Этот логин уже используется."));

        LoginResponse loginResponse = courierHelper.loginCourier(uniqueLogin, password).body().as(LoginResponse.class);
        courierHelper.deleteCourier(loginResponse.getId());
    }

    @Test
    @DisplayName("Create Courier with Missing Mandatory Fields")
    @Description("Verify the message that all mandatory fields are required to create a courier")
    public void testCreateCourierWithMissingFields() {
        String uniqueLogin = courierHelper.generateUniqueLogin();
        CourierCreateRequest courierCreateRequest = new CourierCreateRequest(uniqueLogin);

        courierHelper.createCourier(courierCreateRequest)
                .then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create Courier and Validate Response Code")
    @Description("Verify that the API responds with the correct status code")
    public void testCreateCourierAndValidateResponseCode() {
        String uniqueLogin = courierHelper.generateUniqueLogin();
        String password = courierHelper.generatePassword();
        String uniqueFirstName = courierHelper.generateFirstName();
        CourierCreateRequest courierCreateRequest = new CourierCreateRequest(uniqueLogin, password, uniqueFirstName);
        courierHelper.createCourier(courierCreateRequest)
                .then()
                .assertThat()
                .statusCode(201);
        LoginResponse loginResponse = courierHelper.loginCourier(uniqueLogin, password).body().as(LoginResponse.class);
        courierHelper.deleteCourier(loginResponse.getId());
    }

    @Test
    @DisplayName("Create Courier and Verify Successful Response")
    @Description("Verify that a successful courier creation response contains 'ok: true'")
    public void testCreateCourierAndVerifySuccessResponse() {
        String uniqueLogin = courierHelper.generateUniqueLogin();
        String password = courierHelper.generatePassword();
        String uniqueFirstName = courierHelper.generateFirstName();
        CourierCreateRequest courierCreateRequest = new CourierCreateRequest(uniqueLogin, password, uniqueFirstName);

        courierHelper.createCourier(courierCreateRequest).then()
                .assertThat()
                .body("ok", equalTo(true));

        LoginResponse loginResponse = courierHelper.loginCourier(uniqueLogin, password).body().as(LoginResponse.class);
        courierHelper.deleteCourier(loginResponse.getId());

    }

    @Test
    @DisplayName("Create Courier with Missing Field and Verify Error Response")
    @Description("Check the status code where creating a courier with a missing field results in an error response")
    public void testCreateCourierWithMissingFieldAndVerifyError() {
        String password = courierHelper.generatePassword();
        String uniqueFirstName = courierHelper.generateFirstName();
        CourierCreateRequest courierCreateRequest = new CourierCreateRequest(password, uniqueFirstName);
        courierHelper.createCourier(courierCreateRequest)
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    @DisplayName("Attempt to Create Courier with Existing Login")
    @Description("Verify the status code that creating a courier with an existing login results in an error response")
    public void testCreateCourierWithExistingLoginAndVerifyError() {
        String uniqueLogin = courierHelper.generateUniqueLogin();
        String password = courierHelper.generatePassword();
        String uniqueFirstName = courierHelper.generateFirstName();
        CourierCreateRequest courierCreateRequest = new CourierCreateRequest(uniqueLogin, password, uniqueFirstName);

        courierHelper.createCourier(courierCreateRequest);

        courierHelper.createCourier(courierCreateRequest)
                .then()
                .assertThat()
                .statusCode(409);

        LoginResponse loginResponse = courierHelper.loginCourier(uniqueLogin, password).body().as(LoginResponse.class);
        courierHelper.deleteCourier(loginResponse.getId());
    }

}
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Login tests for Courier API")
public class CourierLoginTests{
    private static final CourierHelper COURIER_HELPER = new CourierHelper();

    @Test
    @DisplayName("The courier can authorize")
    @Description("The test checks the possibility of successful authorization of the courier")
    public void testCourierCanLogin() {
        String uniqueLogin = COURIER_HELPER.generateUniqueLogin();
        String password = COURIER_HELPER.generatePassword();
        String uniqueFirstName = COURIER_HELPER.generateFirstName();
        CourierCreateRequest courierCreateRequest = new CourierCreateRequest(uniqueLogin, password, uniqueFirstName);
        COURIER_HELPER.createCourier(courierCreateRequest);
        Response response = COURIER_HELPER.loginCourier(uniqueLogin, password);
        response.then()
                .assertThat()
                .statusCode(200);

        LoginResponse loginResponse = response.body().as(LoginResponse.class);
        COURIER_HELPER.deleteCourier(loginResponse.getId());
    }

    @Test
    @DisplayName("For authorization you need to pass all mandatory fields")
    @Description("The test verifies status code that an authorization request returns an error if mandatory fields are missing")
    public void testLoginRequiresAllFields() {
        Response response = COURIER_HELPER.loginCourier("", "");
        response.then()
                .statusCode(400);
    }


    @Test
    @DisplayName("The system will return an error if your login and password is incorrect")
    @Description("The test verifies that the system returns an error if the login and password is incorrect")
    public void testIncorrectLoginOrPassword() {
        Response response = COURIER_HELPER.loginCourier("nonExistent", "invalidPassword");

        response.then()
                .assertThat()
                .statusCode(404)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("If there is no login field, the query returns an error")
    @Description("The test verifies message that the authorization request returns an error if no login is present")
    public void testLoginWithoutLoginField() {
        Response response = COURIER_HELPER.loginCourier("", "1234");
        response.then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("If there is no password field, the query returns an error")
    @Description("The test verifies message that the authorization request returns an error if no password is present")
    public void testLoginWithoutPasswordField() {
        Response response = COURIER_HELPER.loginCourier("login", "");
        response.then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("A successful request returns an id")
    @Description("The test verifies that a successful authorization request returns an id field")
    public void testSuccessfulLoginReturnsId() {
        String uniqueLogin = COURIER_HELPER.generateUniqueLogin();
        String password = COURIER_HELPER.generatePassword();
        String uniqueFirstName = COURIER_HELPER.generateFirstName();
        CourierCreateRequest courierCreateRequest = new CourierCreateRequest(uniqueLogin, password, uniqueFirstName);
        COURIER_HELPER.createCourier(courierCreateRequest);
        Response response = COURIER_HELPER.loginCourier(uniqueLogin, password);

        response.then()
                .assertThat()
                .body("id", notNullValue());

        LoginResponse loginResponse = response.body().as(LoginResponse.class);
        COURIER_HELPER.deleteCourier(loginResponse.getId());
    }

}

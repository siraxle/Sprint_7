import io.restassured.RestAssured;
import org.junit.Before;

public class BaseTest {

    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URL;
    }

}

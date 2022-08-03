import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;


public class UserTest {

    @Test(priority = 1)
    public void whenUserCreated_thenOK(){
        RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl/v2/";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParameters = new JSONObject(
                "{\r\n  \"id\": 20,\r\n  " +
                        "\"username\": \"testUsername\",\r\n  " +
                        "\"firstName\": \"testFirstName\",\r\n  " +
                        "\"lastName\": \"testLastName\",\r\n  " +
                        "\"email\": \"testEmail\",\r\n  " +
                        "\"password\": \"testPassword\",\r\n  " +
                        "\"phone\": \"testPhone\",\r\n  " +
                        "\"userStatus\": 0\r\n}"
        );
        request.header("Content-Type", "application/json");
        request.body(requestParameters.toString());

        Response response = request.post("/user");
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 2)
    public void whenUserGetContentMatch_thenOK(){
        RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl/v2/";
        Response response = RestAssured.get("/user/testUsername");

        JsonPath jsonPath = response.jsonPath();

        String id = jsonPath.get("id")+"";
        Assert.assertEquals(id, "20");

        String username = jsonPath.get("username")+"";
        Assert.assertEquals(username, "testUsername");

        String firstName = jsonPath.get("firstName")+"";
        Assert.assertEquals(firstName, "testFirstName");

        String lastName = jsonPath.get("lastName")+"";
        Assert.assertEquals(lastName, "testLastName");

        String email = jsonPath.get("email")+"";
        Assert.assertEquals(email, "testEmail");

        String password = jsonPath.get("password")+"";
        Assert.assertEquals(password, "testPassword");

        String phone = jsonPath.get("phone")+"";
        Assert.assertEquals(phone, "testPhone");

        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 3)
    public void whenUserUpdate_thenOK(){
        RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl/v2";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParameters = new JSONObject(
                "{\r\n  \"id\": 20,\r\n  " +
                        "\"username\": \"testUsername\",\r\n  " +
                        "\"firstName\": \"updateTestFirstName\",\r\n  " +
                        "\"lastName\": \"updateTestLastName\",\r\n  " +
                        "\"email\": \"updateTestEmail\",\r\n  " +
                        "\"password\": \"updateTestPassword\",\r\n  " +
                        "\"phone\": \"updateTestPhone\",\r\n  " +
                        "\"userStatus\": 0\r\n}"
        );
        request.header("Content-Type", "application/json");
        request.body(requestParameters.toString());

        Response response = request.put("/user/testUsername");
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 4)
    public void whenUpdetedUserGetContentMatch_thenOK(){
        RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl/v2/";
        Response response = RestAssured.get("/user/testUsername");

        JsonPath jsonPath = response.jsonPath();

        String id = jsonPath.get("id")+"";
        Assert.assertEquals(id, "20");

        String username = jsonPath.get("username")+"";
        Assert.assertEquals(username, "testUsername");

        String firstName = jsonPath.get("firstName")+"";
        Assert.assertEquals(firstName, "updateTestFirstName");

        String lastName = jsonPath.get("lastName")+"";
        Assert.assertEquals(lastName, "updateTestLastName");

        String email = jsonPath.get("email")+"";
        Assert.assertEquals(email, "updateTestEmail");

        String password = jsonPath.get("password")+"";
        Assert.assertEquals(password, "updateTestPassword");

        String phone = jsonPath.get("phone")+"";
        Assert.assertEquals(phone, "updateTestPhone");

        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 5)
    public void whenPetDelete_thenOK(){
        RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl/v2/";
        Response response = RestAssured.delete("/user/testUsername");

        Assert.assertEquals(response.statusCode(), 200);

    }
}

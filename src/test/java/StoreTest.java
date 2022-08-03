import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StoreTest {


    @Test(priority = 1)
    public void whenOrderCreated_thenOK() {

        RestAssured.baseURI = ("https://swaggerpetstore.przyklady.javastart.pl/v2");
        RequestSpecification request = RestAssured.given();
        JSONObject requestParameters = new JSONObject(
                "{\r\n  \"id\": 20,\r\n  " +
                        "\"petId\": 20,\r\n  " +
                        "\"quantity\": 1,\r\n  " +
                        "\"shipDate\": \"2022-08-03T14:54:58.359Z\",\r\n  " +
                        "\"status\": \"placed\",\r\n  " +
                        "\"complete\": false\r\n}"
        );

        request.header("Content-Type", "application/json");
        request.body(requestParameters.toString());

        Response response = request.post("/store/order");
        Assert.assertEquals(response.statusCode(), 200);

    }

    @Test(priority = 2)
    public void whenCreatedOrderExists_thenOK(){
        RestAssured.baseURI = ("https://swaggerpetstore.przyklady.javastart.pl/v2");
        Response response = RestAssured.get("/store/order/20");

        JsonPath jsonPath = response.jsonPath();

        String id = jsonPath.get("id")+"";
        Assert.assertEquals(id, "20");

        String quantity = jsonPath.get("quantity")+"";
        Assert.assertEquals(quantity, "1");

        String status = jsonPath.get("status")+"";
        Assert.assertEquals(status, "placed");

        Assert.assertEquals(response.statusCode(), 200);

    }

    @Test(priority = 3)
    public void whenOrderDeleted_thenOK(){
        RestAssured.baseURI = ("https://swaggerpetstore.przyklady.javastart.pl/v2");
        Response response = RestAssured.delete("/store/order/20");

        Assert.assertEquals(response.statusCode(), 200);
    }
}

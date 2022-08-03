import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PetTest {





    @Test(priority = 1)
    public void whenPetCreated_thenOK() {
        RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl/v2";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParameters = new JSONObject(
                "{\r\n  \"id\": 20,\r\n  " +
                        "\"category\": {\r\n    \"id\": 20,\r\n    \"name\": \"petCategory\"\r\n  },\r\n  " +
                        "\"name\": \"testPet\",\r\n " +
                        "\"photoUrls\": [\r\n    \"testPhotoUrl\"\r\n  ],\r\n  " +
                        "\"tags\": [\r\n    {\r\n      \"id\": 20,\r\n      \"name\": \"testTagName\"\r\n    }\r\n  ],\r\n  " +
                        "\"status\": \"testStatus\"\r\n}"
        );
        request.header("Content-Type", "application/json");
        request.body(requestParameters.toString());

        Response response = request.post("/pet");
        Assert.assertEquals(response.statusCode(), 200);


    }

    @Test(priority = 2)
    public void whenPetGetContentMatch_thenOK(){
        RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl/v2";
        Response response = RestAssured.get("/pet/20");

        JsonPath jsonPath = response.jsonPath();

        String id = jsonPath.get("id")+"";
        Assert.assertEquals(id, "20");

        String name = jsonPath.get("name")+"";
        Assert.assertEquals(name, "testPet");

        String status = jsonPath.get("status")+"";
        Assert.assertEquals(status, "testStatus");

        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 3)
    public void whenPetUpdate_thenOK(){

        RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl/v2";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParameters = new JSONObject(
                "{\r\n  \"id\": 20,\r\n  " +
                        "\"category\": {\r\n    \"id\": 20,\r\n    \"name\": \"updatePetCategory\"\r\n  },\r\n  " +
                        "\"name\": \"updateTestPet\",\r\n " +
                        "\"photoUrls\": [\r\n    \"updateTestPhotoUrl\"\r\n  ],\r\n  " +
                        "\"tags\": [\r\n    {\r\n      \"id\": 20,\r\n      \"name\": \"updateTestTagName\"\r\n    }\r\n  ],\r\n  " +
                        "\"status\": \"updateTestStatus\"\r\n}"
        );


        request.header("Content-Type", "application/json");
        request.body(requestParameters.toString());

        Response response = request.put("/pet");
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 4)
    public void whenPetGetUpdateContentMatch_thenOK(){
        RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl/v2";
        Response response = RestAssured.get("/pet/20");

        JsonPath jsonPath = response.jsonPath();

        String id = jsonPath.get("id")+"";
        Assert.assertEquals(id, "20");

        String name = jsonPath.get("name")+"";
        Assert.assertEquals(name, "updateTestPet");

        String status = jsonPath.get("status")+"";
        Assert.assertEquals(status, "updateTestStatus");

        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 5)
    public void whenPetDelete_thenOK(){
        RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl/v2";
        Response response = RestAssured.delete("/pet/20");

        Assert.assertEquals(response.statusCode(), 200);

    }
}

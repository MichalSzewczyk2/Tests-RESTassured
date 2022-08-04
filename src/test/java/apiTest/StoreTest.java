package apiTest;

import apiEngineEndpoints.PetEndpoints;
import apiEngineEndpoints.StoreEndpoints;
import apiPayload.Order;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class StoreTest {

    Faker faker;
    Order orderPayload;

    @BeforeTest
    public void beforeTest(){
        faker = new Faker();
        orderPayload = new Order();

        orderPayload.setId(faker.idNumber().hashCode());
        orderPayload.setPetId(faker.idNumber().hashCode());
        orderPayload.setQuantity(faker.number().numberBetween(0,10));
        orderPayload.setShipDate("2022-08-03T15:01:36.875Z");
        orderPayload.setStatus(faker.bothify("placed"));
        orderPayload.setComplete(faker.bothify("true"));
    }

    @Test(priority = 1)
    public void testPostOrder() {

        String payload = orderPayload.toString();

        Response response = StoreEndpoints.createStoreOrder(payload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertTrue(response.getStatusLine().contains("OK"));

    }

    @Test(priority = 2)
    public void testGetOrderById(){
        Response response = StoreEndpoints.readStoreOrder(orderPayload.getId());
        response.then().log().body().statusCode(200);
    }

    @Test(priority = 3)
    public void testDeleteOrderById(){
        Response response = StoreEndpoints.deleteStoreOrder(orderPayload.getId());
        response.then().log().body().statusCode(200);
    }
}

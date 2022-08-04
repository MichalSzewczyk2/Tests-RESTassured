package apiTest;

import apiEngineEndpoints.PetEndpoints;
import apiEngineEndpoints.Routes;
import apiEngineEndpoints.StoreEndpoints;
import apiPayload.Order;
import com.github.javafaker.Faker;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


/*
To run this test from command line use:
        mvn test -Dtest="apiTest.StoreTest"
 */

public class StoreTest {

    Faker faker;
    Order orderPayload;
    ExtentReports extents;

    @BeforeTest
    public void beforeTest(){
        faker = new Faker();
        orderPayload = new Order();
        extents = new ExtentReports(Routes.path+Routes.storePath);

        orderPayload.setId(faker.idNumber().hashCode());
        orderPayload.setPetId(faker.idNumber().hashCode());
        orderPayload.setQuantity(faker.number().numberBetween(0,10));
        orderPayload.setShipDate(orderPayload.randomShipDate());
        orderPayload.setStatus(orderPayload.randomStatus());
        orderPayload.setComplete(orderPayload.randomComplete());
    }

    @AfterTest
    public void afterTest(){
        extents.close();
    }

    @Test(priority = 1)
    public void testPostOrder() {
        ExtentTest test = extents.startTest("postOrder");

        String payload = orderPayload.toString();

        Response response = StoreEndpoints.createStoreOrder(payload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertTrue(response.getStatusLine().contains("OK"));

        test.log(LogStatus.PASS,"Post order response code is: "+response.getStatusCode());
        extents.endTest(test);
        extents.flush();

    }

    @Test(priority = 2)
    public void testGetOrderById(){

        ExtentTest test = extents.startTest("getOrder");

        Response response = StoreEndpoints.readStoreOrder(orderPayload.getId());
        response.then().log().body().statusCode(200);

        test.log(LogStatus.PASS,"Get order response code is: "+response.getStatusCode());
        extents.endTest(test);
        extents.flush();
    }

    @Test(priority = 3)
    public void testDeleteOrderById(){

        ExtentTest test = extents.startTest("deleteOrder");

        Response response = StoreEndpoints.deleteStoreOrder(orderPayload.getId());
        response.then().log().body().statusCode(200);

        test.log(LogStatus.PASS,"Delete order response code is: "+response.getStatusCode());
        extents.endTest(test);
        extents.flush();
    }
}

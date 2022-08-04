package apiTest;

import apiEngineEndpoints.Routes;
import apiEngineEndpoints.UserEndpoints;
import apiPayload.User;
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
        mvn test -Dtest="apiTest.UserTest"
 */
public class UserTest {

    Faker faker;
    User userPayload;
    ExtentReports extents;

    @BeforeTest
    public void beforeTest(){
        faker = new Faker();
        userPayload = new User();
        extents = new ExtentReports(Routes.path+Routes.userPath);

        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().emailAddress());
        userPayload.setPassword(faker.internet().password());
        userPayload.setPhone(faker.phoneNumber().cellPhone());
        userPayload.setUserStatus(faker.number().hashCode());
    }

    @AfterTest
    public void afterTest(){
        extents.close();
    }

    @Test(priority = 1)
    public void testPostUser(){
        ExtentTest test = extents.startTest("postUser");

        String payload = userPayload.toString();

        Response response = UserEndpoints.createUser(payload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertTrue(response.getStatusLine().contains("OK"));

        test.log(LogStatus.PASS,"Post user response code is: "+response.getStatusCode());
        extents.endTest(test);
        extents.flush();

    }

    @Test(priority = 2)
    public void testGetUserByUsername(){

        ExtentTest test = extents.startTest("getUser");

        Response response = UserEndpoints.readUser(userPayload.getUsername());
        response.then().log().body().statusCode(200);

        test.log(LogStatus.PASS,"Get user response code is: "+response.getStatusCode());
        extents.endTest(test);
        extents.flush();

    }

    @Test(priority = 3)
    public void testLoginUser(){

        ExtentTest test = extents.startTest("loginUser");

        Response response = UserEndpoints.loginUser(userPayload.getUsername(), userPayload.getPassword());
        response.then().log().body().statusCode(200);

        test.log(LogStatus.PASS,"Login user response code is: "+response.getStatusCode());
        extents.endTest(test);
        extents.flush();

    }

    @Test(priority = 4)
    public void testLogoutUser(){

        ExtentTest test = extents.startTest("logoutUser");

        Response response = UserEndpoints.logoutUser();
        response.then().log().body().statusCode(200);

        test.log(LogStatus.PASS,"Logout user response code is: "+response.getStatusCode());
        extents.endTest(test);
        extents.flush();

    }

    @Test(priority = 5)
    public void whenUserUpdate_thenOK(){

        ExtentTest test = extents.startTest("updateUser");

        User newUserPayload = new User();
        newUserPayload.setId(userPayload.getId());
        newUserPayload.setUsername(userPayload.getUsername());
        newUserPayload.setFirstName(userPayload.getFirstName() + "is first name");
        newUserPayload.setLastName(userPayload.getLastName() + " is last name");
        newUserPayload.setEmail(userPayload.getEmail() + " is email");
        newUserPayload.setPassword(userPayload.getPassword() + "is password");
        newUserPayload.setPhone(userPayload.getPhone() + " is phone");
        newUserPayload.setUserStatus(userPayload.getUserStatus());

        String payload = newUserPayload.toString();

        Response response = UserEndpoints.updateUser(newUserPayload.getUsername(), payload);
        response.then().log().body().statusCode(200);

        Response afterUpdateResponse = UserEndpoints.readUser(newUserPayload.getUsername());
        afterUpdateResponse.then().log().body().statusCode(200);

        test.log(LogStatus.PASS,"Update user response code is: "+response.getStatusCode());
        extents.endTest(test);
        extents.flush();

    }

    @Test(priority = 6)
    public void whenPetDelete_thenOK(){

        ExtentTest test = extents.startTest("deleteUser");

        Response response = UserEndpoints.deleteUser(userPayload.getUsername());
        response.then().log().body().statusCode(200);

        test.log(LogStatus.PASS,"Delete user response code is: "+response.getStatusCode());
        extents.endTest(test);
        extents.flush();

    }
}

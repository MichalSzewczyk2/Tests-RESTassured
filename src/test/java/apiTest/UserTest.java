package apiTest;

import apiEngineEndpoints.UserEndpoints;
import apiPayload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class UserTest {

    Faker faker;
    User userPayload;

    @BeforeTest
    public void beforeTest(){
        faker = new Faker();
        userPayload = new User();

        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().emailAddress());
        userPayload.setPassword(faker.internet().password());
        userPayload.setPhone(faker.phoneNumber().cellPhone());
        userPayload.setUserStatus(faker.number().hashCode());
    }


    @Test(priority = 1)
    public void testPostUser(){

        String payload = userPayload.toString();

        Response response = UserEndpoints.createUser(payload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertTrue(response.getStatusLine().contains("OK"));

    }

    @Test(priority = 2)
    public void testGetUserByUsername(){
        Response response = UserEndpoints.readUser(userPayload.getUsername());
        response.then().log().body().statusCode(200);
    }

    @Test(priority = 3)
    public void testLoginUser(){
        Response response = UserEndpoints.loginUser(userPayload.getUsername(), userPayload.getPassword());
        response.then().log().body().statusCode(200);
    }

    @Test(priority = 4)
    public void testLogoutUser(){
        Response response = UserEndpoints.logoutUser();
        response.then().log().body().statusCode(200);
    }

    @Test(priority = 5)
    public void whenUserUpdate_thenOK(){
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
    }

    @Test(priority = 6)
    public void whenPetDelete_thenOK(){
        Response response = UserEndpoints.deleteUser(userPayload.getUsername());
        response.then().log().body().statusCode(200);
    }
}

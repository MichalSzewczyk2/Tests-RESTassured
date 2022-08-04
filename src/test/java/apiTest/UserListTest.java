package apiTest;

import apiEngineEndpoints.UserEndpoints;
import apiPayload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class UserListTest {

    Faker faker;
    List<User> users;

    @BeforeTest
    public void beforeTest(){
        faker = new Faker();
        users = new ArrayList<User>();
        int listLength = faker.number().numberBetween(2,10);

        for (int i = 0; i < listLength; i++){
            User user = new User();

            user.setId(faker.number().numberBetween(1,100));
            user.setUsername(faker.name().username());
            user.setFirstName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setEmail(faker.internet().emailAddress());
            user.setPassword(faker.internet().password());
            user.setPhone(faker.phoneNumber().cellPhone());
            user.setUserStatus(faker.number().hashCode());

            users.add(user);
        }

    }

    @Test(priority = 1)
    public void testCreateUsersWithList(){
        
        StringBuilder sb = new StringBuilder();
        sb.append("[\r\n");
        for (User user : users) {
            sb.append(user.toString());
                sb.append(",\r\n");
        }
        sb.deleteCharAt(sb.length() - 3);
        sb.append("]");

        String payload = sb.toString();

        Response response = UserEndpoints.createUserUsingList(payload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertTrue(response.getStatusLine().contains("OK"));
    }

    @Test(priority = 2)
    public void testGetMultipleUserByUsername(){

        for (User user : users){
            Response response = UserEndpoints.readUser(user.getUsername());
            response.then().log().body().statusCode(200);
        }

    }

    @Test(priority = 3)
    public void testLoginLogoutMultipleUsers(){

        for (User user : users){

            Response loginResponse = UserEndpoints.loginUser(user.getUsername(), user.getPassword());
            loginResponse.then().log().body().statusCode(200);

            Response logoutResponse = UserEndpoints.logoutUser();
            logoutResponse.then().log().body().statusCode(200);

        }

    }

    @Test(priority = 4)
    public void testUpdateMultipleUsers(){

        for (User user : users){
            User newUserPayload = new User();
            newUserPayload.setId(user.getId());
            newUserPayload.setUsername(user.getUsername());
            newUserPayload.setFirstName(user.getFirstName() + "is first name");
            newUserPayload.setLastName(user.getLastName() + " is last name");
            newUserPayload.setEmail(user.getEmail() + " is email");
            newUserPayload.setPassword(user.getPassword() + "is password");
            newUserPayload.setPhone(user.getPhone() + " is phone");
            newUserPayload.setUserStatus(user.getUserStatus());

            String payload = newUserPayload.toString();

            Response response = UserEndpoints.updateUser(newUserPayload.getUsername(), payload);
            response.then().log().body().statusCode(200);

            Response afterUpdateResponse = UserEndpoints.readUser(newUserPayload.getUsername());
            afterUpdateResponse.then().log().body().statusCode(200);

        }

    }

    @Test(priority = 5)
    public void testDeleteMultipleUsers(){

        for(User user : users){
            Response response = UserEndpoints.deleteUser(user.getUsername());
            response.then().log().body().statusCode(200);
        }
    }
}

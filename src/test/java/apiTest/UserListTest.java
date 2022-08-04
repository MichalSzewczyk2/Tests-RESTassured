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

import java.util.ArrayList;
import java.util.List;

/*
To run this test from command line use:
        mvn test -Dtest="apiTest.UserListTest"
 */
public class UserListTest {

    Faker faker;
    List<User> users;
    ExtentReports extents;

    @BeforeTest
    public void beforeTest(){
        faker = new Faker();
        users = new ArrayList<User>();
        extents = new ExtentReports(Routes.path+Routes.userListPath);

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

    @AfterTest
    public void afterTest(){
        extents.close();
    }

    @Test(priority = 1)
    public void testCreateUsersWithList(){

        ExtentTest test = extents.startTest("postUserWithList");
        
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

        test.log(LogStatus.PASS,"Post user with list response code is: "+response.getStatusCode());
        extents.endTest(test);
        extents.flush();

    }

    @Test(priority = 2)
    public void testGetMultipleUserByUsername(){

        ExtentTest test = extents.startTest("getMultipleUser");

        for (User user : users){
            Response response = UserEndpoints.readUser(user.getUsername());
            response.then().log().body().statusCode(200);
            test.log(LogStatus.PASS,"get user response code is: "+response.getStatusCode());
        }

        extents.endTest(test);
        extents.flush();

    }

    @Test(priority = 3)
    public void testLoginLogoutMultipleUsers(){

        ExtentTest test = extents.startTest("loginLogoutUser");

        for (User user : users){

            Response loginResponse = UserEndpoints.loginUser(user.getUsername(), user.getPassword());
            loginResponse.then().log().body().statusCode(200);
            test.log(LogStatus.PASS,"Login response code is: "+loginResponse.getStatusCode());
            Response logoutResponse = UserEndpoints.logoutUser();
            logoutResponse.then().log().body().statusCode(200);
            test.log(LogStatus.PASS,"Logout response code is: "+logoutResponse.getStatusCode());


        }

        extents.endTest(test);
        extents.flush();

    }

    @Test(priority = 4)
    public void testUpdateMultipleUsers(){

        ExtentTest test = extents.startTest("updateMultipleUsers");

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
            test.log(LogStatus.PASS,"Update user response code is: "+response.getStatusCode());
        }

        extents.endTest(test);
        extents.flush();

    }

    @Test(priority = 5)
    public void testDeleteMultipleUsers(){

        ExtentTest test = extents.startTest("deleteMultipleUsers");

        for(User user : users){
            Response response = UserEndpoints.deleteUser(user.getUsername());
            response.then().log().body().statusCode(200);

            test.log(LogStatus.PASS,"Delete user response code is: "+response.getStatusCode());

        }


        extents.endTest(test);
        extents.flush();

    }
}

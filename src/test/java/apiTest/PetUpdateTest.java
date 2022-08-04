package apiTest;

import apiEngineEndpoints.PetEndpoints;
import apiEngineEndpoints.Routes;
import apiPayload.Pet;
import com.github.javafaker.Faker;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;

/*
To run this test from command line use:
        mvn test -Dtest="apiTest.PetUpdateTest"
 */

public class PetUpdateTest {

    Faker faker;
    Pet pet;
    Pet updatePet;
    ExtentReports extents;


    @BeforeTest
    public void beforeTest(){
        faker = new Faker();
        pet = new Pet();
        extents = new ExtentReports(Routes.path+Routes.petFormPath);

        pet.setId(faker.idNumber().hashCode());
        pet.setCategoryId(faker.idNumber().hashCode());
        pet.setCategoryName(faker.animal().name());
        pet.setName(faker.name().name());
        pet.setPhotoUrls(faker.internet().url());
        pet.setTagId(faker.idNumber().hashCode());
        pet.setTagName(faker.bothify("???????"));
        pet.setStatus(pet.randomStatus());
    }

    @AfterTest
    public void afterTest(){
        extents.close();
    }

    @Test(priority = 1)
    public void postAndLoadPet(){

        ExtentTest test = extents.startTest("postGetPet");

        String payload = pet.toString();

        Response postResponse = PetEndpoints.createPet(payload);
        postResponse.then().log().all();
        Assert.assertEquals(postResponse.getStatusCode(),200);
        Assert.assertTrue(postResponse.getStatusLine().contains("OK"));

        test.log(LogStatus.PASS,"PostPet response code is: "+ postResponse.getStatusCode());

        Response getResponse = PetEndpoints.readPet(pet.getId());
        getResponse.then().log().body().statusCode(200);

        test.log(LogStatus.PASS,"GetPet response code is: "+ getResponse.getStatusCode());

        JsonPath jsonPath = getResponse.jsonPath();

        updatePet = new Pet();
        updatePet.setId(jsonPath.get("id"));
        Map<String, Object> category = jsonPath.get("category");
        updatePet.setCategoryId((int)category.get("id"));
        updatePet.setCategoryName((String) category.get("name"));
        updatePet.setName(jsonPath.get("name"));
        Map<String, Object> tags = jsonPath.get("tags[0]");
        updatePet.setTagId((int)tags.get("id"));
        updatePet.setTagName((String)tags.get("name"));
        updatePet.setStatus(jsonPath.get("status"));


        extents.endTest(test);
        extents.flush();

    }

    @Test(priority = 2)
    public void updatePetWithFormData(){

        ExtentTest test = extents.startTest("updatePetWithForm");

        updatePet.setStatus(updatePet.getStatus() + "form update");

        Response response = PetEndpoints.updateForms(updatePet.getId(), updatePet.getName(), updatePet.getStatus());
        response.then().log().body().statusCode(200);
        JsonPath jsonPath = response.jsonPath();
        String status = jsonPath.get("status");
        Assert.assertNotEquals(status, pet.getStatus());

        test.log(LogStatus.PASS,"Update pet response code is: "+response.getStatusCode());
        extents.endTest(test);
        extents.flush();

    }

    @Test(priority = 3)
    public void deletePet(){

        ExtentTest test = extents.startTest("deletePet");

        Response response = PetEndpoints.deletePet(pet.getId());
        response.then().log().body().statusCode(200);

        test.log(LogStatus.PASS,"Delete pet response code is: "+response.getStatusCode());
        extents.endTest(test);
        extents.flush();
    }


}

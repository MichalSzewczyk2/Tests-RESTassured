package apiTest;

import apiEngineEndpoints.PetEndpoints;
import apiEngineEndpoints.Routes;
import apiPayload.Pet;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;


/*
To run this test from command line use:
        mvn test -Dtest="apiTest.PetTest"
 */
public class PetTest {

    Faker faker;
    Pet petPayload;
    ExtentReports extents;

    @BeforeTest
    public void beforeTest(){
        faker = new Faker();
        petPayload = new Pet();
        extents = new ExtentReports(Routes.path+Routes.petPath);

        petPayload.setId(faker.idNumber().hashCode());
        petPayload.setCategoryId(faker.idNumber().hashCode());
        petPayload.setCategoryName(faker.animal().name());
        petPayload.setName(faker.name().name());
        petPayload.setName(faker.name().name());
        petPayload.setTagId(faker.idNumber().hashCode());
        petPayload.setTagName(faker.bothify("???????"));
        petPayload.setStatus(petPayload.randomStatus());
    }
    @AfterTest
    public void afterTest(){
        extents.close();
    }

    @Test(priority = 1)
    public void testPostPet() {


        ExtentTest test = extents.startTest("postPet");

        String payload = petPayload.toString();

        Response response = PetEndpoints.createPet(payload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertTrue(response.getStatusLine().contains("OK"));

        test.log(LogStatus.PASS,"PostPet response code is: "+response.getStatusCode());
        extents.endTest(test);
        extents.flush();
    }

    @Test(priority = 2)
    public void testGetUserById(){
        ExtentTest test = extents.startTest("getPet");
        Response response = PetEndpoints.readPet(petPayload.getId());
        response.then().log().body().statusCode(200);
        test.log(LogStatus.PASS,"GetPet response code is: "+response.getStatusCode());
        extents.endTest(test);
        extents.flush();
    }

    @Test(priority = 3)
    public void testUpdatePetById(){

        ExtentTest test = extents.startTest("updatePet");
        Pet newPetPayload = new Pet();
        newPetPayload.setId(petPayload.getId());
        newPetPayload.setCategoryId(petPayload.getCategoryId());
        newPetPayload.setCategoryName(petPayload.getCategoryName()+"is category name");
        newPetPayload.setName(petPayload.getName()+"is name");
        newPetPayload.setPhotoUrls(petPayload.getPhotoUrls()+"is photo URLs");
        newPetPayload.setTagId(petPayload.getTagId());
        newPetPayload.setTagName(petPayload.getTagName()+"is tag name");
        newPetPayload.setStatus(petPayload.getStatus()+"is Status");

        String payload = newPetPayload.toString();

        Response response = PetEndpoints.updatePet(payload);
        response.then().log().body().statusCode(200);

        test.log(LogStatus.PASS,"Update pet response code is: "+response.getStatusCode());

        Response afterUpdateResponse = PetEndpoints.readPet(petPayload.getId());
        afterUpdateResponse.then().log().body().statusCode(200);

        test.log(LogStatus.PASS,"Read pet after update response code is: "+response.getStatusCode());
        extents.endTest(test);
        extents.flush();

    }

    @Test(priority = 4)
    public void testDeletePetById(){

        ExtentTest test = extents.startTest("deletePet");

        Response response = PetEndpoints.deletePet(petPayload.getId());
        response.then().log().body().statusCode(200);

        test.log(LogStatus.PASS,"Delete pet response code is: "+response.getStatusCode());
        extents.endTest(test);
        extents.flush();

    }
}

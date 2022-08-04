package apiTest;

import apiEngineEndpoints.PetEndpoints;
import apiPayload.Pet;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;

public class PetTest {

    Faker faker;
    Pet petPayload;

    @BeforeTest
    public void beforeTest(){
        faker = new Faker();
        petPayload = new Pet();

        petPayload.setId(faker.idNumber().hashCode());
        petPayload.setCategoryId(faker.idNumber().hashCode());
        petPayload.setCategoryName(faker.animal().name());
        petPayload.setName(faker.name().name());
        petPayload.setPhotoUrls(faker.internet().url());
        petPayload.setTagId(faker.idNumber().hashCode());
        petPayload.setTagName(faker.bothify("???????"));
        petPayload.setStatus(faker.bothify("???????"));
    }

    @Test(priority = 1)
    public void testPostPet() {

        String payload = petPayload.toString();

        Response response = PetEndpoints.createPet(payload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertTrue(response.getStatusLine().contains("OK"));
    }

    @Test(priority = 2)
    public void testGetUserById(){
        Response response = PetEndpoints.readPet(petPayload.getId());
        response.then().log().body().statusCode(200);
    }

    @Test(priority = 3)
    public void testUpdatePetById(){

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

        Response afterUpdateResponse = PetEndpoints.readPet(petPayload.getId());
        afterUpdateResponse.then().log().body().statusCode(200);

    }

    @Test(priority = 4)
    public void testDeletePetById(){
        Response response = PetEndpoints.deletePet(petPayload.getId());
        response.then().log().body().statusCode(200);
    }
}

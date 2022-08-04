package apiEngineEndpoints;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PetEndpoints {

    public static Response createPet(String payload){
        RestAssured.baseURI = Routes.baseURI;
        Response response = RestAssured.
                given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload).
                when().post(Routes.postDeletePetURI);
        return response;
    }

    public static Response readPet(int id){
        RestAssured.baseURI = Routes.baseURI;
        Response response = RestAssured.
                given().pathParam("id",id).
                when().get(Routes.getPutPetURI);
        return response;
    }

    public static Response updatePet(String payload){
        RestAssured.baseURI = Routes.baseURI;
        Response response = RestAssured.
                given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload).
                when().put(Routes.postDeletePetURI);
        return response;
    }

    public static Response deletePet(int id){
        RestAssured.baseURI = Routes.baseURI;
        Response response = RestAssured.
                given().pathParam("id",id).
                when().delete(Routes.getPutPetURI);
        return response;
    }

}

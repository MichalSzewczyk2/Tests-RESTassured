package apiEngineEndpoints;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class StoreEndpoints {

    public static Response createStoreOrder(String payload){
        RestAssured.baseURI = Routes.baseURI;
        Response response = RestAssured.
                given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload).
                when().post(Routes.postStoreURI);
        return response;
    }

    public static Response readStoreOrder(int id){
        RestAssured.baseURI = Routes.baseURI;
        Response response = RestAssured.
                given().pathParam("id",id).
                when().get(Routes.getPutDeleteStoreURI);
        return response;
    }

    public static Response deleteStoreOrder(int id){
        RestAssured.baseURI = Routes.baseURI;
        Response response = RestAssured.
                given().pathParam("id",id).
                when().delete(Routes.getPutDeleteStoreURI);
        return response;
    }
}

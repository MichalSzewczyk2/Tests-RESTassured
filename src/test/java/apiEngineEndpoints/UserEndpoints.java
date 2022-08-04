package apiEngineEndpoints;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndpoints {
    public static Response createUser(String payload){
        RestAssured.baseURI = Routes.baseURI;
        Response response = RestAssured.
                given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload).
                when().post(Routes.postUserURI);
        return response;
    }

    public static Response readUser(String username){
        RestAssured.baseURI = Routes.baseURI;
        Response response = RestAssured.
                given().pathParam("username", username).
                when().get(Routes.getPutDeleteUserURI);
        return response;
    }

    public static Response updateUser(String username, String payload){
        RestAssured.baseURI = Routes.baseURI;
        Response response = RestAssured.
                given().contentType(ContentType.JSON).accept(ContentType.JSON).
                pathParam("username",username).body(payload).
                when().put(Routes.getPutDeleteUserURI);
        return response;
    }

    public static Response loginUser(String username, String password){
        RestAssured.baseURI = Routes.baseURI;
        Response response = RestAssured.
                given().queryParam("username", username).
                queryParam("password", password).
                when().get(Routes.loginUserURI);
        return response;
    }

    public static Response logoutUser(){
        RestAssured.baseURI = Routes.baseURI;
        Response response = RestAssured.
                given().when().get(Routes.logoutUserURI);
        return response;
    }

    public static Response createUserUsingList(String payload){
        RestAssured.baseURI = Routes.baseURI;
        Response response = RestAssured.
                given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload).
                when().post(Routes.postListUsersURI);
        return response;
    }

    public static Response deleteUser(String username){
        RestAssured.baseURI = Routes.baseURI;
        Response response = RestAssured.
                given().pathParam("username",username).
                when().delete(Routes.getPutDeleteUserURI);
        return response;
    }
}

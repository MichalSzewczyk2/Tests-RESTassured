package apiEngineEndpoints;

public class Routes {

    public static String baseURI = "https://swaggerpetstore.przyklady.javastart.pl/v2/";

    public static String postDeletePetURI = "/pet";

    public static String getPutPetURI = "/pet/{id}";

    public static String postUpdatePetURI = "/pet/{id}";

    public static String postStoreURI = "/store/order";

    public static String getPutDeleteStoreURI = "/store/order/{id}";

    public static String postUserURI = "/user";

    public static String getPutDeleteUserURI = "/user/{username}";

    public static String loginUserURI = "/user/login";

    public static String logoutUserURI = "/user/logout";

    public static String postListUsersURI = "/user/createWithList";
}

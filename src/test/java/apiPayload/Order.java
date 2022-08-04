package apiPayload;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {

    private int id;
    private int petId;
    private int quantity;
    private String shipDate;
    private String status;
    private String complete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String randomShipDate(){
        Faker faker = new Faker();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        //Date date = faker.date().between((Date) "2000-01-01T00:00:00","2020-01-01T00:00:00");
        System.out.println(sdf.format(date));
        return sdf.format(date) + "";
    }

    public String randomStatus(){
        String[] status ={"placed","approved","delivered"};
        Faker random = new Faker();
        int statusInt = random.number().numberBetween(0,2);
        return status[statusInt];
    }

    public String randomComplete(){
        String[] status ={"true","false"};
        Faker random = new Faker();
        int statusInt = random.number().numberBetween(0,1);
        return status[statusInt];
    }

    @Override
    public String toString() {
        return "{\r\n  \"id\": " + id + ",\r\n  " +
                "\"petId\": " + petId + ",\r\n  " +
                "\"quantity\": " + quantity + ",\r\n  " +
                "\"shipDate\": \"" + shipDate + "\",\r\n  " +
                "\"status\": \"" + status + "\",\r\n  " +
                "\"complete\": " + complete + "\r\n}";
    }

}

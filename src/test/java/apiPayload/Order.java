package apiPayload;

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

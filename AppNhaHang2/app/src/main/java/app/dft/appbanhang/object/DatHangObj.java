package app.dft.appbanhang.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Inuyasha on 14/06/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatHangObj {
    String _id, name, address, email, phone, userId, updated, created;
    int status, amount;
    DonHangObj totalProduct;
    String transportCost, transportCode, delivery;

    @JsonProperty("transportCost")
    public String getTransportCost() {
        return transportCost;
    }

    public void setTransportCost(String transportCost) {
        this.transportCost = transportCost;
    }

    @JsonProperty("transportCode")
    public String getTransportCode() {
        return transportCode;
    }

    public void setTransportCode(String transportCode) {
        this.transportCode = transportCode;
    }

    @JsonProperty("delivery")
    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    @JsonProperty("_id")
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("updated")
    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @JsonProperty("created")
    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @JsonProperty("status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @JsonProperty("amount")
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @JsonProperty("totalProduct")
    public DonHangObj getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(DonHangObj totalProduct) {
        this.totalProduct = totalProduct;
    }

    public DatHangObj(String _id, String name, String address, String email, String phone, String userId, String updated, String created, int status, int amount, DonHangObj totalProduct, String transportCost, String transportCode, String delivery) {
        super();
        this._id = _id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.userId = userId;
        this.updated = updated;
        this.created = created;
        this.status = status;
        this.amount = amount;
        this.totalProduct = totalProduct;
        this.transportCost = transportCost;
        this.transportCode = transportCode;
        this.delivery = delivery;
    }

    public DatHangObj() {
        super();
    }
}

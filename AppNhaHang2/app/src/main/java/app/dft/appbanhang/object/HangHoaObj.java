package app.dft.appbanhang.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by ductv on 5/10/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HangHoaObj {
    String _id, name, description, descriptionShort, image, userId, updated, created, priceNew, maxCoinNew, startDate, endDate;
    int price, total, maxCoin, status, freeShip, warranty, rateCoin2;


    @JsonProperty("rateCoin")
    public int getRateCoin2() {
        return rateCoin2;
    }

    public void setRateCoin2(int rateCoin2) {
        this.rateCoin2 = rateCoin2;
    }




    @JsonProperty("descriptionShort")
    public String getDescriptionShort() {
        return descriptionShort;
    }

    public void setDescriptionShort(String descriptionShort) {
        this.descriptionShort = descriptionShort;
    }

    @JsonProperty("priceNew")
    public String getPriceNew() {
        return priceNew;
    }

    public void setPriceNew(String priceNew) {
        this.priceNew = priceNew;
    }

    @JsonProperty("maxCoinNew")
    public String getMaxCoinNew() {
        return maxCoinNew;
    }

    public void setMaxCoinNew(String maxCoinNew) {
        this.maxCoinNew = maxCoinNew;
    }

    @JsonProperty("startDate")
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @JsonProperty("endDate")
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    @JsonProperty("price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @JsonProperty("total")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @JsonProperty("maxCoin")
    public int getMaxCoin() {
        return maxCoin;
    }

    public void setMaxCoin(int maxCoin) {
        this.maxCoin = maxCoin;
    }

    @JsonProperty("status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @JsonProperty("freeShip")
    public int getFreeShip() {
        return freeShip;
    }

    public void setFreeShip(int freeShip) {
        this.freeShip = freeShip;
    }

    @JsonProperty("warranty")
    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }


    public HangHoaObj(String _id, String name, String description, String descriptionShort, String image, String userId, String updated, String created, String priceNew, String maxCoinNew, String startDate, String endDate, int price, int total, int maxCoin, int status, int freeShip, int warranty, int rateCoin2) {
        this._id = _id;
        this.name = name;
        this.description = description;
        this.descriptionShort = descriptionShort;
        this.image = image;
        this.userId = userId;
        this.updated = updated;
        this.created = created;
        this.priceNew = priceNew;
        this.maxCoinNew = maxCoinNew;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.total = total;
        this.maxCoin = maxCoin;
        this.status = status;
        this.freeShip = freeShip;
        this.warranty = warranty;
        this.rateCoin2 = rateCoin2;
    }

    public HangHoaObj() {
        super();
    }
}

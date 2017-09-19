package app.dft.appbanhang.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ductv on 4/4/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserObj {
    String _id, username, fullName, email, androidToken, iosToken, presenter, restaurantId, avatar, birthday,
            phone, address, social, location, businessCode, bankCode, taxCode;
    int type, gender, coin, rateCoin;

    @JsonProperty("businessCode")
    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
    @JsonProperty("bankCode")
    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
    @JsonProperty("taxCode")
    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }
    @JsonProperty("rateCoin")
    public int getRateCoin() {
        return rateCoin;
    }

    public void setRateCoin(int rateCoin) {
        this.rateCoin = rateCoin;
    }

    @JsonProperty("type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @JsonProperty("gender")
    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("social")
    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("_id")
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("fullName")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("androidToken")
    public String getAndroidToken() {
        return androidToken;
    }

    public void setAndroidToken(String androidToken) {
        this.androidToken = androidToken;
    }

    @JsonProperty("iosToken")
    public String getIosToken() {
        return iosToken;
    }

    public void setIosToken(String iosToken) {
        this.iosToken = iosToken;
    }

    @JsonProperty("coin")
    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    @JsonProperty("presenter")
    public String getPresenter() {
        return presenter;
    }

    public void setPresenter(String presenter) {
        this.presenter = presenter;
    }

    @JsonProperty("restaurantId")
    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    @JsonProperty("avatar")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @JsonProperty("birthday")
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public UserObj(String _id, String username, String fullName, String email, String androidToken, String iosToken, String presenter, String restaurantId, String avatar, String birthday, String phone, String address, String social, String location, String businessCode, String bankCode, String taxCode, int type, int gender, int coin, int rateCoin) {
        super();
        this._id = _id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.androidToken = androidToken;
        this.iosToken = iosToken;
        this.presenter = presenter;
        this.restaurantId = restaurantId;
        this.avatar = avatar;
        this.birthday = birthday;
        this.phone = phone;
        this.address = address;
        this.social = social;
        this.location = location;
        this.businessCode = businessCode;
        this.bankCode = bankCode;
        this.taxCode = taxCode;
        this.type = type;
        this.gender = gender;
        this.coin = coin;
        this.rateCoin = rateCoin;
    }

    public UserObj() {
        super();
    }
}

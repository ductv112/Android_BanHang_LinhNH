package app.dft.appbanhang.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Inuyasha on 22/06/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThongTinUngDungObj {
    String _id, versionAndroid, versionIOS, email, page, phone, skype, facebook, develop, android, ios, logo;

    @JsonProperty("_id")
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @JsonProperty("versionAndroid")
    public String getVersionAndroid() {
        return versionAndroid;
    }

    public void setVersionAndroid(String versionAndroid) {
        this.versionAndroid = versionAndroid;
    }

    @JsonProperty("versionIOS")
    public String getVersionIOS() {
        return versionIOS;
    }

    public void setVersionIOS(String versionIOS) {
        this.versionIOS = versionIOS;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("page")
    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("skype")
    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    @JsonProperty("facebook")
    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    @JsonProperty("develop")
    public String getDevelop() {
        return develop;
    }

    public void setDevelop(String develop) {
        this.develop = develop;
    }

    @JsonProperty("android")
    public String getAndroid() {
        return android;
    }

    public void setAndroid(String android) {
        this.android = android;
    }

    @JsonProperty("ios")
    public String getIos() {
        return ios;
    }

    public void setIos(String ios) {
        this.ios = ios;
    }

    @JsonProperty("logo")
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public ThongTinUngDungObj(String _id, String versionAndroid, String versionIOS, String email, String page, String phone, String skype, String facebook, String develop, String android, String ios, String logo) {
        super();
        this._id = _id;
        this.versionAndroid = versionAndroid;
        this.versionIOS = versionIOS;
        this.email = email;
        this.page = page;
        this.phone = phone;
        this.skype = skype;
        this.facebook = facebook;
        this.develop = develop;
        this.android = android;
        this.ios = ios;
        this.logo = logo;
    }

    public ThongTinUngDungObj() {
        super();
    }
}

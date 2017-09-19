package app.dft.appbanhang.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sony on 4/18/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestaurantObj {
    String _id, name, avatar, check;
    double distance = 0;
    List<Double> location = new ArrayList<>();
    List<String> listDanDuong = new ArrayList<>();

    @JsonProperty("listDanDuong")
    public List<String> getListDanDuong() {
        return listDanDuong;
    }

    public void setListDanDuong(List<String> listDanDuong) {
        this.listDanDuong = listDanDuong;
    }

    @JsonProperty("check")
    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
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

    @JsonProperty("avatar")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @JsonProperty("distance")
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @JsonProperty("location")
    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public RestaurantObj() {
        super();
    }

    public RestaurantObj(String _id, String name, String avatar, double distance, List<Double> location, String check, List<String> listDanDuong) {
        super();
        this._id = _id;
        this.name = name;
        this.avatar = avatar;
        this.distance = distance;
        this.location = location;
        this.check = check;
        this.listDanDuong = listDanDuong;
    }

    public RestaurantObj(String name, String avatar, List<Double> location, List<String> listDanDuong) {
        super();
        this.name = name;
        this.avatar = avatar;
        this.location = location;
        this.listDanDuong = listDanDuong;
    }
}

package app.dft.appbanhang.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sony on 4/26/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestaurantDetailObj {
    String _id, name, phone, code, avatar;
    int like = -1, member = -1, distance = -1, maxPoint = 0;
    List<Double> location = new ArrayList<>();
    CampainObj campainPromotion, campainPoint, campainMember;
    CurrentMemberObj currentMember = new CurrentMemberObj();

    @JsonProperty("maxPoint")
    public int getMaxPoint() {
        return maxPoint;
    }

    @JsonProperty("currentMember")
    public CurrentMemberObj getCurrentMember() {
        return currentMember;
    }

    @JsonProperty("_id")
    public String get_id() {
        return _id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("avatar")
    public String getAvatar() {
        return avatar;
    }

    @JsonProperty("like")
    public int getLike() {
        return like;
    }

    @JsonProperty("member")
    public int getMember() {
        return member;
    }

    @JsonProperty("distance")
    public int getDistance() {
        return distance;
    }

    @JsonProperty("location")
    public List<Double> getLocation() {
        return location;
    }

    @JsonProperty("campainPromotion")
    public CampainObj getCampainPromotion() {
        return campainPromotion;
    }

    @JsonProperty("campainPoint")
    public CampainObj getCampainPoint() {
        return campainPoint;
    }

    @JsonProperty("campainMember")
    public CampainObj getCampainMember() {
        return campainMember;
    }

    public void setMaxPoint(int maxPoint) {
        this.maxPoint = maxPoint;
    }

    public void setCurrentMember(CurrentMemberObj currentMember) {
        this.currentMember = currentMember;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public void setMember(int member) {
        this.member = member;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public void setCampainPromotion(CampainObj campainPromotion) {
        this.campainPromotion = campainPromotion;
    }

    public void setCampainPoint(CampainObj campainPoint) {
        this.campainPoint = campainPoint;
    }

    public void setCampainMember(CampainObj campainMember) {
        this.campainMember = campainMember;
    }

    public RestaurantDetailObj() {
        super();
    }


    public RestaurantDetailObj(String _id, String name, String phone, String code, String avatar, int like, int member, int distance, List<Double> location, CampainObj campainPromotion, CampainObj campainPoint, CampainObj campainMember, int maxPoint, CurrentMemberObj currentMember) {
        super();
        this._id = _id;
        this.name = name;
        this.phone = phone;
        this.code = code;
        this.avatar = avatar;
        this.like = like;
        this.member = member;
        this.distance = distance;
        this.location = location;
        this.campainPromotion = campainPromotion;
        this.campainPoint = campainPoint;
        this.campainMember = campainMember;
        this.maxPoint = maxPoint;
        this.currentMember = currentMember;
    }
}

package app.dft.appbanhang.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Sony on 4/26/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CampainObj {
    String _id, restaurantId, title, content, startDate, endDate, type, avatar;

    @JsonProperty("_id")
    public String get_id() {
        return _id;
    }

    @JsonProperty("restaurantId")
    public String getRestaurantId() {
        return restaurantId;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("content")
    public String getContent() {
        return content;
    }

    @JsonProperty("startDate")
    public String getStartDate() {
        return startDate;
    }

    @JsonProperty("endDate")
    public String getEndDate() {
        return endDate;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("avatar")
    public String getAvatar() {
        return avatar;
    }


    public void set_id(String _id) {
        this._id = _id;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public CampainObj() {
        super();
    }

    public CampainObj(String _id, String restaurantId, String title, String content, String startDate, String endDate, String type, String avatar) {
        super();
        this._id = _id;
        this.restaurantId = restaurantId;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.avatar = avatar;
    }
}

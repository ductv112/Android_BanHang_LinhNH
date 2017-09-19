package app.dft.appbanhang.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Inuyasha on 11/05/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HomeObj {

    String _id, title, describe, productId, userId, banner, link;
    int type, typeAdvertise, position;
    HangHoaObj productFirst;
    ADVFilterObj filter;

    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @JsonProperty("_id")
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("describe")
    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @JsonProperty("productId")
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @JsonProperty("typeAdvertise")
    public int getTypeAdvertise() {
        return typeAdvertise;
    }

    public void setTypeAdvertise(int typeAdvertise) {
        this.typeAdvertise = typeAdvertise;
    }

    @JsonProperty("productFirst")
    public HangHoaObj getProductFirst() {
        return productFirst;
    }

    public void setProductFirst(HangHoaObj productFirst) {
        this.productFirst = productFirst;
    }

    @JsonProperty("filter")
    public ADVFilterObj getFilter() {
        return filter;
    }

    public void setFilter(ADVFilterObj filter) {
        this.filter = filter;
    }

    @JsonProperty("banner")
    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    @JsonProperty("position")
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public HomeObj(String _id, String title, String describe, String productId, String userId, String banner, String link, int type, int typeAdvertise, int position, HangHoaObj productFirst, ADVFilterObj filter) {
        super();
        this._id = _id;
        this.title = title;
        this.describe = describe;
        this.productId = productId;
        this.userId = userId;
        this.banner = banner;
        this.link = link;
        this.type = type;
        this.typeAdvertise = typeAdvertise;
        this.position = position;
        this.productFirst = productFirst;
        this.filter = filter;
    }

    public HomeObj() {
        super();
    }
}

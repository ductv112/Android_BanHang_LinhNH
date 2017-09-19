package app.dft.appbanhang.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Inuyasha on 24/05/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThuongHieuObj {

    String _id, name, nation, logo;

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
    @JsonProperty("nation")
    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }
    @JsonProperty("logo")
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public ThuongHieuObj(String _id, String name, String nation, String logo) {
        super();
        this._id = _id;
        this.name = name;
        this.nation = nation;
        this.logo = logo;
    }

    public ThuongHieuObj() {
        super();
    }
}

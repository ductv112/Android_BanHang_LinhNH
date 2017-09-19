package app.dft.appbanhang.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Inuyasha on 11/05/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoaiHangHoaObj {
    String _id, name;
    int type;

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

    @JsonProperty("type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public LoaiHangHoaObj(String _id, String name, int type) {
        super();
        this._id = _id;
        this.name = name;
        this.type = type;
    }

    public LoaiHangHoaObj() {
        super();
    }
}

package app.dft.appbanhang.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Inuyasha on 30/08/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NhomHangHoaObj {
    String _id, name;


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

    public NhomHangHoaObj(String _id, String name) {
        super();
        this._id = _id;
        this.name = name;
    }

    public NhomHangHoaObj() {
        super();
    }
}

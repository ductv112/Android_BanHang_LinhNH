package app.dft.appbanhang.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by ductv on 4/26/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResposeArrayObj {
    int code;
    String msg;
    List<String> data;

    @JsonProperty("code")
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @JsonProperty("msg")
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @JsonProperty("data")
    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public ResposeArrayObj(int code, String msg, List<String> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResposeArrayObj() {
        super();
    }
}

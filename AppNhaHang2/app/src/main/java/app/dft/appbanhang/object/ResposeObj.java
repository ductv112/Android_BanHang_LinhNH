package app.dft.appbanhang.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ductv on 4/26/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResposeObj {
    int code;
    String msg, data;

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
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ResposeObj(int code, String msg, String data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResposeObj() {
        super();
    }
}

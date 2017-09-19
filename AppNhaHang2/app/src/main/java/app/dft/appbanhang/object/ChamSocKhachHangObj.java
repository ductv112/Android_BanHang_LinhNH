package app.dft.appbanhang.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Inuyasha on 23/06/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChamSocKhachHangObj {
    String _id, ask, answer;

    @JsonProperty("_id")
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @JsonProperty("ask")
    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    @JsonProperty("answer")
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ChamSocKhachHangObj(String _id, String ask, String answer) {
        super();
        this._id = _id;
        this.ask = ask;
        this.answer = answer;
    }

    public ChamSocKhachHangObj() {
        super();
    }
}

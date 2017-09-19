package app.dft.appbanhang.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Inuyasha on 05/05/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThongBaoHeThongObj {
    String _id, msg, title, content, campaignId;
    int type, status;

    @JsonProperty("_id")
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @JsonProperty("msg")
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonProperty("campaignId")
    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    @JsonProperty("type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @JsonProperty("status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ThongBaoHeThongObj(String _id, String msg, String title, String content, String campaignId, int type, int status) {
        super();
        this._id = _id;
        this.msg = msg;
        this.title = title;
        this.content = content;
        this.campaignId = campaignId;
        this.type = type;
        this.status = status;
    }

    public ThongBaoHeThongObj() {
        super();
    }
}

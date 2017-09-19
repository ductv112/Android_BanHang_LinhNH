package app.dft.appbanhang.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Inuyasha on 10/05/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckConnectObj {

    String _id, version;
    int activePayment, activeAdvertisment;

    @JsonProperty("_id")
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @JsonProperty("activePayment")
    public int getActivePayment() {
        return activePayment;
    }

    public void setActivePayment(int activePayment) {
        this.activePayment = activePayment;
    }

    @JsonProperty("activeAdvertisment")
    public int getActiveAdvertisment() {
        return activeAdvertisment;
    }

    public void setActiveAdvertisment(int activeAdvertisment) {
        this.activeAdvertisment = activeAdvertisment;
    }

    public CheckConnectObj(String _id, String version, int activePayment, int activeAdvertisment) {
        super();
        this._id = _id;
        this.version = version;
        this.activePayment = activePayment;
        this.activeAdvertisment = activeAdvertisment;
    }

    public CheckConnectObj() {
        super();
    }
}

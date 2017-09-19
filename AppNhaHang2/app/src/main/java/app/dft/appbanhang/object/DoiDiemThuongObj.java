package app.dft.appbanhang.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ductv on 6/23/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DoiDiemThuongObj {
    Long time = -1L;
    int coin = 0;

    @JsonProperty("time")
    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @JsonProperty("coin")
    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public DoiDiemThuongObj(Long time, int coin) {
        super();
        this.time = time;
        this.coin = coin;
    }

    public DoiDiemThuongObj() {
        super();
    }
}

package app.dft.appbanhang.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Inuyasha on 14/06/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DonHangObj {
    int total, price, rateCoin, coin;
    HangHoaObj productId;

    @JsonProperty("total")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @JsonProperty("price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @JsonProperty("rateCoin")
    public int getRateCoin() {
        return rateCoin;
    }

    public void setRateCoin(int rateCoin) {
        this.rateCoin = rateCoin;
    }

    @JsonProperty("coin")
    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    @JsonProperty("productId")
    public HangHoaObj getProductId() {
        return productId;
    }

    public void setProductId(HangHoaObj productId) {
        this.productId = productId;
    }

    public DonHangObj(int total, int price, int rateCoin, int coin, HangHoaObj productId) {
        super();
        this.total = total;
        this.price = price;
        this.rateCoin = rateCoin;
        this.coin = coin;
        this.productId = productId;
    }

    public DonHangObj() {
        super();
    }
}

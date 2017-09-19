package app.dft.appbanhang.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ductv on 6/7/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GioHangObj extends HangHoaObj {
    String totalProductCart, rateCoin, thanhTien;
    String totalPoinCart = "0";

    @JsonProperty("thanhTien")
    public String getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(String thanhTien) {
        this.thanhTien = thanhTien;
    }

    @JsonProperty("totalProductCart")
    public String getTotalProductCart() {
        return totalProductCart;
    }

    public void setTotalProductCart(String totalProductCart) {
        this.totalProductCart = totalProductCart;
    }

    public String getTotalPoinCart() {
        return totalPoinCart;
    }

    public void setTotalPoinCart(String totalPoinCart) {
        this.totalPoinCart = totalPoinCart;
    }

    @JsonProperty("rateCoin")
    public String getRateCoin() {
        return rateCoin;
    }

    public void setRateCoin(String rateCoin) {
        this.rateCoin = rateCoin;
    }

    public GioHangObj(String _id, String name, String description, String descriptionShort, String image, String userId, String updated, String created, String priceNew, String maxCoinNew, String startDate, String endDate, int price, int total, int maxCoin, int status, int freeShip, int warranty, int rateCoin2, String totalProductCart, String rateCoin, String thanhTien, String totalPoinCart) {
        super(_id, name, description, descriptionShort, image, userId, updated, created, priceNew, maxCoinNew, startDate, endDate, price, total, maxCoin, status, freeShip, warranty, rateCoin2);
        this.totalProductCart = totalProductCart;
        this.rateCoin = rateCoin;
        this.thanhTien = thanhTien;
        this.totalPoinCart = totalPoinCart;
    }

    public GioHangObj(String totalProductCart, String rateCoin, String thanhTien, String totalPoinCart) {
        this.totalProductCart = totalProductCart;
        this.rateCoin = rateCoin;
        this.thanhTien = thanhTien;
        this.totalPoinCart = totalPoinCart;
    }

    public GioHangObj() {
        super();
    }
}

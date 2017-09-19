package app.dft.appbanhang.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Inuyasha on 25/05/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChiTietHangHoaObj extends HangHoaObj {

    int rateCoin;
    ArrayList<String> tag;
    ThuongHieuObj trademarkId;
    List<LoaiHangHoaObj> productTypeId;
    List<String> describe;
    ArrayList<String> size;
    NhomHangHoaObj productGroupId;

    @JsonProperty("size")
    public ArrayList<String> getSize() {
        return size;
    }

    public void setSize(ArrayList<String> size) {
        this.size = size;
    }

    @JsonProperty("rateCoin")
    public int getRateCoin() {
        return rateCoin;
    }

    public void setRateCoin(int rateCoin) {
        this.rateCoin = rateCoin;
    }
    @JsonProperty("tag")
    public ArrayList<String> getTag() {
        return tag;
    }

    public void setTag(ArrayList<String> tag) {
        this.tag = tag;
    }

    @JsonProperty("describe")
    public List<String> getDescribe() {
        return describe;
    }

    public void setDescribe(List<String> describe) {
        this.describe = describe;
    }

    @JsonProperty("trademarkId")
    public ThuongHieuObj getTrademarkId() {
        return trademarkId;
    }

    public void setTrademarkId(ThuongHieuObj trademarkId) {
        this.trademarkId = trademarkId;
    }

    @JsonProperty("productTypeId")
    public List<LoaiHangHoaObj> getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(List<LoaiHangHoaObj> productTypeId) {
        this.productTypeId = productTypeId;
    }
    @JsonProperty("productGroupId")
    public NhomHangHoaObj getProductGroupId() {
        return productGroupId;
    }

    public void setProductGroupId(NhomHangHoaObj productGroupId) {
        this.productGroupId = productGroupId;
    }

    public ChiTietHangHoaObj(String _id, String name, String description, String descriptionShort, String image, String userId, String updated, String created, String priceNew, String maxCoinNew, String startDate, String endDate, int price, int total, int maxCoin, int status, int freeShip, int warranty, int rateCoin2, int rateCoin, ArrayList<String> tag, ThuongHieuObj trademarkId, List<LoaiHangHoaObj> productTypeId, List<String> describe, ArrayList<String> size, NhomHangHoaObj productGroupId) {
        super(_id, name, description, descriptionShort, image, userId, updated, created, priceNew, maxCoinNew, startDate, endDate, price, total, maxCoin, status, freeShip, warranty, rateCoin2);
        this.rateCoin = rateCoin;
        this.tag = tag;
        this.trademarkId = trademarkId;
        this.productTypeId = productTypeId;
        this.describe = describe;
        this.size = size;
        this.productGroupId = productGroupId;
    }

    public ChiTietHangHoaObj(int rateCoin, ArrayList<String> tag, ThuongHieuObj trademarkId, List<LoaiHangHoaObj> productTypeId, List<String> describe, ArrayList<String> size, NhomHangHoaObj productGroupId) {
        this.rateCoin = rateCoin;
        this.tag = tag;
        this.trademarkId = trademarkId;
        this.productTypeId = productTypeId;
        this.describe = describe;
        this.size = size;
        this.productGroupId = productGroupId;
    }

    public ChiTietHangHoaObj(String _id, String name, String description, String descriptionShort, String image, String userId, String updated, String created, String priceNew, String maxCoinNew, String startDate, String endDate, int price, int total, int maxCoin, int status, int freeShip, int warranty, int rateCoin2) {
        super(_id, name, description, descriptionShort, image, userId, updated, created, priceNew, maxCoinNew, startDate, endDate, price, total, maxCoin, status, freeShip, warranty, rateCoin2);
    }

    public ChiTietHangHoaObj() {
    }
}

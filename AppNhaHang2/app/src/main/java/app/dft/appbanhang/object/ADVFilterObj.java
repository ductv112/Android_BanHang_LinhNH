package app.dft.appbanhang.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by ductv on 5/30/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ADVFilterObj {
    String productHot, promotion;
    List<String> productTypeId, trademarkId;
    String userId, keyword, rateCoin;

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("keyword")
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @JsonProperty("rateCoin")
    public String getRateCoin() {
        return rateCoin;
    }

    public void setRateCoin(String rateCoin) {
        this.rateCoin = rateCoin;
    }

    @JsonProperty("productHot")
    public String getProductHot() {
        return productHot;
    }

    public void setProductHot(String productHot) {
        this.productHot = productHot;
    }

    @JsonProperty("promotion")
    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }


    @JsonProperty("productTypeId")
    public List<String> getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(List<String> productTypeId) {
        this.productTypeId = productTypeId;
    }

    @JsonProperty("trademarkId")
    public List<String> getTrademarkId() {
        return trademarkId;
    }

    public void setTrademarkId(List<String> trademarkId) {
        this.trademarkId = trademarkId;
    }

    public ADVFilterObj(String productHot, String promotion, List<String> productTypeId, List<String> trademarkId, String userId, String keyword, String rateCoin) {
        super();
        this.productHot = productHot;
        this.promotion = promotion;
        this.productTypeId = productTypeId;
        this.trademarkId = trademarkId;
        this.userId = userId;
        this.keyword = keyword;
        this.rateCoin = rateCoin;
    }

    public ADVFilterObj() {
        super();
    }
}

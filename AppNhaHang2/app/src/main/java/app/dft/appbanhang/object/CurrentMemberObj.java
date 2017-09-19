package app.dft.appbanhang.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Sony on 4/27/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentMemberObj {
    int member = 0, like = 0, checkIn = 0, totalPoint = 0;

    @JsonProperty("member")
    public int getMember() {
        return member;
    }

    public void setMember(int member) {
        this.member = member;
    }

    @JsonProperty("like")
    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    @JsonProperty("checkIn")
    public int getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(int checkIn) {
        this.checkIn = checkIn;
    }

    @JsonProperty("totalPoint")
    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    public CurrentMemberObj() {
        super();
    }

    public CurrentMemberObj(int member, int like, int checkIn, int totalPoint) {
        super();
        this.member = member;
        this.like = like;
        this.checkIn = checkIn;
        this.totalPoint = totalPoint;
    }
}

package idv.randy.idv.randy.friends;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.sql.Timestamp;
import java.util.List;

import idv.randy.petwall.PwVO;

public class FriendsVO implements java.io.Serializable {
    private Integer memNo1;
    private Integer memNo2;
    private String status;

    public Integer getMemNo1() {
        return memNo1;
    }

    public void setMemNo1(Integer memNo1) {
        this.memNo1 = memNo1;
    }

    public Integer getMemNo2() {
        return memNo2;
    }

    public void setMemNo2(Integer memNo2) {
        this.memNo2 = memNo2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static List<FriendsVO> decodeToList(String stringIn) {
        Gson gsonb = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        List<FriendsVO> list = gsonb.fromJson(stringIn, new TypeToken<List<FriendsVO>>() {
        }.getType());
        return list;
    }

}
package idv.randy.petwall;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.sql.Date;
import java.util.List;

public class PwrVO {

    private Integer pwrno;
    private Integer pwno;
    private Integer memno;
    private Date pwrdate;
    private String pwrcontent;

    public static List<PwrVO> decodeToList(String stringIn) {
        Gson gsonb = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        List<PwrVO> list = gsonb.fromJson(stringIn, new TypeToken<List<PwrVO>>() {
        }.getType());
        return list;
    }

    public Integer getPwrno() {
        return pwrno;
    }

    public void setPwrno(Integer pwrno) {
        this.pwrno = pwrno;
    }

    public Integer getPwno() {
        return pwno;
    }

    public void setPwno(Integer pwno) {
        this.pwno = pwno;
    }

    public Integer getMemno() {
        return memno;
    }

    public void setMemno(Integer memno) {
        this.memno = memno;
    }

    public Date getPwrdate() {
        return pwrdate;
    }

    public void setPwrdate(Date pwrdate) {
        this.pwrdate = pwrdate;
    }

    public String getPwrcontent() {
        return pwrcontent;
    }

    public void setPwrcontent(String pwrcontent) {
        this.pwrcontent = pwrcontent;
    }
}
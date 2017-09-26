package idv.randy.petwall;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class PwVO implements Parcelable{

    protected PwVO(Parcel in) {
        pwPicture = in.createByteArray();
        pwContent = in.readString();
        pwFilm = in.createByteArray();
        pwPraise = in.readString();
    }

    public static final Creator<PwVO> CREATOR = new Creator<PwVO>() {
        @Override
        public PwVO createFromParcel(Parcel in) {
            return new PwVO(in);
        }

        @Override
        public PwVO[] newArray(int size) {
            return new PwVO[size];
        }
    };

    public Integer getPwNo() {
        return pwNo;
    }

    public void setPwNo(Integer pwNo) {
        this.pwNo = pwNo;
    }

    public Date getPwDate() {
        return pwDate;
    }

    public void setPwDate(Date pwDate) {
        this.pwDate = pwDate;
    }

    public byte[] getPwPicture() {
        return pwPicture;
    }

    public void setPwPicture(byte[] pwPicture) {
        this.pwPicture = pwPicture;
    }

    public String getPwContent() {
        return pwContent;
    }

    public void setPwContent(String pwContent) {
        this.pwContent = pwContent;
    }

    public byte[] getPwFilm() {
        return pwFilm;
    }

    public void setPwFilm(byte[] pwFilm) {
        this.pwFilm = pwFilm;
    }

    public String getPwPraise() {
        return pwPraise;
    }

    public void setPwPraise(String pwPraise) {
        this.pwPraise = pwPraise;
    }

    public Integer getMemno() {
        return memno;
    }

    public void setMemno(Integer memno) {
        this.memno = memno;
    }

    Integer pwNo;
    Date pwDate;
    byte[] pwPicture;
    String pwContent;
    byte[] pwFilm;
    String pwPraise;
    Integer memno;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(pwPicture);
        dest.writeString(pwContent);
        dest.writeByteArray(pwFilm);
        dest.writeString(pwPraise);
    }
    public static List<PwVO> decodeToList(String stringIn) {
        Gson gsonb = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        List<PwVO> list = gsonb.fromJson(stringIn, new TypeToken<List<PwVO>>() {
        }.getType());
        return list;
    }

}

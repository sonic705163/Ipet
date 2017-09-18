package com.example.java.iPet;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;

public class PetWallVO implements Parcelable{

    protected PetWallVO(Parcel in) {
        pwPicture = in.createByteArray();
        pwContent = in.readString();
        pwFilm = in.createByteArray();
        pwPraise = in.readString();
    }

    public static final Creator<PetWallVO> CREATOR = new Creator<PetWallVO>() {
        @Override
        public PetWallVO createFromParcel(Parcel in) {
            return new PetWallVO(in);
        }

        @Override
        public PetWallVO[] newArray(int size) {
            return new PetWallVO[size];
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
}

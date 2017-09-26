package idv.randy.me;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.sql.Timestamp;
import java.util.List;

import idv.randy.petwall.PwVO;

public class MembersVO implements Parcelable {
    private Integer memNo;
    private String menId;
    private String memPassword;
    private String memName;
    private String memPhone;
    private String memCellPhone;
    private String memEmail;
    private String memSex;
    private Timestamp memBirthday;
    private String memAddress;
    private String memPost;
    private String memRight;
    private Timestamp memCreateDate;
    private String memIsFeeder;
    private String memFeedAddress;
    private byte[] memImg;
    private String againPassword;
    private String day;
    private String month;
    private String year;

    protected MembersVO(Parcel in) {
        menId = in.readString();
        memPassword = in.readString();
        memName = in.readString();
        memPhone = in.readString();
        memCellPhone = in.readString();
        memEmail = in.readString();
        memSex = in.readString();
        memAddress = in.readString();
        memPost = in.readString();
        memRight = in.readString();
        memIsFeeder = in.readString();
        memFeedAddress = in.readString();
        memImg = in.createByteArray();
        againPassword = in.readString();
        day = in.readString();
        month = in.readString();
        year = in.readString();
    }

    public static final Creator<MembersVO> CREATOR = new Creator<MembersVO>() {
        @Override
        public MembersVO createFromParcel(Parcel in) {
            return new MembersVO(in);
        }

        @Override
        public MembersVO[] newArray(int size) {
            return new MembersVO[size];
        }
    };

    public Integer getMemNo() {
        return memNo;
    }

    public void setMemNo(Integer memNo) {
        this.memNo = memNo;
    }

    public String getMenId() {
        return menId;
    }

    public void setMenId(String menId) {
        this.menId = menId;
    }

    public String getMemPassword() {
        return memPassword;
    }

    public void setMemPassword(String memPassword) {
        this.memPassword = memPassword;
    }

    public String getMemName() {
        return memName;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public String getMemPhone() {
        return memPhone;
    }

    public void setMemPhone(String memPhone) {
        this.memPhone = memPhone;
    }

    public String getMemCellPhone() {
        return memCellPhone;
    }

    public void setMemCellPhone(String memCellPhone) {
        this.memCellPhone = memCellPhone;
    }

    public String getMemEmail() {
        return memEmail;
    }

    public void setMemEmail(String memEmail) {
        this.memEmail = memEmail;
    }

    public String getMemSex() {
        return memSex;
    }

    public void setMemSex(String memSex) {
        this.memSex = memSex;
    }

    public Timestamp getMemBirthday() {
        return memBirthday;
    }

    public void setMemBirthday(Timestamp memBirthday) {
        this.memBirthday = memBirthday;
    }

    public String getMemAddress() {
        return memAddress;
    }

    public void setMemAddress(String memAddress) {
        this.memAddress = memAddress;
    }

    public String getMemPost() {
        return memPost;
    }

    public void setMemPost(String memPost) {
        this.memPost = memPost;
    }

    public String getMemRight() {
        return memRight;
    }

    public void setMemRight(String memRight) {
        this.memRight = memRight;
    }

    public Timestamp getMemCreateDate() {
        return memCreateDate;
    }

    public void setMemCreateDate(Timestamp memCreateDate) {
        this.memCreateDate = memCreateDate;
    }

    public String getMemIsFeeder() {
        return memIsFeeder;
    }

    public void setMemIsFeeder(String memIsFeeder) {
        this.memIsFeeder = memIsFeeder;
    }

    public String getMemFeedAddress() {
        return memFeedAddress;
    }

    public void setMemFeedAddress(String memFeedAddress) {
        this.memFeedAddress = memFeedAddress;
    }

    public byte[] getMemImg() {
        return memImg;
    }

    public void setMemImg(byte[] memImg) {
        this.memImg = memImg;
    }

    public String getAgainPassword() {
        return againPassword;
    }

    public void setAgainPassword(String againPassword) {
        this.againPassword = againPassword;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(menId);
        dest.writeString(memPassword);
        dest.writeString(memName);
        dest.writeString(memPhone);
        dest.writeString(memCellPhone);
        dest.writeString(memEmail);
        dest.writeString(memSex);
        dest.writeString(memAddress);
        dest.writeString(memPost);
        dest.writeString(memRight);
        dest.writeString(memIsFeeder);
        dest.writeString(memFeedAddress);
        dest.writeByteArray(memImg);
        dest.writeString(againPassword);
        dest.writeString(day);
        dest.writeString(month);
        dest.writeString(year);
    }

    public static MembersVO decodeToVO(String stringIn) {
        Gson gsonb = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        MembersVO membersVO = gsonb.fromJson(stringIn, MembersVO.class);
        return membersVO;
    };
    public static List<MembersVO> decodeToList(String stringIn) {
        Gson gsonb = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        List<MembersVO> list = gsonb.fromJson(stringIn, new TypeToken<List<MembersVO>>() {
        }.getType());
        return list;
    }

};


package idv.jack;


import java.io.Serializable;
import java.sql.Date;

public class Case implements Serializable {

    private Integer petno;
    private Integer memno;
    private Integer breedno;
    private String status;
    private String petname;
    private String petage;
    private String petsize;
    private String petcolor;
    private String petposition;
    private String petic;
    private String tnr;
    private String situation;
    private Date petdate;
    private Integer memn02;
    private String petitle;
    private Double petLongitude;
    private Double petlatitude;
    private String petsex;


    public Case(Integer petno, Integer memno, Integer breedno, String status, String petname, String petage, String petsize, String petcolor, String petposition, String petic, String tnr, String situation, Date petdate, Integer memn02, String petitle, Double petLongitude, Double petlatitude, String petsex) {
        this.petno = petno;
        this.memno = memno;
        this.breedno = breedno;
        this.status = status;
        this.petname = petname;
        this.petage = petage;
        this.petsize = petsize;
        this.petcolor = petcolor;
        this.petposition = petposition;
        this.petic = petic;
        this.tnr = tnr;
        this.situation = situation;
        this.petdate = petdate;
        this.memn02 = memn02;
        this.petitle = petitle;
        this.petLongitude = petLongitude;
        this.petlatitude = petlatitude;
        this.petsex = petsex;
    }

    public Case() {
        super();
    }

    public String getPetname() {
        return petname;
    }

    public void setPetname(String petname) {
        this.petname = petname;
    }

    public String getPetage() {
        return petage;
    }

    public void setPetage(String petage) {
        this.petage = petage;
    }

    public Integer getPetno() {
        return petno;
    }

    public void setPetno(Integer petno) {
        this.petno = petno;
    }

    public Integer getMemno() {
        return memno;
    }

    public void setMemno(Integer memno) {
        this.memno = memno;
    }

    public Integer getBreedno() {
        return breedno;
    }

    public void setBreedno(Integer breedno) {
        this.breedno = breedno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPetcolor() {
        return petcolor;
    }

    public String getPetposition() {
        return petposition;
    }

    public void setPetposition(String petposition) {
        this.petposition = petposition;
    }

    public void setPetcolor(String petcolor) {
        this.petcolor = petcolor;

    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }
    public interface pet {
        void pet(String petname);
    }

    public String getPetsize() {
        return petsize;
    }

    public void setPetsize(String petsize) {
        this.petsize = petsize;
    }

    public String getPetic() {
        return petic;
    }

    public void setPetic(String petic) {
        this.petic = petic;
    }

    public String getTnr() {
        return tnr;
    }

    public void setTnr(String tnr) {
        this.tnr = tnr;
    }

    public Date getPetdate() {
        return petdate;
    }

    public void setPetdate(Date petdate) {
        this.petdate = petdate;
    }

    public Integer getMemn02() {
        return memn02;
    }

    public void setMemn02(Integer memn02) {
        this.memn02 = memn02;
    }

    public String getPetitle() {
        return petitle;
    }

    public void setPetitle(String petitle) {
        this.petitle = petitle;
    }

    public Double getPetLongitude() {
        return petLongitude;
    }

    public void setPetLongitude(Double petLongitude) {
        this.petLongitude = petLongitude;
    }

    public Double getPetlatitude() {
        return petlatitude;
    }

    public void setPetlatitude(Double petlatitude) {
        this.petlatitude = petlatitude;
    }

    public String getPetsex() {
        return petsex;
    }

    public void setPetsex(String petsex) {
        this.petsex = petsex;
    }


}


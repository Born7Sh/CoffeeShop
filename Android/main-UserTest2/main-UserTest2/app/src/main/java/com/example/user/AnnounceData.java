package com.example.user;

import com.google.gson.annotations.SerializedName;

public class AnnounceData {
    @SerializedName("id")
    private int AnnounceNum;
    @SerializedName("title")
    private String AnnounceTitle;
    private String AnnounceDate = "2020-09-21";
    @SerializedName("body")
    private String Announce;

    public AnnounceData (int AnnounceNum, String AnnounceTitle, String AnnounceDate, String Announce){
        this.AnnounceNum = AnnounceNum;
        this.AnnounceTitle = AnnounceTitle;
        this.AnnounceDate = AnnounceDate;
        this.Announce = Announce;
    }
    public AnnounceData(int AnnounceNum, String AnnounceTitle, String Annoucne)
    {
        this.AnnounceNum = AnnounceNum;
        this.AnnounceTitle = AnnounceTitle;
        this.Announce = Announce;
        this.AnnounceDate = "2020-09-21";
    }
    public String getAnnounce() {
        return Announce;
    }

    public void setAnnounce(String announce) {
        Announce = announce;
    }

    public int getAnnounceNum() {
        return AnnounceNum;
    }

    public void setAnnounceNum(int announceNum) {
        AnnounceNum = announceNum;
    }

    public String getAnnounceDate() {
        return AnnounceDate;
    }

    public String getAnnounceTitle() {
        return AnnounceTitle;
    }

    public void setAnnounceDate(String announceDate) {
        AnnounceDate = announceDate;
    }

    public void setAnnounceTitle(String announceTitle) {
        AnnounceTitle = announceTitle;
    }
}

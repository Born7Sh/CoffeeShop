package com.example.user;

public class AnnounceData {
    private int AnnounceNum;
    private String AnnounceTitle;
    private String AnnounceDate;
    private String Announce;

    public AnnounceData (int AnnounceNum, String AnnounceTitle, String AnnounceDate, String Announce){
        this.AnnounceNum = AnnounceNum;
        this.AnnounceTitle = AnnounceTitle;
        this.AnnounceDate = AnnounceDate;
        this.Announce = Announce;
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

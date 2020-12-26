package com.example.user;

public class ComFavorite {
    int id;
    int uid;
    int cno;

    public ComFavorite(int uid, int cno){
        this.uid = uid;
        this.cno = cno;
    }

    public void setId(int id){
        this.id = id;
    }
}

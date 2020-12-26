package com.example.user;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ComFavoriteAdapter {
    private ArrayList<ComFavorite> listFavorite = new ArrayList<ComFavorite>();



    public void addFavorite(ComFavorite f1){
        this.listFavorite.add(f1);
    }

    public ArrayList getList(){
        return this.listFavorite;
    }

    public int getCount(){
        return this.listFavorite.size();
    }

    public int getUid(int index){
        return this.listFavorite.get(index).uid;
    }
    public int getCno(int index){
        return this.listFavorite.get(index).cno;
    }
}
package com.example.user;

import java.util.ArrayList;
import java.util.List;

public class Book {
    List<Boolean> TimeList;

    public Book(){
        TimeList = new ArrayList<Boolean>(48);
        for(int i=0; i<48; i++) {
            TimeList.add(false);
        }
    }

    public void setTime(int index, boolean set){
        this.TimeList.set(index,set);
    }
}

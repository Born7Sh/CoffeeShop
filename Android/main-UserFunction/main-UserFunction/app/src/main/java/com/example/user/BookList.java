package com.example.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BookList implements Serializable {
    List<Integer> BookList;

    public BookList(){
        BookList = new ArrayList<Integer>(48);
        for(int i=0; i<48; i++) {
            BookList.add(0);
        }
    }

    public void setTime(int index, int set){
        this.BookList.set(index,set);
    }

    public void setBook1(Booking1 book1){
        int im =  book1.t0000 ? 1 : 0;
        this.BookList.set(0,this.BookList.get(0) + (book1.sno*im));
        im = book1.t0030 ? 1 : 0;
        this.BookList.set(1,this.BookList.get(1) + (book1.sno*im));
        im =  book1.t0100 ? 1 : 0;
        this.BookList.set(2,this.BookList.get(2) + (book1.sno*im));
        im =  book1.t0130 ? 1 : 0;
        this.BookList.set(3,this.BookList.get(3) + (book1.sno*im));
        im =  book1.t0200 ? 1 : 0;
        this.BookList.set(4,this.BookList.get(4) + (book1.sno*im));
        im =  book1.t0230 ? 1 : 0;
        this.BookList.set(5,this.BookList.get(5) + (book1.sno*im));
        im =  book1.t0300 ? 1 : 0;
        this.BookList.set(6,this.BookList.get(6) + (book1.sno*im));
        im =  book1.t0330 ? 1 : 0;
        this.BookList.set(7,this.BookList.get(7) + (book1.sno*im));
        im =  book1.t0400 ? 1 : 0;
        this.BookList.set(8,this.BookList.get(8) + (book1.sno*im));
        im =  book1.t0430 ? 1 : 0;
        this.BookList.set(9,this.BookList.get(9) + (book1.sno*im));
        im =  book1.t0500 ? 1 : 0;
        this.BookList.set(10,this.BookList.get(10) + (book1.sno*im));
        im =  book1.t0530 ? 1 : 0;
        this.BookList.set(11,this.BookList.get(11) + (book1.sno*im));
        im =  book1.t0600 ? 1 : 0;
        this.BookList.set(12,this.BookList.get(12) + (book1.sno*im));
        im =  book1.t0630 ? 1 : 0;
        this.BookList.set(13,this.BookList.get(13) + (book1.sno*im));
        im =  book1.t0700 ? 1 : 0;
        this.BookList.set(14,this.BookList.get(14) + (book1.sno*im));
        im =  book1.t0730 ? 1 : 0;
        this.BookList.set(15,this.BookList.get(15) + (book1.sno*im));
        im =  book1.t0800 ? 1 : 0;
        this.BookList.set(16,this.BookList.get(16) + (book1.sno*im));
        im =  book1.t0830 ? 1 : 0;
        this.BookList.set(17,this.BookList.get(17) + (book1.sno*im));
    }

    public void setBook2(Booking2 book2){
        int im = book2.t0900 ? 1: 0;
        this.BookList.set(18,this.BookList.get(18) + (book2.sno*im));
        im = book2.t0930 ? 1 : 0;
        this.BookList.set(19,this.BookList.get(19) + (book2.sno*im));
        im = book2.t1000 ? 1 : 0;
        this.BookList.set(20,this.BookList.get(20) + (book2.sno*im));
        im = book2.t1030 ? 1 : 0;
        this.BookList.set(21,this.BookList.get(21) + (book2.sno*im));
        im = book2.t1100 ? 1 : 0;
        this.BookList.set(22,this.BookList.get(22) + (book2.sno*im));
        im = book2.t1130 ? 1 : 0;
        this.BookList.set(23,this.BookList.get(23) + (book2.sno*im));
        im = book2.t1200 ? 1 : 0;
        this.BookList.set(24,this.BookList.get(24) + (book2.sno*im));
        im = book2.t1230 ? 1 : 0;
        this.BookList.set(25,this.BookList.get(25) + (book2.sno*im));
        im = book2.t1300 ? 1 : 0;
        this.BookList.set(26,this.BookList.get(26) + (book2.sno*im));
        im = book2.t1330 ? 1 : 0;
        this.BookList.set(28,this.BookList.get(27) + (book2.sno*im));
        im = book2.t1400 ? 1 : 0;
        this.BookList.set(28,this.BookList.get(28) + (book2.sno*im));
        im = book2.t1430 ? 1 : 0;
        this.BookList.set(29,this.BookList.get(29) + (book2.sno*im));
        im = book2.t1500 ? 1 : 0;
        this.BookList.set(30,this.BookList.get(30) + (book2.sno*im));
        im = book2.t1530 ? 1 : 0;
        this.BookList.set(31,this.BookList.get(31) + (book2.sno*im));
        im = book2.t1600 ? 1 : 0;
        this.BookList.set(32,this.BookList.get(32) + (book2.sno*im));
        im = book2.t1630 ? 1 : 0;
        this.BookList.set(33,this.BookList.get(33) + (book2.sno*im));
    }

    public void setBook3(Booking3 book3){
        int im = book3.t1700 ? 1 : 0;
        this.BookList.set(34,this.BookList.get(34) + (book3.sno*im));
        im = book3.t1730 ? 1 : 0;
        this.BookList.set(35,this.BookList.get(35) + (book3.sno*im));
        im = book3.t1800 ? 1 : 0;
        this.BookList.set(36,this.BookList.get(36) + (book3.sno*im));
        im = book3.t1830 ? 1 : 0;
        this.BookList.set(37,this.BookList.get(37) + (book3.sno*im));
        im = book3.t1900 ? 1 : 0;
        this.BookList.set(38,this.BookList.get(38) + (book3.sno*im));
        im = book3.t1930 ? 1 : 0;
        this.BookList.set(39,this.BookList.get(39) + (book3.sno*im));
        im = book3.t2000 ? 1 : 0;
        this.BookList.set(40,this.BookList.get(40) + (book3.sno*im));
        im = book3.t2030 ? 1 : 0;
        this.BookList.set(41,this.BookList.get(41) + (book3.sno*im));
        im = book3.t2100 ? 1 : 0;
        this.BookList.set(42,this.BookList.get(42) + (book3.sno*im));
        im = book3.t2130 ? 1 : 0;
        this.BookList.set(43,this.BookList.get(43) + (book3.sno*im));
        im = book3.t2200 ? 1 : 0;
        this.BookList.set(44,this.BookList.get(44) + (book3.sno*im));
        im = book3.t2230 ? 1 : 0;
        this.BookList.set(45,this.BookList.get(45) + (book3.sno*im));
        im = book3.t2300 ? 1 : 0;
        this.BookList.set(46,this.BookList.get(46) + (book3.sno*im));
        im = book3.t2330 ? 1 : 0;
        this.BookList.set(47,this.BookList.get(47) + (book3.sno*im));

    }
}

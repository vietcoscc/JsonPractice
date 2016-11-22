package com.example.vaio.object;

/**
 * Created by vaio on 11/21/2016.
 */

public class ItemObject {
    private String type;
    private String imageurl;
    private String muatienmat;
    private String muack;
    private String bantienmat;
    private String banck;

    public ItemObject(String type, String imageurl, String muatienmat, String muack, String bantienmat, String banck) {
        this.type = type;
        this.imageurl = imageurl;
        this.muatienmat = muatienmat;
        this.muack = muack;
        this.bantienmat = bantienmat;
        this.banck = banck;
    }

    public String getType() {
        return type;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getMuatienmat() {
        return muatienmat;
    }

    public String getMuack() {
        return muack;
    }

    public String getBantienmat() {
        return bantienmat;
    }

    public String getBanck() {
        return banck;
    }

    @Override
    public String toString() {
        return type.toLowerCase()+"_"+muatienmat+"_"+bantienmat+"_"+muack+"_"+banck;
    }
}

package com.nakashita.tpmobile.modele;

import android.os.Parcel;
import android.os.Parcelable;

public class Color implements Parcelable {


    public final static int HEXA = 0;
    public final static int RGB = 1;

    private int displayState;

    private int r;
    private int g;
    private int b;
    private String hexaString;
    private int hexa;


    public Color(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
        this.hexaString = ("#" + Integer.toHexString(0x100 | r).substring(1) + "" + Integer.toHexString(0x100 | g).substring(1) + "" + Integer.toHexString(0x100 | b).substring(1)).toUpperCase();
        this.hexa = ((255<<24)|(r&0x0ff)<<16)|((g&0x0ff)<<8)|(b&0x0ff);
    }

    public Color(String hexaString){
        this.hexaString = hexaString;
        this.r = Integer.valueOf(hexaString.substring(1,3), 16);
        this.g = Integer.valueOf(hexaString.substring(3,5), 16);
        this.b = Integer.valueOf(hexaString.substring(5,7), 16);
        this.hexa = ((255<<24)|(r&0x0ff)<<16)|((g&0x0ff)<<8)|(b&0x0ff);
    }

    public Color(int intWithAlpha){
        this.hexaString = "#" + Integer.toHexString(intWithAlpha).substring(2,8);
        this.r = Integer.valueOf(hexaString.substring(1,3), 16);
        this.g = Integer.valueOf(hexaString.substring(3,5), 16);
        this.b = Integer.valueOf(hexaString.substring(5,7), 16);
        this.hexa = ((255<<24)|(r&0x0ff)<<16)|((g&0x0ff)<<8)|(b&0x0ff);
    }

    protected Color(Parcel in) {
        displayState = in.readInt();
        r = in.readInt();
        g = in.readInt();
        b = in.readInt();
        hexaString = in.readString();
        hexa = in.readInt();
    }

    public static final Creator<Color> CREATOR = new Creator<Color>() {
        @Override
        public Color createFromParcel(Parcel in) {
            return new Color(in);
        }

        @Override
        public Color[] newArray(int size) {
            return new Color[size];
        }
    };


    public String getHexaString() {
        displayState = HEXA;
        return hexaString;
    }

    public String getRgbString() {
        displayState = RGB;
        return "RGB("+r+","+g+","+b+")";
    }

    public int getHexa(){
        displayState = HEXA;
        return hexa;
    }

    public int getDisplayState() {
        return displayState;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    @Override
    public String toString(){
        return hexaString;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(displayState);
        dest.writeInt(r);
        dest.writeInt(g);
        dest.writeInt(b);
        dest.writeString(hexaString);
        dest.writeInt(hexa);
    }
}

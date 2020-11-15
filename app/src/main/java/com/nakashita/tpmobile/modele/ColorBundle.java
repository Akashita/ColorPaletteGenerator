package com.nakashita.tpmobile.modele;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ColorBundle implements Parcelable {

    private static int ID = 1;

    private int id;
    public final static int COLOR1 = 1;
    public final static int COLOR2 = 2;
    public final static int COLOR3 = 3;

    private Color color1;
    private Color color2;
    private Color color3;

    private String label;

    public ColorBundle(){
        this.label = "";
        this.id = ID++;
        //We do the average between on temporary color and the 3 generated colors.
        //This operation permit to have a palette with 3 colors which form an aesthetic whole (like pastel colors)
        Color colorTMP = new Color((int) (Math.random()*255), (int) (Math.random()*255), (int) (Math.random()*255));
        this.color1 = average(new Color((int) (Math.random()*255), (int) (Math.random()*255), (int) (Math.random()*255)), colorTMP);
        this.color2 = average(new Color((int) (Math.random()*255), (int) (Math.random()*255), (int) (Math.random()*255)), colorTMP);
        this.color3 = average(new Color((int) (Math.random()*255), (int) (Math.random()*255), (int) (Math.random()*255)), colorTMP);
    }

    public ColorBundle(int id, String label, String color1, String color2, String color3){
        this.id = id;
        this.label = label;
        this.color1 = new Color(color1);
        this.color2 = new Color(color2);
        this.color3 = new Color(color3);
    }


    public ColorBundle(String label, Color color1, Color color2, Color color3){
        this.id = ID++;
        this.label = label;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
    }


    //Recreating a color palette from Parcels (useful after an activity is destroyed to recreate all it's attributes)
    protected ColorBundle(Parcel in) {
        ID = in.readInt();
        id = in.readInt();
        color1 = in.readParcelable(Color.class.getClassLoader());
        color2 = in.readParcelable(Color.class.getClassLoader());
        color3 = in.readParcelable(Color.class.getClassLoader());
        label = in.readString();
    }

    private Color average(Color color1, Color color2){
        int r = (color1.getR() + color2.getR())/2;
        int g = (color1.getG() + color2.getG())/2;
        int b = (color1.getB() + color2.getB())/2;
        return new Color(r,g,b);
    }

    public static final Creator<ColorBundle> CREATOR = new Creator<ColorBundle>() {
        @Override
        public ColorBundle createFromParcel(Parcel in) {
            return new ColorBundle(in);
        }

        @Override
        public ColorBundle[] newArray(int size) {
            return new ColorBundle[size];
        }
    };

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public int getId() {
        return id;
    }

    public Color getColor(int colorIdent){
        Color res = null;
        switch (colorIdent){
            case COLOR1 :
                res = color1;
                break;
            case COLOR2 :
                res = color2;
                break;
            case COLOR3 :
                res = color3;
                break;
        }

        return res;
    }

    @Override
    public String toString(){
        return  color1.toString()+" | "+color2.toString()+" | "+color3.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeInt(id);
        dest.writeParcelable(color1, flags);
        dest.writeParcelable(color2, flags);
        dest.writeParcelable(color3, flags);
        dest.writeString(label);
    }
}

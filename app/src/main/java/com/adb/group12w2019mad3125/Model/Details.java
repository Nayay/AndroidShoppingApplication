package com.adb.group12w2019mad3125.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Details implements Parcelable {
    private String image,pname,price,quantity;

    public Details() {
    }

    public Details(String image, String pname, String price, String quantity) {
        this.image = image;
        this.pname = pname;
        this.price = price;
        this.quantity = quantity;
    }

    protected Details(Parcel in) {
        image = in.readString();
        pname = in.readString();
        price = in.readString();
        quantity = in.readString();
    }

    public static final Creator<Details> CREATOR = new Creator<Details>() {
        @Override
        public Details createFromParcel(Parcel in) {
            return new Details(in);
        }

        @Override
        public Details[] newArray(int size) {
            return new Details[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(pname);
        dest.writeString(price);
        dest.writeString(quantity);
    }
}

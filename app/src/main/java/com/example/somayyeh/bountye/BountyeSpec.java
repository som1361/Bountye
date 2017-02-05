package com.example.somayyeh.bountye;

import android.graphics.Bitmap;

import java.io.Serializable;


public class BountyeSpec implements Serializable {

    private String mSellerName;
    private Bitmap mItemImage;
    private String mTitle;
    private String mPrice;
    private Bitmap mSellerPhoto;
    private String mPickupLocation;

    public BountyeSpec(String sellerName, Bitmap itemImage, String title, String price, Bitmap sellerPhoto, String pickupLocation) {
        mItemImage = itemImage;
        mSellerName = sellerName;
        mTitle = title;
        mPrice = price;
        mSellerPhoto = sellerPhoto;
        mPickupLocation = pickupLocation;
    }

    public String getSellerName() {
        return mSellerName;

    }

    public Bitmap getItemImage() {
        return mItemImage;
    }


    public String getTitle() {
        return mTitle;

    }

    public String getPrice() {
        return mPrice;

    }

    public Bitmap getSellerPhoto() {
        return mSellerPhoto;

    }

    public String getPickupLocation() {
        return mPickupLocation;

    }

}

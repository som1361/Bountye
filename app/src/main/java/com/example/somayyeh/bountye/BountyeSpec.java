package com.example.somayyeh.bountye;

import android.graphics.Bitmap;

import java.io.Serializable;


public class BountyeSpec implements Serializable {

    private String mSellerName;
    private String mItemImage;
    private String mTitle;
    private String mPrice;
    private String mSellerPhoto;
    private String mPickupLocation;

    public BountyeSpec(String sellerName, String itemImage, String title, String price, String sellerPhoto, String pickupLocation) {
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

    public String getItemImage() {
        return mItemImage;
    }


    public String getTitle() {
        return mTitle;

    }

    public String getPrice() {
        return mPrice;

    }

    public String getSellerPhoto() {
        return mSellerPhoto;

    }

    public String getPickupLocation() {
        return mPickupLocation;

    }

}

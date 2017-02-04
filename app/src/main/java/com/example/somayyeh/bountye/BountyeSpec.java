package com.example.somayyeh.bountye;

import android.graphics.Bitmap;

import java.io.Serializable;


public class BountyeSpec implements Serializable {

    private String mSellerName;
    private Bitmap mItemImage;

    public BountyeSpec(String sellerName, Bitmap itemImage) {
        mItemImage = itemImage;
        mSellerName = sellerName;
    }

    public String getSellerName() {
        return mSellerName;

    }

    public Bitmap getItemImage() {
        return mItemImage;
    }

}

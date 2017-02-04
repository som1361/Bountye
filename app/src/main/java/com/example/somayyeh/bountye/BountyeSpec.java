package com.example.somayyeh.bountye;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by somayyeh on 7/31/16.
 */
public class BountyeSpec implements Serializable
{
    private String mSellerName;
    private Bitmap mSellerImage;

    public BountyeSpec(String sellerName, Bitmap sellerImage)
    {
      mSellerImage = sellerImage;
        mSellerName = sellerName;
    }
    public String getSellerName()
    {
        return mSellerName;

    }
    public Bitmap getmSellerImage()
    {
        return mSellerImage;
    }

}

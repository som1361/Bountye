package com.example.somayyeh.bountye;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BountyeDataParser
{
    public static List<BountyeSpec> extractBountyeData(String jsonResponse) throws JSONException, IOException {
        List<BountyeSpec> bountyesList = new ArrayList<BountyeSpec>();
        String sellerName = "";
        Bitmap bmp = null;
        String title = "";
        Bitmap photo = null;

        JSONArray root = new JSONArray(jsonResponse);
        int i;
        for (i = 0; i < root.length(); i++) {

            JSONObject items = root.getJSONObject(i);

            title = items.getString("title");

            JSONObject seller = items.getJSONObject("seller");
            sellerName = seller.getString("firstName");

            String sellerImage = seller.getString("photo");

            JSONArray photos = items.getJSONArray("photos");
            JSONObject photoElements = photos.optJSONObject(0);
            String itemImage =null;
            if (photoElements != null) {
                itemImage = photoElements.getString("imageUrl");
            }

            JSONObject pickupLocation = items.getJSONObject("pickupLocation");
            String location = pickupLocation.getString("suburb") + ", " + pickupLocation.getString("state");

            String price = items.getString("price");


            BountyeSpec bountyeItem = new BountyeSpec(sellerName, itemImage, title, price, sellerImage, location);
            bountyesList.add(bountyeItem);
        }

        return bountyesList;

    }


}

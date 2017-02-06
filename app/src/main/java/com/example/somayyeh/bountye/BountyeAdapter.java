package com.example.somayyeh.bountye;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class BountyeAdapter extends ArrayAdapter<BountyeSpec> {

    public BountyeAdapter(Context context, ArrayList<BountyeSpec> things) {
        super(context, 0, things);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridItemView = convertView;
        if (gridItemView == null) {
            gridItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.everything_item, parent, false);
        }

        BountyeSpec currentItem = getItem(position);
        ImageView itemImage = (ImageView) gridItemView.findViewById(R.id.itemImage);
        Glide.with(getContext())
                .load(currentItem.getItemImage())
                .into(itemImage);

       // itemImage.setImageBitmap(currentItem.getItemImage());
        TextView itemName = (TextView) gridItemView.findViewById(R.id.itemName);
        itemName.setText("" + currentItem.getTitle());
        TextView price= (TextView) gridItemView.findViewById(R.id.itemPrice);
        price.setText("" + currentItem.getPrice());
        ImageView sellerImage = (ImageView) gridItemView.findViewById(R.id.sellerPhoto);
        Glide.with(getContext())
                .load(currentItem.getSellerPhoto())
                .into(sellerImage);
       // sellerImage.setImageBitmap(currentItem.getSellerPhoto());
        TextView sellerName = (TextView) gridItemView.findViewById(R.id.seller_name);
        sellerName.setText("" + currentItem.getSellerName());
        TextView sellerLocation = (TextView) gridItemView.findViewById(R.id.seller_location);
        sellerLocation.setText("" + currentItem.getPickupLocation());


        return gridItemView;

    }
}

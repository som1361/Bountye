package com.example.somayyeh.bountye;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class BountyeAdapter extends ArrayAdapter<BountyeSpec> {
    public BountyeAdapter(Context context, ArrayList<BountyeSpec> things) {
        super(context, 0 ,things);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridItemView = convertView;
        if(gridItemView == null) {
            gridItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.everything_item, parent, false);
        }

        BountyeSpec currentItem = getItem(position);
        ImageView itemImage = (ImageView) gridItemView.findViewById(R.id.itemImage);
        itemImage.setImageBitmap(currentItem.getmSellerImage());
        TextView itemName = (TextView) gridItemView.findViewById(R.id.itemName);
        itemName.setText("" + currentItem.getSellerName());

        return gridItemView;

    }
}

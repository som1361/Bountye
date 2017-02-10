package com.example.somayyeh.bountye;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class SellItemFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView currentImageView;
    private ImageView photo1, photo2, photo3, photo4,photo5 ;
    ImageView[] IMGS = { photo1, photo2, photo3,photo4, photo5 };
    private int[] myImgViews = {R.id.ImageView1,R.id.ImageView2,R.id.ImageView3,R.id.ImageView4,R.id.ImageView5};
    Bitmap bitmap=null;

    private View rootView;


    public SellItemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_sell_item, container, false);
        ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.sellProgressBar);
        progressBar.setVisibility(View.GONE);
        for(int i = 0; i < 5; i++) {
            IMGS[i]=(ImageView)rootView.findViewById(myImgViews[i]);
            IMGS[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   currentImageView = (ImageView)v;
                    selectPhoto();


                }
            });

        }

        return rootView;

    }



    public void selectPhoto() {

       Intent takePictureIntent = new Intent("android.media.action.IMAGE_CAPTURE" );
        File f = new File(android.os.Environment.getExternalStorageDirectory(), "user_photo.jpg");
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("user_photo.jpg")) {
                        f = temp;
                        break;
                    }
                }


                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                        bitmapOptions);

                currentImageView.setImageBitmap(bitmap);
                ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.sellProgressBar);
                progressBar.setVisibility(View.VISIBLE);
                    new PostUtils(getActivity()).execute(bitmap);
                // String result = PostUtils.fetchUploadeData(Url, bitmap);
                   // String result = "You are uploading the photo";
                  //  Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();


            }


        }
    }


    }


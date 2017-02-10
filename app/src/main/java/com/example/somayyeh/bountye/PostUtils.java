package com.example.somayyeh.bountye;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class PostUtils extends AsyncTask<Bitmap, Void, String> {

    private static final String LOG_TAG = PostUtils.class.getSimpleName();
    private static final String UPLOAD_URL = "http://dev.api.bountye.com/api/user/avatar";
    private static URL url;
    private static  FragmentActivity activity;
    private static String JsonResponse;

    public PostUtils(FragmentActivity a) {
        activity = a;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Bitmap... params)
    {
        try {
           url = new URL(UPLOAD_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {

            makeHttpRequest(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* try {
            return extractUploadData();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
         if (result==null)
         {
             result="Upload failed.";
         }
       Toast.makeText(activity, result , Toast.LENGTH_LONG).show();
        ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.sellProgressBar);
        progressBar.setVisibility(View.GONE);
    }

    public static void makeHttpRequest(Bitmap bitmap) throws IOException
    {
        //Static stuff
      //  String attachmentName = "temp";
        String attachmentFileName = "user_photo";

        //set up request
        HttpURLConnection connection = null;
        URL url = new URL(UPLOAD_URL);
        connection = (HttpURLConnection) url.openConnection();
        //it includes a request body
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Cache-Control", "no-cache");
        connection.setRequestProperty("Content-Type", "multipart/form-data");

        //convert Bitmap to PNG
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
       // String image = Base64.encodeToString(byteArray, Base64.DEFAULT);

        DataOutputStream outputStream = new DataOutputStream(
                connection.getOutputStream());


        outputStream.writeBytes("Content-Disposition: form-data; name=\"" +
                attachmentFileName + "\"");
        outputStream.write(byteArray);

        outputStream.flush();
        outputStream.close();

       // Get response
        InputStream responseStream = new
                BufferedInputStream(connection.getInputStream());

        BufferedReader responseStreamReader =
                new BufferedReader(new InputStreamReader(responseStream));

        String line = "";
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = responseStreamReader.readLine()) != null)
        {
            stringBuilder.append(line).append("\n");
        }
        responseStreamReader.close();

        JsonResponse = stringBuilder.toString();
       // System.out.println("json response is:" + JsonResponse);
        //Toast.makeText(context, JsonResponse , Toast.LENGTH_LONG).show();

        //Close response stream

        responseStream.close();
        connection.disconnect();

    }


    public static String extractUploadData() throws JSONException, IOException
    {
        String result="";
        JSONObject root = new JSONObject(JsonResponse);
        result = root.getString("originalFileName");

        return result;
    }


}
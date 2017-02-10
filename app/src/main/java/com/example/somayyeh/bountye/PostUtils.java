package com.example.somayyeh.bountye;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
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
    private static Context context;
    private static String JsonResponse;

    public PostUtils(Context c) {
        context = c;
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
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
       Toast.makeText(context, s , Toast.LENGTH_LONG).show();

    }

    public static void makeHttpRequest(Bitmap bitmap) throws IOException
    {
        //Static stuff
        String attachmentName = "user_photo";
        String attachmentFileName = "user_photo.png";



        //set up request
        HttpURLConnection connection = null;
        URL url = new URL(UPLOAD_URL);
        connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Cache-Control", "no-cache");
        connection.setRequestProperty(
                "Content-Type", "multipart/form-data");
        connection.setRequestProperty("","");

        //convert Bitmap to PNG
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
       // String image = Base64.encodeToString(byteArray, Base64.DEFAULT);

      //  OutputStream outputStream = connection.getOutputStream();
        DataOutputStream outputStream = new DataOutputStream(
                connection.getOutputStream());


        outputStream.writeBytes("Content-Disposition: form-data; name=\"" +
                attachmentName + "\";filename=\"" +
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

        while ((line = responseStreamReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        responseStreamReader.close();

        JsonResponse = stringBuilder.toString();
       // System.out.println("json response is:" + JsonResponse);
        //Toast.makeText(context, JsonResponse , Toast.LENGTH_LONG).show();

        //Close response stream:

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
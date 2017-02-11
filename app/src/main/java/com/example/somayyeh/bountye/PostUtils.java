package com.example.somayyeh.bountye;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

           return makeHttpRequest(params[0]);
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
        Log.v(LOG_TAG, "Json response is: " + result);

        if (result==null)
         {
             result="Upload failed.";
         }

        Toast.makeText(activity, result , Toast.LENGTH_LONG).show();
        ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.sellProgressBar);
        progressBar.setVisibility(View.GONE);
    }

    public static String makeHttpRequest(Bitmap bitmap) throws IOException {

        String twoHyphens = "--";
        String boundary =  "*****";
        String lineEnd = "\r\n";

        String filePath = Environment.getExternalStorageDirectory()  +"/"+ "user_photo";
        String fileName = null;
        File sourceFile = new File(filePath);

        if (!sourceFile.isFile()) {

            Log.e("uploadFile", "Source File not exist :" + fileName);
            return "Source File not exist";
        }

            //set up request
        HttpURLConnection connection = null;
        URL url = new URL(UPLOAD_URL);
        InputStream inputStream = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            connection.setRequestProperty("uploaded_file", fileName);

            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
            dos.writeBytes(lineEnd + twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"userfile\"; filename=\"" + fileName + "\"" + lineEnd);
            dos.writeBytes("Content-Type: application/octet-stream" + lineEnd);
            dos.writeBytes(lineEnd);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, dos);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            dos.flush();
            dos.close();

            inputStream = connection.getInputStream();
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            JsonResponse = output.toString();

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the bountye JSON results.", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return JsonResponse;
    }

    public static String extractUploadData() throws JSONException, IOException
    {
        String result="";
        JSONObject root = new JSONObject(JsonResponse);
        result = root.getString("originalFileName");

        return result;
    }


}
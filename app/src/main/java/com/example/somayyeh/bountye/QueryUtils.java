package com.example.somayyeh.bountye;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public static List<BountyeSpec> fetchBountyeData(String url) throws JSONException, IOException {
        List<BountyeSpec> bountyeList = null;
        try {
            URL murl = createUrl(url);
            String jsonResponse = makeHttpRequest(murl);
            bountyeList = extractBountyeData(jsonResponse);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return bountyeList;

    }

    public static URL createUrl(String url) throws MalformedURLException {
        return new URL(url);
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);
              //  urlConnection.disconnect();
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the bountye JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    public static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        String result = "";
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        result = output.toString();
        Log.v(LOG_TAG, "response is: " + result);
        return result;
    }

    public static List<BountyeSpec> extractBountyeData(String jsonResponse) throws JSONException, IOException {
        List<BountyeSpec> bountyesList = new ArrayList<BountyeSpec>();
        String sellerName = "";
        Bitmap bmp = null;
        String title = "";
        Bitmap photo = null;

        Log.v(LOG_TAG,jsonResponse);

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
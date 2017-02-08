package com.example.somayyeh.bountye;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

class BountyLoader extends AsyncTaskLoader<List<BountyeSpec>> {

    private String mUrl;

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    public BountyLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public List<BountyeSpec> loadInBackground() {
        if (mUrl == null)
            return null;
        try {

            return QueryUtils.fetchBountyeData( mUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

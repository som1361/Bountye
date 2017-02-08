package com.example.somayyeh.bountye;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class everyThingFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<BountyeSpec>> {

    private static final int DEFAULT_THRESHOLD = 20;
    private boolean firstLoad = true;
    private BountyeAdapter bountyeAdapter;
    private View rootView;
    private boolean isLoading = false;
    private int offset=0;
    LoaderManager loaderManager;
    private List<BountyeSpec> bufferedData;

    public String bountyeUrl;
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public everyThingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.everything_list, container, false);
        bountyeAdapter = new BountyeAdapter(getActivity(), new ArrayList<BountyeSpec>());
        GridView list = (GridView) rootView.findViewById(R.id.gridview);
        list.setAdapter(bountyeAdapter);

        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if ( isLoading && bufferedData.size()>0 && firstVisibleItem + visibleItemCount >= bountyeAdapter.getCount()) {
                    loadMore();
                }
            }

        });

        //check network connectivity
        ConnectivityManager myCon = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = myCon.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {

            loaderManager = getActivity().getLoaderManager();
            if (firstLoad)
            {
                loaderManager.initLoader(0, null, this);
            }
            loaderManager.initLoader(1, null, this);


        } else {
            ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            TextView emptyState = (TextView) rootView.findViewById(R.id.emptyList);
            emptyState.setText(R.string.no_network_data);
        }

        return rootView;


    }

    public void loadMore()

    {

           // bountyeAdapter.clear();

            bountyeAdapter.addAll(bufferedData);
            loaderManager.restartLoader(1, null, this);

    }


    @Override
    public Loader<List<BountyeSpec>> onCreateLoader(int id, Bundle args) {
        createUrl();
        return new BountyLoader(getContext(), bountyeUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<BountyeSpec>> loader, List<BountyeSpec> data)
    {
        switch (loader.getId())
        {
            case 0:
                if (data != null && !data.isEmpty()) {
                    bountyeAdapter.addAll(data);
                    getActivity().getLoaderManager().destroyLoader(0);
                }
                break;
            case 1:
                if (data != null && !data.isEmpty()) {
                    bufferedData = new ArrayList<BountyeSpec>(data);
                    isLoading = true;
                }
                else
                {
                    isLoading=false;
                }

                break;
            default:


        }

        ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onLoaderReset(Loader<List<BountyeSpec>> loader) {
        bountyeAdapter.clear();

    }
    public void createUrl()
    {
        if (firstLoad)

            firstLoad=false;

        else
            offset += DEFAULT_THRESHOLD;

        bountyeUrl= "http://dev.api.bountye.com/api/user/feed?offset="+offset+"&count=20";
    }

}
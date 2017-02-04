package com.example.somayyeh.bountye;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
 public class everyThingFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<BountyeSpec>>{

    private BountyeAdapter bountyeAdapter;
    private View rootView;

    public final String BOUNTYE_URL = "http://dev.api.bountye.com/api/user/feed?offset=0&count=20";
    public everyThingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.everything_list,container,false);

        bountyeAdapter = new BountyeAdapter(getActivity(),new ArrayList<BountyeSpec>());
        GridView list = (GridView) rootView.findViewById(R.id.gridview);
        list.setAdapter(bountyeAdapter);

        //check network connectivity
        ConnectivityManager myCon = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = myCon.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnected()) {

            LoaderManager loaderManager = getActivity().getLoaderManager();
            loaderManager.initLoader(0, null, this);


        }
        else
        {
            ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            TextView emptyState = (TextView) rootView.findViewById(R.id.emptyList);
            emptyState.setText(R.string.no_network_data);
        }

        return rootView;

    }

    @Override
    public Loader<List<BountyeSpec>> onCreateLoader(int id, Bundle args) {
        return new BountyLoader(getContext(),BOUNTYE_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<BountyeSpec>> loader, List<BountyeSpec> data)
    {
        bountyeAdapter.clear();
        if (data != null && !data.isEmpty())
        {
            bountyeAdapter.addAll(data);
        }
      ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onLoaderReset(Loader<List<BountyeSpec>> loader)
    {
      bountyeAdapter.clear();

    }
}

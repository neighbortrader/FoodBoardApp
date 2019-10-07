package com.github.neighbortrader.foodboardapp.ui.offerOverview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OfferOverviewFragment extends Fragment {

    public static String TAG = OfferOverviewFragment.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView offerRecyclerView;
    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout pullToRefreshLayout;
    private Unbinder unbinder;
    private OfferOverviewController controller;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.offeroverview, container, false);

        unbinder = ButterKnife.bind(this, view);

        controller = new OfferOverviewController(this);

        offerRecyclerView = view.findViewById(R.id.recyclerView);

        linearLayoutManager = new LinearLayoutManager(ContextHandler.getAppContext());
        offerRecyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewAdapter(controller.getCurrentOffers());
        offerRecyclerView.setAdapter(adapter);

        pullToRefreshLayout.setColorSchemeResources(R.color.color_primary, R.color.color_secondary_variant, R.color.color_on_secondary);

        pullToRefreshLayout.setOnRefreshListener(
                () -> {
                    pullToRefreshLayout.setRefreshing(true);
                    controller.invokeUpdate();
                }
        );

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");

        controller.destroy();
    }

    public void updateUi(ArrayList<Offer> offerArrayList) {
        Log.d(TAG, "updateUi()");
        adapter.clearOfferList();
        adapter.setOfferList(offerArrayList);
        adapter.notifyDataSetChanged();
    }

    public void setRefreshing(boolean value) {
        pullToRefreshLayout.setRefreshing(value);
    }
}

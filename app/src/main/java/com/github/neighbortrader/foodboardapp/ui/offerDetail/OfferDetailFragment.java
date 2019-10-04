package com.github.neighbortrader.foodboardapp.ui.offerDetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.ui.offerOverview.OfferOverviewController;
import com.github.neighbortrader.foodboardapp.ui.offerOverview.OfferOverviewFragment;
import com.github.neighbortrader.foodboardapp.ui.offerOverview.RecyclerViewAdapter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OfferDetailFragment extends Fragment {
    private Unbinder unbinder;

    public static String TAG = OfferDetailFragment.class.getSimpleName();

    private OfferDetailController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.offeroverview, container, false);

        unbinder = ButterKnife.bind(this, view);

        controller = new OfferDetailController(this);


        return view;
    }
}

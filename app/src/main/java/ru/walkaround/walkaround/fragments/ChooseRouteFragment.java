package ru.walkaround.walkaround.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.walkaround.walkaround.R;
import ru.walkaround.walkaround.StubDataUtils;
import ru.walkaround.walkaround.activities.MainActivity;
import ru.walkaround.walkaround.adapters.ChooseRouteRecyclerAdapter;
import ru.walkaround.walkaround.listeners.RecyclerViewClickListener;
import ru.walkaround.walkaround.model.Route;

public class ChooseRouteFragment extends Fragment {

    private List<Route> routes = new ArrayList<>();

    public ChooseRouteFragment() {
    }

    public static ChooseRouteFragment newInstance() {
        return new ChooseRouteFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_choose_route, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.choose_route_recycler_view);

        RecyclerViewClickListener listener = (view, position) -> {
            startActivity(new Intent(getActivity(), MainActivity.class));
        };

        StubDataUtils.generateDemoRoutes(getActivity(), routes); //TODO: Remove in prod

        ChooseRouteRecyclerAdapter adapter = new ChooseRouteRecyclerAdapter(routes, getActivity(), listener);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }

}

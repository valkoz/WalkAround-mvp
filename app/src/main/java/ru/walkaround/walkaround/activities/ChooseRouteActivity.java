package ru.walkaround.walkaround.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.walkaround.walkaround.R;
import ru.walkaround.walkaround.StubDataUtils;
import ru.walkaround.walkaround.adapters.ChooseRouteRecyclerAdapter;
import ru.walkaround.walkaround.listeners.RecyclerViewClickListener;
import ru.walkaround.walkaround.model.Route;

public class ChooseRouteActivity extends AppCompatActivity {

    private List<Route> routes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_route);

        getWindow().setExitTransition(new Slide(Gravity.LEFT));
        //getWindow().setEnterTransition(new Slide(Gravity.END));

        RecyclerView recyclerView = findViewById(R.id.choose_route_recycler_view);

        RecyclerViewClickListener listener = (view, position) -> {
            Toast.makeText(this, "Position " + position, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        };

        StubDataUtils.generateDemoRoutes(this, routes); //TODO: Remove in prod

        ChooseRouteRecyclerAdapter adapter = new ChooseRouteRecyclerAdapter(routes, this, listener);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

}

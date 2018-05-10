package ru.walkaround.walkaround.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.walkaround.walkaround.R;
import ru.walkaround.walkaround.adapters.ChooseRouteRecyclerAdapter;
import ru.walkaround.walkaround.listeners.RecyclerViewClickListener;
import ru.walkaround.walkaround.model.Place;
import ru.walkaround.walkaround.model.Route;

public class ChooseRouteActivity extends AppCompatActivity {

    private List<Route> routes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_route);

        RecyclerView recyclerView = findViewById(R.id.choose_route_recycler_view);

        RecyclerViewClickListener listener = (view, position) -> {
            Toast.makeText(this, "Position " + position, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        };

        initStubRoutes();

        ChooseRouteRecyclerAdapter adapter = new ChooseRouteRecyclerAdapter(routes, this, listener);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    //TODO: remove in prod
    private void initStubRoutes() {

        List<Place> places = new ArrayList<>();
        List<String> placeNames = Arrays.asList("Bolshoy Theatre", "Ivan the Great White Bell Tower", "Kremlin");

        for (String s : placeNames) {
            Place p = new Place(s);
            p.setImage(getResources().getIdentifier(s.toLowerCase().replace(" ", ""), "drawable", getPackageName()));
            places.add(p);
        }

        routes.add(new Route("3:50", "10 км", "1000 руб", places));
        routes.add(new Route("1:30", "5.6 км", "1500 руб", places));
        routes.add(new Route("2:20", "6.2 км", "700 руб", places));

    }
}
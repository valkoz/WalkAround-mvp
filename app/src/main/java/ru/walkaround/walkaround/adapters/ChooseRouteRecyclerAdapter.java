package ru.walkaround.walkaround.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import ru.walkaround.walkaround.R;
import ru.walkaround.walkaround.model.Place;
import ru.walkaround.walkaround.model.Route;

public class ChooseRouteRecyclerAdapter extends
        RecyclerView.Adapter<ChooseRouteRecyclerAdapter.RouteViewHolder> {

    private List<Route> routeList;

    private Activity context;


    public class RouteViewHolder extends RecyclerView.ViewHolder {
        public TextView timeInMinutes;
        public TextView distance;
        public TextView cost;
        public TextView firstPlace;
        public TextView secondPlace;
        public TextView thirdPlace;
        public ImageView imageView;

        public RouteViewHolder(View view) {
            super(view);
            timeInMinutes = view.findViewById(R.id.choose_route_time);
            distance = view.findViewById(R.id.choose_route_distance);
            cost = view.findViewById(R.id.choose_route_cost);
            firstPlace = view.findViewById(R.id.choose_route_place1);
            secondPlace = view.findViewById(R.id.choose_route_place2);
            thirdPlace = view.findViewById(R.id.choose_route_place3);
            imageView = view.findViewById(R.id.choose_route_image);
        }

    }

    public ChooseRouteRecyclerAdapter(List<Route> routes, Activity context) {
        this.routeList = routes;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder holder, int position) {
        Route r = routeList.get(position);

        holder.timeInMinutes.setText(r.getMinutes());
        holder.distance.setText(r.getDistance());
        holder.cost.setText(r.getCost());

        List<Place> places = r.getPlaces().subList(0, 3);
        List<String> placeNames = new ArrayList<>();
        for (Place p : places) {
            placeNames.add(p.getName());
        }
        holder.firstPlace.setText(placeNames.get(0));
        holder.secondPlace.setText(placeNames.get(1));
        holder.thirdPlace.setText(placeNames.get(2));

        //holder.imageView.setImageResource(c.getImage());
        //USAGE: glide lib: https://github.com/bumptech/glide
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(50));

        Glide.with(context)
                .load(places.get(0).getImage())
                .apply(requestOptions)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        if (routeList != null)
            return routeList.size();
        else
            return 0;
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_route, parent, false);
        return new RouteViewHolder(v);
    }
}

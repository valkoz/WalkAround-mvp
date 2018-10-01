package ru.walkaround.walkaround.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.walkaround.walkaround.R;
import ru.walkaround.walkaround.listeners.RecyclerViewClickListener;
import ru.walkaround.walkaround.model.Place;
import ru.walkaround.walkaround.model.Route;

public class ChooseRouteRecyclerAdapter extends
        RecyclerView.Adapter<ChooseRouteRecyclerAdapter.RouteViewHolder> {

    private List<Route> routeList;
    private RecyclerViewClickListener listener;
    private Activity context;

    //OnClick implemented as here: https://android.jlelse.eu/click-listener-for-recyclerview-adapter-2d17a6f6f6c9
    public class RouteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.choose_route_time)
        TextView timeInMinutes;
        @BindView(R.id.choose_route_distance)
        TextView distance;
        @BindView(R.id.choose_route_cost)
        TextView cost;
        @BindView(R.id.choose_route_place1)
        TextView firstPlace;
        @BindView(R.id.choose_route_place2)
        TextView secondPlace;
        @BindView(R.id.choose_route_place3)
        TextView thirdPlace;
        @BindView(R.id.choose_route_image)
        ImageView imageView;

        private RecyclerViewClickListener listener;

        public RouteViewHolder(View view, RecyclerViewClickListener listener) {
            super(view);
            ButterKnife.bind(this, view);
            this.listener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i("Do what you want here", "For instance, decolor image");
//            imageView.setImageAlpha(1);
            listener.onClick(v, getAdapterPosition());
        }
    }

    public ChooseRouteRecyclerAdapter(List<Route> routes, Activity context, RecyclerViewClickListener listener) {
        this.routeList = routes;
        this.context = context;
        this.listener = listener;
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
        holder.firstPlace.setCompoundDrawablesRelativeWithIntrinsicBounds(places.get(0).getType(), 0, 0, 0);
        holder.secondPlace.setText(placeNames.get(1));
        holder.secondPlace.setCompoundDrawablesRelativeWithIntrinsicBounds(places.get(1).getType(), 0, 0, 0);
        holder.thirdPlace.setText(placeNames.get(2));
        holder.thirdPlace.setCompoundDrawablesRelativeWithIntrinsicBounds(places.get(2).getType(), 0, 0, 0);

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
        return new RouteViewHolder(v, listener);
    }
}

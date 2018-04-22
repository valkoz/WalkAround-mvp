package ru.walkaround.walkaround;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CategoryRecyclerAdapter extends
        RecyclerView.Adapter<CategoryRecyclerAdapter.MyViewHolder> {

    private List<Category> categoryList;

    private Activity context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView countryText;
        public CheckBox checkBox;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            countryText = view.findViewById(R.id.label);
            checkBox = view.findViewById(R.id.check);
            imageView = view.findViewById(R.id.image_category);
        }

    }

    public CategoryRecyclerAdapter(List<Category> countryList, Activity context) {
        this.categoryList = countryList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category c = categoryList.get(position);
        holder.countryText.setText(c.getName());
        holder.checkBox.setPressed(c.isSelected());

        //holder.imageView.setImageResource(c.getImage());
        //USAGE: glide lib: https://github.com/bumptech/glide
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(50));

        Glide.with(context)
                .load(c.getImage())
                .apply(requestOptions)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category,parent, false);
        return new MyViewHolder(v);
    }
}

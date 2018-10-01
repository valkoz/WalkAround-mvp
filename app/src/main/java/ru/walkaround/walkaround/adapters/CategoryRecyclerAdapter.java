package ru.walkaround.walkaround.adapters;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.walkaround.walkaround.R;
import ru.walkaround.walkaround.listeners.RecyclerViewClickListener;
import ru.walkaround.walkaround.model.Category;

public class CategoryRecyclerAdapter extends
        RecyclerView.Adapter<CategoryRecyclerAdapter.MyViewHolder> {

    private final static int OPAQUE = 0b11111111;
    private final static int TRANSPERENT = 0b01111111;

    private List<Category> categoryList;

    private Activity context;

    private RecyclerViewClickListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.category_label)
        TextView countryText;
        @BindView(R.id.category_image)
        ImageView imageView;
        @BindView(R.id.category_check_box)
        CheckBox checkBox;

        private RecyclerViewClickListener listener;

        public MyViewHolder(View view, RecyclerViewClickListener listener) {
            super(view);
            ButterKnife.bind(this, view);
            this.listener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (imageView.isSelected()) {
                imageView.setSelected(false);
                checkBox.setChecked(false);
                imageView.setImageAlpha(TRANSPERENT);
            } else {
                imageView.setSelected(true);
                checkBox.setChecked(true);
                imageView.setImageAlpha(OPAQUE);
            }
            listener.onClick(v, getAdapterPosition());
        }
    }

    public CategoryRecyclerAdapter(List<Category> countryList, Activity context, RecyclerViewClickListener listener) {
        this.categoryList = countryList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category c = categoryList.get(position);
        holder.countryText.setText(c.getName());

        //holder.imageView.setImageResource(c.getImage());
        //USAGE: glide lib: https://github.com/bumptech/glide
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(50));

        Glide.with(context)
                .load(c.getImage())
                .apply(requestOptions)
                .into(holder.imageView);

        holder.imageView.setImageAlpha(TRANSPERENT);

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
        return new MyViewHolder(v, listener);
    }
}

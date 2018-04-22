package ru.walkaround.walkaround;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CategoryArrayAdapter extends ArrayAdapter<Category> {

    private final Activity context;
    private final List<Category> data;

    public CategoryArrayAdapter(@NonNull Activity context, List<Category> data) {
        super(context, R.layout.item_category, R.id.label, data);
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.item_category, parent, false);
        TextView textView = rowView.findViewById(R.id.label);
        CheckBox checkBox = rowView.findViewById(R.id.check);
        ImageView imageView = rowView.findViewById(R.id.image_category);
        imageView.setImageResource(data.get(position).getImage());
        textView.setText(data.get(position).getName());

        return rowView;
    }
}

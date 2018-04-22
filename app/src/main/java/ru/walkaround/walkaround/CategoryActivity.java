package ru.walkaround.walkaround;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private final List<String> categoryNames = Arrays.asList("Museum", "Park", "Bar", "Sport");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        RecyclerView recyclerView = findViewById(R.id.categories_list);

        List<Category> categories = new ArrayList<>();

        for (String s: categoryNames) {
            Category c = new Category(s);
            c.setImage(getResources().getIdentifier(s.toLowerCase(), "drawable", getPackageName()));
            categories.add(c);
        }

        CategoryRecyclerAdapter categoryRecyclerAdapter = new CategoryRecyclerAdapter(categories, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(categoryRecyclerAdapter);

    }
}

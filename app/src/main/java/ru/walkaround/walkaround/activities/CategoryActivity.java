package ru.walkaround.walkaround.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.walkaround.walkaround.R;
import ru.walkaround.walkaround.adapters.CategoryRecyclerAdapter;
import ru.walkaround.walkaround.model.Category;

public class CategoryActivity extends AppCompatActivity {

    private final List<String> categoryNames = Arrays.asList("Museum", "Park", "Bar", "Sport");

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        RecyclerView recyclerView = findViewById(R.id.categories_list);
        button = findViewById(R.id.category_button);

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

        button.setOnClickListener(v -> startActivity(new Intent(CategoryActivity.this, ChooseRouteActivity.class)));



    }
}

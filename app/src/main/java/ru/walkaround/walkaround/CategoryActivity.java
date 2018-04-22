package ru.walkaround.walkaround;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private final List<String> categoryNames = Arrays.asList("Museum", "Park", "Bar", "Sport");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ListView listView = findViewById(R.id.categories_list);

        List<Category> categories = new ArrayList<>();

        for (String s: categoryNames) {
            Category c = new Category(s);
            c.setImage(getResources().getIdentifier(s.toLowerCase(), "drawable", getPackageName()));
            categories.add(c);
        }
        CategoryArrayAdapter adapter = new CategoryArrayAdapter(this, categories);

        listView.setAdapter(adapter);

    }
}

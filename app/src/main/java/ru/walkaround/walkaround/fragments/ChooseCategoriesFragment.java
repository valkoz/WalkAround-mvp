package ru.walkaround.walkaround.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.walkaround.walkaround.R;
import ru.walkaround.walkaround.adapters.CategoryRecyclerAdapter;
import ru.walkaround.walkaround.listeners.RecyclerViewClickListener;
import ru.walkaround.walkaround.model.Category;

public class ChooseCategoriesFragment extends Fragment {

    private final List<String> categoryNames = Arrays.asList("Entertainment", "Food", "Museum", "Park", "Religion", "Shop");

    private Button button;

    public ChooseCategoriesFragment() {
    }

    public static ChooseCategoriesFragment newInstance() {
        return new ChooseCategoriesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_choose_categories, container, false);
        RecyclerView recyclerView = fragmentView.findViewById(R.id.categories_list);
        button = fragmentView.findViewById(R.id.category_button);

        List<Category> categories = new ArrayList<>();

        for (String s : categoryNames) {
            Category c = new Category(s);
            c.setImage(getResources().getIdentifier("drawable_" + s.toLowerCase(), "drawable", getActivity().getPackageName()));
            categories.add(c);
        }

        RecyclerViewClickListener listener = (view, position) -> {
        };

        CategoryRecyclerAdapter categoryRecyclerAdapter = new CategoryRecyclerAdapter(categories, getActivity(), listener);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(categoryRecyclerAdapter);

        button.setOnClickListener(v -> {
            ChooseRouteFragment chooseRouteFragment = ChooseRouteFragment.newInstance();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            transaction.add(R.id.fragment_content, chooseRouteFragment);
            transaction.hide(ChooseCategoriesFragment.this);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return fragmentView;
    }

}

package com.example.mymall;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainFragment extends Fragment {
    private BottomNavigationView bottomNavView;
    private RecyclerView newItemRecView, popularItemRecView, suggestedItemRecView;
    private GroceryItemAdapter newItemAdapter, popularItemAdapter, suggestedItemAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initViews(view);
        initBottomNavView();


//        Utils.clearSharedPreferences(getActivity());



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecView();
    }

    private void initRecView() {
        newItemAdapter = new GroceryItemAdapter(getActivity());
        newItemRecView.setAdapter(newItemAdapter);
        newItemRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        popularItemAdapter = new GroceryItemAdapter(getActivity());
        popularItemRecView.setAdapter(popularItemAdapter);
        popularItemRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        suggestedItemAdapter = new GroceryItemAdapter(getActivity());
        suggestedItemRecView.setAdapter(suggestedItemAdapter);
        suggestedItemRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));


        ArrayList<GroceryItem> newItems = Utils.getAllItem(getActivity());
        if (null != newItems) {
            Comparator<GroceryItem> newItemComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {
                    return o1.getId() - o2.getId();
                }
            };

            Collections.sort(newItems, Collections.reverseOrder(newItemComparator));
            newItemAdapter.setItems(newItems);
        }

        ArrayList<GroceryItem> popularItems = Utils.getAllItem(getActivity());
        if (null != popularItems) {
            Comparator<GroceryItem> popularItemComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {
                    return o1.getPopularityPoint() - o2.getPopularityPoint();
                }
            };

            Collections.sort(newItems, Collections.reverseOrder(popularItemComparator));
            popularItemAdapter.setItems(popularItems);
        }

        ArrayList<GroceryItem> suggestedItems = Utils.getAllItem(getActivity());
        if (null != suggestedItems) {
            Comparator<GroceryItem> suggestedItemComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {
                    return o1.getUserPoint() - o2.getUserPoint();
                }
            };

            Collections.sort(suggestedItems, Collections.reverseOrder(suggestedItemComparator));
            suggestedItemAdapter.setItems(suggestedItems);
        }

    }
    private void initBottomNavView(){
        bottomNavView.setSelectedItemId(R.id.home);
        // TODO: 01/08/21 finish this
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        break;
                    case R.id.search:
                        Toast.makeText(getActivity(), "Search selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.cart:
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
    private void initViews(View view){
        bottomNavView = view.findViewById(R.id.bottomNavView);
        newItemRecView = view.findViewById(R.id.newItemRecView);
        popularItemRecView = view.findViewById(R.id.popularItemRecView);
        suggestedItemRecView = view.findViewById(R.id.suggestedItemRecView);
    }
}

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

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainFragment extends Fragment {
    private BottomNavigationView bottomNavView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initViews(view);
        initBottomNavView();

        return view;
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
    }
}
